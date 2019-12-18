package surveyor.id.com.mobilesurvey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import surveyor.id.com.mobilesurvey.modal.DatabaseManager;

public class ApproveActivity extends AppCompatActivity {

    private TextView Tam_Nama,Tam_Identity_tipe_b,Tam_Identity_no_b,Tam_Address_home_b,
            Tam_Telephone_b,Tam_Sex_b,Tam_Handphone_1_b,Tam_Identity_tipe,Tam_Identity_no,
            Tam_Address_home,Tam_Telephone,Tam_Sex,Tam_Handphone_1,Tam_Kategori_kendaraan,
            Tam_Merk_kendaraan,Tam_Type_kendaraan,Tam_Model_kendaraan,Tam_Tahun_kendaraan,
            Tam_Warna_kendaraan,Tam_Plat_nomor_kendaraan,Tam_Bahan_bakar_kendaraan,
            Tam_Km_kendaraan,Tam_Asuransi,Tam_Otr,Tam_Dp,Tam_Jml_pinjaman,
            Tam_Tenor,Tam_Jaminan_multiguna,Tam_Judul_data_kendaraan_kredit,Tam_name,
            Tam_mother_maiden_name,Tam_gelar,Tam_title,Tam_marital_status,Tam_identity_type,
            Tam_npwp_no,Tam_birth_place,Tam_birth_date,Tam_address_ktp,Tam_sandi_dati_2_ktp,
            Tam_kab_or_kodya_ktp,Tam_postal_code_ktp,Tam_address_home,Tam_sandi_dati_2_home,
            Tam_kab_or_kodya_home,Tam_postal_code_home,Tam_mail_address,Tam_tipe_rumah,
            Tam_home_status,Tam_telephone,Tam_telephone_2,Tam_education,Tam_sex,Tam_nama_panggilan,
            Tam_identity_no,Tam_sandi_lahir,Tam_religion,Tam_kecamatan_ktp,Tam_province_ktp,
            Tam_kelurahan_ktp,Tam_jarak,Tam_kecamatan_home,Tam_province_home,Tam_kelurahan_home,
            Tam_stay_length,Tam_handphone_1,Tam_handphone_2,Tam_email_stay,Tam_pekerjaan,
            Tam_job_title,Tam_name_economy_code,Tam_company_name,Tam_company_address,
            Tam_province_company,Tam_kab_kodya_company,Tam_kecamatan_company,Tam_kelurahan_company,
            Tam_sandi_dati_2_company,Tam_postal_code_company,Tam_company_telephone_1,
            Tam_company_telephone_2,Tam_line_of_business,Tam_economy_code,Tam_estabilished_since,
            Tam_company_fax_1,Tam_spouse_name,Tam_spouse_title,Tam_spouse_identity_type,
            Tam_spouse_birth_place_or_sandi_lahir,Tam_spouse_religion,Tam_spouse_address,
            Tam_province_spouse,Tam_kab_kodya_spouse,Tam_kecamatan_spouse,Tam_kelurahan_spouse,
            Tam_sandi_dati_2_spouse,Tam_postal_code_spouse,Tam_spouse_no_handphone,
            Tam_spouse_occupation_or_pekerjaan,Tam_spouse_company_name,Tam_spouse_company_address,
            Tam_spouse_company_telephone,Tam_spouse_line_of_business,Tam_spouse_job_title,
            Tam_spouse_sex,Tam_spouse_identity_no,Tam_spouse_date_of_birth,Tam_spouse_fax,
            Tam_has_contact_person,Tam_contact_name,Tam_contact_address,Tam_contact_province,
            Tam_contact_kab_or_kodya,Tam_contact_kecamatan,Tam_contact_kelurahan,
            Tam_contact_sandi_dati_2,Tam_contact_Postal_code,Tam_contact_telephone,Tam_relationship;
    private RelativeLayout Tam_Data_Dana_Tunai,Tam_Data_Multiguna,Tam_Data_Kendaraan,
            Tam_Data_Spouse_Data,Tam_Box_km_kendaraan,Tam_Box_otr,Tam_Box_dp;
    private String fk_identity_no,fk_address_home,fk_telephone,fk_sex,fk_handphone_1,fk_asuransi,
            fk_dp,fk_jaminan_multiguna,fk_otr,fk_get_id_order,fk_namalengkap,fk_tempatlahir,
            fk_tgl_lahir,fk_alamat,fk_kategori_kendaraan,fk_status_kendaraan,
            fk_merk_kendaraan, fk_model_kendaraan,fk_type_kendaraan,fk_tahun,fk_warna,fk_plat_nomor,
            fk_km_kendaraan,fk_bahan_bakar,fk_status_survey,fk_latitude,fk_longitude,
            fk_jenis_kredit,fk_jml_pinjaman,fk_tenor;

