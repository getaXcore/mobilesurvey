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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;


public class InputFullFragmentSembilan extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private EditText Omzet_or_penghasilan_gross,Penghasilan_nett_or_take_home_pay,
            Penghasilan_pasangan,Penghasilan_tambahan,Pengeluaran_or_kebutuhan_hidup,
            Total_cicilan_leasing_lain,Balance_terakhir_di_rekening_tabungan,
            Rata_rata_mutasi_in_3_bulan_terakhir,Rata_rata_mutasi_out_3_bulan_terakhir;
    private String get_id_order,get_id_surveyor;
    private DatabaseManager dm;
    private Button b_simpan;
    private RelativeLayout box_penghasilan_pasangan;


    public static String C_Omzet_or_penghasilan_gross,C_Penghasilan_nett_or_take_home_pay,
            C_Penghasilan_pasangan,C_Penghasilan_tambahan,C_Pengeluaran_or_kebutuhan_hidup,
            C_Total_cicilan_leasing_lain,C_Balance_terakhir_di_rekening_tabungan,
            C_Rata_rata_mutasi_in_3_bulan_terakhir,C_Rata_rata_mutasi_out_3_bulan_terakhir;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_sembilan, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        box_penghasilan_pasangan                = (RelativeLayout)view.findViewById(R.id.box_penghasilan_pasangan);
        Omzet_or_penghasilan_gross              = (EditText) view.findViewById(R.id.etx_omzet_or_penghasilan_gross);
        Penghasilan_nett_or_take_home_pay       = (EditText) view.findViewById(R.id.etx_penghasilan_nett_or_take_home_pay);
        Penghasilan_pasangan                    = (EditText) view.findViewById(R.id.etx_penghasilan_pasangan);
        Penghasilan_tambahan                    = (EditText) view.findViewById(R.id.etx_penghasilan_tambahan);
        Pengeluaran_or_kebutuhan_hidup          = (EditText) view.findViewById(R.id.etx_pengeluaran_or_kebutuhan_hidup);
        Total_cicilan_leasing_lain              = (EditText) view.findViewById(R.id.etx_total_cicilan_leasing_lain);
        Balance_terakhir_di_rekening_tabungan   = (EditText) view.findViewById(R.id.etx_balance_terakhir_di_rekening_tabungan);
        Rata_rata_mutasi_in_3_bulan_terakhir    = (EditText) view.findViewById(R.id.etx_rata_rata_mutasi_in_3_bulan_terakhir);
        Rata_rata_mutasi_out_3_bulan_terakhir   = (EditText) view.findViewById(R.id.etx_rata_rata_mutasi_out_3_bulan_terakhir);
        Img_check                               = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                                = (Button) view.findViewById(R.id.bt_simpan);

        setCurrencyCalculate(Omzet_or_penghasilan_gross);
        setCurrencyCalculate(Penghasilan_nett_or_take_home_pay);
        setCurrencyCalculate(Penghasilan_pasangan);
        setCurrencyCalculate(Penghasilan_tambahan);
        setCurrencyCalculate(Pengeluaran_or_kebutuhan_hidup);
        setCurrencyCalculate(Total_cicilan_leasing_lain);
        setCurrencyCalculate(Balance_terakhir_di_rekening_tabungan);
        setCurrencyCalculate(Rata_rata_mutasi_in_3_bulan_terakhir);
        setCurrencyCalculate(Rata_rata_mutasi_out_3_bulan_terakhir);

        hasil_data();

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Omzet_or_penghasilan_gross               = Omzet_or_penghasilan_gross.getText().toString();
                C_Penghasilan_nett_or_take_home_pay        = Penghasilan_nett_or_take_home_pay.getText().toString();
                C_Penghasilan_pasangan                     = Penghasilan_pasangan.getText().toString();
                C_Penghasilan_tambahan                     = Penghasilan_tambahan.getText().toString();
                C_Pengeluaran_or_kebutuhan_hidup           = Pengeluaran_or_kebutuhan_hidup.getText().toString();
                C_Total_cicilan_leasing_lain               = Total_cicilan_leasing_lain.getText().toString();
                C_Balance_terakhir_di_rekening_tabungan    = Balance_terakhir_di_rekening_tabungan.getText().toString();
                C_Rata_rata_mutasi_in_3_bulan_terakhir     = Rata_rata_mutasi_in_3_bulan_terakhir.getText().toString();
                C_Rata_rata_mutasi_out_3_bulan_terakhir    = Rata_rata_mutasi_out_3_bulan_terakhir.getText().toString();

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey9(get_id_surveyor,get_id_order,C_Omzet_or_penghasilan_gross,
                            C_Penghasilan_nett_or_take_home_pay,C_Penghasilan_pasangan,
                            C_Penghasilan_tambahan,C_Pengeluaran_or_kebutuhan_hidup,
                            C_Total_cicilan_leasing_lain,C_Balance_terakhir_di_rekening_tabungan,
                            C_Rata_rata_mutasi_in_3_bulan_terakhir,
                            C_Rata_rata_mutasi_out_3_bulan_terakhir);
                    Toast.makeText(hsContext, "Simpan Sementara", Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey9(get_id_order,C_Omzet_or_penghasilan_gross,
                            C_Penghasilan_nett_or_take_home_pay,C_Penghasilan_pasangan,
                            C_Penghasilan_tambahan,C_Pengeluaran_or_kebutuhan_hidup,
                            C_Total_cicilan_leasing_lain,C_Balance_terakhir_di_rekening_tabungan,
                            C_Rata_rata_mutasi_in_3_bulan_terakhir,
                            C_Rata_rata_mutasi_out_3_bulan_terakhir);
                    Toast.makeText(hsContext, "Update Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                }
                hasil_data();
            }

        });
        return view;
    }

    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_omzet_or_penghasilan_gross             = ""+baris.get(119);
            String t_penghasilan_nett_or_take_home_pay      = ""+baris.get(120);
            String t_penghasilan_pasangan                   = ""+baris.get(121);
            String t_penghasilan_tambahan                   = ""+baris.get(122);
            String t_pengeluaran_or_kebutuhan_hidup         = ""+baris.get(123);
            String t_total_cicilan_leasing_lain             = ""+baris.get(124);
            String t_balance_terakhir_di_rekening_tabungan  = ""+baris.get(125);
            String t_rata_rata_mutasi_in_3_bulan_terakhir   = ""+baris.get(126);
            String t_rata_rata_mutasi_out_3_bulan_terakhir  = ""+baris.get(127);

            if(t_omzet_or_penghasilan_gross.equals("null")){
                status_lengkap = status_lengkap+1;
                Omzet_or_penghasilan_gross.setText("");
            }else{
                Omzet_or_penghasilan_gross.setText(t_omzet_or_penghasilan_gross);
            }

            if(t_penghasilan_nett_or_take_home_pay.equals("null")){
                status_lengkap = status_lengkap+1;
                Penghasilan_nett_or_take_home_pay.setText("");
            }else{
                Penghasilan_nett_or_take_home_pay.setText(t_penghasilan_nett_or_take_home_pay);
            }

            if(t_penghasilan_pasangan.equals("null")){
               // status_lengkap = status_lengkap+1;
                Penghasilan_pasangan.setText("");
            }else{
                Penghasilan_pasangan.setText(t_penghasilan_pasangan);
            }

            if(t_penghasilan_tambahan.equals("null")){
                status_lengkap = status_lengkap+1;
                Penghasilan_tambahan.setText("");
            }else{
                Penghasilan_tambahan.setText(t_penghasilan_tambahan);
            }

            if(t_pengeluaran_or_kebutuhan_hidup.equals("null")){
                status_lengkap = status_lengkap+1;
                Pengeluaran_or_kebutuhan_hidup.setText("");
            }else{
                Pengeluaran_or_kebutuhan_hidup.setText(t_pengeluaran_or_kebutuhan_hidup);
            }

            if(t_total_cicilan_leasing_lain.equals("null")){
                Total_cicilan_leasing_lain.setText("");
            }else{
                Total_cicilan_leasing_lain.setText(t_total_cicilan_leasing_lain);
            }

            if(t_balance_terakhir_di_rekening_tabungan.equals("null")){
                Balance_terakhir_di_rekening_tabungan.setText("");
            }else{
                Balance_terakhir_di_rekening_tabungan.setText(t_balance_terakhir_di_rekening_tabungan);
            }

            if(t_rata_rata_mutasi_in_3_bulan_terakhir.equals("null")){
                Rata_rata_mutasi_in_3_bulan_terakhir.setText("");
            }else{
                Rata_rata_mutasi_in_3_bulan_terakhir.setText(t_rata_rata_mutasi_in_3_bulan_terakhir);
            }

            if(t_rata_rata_mutasi_out_3_bulan_terakhir.equals("null")){
                Rata_rata_mutasi_out_3_bulan_terakhir.setText("");
            }else{
                Rata_rata_mutasi_out_3_bulan_terakhir.setText(t_rata_rata_mutasi_out_3_bulan_terakhir);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab9 = dm.ambilBarisTab("TAB 9",get_id_order);
                if (data_tab9.size() < 1) {
                    dm.addRowTab("Pendapatan/Kapasitas","TAB 9", get_id_order);
                }
            }
            Log.i("jumlah data 1 : ", ""+status_lengkap);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisStatusPernikahan(get_id_order);//
        if (t_data.size() < 1) {

        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_marital_status = "" + baris.get(1);
            if (t_marital_status.equals("Married")) {
                box_penghasilan_pasangan.setVisibility(View.VISIBLE);
            }else{
                box_penghasilan_pasangan.setVisibility(View.GONE);
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