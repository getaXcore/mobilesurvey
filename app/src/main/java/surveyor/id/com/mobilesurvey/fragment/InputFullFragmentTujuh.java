package surveyor.id.com.mobilesurvey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class InputFullFragmentTujuh extends Fragment  {
    private int status_lengkap;
    private static final String TAG = InputFullFragmentTujuh.class.getSimpleName();
    private ImageView Img_check;
    private String tam_json_hasil,tx_kondisi_mobil,tujuan_penggunaan_unit,kondisi_mobil,get_id_order,get_id_surveyor;
    private ArrayAdapter<String> myAdapter_tujuan_penggunaan_unit,myAdapter_kondisi_mobil;
    private ArrayList<String> cek_list_tujuan_penggunaan_unit,cek_list_kondisi_mobil;
    private DatabaseManager dm;
    private Button b_simpan;
    private Spinner S_tujuan_penggunaan_unit,S_kondisi_mobil;
    private EditText Lama_kepemilikan_kendaraan_tahun,Lama_kepemilikan_kendaraan_bulan,etx_bagian_kondisi_tidak_baik;
    private RelativeLayout Bagian_kondisi_tidak_baik;

    public static String C_tujuan_penggunaan_unit,C_kondisi_mobil,
            C_Lama_kepemilikan_kendaraan_tahun,C_Lama_kepemilikan_kendaraan_bulan,
            C_etx_bagian_kondisi_tidak_baik;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_tujuh, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        S_tujuan_penggunaan_unit            = (Spinner) view.findViewById(R.id.spinner_tujuan_penggunaan_unit);
        S_kondisi_mobil                     = (Spinner) view.findViewById(R.id.spinner_kondisi_mobil);
        Lama_kepemilikan_kendaraan_tahun    = (EditText) view.findViewById(R.id.etx_lama_kepemilikan_kendaraan_tahun);
        Lama_kepemilikan_kendaraan_bulan    = (EditText) view.findViewById(R.id.etx_lama_kepemilikan_kendaraan_bulan);
        etx_bagian_kondisi_tidak_baik       = (EditText) view.findViewById(R.id.etx_bagian_kondisi_tidak_baik);
        Bagian_kondisi_tidak_baik           = (RelativeLayout) view.findViewById(R.id.bagian_kondisi_tidak_baik);
        Img_check                           = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                            = (Button) view.findViewById(R.id.bt_simpan);

        TampilTujuanPenggunaanUnit();
        TampilKondisiMobil();
        hasil_data();

        S_kondisi_mobil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub

                tx_kondisi_mobil = String.valueOf(S_kondisi_mobil.getSelectedItem());
                S_kondisi_mobil.setSelection(position);

                if(tx_kondisi_mobil.equals("Tidak dalam Kondisi Baik, di bagian")){
                    Bagian_kondisi_tidak_baik.setVisibility(View.VISIBLE);
                }else {
                    Bagian_kondisi_tidak_baik.setVisibility(View.GONE);
                    etx_bagian_kondisi_tidak_baik.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_tujuan_penggunaan_unit           = String.valueOf(S_tujuan_penggunaan_unit.getSelectedItem());
                C_kondisi_mobil                    = String.valueOf(S_kondisi_mobil.getSelectedItem());
                C_Lama_kepemilikan_kendaraan_tahun = Lama_kepemilikan_kendaraan_tahun.getText().toString();
                C_Lama_kepemilikan_kendaraan_bulan = Lama_kepemilikan_kendaraan_bulan.getText().toString();
                C_etx_bagian_kondisi_tidak_baik    = etx_bagian_kondisi_tidak_baik.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey7(get_id_surveyor,get_id_order,C_tujuan_penggunaan_unit,
                            C_kondisi_mobil,C_etx_bagian_kondisi_tidak_baik,
                            C_Lama_kepemilikan_kendaraan_tahun,C_Lama_kepemilikan_kendaraan_bulan);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey7(get_id_order,C_tujuan_penggunaan_unit,C_kondisi_mobil,
                            C_etx_bagian_kondisi_tidak_baik,C_Lama_kepemilikan_kendaraan_tahun,
                            C_Lama_kepemilikan_kendaraan_bulan);
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
            String t_tujuan_penggunaan_unit             = ""+baris.get(112);
            String t_kondisi_mobil                      = ""+baris.get(113);
            String t_bagian_kondisi_yang_tidak_baik     = ""+baris.get(114);
            String t_lama_kepemilikan_kendaraan_tahun   = ""+baris.get(115);
            String t_lama_kepemilikan_kendaraan_bulan   = ""+baris.get(116);

            if(t_tujuan_penggunaan_unit.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_tujuan_penggunaan_unit = myAdapter_tujuan_penggunaan_unit.getPosition(t_tujuan_penggunaan_unit);
            S_tujuan_penggunaan_unit.setSelection(spinner_tujuan_penggunaan_unit);

            if(t_kondisi_mobil.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_kondisi_mobil = myAdapter_kondisi_mobil.getPosition(t_kondisi_mobil);
            S_kondisi_mobil.setSelection(spinner_kondisi_mobil);

            if(t_bagian_kondisi_yang_tidak_baik.equals("null")){
                etx_bagian_kondisi_tidak_baik.setText("");
            }else{
                etx_bagian_kondisi_tidak_baik.setText(t_bagian_kondisi_yang_tidak_baik);
            }

            if(t_lama_kepemilikan_kendaraan_tahun.equals("null")){
                status_lengkap = status_lengkap+1;
                Lama_kepemilikan_kendaraan_tahun.setText("");
            }else{
                Lama_kepemilikan_kendaraan_tahun.setText(t_lama_kepemilikan_kendaraan_tahun);
            }
            if(t_lama_kepemilikan_kendaraan_bulan.equals("null")){
                status_lengkap = status_lengkap+1;
                Lama_kepemilikan_kendaraan_bulan.setText("");
            }else{
                Lama_kepemilikan_kendaraan_bulan.setText(t_lama_kepemilikan_kendaraan_bulan);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab7 = dm.ambilBarisTab("TAB 7",get_id_order);
                if (data_tab7.size() < 1) {
                    dm.addRowTab("Objek Pembiayaan","TAB 7", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    public void TampilTujuanPenggunaanUnit(){
        ArrayList<ArrayList<Object>> data_tujuan_penggunaan_unit = dm.ambilBarisJsonPilih("Tujuan Penggunaan Unit");
        if(data_tujuan_penggunaan_unit.size()>0){
            ArrayList<Object> baris = data_tujuan_penggunaan_unit.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_tujuan_penggunaan_unit = new ArrayList<String>();
                cek_list_tujuan_penggunaan_unit.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            tujuan_penggunaan_unit = obj.getString("nama_tujuan_penggunaan_unit");
                            cek_list_tujuan_penggunaan_unit.add(tujuan_penggunaan_unit);
                        }
                    }
                }
                myAdapter_tujuan_penggunaan_unit = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_tujuan_penggunaan_unit);
                myAdapter_tujuan_penggunaan_unit.setDropDownViewResource(R.layout.spinner_item);
                S_tujuan_penggunaan_unit.setAdapter(myAdapter_tujuan_penggunaan_unit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void TampilKondisiMobil(){
        ArrayList<ArrayList<Object>> data_kondisi_mobil = dm.ambilBarisJsonPilih("Kondisi Mobil");
        if(data_kondisi_mobil.size()>0){
            ArrayList<Object> baris = data_kondisi_mobil.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_kondisi_mobil = new ArrayList<String>();
                cek_list_kondisi_mobil.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            kondisi_mobil = obj.getString("nama_kondisi_mobil");
                            cek_list_kondisi_mobil.add(kondisi_mobil);
                        }
                    }
                }
                myAdapter_kondisi_mobil = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_kondisi_mobil);
                myAdapter_kondisi_mobil.setDropDownViewResource(R.layout.spinner_item);
                S_kondisi_mobil.setAdapter(myAdapter_kondisi_mobil);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateTujuanPenggunaanUnit();
        UpdateKondisiMobil();
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
