package surveyor.id.com.mobilesurvey.adapter;

/**
 * Created by Vigaz on 9/17/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import surveyor.id.com.mobilesurvey.modal.Hitung;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListAchievementAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Hitung> hitungItems;
    ImageLoader imageLoader;

    public CustomListAchievementAdapter(Activity activity, List<Hitung> hitungItems) {
        this.activity = activity;
        this.hitungItems = hitungItems;
    }

    @Override
    public int getCount() {
        return hitungItems.size();
    }

    @Override
    public Object getItem(int location) {
        return hitungItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_achievement, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);
        /*
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView bulan = (TextView) convertView.findViewById(R.id.bulan);
        TextView tahun = (TextView) convertView.findViewById(R.id.tahun);
        */

        TextView title_achievement = (TextView) convertView.findViewById(R.id.nama);
        TextView description_achievement = (TextView) convertView.findViewById(R.id.surveyaddress);
        TextView point_achievement = (TextView) convertView.findViewById(R.id.kelurahan);
        ImageView gambar_achievement = (ImageView) convertView.findViewById(R.id.gambar_achievement);

        //tambah ->
        //       TextView jarak = (TextView) convertView.findViewById(R.id.jarak);
        // <- tambah

        Hitung j = hitungItems.get(position);

        //     thumbNail.setImageUrl(j.getGambar(), imageLoader);

        /*
        tanggal.setText(j.getTanggal());
        bulan.setText(j.getBulan());
        tahun.setText(j.getTahun());
      */

        int c_number_goals = Integer.parseInt(j.getNumberGoals());
        int c_total_survey = Integer.parseInt(j.getTotalsurvey());

        //  int c_total_survey = 15;
        if(c_total_survey >= c_number_goals){
            title_achievement.setTextColor(Color.parseColor("#4BC1E5"));
            description_achievement.setTextColor(Color.parseColor("#4BC1E5"));
            point_achievement.setTextColor(Color.parseColor("#4BC1E5"));
            gambar_achievement.setImageResource(R.drawable.achievement_tercapai);
        }else{
            title_achievement.setTextColor(Color.parseColor("#D0CFCE"));
            description_achievement.setTextColor(Color.parseColor("#D0CFCE"));
            point_achievement.setTextColor(Color.parseColor("#D0CFCE"));
            gambar_achievement.setImageResource(R.drawable.achievement_belum_tercapai);
        }
        title_achievement.setText(j.getTitleAchievement());
        description_achievement.setText(j.getDescriptionAchievement());
        point_achievement.setText(""+j.getPointAchievement()+" xp");
        //   jarak.setText(j.getJarak()+" Km");
        return convertView;
    }

}