    private TextView Tam_Jarak_rumah_ke_cabang, Tam_Luas_tanah, Tam_Luas_bangunan_rumah,
            Tam_Status_kepemilikan_rumah, Tam_Klasifikasi_perumahan, Tam_Tempat_menaruh_kendaraan,
            Tam_Status_garasi_kendaraan, Tam_Bentuk_bangunan_rumah, Tam_Kondisi_rumah,
            Tam_Luas_jalan_masuk_rumah, Tam_Status_kepemilikan_rumah_pemohon,
            Tam_Furniture_or_perabot, Tam_Jarak_tempat_usaha_dari_rumah,
            Tam_Status_kepemilikan_usaha, Tam_Bentuk_bangunan_tempat_usaha, Tam_Lokasi_tempat_usaha,
            Tam_Jumlah_karyawan, Tam_Lama_pemohon_menempati_tempat_usaha_tahun,
            Tam_Lama_pemohon_menempati_tempat_usaha_bulan, Tam_Lama_usaha_berdiri_tahun,
            Tam_Lama_usaha_berdiri_bulan, Tam_Pekerjaan_or_usaha_terkait_ekspor_or_impor,
            Tam_Tujuan_penggunaan_unit, Tam_Kondisi_mobil, Tam_Lama_kepemilikan_kendaraan_tahun,
            Tam_Lama_kepemilikan_kendaraan_bulan, Tam_Jumlah_tanggungan, Tam_Jumlah_anak,
            Tam_Omzet_or_penghasilan_gross, Tam_Penghasilan_nett_or_take_home_pay,
            Tam_Penghasilan_pasangan, Tam_Penghasilan_tambahan, Tam_Pengeluaran_or_kebutuhan_hidup,
            Tam_Total_cicilan_leasing_lain, Tam_Balance_terakhir_di_rekening_tabungan,
            Tam_Rata_rata_mutasi_in_3_bulan_terakhir, Tam_Rata_rata_mutasi_out_3_bulan_terakhir,
            Tam_Collectabilitas_sid_or_slik_tertinggi, Tam_Pernah_kredit_di_tempat_lain,
            Tam_Overdue_tertinggi, Tam_Baki_debet_or_outstanding_hutang, Tam_Nama_finance_company,
            Tam_Alasan_menunggak_khusus_lebih_dari_coll_2, Tam_Apakah_direkomendasikan,
            Tam_Alasan_or_point_penting_rekomendasi_anda;
    private DatabaseManager dm;
    private String fk_jarak_rumah_ke_cabang, fk_luas_tanah, fk_luas_bangunan_rumah,
            fk_status_kepemilikan_rumah, fk_klasifikasi_perumahan, fk_tempat_menaruh_kendaraan,
            fk_status_garasi_kendaraan, fk_bentuk_bangunan_rumah, fk_kondisi_rumah,
            fk_luas_jalan_masuk_rumah, fk_status_kepemilikan_rumah_pemohon, fk_furniture,
            fk_jarak_tempat_usaha_dari_rumah, fk_status_kepemilikan_usaha,
            fk_bentuk_bangunan_tempat_usaha, fk_lokasi_tempat_usaha, fk_jumlah_karyawan,
            fk_lama_pemohon_menempati_tempat_usaha, fk_bulan_lama_pemohon_menempati_tempat_usaha,
            fk_tahun_lama_pemohon_menempati_tempat_usaha, fk_lama_usaha_berdiri,
            fk_bulan_lama_usaha_berdiri, fk_tahun_lama_usaha_berdiri,
            fk_pekerjaan_or_usaha_terkait_ekspor_or_impor, fk_tujuan_penggunaan_unit,
            fk_kondisi_mobil, fk_lama_kepemilikan_kendaraan, fk_bulan_lama_kepemilikan_kendaraan,
            fk_tahun_lama_kepemilikan_kendaraan, fk_jumlah_tanggungan, fk_jumlah_anak,
            fk_omzet_or_penghasilan_gross, fk_penghasilan_nett_or_take_home_pay,
            fk_penghasilan_pasangan, fk_penghasilan_tambahan, fk_pengeluaran_or_kebutuhan_hidup,
            fk_total_cicilan_leasing_lain, fk_balance_terakhir_di_rekening_tabungan,
            fk_rata_rata_mutasi_in_3_bulan_terakhir, fk_rata_rata_mutasi_out_3_bulan_terakhir,
            fk_collectabilitas_sid_or_slik_tertinggi, fk_pernah_kredit_di_tempat_lain,
            fk_overdue_tertinggi, fk_baki_debet_or_outstanding_hutang, fk_nama_finance_company,
            fk_alasan_menunggak_khusus_lebih_dari_coll_2, fk_apakah_direkomendasikan,
            fk_alasan_or_point_penting_rekomendasi_anda;
    private String tam_json,fk_mother_maiden_name,fk_gelar,fk_title,fk_marital_status,
            fk_identity_type, fk_npwp_no,fk_sandi_dati_2_ktp,fk_kab_or_kodya_ktp,fk_postal_code_ktp,
            fk_sandi_dati_2_home,fk_kab_or_kodya_home,fk_postal_code_home,fk_mail_address,
            fk_tipe_rumah,fk_home_status,fk_telephone_2,fk_education,fk_nama_panggilan,
            fk_sandi_lahir,fk_religion,fk_kecamatan_ktp,fk_province_ktp,fk_kelurahan_ktp,fk_jarak_customer,
            fk_kecamatan_home,fk_province_home,fk_kelurahan_home,fk_stay_length,fk_handphone_2,
            fk_email_stay,fk_pekerjaan,fk_job_title_pekerjaan,fk_name_economy_code,fk_company_name,
            fk_address_pekerjaan,fk_sandi_dati_2_pekerjaan,fk_kab_or_kodya_pekerjaan,
            fk_postal_code_pekerjaan,fk_telephone_1_pekerjaan,fk_telephone_2_pekerjaan,
            fk_line_of_business,fk_economy_code,fk_estabilished_since,fk_kecamatan_pekerjaan,
            fk_province_pekerjaan,fk_kelurahan_pekerjaan,fk_fax_1_pekerjaan,fk_name_spouse,
            fk_title_spouse,fk_identity_type_spouse,fk_birth_place_spouse,fk_religion_spouse,
            fk_address_spouse,fk_sandi_dati_2_spouse,fk_kab_or_kodya_spouse,fk_postal_code_spouse,
            fk_no_handphone,fk_occupation_or_pekerjaan_spouse,fk_company_name_spouse,
            fk_address_pekerjaan_spouse,fk_telephone_spouse,fk_line_of_business_spouse,
            fk_job_title_spouse,fk_sex_spouse,fk_identity_no_spouse,fk_date_of_birth_spouse,
            fk_kecamatan_spouse,fk_province_spouse,fk_kelurahan_spouse,fk_estabilished_since_spouse,
            fk_fax_spouse,fk_has_contact_person,fk_name_contact,fk_address_contact,
            fk_sandi_dati_2_contact,fk_kab_or_kodya_contact,fk_postal_code_contact,
            fk_telephone_contact,fk_relationship_contact,fk_kecamatan_contact,fk_province_contact,
            fk_kelurahan_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Approve Customer");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = new DatabaseManager(this);
        Tam_Nama                                        = (TextView) findViewById(R.id.t_nama);
        Tam_Identity_tipe_b                             = (TextView) findViewById(R.id.t_identity_type_b);
        Tam_Identity_no_b                               = (TextView) findViewById(R.id.t_identity_no_b);
        Tam_Address_home_b                              = (TextView) findViewById(R.id.t_address_home_b);
        Tam_Telephone_b                                 = (TextView) findViewById(R.id.t_telephone_b);
        Tam_Sex_b                                       = (TextView) findViewById(R.id.t_sex_b);
        Tam_Handphone_1_b                               = (TextView) findViewById(R.id.t_handphone_1_b);
        Tam_Kategori_kendaraan                          = (TextView) findViewById(R.id.t_kategori_kendaraan);
        Tam_Merk_kendaraan                              = (TextView) findViewById(R.id.t_merk);
        Tam_Type_kendaraan                              = (TextView) findViewById(R.id.t_type);
        Tam_Model_kendaraan                             = (TextView) findViewById(R.id.t_model);
        Tam_Tahun_kendaraan                             = (TextView) findViewById(R.id.t_tahun);
        Tam_Warna_kendaraan                             = (TextView) findViewById(R.id.t_warna);
        Tam_Plat_nomor_kendaraan                        = (TextView) findViewById(R.id.t_plat);
        Tam_Bahan_bakar_kendaraan                       = (TextView) findViewById(R.id.t_bahan_bakar);
        Tam_Km_kendaraan                                = (TextView) findViewById(R.id.t_km_kendaraan);
        Tam_Asuransi                                    = (TextView) findViewById(R.id.t_asuransi);
        Tam_Otr                                         = (TextView) findViewById(R.id.t_otr);
        Tam_Dp                                          = (TextView) findViewById(R.id.t_dp);
        Tam_Jml_pinjaman                                = (TextView) findViewById(R.id.t_jml_pinjaman);
        Tam_Tenor                                       = (TextView) findViewById(R.id.t_tenor);
        Tam_Jaminan_multiguna                           = (TextView) findViewById(R.id.t_jaminan_multiguna);

        Tam_Data_Multiguna                              = (RelativeLayout) findViewById(R.id.data_multiguna);
        Tam_Data_Kendaraan                              = (RelativeLayout) findViewById(R.id.data_kendaraan);
        Tam_Data_Spouse_Data                            = (RelativeLayout) findViewById(R.id.data_spouse_data);


        Tam_Box_km_kendaraan                            = (RelativeLayout) findViewById(R.id.box_km_kendaraan);
        Tam_Box_otr                                     = (RelativeLayout) findViewById(R.id.box_otr);
        Tam_Box_dp                                      = (RelativeLayout) findViewById(R.id.box_dp);
        Tam_Judul_data_kendaraan_kredit                 = (TextView) findViewById(R.id.judul_data_kendaraan_kredit);

