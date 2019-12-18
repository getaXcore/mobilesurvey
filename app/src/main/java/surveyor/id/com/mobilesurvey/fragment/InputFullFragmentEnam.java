package surveyor.id.com.mobilesurvey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;


public class InputFullFragmentEnam extends Fragment  {
    private static final String TAG = InputFullFragmentEnam.class.getSimpleName();
    private ImageView Img_check;
    private int status_lengkap;
    private ArrayAdapter<String> myAdapter_status_kepemilikan_usaha,
            myAdapter_bentuk_bangunan_tempat_usaha,myAdapter_lokasi_tempat_usaha,
            myAdapter_jumlah_karyawan;
    private ArrayList<String> cek_list_status_kepemilikan_usaha,cek_list_bentuk_bangunan_tmp_usaha,
            cek_list_lokasi_tempat_usaha,cek_list_jumlah_karyawan;
    private RadioButton radioButton;
    private View view;
    private String tam_json_hasil,get_id_order,get_id_surveyor,status_kepemilikan_usaha,
            bentuk_bangunan_tmp_usaha,lokasi_tempat_usaha,jumlah_karyawan;
    private DatabaseManager dm;
    private RadioGroup radioGroup_pekerjaan_or_usaha_terkait_ekspor_or_impor;
    private Button b_simpan;
    private Spinner S_status_kepemilikan_usaha,S_bentuk_bangunan_tempat_usaha,S_lokasi_tempat_usaha,
            S_jumlah_karyawan;
    private EditText Jarak_tempat_usaha_dari_rumah,Lama_pemohon_menempati_tempat_usaha_tahun,
            Lama_pemohon_menempati_tempat_usaha_bulan,Lama_usaha_berdiri_tahun,
            Lama_usaha_berdiri_bulan;

    public static String C_Jarak_tempat_usaha_dari_rumah,C_status_kepemilikan_usaha,
            C_bentuk_bangunan_tempat_usaha,C_lokasi_tempat_usaha,C_jumlah_karyawan,
            C_Lama_pemohon_menempati_tempat_usaha_tahun,C_Lama_pemohon_menempati_tempat_usaha_bulan,
            C_Lama_usaha_berdiri_tahun,C_Lama_usaha_berdiri_bulan,
            C_pekerjaan_or_usaha_terkait_ekspor_or_impor;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_full_fragment_enam, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        Jarak_tempat_usaha_dari_rumah                           = (EditText) view.findViewById(R.id.etx_jarak_tempat_usaha_dari_rumah);
        S_status_kepemilikan_usaha                              = (Spinner) view.findViewById(R.id.spinner_status_kepemilikan_usaha);
        S_bentuk_bangunan_tempat_usaha                          = (Spinner) view.findViewById(R.id.spinner_bentuk_bangunan_tempat_usaha);
        S_lokasi_tempat_usaha                                   = (Spinner) view.findViewById(R.id.spinner_lokasi_tempat_usaha);
        S_jumlah_karyawan                                       = (Spinner) view.findViewById(R.id.spinner_jumlah_karyawan);
        Lama_pemohon_menempati_tempat_usaha_tahun               = (EditText) view.findViewById(R.id.etx_lama_pemohon_menempati_tempat_usaha_tahun);
        Lama_pemohon_menempati_tempat_usaha_bulan               = (EditText) view.findViewById(R.id.etx_lama_pemohon_menempati_tempat_usaha_bulan);
        Lama_usaha_berdiri_tahun                                = (EditText) view.findViewById(R.id.etx_lama_usaha_berdiri_tahun);
        Lama_usaha_berdiri_bulan                                = (EditText) view.findViewById(R.id.etx_lama_usaha_berdiri_bulan);
        radioGroup_pekerjaan_or_usaha_terkait_ekspor_or_impor   = (RadioGroup) view.findViewById(R.id.radioGroup_pekerjaan_or_usaha_terkait_ekspor_or_impor);
        Img_check                                               = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                                                = (Button) view.findViewById(R.id.bt_simpan);

        TampilStatusKepemilikanUsaha();
        TampilBentukBangunanTmpUsaha();
        TampilLokasiTempatUsaha();
        TampilJumlahKaryawan();
        hasil_data();

        b_simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_Jarak_tempat_usaha_dari_rumah                = Jarak_tempat_usaha_dari_rumah.getText().toString();
                C_status_kepemilikan_usaha                     = String.valueOf(S_status_kepemilikan_usaha.getSelectedItem());
                C_bentuk_bangunan_tempat_usaha                 = String.valueOf(S_bentuk_bangunan_tempat_usaha.getSelectedItem());
                C_lokasi_tempat_usaha                          = String.valueOf(S_lokasi_tempat_usaha.getSelectedItem());
                C_jumlah_karyawan                              = String.valueOf(S_jumlah_karyawan.getSelectedItem());
                C_Lama_pemohon_menempati_tempat_usaha_tahun    = Lama_pemohon_menempati_tempat_usaha_tahun.getText().toString();
                C_Lama_pemohon_menempati_tempat_usaha_bulan    = Lama_pemohon_menempati_tempat_usaha_bulan.getText().toString();
                C_Lama_usaha_berdiri_tahun                     = Lama_usaha_berdiri_tahun.getText().toString();
                C_Lama_usaha_berdiri_bulan                     = Lama_usaha_berdiri_bulan.getText().toString();

