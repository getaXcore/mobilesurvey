package surveyor.id.com.mobilesurvey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSebelas;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class InputGaleriPhotoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    Bitmap bitmap,cameraBmp,KecilcameraBmp;
    int SELECT_FILE=11;
    String Get_id_order,Get_photo_name;
    DatabaseManager dm;
    private String latitude,longitude,latitude_lis,longitude_lis,sphoto_latitude,sphoto_longitude;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    private LocationListener locListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_galeri_photo);

        dm = new DatabaseManager(this);

        Get_id_order    = getIntent().getExtras().getString("id_order");
        Get_photo_name  = getIntent().getExtras().getString("photo_name");
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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        String sphoto_link = getStringImage(KecilcameraBmp);
        String sphoto_bitmap = getStringImage(cameraBmp);
        /*String sphoto_latitude = latitude;
        String sphoto_longitude = longitude;*/
        String sphoto_id_order = Get_id_order;

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

        dm.deletePhotoAll(Get_photo_name,Get_id_order);
        dm.addRowPhoto(Get_photo_name, sphoto_link, sphoto_bitmap, sphoto_latitude, sphoto_longitude, sphoto_id_order);
        finish();
        //InputFullFragmentSebelas.dialog_photo.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            try {
                Uri filePath = data.getData();
                Bitmap bitmap_logo = null;

                bitmap_logo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);

                Log.i("bitmap Logo", String.valueOf(bitmap_logo));

                String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                Cursor cur = this.managedQuery(filePath, orientationColumn, null, null, null);
                int orientation = -1;
                if (cur != null && cur.moveToFirst()) {
                    orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                }
                InputStream imageStream = this.getContentResolver().openInputStream(filePath);
                Bitmap bitmapfixrotate = BitmapFactory.decodeStream(imageStream);
                switch(orientation) {
                    case 90:
                        bitmapfixrotate = rotateImage(bitmap_logo, 90);
                        break;
                    case 180:
                        bitmapfixrotate = rotateImage(bitmap_logo, 180);
                        break;
                    case 270:
                        bitmapfixrotate = rotateImage(bitmap_logo, 270);
                        break;
                    default:
                        break;
                }

                KecilcameraBmp = ThumbnailUtils.extractThumbnail(bitmapfixrotate, 400, 400);
                cameraBmp = bitmapfixrotate;

                uploadImage();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            finish();
            //InputFullFragmentSebelas.dialog_photo.dismiss();
        }
    }

    private void getMyLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                latitude = "" + String.valueOf(mLastLocation.getLatitude());
                longitude = "" + String.valueOf(mLastLocation.getLongitude());
                //   callListVolley(latitude, longitude);
            }
        } catch (SecurityException e) {

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
