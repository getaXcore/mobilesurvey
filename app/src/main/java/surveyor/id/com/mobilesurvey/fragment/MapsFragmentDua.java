package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.LoginActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.OtherUtil;


public class MapsFragmentDua extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private static final String TAG = MapsFragmentTiga.class.getSimpleName();
    private DatabaseManager dm;
    private Button btn_simpan;
    private Spinner et_komentar;
    private ArrayList<String> cek_list_pending;
    private String h_pending;
    private ArrayAdapter<String> myAdapter_alasan;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    String tag_json_obj = "json_obj_req";
    private String t_id_order,t_latitude,t_longitude,t_janji_survey,get_username,get_userid,
            latitude,longitude;
    private Context hsContext;
    private int resError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps_fragment_dua, container, false);

        buildGoogleApiClient();
        dm = new DatabaseManager(hsContext);

        resError = 1;

        ArrayList<ArrayList<Object>> data_user = dm.ambilSemuaBaris();
        if(data_user.size() > 0){
            ArrayList<Object> baris = data_user.get(0);
            get_username    = baris.get(0).toString();
            get_userid      = baris.get(3).toString();
        }


        et_komentar = (Spinner)view.findViewById(R.id.komentar);
        btn_simpan  = (Button)view.findViewById(R.id.bt_simpan);

        t_id_order      = getArguments().getString("id_order");
        t_latitude      = getArguments().getString("latitude");
        t_longitude     = getArguments().getString("longitude");
        t_janji_survey  = getArguments().getString("janji_survey");

        if(t_janji_survey.equals("0000-00-00 00:00:00")){
            btn_simpan.setVisibility(View.GONE);
        }else{
            btn_simpan.setVisibility(View.VISIBLE);
        }
        ArrayList<ArrayList<Object>> data_pending = dm.ambilBarisJsonPilih("Pending");
        if(data_pending.size()>0){
            ArrayList<Object> baris_cancel = data_pending.get(0);
            String tam_json_hasil = baris_cancel.get(0).toString();
            try {
                cek_list_pending = new ArrayList<String>();
                cek_list_pending.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            h_pending = obj.getString("nama_pending");
                            cek_list_pending.add(h_pending);
                        }
                    }
                }
                myAdapter_alasan = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_pending);
                myAdapter_alasan.setDropDownViewResource(R.layout.spinner_item);
                et_komentar.setAdapter(myAdapter_alasan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lokasiget();
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkAlasan = String.valueOf(et_komentar.getSelectedItem());
                if(checkAlasan.equals("--")){
                    Toast.makeText(hsContext,"Anda harus mengisi alasan terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }else{
                    String locationProviders = Settings.Secure.getString(
                            hsContext.getContentResolver(),
                            Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        Toast.makeText(hsContext,
                                "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else {
                        saveData();
                    }
                }

            }
        });
        return view;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(hsContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void cekJson(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                saveData();
            }
        }, 2000);
    }

    private void saveData(){
        OtherUtil.hideAlertDialog();
        OtherUtil.showAlertDialogLoading(hsContext, "Please Wait ...");

        if(latitude == null){
            latitude    = "";
            longitude   = "";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.UPLOAD_URL_CUSTOMER_TIDAK_ADA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String code = jObj.getString("code");
                    if (code.equals("200")) {
                        Log.d("v Add", jObj.toString());
                        Toast.makeText(hsContext, jObj.getString(TAG_MESSAGE),
                                Toast.LENGTH_LONG).show();
                        dm.addRow_status(get_username, get_userid, "Pending");
                        Activity act = (Activity) hsContext;
                        act.finish();
                    } else {
                        Toast.makeText(hsContext, jObj.getString(TAG_MESSAGE),
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
                resError++;
                if(resError > 3){
                    OtherUtil.hideAlertDialog();
                    Toast.makeText(hsContext,"Tidak Terhubung",Toast.LENGTH_LONG).show();
                    resError = 1;
                }else {
                    cekJson();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("alasan", String.valueOf(et_komentar.getSelectedItem()));
                params.put("id_surveyor", get_userid);
                params.put("id_order", t_id_order);
                params.put("latitude", ""+latitude);
                params.put("longitude", ""+longitude);
                params.put("tk", setter.APK_CODE);
                Log.d(TAG, "" + params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
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
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        latitude = "" + String.valueOf(mLastLocation.getLatitude());
                        longitude = "" + String.valueOf(mLastLocation.getLongitude());
                    }
                } catch (SecurityException e) {

                }
            }
        }
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
    public void onDestroy() {
        super.onDestroy();
        OtherUtil.hideAlertDialog();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        hsContext = context;

        /*Activity a;
        if (context instanceof Activity){
            a=(Activity) context;
        }*/
    }
}