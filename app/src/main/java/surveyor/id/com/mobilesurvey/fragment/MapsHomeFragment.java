package surveyor.id.com.mobilesurvey.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.MapsActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;

import static android.content.Context.LOCATION_SERVICE;


public class MapsHomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseManager dm;
    private String get_username, get_namalengkap, get_id_surveyor;;
    private GoogleMap mMap;
    private String id_order, namalengkap_surveyor, nik_surveyor, tanggal_lahir, bulan_lahir,
            tahun_lahir, order_code, name, tempatlahir, tgl_lahir, alamat, rt, rw,
            nama_kelurahan, nama_kota, nama_provinsi, zipcode, telp, jenis_kredit, jml_pinjaman,
            tenor, kategori_kendaraan, status_kendaraan, merk_kendaraan, model_kendaraan,
            type_kendaraan, tahun_kendaraan, warna, transmisi, plat_nomor, km_kendaraan,
            bahan_bakar, harga, nama_kecamatan;
    private int status_survey;
    private Double latitude_survey,longitude_survey;
    private String checking_latitude_survey,checking_longitude_survey;
    public ArrayList<String> list_of_maps_id = new ArrayList<>();
    private MapView mapView;
    private LocationManager locationManager;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps_home, container, false);

        mapView = (MapView) view.findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        dm = new DatabaseManager(hsContext);

        buildGoogleApiClient();

        ArrayList<ArrayList<Object>> data_user = dm.ambilSemuaBaris();
        if(data_user.size()>0){
            ArrayList<Object> baris = data_user.get(0);
            get_username    = baris.get(0).toString();
            get_namalengkap = baris.get(2).toString();
            get_id_surveyor = baris.get(3).toString();
        }

   //     callmaps();
