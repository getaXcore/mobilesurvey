package surveyor.id.com.mobilesurvey;

import android.*;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;

import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.util.PathUri;

public class InputPhotoAllActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    Bitmap bitmapAll, cameraBmpAll, cameraBmpKecilAll;
    int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    String getIdOrder, getPhotoName;
    DatabaseManager dm;
    private String latitude,longitude,latitude_lis,longitude_lis,sphoto_latitude,sphoto_longitude;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    private LocationListener locListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_photo_all);

        dm = new DatabaseManager(this);
        latitude    = "";
        longitude   = "";

        getIdOrder      = getIntent().getExtras().getString("id_order");
        getPhotoName    = getIntent().getExtras().getString("photo_name");

        fotoambil();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locListener = new myLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
    }

    /**
     * location if null
     */
    private class myLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                latitude_lis = String.valueOf(location.getLatitude());
                longitude_lis = String.valueOf(location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public void fotoambil(){
        ContentValues value = new ContentValues();
        value.put(MediaStore.Images.Media.TITLE, "New Picture");
        value.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(photoCaptureIntent, PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        String sphoto_nama      = getPhotoName;
        String sphoto_link      = getStringImage(cameraBmpKecilAll);
        String sphoto_bitmap    = getStringImage(cameraBmpAll);
        /*String sphoto_latitude  = latitude;
        String sphoto_longitude = longitude;*/
        String sphoto_id_order  = getIdOrder;

        if(latitude == null){
            sphoto_latitude = latitude_lis;
            sphoto_longitude = longitude_lis;
        }else if(latitude.equals("")){
            sphoto_latitude = latitude_lis;
            sphoto_longitude = longitude_lis;
        }else{
            sphoto_latitude = latitude;
            sphoto_longitude = longitude;
        }


        dm.deletePhotoAll(getPhotoName,getIdOrder);
        dm.addRowPhoto(sphoto_nama, sphoto_link, sphoto_bitmap, sphoto_latitude, sphoto_longitude, sphoto_id_order);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                bitmapAll           = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                cameraBmpAll        = ThumbnailUtils.extractThumbnail(bitmapAll, 1205, 1795);
                cameraBmpKecilAll   = ThumbnailUtils.extractThumbnail(bitmapAll, 100, 100);

                //Log.i("imageUri",imageUri.toString());

                //Get RealPathUri
                PathUri pathUri = new PathUri(getApplicationContext());
                String selectedImagePath = pathUri.getPathFromURI(imageUri);
                //Create File From Uri
                File img_file = new File(selectedImagePath);
                //Get File size
                long imageFileSize = img_file.length()/1000; //in KB

                if (imageFileSize < 3000){ //less than 3 MB
                    uploadImage();
                }else {
                    Toast.makeText(getApplicationContext(),"Ukuran Foto terlalu besar, tidak boleh lebih dari 3MB. Silakan atur kembali pengaturan kamera.",Toast.LENGTH_LONG).show();
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            finish();
        }
    }

    private void getMyLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude    = ""+String.valueOf(mLastLocation.getLatitude());
                longitude   = ""+String.valueOf(mLastLocation.getLongitude());
            }
        } catch (SecurityException e) {
            Log.e("Photo All : ", "error "+e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
