package com.example.mystudyapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystudyapp.R;
import com.example.mystudyapp.models.GeoIp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GeoIpActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private EditText mAddressEdit;
    private TextView mResultText;
    private Button mSubmit_btn;
    private Retrofit mRetrofit;
    private FreeGeoIpService mService;
    private ProgressBar mProgressBar;

    private GoogleMap mMap;


    interface FreeGeoIpService {
        @GET("{address}?access_key=6a0ae413ac6b79d4ceb9fb01dd102efa")
        Call<GeoIp> getGeoIp(@Path("address") String address);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_ip);

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRetrofit = new Retrofit.Builder()
                //.baseUrl("http://freegeoip.net/")
                .baseUrl("http://api.ipstack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(FreeGeoIpService.class);

        mAddressEdit = findViewById(R.id.address_edit);
        mResultText = findViewById(R.id.result_text);

        mSubmit_btn = findViewById(R.id.submit_btn);
        mSubmit_btn.setOnClickListener(this);

        mProgressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onClick(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mService.getGeoIp(mAddressEdit.getText().toString())
                .enqueue(new Callback<GeoIp>() {
                    @Override //성공
                    public void onResponse(Call<GeoIp> call, Response<GeoIp> response) {
                        GeoIp geoIp = response.body();
                        Log.d("TAG", "on Response : " + geoIp);
                        if (geoIp != null) {
                            mResultText.setText(geoIp.toString());
                            mProgressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(GeoIpActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override //실패
                    public void onFailure(Call<GeoIp> call, Throwable throwable) {
                        Toast.makeText(GeoIpActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

}