                int selectedId = radioGroup_pekerjaan_or_usaha_terkait_ekspor_or_impor.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);
                C_pekerjaan_or_usaha_terkait_ekspor_or_impor = ""+radioButton.getText();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey6(get_id_surveyor,get_id_order,C_Jarak_tempat_usaha_dari_rumah,
                            C_status_kepemilikan_usaha,C_bentuk_bangunan_tempat_usaha,
                            C_lokasi_tempat_usaha,C_jumlah_karyawan,
                            C_Lama_pemohon_menempati_tempat_usaha_tahun,
                            C_Lama_pemohon_menempati_tempat_usaha_bulan,
                            C_Lama_usaha_berdiri_tahun,C_Lama_usaha_berdiri_bulan,
                            C_pekerjaan_or_usaha_terkait_ekspor_or_impor);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey6(get_id_order,C_Jarak_tempat_usaha_dari_rumah,
                            C_status_kepemilikan_usaha,C_bentuk_bangunan_tempat_usaha,
                            C_lokasi_tempat_usaha,C_jumlah_karyawan,
                            C_Lama_pemohon_menempati_tempat_usaha_tahun,
                            C_Lama_pemohon_menempati_tempat_usaha_bulan,C_Lama_usaha_berdiri_tahun,
                            C_Lama_usaha_berdiri_bulan,
                            C_pekerjaan_or_usaha_terkait_ekspor_or_impor);
                    Toast.makeText(hsContext, "Update Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                }
                hasil_data();
            }

        });
        return view;
    }

    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);//
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_jarak_tempat_usaha_dari_rumah              = ""+baris.get(102);
            String t_status_kepemilikan_usaha                   = ""+baris.get(103);
            String t_bentuk_bangunan_tempat_usaha               = ""+baris.get(104);
            String t_lokasi_tempat_usaha                        = ""+baris.get(105);
            String t_jumlah_karyawan                            = ""+baris.get(106);
            String t_lama_pemohon_menempati_tempat_usaha_tahun  = ""+baris.get(107);
            String t_lama_pemohon_menempati_tempat_usaha_bulan  = ""+baris.get(108);
            String t_lama_usaha_berdiri_tahun                   = ""+baris.get(109);
            String t_lama_usaha_berdiri_bulan                   = ""+baris.get(110);
            String t_pekerjaan_or_usaha_terkait_ekspor_or_impor = ""+baris.get(111);

            RadioButton R_ya    = (RadioButton) view.findViewById(R.id.radioButton_ya);
            RadioButton R_tidak = (RadioButton) view.findViewById(R.id.radioButton_tidak);
            if(t_pekerjaan_or_usaha_terkait_ekspor_or_impor.equals("Ya")){
                R_ya.setChecked(true);
                R_tidak.setChecked(false);
            }else{
                R_ya.setChecked(false);
                R_tidak.setChecked(true);
            }

            if(t_jarak_tempat_usaha_dari_rumah.equals("null")){
                Jarak_tempat_usaha_dari_rumah.setText("");
            }else{
                Jarak_tempat_usaha_dari_rumah.setText(t_jarak_tempat_usaha_dari_rumah);
            }

            if(t_status_kepemilikan_usaha.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_status_kepemilikan_usaha = myAdapter_status_kepemilikan_usaha.
                    getPosition(t_status_kepemilikan_usaha);
            S_status_kepemilikan_usaha.setSelection(spinner_status_kepemilikan_usaha);

            if(t_bentuk_bangunan_tempat_usaha.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_bentuk_bangunan_tempat_usaha = myAdapter_bentuk_bangunan_tempat_usaha.
                    getPosition(t_bentuk_bangunan_tempat_usaha);
            S_bentuk_bangunan_tempat_usaha.setSelection(spinner_bentuk_bangunan_tempat_usaha);


            int spinner_lokasi_tempat_usaha = myAdapter_lokasi_tempat_usaha.
                    getPosition(t_lokasi_tempat_usaha);
            S_lokasi_tempat_usaha.setSelection(spinner_lokasi_tempat_usaha);

            if(t_jumlah_karyawan.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_jumlah_karyawan = myAdapter_jumlah_karyawan.
                    getPosition(t_jumlah_karyawan);
            S_jumlah_karyawan.setSelection(spinner_jumlah_karyawan);

            if(t_lama_pemohon_menempati_tempat_usaha_tahun.equals("null")){
                status_lengkap = status_lengkap+1;
                Lama_pemohon_menempati_tempat_usaha_tahun.setText("");
            }else{
                Lama_pemohon_menempati_tempat_usaha_tahun.setText(t_lama_pemohon_menempati_tempat_usaha_tahun);
            }

            if(t_lama_pemohon_menempati_tempat_usaha_bulan.equals("null")){
                status_lengkap = status_lengkap+1;
                Lama_pemohon_menempati_tempat_usaha_bulan.setText("");
            }else{
                Lama_pemohon_menempati_tempat_usaha_bulan.setText(t_lama_pemohon_menempati_tempat_usaha_bulan);
            }

            if(t_lama_usaha_berdiri_tahun.equals("null")){
                Lama_usaha_berdiri_tahun.setText("");
            }else{
                Lama_usaha_berdiri_tahun.setText(t_lama_usaha_berdiri_tahun);
            }

            if(t_lama_usaha_berdiri_bulan.equals("null")){
                Lama_usaha_berdiri_bulan.setText("");
            }else{
                Lama_usaha_berdiri_bulan.setText(t_lama_usaha_berdiri_bulan);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab6 = dm.ambilBarisTab("TAB 6",get_id_order);
                if (data_tab6.size() < 1) {
                    dm.addRowTab("Pekerjaan/Tempat Usaha","TAB 6", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    public void TampilStatusKepemilikanUsaha(){
        ArrayList<ArrayList<Object>> data_status_kepemilikan_usaha = dm.ambilBarisJsonPilih("Status Kepemilikan Usaha");
        if(data_status_kepemilikan_usaha.size()>0){
            ArrayList<Object> baris = data_status_kepemilikan_usaha.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_status_kepemilikan_usaha = new ArrayList<String>();
                cek_list_status_kepemilikan_usaha.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            status_kepemilikan_usaha = obj.getString("nama_status_kepemilikan_usaha");
                            cek_list_status_kepemilikan_usaha.add(status_kepemilikan_usaha);
                        }
                    }
                }
                myAdapter_status_kepemilikan_usaha = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_status_kepemilikan_usaha);
                myAdapter_status_kepemilikan_usaha.setDropDownViewResource(R.layout.spinner_item);
                S_status_kepemilikan_usaha.setAdapter(myAdapter_status_kepemilikan_usaha);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilBentukBangunanTmpUsaha(){
        ArrayList<ArrayList<Object>> data_bentuk_bangunan_tmp_usaha = dm.ambilBarisJsonPilih("Bentuk Bangunan Tmp Usaha");
        if(data_bentuk_bangunan_tmp_usaha.size()>0){
            ArrayList<Object> baris = data_bentuk_bangunan_tmp_usaha.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_bentuk_bangunan_tmp_usaha = new ArrayList<String>();
                cek_list_bentuk_bangunan_tmp_usaha.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            bentuk_bangunan_tmp_usaha = obj.getString("nama_bentuk_bangunan_tmp_usaha");
                            cek_list_bentuk_bangunan_tmp_usaha.add(bentuk_bangunan_tmp_usaha);
                        }
                    }
                }
                myAdapter_bentuk_bangunan_tempat_usaha = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_bentuk_bangunan_tmp_usaha);
                myAdapter_bentuk_bangunan_tempat_usaha.setDropDownViewResource(R.layout.spinner_item);
                S_bentuk_bangunan_tempat_usaha.setAdapter(myAdapter_bentuk_bangunan_tempat_usaha);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilLokasiTempatUsaha(){
        ArrayList<ArrayList<Object>> data_lokasi_tempat_usaha = dm.ambilBarisJsonPilih("Lokasi Tempat Usaha");
        if(data_lokasi_tempat_usaha.size()>0){
            ArrayList<Object> baris = data_lokasi_tempat_usaha.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_lokasi_tempat_usaha = new ArrayList<String>();
                cek_list_lokasi_tempat_usaha.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            lokasi_tempat_usaha = obj.getString("nama_lokasi_tempat_usaha");
                            cek_list_lokasi_tempat_usaha.add(lokasi_tempat_usaha);
                        }
                    }
                }
                myAdapter_lokasi_tempat_usaha = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_lokasi_tempat_usaha);
                myAdapter_lokasi_tempat_usaha.setDropDownViewResource(R.layout.spinner_item);
                S_lokasi_tempat_usaha.setAdapter(myAdapter_lokasi_tempat_usaha);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilJumlahKaryawan(){
        ArrayList<ArrayList<Object>> data_jumlah_karyawan = dm.ambilBarisJsonPilih("Jumlah Karyawan");
        if(data_jumlah_karyawan.size()>0){
            ArrayList<Object> baris = data_jumlah_karyawan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_jumlah_karyawan = new ArrayList<String>();
                cek_list_jumlah_karyawan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            jumlah_karyawan = obj.getString("nama_jumlah_karyawan");
                            cek_list_jumlah_karyawan.add(jumlah_karyawan);
                        }
                    }
                }
                myAdapter_jumlah_karyawan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_jumlah_karyawan);
                myAdapter_jumlah_karyawan.setDropDownViewResource(R.layout.spinner_item);
                S_jumlah_karyawan.setAdapter(myAdapter_jumlah_karyawan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateStatusKepemilikanUsaha();
        UpdateBentukBangunanTmpUsaha();
        UpdateLokasiTempatUsaha();
        UpdateJumlahKaryawan();
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
