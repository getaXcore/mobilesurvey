package surveyor.id.com.mobilesurvey;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import surveyor.id.com.mobilesurvey.fragment.MapsHomeFragment;
import surveyor.id.com.mobilesurvey.fragment.PendingSendSurveyFragment;
import surveyor.id.com.mobilesurvey.fragment.PendingSurveyFragment;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.service.BackgroundService;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity implements ActivityCompat.
        OnRequestPermissionsResultCallback, NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<LocationSettingsResult> {
    private String ck_id_message, id_balas_message, id_message, to_id_admin, from_id_admin,
            from_nama, balas_message, entry_date_balas, type_message;
    public Timer timer_lokasi_cek;
    private Fragment fgment;
    Intent mServiceIntent;
    private BackgroundService mBackgroundService;
    Context ctx;
    private String tam_json;
    private String code_update_apk,link_update_apk;
    private Dialog dialog_update_apk;
    private String latitude,longitude;

    public Context getCtx() {
        return ctx;
    }
    private int MY_EXTERNAL_STORAGE_REQUEST_CODE = 3;
    private int MY_LOCATION_REQUEST_CODE = 2;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    String get_username, get_namalengkap;
    private BottomNavigationView bottomNavigation;
    public String cek_in_id_kirimlokasi_up, cek_in_ceklatitude_up, cek_in_ceklongitude_up,
            cek_in_id_surveyor_up, cek_in_time_surveyor_up;
    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE, android.Manifest.
            permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION};
    public String p_photo_nama, p_photo_bitmap, p_photo_latitude, p_photo_longitude, p_photo_id_order;
    public String ceklat, ceklong, cektime;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 200000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 1;
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private DatabaseManager dm;
    private String Get_id_order;
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;
    private String photo_nama, photo_bitmap, photo_id_order, photo_status, photo_latitude, photo_longitude;
    String tag_json_obj = "json_obj_req";
    private int cek_status_survey;
    public Map<String, String> params;
    private String get_id_surveyor;

    private String t_nama,t_mother,t_title,t_marital_status,t_identity_type,t_Npwp_no,t_Birth_place,
            t_Birth_date,t_Identity_no,t_address_ktp,t_province_ktp,t_kab_kodya_ktp,t_kecamatan_ktp,
            t_kelurahan_ktp,t_sandi_dati_2_ktp,t_postal_code_ktp,t_address_home,t_province_home,
            t_kab_kodya_home,t_kecamatan_home,t_kelurahan_home,t_sandi_dati_2_home,
            t_postal_code_home,t_mail_address,t_education,t_sex,t_Nama_panggilan,t_Sandi_lahir,
            t_religion,t_Stay_length,t_Telephone,t_Telephone_2,t_Handphone_1,t_Handphone_2,t_email,
            t_Pekerjaan,t_job_title,t_Name_economy_code,t_Economy_code,t_Company_name,
            t_Company_address,t_Company_province,t_Company_kab_kodya,t_Company_kecamatan,
            t_Company_kelurahan,t_Company_sandi_dati_2,t_Company_postal_code,t_Company_telephone_1,
            t_Company_telephone_2,t_Line_of_business,t_Estabilished_since,t_Company_fax_1,
            t_Spouse_name,t_spouse_title,t_spouse_religion,t_spouse_sex,t_spouse_identity_type,
            t_Spouse_identity_no,t_Spouse_birth_place_or_sandi_lahir,t_Spouse_date_of_birth,
            t_Spouse_address,t_Spouse_province,t_Spouse_kab_kodya,t_Spouse_kecamatan,
            t_Spouse_kelurahan,t_Spouse_postal_code,t_Spouse_sandi_dati_2,t_Spouse_no_handphone,
            t_spouse_occupation_or_pekerjaan,t_Spouse_company_name,t_spouse_job_title,
            t_Spouse_line_of_business,t_Spouse_company_address,t_Spouse_company_telephone,
            t_Spouse_fax,t_has_contact_person,t_contact_name,t_Contact_address,t_Contact_province,
            t_Contact_kab_kodya,t_Contact_kecamatan,t_Contact_kelurahan,t_Contact_sandi_dati_2,
            t_Contact_postal_code,t_contact_telephone,t_relationship,t_tipe_rumah,t_home_status,
            t_jarak_rumah_ke_cabang,t_luas_tanah,t_luas_bangunan_rumah,t_status_kepemilikan_rumah,
            t_klasifikasi_perumahan,t_tempat_menaruh_kendaraan,t_status_garasi_kendaraan,
            t_bentuk_bangunan_rumah,t_kondisi_rumah,t_luas_jalan_masuk_rumah,
            t_status_kepemilikan_rumah_pemohon,t_furniture,t_jarak_tempat_usaha_dari_rumah,
            t_status_kepemilikan_usaha,t_bentuk_bangunan_tempat_usaha,t_lokasi_tempat_usaha,
            t_jumlah_karyawan,t_lama_pemohon_menempati_tempat_usaha_tahun,
            t_lama_pemohon_menempati_tempat_usaha_bulan,t_lama_usaha_berdiri_tahun,
            t_lama_usaha_berdiri_bulan,t_pekerjaan_or_usaha_terkait_ekspor_or_impor,
            t_tujuan_penggunaan_unit,t_kondisi_mobil,t_bagian_kondisi_yang_tidak_baik,
            t_lama_kepemilikan_kendaraan_tahun,t_lama_kepemilikan_kendaraan_bulan,
            t_jumlah_tanggungan,t_jumlah_anak,t_omzet_or_penghasilan_gross,
            t_penghasilan_nett_or_take_home_pay,t_penghasilan_pasangan,t_penghasilan_tambahan,
            t_pengeluaran_or_kebutuhan_hidup,t_total_cicilan_leasing_lain,
            t_balance_terakhir_di_rekening_tabungan,t_rata_rata_mutasi_in_3_bulan_terakhir,
            t_rata_rata_mutasi_out_3_bulan_terakhir,t_collectabilitas_sid_or_slik_tertinggi,
            t_pernah_kredit_di_tempat_lain,t_overdue_tertinggi,t_baki_debet_or_outstanding_hutang,
            t_nama_finance_company,t_alasan_menunggak_khusus_lebih_dari_coll_2,
            t_apakah_direkomendasikan,t_alasan_or_point_penting_rekomendasi_anda,
            t_id_province_ktp,t_id_kab_kodya_ktp,t_id_kecamatan_ktp,t_id_kelurahan_ktp,t_zipcode_ktp,
            t_Spouse_id_province,t_Spouse_id_kab_kodya,t_Spouse_id_kecamatan,t_Spouse_id_kelurahan,t_Spouse_zipcode,
            t_category_name,t_category_id,t_Jenis_pekerjaan,t_Jenis_pekerjaan_code,t_Jenis_pekerjaan_spouse,t_Jenis_pekerjaan_code_spouse;

    private android.location.LocationListener locListener;
    private LocationManager locationManager;
    private String latitude_lis,longitude_lis,sphoto_latitude,sphoto_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        dm = new DatabaseManager(this);
        
        ctx = this;
        mBackgroundService = new BackgroundService(getCtx());
        mServiceIntent = new Intent(getCtx(), mBackgroundService.getClass());
        if (!isMyServiceRunning(mBackgroundService.getClass())) {
            startService(mServiceIntent);
        }
        
        PowerManager.WakeLock screenLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();
        screenLock.release();

        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();//asalnya nyala, dimatiin dulu
        if(data6.size() > 0){//asalnya nyala, dimatiin dulu
            ArrayList<Object> baris = data6.get(0);//asalnya nyala, dimatiin dulu
            get_username    = baris.get(0).toString();//asalnya nyala, dimatiin dulu
            get_namalengkap = baris.get(2).toString();//asalnya nyala, dimatiin dulu
            get_id_surveyor = baris.get(3).toString();//asalnya nyala, dimatiin dulu

            /*get_username = "admincabang01";
            get_namalengkap = "Taufan Septaufani";
            get_id_surveyor = "186";*/
        }//asalnya nyala, dimatiin dulu

        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";
        updateValuesFromBundle(savedInstanceState);

        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
        if (hasPermissions(HomeActivity.this, PERMISSIONS)) {

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("kategori_kendaraan", "Motor");
        fgment = new MapsHomeFragment();
        fgment.setArguments(bundle);
        
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fgment).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Bundle bundle;
                switch (id) {
                    case R.id.navigation_data_new:
                        bundle = new Bundle();
                        bundle.putString("kategori_kendaraan", "Motor");
                        fgment = new MapsHomeFragment();
                        fgment.setArguments(bundle);
                        break;
                    case R.id.navigation_data_approve:
                        bundle = new Bundle();
                        bundle.putString("kategori_kendaraan", "Motor");
                        fgment = new PendingSendSurveyFragment();
                        fgment.setArguments(bundle);
                        break;
                    case R.id.navigation_data_pending:
                        bundle = new Bundle();
                        bundle.putString("kategori_kendaraan", "Motor");
                        fgment = new PendingSurveyFragment();
                        fgment.setArguments(bundle);
                        break;
                   /* case R.id.navigation_data_reject:

                        break;*/
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_fragment, fgment).commit();
                return true;
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = (TextView) header.findViewById(R.id.textView_nav);
        name.setText(get_namalengkap);
        timer_lokasi_cek = new Timer();
        timer_lokasi_cek.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mGoogleApiClient.isConnected() && mCurrentLocation != null) {
                            uploaddata();
                            kirimlokasi();
                            cekchat();
                            updatejson();
                        }
                    }
                });
            }
        }, 18000, 600000);
        cek_status_survey = 0;
        bottomNavigation.setSelectedItemId(R.id.bottom_navigation);



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
    private class myLocationListener implements android.location.LocationListener {

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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_UPDATES);
            }
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }


    private synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void getMyLocation() {
        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                latitude = "" + String.valueOf(mCurrentLocation.getLatitude());
                longitude = "" + String.valueOf(mCurrentLocation.getLongitude());
            }

            SimpleDateFormat gh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mLastUpdateTime = gh.format(new Date());
        } catch (SecurityException e) {

        }
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        //          startActivity(getIntent());
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, android.Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;

            }
        });

    }

    private void updateUI() {
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            ceklat = String.valueOf(mCurrentLocation.getLatitude());
            ceklong = String.valueOf(mCurrentLocation.getLongitude());
            cektime = String.valueOf(mLastUpdateTime);
        }
    }

    public void updatejson() {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_json_all,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            dm.deleteTugasAll("List All");
                            dm.addRowTugas(String.valueOf(response), "List All");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_surveyor", get_id_surveyor);
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        } else if (requestCode == MY_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        } else if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent o = getIntent();
                finish();
                startActivity(o);
            }

        }

    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                uploaddata();
            }
        }, 8000);

        kirimlokasi();
        cekchat();
        updatejson();
        UpdateUpdateApk();

        UpdateGelar();
        UpdateTitle();
        UpdateMaritalstatus();
        UpdateIdentityType();
        UpdateMailAddress();
        UpdateTipeRumah();
        UpdateHomeStatus();
        UpdateStatusKepemilikanRumah();
        UpdateKlasifikasiPerumahan();
        UpdateTempatMenaruhKendaraan();
        UpdateStatusGarasiKendaraan();
        UpdateBentukBangunanRumah();
        UpdateKondisiRumah();
        UpdateLuasJalanMasukRumah();
        UpdateStatusKepemilikanRumahPemohon();
        UpdateStatusKepemilikanUsaha();
        UpdateBentukBangunanTmpUsaha();
        UpdateLokasiTempatUsaha();
        UpdateJumlahKaryawan();
        UpdateTujuanPenggunaanUnit();
        UpdateKondisiMobil();
        UpdateEducation();
        UpdateSex();
        UpdateReligion();
        UpdateJobTitle();
        UpdateSpousePekerjaan();
        UpdateHasContactPerson();
        UpdateRelationship();
        UpdateReturn();
        UpdateEconomyCode();
        UpdateCancel();
        UpdatePending();

        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris_status();
        if (data6.size() > 0) {
            dm.deleteAll_status();
            startActivity(getIntent());
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            //     stopLocationUpdates();
        }
        locationManager.removeUpdates(locListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //       mGoogleApiClient.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sync) {
            uploaddata();
            uploadphoto();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_tugas_survey) {
            String locationProviders = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (locationProviders == null || locationProviders.equals("")) {
                /*Toast.makeText(HomeActivity.this, "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                        Toast.LENGTH_LONG).show();*/
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }else{
                Intent ab = new Intent(HomeActivity.this, ListTugasActivity.class);
                Bundle detail = new Bundle();
                detail.putString("username", get_username);

                ab.putExtras(detail);
                startActivity(ab);
            }

            // Handle the camera action
        } else if (id == R.id.nav_tugas_survey_pending) {
        //    Intent ab = new Intent(HomeActivity.this, HomeActivity.class);
            Intent ab = new Intent(HomeActivity.this, ListTugasPendingActivity.class);
            Bundle detail = new Bundle();
            detail.putString("username", get_username);

            ab.putExtras(detail);
            startActivity(ab);
        } else if (id == R.id.nav_history_survey) {
            Intent ab = new Intent(HomeActivity.this, HistoryActivity.class);
            Bundle detail = new Bundle();
            detail.putString("username", get_username);

            ab.putExtras(detail);
            startActivity(ab);
        } else if (id == R.id.nav_lanjut_input_data_survey) {
            Intent ab = new Intent(HomeActivity.this, LanjutSurveyActivity.class);
            Bundle detail = new Bundle();
            detail.putString("username", get_username);

            ab.putExtras(detail);
            startActivity(ab);
        } else if (id == R.id.nav_achievement) {
            Intent ab = new Intent(HomeActivity.this, AchievementActivity.class);
            Bundle detail = new Bundle();
            detail.putString("username", get_username);

            ab.putExtras(detail);
            startActivity(ab);
        } else if (id == R.id.nav_pemberitahuan) {
            Intent ab = new Intent(HomeActivity.this, DaftarChatActivity.class);
            Bundle detail = new Bundle();
            detail.putString("username", get_username);

            ab.putExtras(detail);
            startActivity(ab);
        } else if (id == R.id.nav_keluar) {
            dm.deleteAll();

            Intent ab = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(ab);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //       mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        SimpleDateFormat gh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mLastUpdateTime = gh.format(new Date());

        updateLocationUI();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void uploadphoto(){
        ArrayList<ArrayList<Object>> statusTerkirim = dm.ambilBarisStatusTerkirim();
        if (statusTerkirim.size() > 0) {
            ArrayList<Object> baris_statusTerkirim = statusTerkirim.get(0);
            String checkIdOrder  = ""+baris_statusTerkirim.get(1);


            ArrayList<ArrayList<Object>> dataphoto = dm.ambilBarisPhotoAll(checkIdOrder);
            if (dataphoto.size() > 0) {
                ArrayList<Object> baris = dataphoto.get(0);
                photo_nama      = ""+baris.get(0);
                photo_bitmap    = ""+baris.get(2);
                photo_latitude  = ""+baris.get(3);
                photo_longitude = ""+baris.get(4);
                photo_id_order  = ""+baris.get(5);
                photo_status    = ""+baris.get(6);

                if(photo_latitude == null){
                    photo_latitude = "";
                }
                if(photo_longitude == null){
                    photo_longitude = "";
                }

                if(photo_latitude.equals("null")){
                    photo_latitude = "";
                }
                if(photo_longitude.equals("null")){
                    photo_longitude = "";
                }
                if(photo_status.equals("1")){
                    simpan_photo(photo_nama,photo_bitmap,photo_latitude,photo_longitude,photo_id_order);
                }
            }else{
                statusKirim1(checkIdOrder);
            }
        }
    }



    private void statusKirim1(final String hasilIdOrder){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPDATE_STATUS_SURVEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        JSONObject jObjck = null;
                        try {
                            jObjck = new JSONObject(response);
                            String code = jObjck.getString("code");
                            if (code.equals("200")) {
                                dm.deleteStatusTerkirim(hasilIdOrder);
                                //uploadphoto();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params1 = new HashMap<String, String>();

                params1.put("id_order", hasilIdOrder);
                params1.put("tk", setter.APK_CODE);
                Log.d(TAG, "" + params1);
                return params1;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public void simpan_photo(String s_photo_name, String s_photo_bitmap, String s_photo_latitude,
                             String s_photo_longitude, String s_photo_id_order){
        p_photo_nama        = s_photo_name;
        p_photo_bitmap      = s_photo_bitmap;
        p_photo_latitude    = s_photo_latitude;
        p_photo_longitude   = s_photo_longitude;
        p_photo_id_order    = s_photo_id_order;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPLOAD_URL_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        JSONObject jObjck = null;
                        try {
                            jObjck = new JSONObject(response);
                            String code = jObjck.getString("code");
                            if (code.equals("200")) {
                                dm.deletePhotoAll(p_photo_nama,p_photo_id_order);
                                uploadphoto();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                params = new HashMap<String, String>();

                params.put("photo_nama", p_photo_nama);
                params.put("photo_bitmap", p_photo_bitmap);
                params.put("photo_latitude", p_photo_latitude);
                params.put("photo_longitude", p_photo_longitude);
                params.put("id_order", p_photo_id_order);
                params.put("tk", setter.APK_CODE);
                Log.d(TAG, "" + params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public void uploaddata(){
        ArrayList<ArrayList<Object>> data10 = dm.ambilBarisJson("Inputan");//
        if (data10.size() > 0) {
            for (int i = 0; i < data10.size(); i++) {
                ArrayList<Object> baris = data10.get(0);
                //getmjson = baris.get(0).toString();
                Get_id_order = baris.get(1).toString();

                ArrayList<Object> arrayLists = dm.ambilBarisSurvey1Tbh(Get_id_order);
                ArrayList<Object> arrayLists3 = dm.ambilBarisSurvey3Tbh(Get_id_order);

                ArrayList<ArrayList<Object>> t_data_inputan = dm.ambilBarisSurvey(Get_id_order);
                if (t_data_inputan.size() > 0 && arrayLists.size() > 0) {

                    if(mCurrentLocation != null){
                        String check_lat = ""+String.valueOf(mCurrentLocation.getLatitude());
                        String check_long = ""+String.valueOf(mCurrentLocation.getLongitude());

                        if(check_lat == null){
                            sphoto_latitude = latitude_lis;
                            sphoto_longitude = longitude_lis;
                        }else if(check_lat.equals("")){
                            sphoto_latitude = latitude_lis;
                            sphoto_longitude = longitude_lis;
                        }else{
                            sphoto_latitude = check_lat;
                            sphoto_longitude = check_long;
                        }
                    }else{
                        sphoto_latitude = latitude_lis;
                        sphoto_longitude = longitude_lis;
                    }

                    t_id_province_ktp                               = ""+arrayLists.get(0);
                    t_id_kab_kodya_ktp                              = ""+arrayLists.get(1);
                    t_id_kecamatan_ktp                              = ""+arrayLists.get(2);
                    t_id_kelurahan_ktp                              = ""+arrayLists.get(3);
                    t_zipcode_ktp                                   = ""+arrayLists.get(4);

                    t_Spouse_id_province                            = ""+arrayLists3.get(0);
                    t_Spouse_id_kab_kodya                           = ""+arrayLists3.get(1);
                    t_Spouse_id_kecamatan                           = ""+arrayLists3.get(2);
                    t_Spouse_id_kelurahan                           = ""+arrayLists3.get(3);
                    t_Spouse_zipcode                                = ""+arrayLists3.get(4);


                    ArrayList<Object> baris_inputan = t_data_inputan.get(0);
                    t_nama                                          = ""+baris_inputan.get(2);
                    t_mother                                        = ""+baris_inputan.get(3);
                    t_title                                         = ""+baris_inputan.get(4);
                    t_marital_status                                = ""+baris_inputan.get(5);
                    t_identity_type                                 = ""+baris_inputan.get(6);
                    t_Npwp_no                                       = ""+baris_inputan.get(7);
                    t_Birth_place                                   = ""+baris_inputan.get(8);
                    t_Birth_date                                    = ""+baris_inputan.get(9);
                    t_Identity_no                                   = ""+baris_inputan.get(10);
                    t_address_ktp                                   = ""+baris_inputan.get(11);
                    t_province_ktp                                  = ""+baris_inputan.get(12);
                    t_kab_kodya_ktp                                 = ""+baris_inputan.get(13);
                    t_kecamatan_ktp                                 = ""+baris_inputan.get(14);
                    t_kelurahan_ktp                                 = ""+baris_inputan.get(15);
                    t_sandi_dati_2_ktp                              = ""+baris_inputan.get(16);
                    t_postal_code_ktp                               = ""+baris_inputan.get(17);
                    t_address_home                                  = ""+baris_inputan.get(18);
                    t_province_home                                 = ""+baris_inputan.get(19);
                    t_kab_kodya_home                                = ""+baris_inputan.get(20);
                    t_kecamatan_home                                = ""+baris_inputan.get(21);
                    t_kelurahan_home                                = ""+baris_inputan.get(22);
                    t_sandi_dati_2_home                             = ""+baris_inputan.get(23);
                    t_postal_code_home                              = ""+baris_inputan.get(24);
                    t_mail_address                                  = ""+baris_inputan.get(25);
                    t_education                                     = ""+baris_inputan.get(26);
                    t_sex                                           = ""+baris_inputan.get(27);
                    t_Nama_panggilan                                = ""+baris_inputan.get(28);
                    t_Sandi_lahir                                   = ""+baris_inputan.get(29);
                    t_religion                                      = ""+baris_inputan.get(30);
                    t_Stay_length                                   = ""+baris_inputan.get(31);
                    t_Telephone                                     = ""+baris_inputan.get(32);
                    t_Telephone_2                                   = ""+baris_inputan.get(33);
                    t_Handphone_1                                   = ""+baris_inputan.get(34);
                    t_Handphone_2                                   = ""+baris_inputan.get(35);
                    t_email                                         = ""+baris_inputan.get(36);

                    t_Pekerjaan                                     = ""+baris_inputan.get(37);
                    t_job_title                                     = ""+baris_inputan.get(38);
                    t_Name_economy_code                             = ""+baris_inputan.get(39);
                    t_Economy_code                                  = ""+baris_inputan.get(40);
                    t_Company_name                                  = ""+baris_inputan.get(41);
                    t_Company_address                               = ""+baris_inputan.get(42);
                    t_Company_province                              = ""+baris_inputan.get(43);
                    t_Company_kab_kodya                             = ""+baris_inputan.get(44);
                    t_Company_kecamatan                             = ""+baris_inputan.get(45);
                    t_Company_kelurahan                             = ""+baris_inputan.get(46);
                    t_Company_sandi_dati_2                          = ""+baris_inputan.get(47);
                    t_Company_postal_code                           = ""+baris_inputan.get(48);
                    t_Company_telephone_1                           = ""+baris_inputan.get(49);
                    t_Company_telephone_2                           = ""+baris_inputan.get(50);
                    t_Line_of_business                              = ""+baris_inputan.get(51);
                    t_Estabilished_since                            = ""+baris_inputan.get(52);
                    t_Company_fax_1                                 = ""+baris_inputan.get(53);

                    t_Spouse_name                                   = ""+baris_inputan.get(54);
                    t_spouse_title                                  = ""+baris_inputan.get(55);
                    t_spouse_religion                               = ""+baris_inputan.get(56);
                    t_spouse_sex                                    = ""+baris_inputan.get(57);
                    t_spouse_identity_type                          = ""+baris_inputan.get(58);
                    t_Spouse_identity_no                            = ""+baris_inputan.get(59);
                    t_Spouse_birth_place_or_sandi_lahir             = ""+baris_inputan.get(60);
                    t_Spouse_date_of_birth                          = ""+baris_inputan.get(61);
                    t_Spouse_address                                = ""+baris_inputan.get(62);
                    t_Spouse_province                               = ""+baris_inputan.get(63);
                    t_Spouse_kab_kodya                              = ""+baris_inputan.get(64);
                    t_Spouse_kecamatan                              = ""+baris_inputan.get(65);
                    t_Spouse_kelurahan                              = ""+baris_inputan.get(66);
                    t_Spouse_postal_code                            = ""+baris_inputan.get(67);
                    t_Spouse_sandi_dati_2                           = ""+baris_inputan.get(68);
                    t_Spouse_no_handphone                           = ""+baris_inputan.get(69);
                    t_spouse_occupation_or_pekerjaan                = ""+baris_inputan.get(70);
                    t_Spouse_company_name                           = ""+baris_inputan.get(71);
                    t_spouse_job_title                              = ""+baris_inputan.get(72);
                    t_Spouse_line_of_business                       = ""+baris_inputan.get(73);
                    t_Spouse_company_address                        = ""+baris_inputan.get(74);
                    t_Spouse_company_telephone                      = ""+baris_inputan.get(75);
                    t_Spouse_fax                                    = ""+baris_inputan.get(76);

                    t_has_contact_person                            = ""+baris_inputan.get(77);
                    t_contact_name                                  = ""+baris_inputan.get(78);
                    t_Contact_address                               = ""+baris_inputan.get(79);
                    t_Contact_province                              = ""+baris_inputan.get(80);
                    t_Contact_kab_kodya                             = ""+baris_inputan.get(81);
                    t_Contact_kecamatan                             = ""+baris_inputan.get(82);
                    t_Contact_kelurahan                             = ""+baris_inputan.get(83);
                    t_Contact_sandi_dati_2                          = ""+baris_inputan.get(84);
                    t_Contact_postal_code                           = ""+baris_inputan.get(85);
                    t_contact_telephone                             = ""+baris_inputan.get(86);
                    t_relationship                                  = ""+baris_inputan.get(87);

                    t_tipe_rumah                                    = ""+baris_inputan.get(88);
                    t_home_status                                   = ""+baris_inputan.get(89);
                    t_jarak_rumah_ke_cabang                         = ""+baris_inputan.get(90);
                    t_luas_tanah                                    = ""+baris_inputan.get(91);
                    t_luas_bangunan_rumah                           = ""+baris_inputan.get(92);
                    t_status_kepemilikan_rumah                      = ""+baris_inputan.get(93);
                    t_klasifikasi_perumahan                         = ""+baris_inputan.get(94);
                    t_tempat_menaruh_kendaraan                      = ""+baris_inputan.get(95);
                    t_status_garasi_kendaraan                       = ""+baris_inputan.get(96);
                    t_bentuk_bangunan_rumah                         = ""+baris_inputan.get(97);
                    t_kondisi_rumah                                 = ""+baris_inputan.get(98);
                    t_luas_jalan_masuk_rumah                        = ""+baris_inputan.get(99);
                    t_status_kepemilikan_rumah_pemohon              = ""+baris_inputan.get(100);
                    t_furniture                                     = ""+baris_inputan.get(101);

                    t_jarak_tempat_usaha_dari_rumah                 = ""+baris_inputan.get(102);
                    t_status_kepemilikan_usaha                      = ""+baris_inputan.get(103);
                    t_bentuk_bangunan_tempat_usaha                  = ""+baris_inputan.get(104);
                    t_lokasi_tempat_usaha                           = ""+baris_inputan.get(105);
                    t_jumlah_karyawan                               = ""+baris_inputan.get(106);
                    t_lama_pemohon_menempati_tempat_usaha_tahun     = ""+baris_inputan.get(107);
                    t_lama_pemohon_menempati_tempat_usaha_bulan     = ""+baris_inputan.get(108);
                    t_lama_usaha_berdiri_tahun                      = ""+baris_inputan.get(109);
                    t_lama_usaha_berdiri_bulan                      = ""+baris_inputan.get(110);
                    t_pekerjaan_or_usaha_terkait_ekspor_or_impor    = ""+baris_inputan.get(111);

                    t_tujuan_penggunaan_unit                        = ""+baris_inputan.get(112);
                    t_kondisi_mobil                                 = ""+baris_inputan.get(113);
                    t_bagian_kondisi_yang_tidak_baik                = ""+baris_inputan.get(114);
                    t_lama_kepemilikan_kendaraan_tahun              = ""+baris_inputan.get(115);
                    t_lama_kepemilikan_kendaraan_bulan              = ""+baris_inputan.get(116);

                    t_jumlah_tanggungan                             = ""+baris_inputan.get(117);
                    t_jumlah_anak                                   = ""+baris_inputan.get(118);

                    t_omzet_or_penghasilan_gross                    = ""+baris_inputan.get(119);
                    t_penghasilan_nett_or_take_home_pay             = ""+baris_inputan.get(120);
                    t_penghasilan_pasangan                          = ""+baris_inputan.get(121);
                    t_penghasilan_tambahan                          = ""+baris_inputan.get(122);
                    t_pengeluaran_or_kebutuhan_hidup                = ""+baris_inputan.get(123);
                    t_total_cicilan_leasing_lain                    = ""+baris_inputan.get(124);
                    t_balance_terakhir_di_rekening_tabungan         = ""+baris_inputan.get(125);
                    t_rata_rata_mutasi_in_3_bulan_terakhir          = ""+baris_inputan.get(126);
                    t_rata_rata_mutasi_out_3_bulan_terakhir         = ""+baris_inputan.get(127);

                    t_collectabilitas_sid_or_slik_tertinggi         = ""+baris_inputan.get(128);
                    t_pernah_kredit_di_tempat_lain                  = ""+baris_inputan.get(129);
                    t_overdue_tertinggi                             = ""+baris_inputan.get(130);
                    t_baki_debet_or_outstanding_hutang              = ""+baris_inputan.get(131);
                    t_nama_finance_company                          = ""+baris_inputan.get(132);
                    t_alasan_menunggak_khusus_lebih_dari_coll_2     = ""+baris_inputan.get(133);

                    t_apakah_direkomendasikan                       = ""+baris_inputan.get(134);
                    t_alasan_or_point_penting_rekomendasi_anda      = ""+baris_inputan.get(135);

                    t_category_name                                 = ""+baris.get(136);
                    t_category_id                                   = ""+baris.get(137);


                    t_Jenis_pekerjaan                               = ""+baris.get(140);
                    t_Jenis_pekerjaan_code                          = ""+baris.get(141);

                    t_Jenis_pekerjaan_spouse                        = ""+baris.get(144);
                    t_Jenis_pekerjaan_code_spouse                   = ""+baris.get(145);



                    //1
                    if(t_nama.equals("null")){
                        t_nama = "";
                    }
                    if(t_mother.equals("null")){
                        t_mother = "";
                    }
                    if(t_title.equals("null")){
                        t_title = "";
                    }
                    if(t_marital_status.equals("null")){
                        t_marital_status = "";
                    }
                    if(t_identity_type.equals("null")){
                        t_identity_type = "";
                    }


                    //6
                    if(t_Npwp_no.equals("null")){
                        t_Npwp_no = "";
                    }
                    if(t_Birth_place.equals("null")){
                        t_Birth_place = "";
                    }
                    if(t_Birth_date.equals("null")){
                        t_Birth_date = "";
                    }
                    if(t_Identity_no.equals("null")){
                        t_Identity_no = "";
                    }
                    if(t_address_ktp.equals("null")){
                        t_address_ktp = "";
                    }


                    //11
                    if(t_province_ktp.equals("null")){
                        t_province_ktp = "";
                    }
                    if (t_id_province_ktp.equals("null")){
                        t_id_province_ktp = "";
                    }
                    if(t_kab_kodya_ktp.equals("null")){
                        t_kab_kodya_ktp = "";
                    }
                    if (t_id_kab_kodya_ktp.equals("null")){
                        t_id_kab_kodya_ktp = "";
                    }
                    if(t_kecamatan_ktp.equals("null")){
                        t_kecamatan_ktp = "";
                    }
                    if (t_id_kecamatan_ktp.equals("null")){
                        t_id_kecamatan_ktp = "";
                    }
                    if(t_kelurahan_ktp.equals("null")){
                        t_kelurahan_ktp = "";
                    }
                    if (t_id_kelurahan_ktp.equals("null")){
                        t_id_kelurahan_ktp = "";
                    }
                    if(t_sandi_dati_2_ktp.equals("null")){
                        t_sandi_dati_2_ktp = "";
                    }


                    //16
                    if(t_postal_code_ktp.equals("null")){
                        t_postal_code_ktp = "";
                    }
                    if (t_zipcode_ktp.equals("null")){
                        t_zipcode_ktp = "";
                    }
                    if(t_address_home.equals("null")){
                        t_address_home = "";
                    }
                    if(t_province_home.equals("null")){
                        t_province_home = "";
                    }
                    if(t_kab_kodya_home.equals("null")){
                        t_kab_kodya_home = "";
                    }
                    if(t_kecamatan_home.equals("null")){
                        t_kecamatan_home = "";
                    }


                    //21
                    if(t_kelurahan_home.equals("null")){
                        t_kelurahan_home = "";
                    }
                    if(t_sandi_dati_2_home.equals("null")){
                        t_sandi_dati_2_home = "";
                    }
                    if(t_postal_code_home.equals("null")){
                        t_postal_code_home = "";
                    }
                    if(t_mail_address.equals("null")){
                        t_mail_address = "";
                    }
                    if(t_education.equals("null")){
                        t_education = "";
                    }


                    //26
                    if(t_sex.equals("null")){
                        t_sex = "";
                    }
                    if(t_Nama_panggilan.equals("null")){
                        t_Nama_panggilan = "";
                    }
                    if(t_Sandi_lahir.equals("null")){
                        t_Sandi_lahir = "";
                    }
                    if(t_religion.equals("null")){
                        t_religion = "";
                    }
                    if(t_Stay_length.equals("null")){
                        t_Stay_length = "";
                    }


                    //31
                    if(t_Telephone.equals("null")){
                        t_Telephone = "";
                    }
                    if(t_Telephone_2.equals("null")){
                        t_Telephone_2 = "";
                    }
                    if(t_Handphone_1.equals("null")){
                        t_Handphone_1 = "";
                    }
                    if(t_Handphone_2.equals("null")){
                        t_Handphone_2 = "";
                    }
                    if(t_email.equals("null")){
                        t_email = "";
                    }


                    //36
                    if(t_Pekerjaan.equals("null")){
                        t_Pekerjaan = "";
                    }
                    if(t_job_title.equals("null")){
                        t_job_title = "";
                    }
                    if(t_Name_economy_code.equals("null")){
                        t_Name_economy_code = "";
                    }
                    if(t_Economy_code.equals("null")){
                        t_Economy_code = "";
                    }
                    if(t_Company_name.equals("null")){
                        t_Company_name = "";
                    }


                    //41
                    if(t_Company_address.equals("null")){
                        t_Company_address = "";
                    }
                    if(t_Company_province.equals("null")){
                        t_Company_province = "";
                    }
                    if(t_Company_kab_kodya.equals("null")){
                        t_Company_kab_kodya = "";
                    }
                    if(t_Company_kecamatan.equals("null")){
                        t_Company_kecamatan = "";
                    }
                    if(t_Company_kelurahan.equals("null")){
                        t_Company_kelurahan = "";
                    }


                    //46
                    if(t_Company_sandi_dati_2.equals("null")){
                        t_Company_sandi_dati_2 = "";
                    }
                    if(t_Company_postal_code.equals("null")){
                        t_Company_postal_code = "";
                    }
                    if(t_Company_telephone_1.equals("null")){
                        t_Company_telephone_1 = "";
                    }
                    if(t_Company_telephone_2.equals("null")){
                        t_Company_telephone_2 = "";
                    }
                    if(t_Line_of_business.equals("null")){
                        t_Line_of_business = "";
                    }


                    //51
                    if(t_Estabilished_since.equals("null")){
                        t_Estabilished_since = "";
                    }
                    if(t_Company_fax_1.equals("null")){
                        t_Company_fax_1 = "";
                    }
                    if(t_Spouse_name.equals("null")){
                        t_Spouse_name = "";
                    }
                    if(t_spouse_title.equals("null")){
                        t_spouse_title = "";
                    }
                    if(t_spouse_religion.equals("null")){
                        t_spouse_religion = "";
                    }


                    //56
                    if(t_spouse_sex.equals("null")){
                        t_spouse_sex = "";
                    }
                    if(t_spouse_identity_type.equals("null")){
                        t_spouse_identity_type = "";
                    }
                    if(t_Spouse_identity_no.equals("null")){
                        t_Spouse_identity_no = "";
                    }
                    if(t_Spouse_birth_place_or_sandi_lahir.equals("null")){
                        t_Spouse_birth_place_or_sandi_lahir = "";
                    }
                    /*if(t_Spouse_date_of_birth.equals("null")){
                        t_Spouse_date_of_birth = "";
                    }*/


                    //61
                    if(t_Spouse_address.equals("null")){
                        t_Spouse_address = "";
                    }
                    if(t_Spouse_province.equals("null")){
                        t_Spouse_province = "";
                    }
                    if (t_Spouse_id_province.equals("null")){
                        t_Spouse_id_province = "";
                    }
                    if(t_Spouse_kab_kodya.equals("null")){
                        t_Spouse_kab_kodya = "";
                    }
                    if (t_Spouse_id_kab_kodya.equals("null")){
                        t_Spouse_id_kab_kodya = "";
                    }
                    if(t_Spouse_kecamatan.equals("null")){
                        t_Spouse_kecamatan = "";
                    }
                    if (t_Spouse_id_kecamatan.equals("null")){
                        t_Spouse_id_kecamatan = "";
                    }
                    if(t_Spouse_kelurahan.equals("null")){
                        t_Spouse_kelurahan = "";
                    }
                    if (t_Spouse_id_kelurahan.equals("null")){
                        t_Spouse_id_kelurahan = "";
                    }


                    //66
                    if(t_Spouse_postal_code.equals("null")){
                        t_Spouse_postal_code = "";
                    }
                    if (t_Spouse_zipcode.equals("null")){
                        t_Spouse_zipcode = "";
                    }
                    if(t_Spouse_sandi_dati_2.equals("null")){
                        t_Spouse_sandi_dati_2 = "";
                    }
                    if(t_Spouse_no_handphone.equals("null")){
                        t_Spouse_no_handphone = "";
                    }
                    if(t_spouse_occupation_or_pekerjaan.equals("null")){
                        t_spouse_occupation_or_pekerjaan = "";
                    }
                    if(t_Spouse_company_name.equals("null")){
                        t_Spouse_company_name = "";
                    }


                    //71
                    if(t_spouse_job_title.equals("null")){
                        t_spouse_job_title = "";
                    }
                    if(t_Spouse_line_of_business.equals("null")){
                        t_Spouse_line_of_business = "";
                    }
                    if(t_Spouse_company_address.equals("null")){
                        t_Spouse_company_address = "";
                    }
                    if(t_Spouse_company_telephone.equals("null")){
                        t_Spouse_company_telephone = "";
                    }
                    if(t_Spouse_fax.equals("null")){
                        t_Spouse_fax = "";
                    }


                    //76
                    if(t_has_contact_person.equals("null")){
                        t_has_contact_person = "";
                    }
                    if(t_contact_name.equals("null")){
                        t_contact_name = "";
                    }
                    if(t_Contact_address.equals("null")){
                        t_Contact_address = "";
                    }
                    if(t_Contact_province.equals("null")){
                        t_Contact_province = "";
                    }
                    if(t_Contact_kab_kodya.equals("null")){
                        t_Contact_kab_kodya = "";
                    }


                    //81
                    if(t_Contact_kecamatan.equals("null")){
                        t_Contact_kecamatan = "";
                    }
                    if(t_Contact_kelurahan.equals("null")){
                        t_Contact_kelurahan = "";
                    }
                    if(t_Contact_sandi_dati_2.equals("null")){
                        t_Contact_sandi_dati_2 = "";
                    }
                    if(t_Contact_postal_code.equals("null")){
                        t_Contact_postal_code = "";
                    }
                    if(t_contact_telephone.equals("null")){
                        t_contact_telephone = "";
                    }


                    //86
                    if(t_relationship.equals("null")){
                        t_relationship = "";
                    }
                    if(t_tipe_rumah.equals("null")){
                        t_tipe_rumah = "";
                    }
                    if(t_home_status.equals("null")){
                        t_home_status = "";
                    }
                    if(t_jarak_rumah_ke_cabang.equals("null")){
                        t_jarak_rumah_ke_cabang = "";
                    }
                    if(t_luas_tanah.equals("null")){
                        t_luas_tanah = "";
                    }


                    //91
                    if(t_luas_bangunan_rumah.equals("null")){
                        t_luas_bangunan_rumah = "";
                    }
                    if(t_status_kepemilikan_rumah.equals("null")){
                        t_status_kepemilikan_rumah = "";
                    }
                    if(t_klasifikasi_perumahan.equals("null")){
                        t_klasifikasi_perumahan = "";
                    }
                    if(t_tempat_menaruh_kendaraan.equals("null")){
                        t_tempat_menaruh_kendaraan = "";
                    }
                    if(t_status_garasi_kendaraan.equals("null")){
                        t_status_garasi_kendaraan = "";
                    }


                    //96
                    if(t_bentuk_bangunan_rumah.equals("null")){
                        t_bentuk_bangunan_rumah = "";
                    }
                    if(t_kondisi_rumah.equals("null")){
                        t_kondisi_rumah = "";
                    }
                    if(t_luas_jalan_masuk_rumah.equals("null")){
                        t_luas_jalan_masuk_rumah = "";
                    }
                    if(t_status_kepemilikan_rumah_pemohon.equals("null")){
                        t_status_kepemilikan_rumah_pemohon = "";
                    }
                    if(t_furniture.equals("null")){
                        t_furniture = "";
                    }


                    //101
                    if(t_jarak_tempat_usaha_dari_rumah.equals("null")){
                        t_jarak_tempat_usaha_dari_rumah = "";
                    }
                    if(t_status_kepemilikan_usaha.equals("null")){
                        t_status_kepemilikan_usaha = "";
                    }
                    if(t_bentuk_bangunan_tempat_usaha.equals("null")){
                        t_bentuk_bangunan_tempat_usaha = "";
                    }
                    if(t_lokasi_tempat_usaha.equals("null")){
                        t_lokasi_tempat_usaha = "";
                    }
                    if(t_jumlah_karyawan.equals("null")){
                        t_jumlah_karyawan = "";
                    }


                    //106
                    if(t_lama_pemohon_menempati_tempat_usaha_tahun.equals("null")){
                        t_lama_pemohon_menempati_tempat_usaha_tahun = "";
                    }
                    if(t_lama_pemohon_menempati_tempat_usaha_bulan.equals("null")){
                        t_lama_pemohon_menempati_tempat_usaha_bulan = "";
                    }
                    if(t_lama_usaha_berdiri_tahun.equals("null")){
                        t_lama_usaha_berdiri_tahun = "";
                    }
                    if(t_lama_usaha_berdiri_bulan.equals("null")){
                        t_lama_usaha_berdiri_bulan = "";
                    }
                    if(t_pekerjaan_or_usaha_terkait_ekspor_or_impor.equals("null")){
                        t_pekerjaan_or_usaha_terkait_ekspor_or_impor = "";
                    }


                    //111
                    if(t_tujuan_penggunaan_unit.equals("null")){
                        t_tujuan_penggunaan_unit = "";
                    }
                    if(t_kondisi_mobil.equals("null")){
                        t_kondisi_mobil = "";
                    }
                    if(t_bagian_kondisi_yang_tidak_baik.equals("null")){
                        t_bagian_kondisi_yang_tidak_baik = "";
                    }
                    if(t_lama_kepemilikan_kendaraan_tahun.equals("null")){
                        t_lama_kepemilikan_kendaraan_tahun = "";
                    }
                    if(t_lama_kepemilikan_kendaraan_bulan.equals("null")){
                        t_lama_kepemilikan_kendaraan_bulan = "";
                    }


                    //116
                    if(t_jumlah_tanggungan.equals("null")){
                        t_jumlah_tanggungan = "";
                    }
                    if(t_jumlah_anak.equals("null")){
                        t_jumlah_anak = "";
                    }
                    if(t_omzet_or_penghasilan_gross.equals("null")){
                        t_omzet_or_penghasilan_gross = "";
                    }
                    if(t_penghasilan_nett_or_take_home_pay.equals("null")){
                        t_penghasilan_nett_or_take_home_pay = "";
                    }
                    if(t_penghasilan_pasangan.equals("null")){
                        t_penghasilan_pasangan = "";
                    }


                    //121
                    if(t_penghasilan_tambahan.equals("null")){
                        t_penghasilan_tambahan = "";
                    }
                    if(t_pengeluaran_or_kebutuhan_hidup.equals("null")){
                        t_pengeluaran_or_kebutuhan_hidup = "";
                    }
                    if(t_total_cicilan_leasing_lain.equals("null")){
                        t_total_cicilan_leasing_lain = "";
                    }
                    if(t_balance_terakhir_di_rekening_tabungan.equals("null")){
                        t_balance_terakhir_di_rekening_tabungan = "";
                    }
                    if(t_rata_rata_mutasi_in_3_bulan_terakhir.equals("null")){
                        t_rata_rata_mutasi_in_3_bulan_terakhir = "";
                    }


                    //126
                    if(t_rata_rata_mutasi_out_3_bulan_terakhir.equals("null")){
                        t_rata_rata_mutasi_out_3_bulan_terakhir = "";
                    }
                    if(t_collectabilitas_sid_or_slik_tertinggi.equals("null")){
                        t_collectabilitas_sid_or_slik_tertinggi = "";
                    }
                    if(t_pernah_kredit_di_tempat_lain.equals("null")){
                        t_pernah_kredit_di_tempat_lain = "";
                    }
                    if(t_overdue_tertinggi.equals("null")){
                        t_overdue_tertinggi = "";
                    }
                    if(t_baki_debet_or_outstanding_hutang.equals("null")){
                        t_baki_debet_or_outstanding_hutang = "";
                    }


                    //131
                    if(t_nama_finance_company.equals("null")){
                        t_nama_finance_company = "";
                    }
                    if(t_alasan_menunggak_khusus_lebih_dari_coll_2.equals("null")){
                        t_alasan_menunggak_khusus_lebih_dari_coll_2 = "";
                    }
                    if(t_apakah_direkomendasikan.equals("null")){
                        t_apakah_direkomendasikan = "";
                    }
                    if(t_alasan_or_point_penting_rekomendasi_anda.equals("null")){
                        t_alasan_or_point_penting_rekomendasi_anda = "";
                    }

                    if (t_category_name.equals("null")){
                        t_category_name = "";
                    }

                    //140 & 141
                    if (t_Jenis_pekerjaan.equals("null")){
                        t_Jenis_pekerjaan = "";
                    }
                    if (t_Jenis_pekerjaan_code.equals("null")){
                        t_Jenis_pekerjaan_code = "";
                    }

                    //144 & 145
                    if (t_Jenis_pekerjaan_spouse.equals("null")){
                        t_Jenis_pekerjaan_spouse = "";
                    }
                    if (t_Jenis_pekerjaan_code_spouse.equals("null")){
                        t_Jenis_pekerjaan_code_spouse = "";
                    }




                    StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPLOAD_URL_UP2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(HomeActivity.this," aaada data "+response,Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Response: " + response.toString());
                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        //success = jObj.getInt(TAG_SUCCESS);
                                        String code = jObj.getString("code");
                                        if (code.equals("200")) {
                                            Log.d("v Add", jObj.toString());
                                            dm.deleteTugasAllId("Inputan",Get_id_order);
                                            dm.deleteSurvey(Get_id_order);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "ljjj: " + error.getMessage());
                                    //Toast.makeText(HomeActivity.this,"error ada data "+error.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> params = new HashMap<String, String>();
                            //params = new HashMap<String, String>();

                            params.put("nama", t_nama);//1
                            params.put("mother", t_mother);//2
                            params.put("title", t_title);//3
                            params.put("marital_status", t_marital_status);//4
                            params.put("identity_type", t_identity_type);//5
                            params.put("npwp_no", t_Npwp_no);//6
                            params.put("birth_place", t_Birth_place);//7
                            params.put("birth_date", t_Birth_date);//8
                            params.put("address_ktp", t_address_ktp);//9
                            params.put("province_ktp", t_province_ktp);//10
                            params.put("kab_kodya_ktp", t_kab_kodya_ktp);//11
                            params.put("kecamatan_ktp", t_kecamatan_ktp);//12
                            params.put("kelurahan_ktp", t_kelurahan_ktp);//13
                            params.put("sandi_dati_2_ktp", t_sandi_dati_2_ktp);//14
                            params.put("postal_code_ktp", t_postal_code_ktp);//15
                            params.put("address_home", t_address_home);//16
                            params.put("province_home", t_province_home);//17
                            params.put("kab_kodya_home", t_kab_kodya_home);//18
                            params.put("kecamatan_home", t_kecamatan_home);//19
                            params.put("kelurahan_home", t_kelurahan_home);//20
                            params.put("sandi_dati_2_home", t_sandi_dati_2_home);//21
                            params.put("postal_code_home", t_postal_code_home);//22
                            params.put("mail_address", t_mail_address);//23
                            params.put("tipe_rumah", t_tipe_rumah);//24
                            params.put("home_status", t_home_status);//25
                            params.put("telephone", t_Telephone);//26
                            params.put("telephone_2", t_Telephone_2);//27
                            params.put("education", t_education);//28
                            params.put("sex", t_sex);//29
                            params.put("nama_panggilan", t_Nama_panggilan);//30
                            params.put("identity_no", t_Identity_no);//31
                            params.put("sandi_lahir", t_Sandi_lahir);//32
                            params.put("religion", t_religion);//33
                            params.put("stay_length", t_Stay_length);//34
                            params.put("handphone_1", t_Handphone_1);//35
                            params.put("handphone_2", t_Handphone_2);//36
                            params.put("email", t_email);//37

                            params.put("pekerjaan", t_Pekerjaan);//38
                            params.put("job_title", t_job_title);//39
                            params.put("name_economy_code", t_Name_economy_code);//40
                            params.put("company_name", t_Company_name);//41
                            params.put("company_address", t_Company_address);//42
                            params.put("company_province", t_Company_province);//43
                            params.put("company_kab_kodya", t_Company_kab_kodya);//44
                            params.put("company_kecamatan", t_Company_kecamatan);//45
                            params.put("company_kelurahan", t_Company_kelurahan);//46
                            params.put("company_sandi_dati_2", t_Company_sandi_dati_2);//47
                            params.put("company_postal_code", t_Company_postal_code);//48
                            params.put("company_telephone_1", t_Company_telephone_1);//49
                            params.put("company_telephone_2", t_Company_telephone_2);//50
                            params.put("line_of_business", t_Line_of_business);//51
                            params.put("economy_code", t_Economy_code);//52
                            params.put("estabilished_since", t_Estabilished_since);//53
                            params.put("company_fax_1", t_Company_fax_1);//54

                            params.put("spouse_name", t_Spouse_name);//55
                            params.put("spouse_title", t_spouse_title);//56
                            params.put("spouse_identity_type", t_spouse_identity_type);//57
                            params.put("spouse_birth_place_or_sandi_lahir", t_Spouse_birth_place_or_sandi_lahir);//58
                            params.put("spouse_religion", t_spouse_religion);//59
                            params.put("spouse_address", t_Spouse_address);//60
                            params.put("spouse_province", t_Spouse_province);//61
                            params.put("spouse_kab_kodya", t_Spouse_kab_kodya);//62
                            params.put("spouse_kecamatan", t_Spouse_kecamatan);//63
                            params.put("spouse_kelurahan", t_Spouse_kelurahan);//64
                            params.put("spouse_sandi_dati_2", t_Spouse_sandi_dati_2);//65
                            params.put("spouse_postal_code", t_Spouse_postal_code);//66
                            params.put("spouse_no_handphone", t_Spouse_no_handphone);//67
                            params.put("spouse_occupation_or_pekerjaan", t_spouse_occupation_or_pekerjaan);//68
                            params.put("spouse_company_name", t_Spouse_company_name);//69
                            params.put("spouse_company_address", t_Spouse_company_address);//70
                            params.put("spouse_company_telephone", t_Spouse_company_telephone);//71
                            params.put("spouse_line_of_business", t_Spouse_line_of_business);//72
                            params.put("spouse_job_title", t_spouse_job_title);//73
                            params.put("spouse_sex", t_spouse_sex);//74
                            params.put("spouse_identity_no", t_Spouse_identity_no);//75
                            params.put("spouse_date_of_birth", t_Spouse_date_of_birth);//76
                            params.put("spouse_fax", t_Spouse_fax);//77

                            params.put("has_contact_person", t_has_contact_person);//78
                            params.put("contact_name", t_contact_name);//79
                            params.put("contact_address", t_Contact_address);//80
                            params.put("contact_province", t_Contact_province);//81
                            params.put("contact_kab_kodya", t_Contact_kab_kodya);//82
                            params.put("contact_kecamatan", t_Contact_kecamatan);//83
                            params.put("contact_kelurahan", t_Contact_kelurahan);//84
                            params.put("contact_sandi_dati_2", t_Contact_sandi_dati_2);//85
                            params.put("contact_postal_code", t_Contact_postal_code);//86
                            params.put("contact_telephone", t_contact_telephone);//87
                            params.put("relationship", t_relationship);//88

                            params.put("jarak_rumah_ke_cabang", t_jarak_rumah_ke_cabang);//89
                            params.put("luas_tanah", t_luas_tanah);//90
                            params.put("luas_bangunan_rumah", t_luas_bangunan_rumah);//91
                            params.put("status_kepemilikan_rumah", t_status_kepemilikan_rumah);//92
                            params.put("klasifikasi_perumahan", t_klasifikasi_perumahan);//93
                            params.put("tempat_menaruh_kendaraan", t_tempat_menaruh_kendaraan);//94
                            params.put("status_garasi_kendaraan", t_status_garasi_kendaraan);//95
                            params.put("bentuk_bangunan_rumah", t_bentuk_bangunan_rumah);//96
                            params.put("kondisi_rumah", t_kondisi_rumah);//97
                            params.put("luas_jalan_masuk_rumah", t_luas_jalan_masuk_rumah);//98
                            params.put("status_kepemilikan_rumah_pemohon", t_status_kepemilikan_rumah_pemohon);//99
                            params.put("furniture", t_furniture);//100
                            params.put("jarak_tempat_usaha_dari_rumah", t_jarak_tempat_usaha_dari_rumah);//101
                            params.put("status_kepemilikan_usaha", t_status_kepemilikan_usaha);//102
                            params.put("bentuk_bangunan_tempat_usaha", t_bentuk_bangunan_tempat_usaha);//103
                            params.put("lokasi_tempat_usaha", t_lokasi_tempat_usaha);//104
                            params.put("jumlah_karyawan", t_jumlah_karyawan);//105
                            params.put("lama_pemohon_menempati_tempat_usaha_tahun", t_lama_pemohon_menempati_tempat_usaha_tahun);//106
                            params.put("lama_pemohon_menempati_tempat_usaha_bulan", t_lama_pemohon_menempati_tempat_usaha_bulan);//107
                            params.put("lama_usaha_berdiri_tahun", t_lama_usaha_berdiri_tahun);//108
                            params.put("lama_usaha_berdiri_bulan", t_lama_usaha_berdiri_bulan);//109
                            params.put("pekerjaan_or_usaha_terkait_ekspor_or_impor", t_pekerjaan_or_usaha_terkait_ekspor_or_impor);//110
                            params.put("tujuan_penggunaan_unit", t_tujuan_penggunaan_unit);//111
                            params.put("kondisi_mobil", t_kondisi_mobil);//112
                            params.put("bagian_kondisi_yang_tidak_baik", t_bagian_kondisi_yang_tidak_baik);//113
                            params.put("lama_kepemilikan_kendaraan_tahun", t_lama_kepemilikan_kendaraan_tahun);//114
                            params.put("lama_kepemilikan_kendaraan_bulan", t_lama_kepemilikan_kendaraan_bulan);//115
                            params.put("jumlah_tanggungan", t_jumlah_tanggungan);//116
                            params.put("jumlah_anak", t_jumlah_anak);//117
                            params.put("omzet_or_penghasilan_gross", t_omzet_or_penghasilan_gross);//118
                            params.put("penghasilan_nett_or_take_home_pay", t_penghasilan_nett_or_take_home_pay);//119
                            params.put("penghasilan_pasangan", t_penghasilan_pasangan);//120
                            params.put("penghasilan_tambahan", t_penghasilan_tambahan);//121
                            params.put("pengeluaran_or_kebutuhan_hidup", t_pengeluaran_or_kebutuhan_hidup);//122
                            params.put("total_cicilan_leasing_lain", t_total_cicilan_leasing_lain);//123
                            params.put("balance_terakhir_di_rekening_tabungan", t_balance_terakhir_di_rekening_tabungan);//124
                            params.put("rata_rata_mutasi_in_3_bulan_terakhir", t_rata_rata_mutasi_in_3_bulan_terakhir);//125
                            params.put("rata_rata_mutasi_out_3_bulan_terakhir", t_rata_rata_mutasi_out_3_bulan_terakhir);//126
                            params.put("collectabilitas_sid_or_slik_tertinggi", t_collectabilitas_sid_or_slik_tertinggi);//127
                            params.put("pernah_kredit_di_tempat_lain", t_pernah_kredit_di_tempat_lain);//128
                            params.put("overdue_tertinggi", t_overdue_tertinggi);//129
                            params.put("baki_debet_or_outstanding_hutang", t_baki_debet_or_outstanding_hutang);//130
                            params.put("nama_finance_company", t_nama_finance_company);//131
                            params.put("alasan_menunggak_khusus_lebih_dari_coll_2", t_alasan_menunggak_khusus_lebih_dari_coll_2);//132
                            params.put("apakah_direkomendasikan", t_apakah_direkomendasikan);//133
                            params.put("alasan_or_point_penting_rekomendasi_anda", t_alasan_or_point_penting_rekomendasi_anda);//134

                            //Tambahan
                            params.put("kode_propinsi_ktp",t_id_province_ktp);
                            params.put("kode_kab_kodya_ktp",t_id_kab_kodya_ktp);
                            params.put("kode_kec_ktp",t_id_kecamatan_ktp);
                            params.put("kode_kel_ktp",t_id_kelurahan_ktp);
                            params.put("zipcode_ktp",t_zipcode_ktp);
                            params.put("kode_propinsi_spouse",t_Spouse_id_province);
                            params.put("kode_kab_kodya_spouse",t_Spouse_id_kab_kodya);
                            params.put("kode_kec_spouse",t_Spouse_id_kecamatan);
                            params.put("kode_kel_spouse",t_Spouse_id_kelurahan);
                            params.put("zipcode_spouse",t_Spouse_zipcode);
                            params.put("category_name",t_category_name);
                            params.put("ocpt_code",t_Jenis_pekerjaan_code);
                            params.put("ocpt_desc",t_Jenis_pekerjaan);
                            params.put("ocpt_code_spouse",t_Jenis_pekerjaan_code_spouse);
                            params.put("ocpt_desc_spouse",t_Jenis_pekerjaan_spouse);
                            //End Tambahan

                            params.put("latitude", ""+sphoto_latitude);//135
                            params.put("longitude", ""+sphoto_longitude);//136
                            params.put("id_surveyor", get_id_surveyor);//137
                            params.put("id_order", Get_id_order);//138
                            params.put("tk", setter.APK_CODE);//139



                            //kembali ke parameters
                            Log.d(TAG, ""+params);


                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                }
            }
        }
    }


    public void kirimlokasi(){
        SimpleDateFormat gh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hf = gh.format(new Date());
        if(mCurrentLocation != null){
            dm.addRowKirimlokasi(String.valueOf(mCurrentLocation.getLatitude()),
                    String.valueOf(mCurrentLocation.getLongitude()), get_id_surveyor,
                    hf);
        }
        cekkirimlokasi();

    }

    public void cekkirimlokasi(){
        ArrayList<ArrayList<Object>> datakirimlokasicek6 = dm.ambilBarisKirimlokasicheck(get_id_surveyor);
        if (datakirimlokasicek6.size() > 0) {
            ArrayList<Object> barislokasi6 = datakirimlokasicek6.get(0);
            String w_id_kirimlokasi = barislokasi6.get(0).toString();
            String w_ceklatitude = barislokasi6.get(1).toString();
            String w_ceklongitude = barislokasi6.get(2).toString();
            String w_id_surveyor = barislokasi6.get(3).toString();
            String w_time_surveyor = barislokasi6.get(4).toString();
            savelokasi(w_id_kirimlokasi,w_ceklatitude,w_ceklongitude,w_id_surveyor,w_time_surveyor);
        }
    }

    public void savelokasi(String cek_in_id_kirimlokasi, String cek_in_ceklatitude,
                           String cek_in_ceklongitude, String cek_in_id_surveyor,
                           String cek_in_time_surveyor){
        cek_in_id_kirimlokasi_up = cek_in_id_kirimlokasi;
        cek_in_ceklatitude_up = cek_in_ceklatitude;
        cek_in_ceklongitude_up = cek_in_ceklongitude;
        cek_in_id_surveyor_up = cek_in_id_surveyor;
        cek_in_time_surveyor_up = cek_in_time_surveyor;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.URL_KIRIM_LOKASI,
                new Response.Listener<String>() {
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
                                    Log.d("v Add", jObj.toString());
                                    String hasil_id_kirimlokasi = hData.getString("hasil_id_kirimlokasi");
                                    dm.deleteKirimlokasiAll(hasil_id_kirimlokasi);
                                }
                            } else if (code.equals("202")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    JSONObject hData = arrayData.getJSONObject(0);
                                    String hasil_id_kirimlokasi_g = hData.getString("hasil_id_kirimlokasi");
                                    dm.deleteKirimlokasiAll(hasil_id_kirimlokasi_g);
                                }
                            }
                            cekkirimlokasi();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_kirimlokasi", cek_in_id_kirimlokasi_up);
                map.put("ceklat", cek_in_ceklatitude_up);
                map.put("ceklong", cek_in_ceklongitude_up);
                map.put("id_surveyor", cek_in_id_surveyor_up);
                map.put("cektime", cek_in_time_surveyor_up);
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);
    }


    public void cekchat(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_chat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200") && jObj.has("data")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject hData = arrayData.getJSONObject(i);
                                        id_message = hData.getString("id_message");
                                        chatdetail(id_message);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = null;

                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data !=null){
                            switch (response.statusCode){
                                case 400:
                                    json = new String(response.data);
                                    json = trimMessage(json,"message");
                                    if (json != null) displayMessage(json);
                                    break;
                            }

                        }
                    }

                }) {

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if (volleyError.networkResponse !=null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }

                return volleyError;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_surveyor", get_id_surveyor);
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }


    public void chatdetail(String c_id_message){
        ck_id_message = c_id_message;
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_chatdetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject hData = arrayData.getJSONObject(i);
                                        id_balas_message    = hData.getString("id_balas_message");
                                        id_message          = hData.getString("id_message");
                                        to_id_admin         = hData.getString("to_id_admin");
                                        from_id_admin       = hData.getString("from_id_admin");
                                        from_nama           = hData.getString("from_nama");
                                        balas_message       = hData.getString("balas_message");
                                        entry_date_balas    = hData.getString("entry_date_balas");
                                        type_message        = hData.getString("type_message");

                                        ArrayList<ArrayList<Object>> data61 = dm.ambilBarisChatdetailcheck(id_balas_message);
                                        if (data61.size() < 1) {
                                            dm.addRowChatdetail(id_balas_message, id_message, to_id_admin, from_id_admin, from_nama, balas_message, entry_date_balas, type_message);
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //          Toast.makeText(HomeActivity.this, "Tidak Terhubung dengan Server", Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_message", ck_id_message);
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateGelar(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_GELAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Gelar");
                                dm.addRowJsonPilih(String.valueOf(response),"Gelar");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateTitle(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_TITLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Title");
                                dm.addRowJsonPilih(String.valueOf(response),"Title");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateMaritalstatus(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_MARITAL_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Marital Status");
                                dm.addRowJsonPilih(String.valueOf(response),"Marital Status");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }


    public void UpdateIdentityType(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_IDENTITY_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Identity Type");
                                dm.addRowJsonPilih(String.valueOf(response),"Identity Type");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //          Toast.makeText(HomeActivity.this, "Tidak Terhubung dengan Server", Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateMailAddress(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_MAIL_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Mail Address");
                                dm.addRowJsonPilih(String.valueOf(response),"Mail Address");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateTipeRumah(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_TIPE_RUMAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Tipe Rumah");
                                dm.addRowJsonPilih(String.valueOf(response),"Tipe Rumah");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateHomeStatus(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_HOME_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Home Status");
                                dm.addRowJsonPilih(String.valueOf(response),"Home Status");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateStatusKepemilikanRumah(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_STATUS_KEPEMILIKAN_RUMAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Status Kepemilikan Rumah");
                                dm.addRowJsonPilih(String.valueOf(response),"Status Kepemilikan Rumah");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateKlasifikasiPerumahan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_KLASIFIKASI_PERUMAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Klasifikasi Perumahan");
                                dm.addRowJsonPilih(String.valueOf(response),"Klasifikasi Perumahan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateTempatMenaruhKendaraan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_TEMPAT_MENARUH_KENDARAAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Tempat Menaruh Kendaraan");
                                dm.addRowJsonPilih(String.valueOf(response),"Tempat Menaruh Kendaraan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateStatusGarasiKendaraan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_STATUS_GARASI_KENDARAAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Status Garasi Kendaraan");
                                dm.addRowJsonPilih(String.valueOf(response),"Status Garasi Kendaraan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateBentukBangunanRumah(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_BENTUK_BANGUNAN_RUMAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Bentuk Bangunan Rumah");
                                dm.addRowJsonPilih(String.valueOf(response),"Bentuk Bangunan Rumah");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateKondisiRumah(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_KONDISI_RUMAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Kondisi Rumah");
                                dm.addRowJsonPilih(String.valueOf(response),"Kondisi Rumah");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateLuasJalanMasukRumah(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_LUAS_JALAN_MASUK_RUMAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Luas Jalan Masuk Rumah");
                                dm.addRowJsonPilih(String.valueOf(response),"Luas Jalan Masuk Rumah");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateStatusKepemilikanRumahPemohon(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_STATUS_KEPEMILIKAN_RUMAH_PEMOHON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Status Kepemilikan Rumah Pemohon");
                                dm.addRowJsonPilih(String.valueOf(response),"Status Kepemilikan Rumah Pemohon");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateStatusKepemilikanUsaha(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_STATUS_KEPEMILIKAN_USAHA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Status Kepemilikan Usaha");
                                dm.addRowJsonPilih(String.valueOf(response),"Status Kepemilikan Usaha");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateBentukBangunanTmpUsaha(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_BENTUK_BANGUNAN_TMP_USAHA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Bentuk Bangunan Tmp Usaha");
                                dm.addRowJsonPilih(String.valueOf(response),"Bentuk Bangunan Tmp Usaha");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateLokasiTempatUsaha(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_LOKASI_TEMPAT_USAHA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Lokasi Tempat Usaha");
                                dm.addRowJsonPilih(String.valueOf(response),"Lokasi Tempat Usaha");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateJumlahKaryawan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JUMLAH_KARYAWAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Jumlah Karyawan");
                                dm.addRowJsonPilih(String.valueOf(response),"Jumlah Karyawan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateTujuanPenggunaanUnit(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_TUJUAN_PENGGUNAAN_UNIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Tujuan Penggunaan Unit");
                                dm.addRowJsonPilih(String.valueOf(response),"Tujuan Penggunaan Unit");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateKondisiMobil(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_KONDISI_MOBIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Kondisi Mobil");
                                dm.addRowJsonPilih(String.valueOf(response),"Kondisi Mobil");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }


    public void UpdateEducation(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_EDUCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Education");
                                dm.addRowJsonPilih(String.valueOf(response),"Education");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateSex(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_SEX,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Sex");
                                dm.addRowJsonPilih(String.valueOf(response),"Sex");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateReligion(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_RELIGION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Religion");
                                dm.addRowJsonPilih(String.valueOf(response),"Religion");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateJobTitle(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JOB_TITLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Job Title");
                                dm.addRowJsonPilih(String.valueOf(response),"Job Title");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateSpousePekerjaan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_SPOUSE_PEKERJAAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Pekerjaan");
                                dm.addRowJsonPilih(String.valueOf(response),"Pekerjaan");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateHasContactPerson(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_HAS_CONTACT_PERSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Has Contact Person");
                                dm.addRowJsonPilih(String.valueOf(response),"Has Contact Person");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateRelationship(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_RELATIONSHIP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Relationship");
                                dm.addRowJsonPilih(String.valueOf(response),"Relationship");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateReturn(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_RETURN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Return");
                                dm.addRowJsonPilih(String.valueOf(response),"Return");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateEconomyCode(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_ECONOMY_CODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Economy Code");
                                dm.addRowJsonPilih(String.valueOf(response),"Economy Code");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateCancel(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_CANCEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Cancel");
                                dm.addRowJsonPilih(String.valueOf(response),"Cancel");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdatePending(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_PENDING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Pending");
                                dm.addRowJsonPilih(String.valueOf(response),"Pending");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateUpdateApk(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_CHECK_APK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Update Apk");
                                dm.addRowJsonPilih(String.valueOf(response),"Update Apk");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jArr);


        ArrayList<ArrayList<Object>> data_update_apk = dm.ambilBarisJsonPilih("Update Apk");//
        if (data_update_apk.size() > 0) {
            try {
                ArrayList<Object> baris = data_update_apk.get(0);
                tam_json = baris.get(0).toString();
                JSONObject jObj = new JSONObject(tam_json);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        JSONObject hData = arrayData.getJSONObject(0);
                        code_update_apk = hData.getString("code_update_apk");
                        if(!code_update_apk.equals(setter.APK_UPDATE_CODE)){
                            link_update_apk = hData.getString("link_update_apk");
                            CustomDialogUpdateAPK();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void CustomDialogUpdateAPK(){
        dialog_update_apk = new Dialog(HomeActivity.this);
        dialog_update_apk.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_update_apk.setContentView(R.layout.dialogbox_update_apk);
        dialog_update_apk.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_update_apk.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog_update_apk.setCanceledOnTouchOutside(true);
        dialog_update_apk.setCanceledOnTouchOutside(false);
        dialog_update_apk.setCancelable(false);
        Button bt_update =(Button) dialog_update_apk.findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = link_update_apk;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
               // dialog_update_apk.dismiss();
            }
        });
        dialog_update_apk.show();
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public void  displayMessage(String toastString){
        Toast.makeText(getApplicationContext(),toastString,Toast.LENGTH_LONG).show();
    }
}