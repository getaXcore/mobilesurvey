package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;


public class InputFullFragmentDua extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private EditText Company_name,Company_address,Company_telephone_1,Company_telephone_2,
            Line_of_business, Estabilished_since,Company_fax_1;
    private Spinner S_job_title,S_pekerjaan;
    private String pekerjaan,job_title,province_company,kab_kodya_company,kecamatan_company,
            kelurahan_company,kodesandidati2_company,kodepos_company,sektor_ekonomi,kode_ekonomi,
            tam_json_hasil,get_id_order,get_id_surveyor,
            id_province_company,id_kab_kodya_company,id_kecamatan_company,id_kelurahan_company,zipcode_company,
            ocpt_type_id,ocpt_type_desc,ocpt_code_id,ocpt_code_desc;
    private static final String TAG = InputFullFragmentDua.class.getSimpleName();
    private ArrayList<String> cek_list_pekerjaan,cek_list_job_title,cek_list_province_company,
            cek_list_kab_kodya_company,cek_list_kecamatan_company,cek_list_kelurahan_company,
            cek_list_economy_code,cek_list_id_province_company,cek_list_id_kab_kodya_company,
            cek_list_id_kec_company,cek_list_id_kel_company,cek_list_ocpt_type_id,cek_list_ocpt_type,
            cek_list_ocpt_code_id,cek_list_ocpt_code;
    private ArrayAdapter<String> myAdapter_pekerjaan,myAdapter_job_title;
    private SpinnerDialog spinnerDialog_province_company,spinnerDialog_kab_kodya_company,
            spinnerDialog_kecamatan_company,spinnerDialog_kelurahan_company,
            spinnerDialog_economy_code,spinnerDialog_ocpt_type,spinnerDialog_ocpt_code;
    private TextView S_name_economy_code,S_province_company,S_kab_kodya_company,S_kecamatan_company,
            S_kelurahan_company,Sandi_dati_2_company,Postal_code_company,Economy_code,S_Tipe_pekerjaan,S_Jenis_Pekerjaan;
    private DatabaseManager dm;
    private Button b_simpan;

    public static String C_pekerjaan,C_job_title,C_Name_economy_code,C_Company_name,
            C_Company_address,C_Company_province,C_Company_kab_or_kodya,C_Company_kecamatan,
            C_Company_kelurahan,C_Sandi_dati_2_company,C_Postal_code_company,C_Company_telephone_1,
            C_Company_telephone_2,C_Line_of_business,C_Economy_code,C_Estabilished_since,
            C_Company_fax_1,C_Tipe_pekerjaan,C_Tipe_pekerjaan_code,C_Jenis_pekerjaan,C_Jenis_pekerjaan_code;
    private Context hsContext;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_dua, container, false);

        dm = new DatabaseManager(hsContext);
        get_id_order = getArguments().getString("id_order");
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        S_Tipe_pekerjaan        = (TextView) view.findViewById(R.id.spinner_tipe_pekerjaan);
        S_Jenis_Pekerjaan       = (TextView) view.findViewById(R.id.spinner_pekerjaan);
        //S_pekerjaan             = (Spinner) view.findViewById(R.id.spinner_pekerjaan);
        S_job_title             = (Spinner) view.findViewById(R.id.spinner_job_title);
        S_name_economy_code     = (TextView) view.findViewById(R.id.etx_name_economy_code);
        Company_name            = (EditText) view.findViewById(R.id.etx_company_name);
        Company_address         = (EditText) view.findViewById(R.id.etx_company_address);
        S_province_company      = (TextView) view.findViewById(R.id.spinner_company_province);
        S_kab_kodya_company     = (TextView) view.findViewById(R.id.spinner_company_kab_or_kodya);
        S_kecamatan_company     = (TextView) view.findViewById(R.id.spinner_company_kecamatan);
        S_kelurahan_company     = (TextView) view.findViewById(R.id.spinner_company_kelurahan);
        Sandi_dati_2_company    = (TextView) view.findViewById(R.id.etx_company_sandi_dati_2);
        Postal_code_company     = (TextView) view.findViewById(R.id.etx_company_postal_code);
        Company_telephone_1     = (EditText) view.findViewById(R.id.etx_company_telephone_1);
        Company_telephone_2     = (EditText) view.findViewById(R.id.etx_company_telephone_2);
        Line_of_business        = (EditText) view.findViewById(R.id.etx_line_of_business);
        Economy_code            = (TextView) view.findViewById(R.id.etx_economy_code);
        Estabilished_since      = (EditText) view.findViewById(R.id.etx_estabilished_since);
        Company_fax_1           = (EditText) view.findViewById(R.id.etx_company_fax_1);
        Img_check               = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                = (Button) view.findViewById(R.id.bt_simpan);

        //TampilPekerjaan();
        TampilTipePekerjaan();
        TampilJenisPekerjaan();
        TampilJobTitle();
        TampilEconomyCode();
        TampilProvinceCompany();
        TampilKabKodyaCompany();
        TampilKecamatanCompany();
        TampilKelurahanCompany();
        hasil_data();

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //C_pekerjaan            = String.valueOf(S_pekerjaan.getSelectedItem());
                C_Tipe_pekerjaan       = S_Tipe_pekerjaan.getText().toString();
                C_Tipe_pekerjaan_code  = S_Tipe_pekerjaan.getTag().toString();
                C_Jenis_pekerjaan      = S_Jenis_Pekerjaan.getText().toString();
                C_Jenis_pekerjaan_code = S_Jenis_Pekerjaan.getTag().toString();
                C_job_title            = String.valueOf(S_job_title.getSelectedItem());
                C_Name_economy_code    = S_name_economy_code.getText().toString();
                C_Company_name         = Company_name.getText().toString();
                C_Company_address      = Company_address.getText().toString();
                C_Company_province     = S_province_company.getText().toString();
                C_Company_kab_or_kodya = S_kab_kodya_company.getText().toString();
                C_Company_kecamatan    = S_kecamatan_company.getText().toString();
                C_Company_kelurahan    = S_kelurahan_company.getText().toString();
                C_Sandi_dati_2_company = Sandi_dati_2_company.getText().toString();
                C_Postal_code_company  = Postal_code_company.getText().toString();
                C_Company_telephone_1  = Company_telephone_1.getText().toString();
                C_Company_telephone_2  = Company_telephone_2.getText().toString();
                C_Line_of_business     = Line_of_business.getText().toString();
                C_Economy_code         = Economy_code.getText().toString();
                C_Estabilished_since   = Estabilished_since.getText().toString();
                C_Company_fax_1        = Company_fax_1.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey2(get_id_surveyor, get_id_order, C_Jenis_pekerjaan, C_job_title,
                            C_Name_economy_code, C_Company_name, C_Company_address,
                            C_Company_province, C_Company_kab_or_kodya, C_Company_kecamatan,
                            C_Company_kelurahan, C_Sandi_dati_2_company, C_Postal_code_company,
                            C_Company_telephone_1, C_Company_telephone_2, C_Line_of_business,
                            C_Economy_code, C_Estabilished_since, C_Company_fax_1,C_Tipe_pekerjaan,C_Tipe_pekerjaan_code,
                            C_Jenis_pekerjaan,C_Jenis_pekerjaan_code);
                    Toast.makeText(hsContext, "Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey2(get_id_order, C_Jenis_pekerjaan, C_job_title,
                            C_Name_economy_code, C_Company_name, C_Company_address,
                            C_Company_province, C_Company_kab_or_kodya, C_Company_kecamatan,
                            C_Company_kelurahan, C_Sandi_dati_2_company, C_Postal_code_company,
                            C_Company_telephone_1, C_Company_telephone_2, C_Line_of_business,
                            C_Economy_code, C_Estabilished_since, C_Company_fax_1,C_Tipe_pekerjaan,C_Tipe_pekerjaan_code,
                            C_Jenis_pekerjaan,C_Jenis_pekerjaan_code);
                    Toast.makeText(hsContext,
                            "Update Simpan Sementara", Toast.LENGTH_LONG).show();
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
            //String t_pekerjaan              = ""+baris.get(37);
            String t_job_title              = ""+baris.get(38);
            String t_Name_economy_code      = ""+baris.get(39);
            String t_Economy_code           = ""+baris.get(40);
            String t_Company_name           = ""+baris.get(41);
            String t_Company_address        = ""+baris.get(42);
            String t_Company_province       = ""+baris.get(43);
            String t_Company_kab_or_kodya   = ""+baris.get(44);
            String t_Company_kecamatan      = ""+baris.get(45);
            String t_Company_kelurahan      = ""+baris.get(46);
            String t_Sandi_dati_2_company   = ""+baris.get(47);
            String t_Postal_code_company    = ""+baris.get(48);
            String t_Company_telephone_1    = ""+baris.get(49);
            String t_Company_telephone_2    = ""+baris.get(50);
            String t_Line_of_business       = ""+baris.get(51);
            String t_Estabilished_since     = ""+baris.get(52);
            String t_Company_fax_1          = ""+baris.get(53);
            String t_Tipe_pekerjaan         = ""+baris.get(138);
            String t_Tipe_pekerjaan_code    = ""+baris.get(139);
            String t_Jenis_pekerjaan        = ""+baris.get(140);
            String t_Jenis_pekerjaan_code   = ""+baris.get(141);

            /*if(t_pekerjaan.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_pekerjaan = myAdapter_pekerjaan.getPosition(t_pekerjaan);*/
            //S_pekerjaan.setSelection(spinner_pekerjaan);

            if (t_Tipe_pekerjaan.equals("--") || t_Tipe_pekerjaan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_Tipe_pekerjaan.setText("");
            }else {
                S_Tipe_pekerjaan.setText(t_Tipe_pekerjaan);
            }
            if (t_Tipe_pekerjaan_code.equals("")){
                status_lengkap = status_lengkap+1;
                S_Tipe_pekerjaan.setTag("");
            }else {
                S_Tipe_pekerjaan.setTag(t_Tipe_pekerjaan_code);
            }

            if (t_Jenis_pekerjaan.equals("--") || t_Jenis_pekerjaan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_Jenis_Pekerjaan.setText("");
            }else {
                S_Jenis_Pekerjaan.setText(t_Jenis_pekerjaan);
            }
            if (t_Jenis_pekerjaan_code.equals("")){
                status_lengkap = status_lengkap+1;
                S_Jenis_Pekerjaan.setTag("");
            }else {
                S_Jenis_Pekerjaan.setTag(t_Jenis_pekerjaan_code);
            }

            if(t_job_title.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_job_title = myAdapter_job_title.getPosition(t_job_title);
            S_job_title.setSelection(spinner_job_title);

            if(t_Name_economy_code.equals("null")){
                status_lengkap = status_lengkap+1;
                S_name_economy_code.setText("");
            }else{
                S_name_economy_code.setText(t_Name_economy_code);
            }

            if(t_Company_name.equals("null")){
                status_lengkap = status_lengkap+1;
                Company_name.setText("");
            }else{
                Company_name.setText(t_Company_name);
            }

            if(t_Company_address.equals("null")){
                status_lengkap = status_lengkap+1;
                Company_address.setText("");
            }else{
                Company_address.setText(t_Company_address);
            }

            if(t_Company_province.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_company.setText("");
            }else{
                S_province_company.setText(t_Company_province);
            }

            if(t_Company_kab_or_kodya.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_company.setText("");
            }else{
                S_kab_kodya_company.setText(t_Company_kab_or_kodya);
            }

            if(t_Company_kecamatan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_company.setText("");
            }else{
                S_kecamatan_company.setText(t_Company_kecamatan);
            }

            if(t_Company_kelurahan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_company.setText("");
            }else{
                S_kelurahan_company.setText(t_Company_kelurahan);
            }

            if(t_Sandi_dati_2_company.equals("null")){
                status_lengkap = status_lengkap+1;
                Sandi_dati_2_company.setText("");
            }else{
                Sandi_dati_2_company.setText(t_Sandi_dati_2_company);
            }

            if(t_Postal_code_company.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_company.setText("");
            }else{
                Postal_code_company.setText(t_Postal_code_company);
            }

            if(t_Company_telephone_1.equals("null")){
                status_lengkap = status_lengkap+1;
                Company_telephone_1.setText("");
            }else{
                Company_telephone_1.setText(t_Company_telephone_1);
            }

            if(t_Company_telephone_2.equals("null")){
                Company_telephone_2.setText("");
            }else{
                Company_telephone_2.setText(t_Company_telephone_2);
            }

            if(t_Line_of_business.equals("null")){
                Line_of_business.setText("");
            }else{
                Line_of_business.setText(t_Line_of_business);
            }

            if(t_Economy_code.equals("null")){
                status_lengkap = status_lengkap+1;
                Economy_code.setText("");
            }else{
                Economy_code.setText(t_Economy_code);
            }

            if(t_Estabilished_since.equals("null")){
                Estabilished_since.setText("");
            }else{
                Estabilished_since.setText(t_Estabilished_since);
            }

            if(t_Company_fax_1.equals("null")){
                Company_fax_1.setText("");
            }else{
                Company_fax_1.setText(t_Company_fax_1);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab2 = dm.ambilBarisTab("TAB 2",get_id_order);
                if (data_tab2.size() < 1) {
                    dm.addRowTab("Occupation Data","TAB 2", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    /*public void TampilPekerjaan(){
        ArrayList<ArrayList<Object>> data_spouse_pekerjaan = dm.ambilBarisJsonPilih("Pekerjaan");
        if(data_spouse_pekerjaan.size()>0){
            ArrayList<Object> baris = data_spouse_pekerjaan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_pekerjaan = new ArrayList<String>();
                cek_list_pekerjaan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            pekerjaan = obj.getString("nama_pekerjaan");
                            cek_list_pekerjaan.add(pekerjaan);
                        }
                    }
                }
                myAdapter_pekerjaan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_pekerjaan);
                myAdapter_pekerjaan.setDropDownViewResource(R.layout.spinner_item);
                S_pekerjaan.setAdapter(myAdapter_pekerjaan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/


    public void TampilJobTitle(){
        ArrayList<ArrayList<Object>> data_job_title = dm.ambilBarisJsonPilih("Job Title");
        if(data_job_title.size()>0){
            ArrayList<Object> baris = data_job_title.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_job_title = new ArrayList<String>();
                cek_list_job_title.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            job_title = obj.getString("nama_job_title");
                            cek_list_job_title.add(job_title);
                        }
                    }
                }
                myAdapter_job_title = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_job_title);
                myAdapter_job_title.setDropDownViewResource(R.layout.spinner_item);
                S_job_title.setAdapter(myAdapter_job_title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilEconomyCode(){
        ArrayList<ArrayList<Object>> data_economy_code = dm.ambilBarisJsonPilih("Economy Code");
        if(data_economy_code.size()>0){
            ArrayList<Object> baris = data_economy_code.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_economy_code = new ArrayList<String>();
                cek_list_economy_code.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            sektor_ekonomi = obj.getString("sektorekonomi");
                            if (cek_list_economy_code.contains(sektor_ekonomi)) {

                            }else{
                                cek_list_economy_code.add(sektor_ekonomi);
                            }
                        }
                    }
                }
                spinnerDialog_economy_code = new SpinnerDialog((Activity) hsContext,
                        cek_list_economy_code,"Select item");
                S_name_economy_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialog_economy_code.showSpinerDialog();
                    }
                });
                spinnerDialog_economy_code.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        S_name_economy_code.setText(item);
                        TampilallEconomyCode();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilallEconomyCode(){
        ArrayList<ArrayList<Object>> data_code_economy = dm.ambilBarisJsonPilih("Economy Code");
        if(data_code_economy.size()>0){
            ArrayList<Object> baris = data_code_economy.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            sektor_ekonomi = obj.getString("sektorekonomi");
                            kode_ekonomi = obj.getString("kode");
                            if(sektor_ekonomi.equals(S_name_economy_code.getText())){
                                Economy_code.setText(kode_ekonomi);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilTipePekerjaan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_OCPT_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,response.toString());
                        try {
                            cek_list_ocpt_type = new ArrayList<String>();
                            cek_list_ocpt_type.add("--");

                            cek_list_ocpt_type_id = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")){
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0){
                                    for (int i=0;i<arrayData.length();i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        ocpt_type_desc = obj.getString("ocpt_descr");
                                        ocpt_type_id = obj.getString("ocpt_type");
                                        cek_list_ocpt_type.add(ocpt_type_desc);
                                        cek_list_ocpt_type_id.add(ocpt_type_id);
                                    }
                                }
                            }

                            spinnerDialog_ocpt_type = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_ocpt_type,"Select item");
                            S_Tipe_pekerjaan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_ocpt_type.showSpinerDialog();
                                }
                            });
                            spinnerDialog_ocpt_type.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_Tipe_pekerjaan.setText(item);
                                    S_Tipe_pekerjaan.setTag(cek_list_ocpt_type_id.get(position-1));
                                    S_Jenis_Pekerjaan.setText("");
                                    TampilJenisPekerjaan();
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(hsContext,"Gagal memuat Tipe Pekrjaan. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
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

    public void TampilJenisPekerjaan(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_OCPT_CODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,response.toString());
                        try {
                            cek_list_ocpt_code = new ArrayList<String>();
                            cek_list_ocpt_code.add("--");

                            cek_list_ocpt_code_id = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")){
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0){
                                    for (int i=0;i<arrayData.length();i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        ocpt_code_desc = obj.getString("ocpt_descr");
                                        ocpt_code_id = obj.getString("ocpt_code");
                                        cek_list_ocpt_code.add(ocpt_code_desc);
                                        cek_list_ocpt_code_id.add(ocpt_code_id);
                                    }
                                }
                            }
                            spinnerDialog_ocpt_code = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_ocpt_code,"Select item");
                            S_Jenis_Pekerjaan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_ocpt_code.showSpinerDialog();
                                }
                            });
                            spinnerDialog_ocpt_code.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_Jenis_Pekerjaan.setText(item);
                                    S_Jenis_Pekerjaan.setTag(cek_list_ocpt_code_id.get(position-1));
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(hsContext,"Gagal memuat Jenis Pekrjaan. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String ocpt_type;
                if (S_Tipe_pekerjaan.getTag() != null){
                    ocpt_type = S_Tipe_pekerjaan.getTag().toString();
                }else{
                    ocpt_type = "";
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("ocpt_type",ocpt_type);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilProvinceCompanyOld(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_province_company = new ArrayList<String>();
                            cek_list_province_company.add("--");

                            cek_list_id_province_company = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray provData = jsonArray.getJSONArray(0);
                                JSONArray idProvData = jsonArray.getJSONArray(1);
                                if (provData.length() > 0) {
                                    for(int i=0; i<provData.length(); i++){
                                        JSONObject obj = provData.getJSONObject(i);
                                        province_company = obj.getString("propinsi");
                                        if (cek_list_province_company.contains(province_company)) {

                                        }else{
                                            cek_list_province_company.add(province_company);
                                        }

                                        JSONObject jsonObject = idProvData.getJSONObject(i);
                                        id_province_company = jsonObject.getString("kode_propinsi");
                                        if (cek_list_id_province_company.contains(id_province_company)){

                                        }else {
                                            cek_list_id_province_company.add(id_province_company);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_province_company = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_province_company,"Select item");
                            S_province_company.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_province_company.showSpinerDialog();
                                }
                            });
                            spinnerDialog_province_company.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_province_company.setText(item);
                                    S_province_company.setTag(cek_list_id_province_company.get(position-1));
                                    S_kab_kodya_company.setText("");
                                    S_kecamatan_company.setText("");
                                    S_kelurahan_company.setText("");
                                    Sandi_dati_2_company.setText("");
                                    Postal_code_company.setText("");
                                    TampilKabKodyaCompany();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(hsContext,"Gagal memuat propinsi. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
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
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);

        //loading process dialog
        /*progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data provinsi...");
        progressDialog.show();*/
    }

    public void TampilProvinceCompany(){
        ArrayList<ArrayList<Object>> ListData = dm.ambilSemuaProvinsi();

        if (ListData.size() > 0){
            try {
                cek_list_province_company = new ArrayList<String>();
                cek_list_id_province_company = new ArrayList<String>();

                for (int i=0;i<ListData.size();i++){
                    province_company = ListData.get(i).get(0).toString();
                    id_province_company = ListData.get(i).get(1).toString();

                    cek_list_province_company.add(province_company);
                    cek_list_id_province_company.add(id_province_company);
                }

                spinnerDialog_province_company = new SpinnerDialog((Activity) hsContext,cek_list_province_company,"Select item");
                S_province_company.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spinnerDialog_province_company.showSpinerDialog();
                    }
                });
                spinnerDialog_province_company.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        if (!item.equals("")){
                            S_province_company.setText(item);
                            S_province_company.setTag(cek_list_id_province_company.get(position));
                            S_kab_kodya_company.setText("");
                            S_kecamatan_company.setText("");
                            S_kelurahan_company.setText("");
                            Sandi_dati_2_company.setText("");
                            Postal_code_company.setText("");
                            TampilKabKodyaCompany();
                        }
                    }
                });
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public void TampilKabKodyaCompanyOld(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kab_kodya_company = new ArrayList<String>();
                            cek_list_kab_kodya_company.add("--");

                            cek_list_id_kab_kodya_company = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kotaData = jsonArray.getJSONArray(0);
                                JSONArray idKotaData = jsonArray.getJSONArray(1);
                                if (kotaData.length() > 0) {
                                    for(int i=0; i<kotaData.length(); i++){
                                        JSONObject obj = kotaData.getJSONObject(i);
                                        kab_kodya_company = obj.getString("kbpktm");
                                        if (cek_list_kab_kodya_company.contains(kab_kodya_company)) {
                                            // true
                                        }else{
                                            cek_list_kab_kodya_company.add(kab_kodya_company);
                                        }

                                        JSONObject jsonObject = idKotaData.getJSONObject(i);
                                        id_kab_kodya_company = jsonObject.getString("kode_kota");
                                        if (cek_list_id_kab_kodya_company.contains(id_kab_kodya_company)){

                                        }else{
                                            cek_list_id_kab_kodya_company.add(id_kab_kodya_company);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kab_kodya_company = new SpinnerDialog((Activity) hsContext,
                                    cek_list_kab_kodya_company,"Select item");
                            S_kab_kodya_company.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kab_kodya_company.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kab_kodya_company.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kab_kodya_company.setText(item);
                                    S_kab_kodya_company.setTag(cek_list_id_kab_kodya_company.get(position-1));
                                    S_kecamatan_company.setText("");
                                    S_kelurahan_company.setText("");
                                    Sandi_dati_2_company.setText("");
                                    Postal_code_company.setText("");
                                    TampilKecamatanCompany();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(hsContext,"Gagal memuat data kab/kodya. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
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
                String idProv;

                if (S_province_company.getTag() != null){
                    idProv = S_province_company.getTag().toString();
                }else {
                    idProv = "";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_propinsi", idProv);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);

        //loading process dialog
        /*progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data kab/kodya...");
        progressDialog.show();*/
    }

    public void TampilKabKodyaCompany(){
        String provCode;
        if (S_province_company.getTag() != null){
            provCode = S_province_company.getTag().toString();
        }else {
            provCode = "";
        }

        ArrayList<ArrayList<Object>> ListData = dm.ambilKotaByProv(provCode);

        if (ListData.size() > 0) {

            try {

                cek_list_kab_kodya_company = new ArrayList<String>();
                cek_list_id_kab_kodya_company = new ArrayList<String>();

                for (int i=0; i<ListData.size();i++){
                    kab_kodya_company = ListData.get(i).get(0).toString();
                    id_kab_kodya_company = ListData.get(i).get(1).toString();

                    cek_list_kab_kodya_company.add(kab_kodya_company);
                    cek_list_id_kab_kodya_company.add(id_kab_kodya_company);

                }

                spinnerDialog_kab_kodya_company = new SpinnerDialog((Activity) hsContext,cek_list_kab_kodya_company,"Select item");
                S_kab_kodya_company.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialog_kab_kodya_company.showSpinerDialog();
                    }
                });
                spinnerDialog_kab_kodya_company.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        S_kab_kodya_company.setText(item);
                        S_kab_kodya_company.setTag(cek_list_id_kab_kodya_company.get(position));
                        S_kecamatan_company.setText("");
                        S_kelurahan_company.setText("");
                        Sandi_dati_2_company.setText("");
                        Postal_code_company.setText("");
                        TampilKecamatanCompany();
                    }
                });
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public void TampilKecamatanCompany(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kecamatan_company = new ArrayList<String>();
                            cek_list_kecamatan_company.add("--");

                            cek_list_id_kec_company = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kecData = jsonArray.getJSONArray(0);
                                JSONArray idKecData = jsonArray.getJSONArray(1);
                                if (kecData.length() > 0) {
                                    for(int i=0; i<kecData.length(); i++){
                                        JSONObject obj = kecData.getJSONObject(i);
                                        kecamatan_company = obj.getString("kecamatan");
                                        if (cek_list_kecamatan_company.contains(kecamatan_company)) {
                                            // true
                                        }else{
                                            cek_list_kecamatan_company.add(kecamatan_company);
                                        }

                                        JSONObject jsonObject = idKecData.getJSONObject(i);
                                        id_kecamatan_company = jsonObject.getString("kode_kecamatan");
                                        if (cek_list_id_kec_company.contains(id_kecamatan_company)){

                                        }else {
                                            cek_list_id_kec_company.add(id_kecamatan_company);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kecamatan_company = new SpinnerDialog(
                                    (Activity) hsContext,cek_list_kecamatan_company,
                                    "Select item");
                            S_kecamatan_company.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kecamatan_company.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kecamatan_company.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kecamatan_company.setText(item);
                                    S_kecamatan_company.setTag(cek_list_id_kec_company.get(position-1));
                                    S_kelurahan_company.setText("");
                                    Sandi_dati_2_company.setText("");
                                    Postal_code_company.setText("");
                                    TampilKelurahanCompany();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(hsContext,"Gagal memuat data kecamatan. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
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
                String idKota;

                if (S_kab_kodya_company.getTag() != null){
                    idKota = S_kab_kodya_company.getTag().toString();
                }else {
                    idKota = "";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_kbpktm", idKota);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);

        //loading process dialog
        /*progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data kecamatan...");
        progressDialog.show();*/
    }

    public void TampilKelurahanCompany(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kelurahan_company = new ArrayList<String>();
                            cek_list_kelurahan_company.add("--");

                            cek_list_id_kel_company = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kelData = jsonArray.getJSONArray(0);
                                JSONArray idKelData = jsonArray.getJSONArray(1);
                                if (kelData.length() > 0) {
                                    for(int i=0; i<kelData.length(); i++){
                                        JSONObject obj = kelData.getJSONObject(i);
                                        kelurahan_company = obj.getString("kelurahan");
                                        if (cek_list_kelurahan_company.contains(kelurahan_company)) {
                                            // true
                                        }else{
                                            cek_list_kelurahan_company.add(kelurahan_company);
                                        }

                                        JSONObject jsonObject = idKelData.getJSONObject(i);
                                        id_kelurahan_company = jsonObject.getString("kode_kelurahan");
                                        if (cek_list_id_kel_company.contains(id_kelurahan_company)){

                                        }else {
                                            cek_list_id_kel_company.add(id_kelurahan_company);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kelurahan_company = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kelurahan_company,"Select item");
                            S_kelurahan_company.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kelurahan_company.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kelurahan_company.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kelurahan_company.setText(item);
                                    S_kelurahan_company.setTag(cek_list_id_kel_company.get(position-1));
                                    TampilSandiDati2KodePosCompany();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(hsContext,"Gagal memuat data kelurahan. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));

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
                String idKec;


                if (S_kecamatan_company.getTag() != null){
                    idKec = S_kecamatan_company.getTag().toString();
                }else {
                    idKec = "";
                }


                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_kecamatan", idKec);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);

        //loading process dialog
        /*progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data kelurahan...");
        progressDialog.show();*/
    }

    public void TampilSandiDati2KodePosCompany(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KODEPOS_SANDIDATI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        kodepos_company = obj.getString("kodepos");
                                        zipcode_company = obj.getString("zipcode");
                                        kodesandidati2_company = obj.getString("sandidati2");

                                        Postal_code_company.setText(kodepos_company);
                                        Postal_code_company.setTag(zipcode_company);
                                        Sandi_dati_2_company.setText(kodesandidati2_company);
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

                        Toast.makeText(hsContext,"Gagal memuat data sandi dati. Koneksi internet tidak stabil.",Toast.LENGTH_LONG).show();
                        Log.e("VollyError",String.valueOf(error.getMessage()));
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
                String idProv,idKota,idKec,idKel;

                if (S_province_company.getTag() != null){
                    idProv = S_province_company.getTag().toString();
                }else {
                    idProv = "";
                }
                if (S_kab_kodya_company.getTag() != null){
                    idKota = S_kab_kodya_company.getTag().toString();
                }else {
                    idKota = "";
                }
                if (S_kecamatan_company.getTag() != null){
                    idKec = S_kecamatan_company.getTag().toString();
                }else {
                    idKec = "";
                }
                if (S_kelurahan_company.getTag() != null){
                    idKel = S_kelurahan_company.getTag().toString();
                }else {
                    idKel = "";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_propinsi", idProv);
                map.put("kode_kbpktm", idKota);
                map.put("kode_kecamatan", idKec);
                map.put("kode_kelurahan", idKel);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);

        //loading process dialog
        /*progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data sandi dati/lahir...");
        progressDialog.show();*/
    }

    /*public void UpdatePekerjaan(){
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
    }*/

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
        //UpdatePekerjaan();
        UpdateJobTitle();
    }

    @Override
    public void onPause() {
        super.onPause();

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
        Toast.makeText(hsContext,toastString,Toast.LENGTH_LONG).show();
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

