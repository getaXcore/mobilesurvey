package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;


public class InputFullFragmentSatu extends Fragment implements DatePickerDialog.OnDateSetListener {
    private int status_lengkap;
    private ImageView Img_check;
    private ArrayList<String> cek_list_title,cek_list_marital_status,cek_list_identity_type,
            cek_list_mail_address,cek_list_education,cek_list_sex,cek_list_religion,
            cek_list_province_ktp,cek_list_id_province_ktp,cek_list_kab_kodya_ktp,cek_list_id_kab_kodya_ktp,cek_list_kecamatan_ktp,
            cek_list_id_kecamatan_ktp,cek_list_kelurahan_ktp,cek_list_id_kelurahan_ktp,cek_list_province_home,cek_list_kab_kodya_home,
            cek_list_kecamatan_home,cek_list_kelurahan_home,cek_list_id_province_home,cek_list_id_kab_kodya_home,cek_list_id_kel_home,
            cek_list_id_kec_home;
    private String title,marital_status,identity_type,mail_address,education,sex,religion,
            province_ktp,id_province_ktp,kab_kodya_ktp,id_kab_kodya_ktp,kecamatan_ktp,id_kecamatan_ktp,kelurahan_ktp,id_kelurahan_ktp,
            kodesandidati2_ktp,kodepos_ktp,zipcode_ktp,province_home,kab_kodya_home,kecamatan_home,kelurahan_home,kodesandidati2_home,
            kodepos_home,id_province_home,id_kab_kodya_home,id_kec_home,id_kel_home,zipcode_home;
    private ArrayAdapter<String> myAdapter_title,myAdapter_marital_status,
            myAdapter_identity_type,myAdapter_mail_address,myAdapter_education,myAdapter_sex,
            myAdapter_religion;
    private SpinnerDialog spinnerDialog_province_ktp,spinnerDialog_kab_kodya_ktp,
            spinnerDialog_kecamatan_ktp,spinnerDialog_kelurahan_ktp,spinnerDialog_province_home,
            spinnerDialog_kab_kodya_home,spinnerDialog_kecamatan_home,spinnerDialog_kelurahan_home;
    private static final String TAG = InputFullFragmentSatu.class.getSimpleName();
    private Spinner S_title,S_marital_status,S_identity_type,S_mail_address,S_education,S_sex,
            S_religion;
    private EditText Name,Mother_maiden_name,Npwp_no,Birth_place,Telephone,Telephone_2,
            Nama_panggilan,Identity_no,Stay_length,Handphone_1,Handphone_2,
            Email_stay,Address_ktp,Address_home;
    private TextView S_province_ktp,S_kab_kodya_ktp,S_kecamatan_ktp,S_kelurahan_ktp,
            Sandi_dati_2_ktp,Postal_code_ktp,S_province_home,S_kab_kodya_home,S_kecamatan_home,
            S_kelurahan_home,Sandi_dati_2_home,Postal_code_home,S_birth_date,Sandi_lahir;
    private String get_id_order,get_id_surveyor,get_name,get_identity_type,get_identity_no,
            get_address_home,get_telephone,get_sex,get_handphone_1,tam_json_hasil,real_date;
    private DatabaseManager dm;
    private Button b_simpan;


