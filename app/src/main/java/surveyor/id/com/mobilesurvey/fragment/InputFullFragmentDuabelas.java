package surveyor.id.com.mobilesurvey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;


public class InputFullFragmentDuabelas extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private String get_id_order,get_id_surveyor;
    private DatabaseManager dm;
    private EditText Alasan_or_point_penting_rekomendasi_anda;
    private RadioGroup radioGroup_apakah_direkomendasikan;
    private Button b_simpan;
    private View view;
    private RadioButton radioButton;

    public static String C_apakah_direkomendasikan,C_Alasan_or_point_penting_rekomendasi_anda;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_input_full_fragment_duabelas, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        radioGroup_apakah_direkomendasikan          = (RadioGroup) view.findViewById(R.id.radioGroup_apakah_direkomendasikan);
        Alasan_or_point_penting_rekomendasi_anda    = (EditText) view.findViewById(R.id.etx_alasan_or_point_penting_rekomendasi_anda);
        Img_check                                   = (ImageView) view.findViewById(R.id.img_check);
        b_simpan                                    = (Button) view.findViewById(R.id.bt_simpan);

        hasil_data();
        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup_apakah_direkomendasikan.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);
                C_apakah_direkomendasikan                  = ""+radioButton.getText();
                C_Alasan_or_point_penting_rekomendasi_anda = Alasan_or_point_penting_rekomendasi_anda.getText().toString();


                /*int selectedId = radioGroup_apakah_direkomendasikan.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);
                final String C_apakah_direkomendasikan                  = ""+radioButton.getText();
                final String C_Alasan_or_point_penting_rekomendasi_anda = Alasan_or_point_penting_rekomendasi_anda.getText().toString();*/

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey12(get_id_surveyor,get_id_order,C_apakah_direkomendasikan,
                            C_Alasan_or_point_penting_rekomendasi_anda);
                    Toast.makeText(hsContext, "Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey12(get_id_order,C_apakah_direkomendasikan,
                            C_Alasan_or_point_penting_rekomendasi_anda);
                    Toast.makeText(hsContext,
                            "Update Simpan Sementara", Toast.LENGTH_LONG).show();
                }
                hasil_data();
            }

        });
        return view;
    }


    public void hasil_data(){
        status_lengkap = 0;
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);//
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_apakah_direkomendasikan                    = ""+baris.get(134);
            String t_alasan_or_point_penting_rekomendasi_anda   = ""+baris.get(135);

            RadioButton R_ya    = (RadioButton) view.findViewById(R.id.apakah_direkomendasikan_ya);
            RadioButton R_tidak = (RadioButton) view.findViewById(R.id.apakah_direkomendasikan_tidak);
            if(t_apakah_direkomendasikan.equals("Ya")){
                R_ya.setChecked(true);
                R_tidak.setChecked(false);
            }else{
                R_ya.setChecked(false);
                R_tidak.setChecked(true);
            }

            if(t_alasan_or_point_penting_rekomendasi_anda.equals("null")){
                status_lengkap = status_lengkap+1;
                Alasan_or_point_penting_rekomendasi_anda.setText("");
            }else{
                Alasan_or_point_penting_rekomendasi_anda.setText(t_alasan_or_point_penting_rekomendasi_anda);
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);

                ArrayList<ArrayList<Object>> data_tab12 = dm.ambilBarisTab("TAB 12",get_id_order);
                if (data_tab12.size() < 1) {
                    dm.addRowTab("Pendapatan/Kapasitas","TAB 12", get_id_order);
                }
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