//        callmaps();

        return view;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(hsContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void updateLocationUI() {
        if (ActivityCompat.checkSelfPermission(hsContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(hsContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null) {
            LatLng sekarang = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sekarang, 16));
        }else {
            //Toast.makeText(hsContext, "Mohon Nyalakan GPS Anda", Toast.LENGTH_LONG).show();
            LatLng indonesia = new LatLng(-6.208864, 106.845818);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 12));
        }
    }

    public void callmaps() {
        updateLocationUI();
        updatejson();
        list_of_maps_id.isEmpty();
        ArrayList<ArrayList<Object>> data_list_all  = dm.ambilBarisJson("List All");//
        if (data_list_all .size() > 0) {
            try {
                ArrayList<Object> baris = data_list_all.get(0);
                String tam_json_all = baris.get(0).toString();

                JSONObject jObj = new JSONObject(tam_json_all);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj_li = arrayData.getJSONObject(i);
                            status_survey = obj_li.getInt("status_survey");
                            //       Toast.makeText(getBaseContext(), kategori_kendaraan, Toast.LENGTH_LONG).show();
                            if (status_survey == 0) {
                                //      Log.d("Persamaan ", kategori_kendaraan + "=" + cek_tam_kategori_kendaraan + " ," + status_survey + " = " + cek_status_survey);

                                id_order                    = obj_li.getString("id_order");
                                namalengkap_surveyor        = get_namalengkap;
                                order_code                  = obj_li.getString("order_code");
                                name                        = obj_li.getString("name");

                                jenis_kredit                = obj_li.getString("jenis_kredit");
                                jml_pinjaman                = obj_li.getString("jml_pinjaman");
                                tenor                       = obj_li.getString("tenor");
                                kategori_kendaraan          = obj_li.getString("kategori_kendaraan");
                                merk_kendaraan              = obj_li.getString("merk_kendaraan");
                                model_kendaraan             = obj_li.getString("model_kendaraan");
                                type_kendaraan              = obj_li.getString("type_kendaraan");
                                tahun_kendaraan             = obj_li.getString("tahun_kendaraan");
                                warna                       = obj_li.getString("warna");
                                plat_nomor                  = obj_li.getString("plat_nomor");
                                km_kendaraan                = obj_li.getString("km_kendaraan");
                                bahan_bakar                 = obj_li.getString("bahan_bakar");
                                status_survey               = obj_li.getInt("status_survey");


                                checking_latitude_survey    = obj_li.getString("latitude_survey");
                                checking_longitude_survey   = obj_li.getString("longitude_survey");

                                if(checking_longitude_survey != null && checking_longitude_survey != null){
                                    if(!checking_latitude_survey.equals("null") && !checking_longitude_survey.equals("null")){
                                        if(!checking_latitude_survey.equals("") && !checking_longitude_survey.equals("")){
                                            latitude_survey     = obj_li.getDouble("latitude_survey");
                                            longitude_survey    = obj_li.getDouble("longitude_survey");

                                            LatLng t_lokasi = new LatLng(latitude_survey, longitude_survey);
                                            if (kategori_kendaraan.equals("Motor Besar Bekas")) {
                                                mMap.addMarker(new MarkerOptions().position(t_lokasi).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_motor_kecil)).title(name).snippet("Marker Description"));
                                                list_of_maps_id.add(id_order);
                                            } else if (kategori_kendaraan.equals("Mobil Bekas")) {
                                                mMap.addMarker(new MarkerOptions().position(t_lokasi).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_mobil_kecil)).title(name).snippet("Marker Description"));
                                                list_of_maps_id.add(id_order);
                                            } else {
                                                mMap.addMarker(new MarkerOptions().position(t_lokasi).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_uang_kecil)).title(name).snippet("Marker Description"));
                                                list_of_maps_id.add(id_order);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                try {
                    String posisi_s = marker.getId().replace("m","");
                    int posisi = Integer.parseInt(posisi_s);
                    String id_order_ambil = list_of_maps_id.get(posisi);
                    ArrayList<ArrayList<Object>> data6 = dm.ambilBarisJson("List All");
                    ArrayList<Object> baris = data6.get(0);
                    String tam_json_all = baris.get(0).toString();
                    JSONArray response_li = new JSONArray(tam_json_all);
                    for (int i = 0; i < response_li.length(); i++) {
                        JSONObject obj_li = response_li.getJSONObject(i);
                        String id_order_ambil_cek = obj_li.getString("id_order");
                        if (id_order_ambil.equals(id_order_ambil_cek)) {
                          //  Log.d("Persamaan ", kategori_kendaraan + "=" + cek_tam_kategori_kendaraan + " ," + status_survey + " = " + cek_tam_status_survey);

                            String id_order_cek = obj_li.getString("id_order");
                            String id_customer_cek = obj_li.getString("id_customer");
                            String namalengkap_surveyor_cek = get_namalengkap;
                            String asuransi_cek = obj_li.getString("asuransi");
                            String jenis_kredit_cek = obj_li.getString("jenis_kredit");
                            String order_code_cek = obj_li.getString("order_code");
                            String jml_pinjaman_cek = obj_li.getString("jml_pinjaman");
                            String otr_cek = obj_li.getString("otr");
                            String dp_cek = obj_li.getString("dp");
                            String tenor_cek = obj_li.getString("tenor");
                            String date_survey_cek = obj_li.getString("date_survey");
                            String janji_survey_cek = obj_li.getString("janji_survey");
                            String janji_survey_tanggal_cek = obj_li.getString("janji_survey_tanggal");
                            String janji_survey_bulan_cek = obj_li.getString("janji_survey_bulan");
                            String janji_survey_tahun_cek = obj_li.getString("janji_survey_tahun");
                            String janji_survey_jam_cek = obj_li.getString("janji_survey_jam");
                            String janji_survey_menit_cek = obj_li.getString("janji_survey_menit");
                            String order_date_cek = obj_li.getString("order_date");
                            String c_status_survey_cek = obj_li.getString("status_survey");
                            String status_order_cek = obj_li.getString("status_order");
                            String kode_cabang_cek = obj_li.getString("kode_cabang");
                            String jaminan_multiguna_cek = obj_li.getString("jaminan_multiguna");
                            String c_latitude_survey_cek = obj_li.getString("latitude_survey");
                            String c_longitude_survey_cek = obj_li.getString("longitude_survey");

                            String kategori_kendaraan_cek = obj_li.getString("kategori_kendaraan");
                            String status_kendaraan_cek = obj_li.getString("status_kendaraan");
                            String merk_kendaraan_cek = obj_li.getString("merk_kendaraan");
                            String model_kendaraan_cek = obj_li.getString("model_kendaraan");
                            String type_kendaraan_cek = obj_li.getString("type_kendaraan");
                            String tahun_kendaraan_cek = obj_li.getString("tahun_kendaraan");
                            String warna_cek = obj_li.getString("warna");
                            String plat_nomor_cek = obj_li.getString("plat_nomor");
                            String km_kendaraan_cek = obj_li.getString("km_kendaraan");
                            String bahan_bakar_cek = obj_li.getString("bahan_bakar");

                            int status_survey_cek = obj_li.getInt("status_survey");
                            double latitude_survey_cek = obj_li.getDouble("latitude_survey");
                            double longitude_survey_cek = obj_li.getDouble("longitude_survey");

                            String name_cek = obj_li.getString("name");
                            String mother_maiden_name_cek = obj_li.getString("mother_maiden_name");
                            String gelar_cek = obj_li.getString("gelar");
                            String title_cek = obj_li.getString("title");
                            String marital_status_cek = obj_li.getString("marital_status");
                            String identity_type_cek = obj_li.getString("identity_type");
                            String npwp_no_cek = obj_li.getString("npwp_no");
                            String birth_place_cek = obj_li.getString("birth_place");
                            String birth_date_cek = obj_li.getString("birth_date");
                            String address_ktp_cek = obj_li.getString("address_ktp");
                            String sandi_dati_2_ktp_cek = obj_li.getString("sandi_dati_2_ktp");
                            String kab_or_kodya_ktp_cek = obj_li.getString("kab_or_kodya_ktp");
                            String postal_code_ktp_cek = obj_li.getString("postal_code_ktp");
                            String address_home_cek = obj_li.getString("address_home");
                            String sandi_dati_2_home_cek = obj_li.getString("sandi_dati_2_home");
                            String kab_or_kodya_home_cek = obj_li.getString("kab_or_kodya_home");
                            String postal_code_home_cek = obj_li.getString("postal_code_home");
                            String mail_address_cek = obj_li.getString("mail_address");
                            String tipe_rumah_cek = obj_li.getString("tipe_rumah");
                            String home_status_cek = obj_li.getString("home_status");
                            String telephone_cek = obj_li.getString("telephone");
                            String telephone_2_cek = obj_li.getString("telephone_2");
                            String education_cek = obj_li.getString("education");
                            String sex_cek = obj_li.getString("sex");
                            String nama_panggilan_cek = obj_li.getString("nama_panggilan");
                            String identity_no_cek = obj_li.getString("identity_no");
                            String sandi_lahir_cek = obj_li.getString("sandi_lahir");
                            String religion_cek = obj_li.getString("religion");
                            String kecamatan_ktp_cek = obj_li.getString("kecamatan_ktp");
                            String province_ktp_cek = obj_li.getString("province_ktp");
                            String kelurahan_ktp_cek = obj_li.getString("kelurahan_ktp");
                            String jarak_customer_cek = obj_li.getString("jarak_customer");
                            String kecamatan_home_cek = obj_li.getString("kecamatan_home");
                            String province_home_cek = obj_li.getString("province_home");
                            String kelurahan_home_cek = obj_li.getString("kelurahan_home");
                            String stay_length_cek = obj_li.getString("stay_length");
                            String handphone_1_cek = obj_li.getString("handphone_1");
                            String handphone_2_cek = obj_li.getString("handphone_2");
                            String email_stay_cek = obj_li.getString("email_stay");
                            String pekerjaan_cek = obj_li.getString("pekerjaan");
                            String job_title_pekerjaan_cek = obj_li.getString("job_title_pekerjaan");
                            String name_economy_code_cek = obj_li.getString("name_economy_code");
                            String company_name_cek = obj_li.getString("company_name");
                            String address_pekerjaan_cek = obj_li.getString("address_pekerjaan");
                            String sandi_dati_2_pekerjaan_cek = obj_li.getString("sandi_dati_2_pekerjaan");
                            String kab_or_kodya_pekerjaan_cek = obj_li.getString("kab_or_kodya_pekerjaan");
                            String postal_code_pekerjaan_cek = obj_li.getString("postal_code_pekerjaan");
                            String telephone_1_pekerjaan_cek = obj_li.getString("telephone_1_pekerjaan");
                            String telephone_2_pekerjaan_cek = obj_li.getString("telephone_2_pekerjaan");
                            String line_of_business_cek = obj_li.getString("line_of_business");
                            String economy_code_cek = obj_li.getString("economy_code");
                            String estabilished_since_cek = obj_li.getString("estabilished_since");
                            String kecamatan_pekerjaan_cek = obj_li.getString("kecamatan_pekerjaan");
                            String province_pekerjaan_cek = obj_li.getString("province_pekerjaan");
                            String kelurahan_pekerjaan_cek = obj_li.getString("kelurahan_pekerjaan");
                            String fax_1_pekerjaan_cek = obj_li.getString("fax_1_pekerjaan");
                            String name_spouse_cek = obj_li.getString("name_spouse");
                            String title_spouse_cek = obj_li.getString("title_spouse");
                            String identity_type_spouse_cek = obj_li.getString("identity_type_spouse");
                            String birth_place_spouse_cek = obj_li.getString("birth_place_spouse");
                            String religion_spouse_cek = obj_li.getString("religion_spouse");
                            String address_spouse_cek = obj_li.getString("address_spouse");
                            String sandi_dati_2_spouse_cek = obj_li.getString("sandi_dati_2_spouse");
                            String kab_or_kodya_spouse_cek = obj_li.getString("kab_or_kodya_spouse");
                            String postal_code_spouse_cek = obj_li.getString("postal_code_spouse");
                            String no_handphone_cek = obj_li.getString("no_handphone");
                            String occupation_or_pekerjaan_spouse_cek = obj_li.getString("occupation_or_pekerjaan_spouse");
                            String company_name_spouse_cek = obj_li.getString("company_name_spouse");
                            String address_pekerjaan_spouse_cek = obj_li.getString("address_pekerjaan_spouse");
                            String telephone_spouse_cek = obj_li.getString("telephone_spouse");
                            String line_of_business_spouse_cek = obj_li.getString("line_of_business_spouse");
                            String job_title_spouse_cek = obj_li.getString("job_title_spouse");
                            String sex_spouse_cek = obj_li.getString("sex_spouse");
                            String identity_no_spouse_cek = obj_li.getString("identity_no_spouse");
                            String date_of_birth_spouse_cek = obj_li.getString("date_of_birth_spouse");
                            String kecamatan_spouse_cek = obj_li.getString("kecamatan_spouse");
                            String province_spouse_cek = obj_li.getString("province_spouse");
                            String kelurahan_spouse_cek = obj_li.getString("kelurahan_spouse");
                            String estabilished_since_spouse_cek = obj_li.getString("estabilished_since_spouse");
                            String fax_spouse_cek = obj_li.getString("fax_spouse");
                            String has_contact_person_cek = obj_li.getString("has_contact_person");
                            String name_contact_cek = obj_li.getString("name_contact");
                            String address_contact_cek = obj_li.getString("address_contact");
                            String sandi_dati_2_contact_cek = obj_li.getString("sandi_dati_2_contact");
                            String kab_or_kodya_contact_cek = obj_li.getString("kab_or_kodya_contact");
                            String postal_code_contact_cek = obj_li.getString("postal_code_contact");
                            String telephone_contact_cek = obj_li.getString("telephone_contact");
                            String relationship_contact_cek = obj_li.getString("relationship_contact");
                            String kecamatan_contact_cek = obj_li.getString("kecamatan_contact");
                            String province_contact_cek = obj_li.getString("province_contact");
                            String kelurahan_contact_cek = obj_li.getString("kelurahan_contact");


                            String jarak_rumah_ke_cabang = obj_li.getString("jarak_rumah_ke_cabang");
                            String luas_tanah = obj_li.getString("luas_tanah");
                            String luas_bangunan_rumah = obj_li.getString("luas_bangunan_rumah");
                            String status_kepemilikan_rumah = obj_li.getString("status_kepemilikan_rumah");
                            String klasifikasi_perumahan = obj_li.getString("klasifikasi_perumahan");
                            String tempat_menaruh_kendaraan = obj_li.getString("tempat_menaruh_kendaraan");
                            String status_garasi_kendaraan = obj_li.getString("status_garasi_kendaraan");
                            String bentuk_bangunan_rumah = obj_li.getString("bentuk_bangunan_rumah");
                            String kondisi_rumah = obj_li.getString("kondisi_rumah");
                            String luas_jalan_masuk_rumah = obj_li.getString("luas_jalan_masuk_rumah");
                            String status_kepemilikan_rumah_pemohon = obj_li.getString("status_kepemilikan_rumah_pemohon");
                            String furniture = obj_li.getString("furniture");

                            String jarak_tempat_usaha_dari_rumah = obj_li.getString("jarak_tempat_usaha_dari_rumah");
                            String status_kepemilikan_usaha = obj_li.getString("status_kepemilikan_usaha");
                            String bentuk_bangunan_tempat_usaha = obj_li.getString("bentuk_bangunan_tempat_usaha");
                            String lokasi_tempat_usaha = obj_li.getString("lokasi_tempat_usaha");
                            String jumlah_karyawan = obj_li.getString("jumlah_karyawan");
                            String lama_pemohon_menempati_tempat_usaha = obj_li.getString("lama_pemohon_menempati_tempat_usaha");
                            String bulan_lama_pemohon_menempati_tempat_usaha = obj_li.getString("bulan_lama_pemohon_menempati_tempat_usaha");
                            String tahun_lama_pemohon_menempati_tempat_usaha = obj_li.getString("tahun_lama_pemohon_menempati_tempat_usaha");
                            String lama_usaha_berdiri = obj_li.getString("lama_usaha_berdiri");
                            String bulan_lama_usaha_berdiri = obj_li.getString("bulan_lama_usaha_berdiri");
                            String tahun_lama_usaha_berdiri = obj_li.getString("tahun_lama_usaha_berdiri");
                            String pekerjaan_or_usaha_terkait_ekspor_or_impor = obj_li.getString("pekerjaan_or_usaha_terkait_ekspor_or_impor");

                            String tujuan_penggunaan_unit = obj_li.getString("tujuan_penggunaan_unit");
                            String kondisi_mobil = obj_li.getString("kondisi_mobil");
                            String lama_kepemilikan_kendaraan = obj_li.getString("lama_kepemilikan_kendaraan");
                            String bulan_lama_kepemilikan_kendaraan = obj_li.getString("bulan_lama_kepemilikan_kendaraan");
                            String tahun_lama_kepemilikan_kendaraan = obj_li.getString("tahun_lama_kepemilikan_kendaraan");

                            String jumlah_tanggungan = obj_li.getString("jumlah_tanggungan");
                            String jumlah_anak = obj_li.getString("jumlah_anak");

                            String omzet_or_penghasilan_gross = obj_li.getString("omzet_or_penghasilan_gross");
                            String penghasilan_nett_or_take_home_pay = obj_li.getString("penghasilan_nett_or_take_home_pay");
                            String penghasilan_pasangan = obj_li.getString("penghasilan_pasangan");
                            String penghasilan_tambahan = obj_li.getString("penghasilan_tambahan");
                            String pengeluaran_or_kebutuhan_hidup = obj_li.getString("pengeluaran_or_kebutuhan_hidup");
                            String total_cicilan_leasing_lain = obj_li.getString("total_cicilan_leasing_lain");
                            String balance_terakhir_di_rekening_tabungan = obj_li.getString("balance_terakhir_di_rekening_tabungan");
                            String rata_rata_mutasi_in_3_bulan_terakhir = obj_li.getString("rata_rata_mutasi_in_3_bulan_terakhir");
                            String rata_rata_mutasi_out_3_bulan_terakhir = obj_li.getString("rata_rata_mutasi_out_3_bulan_terakhir");

                            String collectabilitas_sid_or_slik_tertinggi = obj_li.getString("collectabilitas_sid_or_slik_tertinggi");
                            String pernah_kredit_di_tempat_lain = obj_li.getString("pernah_kredit_di_tempat_lain");
                            String overdue_tertinggi = obj_li.getString("overdue_tertinggi");
                            String baki_debet_or_outstanding_hutang = obj_li.getString("baki_debet_or_outstanding_hutang");
                            String nama_finance_company = obj_li.getString("nama_finance_company");
                            String alasan_menunggak_khusus_lebih_dari_coll_2 = obj_li.getString("alasan_menunggak_khusus_lebih_dari_coll_2");

                            String apakah_direkomendasikan = obj_li.getString("apakah_direkomendasikan");
                            String alasan_or_point_penting_rekomendasi_anda = obj_li.getString("alasan_or_point_penting_rekomendasi_anda");

                            String photo_customer_app_cek = obj_li.getString("photo_customer_app");
                            String photo_customer_home_cek = obj_li.getString("photo_customer_home");
                            String photo_customer_home2_cek = obj_li.getString("photo_customer_home2");
                            String photo_customer_id_cek = obj_li.getString("photo_customer_id");
                            String photo_customer_kk_cek = obj_li.getString("photo_customer_kk");
                            String photo_customer_pbb_cek = obj_li.getString("photo_customer_pbb");
                            String photo_customer_salary_cek = obj_li.getString("photo_customer_salary");
                            String photo_customer_business_cek = obj_li.getString("photo_customer_business");
                            String photo_ttd_digital_cek = obj_li.getString("photo_ttd_digital");
                            String photo_ttd_digital_surveyor_cek = obj_li.getString("photo_ttd_digital_surveyor");
                            String photo_ktp_cek = obj_li.getString("photo_ktp");

                            String status = obj_li.getString("status");

                            if(status_survey_cek==0){
                                Intent ab = new Intent(hsContext, MapsActivity.class);
                                Bundle detail = new Bundle();
                                detail.putString("id_order", id_order_cek);
                                detail.putString("id_customer", id_customer_cek);
                                detail.putString("namalengkap_surveyor", get_namalengkap);
                                detail.putString("asuransi", asuransi_cek);
                                detail.putString("jenis_kredit", jenis_kredit_cek);
                                detail.putString("order_code", order_code_cek);
                                detail.putString("jml_pinjaman", jml_pinjaman_cek);
                                detail.putString("otr", otr_cek);
                                detail.putString("dp", dp_cek);
                                detail.putString("tenor", tenor_cek);
                                detail.putString("kode_cabang", kode_cabang_cek);
                                detail.putString("jaminan_multiguna", jaminan_multiguna_cek);

                                detail.putString("name", name_cek);
                                detail.putString("identity_type", identity_type_cek);
                                detail.putString("identity_no", identity_no_cek);
                                detail.putString("address_home", address_home_cek);
                                detail.putString("telephone", telephone_cek);
                                detail.putString("sex", sex_cek);
                                detail.putString("handphone_1", handphone_1_cek);

                                detail.putString("kategori_kendaraan", kategori_kendaraan_cek);
                                detail.putString("merk_kendaraan", merk_kendaraan_cek);
                                detail.putString("model_kendaraan", model_kendaraan_cek);
                                detail.putString("type_kendaraan", type_kendaraan_cek);
                                detail.putString("tahun", tahun_kendaraan_cek);
                                detail.putString("warna", warna_cek);
                                detail.putString("plat_nomor", plat_nomor_cek);
                                detail.putString("km_kendaraan", km_kendaraan_cek);
                                detail.putString("bahan_bakar", bahan_bakar_cek);

                                detail.putString("status_survey", "" + status_survey_cek);
                                detail.putString("latitude_survey", "" + latitude_survey_cek);
                                detail.putString("longitude_survey", "" + longitude_survey_cek);
                                ab.putExtras(detail);
                                startActivity(ab);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updatejson() {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_json_all,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            dm.deleteTugasAll("List All");
                            dm.addRowTugas(String.valueOf(response), "List All");
                        } catch (Exception e) {
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
                map.put("id_surveyor", get_id_surveyor);
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(hsContext);
        requestQueue.add(jArr);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        sendRequest();
        callmaps();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.clear();
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mMap.setMyLocationEnabled(true);
    }

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
    public void onResume() {
        super.onResume();
    //    updateLocationUI();
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
