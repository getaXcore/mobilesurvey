package surveyor.id.com.mobilesurvey.adapter;

/**
 * Created by Vigaz on 9/25/2017.
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

import surveyor.id.com.mobilesurvey.modal.Jarak;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Jarak> jarakItems;
    ImageLoader imageLoader;

    public CustomListAdapter(Activity activity, List<Jarak> jarakItems) {
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
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);

        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView bulan = (TextView) convertView.findViewById(R.id.bulan);
        TextView tahun = (TextView) convertView.findViewById(R.id.tahun);
        TextView surveyaddress = (TextView) convertView.findViewById(R.id.surveyaddress);
        TextView kelurahan = (TextView) convertView.findViewById(R.id.kelurahan);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView jarak = (TextView) convertView.findViewById(R.id.jarak);

        Jarak j = jarakItems.get(position);
        //     thumbNail.setImageUrl(j.getGambar(), imageLoader);
        tanggal.setText(j.getTanggal());
        bulan.setText(j.getBulan());
        tahun.setText(j.getTahun());
        surveyaddress.setText(j.getSurveyaddress());
        nama.setText(j.getNama());
        String h_jarak = j.getJarak();
        if(h_jarak.equals("Tidak Ada Maps")){
            jarak.setText(j.getJarak());
        }else {
            jarak.setText(j.getJarak()+" Km");
        }

        return convertView;
    }

}