package surveyor.id.com.mobilesurvey.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import surveyor.id.com.mobilesurvey.InputGaleriPhotoActivity;
import surveyor.id.com.mobilesurvey.InputPhotoAllActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.SignatureActivity;
import surveyor.id.com.mobilesurvey.SignatureSurveyorActivity;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;


public class InputFullFragmentSebelas extends Fragment  {
    private int status_lengkap;
    private ImageView photo_home1,photo_home2,photo_id,photo_kk,photo_pbb,photo_app,photo_salary,
            photo_tempat_usaha,photo_bpkb,photo_stnk,photo_mobil_tampak_depan,
            photo_mobil_tampak_samping_kanan,photo_mobil_tampak_samping_kiri,
            photo_mobil_tampak_belakang,photo_dashboard,photo_mesin,photo_nomor_rangka,
            photo_cmo_dan_kendaraan,photo_interior_rumah,photo_customer_tanda_tangan,
            photo_pasangan_tanda_tangan,img_cek_ttd,img_cek_ttd_surveyor,Img_check;
    private String Get_id_order;
    private DatabaseManager dm;
    public Bitmap bitmapop,bitmapop2,bitmapop3,bitmapop4,bitmapop5,bitmapop6,bitmapop7, bitmapop9,
            bitmapop10,bitmapop11,bitmapop12,bitmapop13,bitmapop14,bitmapop15,bitmapop16,bitmapop17,
            bitmapop18,bitmapop19,bitmapop20,bitmapop21,bitmap,bitmap_ttd_surveyor;
    public static Dialog dialog_photo;
    private LinearLayout box_photo_pasangan_tanda_tangan;
    private Context hsContext;
    private ImageView photoAktifitasUsaha,photoCmoBersamaRumahDebTampakDepan,
            photoStnkTampakBelakang,photoBpkbHalamanIdentitasKendaraan,
            photoKtpAsliPasanganCalonDebitur,photoKkSecaraUtuh,
            photoSertifikatTanahGirikAjbRekeningListrik;
    private ExifInterface exifInterface;
    private static final String TAG = InputFullFragmentSebelas.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_sebelas, container, false);

        dm = new DatabaseManager(hsContext);
        Get_id_order = getArguments().getString("id_order");

        photo_home1                         = (ImageView) view.findViewById(R.id.photo_home1);
        photo_home2                         = (ImageView) view.findViewById(R.id.photo_home2);
        photo_id                            = (ImageView) view.findViewById(R.id.photo_id);
        photo_kk                            = (ImageView) view.findViewById(R.id.photo_kk);
        photo_pbb                           = (ImageView) view.findViewById(R.id.photo_pbb);
        photo_app                           = (ImageView) view.findViewById(R.id.photo_app);
        photo_salary                        = (ImageView) view.findViewById(R.id.photo_salary);
        photo_tempat_usaha                  = (ImageView) view.findViewById(R.id.photo_tempat_usaha);
        photo_bpkb                          = (ImageView) view.findViewById(R.id.photo_bpkb);
        photo_stnk                          = (ImageView) view.findViewById(R.id.photo_stnk);
        photo_mobil_tampak_depan            = (ImageView) view.findViewById(R.id.photo_mobil_tampak_depan);
        photo_mobil_tampak_samping_kanan    = (ImageView) view.findViewById(R.id.photo_mobil_tampak_samping_kanan);
        photo_mobil_tampak_samping_kiri     = (ImageView) view.findViewById(R.id.photo_mobil_tampak_samping_kiri);
        photo_mobil_tampak_belakang         = (ImageView) view.findViewById(R.id.photo_mobil_tampak_belakang);
        photo_dashboard                     = (ImageView) view.findViewById(R.id.photo_dashboard);
        photo_mesin                         = (ImageView) view.findViewById(R.id.photo_mesin_mobil);
        photo_nomor_rangka                  = (ImageView) view.findViewById(R.id.photo_nomer_rangka);
        photo_cmo_dan_kendaraan             = (ImageView) view.findViewById(R.id.photo_cmo_dan_kendaraan);
        photo_interior_rumah                = (ImageView) view.findViewById(R.id.photo_interior_rumah);
        photo_customer_tanda_tangan         = (ImageView) view.findViewById(R.id.photo_customer_tanda_tangan);
        photo_pasangan_tanda_tangan         = (ImageView) view.findViewById(R.id.photo_pasangan_tanda_tangan);
        img_cek_ttd                         = (ImageView) view.findViewById(R.id.img_cek_ttd);
        img_cek_ttd_surveyor                = (ImageView) view.findViewById(R.id.img_cek_ttd_surveyor);
        box_photo_pasangan_tanda_tangan     = (LinearLayout) view.findViewById(R.id.box_photo_pasangan_tanda_tangan);
        Img_check                           = (ImageView) view.findViewById(R.id.img_check);

 // ----------baru 17 des 2018
        photoAktifitasUsaha                         = (ImageView) view.findViewById(R.id.photo_aktifitas_usaha);
        photoCmoBersamaRumahDebTampakDepan          = (ImageView) view.findViewById(R.id.photo_cmo_bersama_rumah_deb_tampak_depan);

        photoStnkTampakBelakang                     = (ImageView) view.findViewById(R.id.photo_stnk_tampak_belakang);
        photoBpkbHalamanIdentitasKendaraan          = (ImageView) view.findViewById(R.id.photo_bpkb_halaman_identitas_kendaraan);
        photoKtpAsliPasanganCalonDebitur            = (ImageView) view.findViewById(R.id.photo_ktp_asli_pasangan_calon_debitur);
        photoKkSecaraUtuh                           = (ImageView) view.findViewById(R.id.photo_kk_secara_utuh);
        photoSertifikatTanahGirikAjbRekeningListrik = (ImageView) view.findViewById(R.id.photo_sertifikat_tanah_girik_ajb_rekening_listrik);

 // ----------end baru 17 des 2018

        img_cek_ttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hs = new Intent(hsContext, SignatureActivity.class);
                Bundle keldata = new Bundle();
                keldata.putString("id_order", Get_id_order);
                hs.putExtras(keldata);
                startActivity(hs);
                //hasil_data();
            }
        });

        img_cek_ttd_surveyor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hs = new Intent(hsContext, SignatureSurveyorActivity.class);
                Bundle keldata = new Bundle();
                keldata.putString("id_order", Get_id_order);
                hs.putExtras(keldata);
                startActivity(hs);
            }
        });

        photo_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "home1");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photo_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("home2");
            }
        });

        photo_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "customer_id");
                    hs.putExtras(keldata);
                    startActivity(hs);

                }

            }
        });

        photo_kk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "customer_kk");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photo_pbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("customer_pbb");
            }
        });

        photo_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("customer_app");
            }
        });

        photo_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("customer_salary");
            }
        });

        photo_tempat_usaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "tempat_usaha");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }*/
                CustomDialogPhoto("tempat_usaha");
            }
        });

        photo_bpkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("bpkb");
            }
        });

        photo_stnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CustomDialogPhoto("stnk");
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "stnk");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photo_mobil_tampak_depan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("mobil_tampak_depan");
            }
        });

        photo_mobil_tampak_samping_kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("mobil_tampak_samping_kanan");
            }
        });

        photo_mobil_tampak_samping_kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("mobil_tampak_samping_kiri");
            }
        });

        photo_mobil_tampak_belakang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("mobil_tampak_belakang");
            }
        });

        photo_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("dashboard");
            }
        });

        photo_mesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("mesin");
            }
        });

        photo_nomor_rangka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("nomor_rangka");
            }
        });

        photo_cmo_dan_kendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("cmo_dan_kendaraan");
            }
        });

        photo_interior_rumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext,
                            InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "interior_rumah");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }*/
                CustomDialogPhoto("interior_rumah");
            }
        });

        photo_customer_tanda_tangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "customer_tanda_tangan");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photo_pasangan_tanda_tangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "pasangan_tanda_tangan");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }*/
                CustomDialogPhoto("pasangan_tanda_tangan");
            }
        });

