package surveyor.id.com.mobilesurvey;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import surveyor.id.com.mobilesurvey.adapter.CustomListAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.Hitung;
import surveyor.id.com.mobilesurvey.modal.Jarak;
import surveyor.id.com.mobilesurvey.modal.LanjutSurvey;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.service.BackgroundService;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.OtherUtil;

public class ListTugasActivity extends AppCompatActivity implements GoogleApiClient.
        ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SwipeRefreshLayout.
        OnRefreshListener {
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    SwipeRefreshLayout swipe;
    ListView list;
    CustomListAdapter adapter;
    List<Jarak> itemList = new ArrayList<>();
    DatabaseManager dm;
    private int status_survey_cek;
    private static final String TAG = ListTugasActivity.class.getSimpleName();
    private String get_username, latitude, longitude, tam_json, get_namalengkap, get_id_surveyor,
            id_order_cek, id_customer_cek, namalengkap_surveyor_cek, asuransi_cek, jenis_kredit_cek,
            order_code_cek, jml_pinjaman_cek, otr_cek, dp_cek, tenor_cek, date_survey_cek,
            janji_survey_cek, janji_survey_tanggal_cek, janji_survey_bulan_cek,
            janji_survey_tahun_cek, janji_survey_jam_cek, janji_survey_menit_cek, order_date_cek,
            c_status_survey_cek, status_order_cek, kode_cabang_cek, jaminan_multiguna_cek,
            c_latitude_survey_cek, c_longitude_survey_cek, kategori_kendaraan_cek,
            status_kendaraan_cek, merk_kendaraan_cek, model_kendaraan_cek, type_kendaraan_cek,
            tahun_kendaraan_cek, warna_cek, transmisi_cek, plat_nomor_cek, km_kendaraan_cek,
            bahan_bakar_cek, harga_cek, name_cek, identity_type_cek, address_home_cek, telephone_cek,
            telephone_2_cek, sex_cek, identity_no_cek, handphone_1_cek, handphone_2_cek, status,
            janji_survey, jarak;
    Intent mServiceIntent;
    private BackgroundService mBackgroundService;
    Context ctx;
    private int jumlah_notif;
    private int resError;
    private String sDetailHasilDateBulan;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tugas);

        resError = 1;
        ctx = this;
        mBackgroundService = new BackgroundService(getCtx());
        mServiceIntent = new Intent(getCtx(), mBackgroundService.getClass());
        if (!isMyServiceRunning(mBackgroundService.getClass())) {
            startService(mServiceIntent);
        }

        dm = new DatabaseManager(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("List Tugas");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ArrayList<Object>> data_user = dm.ambilSemuaBaris();
        if (data_user.size() > 0) {
            ArrayList<Object> baris_user = data_user.get(0);
            get_username = baris_user.get(0).toString();
            get_namalengkap = baris_user.get(2).toString();
            get_id_surveyor = baris_user.get(3).toString();
        }
        //get_username = getIntent().getExtras().getString("username");
        list = (ListView) findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        adapter = new CustomListAdapter(this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           lokasi();
                       }
                   }
        );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tampil_hal(position);
            }

        });


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }


    private void lokasi() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                getMyLocation();
            }
        }
    }

    public void tampil_hal(int position) {
        Jarak j = itemList.get(position);
        String get_id_order = ""+j.getIdOrder();

        ArrayList<ArrayList<Object>> data_list = dm.ambilBarisJson("List Tugas");
        if (data_list.size() > 0) {
            try {
                ArrayList<Object> baris = data_list.get(0);
                tam_json = baris.get(0).toString();

                JSONObject jObj = new JSONObject(tam_json);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        //JSONObject obj = arrayData.getJSONObject(cek_position);
                        for (int i = 0; i < arrayData.length(); i++) {
                            JSONObject obj = arrayData.getJSONObject(i);
                            String h_id_order_cek = obj.getString("id_order");
                            if(get_id_order.equals(h_id_order_cek)){
                                id_order_cek                = obj.getString("id_order");
                                id_customer_cek             = obj.getString("id_customer");
                                namalengkap_surveyor_cek    = get_namalengkap;
                                asuransi_cek                = obj.getString("asuransi");
                                jenis_kredit_cek            = obj.getString("jenis_kredit");
                                order_code_cek              = obj.getString("order_code");
                                jml_pinjaman_cek            = obj.getString("jml_pinjaman");
                                otr_cek                     = obj.getString("otr");
                                dp_cek                      = obj.getString("dp");
                                tenor_cek                   = obj.getString("tenor");
                                date_survey_cek             = obj.getString("date_survey");
                                janji_survey_cek            = obj.getString("janji_survey");
                                janji_survey_tanggal_cek    = obj.getString("janji_survey_tanggal");
                                janji_survey_bulan_cek      = obj.getString("janji_survey_bulan");
                                janji_survey_tahun_cek      = obj.getString("janji_survey_tahun");
                                janji_survey_jam_cek        = obj.getString("janji_survey_jam");
                                janji_survey_menit_cek      = obj.getString("janji_survey_menit");
                                order_date_cek              = obj.getString("order_date");
                                c_status_survey_cek         = obj.getString("status_survey");
                                status_order_cek            = obj.getString("status_order");
                                kode_cabang_cek             = obj.getString("kode_cabang");
                                jaminan_multiguna_cek       = obj.getString("jaminan_multiguna");
                                c_latitude_survey_cek       = obj.getString("latitude_survey");
                                c_longitude_survey_cek      = obj.getString("longitude_survey");

                                kategori_kendaraan_cek      = obj.getString("kategori_kendaraan");
                                status_kendaraan_cek        = obj.getString("status_kendaraan");
                                merk_kendaraan_cek          = obj.getString("merk_kendaraan");
                                model_kendaraan_cek         = obj.getString("model_kendaraan");
                                type_kendaraan_cek          = obj.getString("type_kendaraan");
                                tahun_kendaraan_cek         = obj.getString("tahun_kendaraan");
                                warna_cek                   = obj.getString("warna");
                                plat_nomor_cek              = obj.getString("plat_nomor");
                                km_kendaraan_cek            = obj.getString("km_kendaraan");
                                bahan_bakar_cek             = obj.getString("bahan_bakar");

                                status_survey_cek           = obj.getInt("status_survey");
                                // latitude_survey_cek = obj.getDouble("latitude_survey");
                                //   longitude_survey_cek = obj.getDouble("longitude_survey");

                                name_cek                    = obj.getString("name");
                                identity_type_cek           = obj.getString("identity_type");
                                address_home_cek            = obj.getString("address_home");
                                telephone_cek               = obj.getString("telephone");
                                telephone_2_cek             = obj.getString("telephone_2");
                                sex_cek                     = obj.getString("sex");
                                identity_no_cek             = obj.getString("identity_no");
                                handphone_1_cek             = obj.getString("handphone_1");
                                handphone_2_cek             = obj.getString("handphone_2");

                                jarak                       = obj.getString("jarak");

                                status                      = obj.getString("status");


                                ArrayList<ArrayList<Object>> data_janji_bayar = dm.ambilBarisJanjiSurveyByOrder(get_id_surveyor,id_order_cek);
                                if (data_janji_bayar.size() > 0) {
                                    ArrayList<Object> baris_janjiSurvey = data_janji_bayar.get(0);
                                    String janjiSurveyJanjiSurvey  = "" + baris_janjiSurvey.get(3);
                                    janji_survey                = janjiSurveyJanjiSurvey;
                                }else{
                                    janji_survey                = obj.getString("janji_survey");
                                }

                                finish();
                                Intent ab = new Intent(this, MapsActivity.class);
                                Bundle detail = new Bundle();
                                detail.putString("id_order", id_order_cek);
                                detail.putString("id_customer", id_customer_cek);
                                detail.putString("namalengkap_surveyor", get_namalengkap);
                                detail.putString("asuransi", asuransi_cek);
                                detail.putString("jenis_kredit", jenis_kredit_cek);
                                detail.putString("order_code", order_code_cek);
                                detail.putString("jml_pinjaman", jml_pinjaman_cek);
                                detail.putString("otr", otr_cek);
                                detail.putString("dp", dp_cek);
                                detail.putString("tenor", tenor_cek);
                                detail.putString("kode_cabang", kode_cabang_cek);
                                detail.putString("jaminan_multiguna", jaminan_multiguna_cek);

                                detail.putString("name", name_cek);
                                detail.putString("identity_type", identity_type_cek);
                                detail.putString("identity_no", identity_no_cek);
                                detail.putString("address_home", address_home_cek);
                                detail.putString("telephone", telephone_cek);
                                detail.putString("sex", sex_cek);
                                detail.putString("handphone_1", handphone_1_cek);

                                detail.putString("kategori_kendaraan", kategori_kendaraan_cek);
                                detail.putString("merk_kendaraan", merk_kendaraan_cek);
                                detail.putString("model_kendaraan", model_kendaraan_cek);
                                detail.putString("type_kendaraan", type_kendaraan_cek);
                                detail.putString("tahun", tahun_kendaraan_cek);
                                detail.putString("warna", warna_cek);
                                detail.putString("plat_nomor", plat_nomor_cek);
                                detail.putString("km_kendaraan", km_kendaraan_cek);
                                detail.putString("bahan_bakar", bahan_bakar_cek);
                                detail.putString("janji_survey", janji_survey);

                                detail.putString("status_survey", "" + c_status_survey_cek);
                                if (c_latitude_survey_cek == null) {
                                    detail.putString("latitude_survey", "0");
                                    detail.putString("longitude_survey", "0");
                                } else {
                                    detail.putString("latitude_survey", "" + c_latitude_survey_cek);
                                    detail.putString("longitude_survey", "" + c_longitude_survey_cek);
                                }

                                if (jarak.equals("null")) {
                                    detail.putString("latitude_survey", "null");
                                    detail.putString("longitude_survey", "null");
                                }

                                ab.putExtras(detail);
                                startActivity(ab);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void cekJson(final String h_lat, final String h_lng){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callListVolley(h_lat,h_lng);
            }
        }, 2000);
    }

    private void callListVolley(final String lat, final String lng) {
        swipe.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.url_list_tugas,
                new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         itemList.clear();
                         adapter.notifyDataSetChanged();
                         Log.d(TAG, response.toString());
                         dm.deleteTugasAll("List Tugas");
                         dm.addRowTugas(String.valueOf(response), "List Tugas");

                         ArrayList<ArrayList<Object>> data_list = dm.ambilBarisJson("List Tugas");
                         if (data_list.size() > 0) {
                             try {
                                 ArrayList<Object> baris = data_list.get(0);
                                 tam_json = baris.get(0).toString();

                                 JSONObject jObj = new JSONObject(response);
                                 String code = jObj.getString("code");
                                 if (code.equals("200")) {
                                     String data = jObj.getString("data");
                                     JSONArray arrayData = new JSONArray(data);
                                     if (arrayData.length() > 0) {
                                         for (int i = 0; i < arrayData.length(); i++) {
                                             JSONObject obj = arrayData.getJSONObject(i);
                                             Jarak j = new Jarak();
                                             String checkIdOrder = obj.getString("id_order");
                                             j.setIdOrder(obj.getString("id_order"));
                                             j.setNama(obj.getString("name"));

                                             ArrayList<ArrayList<Object>> data_janji_bayar = dm.ambilBarisJanjiSurveyByOrder(get_id_surveyor,checkIdOrder);
                                             if (data_janji_bayar.size() > 0) {

                                                 ArrayList<Object> baris_janjiSurvey = data_janji_bayar.get(0);
                                                 String janjiSurveyJanjiSurvey  = "" + baris_janjiSurvey.get(3);

                                                 String[] hasilJanjiSurvey = janjiSurveyJanjiSurvey.split(" ");
                                                 String hasilDate = hasilJanjiSurvey[0];

                                                 String[] detailHasilDate = hasilDate.split("-");
                                                 String detailHasilDateTahun = detailHasilDate[0];
                                                 String detailHasilDateBulan = detailHasilDate[1];
                                                 String detailHasilDateTanggal = detailHasilDate[2];

                                                 if(detailHasilDateBulan.equals("01")){
                                                     sDetailHasilDateBulan = "Januari";
                                                 }else if(detailHasilDateBulan.equals("02")){
                                                     sDetailHasilDateBulan = "Februari";
                                                 }else if(detailHasilDateBulan.equals("03")){
                                                     sDetailHasilDateBulan = "Maret";
                                                 }else if(detailHasilDateBulan.equals("04")){
                                                     sDetailHasilDateBulan = "April";
                                                 }else if(detailHasilDateBulan.equals("05")){
                                                     sDetailHasilDateBulan = "Mei";
                                                 }else if(detailHasilDateBulan.equals("06")){
                                                     sDetailHasilDateBulan = "Juni";
                                                 }else if(detailHasilDateBulan.equals("07")){
                                                     sDetailHasilDateBulan = "Juli";
                                                 }else if(detailHasilDateBulan.equals("08")){
                                                     sDetailHasilDateBulan = "Agustus";
                                                 }else if(detailHasilDateBulan.equals("09")){
                                                     sDetailHasilDateBulan = "September";
                                                 }else if(detailHasilDateBulan.equals("10")){
                                                     sDetailHasilDateBulan = "Oktober";
                                                 }else if(detailHasilDateBulan.equals("11")){
                                                     sDetailHasilDateBulan = "November";
                                                 }else if(detailHasilDateBulan.equals("12")){
                                                     sDetailHasilDateBulan = "Desember";
                                                 }else if(detailHasilDateBulan.equals("1")){
                                                     sDetailHasilDateBulan = "Januari";
                                                 }else if(detailHasilDateBulan.equals("2")){
                                                     sDetailHasilDateBulan = "Februari";
                                                 }else if(detailHasilDateBulan.equals("3")){
                                                     sDetailHasilDateBulan = "Maret";
                                                 }else if(detailHasilDateBulan.equals("4")){
                                                     sDetailHasilDateBulan = "April";
                                                 }else if(detailHasilDateBulan.equals("5")){
                                                     sDetailHasilDateBulan = "Mei";
                                                 }else if(detailHasilDateBulan.equals("6")){
                                                     sDetailHasilDateBulan = "Juni";
                                                 }else if(detailHasilDateBulan.equals("7")){
                                                     sDetailHasilDateBulan = "Juli";
                                                 }else if(detailHasilDateBulan.equals("8")){
                                                     sDetailHasilDateBulan = "Agustus";
                                                 }else if(detailHasilDateBulan.equals("9")){
                                                     sDetailHasilDateBulan = "September";
                                                 }else if(detailHasilDateBulan.equals("10")){
                                                     sDetailHasilDateBulan = "Oktober";
                                                 }

                                                 j.setTanggal(detailHasilDateTanggal);
                                                 j.setBulan(sDetailHasilDateBulan);
                                                 j.setTahun(detailHasilDateTahun);
                                             }else{
                                                 j.setTanggal(obj.getString("janji_survey_tanggal"));
                                                 j.setBulan(obj.getString("janji_survey_bulan"));
                                                 j.setTahun(obj.getString("janji_survey_tahun"));
                                             }


                                             j.setSurveyaddress(obj.getString("address_home"));
                                             String cek_jarak = obj.getString("jarak");
                                             if (cek_jarak.equals("Tidak Ada Maps")) {
                                                 j.setJarak(obj.getString("jarak"));
                                             } else {
                                                 if (lat.equals("0") && lng.equals("0")) {
                                                     j.setJarak("--");
                                                 } else {
                                                     if (!cek_jarak.equals("null")) {
                                                         double jarak = Double.parseDouble(obj.getString("jarak"));
                                                         j.setJarak("" + round(jarak, 2));
                                                     } else {
                                                         j.setJarak("Tidak Ada Maps");
                                                     }
                                                 }
                                             }
                                             itemList.add(j);
                                         }
                                     }
                                 }
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                         adapter.notifyDataSetChanged();
                         swipe.setRefreshing(false);
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         resError++;
                         if(resError > 3){
                             resError = 1;

                             Toast.makeText(ListTugasActivity.this,"Gagal mengupdate data",Toast.LENGTH_LONG).show();
                             itemList.clear();
                             adapter.notifyDataSetChanged();
                             ArrayList<ArrayList<Object>> data_list = dm.ambilBarisJson("List Tugas");
                             if (data_list.size() > 0) {
                                 try {
                                     ArrayList<Object> baris = data_list.get(0);
                                     tam_json = baris.get(0).toString();

                                     JSONObject jObj = new JSONObject(tam_json);
                                     String code = jObj.getString("code");
                                     if (code.equals("200")) {
                                         String data = jObj.getString("data");
                                         JSONArray arrayData = new JSONArray(data);
                                         if (arrayData.length() > 0) {
                                             for (int i = 0; i < arrayData.length(); i++) {
                                                 JSONObject obj = arrayData.getJSONObject(i);
                                                 Jarak j = new Jarak();
                                                 String checkIdOrder = obj.getString("id_order");

                                                 j.setIdOrder(obj.getString("id_order"));
                                                 j.setNama(obj.getString("name"));


                                                 ArrayList<ArrayList<Object>> data_janji_bayar = dm.ambilBarisJanjiSurveyByOrder(get_id_surveyor,checkIdOrder);
                                                 if (data_janji_bayar.size() > 0) {
                                                     ArrayList<Object> baris_janjiSurvey = data_janji_bayar.get(0);
                                                     String janjiSurveyJanjiSurvey  = "" + baris_janjiSurvey.get(3);


                                                     String[] hasilJanjiSurvey = janjiSurveyJanjiSurvey.split(" ");
                                                     String hasilDate = hasilJanjiSurvey[0];

                                                     String[] detailHasilDate = hasilDate.split("-");
                                                     String detailHasilDateTahun = detailHasilDate[0];
                                                     String detailHasilDateBulan = detailHasilDate[1];
                                                     String detailHasilDateTanggal = detailHasilDate[2];

                                                     if(detailHasilDateBulan.equals("01")){
                                                         sDetailHasilDateBulan = "Januari";
                                                     }else if(detailHasilDateBulan.equals("02")){
                                                         sDetailHasilDateBulan = "Februari";
                                                     }else if(detailHasilDateBulan.equals("03")){
                                                         sDetailHasilDateBulan = "Maret";
                                                     }else if(detailHasilDateBulan.equals("04")){
                                                         sDetailHasilDateBulan = "April";
                                                     }else if(detailHasilDateBulan.equals("05")){
                                                         sDetailHasilDateBulan = "Mei";
                                                     }else if(detailHasilDateBulan.equals("06")){
                                                         sDetailHasilDateBulan = "Juni";
                                                     }else if(detailHasilDateBulan.equals("07")){
                                                         sDetailHasilDateBulan = "Juli";
                                                     }else if(detailHasilDateBulan.equals("08")){
                                                         sDetailHasilDateBulan = "Agustus";
                                                     }else if(detailHasilDateBulan.equals("09")){
                                                         sDetailHasilDateBulan = "September";
                                                     }else if(detailHasilDateBulan.equals("10")){
                                                         sDetailHasilDateBulan = "Oktober";
                                                     }else if(detailHasilDateBulan.equals("11")){
                                                         sDetailHasilDateBulan = "November";
                                                     }else if(detailHasilDateBulan.equals("12")){
                                                         sDetailHasilDateBulan = "Desember";
                                                     }else if(detailHasilDateBulan.equals("1")){
                                                         sDetailHasilDateBulan = "Januari";
                                                     }else if(detailHasilDateBulan.equals("2")){
                                                         sDetailHasilDateBulan = "Februari";
                                                     }else if(detailHasilDateBulan.equals("3")){
                                                         sDetailHasilDateBulan = "Maret";
                                                     }else if(detailHasilDateBulan.equals("4")){
                                                         sDetailHasilDateBulan = "April";
                                                     }else if(detailHasilDateBulan.equals("5")){
                                                         sDetailHasilDateBulan = "Mei";
                                                     }else if(detailHasilDateBulan.equals("6")){
                                                         sDetailHasilDateBulan = "Juni";
                                                     }else if(detailHasilDateBulan.equals("7")){
                                                         sDetailHasilDateBulan = "Juli";
                                                     }else if(detailHasilDateBulan.equals("8")){
                                                         sDetailHasilDateBulan = "Agustus";
                                                     }else if(detailHasilDateBulan.equals("9")){
                                                         sDetailHasilDateBulan = "September";
                                                     }else if(detailHasilDateBulan.equals("10")){
                                                         sDetailHasilDateBulan = "Oktober";
                                                     }

                                                     j.setTanggal(detailHasilDateTanggal);
                                                     j.setBulan(sDetailHasilDateBulan);
                                                     j.setTahun(detailHasilDateTahun);
                                                 }else{
                                                     j.setTanggal(obj.getString("janji_survey_tanggal"));
                                                     j.setBulan(obj.getString("janji_survey_bulan"));
                                                     j.setTahun(obj.getString("janji_survey_tahun"));
                                                 }












                                                 j.setSurveyaddress(obj.getString("address_home"));
                                                 j.setJarak("-");
                                                 itemList.add(j);
                                             }
                                         }
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                             adapter.notifyDataSetChanged();
                             swipe.setRefreshing(false);
                         }else {
                             cekJson(lat,lng);
                         }

                     }

                 }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_surveyor", get_id_surveyor);
                map.put("tk", setter.APK_CODE);
                map.put("lat", lat);
                map.put("long", lng);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ListTugasActivity.this);
        requestQueue.add(stringRequest);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void getMyLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = "" + String.valueOf(mLastLocation.getLatitude());
                longitude = "" + String.valueOf(mLastLocation.getLongitude());
                callListVolley(latitude, longitude);
            } else {
                callListVolley("0", "0");
            }
        } catch (SecurityException e) {
            Toast.makeText(ListTugasActivity.this,
                    "SecurityException:\n" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        lokasi();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(ListTugasActivity.this,
                "onConnectionSuspended: " + String.valueOf(i),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(ListTugasActivity.this,
                "onConnectionFailed: \n" + connectionResult.toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BackgroundService.clearNotifications(getApplicationContext());
        jumlah_notif = 0;
        ArrayList<ArrayList<Object>> data_cek_notif = dm.ambilBarisNotifTugasALL();
        if (data_cek_notif.size() < 1) {

        } else {
            for (int i = 0; i < data_cek_notif.size(); i++) {
                ArrayList<Object> baris = data_cek_notif.get(i);
                String status_notif = baris.get(3).toString();
                if (status_notif.equals("0")) {
                    jumlah_notif = jumlah_notif + 1;
                }
            }

            if (jumlah_notif > 0) {
                dm.updateBarisNotifTugas();
                startActivity(getIntent());
            }
        }
    }
}