package surveyor.id.com.mobilesurvey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;


public class InputFullFragmentSepuluh extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private EditText Collectabilitas_sid_or_slik_tertinggi,Overdue_tertinggi,
            Baki_debet_or_outstanding_hutang,Nama_finance_company,
            Alasan_menunggak_khusus_lebih_dari_coll_2;
    private RadioGroup radioGroup_pernah_kredit_di_tempat_lain;
    private Button b_simpan;
    private RadioButton radioButton;
    private View view;
    private String get_id_order,get_id_surveyor;
    private DatabaseManager dm;

    public static String C_Collectabilitas_sid_or_slik_tertinggi,C_pernah_kredit_di_tempat_lain,
            C_Overdue_tertinggi,C_Baki_debet_or_outstanding_hutang,C_Nama_finance_company,
            C_Alasan_menunggak_khusus_lebih_dari_coll_2;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_input_full_fragment_sepuluh, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        Collectabilitas_sid_or_slik_tertinggi       = (EditText) view.findViewById(R.id.etx_collectabilitas_sid_or_slik_tertinggi);
        radioGroup_pernah_kredit_di_tempat_lain     = (RadioGroup) view.findViewById(R.id.radioGroup_pernah_kredit_di_tempat_lain);
        Overdue_tertinggi                           = (EditText) view.findViewById(R.id.etx_overdue_tertinggi);
        Baki_debet_or_outstanding_hutang            = (EditText) view.findViewById(R.id.etx_baki_debet_or_outstanding_hutang);
        Nama_finance_company                        = (EditText) view.findViewById(R.id.etx_nama_finance_company);
        Alasan_menunggak_khusus_lebih_dari_coll_2   = (EditText) view.findViewById(R.id.etx_alasan_menunggak_khusus_lebih_dari_coll_2);
        b_simpan                                    = (Button) view.findViewById(R.id.bt_simpan);

        Img_check = (ImageView) view.findViewById(R.id.img_check);
        hasil_data();

        setCurrencyCalculate(Collectabilitas_sid_or_slik_tertinggi);
        setCurrencyCalculate(Baki_debet_or_outstanding_hutang);

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Collectabilitas_sid_or_slik_tertinggi        = Collectabilitas_sid_or_slik_tertinggi.getText().toString();
                int selectedId = radioGroup_pernah_kredit_di_tempat_lain.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);
                C_pernah_kredit_di_tempat_lain                 = ""+radioButton.getText();
                C_Overdue_tertinggi                            = Overdue_tertinggi.getText().toString();
                C_Baki_debet_or_outstanding_hutang             = Baki_debet_or_outstanding_hutang.getText().toString();
                C_Nama_finance_company                         = Nama_finance_company.getText().toString();
                C_Alasan_menunggak_khusus_lebih_dari_coll_2    = Alasan_menunggak_khusus_lebih_dari_coll_2.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey10(get_id_surveyor,get_id_order,
                            C_Collectabilitas_sid_or_slik_tertinggi,C_pernah_kredit_di_tempat_lain,
                            C_Overdue_tertinggi,C_Baki_debet_or_outstanding_hutang,
                            C_Nama_finance_company,C_Alasan_menunggak_khusus_lebih_dari_coll_2);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey10(get_id_order,C_Collectabilitas_sid_or_slik_tertinggi,
                            C_pernah_kredit_di_tempat_lain,C_Overdue_tertinggi,
                            C_Baki_debet_or_outstanding_hutang,C_Nama_finance_company,
                            C_Alasan_menunggak_khusus_lebih_dari_coll_2);
                    Toast.makeText(hsContext, "Update Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                }
                hasil_data();
            }

        });


        return view;
    }


    private void setCurrencyCalculate(final EditText edt) {
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
                    String replaceable = String.format("[Rp,.\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency().getSymbol(local));
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

                    String replace = String.format("[\\s]", NumberFormat.getCurrencyInstance().
                            getCurrency().getSymbol(local));
                    String clean = formatted.replaceAll(replace, "");

                    current = formatted;
                    edt.setText(clean);
                    edt.setSelection(clean.length());
                    edt.addTextChangedListener(this);
                }
            }
        });
    }


    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);//
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_collectabilitas_sid_or_slik_tertinggi      = ""+baris.get(128);
            String t_pernah_kredit_di_tempat_lain               = ""+baris.get(129);
            String t_overdue_tertinggi                          = ""+baris.get(130);
            String t_baki_debet_or_outstanding_hutang           = ""+baris.get(131);
            String t_nama_finance_company                       = ""+baris.get(132);
            String t_alasan_menunggak_khusus_lebih_dari_coll_2  = ""+baris.get(133);

            if(t_collectabilitas_sid_or_slik_tertinggi.equals("null")){
                Collectabilitas_sid_or_slik_tertinggi.setText("");
            }else{
                status_lengkap = status_lengkap+1;
                Collectabilitas_sid_or_slik_tertinggi.setText(t_collectabilitas_sid_or_slik_tertinggi);
            }

            RadioButton R_ya = (RadioButton) view.findViewById(R.id.pernah_kredit_di_tempat_lain_ya);
            RadioButton R_tidak = (RadioButton) view.findViewById(R.id.pernah_kredit_di_tempat_lain_tidak);
            if(t_pernah_kredit_di_tempat_lain.equals("Ya")){
                R_ya.setChecked(true);
                R_tidak.setChecked(false);
            }else{
                R_ya.setChecked(false);
                R_tidak.setChecked(true);
            }

            if(t_overdue_tertinggi.equals("null")){
                Overdue_tertinggi.setText("");
            }else{
                status_lengkap = status_lengkap+1;
                Overdue_tertinggi.setText(t_overdue_tertinggi);
            }


            if(t_baki_debet_or_outstanding_hutang.equals("null")){
                Baki_debet_or_outstanding_hutang.setText("");
            }else{
                status_lengkap = status_lengkap+1;
                Baki_debet_or_outstanding_hutang.setText(t_baki_debet_or_outstanding_hutang);
            }

            if(t_nama_finance_company.equals("null")){
                Nama_finance_company.setText("");
            }else{
                status_lengkap = status_lengkap+1;
                Nama_finance_company.setText(t_nama_finance_company);
            }


            if(t_alasan_menunggak_khusus_lebih_dari_coll_2.equals("null")){
                Alasan_menunggak_khusus_lebih_dari_coll_2.setText("");
            }else{
                status_lengkap = status_lengkap+1;
                Alasan_menunggak_khusus_lebih_dari_coll_2.setText(t_alasan_menunggak_khusus_lebih_dari_coll_2);
            }


            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.checked);

                ArrayList<ArrayList<Object>> data_tab10 = dm.ambilBarisTab("TAB 10",get_id_order);
                if (data_tab10.size() < 1) {
                    dm.addRowTab("Karakter Bayar","TAB 10", get_id_order);
                }

            }else {
                Img_check.setImageResource(R.drawable.dont_check);
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
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
