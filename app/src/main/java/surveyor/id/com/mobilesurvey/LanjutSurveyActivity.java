package surveyor.id.com.mobilesurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import surveyor.id.com.mobilesurvey.adapter.CustomListLanjutSurveyAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.LanjutSurvey;

public class LanjutSurveyActivity extends AppCompatActivity implements SwipeRefreshLayout.
        OnRefreshListener {
    String get_username,cek_id_order;
    SwipeRefreshLayout swipe;
    ListView list;
    CustomListLanjutSurveyAdapter adapter;
    List<LanjutSurvey> itemList = new ArrayList<>();
    DatabaseManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanjut_survey);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Lanjut Survey");
        setSupportActionBar(toolbar);

        dm = new DatabaseManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        get_username = getIntent().getExtras().getString("username");
        list = (ListView) findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        adapter = new CustomListLanjutSurveyAdapter(this, itemList);
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
        finish();
        Intent ab = new Intent(LanjutSurveyActivity.this, InputDataFullActivity.class);
        Bundle detail = new Bundle();
        LanjutSurvey j = itemList.get(position);
        detail.putString("id_order", ""+j.getIdOrder());
        detail.putString("name", ""+j.getNamaLengkap());
        detail.putString("address_home", ""+j.getAlamat());
        detail.putString("identity_type", ""+j.getIdentityType());
        detail.putString("identity_no", ""+j.getIdentityNo());
        detail.putString("telephone", ""+j.getTelephone());
        detail.putString("sex", ""+j.getSex());
        detail.putString("handphone_1", ""+j.getHandphone1());

        ab.putExtras(detail);
        startActivity(ab);
    }


    private void callList() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        ArrayList<ArrayList<Object>> data_input = dm.ambilBarisSurveyAll();
        if (data_input.size() > 0) {
            try {
                for (int cek_input = 0; cek_input < data_input.size(); cek_input++) {
                    ArrayList<Object> baris_input = data_input.get(cek_input);
                    String id_order_input = baris_input.get(0).toString();

                    ArrayList<ArrayList<Object>> data6 = dm.ambilBarisJson("List All");
                    if (data6.size() > 0) {
                        ArrayList<Object> baris = data6.get(0);
                        String tam_json_all = baris.get(0).toString();

                        JSONObject jObj = new JSONObject(tam_json_all);
                        String code = jObj.getString("code");
                        if (code.equals("200")) {
                            String data = jObj.getString("data");
                            JSONArray arrayData = new JSONArray(data);
                            if (arrayData.length() > 0) {
                                for(int i=0; i<arrayData.length(); i++){
                                    JSONObject obj_li = arrayData.getJSONObject(i);
                                    cek_id_order = obj_li.getString("id_order");
                                    if (cek_id_order.equals(id_order_input)) {
                                        String statusSurvey = obj_li.getString("status_survey");
                                        if((!statusSurvey.equals("1")) && (!statusSurvey.equals("16")) && (!statusSurvey.equals("6"))){
                                            LanjutSurvey j = new LanjutSurvey();
                                            j.setIdOrder(obj_li.getString("id_order"));
                                            j.setNamaLengkap(obj_li.getString("name"));
                                            j.setAlamat(obj_li.getString("address_home"));
                                            j.setIdentityType(obj_li.getString("identity_type"));
                                            j.setTelephone(obj_li.getString("telephone"));
                                            j.setSex(obj_li.getString("sex"));
                                            j.setIdentityNo(obj_li.getString("identity_no"));
                                            j.setHandphone1(obj_li.getString("handphone_1"));

                                            j.setNamaKelurahan("");
                                            itemList.add(j);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            } catch (JSONException e) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}