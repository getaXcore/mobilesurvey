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
import android.widget.Toast;

import java.util.ArrayList;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;


public class InputFullFragmentDelapan extends Fragment  {
    private ImageView Img_check;
    private int status_lengkap;
    private EditText Jumlah_tanggungan,Jumlah_anak;
    private String get_id_order,get_id_surveyor;
    private DatabaseManager dm;
    private Button b_simpan;

    public static String C_Jumlah_tanggungan,C_Jumlah_anak;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input_full_fragment_delapan, container, false);

        get_id_order = getArguments().getString("id_order");
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_surveyor = dm.ambilSemuaBaris();
        ArrayList<Object> baris_surveyor = data_surveyor.get(0);
        get_id_surveyor = baris_surveyor.get(3).toString();

        Jumlah_tanggungan   = (EditText) view.findViewById(R.id.etx_jumlah_tanggungan);
        Jumlah_anak         = (EditText) view.findViewById(R.id.etx_jumlah_anak);
        Img_check           = (ImageView) view.findViewById(R.id.img_check);
        b_simpan            = (Button) view.findViewById(R.id.bt_simpan);

        hasil_data();
        b_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Jumlah_tanggungan = Jumlah_tanggungan.getText().toString();
                C_Jumlah_anak = Jumlah_anak.getText().toString();

                /*final String C_Jumlah_tanggungan = Jumlah_tanggungan.getText().toString();
                final String C_Jumlah_anak = Jumlah_anak.getText().toString();*/

                ArrayList<ArrayList<Object>> data = dm.ambilBarisSurvey(get_id_order);//
                if (data.size() < 1) {
                    dm.addRowSurvey8(get_id_surveyor,get_id_order,C_Jumlah_tanggungan,C_Jumlah_anak);
                    Toast.makeText(hsContext, "Simpan Sementara",
                            Toast.LENGTH_LONG).show();
                } else {
                    dm.updateBarisSurvey8(get_id_order,C_Jumlah_tanggungan,C_Jumlah_anak);
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
        ArrayList<ArrayList<Object>> t_data = dm.ambilBarisSurvey(get_id_order);
        if (t_data.size() < 1) {
            Img_check.setImageResource(R.drawable.dont_check);
        } else {
            ArrayList<Object> baris = t_data.get(0);
            String t_jumlah_tanggungan  = ""+baris.get(117);
            String t_jumlah_anak        = ""+baris.get(118);

            if(t_jumlah_tanggungan.equals("null")){
                Jumlah_tanggungan.setText("");
            }else{
                Jumlah_tanggungan.setText(t_jumlah_tanggungan);
            }

            if(t_jumlah_anak.equals("null")){
                Jumlah_anak.setText("");
            }else{
                Jumlah_anak.setText(t_jumlah_anak);
            }

            if(t_jumlah_tanggungan.equals("null") && t_jumlah_anak.equals("null")){
                status_lengkap = status_lengkap+1;
            }

            if(status_lengkap>0){
                Img_check.setImageResource(R.drawable.dont_check);
            }else {
                Img_check.setImageResource(R.drawable.checked);
                ArrayList<ArrayList<Object>> data_tab8 = dm.ambilBarisTab("TAB 8",get_id_order);
                if (data_tab8.size() < 1) {
                    dm.addRowTab("Tanggungan","TAB 8", get_id_order);
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