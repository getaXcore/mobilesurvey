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


public class InputFullFragmentLima extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private static final String TAG = InputFullFragmentLima.class.getSimpleName();
    private ArrayAdapter<String> myAdapter_tipe_rumah,
            myAdapter_status_kepemilikan_rumah,myAdapter_klasifikasi_perumahan,
            myAdapter_tempat_menaruh_kendaraan,myAdapter_status_garasi_kendaraan,
            myAdapter_bentuk_bangunan_rumah,myAdapter_kondisi_rumah,
            myAdapter_luas_jalan_masuk_rumah;
    private ArrayList<String> cek_list_tipe_rumah,
            cek_list_status_kepemilikan_rumah,cek_list_klasifikasi_perumahan,
            cek_list_tempat_menaruh_kendaraan,cek_list_status_garasi_kendaraan,
            cek_list_bentuk_bangunan_rumah,cek_list_kondisi_rumah,cek_list_luas_jalan_masuk_rumah;
    private String tam_json_hasil,get_id_order,get_id_surveyor,tipe_rumah,
            status_kepemilikan_rumah,klasifikasi_perumahan,tempat_menaruh_kendaraan,
            status_garasi_kendaraan,bentuk_bangunan_rumah,kondisi_rumah,luas_jalan_masuk_rumah;
    private Button b_simpan;
    private DatabaseManager dm;
    private EditText Jarak_rumah_ke_cabang,Luas_tanah,Luas_bangunan_rumah,Furniture_or_perabot;
    private Spinner S_tipe_rumah,S_status_kepemilikan_rumah,S_klasifikasi_perumahan,
            S_tempat_menaruh_kendaraan,S_status_garasi_kendaraan,S_bentuk_bangunan_rumah,
            S_kondisi_rumah,S_luas_jalan_masuk_rumah;

    public static String C_tipe_rumah,C_Jarak_rumah_ke_cabang,C_Luas_tanah,C_Luas_bangunan_rumah,
            C_status_kepemilikan_rumah,C_klasifikasi_perumahan,C_tempat_menaruh_kendaraan,
            C_status_garasi_kendaraan,C_bentuk_bangunan_rumah,C_kondisi_rumah,
            C_luas_jalan_masuk_rumah,C_furniture_or_perabot;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_lima, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);

        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        S_tipe_rumah                = (Spinner) view.findViewById(R.id.spinner_tipe_rumah);
        Jarak_rumah_ke_cabang       = (EditText) view.findViewById(R.id.etx_jarak_rumah_ke_cabang);
        Luas_tanah                  = (EditText) view.findViewById(R.id.etx_luas_tanah);
        Luas_bangunan_rumah         = (EditText) view.findViewById(R.id.etx_luas_bangunan_rumah);
        S_status_kepemilikan_rumah  = (Spinner) view.findViewById(R.id.spinner_status_kepemilikan_rumah);
        S_klasifikasi_perumahan     = (Spinner) view.findViewById(R.id.spinner_klasifikasi_perumahan);
        S_tempat_menaruh_kendaraan  = (Spinner) view.findViewById(R.id.spinner_tempat_menaruh_kendaraan);
        S_status_garasi_kendaraan   = (Spinner) view.findViewById(R.id.spinner_status_garasi_kendaraan);
        S_bentuk_bangunan_rumah     = (Spinner) view.findViewById(R.id.spinner_bentuk_bangunan_rumah);
        S_kondisi_rumah             = (Spinner) view.findViewById(R.id.spinner_kondisi_rumah);
        S_luas_jalan_masuk_rumah    = (Spinner) view.findViewById(R.id.spinner_luas_jalan_masuk_rumah);
        Furniture_or_perabot        = (EditText) view.findViewById(R.id.etx_furniture_or_perabot);
        Img_check                   = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                    = (Button) view.findViewById(R.id.bt_simpan);

        TampilTipeRumah();
        //TampilHomeStatus();
        TampilStatusKepemilikanRumah();
        TampilKlasifikasiPerumahan();
        TampilTempatMenaruhKendaraan();
        TampilStatusGarasiKendaraan();
        TampilBentukBangunanRumah();
        TampilKondisiRumah();
        TampilLuasJalanMasukRumah();
        hasil_data();

        b_simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_tipe_rumah               = String.valueOf(S_tipe_rumah.getSelectedItem());
                C_Jarak_rumah_ke_cabang    = Jarak_rumah_ke_cabang.getText().toString();
                C_Luas_tanah               = Luas_tanah.getText().toString();
                C_Luas_bangunan_rumah      = Luas_bangunan_rumah.getText().toString();
                C_status_kepemilikan_rumah = String.valueOf(S_status_kepemilikan_rumah.getSelectedItem());
                C_klasifikasi_perumahan    = String.valueOf(S_klasifikasi_perumahan.getSelectedItem());
                C_tempat_menaruh_kendaraan = String.valueOf(S_tempat_menaruh_kendaraan.getSelectedItem());
                C_status_garasi_kendaraan  = String.valueOf(S_status_garasi_kendaraan.getSelectedItem());
                C_bentuk_bangunan_rumah    = String.valueOf(S_bentuk_bangunan_rumah.getSelectedItem());
                C_kondisi_rumah            = String.valueOf(S_kondisi_rumah.getSelectedItem());
                C_luas_jalan_masuk_rumah   = String.valueOf(S_luas_jalan_masuk_rumah.getSelectedItem());
                C_furniture_or_perabot     = Furniture_or_perabot.getText().toString();

                Log.d(TAG, "Response: id_order = "+get_id_order+",Jarak_rumah_ke_cabang = "
                        + C_Jarak_rumah_ke_cabang +",Luas_tanah ="
                        +C_Luas_tanah+",Luas_bangunan_rumah ="
                        +C_Luas_bangunan_rumah+",status_kepemilikan_rumah ="
                        +C_status_kepemilikan_rumah+",klasifikasi_perumahan ="
                        +C_klasifikasi_perumahan +",tempat_menaruh_kendaraan = "
                        +C_tempat_menaruh_kendaraan+",status_garasi_kendaraan = "
                        +C_status_garasi_kendaraan+",bentuk_bangunan_rumah ="
                        +C_bentuk_bangunan_rumah+",kondisi_rumah ="
                        +C_kondisi_rumah+",luas_jalan_masuk_rumah ="
                        +C_luas_jalan_masuk_rumah+",status_kepemilikan_rumah_pemohon ="
                        +" ,furniture_or_perabot ="
                        +C_furniture_or_perabot);
                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey5(get_id_surveyor,get_id_order,C_tipe_rumah,"",
                            C_Jarak_rumah_ke_cabang,C_Luas_tanah,C_Luas_bangunan_rumah,
                            C_status_kepemilikan_rumah,C_klasifikasi_perumahan,
                            C_tempat_menaruh_kendaraan,C_status_garasi_kendaraan,
                            C_bentuk_bangunan_rumah,C_kondisi_rumah,C_luas_jalan_masuk_rumah,
                            "",C_furniture_or_perabot);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey5(get_id_order,C_tipe_rumah,"",
                            C_Jarak_rumah_ke_cabang,C_Luas_tanah,
                            C_Luas_bangunan_rumah,C_status_kepemilikan_rumah,
                            C_klasifikasi_perumahan,C_tempat_menaruh_kendaraan,
                            C_status_garasi_kendaraan,C_bentuk_bangunan_rumah,C_kondisi_rumah,
                            C_luas_jalan_masuk_rumah,"",
                            C_furniture_or_perabot);
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
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_tipe_rumah                         = ""+baris.get(88);
           // String t_home_status                        = ""+baris.get(89);
            String t_jarak_rumah_ke_cabang              = ""+baris.get(90);
            String t_luas_tanah                         = ""+baris.get(91);
            String t_luas_bangunan_rumah                = ""+baris.get(92);
            String t_status_kepemilikan_rumah           = ""+baris.get(93);
            String t_klasifikasi_perumahan              = ""+baris.get(94);
            String t_tempat_menaruh_kendaraan           = ""+baris.get(95);
            String t_status_garasi_kendaraan            = ""+baris.get(96);
            String t_bentuk_bangunan_rumah              = ""+baris.get(97);
            String t_kondisi_rumah                      = ""+baris.get(98);
            String t_luas_jalan_masuk_rumah             = ""+baris.get(99);
            String t_status_kepemilikan_rumah_pemohon   = ""+baris.get(100);
            String t_furniture                          = ""+baris.get(101);

            if(t_jarak_rumah_ke_cabang.equals("null")){
                status_lengkap = status_lengkap+1;
                Jarak_rumah_ke_cabang.setText("");
            }else{
                Jarak_rumah_ke_cabang.setText(t_jarak_rumah_ke_cabang);
            }

            if(t_luas_tanah.equals("null")){
                status_lengkap = status_lengkap+1;
                Luas_tanah.setText("");
            }else{
                Luas_tanah.setText(t_luas_tanah);
            }

            if(t_luas_bangunan_rumah.equals("null")){
                status_lengkap = status_lengkap+1;
                Luas_bangunan_rumah.setText("");
            }else{
                Luas_bangunan_rumah.setText(t_luas_bangunan_rumah);
            }

            if(t_tipe_rumah.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_tipe_rumah = myAdapter_tipe_rumah.
                    getPosition(t_tipe_rumah);
            S_tipe_rumah.setSelection(spinner_tipe_rumah);



            if(t_status_kepemilikan_rumah.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_status_kepemilikan_rumah = myAdapter_status_kepemilikan_rumah.
                    getPosition(t_status_kepemilikan_rumah);
            S_status_kepemilikan_rumah.setSelection(spinner_status_kepemilikan_rumah);

            if(t_klasifikasi_perumahan.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_klasifikasi_perumahan = myAdapter_klasifikasi_perumahan.
                    getPosition(t_klasifikasi_perumahan);
            S_klasifikasi_perumahan.setSelection(spinner_klasifikasi_perumahan);

            if(t_tempat_menaruh_kendaraan.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_tempat_menaruh_kendaraan = myAdapter_tempat_menaruh_kendaraan.
                    getPosition(t_tempat_menaruh_kendaraan);
            S_tempat_menaruh_kendaraan.setSelection(spinner_tempat_menaruh_kendaraan);

            if(t_status_garasi_kendaraan.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_status_garasi_kendaraan = myAdapter_status_garasi_kendaraan.
                    getPosition(t_status_garasi_kendaraan);
            S_status_garasi_kendaraan.setSelection(spinner_status_garasi_kendaraan);

            if(t_bentuk_bangunan_rumah.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_bentuk_bangunan_rumah = myAdapter_bentuk_bangunan_rumah.
                    getPosition(t_bentuk_bangunan_rumah);
            S_bentuk_bangunan_rumah.setSelection(spinner_bentuk_bangunan_rumah);

            if(t_kondisi_rumah.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_kondisi_rumah = myAdapter_kondisi_rumah.getPosition(t_kondisi_rumah);
            S_kondisi_rumah.setSelection(spinner_kondisi_rumah);

            if(t_luas_jalan_masuk_rumah.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_luas_jalan_masuk_rumah = myAdapter_luas_jalan_masuk_rumah.
                    getPosition(t_luas_jalan_masuk_rumah);
            S_luas_jalan_masuk_rumah.setSelection(spinner_luas_jalan_masuk_rumah);

            if(t_status_kepemilikan_rumah_pemohon.equals("--")){
                status_lengkap = status_lengkap+1;
            }

            if(t_furniture.equals("null")){
                status_lengkap = status_lengkap+1;
                Furniture_or_perabot.setText("");
            }else{
                Furniture_or_perabot.setText(t_furniture);
            }


            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab5 = dm.ambilBarisTab("TAB 5",get_id_order);
                if (data_tab5.size() < 1) {
                    dm.addRowTab("Tempat Tinggal(Rumah)","TAB 5", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    public void TampilTipeRumah(){
        ArrayList<ArrayList<Object>> data_tipe_rumah = dm.ambilBarisJsonPilih("Tipe Rumah");
        if(data_tipe_rumah.size()>0){
            ArrayList<Object> baris = data_tipe_rumah.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_tipe_rumah = new ArrayList<String>();
                cek_list_tipe_rumah.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            tipe_rumah = obj.getString("nama_tipe_rumah");
                            cek_list_tipe_rumah.add(tipe_rumah);
                        }
                    }
                }
                myAdapter_tipe_rumah = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_tipe_rumah);
                myAdapter_tipe_rumah.setDropDownViewResource(R.layout.spinner_item);
                S_tipe_rumah.setAdapter(myAdapter_tipe_rumah);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public void TampilStatusKepemilikanRumah(){
        ArrayList<ArrayList<Object>> data_status_kepemilikan_rumah = dm.ambilBarisJsonPilih("Status Kepemilikan Rumah");
        if(data_status_kepemilikan_rumah.size()>0){
            ArrayList<Object> baris = data_status_kepemilikan_rumah.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_status_kepemilikan_rumah = new ArrayList<String>();
                cek_list_status_kepemilikan_rumah.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            status_kepemilikan_rumah = obj.getString("nama_status_kepemilikan_rumah");
                            cek_list_status_kepemilikan_rumah.add(status_kepemilikan_rumah);
                        }
                    }
                }
                myAdapter_status_kepemilikan_rumah = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_status_kepemilikan_rumah);
                myAdapter_status_kepemilikan_rumah.setDropDownViewResource(R.layout.spinner_item);
                S_status_kepemilikan_rumah.setAdapter(myAdapter_status_kepemilikan_rumah);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilKlasifikasiPerumahan(){
        ArrayList<ArrayList<Object>> data_klasifikasi_perumahan = dm.ambilBarisJsonPilih("Klasifikasi Perumahan");
        if(data_klasifikasi_perumahan.size()>0){
            ArrayList<Object> baris = data_klasifikasi_perumahan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_klasifikasi_perumahan = new ArrayList<String>();
                cek_list_klasifikasi_perumahan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            klasifikasi_perumahan = obj.getString("nama_klasifikasi_perumahan");
                            cek_list_klasifikasi_perumahan.add(klasifikasi_perumahan);
                        }
                    }
                }
                myAdapter_klasifikasi_perumahan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_klasifikasi_perumahan);
                myAdapter_klasifikasi_perumahan.setDropDownViewResource(R.layout.spinner_item);
                S_klasifikasi_perumahan.setAdapter(myAdapter_klasifikasi_perumahan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilTempatMenaruhKendaraan(){
        ArrayList<ArrayList<Object>> data_tempat_menaruh_kendaraan = dm.ambilBarisJsonPilih("Tempat Menaruh Kendaraan");
        if(data_tempat_menaruh_kendaraan.size()>0){
            ArrayList<Object> baris = data_tempat_menaruh_kendaraan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_tempat_menaruh_kendaraan = new ArrayList<String>();
                cek_list_tempat_menaruh_kendaraan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            tempat_menaruh_kendaraan = obj.getString("nama_tempat_menaruh_kendaraan");
                            cek_list_tempat_menaruh_kendaraan.add(tempat_menaruh_kendaraan);
                        }
                    }
                }
                myAdapter_tempat_menaruh_kendaraan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_tempat_menaruh_kendaraan);
                myAdapter_tempat_menaruh_kendaraan.setDropDownViewResource(R.layout.spinner_item);
                S_tempat_menaruh_kendaraan.setAdapter(myAdapter_tempat_menaruh_kendaraan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilStatusGarasiKendaraan(){
        ArrayList<ArrayList<Object>> data_status_garasi_kendaraan = dm.ambilBarisJsonPilih("Status Garasi Kendaraan");
        if(data_status_garasi_kendaraan.size()>0){
            ArrayList<Object> baris = data_status_garasi_kendaraan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_status_garasi_kendaraan = new ArrayList<String>();
                cek_list_status_garasi_kendaraan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            status_garasi_kendaraan = obj.getString("nama_status_garasi_kendaraan");
                            cek_list_status_garasi_kendaraan.add(status_garasi_kendaraan);
                        }
                    }
                }
                myAdapter_status_garasi_kendaraan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_status_garasi_kendaraan);
                myAdapter_status_garasi_kendaraan.setDropDownViewResource(R.layout.spinner_item);
                S_status_garasi_kendaraan.setAdapter(myAdapter_status_garasi_kendaraan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilBentukBangunanRumah(){
        ArrayList<ArrayList<Object>> data_bentuk_bangunan_rumah = dm.ambilBarisJsonPilih("Bentuk Bangunan Rumah");
        if(data_bentuk_bangunan_rumah.size()>0){
            ArrayList<Object> baris = data_bentuk_bangunan_rumah.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_bentuk_bangunan_rumah = new ArrayList<String>();
                cek_list_bentuk_bangunan_rumah.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            bentuk_bangunan_rumah = obj.getString("nama_bentuk_bangunan_rumah");
                            cek_list_bentuk_bangunan_rumah.add(bentuk_bangunan_rumah);
                        }
                    }
                }
                myAdapter_bentuk_bangunan_rumah = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_bentuk_bangunan_rumah);
                myAdapter_bentuk_bangunan_rumah.setDropDownViewResource(R.layout.spinner_item);
                S_bentuk_bangunan_rumah.setAdapter(myAdapter_bentuk_bangunan_rumah);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilKondisiRumah(){
        ArrayList<ArrayList<Object>> data_kondisi_rumah = dm.ambilBarisJsonPilih("Kondisi Rumah");
        if(data_kondisi_rumah.size()>0){
            ArrayList<Object> baris = data_kondisi_rumah.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_kondisi_rumah = new ArrayList<String>();
                cek_list_kondisi_rumah.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            kondisi_rumah = obj.getString("nama_kondisi_rumah");
                            cek_list_kondisi_rumah.add(kondisi_rumah);
                        }
                    }
                }
                myAdapter_kondisi_rumah = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_kondisi_rumah);
                myAdapter_kondisi_rumah.setDropDownViewResource(R.layout.spinner_item);
                S_kondisi_rumah.setAdapter(myAdapter_kondisi_rumah);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilLuasJalanMasukRumah(){
        ArrayList<ArrayList<Object>> data_luas_jalan_masuk_rumah = dm.ambilBarisJsonPilih("Luas Jalan Masuk Rumah");
        if(data_luas_jalan_masuk_rumah.size()>0){
            ArrayList<Object> baris = data_luas_jalan_masuk_rumah.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_luas_jalan_masuk_rumah = new ArrayList<String>();
                cek_list_luas_jalan_masuk_rumah.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            luas_jalan_masuk_rumah = obj.getString("nama_luas_jalan_masuk_rumah");
                            cek_list_luas_jalan_masuk_rumah.add(luas_jalan_masuk_rumah);
                        }
                    }
                }
                myAdapter_luas_jalan_masuk_rumah = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_luas_jalan_masuk_rumah);
                myAdapter_luas_jalan_masuk_rumah.setDropDownViewResource(R.layout.spinner_item);
                S_luas_jalan_masuk_rumah.setAdapter(myAdapter_luas_jalan_masuk_rumah);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    @Override
    public void onResume() {
        super.onResume();
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