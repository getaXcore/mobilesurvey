package surveyor.id.com.mobilesurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import surveyor.id.com.mobilesurvey.fragment.InputFullFragmentSatu;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;

public class DaftarChatActivity extends AppCompatActivity implements  SwipeRefreshLayout.
        OnRefreshListener {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private ArrayList<String> list_of_rooms_from_id_admin = new ArrayList<>();
    private ArrayList<String> list_of_rooms_subject = new ArrayList<>();
    private ArrayList<String> list_of_rooms_id_message = new ArrayList<>();
    String get_username,get_id_surveyor;
    private DatabaseManager dm;
    
    private static final String TAG = DaftarChatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_chat);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Daftar Chat");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = new DatabaseManager(this);

        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();
        ArrayList<Object> baris = data6.get(0);
        get_username = baris.get(0).toString();
        get_id_surveyor = baris.get(3).toString();


        listView = (ListView) findViewById(R.id.room_list);
        callListVolley();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent ab = new Intent(DaftarChatActivity.this, ChatRoom.class);
                Bundle detail = new Bundle();
                detail.putString("id_message", list_of_rooms_id_message.get(position));
                detail.putString("judul", list_of_rooms.get(position));
                detail.putString("username", get_username);
                ab.putExtras(detail);
                startActivity(ab);
            }
        });
    }

    private void callListVolley() {
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_list_chat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            list_of_rooms.clear();
                            JSONObject jObj = new JSONObject(response);
                            String code = jObj.getString("code");
                            if (code.equals("200")) {
                                String data = jObj.getString("data");
                                JSONArray arrayData = new JSONArray(data);
                                if (arrayData.length() > 0) {
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        String nama = obj.getString("from_nama")+" - "+obj.getString("subject");
                                        String from_id_admin = obj.getString("from_id");
                                        String subject = obj.getString("subject");
                                        String id_message = obj.getString("id_message");
                                        list_of_rooms.add(nama);
                                        list_of_rooms_from_id_admin.add(from_id_admin);
                                        list_of_rooms_subject.add(subject);
                                        list_of_rooms_id_message.add(id_message);
                                        arrayAdapter = new ArrayAdapter<String>(DaftarChatActivity.this,
                                                android.R.layout.simple_list_item_1,list_of_rooms);
                                        listView.setAdapter(arrayAdapter);
                                    }
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
                        Toast.makeText(getBaseContext(), "Tidak Terhubung dengan Server",
                                Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tk", setter.APK_CODE);
                map.put("id_surveyor", get_id_surveyor);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jArr);
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
        callListVolley();

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