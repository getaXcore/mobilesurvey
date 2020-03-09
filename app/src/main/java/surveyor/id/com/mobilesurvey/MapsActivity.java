package surveyor.id.com.mobilesurvey;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import surveyor.id.com.mobilesurvey.adapter.MyAdapter;
import surveyor.id.com.mobilesurvey.modal.DatabaseManager;
import surveyor.id.com.mobilesurvey.modal.Route;
import surveyor.id.com.mobilesurvey.util.DirectionFinder;
import surveyor.id.com.mobilesurvey.util.DirectionFinderListener;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, DirectionFinderListener {
    public Dialog dialog;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private DatabaseManager dm;
    private int status_icon_menu;
    private String namalengkap_surveyor,order_code, status_survey,kategori_kendaraan,merk_kendaraan,
            model_kendaraan,type_kendaraan,tahun,warna,plat_nomor,km_kendaraan,bahan_bakar,
            jenis_kredit,jml_pinjaman,tenor,Get_status_survey_s,destination,origin,latitude,
            longitude,id_order,id_customer,asuransi,otr,dp,kode_cabang,jaminan_multiguna,name,
            identity_type,identity_no,address_home,telephone,sex,handphone_1,latitude_survey,
            longitude_survey,janji_survey;
    private static final String TAG = "MapsActivity";
    private int Get_status_survey;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private SlidingUpPanelLayout mLayout;
    View box_sliding;
    private RelativeLayout box_menu_maps;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    Toolbar toolbar;
    private float slideOffsetsGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Maps Customer");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dm = new DatabaseManager(this);
        buildGoogleApiClient();

        box_sliding     = (View) findViewById(R.id.c_up);
        box_menu_maps   = (RelativeLayout) findViewById(R.id.box_menu_maps);
        mLayout         = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        id_order                = getIntent().getExtras().getString("id_order");
        id_customer             = getIntent().getExtras().getString("id_customer");
        namalengkap_surveyor    = getIntent().getExtras().getString("namalengkap_surveyor");
        asuransi                = getIntent().getExtras().getString("asuransi");
        jenis_kredit            = getIntent().getExtras().getString("jenis_kredit");
        order_code              = getIntent().getExtras().getString("order_code");
        jml_pinjaman            = getIntent().getExtras().getString("jml_pinjaman");
        otr                     = getIntent().getExtras().getString("otr");
        dp                      = getIntent().getExtras().getString("dp");
        tenor                   = getIntent().getExtras().getString("tenor");
        kode_cabang             = getIntent().getExtras().getString("kode_cabang");
        jaminan_multiguna       = getIntent().getExtras().getString("jaminan_multiguna");

        name                    = getIntent().getExtras().getString("name");
        identity_type           = getIntent().getExtras().getString("identity_type");
        identity_no             = getIntent().getExtras().getString("identity_no");
        address_home            = getIntent().getExtras().getString("address_home");
        telephone               = getIntent().getExtras().getString("telephone");
        sex                     = getIntent().getExtras().getString("sex");
        handphone_1             = getIntent().getExtras().getString("handphone_1");

        kategori_kendaraan      = getIntent().getExtras().getString("kategori_kendaraan");
        merk_kendaraan          = getIntent().getExtras().getString("merk_kendaraan");
        model_kendaraan         = getIntent().getExtras().getString("model_kendaraan");
        type_kendaraan          = getIntent().getExtras().getString("type_kendaraan");
        tahun                   = getIntent().getExtras().getString("tahun");
        warna                   = getIntent().getExtras().getString("warna");
        plat_nomor              = getIntent().getExtras().getString("plat_nomor");
        km_kendaraan            = getIntent().getExtras().getString("km_kendaraan");
        bahan_bakar             = getIntent().getExtras().getString("bahan_bakar");
        status_survey           = getIntent().getExtras().getString("status_survey");

        latitude_survey         = getIntent().getExtras().getString("latitude_survey");
        longitude_survey        = getIntent().getExtras().getString("longitude_survey");
        janji_survey            = getIntent().getExtras().getString("janji_survey");

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);

                slideOffsetsGlobal = slideOffset;

                if(slideOffset==0.0){
                    box_sliding.setVisibility(View.GONE);
                }else if(slideOffset==1.0){
                    box_sliding.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.
                    PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        box_sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                box_sliding.setVisibility(View.GONE);
            }
        });

        mViewPager = (ViewPager)findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),id_order,id_customer,
                namalengkap_surveyor,asuransi,jenis_kredit,order_code,jml_pinjaman,otr,dp,tenor,
                kode_cabang,jaminan_multiguna,name,identity_type,identity_no,address_home,telephone,
                sex,handphone_1,kategori_kendaraan,merk_kendaraan,model_kendaraan,type_kendaraan,
                tahun,warna,plat_nomor,km_kendaraan,bahan_bakar,status_survey,
                latitude_survey,longitude_survey,janji_survey,this));
        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.st1_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.background_toolbar));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.slidbk));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

            }

            @Override
            public void onPageSelected(int position) {
                if(slideOffsetsGlobal < 1.0){
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(latitude_survey != null){
            if(!latitude_survey.equals("")){
                if(!latitude_survey.equals("null")){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                fab.setVisibility(View.GONE);
            }
        }else{
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+latitude_survey+"," +
                                longitude_survey));
                startActivity(intent);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void sendRequest() {
        destination = "" + latitude_survey + ", " + longitude_survey;

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                kirimdat(destination);
            } else {
                kirimdat(destination);
            }
        }
    }

    public void kirimdat(String t_destination) {
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                    ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(this, android.Manifest.permission.
                            ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = "" + String.valueOf(mLastLocation.getLatitude());
                longitude = "" + String.valueOf(mLastLocation.getLongitude());
                origin = "" + latitude + "," + longitude;
            } else {
                origin = "2 " + mLastLocation;
            }
        } catch (SecurityException e) {

        }

        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (t_destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, t_destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //disable direct google maps
        mMap.getUiSettings().setMapToolbarEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng indo = new LatLng(-6.175743, 106.854434);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indo, 6));
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.", "Finding direction..!" ,true);
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }
        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }
        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            //    ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //    ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_surveyor_kecil))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            Get_status_survey_s = getIntent().getExtras().getString("status_survey");
            Get_status_survey = Integer.parseInt(Get_status_survey_s);
            if (Get_status_survey == 0) {
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_data_new_kecil))
                        .title(route.endAddress)
                        .position(route.endLocation)));
            } else if (Get_status_survey == 1) {
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_data_approv_kecil))
                        .title(route.endAddress)
                        .position(route.endLocation)));
            } else if (Get_status_survey == 2) {
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_data_pending_kecil))
                        .title(route.endAddress)
                        .position(route.endLocation)));
            } else {
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_data_reject_kecil))
                        .title(route.endAddress)
                        .position(route.endLocation)));
            }
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);
            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sendRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info:
                if(status_icon_menu==1){
                    status_icon_menu=0;
                    box_menu_maps.setVisibility(View.VISIBLE);

                }else{
                    status_icon_menu=1;
                    box_menu_maps.setVisibility(View.GONE);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<ArrayList<Object>> data6 = dm.ambilSemuaBaris_status();
        if (data6.size() < 1) {

        } else {
            finish();
        }
    }
}
