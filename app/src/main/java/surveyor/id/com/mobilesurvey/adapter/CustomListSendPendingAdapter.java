package surveyor.id.com.mobilesurvey.adapter;

/**
 * Created by Vigaz on 11/3/2017.
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

import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.SendPending;
import surveyor.id.com.mobilesurvey.util.AppController;

public class CustomListSendPendingAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SendPending> sendpendingItems;
    ImageLoader imageLoader;

    public CustomListSendPendingAdapter(Activity activity, List<SendPending> sendpendingItems) {
        this.activity = activity;
        this.sendpendingItems = sendpendingItems;
    }

    @Override
    public int getCount() {
        return sendpendingItems.size();
    }

    @Override
    public Object getItem(int location) {
        return sendpendingItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_send_pending, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //  NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.gambar);

        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        SendPending s = sendpendingItems.get(position);

        nama.setText(s.getName());
        alamat.setText(s.getAddressHome());

        return convertView;
    }

}