        Tam_name                                        = (TextView) findViewById(R.id.t_name);
        Tam_mother_maiden_name                          = (TextView) findViewById(R.id.t_mother_maiden_name);
        Tam_gelar                                       = (TextView) findViewById(R.id.t_gelar);
        Tam_title                                       = (TextView) findViewById(R.id.t_title);
        Tam_marital_status                              = (TextView) findViewById(R.id.t_marital_status);
        Tam_identity_type                               = (TextView) findViewById(R.id.t_identity_type);
        Tam_npwp_no                                     = (TextView) findViewById(R.id.t_npwp_no);
        Tam_birth_place                                 = (TextView) findViewById(R.id.t_birth_place);
        Tam_birth_date                                  = (TextView) findViewById(R.id.t_birth_date);
        Tam_address_ktp                                 = (TextView) findViewById(R.id.t_address_ktp);
        Tam_sandi_dati_2_ktp                            = (TextView) findViewById(R.id.t_sandi_dati_2_ktp);
        Tam_kab_or_kodya_ktp                            = (TextView) findViewById(R.id.t_kab_or_kodya_ktp);
        Tam_postal_code_ktp                             = (TextView) findViewById(R.id.t_postal_code_ktp);
        Tam_address_home                                = (TextView) findViewById(R.id.t_address_home);
        Tam_sandi_dati_2_home                           = (TextView) findViewById(R.id.t_sandi_dati_2_home);
        Tam_kab_or_kodya_home                           = (TextView) findViewById(R.id.t_kab_or_kodya_home);
        Tam_postal_code_home                            = (TextView) findViewById(R.id.t_postal_code_home);
        Tam_mail_address                                = (TextView) findViewById(R.id.t_mail_address);
        Tam_tipe_rumah                                  = (TextView) findViewById(R.id.t_tipe_rumah);
        Tam_home_status                                 = (TextView) findViewById(R.id.t_home_status);
        Tam_telephone                                   = (TextView) findViewById(R.id.t_telephone);
        Tam_telephone_2                                 = (TextView) findViewById(R.id.t_telephone_2);
        Tam_education                                   = (TextView) findViewById(R.id.t_education);
        Tam_sex                                         = (TextView) findViewById(R.id.t_sex);
        Tam_nama_panggilan                              = (TextView) findViewById(R.id.t_nama_panggilan);
        Tam_identity_no                                 = (TextView) findViewById(R.id.t_identity_no);
        Tam_sandi_lahir                                 = (TextView) findViewById(R.id.t_sandi_lahir);
        Tam_religion                                    = (TextView) findViewById(R.id.t_religion);
        Tam_kecamatan_ktp                               = (TextView) findViewById(R.id.t_kecamatan_ktp);
        Tam_province_ktp                                = (TextView) findViewById(R.id.t_province_ktp);
        Tam_kelurahan_ktp                               = (TextView) findViewById(R.id.t_kelurahan_ktp);
        Tam_jarak                                       = (TextView) findViewById(R.id.t_jarak);
        Tam_kecamatan_home                              = (TextView) findViewById(R.id.t_kecamatan_home);
        Tam_province_home                               = (TextView) findViewById(R.id.t_province_home);
        Tam_kelurahan_home                              = (TextView) findViewById(R.id.t_kelurahan_home);
        Tam_stay_length                                 = (TextView) findViewById(R.id.t_stay_length);
        Tam_handphone_1                                 = (TextView) findViewById(R.id.t_handphone_1);
        Tam_handphone_2                                 = (TextView) findViewById(R.id.t_handphone_2);
        Tam_email_stay                                  = (TextView) findViewById(R.id.t_email_stay);

        Tam_pekerjaan                                   = (TextView) findViewById(R.id.etx_pekerjaan);
        Tam_job_title                                   = (TextView) findViewById(R.id.etx_job_title);
        Tam_name_economy_code                           = (TextView) findViewById(R.id.etx_name_economy_code);
        Tam_company_name                                = (TextView) findViewById(R.id.etx_company_name);
        Tam_company_address                             = (TextView) findViewById(R.id.etx_company_address);
        Tam_province_company                            = (TextView) findViewById(R.id.etx_company_province);
        Tam_kab_kodya_company                           = (TextView) findViewById(R.id.etx_company_kab_or_kodya);
        Tam_kecamatan_company                           = (TextView) findViewById(R.id.etx_company_kecamatan);
        Tam_kelurahan_company                           = (TextView) findViewById(R.id.etx_company_kelurahan);
        Tam_sandi_dati_2_company                        = (TextView) findViewById(R.id.etx_company_sandi_dati_2);
        Tam_postal_code_company                         = (TextView) findViewById(R.id.etx_company_postal_code);
        Tam_company_telephone_1                         = (TextView) findViewById(R.id.etx_company_telephone_1);
        Tam_company_telephone_2                         = (TextView) findViewById(R.id.etx_company_telephone_2);
        Tam_line_of_business                            = (TextView) findViewById(R.id.etx_line_of_business);
        Tam_economy_code                                = (TextView) findViewById(R.id.etx_economy_code);
        Tam_estabilished_since                          = (TextView) findViewById(R.id.etx_estabilished_since);
        Tam_company_fax_1                               = (TextView) findViewById(R.id.etx_company_fax_1);

        Tam_spouse_name                                 = (TextView) findViewById(R.id.etx_spouse_name);
        Tam_spouse_title                                = (TextView) findViewById(R.id.etx_spouse_title);
        Tam_spouse_identity_type                        = (TextView) findViewById(R.id.etx_spouse_identity_type);
        Tam_spouse_birth_place_or_sandi_lahir           = (TextView) findViewById(R.id.etx_spouse_birth_place_or_sandi_lahir);
        Tam_spouse_religion                             = (TextView) findViewById(R.id.etx_spouse_religion);
        Tam_spouse_address                              = (TextView) findViewById(R.id.etx_spouse_address);
        Tam_province_spouse                             = (TextView) findViewById(R.id.etx_spouse_province);
        Tam_kab_kodya_spouse                            = (TextView) findViewById(R.id.etx_spouse_kab_or_kodya);
        Tam_kecamatan_spouse                            = (TextView) findViewById(R.id.etx_spouse_kecamatan);
        Tam_kelurahan_spouse                            = (TextView) findViewById(R.id.etx_spouse_kelurahan);
        Tam_sandi_dati_2_spouse                         = (TextView) findViewById(R.id.etx_spouse_sandi_dati_2);
        Tam_postal_code_spouse                          = (TextView) findViewById(R.id.etx_spouse_postal_code);
        Tam_spouse_no_handphone                         = (TextView) findViewById(R.id.etx_spouse_no_handphone);
        Tam_spouse_occupation_or_pekerjaan              = (TextView) findViewById(R.id.etx_spouse_occupation_or_pekerjaan);
        Tam_spouse_company_name                         = (TextView) findViewById(R.id.etx_spouse_company_name);
        Tam_spouse_company_address                      = (TextView) findViewById(R.id.etx_spouse_company_address);
        Tam_spouse_company_telephone                    = (TextView) findViewById(R.id.etx_spouse_company_telephone);
        Tam_spouse_line_of_business                     = (TextView) findViewById(R.id.etx_spouse_line_of_business);
        Tam_spouse_job_title                            = (TextView) findViewById(R.id.etx_spouse_job_title);
        Tam_spouse_sex                                  = (TextView) findViewById(R.id.etx_spouse_sex);
        Tam_spouse_identity_no                          = (TextView) findViewById(R.id.etx_spouse_identity_no);
        Tam_spouse_date_of_birth                        = (TextView) findViewById(R.id.etx_spouse_date_of_birth);
        Tam_spouse_fax                                  = (TextView) findViewById(R.id.etx_spouse_fax);

