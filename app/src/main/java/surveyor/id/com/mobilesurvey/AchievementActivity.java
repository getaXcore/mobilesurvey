package surveyor.id.com.mobilesurvey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
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

import surveyor.id.com.mobilesurvey.adapter.CustomListAchievementAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.Hitung;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;

public class AchievementActivity extends AppCompatActivity {
    ListView list;
    CustomListAchievementAdapter adapter;
    List<Hitung> itemList = new ArrayList<>();
    private TextView s_isi_target,s_isi_perjalanan,s_nama_username,s_isi_perjalanan_pending,
            s_isi_perjalanan_cancel;
    private String t_target,t_s_tersurvey,t_s_cancel,t_s_pending,get_username,get_userid;
    private static final String TAG = AchievementActivity.class.getSimpleName();
    private DatabaseManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Achievement");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = new DatabaseManager(this);
        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();
        ArrayList<Object> baris = data6.get(0);
        get_username = baris.get(0).toString();
        get_userid = baris.get(3).toString();

        s_nama_username             = (TextView) findViewById(R.id.nama_username);
        s_isi_target                = (TextView) findViewById(R.id.isi_target_new);
        s_isi_perjalanan            = (TextView) findViewById(R.id.isi_perjalanan_tersurvey);
        s_isi_perjalanan_pending    = (TextView) findViewById(R.id.isi_perjalanan_pending);
        s_isi_perjalanan_cancel     = (TextView) findViewById(R.id.isi_perjalanan_cancel);
        list                        = (ListView) findViewById(R.id.list_achievement);

        adapter = new CustomListAchievementAdapter(this, itemList);
        list.setAdapter(adapter);
        s_nama_username.setText(get_username);
        hitungsurvey();
    }

    private void callListVolley(final String g_target, final String g_total_survey) {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        itemList.clear();
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        Hitung j = new Hitung();
                                        j.setTitleAchievement(obj.getString("title_achievement"));
                                        j.setDescriptionAchievement(obj.getString("description_achievement"));
                                        j.setPointAchievement(obj.getString("point_achievement"));
                                        j.setActiveAchievement(obj.getString("active_achievement"));
                                        j.setNumberGoals(obj.getString("number_goals"));
                                        j.setTarget(g_target);
                                        j.setTotalSurvey(g_total_survey);
                                        itemList.add(j);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dm.deleteJsonPilihAll("Achievement");
                        dm.addRowJsonPilih(String.valueOf(response),"Achievement");
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        itemList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AchievementActivity.this,"Gagal mengupdate data",Toast.LENGTH_LONG).show();
                        ArrayList<ArrayList<Object>> data_achievement = dm.ambilBarisJsonPilih("Achievement");
                        if (data_achievement.size() > 0) {
                            ArrayList<Object> baristp = data_achievement.get(0);
                            String getmjson = baristp.get(0).toString();
                            try {
                                JSONObject jObj = new JSONObject(getmjson);
                                String code = jObj.getString("code");
                                if (code.equals("200")) {
                                    String data = jObj.getString("data");
                                    JSONArray arrayData = new JSONArray(data);
                                    if (arrayData.length() > 0) {
                                        for(int i=0; i<arrayData.length(); i++){
                                            JSONObject obj = arrayData.getJSONObject(i);
                                            Hitung j = new Hitung();
                                            j.setTitleAchievement(obj.getString("title_achievement"));
                                            j.setDescriptionAchievement(obj.getString("description_achievement"));
                                            j.setPointAchievement(obj.getString("point_achievement"));
                                            j.setActiveAchievement(obj.getString("active_achievement"));
                                            j.setNumberGoals(obj.getString("number_goals"));
                                            j.setTarget(g_target);
                                            j.setTotalSurvey(g_total_survey);
                                            itemList.add(j);
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AchievementActivity.this);
        requestQueue.add(jArr);
    }

    private void hitungsurvey() {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_jumlah,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for (int i = 0; i < arrayData.length(); i++) {
                                        try {
                                            JSONObject obj = arrayData.getJSONObject(i);
                                            t_target = obj.getString("target");
                                            //  t_total_survey = obj.getString("total_survey");
                                            t_s_tersurvey = obj.getString("s_tersurvey");
                                            t_s_cancel = obj.getString("s_cancel");
                                            t_s_pending = obj.getString("s_pending");
                                            if(!t_target.equals("null")){
                                                s_isi_target.setText(t_target);
                                            }
                                            if(!t_s_tersurvey.equals("null")){
                                                s_isi_perjalanan.setText(t_s_tersurvey);
                                            }
                                            if(!t_s_pending.equals("null")){
                                                s_isi_perjalanan_pending.setText(t_s_pending);
                                            }
                                            if(!t_s_cancel.equals("null")){
                                                s_isi_perjalanan_cancel.setText(t_s_cancel);
                                            }
                                            callListVolley(t_target,t_s_tersurvey);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    dm.deleteJsonPilihAll("Cek Achievement");
                                    dm.addRowJsonPilih(String.valueOf(response),"Cek Achievement");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ArrayList<ArrayList<Object>> data_cek_achievement = dm.ambilBarisJsonPilih("Cek Achievement");
                        if (data_cek_achievement.size() < 1) {

                        } else {
                            ArrayList<Object> baristp = data_cek_achievement.get(0);
                            String getmjson = baristp.get(0).toString();
                            try {
                                JSONObject jObj = new JSONObject(getmjson);
                                String code = jObj.getString("code");
                                if (code.equals("200")) {
                                    String data = jObj.getString("data");
                                    JSONArray arrayData = new JSONArray(data);
                                    if (arrayData.length() > 0) {
                                        for(int i=0; i<arrayData.length(); i++){
                                            JSONObject obj = arrayData.getJSONObject(i);
                                            t_target = obj.getString("target");
                                            // t_total_survey = obj.getString("total_survey");
                                            t_s_tersurvey = obj.getString("s_tersurvey");
                                            t_s_cancel = obj.getString("s_cancel");
                                            t_s_pending = obj.getString("s_pending");
                                            if(!t_target.equals("null")){
                                                s_isi_target.setText(t_target);
                                            }
                                            if(!t_s_tersurvey.equals("null")){
                                                s_isi_perjalanan.setText(t_s_tersurvey);
                                            }
                                            if(!t_s_pending.equals("null")){
                                                s_isi_perjalanan_pending.setText(t_s_pending);
                                            }
                                            if(!t_s_cancel.equals("null")){
                                                s_isi_perjalanan_cancel.setText(t_s_cancel);
                                            }
                                            callListVolley(t_target,t_s_tersurvey);
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("id_surveyor", get_userid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AchievementActivity.this);
        requestQueue.add(jArr);
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
