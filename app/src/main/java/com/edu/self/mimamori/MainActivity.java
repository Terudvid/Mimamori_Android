package com.edu.self.mimamori;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView statusTextView;

    double longitude;
    double latitude;

    Date timeStamp;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        latitudeTextView = (TextView)findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView)findViewById(R.id.longitudeTextView);
        statusTextView = (TextView)findViewById(R.id.statusTextView);

        longitude = 0.0;
        latitude = 0.0;

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        latitudeTextView.setText("経度");
        longitudeTextView.setText("緯度");
        statusTextView.setText("現在は計測していません");

    }

    public void start(View v){

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000, // 通知のための最小時間間隔（ミリ秒）
                100, // 通知のための最小距離間隔（メートル）
                this
        );

        latitudeTextView.setText(String.valueOf(latitude));
        longitudeTextView.setText(String.valueOf(longitude));
        statusTextView.setText("計測中");

    }

    public void stop(View v){

        locationManager.removeUpdates(this);

        latitudeTextView.setText("経度");
        longitudeTextView.setText("緯度");
        statusTextView.setText("計測中断");
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                100000, // 通知のための最小時間間隔（ミリ秒）
                100, // 通知のための最小距離間隔（メートル）
                this
        );

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        latitudeTextView.setText(String.valueOf(latitude));
        longitudeTextView.setText(String.valueOf(longitude));

        ParseObject geoObject = new ParseObject("GeoData");
        geoObject.put("latitude", latitude);
        geoObject.put("longitude", longitude);


//        Long longTimeStamp = System.currentTimeMillis()/1000;
//        String timestamp = longTimeStamp.toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm:ss");
        String timeStamp = sdf.format(c.getTime());
        geoObject.put("timeStamp", timeStamp);
        geoObject.put("OS", "Android");
        geoObject.saveInBackground();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        locationManager.removeUpdates(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