        Tam_has_contact_person                          = (TextView) findViewById(R.id.etx_has_contact_person);
        Tam_contact_name                                = (TextView) findViewById(R.id.etx_contact_name);
        Tam_contact_address                             = (TextView) findViewById(R.id.etx_contact_address);
        Tam_contact_province                            = (TextView) findViewById(R.id.etx_contact_province);
        Tam_contact_kab_or_kodya                        = (TextView) findViewById(R.id.etx_contact_kab_or_kodya);
        Tam_contact_kecamatan                           = (TextView) findViewById(R.id.etx_contact_kecamatan);
        Tam_contact_kelurahan                           = (TextView) findViewById(R.id.etx_contact_kelurahan);
        Tam_contact_sandi_dati_2                        = (TextView) findViewById(R.id.etx_contact_sandi_dati_2);
        Tam_contact_Postal_code                         = (TextView) findViewById(R.id.etx_contact_postal_code);
        Tam_contact_telephone                           = (TextView) findViewById(R.id.etx_contact_telephone);
        Tam_relationship                                = (TextView) findViewById(R.id.etx_relationship);

        Tam_Data_Dana_Tunai                             = (RelativeLayout) findViewById(R.id.data_dana_tunai);
        Tam_Data_Kendaraan                              = (RelativeLayout) findViewById(R.id.data_kendaraan);

        Tam_Jarak_rumah_ke_cabang                       = (TextView) findViewById(R.id.t_jarak_rumah_ke_cabang);
        Tam_Luas_tanah                                  = (TextView) findViewById(R.id.t_luas_tanah);
        Tam_Luas_bangunan_rumah                         = (TextView) findViewById(R.id.t_luas_bangunan_rumah);
        Tam_Status_kepemilikan_rumah                    = (TextView) findViewById(R.id.t_status_kepemilikan_rumah);
        Tam_Klasifikasi_perumahan                       = (TextView) findViewById(R.id.t_klasifikasi_perumahan);
        Tam_Tempat_menaruh_kendaraan                    = (TextView) findViewById(R.id.t_tempat_menaruh_kendaraan);
        Tam_Status_garasi_kendaraan                     = (TextView) findViewById(R.id.t_status_garasi_kendaraan);
        Tam_Bentuk_bangunan_rumah                       = (TextView) findViewById(R.id.t_bentuk_bangunan_rumah);
        Tam_Kondisi_rumah                               = (TextView) findViewById(R.id.t_kondisi_rumah);
        Tam_Luas_jalan_masuk_rumah                      = (TextView) findViewById(R.id.t_luas_jalan_masuk_rumah);
        Tam_Status_kepemilikan_rumah_pemohon            = (TextView) findViewById(R.id.t_status_kepemilikan_rumah_pemohon);
        Tam_Furniture_or_perabot                        = (TextView) findViewById(R.id.t_furniture_or_perabot);

        Tam_Jarak_tempat_usaha_dari_rumah               = (TextView) findViewById(R.id.t_jarak_tempat_usaha_dari_rumah);
        Tam_Status_kepemilikan_usaha                    = (TextView) findViewById(R.id.t_status_kepemilikan_usaha);
        Tam_Bentuk_bangunan_tempat_usaha                = (TextView) findViewById(R.id.t_bentuk_bangunan_tempat_usaha);
        Tam_Lokasi_tempat_usaha                         = (TextView) findViewById(R.id.t_lokasi_tempat_usaha);
        Tam_Jumlah_karyawan                             = (TextView) findViewById(R.id.t_jumlah_karyawan);
        Tam_Lama_pemohon_menempati_tempat_usaha_tahun   = (TextView) findViewById(R.id.t_lama_pemohon_menempati_tempat_usaha_tahun);
        Tam_Lama_pemohon_menempati_tempat_usaha_bulan   = (TextView) findViewById(R.id.t_lama_pemohon_menempati_tempat_usaha_bulan);
        Tam_Lama_usaha_berdiri_tahun                    = (TextView) findViewById(R.id.t_lama_usaha_berdiri_tahun);
        Tam_Lama_usaha_berdiri_bulan                    = (TextView) findViewById(R.id.t_lama_usaha_berdiri_bulan);
        Tam_Pekerjaan_or_usaha_terkait_ekspor_or_impor  = (TextView) findViewById(R.id.t_pekerjaan_or_usaha_terkait_ekspor_or_impor);

        Tam_Tujuan_penggunaan_unit                      = (TextView) findViewById(R.id.t_tujuan_penggunaan_unit);
        Tam_Kondisi_mobil                               = (TextView) findViewById(R.id.t_kondisi_mobil);
        Tam_Lama_kepemilikan_kendaraan_tahun            = (TextView) findViewById(R.id.t_lama_kepemilikan_kendaraan_tahun);
        Tam_Lama_kepemilikan_kendaraan_bulan            = (TextView) findViewById(R.id.t_lama_kepemilikan_kendaraan_bulan);

        Tam_Jumlah_tanggungan                           = (TextView) findViewById(R.id.t_jumlah_tanggungan);
        Tam_Jumlah_anak                                 = (TextView) findViewById(R.id.t_jumlah_anak);

        Tam_Omzet_or_penghasilan_gross                  = (TextView) findViewById(R.id.t_omzet_or_penghasilan_gross);
        Tam_Penghasilan_nett_or_take_home_pay           = (TextView) findViewById(R.id.t_penghasilan_nett_or_take_home_pay);
        Tam_Penghasilan_pasangan                        = (TextView) findViewById(R.id.t_penghasilan_pasangan);
        Tam_Penghasilan_tambahan                        = (TextView) findViewById(R.id.t_penghasilan_tambahan);
        Tam_Pengeluaran_or_kebutuhan_hidup              = (TextView) findViewById(R.id.t_pengeluaran_or_kebutuhan_hidup);
        Tam_Total_cicilan_leasing_lain                  = (TextView) findViewById(R.id.t_total_cicilan_leasing_lain);
        Tam_Balance_terakhir_di_rekening_tabungan       = (TextView) findViewById(R.id.t_balance_terakhir_di_rekening_tabungan);
        Tam_Rata_rata_mutasi_in_3_bulan_terakhir        = (TextView) findViewById(R.id.t_rata_rata_mutasi_in_3_bulan_terakhir);
        Tam_Rata_rata_mutasi_out_3_bulan_terakhir       = (TextView) findViewById(R.id.t_rata_rata_mutasi_out_3_bulan_terakhir);

        Tam_Collectabilitas_sid_or_slik_tertinggi       = (TextView) findViewById(R.id.t_collectabilitas_sid_or_slik_tertinggi);
        Tam_Pernah_kredit_di_tempat_lain                = (TextView) findViewById(R.id.t_pernah_kredit_di_tempat_lain);
        Tam_Overdue_tertinggi                           = (TextView) findViewById(R.id.t_overdue_tertinggi);
        Tam_Baki_debet_or_outstanding_hutang            = (TextView) findViewById(R.id.t_baki_debet_or_outstanding_hutang);
        Tam_Nama_finance_company                        = (TextView) findViewById(R.id.t_nama_finance_company);
        Tam_Alasan_menunggak_khusus_lebih_dari_coll_2   = (TextView) findViewById(R.id.t_alasan_menunggak_khusus_lebih_dari_coll_2);

