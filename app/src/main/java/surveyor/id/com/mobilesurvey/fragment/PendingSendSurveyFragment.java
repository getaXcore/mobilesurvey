package surveyor.id.com.mobilesurvey.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import surveyor.id.com.mobilesurvey.ApproveActivity;
import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.adapter.CustomListSendPendingAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.LanjutSurvey;
import surveyor.id.com.mobilesurvey.modal.SendPending;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;


public class PendingSendSurveyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    String get_username,get_namalengkap,get_id_surveyor;
    SwipeRefreshLayout swipe;
    ListView list;
    CustomListSendPendingAdapter adapter;
    List<SendPending> itemList = new ArrayList<>();
    private String tam_json;
    DatabaseManager dm;
    private Context hsContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_send_survey, container, false);
        dm = new DatabaseManager(hsContext);
        ArrayList<ArrayList<Object>> data_user = dm.ambilSemuaBaris();
        if(data_user.size()>0){
            ArrayList<Object> baris = data_user.get(0);
            get_username    = baris.get(0).toString();
            get_namalengkap = baris.get(2).toString();
            get_id_surveyor = baris.get(3).toString();
        }


        list = (ListView) view.findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        adapter = new CustomListSendPendingAdapter((Activity) hsContext, itemList);
        list.setAdapter(adapter);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callList();
                       }
                   }
        );
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tampil_hal(position);
            }
        });
        return view;
    }

    public void tampil_hal(int position){
        ArrayList<ArrayList<Object>> data_tam = dm.ambilBarisJson("Inputan");
        if (data_tam.size() > 0) {
            try {
                ArrayList<Object> baris = data_tam.get(0);
                tam_json = baris.get(0).toString();

                JSONArray jObj = new JSONArray(tam_json);
                JSONObject obj = jObj.getJSONObject(position);
                String id_order = obj.getString("id_order");
                Activity act = (Activity) hsContext;
                act.finish();
                Intent ab = new Intent(hsContext, ApproveActivity.class);
                Bundle detail = new Bundle();
                detail.putString("id_order", id_order);
                ab.putExtras(detail);
                startActivity(ab);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void callList() {
        itemList.clear();
        swipe.setRefreshing(true);

        ArrayList<ArrayList<Object>> data10 = dm.ambilBarisJson("Inputan");
        if (data10.size() > 0) {
            try {
                for (int i = 0; i < data10.size(); i++) {
                    ArrayList<Object> baris = data10.get(i);
                    String getmjson = baris.get(0).toString();
                    String Get_id_order = baris.get(1).toString();

                    String[] dl_getmjson = getmjson.split("#,#");
                    String nama = dl_getmjson[0];
                    String alamat = dl_getmjson[1];


                    SendPending j = new SendPending();
                    j.setIdOrder(Get_id_order);
                    j.setName(nama);
                    j.setAddressHome(alamat);

                    itemList.add(j);


                    /*if(!getmjson.equals("")){
                        JSONArray br = new JSONArray(getmjson);
                        JSONObject obj = br.getJSONObject(0);


                        SendPending j = new SendPending();
                        j.setIdOrder(Get_id_order);
                        j.setName(obj.getString("nama"));
                        j.setAddressHome(obj.getString("address_home"));

                        itemList.add(j);
                    }else{
                        String[] dl_Get_id_order = Get_id_order.split("#,#");
                        String nama = dl_Get_id_order[0];
                        String alamat = dl_Get_id_order[1];


                        SendPending j = new SendPending();
                        j.setIdOrder(Get_id_order);
                        j.setName(nama);
                        j.setAddressHome(alamat);

                        itemList.add(j);
                    }*/

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onRefresh() {
        callList();
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

