package surveyor.id.com.mobilesurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import surveyor.id.com.mobilesurvey.adapter.CustomListChatAdapter;
import surveyor.id.com.mobilesurvey.modal.Chat;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;

/**
 * Created by Vigaz on 5/3/2017.
 */

public class ChatRoom extends AppCompatActivity {
    private Button btn_send_msg;
    public Runnable r;
    public int cf;
    public ListView list;
    public CustomListChatAdapter adapter;
    public List<Chat> itemList = new ArrayList<>();
    private EditText input_msg;
    private ListView chat_conversation;
    public Map<String,String> params;
    public String id_balas_message,judul,from_id_admin,to_id_admin,id_message,from_nama,
            balas_message,entry_date_balas,type_message,id_data_messagedetail,in_from_id_admin,
            in_to_id_admin,in_id_message,in_from_nama,in_balas_message,in_entry_date_balas,
            in_type_message,in_id_data_messagedetail,pesan;
    public DatabaseManager dm;
    
    private static final String TAG = ChatRoom.class.getSimpleName();
    Timer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Chat Admin");
        toolbar.setSubtitle(judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = new DatabaseManager(this);
        btn_send_msg        = (Button)findViewById(R.id.kirim);
        input_msg           = (EditText)findViewById(R.id.chat_text);
        chat_conversation   = (ListView)findViewById(R.id.tampil_chat);
        
        adapter = new CustomListChatAdapter(this, itemList);
        chat_conversation.setAdapter(adapter);
        
        judul       = getIntent().getExtras().get("judul").toString();
        id_message  = getIntent().getExtras().get("id_message").toString();
        
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<ArrayList<Object>> datachatcek66 = dm.ambilBarisChatdetailcheckpending();
                        if (datachatcek66.size() < 1) {

                        }else{
                            displayData();
                        }
                        callListVolley2();
                    }
                });

            }
        }, 0, 30000);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesan = input_msg.getText().toString().trim();
                id_message = getIntent().getExtras().get("id_message").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                dm.addRowChatdetailinput(id_message, to_id_admin, from_id_admin, pesan, from_nama,
                        currentDateandTime);
                ArrayList<ArrayList<Object>> data61 = dm.ambilBarisChatdetail(id_message);
                int g = data61.size();
                int j = g-1;
                ArrayList<Object> baris = data61.get(j);
                id_message = baris.get(1).toString();
                to_id_admin = baris.get(2).toString();
                from_id_admin = baris.get(3).toString();
                from_nama = baris.get(4).toString();
                balas_message = baris.get(5).toString();
                entry_date_balas = baris.get(6).toString();
                type_message = baris.get(7).toString();
                id_data_messagedetail = baris.get(9).toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, 
                        setter.URL_KIRIM, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    String code = jObj.getString("code");
                                    if (code.equals("200")) {
                                        String data = jObj.getString("data");
                                        JSONArray arrayData = new JSONArray(data);
                                        if (arrayData.length() > 0) {
                                            for(int i=0; i<arrayData.length(); i++){
                                                JSONObject obj = arrayData.getJSONObject(i);
                                                String id_balas_message = obj.getString("id_balas_message");
                                                dm.updateBarisChatdetail(id_data_messagedetail,id_balas_message);
                                                Toast.makeText(ChatRoom.this, "ambil semua baris:" +
                                                        id_balas_message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(ChatRoom.this,
                                                "Gagal Mengirim Pesan, Sambungan Tidak Tersedia",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChatRoom.this,
                                        "Gagal Mengirim Pesan, Sambungan Tidak Tersedia", Toast.
                                                LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        //   map.put("pesan", pesan);
                        map.put("from_id_admin", from_id_admin);
                        map.put("to_id_admin", to_id_admin);
                        map.put("id_message", id_message);
                        map.put("from_nama", from_nama);
                        map.put("balas_message", balas_message);
                        map.put("entry_date_balas", entry_date_balas);
                        map.put("type_message", type_message);
                        map.put("tk", setter.APK_CODE);
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(ChatRoom.this);
                requestQueue.add(stringRequest);
                input_msg.setText(null);
                itemList.clear();
                ArrayList<ArrayList<Object>> data42 = dm.ambilBarisChatdetail(id_message);
                for (int i = 0; i < data42.size(); i++) {
                    ArrayList<Object> baris42 = data42.get(i);
                    Chat jj = new Chat();
                    jj.setIdBalasMessage(baris42.get(0).toString());
                    jj.setIdMessage(baris42.get(1).toString());
                    jj.setToIdAdmin(baris42.get(2).toString());
                    jj.setFromIdAdmin(baris42.get(3).toString());
                    jj.setFromNama(baris42.get(4).toString());
                    jj.setBalasMessage(baris42.get(5).toString());
                    jj.setEntryDateBalas(baris42.get(6).toString());
                    jj.setTypeMessage(baris42.get(7).toString());
                    itemList.add(jj);
                }

                chat_conversation.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                chat_conversation.setStackFromBottom(true);
                adapter.notifyDataSetChanged();
            }
        });
        cf=1;
    }

    public void displayData(){
        ArrayList<ArrayList<Object>> datachatcek6 = dm.ambilBarisChatdetailcheckpending();
        if (datachatcek6.size() < 1) {

        }else {
            ArrayList<Object> barischatcek6 = datachatcek6.get(0);
            in_id_message = barischatcek6.get(1).toString();
            in_to_id_admin = barischatcek6.get(2).toString();
            in_from_id_admin = barischatcek6.get(3).toString();
            in_from_nama = barischatcek6.get(4).toString();
            in_balas_message = barischatcek6.get(5).toString();
            in_entry_date_balas = barischatcek6.get(6).toString();
            in_type_message = barischatcek6.get(7).toString();
            in_id_data_messagedetail = barischatcek6.get(9).toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.URL_KIRIM,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String code = jObj.getString("code");
                                if (code.equals("200")) {
                                    String data = jObj.getString("data");
                                    JSONArray arrayData = new JSONArray(data);
                                    if (arrayData.length() > 0) {
                                        for(int i=0; i<arrayData.length(); i++){
                                            JSONObject obj = arrayData.getJSONObject(i);
                                            String id_balas_message = obj.getString("id_balas_message");
                                            dm.updateBarisChatdetail(in_id_data_messagedetail,id_balas_message);
                                            Toast.makeText(ChatRoom.this, "ambil semua baris:" +
                                                    id_balas_message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(ChatRoom.this,
                                            "Gagal Mengirim Pesan, Sambungan Tidak Tersedia",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ChatRoom.this,
                                    "Gagal Mengirim Pesan, Sambungan Tidak Tersedia",
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    //   map.put("pesan", pesan);
                    map.put("from_id_admin", in_from_id_admin);
                    map.put("to_id_admin", in_to_id_admin);
                    map.put("id_message", in_id_message);
                    map.put("from_nama", in_from_nama);
                    map.put("balas_message", in_balas_message);
                    map.put("entry_date_balas", in_entry_date_balas);
                    map.put("type_message", in_type_message);
                    map.put("tk", setter.APK_CODE);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(ChatRoom.this);
            requestQueue.add(stringRequest);
            adapter.notifyDataSetChanged();
        }
    }

    public void callListVolley2(){
        ArrayList<ArrayList<Object>> data6 = dm.ambilBarisChatdetail(id_message);
        if(data6.size() < 1){

        }else{
            itemList.clear();
            adapter.notifyDataSetChanged();
            for (int i = 0; i < data6.size(); i++) {
                ArrayList<Object> baris = data6.get(i);
                Chat j = new Chat();
                j.setIdBalasMessage(baris.get(0).toString());
                j.setIdMessage(baris.get(1).toString());
                j.setToIdAdmin(baris.get(2).toString());
                j.setFromIdAdmin(baris.get(3).toString());
                j.setFromNama(baris.get(4).toString());
                j.setBalasMessage(baris.get(5).toString());
                j.setEntryDateBalas(baris.get(6).toString());
                j.setTypeMessage(baris.get(7).toString());
                itemList.add(j);
            }
        }
        id_message = getIntent().getExtras().get("id_message").toString();
        StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_chatdetail,
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
                                    for(int i=0; i<arrayData.length(); i++){
                                        JSONObject hData = arrayData.getJSONObject(i);
                                        id_balas_message    = hData.getString("id_balas_message");
                                        id_message          = hData.getString("id_message");
                                        to_id_admin         = hData.getString("to_id_admin");
                                        from_id_admin       = hData.getString("from_id_admin");
                                        from_nama           = hData.getString("from_nama");
                                        balas_message       = hData.getString("balas_message");
                                        entry_date_balas    = hData.getString("entry_date_balas");
                                        type_message        = hData.getString("type_message");

                                        ArrayList<ArrayList<Object>> data61 = dm.ambilBarisChatdetailcheck(id_balas_message);
                                        if (data61.size() < 1) {
                                            dm.addRowChatdetail(id_balas_message, id_message, to_id_admin, from_id_admin, from_nama, balas_message, entry_date_balas, type_message);
                                            finish();
                                            startActivity(getIntent());
                                        }
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

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_message", id_message);
                map.put("tk", setter.APK_CODE);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ChatRoom.this);
        requestQueue.add(jArr);
        //    callListVolley2();
        itemList.clear();
        adapter.notifyDataSetChanged();
        ArrayList<ArrayList<Object>> data67 = dm.ambilBarisChatdetail(id_message);
        for (int i = 0; i < data67.size(); i++) {
            ArrayList<Object> baris67 = data67.get(i);
            Chat je = new Chat();
            je.setIdBalasMessage(baris67.get(0).toString());
            je.setIdMessage(baris67.get(1).toString());
            je.setToIdAdmin(baris67.get(2).toString());
            je.setFromIdAdmin(baris67.get(3).toString());
            je.setFromNama(baris67.get(4).toString());
            je.setBalasMessage(baris67.get(5).toString());
            je.setEntryDateBalas(baris67.get(6).toString());
            je.setTypeMessage(baris67.get(7).toString());
            itemList.add(je);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<ArrayList<Object>> datachatcek66 = dm.ambilBarisChatdetailcheckpending();
        if (datachatcek66.size() < 1) {

        }else{
            displayData();
        }
        //  new GetContent().execute();
        //  finish();
        //    startActivity(getIntent());
        // itemList.clear();
        callListVolley2();
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

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
