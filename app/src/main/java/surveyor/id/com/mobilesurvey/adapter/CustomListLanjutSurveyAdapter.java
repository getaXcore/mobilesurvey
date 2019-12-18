package surveyor.id.com.mobilesurvey.adapter;

/**
 * Created by Vigaz on 9/19/2017.
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

import surveyor.id.com.mobilesurvey.modal.LanjutSurvey;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListLanjutSurveyAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<LanjutSurvey> lanjutsurveyItems;
    ImageLoader imageLoader;

    public CustomListLanjutSurveyAdapter(Activity activity, List<LanjutSurvey> jarakItems) {
        this.activity = activity;
        this.lanjutsurveyItems = jarakItems;
    }

    @Override
    public int getCount() {
        return lanjutsurveyItems.size();
    }

    @Override
    public Object getItem(int location) {
        return lanjutsurveyItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_lanjut_survey, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);

        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView surveyaddress = (TextView) convertView.findViewById(R.id.surveyaddress);
        TextView kelurahan = (TextView) convertView.findViewById(R.id.kelurahan);

        LanjutSurvey j = lanjutsurveyItems.get(position);

        nama.setText(j.getNamaLengkap());
        surveyaddress.setText(j.getAlamat());
        kelurahan.setText(j.getNamaKelurahan());

        return convertView;
    }

}
