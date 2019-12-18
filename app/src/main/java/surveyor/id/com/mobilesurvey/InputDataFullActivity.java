package surveyor.id.com.mobilesurvey;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDelapan;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDuabelas;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSebelas;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.OtherUtil;

import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDelapan.C_Jumlah_anak;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDelapan.C_Jumlah_tanggungan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_address;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_fax_1;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_kab_or_kodya;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_kecamatan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_kelurahan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_province;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_telephone_1;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Company_telephone_2;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Economy_code;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Estabilished_since;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Line_of_business;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Name_economy_code;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Postal_code_company;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_Sandi_dati_2_company;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_job_title;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDua.C_pekerjaan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDuabelas.C_Alasan_or_point_penting_rekomendasi_anda;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentDuabelas.C_apakah_direkomendasikan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_Contact_Postal_code;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_Contact_address;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_Contact_name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_Contact_sandi_dati_2;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_Contact_telephone;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_contact_kab_or_kodya;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_contact_kecamatan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_contact_kelurahan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_contact_province;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_has_contact_person;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEmpat.C_relationship;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_Jarak_tempat_usaha_dari_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_Lama_pemohon_menempati_tempat_usaha_bulan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_Lama_pemohon_menempati_tempat_usaha_tahun;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_Lama_usaha_berdiri_bulan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_Lama_usaha_berdiri_tahun;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_bentuk_bangunan_tempat_usaha;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_jumlah_karyawan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_lokasi_tempat_usaha;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_pekerjaan_or_usaha_terkait_ekspor_or_impor;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentEnam.C_status_kepemilikan_usaha;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_Jarak_rumah_ke_cabang;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_Luas_bangunan_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_Luas_tanah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_bentuk_bangunan_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_furniture_or_perabot;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_klasifikasi_perumahan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_kondisi_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_luas_jalan_masuk_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_status_garasi_kendaraan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_status_kepemilikan_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_tempat_menaruh_kendaraan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentLima.C_tipe_rumah;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Address_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Address_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Birth_date;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Birth_place;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Handphone_1;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Handphone_2;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Identity_no;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Mother_maiden_name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Nama_panggilan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Npwp_no;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Postal_code_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Postal_code_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Sandi_dati_2_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Sandi_dati_2_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Sandi_lahir;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Stay_length;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Telephone;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_Telephone_2;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_education;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_email;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_identity_type;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kab_kodya_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kab_kodya_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kecamatan_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kecamatan_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kelurahan_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_kelurahan_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_mail_address;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_marital_status;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_province_home;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_province_ktp;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_religion;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_sex;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu.C_title;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Balance_terakhir_di_rekening_tabungan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Omzet_or_penghasilan_gross;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Pengeluaran_or_kebutuhan_hidup;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Penghasilan_nett_or_take_home_pay;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Penghasilan_pasangan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Penghasilan_tambahan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Rata_rata_mutasi_in_3_bulan_terakhir;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Rata_rata_mutasi_out_3_bulan_terakhir;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSembilan.C_Total_cicilan_leasing_lain;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_Alasan_menunggak_khusus_lebih_dari_coll_2;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_Baki_debet_or_outstanding_hutang;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_Collectabilitas_sid_or_slik_tertinggi;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_Nama_finance_company;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_Overdue_tertinggi;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSepuluh.C_pernah_kredit_di_tempat_lain;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Postal_code_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Sandi_dati_2_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_address;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_birth_place_or_sandi_lahir;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_company_address;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_company_name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_company_telephone;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_date_of_birth;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_fax;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_identity_no;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_line_of_business;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_name;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_Spouse_no_handphone;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_kab_kodya_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_kecamatan_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_kelurahan_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_province_spouse;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_identity_type;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_job_title;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_occupation_or_pekerjaan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_religion;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_sex;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTiga.C_spouse_title;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh.C_Lama_kepemilikan_kendaraan_bulan;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh.C_Lama_kepemilikan_kendaraan_tahun;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh.C_etx_bagian_kondisi_tidak_baik;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh.C_kondisi_mobil;
import static surveyor.id.com.mobilesurvey.fragment.InputFullFragmentTujuh.C_tujuan_penggunaan_unit;

