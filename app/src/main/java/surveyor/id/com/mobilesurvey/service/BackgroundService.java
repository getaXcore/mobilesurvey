package surveyor.id.com.mobilesurvey.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

import surveyor.id.com.mobilesurvey.HomeActivity;
import surveyor.id.com.mobilesurvey.ListTugasActivity;
import surveyor.id.com.mobilesurvey.R;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.setter;
import surveyor.id.com.mobilesurvey.util.AppController;
import surveyor.id.com.mobilesurvey.util.NotificationClass;

/**
 * Created by fabio on 30/01/2016.
 */
public class BackgroundService extends Service {
    private DatabaseManager dm;

    String get_username,get_userid,t_id_order,t_nama,t_alamat;
    private static final String TAG = BackgroundService.class.getSimpleName();
    public int counter=0;
    Uri uri;
    public Context context = this;
    public Map<String, String> params;
    private String photo_nama, photo_bitmap, photo_id_order, photo_status, photo_latitude, photo_longitude;
    public String p_photo_nama, p_photo_bitmap, p_photo_latitude, p_photo_longitude, p_photo_id_order;
    String tag_json_obj = "json_obj_req";
    private Context mContext;
    private String notifSampaiId,notifSampaiIdSurveyor,notifSampaiIdOrder,notifSampaiStatusHasil,
            notifSampaiLatitude,notifSampaiLongitude,notifSampaiTk,notifSampaiTanggal;
    private String janjiSurveyId,janjiSurveyIdSurveyor,janjiSurveyIdOrder,janjiSurveyJanjiSurvey,
            janjiSurveyTk;
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "surveyor.id.com.mobilesurvey";
    private static final String CHANNEL_NAME = "MY Background Service";

