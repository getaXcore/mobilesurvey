package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.ximpli.library.datetimepicker.date.DatePickerDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;


public class InputFullFragmentTiga extends Fragment implements DatePickerDialog.OnDateSetListener {
    private ImageView Img_check;
    private int status_lengkap;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG = InputFullFragmentTiga.class.getSimpleName();
    private String spouse_title,spouse_identity_type,spouse_religion,spouse_pekerjaan,
            spouse_job_title, spouse_sex,province_spouse,id_province_spouse,id_kab_kodya_spouse,kab_kodya_spouse,
            kecamatan_spouse,id_kecamatan_spouse,kelurahan_spouse,id_kelurahan_spouse,kodesandidati2_spouse,
            kodepos_spouse,zipcode_spouse,tam_json_hasil,get_id_order,
            spouse_tipe_pekerjaan,spouse_tipe_pekerjaan_code,spouse_jenis_pekerjaan,spouse_jenis_pekerjaan_code,
            get_id_surveyor,real_date;
    private int success;
    private ArrayList<String> cek_list_spouse_title,cek_list_spouse_identity_type,
            cek_list_spouse_religion, cek_list_spouse_pekerjaan,cek_list_spouse_job_title,
            cek_list_spouse_sex,cek_list_province_spouse, cek_list_id_province_spouse,cek_list_kab_kodya_spouse,
            cek_list_id_kab_kodya_spouse,cek_list_id_kecamatan_spouse,cek_list_id_kelurahan_spouse,
            cek_list_kecamatan_spouse,cek_list_kelurahan_spouse,
            cek_list_tipe_pekerjaan,cek_list_tipe_pekerjaan_code,cek_list_jenis_pekerjan,cek_list_jenis_pekerjaan_code;
    private ArrayAdapter<String> myAdapter_spouse_title,myAdapter_spouse_identity_type,
            myAdapter_spouse_religion,myAdapter_spouse_pekerjaan,myAdapter_spouse_job_title,
            myAdapter_spouse_sex;
    private EditText Spouse_name,Spouse_no_handphone,
            Spouse_company_name, Spouse_company_telephone,Spouse_line_of_business,Spouse_identity_no,
            Spouse_birth_place_or_sandi_lahir,Spouse_fax,Spouse_address,
            Spouse_company_address;
    private Spinner S_spouse_title,S_spouse_identity_type,S_spouse_religion,
            S_spouse_occupation_or_pekerjaan, S_spouse_job_title,S_spouse_sex;
    private SpinnerDialog spinnerDialog_province_spouse,spinnerDialog_kab_kodya_spouse,
            spinnerDialog_kecamatan_spouse,spinnerDialog_kelurahan_spouse,spinnerDialog_tipe_pekerjaan,spinnerDialog_jenis_pekerjaan;
    private TextView S_province_spouse,S_kab_kodya_spouse,S_kecamatan_spouse,S_kelurahan_spouse,
            Sandi_dati_2_spouse,Postal_code_spouse,Spouse_date_of_birth,S_Tipe_pekerjaan_spouse,S_Jenis_pekerjaan_spouse,S_Tipe_pekerjaan_spouse_code,S_Jenis_pekerjaan_spouse_code;
    private DatabaseManager dm;
    private Button b_simpan;
    private RelativeLayout data_spouse_data,data_spouse_data_not;
    private ImageView img_single_bg;
    private RelativeLayout box_text_pesan;

