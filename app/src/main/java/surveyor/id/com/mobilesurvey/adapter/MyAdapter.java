package surveyor.id.com.mobilesurvey.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.fragment.MapsFragmentDua;
import surveyor.id.com.mobilesurvey.fragment.MapsFragmentSatu;
import surveyor.id.com.mobilesurvey.fragment.MapsFragmentTiga;

public class MyAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"A","B","C"};
    int[] icons = new int[]{R.mipmap.icon_customer_ada,R.mipmap.icon_customer_tidak_ada2,
            R.mipmap.icon_customer_reject};
    private int heightIcon,heightIcon2;
    private String fk_id_order,fk_id_customer,fk_namalengkap_surveyor,fk_asuransi,fk_jenis_kredit,
            fk_order_code,fk_jml_pinjaman,fk_otr,fk_dp,fk_tenor,fk_kode_cabang,fk_jaminan_multiguna,
            fk_name,fk_identity_type,fk_identity_no,fk_address_home,fk_telephone,fk_sex,
            fk_handphone_1,fk_kategori_kendaraan,fk_merk_kendaraan,fk_model_kendaraan,
            fk_type_kendaraan,fk_tahun,fk_warna,fk_plat_nomor,fk_km_kendaraan,
            fk_bahan_bakar,fk_status_survey,fk_latitude_survey,fk_longitude_survey,
            fk_janji_survey;

    public MyAdapter(FragmentManager fm,String id_order, String id_customer, String namalengkap_surveyor,
              String asuransi, String jenis_kredit, String order_code, String jml_pinjaman,
              String otr, String dp, String tenor, String kode_cabang, String jaminan_multiguna,
              String name, String identity_type, String identity_no, String address_home,
              String telephone, String sex, String handphone_1, String kategori_kendaraan,
              String merk_kendaraan, String model_kendaraan, String type_kendaraan, String tahun,
              String warna, String plat_nomor, String km_kendaraan, String bahan_bakar,
              String status_survey, String latitude_survey, String longitude_survey,
              String janji_survey, Context c){
        super(fm);
        fk_id_order = id_order;
        fk_id_customer = id_customer;
        fk_namalengkap_surveyor = namalengkap_surveyor;
        fk_asuransi = asuransi;
        fk_jenis_kredit = jenis_kredit;
        fk_order_code = order_code;
        fk_jml_pinjaman = jml_pinjaman;
        fk_otr = otr;
        fk_dp = dp;
        fk_tenor = tenor;
        fk_kode_cabang = kode_cabang;
        fk_jaminan_multiguna = jaminan_multiguna;
        fk_name = name;
        fk_identity_type = identity_type;
        fk_identity_no = identity_no;
        fk_address_home = address_home;
        fk_telephone = telephone;
        fk_sex = sex;
        fk_handphone_1 = handphone_1;
        fk_kategori_kendaraan = kategori_kendaraan;
        fk_merk_kendaraan = merk_kendaraan;
        fk_model_kendaraan = model_kendaraan;
        fk_type_kendaraan = type_kendaraan;
        fk_tahun = tahun;
        fk_warna = warna;
        fk_plat_nomor = plat_nomor;
        fk_km_kendaraan = km_kendaraan;
        fk_bahan_bakar = bahan_bakar;
        fk_status_survey = status_survey;
        fk_latitude_survey = latitude_survey;
        fk_longitude_survey = longitude_survey;
        fk_janji_survey = janji_survey;

        mContext = c;
        double scale=c.getResources().getDisplayMetrics().density;
        heightIcon=(int)(40*scale+0.5f);
        heightIcon2=(int)(90*scale+0.5f);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        if (position == 0){
            frag = new MapsFragmentSatu();
        }else if (position == 1){
            frag = new MapsFragmentDua();
        }else if (position == 2){
            frag = new MapsFragmentTiga();
        }
        Bundle b = new Bundle();
        b.putInt("position", position);

        b.putString("id_order", fk_id_order);
        b.putString("id_customer", fk_id_customer);
        b.putString("namalengkap_surveyor", fk_namalengkap_surveyor);
        b.putString("asuransi", fk_asuransi);
        b.putString("jenis_kredit", fk_jenis_kredit);
        b.putString("order_code", fk_order_code);
        b.putString("jml_pinjaman", fk_jml_pinjaman);
        b.putString("otr", fk_otr);
        b.putString("dp", fk_dp);
        b.putString("tenor", fk_tenor);
        b.putString("kode_cabang", fk_kode_cabang);
        b.putString("jaminan_multiguna", fk_jaminan_multiguna);
        b.putString("name", fk_name);
        b.putString("identity_type", fk_identity_type);
        b.putString("identity_no", fk_identity_no);
        b.putString("address_home", fk_address_home);
        b.putString("telephone", fk_telephone);
        b.putString("sex", fk_sex);
        b.putString("handphone_1", fk_handphone_1);
        b.putString("kategori_kendaraan", fk_kategori_kendaraan);
        b.putString("merk_kendaraan", fk_merk_kendaraan);
        b.putString("model_kendaraan", fk_model_kendaraan);
        b.putString("type_kendaraan", fk_type_kendaraan);
        b.putString("tahun", fk_tahun);
        b.putString("warna", fk_warna);
        b.putString("plat_nomor", fk_plat_nomor);
        b.putString("km_kendaraan", fk_km_kendaraan);
        b.putString("bahan_bakar", fk_bahan_bakar);
        b.putString("status_survey", fk_status_survey);
        b.putString("latitude_survey", fk_latitude_survey);
        b.putString("longitude_survey", fk_longitude_survey);
        b.putString("janji_survey", fk_janji_survey);

        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable d = mContext.getResources().getDrawable(icons[position]);
        d.setBounds(0,0,heightIcon2,heightIcon);
        ImageSpan is = new ImageSpan(d);
        SpannableString sp = new SpannableString("ll");
        sp.setSpan(is,0,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (sp);
    }
}