    public BackgroundService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public BackgroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground();
        }else {
            startForeground(1,new Notification());
        }
        return super.onStartCommand(intent, flags, startId);

        //return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
       // Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        Intent broadcastIntent = new Intent("com.id.surveyor.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        dm = new DatabaseManager(this);
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 5000, 15000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
                cek_tugas();
                uploadphoto();
                cekNotifSampai();
                cekJanjiSurvey();
            }
        };
    }


    public void cek_tugas(){
        dm = new DatabaseManager(this);
        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris();
        if (data6.size() > 0) {
            ArrayList<Object> baris = data6.get(0);
            get_username    = baris.get(0).toString();
            get_userid      = baris.get(3).toString();

            StringRequest jArr = new StringRequest(Request.Method.POST, setter.url_cek_tugas,
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
                                            JSONObject obj = arrayData.getJSONObject(i);
                                            t_id_order = obj.getString("id_order");
                                            t_nama = obj.getString("name");
                                            t_alamat = obj.getString("address_home");

                                            ArrayList<ArrayList<Object>> data_notif = dm.ambilBarisNotifTugas(t_id_order);
                                            if (data_notif.size() < 1) {
                                                dm.addRowNotifTugas(t_id_order,t_nama,t_alamat);

                                                playNotificationSound();

                                                NotificationCompat.Builder mBuilder =
                                                        new NotificationCompat.Builder(context);
                                                //Create the intent that’ll fire when the user taps the notification//
                                                //Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse("https://www.androidauthority.com/"));
                                                Intent intent = new Intent(context, ListTugasActivity.class);
                                                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                                                mBuilder.setContentIntent(pendingIntent);

                                                mBuilder.setColor(getResources().getColor(R.color.biru));
                                                mBuilder.setSmallIcon(R.drawable.ic_stat_notif_data_new);
                                                // mBuilder.setLargeIcon(R.drawable.person_icon);
                                                mBuilder.setContentTitle("Tugas Baru ke "+t_nama);
                                                mBuilder.setContentText(t_alamat);
                                                NotificationManager mNotificationManager =
                                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                mNotificationManager.notify(i, mBuilder.build());
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
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
                    map.put("id_surveyor", get_userid);
                    map.put("tk", setter.APK_CODE);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jArr);
            requestQueue.stop();


            /*
            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("id_surveyor", get_userid);
            postParam.put("tk", setter.APK_CODE);
            JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.POST,
                    setter.url_cek_tugas, new JSONObject(postParam),
                    new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "Response: " + response.toString());

                    JSONObject jObjck = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            t_id_order = obj.getString("id_order");
                            t_nama = obj.getString("name");
                            t_alamat = obj.getString("address_home");

                            ArrayList<ArrayList<Object>> data_notif = dm.ambilBarisNotifTugas(t_id_order);
                            if (data_notif.size() < 1) {
                                dm.addRowNotifTugas(t_id_order,t_nama,t_alamat);

                                playNotificationSound();

                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(context);
                                //Create the intent that’ll fire when the user taps the notification//
                                //Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse("https://www.androidauthority.com/"));
                                Intent intent = new Intent(context, ListTugasActivity.class);
                                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                                mBuilder.setContentIntent(pendingIntent);

                                mBuilder.setColor(getResources().getColor(R.color.biru));
                                mBuilder.setSmallIcon(R.drawable.ic_stat_notif_data_new);
                                // mBuilder.setLargeIcon(R.drawable.person_icon);
                                mBuilder.setContentTitle("Tugas Baru ke "+t_nama);
                                mBuilder.setContentText(t_alamat);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                mNotificationManager.notify(0, mBuilder.build());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //  dm.deleteJsonPilihAll("Cek Achievement");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    //membuat parameters
                    HashMap<String, String> params_sandi = new HashMap<String, String>();
                    Log.d(TAG, "" + params_sandi);
                    return params_sandi;
                }

            };
            AppController.getInstance().addToRequestQueue(stringRequest);*/
        }

    }


    public void cekJanjiSurvey(){
        ArrayList<ArrayList<Object>> janjiSurvey = dm.ambilBarisJanjiSurvey();
        if (janjiSurvey.size() > 0) {
            ArrayList<Object> baris_janjiSurvey = janjiSurvey.get(0);
            janjiSurveyId           = "" + baris_janjiSurvey.get(0);
            janjiSurveyIdSurveyor   = "" + baris_janjiSurvey.get(1);
            janjiSurveyIdOrder      = "" + baris_janjiSurvey.get(2);
            janjiSurveyJanjiSurvey  = "" + baris_janjiSurvey.get(3);
            janjiSurveyTk           = "" + baris_janjiSurvey.get(4);


            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    setter.URL_JANJI_SURVEY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String code = jObj.getString("code");
                        if (code.equals("200")) {
                            dm.deleteJanjiSurvey(janjiSurveyId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id_surveyor", janjiSurveyIdSurveyor);
                    params.put("id_order", janjiSurveyIdOrder);
                    params.put("janji_survey", janjiSurveyJanjiSurvey);
                    params.put("tk", janjiSurveyTk);
                    Log.d(TAG, ""+params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        }
    }


    public void cekNotifSampai(){
        ArrayList<ArrayList<Object>> notifSampai = dm.ambilBarisNotifSampai();
        if (notifSampai.size() > 0) {
            ArrayList<Object> baris_notifSampai = notifSampai.get(0);
            notifSampaiId            = ""+baris_notifSampai.get(0);
            notifSampaiIdSurveyor    = ""+baris_notifSampai.get(1);
            notifSampaiIdOrder       = ""+baris_notifSampai.get(2);
            notifSampaiStatusHasil   = ""+baris_notifSampai.get(3);
            notifSampaiLatitude      = ""+baris_notifSampai.get(4);
            notifSampaiLongitude     = ""+baris_notifSampai.get(5);
            notifSampaiTk            = ""+baris_notifSampai.get(6);
            notifSampaiTanggal       = ""+baris_notifSampai.get(7);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    setter.UPLOAD_URL_NOTIF, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String code = jObj.getString("code");
                        if (code.equals("200")) {
                            dm.deleteNotifSampai(notifSampaiId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id_surveyor", notifSampaiIdSurveyor);
                    params.put("id_order", notifSampaiIdOrder);
                    params.put("status_hasil", notifSampaiStatusHasil);
                    params.put("latitude", notifSampaiLatitude);
                    params.put("longitude", notifSampaiLongitude);
                    params.put("tk", notifSampaiTk);
                    params.put("tanggal", notifSampaiTanggal);
                    Log.d(TAG, ""+params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        }
    }




    public void uploadphoto(){
        ArrayList<ArrayList<Object>> statusTerkirim = dm.ambilBarisStatusTerkirim();
        if (statusTerkirim.size() > 0) {
            ArrayList<Object> baris_statusTerkirim = statusTerkirim.get(0);
            String checkIdOrder  = ""+baris_statusTerkirim.get(1);


            ArrayList<ArrayList<Object>> dataphoto = dm.ambilBarisPhotoAll(checkIdOrder);
            if (dataphoto.size() > 0) {
                ArrayList<Object> baris = dataphoto.get(0);
                photo_nama      = ""+baris.get(0);
                photo_bitmap    = ""+baris.get(2);
                photo_latitude  = ""+baris.get(3);
                photo_longitude = ""+baris.get(4);
                photo_id_order  = ""+baris.get(5);
                photo_status    = ""+baris.get(6);

                if(photo_latitude == null){
                    photo_latitude = "";
                }
                if(photo_longitude == null){
                    photo_longitude = "";
                }

                if(photo_latitude.equals("null")){
                    photo_latitude = "";
                }
                if(photo_longitude.equals("null")){
                    photo_longitude = "";
                }
                if(photo_status.equals("1")){
                    simpan_photo(photo_nama,photo_bitmap,photo_latitude,photo_longitude,photo_id_order);
                }
            }else{
                statusKirim1(checkIdOrder);
            }
        }
    }


    private void statusKirim1(final String hasilIdOrder){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPDATE_STATUS_SURVEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        JSONObject jObjck = null;
                        try {
                            jObjck = new JSONObject(response);
                            String code = jObjck.getString("code");
                            if (code.equals("200")) {
                                dm.deleteStatusTerkirim(hasilIdOrder);
                                //uploadphoto();
                                NotificationClass notificationClass = new NotificationClass(context);
                                notificationClass.NotifyBuilder("Status Pengiriman","Data survey berhasil terkirim. Terima kasih!");
                                notificationClass.createNotificationChannel();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            NotificationClass notificationClass = new NotificationClass(context);
                            notificationClass.NotifyBuilder("Status Pengiriman","Sebagian photo belum berhasil diupload. Koneksi internet tidak stabil.");
                            notificationClass.createNotificationChannel();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params1 = new HashMap<String, String>();

                params1.put("id_order", hasilIdOrder);
                params1.put("tk", setter.APK_CODE);
                Log.d(TAG, "" + params1);
                return params1;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }


    public void simpan_photo(String s_photo_name, String s_photo_bitmap, String s_photo_latitude,
                             String s_photo_longitude, String s_photo_id_order){
        p_photo_nama        = s_photo_name;
        p_photo_bitmap      = s_photo_bitmap;
        p_photo_latitude    = s_photo_latitude;
        p_photo_longitude   = s_photo_longitude;
        p_photo_id_order    = s_photo_id_order;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, setter.UPLOAD_URL_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        JSONObject jObjck = null;
                        try {
                            jObjck = new JSONObject(response);
                            String code = jObjck.getString("code");
                            if (code.equals("200")) {
                                dm.deletePhotoAll(p_photo_nama,p_photo_id_order);
                                //uploadphoto();

                                Toast.makeText(getApplicationContext(),"Photo "+p_photo_nama+" berhasil diupload",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Proses upload foto terhenti. Photo "+p_photo_nama+" gagal diupload. Koneksi internet tidak stabil. Anda bisa melakukan refresh di halaman Home jika diperlukan.",Toast.LENGTH_LONG).show();
                Log.e("VollyError",String.valueOf(error.getMessage()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                params = new HashMap<String, String>();

                params.put("photo_nama", p_photo_nama);
                params.put("photo_bitmap", p_photo_bitmap);
                params.put("photo_latitude", p_photo_latitude);
                params.put("photo_longitude", p_photo_longitude);
                params.put("id_order", p_photo_id_order);
                params.put("tk", setter.APK_CODE);
                Log.d(TAG, "" + params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }






    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void playNotificationSound() {
        try {
            /*PathUri alarmSound = PathUri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");*/
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/arpeggio");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForeground(){

        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIF_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.icon1)
                .setContentTitle("Mobile Survey is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2,notification);
    }
}