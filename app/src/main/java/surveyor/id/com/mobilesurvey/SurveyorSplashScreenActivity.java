package surveyor.id.com.mobilesurvey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.DataSource;
import surveyor.id.com.mobilesurvey.util.OtherUtil;

public class SurveyorSplashScreenActivity extends AppCompatActivity {
    private DatabaseManager dm;
    private int PERMISSION_ALL = 1;
    private TelephonyManager mngr;
    private String identifier = null;
    private String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE, android.Manifest.
            permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION};
    private TextView txDetailVersion, txtBar;
    private PackageInfo pInfo;
    private int resError;
    private ProgressBar progressBar;
    private Context hsContext;
    private CameraManager mngrCam;
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveyor_splash_screen);
        dm = new DatabaseManager(this);
        resError = 1;

        //insert data propinsi,kota
        dataSource = new DataSource(this);
        dataSource.insertProv();
        dataSource.insertKota();

        mngrCam = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_ALL);
            return;
        }

        txDetailVersion = (TextView) findViewById(R.id.tx_detail_version);
        progressBar = (ProgressBar) findViewById(R.id.pgBar);
        txtBar = (TextView) findViewById(R.id.txtBar);

        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            txDetailVersion.setText(" " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //asalnya nyala, dimatiin dulu
    public void panggilimei() {
        ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();//
        if (data.size() < 1) {
            mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_ALL);
                return;
            }
            identifier = mngr.getDeviceId();

            if (identifier == null || identifier .length() == 0) {
                identifier = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            String locationProviders = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (locationProviders == null || locationProviders.equals("")) {
                Toast.makeText(SurveyorSplashScreenActivity.this,
                        "GPS belum diaktifkan, Mohon nyalakan GPS anda.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }else {
                checkLoginImei();
            }

        }else{
            String locationProviders = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (locationProviders == null || locationProviders.equals("")) {
                Toast.makeText(SurveyorSplashScreenActivity.this,
                        "GPS belum diaktifkan, Mohon nyalakan GPS anda.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }else {
                final int welcomeScreenDisplay = 3000; // 3000 = 3 detik
                Thread welcomeThread = new Thread() {
                    int wait = 0;

                    @Override
                    public void run() {
                        try {
                            super.run();
                            while (wait < welcomeScreenDisplay) {
                                sleep(100);
                                wait += 100;
                            }
                        } catch (Exception e) {
                            System.out.println("EXc=" + e);
                        } finally {
                            String locationProviders = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                            if (locationProviders == null || locationProviders.equals("")) {
                                Toast.makeText(SurveyorSplashScreenActivity.this,
                                        "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            } else {
                                finish();
                                Intent intent = new Intent(SurveyorSplashScreenActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                };
                welcomeThread.start();
            }
        }
    }


    private void cekJson(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginImei();
            }
        }, 2000);
    }


    private void checkLoginImei(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.URL_SERVICE_1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String code = jObj.getString("code");
                    if (code.equals("200")) {

                        Log.d("v Add", jObj.toString());
                        final int welcomeScreenDisplay = 3000; // 3000 = 3 detik
                        Thread welcomeThread = new Thread() {
                            int wait = 0;
                            @Override
                            public void run() {
                                try {
                                    super.run();
                                    while (wait < welcomeScreenDisplay) {
                                        sleep(100);
                                        wait += 100;
                                    }
                                } catch (Exception e) {
                                    System.out.println("EXc=" + e);
                                } finally {
                                    finish();
                                    Intent intent = new Intent(
                                            SurveyorSplashScreenActivity.this,
                                            LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        };
                        welcomeThread.start();
                    } else {
                        Toast.makeText(SurveyorSplashScreenActivity.this,
                                "Imei Tidak Terdaftar", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resError++;
                if(resError > 3){
                    OtherUtil.hideAlertDialog();
                    Toast.makeText(SurveyorSplashScreenActivity.this,"Tidak Terhubung",Toast.LENGTH_LONG).show();
                    resError = 1;
                }else {
                    cekJson();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("imei", identifier);
                //map.put("imei", "357884082924529");
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SurveyorSplashScreenActivity.this);
        requestQueue.add(stringRequest);
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null &&
                permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.
                        PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                panggilimei();
            } else {
                Toast.makeText(this, "Mohon Aktifkan Perizinan untuk dapat menggunakan Aplikasi",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /*public void ambilData(){
        //Ambil Data Provinsi
        txtBar.setText("Menyiapkan data prop...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JsonDataProvinsi", response);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("200")){
                        dm.deleteJsonPilihAll("ListProv");
                        dm.addRowJsonPilih(response, "ListProv");


                        //Ambil Data KabKodya
                        txtBar.setText("Menyiapkan data kab...");
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    String code = jsonObject.getString("code");
                                    if (code.equals("200")) {
                                        dm.deleteJsonPilihAll("ListKabKodya");
                                        dm.addRowJsonPilih(response, "ListKabKodya");

                                        //langsung ke login
                                        Intent intent = new Intent(
                                              SurveyorSplashScreenActivity.this,
                                               LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Log.e("CodeResponseDataKabKodya",code);
                                        Toast.makeText(getApplicationContext(),"Gagal Ambil Data KabKodya. Code "+code,Toast.LENGTH_LONG);
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Gagal memuat data. Mohon periksa koneksi internet Anda",Toast.LENGTH_SHORT);
                                Log.e("GagalAmbilDataKabKodya",String.valueOf(error));
                            }
                        }){
                            protected Map<String,String> getParams() throws AuthFailureError{
                                Map<String,String> params = new HashMap<>();
                                params.put("tk",setter.APK_CODE);
                                return params;
                            }

                        };

                    }else {
                        Log.e("CodeResponseDataProvinsi",code);
                        Toast.makeText(getApplicationContext(),"Gagal Ambil Data Propinsi. Code "+code,Toast.LENGTH_LONG);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Gagal memuat data. Mohon periksa koneksi internet Anda",Toast.LENGTH_SHORT);
                Log.e("GagalAmbilDataProvinsi",String.valueOf(error));
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("tk",setter.APK_CODE);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }*/

    //asalnya nyala, dimatiin dulu
    @Override
    protected void onResume() {
        super.onResume();
       if (hasPermissions(SurveyorSplashScreenActivity.this, PERMISSIONS)) { //asalnya nyala, dimatiin dulu
            /*RootBeer rootBeer = new RootBeer(this);
            if (rootBeer.isRooted()) {
                //we found indication of root
                finish();
                Toast.makeText(this,"Maaf HP anda dalam keadaan ROOT",Toast.LENGTH_LONG).show();
            } else {
                //we didn't find indication of root
                panggilimei();
            }*/
            //panggilimei(); //asalnya nyala, dimatiin dulu
           final int welcomeScreenDisplay = 3000; // 3000 = 3 detik
           Thread welcomeThread = new Thread() {
               int wait = 0;

               @Override
               public void run() {
                   try {
                       super.run();
                       while (wait < welcomeScreenDisplay) {
                           sleep(100);
                           wait += 100;
                       }

                   } catch (Exception e) {
                       System.out.println("EXc=" + e);
                   } finally {

                       //langsung ke login
                       Intent intent = new Intent(
                               SurveyorSplashScreenActivity.this,
                               LoginActivity.class);
                       startActivity(intent);
                       finish();
                   }
               }
           };
           welcomeThread.start();
           //ambilData();
       } else { //asalnya nyala, dimatiin dulu
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL); //asalnya nyala, dimatiin dulu
        } //asalnya nyala, dimatiin dulu

    }

}