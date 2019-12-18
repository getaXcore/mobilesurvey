package surveyor.id.com.mobilesurvey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import surveyor.id.com.mobilesurvey.adapter.CustomListHistoryAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.Jarak;
import surveyor.id.com.mobilesurvey.modal.JarakHistory;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.OtherUtil;

public class HistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    ListView list;
    CustomListHistoryAdapter adapter;
    List<JarakHistory> itemList = new ArrayList<>();
    private String get_username, tam_json, get_namalengkap, get_id_surveyor;
    private static final String TAG = HistoryActivity.class.getSimpleName();
    private DatabaseManager dm;
    private int resError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("History Tugas");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resError = 1;

        dm = new DatabaseManager(this);
        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();
        ArrayList<Object> baris = data6.get(0);
        get_username = baris.get(0).toString();
        get_namalengkap = baris.get(2).toString();
        get_id_surveyor = baris.get(3).toString();

        get_username = getIntent().getExtras().getString("username");
        list = (ListView) findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        adapter = new CustomListHistoryAdapter(this, itemList);
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
    }

    public void tampil_hal(int position){
        ArrayList<ArrayList<Object>> data6 = dm.ambilBarisJson("List All");
        if (data6.size() > 0) {
            try {
                ArrayList<Object> baris = data6.get(0);
                tam_json = baris.get(0).toString();
                JarakHistory j = itemList.get(position);
                String cek_id_order = j.getIdOrder();

                JSONObject jObj = new JSONObject(tam_json);
                String code = jObj.getString("code");
                if (code.equals("200")) {
                    String data = jObj.getString("data");
                    JSONArray arrayData = new JSONArray(data);
                    if (arrayData.length() > 0) {
                        for(int i=0; i<arrayData.length(); i++){
                            JSONObject obj = arrayData.getJSONObject(i);
                            String h_id_order = obj.getString("id_order");
                            if(cek_id_order.equals(h_id_order)){
                                String id_order = obj.getString("id_order");
                                finish();
                                Intent ab = new Intent(HistoryActivity.this, ApproveActivity.class);
                                Bundle detail = new Bundle();
                                detail.putString("id_order", id_order);
                                ab.putExtras(detail);
                                startActivity(ab);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void cekJson(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callList();
            }
        }, 2000);
    }


    public void callList() {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_json_all,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            itemList.clear();
                            adapter.notifyDataSetChanged();
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        String status_survey = obj.getString("status_survey");
                                        if(status_survey.equals("1")){
                                            JarakHistory j = new JarakHistory();
                                            j.setNama(obj.getString("name"));
                                            j.setTanggal(obj.getString("date_survey_tanggal"));
                                            j.setBulan(obj.getString("date_survey_bulan"));
                                            j.setTahun(obj.getString("date_survey_tahun"));
                                            j.setSurveyaddress(obj.getString("address_ktp"));
                                            j.setKelurahan(obj.getString("kab_or_kodya_ktp"));
                                            j.setStatusSurvey(obj.getString("status_survey"));
                                            j.setIdOrder(obj.getString("id_order"));
                                            j.setJarak("--");
                                            itemList.add(j);
                                        }
                                    }
                                }
                            }
                            dm.deleteTugasAll("List All");
                            dm.addRowTugas(String.valueOf(response), "List All");
                            adapter.notifyDataSetChanged();
                            swipe.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resError++;
                        if(resError > 3){
                            resError = 1;

                            Toast.makeText(HistoryActivity.this,"Gagal mengupdate data",Toast.LENGTH_LONG).show();
                            itemList.clear();
                            adapter.notifyDataSetChanged();
                            Log.i(TAG,"error "+error.getMessage());
                            ArrayList<ArrayList<Object>> data_list = dm.ambilBarisJson("List All");
                            if (data_list.size() > 0) {
                                try {
                                    ArrayList<Object> baris = data_list.get(0);
                                    tam_json = baris.get(0).toString();

                                    JSONObject jObj = new JSONObject(tam_json);
                                    String code = jObj.getString("code");
                                    if (code.equals("200")) {
                                        String data = jObj.getString("data");
                                        JSONArray arrayData = new JSONArray(data);
                                        if (arrayData.length() > 0) {
                                            for(int i=0; i<arrayData.length(); i++){
                                                JSONObject obj = arrayData.getJSONObject(i);
                                                String status_survey = obj.getString("status_survey");
                                                if(status_survey.equals("1")){
                                                    JarakHistory j = new JarakHistory();
                                                    j.setNama(obj.getString("name"));
                                                    j.setTanggal(obj.getString("date_survey_tanggal"));
                                                    j.setBulan(obj.getString("date_survey_bulan"));
                                                    j.setTahun(obj.getString("date_survey_tahun"));
                                                    j.setSurveyaddress(obj.getString("address_ktp"));
                                                    j.setKelurahan(obj.getString("kab_or_kodya_ktp"));
                                                    j.setStatusSurvey(obj.getString("status_survey"));
                                                    j.setIdOrder(obj.getString("id_order"));
                                                    j.setJarak("--");
                                                    itemList.add(j);
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                            swipe.setRefreshing(false);
                        }else {
                            cekJson();
                        }
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_surveyor", get_id_surveyor);
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
        requestQueue.add(jArr);
    }

    @Override
    public void onRefresh() {
        callList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}