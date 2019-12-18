package surveyor.id.com.mobilesurvey.adapter;

/**
 * Created by Vigaz on 9/27/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import surveyor.id.com.mobilesurvey.modal.JarakHistory;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListHistoryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<JarakHistory> jarakItems;
    ImageLoader imageLoader;

    public CustomListHistoryAdapter(Activity activity, List<JarakHistory> jarakItems) {
        this.activity = activity;
        this.jarakItems = jarakItems;
    }

    @Override
    public int getCount() {
        return jarakItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jarakItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_history, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView bulan = (TextView) convertView.findViewById(R.id.bulan);
        TextView tahun = (TextView) convertView.findViewById(R.id.tahun);
        TextView surveyaddress = (TextView) convertView.findViewById(R.id.surveyaddress);
        TextView kelurahan = (TextView) convertView.findViewById(R.id.kelurahan);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        JarakHistory j = jarakItems.get(position);
        //     thumbNail.setImageUrl(j.getGambar(), imageLoader);
        tanggal.setText(j.getTanggal());
        bulan.setText(j.getBulan());
        tahun.setText(j.getTahun());
        surveyaddress.setText(j.getSurveyaddress());
        kelurahan.setText(j.getKelurahan());
        nama.setText(j.getNama());
        //jarak.setText(j.getJarak()+" Km");

        /*String cek_status_survey = j.getStatusSurvey();
        if(cek_status_survey.equals("2")){
            statussurvey.setText("Reject");
        }else if(cek_status_survey.equals("1")){
            statussurvey.setText("Approve");
        }else{
            statussurvey.setText("belum survey");
        }*/

        return convertView;
    }

}