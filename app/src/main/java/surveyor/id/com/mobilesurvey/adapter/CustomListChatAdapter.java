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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.Chat;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListChatAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Chat> chatItems;
    ImageLoader imageLoader;

    private int c_type_message;

    public CustomListChatAdapter(Activity activity, List<Chat> chatItems) {
        this.activity = activity;
        this.chatItems = chatItems;
    }

    @Override
    public int getCount() {
        return chatItems.size();
    }

    @Override
    public Object getItem(int location) {
        return chatItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_chat, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);
        /*
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView bulan = (TextView) convertView.findViewById(R.id.bulan);
        TextView tahun = (TextView) convertView.findViewById(R.id.tahun);
        */

        TextView title_achievement = (TextView) convertView.findViewById(R.id.nama);
        TextView chat_detail = (TextView) convertView.findViewById(R.id.chatdetail);
        TextView waktu = (TextView) convertView.findViewById(R.id.tanggal);
        RelativeLayout l_nama_dll = (RelativeLayout) convertView.findViewById(R.id.nama_dll);
        //tambah ->
        //       TextView jarak = (TextView) convertView.findViewById(R.id.jarak);
        // <- tambah
        Chat j = chatItems.get(position);
        //     thumbNail.setImageUrl(j.getGambar(), imageLoader);
        /*
        tanggal.setText(j.getTanggal());
        bulan.setText(j.getBulan());
        tahun.setText(j.getTahun());
      */
        c_type_message = Integer.parseInt(j.getTypeMessage());
        //    int c_total_survey = Integer.parseInt(j.getTotalsurvey());
        //  int c_total_survey = 15;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)l_nama_dll.getLayoutParams();
        if(c_type_message == 2){
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);
            params.addRule(RelativeLayout.LEFT_OF, R.id.nama_dll);

            l_nama_dll.setBackgroundResource(R.drawable.icon2);
            l_nama_dll.setLayoutParams(params);
            title_achievement.setTextColor(Color.parseColor("#4BC1E5"));
            chat_detail.setTextColor(Color.parseColor("#4BC1E5"));
            waktu.setTextColor(Color.parseColor("#D0CFCE"));
            title_achievement.setText("saya");
        }else{
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,1);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.nama_dll);

            l_nama_dll.setBackgroundResource(R.drawable.icon1);
            l_nama_dll.setLayoutParams(params);
            title_achievement.setTextColor(Color.parseColor("#000000"));
            chat_detail.setTextColor(Color.parseColor("#000000"));
            waktu.setTextColor(Color.parseColor("#D0CFCE"));
            title_achievement.setText(j.getFromNama());
        }

        chat_detail.setText(j.getBalasMessage());
        waktu.setText(j.getEntryDateBalas());
        //   jarak.setText(j.getJarak()+" Km");

        return convertView;
    }

}

