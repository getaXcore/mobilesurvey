package surveyor.id.com.mobilesurvey.modal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseManager {
    private static final String NAMA_DB = "surveyor_olym";
    private static final int DB_VERSION = 19;

    //VARIABLE LOGIN
    private static final String ROW_ID          = "_id";
    private static final String ROW_USERNAME    = "username";
    private static final String ROW_USERID      = "userid";
    private static final String ROW_ALAMAT      = "alamat";
    private static final String ROW_NAMALENGKAP = "namalengkap";
    private static final String NAMA_TABEL      = "loginku";
    private static final String CREATE_TABLE    = "create table "
            + NAMA_TABEL        + " ("
            + ROW_ID            + " integer PRIMARY KEY autoincrement,"
            + ROW_USERNAME      + " text,"
            + ROW_USERID        + " text,"
            + ROW_ALAMAT        + " text,"
            + ROW_NAMALENGKAP   + " text)";
    //end variable login

    //VARIABLE status
    private static final String ROW_STATUS_ID       = "status_id";
    private static final String ROW_STATUS_USERNAME = "username";
    private static final String ROW_STATUS_USERID   = "userid";
    private static final String ROW_STATUS_INPUT    = "statusinput";
    private static final String NAMA_TABEL_STATUS   = "status_input";
    private static final String CREATE_TABLE_STATUS = "create table "
            + NAMA_TABEL_STATUS     + " ("
            + ROW_STATUS_ID         + " integer PRIMARY KEY autoincrement,"
            + ROW_STATUS_USERNAME   + " text,"
            + ROW_STATUS_USERID     + " text,"
            + ROW_STATUS_INPUT      + " text)";
    //end variable status

    //variable simpan json
    private static final String ROW_ID_JSON             = "json_id";
    private static final String ROW_TUGAS_JSON          = "json_tugas";
    private static final String ROW_TUGAS_NAMA          = "json_nama";
    private static final String ROW_TUGAS_ID_ORDER      = "json_id_order";
    private static final String ROW_TUGAS_TANGGAL       = "json_tanggal";
    private static final String NAMA_TABEL_JSON_TUGAS   = "simpantugas";
    private static final String CREATE_TABLE_TUGAS      = "create table "
            + NAMA_TABEL_JSON_TUGAS + " ("
            + ROW_ID_JSON           + " integer PRIMARY KEY autoincrement,"
            + ROW_TUGAS_JSON        + " text,"
            + ROW_TUGAS_NAMA        + " text,"
            + ROW_TUGAS_ID_ORDER    + " text,"
            + ROW_TUGAS_TANGGAL     + " text)";
    //end variable simpan json

    //variable foto
    private static final String ROW_ID_PHOTO        = "photo_id";
    private static final String ROW_PHOTO_NAMA      = "photo_nama";
    private static final String ROW_PHOTO_LINK      = "photo_link";
    private static final String ROW_PHOTO_BITMAP    = "photo_bitmap";
    private static final String ROW_PHOTO_LATITUDE  = "photo_latitude";
    private static final String ROW_PHOTO_LONGITUDE = "photo_longitude";
    private static final String ROW_PHOTO_STATUS    = "photo_status";
    private static final String ROW_PHOTO_ID_ORDER  = "photo_id_order";
    private static final String NAMA_TABEL_PHOTO    = "simpanphoto";
    private static final String CREATE_TABLE_PHOTO  = "create table "
            + NAMA_TABEL_PHOTO      + " ("
            + ROW_ID_PHOTO          + " integer PRIMARY KEY autoincrement,"
            + ROW_PHOTO_NAMA        + " text,"
            + ROW_PHOTO_LINK        + " text,"
            + ROW_PHOTO_BITMAP      + " text,"
            + ROW_PHOTO_LATITUDE    + " text,"
            + ROW_PHOTO_LONGITUDE   + " text,"
            + ROW_PHOTO_STATUS      + " text,"
            + ROW_PHOTO_ID_ORDER    + " text)";
    //end variable foto

    //variable tab
    private static final String ROW_ID_TAB          = "tab_id";
    private static final String ROW_TAB_NAMA        = "tab_nama";
    private static final String ROW_TAB_KE          = "tab_link";
    private static final String ROW_TAB_STATUS      = "tab_bitmap";
    private static final String ROW_TAB_ID_ORDER    = "tab_latitude";
    private static final String NAMA_TABEL_TAB      = "simpantab";
    private static final String CREATE_TABLE_TAB    = "create table "
            + NAMA_TABEL_TAB    +" ("
            + ROW_ID_TAB        + " integer PRIMARY KEY autoincrement,"
            + ROW_TAB_NAMA      + " text,"
            + ROW_TAB_KE        + " text,"
            + ROW_TAB_STATUS    + " text,"
            + " text,"
            + ROW_TAB_ID_ORDER  + " text)";
    //end variable tab

    //variable chatdetail
    private static final String ROW_ID_CHATDETAIL               = "chatdetail_id";
    private static final String ROW_CHATDETAIL_ID_BALAS_MESSAGE = "chatdetail_id_balas_message";
    private static final String ROW_CHATDETAIL_ID_MESSAGE       = "chatdetail_id_message";
    private static final String ROW_CHATDETAIL_TO_ID_ADMIN      = "chatdetail_to_id_admin";
    private static final String ROW_CHATDETAIL_FROM_ID_ADMIN    = "chatdetail_from_id_admin";
    private static final String ROW_CHATDETAIL_FROM_NAMA        = "chatdetail_from_nama";
    private static final String ROW_CHATDETAIL_BALAS_MESSAGE    = "chatdetail_balas_message";
    private static final String ROW_CHATDETAIL_ENTRY_DATE_BALAS = "chatdetail_entry_date_balas";
    private static final String ROW_CHATDETAIL_TYPE_MESSAGE     = "chatdetail_type_message";
    private static final String ROW_CHATDETAIL_STATUS           = "chatdetail_status";
    private static final String NAMA_TABEL_CHATDETAIL           = "simpanchatdetail";
    private static final String CREATE_TABLE_CHATDETAIL         = "create table "
            + NAMA_TABEL_CHATDETAIL             + " ("
            + ROW_ID_CHATDETAIL                 + " integer PRIMARY KEY autoincrement,"
            + ROW_CHATDETAIL_ID_BALAS_MESSAGE   + " text,"
            + ROW_CHATDETAIL_ID_MESSAGE         + " text,"
            + ROW_CHATDETAIL_TO_ID_ADMIN        + " text,"
            + ROW_CHATDETAIL_FROM_ID_ADMIN      + " text,"
            + ROW_CHATDETAIL_FROM_NAMA          + " text,"
            + ROW_CHATDETAIL_BALAS_MESSAGE      + " text,"
            + ROW_CHATDETAIL_ENTRY_DATE_BALAS   + " text,"
            + ROW_CHATDETAIL_TYPE_MESSAGE       + " text,"
            + ROW_CHATDETAIL_STATUS             + " text)";
    //end variable chatdetail

    //variable chatdetail
    private static final String ROW_ID_KIRIMLOKASI                  = "kirimlokasi_id";
    private static final String ROW_KIRIMLOKASI_LATITUDE_SURVEYOR   = "kirimlokasi_latitude_surveyor";
    private static final String ROW_KIRIMLOKASI_LONGITUDE_SURVEYOR  = "kirimlokasi_longitude_surveyor";
    private static final String ROW_KIRIMLOKASI_ID_SURVEYOR         = "kirimlokasi_id_surveyor";
    private static final String ROW_KIRIMLOKASI_TIME_SURVEYOR       = "kirimlokasi_time_surveyor";
    private static final String NAMA_TABEL_KIRIMLOKASI              = "simpankirimlokasi";
    private static final String CREATE_TABLE_KIRIMLOKASI            = "create table "
            + NAMA_TABEL_KIRIMLOKASI                + " ("
            + ROW_ID_KIRIMLOKASI                    + " integer PRIMARY KEY autoincrement,"
            + ROW_KIRIMLOKASI_LATITUDE_SURVEYOR     + " text,"
            + ROW_KIRIMLOKASI_LONGITUDE_SURVEYOR    + " text,"
            + ROW_KIRIMLOKASI_ID_SURVEYOR           + " text,"
            + ROW_KIRIMLOKASI_TIME_SURVEYOR         + " text)";
    //end variable chatdetail

    //Tambahan utk data survey
    private static final String ID_DATA_1                                       = "id_data_1";
    private static final String ID_ORDER_1                                      = "id_order_1";
    private static final String ID_PROVINCE_KTP                                 = "id_province_ktp";
    private static final String ID_KAB_KODYA_KTP                                = "id_kab_kodya_ktp";
    private static final String ID_KEC_KTP                                      = "id_kec_ktp";
    private static final String ID_KEL_KTP                                      = "id_kel_ktp";
    private static final String ZIPCODE_KTP                                     = "zipcode_ktp";
    private static final String NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN               = "simpansurvey1_tambahan";
    private static final String CREATE_TABLE_SIMPANSURVEY1_TAMBAHAN             = "create table "
            + NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN                           + " ("
            + ID_DATA_1                                         + " integer PRIMARY KEY autoincrement,"
            + ID_ORDER_1                                        + " text,"
            + ID_PROVINCE_KTP                                   + " text,"
            + ID_KAB_KODYA_KTP                                  + " text,"
            + ID_KEC_KTP                                        + " text,"
            + ID_KEL_KTP                                        + " text,"
            + ZIPCODE_KTP                                       + " text)";

    private static final String ID_DATA_2                                       = "id_data_2";
    private static final String ID_ORDER_2                                      = "id_order_2";
    private static final String ID_PROVINCE_SPOUSE                              = "id_province_spouse";
    private static final String ID_KAB_KODYA_SPOUSE                             = "id_kab_kodya_spouse";
    private static final String ID_KEC_SPOUSE                                   = "id_kec_spouse";
    private static final String ID_KEL_SPOUSE                                   = "id_kel_spouse";
    private static final String ZIPCODE_SPOUSE                                  = "zipcode_spouse";
    private static final String NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN               = "simpansurvey3_tambahan";
    private static final String CREATE_TABLE_SIMPANSURVEY3_TAMBAHAN             = "create table "
            + NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN                           + " ("
            + ID_DATA_2                                         + " integer PRIMARY KEY autoincrement,"
            + ID_ORDER_2                                        + " text,"
            + ID_PROVINCE_SPOUSE                                + " text,"
            + ID_KAB_KODYA_SPOUSE                               + " text,"
            + ID_KEC_SPOUSE                                     + " text,"
            + ID_KEL_SPOUSE                                     + " text,"
            + ZIPCODE_SPOUSE                                    + " text)";

    //variable SURVEY
    private static final String ROW_ID_SURVEY                                   = "id_survey";
    private static final String ROW_ID_ORDER                                    = "id_order";
    private static final String ROW_ID_SURVEYOR                                 = "id_surveyor";
    //private static final String ROW_ID_CUSTOMER                               = "id_customer";
    private static final String ROW_NAME                                        = "name";
    private static final String ROW_MOTHER_MAIDEN_NAME                          = "mother_maiden_name";
    private static final String ROW_GELAR                                       = "gelar";
    private static final String ROW_TITLE                                       = "title";
    private static final String ROW_MARITAL_STATUS                              = "marital_status";
    private static final String ROW_IDENTITY_TYPE                               = "identity_type";
    private static final String ROW_NPWP_NO                                     = "npwp_no";
    private static final String ROW_BIRTH_PLACE                                 = "birth_place";
    private static final String ROW_BIRTH_DATE                                  = "birth_date";
    private static final String ROW_ADDRESS_KTP                                 = "address_ktp";
    private static final String ROW_SANDI_DATI_2_KTP                            = "sandi_dati_2_ktp";
    private static final String ROW_KAB_OR_KODYA_KTP                            = "kab_or_kodya_ktp";
    private static final String ROW_POSTAL_CODE_KTP                             = "postal_code_ktp";
    private static final String ROW_ADDRESS_HOME                                = "address_home";
    private static final String ROW_SANDI_DATI_2_HOME                           = "sandi_dati_2_home";
    private static final String ROW_KAB_OR_KODYA_HOME                           = "kab_or_kodya_home";
    private static final String ROW_POSTAL_CODE_HOME                            = "postal_code_home";
    private static final String ROW_MAIL_ADDRESS                                = "mail_address";
    private static final String ROW_TIPE_RUMAH                                  = "tipe_rumah";
    private static final String ROW_HOME_STATUS                                 = "home_status";
    private static final String ROW_TELEPHONE                                   = "telephone";
    private static final String ROW_TELEPHONE_2                                 = "telephone_2";
    private static final String ROW_EDUCATION                                   = "education";
    private static final String ROW_SEX                                         = "sex";
    private static final String ROW_NAMA_PANGGILAN                              = "nama_panggilan";
    private static final String ROW_IDENTITY_NO                                 = "identity_no";
    private static final String ROW_SANDI_LAHIR                                 = "sandi_lahir";
    private static final String ROW_RELIGION                                    = "religion";
    private static final String ROW_KECAMATAN_KTP                               = "kecamatan_ktp";
    private static final String ROW_PROVINCE_KTP                                = "province_ktp";
    private static final String ROW_KELURAHAN_KTP                               = "kelurahan_ktp";
    private static final String ROW_KECAMATAN_HOME                              = "kecamatan_home";
    private static final String ROW_PROVINCE_HOME                               = "province_home";
    private static final String ROW_KELURAHAN_HOME                              = "kelurahan_home";
    private static final String ROW_STAY_LENGTH                                 = "stay_length";
    private static final String ROW_HANDPHONE_STAY_1                            = "handphone_stay_1";
    private static final String ROW_HANDPHONE_STAY_2                            = "handphone_stay_2";
    private static final String ROW_EMAIL_STAY                                  = "email_stay";
    private static final String ROW_PEKERJAAN                                   = "pekerjaan";
    private static final String ROW_JOB_TITLE_PEKERJAAN                         = "job_title_pekerjaan";
    private static final String ROW_OCCUPATION_OR_PEKERJAAN                     = "occupation_or_pekerjaan";
    private static final String ROW_NAME_ECONOMY_CODE                           = "name_economy_code";
    private static final String ROW_COMPANY_NAME                                = "company_name";
    private static final String ROW_ADDRESS_PEKERJAAN                           = "address_pekerjaan";
    private static final String ROW_SANDI_DATI_2_PEKERJAAN                      = "sandi_dati_2_pekerjaan";
    private static final String ROW_KAB_OR_KODYA_PEKERJAAN                      = "kab_or_kodya_pekerjaan";
    private static final String ROW_POSTAL_CODE_PEKERJAAN                       = "postal_code_pekerjaan";
    private static final String ROW_TELEPHONE_1_PEKERJAAN                       = "telephone_1_pekerjaan";
    private static final String ROW_TELEPHONE_2_PEKERJAAN                       = "telephone_2_pekerjaan";
    private static final String ROW_LINE_OF_BUSINESS                            = "line_of_business";
    private static final String ROW_ECONOMY_CODE                                = "economy_code";
    private static final String ROW_ESTABILISHED_SINCE                          = "estabilished_since";
    private static final String ROW_KECAMATAN_PEKERJAAN                         = "kecamatan_pekerjaan";
    private static final String ROW_PROVINCE_PEKERJAAN                          = "province_pekerjaan";
    private static final String ROW_KELURAHAN_PEKERJAAN                         = "kelurahan_pekerjaan";
    private static final String ROW_FAX_1_PEKERJAAN                             = "fax_1_pekerjaan";
    private static final String ROW_NAME_SPOUSE                                 = "name_spouse";
    private static final String ROW_TITLE_SPOUSE                                = "title_spouse";
    private static final String ROW_IDENTITY_TYPE_SPOUSE                        = "identity_type_spouse";
    private static final String ROW_BIRTH_PLACE_SPOUSE                          = "birth_place_spouse";
    private static final String ROW_RELIGION_SPOUSE                             = "religion_spouse";
    private static final String ROW_ADDRESS_SPOUSE                              = "address_spouse";
    private static final String ROW_SANDI_DATI_2_SPOUSE                         = "sandi_dati_2_spouse";
    private static final String ROW_KAB_OR_KODYA_SPOUSE                         = "kab_or_kodya_spouse";
    private static final String ROW_POSTAL_CODE_SPOUSE                          = "postal_code_spouse";
    private static final String ROW_NO_HANDPHONE_SPOUSE                         = "no_handphone_spouse";
    private static final String ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE              = "occupation_or_pekerjaan_spouse";
    private static final String ROW_COMPANY_NAME_SPOUSE                         = "company_name_spouse";
    private static final String ROW_ADDRESS_PEKERJAAN_SPOUSE                    = "address_pekerjaan_spouse";
    private static final String ROW_TELEPHONE_SPOUSE                            = "telephone_spouse";
    private static final String ROW_LINE_OF_BUSSINESS_SPOUSE                    = "line_of_business_spouse";
    private static final String ROW_JOB_TITLE_SPOUSE                            = "job_title_spouse";
    private static final String ROW_SEX_SPOUSE                                  = "sex_spouse";
    private static final String ROW_IDENTITY_NO_SPOUSE                          = "identity_no_spouse";
    private static final String ROW_DATE_OF_BIRTH_SPOUSE                        = "date_of_birth_spouse";
    private static final String ROW_KECAMATAN_SPOUSE                            = "kecamatan_spouse";
    private static final String ROW_PROVINCE_SPOUSE                             = "province_spouse";
    private static final String ROW_KELURAHAN_SPOUSE                            = "kelurahan_spouse";
    private static final String ROW_ESTABILISHED_SINCE_SPOUSE                   = "estabilished_since_spouse";
    private static final String ROW_FAX_SPOUSE                                  = "fax_spouse";
    private static final String ROW_HAS_CONTACT_PERSON                          = "has_contact_person";
    private static final String ROW_NAME_CONTACT                                = "name_contact";
    private static final String ROW_ADDRESS_CONTACT                             = "address_contact";
    private static final String ROW_SANDI_DATI_2_CONTACT                        = "sandi_dati_2_contact";
    private static final String ROW_KAB_OR_KODYA_CONTACT                        = "kab_or_kodya_contact";
    private static final String ROW_POSTAL_CODE_CONTACT                         = "postal_code_contact";
    private static final String ROW_TELEPHONE_CONTACT                           = "telephone_contact";
    private static final String ROW_RELATIONSHIP_CONTACT                        = "relationship_contact";
    private static final String ROW_KECAMATAN_CONTACT                           = "kecamatan_contact";
    private static final String ROW_PROVINCE_CONTACT                            = "province_contact";
    private static final String ROW_KELURAHAN_CONTACT                           = "kelurahan_contact";
    private static final String ROW_JARAK_RUMAH_KE_CABANG                       = "jarak_rumah_ke_cabang";
    private static final String ROW_LUAS_TANAH                                  = "luas_tanah";
    private static final String ROW_LUAS_BANGUNAN_RUMAH                         = "luas_bangunan_rumah";
    private static final String ROW_STATUS_KEPEMILIKAN_RUMAH                    = "status_kepemilikan_rumah";
    private static final String ROW_KLASIFIKASI_PERUMAHAN                       = "klasifikasi_perumahan";
    private static final String ROW_TEMPAT_MENARUH_KENDARAAN                    = "tempat_menaruh_kendaraan";
    private static final String ROW_STATUS_GARASI_KENDARAAN                     = "status_garasi_kendaraan";
    private static final String ROW_BENTUK_BANGUNAN_RUMAH                       = "bentuk_bangunan_rumah";
    private static final String ROW_KONDISI_RUMAH                               = "kondisi_rumah";
    private static final String ROW_LUAS_JALAN_MASUK_RUMAH                      = "luas_jalan_masuk_rumah";
    private static final String ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON            = "status_kepemilikan_rumah_pemohon";
    private static final String ROW_FURNITURE                                   = "furniture";
    private static final String ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH               = "jarak_tempat_usaha_dari_rumah";
    private static final String ROW_STATUS_KEPEMILIKAN_USAHA                    = "status_kepemilikan_usaha";
    private static final String ROW_BENTUK_BANGUNAN_TEMPAT_USAHA                = "bentuk_bangunan_tempat_usaha";
    private static final String ROW_LOKASI_TEMPAT_USAHA                         = "lokasi_tempat_usaha";
    private static final String ROW_JUMLAH_KARYAWAN                             = "jumlah_karyawan";
    private static final String ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN   = "lama_pemohon_menempati_tempat_usaha_tahun";
    private static final String ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN   = "lama_pemohon_menempati_tempat_usaha_bulan";
    private static final String ROW_LAMA_USAHA_BERDIRI_TAHUN                    = "lama_usaha_berdiri_tahun";
    private static final String ROW_LAMA_USAHA_BERDIRI_BULAN                    = "lama_usaha_berdiri_bulan";
    private static final String ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR  = "pekerjaan_or_usaha_terkait_ekspor_or_impor";
    private static final String ROW_TUJUAN_PENGGUNAAN_UNIT                      = "tujuan_penggunaan_unit";
    private static final String ROW_KONDISI_MOBIL                               = "kondisi_mobil";
    private static final String ROW_BAGIAN_KONDISI_TIDAK_BAIK                   = "bagian_kondisi_tidak_baik";
    private static final String ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN            = "lama_kepemilikan_kendaraan_tahun";
    private static final String ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN            = "lama_kepemilikan_kendaraan_bulan";
    private static final String ROW_JUMLAH_TANGGUNGAN                           = "jumlah_tanggungan";
    private static final String ROW_JUMLAH_ANAK                                 = "jumlah_anak";
    private static final String ROW_OMZET_OR_PENGHASILAN_GROSS                  = "omzet_or_penghasilan_gross";
    private static final String ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY           = "penghasilan_nett_or_take_home_pay";
    private static final String ROW_PENGHASILAN_PASANGAN                        = "penghasilan_pasangan";
    private static final String ROW_PENGHASILAN_TAMBAHAN                        = "penghasilan_tambahan";
    private static final String ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP              = "pengeluaran_or_kebutuhan_hidup";
    private static final String ROW_TOTAL_CICILAN_LEASING_LAIN                  = "total_cicilan_leasing_lain";
    private static final String ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN       = "balance_terakhir_di_rekening_tabungan";
    private static final String ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR        = "rata_rata_mutasi_in_3_bulan_terakhir";
    private static final String ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR       = "rata_rata_mutasi_out_3_bulan_terakhir";
    private static final String ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI       = "collectabilitas_sid_or_slik_tertinggi";
    private static final String ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN                = "pernah_kredit_di_tempat_lain";
    private static final String ROW_OVERDUE_TERTINGGI                           = "overdue_tertinggi";
    private static final String ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG            = "baki_debet_or_outstanding_hutang";
    private static final String ROW_NAMA_FINANCE_COMPANY                        = "nama_finance_company";
    private static final String ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2   = "alasan_menunggak_khusus_lebih_dari_coll_2";
    private static final String ROW_APAKAH_DIREKOMENDASIKAN                     = "apakah_direkomendasikan";
    private static final String ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA    = "alasan_or_point_penting_rekomendasi_anda";
    private static final String NAMA_TABEL_SIMPANSURVEY                         = "simpansurvey";
    private static final String CREATE_TABLE_SIMPANSURVEY                       = "create table "
            + NAMA_TABEL_SIMPANSURVEY                           + " ("
            + ROW_ID_SURVEY                                     + " integer PRIMARY KEY autoincrement,"
            + ROW_ID_ORDER                                      + " text,"
            + ROW_ID_SURVEYOR                                   + " text,"
            + ROW_NAME                                          + " text,"
            + ROW_MOTHER_MAIDEN_NAME                            + " text,"
            + ROW_GELAR                                         + " text,"
            + ROW_TITLE                                         + " text,"
            + ROW_MARITAL_STATUS                                + " text,"
            + ROW_IDENTITY_TYPE                                 + " text,"
            + ROW_NPWP_NO                                       + " text,"
            + ROW_BIRTH_PLACE                                   + " text,"
            + ROW_BIRTH_DATE                                    + " text,"
            + ROW_ADDRESS_KTP                                   + " text,"
            + ROW_SANDI_DATI_2_KTP                              + " text,"
            + ROW_KAB_OR_KODYA_KTP                              + " text,"
            + ROW_POSTAL_CODE_KTP                               + " text,"
            + ROW_ADDRESS_HOME                                  + " text,"
            + ROW_SANDI_DATI_2_HOME                             + " text,"
            + ROW_KAB_OR_KODYA_HOME                             + " text,"
            + ROW_POSTAL_CODE_HOME                              + " text,"
            + ROW_MAIL_ADDRESS                                  + " text,"
            + ROW_TIPE_RUMAH                                    + " text,"
            + ROW_HOME_STATUS                                   + " text,"
            + ROW_TELEPHONE                                     + " text,"
            + ROW_TELEPHONE_2                                   + " text,"
            + ROW_EDUCATION                                     + " text,"
            + ROW_SEX                                           + " text,"
            + ROW_NAMA_PANGGILAN                                + " text,"
            + ROW_IDENTITY_NO                                   + " text,"
            + ROW_SANDI_LAHIR                                   + " text,"
            + ROW_RELIGION                                      + " text,"
            + ROW_KECAMATAN_KTP                                 + " text,"
            + ROW_PROVINCE_KTP                                  + " text,"
            + ROW_KELURAHAN_KTP                                 + " text,"
            + ROW_KECAMATAN_HOME                                + " text,"
            + ROW_PROVINCE_HOME                                 + " text,"
            + ROW_KELURAHAN_HOME                                + " text,"
            + ROW_STAY_LENGTH                                   + " text,"
            + ROW_HANDPHONE_STAY_1                              + " text,"
            + ROW_HANDPHONE_STAY_2                              + " text,"
            + ROW_EMAIL_STAY                                    + " text,"
            + ROW_PEKERJAAN                                     + " text,"
            + ROW_JOB_TITLE_PEKERJAAN                           + " text,"
            + ROW_OCCUPATION_OR_PEKERJAAN                       + " text,"
            + ROW_NAME_ECONOMY_CODE                             + " text,"
            + ROW_COMPANY_NAME                                  + " text,"
            + ROW_ADDRESS_PEKERJAAN                             + " text,"
            + ROW_SANDI_DATI_2_PEKERJAAN                        + " text,"
            + ROW_KAB_OR_KODYA_PEKERJAAN                        + " text,"
            + ROW_POSTAL_CODE_PEKERJAAN                         + " text,"
            + ROW_TELEPHONE_1_PEKERJAAN                         + " text,"
            + ROW_TELEPHONE_2_PEKERJAAN                         + " text,"
            + ROW_LINE_OF_BUSINESS                              + " text,"
            + ROW_ECONOMY_CODE                                  + " text,"
            + ROW_ESTABILISHED_SINCE                            + " text,"
            + ROW_KECAMATAN_PEKERJAAN                           + " text,"
            + ROW_PROVINCE_PEKERJAAN                            + " text,"
            + ROW_KELURAHAN_PEKERJAAN                           + " text,"
            + ROW_FAX_1_PEKERJAAN                               + " text,"
            + ROW_NAME_SPOUSE                                   + " text,"
            + ROW_TITLE_SPOUSE                                  + " text,"
            + ROW_IDENTITY_TYPE_SPOUSE                          + " text,"
            + ROW_BIRTH_PLACE_SPOUSE                            + " text,"
            + ROW_RELIGION_SPOUSE                               + " text,"
            + ROW_ADDRESS_SPOUSE                                + " text,"
            + ROW_SANDI_DATI_2_SPOUSE                           + " text,"
            + ROW_KAB_OR_KODYA_SPOUSE                           + " text,"
            + ROW_POSTAL_CODE_SPOUSE                            + " text,"
            + ROW_NO_HANDPHONE_SPOUSE                           + " text,"
            + ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE                + " text,"
            + ROW_COMPANY_NAME_SPOUSE                           + " text,"
            + ROW_ADDRESS_PEKERJAAN_SPOUSE                      + " text,"
            + ROW_TELEPHONE_SPOUSE                              + " text,"
            + ROW_LINE_OF_BUSSINESS_SPOUSE                      + " text,"
            + ROW_JOB_TITLE_SPOUSE                              + " text,"
            + ROW_SEX_SPOUSE                                    + " text,"
            + ROW_IDENTITY_NO_SPOUSE                            + " text,"
            + ROW_DATE_OF_BIRTH_SPOUSE                          + " text,"
            + ROW_KECAMATAN_SPOUSE                              + " text,"
            + ROW_PROVINCE_SPOUSE                               + " text,"
            + ROW_KELURAHAN_SPOUSE                              + " text,"
            + ROW_ESTABILISHED_SINCE_SPOUSE                     + " text,"
            + ROW_FAX_SPOUSE                                    + " text,"
            + ROW_HAS_CONTACT_PERSON                            + " text,"
            + ROW_NAME_CONTACT                                  + " text,"
            + ROW_ADDRESS_CONTACT                               + " text,"
            + ROW_SANDI_DATI_2_CONTACT                          + " text,"
            + ROW_KAB_OR_KODYA_CONTACT                          + " text,"
            + ROW_POSTAL_CODE_CONTACT                           + " text,"
            + ROW_TELEPHONE_CONTACT                             + " text,"
            + ROW_RELATIONSHIP_CONTACT                          + " text,"
            + ROW_KECAMATAN_CONTACT                             + " text,"
            + ROW_PROVINCE_CONTACT                              + " text,"
            + ROW_KELURAHAN_CONTACT                             + " text,"
            + ROW_JARAK_RUMAH_KE_CABANG                         + " text,"
            + ROW_LUAS_TANAH                                    + " text,"
            + ROW_LUAS_BANGUNAN_RUMAH                           + " text,"
            + ROW_STATUS_KEPEMILIKAN_RUMAH                      + " text,"
            + ROW_KLASIFIKASI_PERUMAHAN                         + " text,"
            + ROW_TEMPAT_MENARUH_KENDARAAN                      + " text,"
            + ROW_STATUS_GARASI_KENDARAAN                       + " text,"
            + ROW_BENTUK_BANGUNAN_RUMAH                         + " text,"
            + ROW_KONDISI_RUMAH                                 + " text,"
            + ROW_LUAS_JALAN_MASUK_RUMAH                        + " text,"
            + ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON              + " text,"
            + ROW_FURNITURE                                     + " text,"
            + ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH                 + " text,"
            + ROW_STATUS_KEPEMILIKAN_USAHA                      + " text,"
            + ROW_BENTUK_BANGUNAN_TEMPAT_USAHA                  + " text,"
            + ROW_LOKASI_TEMPAT_USAHA                           + " text,"
            + ROW_JUMLAH_KARYAWAN                               + " text,"
            + ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN     + " text,"
            + ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN     + " text,"
            + ROW_LAMA_USAHA_BERDIRI_TAHUN                      + " text,"
            + ROW_LAMA_USAHA_BERDIRI_BULAN                      + " text,"
            + ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR    + " text,"
            + ROW_TUJUAN_PENGGUNAAN_UNIT                        + " text,"
            + ROW_KONDISI_MOBIL                                 + " text,"
            + ROW_BAGIAN_KONDISI_TIDAK_BAIK                     + " text,"
            + ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN              + " text,"
            + ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN              + " text,"
            + ROW_JUMLAH_TANGGUNGAN                             + " text,"
            + ROW_JUMLAH_ANAK                                   + " text,"
            + ROW_OMZET_OR_PENGHASILAN_GROSS                    + " text,"
            + ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY             + " text,"
            + ROW_PENGHASILAN_PASANGAN                          + " text,"
            + ROW_PENGHASILAN_TAMBAHAN                          + " text,"
            + ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP                + " text,"
            + ROW_TOTAL_CICILAN_LEASING_LAIN                    + " text,"
            + ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN         + " text,"
            + ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR          + " text,"
            + ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR         + " text,"
            + ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI         + " text,"
            + ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN                  + " text,"
            + ROW_OVERDUE_TERTINGGI                             + " text,"
            + ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG              + " text,"
            + ROW_NAMA_FINANCE_COMPANY                          + " text,"
            + ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2     + " text,"
            + ROW_APAKAH_DIREKOMENDASIKAN                       + " text,"
            + ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA      + " text)";
    //end variable SURVEY

    //variable sandidati2
    private static final String ROW_ID_SANDIDATI2               = "json_sandidati2_id";
    private static final String ROW_SANDIDATI2_ID_SANDIDATI2    = "id_sandidati2";
    private static final String ROW_SANDIDATI2_ALAMATID         = "alamatid";
    private static final String ROW_SANDIDATI2_KODEPOS          = "kodepos";
    private static final String ROW_SANDIDATI2_KELURAHAN        = "kelurahan";
    private static final String ROW_SANDIDATI2_KECAMATAN        = "kecamatan";
    private static final String ROW_SANDIDATI2_KBPKTM           = "kbpktm";
    private static final String ROW_SANDIDATI2_WIL              = "wil";
    private static final String ROW_SANDIDATI2_PROPINSI         = "propinsi";
    private static final String ROW_SANDIDATI2_SANDIDATI2       = "sandidati2";
    private static final String NAMA_TABEL_SANDIDATI2           = "sandidati2";
    private static final String CREATE_TABLE_SANDIDATI2         = "create table "
            + NAMA_TABEL_SANDIDATI2         +" ("
            + ROW_ID_SANDIDATI2             + " integer PRIMARY KEY autoincrement,"
            + ROW_SANDIDATI2_ID_SANDIDATI2  + " text,"
            + ROW_SANDIDATI2_ALAMATID       + " text,"
            + ROW_SANDIDATI2_KODEPOS        + " text,"
            + ROW_SANDIDATI2_KELURAHAN      + " text,"
            + ROW_SANDIDATI2_KECAMATAN      + " text,"
            + ROW_SANDIDATI2_KBPKTM         + " text,"
            + ROW_SANDIDATI2_WIL            + " text,"
            + ROW_SANDIDATI2_PROPINSI       + " text,"
            + ROW_SANDIDATI2_SANDIDATI2     + " text)";
    //end variable sandidati2

    //variable simpan json_pilih
    private static final String ROW_ID_JSON_PILIH       = "json_pilih_id";
    private static final String ROW_JSON_PILIH_HASIL    = "json_hasil";
    private static final String ROW_JSON_PILIH_NAMA     = "json_nama";
    private static final String ROW_JSON_PILIH_TANGGAL  = "json_tanggal";
    private static final String NAMA_TABEL_JSON_PILIH   = "simpanjsonpilih";
    private static final String CREATE_TABLE_JSON_PILIH = "create table "
            + NAMA_TABEL_JSON_PILIH     + " ("
            + ROW_ID_JSON_PILIH         + " integer PRIMARY KEY autoincrement,"
            + ROW_JSON_PILIH_HASIL      + " text,"
            + ROW_JSON_PILIH_NAMA       + " text,"
            + ROW_JSON_PILIH_TANGGAL    + " text)";
    //end variable simpan json_pilih

    //variable simpan notif tugas
    private static final String ROW_ID_NOTIF_TUGAS          = "notif_tugas_id";
    private static final String ROW_NOTIF_TUGAS_ID_ORDER    = "notif_id_order";
    private static final String ROW_NOTIF_TUGAS_NAMA        = "notif_nama";
    private static final String ROW_NOTIF_TUGAS_ALAMAT      = "notif_alamat";
    private static final String ROW_NOTIF_TUGAS_STATUS      = "notif_status";
    private static final String NAMA_TABEL_NOTIF_TUGAS      = "simpannotif";
    private static final String CREATE_TABLE_NOTIF_TUGAS    = "create table "
            + NAMA_TABEL_NOTIF_TUGAS    + " ("
            + ROW_ID_NOTIF_TUGAS        + " integer PRIMARY KEY autoincrement,"
            + ROW_NOTIF_TUGAS_ID_ORDER  + " text,"
            + ROW_NOTIF_TUGAS_NAMA      + " text,"
            + ROW_NOTIF_TUGAS_ALAMAT    + " text,"
            + ROW_NOTIF_TUGAS_STATUS    + " text)";
    //end variable simpan notif tugas

    //VARIABLE status
    private static final String ROW_STATUS_PERNIKAHAN_ID        = "status_pernikahan_id";
    private static final String ROW_STATUS_PERNIKAHAN_HASIL     = "status_pernikahan";
    private static final String ROW_STATUS_PERNIKAHAN_ID_ORDER  = "id_order";
    private static final String NAMA_TABEL_STATUS_PERNIKAHAN    = "status_pernikahan_input";
    private static final String CREATE_TABLE_STATUS_PERNIKAHAN  = "create table "
            + NAMA_TABEL_STATUS_PERNIKAHAN      + " ("
            + ROW_STATUS_PERNIKAHAN_ID          + " integer PRIMARY KEY autoincrement,"
            + ROW_STATUS_PERNIKAHAN_HASIL       + " text,"
            + ROW_STATUS_PERNIKAHAN_ID_ORDER    + " text)";
    //end variable status


    //VARIABLE status
    private static final String ROW_STATUS_TERKIRIM_ID        = "status_terkirim_id";
    private static final String ROW_STATUS_TERKIRIM_ID_ORDER  = "status_terkirim_id_order";
    private static final String NAMA_TABEL_STATUS_TERKIRIM    = "tb_status_terkirim";
    private static final String CREATE_TABLE_STATUS_TERKIRIM  = "create table "
            + NAMA_TABEL_STATUS_TERKIRIM      + " ("
            + ROW_STATUS_TERKIRIM_ID          + " integer PRIMARY KEY autoincrement,"
            + ROW_STATUS_TERKIRIM_ID_ORDER    + " text)";
    //end variable status

    //VARIABLE notif sampai
    private static final String ROW_NOTIF_SAMPAI_ID             = "notif_sampai_id";
    private static final String ROW_NOTIF_SAMPAI_ID_SURVEYOR    = "notif_sampai_id_surveyor";
    private static final String ROW_NOTIF_SAMPAI_ID_ORDER       = "notif_sampai_id_order";
    private static final String ROW_NOTIF_SAMPAI_STATUS_HASIL   = "notif_sampai_status_hasil";
    private static final String ROW_NOTIF_SAMPAI_LATITUDE       = "notif_sampai_latitude";
    private static final String ROW_NOTIF_SAMPAI_LONGITUDE      = "notif_sampai_longitude";
    private static final String ROW_NOTIF_SAMPAI_TANGGAL        = "notif_sampai_tanggal";
    private static final String ROW_NOTIF_SAMPAI_TK             = "notif_sampai_tk";
    private static final String NAMA_TABEL_NOTIF_SAMPAI         = "tb_notif_sampai";
    private static final String CREATE_TABLE_NOTIF_SAMPAI       = "create table "
            + NAMA_TABEL_NOTIF_SAMPAI       + " ("
            + ROW_NOTIF_SAMPAI_ID           + " integer PRIMARY KEY autoincrement,"
            + ROW_NOTIF_SAMPAI_ID_SURVEYOR  + " text,"
            + ROW_NOTIF_SAMPAI_ID_ORDER     + " text,"
            + ROW_NOTIF_SAMPAI_STATUS_HASIL + " text,"
            + ROW_NOTIF_SAMPAI_LATITUDE     + " text,"
            + ROW_NOTIF_SAMPAI_LONGITUDE    + " text,"
            + ROW_NOTIF_SAMPAI_TANGGAL      + " text,"
            + ROW_NOTIF_SAMPAI_TK           + " text)";
    //end variable notif sampai


    //VARIABLE janji survey
    private static final String ROW_JANJI_SURVEY_ID             = "notif_sampai_id";
    private static final String ROW_JANJI_SURVEY_ID_SURVEYOR    = "notif_sampai_id_surveyor";
    private static final String ROW_JANJI_SURVEY_ID_ORDER       = "notif_sampai_id_order";
    private static final String ROW_JANJI_SURVEY_JANJI_SURVEY   = "notif_sampai_janji_survey";
    private static final String ROW_JANJI_SURVEY_TK             = "notif_sampai_tk";
    private static final String NAMA_TABEL_JANJI_SURVEY         = "tb_janji_survey";
    private static final String CREATE_TABLE_JANJI_SURVEY       = "create table "
            + NAMA_TABEL_JANJI_SURVEY       + " ("
            + ROW_JANJI_SURVEY_ID           + " integer PRIMARY KEY autoincrement,"
            + ROW_JANJI_SURVEY_ID_SURVEYOR  + " text,"
            + ROW_JANJI_SURVEY_ID_ORDER     + " text,"
            + ROW_JANJI_SURVEY_JANJI_SURVEY + " text,"
            + ROW_JANJI_SURVEY_TK           + " text)";
    //end variable janji survey

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    private static class DatabaseOpenHelper extends
            SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_STATUS);
            db.execSQL(CREATE_TABLE_TUGAS);
            db.execSQL(CREATE_TABLE_PHOTO);
            db.execSQL(CREATE_TABLE_CHATDETAIL);
            db.execSQL(CREATE_TABLE_KIRIMLOKASI);
            db.execSQL(CREATE_TABLE_SIMPANSURVEY);
            db.execSQL(CREATE_TABLE_JSON_PILIH);
            db.execSQL(CREATE_TABLE_SANDIDATI2);
            db.execSQL(CREATE_TABLE_NOTIF_TUGAS);
            db.execSQL(CREATE_TABLE_TAB);
            db.execSQL(CREATE_TABLE_STATUS_PERNIKAHAN);
            db.execSQL(CREATE_TABLE_STATUS_TERKIRIM);
            db.execSQL(CREATE_TABLE_NOTIF_SAMPAI);
            db.execSQL(CREATE_TABLE_JANJI_SURVEY);
            db.execSQL(CREATE_TABLE_SIMPANSURVEY1_TAMBAHAN);
            db.execSQL(CREATE_TABLE_SIMPANSURVEY3_TAMBAHAN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int
                newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_STATUS);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_JSON_TUGAS);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_PHOTO);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_CHATDETAIL);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_KIRIMLOKASI);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_SIMPANSURVEY);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_JSON_PILIH);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_SANDIDATI2);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_NOTIF_TUGAS);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_TAB);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_STATUS_PERNIKAHAN);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_STATUS_TERKIRIM);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_NOTIF_SAMPAI);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_JANJI_SURVEY);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN);
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN);
            onCreate(db);
        }
    }

    public void close() {
        dbHelper.close();
    }

    //table login
    public void addRow(String username, String userid, String alamat, String namalengkap) {
        ContentValues values = new ContentValues();
        values.put(ROW_USERNAME, username);
        values.put(ROW_USERID, userid);
        values.put(ROW_ALAMAT, alamat);
        values.put(ROW_NAMALENGKAP, namalengkap);
        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL, new String[] {  ROW_USERNAME,
                    ROW_ALAMAT,ROW_NAMALENGKAP,ROW_USERID }, null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<Object> ambilBaris(long rowId) {
        ArrayList<Object> arrbaris = new ArrayList<Object>();
        Cursor cursor = null;
        try {
            cursor = db.query(NAMA_TABEL, new String[] { ROW_ID,
                            ROW_USERNAME,ROW_USERID,ROW_ALAMAT,ROW_NAMALENGKAP }, ROW_ID + "=" + rowId, null, null, null,
                    null,null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    arrbaris.add(cursor.getLong(0));
                    arrbaris.add(cursor.getString(1));
                    arrbaris.add(cursor.getString(2));
                    arrbaris.add(cursor.getString(3));
                    arrbaris.add(cursor.getString(4));
                } while (cursor.moveToNext());
                String r = String.valueOf(arrbaris);
                Toast.makeText(context, "haha" + r,
                        Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            Toast.makeText(context, "hhii" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }
        return arrbaris;
    }

    public void deleteAll(){
        try {
            db.delete(NAMA_TABEL, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //end table login

    //table STATUS
    public void addRow_status(String username, String userid, String status) {
        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_USERNAME, username);
        values.put(ROW_STATUS_USERID, userid);
        values.put(ROW_STATUS_INPUT, status);
        try {
            db.insert(NAMA_TABEL_STATUS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris_status() {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_STATUS, new String[] {  ROW_STATUS_USERNAME,
                    ROW_STATUS_USERID,ROW_STATUS_INPUT }, null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deleteAll_status(){
        try {
            db.delete(NAMA_TABEL_STATUS, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //end table status


    //table simpan json
    public void addRowTugas(String json_tugas,String json_nama) {
        ContentValues values = new ContentValues();
        values.put(ROW_TUGAS_JSON, json_tugas);
        values.put(ROW_TUGAS_NAMA, json_nama);
        values.put(ROW_TUGAS_TANGGAL, "datetime()");

        try {
            db.insert(NAMA_TABEL_JSON_TUGAS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowTugasId(String json_tugas,String json_nama,String json_id_order) {
        ContentValues values = new ContentValues();
        values.put(ROW_TUGAS_JSON, json_tugas);
        values.put(ROW_TUGAS_NAMA, json_nama);
        values.put(ROW_TUGAS_ID_ORDER, json_id_order);
        values.put(ROW_TUGAS_TANGGAL, "datetime()");

        try {
            db.insert(NAMA_TABEL_JSON_TUGAS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteTugasAll(String json_name){
        try {
            db.delete(NAMA_TABEL_JSON_TUGAS, ROW_TUGAS_NAMA + "='" + json_name+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public void deleteTugasAllId(String json_name,String json_id_order){
        try {
            db.delete(NAMA_TABEL_JSON_TUGAS, ROW_TUGAS_ID_ORDER+"="+json_id_order+" AND "+ROW_TUGAS_NAMA + "='" + json_name+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisJson(String json_name) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_JSON_TUGAS, new String[] {  ROW_TUGAS_JSON,
                    ROW_TUGAS_ID_ORDER,ROW_TUGAS_NAMA,ROW_TUGAS_TANGGAL }, ROW_TUGAS_NAMA + "='" + json_name+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }
    //table simpan json

    //table simpan photo
    public void addRowPhoto(String photo_nama,String photo_link, String photo_bitmap,
                            String sphoto_latitude, String sphoto_longitude, String photo_id_order) {
        ContentValues values = new ContentValues();
        values.put(ROW_PHOTO_NAMA, photo_nama);
        values.put(ROW_PHOTO_LINK, photo_link);
        values.put(ROW_PHOTO_BITMAP, photo_bitmap);
        values.put(ROW_PHOTO_LATITUDE, sphoto_latitude);
        values.put(ROW_PHOTO_LONGITUDE, sphoto_longitude);
        values.put(ROW_PHOTO_ID_ORDER, photo_id_order);
        values.put(ROW_PHOTO_STATUS, "0");

        try {
            db.insert(NAMA_TABEL_PHOTO, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisPhoto(String photo_name, String photo_id_order) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_PHOTO, new String[] {  ROW_PHOTO_NAMA,
                    ROW_PHOTO_LINK,ROW_PHOTO_BITMAP,ROW_PHOTO_LATITUDE,ROW_PHOTO_LONGITUDE,ROW_PHOTO_ID_ORDER }, ROW_PHOTO_ID_ORDER + "=" + photo_id_order +" AND "+ROW_PHOTO_NAMA + "='" + photo_name+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void updateBarisPhoto(String photo_id_order) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PHOTO_STATUS, "1");

        try {
            db.update(NAMA_TABEL_PHOTO, cv, ROW_PHOTO_ID_ORDER + "=" + photo_id_order, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisPhotoAll(String id_order) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_PHOTO, new String[] {  ROW_PHOTO_NAMA,
                    ROW_PHOTO_LINK,ROW_PHOTO_BITMAP,ROW_PHOTO_LATITUDE,ROW_PHOTO_LONGITUDE,
                    ROW_PHOTO_ID_ORDER,ROW_PHOTO_STATUS },
                    ROW_PHOTO_ID_ORDER+" = '"+id_order+"'", null,
                    null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deletePhotoAll(String photo_name, String photo_id_order){
        try {
            db.delete(NAMA_TABEL_PHOTO, ROW_PHOTO_ID_ORDER + "=" + photo_id_order +" AND "+ROW_PHOTO_NAMA + "='" + photo_name+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //table simpan photo

    //table simpan tab
    public void addRowTab(String tab_nama,String tab_ke, String tab_id_order) {
        ContentValues values = new ContentValues();
        values.put(ROW_TAB_NAMA, tab_nama);
        values.put(ROW_TAB_KE, tab_ke);
        values.put(ROW_TAB_STATUS, "0");
        values.put(ROW_TAB_ID_ORDER, tab_id_order);

        try {
            db.insert(NAMA_TABEL_TAB, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisTab(String tab_ke, String tab_id_order) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_TAB, new String[] {  ROW_TAB_NAMA,
                    ROW_TAB_KE,ROW_TAB_ID_ORDER,ROW_TAB_STATUS }, ROW_TAB_ID_ORDER + "=" + tab_id_order +" AND "+ROW_TAB_KE + "='" + tab_ke+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<ArrayList<Object>> ambilBarisTabAll(String tab_id_order) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_TAB, new String[] {  ROW_TAB_NAMA,
                    ROW_TAB_KE,ROW_TAB_ID_ORDER,ROW_TAB_STATUS}, ROW_TAB_ID_ORDER + "=" + tab_id_order, null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }
    //table simpan tab

    //table simpan chat
    public void addRowChatdetail(String id_balas_message, String id_message, String to_id_admin, String from_id_admin, String from_nama, String balas_message, String entry_date_balas, String type_message) {

        ContentValues values = new ContentValues();
        values.put(ROW_CHATDETAIL_ID_BALAS_MESSAGE, id_balas_message);
        values.put(ROW_CHATDETAIL_ID_MESSAGE, id_message);
        values.put(ROW_CHATDETAIL_TO_ID_ADMIN, to_id_admin);
        values.put(ROW_CHATDETAIL_FROM_ID_ADMIN, from_id_admin);
        values.put(ROW_CHATDETAIL_FROM_NAMA, from_nama);
        values.put(ROW_CHATDETAIL_BALAS_MESSAGE, balas_message);
        values.put(ROW_CHATDETAIL_ENTRY_DATE_BALAS, entry_date_balas);
        values.put(ROW_CHATDETAIL_TYPE_MESSAGE, type_message);
        values.put(ROW_CHATDETAIL_STATUS, "Sukses");

        try {
            db.insert(NAMA_TABEL_CHATDETAIL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowChatdetailinput(String id_message, String to_id_admin, String from_id_admin, String balas_message, String from_nama, String time) {
        ContentValues values = new ContentValues();
        values.put(ROW_CHATDETAIL_ID_BALAS_MESSAGE, "0");
        values.put(ROW_CHATDETAIL_ID_MESSAGE, id_message);
        values.put(ROW_CHATDETAIL_TO_ID_ADMIN, to_id_admin);
        values.put(ROW_CHATDETAIL_FROM_ID_ADMIN, from_id_admin);
        values.put(ROW_CHATDETAIL_FROM_NAMA, from_nama);
        values.put(ROW_CHATDETAIL_BALAS_MESSAGE, balas_message);
        values.put(ROW_CHATDETAIL_ENTRY_DATE_BALAS, time);
        values.put(ROW_CHATDETAIL_TYPE_MESSAGE, "2");
        values.put(ROW_CHATDETAIL_STATUS, "Pending");

        try {
            db.insert(NAMA_TABEL_CHATDETAIL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisChatdetail(String id_message) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_CHATDETAIL, new String[] {  ROW_CHATDETAIL_ID_BALAS_MESSAGE,
                    ROW_CHATDETAIL_ID_MESSAGE,ROW_CHATDETAIL_TO_ID_ADMIN,ROW_CHATDETAIL_FROM_ID_ADMIN,ROW_CHATDETAIL_FROM_NAMA,ROW_CHATDETAIL_BALAS_MESSAGE,ROW_CHATDETAIL_ENTRY_DATE_BALAS,ROW_CHATDETAIL_TYPE_MESSAGE,ROW_CHATDETAIL_STATUS, ROW_ID_CHATDETAIL }, ROW_CHATDETAIL_ID_MESSAGE + "=" + id_message, null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));
                    dataList.add(cur.getString(8));
                    dataList.add(cur.getString(9));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<ArrayList<Object>> ambilBarisChatdetailcheck(String id_balas_message) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_CHATDETAIL, new String[] {  ROW_CHATDETAIL_ID_BALAS_MESSAGE,
                    ROW_CHATDETAIL_ID_MESSAGE,ROW_CHATDETAIL_TO_ID_ADMIN,ROW_CHATDETAIL_FROM_ID_ADMIN,ROW_CHATDETAIL_FROM_NAMA,ROW_CHATDETAIL_BALAS_MESSAGE,ROW_CHATDETAIL_ENTRY_DATE_BALAS,ROW_CHATDETAIL_TYPE_MESSAGE,ROW_CHATDETAIL_STATUS, ROW_ID_CHATDETAIL }, ROW_CHATDETAIL_ID_BALAS_MESSAGE + "=" + id_balas_message, null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));
                    dataList.add(cur.getString(8));
                    dataList.add(cur.getString(9));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<ArrayList<Object>> ambilBarisChatdetailcheckpending() {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_CHATDETAIL, new String[] {  ROW_CHATDETAIL_ID_BALAS_MESSAGE,
                    ROW_CHATDETAIL_ID_MESSAGE,ROW_CHATDETAIL_TO_ID_ADMIN,ROW_CHATDETAIL_FROM_ID_ADMIN,ROW_CHATDETAIL_FROM_NAMA,ROW_CHATDETAIL_BALAS_MESSAGE,ROW_CHATDETAIL_ENTRY_DATE_BALAS,ROW_CHATDETAIL_TYPE_MESSAGE,ROW_CHATDETAIL_STATUS, ROW_ID_CHATDETAIL }, ROW_CHATDETAIL_STATUS + "='Pending'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));
                    dataList.add(cur.getString(8));
                    dataList.add(cur.getString(9));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void updateBarisChatdetail(String id_data_messagedetail, String id_balas_message) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_CHATDETAIL_ID_BALAS_MESSAGE, id_balas_message);
        cv.put(ROW_CHATDETAIL_STATUS, "Sukses");

        try {
            db.update(NAMA_TABEL_CHATDETAIL, cv, ROW_ID_CHATDETAIL + "=" + id_data_messagedetail, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }
    //end table simpan chat

    //table simpan lokasi
    public void addRowKirimlokasi(String ceklat, String ceklong, String id_surveyor, String cektime) {

        ContentValues values = new ContentValues();
        values.put(ROW_KIRIMLOKASI_LATITUDE_SURVEYOR, ceklat);
        values.put(ROW_KIRIMLOKASI_LONGITUDE_SURVEYOR, ceklong);
        values.put(ROW_KIRIMLOKASI_ID_SURVEYOR, id_surveyor);
        values.put(ROW_KIRIMLOKASI_TIME_SURVEYOR, cektime);

        try {
            db.insert(NAMA_TABEL_KIRIMLOKASI, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisKirimlokasicheck(String id_surveyor) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_KIRIMLOKASI, new String[] {  ROW_ID_KIRIMLOKASI,
                    ROW_KIRIMLOKASI_LATITUDE_SURVEYOR,ROW_KIRIMLOKASI_LONGITUDE_SURVEYOR,
                    ROW_KIRIMLOKASI_ID_SURVEYOR,ROW_KIRIMLOKASI_TIME_SURVEYOR },
                    ROW_KIRIMLOKASI_ID_SURVEYOR + "='"+id_surveyor +"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deleteKirimlokasiAll(String hasil_id_kirimlokasi){
        try {
            db.delete(NAMA_TABEL_KIRIMLOKASI, ROW_ID_KIRIMLOKASI + "=" + hasil_id_kirimlokasi, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //end table simpan lokasi

    //SIMPAN SURVEY
    public void deleteSurvey(String id_order){
        try {
            db.delete(NAMA_TABEL_SIMPANSURVEY, ROW_ID_ORDER + "=" + id_order, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public void addRowSurveyAll(String H_id_surveyor,String H_id_order,String H_Name,
                                String H_Mother_maiden_name,String H_title,String H_marital_status,
                                String H_identity_type,String H_Npwp_no,String H_Birth_place,
                                String H_Birth_date,String H_Identity_no,String H_Address_ktp,
                                String H_province_ktp,String H_kab_kodya_ktp,String H_kecamatan_ktp,
                                String H_kelurahan_ktp,String H_Sandi_dati_2_ktp,
                                String H_Postal_code_ktp,String H_Address_home,
                                String H_province_home,String H_kab_kodya_home,
                                String H_kecamatan_home,String H_kelurahan_home,
                                String H_Sandi_dati_2_home,String H_Postal_code_home,
                                String H_mail_address,String H_education,String H_sex,
                                String H_Nama_panggilan,String H_Sandi_lahir,String H_religion,
                                String H_Stay_length,String H_Telephone,String H_Telephone_2,
                                String H_Handphone_1,String H_Handphone_2,String H_email,

                                String H_pekerjaan, String H_job_title, String H_Name_economy_code,
                                String H_Company_name, String H_Company_address,
                                String H_Company_province, String H_Company_kab_or_kodya,
                                String H_Company_kecamatan, String H_Company_kelurahan,
                                String H_Sandi_dati_2_company, String H_Postal_code_company,
                                String H_Company_telephone_1, String H_Company_telephone_2,
                                String H_Line_of_business, String H_Economy_code,
                                String H_Estabilished_since, String H_Company_fax_1,

                                String H_Spouse_name,String H_spouse_title,
                                String H_spouse_identity_type,String H_Spouse_identity_no,
                                String H_Spouse_birth_place_or_sandi_lahir,String H_spouse_religion,
                                String H_Spouse_address,String H_province_spouse,
                                String H_kab_kodya_spouse,String H_kecamatan_spouse,
                                String H_kelurahan_spouse,String H_Sandi_dati_2_spouse,
                                String H_Postal_code_spouse,String H_Spouse_no_handphone,
                                String H_spouse_occupation_or_pekerjaan,
                                String H_Spouse_company_name,String H_Spouse_company_address,
                                String H_Spouse_company_telephone,String H_Spouse_line_of_business,
                                String H_spouse_job_title,String H_spouse_sex,
                                String H_Spouse_date_of_birth,String H_Spouse_fax,

                                String H_has_contact_person,String H_Contact_name,
                                String H_Contact_address,String H_contact_province,
                                String H_contact_kab_or_kodya,String H_contact_kecamatan,
                                String H_contact_kelurahan,String H_Contact_sandi_dati_2,
                                String H_Contact_Postal_code, String H_Contact_telephone,
                                String H_relationship,

                                String H_tipe_rumah,String H_home_status_kosong,
                                String H_Jarak_rumah_ke_cabang,String H_Luas_tanah,String H_Luas_bangunan_rumah,
                                String H_status_kepemilikan_rumah,String H_klasifikasi_perumahan,
                                String H_tempat_menaruh_kendaraan,String H_status_garasi_kendaraan,
                                String H_bentuk_bangunan_rumah,String H_kondisi_rumah,String H_luas_jalan_masuk_rumah,
                                String H_status_kepemilikan_rumah_pemohon_kosong,String H_furniture_or_perabot,

                                String H_Jarak_tempat_usaha_dari_rumah,
                                String H_status_kepemilikan_usaha,String H_bentuk_bangunan_tempat_usaha,
                                String H_lokasi_tempat_usaha,String H_jumlah_karyawan,
                                String H_Lama_pemohon_menempati_tempat_usaha_tahun,
                                String H_Lama_pemohon_menempati_tempat_usaha_bulan,
                                String H_Lama_usaha_berdiri_tahun,String H_Lama_usaha_berdiri_bulan,
                                String H_pekerjaan_or_usaha_terkait_ekspor_or_impor,

                                String H_tujuan_penggunaan_unit,
                                String H_kondisi_mobil,String H_bagian_kondisi_tidak_baik,
                                String H_Lama_kepemilikan_kendaraan_tahun,String H_Lama_kepemilikan_kendaraan_bulan,

                                String H_Jumlah_tanggungan,String H_Jumlah_anak,

                                String H_Omzet_or_penghasilan_gross,
                                String H_Penghasilan_nett_or_take_home_pay,String H_Penghasilan_pasangan,
                                String H_Penghasilan_tambahan,String H_Pengeluaran_or_kebutuhan_hidup,
                                String H_Total_cicilan_leasing_lain,String H_Balance_terakhir_di_rekening_tabungan,
                                String H_Rata_rata_mutasi_in_3_bulan_terakhir,
                                String H_Rata_rata_mutasi_out_3_bulan_terakhir,

                                String H_Collectabilitas_sid_or_slik_tertinggi,String H_pernah_kredit_di_tempat_lain,
                                String H_Overdue_tertinggi,String H_Baki_debet_or_outstanding_hutang,
                                String H_Nama_finance_company,String H_Alasan_menunggak_khusus_lebih_dari_coll_2,

                                String H_apakah_direkomendasikan,
                                String H_Alasan_or_point_penting_rekomendasi_anda){

        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_id_order);
        values.put(ROW_NAME, H_Name);
        values.put(ROW_MOTHER_MAIDEN_NAME, H_Mother_maiden_name);
        values.put(ROW_TITLE, H_title);
        values.put(ROW_MARITAL_STATUS, H_marital_status);
        values.put(ROW_IDENTITY_TYPE, H_identity_type);
        values.put(ROW_NPWP_NO, H_Npwp_no);
        values.put(ROW_BIRTH_PLACE, H_Birth_place);
        values.put(ROW_BIRTH_DATE, H_Birth_date);
        values.put(ROW_ADDRESS_KTP, H_Address_ktp);
        values.put(ROW_PROVINCE_KTP, H_province_ktp);
        values.put(ROW_KAB_OR_KODYA_KTP, H_kab_kodya_ktp);
        values.put(ROW_KECAMATAN_KTP, H_kecamatan_ktp);
        values.put(ROW_KELURAHAN_KTP, H_kelurahan_ktp);
        values.put(ROW_SANDI_DATI_2_KTP, H_Sandi_dati_2_ktp);
        values.put(ROW_POSTAL_CODE_HOME, H_Postal_code_ktp);
        values.put(ROW_ADDRESS_HOME, H_Address_home);
        values.put(ROW_PROVINCE_HOME, H_province_home);
        values.put(ROW_KAB_OR_KODYA_HOME, H_kab_kodya_home);
        values.put(ROW_KECAMATAN_HOME, H_kecamatan_home);
        values.put(ROW_KELURAHAN_HOME, H_kelurahan_home);
        values.put(ROW_SANDI_DATI_2_HOME, H_Sandi_dati_2_home);
        values.put(ROW_POSTAL_CODE_HOME, H_Postal_code_home);
        values.put(ROW_MAIL_ADDRESS, H_mail_address);
        values.put(ROW_TELEPHONE, H_Telephone);
        values.put(ROW_TELEPHONE_2, H_Telephone_2);
        values.put(ROW_EDUCATION, H_education);
        values.put(ROW_SEX, H_sex);
        values.put(ROW_NAMA_PANGGILAN, H_Nama_panggilan);
        values.put(ROW_IDENTITY_NO, H_Identity_no);
        values.put(ROW_SANDI_LAHIR, H_Sandi_lahir);
        values.put(ROW_RELIGION, H_religion);
        values.put(ROW_STAY_LENGTH, H_Stay_length);
        values.put(ROW_HANDPHONE_STAY_1, H_Handphone_1);
        values.put(ROW_HANDPHONE_STAY_2, H_Handphone_2);
        values.put(ROW_EMAIL_STAY, H_email);

        values.put(ROW_PEKERJAAN, H_pekerjaan);
        values.put(ROW_JOB_TITLE_PEKERJAAN, H_job_title);
        values.put(ROW_NAME_ECONOMY_CODE, H_Name_economy_code);
        values.put(ROW_COMPANY_NAME, H_Company_name);
        values.put(ROW_ADDRESS_PEKERJAAN, H_Company_address);
        values.put(ROW_PROVINCE_PEKERJAAN, H_Company_province);
        values.put(ROW_KAB_OR_KODYA_PEKERJAAN, H_Company_kab_or_kodya);
        values.put(ROW_KECAMATAN_PEKERJAAN, H_Company_kecamatan);
        values.put(ROW_KELURAHAN_PEKERJAAN, H_Company_kelurahan);
        values.put(ROW_SANDI_DATI_2_PEKERJAAN, H_Sandi_dati_2_company);
        values.put(ROW_POSTAL_CODE_PEKERJAAN, H_Postal_code_company);
        values.put(ROW_TELEPHONE_1_PEKERJAAN, H_Company_telephone_1);
        values.put(ROW_TELEPHONE_2_PEKERJAAN, H_Company_telephone_2);
        values.put(ROW_LINE_OF_BUSINESS, H_Line_of_business);
        values.put(ROW_ECONOMY_CODE, H_Economy_code);
        values.put(ROW_ESTABILISHED_SINCE, H_Estabilished_since);
        values.put(ROW_FAX_1_PEKERJAAN, H_Company_fax_1);


        values.put(ROW_NAME_SPOUSE, H_Spouse_name);
        values.put(ROW_TITLE_SPOUSE, H_spouse_title);
        values.put(ROW_IDENTITY_TYPE_SPOUSE, H_spouse_identity_type);
        values.put(ROW_BIRTH_PLACE_SPOUSE, H_Spouse_birth_place_or_sandi_lahir);
        values.put(ROW_RELIGION_SPOUSE, H_spouse_religion);
        values.put(ROW_ADDRESS_SPOUSE, H_Spouse_address);
        values.put(ROW_PROVINCE_SPOUSE, H_province_spouse);
        values.put(ROW_KAB_OR_KODYA_SPOUSE, H_kab_kodya_spouse);
        values.put(ROW_KECAMATAN_SPOUSE, H_kecamatan_spouse);
        values.put(ROW_KELURAHAN_SPOUSE, H_kelurahan_spouse);
        values.put(ROW_SANDI_DATI_2_SPOUSE, H_Sandi_dati_2_spouse);
        values.put(ROW_POSTAL_CODE_SPOUSE, H_Postal_code_spouse);
        values.put(ROW_NO_HANDPHONE_SPOUSE, H_Spouse_no_handphone);
        values.put(ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE, H_spouse_occupation_or_pekerjaan);
        values.put(ROW_COMPANY_NAME_SPOUSE, H_Spouse_company_name);
        values.put(ROW_ADDRESS_PEKERJAAN_SPOUSE, H_Spouse_company_address);
        values.put(ROW_TELEPHONE_SPOUSE, H_Spouse_company_telephone);
        values.put(ROW_LINE_OF_BUSSINESS_SPOUSE, H_Spouse_line_of_business);
        values.put(ROW_JOB_TITLE_SPOUSE, H_spouse_job_title);
        values.put(ROW_SEX_SPOUSE, H_spouse_sex);
        values.put(ROW_IDENTITY_NO_SPOUSE, H_Spouse_identity_no);
        values.put(ROW_DATE_OF_BIRTH_SPOUSE, H_Spouse_date_of_birth);
        values.put(ROW_FAX_SPOUSE, H_Spouse_fax);


        values.put(ROW_HAS_CONTACT_PERSON, H_has_contact_person);
        values.put(ROW_NAME_CONTACT, H_Contact_name);
        values.put(ROW_ADDRESS_CONTACT, H_Contact_address);
        values.put(ROW_PROVINCE_CONTACT, H_contact_province);
        values.put(ROW_KAB_OR_KODYA_CONTACT, H_contact_kab_or_kodya);
        values.put(ROW_KECAMATAN_CONTACT, H_contact_kecamatan);
        values.put(ROW_KELURAHAN_CONTACT, H_contact_kelurahan);
        values.put(ROW_SANDI_DATI_2_CONTACT, H_Contact_sandi_dati_2);
        values.put(ROW_POSTAL_CODE_CONTACT, H_Contact_Postal_code);
        values.put(ROW_TELEPHONE_CONTACT, H_Contact_telephone);
        values.put(ROW_RELATIONSHIP_CONTACT, H_relationship);


        values.put(ROW_TIPE_RUMAH, H_tipe_rumah);
        values.put(ROW_HOME_STATUS, H_home_status_kosong);
        values.put(ROW_JARAK_RUMAH_KE_CABANG, H_Jarak_rumah_ke_cabang);
        values.put(ROW_LUAS_TANAH, H_Luas_tanah);
        values.put(ROW_LUAS_BANGUNAN_RUMAH, H_Luas_bangunan_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_RUMAH, H_status_kepemilikan_rumah);
        values.put(ROW_KLASIFIKASI_PERUMAHAN, H_klasifikasi_perumahan);
        values.put(ROW_TEMPAT_MENARUH_KENDARAAN, H_tempat_menaruh_kendaraan);
        values.put(ROW_STATUS_GARASI_KENDARAAN, H_status_garasi_kendaraan);
        values.put(ROW_BENTUK_BANGUNAN_RUMAH, H_bentuk_bangunan_rumah);
        values.put(ROW_KONDISI_RUMAH, H_kondisi_rumah);
        values.put(ROW_LUAS_JALAN_MASUK_RUMAH, H_luas_jalan_masuk_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON, H_status_kepemilikan_rumah_pemohon_kosong);
        values.put(ROW_FURNITURE, H_furniture_or_perabot);


        values.put(ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH, H_Jarak_tempat_usaha_dari_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_USAHA, H_status_kepemilikan_usaha);
        values.put(ROW_BENTUK_BANGUNAN_TEMPAT_USAHA, H_bentuk_bangunan_tempat_usaha);
        values.put(ROW_LOKASI_TEMPAT_USAHA, H_lokasi_tempat_usaha);
        values.put(ROW_JUMLAH_KARYAWAN, H_jumlah_karyawan);
        values.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN, H_Lama_pemohon_menempati_tempat_usaha_tahun);
        values.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN, H_Lama_pemohon_menempati_tempat_usaha_bulan);
        values.put(ROW_LAMA_USAHA_BERDIRI_TAHUN, H_Lama_usaha_berdiri_tahun);
        values.put(ROW_LAMA_USAHA_BERDIRI_BULAN, H_Lama_usaha_berdiri_bulan);
        values.put(ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR, H_pekerjaan_or_usaha_terkait_ekspor_or_impor);


        values.put(ROW_TUJUAN_PENGGUNAAN_UNIT, H_tujuan_penggunaan_unit);
        values.put(ROW_KONDISI_MOBIL, H_kondisi_mobil);
        values.put(ROW_BAGIAN_KONDISI_TIDAK_BAIK, H_bagian_kondisi_tidak_baik);
        values.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN, H_Lama_kepemilikan_kendaraan_tahun);
        values.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN, H_Lama_kepemilikan_kendaraan_bulan);


        values.put(ROW_JUMLAH_TANGGUNGAN, H_Jumlah_tanggungan);
        values.put(ROW_JUMLAH_ANAK, H_Jumlah_anak);


        values.put(ROW_OMZET_OR_PENGHASILAN_GROSS, H_Omzet_or_penghasilan_gross);
        values.put(ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY, H_Penghasilan_nett_or_take_home_pay);
        values.put(ROW_PENGHASILAN_PASANGAN, H_Penghasilan_pasangan);
        values.put(ROW_PENGHASILAN_TAMBAHAN, H_Penghasilan_tambahan);
        values.put(ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP, H_Pengeluaran_or_kebutuhan_hidup);
        values.put(ROW_TOTAL_CICILAN_LEASING_LAIN, H_Total_cicilan_leasing_lain);
        values.put(ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN, H_Balance_terakhir_di_rekening_tabungan);
        values.put(ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_in_3_bulan_terakhir);
        values.put(ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_out_3_bulan_terakhir);


        values.put(ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI, H_Collectabilitas_sid_or_slik_tertinggi);
        values.put(ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN, H_pernah_kredit_di_tempat_lain);
        values.put(ROW_OVERDUE_TERTINGGI, H_Overdue_tertinggi);
        values.put(ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG, H_Baki_debet_or_outstanding_hutang);
        values.put(ROW_NAMA_FINANCE_COMPANY, H_Nama_finance_company);
        values.put(ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2, H_Alasan_menunggak_khusus_lebih_dari_coll_2);

        values.put(ROW_APAKAH_DIREKOMENDASIKAN, H_apakah_direkomendasikan);
        values.put(ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA, H_Alasan_or_point_penting_rekomendasi_anda);
        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

    }

    public void addRowSurvey1Tambahan(String H_id_order ,String H_id_province_ktp, String H_id_kab_kodya_ktp, String H_id_kec_ktp, String H_id_kel_ktp, String H_zipcode_ktp){
        ContentValues values = new ContentValues();
        values.put(ID_ORDER_1, H_id_order);
        values.put(ID_PROVINCE_KTP, H_id_province_ktp);
        values.put(ID_KAB_KODYA_KTP, H_id_kab_kodya_ktp);
        values.put(ID_KEC_KTP, H_id_kec_ktp);
        values.put(ID_KEL_KTP, H_id_kel_ktp);
        values.put(ZIPCODE_KTP, H_zipcode_ktp);

        try{
            db.insert(NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN,null,values);
        }catch (Exception e){
            Log.e("DB ERROR",e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey3Tambahan(String H_id_order,String H_id_province_spouse, String H_id_kab_kodya_spouse, String H_id_kec_spouse, String H_id_kel_spouse, String H_zipcode_spouse){
        ContentValues values = new ContentValues();
        values.put(ID_ORDER_2,H_id_order);
        values.put(ID_PROVINCE_SPOUSE,H_id_province_spouse);
        values.put(ID_KAB_KODYA_SPOUSE, H_id_kab_kodya_spouse);
        values.put(ID_KEC_SPOUSE,H_id_kec_spouse);
        values.put(ID_KEL_SPOUSE, H_id_kel_spouse);
        values.put(ZIPCODE_SPOUSE, H_zipcode_spouse);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN,null,values);
        }catch (Exception e){
            Log.e("DB ERROR",e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey1(String H_id_surveyor, String H_Id_order,String H_Name, String H_Mother_maiden_name,
                              String H_title, String H_marital_status, String H_identity_type, String H_Npwp_no,
                              String H_Birth_place, String H_Birth_date, String H_Identity_no, String H_Address_ktp, String H_province_ktp, String H_kab_kodya_ktp,
                              String H_kecamatan_ktp, String H_kelurahan_ktp, String H_Sandi_dati_2_ktp, String H_Postal_code_ktp,
                              String H_Address_home, String H_province_home, String H_kab_kodya_home, String H_kecamatan_home,
                              String H_kelurahan_home,String H_Sandi_dati_2_home, String H_Postal_code_home, String H_mail_address,
                              String H_education, String H_sex, String H_Nama_panggilan, String H_Sandi_lahir, String H_religion, String H_Stay_length,
                              String H_Telephone, String H_Telephone_2, String H_Handphone_1, String H_Handphone_2, String H_email) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_NAME, H_Name);
        values.put(ROW_MOTHER_MAIDEN_NAME, H_Mother_maiden_name);

        values.put(ROW_TITLE, H_title);
        values.put(ROW_MARITAL_STATUS, H_marital_status);
        values.put(ROW_IDENTITY_TYPE, H_identity_type);
        values.put(ROW_NPWP_NO, H_Npwp_no);
        values.put(ROW_BIRTH_PLACE, H_Birth_place);

        values.put(ROW_BIRTH_DATE, H_Birth_date);
        values.put(ROW_ADDRESS_KTP, H_Address_ktp);
        values.put(ROW_PROVINCE_KTP, H_province_ktp);
        values.put(ROW_KAB_OR_KODYA_KTP, H_kab_kodya_ktp);
        values.put(ROW_KECAMATAN_KTP, H_kecamatan_ktp);
        values.put(ROW_KELURAHAN_KTP, H_kelurahan_ktp);
        values.put(ROW_SANDI_DATI_2_KTP, H_Sandi_dati_2_ktp);
        values.put(ROW_POSTAL_CODE_HOME, H_Postal_code_ktp);

        values.put(ROW_ADDRESS_HOME, H_Address_home);
        values.put(ROW_PROVINCE_HOME, H_province_home);
        values.put(ROW_KAB_OR_KODYA_HOME, H_kab_kodya_home);
        values.put(ROW_KECAMATAN_HOME, H_kecamatan_home);
        values.put(ROW_KELURAHAN_HOME, H_kelurahan_home);
        values.put(ROW_SANDI_DATI_2_HOME, H_Sandi_dati_2_home);
        values.put(ROW_POSTAL_CODE_HOME, H_Postal_code_home);

        values.put(ROW_MAIL_ADDRESS, H_mail_address);
        values.put(ROW_TELEPHONE, H_Telephone);
        values.put(ROW_TELEPHONE_2, H_Telephone_2);
        values.put(ROW_EDUCATION, H_education);
        values.put(ROW_SEX, H_sex);
        values.put(ROW_NAMA_PANGGILAN, H_Nama_panggilan);
        values.put(ROW_IDENTITY_NO, H_Identity_no);
        values.put(ROW_SANDI_LAHIR, H_Sandi_lahir);
        values.put(ROW_RELIGION, H_religion);
        values.put(ROW_STAY_LENGTH, H_Stay_length);
        values.put(ROW_HANDPHONE_STAY_1, H_Handphone_1);
        values.put(ROW_HANDPHONE_STAY_2, H_Handphone_2);
        values.put(ROW_EMAIL_STAY, H_email);


        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey2(String H_id_surveyor,String H_Id_order,String H_Pekerjaan,String H_job_title,
                              String H_Name_economy_code, String H_Company_name, String H_Company_address,
                              String H_Company_province, String H_Company_kab_or_kodya,
                              String H_Company_kecamatan, String H_Company_kelurahan,
                              String H_Sandi_dati_2_company, String H_Postal_code_company,
                              String H_Company_telephone_1, String H_Company_telephone_2,
                              String H_Line_of_business, String H_Economy_code ,
                              String H_Estabilished_since, String H_Company_fax_1) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_PEKERJAAN, H_Pekerjaan);
        values.put(ROW_JOB_TITLE_PEKERJAAN, H_job_title);
        values.put(ROW_NAME_ECONOMY_CODE, H_Name_economy_code);
        values.put(ROW_COMPANY_NAME, H_Company_name);
        values.put(ROW_ADDRESS_PEKERJAAN, H_Company_address);
        values.put(ROW_PROVINCE_PEKERJAAN, H_Company_province);
        values.put(ROW_KAB_OR_KODYA_PEKERJAAN, H_Company_kab_or_kodya);
        values.put(ROW_KECAMATAN_PEKERJAAN, H_Company_kecamatan);
        values.put(ROW_KELURAHAN_PEKERJAAN, H_Company_kelurahan);
        values.put(ROW_SANDI_DATI_2_PEKERJAAN, H_Sandi_dati_2_company);
        values.put(ROW_POSTAL_CODE_PEKERJAAN, H_Postal_code_company);
        values.put(ROW_TELEPHONE_1_PEKERJAAN, H_Company_telephone_1);
        values.put(ROW_TELEPHONE_2_PEKERJAAN, H_Company_telephone_2);
        values.put(ROW_LINE_OF_BUSINESS, H_Line_of_business);
        values.put(ROW_ECONOMY_CODE, H_Economy_code);
        values.put(ROW_ESTABILISHED_SINCE, H_Estabilished_since);
        values.put(ROW_FAX_1_PEKERJAAN, H_Company_fax_1);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey3(String H_id_surveyor, String H_Id_order, String H_Spouse_name,
                              String H_spouse_title, String H_spouse_identity_type,
                              String H_Spouse_identity_no,
                              String H_Spouse_birth_place_or_sandi_lahir, String H_spouse_religion,
                              String H_Spouse_address, String H_province_spouse,
                              String H_kab_kodya_spouse, String H_kecamatan_spouse,
                              String H_kelurahan_spouse, String H_Sandi_dati_2_spouse,
                              String H_Postal_code_spouse, String H_Spouse_no_handphone,
                              String H_spouse_occupation_or_pekerjaan, String H_Spouse_company_name,
                              String H_Spouse_company_address, String H_Spouse_company_telephone,
                              String H_Spouse_line_of_business, String H_spouse_job_title,
                              String H_spouse_sex, String H_Spouse_date_of_birth,
                              String H_Spouse_fax) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_NAME_SPOUSE, H_Spouse_name);
        values.put(ROW_TITLE_SPOUSE, H_spouse_title);
        values.put(ROW_IDENTITY_TYPE_SPOUSE, H_spouse_identity_type);
        values.put(ROW_BIRTH_PLACE_SPOUSE, H_Spouse_birth_place_or_sandi_lahir);
        values.put(ROW_RELIGION_SPOUSE, H_spouse_religion);
        values.put(ROW_ADDRESS_SPOUSE, H_Spouse_address);
        values.put(ROW_PROVINCE_SPOUSE, H_province_spouse);
        values.put(ROW_KAB_OR_KODYA_SPOUSE, H_kab_kodya_spouse);
        values.put(ROW_KECAMATAN_SPOUSE, H_kecamatan_spouse);
        values.put(ROW_KELURAHAN_SPOUSE, H_kelurahan_spouse);
        values.put(ROW_SANDI_DATI_2_SPOUSE, H_Sandi_dati_2_spouse);
        values.put(ROW_POSTAL_CODE_SPOUSE, H_Postal_code_spouse);
        values.put(ROW_NO_HANDPHONE_SPOUSE, H_Spouse_no_handphone);
        values.put(ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE, H_spouse_occupation_or_pekerjaan);
        values.put(ROW_COMPANY_NAME_SPOUSE, H_Spouse_company_name);
        values.put(ROW_ADDRESS_PEKERJAAN_SPOUSE, H_Spouse_company_address);
        values.put(ROW_TELEPHONE_SPOUSE, H_Spouse_company_telephone);
        values.put(ROW_LINE_OF_BUSSINESS_SPOUSE, H_Spouse_line_of_business);
        values.put(ROW_JOB_TITLE_SPOUSE, H_spouse_job_title);
        values.put(ROW_SEX_SPOUSE, H_spouse_sex);
        values.put(ROW_IDENTITY_NO_SPOUSE, H_Spouse_identity_no);
        values.put(ROW_DATE_OF_BIRTH_SPOUSE, H_Spouse_date_of_birth);
        values.put(ROW_FAX_SPOUSE, H_Spouse_fax);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey4(String H_id_surveyor, String H_Id_order, String H_has_contact_person,
                              String H_Contact_name, String H_Contact_address,
                              String H_contact_province, String H_contact_kab_or_kodya,
                              String H_contact_kecamatan, String H_contact_kelurahan,
                              String H_Contact_sandi_dati_2, String H_Contact_Postal_code,
                              String H_Contact_telephone, String H_relationship) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_HAS_CONTACT_PERSON, H_has_contact_person);
        values.put(ROW_NAME_CONTACT, H_Contact_name);
        values.put(ROW_ADDRESS_CONTACT, H_Contact_address);
        values.put(ROW_PROVINCE_CONTACT, H_contact_province);
        values.put(ROW_KAB_OR_KODYA_CONTACT, H_contact_kab_or_kodya);
        values.put(ROW_KECAMATAN_CONTACT, H_contact_kecamatan);
        values.put(ROW_KELURAHAN_CONTACT, H_contact_kelurahan);
        values.put(ROW_SANDI_DATI_2_CONTACT, H_Contact_sandi_dati_2);
        values.put(ROW_POSTAL_CODE_CONTACT, H_Contact_Postal_code);
        values.put(ROW_TELEPHONE_CONTACT, H_Contact_telephone);
        values.put(ROW_RELATIONSHIP_CONTACT, H_relationship);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey5(String H_id_surveyor,String H_Id_order, String H_tipe_rumah,
                              String H_home_status,String H_Jarak_rumah_ke_cabang,
                              String H_Luas_tanah, String H_Luas_bangunan_rumah,
                              String H_status_kepemilikan_rumah, String H_klasifikasi_perumahan,
                              String H_tempat_menaruh_kendaraan, String H_status_garasi_kendaraan,
                              String H_bentuk_bangunan_rumah, String H_kondisi_rumah,
                              String H_luas_jalan_masuk_rumah,
                              String H_status_kepemilikan_rumah_pemohon,
                              String H_furniture_or_perabot) {


        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_TIPE_RUMAH, H_tipe_rumah);
        values.put(ROW_HOME_STATUS, H_home_status);
        values.put(ROW_JARAK_RUMAH_KE_CABANG, H_Jarak_rumah_ke_cabang);
        values.put(ROW_LUAS_TANAH, H_Luas_tanah);
        values.put(ROW_LUAS_BANGUNAN_RUMAH, H_Luas_bangunan_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_RUMAH, H_status_kepemilikan_rumah);
        values.put(ROW_KLASIFIKASI_PERUMAHAN, H_klasifikasi_perumahan);
        values.put(ROW_TEMPAT_MENARUH_KENDARAAN, H_tempat_menaruh_kendaraan);
        values.put(ROW_STATUS_GARASI_KENDARAAN, H_status_garasi_kendaraan);
        values.put(ROW_BENTUK_BANGUNAN_RUMAH, H_bentuk_bangunan_rumah);
        values.put(ROW_KONDISI_RUMAH, H_kondisi_rumah);
        values.put(ROW_LUAS_JALAN_MASUK_RUMAH, H_luas_jalan_masuk_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON, H_status_kepemilikan_rumah_pemohon);
        values.put(ROW_FURNITURE, H_furniture_or_perabot);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey6(String H_id_surveyor,String H_Id_order,String H_Jarak_tempat_usaha_dari_rumah,String H_status_kepemilikan_usaha,String H_bentuk_bangunan_tempat_usaha,String H_lokasi_tempat_usaha,String H_jumlah_karyawan,String H_Lama_pemohon_menempati_tempat_usaha_tahun,String H_Lama_pemohon_menempati_tempat_usaha_bulan,String H_Lama_usaha_berdiri_tahun,String H_Lama_usaha_berdiri_bulan,String H_pekerjaan_or_usaha_terkait_ekspor_or_impor) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH, H_Jarak_tempat_usaha_dari_rumah);
        values.put(ROW_STATUS_KEPEMILIKAN_USAHA, H_status_kepemilikan_usaha);
        values.put(ROW_BENTUK_BANGUNAN_TEMPAT_USAHA, H_bentuk_bangunan_tempat_usaha);
        values.put(ROW_LOKASI_TEMPAT_USAHA, H_lokasi_tempat_usaha);
        values.put(ROW_JUMLAH_KARYAWAN, H_jumlah_karyawan);
        values.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN, H_Lama_pemohon_menempati_tempat_usaha_tahun);
        values.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN, H_Lama_pemohon_menempati_tempat_usaha_bulan);
        values.put(ROW_LAMA_USAHA_BERDIRI_TAHUN, H_Lama_usaha_berdiri_tahun);
        values.put(ROW_LAMA_USAHA_BERDIRI_BULAN, H_Lama_usaha_berdiri_bulan);
        values.put(ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR, H_pekerjaan_or_usaha_terkait_ekspor_or_impor);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey7(String H_id_surveyor,String H_Id_order, String H_tujuan_penggunaan_unit, String H_kondisi_mobil, String H_bagian_kondisi_tidak_baik, String H_Lama_kepemilikan_kendaraan_tahun, String H_Lama_kepemilikan_kendaraan_bulan) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_TUJUAN_PENGGUNAAN_UNIT, H_tujuan_penggunaan_unit);
        values.put(ROW_KONDISI_MOBIL, H_kondisi_mobil);
        values.put(ROW_BAGIAN_KONDISI_TIDAK_BAIK, H_bagian_kondisi_tidak_baik);
        values.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN, H_Lama_kepemilikan_kendaraan_tahun);
        values.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN, H_Lama_kepemilikan_kendaraan_bulan);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey8(String H_id_surveyor,String H_Id_order, String H_Jumlah_tanggungan, String H_Jumlah_anak) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_JUMLAH_TANGGUNGAN, H_Jumlah_tanggungan);
        values.put(ROW_JUMLAH_ANAK, H_Jumlah_anak);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey9(String H_id_surveyor,String H_Id_order, String H_Omzet_or_penghasilan_gross, String H_Penghasilan_nett_or_take_home_pay, String H_Penghasilan_pasangan, String H_Penghasilan_tambahan, String H_Pengeluaran_or_kebutuhan_hidup, String H_Total_cicilan_leasing_lain, String H_Balance_terakhir_di_rekening_tabungan, String H_Rata_rata_mutasi_in_3_bulan_terakhir, String H_Rata_rata_mutasi_out_3_bulan_terakhir) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_OMZET_OR_PENGHASILAN_GROSS, H_Omzet_or_penghasilan_gross);
        values.put(ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY, H_Penghasilan_nett_or_take_home_pay);
        values.put(ROW_PENGHASILAN_PASANGAN, H_Penghasilan_pasangan);
        values.put(ROW_PENGHASILAN_TAMBAHAN, H_Penghasilan_tambahan);
        values.put(ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP, H_Pengeluaran_or_kebutuhan_hidup);
        values.put(ROW_TOTAL_CICILAN_LEASING_LAIN, H_Total_cicilan_leasing_lain);
        values.put(ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN, H_Balance_terakhir_di_rekening_tabungan);
        values.put(ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_in_3_bulan_terakhir);
        values.put(ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_out_3_bulan_terakhir);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey10(String H_id_surveyor,String H_Id_order, String H_Collectabilitas_sid_or_slik_tertinggi, String H_pernah_kredit_di_tempat_lain, String H_Overdue_tertinggi, String H_Baki_debet_or_outstanding_hutang, String H_Nama_finance_company, String H_Alasan_menunggak_khusus_lebih_dari_coll_2) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI, H_Collectabilitas_sid_or_slik_tertinggi);
        values.put(ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN, H_pernah_kredit_di_tempat_lain);
        values.put(ROW_OVERDUE_TERTINGGI, H_Overdue_tertinggi);
        values.put(ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG, H_Baki_debet_or_outstanding_hutang);
        values.put(ROW_NAMA_FINANCE_COMPANY, H_Nama_finance_company);
        values.put(ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2, H_Alasan_menunggak_khusus_lebih_dari_coll_2);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowSurvey12(String H_id_surveyor,String H_Id_order, String H_apakah_direkomendasikan,String H_Alasan_or_point_penting_rekomendasi_anda) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID_SURVEYOR, H_id_surveyor);
        values.put(ROW_ID_ORDER, H_Id_order);
        values.put(ROW_APAKAH_DIREKOMENDASIKAN, H_apakah_direkomendasikan);
        values.put(ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA, H_Alasan_or_point_penting_rekomendasi_anda);

        try {
            db.insert(NAMA_TABEL_SIMPANSURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisSurvey1Tambahan(String id_order){
        ArrayList<ArrayList<Object>> arrayLists = new ArrayList<ArrayList<Object>>();
        Cursor cursor = null;
        try {
            cursor = db.query(NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN, new String[]{
                    ID_PROVINCE_KTP, ID_KAB_KODYA_KTP, ID_KEC_KTP, ID_KEL_KTP, ZIPCODE_KTP
            },ID_ORDER_1 + "='"+id_order+"'",null,null,null,null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()){
                do {
                    ArrayList<Object> objectArrayList = new ArrayList<Object>();
                    objectArrayList.add(cursor.getString(0));
                    objectArrayList.add(cursor.getString(1));
                    objectArrayList.add(cursor.getString(2));
                    objectArrayList.add(cursor.getString(3));
                    objectArrayList.add(cursor.getString(4));
                    arrayLists.add(objectArrayList);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("ambilBrsSurvey1Tambahan", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }

        return arrayLists;
    }

    public ArrayList<Object> ambilBarisSurvey1Tbh(String id_order){
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Cursor cursor = null;
        try{
            cursor = db.query(NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN, new String[]{
                    ID_PROVINCE_KTP, ID_KAB_KODYA_KTP, ID_KEC_KTP, ID_KEL_KTP, ZIPCODE_KTP
            },ID_ORDER_1 + "='"+id_order+"'",null,null,null,null,null);

            if (cursor.moveToFirst()){
                do {
                    arrayList.add(cursor.getString(0));
                    arrayList.add(cursor.getString(1));
                    arrayList.add(cursor.getString(2));
                    arrayList.add(cursor.getString(3));
                    arrayList.add(cursor.getString(4));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AmbilBarisSurvey1Tbh",e.toString());
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return arrayList;
    }

    public ArrayList<Object> ambilBarisSurvey3Tbh(String id_order){
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Cursor cursor = null;
        try {
            cursor = db.query(NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN, new String[]{
                    ID_PROVINCE_SPOUSE, ID_KAB_KODYA_SPOUSE, ID_KEC_SPOUSE, ID_KEL_SPOUSE, ZIPCODE_SPOUSE
            },ID_ORDER_2 + "='"+id_order+"'",null,null,null,null,null);

            if (cursor.moveToFirst()){
                do {
                    arrayList.add(cursor.getString(0));
                    arrayList.add(cursor.getString(1));
                    arrayList.add(cursor.getString(2));
                    arrayList.add(cursor.getString(3));
                    arrayList.add(cursor.getString(4));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AmbilBarisSurvey3Tbh",e.toString());
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return arrayList;
    }

    public ArrayList<ArrayList<Object>> ambilBarisSurvey3Tambahan(String id_order){
        ArrayList<ArrayList<Object>> arrayLists = new ArrayList<ArrayList<Object>>();
        Cursor cursor = null;
        try {
            cursor = db.query(NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN, new String[]{
                    ID_PROVINCE_SPOUSE, ID_KAB_KODYA_SPOUSE, ID_KEC_SPOUSE, ID_KEL_SPOUSE, ZIPCODE_SPOUSE
            },ID_ORDER_2 + "='"+id_order+"'",null,null,null,null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()){
                do {
                    ArrayList<Object> objectArrayList = new ArrayList<Object>();
                    objectArrayList.add(cursor.getString(0));
                    objectArrayList.add(cursor.getString(1));
                    objectArrayList.add(cursor.getString(2));
                    objectArrayList.add(cursor.getString(3));
                    objectArrayList.add(cursor.getString(4));
                    arrayLists.add(objectArrayList);
                }while (cursor.moveToNext());

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DB ERROR",e.toString());
            Toast.makeText(context,"gagal ambil semua baris:" + e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }

        return arrayLists;
    }

    public ArrayList<ArrayList<Object>> ambilBarisSurvey(String Id_order) {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {

            cur = db.query(NAMA_TABEL_SIMPANSURVEY, new String[] {  ROW_ID_ORDER,
                    ROW_ID_SURVEYOR, ROW_NAME, ROW_MOTHER_MAIDEN_NAME,ROW_TITLE, ROW_MARITAL_STATUS, ROW_IDENTITY_TYPE, ROW_NPWP_NO, ROW_BIRTH_PLACE,
                    ROW_BIRTH_DATE,ROW_IDENTITY_NO,ROW_ADDRESS_KTP,ROW_PROVINCE_KTP,ROW_KAB_OR_KODYA_KTP,ROW_KECAMATAN_KTP,
                    ROW_KELURAHAN_KTP,ROW_SANDI_DATI_2_KTP,ROW_POSTAL_CODE_HOME,ROW_ADDRESS_HOME,ROW_PROVINCE_HOME,
                    ROW_KAB_OR_KODYA_HOME,ROW_KECAMATAN_HOME,ROW_KELURAHAN_HOME,ROW_SANDI_DATI_2_HOME,ROW_POSTAL_CODE_HOME,
                    ROW_MAIL_ADDRESS, ROW_EDUCATION, ROW_SEX, ROW_NAMA_PANGGILAN, ROW_SANDI_LAHIR, ROW_RELIGION,
                    ROW_STAY_LENGTH, ROW_TELEPHONE, ROW_TELEPHONE_2, ROW_HANDPHONE_STAY_1, ROW_HANDPHONE_STAY_2, ROW_EMAIL_STAY,
                    ROW_PEKERJAAN, ROW_JOB_TITLE_PEKERJAAN, ROW_NAME_ECONOMY_CODE, ROW_ECONOMY_CODE, ROW_COMPANY_NAME,
                    ROW_ADDRESS_PEKERJAAN,ROW_PROVINCE_PEKERJAAN,ROW_KAB_OR_KODYA_PEKERJAAN,
                    ROW_KECAMATAN_PEKERJAAN, ROW_KELURAHAN_PEKERJAAN,ROW_SANDI_DATI_2_PEKERJAAN,
                    ROW_POSTAL_CODE_PEKERJAAN, ROW_TELEPHONE_1_PEKERJAAN, ROW_TELEPHONE_2_PEKERJAAN,
                    ROW_LINE_OF_BUSINESS, ROW_ESTABILISHED_SINCE,
                    ROW_FAX_1_PEKERJAAN, ROW_NAME_SPOUSE, ROW_TITLE_SPOUSE,
                    ROW_RELIGION_SPOUSE, ROW_SEX_SPOUSE, ROW_IDENTITY_TYPE_SPOUSE,ROW_IDENTITY_NO_SPOUSE,ROW_BIRTH_PLACE_SPOUSE,
                    ROW_DATE_OF_BIRTH_SPOUSE,ROW_ADDRESS_SPOUSE,ROW_PROVINCE_SPOUSE,ROW_KAB_OR_KODYA_SPOUSE,
                    ROW_KECAMATAN_SPOUSE,ROW_KELURAHAN_SPOUSE,ROW_POSTAL_CODE_SPOUSE,ROW_SANDI_DATI_2_SPOUSE,
                    ROW_NO_HANDPHONE_SPOUSE,ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE,ROW_COMPANY_NAME_SPOUSE,
                    ROW_JOB_TITLE_SPOUSE,ROW_LINE_OF_BUSSINESS_SPOUSE,ROW_ADDRESS_PEKERJAAN_SPOUSE,
                    ROW_TELEPHONE_SPOUSE,ROW_FAX_SPOUSE,ROW_HAS_CONTACT_PERSON,ROW_NAME_CONTACT, ROW_ADDRESS_CONTACT,
                    ROW_PROVINCE_CONTACT,ROW_KAB_OR_KODYA_CONTACT,ROW_KECAMATAN_CONTACT,
                    ROW_KELURAHAN_CONTACT,ROW_SANDI_DATI_2_CONTACT,ROW_POSTAL_CODE_CONTACT,
                    ROW_TELEPHONE_CONTACT,ROW_RELATIONSHIP_CONTACT,ROW_TIPE_RUMAH, ROW_HOME_STATUS,ROW_JARAK_RUMAH_KE_CABANG,ROW_LUAS_TANAH,ROW_LUAS_BANGUNAN_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_RUMAH,ROW_KLASIFIKASI_PERUMAHAN,ROW_TEMPAT_MENARUH_KENDARAAN,
                    ROW_STATUS_GARASI_KENDARAAN,ROW_BENTUK_BANGUNAN_RUMAH,ROW_KONDISI_RUMAH,ROW_LUAS_JALAN_MASUK_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON,ROW_FURNITURE,ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_USAHA,ROW_BENTUK_BANGUNAN_TEMPAT_USAHA,ROW_LOKASI_TEMPAT_USAHA,
                    ROW_JUMLAH_KARYAWAN,ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN,
                    ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN,ROW_LAMA_USAHA_BERDIRI_TAHUN,ROW_LAMA_USAHA_BERDIRI_BULAN,
                    ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR,ROW_TUJUAN_PENGGUNAAN_UNIT,ROW_KONDISI_MOBIL,ROW_BAGIAN_KONDISI_TIDAK_BAIK,
                    ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN,ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN,ROW_JUMLAH_TANGGUNGAN,ROW_JUMLAH_ANAK,ROW_OMZET_OR_PENGHASILAN_GROSS,
                    ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY,ROW_PENGHASILAN_PASANGAN,ROW_PENGHASILAN_TAMBAHAN,
                    ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP,ROW_TOTAL_CICILAN_LEASING_LAIN,ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN,
                    ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR,ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR,
                    ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI,ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN,
                    ROW_OVERDUE_TERTINGGI,ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG,ROW_NAMA_FINANCE_COMPANY,
                    ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2,ROW_APAKAH_DIREKOMENDASIKAN,
                    ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA}, ROW_ID_ORDER + "='" + Id_order+"'", null, null, null, null);
            cur.moveToFirst();

            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));
                    dataList.add(cur.getString(8));
                    dataList.add(cur.getString(9));
                    dataList.add(cur.getString(10));
                    dataList.add(cur.getString(11));
                    dataList.add(cur.getString(12));
                    dataList.add(cur.getString(13));
                    dataList.add(cur.getString(14));
                    dataList.add(cur.getString(15));
                    dataList.add(cur.getString(16));
                    dataList.add(cur.getString(17));
                    dataList.add(cur.getString(18));
                    dataList.add(cur.getString(19));
                    dataList.add(cur.getString(20));
                    dataList.add(cur.getString(21));
                    dataList.add(cur.getString(22));
                    dataList.add(cur.getString(23));
                    dataList.add(cur.getString(24));
                    dataList.add(cur.getString(25));
                    dataList.add(cur.getString(26));
                    dataList.add(cur.getString(27));
                    dataList.add(cur.getString(28));
                    dataList.add(cur.getString(29));
                    dataList.add(cur.getString(30));
                    dataList.add(cur.getString(31));
                    dataList.add(cur.getString(32));
                    dataList.add(cur.getString(33));
                    dataList.add(cur.getString(34));
                    dataList.add(cur.getString(35));
                    dataList.add(cur.getString(36));
                    dataList.add(cur.getString(37));
                    dataList.add(cur.getString(38));
                    dataList.add(cur.getString(39));
                    dataList.add(cur.getString(40));
                    dataList.add(cur.getString(41));
                    dataList.add(cur.getString(42));
                    dataList.add(cur.getString(43));
                    dataList.add(cur.getString(44));
                    dataList.add(cur.getString(45));
                    dataList.add(cur.getString(46));
                    dataList.add(cur.getString(47));
                    dataList.add(cur.getString(48));
                    dataList.add(cur.getString(49));
                    dataList.add(cur.getString(50));
                    dataList.add(cur.getString(51));
                    dataList.add(cur.getString(52));
                    dataList.add(cur.getString(53));
                    dataList.add(cur.getString(54));
                    dataList.add(cur.getString(55));
                    dataList.add(cur.getString(56));
                    dataList.add(cur.getString(57));
                    dataList.add(cur.getString(58));
                    dataList.add(cur.getString(59));
                    dataList.add(cur.getString(60));
                    dataList.add(cur.getString(61));
                    dataList.add(cur.getString(62));
                    dataList.add(cur.getString(63));
                    dataList.add(cur.getString(64));
                    dataList.add(cur.getString(65));
                    dataList.add(cur.getString(66));
                    dataList.add(cur.getString(67));
                    dataList.add(cur.getString(68));
                    dataList.add(cur.getString(69));
                    dataList.add(cur.getString(70));
                    dataList.add(cur.getString(71));
                    dataList.add(cur.getString(72));
                    dataList.add(cur.getString(73));
                    dataList.add(cur.getString(74));
                    dataList.add(cur.getString(75));
                    dataList.add(cur.getString(76));
                    dataList.add(cur.getString(77));
                    dataList.add(cur.getString(78));
                    dataList.add(cur.getString(79));
                    dataList.add(cur.getString(80));
                    dataList.add(cur.getString(81));
                    dataList.add(cur.getString(82));
                    dataList.add(cur.getString(83));
                    dataList.add(cur.getString(84));
                    dataList.add(cur.getString(85));
                    dataList.add(cur.getString(86));
                    dataList.add(cur.getString(87));
                    dataList.add(cur.getString(88));
                    dataList.add(cur.getString(89));
                    dataList.add(cur.getString(90));
                    dataList.add(cur.getString(91));
                    dataList.add(cur.getString(92));
                    dataList.add(cur.getString(93));
                    dataList.add(cur.getString(94));
                    dataList.add(cur.getString(95));
                    dataList.add(cur.getString(96));
                    dataList.add(cur.getString(97));
                    dataList.add(cur.getString(98));
                    dataList.add(cur.getString(99));
                    dataList.add(cur.getString(100));
                    dataList.add(cur.getString(101));
                    dataList.add(cur.getString(102));
                    dataList.add(cur.getString(103));
                    dataList.add(cur.getString(104));
                    dataList.add(cur.getString(105));
                    dataList.add(cur.getString(106));
                    dataList.add(cur.getString(107));
                    dataList.add(cur.getString(108));
                    dataList.add(cur.getString(109));
                    dataList.add(cur.getString(110));
                    dataList.add(cur.getString(111));
                    dataList.add(cur.getString(112));
                    dataList.add(cur.getString(113));
                    dataList.add(cur.getString(114));
                    dataList.add(cur.getString(115));
                    dataList.add(cur.getString(116));
                    dataList.add(cur.getString(117));
                    dataList.add(cur.getString(118));
                    dataList.add(cur.getString(119));
                    dataList.add(cur.getString(120));
                    dataList.add(cur.getString(121));
                    dataList.add(cur.getString(122));
                    dataList.add(cur.getString(123));
                    dataList.add(cur.getString(124));
                    dataList.add(cur.getString(125));
                    dataList.add(cur.getString(126));
                    dataList.add(cur.getString(127));
                    dataList.add(cur.getString(128));
                    dataList.add(cur.getString(129));
                    dataList.add(cur.getString(130));
                    dataList.add(cur.getString(131));
                    dataList.add(cur.getString(132));
                    dataList.add(cur.getString(133));
                    dataList.add(cur.getString(134));
                    dataList.add(cur.getString(135));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<ArrayList<Object>> ambilBarisSurveyAll() {
        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_SIMPANSURVEY, new String[] {  ROW_ID_ORDER,
                    ROW_ID_SURVEYOR, ROW_NAME, ROW_MOTHER_MAIDEN_NAME,ROW_TITLE, ROW_MARITAL_STATUS, ROW_IDENTITY_TYPE, ROW_NPWP_NO, ROW_BIRTH_PLACE,
                    ROW_BIRTH_DATE,ROW_IDENTITY_NO,ROW_ADDRESS_KTP,ROW_PROVINCE_KTP,ROW_KAB_OR_KODYA_KTP,ROW_KECAMATAN_KTP,
                    ROW_KELURAHAN_KTP,ROW_SANDI_DATI_2_KTP,ROW_POSTAL_CODE_HOME,ROW_ADDRESS_HOME,ROW_PROVINCE_HOME,
                    ROW_KAB_OR_KODYA_HOME,ROW_KECAMATAN_HOME,ROW_KELURAHAN_HOME,ROW_SANDI_DATI_2_HOME,ROW_POSTAL_CODE_HOME,
                    ROW_MAIL_ADDRESS, ROW_EDUCATION, ROW_SEX, ROW_NAMA_PANGGILAN, ROW_SANDI_LAHIR, ROW_RELIGION,
                    ROW_STAY_LENGTH, ROW_TELEPHONE, ROW_TELEPHONE_2, ROW_HANDPHONE_STAY_1, ROW_HANDPHONE_STAY_2, ROW_EMAIL_STAY,
                    ROW_PEKERJAAN, ROW_JOB_TITLE_PEKERJAAN, ROW_NAME_ECONOMY_CODE, ROW_ECONOMY_CODE, ROW_COMPANY_NAME,
                    ROW_ADDRESS_PEKERJAAN,ROW_PROVINCE_PEKERJAAN,ROW_KAB_OR_KODYA_PEKERJAAN,
                    ROW_KECAMATAN_PEKERJAAN, ROW_KELURAHAN_PEKERJAAN,ROW_SANDI_DATI_2_PEKERJAAN,
                    ROW_POSTAL_CODE_PEKERJAAN, ROW_TELEPHONE_1_PEKERJAAN, ROW_TELEPHONE_2_PEKERJAAN,
                    ROW_LINE_OF_BUSINESS, ROW_ESTABILISHED_SINCE,
                    ROW_FAX_1_PEKERJAAN, ROW_NAME_SPOUSE, ROW_TITLE_SPOUSE,
                    ROW_RELIGION_SPOUSE, ROW_SEX_SPOUSE, ROW_IDENTITY_TYPE_SPOUSE,ROW_IDENTITY_NO_SPOUSE,ROW_BIRTH_PLACE_SPOUSE,
                    ROW_DATE_OF_BIRTH_SPOUSE,ROW_ADDRESS_SPOUSE,ROW_PROVINCE_SPOUSE,ROW_KAB_OR_KODYA_SPOUSE,
                    ROW_KECAMATAN_SPOUSE,ROW_KELURAHAN_SPOUSE,ROW_POSTAL_CODE_SPOUSE,ROW_SANDI_DATI_2_SPOUSE,
                    ROW_NO_HANDPHONE_SPOUSE,ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE,ROW_COMPANY_NAME_SPOUSE,
                    ROW_JOB_TITLE_SPOUSE,ROW_LINE_OF_BUSSINESS_SPOUSE,ROW_ADDRESS_PEKERJAAN_SPOUSE,
                    ROW_TELEPHONE_SPOUSE,ROW_FAX_SPOUSE,ROW_HAS_CONTACT_PERSON,ROW_NAME_CONTACT, ROW_ADDRESS_CONTACT,
                    ROW_PROVINCE_CONTACT,ROW_KAB_OR_KODYA_CONTACT,ROW_KECAMATAN_CONTACT,
                    ROW_KELURAHAN_CONTACT,ROW_SANDI_DATI_2_CONTACT,ROW_POSTAL_CODE_CONTACT,
                    ROW_TELEPHONE_CONTACT,ROW_RELATIONSHIP_CONTACT,ROW_TIPE_RUMAH, ROW_HOME_STATUS,ROW_JARAK_RUMAH_KE_CABANG,ROW_LUAS_TANAH,ROW_LUAS_BANGUNAN_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_RUMAH,ROW_KLASIFIKASI_PERUMAHAN,ROW_TEMPAT_MENARUH_KENDARAAN,
                    ROW_STATUS_GARASI_KENDARAAN,ROW_BENTUK_BANGUNAN_RUMAH,ROW_KONDISI_RUMAH,ROW_LUAS_JALAN_MASUK_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON,ROW_FURNITURE,ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH,
                    ROW_STATUS_KEPEMILIKAN_USAHA,ROW_BENTUK_BANGUNAN_TEMPAT_USAHA,ROW_LOKASI_TEMPAT_USAHA,
                    ROW_JUMLAH_KARYAWAN,ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN,
                    ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN,ROW_LAMA_USAHA_BERDIRI_TAHUN,ROW_LAMA_USAHA_BERDIRI_BULAN,
                    ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR,ROW_TUJUAN_PENGGUNAAN_UNIT,ROW_KONDISI_MOBIL,ROW_BAGIAN_KONDISI_TIDAK_BAIK,
                    ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN,ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN,ROW_JUMLAH_TANGGUNGAN,ROW_JUMLAH_ANAK,ROW_OMZET_OR_PENGHASILAN_GROSS,
                    ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY,ROW_PENGHASILAN_PASANGAN,ROW_PENGHASILAN_TAMBAHAN,
                    ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP,ROW_TOTAL_CICILAN_LEASING_LAIN,ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN,
                    ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR,ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR,
                    ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI,ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN,
                    ROW_OVERDUE_TERTINGGI,ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG,ROW_NAMA_FINANCE_COMPANY,
                    ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2,ROW_APAKAH_DIREKOMENDASIKAN,
                    ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA}, null, null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));
                    dataList.add(cur.getString(8));
                    dataList.add(cur.getString(9));
                    dataList.add(cur.getString(10));
                    dataList.add(cur.getString(11));
                    dataList.add(cur.getString(12));
                    dataList.add(cur.getString(13));
                    dataList.add(cur.getString(14));
                    dataList.add(cur.getString(15));
                    dataList.add(cur.getString(16));
                    dataList.add(cur.getString(17));
                    dataList.add(cur.getString(18));
                    dataList.add(cur.getString(19));
                    dataList.add(cur.getString(20));
                    dataList.add(cur.getString(21));
                    dataList.add(cur.getString(22));
                    dataList.add(cur.getString(23));
                    dataList.add(cur.getString(24));
                    dataList.add(cur.getString(25));
                    dataList.add(cur.getString(26));
                    dataList.add(cur.getString(27));
                    dataList.add(cur.getString(28));
                    dataList.add(cur.getString(29));
                    dataList.add(cur.getString(30));
                    dataList.add(cur.getString(31));
                    dataList.add(cur.getString(32));
                    dataList.add(cur.getString(33));
                    dataList.add(cur.getString(34));
                    dataList.add(cur.getString(35));
                    dataList.add(cur.getString(36));
                    dataList.add(cur.getString(37));
                    dataList.add(cur.getString(38));
                    dataList.add(cur.getString(39));
                    dataList.add(cur.getString(40));
                    dataList.add(cur.getString(41));
                    dataList.add(cur.getString(42));
                    dataList.add(cur.getString(43));
                    dataList.add(cur.getString(44));
                    dataList.add(cur.getString(45));
                    dataList.add(cur.getString(46));
                    dataList.add(cur.getString(47));
                    dataList.add(cur.getString(48));
                    dataList.add(cur.getString(49));
                    dataList.add(cur.getString(50));
                    dataList.add(cur.getString(51));
                    dataList.add(cur.getString(52));
                    dataList.add(cur.getString(53));
                    dataList.add(cur.getString(54));
                    dataList.add(cur.getString(55));
                    dataList.add(cur.getString(56));
                    dataList.add(cur.getString(57));
                    dataList.add(cur.getString(58));
                    dataList.add(cur.getString(59));
                    dataList.add(cur.getString(60));
                    dataList.add(cur.getString(61));
                    dataList.add(cur.getString(62));
                    dataList.add(cur.getString(63));
                    dataList.add(cur.getString(64));
                    dataList.add(cur.getString(65));
                    dataList.add(cur.getString(66));
                    dataList.add(cur.getString(67));
                    dataList.add(cur.getString(68));
                    dataList.add(cur.getString(69));
                    dataList.add(cur.getString(70));
                    dataList.add(cur.getString(71));
                    dataList.add(cur.getString(72));
                    dataList.add(cur.getString(73));
                    dataList.add(cur.getString(74));
                    dataList.add(cur.getString(75));
                    dataList.add(cur.getString(76));
                    dataList.add(cur.getString(77));
                    dataList.add(cur.getString(78));
                    dataList.add(cur.getString(79));
                    dataList.add(cur.getString(80));
                    dataList.add(cur.getString(81));
                    dataList.add(cur.getString(82));
                    dataList.add(cur.getString(83));
                    dataList.add(cur.getString(84));
                    dataList.add(cur.getString(85));
                    dataList.add(cur.getString(86));
                    dataList.add(cur.getString(87));
                    dataList.add(cur.getString(88));
                    dataList.add(cur.getString(89));
                    dataList.add(cur.getString(90));
                    dataList.add(cur.getString(91));
                    dataList.add(cur.getString(92));
                    dataList.add(cur.getString(93));
                    dataList.add(cur.getString(94));
                    dataList.add(cur.getString(95));
                    dataList.add(cur.getString(96));
                    dataList.add(cur.getString(97));
                    dataList.add(cur.getString(98));
                    dataList.add(cur.getString(99));
                    dataList.add(cur.getString(100));
                    dataList.add(cur.getString(101));
                    dataList.add(cur.getString(102));
                    dataList.add(cur.getString(103));
                    dataList.add(cur.getString(104));
                    dataList.add(cur.getString(105));
                    dataList.add(cur.getString(106));
                    dataList.add(cur.getString(107));
                    dataList.add(cur.getString(108));
                    dataList.add(cur.getString(109));
                    dataList.add(cur.getString(110));
                    dataList.add(cur.getString(111));
                    dataList.add(cur.getString(112));
                    dataList.add(cur.getString(113));
                    dataList.add(cur.getString(114));
                    dataList.add(cur.getString(115));
                    dataList.add(cur.getString(116));
                    dataList.add(cur.getString(117));
                    dataList.add(cur.getString(118));
                    dataList.add(cur.getString(119));
                    dataList.add(cur.getString(120));
                    dataList.add(cur.getString(121));
                    dataList.add(cur.getString(122));
                    dataList.add(cur.getString(123));
                    dataList.add(cur.getString(124));
                    dataList.add(cur.getString(125));
                    dataList.add(cur.getString(126));
                    dataList.add(cur.getString(127));
                    dataList.add(cur.getString(128));
                    dataList.add(cur.getString(129));
                    dataList.add(cur.getString(130));
                    dataList.add(cur.getString(131));
                    dataList.add(cur.getString(132));
                    dataList.add(cur.getString(133));
                    dataList.add(cur.getString(134));
                    dataList.add(cur.getString(135));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void updateBarisSurvey1Tambahan(String H_id_order,String H_id_province_ktp,String H_id_kab_kodya_ktp,String H_id_kec_ktp,String H_id_kel_ktp,String H_zipcode_ktp){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PROVINCE_KTP, H_id_province_ktp);
        contentValues.put(ID_KAB_KODYA_KTP, H_id_kab_kodya_ktp);
        contentValues.put(ID_KEC_KTP, H_id_kec_ktp);
        contentValues.put(ID_KEL_KTP, H_id_kel_ktp);
        contentValues.put(ZIPCODE_KTP, H_zipcode_ktp);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY1_TAMBAHAN,contentValues,ID_ORDER_1 + "='"+H_id_order+"'",null);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DB ERROR",e.toString());
        }
    }

    public void updateBarisSurvey3Tambahan(String H_id_order,String H_id_province_spouse,String H_id_kab_kodya_spouse,String H_id_kec_spouse, String H_id_kel_spouse, String H_zipcode_spouse){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PROVINCE_SPOUSE,H_id_province_spouse);
        contentValues.put(ID_KAB_KODYA_SPOUSE,H_id_kab_kodya_spouse);
        contentValues.put(ID_KEC_SPOUSE,H_id_kec_spouse);
        contentValues.put(ID_KEL_SPOUSE,H_id_kel_spouse);
        contentValues.put(ZIPCODE_SPOUSE,H_zipcode_spouse);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY3_TAMBAHAN,contentValues,ID_ORDER_2 + "='"+H_id_order+"'",null);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DB ERROR",e.toString());
        }
    }

    public void updateBarisSurvey1(String H_Id_order,String H_Nama, String H_Mother_maiden_name,
                                   String H_title, String H_marital_status, String H_identity_type, String H_Npwp_no,
                                   String H_Birth_place, String H_Birth_date, String H_Identity_no, String H_Address_ktp, String H_province_ktp, String H_kab_kodya_ktp,
                                   String H_kecamatan_ktp, String H_kelurahan_ktp, String H_Sandi_dati_2_ktp, String H_Postal_code_ktp,
                                   String H_Address_home, String H_province_home, String H_kab_kodya_home, String H_kecamatan_home,
                                   String H_kelurahan_home,String H_Sandi_dati_2_home, String H_Postal_code_home, String H_mail_address,
                                   String H_education, String H_sex, String H_Nama_panggilan, String H_Sandi_lahir, String H_religion, String H_Stay_length,
                                   String H_Telephone, String H_Telephone_2, String H_Handphone_1, String H_Handphone_2, String H_email) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_NAME, H_Nama);
        cv.put(ROW_MOTHER_MAIDEN_NAME, H_Mother_maiden_name);
        cv.put(ROW_TITLE, H_title);
        cv.put(ROW_MARITAL_STATUS, H_marital_status);
        cv.put(ROW_IDENTITY_TYPE, H_identity_type);
        cv.put(ROW_NPWP_NO, H_Npwp_no);
        cv.put(ROW_BIRTH_PLACE, H_Birth_place);
        cv.put(ROW_BIRTH_DATE, H_Birth_date);
        cv.put(ROW_ADDRESS_KTP, H_Address_ktp);
        cv.put(ROW_PROVINCE_KTP, H_province_ktp);
        cv.put(ROW_KAB_OR_KODYA_KTP, H_kab_kodya_ktp);
        cv.put(ROW_KECAMATAN_KTP, H_kecamatan_ktp);
        cv.put(ROW_KELURAHAN_KTP, H_kelurahan_ktp);
        cv.put(ROW_SANDI_DATI_2_KTP, H_Sandi_dati_2_ktp);
        cv.put(ROW_POSTAL_CODE_HOME, H_Postal_code_ktp);
        cv.put(ROW_ADDRESS_HOME, H_Address_home);
        cv.put(ROW_PROVINCE_HOME, H_province_home);
        cv.put(ROW_KAB_OR_KODYA_HOME, H_kab_kodya_home);
        cv.put(ROW_KECAMATAN_HOME, H_kecamatan_home);
        cv.put(ROW_KELURAHAN_HOME, H_kelurahan_home);
        cv.put(ROW_SANDI_DATI_2_HOME, H_Sandi_dati_2_home);
        cv.put(ROW_POSTAL_CODE_HOME, H_Postal_code_home);
        cv.put(ROW_MAIL_ADDRESS, H_mail_address);
        cv.put(ROW_TELEPHONE, H_Telephone);
        cv.put(ROW_TELEPHONE_2, H_Telephone_2);
        cv.put(ROW_EDUCATION, H_education);
        cv.put(ROW_SEX, H_sex);
        cv.put(ROW_NAMA_PANGGILAN, H_Nama_panggilan);
        cv.put(ROW_IDENTITY_NO, H_Identity_no);
        cv.put(ROW_SANDI_LAHIR, H_Sandi_lahir);
        cv.put(ROW_RELIGION, H_religion);
        cv.put(ROW_STAY_LENGTH, H_Stay_length);
        cv.put(ROW_HANDPHONE_STAY_1, H_Handphone_1);
        cv.put(ROW_HANDPHONE_STAY_2, H_Handphone_2);
        cv.put(ROW_EMAIL_STAY, H_email);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey2(String H_Id_order, String H_Pekerjaan,String H_job_title,
                                   String H_Name_economy_code, String H_Company_name,
                                   String H_Company_address, String H_Company_province,
                                   String H_Company_kab_or_kodya, String H_Company_kecamatan,
                                   String H_Company_kelurahan, String H_Sandi_dati_2_company,
                                   String H_Postal_code_company, String H_Company_telephone_1,
                                   String H_Company_telephone_2, String H_Line_of_business,
                                   String H_Economy_code , String H_Estabilished_since,
                                   String H_Company_fax_1) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PEKERJAAN, H_Pekerjaan);
        cv.put(ROW_JOB_TITLE_PEKERJAAN, H_job_title);
        cv.put(ROW_NAME_ECONOMY_CODE, H_Name_economy_code);
        cv.put(ROW_COMPANY_NAME, H_Company_name);
        cv.put(ROW_ADDRESS_PEKERJAAN, H_Company_address);
        cv.put(ROW_PROVINCE_PEKERJAAN, H_Company_province);
        cv.put(ROW_KAB_OR_KODYA_PEKERJAAN, H_Company_kab_or_kodya);
        cv.put(ROW_KECAMATAN_PEKERJAAN, H_Company_kecamatan);
        cv.put(ROW_KELURAHAN_PEKERJAAN, H_Company_kelurahan);
        cv.put(ROW_SANDI_DATI_2_PEKERJAAN, H_Sandi_dati_2_company);
        cv.put(ROW_POSTAL_CODE_PEKERJAAN, H_Postal_code_company);
        cv.put(ROW_TELEPHONE_1_PEKERJAAN, H_Company_telephone_1);
        cv.put(ROW_TELEPHONE_2_PEKERJAAN, H_Company_telephone_2);
        cv.put(ROW_LINE_OF_BUSINESS, H_Line_of_business);
        cv.put(ROW_ECONOMY_CODE, H_Economy_code);
        cv.put(ROW_ESTABILISHED_SINCE, H_Estabilished_since);
        cv.put(ROW_FAX_1_PEKERJAAN, H_Company_fax_1);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey3(String H_Id_order, String H_Spouse_name, String H_spouse_title,
                                   String H_spouse_identity_type, String H_Spouse_identity_no,
                                   String H_Spouse_birth_place_or_sandi_lahir,
                                   String H_spouse_religion, String H_Spouse_address,
                                   String H_province_spouse, String H_kab_kodya_spouse,
                                   String H_kecamatan_spouse, String H_kelurahan_spouse,
                                   String H_Sandi_dati_2_spouse, String H_Postal_code_spouse,
                                   String H_Spouse_no_handphone,
                                   String H_spouse_occupation_or_pekerjaan,
                                   String H_Spouse_company_name , String H_Spouse_company_address,
                                   String H_Spouse_company_telephone,
                                   String H_Spouse_line_of_business, String H_spouse_job_title,
                                   String H_spouse_sex, String H_Spouse_date_of_birth,
                                   String H_Spouse_fax) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_NAME_SPOUSE, H_Spouse_name);
        cv.put(ROW_TITLE_SPOUSE, H_spouse_title);
        cv.put(ROW_IDENTITY_TYPE_SPOUSE, H_spouse_identity_type);
        cv.put(ROW_BIRTH_PLACE_SPOUSE, H_Spouse_birth_place_or_sandi_lahir);
        cv.put(ROW_RELIGION_SPOUSE, H_spouse_religion);
        cv.put(ROW_ADDRESS_SPOUSE, H_Spouse_address);
        cv.put(ROW_PROVINCE_SPOUSE, H_province_spouse);
        cv.put(ROW_KAB_OR_KODYA_SPOUSE, H_kab_kodya_spouse);
        cv.put(ROW_KECAMATAN_SPOUSE, H_kecamatan_spouse);
        cv.put(ROW_KELURAHAN_SPOUSE, H_kelurahan_spouse);
        cv.put(ROW_SANDI_DATI_2_SPOUSE, H_Sandi_dati_2_spouse);
        cv.put(ROW_POSTAL_CODE_SPOUSE, H_Postal_code_spouse);
        cv.put(ROW_NO_HANDPHONE_SPOUSE, H_Spouse_no_handphone);
        cv.put(ROW_OCCUPATION_OR_PEKERJAAN_SPOUSE, H_spouse_occupation_or_pekerjaan);
        cv.put(ROW_COMPANY_NAME_SPOUSE, H_Spouse_company_name);
        cv.put(ROW_ADDRESS_PEKERJAAN_SPOUSE, H_Spouse_company_address);
        cv.put(ROW_TELEPHONE_SPOUSE, H_Spouse_company_telephone);
        cv.put(ROW_LINE_OF_BUSSINESS_SPOUSE, H_Spouse_line_of_business);
        cv.put(ROW_JOB_TITLE_SPOUSE, H_spouse_job_title);
        cv.put(ROW_SEX_SPOUSE, H_spouse_sex);
        cv.put(ROW_IDENTITY_NO_SPOUSE, H_Spouse_identity_no);
        cv.put(ROW_DATE_OF_BIRTH_SPOUSE, H_Spouse_date_of_birth);
        cv.put(ROW_FAX_SPOUSE, H_Spouse_fax);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey4(String H_Id_order, String H_has_contact_person,
                                   String H_Contact_name, String H_Contact_address,
                                   String H_contact_province, String H_contact_kab_or_kodya,
                                   String H_contact_kecamatan, String H_contact_kelurahan,
                                   String H_Contact_sandi_dati_2, String H_Contact_Postal_code,
                                   String H_Contact_telephone, String H_relationship) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_HAS_CONTACT_PERSON, H_has_contact_person);
        cv.put(ROW_NAME_CONTACT, H_Contact_name);
        cv.put(ROW_ADDRESS_CONTACT, H_Contact_address);
        cv.put(ROW_PROVINCE_CONTACT, H_contact_province);
        cv.put(ROW_KAB_OR_KODYA_CONTACT, H_contact_kab_or_kodya);
        cv.put(ROW_KECAMATAN_CONTACT, H_contact_kecamatan);
        cv.put(ROW_KELURAHAN_CONTACT, H_contact_kelurahan);
        cv.put(ROW_SANDI_DATI_2_CONTACT, H_Contact_sandi_dati_2);
        cv.put(ROW_POSTAL_CODE_CONTACT, H_Contact_Postal_code);
        cv.put(ROW_TELEPHONE_CONTACT, H_Contact_telephone);
        cv.put(ROW_RELATIONSHIP_CONTACT, H_relationship);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey5(String H_Id_order, String H_tipe_rumah,String H_home_status,
                                   String H_Jarak_rumah_ke_cabang, String H_Luas_tanah,
                                   String H_Luas_bangunan_rumah, String H_status_kepemilikan_rumah,
                                   String H_klasifikasi_perumahan, String H_tempat_menaruh_kendaraan,
                                   String H_status_garasi_kendaraan, String H_bentuk_bangunan_rumah,
                                   String H_kondisi_rumah, String H_luas_jalan_masuk_rumah,
                                   String H_status_kepemilikan_rumah_pemohon,
                                   String H_furniture_or_perabot) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TIPE_RUMAH, H_tipe_rumah);
        cv.put(ROW_HOME_STATUS, H_home_status);
        cv.put(ROW_JARAK_RUMAH_KE_CABANG, H_Jarak_rumah_ke_cabang);
        cv.put(ROW_LUAS_TANAH, H_Luas_tanah);
        cv.put(ROW_LUAS_BANGUNAN_RUMAH, H_Luas_bangunan_rumah);
        cv.put(ROW_STATUS_KEPEMILIKAN_RUMAH, H_status_kepemilikan_rumah);
        cv.put(ROW_KLASIFIKASI_PERUMAHAN, H_klasifikasi_perumahan);
        cv.put(ROW_TEMPAT_MENARUH_KENDARAAN, H_tempat_menaruh_kendaraan);
        cv.put(ROW_STATUS_GARASI_KENDARAAN, H_status_garasi_kendaraan);
        cv.put(ROW_BENTUK_BANGUNAN_RUMAH, H_bentuk_bangunan_rumah);
        cv.put(ROW_KONDISI_RUMAH, H_kondisi_rumah);
        cv.put(ROW_LUAS_JALAN_MASUK_RUMAH, H_luas_jalan_masuk_rumah);
        cv.put(ROW_STATUS_KEPEMILIKAN_RUMAH_PEMOHON, H_status_kepemilikan_rumah_pemohon);
        cv.put(ROW_FURNITURE, H_furniture_or_perabot);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey6(String H_Id_order, String H_Jarak_tempat_usaha_dari_rumah,
                                   String H_status_kepemilikan_usaha, String H_bentuk_bangunan_tempat_usaha,
                                   String H_lokasi_tempat_usaha, String H_jumlah_karyawan,
                                   String H_Lama_pemohon_menempati_tempat_usaha_tahun,
                                   String H_Lama_pemohon_menempati_tempat_usaha_bulan, String H_Lama_usaha_berdiri_tahun,
                                   String H_Lama_usaha_berdiri_bulan, String H_pekerjaan_or_usaha_terkait_ekspor_or_impor) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_JARAK_TEMPAT_USAHA_DARI_RUMAH, H_Jarak_tempat_usaha_dari_rumah);
        cv.put(ROW_STATUS_KEPEMILIKAN_USAHA, H_status_kepemilikan_usaha);
        cv.put(ROW_BENTUK_BANGUNAN_TEMPAT_USAHA, H_bentuk_bangunan_tempat_usaha);
        cv.put(ROW_LOKASI_TEMPAT_USAHA, H_lokasi_tempat_usaha);
        cv.put(ROW_JUMLAH_KARYAWAN, H_jumlah_karyawan);
        cv.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_TAHUN, H_Lama_pemohon_menempati_tempat_usaha_tahun);
        cv.put(ROW_LAMA_PEMOHON_MENEMPATI_TEMPAT_USAHA_BULAN, H_Lama_pemohon_menempati_tempat_usaha_bulan);
        cv.put(ROW_LAMA_USAHA_BERDIRI_TAHUN, H_Lama_usaha_berdiri_tahun);
        cv.put(ROW_LAMA_USAHA_BERDIRI_BULAN, H_Lama_usaha_berdiri_bulan);
        cv.put(ROW_PEKERJAAN_OR_USAHA_TERKAIT_EKSPOR_OR_IMPOR, H_pekerjaan_or_usaha_terkait_ekspor_or_impor);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey7(String H_Id_order, String H_tujuan_penggunaan_unit, String H_kondisi_mobil, String H_bagian_kondisi_tidak_baik, String H_Lama_kepemilikan_kendaraan_tahun, String H_Lama_kepemilikan_kendaraan_bulan) {

        ContentValues cv = new ContentValues();
        cv.put(ROW_TUJUAN_PENGGUNAAN_UNIT, H_tujuan_penggunaan_unit);
        cv.put(ROW_KONDISI_MOBIL, H_kondisi_mobil);
        cv.put(ROW_BAGIAN_KONDISI_TIDAK_BAIK, H_bagian_kondisi_tidak_baik);
        cv.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_TAHUN, H_Lama_kepemilikan_kendaraan_tahun);
        cv.put(ROW_LAMA_KEPEMILIKAN_KENDARAAN_BULAN, H_Lama_kepemilikan_kendaraan_bulan);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey8(String H_Id_order, String H_Jumlah_tanggungan, String H_Jumlah_anak) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_JUMLAH_TANGGUNGAN, H_Jumlah_tanggungan);
        cv.put(ROW_JUMLAH_ANAK, H_Jumlah_anak);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey9(String H_Id_order, String H_Omzet_or_penghasilan_gross, String H_Penghasilan_nett_or_take_home_pay, String H_Penghasilan_pasangan, String H_Penghasilan_tambahan, String H_Pengeluaran_or_kebutuhan_hidup, String H_Total_cicilan_leasing_lain, String H_Balance_terakhir_di_rekening_tabungan, String H_Rata_rata_mutasi_in_3_bulan_terakhir, String H_Rata_rata_mutasi_out_3_bulan_terakhir) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_OMZET_OR_PENGHASILAN_GROSS, H_Omzet_or_penghasilan_gross);
        cv.put(ROW_PENGHASILAN_NETT_OR_TAKE_HOME_PAY, H_Penghasilan_nett_or_take_home_pay);
        cv.put(ROW_PENGHASILAN_PASANGAN, H_Penghasilan_pasangan);
        cv.put(ROW_PENGHASILAN_TAMBAHAN, H_Penghasilan_tambahan);
        cv.put(ROW_PENGELUARAN_OR_KEBUTUHAN_HIDUP, H_Pengeluaran_or_kebutuhan_hidup);
        cv.put(ROW_TOTAL_CICILAN_LEASING_LAIN, H_Total_cicilan_leasing_lain);
        cv.put(ROW_BALANCE_TERAKHIR_DI_REKENING_TABUNGAN, H_Balance_terakhir_di_rekening_tabungan);
        cv.put(ROW_RATA_RATA_MUTASI_IN_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_in_3_bulan_terakhir);
        cv.put(ROW_RATA_RATA_MUTASI_OUT_3_BULAN_TERAKHIR, H_Rata_rata_mutasi_out_3_bulan_terakhir);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey10(String H_Id_order, String H_Collectabilitas_sid_or_slik_tertinggi, String H_pernah_kredit_di_tempat_lain, String H_Overdue_tertinggi, String H_Baki_debet_or_outstanding_hutang, String H_Nama_finance_company, String H_Alasan_menunggak_khusus_lebih_dari_coll_2) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_COLLECTABILITAS_SID_OR_SLIK_TERTINGGI, H_Collectabilitas_sid_or_slik_tertinggi);
        cv.put(ROW_PERNAH_KREDIT_DI_TEMPAT_LAIN, H_pernah_kredit_di_tempat_lain);
        cv.put(ROW_OVERDUE_TERTINGGI, H_Overdue_tertinggi);
        cv.put(ROW_BAKI_DEBET_OR_OUTSTANDING_HUTANG, H_Baki_debet_or_outstanding_hutang);
        cv.put(ROW_NAMA_FINANCE_COMPANY, H_Nama_finance_company);
        cv.put(ROW_ALASAN_MENUNGGAK_KHUSUS_LEBIH_DARI_COLL_2, H_Alasan_menunggak_khusus_lebih_dari_coll_2);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void updateBarisSurvey12(String H_Id_order, String H_apakah_direkomendasikan, String H_Alasan_or_point_penting_rekomendasi_anda) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_APAKAH_DIREKOMENDASIKAN, H_apakah_direkomendasikan);
        cv.put(ROW_ALASAN_OR_POINT_PENTING_REKOMENDASI_ANDA, H_Alasan_or_point_penting_rekomendasi_anda);

        try {
            db.update(NAMA_TABEL_SIMPANSURVEY, cv, ROW_ID_ORDER + "='" + H_Id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }
    //end table survey

    //table simpan json
    public void addRowJsonPilih(String json_hasil,String json_nama) {
        ContentValues values = new ContentValues();
        values.put(ROW_JSON_PILIH_HASIL, json_hasil);
        values.put(ROW_JSON_PILIH_NAMA, json_nama);
        values.put(ROW_JSON_PILIH_TANGGAL, "datetime()");

        try {
            db.insert(NAMA_TABEL_JSON_PILIH, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteJsonPilihAll(String json_name){
        try {
            db.delete(NAMA_TABEL_JSON_PILIH, ROW_JSON_PILIH_NAMA + "='" + json_name+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisJsonPilih(String json_name) {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_JSON_PILIH, new String[] {  ROW_JSON_PILIH_HASIL,
                    ROW_JSON_PILIH_NAMA,ROW_JSON_PILIH_TANGGAL }, ROW_JSON_PILIH_NAMA + "='" + json_name+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }
    //end table simpan json

    //simpan notif tugas
    public void addRowNotifTugas(String h_id_order,String h_nama,String h_alamat) {
        ContentValues values = new ContentValues();
        values.put(ROW_NOTIF_TUGAS_ID_ORDER, h_id_order);
        values.put(ROW_NOTIF_TUGAS_NAMA, h_nama);
        values.put(ROW_NOTIF_TUGAS_ALAMAT, h_alamat);
        values.put(ROW_NOTIF_TUGAS_STATUS, "0");

        try {
            db.insert(NAMA_TABEL_NOTIF_TUGAS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisNotifTugas(String id_order) {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_NOTIF_TUGAS, new String[] {  ROW_NOTIF_TUGAS_ID_ORDER,
                    ROW_NOTIF_TUGAS_NAMA,ROW_NOTIF_TUGAS_ALAMAT,ROW_NOTIF_TUGAS_STATUS },
                    ROW_NOTIF_TUGAS_ID_ORDER + "='" + id_order+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public ArrayList<ArrayList<Object>> ambilBarisNotifTugasALL() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_NOTIF_TUGAS, new String[] {  ROW_NOTIF_TUGAS_ID_ORDER,
                    ROW_NOTIF_TUGAS_NAMA,ROW_NOTIF_TUGAS_ALAMAT,ROW_NOTIF_TUGAS_STATUS }, null, null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void updateBarisNotifTugas() {
        ContentValues cv = new ContentValues();
        cv.put(ROW_NOTIF_TUGAS_STATUS, "1");


        try {
            db.update(NAMA_TABEL_NOTIF_TUGAS, cv, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }
    //end notif tugas

    //simpan status pernikahan
    public void addRowStatusPernikahan(String h_id_order,String h_hasil) {
        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_PERNIKAHAN_ID_ORDER, h_id_order);
        values.put(ROW_STATUS_PERNIKAHAN_HASIL, h_hasil);

        try {
            db.insert(NAMA_TABEL_STATUS_PERNIKAHAN, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteStatusPernikahanAll(String id_order){
        try {
            db.delete(NAMA_TABEL_STATUS_PERNIKAHAN, ROW_STATUS_PERNIKAHAN_ID_ORDER + "='" + id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public ArrayList<ArrayList<Object>> ambilBarisStatusPernikahan(String id_order) {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_STATUS_PERNIKAHAN, new String[] {  ROW_STATUS_PERNIKAHAN_ID_ORDER,
                    ROW_STATUS_PERNIKAHAN_HASIL }, ROW_STATUS_PERNIKAHAN_ID_ORDER + "='" + id_order+"'", null, null, null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));

                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }



    //table status terkirim
    public void addRowStatusTerkirim(String id_order) {
        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_TERKIRIM_ID_ORDER, id_order);

        try {
            db.insert(NAMA_TABEL_STATUS_TERKIRIM, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }


    public ArrayList<ArrayList<Object>> ambilBarisStatusTerkirim() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_STATUS_TERKIRIM, new String[] {  ROW_STATUS_TERKIRIM_ID,
                    ROW_STATUS_TERKIRIM_ID_ORDER }, null, null, null,
                    null, ROW_STATUS_TERKIRIM_ID+" ASC LIMIT 1");
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));

                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deleteStatusTerkirim(String id_order){
        try {
            db.delete(NAMA_TABEL_STATUS_TERKIRIM,
                    ROW_STATUS_TERKIRIM_ID_ORDER+" = '"+id_order+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //table simpan photo





    //table notif sampai
    public void addRowNotifSampai(String id_surveyor, String id_order, String status_hasil,
                                  String latitude, String longitude, String tanggal, String tk) {
        ContentValues values = new ContentValues();
        values.put(ROW_NOTIF_SAMPAI_ID_SURVEYOR, id_surveyor);
        values.put(ROW_NOTIF_SAMPAI_ID_ORDER, id_order);
        values.put(ROW_NOTIF_SAMPAI_STATUS_HASIL, status_hasil);
        values.put(ROW_NOTIF_SAMPAI_LATITUDE, latitude);
        values.put(ROW_NOTIF_SAMPAI_LONGITUDE, longitude);
        values.put(ROW_NOTIF_SAMPAI_TANGGAL, tanggal);
        values.put(ROW_NOTIF_SAMPAI_TK, tk);

        try {
            db.insert(NAMA_TABEL_NOTIF_SAMPAI, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }



    public ArrayList<ArrayList<Object>> ambilBarisNotifSampai() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_NOTIF_SAMPAI, new String[] {  ROW_NOTIF_SAMPAI_ID,
                            ROW_NOTIF_SAMPAI_ID_SURVEYOR,
                            ROW_NOTIF_SAMPAI_ID_ORDER,
                            ROW_NOTIF_SAMPAI_STATUS_HASIL,
                            ROW_NOTIF_SAMPAI_LATITUDE,
                            ROW_NOTIF_SAMPAI_LONGITUDE,
                            ROW_NOTIF_SAMPAI_TK,
                            ROW_NOTIF_SAMPAI_TANGGAL }, null,
                    null, null,
                    null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));

                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deleteNotifSampai(String id){
        try {
            db.delete(NAMA_TABEL_NOTIF_SAMPAI,
                    ROW_NOTIF_SAMPAI_ID+" = '"+id+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //table notif sampai



    //table janji survey
    public void addRowJanjiSurvey(String id_surveyor, String id_order, String janji_survey,
                                  String tk) {
        ContentValues values = new ContentValues();
        values.put(ROW_JANJI_SURVEY_ID_SURVEYOR, id_surveyor);
        values.put(ROW_JANJI_SURVEY_ID_ORDER, id_order);
        values.put(ROW_JANJI_SURVEY_JANJI_SURVEY, janji_survey);
        values.put(ROW_JANJI_SURVEY_TK, tk);

        try {
            db.insert(NAMA_TABEL_JANJI_SURVEY, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }


    public ArrayList<ArrayList<Object>> ambilBarisJanjiSurveyByOrder(String id_surveyor, String id_order) {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_JANJI_SURVEY, new String[] {  ROW_JANJI_SURVEY_ID,
                            ROW_JANJI_SURVEY_ID_SURVEYOR,
                            ROW_JANJI_SURVEY_ID_ORDER,
                            ROW_JANJI_SURVEY_JANJI_SURVEY,
                            ROW_JANJI_SURVEY_TK },
                    ROW_JANJI_SURVEY_ID_SURVEYOR+" = '"+id_surveyor+"' AND "
                            +ROW_JANJI_SURVEY_ID_ORDER+" = '"+id_order+"'",
                    null, null,
                    null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));

                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }


    public ArrayList<ArrayList<Object>> ambilBarisJanjiSurvey() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur = null;
        try {
            cur = db.query(NAMA_TABEL_JANJI_SURVEY, new String[] {  ROW_JANJI_SURVEY_ID,
                            ROW_JANJI_SURVEY_ID_SURVEYOR,
                            ROW_JANJI_SURVEY_ID_ORDER,
                            ROW_JANJI_SURVEY_JANJI_SURVEY,
                            ROW_JANJI_SURVEY_TK }, null,
                    null, null,
                    null, null);
            cur.moveToFirst();
            if (
                    !cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));

                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cur != null)
                cur.close();
        }
        return dataArray;
    }

    public void deleteJanjiSurvey(String id){
        try {
            db.delete(NAMA_TABEL_JANJI_SURVEY,
                    ROW_JANJI_SURVEY_ID+" = '"+id+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    //table janji survey
}