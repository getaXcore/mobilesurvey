package surveyor.id.com.mobilesurvey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.OtherUtil;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    private EditText editTextUsername,editTextPassword;
    private Button buttonLogin;
    private String username,password,latitude, longitude;
    private DatabaseManager dm;
    private TelephonyManager mngr;
    private String identifier = null;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String latitude_lis,longitude_lis;
    private android.location.LocationListener locListener;
    private LocationManager locationManager;
    private String sphoto_latitude,sphoto_longitude;
    private ImageView tampilPassword;
    private int statTamPass;
    private int resError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dm = new DatabaseManager(this);
        statTamPass = 0;
        resError = 1;
        GoogleApiClient();
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


        latitude        = "";
        longitude       = "";
        latitude_lis    = "";
        longitude_lis   = "";

        editTextUsername    = (EditText) findViewById(R.id.et_email);
        editTextPassword    = (EditText) findViewById(R.id.et_password);
        tampilPassword      = (ImageView) findViewById(R.id.tampil_password);
        buttonLogin         = (Button) findViewById(R.id.masuk);


        mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if (mngr != null) {
            identifier = mngr.getDeviceId();
        }
        if (identifier == null || identifier .length() == 0) {
            identifier = Settings.Secure.getString(getContentResolver(), Settings.Secure.
                    ANDROID_ID);
        }
        ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();
        if (data.size() < 1) {
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkLogin();
                }
                });
        }else {
            Intent home = new Intent (this,HomeActivity.class);
            startActivity(home);
            finish();
        }

        tampilPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statTamPass == 0){
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    statTamPass = 1;
                    tampilPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visib_on));
                }else{
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    statTamPass = 0;
                    tampilPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visib_off));
                }


            }
        });
    }

    private void cekJson(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                login();
            }
        }, 2000);
    }

    private void checkLogin(){
        String locationProviders = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (locationProviders == null || locationProviders.equals("")) {
            Toast.makeText(LoginActivity.this,
                    "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }else {
            if(latitude == null){
                sphoto_latitude     = latitude_lis;
                sphoto_longitude    = longitude_lis;
            }else if(latitude.equals("")){
                sphoto_latitude     = latitude_lis;
                sphoto_longitude    = longitude_lis;
            }else{
                sphoto_latitude     = latitude;
                sphoto_longitude    = longitude;
            }

            username = editTextUsername.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();

            login();
        }
    }

    private void login(){
        OtherUtil.hideAlertDialog();
        OtherUtil.showAlertDialogLoading(LoginActivity.this, "Please Wait ...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.URL_SERVICE_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String code = jObj.getString("code");
                    if (code.equals("200")) {
                        String data = jObj.getString("data");
                        JSONArray arrayData = new JSONArray(data);
                        if (arrayData.length() > 0) {
                            JSONObject hData = arrayData.getJSONObject(0);
                            String userid = hData.getString("userid");
                            String username = hData.getString("username");
                            String alamat = hData.getString("alamat");
                            String namalengkap = hData.getString("namalengkap");
                            Log.d("v Add", arrayData.toString());

                            dm.deleteAll();
                            dm.addRow(username, userid, alamat, namalengkap);
                            Intent intent = new Intent(LoginActivity.this,
                                    HomeActivity.class);
                            startActivity(intent);
                        }
                        OtherUtil.hideAlertDialog();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Gagal",
                                Toast.LENGTH_LONG).show();
                        OtherUtil.hideAlertDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    OtherUtil.hideAlertDialog();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resError++;
                if(resError > 3){
                    OtherUtil.hideAlertDialog();
                    Toast.makeText(LoginActivity.this,"Tidak Terhubung",Toast.LENGTH_LONG).show();
                    resError = 1;
                }else {
                    cekJson();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, username);
                map.put(KEY_PASSWORD, password);
                map.put("imei", identifier);
                //map.put("imei", "357884082924529");
                map.put("latitude", ""+sphoto_latitude);
                map.put("longitude", ""+sphoto_longitude);
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    public void GoogleApiClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    private class myLocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                latitude_lis    = String.valueOf(location.getLatitude());
                longitude_lis   = String.valueOf(location.getLongitude());
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

    private void getMyLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = "" + String.valueOf(mLastLocation.getLatitude());
                longitude = "" + String.valueOf(mLastLocation.getLongitude());
            }
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void onLocationChanged(Location location) {
        Log.i("TAG", "Location Changed!");
        Log.i("TAG", " Location: " + location); //I guarantee,I get the changed location here
    }

    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(locListener);
        // locationManager = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locListener);
        OtherUtil.hideAlertDialog();
    }

}