    public static String C_Spouse_name,C_spouse_title,C_spouse_identity_type,
            C_Spouse_birth_place_or_sandi_lahir,C_spouse_religion,C_Spouse_address,
            C_province_spouse,C_id_province_spouse,C_kab_kodya_spouse,C_id_kab_kodya_spouse,
            C_id_kecamatan_spouse,C_kecamatan_spouse,C_kelurahan_spouse,C_id_kelurahan_spouse,
            C_Sandi_dati_2_spouse,C_Postal_code_spouse,C_zipcode_spouse,C_Spouse_no_handphone,
            C_spouse_occupation_or_pekerjaan,C_Spouse_company_name,C_Spouse_company_address,
            C_Spouse_company_telephone,C_Spouse_line_of_business,C_spouse_job_title,C_spouse_sex,
            C_Spouse_identity_no,C_Spouse_date_of_birth,C_Spouse_fax,C_Spouse_tipe_pekerjaan,C_spouse_jenis_pekerjaan,
            C_Spouse_tipe_pekerjaan_code,C_Spouse_jenis_pekerjaan_code;
    private Context hsContext;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_tiga, container, false);

        dm = new DatabaseManager(hsContext);
        get_id_order = getArguments().getString("id_order");

        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        Spouse_name                         = (EditText) view.findViewById(R.id.etx_spouse_name);
        S_spouse_title                      = (Spinner) view.findViewById(R.id.spinner_spouse_title);
        S_spouse_identity_type              = (Spinner) view.findViewById(R.id.spinner_spouse_identity_type);
        Spouse_birth_place_or_sandi_lahir   = (EditText) view.findViewById(R.id.etx_spouse_birth_place_or_sandi_lahir);
        S_spouse_religion                   = (Spinner) view.findViewById(R.id.spinner_spouse_religion);
        Spouse_address                      = (EditText) view.findViewById(R.id.etx_spouse_address);
        S_province_spouse                   = (TextView) view.findViewById(R.id.spinner_spouse_province);
        S_kab_kodya_spouse                  = (TextView) view.findViewById(R.id.spinner_spouse_kab_or_kodya);
        S_kecamatan_spouse                  = (TextView) view.findViewById(R.id.spinner_spouse_kecamatan);
        S_kelurahan_spouse                  = (TextView) view.findViewById(R.id.spinner_spouse_kelurahan);
        Sandi_dati_2_spouse                 = (TextView) view.findViewById(R.id.etx_spouse_sandi_dati_2);
        Postal_code_spouse                  = (TextView) view.findViewById(R.id.etx_spouse_postal_code);
        Spouse_no_handphone                 = (EditText) view.findViewById(R.id.etx_spouse_no_handphone);
        //S_spouse_occupation_or_pekerjaan    = (Spinner) view.findViewById(R.id.spinner_spouse_occupation_or_pekerjaan);
        S_Tipe_pekerjaan_spouse             = (TextView) view.findViewById(R.id.spinner_tipe_pekerjaan_spouse);
        S_Jenis_pekerjaan_spouse            = (TextView) view.findViewById(R.id.spinner_pekerjaan_spouse);
        Spouse_company_name                 = (EditText) view.findViewById(R.id.etx_spouse_company_name);
        Spouse_company_address              = (EditText) view.findViewById(R.id.etx_spouse_company_address);
        Spouse_company_telephone            = (EditText) view.findViewById(R.id.etx_spouse_company_telephone);
        Spouse_line_of_business             = (EditText) view.findViewById(R.id.etx_spouse_line_of_business);
        S_spouse_job_title                  = (Spinner) view.findViewById(R.id.spinner_spouse_job_title);
        S_spouse_sex                        = (Spinner) view.findViewById(R.id.spinner_spouse_sex);
        Spouse_identity_no                  = (EditText) view.findViewById(R.id.etx_spouse_identity_no);
        Spouse_date_of_birth                = (TextView) view.findViewById(R.id.etx_spouse_date_of_birth);
        Spouse_fax                          = (EditText) view.findViewById(R.id.etx_spouse_fax);
        Img_check                           = (ImageView) view.findViewById(R.id.img_check);
        data_spouse_data                    = (RelativeLayout) view.findViewById(R.id.data_spouse_data);
        data_spouse_data_not                = (RelativeLayout) view.findViewById(R.id.cek_data_spouse_data);
        img_single_bg                       = (ImageView) view.findViewById(R.id.img_single_bg);
        box_text_pesan                      = (RelativeLayout) view.findViewById(R.id.box_text_pesan);
        b_simpan                            = (Button) view.findViewById(R.id.bt_simpan);

        TampilSpouseTitle();
        TampilSpouseIdentityType();
        TampilSpouseReligion();
        TampilProvinceSpouse();
        TampilKabKodyaSpouse();
        TampilKecamatanSpouse();
        TampilKelurahanSpouse();
        //TampilSpousePekerjaan();
        TampilTipePekerjaan();
        TampilJenisPekerjaan();
        TampilSpouseJobTitle();
        TampilSpouseSex();

        hasil_data();

        Spouse_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogDate();
            }
        });

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Spouse_name                          = Spouse_name.getText().toString();
                C_spouse_title                         = String.valueOf(S_spouse_title.getSelectedItem());
                C_spouse_identity_type                 = String.valueOf(S_spouse_identity_type.getSelectedItem());
                C_Spouse_birth_place_or_sandi_lahir    = Spouse_birth_place_or_sandi_lahir.getText().toString();
                C_spouse_religion                      = String.valueOf(S_spouse_religion.getSelectedItem());
                C_Spouse_address                       = Spouse_address.getText().toString();
                C_province_spouse                      = S_province_spouse.getText().toString();
                C_id_province_spouse                   = S_province_spouse.getTag().toString();
                C_kab_kodya_spouse                     = S_kab_kodya_spouse.getText().toString();
                C_id_kab_kodya_spouse                  = S_kab_kodya_spouse.getTag().toString();
                C_kecamatan_spouse                     = S_kecamatan_spouse.getText().toString();
                C_id_kecamatan_spouse                  = S_kecamatan_spouse.getTag().toString();
                C_kelurahan_spouse                     = S_kelurahan_spouse.getText().toString();
                C_id_kelurahan_spouse                  = S_kelurahan_spouse.getTag().toString();
                C_Sandi_dati_2_spouse                  = Sandi_dati_2_spouse.getText().toString();
                C_Postal_code_spouse                   = Postal_code_spouse.getText().toString();
                C_zipcode_spouse                       = Postal_code_spouse.getTag().toString();
                C_Spouse_no_handphone                  = Spouse_no_handphone.getText().toString();
                //C_spouse_occupation_or_pekerjaan       = String.valueOf(S_spouse_occupation_or_pekerjaan.getSelectedItem());
                C_Spouse_tipe_pekerjaan                = S_Tipe_pekerjaan_spouse.getText().toString();
                C_Spouse_tipe_pekerjaan_code           = S_Tipe_pekerjaan_spouse.getTag().toString();
                C_spouse_jenis_pekerjaan               = S_Jenis_pekerjaan_spouse.getText().toString();
                C_Spouse_jenis_pekerjaan_code          = S_Jenis_pekerjaan_spouse.getTag().toString();
                C_Spouse_company_name                  = Spouse_company_name.getText().toString();
                C_Spouse_company_address               = Spouse_company_address.getText().toString();
                C_Spouse_company_telephone             = Spouse_company_telephone.getText().toString();
                C_Spouse_line_of_business              = Spouse_line_of_business.getText().toString();
                C_spouse_job_title                     = String.valueOf(S_spouse_job_title.getSelectedItem());
                C_spouse_sex                           = String.valueOf(S_spouse_sex.getSelectedItem());
                C_Spouse_identity_no                   = Spouse_identity_no.getText().toString();
                C_Spouse_date_of_birth                 = Spouse_date_of_birth.getText().toString();
                C_Spouse_fax                           = Spouse_fax.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                ArrayList<Object> dataTambahan = dm.ambilBarisSurvey3Tbh(get_id_order);
                if (data.size() < 1) {
                    dm.addRowSurvey3(get_id_surveyor,get_id_order,C_Spouse_name,C_spouse_title,
                            C_spouse_identity_type,C_Spouse_identity_no,
                            C_Spouse_birth_place_or_sandi_lahir,C_spouse_religion,C_Spouse_address,
                            C_province_spouse,C_kab_kodya_spouse,C_kecamatan_spouse,
                            C_kelurahan_spouse,C_Sandi_dati_2_spouse,C_Postal_code_spouse,
                            C_Spouse_no_handphone,C_spouse_occupation_or_pekerjaan,
                            C_Spouse_company_name,C_Spouse_company_address,
                            C_Spouse_company_telephone,C_Spouse_line_of_business,
                            C_spouse_job_title,C_spouse_sex,C_Spouse_date_of_birth,C_Spouse_fax,
                            C_Spouse_tipe_pekerjaan,C_Spouse_tipe_pekerjaan_code,C_spouse_jenis_pekerjaan,C_Spouse_jenis_pekerjaan_code);
                    dm.addRowSurvey3Tambahan(get_id_order,C_id_province_spouse,C_id_kab_kodya_spouse,C_id_kecamatan_spouse,C_id_kelurahan_spouse,C_zipcode_spouse);
                    Toast.makeText(hsContext, "Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey3(get_id_order,C_Spouse_name,C_spouse_title,
                            C_spouse_identity_type,C_Spouse_identity_no,
                            C_Spouse_birth_place_or_sandi_lahir,C_spouse_religion,C_Spouse_address,
                            C_province_spouse,C_kab_kodya_spouse, C_kecamatan_spouse,
                            C_kelurahan_spouse,C_Sandi_dati_2_spouse, C_Postal_code_spouse,
                            C_Spouse_no_handphone, C_spouse_occupation_or_pekerjaan,
                            C_Spouse_company_name, C_Spouse_company_address,
                            C_Spouse_company_telephone, C_Spouse_line_of_business,
                            C_spouse_job_title, C_spouse_sex, C_Spouse_date_of_birth, C_Spouse_fax,
                            C_Spouse_tipe_pekerjaan,C_Spouse_tipe_pekerjaan_code,C_spouse_jenis_pekerjaan,C_Spouse_jenis_pekerjaan_code);
                    if (dataTambahan.size() < 1){
                        dm.addRowSurvey3Tambahan(get_id_order,C_id_province_spouse,C_id_kab_kodya_spouse,C_id_kecamatan_spouse,C_id_kelurahan_spouse,C_zipcode_spouse);
                    }else {
                        dm.updateBarisSurvey3Tambahan(get_id_order, C_id_province_spouse, C_id_kab_kodya_spouse, C_id_kecamatan_spouse, C_id_kelurahan_spouse, C_zipcode_spouse);
                    }
                    Toast.makeText(hsContext,
                            "Update Simpan Sementara", Toast.LENGTH_LONG).show();
                    //Log.e("datautksimpan","idProv:"+C_id_province_spouse+" idKota"+C_id_kab_kodya_spouse+" idKec"+C_id_kecamatan_spouse+" idKel"+C_id_kelurahan_spouse+" zipcode"+C_zipcode_spouse);
                }
                hasil_data();
            }
        });
        return view;
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

        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                setDate.get(DateTimeFieldType.year()) - 16,
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
            Spouse_date_of_birth.setText(real_date);
        }catch (NullPointerException pe){
            pe.printStackTrace();
        }
    }

    public static String printDateByFormat(DateTime dates, String formatDates){
        DateTimeFormatter fmt = DateTimeFormat.forPattern(formatDates);
        return fmt.print(dates);
    }

    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);//
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            String t_id_province_spouse                 = "";
            String t_id_kab_kodya_spouse                = "";
            String t_id_kec_spouse                      = "";
            String t_id_kel_spouse                      = "";
            String t_zipcode_spouse                     = "";

            ArrayList<Object> arrayList = dm.ambilBarisSurvey3Tbh(get_id_order);
            if (arrayList.size() > 0){
                if (arrayList.get(0) != null){
                    t_id_province_spouse += arrayList.get(0);
                }
                if (arrayList.get(1) != null){
                    t_id_kab_kodya_spouse += arrayList.get(1);
                }
                if (arrayList.get(2) != null){
                    t_id_kec_spouse += arrayList.get(2);
                }
                if (arrayList.get(3) != null){
                    t_id_kel_spouse += arrayList.get(3);
                }
                if (arrayList.get(4) != null){
                    t_zipcode_spouse += arrayList.get(4);
                }
            }


            ArrayList<Object> baris = t_data.get(0);
            String t_Spouse_name                        = ""+baris.get(54);
            String t_spouse_title                       = ""+baris.get(55);
            String t_spouse_religion                    = ""+baris.get(56);
            String t_spouse_sex                         = ""+baris.get(57);
            String t_spouse_identity_type               = ""+baris.get(58);
            String t_Spouse_identity_no                 = ""+baris.get(59);
            String t_Spouse_birth_place_or_sandi_lahir  = ""+baris.get(60);
            String t_Spouse_date_of_birth               = ""+baris.get(61);
            String t_Spouse_address                     = ""+baris.get(62);
            String t_province_spouse                    = ""+baris.get(63);
            String t_kab_kodya_spouse                   = ""+baris.get(64);
            String t_kecamatan_spouse                   = ""+baris.get(65);
            String t_kelurahan_spouse                   = ""+baris.get(66);
            String t_Postal_code_spouse                 = ""+baris.get(67);
            String t_Sandi_dati_2_spouse                = ""+baris.get(68);
            String t_Spouse_no_handphone                = ""+baris.get(69);
            String t_spouse_occupation_or_pekerjaan     = ""+baris.get(70);
            String t_Spouse_company_name                = ""+baris.get(71);
            String t_spouse_job_title                   = ""+baris.get(72);
            String t_Spouse_line_of_business            = ""+baris.get(73);
            String t_Spouse_company_address             = ""+baris.get(74);
            String t_Spouse_company_telephone           = ""+baris.get(75);
            String t_Spouse_fax                         = ""+baris.get(76);
            String t_Spouse_tipe_pekerjaan              = ""+baris.get(142);
            String t_Spouse_tipe_pekerjaan_code         = ""+baris.get(143);
            String t_Spouse_jenis_pekerjaan             = ""+baris.get(144);
            String t_Spouse_jenis_pekerjaan_code        = ""+baris.get(145);

            if(t_spouse_title.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_title = myAdapter_spouse_title.getPosition(t_spouse_title);
            S_spouse_title.setSelection(spinner_spouse_title);

            if(t_spouse_identity_type.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_identity_type = myAdapter_spouse_identity_type.
                    getPosition(t_spouse_identity_type);
            S_spouse_identity_type.setSelection(spinner_spouse_identity_type);

            if(t_spouse_religion.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_religion = myAdapter_spouse_religion.getPosition(t_spouse_religion);
            S_spouse_religion.setSelection(spinner_spouse_religion);

            /*if(t_spouse_occupation_or_pekerjaan.equals("--")){
                //status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_pekerjaan = myAdapter_spouse_pekerjaan.
                    getPosition(t_spouse_occupation_or_pekerjaan);
            S_spouse_occupation_or_pekerjaan.setSelection(spinner_spouse_pekerjaan);*/

            if (t_Spouse_tipe_pekerjaan.equals("--") || t_Spouse_tipe_pekerjaan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_Tipe_pekerjaan_spouse.setText("");
            }else {
                S_Tipe_pekerjaan_spouse.setText(t_Spouse_tipe_pekerjaan);
            }
            if (t_Spouse_tipe_pekerjaan_code.equals("")){
                status_lengkap = status_lengkap+1;
                S_Tipe_pekerjaan_spouse.setTag("");
            }else {
                S_Tipe_pekerjaan_spouse.setTag(t_Spouse_tipe_pekerjaan_code);
            }

            if (t_Spouse_jenis_pekerjaan.equals("--") || t_Spouse_jenis_pekerjaan.equals("null")){
                status_lengkap = status_lengkap+1;
                S_Jenis_pekerjaan_spouse.setText("");
            }else {
               S_Jenis_pekerjaan_spouse.setText(t_Spouse_jenis_pekerjaan);

            }
            if (t_Spouse_jenis_pekerjaan_code.equals("")){
                status_lengkap = status_lengkap+1;
                S_Jenis_pekerjaan_spouse.setTag("");
            }else {
                S_Jenis_pekerjaan_spouse.setTag(t_Spouse_jenis_pekerjaan_code);
            }
            
            if(t_spouse_job_title.equals("--")){
           //     status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_job_title = myAdapter_spouse_job_title.
                    getPosition(t_spouse_job_title);
            S_spouse_job_title.setSelection(spinner_spouse_job_title);

            if(t_spouse_sex.equals("--")){
                status_lengkap = status_lengkap+1;
            }
            int spinner_spouse_sex = myAdapter_spouse_sex.getPosition(t_spouse_sex);
            S_spouse_sex.setSelection(spinner_spouse_sex);

            if(t_Spouse_name.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_name.setText("");
            }else{
                Spouse_name.setText(t_Spouse_name);
            }

            if(t_Spouse_birth_place_or_sandi_lahir.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_birth_place_or_sandi_lahir.setText("");
            }else{
                Spouse_birth_place_or_sandi_lahir.setText(t_Spouse_birth_place_or_sandi_lahir);
            }

            if(t_Spouse_address.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_address.setText("");
            }else{
                Spouse_address.setText(t_Spouse_address);
            }

            if(t_province_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_spouse.setText("");
            }else{
                S_province_spouse.setText(t_province_spouse);
            }

            if (t_id_province_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_province_spouse.setTag("");
            }else{
                S_province_spouse.setTag(t_id_province_spouse);
            }

            if(t_kab_kodya_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_spouse.setText("");
            }else{
                S_kab_kodya_spouse.setText(t_kab_kodya_spouse);
            }

            if (t_id_kab_kodya_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kab_kodya_spouse.setTag("");
            }else {
                S_kab_kodya_spouse.setTag(t_id_kab_kodya_spouse);
            }

            if(t_kecamatan_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_spouse.setText("");
            }else{
                S_kecamatan_spouse.setText(t_kecamatan_spouse);
            }

            if (t_id_kec_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kecamatan_spouse.setTag("");
            }else {
                S_kecamatan_spouse.setTag(t_id_kec_spouse);
            }

            if(t_kelurahan_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_spouse.setText("");
            }else{
                S_kelurahan_spouse.setText(t_kelurahan_spouse);
            }

            if (t_id_kel_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                S_kelurahan_spouse.setTag("");
            }else {
                S_kelurahan_spouse.setTag(t_id_kel_spouse);
            }

            if(t_Sandi_dati_2_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                Sandi_dati_2_spouse.setText("");
            }else{
                Sandi_dati_2_spouse.setText(t_Sandi_dati_2_spouse);
            }

            if(t_Postal_code_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_spouse.setText("");
            }else{
                Postal_code_spouse.setText(t_Postal_code_spouse);
            }

            if (t_zipcode_spouse.equals("null")){
                status_lengkap = status_lengkap+1;
                Postal_code_spouse.setTag("");
            }else {
                Postal_code_spouse.setTag(t_zipcode_spouse);
            }

            if(t_Spouse_no_handphone.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_no_handphone.setText("");
            }else{
                Spouse_no_handphone.setText(t_Spouse_no_handphone);
            }

            if(t_Spouse_company_name.equals("null")){
            //    status_lengkap = status_lengkap+1;
                Spouse_company_name.setText("");
            }else{
                Spouse_company_name.setText(t_Spouse_company_name);
            }

            if(t_Spouse_company_address.equals("null")){
            //    status_lengkap = status_lengkap+1;
                Spouse_company_address.setText("");
            }else{
                Spouse_company_address.setText(t_Spouse_company_address);
            }

            if(t_Spouse_company_telephone.equals("null")){
            //    status_lengkap = status_lengkap+1;
                Spouse_company_telephone.setText("");
            }else{
                Spouse_company_telephone.setText(t_Spouse_company_telephone);
            }

            if(t_Spouse_line_of_business.equals("null")){
              //  status_lengkap = status_lengkap+1;
                Spouse_line_of_business.setText("");
            }else{
                Spouse_line_of_business.setText(t_Spouse_line_of_business);
            }

            if(t_Spouse_identity_no.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_identity_no.setText("");
            }else{
                Spouse_identity_no.setText(t_Spouse_identity_no);
            }

            if(t_Spouse_date_of_birth.equals("null")){
                status_lengkap = status_lengkap+1;
                Spouse_date_of_birth.setText("");
            }else{
                Spouse_date_of_birth.setText(t_Spouse_date_of_birth);
            }

            if(t_Spouse_fax.equals("null")){
               // status_lengkap = status_lengkap+1;
                Spouse_fax.setText("");
            }else{
                Spouse_fax.setText(t_Spouse_fax);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab3 = dm.ambilBarisTab("TAB 3",get_id_order);
                if (data_tab3.size() < 1) {
                    dm.addRowTab("Spouse Data","TAB 3", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
    }

    public void TampilSpouseTitle(){
        ArrayList<ArrayList<Object>> data_spouse_title = dm.ambilBarisJsonPilih("Title");
        if(data_spouse_title.size()>0){
            ArrayList<Object> baris = data_spouse_title.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_title = new ArrayList<String>();
                cek_list_spouse_title.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_title = obj.getString("nama_title");
                            cek_list_spouse_title.add(spouse_title);
                        }
                    }
                }
                myAdapter_spouse_title = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_spouse_title);
                myAdapter_spouse_title.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_title.setAdapter(myAdapter_spouse_title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilSpouseIdentityType(){
        ArrayList<ArrayList<Object>> data_identity_type = dm.ambilBarisJsonPilih("Identity Type");
        if(data_identity_type.size()>0){
            ArrayList<Object> baris = data_identity_type.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_identity_type = new ArrayList<String>();
                cek_list_spouse_identity_type.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_identity_type = obj.getString("nama_identity_type");
                            cek_list_spouse_identity_type.add(spouse_identity_type);
                        }
                    }
                }
                myAdapter_spouse_identity_type = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_spouse_identity_type);
                myAdapter_spouse_identity_type.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_identity_type.setAdapter(myAdapter_spouse_identity_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilSpouseReligion(){
        ArrayList<ArrayList<Object>> data_spouse_religion = dm.ambilBarisJsonPilih("Religion");
        if(data_spouse_religion.size()>0){
            ArrayList<Object> baris = data_spouse_religion.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_religion = new ArrayList<String>();
                cek_list_spouse_religion.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_religion = obj.getString("nama_religion");
                            cek_list_spouse_religion.add(spouse_religion);
                        }
                    }
                }
                myAdapter_spouse_religion = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_spouse_religion);
                myAdapter_spouse_religion.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_religion.setAdapter(myAdapter_spouse_religion);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*public void TampilSpousePekerjaan(){
        ArrayList<ArrayList<Object>> data_spouse_pekerjaan = dm.ambilBarisJsonPilih("Pekerjaan");
        if(data_spouse_pekerjaan.size()>0){
            ArrayList<Object> baris = data_spouse_pekerjaan.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_pekerjaan = new ArrayList<String>();
                cek_list_spouse_pekerjaan.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_pekerjaan = obj.getString("nama_pekerjaan");
                            cek_list_spouse_pekerjaan.add(spouse_pekerjaan);
                        }
                    }
                }
                myAdapter_spouse_pekerjaan = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_spouse_pekerjaan);
                myAdapter_spouse_pekerjaan.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_occupation_or_pekerjaan.setAdapter(myAdapter_spouse_pekerjaan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    public void TampilSpouseJobTitle(){
        ArrayList<ArrayList<Object>> data_spouse_job_title = dm.ambilBarisJsonPilih("Job Title");
        if(data_spouse_job_title.size()>0){
            ArrayList<Object> baris = data_spouse_job_title.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_job_title = new ArrayList<String>();
                cek_list_spouse_job_title.add("--");

                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_job_title = obj.getString("nama_job_title");
                            cek_list_spouse_job_title.add(spouse_job_title);
                        }
                    }
                }
                myAdapter_spouse_job_title = new ArrayAdapter<String>(hsContext,
                        R.layout.spinner_item, cek_list_spouse_job_title);
                myAdapter_spouse_job_title.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_job_title.setAdapter(myAdapter_spouse_job_title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void TampilSpouseSex(){
        ArrayList<ArrayList<Object>> data_sex = dm.ambilBarisJsonPilih("Sex");
        if(data_sex.size()>0){
            ArrayList<Object> baris = data_sex.get(0);
            tam_json_hasil = baris.get(0).toString();
            try {
                cek_list_spouse_sex = new ArrayList<String>();
                cek_list_spouse_sex.add("--");
                JSONObject jObj = new JSONObject(tam_json_hasil);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            spouse_sex = obj.getString("nama_sex");
                            cek_list_spouse_sex.add(spouse_sex);
                        }
                    }
                }
                myAdapter_spouse_sex = new ArrayAdapter<String>(hsContext, R.layout.spinner_item,
                        cek_list_spouse_sex);
                myAdapter_spouse_sex.setDropDownViewResource(R.layout.spinner_item);
                S_spouse_sex.setAdapter(myAdapter_spouse_sex);
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
                            cek_list_tipe_pekerjaan = new ArrayList<String>();
                            cek_list_tipe_pekerjaan.add("--");

                            cek_list_tipe_pekerjaan_code = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")){
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0){
                                    for (int i=0;i<arrayData.length();i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        spouse_tipe_pekerjaan = obj.getString("ocpt_descr");
                                        spouse_tipe_pekerjaan_code = obj.getString("ocpt_type");
                                        cek_list_tipe_pekerjaan.add(spouse_tipe_pekerjaan);
                                        cek_list_tipe_pekerjaan_code.add(spouse_tipe_pekerjaan_code);
                                    }
                                }
                            }


                            spinnerDialog_tipe_pekerjaan = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_tipe_pekerjaan,"Select item");
                            S_Tipe_pekerjaan_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_tipe_pekerjaan.showSpinerDialog();
                                }
                            });
                            spinnerDialog_tipe_pekerjaan.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_Tipe_pekerjaan_spouse.setText(item);
                                    S_Tipe_pekerjaan_spouse.setTag(cek_list_tipe_pekerjaan_code.get(position-1));
                                    S_Jenis_pekerjaan_spouse.setText("");
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
                            cek_list_jenis_pekerjan = new ArrayList<String>();
                            cek_list_jenis_pekerjan.add("--");

                            cek_list_jenis_pekerjaan_code = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")){
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0){
                                    for (int i=0;i<arrayData.length();i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        spouse_jenis_pekerjaan = obj.getString("ocpt_descr");
                                        spouse_jenis_pekerjaan_code = obj.getString("ocpt_code");
                                        cek_list_jenis_pekerjan.add(spouse_jenis_pekerjaan);
                                        cek_list_jenis_pekerjaan_code.add(spouse_jenis_pekerjaan_code);
                                    }
                                }
                            }
                            spinnerDialog_jenis_pekerjaan = new SpinnerDialog(
                                    (Activity) hsContext,
                                    cek_list_jenis_pekerjan,"Select item");
                            S_Jenis_pekerjaan_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_jenis_pekerjaan.showSpinerDialog();
                                }
                            });
                            spinnerDialog_jenis_pekerjaan.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_Jenis_pekerjaan_spouse.setText(item);
                                    S_Jenis_pekerjaan_spouse.setTag(cek_list_jenis_pekerjaan_code.get(position-1));
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String ocpt_type;
                if (S_Tipe_pekerjaan_spouse.getTag() != null){
                    ocpt_type = S_Tipe_pekerjaan_spouse.getTag().toString();
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

    public void TampilProvinceSpouse(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_PROPINSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_province_spouse = new ArrayList<String>();
                            cek_list_province_spouse.add("--");

                            cek_list_id_province_spouse = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray provData = jsonArray.getJSONArray(0);
                                JSONArray idProvData = jsonArray.getJSONArray(1);
                                //Log.e("idProvDta",idProvData.toString());
                                if (provData.length() > 0) {
                                    for(int i=0; i<provData.length(); i++){
                                        JSONObject obj = provData.getJSONObject(i);
                                        province_spouse = obj.getString("propinsi");
                                        //Log.e("provinsiSpouse",province_spouse);
                                        if (cek_list_province_spouse.contains(province_spouse)) {
                                            // true
                                        }else{
                                            cek_list_province_spouse.add(province_spouse);
                                        }

                                        JSONObject jsonObject = idProvData.getJSONObject(i);
                                        id_province_spouse = jsonObject.getString("kode_propinsi");
                                        if (cek_list_id_province_spouse.contains(id_province_spouse)){

                                        }else {
                                            cek_list_id_province_spouse.add(id_province_spouse);
                                        }
                                    }
                                }

                                Log.e("ceklistIdProvData",cek_list_id_province_spouse.toString());
                            }
                            spinnerDialog_province_spouse = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_province_spouse,
                                    "Select item");
                            S_province_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_province_spouse.showSpinerDialog();
                                }
                            });
                            spinnerDialog_province_spouse.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_province_spouse.setText(item);
                                    S_province_spouse.setTag(cek_list_id_province_spouse.get(position-1));
                                    S_kab_kodya_spouse.setText("");
                                    S_kecamatan_spouse.setText("");
                                    S_kelurahan_spouse.setText("");
                                    Sandi_dati_2_spouse.setText("");
                                    Postal_code_spouse.setText("");
                                    TampilKabKodyaSpouse();
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

                        //progressDialog.dismiss();

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

    public void TampilKabKodyaSpouse(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KABKODYA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kab_kodya_spouse = new ArrayList<String>();
                            cek_list_kab_kodya_spouse.add("--");

                            cek_list_id_kab_kodya_spouse = new ArrayList<String>();

                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                //String data = jObj.getString("data");
                                //JSONArray arrayData = new JSONArray(data);
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONArray kotaData = jsonArray.getJSONArray(0);
                                JSONArray idKotaData = jsonArray.getJSONArray(1);
                                Log.e("idKotaData",idKotaData.toString());
                                if (kotaData.length() > 0) {
                                    for(int i=0; i<kotaData.length(); i++){
                                        JSONObject obj = kotaData.getJSONObject(i);
                                        kab_kodya_spouse = obj.getString("kbpktm");
                                        if (cek_list_kab_kodya_spouse.contains(kab_kodya_spouse)) {
                                            // true
                                        }else{
                                            cek_list_kab_kodya_spouse.add(kab_kodya_spouse);
                                        }

                                        JSONObject jsonObject = idKotaData.getJSONObject(i);
                                        id_kab_kodya_spouse = jsonObject.getString("kode_kota");
                                        if (cek_list_id_kab_kodya_spouse.contains(id_kab_kodya_spouse)){

                                        }else {
                                            cek_list_id_kab_kodya_spouse.add(id_kab_kodya_spouse);
                                        }
                                    }
                                }
                                Log.e("idKota",cek_list_id_kab_kodya_spouse.toString());
                            }
                            spinnerDialog_kab_kodya_spouse = new SpinnerDialog((Activity) hsContext,
                                    cek_list_kab_kodya_spouse,"Select item");
                            S_kab_kodya_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kab_kodya_spouse.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kab_kodya_spouse.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kab_kodya_spouse.setText(item);
                                    S_kab_kodya_spouse.setTag(cek_list_id_kab_kodya_spouse.get(position-1));
                                    S_kecamatan_spouse.setText("");
                                    S_kelurahan_spouse.setText("");
                                    Sandi_dati_2_spouse.setText("");
                                    Postal_code_spouse.setText("");
                                    TampilKecamatanSpouse();
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

                        //progressDialog.dismiss();

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
                String idProv;

                if (S_province_spouse.getTag() != null){
                    idProv = S_province_spouse.getTag().toString();
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

    public void TampilKecamatanSpouse(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kecamatan_spouse = new ArrayList<String>();
                            cek_list_kecamatan_spouse.add("--");

                            cek_list_id_kecamatan_spouse = new ArrayList<String>();

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
                                        kecamatan_spouse = obj.getString("kecamatan");
                                        if (cek_list_kecamatan_spouse.contains(kecamatan_spouse)) {
                                            // true
                                        }else{
                                            cek_list_kecamatan_spouse.add(kecamatan_spouse);
                                        }

                                        JSONObject jsonObject = idKecData.getJSONObject(i);
                                        id_kecamatan_spouse = jsonObject.getString("kode_kecamatan");
                                        if (cek_list_id_kecamatan_spouse.contains(id_kecamatan_spouse)){

                                        }else {
                                            cek_list_id_kecamatan_spouse.add(id_kecamatan_spouse);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kecamatan_spouse = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kecamatan_spouse,
                                    "Select item");
                            S_kecamatan_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kecamatan_spouse.showSpinerDialog();
                                }
                            });
                            spinnerDialog_kecamatan_spouse.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kecamatan_spouse.setText(item);
                                    S_kecamatan_spouse.setTag(cek_list_id_kecamatan_spouse.get(position-1));
                                    S_kelurahan_spouse.setText("");
                                    Sandi_dati_2_spouse.setText("");
                                    Postal_code_spouse.setText("");
                                    TampilKelurahanSpouse();
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

                        //progressDialog.dismiss();

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
                String idKota;

                if (S_kab_kodya_spouse.getTag() != null){
                    idKota = S_kab_kodya_spouse.getTag().toString();
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

    public void TampilKelurahanSpouse(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_JSON_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            cek_list_kelurahan_spouse = new ArrayList<String>();
                            cek_list_kelurahan_spouse.add("--");
                            cek_list_id_kelurahan_spouse = new ArrayList<String>();

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
                                        kelurahan_spouse = obj.getString("kelurahan");
                                        if (cek_list_kelurahan_spouse.contains(kelurahan_spouse)) {
                                            // true
                                        }else{
                                            cek_list_kelurahan_spouse.add(kelurahan_spouse);
                                        }

                                        JSONObject jsonObject = idKelData.getJSONObject(i);
                                        id_kelurahan_spouse = jsonObject.getString("kode_kelurahan");
                                        if (cek_list_id_kelurahan_spouse.contains(id_kelurahan_spouse)){

                                        }else {
                                            cek_list_id_kelurahan_spouse.add(id_kelurahan_spouse);
                                        }
                                    }
                                }
                            }
                            spinnerDialog_kelurahan_spouse = new SpinnerDialog(
                                    (Activity) hsContext, cek_list_kelurahan_spouse,
                                    "Select item");
                            S_kelurahan_spouse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spinnerDialog_kelurahan_spouse.showSpinerDialog();
                                }
                            });

                            spinnerDialog_kelurahan_spouse.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    S_kelurahan_spouse.setText(item);
                                    S_kelurahan_spouse.setTag(cek_list_id_kelurahan_spouse.get(position-1));
                                    TampilSandiDati2KodePosSpouse();
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

                        //progressDialog.dismiss();

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
                String idKec;

                if (S_kecamatan_spouse.getTag() != null){
                    idKec = S_kecamatan_spouse.getTag().toString();
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

    public void TampilSandiDati2KodePosSpouse(){
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
                                        kodepos_spouse = obj.getString("kodepos");
                                        zipcode_spouse = obj.getString("zipcode");
                                        kodesandidati2_spouse = obj.getString("sandidati2");

                                        Postal_code_spouse.setText(kodepos_spouse);
                                        Postal_code_spouse.setTag(zipcode_spouse);
                                        Sandi_dati_2_spouse.setText(kodesandidati2_spouse);
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

                        //progressDialog.dismiss();

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
                String idProv,idKota,idKec,idKel;

                if (S_province_spouse.getTag() != null){
                    idProv = S_province_spouse.getTag().toString();
                }else {
                    idProv = "";
                }
                if (S_kab_kodya_spouse.getTag() != null){
                    idKota = S_kab_kodya_spouse.getTag().toString();
                }else {
                    idKota = "";
                }
                if (S_kecamatan_spouse.getTag() != null){
                    idKec = S_kecamatan_spouse.getTag().toString();
                }else {
                    idKec = "";
                }
                if (S_kelurahan_spouse.getTag() != null){
                    idKel = S_kelurahan_spouse.getTag().toString();
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

    public void UpdateSpouseTitle(){
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

    public void UpdateSpouseIdentityType(){
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


    public void UpdateSpouseReligion(){
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

    /*public void UpdateSpousePekerjaan(){
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }*/

    public void UpdateSpouseJobTitle(){
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
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    public void UpdateSpouseSex(){
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

    @Override
    public void onResume() {
        super.onResume();

        UpdateSpouseTitle();
        UpdateSpouseIdentityType();
        UpdateSpouseReligion();
        //UpdateSpousePekerjaan();
        UpdateSpouseJobTitle();
        UpdateSpouseSex();

        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisStatusPernikahan(get_id_order);//
        if (t_data.size() < 1) {

        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_marital_status = "" + baris.get(1);
            if (t_marital_status.equals("Married")) {
                data_spouse_data.setVisibility(View.VISIBLE);
                data_spouse_data_not.setVisibility(View.GONE);
                b_simpan.setVisibility(View.VISIBLE);
                img_single_bg.setVisibility(View.GONE);
                box_text_pesan.setVisibility(View.GONE);
            }else{
                data_spouse_data.setVisibility(View.GONE);
                data_spouse_data_not.setVisibility(View.VISIBLE);
                b_simpan.setVisibility(View.GONE);
                img_single_bg.setVisibility(View.VISIBLE);
                box_text_pesan.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(hsContext, "aa 3",Toast.LENGTH_LONG).show();
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
