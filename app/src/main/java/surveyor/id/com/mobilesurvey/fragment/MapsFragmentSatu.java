package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.ximpli.library.datetimepicker.date.DatePickerDialog;
import id.ximpli.library.datetimepicker.time.RadialPickerLayout;
import id.ximpli.library.datetimepicker.time.TimePickerDialog;
import surveyor.id.com.mobilesurvey.InputDataFullActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.Jarak;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.OtherUtil;


public class MapsFragmentSatu extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private String real_date,latitude,longitude,h_return,t_id_order,hasil_date,get_userid;
    private String id_order,id_customer,namalengkap_surveyor,asuransi,jenis_kredit,order_code,
            jml_pinjaman,otr,dp,tenor,kode_cabang,jaminan_multiguna,name,identity_type,identity_no,
            address_home,telephone,sex,handphone_1,kategori_kendaraan,merk_kendaraan,
            model_kendaraan,type_kendaraan,tahun,warna,transmisi,plat_nomor,harga,status_survey,
            latitude_survey,longitude_survey,janji_survey;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private ArrayList<String> cek_list_return;
    String tag_json_obj = "json_obj_req";
    private TextView Tam_Nama,Tam_Identity_tipe,Tam_Identity_no,Tam_Address_home,Tam_Telephone,
            Tam_Sex,Tam_Handphone_1,Tam_Kategori_kendaraan,Tam_Merk_kendaraan,Tam_Type_kendaraan,
            Tam_Model_kendaraan,Tam_Tahun_kendaraan,Tam_Warna_kendaraan,Tam_Plat_nomor_kendaraan,
            Tam_Asuransi,Tam_Otr,Tam_Dp,Tam_Jml_pinjaman,
            Tam_Tenor,Tam_Jaminan_multiguna,Tam_Judul_data_kendaraan_kredit,et_janji_survey;
    private Dialog dialog,dialog_customdialog3,dialog_customdialog4;
    private ImageView Tam_type_gambar_kendaraan;
    private Button btn_input_kelengkapan;
    private ArrayAdapter<String> myAdapter_alasan;
    private DatabaseManager dm;
    private Spinner S_alasan;
    private static final String TAG = MapsFragmentSatu.class.getSimpleName();
    private RelativeLayout Tam_Data_Multiguna, Tam_Data_Kendaraan,Tam_Box_otr, Tam_Box_dp;
    Uri uri;
    private Button btn_keputusan;
    private FloatingActionButton btn_fab_telp;
    private Context hsContext;
    private int resErrorNotif;
    private int resErrorKeputusan;
    private int resErrorJanjiSurvey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps_fragment_satu, container, false);
        latitude    = "";
        longitude   = "";

        resErrorNotif       = 1;
        resErrorKeputusan   = 1;
        resErrorJanjiSurvey = 1;

        dm = new DatabaseManager(hsContext);
        buildGoogleApiClient();
        lokasiget();
        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();
        ArrayList<Object> baris = data6.get(0);
        //get_username = baris.get(0).toString();
        get_userid = baris.get(3).toString();

        t_id_order = getArguments().getString("id_order");

        btn_keputusan                   = (Button)view.findViewById(R.id.bt_keputusan);
        btn_input_kelengkapan           = (Button)view.findViewById(R.id.input_kelengkapan);
        Tam_type_gambar_kendaraan       = (ImageView) view.findViewById(R.id.gambar_type);

        Tam_Nama                        = (TextView) view.findViewById(R.id.t_nama);
        Tam_Identity_tipe               = (TextView) view.findViewById(R.id.t_identity_type);
        Tam_Identity_no                 = (TextView) view.findViewById(R.id.t_identity_no);
        Tam_Address_home                = (TextView) view.findViewById(R.id.t_address_home);
        Tam_Telephone                   = (TextView) view.findViewById(R.id.t_telephone);
        Tam_Sex                         = (TextView) view.findViewById(R.id.t_sex);
        Tam_Handphone_1                 = (TextView) view.findViewById(R.id.t_handphone_1);
        Tam_Kategori_kendaraan          = (TextView) view.findViewById(R.id.t_kategori_kendaraan);
        Tam_Merk_kendaraan              = (TextView) view.findViewById(R.id.t_merk);
        Tam_Type_kendaraan              = (TextView) view.findViewById(R.id.t_type);
        Tam_Model_kendaraan             = (TextView) view.findViewById(R.id.t_model);
        Tam_Tahun_kendaraan             = (TextView) view.findViewById(R.id.t_tahun);
        Tam_Warna_kendaraan             = (TextView) view.findViewById(R.id.t_warna);
        Tam_Plat_nomor_kendaraan        = (TextView) view.findViewById(R.id.t_plat);
        /*Tam_Bahan_bakar_kendaraan       = (TextView) view.findViewById(R.id.t_bahan_bakar);
        Tam_Km_kendaraan                = (TextView) view.findViewById(R.id.t_km_kendaraan);*/
        Tam_Asuransi                    = (TextView) view.findViewById(R.id.t_asuransi);
        Tam_Otr                         = (TextView) view.findViewById(R.id.t_otr);
        Tam_Dp                          = (TextView) view.findViewById(R.id.t_dp);
        Tam_Jml_pinjaman                = (TextView) view.findViewById(R.id.t_jml_pinjaman);
        Tam_Tenor                       = (TextView) view.findViewById(R.id.t_tenor);
        Tam_Jaminan_multiguna           = (TextView) view.findViewById(R.id.t_jaminan_multiguna);

        Tam_Data_Multiguna              = (RelativeLayout) view.findViewById(R.id.data_multiguna);
        Tam_Data_Kendaraan              = (RelativeLayout) view.findViewById(R.id.data_kendaraan);

        Tam_Box_otr                     = (RelativeLayout) view.findViewById(R.id.box_otr);
        Tam_Box_dp                      = (RelativeLayout) view.findViewById(R.id.box_dp);

        Tam_Judul_data_kendaraan_kredit = (TextView) view.findViewById(R.id.judul_data_kendaraan_kredit);

        tampildata_all();
        if(jenis_kredit.equals("Multiguna")){
            if(!jaminan_multiguna.equals("SERTIFIKAT RUMAH")){
                Tam_Data_Multiguna.setVisibility(view.getVisibility());
                Tam_Data_Kendaraan.setVisibility(view.getVisibility());
            }else{
                Tam_Data_Multiguna.setVisibility(view.getVisibility());
                Tam_Data_Kendaraan.setVisibility(View.GONE);
            }
        }else{
            Tam_Data_Multiguna.setVisibility(View.GONE);
            Tam_Data_Kendaraan.setVisibility(view.getVisibility());
        }
        btn_fab_telp = (FloatingActionButton) view.findViewById(R.id.fab_telp);
        if(status_survey.equals("0")){
            btn_keputusan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String locationProviders = Settings.Secure.getString(
                            hsContext.getContentResolver(),
                            Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        Toast.makeText(hsContext,
                                "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else {
                        CustomDialog2();
                    }
                }
            });
        }
        btn_fab_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = (Activity) hsContext;
                Intent a_telp = new Intent(Intent.ACTION_DIAL, uri.parse("tel:" + act.
                        getIntent().getExtras().getString("handphone_1")));
                startActivity(a_telp);
            }
        });
        btn_input_kelengkapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    Toast.makeText(hsContext, "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else {
                    CustomDialog();
                }
            }
        });
        return view;
    }

    public void CustomDialog(){
        dialog = new Dialog(hsContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ImageView Bt_close = (ImageView)dialog.findViewById(R.id.bt_close);
        Button bt_belum =(Button) dialog.findViewById(R.id.bt_belum);
        Button bt_sudah =(Button) dialog.findViewById(R.id.bt_sudah);
        Bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        bt_belum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_sudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotif();
            }
        });
        dialog.show();
    }

    private void saveNotif(){
        OtherUtil.hideAlertDialog();
        OtherUtil.showAlertDialogLoading(hsContext, "Please Wait ...");

        String tanggal_now = OtherUtil.getTanggalNow();

        dm.addRowNotifSampai(get_userid,t_id_order,"5",""+latitude,
                ""+longitude,tanggal_now,setter.APK_CODE);
        OtherUtil.hideAlertDialog();
        dialog.dismiss();

        Intent input = new Intent(hsContext, InputDataFullActivity.class);
        Bundle keldata2 = new Bundle();
        keldata2.putString("id_order", t_id_order);
        keldata2.putString("name", name);
        keldata2.putString("identity_type", identity_type);
        keldata2.putString("identity_no", identity_no);
        keldata2.putString("address_home", address_home);
        keldata2.putString("telephone", telephone);
        keldata2.putString("sex", sex);
        keldata2.putString("handphone_1", handphone_1);
        input.putExtras(keldata2);
        startActivity(input);
    }

    public void CustomDialog2(){
        dialog = new Dialog(hsContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ImageView Bt_close = (ImageView)dialog.findViewById(R.id.bt_close);
        Button bt_tidak =(Button) dialog.findViewById(R.id.bt_tidak);
        Button bt_iya =(Button) dialog.findViewById(R.id.bt_iya);
        Bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        bt_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CustomDialog3();
            }
        });

        bt_iya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CustomDialog4();
            }
        });
        dialog.show();
    }

    public void CustomDialog3(){
        dialog_customdialog3 = new Dialog(hsContext);
        dialog_customdialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_customdialog3.setContentView(R.layout.dialogbox3);
        dialog_customdialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_customdialog3.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog_customdialog3.setCanceledOnTouchOutside(true);
        dialog_customdialog3.setCanceledOnTouchOutside(false);
        dialog_customdialog3.setCancelable(false);
        ImageView Bt_close = (ImageView)dialog_customdialog3.findViewById(R.id.bt_close);
        Button Bt_simpan = (Button)dialog_customdialog3.findViewById(R.id.bt_simpan);
        S_alasan = (Spinner)dialog_customdialog3.findViewById(R.id.spinner_alasan);
        ArrayList<ArrayList<Object>> data_return = dm.ambilBarisJsonPilih("Cancel");
        if(data_return.size()>0){
            ArrayList<Object> baris = data_return.get(0);
            String tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_return = new ArrayList<String>();
                cek_list_return.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            h_return = obj.getString("nama_cancel");
                            cek_list_return.add(h_return);
                        }
                    }
                }
                myAdapter_alasan = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_return);
                myAdapter_alasan.setDropDownViewResource(R.layout.spinner_item);
                S_alasan.setAdapter(myAdapter_alasan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_customdialog3.dismiss();
            }
        });
        Bt_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveKeputusan();
            }
        });
        dialog_customdialog3.show();
    }

    private void cekJsonKeputusan(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                saveKeputusan();
            }
        }, 2000);
    }

    private void saveKeputusan(){
        OtherUtil.hideAlertDialog();
        OtherUtil.showAlertDialogLoading(hsContext, "Please Wait ...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.URL_KEPUTUSAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String code = jObj.getString("code");
                    if (code.equals("200")) {
                        Log.d("v Add", jObj.toString());
                        dialog_customdialog3.dismiss();
                        Activity act = (Activity) hsContext;
                        act.finish();
                    } else {
                        Toast.makeText(hsContext,
                                "Tidak terhubung dengan server",
                                Toast.LENGTH_LONG).show();
                    }
                    OtherUtil.hideAlertDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    OtherUtil.hideAlertDialog();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resErrorKeputusan++;
                if(resErrorKeputusan > 3){
                    OtherUtil.hideAlertDialog();
                    Toast.makeText(hsContext,"Tidak Terhubung",Toast.LENGTH_LONG).show();
                    resErrorKeputusan = 1;
                }else {
                    cekJsonKeputusan();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id_surveyor", get_userid);
                params.put("id_order", t_id_order);
                params.put("alasan_return", String.valueOf(S_alasan.getSelectedItem()));
                params.put("status_hasil", "2");
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("tk", setter.APK_CODE);
                Log.d(TAG, ""+params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public void CustomDialog4(){
        dialog_customdialog4 = new Dialog(hsContext);
        dialog_customdialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_customdialog4.setContentView(R.layout.dialogbox4);
        dialog_customdialog4.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_customdialog4.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog_customdialog4.setCanceledOnTouchOutside(true);
        dialog_customdialog4.setCanceledOnTouchOutside(false);
        dialog_customdialog4.setCancelable(false);
        ImageView Bt_close = (ImageView)dialog_customdialog4.findViewById(R.id.bt_close);
        Button Bt_simpan = (Button)dialog_customdialog4.findViewById(R.id.bt_simpan);
        et_janji_survey = (TextView) dialog_customdialog4.findViewById(R.id.t_janji_survey);
        et_janji_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogDate();
            }
        });
        Bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_customdialog4.dismiss();
            }
        });
        Bt_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cek_janji_survey = et_janji_survey.getText().toString();
                if(cek_janji_survey != null){
                    if(!cek_janji_survey.equals("")){
                        saveJanjiSurvey();
                    }else {
                        Toast.makeText(hsContext, "Mohon Janji Survey diisi",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(hsContext, "Mohon Janji Survey diisi",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog_customdialog4.show();
    }


    private void cekJsonJanjiSurvey(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                saveJanjiSurvey();
            }
        }, 2000);
    }


    private void saveJanjiSurvey(){
        OtherUtil.hideAlertDialog();
        OtherUtil.showAlertDialogLoading(hsContext, "Please Wait ...");

        ArrayList<ArrayList<Object>> data_janji_bayar = dm.ambilBarisJanjiSurveyByOrder(get_userid,t_id_order);
        if (data_janji_bayar.size() < 1) {
            dm.addRowJanjiSurvey(get_userid,t_id_order,et_janji_survey.getText().toString(),setter.APK_CODE);
        }

        dialog_customdialog4.dismiss();
        Activity act = (Activity) hsContext;
        act.finish();

        OtherUtil.hideAlertDialog();

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.URL_JANJI_SURVEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String code = jObj.getString("code");
                    if (code.equals("200")) {
                        Log.d("v Add", jObj.toString());
                        dialog_customdialog4.dismiss();
                        Activity act = (Activity) hsContext;
                        act.finish();
                    } else {
                        Toast.makeText(hsContext,
                                "Tidak terhubung dengan server",
                                Toast.LENGTH_LONG).show();
                    }
                    OtherUtil.hideAlertDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    OtherUtil.hideAlertDialog();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resErrorJanjiSurvey++;
                if(resErrorJanjiSurvey > 3){
                    OtherUtil.hideAlertDialog();
                    Toast.makeText(hsContext,"Tidak Terhubung",Toast.LENGTH_LONG).show();
                    resErrorJanjiSurvey = 1;
                }else {
                    cekJsonJanjiSurvey();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id_surveyor", get_userid);
                params.put("id_order", t_id_order);
                params.put("janji_survey", et_janji_survey.getText().toString());
                params.put("tk", setter.APK_CODE);
                Log.d(TAG, ""+params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);*/
    }

    public void OpenDialogDate(){
        String tanggal = real_date;
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.DATE, 0);
        final DateTime setDate;
        if(tanggal != null) {
            if (!tanggal.isEmpty()) {
                setDate = DateTime.parse(tanggal);
            } else {
                setDate = new DateTime();
            }
        }else{
            setDate = new DateTime();
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, setDate.get(DateTimeFieldType.year()),
                setDate.get(DateTimeFieldType.monthOfYear()) - 1,
                setDate.get(DateTimeFieldType.dayOfMonth()) + 1);
        dpd.setMinDate(mindate);
        dpd.setAccentColor(ContextCompat.getColor(hsContext, R.color.colorPrimary));
        dpd.setTitle("Tanggal Janji Survey");
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void OpenDialogTime(){
        String tanggal = real_date;
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.DATE, 0);
        final DateTime setDate;
        if(tanggal != null) {
            if (!tanggal.isEmpty()) {
                setDate = DateTime.parse(tanggal);
            } else {
                setDate = new DateTime();
            }
        }else{
            setDate = new DateTime();
        }
        TimePickerDialog dpd = TimePickerDialog.newInstance(this, setDate.get(DateTimeFieldType.hourOfDay()),
                setDate.get(DateTimeFieldType.minuteOfHour()),true);
        //   dpd.setMinDate(mindate);
        dpd.setAccentColor(ContextCompat.getColor(hsContext, R.color.colorPrimary));
        dpd.setTitle("Jam Janji Survey");
        dpd.show(getFragmentManager(), "Timepickerdialog");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(hsContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void lokasiget() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                try{
                    if (ActivityCompat.checkSelfPermission(hsContext, android.Manifest.
                            permission.ACCESS_FINE_LOCATION) != PackageManager.
                            PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(hsContext,
                            android.Manifest.permission.
                            ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }
                    mLastLocation = LocationServices.FusedLocationApi.
                            getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        latitude = "" + String.valueOf(mLastLocation.getLatitude());
                        longitude = "" + String.valueOf(mLastLocation.getLongitude());
                    }
                } catch (SecurityException e) {

                }
            }
        }
    }

    public void tampildata_all(){
        id_order                = getArguments().getString("id_order");
        id_customer             = getArguments().getString("id_customer");
        namalengkap_surveyor    = getArguments().getString("namalengkap_surveyor");
        asuransi                = getArguments().getString("asuransi");
        jenis_kredit            = getArguments().getString("jenis_kredit");
        order_code              = getArguments().getString("order_code");
        jml_pinjaman            = getArguments().getString("jml_pinjaman");
        otr                     = getArguments().getString("otr");
        dp                      = getArguments().getString("dp");
        tenor                   = getArguments().getString("tenor");
        kode_cabang             = getArguments().getString("kode_cabang");
        jaminan_multiguna       = getArguments().getString("jaminan_multiguna");
        name                    = getArguments().getString("name");
        identity_type           = getArguments().getString("identity_type");
        identity_no             = getArguments().getString("identity_no");
        address_home            = getArguments().getString("address_home");
        telephone               = getArguments().getString("telephone");
        sex                     = getArguments().getString("sex");
        handphone_1             = getArguments().getString("handphone_1");
        kategori_kendaraan      = getArguments().getString("kategori_kendaraan");
        merk_kendaraan          = getArguments().getString("merk_kendaraan");
        model_kendaraan         = getArguments().getString("model_kendaraan");
        type_kendaraan          = getArguments().getString("type_kendaraan");
        tahun                   = getArguments().getString("tahun");
        warna                   = getArguments().getString("warna");
        plat_nomor              = getArguments().getString("plat_nomor");

        /*km_kendaraan            = getArguments().getString("km_kendaraan");
        bahan_bakar             = getArguments().getString("bahan_bakar");*/

        status_survey           = getArguments().getString("status_survey");
        latitude_survey         = getArguments().getString("latitude_survey");
        longitude_survey        = getArguments().getString("longitude_survey");
        janji_survey            = getArguments().getString("janji_survey");

        if(janji_survey.equals("0000-00-00 00:00:00")){
            btn_keputusan.setVisibility(View.VISIBLE);
            btn_input_kelengkapan.setVisibility(View.GONE);
        }else{
            btn_keputusan.setVisibility(View.GONE);
            btn_input_kelengkapan.setVisibility(View.VISIBLE);
        }

        Tam_Nama.setText(name);
        Tam_Identity_tipe.setText(identity_type);
        Tam_Identity_no.setText(identity_no);
        Tam_Address_home.setText(address_home);
        Tam_Telephone.setText(telephone);
        Tam_Sex.setText(sex);
        Tam_Handphone_1.setText(handphone_1);
        if(kategori_kendaraan.equals("null")){
            kategori_kendaraan = "-";
        }
        Tam_Kategori_kendaraan.setText(kategori_kendaraan);
        Tam_Merk_kendaraan.setText(merk_kendaraan);
        Tam_Type_kendaraan.setText(type_kendaraan);
        Tam_Model_kendaraan.setText(model_kendaraan);
        Tam_Tahun_kendaraan.setText(tahun);
        Tam_Warna_kendaraan.setText(warna);
        Tam_Plat_nomor_kendaraan.setText(plat_nomor);
        /*Tam_Bahan_bakar_kendaraan.setText(bahan_bakar);
        Tam_Km_kendaraan.setText(km_kendaraan);*/
        if(asuransi.equals("Pilih Asuransi")){
            asuransi = "-";
        }else if(asuransi.equals("null")){
            asuransi = "-";
        }
        Tam_Asuransi.setText(asuransi);
        //Tam_Dp.setText(dp);



        Tam_Tenor.setText(tenor);
        Tam_Jaminan_multiguna.setText(jaminan_multiguna);


        if(jenis_kredit.equals("Multiguna")){
//            Tam_Otr.setText(otr);
            Tam_Box_otr.setVisibility(View.GONE);
            Tam_Box_dp.setVisibility(View.GONE);
            Tam_Judul_data_kendaraan_kredit.setText("Jaminan Kendaraan");

            Double s_jml_pinjaman=Double.parseDouble(jml_pinjaman);
            Tam_Jml_pinjaman.setText("Rp " + String.format("%s", NumberFormat.
                    getInstance(new Locale("id", "ID")).format(s_jml_pinjaman)));
        }else{
            Tam_Jml_pinjaman.setText(jml_pinjaman);
            Double s_otr=Double.parseDouble(otr);
            Tam_Otr.setText("Rp " + String.format("%s", NumberFormat.
                    getInstance(new Locale("id", "ID")).format(s_otr)));
            Double s_dp=Double.parseDouble(dp);
            Tam_Dp.setText("Rp " + String.format("%s", NumberFormat.
                    getInstance(new Locale("id", "ID")).format(s_dp)));
        }
        Tam_Tenor.setText(tenor+" Bulan");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        lokasiget();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        DateTime setDate  = new DateTime(cal.getTime());
        try {
            real_date     = printDateByFormat(setDate, "yyyy-MM-dd");
           // et_janji_survey.setText(real_date);
            hasil_date = real_date;
            OpenDialogTime();
        }catch (NullPointerException pe){
            pe.printStackTrace();
        }

    }

    public static String printDateByFormat(DateTime dates, String formatDates){
        DateTimeFormatter fmt = DateTimeFormat.forPattern(formatDates);
        return fmt.print(dates);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        et_janji_survey.setText(hasil_date+" "+hourOfDay+":"+minute);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hsContext = context;

        /*Activity a;
        if (context instanceof Activity){
            a=(Activity) context;
        }*/
    }
}