        Tam_Apakah_direkomendasikan                     = (TextView) findViewById(R.id.t_apakah_direkomendasikan);
        Tam_Alasan_or_point_penting_rekomendasi_anda    = (TextView) findViewById(R.id.t_alasan_or_point_penting_rekomendasi_anda);

        tampildata_all();
    }

    public void tampildata_all(){
        ArrayList<ArrayList<Object>> data_history = dm.ambilBarisJson("List All");//
        if (data_history.size() < 1) {

        }else {
            try {
                ArrayList<Object> baris = data_history.get(0);
                tam_json = baris.get(0).toString();
                String cek_id_order = getIntent().getExtras().getString("id_order");

                JSONObject jObj = new JSONObject(tam_json);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);

                            String h_id_order = obj.getString("id_order");
                            if(cek_id_order.equals(h_id_order)){
                                String id_order = getIntent().getExtras().getString("id_order");

                                fk_identity_type                                = obj.getString("identity_type");
                                fk_identity_no                                  = obj.getString("identity_no");
                                fk_address_home                                 = obj.getString("address_home");
                                fk_telephone                                    = obj.getString("telephone");
                                fk_sex                                          = obj.getString("sex");
                                fk_handphone_1                                  = obj.getString("handphone_1");
                                fk_asuransi                                     = obj.getString("asuransi");
                                fk_dp                                           = obj.getString("dp");
                                fk_jaminan_multiguna                            = obj.getString("jaminan_multiguna");
                                fk_otr                                          = obj.getString("otr");

                                fk_mother_maiden_name                           = obj.getString("mother_maiden_name");
                                fk_gelar                                        = obj.getString("gelar");
                                fk_title                                        = obj.getString("title");
                                fk_marital_status                               = obj.getString("marital_status");
                                fk_npwp_no                                      = obj.getString("npwp_no");
                                fk_sandi_dati_2_ktp                             = obj.getString("sandi_dati_2_ktp");
                                fk_kab_or_kodya_ktp                             = obj.getString("kab_or_kodya_ktp");
                                fk_postal_code_ktp                              = obj.getString("postal_code_ktp");
                                fk_sandi_dati_2_home                            = obj.getString("sandi_dati_2_home");
                                fk_kab_or_kodya_home                            = obj.getString("kab_or_kodya_home");
                                fk_postal_code_home                             = obj.getString("postal_code_home");
                                fk_mail_address                                 = obj.getString("mail_address");
                                fk_tipe_rumah                                   = obj.getString("tipe_rumah");
                                fk_home_status                                  = obj.getString("home_status");
                                fk_telephone_2                                  = obj.getString("telephone_2");
                                fk_education                                    = obj.getString("education");
                                fk_nama_panggilan                               = obj.getString("nama_panggilan");
                                fk_sandi_lahir                                  = obj.getString("sandi_lahir");
                                fk_religion                                     = obj.getString("religion");
                                fk_kecamatan_ktp                                 = obj.getString("kecamatan_ktp");
                                fk_province_ktp                                 = obj.getString("province_ktp");
                                fk_kelurahan_ktp                                = obj.getString("kelurahan_ktp");
                                fk_jarak_customer                               = obj.getString("jarak_customer");
                                fk_kecamatan_home                               = obj.getString("kecamatan_home");
                                fk_province_home                                = obj.getString("province_home");
                                fk_kelurahan_home                               = obj.getString("kelurahan_home");
                                fk_stay_length                                  = obj.getString("stay_length");
                                fk_handphone_2                                  = obj.getString("handphone_2");
                                fk_email_stay                                   = obj.getString("email_stay");

                                fk_pekerjaan                                    = obj.getString("pekerjaan");
                                fk_job_title_pekerjaan                          = obj.getString("job_title_pekerjaan");
                                fk_name_economy_code                            = obj.getString("name_economy_code");
                                fk_company_name                                 = obj.getString("company_name");
                                fk_address_pekerjaan                            = obj.getString("address_pekerjaan");
                                fk_sandi_dati_2_pekerjaan                       = obj.getString("sandi_dati_2_pekerjaan");
                                fk_kab_or_kodya_pekerjaan                       = obj.getString("kab_or_kodya_pekerjaan");
                                fk_postal_code_pekerjaan                        = obj.getString("postal_code_pekerjaan");
                                fk_telephone_1_pekerjaan                        = obj.getString("telephone_1_pekerjaan");
                                fk_telephone_2_pekerjaan                        = obj.getString("telephone_2_pekerjaan");
                                fk_line_of_business                             = obj.getString("line_of_business");
                                fk_economy_code                                 = obj.getString("economy_code");
                                fk_estabilished_since                           = obj.getString("estabilished_since");
                                fk_kecamatan_pekerjaan                          = obj.getString("kecamatan_pekerjaan");
                                fk_province_pekerjaan                           = obj.getString("province_pekerjaan");
                                fk_kelurahan_pekerjaan                          = obj.getString("kelurahan_pekerjaan");
                                fk_fax_1_pekerjaan                              = obj.getString("fax_1_pekerjaan");

                                fk_name_spouse                                  = obj.getString("name_spouse");
                                fk_title_spouse                                 = obj.getString("title_spouse");
                                fk_identity_type_spouse                         = obj.getString("identity_type_spouse");
                                fk_birth_place_spouse                           = obj.getString("birth_place_spouse");
                                fk_religion_spouse                              = obj.getString("religion_spouse");
                                fk_address_spouse                               = obj.getString("address_spouse");
                                fk_sandi_dati_2_spouse                          = obj.getString("sandi_dati_2_spouse");
                                fk_kab_or_kodya_spouse                          = obj.getString("kab_or_kodya_spouse");
                                fk_postal_code_spouse                           = obj.getString("postal_code_spouse");
                                fk_no_handphone                                 = obj.getString("no_handphone");
                                fk_occupation_or_pekerjaan_spouse               = obj.getString("occupation_or_pekerjaan_spouse");
                                fk_company_name_spouse                          = obj.getString("company_name_spouse");
                                fk_address_pekerjaan_spouse                     = obj.getString("address_pekerjaan_spouse");
                                fk_telephone_spouse                             = obj.getString("telephone_spouse");
                                fk_line_of_business_spouse                      = obj.getString("line_of_business_spouse");
                                fk_job_title_spouse                             = obj.getString("job_title_spouse");
                                fk_sex_spouse                                   = obj.getString("sex_spouse");
                                fk_identity_no_spouse                           = obj.getString("identity_no_spouse");
                                fk_date_of_birth_spouse                         = obj.getString("date_of_birth_spouse");
                                fk_kecamatan_spouse                             = obj.getString("kecamatan_spouse");
                                fk_province_spouse                              = obj.getString("province_spouse");
                                fk_kelurahan_spouse                             = obj.getString("kelurahan_spouse");
                                fk_estabilished_since_spouse                    = obj.getString("estabilished_since_spouse");
                                fk_fax_spouse                                   = obj.getString("fax_spouse");

                                fk_has_contact_person                           = obj.getString("has_contact_person");
                                fk_name_contact                                 = obj.getString("name_contact");
                                fk_address_contact                              = obj.getString("address_contact");
                                fk_sandi_dati_2_contact                         = obj.getString("sandi_dati_2_contact");
                                fk_kab_or_kodya_contact                         = obj.getString("kab_or_kodya_contact");
                                fk_postal_code_contact                          = obj.getString("postal_code_contact");
                                fk_telephone_contact                            = obj.getString("telephone_contact");
                                fk_relationship_contact                         = obj.getString("relationship_contact");
                                fk_kecamatan_contact                            = obj.getString("kecamatan_contact");
                                fk_province_contact                             = obj.getString("province_contact");
                                fk_kelurahan_contact                            = obj.getString("kelurahan_contact");

                                fk_get_id_order                                 = obj.getString("id_order");
                                fk_namalengkap                                  = obj.getString("name");
                                fk_tempatlahir                                  = obj.getString("birth_place");
                                fk_tgl_lahir                                    = obj.getString("birth_date");
                                fk_alamat                                       = obj.getString("address_ktp");
                                fk_kategori_kendaraan                           = obj.getString("kategori_kendaraan");
                                fk_status_kendaraan                             = obj.getString("status_kendaraan");
                                fk_merk_kendaraan                               = obj.getString("merk_kendaraan");
                                fk_model_kendaraan                              = obj.getString("model_kendaraan");
                                fk_type_kendaraan                               = obj.getString("type_kendaraan");
                                fk_tahun                                        = obj.getString("tahun_kendaraan");
                                fk_warna                                        = obj.getString("warna");
                                fk_plat_nomor                                   = obj.getString("plat_nomor");
                                fk_km_kendaraan                                 = obj.getString("km_kendaraan");
                                fk_bahan_bakar                                  = obj.getString("bahan_bakar");
                                fk_status_survey                                = obj.getString("status_survey");
                                fk_jenis_kredit                                 = obj.getString("jenis_kredit");
                                fk_jml_pinjaman                                 = obj.getString("jml_pinjaman");
                                fk_tenor                                        = obj.getString("tenor");
                                fk_latitude                                     = obj.getString("latitude_survey");
                                fk_longitude                                    = obj.getString("longitude_survey");
                                fk_jarak_rumah_ke_cabang                        = obj.getString("jarak_rumah_ke_cabang");
                                fk_luas_tanah                                   = obj.getString("luas_tanah");
                                fk_luas_bangunan_rumah                          = obj.getString("luas_bangunan_rumah");
                                fk_status_kepemilikan_rumah                     = obj.getString("status_kepemilikan_rumah");
                                fk_klasifikasi_perumahan                        = obj.getString("klasifikasi_perumahan");
                                fk_tempat_menaruh_kendaraan                     = obj.getString("tempat_menaruh_kendaraan");
                                fk_status_garasi_kendaraan                      = obj.getString("status_garasi_kendaraan");
                                fk_bentuk_bangunan_rumah                        = obj.getString("bentuk_bangunan_rumah");
                                fk_kondisi_rumah                                = obj.getString("kondisi_rumah");
                                fk_luas_jalan_masuk_rumah                       = obj.getString("luas_jalan_masuk_rumah");
                                fk_status_kepemilikan_rumah_pemohon             = obj.getString("status_kepemilikan_rumah_pemohon");
                                fk_furniture                                    = obj.getString("furniture");
                                fk_jarak_tempat_usaha_dari_rumah                = obj.getString("jarak_tempat_usaha_dari_rumah");
                                fk_status_kepemilikan_usaha                     = obj.getString("status_kepemilikan_usaha");
                                fk_bentuk_bangunan_tempat_usaha                 = obj.getString("bentuk_bangunan_tempat_usaha");
                                fk_lokasi_tempat_usaha                          = obj.getString("lokasi_tempat_usaha");
                                fk_jumlah_karyawan                              = obj.getString("jumlah_karyawan");
                                fk_lama_pemohon_menempati_tempat_usaha          = obj.getString("lama_pemohon_menempati_tempat_usaha");

                                fk_bulan_lama_pemohon_menempati_tempat_usaha    = obj.getString("bulan_lama_pemohon_menempati_tempat_usaha");
                                fk_tahun_lama_pemohon_menempati_tempat_usaha    = obj.getString("tahun_lama_pemohon_menempati_tempat_usaha");
                                fk_lama_usaha_berdiri                           = obj.getString("lama_usaha_berdiri");
                                fk_bulan_lama_usaha_berdiri                     = obj.getString("bulan_lama_usaha_berdiri");
                                fk_tahun_lama_usaha_berdiri                     = obj.getString("tahun_lama_usaha_berdiri");
                                fk_pekerjaan_or_usaha_terkait_ekspor_or_impor   = obj.getString("pekerjaan_or_usaha_terkait_ekspor_or_impor");

                                fk_tujuan_penggunaan_unit                       = obj.getString("tujuan_penggunaan_unit");
                                fk_kondisi_mobil                                = obj.getString("kondisi_mobil");
                                fk_lama_kepemilikan_kendaraan                   = obj.getString("lama_kepemilikan_kendaraan");
                                fk_bulan_lama_kepemilikan_kendaraan             = obj.getString("bulan_lama_kepemilikan_kendaraan");
                                fk_tahun_lama_kepemilikan_kendaraan             = obj.getString("tahun_lama_kepemilikan_kendaraan");

                                fk_jumlah_tanggungan                            = obj.getString("jumlah_tanggungan");
                                fk_jumlah_anak                                  = obj.getString("jumlah_anak");

                                fk_omzet_or_penghasilan_gross                   = obj.getString("omzet_or_penghasilan_gross");
                                fk_penghasilan_nett_or_take_home_pay            = obj.getString("penghasilan_nett_or_take_home_pay");
                                fk_penghasilan_pasangan                         = obj.getString("penghasilan_pasangan");
                                fk_penghasilan_tambahan                         = obj.getString("penghasilan_tambahan");
                                fk_pengeluaran_or_kebutuhan_hidup               = obj.getString("pengeluaran_or_kebutuhan_hidup");

                                fk_total_cicilan_leasing_lain                   = obj.getString("total_cicilan_leasing_lain");
                                fk_balance_terakhir_di_rekening_tabungan        = obj.getString("balance_terakhir_di_rekening_tabungan");
                                fk_rata_rata_mutasi_in_3_bulan_terakhir         = obj.getString("rata_rata_mutasi_in_3_bulan_terakhir");
                                fk_rata_rata_mutasi_out_3_bulan_terakhir        = obj.getString("rata_rata_mutasi_out_3_bulan_terakhir");

                                fk_collectabilitas_sid_or_slik_tertinggi        = obj.getString("collectabilitas_sid_or_slik_tertinggi");
                                fk_pernah_kredit_di_tempat_lain                 = obj.getString("pernah_kredit_di_tempat_lain");

                                fk_overdue_tertinggi                            = obj.getString("overdue_tertinggi");
                                fk_baki_debet_or_outstanding_hutang             = obj.getString("baki_debet_or_outstanding_hutang");
                                fk_nama_finance_company                         = obj.getString("nama_finance_company");
                                fk_alasan_menunggak_khusus_lebih_dari_coll_2    = obj.getString("alasan_menunggak_khusus_lebih_dari_coll_2");

                                fk_apakah_direkomendasikan                      = obj.getString("apakah_direkomendasikan");
                                fk_alasan_or_point_penting_rekomendasi_anda     = obj.getString("alasan_or_point_penting_rekomendasi_anda");

                                if(fk_marital_status.equals("Married")){
                                    Tam_Data_Spouse_Data.setVisibility(View.VISIBLE);
                                }else{
                                    Tam_Data_Spouse_Data.setVisibility(View.GONE);
                                }

                                Tam_Nama.setText(fk_namalengkap);
                                Tam_Identity_tipe_b.setText(fk_identity_type);
                                Tam_Identity_no_b.setText(fk_identity_no);
                                Tam_Address_home_b.setText(fk_address_home);
                                Tam_Telephone_b.setText(fk_telephone);
                                Tam_Sex_b.setText(fk_sex);
                                Tam_Handphone_1_b.setText(fk_handphone_1);
                                Tam_Kategori_kendaraan.setText(fk_kategori_kendaraan);
                                Tam_Merk_kendaraan.setText(fk_merk_kendaraan);
                                Tam_Type_kendaraan.setText(fk_type_kendaraan);
                                Tam_Model_kendaraan.setText(fk_model_kendaraan);
                                Tam_Tahun_kendaraan.setText(fk_tahun);
                                Tam_Warna_kendaraan.setText(fk_warna);
                                Tam_Plat_nomor_kendaraan.setText(fk_plat_nomor);
                                Tam_Bahan_bakar_kendaraan.setText(fk_bahan_bakar);
                                Tam_Km_kendaraan.setText(fk_km_kendaraan);
                                Tam_Asuransi.setText(fk_asuransi);
                                Tam_Dp.setText(fk_dp);

                                // Tam_Tenor.setText(fk_otr);
                                Tam_Jaminan_multiguna.setText(fk_jaminan_multiguna);

                                if(fk_jenis_kredit.equals("Multiguna")){

                                    Tam_Otr.setText(fk_otr);

                                    Tam_Box_km_kendaraan.setVisibility(View.GONE);
                                    Tam_Box_otr.setVisibility(View.GONE);
                                    Tam_Box_dp.setVisibility(View.GONE);

                                    Tam_Judul_data_kendaraan_kredit.setText("Jaminan Kendaraan");

                                    Double s_jml_pinjaman=Double.parseDouble(fk_jml_pinjaman);
                                    Tam_Jml_pinjaman.setText("Rp " + String.format("%s", NumberFormat.
                                            getInstance(new Locale("id", "ID")).format(s_jml_pinjaman)));
                                }else{
                                    Tam_Jml_pinjaman.setText(fk_jml_pinjaman);

                                    Double s_otr=Double.parseDouble(fk_otr);
                                    Tam_Otr.setText("Rp " + String.format("%s", NumberFormat.
                                            getInstance(new Locale("id", "ID")).format(s_otr)));
                                }
                                Tam_Tenor.setText(fk_otr+" Bulan");

                                if(fk_jenis_kredit.equals("Multiguna")){
                                    if(!fk_jaminan_multiguna.equals("SERTIFIKAT RUMAH")){
                                        Tam_Data_Multiguna.setVisibility(View.VISIBLE);
                                        Tam_Data_Kendaraan.setVisibility(View.VISIBLE);
                                    }else{
                                        Tam_Data_Multiguna.setVisibility(View.VISIBLE);
                                        Tam_Data_Kendaraan.setVisibility(View.GONE);
                                    }
                                }else{
                                    Tam_Data_Multiguna.setVisibility(View.GONE);
                                    Tam_Data_Kendaraan.setVisibility(View.VISIBLE);
                                }

                                Tam_name.setText(fk_namalengkap);
                                Tam_mother_maiden_name.setText(fk_mother_maiden_name);
                                Tam_gelar.setText(fk_gelar);
                                Tam_title.setText(fk_title);
                                Tam_marital_status.setText(fk_marital_status);
                                Tam_identity_type.setText(fk_identity_type);
                                Tam_npwp_no.setText(fk_npwp_no);

                                Tam_birth_place.setText(fk_tempatlahir);
                                Tam_birth_date.setText(fk_tgl_lahir);
                                Tam_address_ktp.setText(fk_alamat);
                                Tam_sandi_dati_2_ktp.setText(fk_sandi_dati_2_ktp);

                                Tam_kab_or_kodya_ktp.setText(fk_kab_or_kodya_ktp);
                                Tam_postal_code_ktp.setText(fk_postal_code_ktp);
                                Tam_address_home.setText(fk_address_home);
                                Tam_sandi_dati_2_home.setText(fk_sandi_dati_2_home);
                                Tam_kab_or_kodya_home.setText(fk_kab_or_kodya_home);
                                Tam_postal_code_home.setText(fk_postal_code_home);
                                Tam_mail_address.setText(fk_mail_address);
                                Tam_tipe_rumah.setText(fk_tipe_rumah);
                                Tam_home_status.setText(fk_home_status);
                                Tam_telephone.setText(fk_telephone);
                                Tam_telephone_2.setText(fk_telephone_2);
                                Tam_education.setText(fk_education);
                                Tam_sex.setText(fk_sex);
                                Tam_nama_panggilan.setText(fk_nama_panggilan);
                                Tam_identity_no.setText(fk_identity_no);
                                Tam_sandi_lahir.setText(fk_sandi_lahir);
                                Tam_religion.setText(fk_religion);
                                Tam_kecamatan_ktp.setText(fk_kecamatan_ktp);
                                Tam_province_ktp.setText(fk_province_ktp);
                                Tam_kelurahan_ktp.setText(fk_kelurahan_ktp);
                                Tam_jarak.setText(fk_jarak_customer);
                                Tam_kecamatan_home.setText(fk_kecamatan_home);
                                Tam_province_home.setText(fk_province_home);
                                Tam_kelurahan_home.setText(fk_kelurahan_home);
                                Tam_stay_length.setText(fk_stay_length);
                                Tam_handphone_1.setText(fk_handphone_1);
                                Tam_handphone_2.setText(fk_handphone_2);
                                Tam_email_stay.setText(fk_email_stay);

                                Tam_pekerjaan.setText(fk_pekerjaan);
                                Tam_job_title.setText(fk_job_title_pekerjaan);
                                Tam_name_economy_code.setText(fk_name_economy_code);
                                Tam_company_name.setText(fk_company_name);
                                Tam_company_address.setText(fk_address_pekerjaan);
                                Tam_province_company.setText(fk_province_pekerjaan);
                                Tam_kab_kodya_company.setText(fk_kab_or_kodya_pekerjaan);
                                Tam_kecamatan_company.setText(fk_kecamatan_pekerjaan);
                                Tam_kelurahan_company.setText(fk_kelurahan_pekerjaan);
                                Tam_sandi_dati_2_company.setText(fk_sandi_dati_2_pekerjaan);
                                Tam_postal_code_company.setText(fk_postal_code_pekerjaan);
                                Tam_company_telephone_1.setText(fk_telephone_1_pekerjaan);
                                Tam_company_telephone_2.setText(fk_telephone_2_pekerjaan);
                                Tam_line_of_business.setText(fk_line_of_business);
                                Tam_economy_code.setText(fk_economy_code);
                                Tam_estabilished_since.setText(fk_estabilished_since);
                                Tam_company_fax_1.setText(fk_fax_1_pekerjaan);

                                Tam_spouse_name.setText(fk_name_spouse);
                                Tam_spouse_title.setText(fk_title_spouse);
                                Tam_spouse_identity_type.setText(fk_identity_type_spouse);
                                Tam_spouse_birth_place_or_sandi_lahir.setText(fk_birth_place_spouse);
                                Tam_spouse_religion.setText(fk_religion_spouse);
                                Tam_spouse_address.setText(fk_address_spouse);
                                Tam_province_spouse.setText(fk_province_spouse);
                                Tam_kab_kodya_spouse.setText(fk_kab_or_kodya_spouse);
                                Tam_kecamatan_spouse.setText(fk_kecamatan_spouse);
                                Tam_kelurahan_spouse.setText(fk_kelurahan_spouse);
                                Tam_sandi_dati_2_spouse.setText(fk_sandi_dati_2_spouse);
                                Tam_postal_code_spouse.setText(fk_postal_code_spouse);
                                Tam_spouse_no_handphone.setText(fk_no_handphone);
                                Tam_spouse_occupation_or_pekerjaan.setText(fk_occupation_or_pekerjaan_spouse);
                                Tam_spouse_company_name.setText(fk_company_name_spouse);
                                Tam_spouse_company_address.setText(fk_address_pekerjaan_spouse);
                                Tam_spouse_company_telephone.setText(fk_telephone_spouse);
                                Tam_spouse_line_of_business.setText(fk_line_of_business_spouse);
                                Tam_spouse_job_title.setText(fk_job_title_spouse);
                                Tam_spouse_sex.setText(fk_sex_spouse);
                                Tam_spouse_identity_no.setText(fk_identity_no_spouse);
                                Tam_spouse_date_of_birth.setText(fk_date_of_birth_spouse);
                                Tam_spouse_fax.setText(fk_fax_spouse);

                                Tam_has_contact_person.setText(fk_has_contact_person);
                                Tam_contact_name.setText(fk_name_contact);
                                Tam_contact_address.setText(fk_address_contact);
                                Tam_contact_province.setText(fk_province_contact);
                                Tam_contact_kab_or_kodya.setText(fk_kab_or_kodya_contact);
                                Tam_contact_kecamatan.setText(fk_kecamatan_contact);
                                Tam_contact_kelurahan.setText(fk_kelurahan_contact);
                                Tam_contact_sandi_dati_2.setText(fk_sandi_dati_2_contact);
                                Tam_contact_Postal_code.setText(fk_postal_code_contact);
                                Tam_contact_telephone.setText(fk_telephone_contact);
                                Tam_relationship.setText(fk_relationship_contact);

                                Tam_Jarak_rumah_ke_cabang.setText(fk_jarak_rumah_ke_cabang);
                                Tam_Luas_tanah.setText(fk_luas_tanah);
                                Tam_Luas_bangunan_rumah.setText(fk_luas_bangunan_rumah);
                                Tam_Status_kepemilikan_rumah.setText(fk_status_kepemilikan_rumah);
                                Tam_Klasifikasi_perumahan.setText(fk_klasifikasi_perumahan);
                                Tam_Tempat_menaruh_kendaraan.setText(fk_tempat_menaruh_kendaraan);
                                Tam_Status_garasi_kendaraan.setText(fk_status_garasi_kendaraan);
                                Tam_Bentuk_bangunan_rumah.setText(fk_bentuk_bangunan_rumah);
                                Tam_Kondisi_rumah.setText(fk_kondisi_rumah);
                                Tam_Luas_jalan_masuk_rumah.setText(fk_luas_jalan_masuk_rumah);
                                Tam_Status_kepemilikan_rumah_pemohon.setText(fk_status_kepemilikan_rumah_pemohon);
                                Tam_Furniture_or_perabot.setText(fk_furniture);

                                Tam_Jarak_tempat_usaha_dari_rumah.setText(fk_jarak_tempat_usaha_dari_rumah);
                                Tam_Status_kepemilikan_usaha.setText(fk_status_kepemilikan_usaha);
                                Tam_Bentuk_bangunan_tempat_usaha.setText(fk_bentuk_bangunan_tempat_usaha);
                                Tam_Lokasi_tempat_usaha.setText(fk_lokasi_tempat_usaha);
                                Tam_Jumlah_karyawan.setText(fk_jumlah_karyawan);
                                Tam_Lama_pemohon_menempati_tempat_usaha_bulan.setText(fk_bulan_lama_pemohon_menempati_tempat_usaha);
                                Tam_Lama_pemohon_menempati_tempat_usaha_tahun.setText(fk_tahun_lama_pemohon_menempati_tempat_usaha);
                                Tam_Lama_usaha_berdiri_bulan.setText(fk_bulan_lama_usaha_berdiri);
                                Tam_Lama_usaha_berdiri_tahun.setText(fk_tahun_lama_usaha_berdiri);
                                Tam_Pekerjaan_or_usaha_terkait_ekspor_or_impor.setText(fk_pekerjaan_or_usaha_terkait_ekspor_or_impor);

                                Tam_Tujuan_penggunaan_unit.setText(fk_tujuan_penggunaan_unit);
                                Tam_Kondisi_mobil.setText(fk_kondisi_mobil);
                                Tam_Lama_kepemilikan_kendaraan_bulan.setText(fk_bulan_lama_kepemilikan_kendaraan);
                                Tam_Lama_kepemilikan_kendaraan_tahun.setText(fk_tahun_lama_kepemilikan_kendaraan);

                                Tam_Jumlah_tanggungan.setText(fk_jumlah_tanggungan);
                                Tam_Jumlah_anak.setText(fk_jumlah_anak);

                                Tam_Omzet_or_penghasilan_gross.setText(fk_omzet_or_penghasilan_gross);
                                Tam_Penghasilan_nett_or_take_home_pay.setText(fk_penghasilan_nett_or_take_home_pay);
                                Tam_Penghasilan_pasangan.setText(fk_penghasilan_pasangan);
                                Tam_Penghasilan_tambahan.setText(fk_penghasilan_tambahan);
                                Tam_Pengeluaran_or_kebutuhan_hidup.setText(fk_pengeluaran_or_kebutuhan_hidup);
                                Tam_Total_cicilan_leasing_lain.setText(fk_total_cicilan_leasing_lain);
                                Tam_Balance_terakhir_di_rekening_tabungan.setText(fk_balance_terakhir_di_rekening_tabungan);
                                Tam_Rata_rata_mutasi_in_3_bulan_terakhir.setText(fk_rata_rata_mutasi_in_3_bulan_terakhir);
                                Tam_Rata_rata_mutasi_out_3_bulan_terakhir.setText(fk_rata_rata_mutasi_out_3_bulan_terakhir);

                                Tam_Collectabilitas_sid_or_slik_tertinggi.setText(fk_collectabilitas_sid_or_slik_tertinggi);
                                Tam_Pernah_kredit_di_tempat_lain.setText(fk_pernah_kredit_di_tempat_lain);
                                Tam_Overdue_tertinggi.setText(fk_overdue_tertinggi);
                                Tam_Baki_debet_or_outstanding_hutang.setText(fk_baki_debet_or_outstanding_hutang);
                                Tam_Nama_finance_company.setText(fk_nama_finance_company);
                                Tam_Alasan_menunggak_khusus_lebih_dari_coll_2.setText(fk_alasan_menunggak_khusus_lebih_dari_coll_2);

                                Tam_Apakah_direkomendasikan.setText(fk_apakah_direkomendasikan);
                                Tam_Alasan_or_point_penting_rekomendasi_anda.setText(fk_alasan_or_point_penting_rekomendasi_anda);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