    public static String C_Name,C_Mother_maiden_name,C_title,C_marital_status,C_identity_type,
            C_Npwp_no,C_Birth_place,C_Birth_date,C_Address_ktp,C_province_ktp,C_id_province_ktp,C_kab_kodya_ktp,
            C_id_kab_kodya_ktp,C_id_kecamatan_ktp,C_kecamatan_ktp,C_id_kelurahan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,
            C_Postal_code_ktp,C_zipcode_ktp,C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,C_kelurahan_home,C_Sandi_dati_2_home,
            C_Postal_code_home,C_mail_address,C_Telephone,C_Telephone_2,C_education,C_sex,
            C_Nama_panggilan,C_Identity_no,C_Sandi_lahir,C_religion,C_Stay_length,C_Handphone_1,
            C_Handphone_2,C_email;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_satu, container, false);

        dm = new DatabaseManager(hsContext);
        get_id_order        = getArguments().getString("id_order");
        get_name            = getArguments().getString("get_name");
        get_identity_type   = getArguments().getString("get_identity_type");
        get_identity_no     = getArguments().getString("get_identity_no");
        get_address_home    = getArguments().getString("get_address_home");
        get_telephone       = getArguments().getString("get_telephone");
        get_sex             = getArguments().getString("get_sex");
        get_handphone_1     = getArguments().getString("get_handphone_1");

        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        Name                = (EditText) view.findViewById(R.id.etx_name);
        Mother_maiden_name  = (EditText) view.findViewById(R.id.etx_mother_maiden_name);
        S_title             = (Spinner) view.findViewById(R.id.spinner_title);
        S_marital_status    = (Spinner) view.findViewById(R.id.spinner_marital_status);
        S_identity_type     = (Spinner) view.findViewById(R.id.spinner_identity_type);
        Npwp_no             = (EditText) view.findViewById(R.id.etx_npwp_no);
        Birth_place         = (EditText) view.findViewById(R.id.etx_birth_place);
        S_birth_date        = (TextView) view.findViewById(R.id.etx_birth_date);
        Address_ktp         = (EditText) view.findViewById(R.id.etx_address_ktp);
        S_province_ktp      = (TextView) view.findViewById(R.id.spinner_province_ktp);
        S_kab_kodya_ktp     = (TextView) view.findViewById(R.id.spinner_kab_or_kodya_ktp);
        S_kecamatan_ktp     = (TextView) view.findViewById(R.id.spinner_kecamatan_ktp);
        S_kelurahan_ktp     = (TextView) view.findViewById(R.id.spinner_kelurahan_ktp);
        Sandi_dati_2_ktp    = (TextView) view.findViewById(R.id.etx_sandi_dati_2_ktp);
        Postal_code_ktp     = (TextView) view.findViewById(R.id.etx_postal_code_ktp);
        Address_home        = (EditText) view.findViewById(R.id.etx_address_home);
        S_province_home     = (TextView) view.findViewById(R.id.spinner_province_home);
        S_kab_kodya_home    = (TextView) view.findViewById(R.id.spinner_kab_or_kodya_home);
        S_kecamatan_home    = (TextView) view.findViewById(R.id.spinner_kecamatan_home);
        S_kelurahan_home    = (TextView) view.findViewById(R.id.spinner_kelurahan_home);
        Sandi_dati_2_home   = (TextView) view.findViewById(R.id.etx_sandi_dati_2_home);
        Postal_code_home    = (TextView) view.findViewById(R.id.etx_postal_code_home);
        S_mail_address      = (Spinner) view.findViewById(R.id.spinner_mail_address);
        S_education         = (Spinner) view.findViewById(R.id.spinner_education);
        S_sex               = (Spinner) view.findViewById(R.id.spinner_sex);
        Nama_panggilan      = (EditText) view.findViewById(R.id.etx_nama_panggilan);
        Identity_no         = (EditText) view.findViewById(R.id.etx_identity_no);
        Sandi_lahir         = (TextView) view.findViewById(R.id.etx_sandi_lahir);
        S_religion          = (Spinner) view.findViewById(R.id.spinner_religion);
        Stay_length         = (EditText) view.findViewById(R.id.etx_stay_length);
        Telephone           = (EditText) view.findViewById(R.id.etx_telephone);
        Telephone_2         = (EditText) view.findViewById(R.id.etx_telephone_2);
        Handphone_1         = (EditText) view.findViewById(R.id.etx_handphone_1);
        Handphone_2         = (EditText) view.findViewById(R.id.etx_handphone_2);
        Email_stay          = (EditText) view.findViewById(R.id.etx_email_stay);
        Img_check           = (ImageView) view.findViewById(R.id.img_check);
        b_simpan            = (Button) view.findViewById(R.id.bt_simpan);

        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                KurangDariDua(Name);
            }
        });

        S_birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogDate();
            }
        });

        TampilTitle();
        TampilMaritalStatus();
        TampilIdentityType();
        TampilProvinceKtp();
        TampilProvinceHome();
        TampilMailAddress();
        TampilEducation();
        TampilSex();
        TampilReligion();
        hasil_data();

        S_marital_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                String tx_marital_status = String.valueOf(S_marital_status.getSelectedItem());
                dm.deleteStatusPernikahanAll(get_id_order);
                dm.addRowStatusPernikahan(get_id_order,tx_marital_status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Name                 = Name.getText().toString();
                C_Mother_maiden_name   = Mother_maiden_name.getText().toString();
                C_title                = String.valueOf(S_title.getSelectedItem());
                C_marital_status       = String.valueOf(S_marital_status.getSelectedItem());
                C_identity_type        = String.valueOf(S_identity_type.getSelectedItem());
                C_Npwp_no              = Npwp_no.getText().toString();
                C_Birth_place          = Birth_place.getText().toString();
                C_Birth_date           = S_birth_date.getText().toString();
                C_Address_ktp          = Address_ktp.getText().toString();
                C_province_ktp         = S_province_ktp.getText().toString();
                C_id_province_ktp      = S_province_ktp.getTag().toString();
                C_kab_kodya_ktp        = S_kab_kodya_ktp.getText().toString();
                C_id_kab_kodya_ktp     = S_kab_kodya_ktp.getTag().toString();
                C_kecamatan_ktp        = S_kecamatan_ktp.getText().toString();
                C_id_kecamatan_ktp     = S_kecamatan_ktp.getTag().toString();
                C_kelurahan_ktp        = S_kelurahan_ktp.getText().toString();
                C_id_kelurahan_ktp     = S_kelurahan_ktp.getTag().toString();
                C_Sandi_dati_2_ktp     = Sandi_dati_2_ktp.getText().toString();
                C_Postal_code_ktp      = Postal_code_ktp.getText().toString();
                C_zipcode_ktp          = Postal_code_ktp.getTag().toString();
                C_Address_home         = Address_home.getText().toString();
                C_province_home        = S_province_home.getText().toString();
                C_kab_kodya_home       = S_kab_kodya_home.getText().toString();
                C_kecamatan_home       = S_kecamatan_home.getText().toString();
                C_kelurahan_home       = S_kelurahan_home.getText().toString();
                C_Sandi_dati_2_home    = Sandi_dati_2_home.getText().toString();
                C_Postal_code_home     = Postal_code_home.getText().toString();
                C_mail_address         = String.valueOf(S_mail_address.getSelectedItem());
                C_Telephone            = Telephone.getText().toString();
                C_Telephone_2          = Telephone_2.getText().toString();
                C_education            = String.valueOf(S_education.getSelectedItem());
                C_sex                  = String.valueOf(S_sex.getSelectedItem());
                C_Nama_panggilan       = Nama_panggilan.getText().toString();
                C_Identity_no          = Identity_no.getText().toString();
                C_Sandi_lahir          = Sandi_lahir.getText().toString();
                C_religion             = String.valueOf(S_religion.getSelectedItem());
                C_Stay_length          = Stay_length.getText().toString();
                C_Handphone_1          = Handphone_1.getText().toString();
                C_Handphone_2          = Handphone_2.getText().toString();
                C_email                = Email_stay.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);
                if (data.size() < 1) {
                    dm.addRowSurvey1(get_id_surveyor,get_id_order,C_Name,C_Mother_maiden_name,
                            C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                            C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                            C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                            C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                            C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                            C_education,C_sex,
                            C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                            C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email);
                    dm.addRowSurvey1Tambahan(get_id_order,C_id_province_ktp,C_id_kab_kodya_ktp,C_id_kecamatan_ktp,C_id_kelurahan_ktp,C_zipcode_ktp);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey1(get_id_order,C_Name,C_Mother_maiden_name,
                            C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                            C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                            C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                            C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                            C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                            C_education,C_sex,
                            C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                            C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email);
                    dm.updateBarisSurvey1Tambahan(get_id_order,C_id_province_ktp,C_id_kab_kodya_ktp,C_id_kecamatan_ktp,C_id_kelurahan_ktp,C_zipcode_ktp);
                    Toast.makeText(hsContext, "Update Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                }
                hasil_data();
            }
        });
        ArrayList<ArrayList<Object>> data_surveyor2 = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor2 = data_surveyor2.get(0);
        get_id_surveyor = baris_surveyor2.get(3).toString();
        return view;
    }

    public boolean KurangDariDua(EditText editText){
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length() <= 2) {
            editText.setError(Html
                    .fromHtml("<font color='red'>Nama Harus Lebih Dari 2</font>"));
            return false;
        }
        return true;
    }

    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);
        //ArrayList<ArrayList<Object>> arrayLists = dm.ambilBarisSurvey1Tambahan(get_id_order);
        ArrayList<Object> arrayList = dm.ambilBarisSurvey1Tbh(get_id_order);
        if (t_data.size() < 1 && arrayList.size() < 1) {
            Name.setText(get_name);
            Address_home.setText(get_address_home);
            Telephone.setText(get_telephone);
            Identity_no.setText(get_identity_no);
            Handphone_1.setText(get_handphone_1);

/*            int spinner_identity_type = myAdapter_identity_type.getPosition(get_identity_type);
            S_identity_type.setSelection(spinner_identity_type);*/

/*            int spinner_sex = myAdapter_sex.getPosition(get_sex);
            S_sex.setSelection(spinner_sex);*/

            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            /*ArrayList<Object> arrayList = arrayLists.get(0);
            String t_id_province_ktp    = ""+arrayList.get(0);
            String t_id_kab_kodya_ktp   = ""+arrayList.get(1);
            String t_id_kec_ktp         = ""+arrayList.get(2);
            String t_id_kel_ktp         = ""+arrayList.get(3);
            String t_zipcode_ktp        = ""+arrayList.get(4);*/
            String t_id_province_ktp    = ""+arrayList.get(0);
            String t_id_kab_kodya_ktp   = ""+arrayList.get(1);
            String t_id_kec_ktp         = ""+arrayList.get(2);
            String t_id_kel_ktp         = ""+arrayList.get(3);
            String t_zipcode_ktp        = ""+arrayList.get(4);

            ArrayList<Object> baris = t_data.get(0);
            String t_nama               = ""+baris.get(2);
            String t_mother             = ""+baris.get(3);
            String t_title              = ""+baris.get(4);
            String t_marital_status     = ""+baris.get(5);
            String t_identity_type      = ""+baris.get(6);
            String t_Npwp_no            = ""+baris.get(7);
            String t_Birth_place        = ""+baris.get(8);
            String t_Birth_date         = ""+baris.get(9);
            String t_Identity_no        = ""+baris.get(10);
            String t_Address_ktp        = ""+baris.get(11);
            String t_province_ktp       = ""+baris.get(12);
            String t_kab_kodya_ktp      = ""+baris.get(13);
            String t_kecamatan_ktp      = ""+baris.get(14);
            String t_kelurahan_ktp      = ""+baris.get(15);
            String t_Sandi_dati_2_ktp   = ""+baris.get(16);
            String t_Postal_code_ktp    = ""+baris.get(17);
            String t_Address_home       = ""+baris.get(18);
            String t_province_home      = ""+baris.get(19);
            String t_kab_kodya_home     = ""+baris.get(20);
            String t_kecamatan_home     = ""+baris.get(21);
            String t_kelurahan_home     = ""+baris.get(22);
            String t_Sandi_dati_2_home  = ""+baris.get(23);
            String t_Postal_code_home   = ""+baris.get(24);
            String t_mail_address       = ""+baris.get(25);
            String t_education          = ""+baris.get(26);
            String t_sex                = ""+baris.get(27);
            String t_Nama_panggilan     = ""+baris.get(28);
            String t_Sandi_lahir        = ""+baris.get(29);
            String t_religion           = ""+baris.get(30);
            String t_Stay_length        = ""+baris.get(31);
            String t_Telephone          = ""+baris.get(32);
            String t_Telephone_2        = ""+baris.get(33);
            String t_Handphone_1        = ""+baris.get(34);
            String t_Handphone_2        = ""+baris.get(35);
            String t_email              = ""+baris.get(36);

            if(t_title.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_title = myAdapter_title.getPosition(t_title);
            S_title.setSelection(spinner_title);

            if(t_marital_status.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_marital_status = myAdapter_marital_status.getPosition(t_marital_status);
            S_marital_status.setSelection(spinner_marital_status);

            if(t_identity_type.equals("--")){
                status_lengkap = status_lengkap+1;
                int spinner_identity_type = myAdapter_identity_type.getPosition(get_identity_type);
                S_identity_type.setSelection(spinner_identity_type);
            }else {
                int spinner_identity_type = myAdapter_identity_type.getPosition(t_identity_type);
                S_identity_type.setSelection(spinner_identity_type);
            }

            int spinner_mail_address = myAdapter_mail_address.getPosition(t_mail_address);
            S_mail_address.setSelection(spinner_mail_address);

            int spinner_education = myAdapter_education.getPosition(t_education);
            S_education.setSelection(spinner_education);

            if(t_sex.equals("--")){
                int spinner_sex = myAdapter_sex.getPosition(get_sex);
                S_sex.setSelection(spinner_sex);
            }else {
                int spinner_sex = myAdapter_sex.getPosition(t_sex);
                S_sex.setSelection(spinner_sex);
            }

            if(t_religion.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_religion = myAdapter_religion.getPosition(t_religion);
            S_religion.setSelection(spinner_religion);

            if(t_nama.equals("null")){
                status_lengkap = status_lengkap+1;
                Name.setText(get_name);
            }else{
                Name.setText(t_nama);
            }

            if(t_mother.equals("null")){
                status_lengkap = status_lengkap+1;
                Mother_maiden_name.setText("");
            }else{
                Mother_maiden_name.setText(t_mother);
            }

            if(t_Npwp_no.equals("null")){
                Npwp_no.setText("");
            }else{
                Npwp_no.setText(t_Npwp_no);
            }

            if(t_Birth_place.equals("null")){
                status_lengkap = status_lengkap+1;
                Birth_place.setText("");
            }else{
                Birth_place.setText(t_Birth_place);
            }

            if(t_Birth_date.equals("null")){
                status_lengkap = status_lengkap+1;
                S_birth_date.setText("");
            }else{
                S_birth_date.setText(t_Birth_date);
            }

            if(t_Address_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                Address_ktp.setText("");
            }else{
                Address_ktp.setText(t_Address_ktp);
            }

            if(t_province_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_ktp.setText("");
            }else{
                S_province_ktp.setText(t_province_ktp);
            }

            if (t_id_province_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_ktp.setTag("");
            }else {
                S_province_ktp.setTag(t_id_province_ktp);
            }

            if(t_kab_kodya_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_ktp.setText("");
            }else{
                S_kab_kodya_ktp.setText(t_kab_kodya_ktp);
            }

            if (t_id_kab_kodya_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_ktp.setTag("");
            }else {
                S_kab_kodya_ktp.setTag(t_id_kab_kodya_ktp);
            }

            if(t_kecamatan_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_ktp.setText("");
            }else{
                S_kecamatan_ktp.setText(t_kecamatan_ktp);
            }

            if (t_id_kec_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_ktp.setTag("");
            }else {
                S_kecamatan_ktp.setTag(t_id_kec_ktp);
            }

            if(t_kelurahan_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_ktp.setText("");
            }else{
                S_kelurahan_ktp.setText(t_kelurahan_ktp);
            }

            if (t_id_kel_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_ktp.setTag("");
            }else{
                S_kelurahan_ktp.setTag(t_id_kel_ktp);
            }

            if(t_Sandi_dati_2_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                Sandi_dati_2_ktp.setText("");
            }else{
                Sandi_dati_2_ktp.setText(t_Sandi_dati_2_ktp);
            }

            if(t_Postal_code_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_ktp.setText("");
            }else{
                Postal_code_ktp.setText(t_Postal_code_ktp);
            }

            if (t_zipcode_ktp.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_ktp.setTag("");
            }else {
                Postal_code_ktp.setTag(t_zipcode_ktp);
            }

            if(t_Address_home.equals("null")){
                status_lengkap = status_lengkap+1;
                Address_home.setText(get_address_home);
            }else{
                Address_home.setText(t_Address_home);
            }

            if(t_province_home.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_home.setText("");
            }else{
                S_province_home.setText(t_province_home);
            }

            if(t_kab_kodya_home.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_home.setText("");
            }else{
                S_kab_kodya_home.setText(t_kab_kodya_home);
            }

            if(t_kecamatan_home.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_home.setText("");
            }else{
                S_kecamatan_home.setText(t_kecamatan_home);
            }

            if(t_kelurahan_home.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_home.setText("");
            }else{
                S_kelurahan_home.setText(t_kelurahan_home);
            }

            if(t_Sandi_dati_2_home.equals("null")){
                status_lengkap = status_lengkap+1;
                Sandi_dati_2_home.setText("");
            }else{
                Sandi_dati_2_home.setText(t_Sandi_dati_2_home);
            }

            if(t_Postal_code_home.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_home.setText("");
            }else{
                Postal_code_home.setText(t_Postal_code_home);
            }

            if(t_Telephone.equals("null")){
                Telephone.setText(get_telephone);
            }else{
                Telephone.setText(t_Telephone);
            }

            if(t_Telephone_2.equals("null")){
                Telephone_2.setText("");
            }else{
                Telephone_2.setText(t_Telephone_2);
            }

            if(t_Nama_panggilan.equals("null")){
                Nama_panggilan.setText("");
            }else{
                Nama_panggilan.setText(t_Nama_panggilan);
            }

            if(t_Identity_no.equals("null")){
                status_lengkap = status_lengkap+1;
                Identity_no.setText(get_identity_no);
            }else{
                Identity_no.setText(t_Identity_no);
            }

            if(t_Sandi_lahir.equals("null")){
                status_lengkap = status_lengkap+1;
                Sandi_lahir.setText("");
            }else{
                Sandi_lahir.setText(t_Sandi_lahir);
            }


            if(t_Stay_length.equals("null")){
                Stay_length.setText("");
            }else{
                Stay_length.setText(t_Stay_length);
            }

            if(t_Handphone_1.equals("null")){
                status_lengkap = status_lengkap+1;
                Handphone_1.setText(get_handphone_1);
            }else{
                Handphone_1.setText(t_Handphone_1);
            }

            if(t_Handphone_2.equals("null")){
                Handphone_2.setText("");
            }else{
                Handphone_2.setText(t_Handphone_2);
            }

            if(t_email.equals("null")){
                Email_stay.setText("");
            }else{
                Email_stay.setText(t_email);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);

                ArrayList<ArrayList<Object>> data_tab1 = dm.ambilBarisTab("TAB 1",get_id_order);
                if (data_tab1.size() < 1) {
                    dm.addRowTab("Personal Data","TAB 1", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    private void setCurrencyCalculate(final AppCompatEditText edt) {
        edt.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edt.removeTextChangedListener(this);

                    Locale local = new Locale("id", "id");
                    String replaceable = String.format("[Rp,.\\s]", NumberFormat.getCurrencyInstance().getCurrency()
                            .getSymbol(local));
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }
                   // calculateAllocation();
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(local);
                    formatter.setMaximumFractionDigits(0);
                    formatter.setParseIntegerOnly(true);
                    String formatted = formatter.format((parsed));

                    String replace = String.format("[\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol(local));
                    String clean = formatted.replaceAll(replace, "");

                    current = formatted;
                    edt.setText(clean);
                    edt.setSelection(clean.length());
                    edt.addTextChangedListener(this);
                }
            }
        });
    }

    public void TampilTitle(){
        ArrayList<ArrayList<Object>> data_title = dm.ambilBarisJsonPilih("Title");
        if(data_title.size()>0){
            ArrayList<Object> baris = data_title.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_title = new ArrayList<String>();
                cek_list_title.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            title = obj.getString("nama_title");
                            cek_list_title.add(title);
                        }
                    }
                }
                myAdapter_title = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_title);
                myAdapter_title.setDropDownViewResource(R.layout.spinner_item);
                S_title.setAdapter(myAdapter_title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilMaritalStatus(){
        ArrayList<ArrayList<Object>> data_marital_status = dm.ambilBarisJsonPilih("Marital Status");
        if(data_marital_status.size()>0){
            ArrayList<Object> baris = data_marital_status.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_marital_status = new ArrayList<String>();
                cek_list_marital_status.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            marital_status = obj.getString("nama_marital_status");
                            cek_list_marital_status.add(marital_status);
                        }
                    }
                }
                myAdapter_marital_status = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_marital_status);
                myAdapter_marital_status.setDropDownViewResource(R.layout.spinner_item);
                S_marital_status.setAdapter(myAdapter_marital_status);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilIdentityType(){
        ArrayList<ArrayList<Object>> data_identity_type = dm.ambilBarisJsonPilih("Identity Type");
        if(data_identity_type.size()>0){
            ArrayList<Object> baris = data_identity_type.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_identity_type = new ArrayList<String>();
                cek_list_identity_type.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            identity_type = obj.getString("nama_identity_type");
                            cek_list_identity_type.add(identity_type);
                        }
                    }
                }
                myAdapter_identity_type = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_identity_type);
                myAdapter_identity_type.setDropDownViewResource(R.layout.spinner_item);
                S_identity_type.setAdapter(myAdapter_identity_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilProvinceKtp(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_province_ktp = new ArrayList<String>();
                            cek_list_province_ktp.add("--");

                            cek_list_id_province_ktp = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray provData = jsonArray.getJSONArray(0);
                                JSONArray idProvData = jsonArray.getJSONArray(1);
                                Log.e("data propinsi",provData.toString());

                                if (provData.length() > 0) {
                                    for(int i=0; i<provData.length(); i++){

                                        //Log.e("nama_provinsi",provData.get(i).toString());

                                        JSONObject obj = provData.getJSONObject(i);
                                        province_ktp = obj.getString("propinsi");

                                        if (cek_list_province_ktp.contains(province_ktp)) {
                                        }else{
                                            cek_list_province_ktp.add(province_ktp);
                                        }

                                        JSONObject jsonObject = idProvData.getJSONObject(i);
                                        id_province_ktp = jsonObject.getString("kode_propinsi");

                                        if (cek_list_id_province_ktp.contains(id_province_ktp)) {
                                        }else{
                                            cek_list_id_province_ktp.add(id_province_ktp);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_province_ktp = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_province_ktp,
                                    "Select item");
                            S_province_ktp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_province_ktp.showSpinerDialog();
                                }
                            });
                            spinnerDialog_province_ktp.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    if(!item.equals("")){
                                        S_province_ktp.setText(item);
                                        S_province_ktp.setTag(cek_list_id_province_ktp.get(position-1));
                                        S_kab_kodya_ktp.setText("");
                                        S_kecamatan_ktp.setText("");
                                        S_kelurahan_ktp.setText("");
                                        Sandi_dati_2_ktp.setText("");
                                        Postal_code_ktp.setText("");
                                        Sandi_lahir.setText("");
                                        TampilKabKodyaKtp();
                                    }

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

    public void TampilKabKodyaKtp(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kab_kodya_ktp = new ArrayList<String>();
                            cek_list_kab_kodya_ktp.add("--");

                            cek_list_id_kab_kodya_ktp = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray kotaData = jsonArray.getJSONArray(0);
                                JSONArray idKotaData = jsonArray.getJSONArray(1);
                                if (kotaData.length() > 0) {
                                    for(int i=0; i<kotaData.length(); i++){
                                        JSONObject obj = kotaData.getJSONObject(i);
                                        kab_kodya_ktp = obj.getString("kbpktm");
                                        if (cek_list_kab_kodya_ktp.contains(kab_kodya_ktp)) {

                                        }else{
                                            cek_list_kab_kodya_ktp.add(kab_kodya_ktp);
                                        }

                                        JSONObject jsonObject = idKotaData.getJSONObject(i);
                                        id_kab_kodya_ktp = jsonObject.getString("kode_kota");
                                        if (cek_list_id_kab_kodya_ktp.contains(id_kab_kodya_ktp)){

                                        }else {
                                            cek_list_id_kab_kodya_ktp.add(id_kab_kodya_ktp);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kab_kodya_ktp = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kab_kodya_ktp,
                                    "Select item");
                            S_kab_kodya_ktp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kab_kodya_ktp.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kab_kodya_ktp.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    Log.e("idKota",cek_list_id_kab_kodya_ktp.get(position));
                                    S_kab_kodya_ktp.setText(item);
                                    S_kab_kodya_ktp.setTag(cek_list_id_kab_kodya_ktp.get(position-1));
                                    S_kecamatan_ktp.setText("");
                                    S_kelurahan_ktp.setText("");
                                    Sandi_dati_2_ktp.setText("");
                                    Postal_code_ktp.setText("");
                                    Sandi_lahir.setText("");
                                    TampilKecamatanKtp();
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
                        //          Toast.makeText(HomeActivity.this, "Tidak Terhubung dengan Server", Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idProvince;

                if (S_province_ktp.getTag() != null){
                    idProvince = S_province_ktp.getTag().toString();
                }else {
                    idProvince = "";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_propinsi",idProvince);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilKecamatanKtp(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kecamatan_ktp = new ArrayList<String>();
                            cek_list_kecamatan_ktp.add("--");

                            cek_list_id_kecamatan_ktp = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray kecData = jsonArray.getJSONArray(0);
                                JSONArray idKecData = jsonArray.getJSONArray(1);
                                if (kecData.length() > 0) {
                                    for(int i=0; i<kecData.length(); i++){
                                        JSONObject obj = kecData.getJSONObject(i);
                                        kecamatan_ktp = obj.getString("kecamatan");
                                        if (cek_list_kecamatan_ktp.contains(kecamatan_ktp)) {

                                        }else{
                                            cek_list_kecamatan_ktp.add(kecamatan_ktp);
                                        }

                                        JSONObject jsonObject = idKecData.getJSONObject(i);
                                        id_kecamatan_ktp = jsonObject.getString("kode_kecamatan");
                                        if (cek_list_id_kecamatan_ktp.contains(id_kecamatan_ktp)) {

                                        }else{
                                            cek_list_id_kecamatan_ktp.add(id_kecamatan_ktp);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kecamatan_ktp = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kecamatan_ktp,
                                    "Select item");
                            S_kecamatan_ktp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kecamatan_ktp.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kecamatan_ktp.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kecamatan_ktp.setText(item);
                                    S_kecamatan_ktp.setTag(cek_list_id_kecamatan_ktp.get(position-1));
                                    S_kelurahan_ktp.setText("");
                                    Sandi_dati_2_ktp.setText("");
                                    Postal_code_ktp.setText("");
                                    Sandi_lahir.setText("");
                                    TampilKelurahanKtp();
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

                if (S_kab_kodya_ktp.getTag() != null){
                    idKota = S_kab_kodya_ktp.getTag().toString();
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

    public void TampilKelurahanKtp(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kelurahan_ktp = new ArrayList<String>();
                            cek_list_kelurahan_ktp.add("--");

                            cek_list_id_kelurahan_ktp = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray kelData = jsonArray.getJSONArray(0);
                                JSONArray idKelData = jsonArray.getJSONArray(1);
                                if (kelData.length() > 0) {
                                    for(int i=0; i<kelData.length(); i++){
                                        JSONObject obj = kelData.getJSONObject(i);
                                        kelurahan_ktp = obj.getString("kelurahan");
                                        if (cek_list_kelurahan_ktp.contains(kelurahan_ktp)) {

                                        }else{
                                            cek_list_kelurahan_ktp.add(kelurahan_ktp);
                                        }

                                        JSONObject jsonObject = idKelData.getJSONObject(i);
                                        id_kelurahan_ktp = jsonObject.getString("kode_kelurahan");
                                        if (cek_list_id_kelurahan_ktp.contains(id_kelurahan_ktp)) {

                                        }else{
                                            cek_list_id_kelurahan_ktp.add(id_kelurahan_ktp);
                                        }
                                    }
                                }
                            }

                            spinnerDialog_kelurahan_ktp = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kelurahan_ktp,
                                    "Select item");
                            S_kelurahan_ktp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kelurahan_ktp.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kelurahan_ktp.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kelurahan_ktp.setText(item);
                                    S_kelurahan_ktp.setTag(cek_list_id_kelurahan_ktp.get(position-1));
                                    TampilSandiDati2KodePosKtp();
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

                if (S_kecamatan_ktp.getTag() != null){
                    idKec = S_kecamatan_ktp.getTag().toString();
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


    public void TampilSandiDati2KodePosKtp(){
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
                                        kodepos_ktp = obj.getString("kodepos");
                                        zipcode_ktp = obj.getString("zipcode");
                                        kodesandidati2_ktp = obj.getString("sandidati2");

                                        Postal_code_ktp.setText(kodepos_ktp);
                                        Postal_code_ktp.setTag(zipcode_ktp);
                                        Sandi_dati_2_ktp.setText(kodesandidati2_ktp);
                                        Sandi_lahir.setText(kodesandidati2_ktp);
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

                if (S_province_ktp.getTag() != null){
                    idProv = S_province_ktp.getTag().toString();
                }else {
                    idProv = "";
                }
                if (S_kab_kodya_ktp.getTag() != null){
                    idKota = S_kab_kodya_ktp.getTag().toString();
                }else {
                    idKota = "";
                }
                if (S_kecamatan_ktp.getTag() != null){
                    idKec = S_kecamatan_ktp.getTag().toString();
                }else {
                    idKec = "";
                }
                if (S_kelurahan_ktp.getTag() != null){
                    idKel = S_kelurahan_ktp.getTag().toString();
                }else {
                    idKel = "";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_propinsi", idProv);
                map.put("kode_kbpktm", idKota);
                map.put("kode_kecamatan", idKec);
                map.put("kode_kelurahan", idKel);
                /*Log.e("kode_propinsi",S_province_ktp.getTag().toString());
                Log.e("kode_kbpktm", S_kab_kodya_ktp.getTag().toString());
                Log.e("kode_kecamatan", S_kecamatan_ktp.getTag().toString());
                Log.e("kode_kelurahan", S_kelurahan_ktp.getTag().toString());*/

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilProvinceHome(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_province_home = new ArrayList<String>();
                            cek_list_province_home.add("--");

                            cek_list_id_province_home = new ArrayList<String>();

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
                                        province_home = obj.getString("propinsi");
                                        if (cek_list_province_home.contains(province_home)) {

                                        }else{
                                            cek_list_province_home.add(province_home);
                                        }

                                        JSONObject jsonObject = idProvData.getJSONObject(i);
                                        id_province_home = jsonObject.getString("kode_propinsi");
                                        if (cek_list_id_province_home.contains(id_province_home)){

                                        }else {
                                            cek_list_id_province_home.add(id_province_home);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_province_home = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_province_home,
                                    "Select item");
                            S_province_home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_province_home.showSpinerDialog();
                                }
                            });
                            spinnerDialog_province_home.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_province_home.setText(item);
                                    S_province_home.setTag(cek_list_id_province_home.get(position-1));
                                    S_kab_kodya_home.setText("");
                                    S_kecamatan_home.setText("");
                                    S_kelurahan_home.setText("");
                                    Sandi_dati_2_home.setText("");
                                    Postal_code_home.setText("");
                                    TampilKabKodyaHome();
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

    public void TampilKabKodyaHome(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kab_kodya_home = new ArrayList<String>();
                            cek_list_kab_kodya_home.add("--");

                            cek_list_id_kab_kodya_home = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kotaData  = jsonArray.getJSONArray(0);
                                JSONArray idKotaData = jsonArray.getJSONArray(1);
                                if (kotaData.length() > 0) {
                                    for(int i=0; i<kotaData.length(); i++){
                                        JSONObject obj = kotaData.getJSONObject(i);
                                        kab_kodya_home = obj.getString("kbpktm");
                                        if (cek_list_kab_kodya_home.contains(kab_kodya_home)) {

                                        }else{
                                            cek_list_kab_kodya_home.add(kab_kodya_home);
                                        }

                                        JSONObject jsonObject = idKotaData.getJSONObject(i);
                                        id_kab_kodya_home = jsonObject.getString("kode_kota");
                                        if (cek_list_id_kab_kodya_home.contains(id_kab_kodya_home)){

                                        }else {
                                            cek_list_id_kab_kodya_home.add(id_kab_kodya_home);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kab_kodya_home = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kab_kodya_home,"Select item");
                            S_kab_kodya_home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kab_kodya_home.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kab_kodya_home.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kab_kodya_home.setText(item);
                                    S_kab_kodya_home.setTag(cek_list_id_kab_kodya_home.get(position-1));
                                    S_kecamatan_home.setText("");
                                    S_kelurahan_home.setText("");
                                    Sandi_dati_2_home.setText("");
                                    Postal_code_home.setText("");
                                    TampilKecamatanHome();
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
                map.put("kode_propinsi", S_province_home.getTag().toString());

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilKecamatanHome(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kecamatan_home = new ArrayList<String>();
                            cek_list_kecamatan_home.add("--");

                            cek_list_id_kec_home = new ArrayList<String>();

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
                                        kecamatan_home = obj.getString("kecamatan");
                                        if (cek_list_kecamatan_home.contains(kecamatan_home)) {

                                        }else{
                                            cek_list_kecamatan_home.add(kecamatan_home);
                                        }

                                        JSONObject jsonObject = idKecData.getJSONObject(i);
                                        id_kec_home = jsonObject.getString("kode_kecamatan");
                                        if (cek_list_id_kec_home.contains(id_kec_home)){

                                        }else {
                                            cek_list_id_kec_home.add(id_kec_home);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kecamatan_home = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_kecamatan_home,"Select item");
                            S_kecamatan_home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kecamatan_home.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kecamatan_home.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kecamatan_home.setText(item);
                                    S_kecamatan_home.setTag(cek_list_id_kec_home.get(position-1));
                                    S_kelurahan_home.setText("");
                                    Sandi_dati_2_home.setText("");
                                    Postal_code_home.setText("");
                                    TampilKelurahanHome();
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
                map.put("kode_kbpktm", S_kab_kodya_home.getTag().toString());

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilKelurahanHome(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kelurahan_home = new ArrayList<String>();
                            cek_list_kelurahan_home.add("--");

                            cek_list_id_kel_home = new ArrayList<String>();

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
                                        kelurahan_home = obj.getString("kelurahan");
                                        if (cek_list_kelurahan_home.contains(kelurahan_home)) {
                                            // true
                                        }else{
                                            cek_list_kelurahan_home.add(kelurahan_home);
                                        }

                                        JSONObject jsonObject = idKelData.getJSONObject(i);
                                        id_kel_home = jsonObject.getString("kode_kelurahan");
                                        if (cek_list_id_kel_home.contains(id_kel_home)){

                                        }else {
                                            cek_list_id_kel_home.add(id_kel_home);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kelurahan_home = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kelurahan_home,
                                    "Select item");
                            S_kelurahan_home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kelurahan_home.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kelurahan_home.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kelurahan_home.setText(item);
                                    S_kelurahan_home.setTag(cek_list_id_kel_home.get(position-1));
                                    TampilSandiDati2KodePosHome();
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
                map.put("kode_kecamatan", S_kecamatan_home.getTag().toString());

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilSandiDati2KodePosHome(){
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
                                        kodepos_home = obj.getString("kodepos");
                                        zipcode_home = obj.getString("zipcode");
                                        kodesandidati2_home = obj.getString("sandidati2");

                                        Postal_code_home.setText(kodepos_home);
                                        Postal_code_home.setTag(zipcode_home);
                                        Sandi_dati_2_home.setText(kodesandidati2_home);
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
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("kode_propinsi", S_province_home.getTag().toString());
                map.put("kode_kbpktm", S_kab_kodya_home.getTag().toString());
                map.put("kode_kecamatan", S_kecamatan_home.getTag().toString());
                map.put("kode_kelurahan", S_kelurahan_home.getTag().toString());

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void TampilMailAddress(){
        ArrayList<ArrayList<Object>> data_mail_address = dm.ambilBarisJsonPilih("Mail Address");
        if(data_mail_address.size()>0){
            ArrayList<Object> baris = data_mail_address.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_mail_address = new ArrayList<String>();
                cek_list_mail_address.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            mail_address = obj.getString("nama_mail_address");
                            cek_list_mail_address.add(mail_address);
                        }
                    }
                }
                myAdapter_mail_address = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_mail_address);
                myAdapter_mail_address.setDropDownViewResource(R.layout.spinner_item);
                S_mail_address.setAdapter(myAdapter_mail_address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilEducation(){
        ArrayList<ArrayList<Object>> data_education = dm.ambilBarisJsonPilih("Education");
        if(data_education.size()>0){
            ArrayList<Object> baris = data_education.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_education = new ArrayList<String>();
                cek_list_education.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            education = obj.getString("nama_education");
                            cek_list_education.add(education);
                        }
                    }
                }
                myAdapter_education = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_education);
                myAdapter_education.setDropDownViewResource(R.layout.spinner_item);
                S_education.setAdapter(myAdapter_education);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilSex(){
        ArrayList<ArrayList<Object>> data_sex = dm.ambilBarisJsonPilih("Sex");
        if(data_sex.size()>0){
            ArrayList<Object> baris = data_sex.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_sex = new ArrayList<String>();
                cek_list_sex.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            sex = obj.getString("nama_sex");
                            cek_list_sex.add(sex);
                        }
                    }
                }
                myAdapter_sex = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_sex);
                myAdapter_sex.setDropDownViewResource(R.layout.spinner_item);
                S_sex.setAdapter(myAdapter_sex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilReligion(){
        ArrayList<ArrayList<Object>> data_religion = dm.ambilBarisJsonPilih("Religion");
        if(data_religion.size()>0){
            ArrayList<Object> baris = data_religion.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_religion = new ArrayList<String>();
                cek_list_religion.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            religion = obj.getString("nama_religion");
                            cek_list_religion.add(religion);
                        }
                    }
                }
                myAdapter_religion = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_religion);
                myAdapter_religion.setDropDownViewResource(R.layout.spinner_item);
                S_religion.setAdapter(myAdapter_religion);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
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

    public void OpenDialogDate(){
        String tanggal = real_date;
        Calendar maxdate = Calendar.getInstance();
        maxdate.add(Calendar.YEAR, -16);
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
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, setDate.get(DateTimeFieldType.year()) - 16,
                setDate.get(DateTimeFieldType.monthOfYear()) - 1 ,
                setDate.get(DateTimeFieldType.dayOfMonth()));
        dpd.setMaxDate(maxdate);
        dpd.setAccentColor(ContextCompat.getColor(hsContext, R.color.colorPrimary));
        dpd.setTitle("Tanggal Lahir");
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        DateTime setDate  = new DateTime(cal.getTime());
        try {
            real_date     = printDateByFormat(setDate, "yyyy-MM-dd");
            S_birth_date.setText(real_date);
        }catch (NullPointerException pe){
            pe.printStackTrace();
        }
    }

    public static String printDateByFormat(DateTime dates, String formatDates){
        DateTimeFormatter fmt = DateTimeFormat.forPattern(formatDates);
        return fmt.print(dates);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateTitle();
        UpdateMaritalstatus();
        UpdateIdentityType();
        UpdateMailAddress();
        UpdateEducation();
        UpdateSex();
        UpdateReligion();
    }


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(hsContext, "aa 1",Toast.LENGTH_LONG).show();
        if (S_province_ktp.getTag() != null){
            C_id_province_ktp      = S_province_ktp.getTag().toString();
        }else {
            C_id_province_ktp      = "";
        }
        if (S_kab_kodya_ktp.getTag() != null){
            C_id_kab_kodya_ktp     = S_kab_kodya_ktp.getTag().toString();
        }else {
            C_id_kab_kodya_ktp     = "";
        }
        if (S_kecamatan_ktp.getTag() != null){
            C_id_kecamatan_ktp     = S_kecamatan_ktp.getTag().toString();
        }else {
            C_id_kecamatan_ktp     = "";
        }
        if (S_kelurahan_ktp.getTag() != null){
            C_id_kelurahan_ktp     = S_kelurahan_ktp.getTag().toString();
        }else {
            C_id_kelurahan_ktp     = "";
        }
        if (Postal_code_ktp.getTag() != null){
            C_zipcode_ktp          = Postal_code_ktp.getTag().toString();
        }else {
            C_zipcode_ktp          = "";
        }


        C_Name                 = Name.getText().toString();
        C_Mother_maiden_name   = Mother_maiden_name.getText().toString();
        C_title                = String.valueOf(S_title.getSelectedItem());
        C_marital_status       = String.valueOf(S_marital_status.getSelectedItem());
        C_identity_type        = String.valueOf(S_identity_type.getSelectedItem());
        C_Npwp_no              = Npwp_no.getText().toString();
        C_Birth_place          = Birth_place.getText().toString();
        C_Birth_date           = S_birth_date.getText().toString();
        C_Address_ktp          = Address_ktp.getText().toString();
        C_province_ktp         = S_province_ktp.getText().toString();
        C_kab_kodya_ktp        = S_kab_kodya_ktp.getText().toString();
        C_kecamatan_ktp        = S_kecamatan_ktp.getText().toString();
        C_kelurahan_ktp        = S_kelurahan_ktp.getText().toString();
        C_Sandi_dati_2_ktp     = Sandi_dati_2_ktp.getText().toString();
        C_Postal_code_ktp      = Postal_code_ktp.getText().toString();
        C_Address_home         = Address_home.getText().toString();
        C_province_home        = S_province_home.getText().toString();
        C_kab_kodya_home       = S_kab_kodya_home.getText().toString();
        C_kecamatan_home       = S_kecamatan_home.getText().toString();
        C_kelurahan_home       = S_kelurahan_home.getText().toString();
        C_Sandi_dati_2_home    = Sandi_dati_2_home.getText().toString();
        C_Postal_code_home     = Postal_code_home.getText().toString();
        C_mail_address         = String.valueOf(S_mail_address.getSelectedItem());
        C_Telephone            = Telephone.getText().toString();
        C_Telephone_2          = Telephone_2.getText().toString();
        C_education            = String.valueOf(S_education.getSelectedItem());
        C_sex                  = String.valueOf(S_sex.getSelectedItem());
        C_Nama_panggilan       = Nama_panggilan.getText().toString();
        C_Identity_no          = Identity_no.getText().toString();
        C_Sandi_lahir          = Sandi_lahir.getText().toString();
        C_religion             = String.valueOf(S_religion.getSelectedItem());
        C_Stay_length          = Stay_length.getText().toString();
        C_Handphone_1          = Handphone_1.getText().toString();
        C_Handphone_2          = Handphone_2.getText().toString();
        C_email                = Email_stay.getText().toString();

        ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);
        if (data.size() < 1) {
            dm.addRowSurvey1(get_id_surveyor,get_id_order,C_Name,C_Mother_maiden_name,
                    C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                    C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                    C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                    C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                    C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                    C_education,C_sex,
                    C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                    C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email);
            dm.addRowSurvey1Tambahan(get_id_order,C_id_province_ktp,C_id_kab_kodya_ktp,C_id_kecamatan_ktp,C_kelurahan_ktp,C_zipcode_ktp);
            /*Toast.makeText(hsContext, "Simpan Sementara",
                    Toast.LENGTH_LONG).show();*/
        } else {
            dm.updateBarisSurvey1(get_id_order,C_Name,C_Mother_maiden_name,
                    C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                    C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                    C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                    C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                    C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                    C_education,C_sex,
                    C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                    C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email);
            dm.updateBarisSurvey1Tambahan(get_id_order,C_id_province_ktp,C_id_kab_kodya_ktp,C_id_kecamatan_ktp,C_kelurahan_ktp,C_zipcode_ktp);
            /*Toast.makeText(hsContext,"Update Simpan Sementara", Toast.LENGTH_LONG).show();*/
        }
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