public class InputDataFullActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = InputDataFullActivity.class.getSimpleName();
    private String tag_json_obj = "json_obj_req";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String get_userid, t_has_contact_person, t_contact_name, t_contact_telephone,
            t_relationship, t_Spouse_name, t_spouse_title, t_spouse_identity_type,
            t_Spouse_birth_place_or_sandi_lahir, t_spouse_religion, t_Spouse_no_handphone,
            t_spouse_occupation_or_pekerjaan, t_Spouse_company_name, t_Spouse_company_telephone,
            t_Spouse_line_of_business, t_spouse_job_title, t_spouse_sex, t_Spouse_identity_no,
            t_Spouse_date_of_birth, t_Spouse_fax, t_Pekerjaan, t_job_title, t_Name_economy_code,
            t_Company_name, t_Company_telephone_1, t_Company_telephone_2, t_Line_of_business,
            t_Economy_code, t_Estabilished_since, t_Company_fax_1, t_nama, t_mother, t_title,
            t_marital_status, t_identity_type, t_Npwp_no, t_Birth_place, t_mail_address,
            t_tipe_rumah, t_home_status, t_Telephone, t_Telephone_2, t_education, t_sex,
            t_Nama_panggilan, t_Identity_no, t_Sandi_lahir, t_religion, t_Stay_length,
            t_Handphone_1, t_Handphone_2, t_email, latitude, longitude, p_photo_nama,
            p_photo_bitmap, p_photo_latitude, p_photo_longitude, p_photo_id_order, photo_nama,
            photo_bitmap, photo_latitude, photo_longitude, photo_id_order, photo_status,
            get_id_order, get_name, get_identity_type, get_identity_no, get_address_home,
            get_telephone, get_sex, get_handphone_1, t_jarak_rumah_ke_cabang, t_luas_tanah,
            t_luas_bangunan_rumah, t_status_kepemilikan_rumah, t_klasifikasi_perumahan,
            t_tempat_menaruh_kendaraan, t_status_garasi_kendaraan, t_bentuk_bangunan_rumah,
            t_kondisi_rumah, t_luas_jalan_masuk_rumah, t_status_kepemilikan_rumah_pemohon,
            t_furniture, t_jarak_tempat_usaha_dari_rumah, t_status_kepemilikan_usaha,
            t_bentuk_bangunan_tempat_usaha, t_lokasi_tempat_usaha, t_jumlah_karyawan,
            t_lama_pemohon_menempati_tempat_usaha_tahun,
            t_lama_pemohon_menempati_tempat_usaha_bulan, t_lama_usaha_berdiri_tahun,
            t_lama_usaha_berdiri_bulan, t_pekerjaan_or_usaha_terkait_ekspor_or_impor,
            t_tujuan_penggunaan_unit, t_kondisi_mobil,t_bagian_kondisi_yang_tidak_baik,
            t_lama_kepemilikan_kendaraan_tahun, t_lama_kepemilikan_kendaraan_bulan,
            t_jumlah_tanggungan, t_jumlah_anak, t_omzet_or_penghasilan_gross,
            t_penghasilan_nett_or_take_home_pay, t_penghasilan_pasangan, t_penghasilan_tambahan,
            t_pengeluaran_or_kebutuhan_hidup, t_total_cicilan_leasing_lain,
            t_balance_terakhir_di_rekening_tabungan, t_rata_rata_mutasi_in_3_bulan_terakhir,
            t_rata_rata_mutasi_out_3_bulan_terakhir, t_collectabilitas_sid_or_slik_tertinggi,
            t_pernah_kredit_di_tempat_lain, t_overdue_tertinggi, t_baki_debet_or_outstanding_hutang,
            t_nama_finance_company, t_alasan_menunggak_khusus_lebih_dari_coll_2,
            t_apakah_direkomendasikan, t_alasan_or_point_penting_rekomendasi_anda, t_Birth_date,
            t_address_ktp, t_province_ktp, t_id_province_ktp,t_kab_kodya_ktp, t_id_kab_kodya_ktp,t_kecamatan_ktp, t_id_kecamatan_ktp,t_kelurahan_ktp,
            t_id_kelurahan_ktp,t_sandi_dati_2_ktp, t_postal_code_ktp, t_address_home, t_province_home,
            t_kab_kodya_home, t_kecamatan_home, t_kelurahan_home, t_sandi_dati_2_home,
            t_postal_code_home, t_zipcode_ktp,t_Company_address, t_Company_province, t_Company_kab_kodya,
            t_Company_kecamatan, t_Company_kelurahan, t_Company_sandi_dati_2, t_Company_postal_code,
            t_Spouse_address, t_Spouse_province, t_Spouse_id_province,t_Spouse_kab_kodya, t_Spouse_id_kab_kodya,t_Spouse_kecamatan,
            t_Spouse_id_kecamatan,t_Spouse_kelurahan,t_Spouse_id_kelurahan, t_Spouse_sandi_dati_2, t_Spouse_postal_code,
            t_Spouse_zipcode,t_Spouse_company_address, t_Contact_address, t_Contact_province, t_Contact_kab_kodya,
            t_Contact_kecamatan, t_Contact_kelurahan, t_Contact_sandi_dati_2, t_Contact_postal_code,
            get_username;
    public JSONArray json;
    private Dialog dialog,dialog_gagal_kirim,dialog_back;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    public ProgressDialog loading;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private int currentColor = 0xFF33B5E5;
    private DatabaseManager dm;
    private int resError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_full);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Input Kelengkapan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resError = 1;

        dm = new DatabaseManager(this);

        get_id_order        = getIntent().getExtras().getString("id_order");
        get_name            = getIntent().getExtras().getString("name");
        get_identity_type   = getIntent().getExtras().getString("identity_type");
        get_identity_no     = getIntent().getExtras().getString("identity_no");
        get_address_home    = getIntent().getExtras().getString("address_home");
        get_telephone       = getIntent().getExtras().getString("telephone");
        get_sex             = getIntent().getExtras().getString("sex");
        get_handphone_1     = getIntent().getExtras().getString("handphone_1");

        ArrayList<ArrayList<Object>> data_user = dm.ambilSemuaBaris();
        if(data_user.size() > 0){
            ArrayList<Object> baris = data_user.get(0);
            get_username    = baris.get(0).toString();
            get_userid      = baris.get(3).toString();
        }

        buildGoogleApiClient();
       // lokasiget();
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        tabs.setTextColor(this.getResources().getColor(R.color.putih));
        tabs.setIndicatorColor(currentColor);

        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void lokasiget() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                try{
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                            ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
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

    public void cek_data(){
        ArrayList<ArrayList<Object>> data_tab1  = dm.ambilBarisTab("TAB 1",get_id_order);
        ArrayList<ArrayList<Object>> data_tab2  = dm.ambilBarisTab("TAB 2",get_id_order);
        ArrayList<ArrayList<Object>> data_tab3  = dm.ambilBarisTab("TAB 3",get_id_order);
        ArrayList<ArrayList<Object>> data_tab4  = dm.ambilBarisTab("TAB 4",get_id_order);
        ArrayList<ArrayList<Object>> data_tab5  = dm.ambilBarisTab("TAB 5",get_id_order);
        ArrayList<ArrayList<Object>> data_tab6  = dm.ambilBarisTab("TAB 6",get_id_order);
        ArrayList<ArrayList<Object>> data_tab7  = dm.ambilBarisTab("TAB 7",get_id_order);
        ArrayList<ArrayList<Object>> data_tab8  = dm.ambilBarisTab("TAB 8",get_id_order);
        ArrayList<ArrayList<Object>> data_tab9  = dm.ambilBarisTab("TAB 9",get_id_order);
        ArrayList<ArrayList<Object>> data_tab10 = dm.ambilBarisTab("TAB 10",get_id_order);
        ArrayList<ArrayList<Object>> data_tab11 = dm.ambilBarisTab("TAB 11",get_id_order);
        ArrayList<ArrayList<Object>> data_tab12 = dm.ambilBarisTab("TAB 12",get_id_order);

        if (data_tab1.size() < 1) {
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Personal Data",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab2.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Occupation Data",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab4.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Contact Person Data",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab5.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Tempat Tinggal(Rumah)",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab6.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Pekerjaan/Tempat Usaha",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab7.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Objek Pembiayaan",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab9.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Pendapatan/Kapasitas",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab11.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Data Foto",
                    Toast.LENGTH_LONG).show();
        }else if(data_tab12.size() < 1){
            Toast.makeText(InputDataFullActivity.this,
                    "Mohon Lengkapi Kesimpulan Survey",
                    Toast.LENGTH_LONG).show();
        }else{
            CustomDialog();
        }
    }

    public void CustomDialog(){
        dialog = new Dialog(InputDataFullActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox_kirim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Button bt_tidak =(Button) dialog.findViewById(R.id.bt_tidak);
        Button bt_ya =(Button) dialog.findViewById(R.id.bt_ya);
        bt_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
        dialog.show();
    }

    public void CustomDialog_gagal_kirim(){
        dialog_gagal_kirim = new Dialog(InputDataFullActivity.this);
        dialog_gagal_kirim.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_gagal_kirim.setContentView(R.layout.dialogbox_gagal_kirim);
        dialog_gagal_kirim.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_gagal_kirim.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog_gagal_kirim.setCanceledOnTouchOutside(true);
        dialog_gagal_kirim.setCanceledOnTouchOutside(false);
        dialog_gagal_kirim.setCancelable(false);
        Button bt_tidak =(Button) dialog_gagal_kirim.findViewById(R.id.bt_tidak);
        Button bt_ya =(Button) dialog_gagal_kirim.findViewById(R.id.bt_ya);
        bt_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_gagal_kirim.dismiss();
            }
        });
        bt_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm.deleteTugasAllId("Inputan",get_id_order);
                dm.addRowTugasId(t_nama+"#,#"+t_address_ktp,"Inputan",get_id_order);
                dm.addRowStatusTerkirim(get_id_order);
                dialog_gagal_kirim.dismiss();
                finish();
            }
        });
        dialog_gagal_kirim.show();
    }


    public void CustomDialog_back(){
        dialog_back = new Dialog(InputDataFullActivity.this);
        dialog_back.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_back.setContentView(R.layout.dialogbox_back_input);
        dialog_back.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_back.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog_back.setCanceledOnTouchOutside(true);
        dialog_back.setCanceledOnTouchOutside(false);
        dialog_back.setCancelable(false);
        Button bt_tidak =(Button) dialog_back.findViewById(R.id.bt_tidak);
        Button bt_ya =(Button) dialog_back.findViewById(R.id.bt_ya);
        bt_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_back.dismiss();
            }
        });
        bt_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_back.dismiss();
                finish();
            }
        });
        dialog_back.show();
    }

    public void simpan(){
        dm.updateBarisPhoto(get_id_order);
        ArrayList<Object> arrayLists = dm.ambilBarisSurvey1Tbh(get_id_order);
        ArrayList<Object> arrayLists3 = dm.ambilBarisSurvey3Tbh(get_id_order);
        //ArrayList<ArrayList<Object>> arrayListSpouse = dm.ambilBarisSurvey3Tambahan(get_id_order);
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);
        //Log.e("datatsimpan","t_data:"+t_data.size()+" arrayLists:"+arrayLists.size()+" arrayLists3:"+arrayLists3.size());
        if (t_data.size() > 0 && arrayLists.size() > 0 && arrayLists3.size() > 0) {
            ArrayList<Object> baris = t_data.get(0);


            if(latitude == null){
                latitude    = "";
                longitude   = "";
            }

            t_id_province_ktp                               = ""+arrayLists.get(0);
            t_id_kab_kodya_ktp                              = ""+arrayLists.get(1);
            t_id_kecamatan_ktp                              = ""+arrayLists.get(2);
            t_id_kelurahan_ktp                              = ""+arrayLists.get(3);
            t_zipcode_ktp                                   = ""+arrayLists.get(4);

            t_Spouse_id_province                            = ""+arrayLists3.get(0);
            t_Spouse_id_kab_kodya                           = ""+arrayLists3.get(1);
            t_Spouse_id_kecamatan                           = ""+arrayLists3.get(2);
            t_Spouse_id_kelurahan                           = ""+arrayLists3.get(3);
            t_Spouse_zipcode                                = ""+arrayLists3.get(4);

            t_nama                                          = ""+baris.get(2);
            t_mother                                        = ""+baris.get(3);
            t_title                                         = ""+baris.get(4);
            t_marital_status                                = ""+baris.get(5);
            t_identity_type                                 = ""+baris.get(6);
            t_Npwp_no                                       = ""+baris.get(7);
            t_Birth_place                                   = ""+baris.get(8);
            t_Birth_date                                    = ""+baris.get(9);
            t_Identity_no                                   = ""+baris.get(10);
            t_address_ktp                                   = ""+baris.get(11);
            t_province_ktp                                  = ""+baris.get(12);
            t_kab_kodya_ktp                                 = ""+baris.get(13);
            t_kecamatan_ktp                                 = ""+baris.get(14);
            t_kelurahan_ktp                                 = ""+baris.get(15);
            t_sandi_dati_2_ktp                              = ""+baris.get(16);
            t_postal_code_ktp                               = ""+baris.get(17);
            t_address_home                                  = ""+baris.get(18);
            t_province_home                                 = ""+baris.get(19);
            t_kab_kodya_home                                = ""+baris.get(20);
            t_kecamatan_home                                = ""+baris.get(21);
            t_kelurahan_home                                = ""+baris.get(22);
            t_sandi_dati_2_home                             = ""+baris.get(23);
            t_postal_code_home                              = ""+baris.get(24);
            t_mail_address                                  = ""+baris.get(25);
            t_education                                     = ""+baris.get(26);
            t_sex                                           = ""+baris.get(27);
            t_Nama_panggilan                                = ""+baris.get(28);
            t_Sandi_lahir                                   = ""+baris.get(29);
            t_religion                                      = ""+baris.get(30);
            t_Stay_length                                   = ""+baris.get(31);
            t_Telephone                                     = ""+baris.get(32);
            t_Telephone_2                                   = ""+baris.get(33);
            t_Handphone_1                                   = ""+baris.get(34);
            t_Handphone_2                                   = ""+baris.get(35);
            t_email                                         = ""+baris.get(36);

            //t_id_province_ktp                               =

            t_Pekerjaan                                     = ""+baris.get(37);
            t_job_title                                     = ""+baris.get(38);
            t_Name_economy_code                             = ""+baris.get(39);
            t_Economy_code                                  = ""+baris.get(40);
            t_Company_name                                  = ""+baris.get(41);
            t_Company_address                               = ""+baris.get(42);
            t_Company_province                              = ""+baris.get(43);
            t_Company_kab_kodya                             = ""+baris.get(44);
            t_Company_kecamatan                             = ""+baris.get(45);
            t_Company_kelurahan                             = ""+baris.get(46);
            t_Company_sandi_dati_2                          = ""+baris.get(47);
            t_Company_postal_code                           = ""+baris.get(48);
            t_Company_telephone_1                           = ""+baris.get(49);
            t_Company_telephone_2                           = ""+baris.get(50);
            t_Line_of_business                              = ""+baris.get(51);
            t_Estabilished_since                            = ""+baris.get(52);
            t_Company_fax_1                                 = ""+baris.get(53);

            t_Spouse_name                                   = ""+baris.get(54);
            t_spouse_title                                  = ""+baris.get(55);
            t_spouse_religion                               = ""+baris.get(56);
            t_spouse_sex                                    = ""+baris.get(57);
            t_spouse_identity_type                          = ""+baris.get(58);
            t_Spouse_identity_no                            = ""+baris.get(59);
            t_Spouse_birth_place_or_sandi_lahir             = ""+baris.get(60);
            t_Spouse_date_of_birth                          = ""+baris.get(61);
            t_Spouse_address                                = ""+baris.get(62);
            t_Spouse_province                               = ""+baris.get(63);
            t_Spouse_kab_kodya                              = ""+baris.get(64);
            t_Spouse_kecamatan                              = ""+baris.get(65);
            t_Spouse_kelurahan                              = ""+baris.get(66);
            t_Spouse_postal_code                            = ""+baris.get(67);
            t_Spouse_sandi_dati_2                           = ""+baris.get(68);
            t_Spouse_no_handphone                           = ""+baris.get(69);
            t_spouse_occupation_or_pekerjaan                = ""+baris.get(70);
            t_Spouse_company_name                           = ""+baris.get(71);
            t_spouse_job_title                              = ""+baris.get(72);
            t_Spouse_line_of_business                       = ""+baris.get(73);
            t_Spouse_company_address                        = ""+baris.get(74);
            t_Spouse_company_telephone                      = ""+baris.get(75);
            t_Spouse_fax                                    = ""+baris.get(76);

            t_has_contact_person                            = ""+baris.get(77);
            t_contact_name                                  = ""+baris.get(78);
            t_Contact_address                               = ""+baris.get(79);
            t_Contact_province                              = ""+baris.get(80);
            t_Contact_kab_kodya                             = ""+baris.get(81);
            t_Contact_kecamatan                             = ""+baris.get(82);
            t_Contact_kelurahan                             = ""+baris.get(83);
            t_Contact_sandi_dati_2                          = ""+baris.get(84);
            t_Contact_postal_code                           = ""+baris.get(85);
            t_contact_telephone                             = ""+baris.get(86);
            t_relationship                                  = ""+baris.get(87);

            t_tipe_rumah                                    = ""+baris.get(88);
            t_home_status                                   = ""+baris.get(89);
            t_jarak_rumah_ke_cabang                         = ""+baris.get(90);
            t_luas_tanah                                    = ""+baris.get(91);
            t_luas_bangunan_rumah                           = ""+baris.get(92);
            t_status_kepemilikan_rumah                      = ""+baris.get(93);
            t_klasifikasi_perumahan                         = ""+baris.get(94);
            t_tempat_menaruh_kendaraan                      = ""+baris.get(95);
            t_status_garasi_kendaraan                       = ""+baris.get(96);
            t_bentuk_bangunan_rumah                         = ""+baris.get(97);
            t_kondisi_rumah                                 = ""+baris.get(98);
            t_luas_jalan_masuk_rumah                        = ""+baris.get(99);
            t_status_kepemilikan_rumah_pemohon              = ""+baris.get(100);
            t_furniture                                     = ""+baris.get(101);

            t_jarak_tempat_usaha_dari_rumah                 = ""+baris.get(102);
            t_status_kepemilikan_usaha                      = ""+baris.get(103);
            t_bentuk_bangunan_tempat_usaha                  = ""+baris.get(104);
            t_lokasi_tempat_usaha                           = ""+baris.get(105);
            t_jumlah_karyawan                               = ""+baris.get(106);
            t_lama_pemohon_menempati_tempat_usaha_tahun     = ""+baris.get(107);
            t_lama_pemohon_menempati_tempat_usaha_bulan     = ""+baris.get(108);
            t_lama_usaha_berdiri_tahun                      = ""+baris.get(109);
            t_lama_usaha_berdiri_bulan                      = ""+baris.get(110);
            t_pekerjaan_or_usaha_terkait_ekspor_or_impor    = ""+baris.get(111);

            t_tujuan_penggunaan_unit                        = ""+baris.get(112);
            t_kondisi_mobil                                 = ""+baris.get(113);
            t_bagian_kondisi_yang_tidak_baik                = ""+baris.get(114);
            t_lama_kepemilikan_kendaraan_tahun              = ""+baris.get(115);
            t_lama_kepemilikan_kendaraan_bulan              = ""+baris.get(116);

            t_jumlah_tanggungan                             = ""+baris.get(117);
            t_jumlah_anak                                   = ""+baris.get(118);

            t_omzet_or_penghasilan_gross                    = ""+baris.get(119);
            t_penghasilan_nett_or_take_home_pay             = ""+baris.get(120);
            t_penghasilan_pasangan                          = ""+baris.get(121);
            t_penghasilan_tambahan                          = ""+baris.get(122);
            t_pengeluaran_or_kebutuhan_hidup                = ""+baris.get(123);
            t_total_cicilan_leasing_lain                    = ""+baris.get(124);
            t_balance_terakhir_di_rekening_tabungan         = ""+baris.get(125);
            t_rata_rata_mutasi_in_3_bulan_terakhir          = ""+baris.get(126);
            t_rata_rata_mutasi_out_3_bulan_terakhir         = ""+baris.get(127);

            t_collectabilitas_sid_or_slik_tertinggi         = ""+baris.get(128);
            t_pernah_kredit_di_tempat_lain                  = ""+baris.get(129);
            t_overdue_tertinggi                             = ""+baris.get(130);
            t_baki_debet_or_outstanding_hutang              = ""+baris.get(131);
            t_nama_finance_company                          = ""+baris.get(132);
            t_alasan_menunggak_khusus_lebih_dari_coll_2     = ""+baris.get(133);

            t_apakah_direkomendasikan                       = ""+baris.get(134);
            t_alasan_or_point_penting_rekomendasi_anda      = ""+baris.get(135);





            //1
            if(t_nama.equals("null")){
                t_nama = "";
            }
            if(t_mother.equals("null")){
                t_mother = "";
            }
            if(t_title.equals("null")){
                t_title = "";
            }
            if(t_marital_status.equals("null")){
                t_marital_status = "";
            }
            if(t_identity_type.equals("null")){
                t_identity_type = "";
            }


            //6
            if(t_Npwp_no.equals("null")){
                t_Npwp_no = "";
            }
            if(t_Birth_place.equals("null")){
                t_Birth_place = "";
            }
            if(t_Birth_date.equals("null")){
                t_Birth_date = "";
            }
            if(t_Identity_no.equals("null")){
                t_Identity_no = "";
            }
            if(t_address_ktp.equals("null")){
                t_address_ktp = "";
            }


            //11
            if(t_province_ktp.equals("null")){
                t_province_ktp = "";
            }
            if (t_id_province_ktp.equals("null")){
                t_id_province_ktp = "";
            }
            if(t_kab_kodya_ktp.equals("null")){
                t_kab_kodya_ktp = "";
            }
            if (t_id_kab_kodya_ktp.equals("null")){
                t_id_kab_kodya_ktp = "";
            }
            if(t_kecamatan_ktp.equals("null")){
                t_kecamatan_ktp = "";
            }
            if (t_id_kecamatan_ktp.equals("null")){
                t_id_kecamatan_ktp = "";
            }
            if(t_kelurahan_ktp.equals("null")){
                t_kelurahan_ktp = "";
            }
            if (t_id_kelurahan_ktp.equals("null")){
                t_id_kelurahan_ktp = "";
            }
            if(t_sandi_dati_2_ktp.equals("null")){
                t_sandi_dati_2_ktp = "";
            }


            //16
            if(t_postal_code_ktp.equals("null")){
                t_postal_code_ktp = "";
            }
            if (t_zipcode_ktp.equals("null")){
                t_zipcode_ktp = "";
            }
            if(t_address_home.equals("null")){
                t_address_home = "";
            }
            if(t_province_home.equals("null")){
                t_province_home = "";
            }
            if(t_kab_kodya_home.equals("null")){
                t_kab_kodya_home = "";
            }
            if(t_kecamatan_home.equals("null")){
                t_kecamatan_home = "";
            }


            //21
            if(t_kelurahan_home.equals("null")){
                t_kelurahan_home = "";
            }
            if(t_sandi_dati_2_home.equals("null")){
                t_sandi_dati_2_home = "";
            }
            if(t_postal_code_home.equals("null")){
                t_postal_code_home = "";
            }
            if(t_mail_address.equals("null")){
                t_mail_address = "";
            }
            if(t_education.equals("null")){
                t_education = "";
            }


            //26
            if(t_sex.equals("null")){
                t_sex = "";
            }
            if(t_Nama_panggilan.equals("null")){
                t_Nama_panggilan = "";
            }
            if(t_Sandi_lahir.equals("null")){
                t_Sandi_lahir = "";
            }
            if(t_religion.equals("null")){
                t_religion = "";
            }
            if(t_Stay_length.equals("null")){
                t_Stay_length = "";
            }


            //31
            if(t_Telephone.equals("null")){
                t_Telephone = "";
            }
            if(t_Telephone_2.equals("null")){
                t_Telephone_2 = "";
            }
            if(t_Handphone_1.equals("null")){
                t_Handphone_1 = "";
            }
            if(t_Handphone_2.equals("null")){
                t_Handphone_2 = "";
            }
            if(t_email.equals("null")){
                t_email = "";
            }


            //36
            if(t_Pekerjaan.equals("null")){
                t_Pekerjaan = "";
            }
            if(t_job_title.equals("null")){
                t_job_title = "";
            }
            if(t_Name_economy_code.equals("null")){
                t_Name_economy_code = "";
            }
            if(t_Economy_code.equals("null")){
                t_Economy_code = "";
            }
            if(t_Company_name.equals("null")){
                t_Company_name = "";
            }


            //41
            if(t_Company_address.equals("null")){
                t_Company_address = "";
            }
            if(t_Company_province.equals("null")){
                t_Company_province = "";
            }
            if(t_Company_kab_kodya.equals("null")){
                t_Company_kab_kodya = "";
            }
            if(t_Company_kecamatan.equals("null")){
                t_Company_kecamatan = "";
            }
            if(t_Company_kelurahan.equals("null")){
                t_Company_kelurahan = "";
            }


            //46
            if(t_Company_sandi_dati_2.equals("null")){
                t_Company_sandi_dati_2 = "";
            }
            if(t_Company_postal_code.equals("null")){
                t_Company_postal_code = "";
            }
            if(t_Company_telephone_1.equals("null")){
                t_Company_telephone_1 = "";
            }
            if(t_Company_telephone_2.equals("null")){
                t_Company_telephone_2 = "";
            }
            if(t_Line_of_business.equals("null")){
                t_Line_of_business = "";
            }


            //51
            if(t_Estabilished_since.equals("null")){
                t_Estabilished_since = "";
            }
            if(t_Company_fax_1.equals("null")){
                t_Company_fax_1 = "";
            }
            if(t_Spouse_name.equals("null")){
                t_Spouse_name = "";
            }
            if(t_spouse_title.equals("null")){
                t_spouse_title = "";
            }
            if(t_spouse_religion.equals("null")){
                t_spouse_religion = "";
            }


            //56
            if(t_spouse_sex.equals("null")){
                t_spouse_sex = "";
            }
            if(t_spouse_identity_type.equals("null")){
                t_spouse_identity_type = "";
            }
            if(t_Spouse_identity_no.equals("null")){
                t_Spouse_identity_no = "";
            }
            if(t_Spouse_birth_place_or_sandi_lahir.equals("null")){
                t_Spouse_birth_place_or_sandi_lahir = "";
            }
            if(t_Spouse_date_of_birth.equals("null")){
                t_Spouse_date_of_birth = "";
            }


            //61
            if(t_Spouse_address.equals("null")){
                t_Spouse_address = "";
            }
            if(t_Spouse_province.equals("null")){
                t_Spouse_province = "";
            }
            if (t_Spouse_id_province.equals("null")){
                t_Spouse_id_province = "";
            }
            if(t_Spouse_kab_kodya.equals("null")){
                t_Spouse_kab_kodya = "";
            }
            if (t_Spouse_id_kab_kodya.equals("null")){
                t_Spouse_id_kab_kodya = "";
            }
            if(t_Spouse_kecamatan.equals("null")){
                t_Spouse_kecamatan = "";
            }
            if (t_Spouse_id_kecamatan.equals("null")){
                t_Spouse_id_kecamatan = "";
            }
            if(t_Spouse_kelurahan.equals("null")){
                t_Spouse_kelurahan = "";
            }
            if (t_Spouse_id_kelurahan.equals("null")){
                t_Spouse_id_kelurahan = "";
            }


            //66
            if(t_Spouse_postal_code.equals("null")){
                t_Spouse_postal_code = "";
            }
            if (t_Spouse_zipcode.equals("null")){
                t_Spouse_zipcode = "";
            }
            if(t_Spouse_sandi_dati_2.equals("null")){
                t_Spouse_sandi_dati_2 = "";
            }
            if(t_Spouse_no_handphone.equals("null")){
                t_Spouse_no_handphone = "";
            }
            if(t_spouse_occupation_or_pekerjaan.equals("null")){
                t_spouse_occupation_or_pekerjaan = "";
            }
            if(t_Spouse_company_name.equals("null")){
                t_Spouse_company_name = "";
            }


            //71
            if(t_spouse_job_title.equals("null")){
                t_spouse_job_title = "";
            }
            if(t_Spouse_line_of_business.equals("null")){
                t_Spouse_line_of_business = "";
            }
            if(t_Spouse_company_address.equals("null")){
                t_Spouse_company_address = "";
            }
            if(t_Spouse_company_telephone.equals("null")){
                t_Spouse_company_telephone = "";
            }
            if(t_Spouse_fax.equals("null")){
                t_Spouse_fax = "";
            }


            //76
            if(t_has_contact_person.equals("null")){
                t_has_contact_person = "";
            }
            if(t_contact_name.equals("null")){
                t_contact_name = "";
            }
            if(t_Contact_address.equals("null")){
                t_Contact_address = "";
            }
            if(t_Contact_province.equals("null")){
                t_Contact_province = "";
            }
            if(t_Contact_kab_kodya.equals("null")){
                t_Contact_kab_kodya = "";
            }


            //81
            if(t_Contact_kecamatan.equals("null")){
                t_Contact_kecamatan = "";
            }
            if(t_Contact_kelurahan.equals("null")){
                t_Contact_kelurahan = "";
            }
            if(t_Contact_sandi_dati_2.equals("null")){
                t_Contact_sandi_dati_2 = "";
            }
            if(t_Contact_postal_code.equals("null")){
                t_Contact_postal_code = "";
            }
            if(t_contact_telephone.equals("null")){
                t_contact_telephone = "";
            }


            //86
            if(t_relationship.equals("null")){
                t_relationship = "";
            }
            if(t_tipe_rumah.equals("null")){
                t_tipe_rumah = "";
            }
            if(t_home_status.equals("null")){
                t_home_status = "";
            }
            if(t_jarak_rumah_ke_cabang.equals("null")){
                t_jarak_rumah_ke_cabang = "";
            }
            if(t_luas_tanah.equals("null")){
                t_luas_tanah = "";
            }


            //91
            if(t_luas_bangunan_rumah.equals("null")){
                t_luas_bangunan_rumah = "";
            }
            if(t_status_kepemilikan_rumah.equals("null")){
                t_status_kepemilikan_rumah = "";
            }
            if(t_klasifikasi_perumahan.equals("null")){
                t_klasifikasi_perumahan = "";
            }
            if(t_tempat_menaruh_kendaraan.equals("null")){
                t_tempat_menaruh_kendaraan = "";
            }
            if(t_status_garasi_kendaraan.equals("null")){
                t_status_garasi_kendaraan = "";
            }


            //96
            if(t_bentuk_bangunan_rumah.equals("null")){
                t_bentuk_bangunan_rumah = "";
            }
            if(t_kondisi_rumah.equals("null")){
                t_kondisi_rumah = "";
            }
            if(t_luas_jalan_masuk_rumah.equals("null")){
                t_luas_jalan_masuk_rumah = "";
            }
            if(t_status_kepemilikan_rumah_pemohon.equals("null")){
                t_status_kepemilikan_rumah_pemohon = "";
            }
            if(t_furniture.equals("null")){
                t_furniture = "";
            }


            //101
            if(t_jarak_tempat_usaha_dari_rumah.equals("null")){
                t_jarak_tempat_usaha_dari_rumah = "";
            }
            if(t_status_kepemilikan_usaha.equals("null")){
                t_status_kepemilikan_usaha = "";
            }
            if(t_bentuk_bangunan_tempat_usaha.equals("null")){
                t_bentuk_bangunan_tempat_usaha = "";
            }
            if(t_lokasi_tempat_usaha.equals("null")){
                t_lokasi_tempat_usaha = "";
            }
            if(t_jumlah_karyawan.equals("null")){
                t_jumlah_karyawan = "";
            }


            //106
            if(t_lama_pemohon_menempati_tempat_usaha_tahun.equals("null")){
                t_lama_pemohon_menempati_tempat_usaha_tahun = "";
            }
            if(t_lama_pemohon_menempati_tempat_usaha_bulan.equals("null")){
                t_lama_pemohon_menempati_tempat_usaha_bulan = "";
            }
            if(t_lama_usaha_berdiri_tahun.equals("null")){
                t_lama_usaha_berdiri_tahun = "";
            }
            if(t_lama_usaha_berdiri_bulan.equals("null")){
                t_lama_usaha_berdiri_bulan = "";
            }
            if(t_pekerjaan_or_usaha_terkait_ekspor_or_impor.equals("null")){
                t_pekerjaan_or_usaha_terkait_ekspor_or_impor = "";
            }


            //111
            if(t_tujuan_penggunaan_unit.equals("null")){
                t_tujuan_penggunaan_unit = "";
            }
            if(t_kondisi_mobil.equals("null")){
                t_kondisi_mobil = "";
            }
            if(t_bagian_kondisi_yang_tidak_baik.equals("null")){
                t_bagian_kondisi_yang_tidak_baik = "";
            }
            if(t_lama_kepemilikan_kendaraan_tahun.equals("null")){
                t_lama_kepemilikan_kendaraan_tahun = "";
            }
            if(t_lama_kepemilikan_kendaraan_bulan.equals("null")){
                t_lama_kepemilikan_kendaraan_bulan = "";
            }


            //116
            if(t_jumlah_tanggungan.equals("null")){
                t_jumlah_tanggungan = "";
            }
            if(t_jumlah_anak.equals("null")){
                t_jumlah_anak = "";
            }
            if(t_omzet_or_penghasilan_gross.equals("null")){
                t_omzet_or_penghasilan_gross = "";
            }
            if(t_penghasilan_nett_or_take_home_pay.equals("null")){
                t_penghasilan_nett_or_take_home_pay = "";
            }
            if(t_penghasilan_pasangan.equals("null")){
                t_penghasilan_pasangan = "";
            }


            //121
            if(t_penghasilan_tambahan.equals("null")){
                t_penghasilan_tambahan = "";
            }
            if(t_pengeluaran_or_kebutuhan_hidup.equals("null")){
                t_pengeluaran_or_kebutuhan_hidup = "";
            }
            if(t_total_cicilan_leasing_lain.equals("null")){
                t_total_cicilan_leasing_lain = "";
            }
            if(t_balance_terakhir_di_rekening_tabungan.equals("null")){
                t_balance_terakhir_di_rekening_tabungan = "";
            }
            if(t_rata_rata_mutasi_in_3_bulan_terakhir.equals("null")){
                t_rata_rata_mutasi_in_3_bulan_terakhir = "";
            }


            //126
            if(t_rata_rata_mutasi_out_3_bulan_terakhir.equals("null")){
                t_rata_rata_mutasi_out_3_bulan_terakhir = "";
            }
            if(t_collectabilitas_sid_or_slik_tertinggi.equals("null")){
                t_collectabilitas_sid_or_slik_tertinggi = "";
            }
            if(t_pernah_kredit_di_tempat_lain.equals("null")){
                t_pernah_kredit_di_tempat_lain = "";
            }
            if(t_overdue_tertinggi.equals("null")){
                t_overdue_tertinggi = "";
            }
            if(t_baki_debet_or_outstanding_hutang.equals("null")){
                t_baki_debet_or_outstanding_hutang = "";
            }


            //131
            if(t_nama_finance_company.equals("null")){
                t_nama_finance_company = "";
            }
            if(t_alasan_menunggak_khusus_lebih_dari_coll_2.equals("null")){
                t_alasan_menunggak_khusus_lebih_dari_coll_2 = "";
            }
            if(t_apakah_direkomendasikan.equals("null")){
                t_apakah_direkomendasikan = "";
            }
            if(t_alasan_or_point_penting_rekomendasi_anda.equals("null")){
                t_alasan_or_point_penting_rekomendasi_anda = "";
            }

            saveData();
        }
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
        OtherUtil.showAlertDialogLoading(this, "Please Wait ...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPLOAD_URL_UP2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            //success = jObj.getInt(TAG_SUCCESS);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                Log.d("v Add", jObj.toString());

                                dm.deleteTugasAllId("Inputan",get_id_order);
                                dm.deleteSurvey(get_id_order);
                                dm.addRow_status(get_username, get_userid, "Approve");

                                dm.addRowStatusTerkirim(get_id_order);
                                /// cek_foto();
                                OtherUtil.hideAlertDialog();
                                dialog.dismiss();
                                finish();
                            } else {
                                Toast.makeText(InputDataFullActivity.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                OtherUtil.hideAlertDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            OtherUtil.hideAlertDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resError++;
                        if(resError > 3){
                            OtherUtil.hideAlertDialog();
                            dialog.dismiss();
                            CustomDialog_gagal_kirim();
                            resError = 1;
                        }else {
                            cekJson();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("nama", t_nama);//1
                params.put("mother", t_mother);//2
                params.put("title", t_title);//3
                params.put("marital_status", t_marital_status);//4
                params.put("identity_type", t_identity_type);//5
                params.put("npwp_no", t_Npwp_no);//6
                params.put("birth_place", t_Birth_place);//7
                params.put("birth_date", t_Birth_date);//8
                params.put("address_ktp", t_address_ktp);//9
                params.put("province_ktp", t_province_ktp);//10
                //params.put("id_province_ktp",t_id_province_ktp);
                params.put("kab_kodya_ktp", t_kab_kodya_ktp);//11
                params.put("kecamatan_ktp", t_kecamatan_ktp);//12
                params.put("kelurahan_ktp", t_kelurahan_ktp);//13
                params.put("sandi_dati_2_ktp", t_sandi_dati_2_ktp);//14
                params.put("postal_code_ktp", t_postal_code_ktp);//15
                params.put("address_home", t_address_home);//16
                params.put("province_home", t_province_home);//17
                params.put("kab_kodya_home", t_kab_kodya_home);//18
                params.put("kecamatan_home", t_kecamatan_home);//19
                params.put("kelurahan_home", t_kelurahan_home);//20
                params.put("sandi_dati_2_home", t_sandi_dati_2_home);//21
                params.put("postal_code_home", t_postal_code_home);//22
                params.put("mail_address", t_mail_address);//23
                params.put("tipe_rumah", t_tipe_rumah);//24
                params.put("home_status", t_home_status);//25
                params.put("telephone", t_Telephone);//26
                params.put("telephone_2", t_Telephone_2);//27
                params.put("education", t_education);//28
                params.put("sex", t_sex);//29
                params.put("nama_panggilan", t_Nama_panggilan);//30
                params.put("identity_no", t_Identity_no);//31
                params.put("sandi_lahir", t_Sandi_lahir);//32
                params.put("religion", t_religion);//33
                params.put("stay_length", t_Stay_length);//34
                params.put("handphone_1", t_Handphone_1);//35
                params.put("handphone_2", t_Handphone_2);//36
                params.put("email", t_email);//37

                params.put("pekerjaan", t_Pekerjaan);//38
                params.put("job_title", t_job_title);//39
                params.put("name_economy_code", t_Name_economy_code);//40
                params.put("company_name", t_Company_name);//41
                params.put("company_address", t_Company_address);//42
                params.put("company_province", t_Company_province);//43
                params.put("company_kab_kodya", t_Company_kab_kodya);//44
                params.put("company_kecamatan", t_Company_kecamatan);//45
                params.put("company_kelurahan", t_Company_kelurahan);//46
                params.put("company_sandi_dati_2", t_Company_sandi_dati_2);//47
                params.put("company_postal_code", t_Company_postal_code);//48
                params.put("company_telephone_1", t_Company_telephone_1);//49
                params.put("company_telephone_2", t_Company_telephone_2);//50
                params.put("line_of_business", t_Line_of_business);//51
                params.put("economy_code", t_Economy_code);//52
                params.put("estabilished_since", t_Estabilished_since);//53
                params.put("company_fax_1", t_Company_fax_1);//54

                params.put("spouse_name", t_Spouse_name);//55
                params.put("spouse_title", t_spouse_title);//56
                params.put("spouse_identity_type", t_spouse_identity_type);//57
                params.put("spouse_birth_place_or_sandi_lahir", t_Spouse_birth_place_or_sandi_lahir);//58
                params.put("spouse_religion", t_spouse_religion);//59
                params.put("spouse_address", t_Spouse_address);//60
                params.put("spouse_province", t_Spouse_province);//61
                params.put("spouse_kab_kodya", t_Spouse_kab_kodya);//62
                params.put("spouse_kecamatan", t_Spouse_kecamatan);//63
                params.put("spouse_kelurahan", t_Spouse_kelurahan);//64
                params.put("spouse_sandi_dati_2", t_Spouse_sandi_dati_2);//65
                params.put("spouse_postal_code", t_Spouse_postal_code);//66
                params.put("spouse_no_handphone", t_Spouse_no_handphone);//67
                params.put("spouse_occupation_or_pekerjaan", t_spouse_occupation_or_pekerjaan);//68
                params.put("spouse_company_name", t_Spouse_company_name);//69
                params.put("spouse_company_address", t_Spouse_company_address);//70
                params.put("spouse_company_telephone", t_Spouse_company_telephone);//71
                params.put("spouse_line_of_business", t_Spouse_line_of_business);//72
                params.put("spouse_job_title", t_spouse_job_title);//73
                params.put("spouse_sex", t_spouse_sex);//74
                params.put("spouse_identity_no", t_Spouse_identity_no);//75
                params.put("spouse_date_of_birth", t_Spouse_date_of_birth);//76
                params.put("spouse_fax", t_Spouse_fax);//77

                params.put("has_contact_person", t_has_contact_person);//78
                params.put("contact_name", t_contact_name);//79
                params.put("contact_address", t_Contact_address);//80
                params.put("contact_province", t_Contact_province);//81
                params.put("contact_kab_kodya", t_Contact_kab_kodya);//82
                params.put("contact_kecamatan", t_Contact_kecamatan);//83
                params.put("contact_kelurahan", t_Contact_kelurahan);//84
                params.put("contact_sandi_dati_2", t_Contact_sandi_dati_2);//85
                params.put("contact_postal_code", t_Contact_postal_code);//86
                params.put("contact_telephone", t_contact_telephone);//87
                params.put("relationship", t_relationship);//88

                params.put("jarak_rumah_ke_cabang", t_jarak_rumah_ke_cabang);//89
                params.put("luas_tanah", t_luas_tanah);//90
                params.put("luas_bangunan_rumah", t_luas_bangunan_rumah);//91
                params.put("status_kepemilikan_rumah", t_status_kepemilikan_rumah);//92
                params.put("klasifikasi_perumahan", t_klasifikasi_perumahan);//93
                params.put("tempat_menaruh_kendaraan", t_tempat_menaruh_kendaraan);//94
                params.put("status_garasi_kendaraan", t_status_garasi_kendaraan);//95
                params.put("bentuk_bangunan_rumah", t_bentuk_bangunan_rumah);//96
                params.put("kondisi_rumah", t_kondisi_rumah);//97
                params.put("luas_jalan_masuk_rumah", t_luas_jalan_masuk_rumah);//98
                params.put("status_kepemilikan_rumah_pemohon", t_status_kepemilikan_rumah_pemohon);//99
                params.put("furniture", t_furniture);//100
                params.put("jarak_tempat_usaha_dari_rumah", t_jarak_tempat_usaha_dari_rumah);//101
                params.put("status_kepemilikan_usaha", t_status_kepemilikan_usaha);//102
                params.put("bentuk_bangunan_tempat_usaha", t_bentuk_bangunan_tempat_usaha);//103
                params.put("lokasi_tempat_usaha", t_lokasi_tempat_usaha);//104
                params.put("jumlah_karyawan", t_jumlah_karyawan);//105
                params.put("lama_pemohon_menempati_tempat_usaha_tahun", t_lama_pemohon_menempati_tempat_usaha_tahun);//106
                params.put("lama_pemohon_menempati_tempat_usaha_bulan", t_lama_pemohon_menempati_tempat_usaha_bulan);//107
                params.put("lama_usaha_berdiri_tahun", t_lama_usaha_berdiri_tahun);//108
                params.put("lama_usaha_berdiri_bulan", t_lama_usaha_berdiri_bulan);//109
                params.put("pekerjaan_or_usaha_terkait_ekspor_or_impor", t_pekerjaan_or_usaha_terkait_ekspor_or_impor);//110
                params.put("tujuan_penggunaan_unit", t_tujuan_penggunaan_unit);//111
                params.put("kondisi_mobil", t_kondisi_mobil);//112
                params.put("bagian_kondisi_yang_tidak_baik", t_bagian_kondisi_yang_tidak_baik);//113
                params.put("lama_kepemilikan_kendaraan_tahun", t_lama_kepemilikan_kendaraan_tahun);//114
                params.put("lama_kepemilikan_kendaraan_bulan", t_lama_kepemilikan_kendaraan_bulan);//115
                params.put("jumlah_tanggungan", t_jumlah_tanggungan);//116
                params.put("jumlah_anak", t_jumlah_anak);//117
                params.put("omzet_or_penghasilan_gross", t_omzet_or_penghasilan_gross);//118
                params.put("penghasilan_nett_or_take_home_pay", t_penghasilan_nett_or_take_home_pay);//119
                params.put("penghasilan_pasangan", t_penghasilan_pasangan);//120
                params.put("penghasilan_tambahan", t_penghasilan_tambahan);//121
                params.put("pengeluaran_or_kebutuhan_hidup", t_pengeluaran_or_kebutuhan_hidup);//122
                params.put("total_cicilan_leasing_lain", t_total_cicilan_leasing_lain);//123
                params.put("balance_terakhir_di_rekening_tabungan", t_balance_terakhir_di_rekening_tabungan);//124
                params.put("rata_rata_mutasi_in_3_bulan_terakhir", t_rata_rata_mutasi_in_3_bulan_terakhir);//125
                params.put("rata_rata_mutasi_out_3_bulan_terakhir", t_rata_rata_mutasi_out_3_bulan_terakhir);//126
                params.put("collectabilitas_sid_or_slik_tertinggi", t_collectabilitas_sid_or_slik_tertinggi);//127
                params.put("pernah_kredit_di_tempat_lain", t_pernah_kredit_di_tempat_lain);//128
                params.put("overdue_tertinggi", t_overdue_tertinggi);//129
                params.put("baki_debet_or_outstanding_hutang", t_baki_debet_or_outstanding_hutang);//130
                params.put("nama_finance_company", t_nama_finance_company);//131
                params.put("alasan_menunggak_khusus_lebih_dari_coll_2", t_alasan_menunggak_khusus_lebih_dari_coll_2);//132
                params.put("apakah_direkomendasikan", t_apakah_direkomendasikan);//133
                params.put("alasan_or_point_penting_rekomendasi_anda", t_alasan_or_point_penting_rekomendasi_anda);//134

                //Tambahan
                params.put("kode_propinsi_ktp",t_id_province_ktp);
                params.put("kode_kab_kodya_ktp",t_id_kab_kodya_ktp);
                params.put("kode_kec_ktp",t_id_kecamatan_ktp);
                params.put("kode_kel_ktp",t_id_kelurahan_ktp);
                params.put("zipcode_ktp",t_zipcode_ktp);
                params.put("kode_propinsi_spouse",t_Spouse_id_province);
                params.put("kode_kab_kodya_spouse",t_Spouse_id_kab_kodya);
                params.put("kode_kec_spouse",t_Spouse_id_kecamatan);
                params.put("kode_kel_spouse",t_Spouse_id_kelurahan);
                params.put("zipcode_spouse",t_Spouse_zipcode);
                //End Tambahan

                params.put("latitude", ""+latitude);//135
                params.put("longitude", ""+longitude);//136
                params.put("id_surveyor", get_userid);//137
                params.put("id_order", get_id_order);//138
                params.put("tk", setter.APK_CODE);//139


                //kembali ke parameters
                Log.d(TAG, ""+params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    /*public void cek_foto(){
        ArrayList<ArrayList<Object>> dataphoto = dm.ambilBarisPhotoAll();
        if (dataphoto.size() > 0) {
            ArrayList<Object> baris = dataphoto.get(0);
            photo_nama      = baris.get(0).toString();
            photo_bitmap    = baris.get(2).toString();
            photo_latitude  = baris.get(3).toString();
            photo_longitude = baris.get(4).toString();
            photo_id_order  = baris.get(5).toString();
            photo_status    = baris.get(6).toString();

            if(photo_status.equals("1")){
                simpan_photo(photo_nama,photo_bitmap,photo_latitude,photo_longitude,photo_id_order);
            }else{
                loading.dismiss();
                finish();
            }
        }
    }*/

    /*public void simpan_photo(String s_photo_name, String s_photo_bitmap, String s_photo_latitude,
                             String s_photo_longitude, String s_photo_id_order){
        p_photo_nama = s_photo_name;
        p_photo_bitmap = s_photo_bitmap;
        p_photo_latitude = s_photo_latitude;
        p_photo_longitude = s_photo_longitude;
        p_photo_id_order = s_photo_id_order;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                setter.UPLOAD_URL_PHOTO, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        JSONObject jObjck = null;
                        try {
                            jObjck = new JSONObject(response);
                            //success = jObjck.getInt(TAG_SUCCESS);
                            String code = jObjck.getString("code");
                            String pesan_hasil = jObjck.getString(TAG_MESSAGE);
                            if (code.equals("200")) {
                                dm.deletePhotoAll(p_photo_nama,p_photo_id_order);
                                cek_foto();
                            } else {
                                Toast.makeText(InputDataFullActivity.this,"gagal simpan photo "+pesan_hasil , Toast.LENGTH_LONG).show();
                                loading.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //                  Toast.makeText(HomeActivity.this, "Tidak Ada Koneksi "+stenor, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("photo_nama", p_photo_nama);
                params.put("photo_bitmap", p_photo_bitmap);
                params.put("photo_latitude", p_photo_latitude);
                params.put("photo_longitude", p_photo_longitude);
                params.put("id_order", p_photo_id_order);
                params.put("tk", setter.APK_CODE);

                Log.d(TAG, "" + params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }*/

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
    public void onConnected(@Nullable Bundle bundle) {

        lokasiget();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "Personal Data","Occupation Data","Spouse Data",
                "Contact Person Data","Tempat Tinggal(Rumah)", "Pekerjaan/Tempat Usaha",
                "Objek Pembiayaan","Tanggungan" ,"Pendapatan/Kapasitas", "Karakter Bayar",
                "Data Foto","Kesimpulan Survey"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {


            Fragment frag = null;
          //  int i_id_order = Integer.parseInt(get_id_order);
            if (position == 0){
                frag = new InputFullFragmentSatu();
            }else if (position == 1){
                frag = new InputFullFragmentDua();
            }else if (position == 2){
                frag = new InputFullFragmentTiga();
            }else if (position == 3){
                frag = new InputFullFragmentEmpat();
            }else if (position == 4){
                frag = new InputFullFragmentLima();
            }else if (position == 5){
                frag = new InputFullFragmentEnam();
            }else if (position == 6){
                frag = new InputFullFragmentTujuh();
            }else if (position == 7){
                frag = new InputFullFragmentDelapan();
            }else if (position == 8){
                frag = new InputFullFragmentSembilan();
            }else if (position == 9){
                frag = new InputFullFragmentSepuluh();
            }else if (position == 10){
                frag = new InputFullFragmentSebelas();
            }else if (position == 11){
                frag = new InputFullFragmentDuabelas();
            }



            Bundle b = new Bundle();
            b.putInt("position", position);
        //    b.putInt("id_order", i_id_order);
            b.putString("id_order", get_id_order);
            b.putString("get_name", get_name);
            b.putString("get_identity_type", get_identity_type);
            b.putString("get_identity_no", get_identity_no);
            b.putString("get_address_home", get_address_home);
            b.putString("get_telephone", get_telephone);
            b.putString("get_sex", get_sex);
            b.putString("get_handphone_1", get_handphone_1);

            frag.setArguments(b);
            return frag;
            //  return SuperAwesomeCardFragment.newInstance(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input_data, menu);
        //   menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.icon_menu));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info_kirim:
                //loading = ProgressDialog.show(InputDataFullActivity.this, "Saving...", "Please wait...", false, false);
                //simpan();
                String locationProviders = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    Toast.makeText(InputDataFullActivity.this, "GPS belum diaktifkan, Mohon nyalakan GPS anda.",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else {
                    cek_data();
                }
            /*case R.id.info_save:
                simpan_data_all();*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan_data_all() {
        ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);
        if (data.size() < 1) {
            dm.addRowSurveyAll(get_userid,get_id_order,C_Name,C_Mother_maiden_name,
                    C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                    C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                    C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                    C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                    C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                    C_education,C_sex,C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                    C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email,

                    C_pekerjaan, C_job_title,
                    C_Name_economy_code, C_Company_name, C_Company_address,
                    C_Company_province, C_Company_kab_or_kodya, C_Company_kecamatan,
                    C_Company_kelurahan, C_Sandi_dati_2_company, C_Postal_code_company,
                    C_Company_telephone_1, C_Company_telephone_2, C_Line_of_business,
                    C_Economy_code, C_Estabilished_since, C_Company_fax_1,

                    C_Spouse_name,C_spouse_title,
                    C_spouse_identity_type,C_Spouse_identity_no,
                    C_Spouse_birth_place_or_sandi_lahir,C_spouse_religion,C_Spouse_address,
                    C_province_spouse,C_kab_kodya_spouse,C_kecamatan_spouse,
                    C_kelurahan_spouse,C_Sandi_dati_2_spouse,C_Postal_code_spouse,
                    C_Spouse_no_handphone,C_spouse_occupation_or_pekerjaan,
                    C_Spouse_company_name,C_Spouse_company_address,
                    C_Spouse_company_telephone,C_Spouse_line_of_business,
                    C_spouse_job_title,C_spouse_sex,C_Spouse_date_of_birth,C_Spouse_fax,

                    C_has_contact_person,
                    C_Contact_name,C_Contact_address,C_contact_province,
                    C_contact_kab_or_kodya,C_contact_kecamatan,C_contact_kelurahan,
                    C_Contact_sandi_dati_2,C_Contact_Postal_code, C_Contact_telephone,
                    C_relationship,

                    C_tipe_rumah,"",
                    C_Jarak_rumah_ke_cabang,C_Luas_tanah,C_Luas_bangunan_rumah,
                    C_status_kepemilikan_rumah,C_klasifikasi_perumahan,
                    C_tempat_menaruh_kendaraan,C_status_garasi_kendaraan,
                    C_bentuk_bangunan_rumah,C_kondisi_rumah,C_luas_jalan_masuk_rumah,
                    "",C_furniture_or_perabot,

                    C_Jarak_tempat_usaha_dari_rumah,
                    C_status_kepemilikan_usaha,C_bentuk_bangunan_tempat_usaha,
                    C_lokasi_tempat_usaha,C_jumlah_karyawan,
                    C_Lama_pemohon_menempati_tempat_usaha_tahun,
                    C_Lama_pemohon_menempati_tempat_usaha_bulan,
                    C_Lama_usaha_berdiri_tahun,C_Lama_usaha_berdiri_bulan,
                    C_pekerjaan_or_usaha_terkait_ekspor_or_impor,

                    C_tujuan_penggunaan_unit,
                    C_kondisi_mobil,C_etx_bagian_kondisi_tidak_baik,
                    C_Lama_kepemilikan_kendaraan_tahun,C_Lama_kepemilikan_kendaraan_bulan,

                    C_Jumlah_tanggungan,C_Jumlah_anak,

                    C_Omzet_or_penghasilan_gross,
                    C_Penghasilan_nett_or_take_home_pay,C_Penghasilan_pasangan,
                    C_Penghasilan_tambahan,C_Pengeluaran_or_kebutuhan_hidup,
                    C_Total_cicilan_leasing_lain,C_Balance_terakhir_di_rekening_tabungan,
                    C_Rata_rata_mutasi_in_3_bulan_terakhir,
                    C_Rata_rata_mutasi_out_3_bulan_terakhir,

                    C_Collectabilitas_sid_or_slik_tertinggi,C_pernah_kredit_di_tempat_lain,
                    C_Overdue_tertinggi,C_Baki_debet_or_outstanding_hutang,
                    C_Nama_finance_company,C_Alasan_menunggak_khusus_lebih_dari_coll_2,

                    C_apakah_direkomendasikan,
                    C_Alasan_or_point_penting_rekomendasi_anda);
            Toast.makeText(InputDataFullActivity.this, "Simpan Sementara",
                    Toast.LENGTH_LONG).show();
        } else {
            /*dm.updateBarisSurveyAll(get_id_order,C_Name,C_Mother_maiden_name,
                    C_title,C_marital_status,C_identity_type,C_Npwp_no,C_Birth_place,
                    C_Birth_date,C_Identity_no,C_Address_ktp,C_province_ktp,C_kab_kodya_ktp,
                    C_kecamatan_ktp,C_kelurahan_ktp,C_Sandi_dati_2_ktp,C_Postal_code_ktp,
                    C_Address_home,C_province_home,C_kab_kodya_home,C_kecamatan_home,
                    C_kelurahan_home,C_Sandi_dati_2_home,C_Postal_code_home,C_mail_address,
                    C_education,C_sex,C_Nama_panggilan,C_Sandi_lahir,C_religion,C_Stay_length,
                    C_Telephone,C_Telephone_2,C_Handphone_1,C_Handphone_2,C_email,

                    C_pekerjaan, C_job_title,
                    C_Name_economy_code, C_Company_name, C_Company_address,
                    C_Company_province, C_Company_kab_or_kodya, C_Company_kecamatan,
                    C_Company_kelurahan, C_Sandi_dati_2_company, C_Postal_code_company,
                    C_Company_telephone_1, C_Company_telephone_2, C_Line_of_business,
                    C_Economy_code, C_Estabilished_since, C_Company_fax_1,

                    C_Spouse_name,C_spouse_title,
                    C_spouse_identity_type,C_Spouse_identity_no,
                    C_Spouse_birth_place_or_sandi_lahir,C_spouse_religion,C_Spouse_address,
                    C_province_spouse,C_kab_kodya_spouse, C_kecamatan_spouse,
                    C_kelurahan_spouse,C_Sandi_dati_2_spouse, C_Postal_code_spouse,
                    C_Spouse_no_handphone, C_spouse_occupation_or_pekerjaan,
                    C_Spouse_company_name, C_Spouse_company_address,
                    C_Spouse_company_telephone, C_Spouse_line_of_business,
                    C_spouse_job_title, C_spouse_sex, C_Spouse_date_of_birth, C_Spouse_fax,

                    C_has_contact_person,
                    C_Contact_name,C_Contact_address,C_contact_province,
                    C_contact_kab_or_kodya,C_contact_kecamatan,C_contact_kelurahan,
                    C_Contact_sandi_dati_2,C_Contact_Postal_code, C_Contact_telephone,
                    C_relationship,

                    C_tipe_rumah,"",
                    C_Jarak_rumah_ke_cabang,C_Luas_tanah,
                    C_Luas_bangunan_rumah,C_status_kepemilikan_rumah,
                    C_klasifikasi_perumahan,C_tempat_menaruh_kendaraan,
                    C_status_garasi_kendaraan,C_bentuk_bangunan_rumah,C_kondisi_rumah,
                    C_luas_jalan_masuk_rumah,"",
                    C_furniture_or_perabot,

                    C_Jarak_tempat_usaha_dari_rumah,
                    C_status_kepemilikan_usaha,C_bentuk_bangunan_tempat_usaha,
                    C_lokasi_tempat_usaha,C_jumlah_karyawan,
                    C_Lama_pemohon_menempati_tempat_usaha_tahun,
                    C_Lama_pemohon_menempati_tempat_usaha_bulan,C_Lama_usaha_berdiri_tahun,
                    C_Lama_usaha_berdiri_bulan,
                    C_pekerjaan_or_usaha_terkait_ekspor_or_impor,

                    C_tujuan_penggunaan_unit,
                    C_kondisi_mobil,C_etx_bagian_kondisi_tidak_baik,
                    C_Lama_kepemilikan_kendaraan_tahun,C_Lama_kepemilikan_kendaraan_bulan,

                    C_Jumlah_tanggungan,C_Jumlah_anak,

                    C_Omzet_or_penghasilan_gross,
                    C_Penghasilan_nett_or_take_home_pay,C_Penghasilan_pasangan,
                    C_Penghasilan_tambahan,C_Pengeluaran_or_kebutuhan_hidup,
                    C_Total_cicilan_leasing_lain,C_Balance_terakhir_di_rekening_tabungan,
                    C_Rata_rata_mutasi_in_3_bulan_terakhir,
                    C_Rata_rata_mutasi_out_3_bulan_terakhir,

                    C_Collectabilitas_sid_or_slik_tertinggi,
                    C_pernah_kredit_di_tempat_lain,C_Overdue_tertinggi,
                    C_Baki_debet_or_outstanding_hutang,C_Nama_finance_company,
                    C_Alasan_menunggak_khusus_lebih_dari_coll_2,

                    C_apakah_direkomendasikan,
                    C_Alasan_or_point_penting_rekomendasi_anda);*/
            Toast.makeText(InputDataFullActivity.this, "Update Simpan Sementara",
                    Toast.LENGTH_LONG).show();
        }
       // hasil_data();
    }

    public void UpdateGelar(){
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.URL_GELAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                dm.deleteJsonPilihAll("Gelar");
                                dm.addRowJsonPilih(String.valueOf(response),"Gelar");
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
        requestQueue.add(jArr);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
        requestQueue.add(jArr);
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
        requestQueue.add(jArr);
    }

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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
        requestQueue.add(jArr);
    }

    public void UpdateSpousePekerjaan(){
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InputDataFullActivity.this);
        requestQueue.add(jArr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateGelar();
        UpdateTitle();
        UpdateMaritalstatus();
        UpdateIdentityType();
        UpdateMailAddress();
        UpdateTipeRumah();
        UpdateHomeStatus();
        UpdateEducation();
        UpdateSex();
        UpdateReligion();
        UpdateJobTitle();
        UpdateSpousePekerjaan();
        UpdateHasContactPerson();
        UpdateRelationship();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        CustomDialog_back();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtil.hideAlertDialog();
    }
}