// baru 17 des 2018
        photoAktifitasUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "aktifitas_usaha");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }

            }
        });

        photoCmoBersamaRumahDebTampakDepan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "cmo_bersama_rumah_deb_tampak_depan");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photoStnkTampakBelakang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("stnk_tampak_belakang");
            }
        });

        photoBpkbHalamanIdentitasKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("bpkb_halaman_identitas_kendaraan");
            }
        });

        photoKtpAsliPasanganCalonDebitur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", "ktp_asli_pasangan_calon_debitur");
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
            }
        });

        photoKkSecaraUtuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("kk_secara_utuh");
            }
        });

        photoSertifikatTanahGirikAjbRekeningListrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPhoto("sertifikat_tanah_girik_ajb_rekening_listrik");
            }
        });


// end baru 17 des 2018

        return view;
    }

    public void CustomDialogPhoto(final String nama_poto){
        dialog_photo = new Dialog(hsContext);
        dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_photo.setContentView(R.layout.dialogbox_photo);
        dialog_photo.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog_photo.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        RelativeLayout r_camera =(RelativeLayout) dialog_photo.findViewById(R.id.r_camera);
        RelativeLayout r_galeri =(RelativeLayout) dialog_photo.findViewById(R.id.r_galeri);

        r_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", nama_poto);
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
                dialog_photo.dismiss();
            }
        });
        r_galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationProviders = Settings.Secure.getString(hsContext.
                        getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                    Bundle keldata = new Bundle();
                    keldata.putString("id_order", Get_id_order);
                    keldata.putString("photo_name", nama_poto);
                    hs.putExtras(keldata);
                    startActivity(hs);
                }
                dialog_photo.dismiss();
            }
        });
        /*if(nama_poto.equals("home2")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "home2");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "home2");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("customer_pbb")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_pbb");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_pbb");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });

        }else if(nama_poto.equals("customer_app")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_app");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_app");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("customer_salary")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_salary");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext,
                                InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "customer_salary");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("bpkb")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "bpkb");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "bpkb");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if (nama_poto.equals("stnk")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "stnk");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "stnk");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("mobil_tampak_depan")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_depan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_depan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("mobil_tampak_samping_kanan")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_samping_kanan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_samping_kanan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("mobil_tampak_samping_kiri")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_samping_kiri");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_samping_kiri");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if (nama_poto.equals("mobil_tampak_belakang")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_belakang");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mobil_tampak_belakang");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("dashboard")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "dashboard");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "dashboard");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("mesin")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mesin");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "mesin");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("nomor_rangka")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "nomor_rangka");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "nomor_rangka");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
        }else if(nama_poto.equals("cmo_dan_kendaraan")){
            r_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputPhotoAllActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "cmo_dan_kendaraan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });
            r_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationProviders = Settings.Secure.getString(hsContext.
                            getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }else{
                        Intent hs = new Intent(hsContext, InputGaleriPhotoActivity.class);
                        Bundle keldata = new Bundle();
                        keldata.putString("id_order", Get_id_order);
                        keldata.putString("photo_name", "cmo_dan_kendaraan");
                        hs.putExtras(keldata);
                        startActivity(hs);
                    }
                    dialog_photo.dismiss();
                }
            });

        }*/
        dialog_photo.show();
    }
    /*Penambahan untuk testing compress*/
    public Bitmap getBitmapNew(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,out);
        byte[] byteArray = out.toByteArray();
        Bitmap bitmapopcompress = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        return bitmapopcompress;
    }

    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> data = dm.ambilBarisPhoto("signature",Get_id_order);//
        if (data.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data.get(0);
            String photo_link = baris.get(1).toString();
            bitmap = BitmapFactory.decodeFile(photo_link);
            img_cek_ttd.setImageBitmap(bitmap);
        }

        ArrayList<ArrayList<Object>> data_ttdsurveyor = dm.ambilBarisPhoto("signature surveyor",Get_id_order);//
        if (data_ttdsurveyor.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baristtdsurveyor = data_ttdsurveyor.get(0);
            String photo_link = baristtdsurveyor.get(1).toString();
            bitmap_ttd_surveyor = BitmapFactory.decodeFile(photo_link);
            img_cek_ttd_surveyor.setImageBitmap(bitmap_ttd_surveyor);
        }

        ArrayList<ArrayList<Object>> data_home1 = dm.ambilBarisPhoto("home1",Get_id_order);
        if (data_home1.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_home1.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte= Base64.decode(photo,Base64.DEFAULT);
                bitmapop = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_home1.setImageBitmap(bitmapop);
                photo_home1.setImageBitmap(getBitmapNew(bitmapop));//using bitmap compress


            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_home2 = dm.ambilBarisPhoto("home2",Get_id_order);
        if (data_home2.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_home2.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop2 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                /*Bitmap getBmpHome2 = ThumbnailUtils.extractThumbnail(bitmapop2, 100, 100);
                photo_home2.setImageBitmap(getBmpHome2);*/
                //photo_home2.setImageBitmap(bitmapop2);
                photo_home2.setImageBitmap(getBitmapNew(bitmapop2));//using bitmap compress

            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_id = dm.ambilBarisPhoto("customer_id",Get_id_order);
        if (data_id.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_id.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop3 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_id.setImageBitmap(bitmapop3);
                photo_id.setImageBitmap(getBitmapNew(bitmapop3));//using bitmap compress

            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_kk = dm.ambilBarisPhoto("customer_kk",Get_id_order);
        if (data_kk.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_kk.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop4 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_kk.setImageBitmap(bitmapop4);
                photo_kk.setImageBitmap(getBitmapNew(bitmapop4));//using bitmap compress

            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_pbb = dm.ambilBarisPhoto("customer_pbb",Get_id_order);
        if (data_pbb.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_pbb.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop5 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_pbb.setImageBitmap(bitmapop5);
                photo_pbb.setImageBitmap(getBitmapNew(bitmapop5));//using bitmap compress

            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_app = dm.ambilBarisPhoto("customer_app",Get_id_order);
        if (data_app.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_app.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop6 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_app.setImageBitmap(bitmapop6);
                photo_app.setImageBitmap(getBitmapNew(bitmapop6));//using bitmap compress

                /*Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd:hh:mm:ss");
                String tanggal = df.format(c);

               //Step 1 create file for storing image data on SDCard
                File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File pictureFileDir = new File(sdDir,"OMOSImages");

                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()){
                    Log.d(TAG,"Can't create directory to save image");
                    Toast.makeText(this.getContext(), "Can't create directory to save image", Toast.LENGTH_SHORT).show();
                }

                //Step 2 write image byte array to file
                String photoFile = "Picture_"+ tanggal +".jpg";
                String imageFilePath = pictureFileDir.getPath()+File.separator+photoFile;
                File pictureFile = new File(imageFilePath);

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(encodeByte);
                    fos.close();
                    Toast.makeText(this.getContext(),"New Image saved: "+photoFile,Toast.LENGTH_LONG).show();
                }catch (Exception error){
                    Log.d(TAG,"File "+photoFile+" not save "+error.getMessage());
                    Toast.makeText(this.getContext(),"Image could not be saved.",Toast.LENGTH_SHORT).show();
                }

                //Step 3 Get Exif Info from File path
                try {
                    exifInterface = new ExifInterface(imageFilePath);
                    String make = exifInterface.getAttribute(ExifInterface.TAG_MAKE);

                }catch (IOException e){
                    e.printStackTrace();
                }

                //check the value of "make" here
                exifInterface = new ExifInterface(imageFilePath);
                String dt = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Date & Time : "+dt);

                Log.i("Date&Time",stringBuilder.toString());*/
                /*exifInterface = new ExifInterface(imageFilePath);
                String datePhoto = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
                SimpleDateFormat convertDate = new SimpleDateFormat("MMM dd, yyyy");
                SimpleDateFormat convertTime = new SimpleDateFormat("hh:mm:ss aa");

                Date date1 = null, date2 = null;
                try {
                    date1 = simpleDateFormat.parse(datePhoto);
                    date2 = simpleDateFormat.parse(datePhoto);
                }catch (Exception e){
                    e.printStackTrace();
                }

                String dateFormat = convertDate.format(date1);
                String timeFormat = convertTime.format(date2);*/




            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_salary = dm.ambilBarisPhoto("customer_salary",Get_id_order);
        if (data_salary.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_salary.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop7 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_salary.setImageBitmap(bitmapop7);
                photo_salary.setImageBitmap(getBitmapNew(bitmapop7));//using bitmap compress

            }catch (Exception e){

            }
        }


        ArrayList<ArrayList<Object>> data_tempat_usaha = dm.ambilBarisPhoto("tempat_usaha",Get_id_order);
        if (data_tempat_usaha.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_tempat_usaha.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop9 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_tempat_usaha.setImageBitmap(bitmapop9);
                photo_tempat_usaha.setImageBitmap(getBitmapNew(bitmapop9));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_bpkb = dm.ambilBarisPhoto("bpkb",Get_id_order);
        if (data_bpkb.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_bpkb.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop10 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_bpkb.setImageBitmap(bitmapop10);
                photo_bpkb.setImageBitmap(getBitmapNew(bitmapop10));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_stnk = dm.ambilBarisPhoto("stnk",Get_id_order);
        if (data_stnk.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_stnk.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop11 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_stnk.setImageBitmap(bitmapop11);
                photo_stnk.setImageBitmap(getBitmapNew(bitmapop11));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_mobil_tampak_depan = dm.ambilBarisPhoto("mobil_tampak_depan",Get_id_order);
        if (data_mobil_tampak_depan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_mobil_tampak_depan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop12 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_mobil_tampak_depan.setImageBitmap(bitmapop12);
                photo_mobil_tampak_depan.setImageBitmap(getBitmapNew(bitmapop12));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_mobil_tampak_samping_kanan = dm.ambilBarisPhoto("mobil_tampak_samping_kanan",Get_id_order);
        if (data_mobil_tampak_samping_kanan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_mobil_tampak_samping_kanan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop13 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_mobil_tampak_samping_kanan.setImageBitmap(bitmapop13);
                photo_mobil_tampak_samping_kanan.setImageBitmap(getBitmapNew(bitmapop13));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_mobil_tampak_samping_kiri = dm.ambilBarisPhoto("mobil_tampak_samping_kiri",Get_id_order);
        if (data_mobil_tampak_samping_kiri.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_mobil_tampak_samping_kiri.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop14 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_mobil_tampak_samping_kiri.setImageBitmap(bitmapop14);
                photo_mobil_tampak_samping_kiri.setImageBitmap(getBitmapNew(bitmapop14));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_mobil_tampak_belakang = dm.ambilBarisPhoto("mobil_tampak_belakang",Get_id_order);
        if (data_mobil_tampak_belakang.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_mobil_tampak_belakang.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop15 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_mobil_tampak_belakang.setImageBitmap(bitmapop15);
                photo_mobil_tampak_belakang.setImageBitmap(getBitmapNew(bitmapop15));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_dashboard = dm.ambilBarisPhoto("dashboard",Get_id_order);
        if (data_dashboard.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_dashboard.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop16 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_dashboard.setImageBitmap(bitmapop16);
                photo_dashboard.setImageBitmap(getBitmapNew(bitmapop16));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_mesin = dm.ambilBarisPhoto("mesin",Get_id_order);
        if (data_mesin.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_mesin.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop17 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_mesin.setImageBitmap(bitmapop17);
                photo_mesin.setImageBitmap(getBitmapNew(bitmapop17));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_nomor_rangka = dm.ambilBarisPhoto("nomor_rangka",Get_id_order);
        if (data_nomor_rangka.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_nomor_rangka.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop18 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_nomor_rangka.setImageBitmap(bitmapop18);
                photo_nomor_rangka.setImageBitmap(getBitmapNew(bitmapop18));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_cmo_dan_kendaraan = dm.ambilBarisPhoto("cmo_dan_kendaraan",Get_id_order);
        if (data_cmo_dan_kendaraan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_cmo_dan_kendaraan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop19 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_cmo_dan_kendaraan.setImageBitmap(bitmapop19);
                photo_cmo_dan_kendaraan.setImageBitmap(getBitmapNew(bitmapop19));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_interior_rumah = dm.ambilBarisPhoto("interior_rumah",Get_id_order);
        if (data_interior_rumah.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_interior_rumah.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop20 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_interior_rumah.setImageBitmap(bitmapop20);
                photo_interior_rumah.setImageBitmap(getBitmapNew(bitmapop20));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_customer_tanda_tangan = dm.ambilBarisPhoto("customer_tanda_tangan",Get_id_order);
        if (data_customer_tanda_tangan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_customer_tanda_tangan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                bitmapop21 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photo_customer_tanda_tangan.setImageBitmap(bitmapop21);
                photo_customer_tanda_tangan.setImageBitmap(getBitmapNew(bitmapop21));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisStatusPernikahan(Get_id_order);//
        if (t_data.size() < 1) {

        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_marital_status = "" + baris.get(1);
            if (t_marital_status.equals("Married")) {
                ArrayList<ArrayList<Object>> data_pasangan_tanda_tangan = dm.ambilBarisPhoto("pasangan_tanda_tangan",Get_id_order);
                if (data_pasangan_tanda_tangan.size() < 1) {
                    status_lengkap = status_lengkap+1;
                }else {
                    ArrayList<Object> baris_pasangan_tanda_tangan = data_pasangan_tanda_tangan.get(0);
                    String photo = baris_pasangan_tanda_tangan.get(1).toString();
                    try {
                        byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                        bitmapop21 = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                        //photo_pasangan_tanda_tangan.setImageBitmap(bitmapop21);
                        photo_pasangan_tanda_tangan.setImageBitmap(getBitmapNew(bitmapop21));//using bitmap compress
                    }catch (Exception e){

                    }
                }
            }
        }

// baru 17 des 2018
        ArrayList<ArrayList<Object>> data_aktifitas_usaha = dm.ambilBarisPhoto("aktifitas_usaha",Get_id_order);
        if (data_aktifitas_usaha.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_aktifitas_usaha.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoAktifitasUsaha.setImageBitmap(bitmapop_all);
                photoAktifitasUsaha.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }


        ArrayList<ArrayList<Object>> data_cmo_bersama_rumah_deb_tampak_depan = dm.ambilBarisPhoto("cmo_bersama_rumah_deb_tampak_depan",Get_id_order);
        if (data_cmo_bersama_rumah_deb_tampak_depan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_cmo_bersama_rumah_deb_tampak_depan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoCmoBersamaRumahDebTampakDepan.setImageBitmap(bitmapop_all);
                photoCmoBersamaRumahDebTampakDepan.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_stnk_tampak_belakang = dm.ambilBarisPhoto("stnk_tampak_belakang",Get_id_order);
        if (data_stnk_tampak_belakang.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_stnk_tampak_belakang.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoStnkTampakBelakang.setImageBitmap(bitmapop_all);
                photoStnkTampakBelakang.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_bpkb_halaman_identitas_kendaraan = dm.ambilBarisPhoto("bpkb_halaman_identitas_kendaraan",Get_id_order);
        if (data_bpkb_halaman_identitas_kendaraan.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_bpkb_halaman_identitas_kendaraan.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoBpkbHalamanIdentitasKendaraan.setImageBitmap(bitmapop_all);
                photoBpkbHalamanIdentitasKendaraan.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }


        ArrayList<ArrayList<Object>> data_kk_secara_utuh = dm.ambilBarisPhoto("kk_secara_utuh",Get_id_order);
        if (data_kk_secara_utuh.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_kk_secara_utuh.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoKkSecaraUtuh.setImageBitmap(bitmapop_all);
                photoKkSecaraUtuh.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_sertifikat_tanah_girik_ajb_rekening_listrik = dm.ambilBarisPhoto("sertifikat_tanah_girik_ajb_rekening_listrik",Get_id_order);
        if (data_sertifikat_tanah_girik_ajb_rekening_listrik.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_sertifikat_tanah_girik_ajb_rekening_listrik.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoSertifikatTanahGirikAjbRekeningListrik.setImageBitmap(bitmapop_all);
                photoSertifikatTanahGirikAjbRekeningListrik.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }

        ArrayList<ArrayList<Object>> data_ktp_asli_pasangan_calon_debitur = dm.ambilBarisPhoto("ktp_asli_pasangan_calon_debitur",Get_id_order);
        if (data_ktp_asli_pasangan_calon_debitur.size() < 1) {
            status_lengkap = status_lengkap+1;
        }else {
            ArrayList<Object> baris = data_ktp_asli_pasangan_calon_debitur.get(0);
            String photo = baris.get(1).toString();
            try {
                byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
                Bitmap bitmapop_all = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                //photoKtpAsliPasanganCalonDebitur.setImageBitmap(bitmapop_all);
                photoKtpAsliPasanganCalonDebitur.setImageBitmap(getBitmapNew(bitmapop_all));//using bitmap compress
            }catch (Exception e){

            }
        }


  // End baru 17 des 2018





        if(status_lengkap>0){
            Img_check.setImageResource(R.drawable.dont_check);
        }else {
            Img_check.setImageResource(R.drawable.checked);

            ArrayList<ArrayList<Object>> data_tab11 = dm.ambilBarisTab("TAB 11",Get_id_order);
            if (data_tab11.size() < 1) {
                dm.addRowTab("Data Foto","TAB 11", Get_id_order);
            }

            Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.rLayout),"",Snackbar.LENGTH_LONG);
            View SnackBarView = snackbar.getView();
            SnackBarView.setBackgroundColor(Color.parseColor("#000000"));

            TextView textView = SnackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.parseColor("#FFFFFF"));

            snackbar.setText("Proses Kompres Foto Selesai");
            snackbar.show();
        }
        Log.i("jumlah data 1 : ", ""+status_lengkap);
    }

    @Override
    public void onResume() {
        super.onResume();
        hasil_data();

        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisStatusPernikahan(Get_id_order);//
        if (t_data.size() < 1) {

        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_marital_status = "" + baris.get(1);
            if (t_marital_status.equals("Married")) {
                box_photo_pasangan_tanda_tangan.setVisibility(View.VISIBLE);
            }else{
                box_photo_pasangan_tanda_tangan.setVisibility(View.GONE);
            }
        }
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
