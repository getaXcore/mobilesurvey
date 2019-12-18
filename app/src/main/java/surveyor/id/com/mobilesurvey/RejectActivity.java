package surveyor.id.com.mobilesurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RejectActivity extends AppCompatActivity {
    private TextView Tam_Namalengkap,Tam_Tempat_lahir,Tam_Tanggal_lahir,Tam_Bulan_lahir,Tam_Tahun_lahir,Tam_Alamat,Tam_Rt,Tam_Rw,Tam_Kelurahan,Tam_Kecamatan,Tam_Nama_kota,Tam_Nama_provinsi,Tam_Zipcode,
            Tam_Telp,Tam_Kategori_kendaraan,Tam_Status_kendaraan,Tam_Merk_kendaraan,Tam_Model_kendaraan,Tam_Type_kendaraan,Tam_Tahun,Tam_Warna,Tam_Transmisi,Tam_Plat_nomor,Tam_Km_kendaraan,Tam_Bahan_bakar,Tam_Harga,Tam_Jml_pinjaman,Tam_Tenor;
    private ImageView Tam_type_gambar_kendaraan;
    private RelativeLayout Tam_Data_Dana_Tunai, Tam_Data_Kendaraan;
    private String fk_get_id_order,fk_namalengkap,fk_tempatlahir,fk_tgl_lahir,fk_alamat,fk_rt,fk_rw,fk_nama_kelurahan,fk_nama_kecamatan,
            fk_nama_kota,fk_nama_provinsi,fk_zipcode,fk_telp,fk_kategori_kendaraan,fk_status_kendaraan,fk_merk_kendaraan,
            fk_model_kendaraan,fk_type_kendaraan,fk_tahun,fk_warna,fk_transmisi,fk_plat_nomor,fk_km_kendaraan,fk_bahan_bakar,fk_harga,fk_status_survey,fk_latitude,fk_longitude,fk_tanggal_lahir,fk_bulan_lahir,fk_tahun_lahir,fk_jenis_kredit,fk_jml_pinjaman,fk_tenor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Reject Customer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tam_Namalengkap             = (TextView) findViewById(R.id.t_nama_lengkap);
        Tam_Tempat_lahir            = (TextView) findViewById(R.id.t_tempat_lahir);
        Tam_Tanggal_lahir           = (TextView) findViewById(R.id.t_tanggal_lahir);
        Tam_Bulan_lahir             = (TextView) findViewById(R.id.t_bulan_lahir);
        Tam_Tahun_lahir             = (TextView) findViewById(R.id.t_tahun_lahir);
        Tam_type_gambar_kendaraan   = (ImageView) findViewById(R.id.gambar_type);
        Tam_Alamat                  = (TextView) findViewById(R.id.t_alamat);
        Tam_Rt                      = (TextView) findViewById(R.id.t_rt);
        Tam_Rw                      = (TextView) findViewById(R.id.t_rw);
        Tam_Kelurahan               = (TextView) findViewById(R.id.t_kelurahan);
        Tam_Kecamatan               = (TextView) findViewById(R.id.t_kecamatan);
        //     Tam_Nama_kota = (TextView) view.findViewById(R.id.tam_nama_kota);
        Tam_Nama_provinsi           = (TextView) findViewById(R.id.t_provinsi);
        Tam_Zipcode                 = (TextView) findViewById(R.id.t_kodepos);
        Tam_Telp                    = (TextView) findViewById(R.id.t_no_telp);
        //      Tam_Kategori_kendaraan = (TextView) view.findViewById(R.id.tam_kategori_kendaraan);
        Tam_Status_kendaraan        = (TextView) findViewById(R.id.t_status_kendaraan);
        Tam_Merk_kendaraan          = (TextView) findViewById(R.id.t_merk);
        //       Tam_Model_kendaraan = (TextView) view.findViewById(R.id.tam_model_kendaraan);
        Tam_Type_kendaraan          = (TextView) findViewById(R.id.t_type);
        //      Tam_Tahun = (TextView) view.findViewById(R.id.tam_tahun);
        Tam_Warna                   = (TextView) findViewById(R.id.t_warna);
        Tam_Transmisi               = (TextView) findViewById(R.id.t_transmisi);
        Tam_Plat_nomor              = (TextView) findViewById(R.id.t_plat);
        //       Tam_Km_kendaraan = (TextView) view.findViewById(R.id.tam_km_kendaraan);
        Tam_Bahan_bakar             = (TextView) findViewById(R.id.t_bahan_bakar);
        Tam_Harga                   = (TextView) findViewById(R.id.t_harga);
        Tam_Jml_pinjaman            = (TextView) findViewById(R.id.t_jml_pinjaman);
        Tam_Tenor                   = (TextView) findViewById(R.id.t_tenor);
        Tam_Data_Dana_Tunai         = (RelativeLayout) findViewById(R.id.data_dana_tunai);
        Tam_Data_Kendaraan          = (RelativeLayout) findViewById(R.id.data_kendaraan);

        tampildata_all();

        fk_tenor = getIntent().getExtras().getString("tenor");
        fk_jenis_kredit = getIntent().getExtras().getString("jenis_kredit");

        if(fk_jenis_kredit.equals("Dana Tunai")){
            Tam_Data_Dana_Tunai.setVisibility(View.VISIBLE);
            Tam_Data_Kendaraan.setVisibility(View.GONE);
        }else{
            Tam_Data_Dana_Tunai.setVisibility(View.GONE);
            Tam_Data_Kendaraan.setVisibility(View.VISIBLE);
        }
    }


    public void tampildata_all(){
        fk_get_id_order         = getIntent().getExtras().getString("id_order");
        fk_namalengkap          = getIntent().getExtras().getString("namalengkap");
        fk_tempatlahir          = getIntent().getExtras().getString("tempatlahir");
        fk_tgl_lahir            = getIntent().getExtras().getString("tgl_lahir");
        fk_alamat               = getIntent().getExtras().getString("alamat");
        fk_rt                   = getIntent().getExtras().getString("rt");
        fk_rw                   = getIntent().getExtras().getString("rw");
        fk_nama_kelurahan       = getIntent().getExtras().getString("nama_kelurahan");
        fk_nama_kecamatan       = getIntent().getExtras().getString("nama_kecamatan");
        fk_nama_kota            = getIntent().getExtras().getString("nama_kota");
        fk_nama_provinsi        = getIntent().getExtras().getString("nama_provinsi");
        fk_zipcode              = getIntent().getExtras().getString("zipcode");
        fk_kategori_kendaraan   = getIntent().getExtras().getString("kategori_kendaraan");
        fk_status_kendaraan     = getIntent().getExtras().getString("status_kendaraan");
        fk_merk_kendaraan       = getIntent().getExtras().getString("merk_kendaraan");
        fk_model_kendaraan      = getIntent().getExtras().getString("model_kendaraan");
        fk_type_kendaraan       = getIntent().getExtras().getString("type_kendaraan");
        fk_tahun                = getIntent().getExtras().getString("tahun");
        fk_warna                = getIntent().getExtras().getString("warna");
        fk_transmisi            = getIntent().getExtras().getString("transmisi");
        fk_plat_nomor           = getIntent().getExtras().getString("plat_nomor");
        fk_km_kendaraan         = getIntent().getExtras().getString("km_kendaraan");
        fk_bahan_bakar          = getIntent().getExtras().getString("bahan_bakar");
        fk_harga                = getIntent().getExtras().getString("harga");
        fk_status_survey        = getIntent().getExtras().getString("status_survey");
        fk_telp                 = getIntent().getExtras().getString("telp");
        fk_jenis_kredit         = getIntent().getExtras().getString("jenis_kredit");
        fk_jml_pinjaman         = getIntent().getExtras().getString("jml_pinjaman");
        fk_tenor                = getIntent().getExtras().getString("tenor");
        fk_latitude             = getIntent().getExtras().getString("latitude");
        fk_longitude            = getIntent().getExtras().getString("longitude");
        fk_tanggal_lahir        = getIntent().getExtras().getString("tanggal_lahir");
        fk_bulan_lahir          = getIntent().getExtras().getString("bulan_lahir");
        fk_tahun_lahir          = getIntent().getExtras().getString("tahun_lahir");

        if(fk_kategori_kendaraan.equals("Mobil")){
            Tam_type_gambar_kendaraan.setImageDrawable(getResources().getDrawable(R.drawable.icon_type_mobil));
        }else if(fk_kategori_kendaraan.equals("Motor")){
            Tam_type_gambar_kendaraan.setImageDrawable(getResources().getDrawable(R.drawable.icon_type_motor));
        }

        Tam_Namalengkap.setText(fk_namalengkap);
        Tam_Tempat_lahir.setText(fk_tempatlahir);
        //     Tam_Tanggal_lahir.setText(tgl_lahir);
        Tam_Alamat.setText(fk_alamat);
        Tam_Rt.setText(fk_rt);
        Tam_Rw.setText(fk_rw);
        Tam_Kelurahan.setText(fk_nama_kelurahan);
        Tam_Kecamatan.setText(fk_nama_kecamatan);
        Tam_Tanggal_lahir.setText(fk_tanggal_lahir);
        Tam_Bulan_lahir.setText(fk_bulan_lahir);
        Tam_Tahun_lahir.setText(fk_tahun_lahir);
        //     Tam_Nama_kota.setText(nama_kota);
        Tam_Nama_provinsi.setText(fk_nama_provinsi);
        Tam_Zipcode.setText(fk_zipcode);
        Tam_Telp.setText(fk_telp);
        //       Tam_Kategori_kendaraan.setText(kategori_kendaraan);
        Tam_Status_kendaraan.setText(fk_status_kendaraan);
        Tam_Merk_kendaraan.setText(fk_merk_kendaraan);
        //      Tam_Model_kendaraan.setText(model_kendaraan);
        Tam_Type_kendaraan.setText(fk_type_kendaraan);
        //        Tam_Tahun.setText(tahun_kendaraan);
        Tam_Warna.setText(fk_warna);
        Tam_Transmisi.setText(fk_transmisi);
        Tam_Plat_nomor.setText(fk_plat_nomor);
        //         Tam_Km_kendaraan.setText(km_kendaraan);
        Tam_Bahan_bakar.setText(fk_bahan_bakar);
        Tam_Harga.setText(fk_harga);
        Tam_Jml_pinjaman.setText(fk_jml_pinjaman);
        Tam_Tenor.setText(fk_tenor);
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