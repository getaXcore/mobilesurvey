package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
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


public class InputFullFragmentEmpat extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG = InputFullFragmentSatu.class.getSimpleName();
    private String has_contact_person,relationship,province_contact,kab_kodya_contact,
            kecamatan_contact, kelurahan_contact, kodesandidati2_contact,kodepos_contact,
            get_id_order,get_id_surveyor,tam_json_hasil,
            id_province_contact,id_kab_kodya_contact,id_kec_contact,id_kel_contact,zipcode_contact;
    private ArrayList<String> cek_list_has_contact_person,cek_list_relationship,
            cek_list_province_contact, cek_list_kab_kodya_contact,
            cek_list_kecamatan_contact,cek_list_kelurahan_contact
            ,cek_list_id_province_contact,cek_list_id_kab_kodya_contact,cek_list_id_kec_contact,cek_list_id_kel_contact;
    private ArrayAdapter<String> myAdapter_has_contact_person,myAdapter_relationship;
    private int success;
    private Spinner S_has_contact_person,S_relationship;
    private EditText Contact_name,Contact_telephone,Contact_address;
    private DatabaseManager dm;
    private Button b_simpan;
    private SpinnerDialog spinnerDialog_province_contact,spinnerDialog_kab_kodya_contact,
            spinnerDialog_kecamatan_contact,spinnerDialog_kelurahan_contact;
    private TextView S_contact_province,S_contact_kab_or_kodya,S_contact_kecamatan,
            S_contact_kelurahan,Contact_sandi_dati_2,Contact_Postal_code;

    public static String C_has_contact_person,C_Contact_name,C_Contact_address,C_contact_province,
            C_contact_kab_or_kodya,C_contact_kecamatan,C_contact_kelurahan,C_Contact_sandi_dati_2,
            C_Contact_Postal_code,C_Contact_telephone,C_relationship;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_empat, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);

        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        S_has_contact_person    = (Spinner) view.findViewById(R.id.spinner_has_contact_person);
        Contact_name            = (EditText) view.findViewById(R.id.etx_contact_name);
        Contact_address         = (EditText) view.findViewById(R.id.etx_contact_address);
        S_contact_province      = (TextView) view.findViewById(R.id.spinner_contact_province);
        S_contact_kab_or_kodya  = (TextView) view.findViewById(R.id.spinner_contact_kab_or_kodya);
        S_contact_kecamatan     = (TextView) view.findViewById(R.id.spinner_contact_kecamatan);
        S_contact_kelurahan     = (TextView) view.findViewById(R.id.spinner_contact_kelurahan);
        Contact_sandi_dati_2    = (TextView) view.findViewById(R.id.etx_contact_sandi_dati_2);
        Contact_Postal_code     = (TextView) view.findViewById(R.id.etx_contact_postal_code);
        Contact_telephone       = (EditText) view.findViewById(R.id.etx_contact_telephone);
        S_relationship          = (Spinner) view.findViewById(R.id.spinner_relationship);
        Img_check               = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                = (Button) view.findViewById(R.id.bt_simpan);

        TampilHasContactPerson();
        TampilProvinceContact();
        TampilKabKodyaContact();
        TampilKecamatanContact();
        TampilKelurahanContact();
        TampilRelationship();

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_has_contact_person   = String.valueOf(S_has_contact_person.getSelectedItem());
                C_Contact_name         = Contact_name.getText().toString();
                C_Contact_address      = Contact_address.getText().toString();
                C_contact_province     = S_contact_province.getText().toString();
                C_contact_kab_or_kodya = S_contact_kab_or_kodya.getText().toString();
                C_contact_kecamatan    = S_contact_kecamatan.getText().toString();
                C_contact_kelurahan    = S_contact_kelurahan.getText().toString();
                C_Contact_sandi_dati_2 = Contact_sandi_dati_2.getText().toString();
                C_Contact_Postal_code  = Contact_Postal_code.getText().toString();
                C_Contact_telephone    = Contact_telephone.getText().toString();
                C_relationship         = String.valueOf(S_relationship.getSelectedItem());


                /*final String C_has_contact_person   = String.valueOf(S_has_contact_person.getSelectedItem());
                final String C_Contact_name         = Contact_name.getText().toString();
                final String C_Contact_address      = Contact_address.getText().toString();
                final String C_contact_province     = S_contact_province.getText().toString();
                final String C_contact_kab_or_kodya = S_contact_kab_or_kodya.getText().toString();
                final String C_contact_kecamatan    = S_contact_kecamatan.getText().toString();
                final String C_contact_kelurahan    = S_contact_kelurahan.getText().toString();
                final String C_Contact_sandi_dati_2 = Contact_sandi_dati_2.getText().toString();
                final String C_Contact_Postal_code  = Contact_Postal_code.getText().toString();
                final String C_Contact_telephone    = Contact_telephone.getText().toString();
                final String C_relationship         = String.valueOf(S_relationship.getSelectedItem());*/

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey4(get_id_surveyor,get_id_order,C_has_contact_person,
                            C_Contact_name,C_Contact_address,C_contact_province,
                            C_contact_kab_or_kodya,C_contact_kecamatan,C_contact_kelurahan,
                            C_Contact_sandi_dati_2,C_Contact_Postal_code, C_Contact_telephone,
                            C_relationship);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey4(get_id_order,C_has_contact_person,C_Contact_name,
                            C_Contact_address,C_contact_province,C_contact_kab_or_kodya,
                            C_contact_kecamatan,C_contact_kelurahan,C_Contact_sandi_dati_2,
                            C_Contact_Postal_code,C_Contact_telephone,C_relationship);
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
            String t_has_contact_person     = ""+baris.get(77);
            String t_contact_name           = ""+baris.get(78);
            String t_Contact_address        = ""+baris.get(79);
            String t_contact_province       = ""+baris.get(80);
            String t_contact_kab_or_kodya   = ""+baris.get(81);
            String t_contact_kecamatan      = ""+baris.get(82);
            String t_contact_kelurahan      = ""+baris.get(83);
            String t_Contact_sandi_dati_2   = ""+baris.get(84);
            String t_Contact_Postal_code    = ""+baris.get(85);
            String t_contact_telephone      = ""+baris.get(86);
            String t_relationship           = ""+baris.get(87);

            if(t_has_contact_person.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_has_contact_person = myAdapter_has_contact_person.
                    getPosition(t_has_contact_person);
            S_has_contact_person.setSelection(spinner_has_contact_person);

            if(t_relationship.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_relationship = myAdapter_relationship.getPosition(t_relationship);
            S_relationship.setSelection(spinner_relationship);

            if(t_contact_name.equals("null")){
                status_lengkap = status_lengkap+1;
                Contact_name.setText("");
            }else{
                Contact_name.setText(t_contact_name);
            }

            if(t_Contact_address.equals("null")){
                status_lengkap = status_lengkap+1;
                Contact_address.setText("");
            }else{
                Contact_address.setText(t_Contact_address);
            }

            if(t_contact_province.equals("null")){
                status_lengkap = status_lengkap+1;
                S_contact_province.setText("");
            }else{
                S_contact_province.setText(t_contact_province);
            }

            if(t_contact_kab_or_kodya.equals("null")){
                status_lengkap = status_lengkap+1;
                S_contact_kab_or_kodya.setText("");
            }else{
                S_contact_kab_or_kodya.setText(t_contact_kab_or_kodya);
            }

            if(t_contact_kecamatan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_contact_kecamatan.setText("");
            }else{
                S_contact_kecamatan.setText(t_contact_kecamatan);
            }

            if(t_contact_kelurahan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_contact_kelurahan.setText("");
            }else{
                S_contact_kelurahan.setText(t_contact_kelurahan);
            }

            if(t_Contact_sandi_dati_2.equals("null")){
                status_lengkap = status_lengkap+1;
                Contact_sandi_dati_2.setText("");
            }else{
                Contact_sandi_dati_2.setText(t_Contact_sandi_dati_2);
            }

            if(t_Contact_Postal_code.equals("null")){
                status_lengkap = status_lengkap+1;
                Contact_Postal_code.setText("");
            }else{
                Contact_Postal_code.setText(t_Contact_Postal_code);
            }

            if(t_contact_telephone.equals("null")){
                status_lengkap = status_lengkap+1;
                Contact_telephone.setText("");
            }else{
                Contact_telephone.setText(t_contact_telephone);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab4 = dm.ambilBarisTab("TAB 4",get_id_order);
                if (data_tab4.size() < 1) {
                    dm.addRowTab("Contact Person Data","TAB 4", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    public void TampilHasContactPerson(){
        ArrayList<ArrayList<Object>> data_has_contact_person = dm.ambilBarisJsonPilih("Has Contact Person");
        if(data_has_contact_person.size()>0){
            ArrayList<Object> baris = data_has_contact_person.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_has_contact_person = new ArrayList<String>();
                cek_list_has_contact_person.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            has_contact_person = obj.getString("nama_has_contact_person");
                            cek_list_has_contact_person.add(has_contact_person);
                        }
                    }
                }
                myAdapter_has_contact_person = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_has_contact_person);
                myAdapter_has_contact_person.setDropDownViewResource(R.layout.spinner_item);
                S_has_contact_person.setAdapter(myAdapter_has_contact_person);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilRelationship(){
        ArrayList<ArrayList<Object>> data_relationship = dm.ambilBarisJsonPilih("Relationship");
        if(data_relationship.size()>0){
            ArrayList<Object> baris = data_relationship.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_relationship = new ArrayList<String>();
                cek_list_relationship.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            relationship = obj.getString("nama_relationship");
                            cek_list_relationship.add(relationship);
                        }
                    }
                }
                myAdapter_relationship = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_relationship);
                myAdapter_relationship.setDropDownViewResource(R.layout.spinner_item);
                S_relationship.setAdapter(myAdapter_relationship);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilProvinceContact(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_province_contact = new ArrayList<String>();
                            cek_list_province_contact.add("--");

                            cek_list_id_province_contact = new ArrayList<String>();

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
                                        province_contact = obj.getString("propinsi");
                                        if (cek_list_province_contact.contains(province_contact)) {
                                            // true
                                        }else{
                                            cek_list_province_contact.add(province_contact);
                                        }

                                        JSONObject jsonObject = idProvData.getJSONObject(i);
                                        id_province_contact = jsonObject.getString("kode_propinsi");
                                        if (cek_list_id_province_contact.contains(id_province_contact)){

                                        }else {
                                            cek_list_id_province_contact.add(id_province_contact);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_province_contact = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_province_contact,
                                    "Select item");
                            S_contact_province.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_province_contact.showSpinerDialog();
                                }
                            });

                            spinnerDialog_province_contact.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_contact_province.setText(item);
                                    S_contact_province.setTag(cek_list_id_province_contact.get(position-1));
                                    S_contact_kab_or_kodya.setText("");
                                    S_contact_kecamatan.setText("");
                                    S_contact_kelurahan.setText("");
                                    Contact_sandi_dati_2.setText("");
                                    Contact_Postal_code.setText("");
                                    TampilKabKodyaContact();
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

    public void TampilKabKodyaContact(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kab_kodya_contact = new ArrayList<String>();
                            cek_list_kab_kodya_contact.add("--");

                            cek_list_id_kab_kodya_contact = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kotaData = jsonArray.getJSONArray(0);
                                JSONArray idKota = jsonArray.getJSONArray(1);
                                if (kotaData.length() > 0) {
                                    for(int i=0; i<kotaData.length(); i++){
                                        JSONObject obj = kotaData.getJSONObject(i);
                                        kab_kodya_contact = obj.getString("kbpktm");
                                        if (cek_list_kab_kodya_contact.contains(kab_kodya_contact)) {
                                            // true
                                        }else{
                                            cek_list_kab_kodya_contact.add(kab_kodya_contact);
                                        }

                                        JSONObject jsonObject = idKota.getJSONObject(i);
                                        id_kab_kodya_contact = jsonObject.getString("kode_kota");
                                        if (cek_list_id_kab_kodya_contact.contains(id_kab_kodya_contact)){

                                        }else {
                                            cek_list_id_kab_kodya_contact.add(id_kab_kodya_contact);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kab_kodya_contact = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kab_kodya_contact,"Select item");
                            S_contact_kab_or_kodya.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kab_kodya_contact.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kab_kodya_contact.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_contact_kab_or_kodya.setText(item);
                                    S_contact_kab_or_kodya.setTag(cek_list_id_kab_kodya_contact.get(position-1));
                                    S_contact_kecamatan.setText("");
                                    S_contact_kelurahan.setText("");
                                    Contact_sandi_dati_2.setText("");
                                    Contact_Postal_code.setText("");
                                    TampilKecamatanContact();
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idProv;

                if (S_contact_province.getTag() != null){
                    idProv = S_contact_province.getTag().toString();
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
    }

    public void TampilKecamatanContact(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kecamatan_contact = new ArrayList<String>();
                            cek_list_kecamatan_contact.add("--");

                            cek_list_id_kec_contact = new ArrayList<String>();

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
                                        kecamatan_contact = obj.getString("kecamatan");
                                        if (cek_list_kecamatan_contact.contains(kecamatan_contact)) {
                                            // true
                                        }else{
                                            cek_list_kecamatan_contact.add(kecamatan_contact);
                                        }

                                        JSONObject jsonObject = idKecData.getJSONObject(i);
                                        id_kec_contact = jsonObject.getString("kode_kecamatan");
                                        if (cek_list_id_kec_contact.contains(id_kec_contact)){

                                        }else {
                                            cek_list_id_kec_contact.add(id_kec_contact);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kecamatan_contact = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kecamatan_contact,"Select item");
                            S_contact_kecamatan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kecamatan_contact.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kecamatan_contact.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_contact_kecamatan.setText(item);
                                    S_contact_kecamatan.setTag(cek_list_id_kec_contact.get(position-1));
                                    S_contact_kelurahan.setText("");
                                    Contact_sandi_dati_2.setText("");
                                    Contact_Postal_code.setText("");
                                    TampilKelurahanContact();
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idKota;

                if (S_contact_kab_or_kodya.getTag() != null){
                    idKota = S_contact_kab_or_kodya.getTag().toString();
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
    }

    public void TampilKelurahanContact(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kelurahan_contact = new ArrayList<String>();
                            cek_list_kelurahan_contact.add("--");

                            cek_list_id_kel_contact = new ArrayList<String>();

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
                                        kelurahan_contact = obj.getString("kelurahan");
                                        if (cek_list_kelurahan_contact.contains(kelurahan_contact)) {
                                            // true
                                        }else{
                                            cek_list_kelurahan_contact.add(kelurahan_contact);
                                        }

                                        JSONObject jsonObject = idKelData.getJSONObject(i);
                                        id_kel_contact = jsonObject.getString("kode_kelurahan");
                                        if (cek_list_id_kel_contact.contains(id_kel_contact)){

                                        }else {
                                            cek_list_id_kel_contact.add(id_kel_contact);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kelurahan_contact = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kelurahan_contact,"Select item");
                            S_contact_kelurahan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kelurahan_contact.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kelurahan_contact.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_contact_kelurahan.setText(item);
                                    S_contact_kelurahan.setTag(cek_list_id_kel_contact.get(position-1));
                                    TampilSandiDati2KodePosContact();
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idKec;

                if (S_contact_kecamatan.getTag() != null){
                    idKec = S_contact_kecamatan.getTag().toString();
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
    }

    public void TampilSandiDati2KodePosContact(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KODEPOS_SANDIDATI,
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
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        kodepos_contact = obj.getString("kodepos");
                                        zipcode_contact = obj.getString("zipcode");
                                        kodesandidati2_contact = obj.getString("sandidati2");

                                        Contact_Postal_code.setText(kodepos_contact);
                                        Contact_Postal_code.setTag(zipcode_contact);
                                        Contact_sandi_dati_2.setText(kodesandidati2_contact);
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idProv,idKota,idKec,idKel;

                if (S_contact_province.getTag() != null){
                    idProv = S_contact_province.getTag().toString();
                }else {
                    idProv = "";
                }
                if (S_contact_kab_or_kodya.getTag() != null){
                    idKota = S_contact_kab_or_kodya.getTag().toString();
                }else {
                    idKota = "";
                }
                if (S_contact_kecamatan.getTag() != null){
                    idKec = S_contact_kecamatan.getTag().toString();
                }else {
                    idKec = "";
                }
                if (S_contact_kelurahan.getTag() != null){
                    idKel = S_contact_kelurahan.getTag().toString();
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
        UpdateHasContactPerson();
        UpdateRelationship();

        hasil_data();
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
