package com.example.aleksandr.testtaskn;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView textView, textView2,textView3,textView9,tvTemp;

    Geocoder geocoder;
    List<Address> addresses;
    LocationManager lm;
    DBHelper dbHelper;
    WeatherAPI.ApiInterface api;
    Double lat = 0.0;
    Double lon = 0.0;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemp = (TextView) findViewById(R.id.tvTemp);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        dbHelper = new DBHelper(this);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView9 = findViewById(R.id.textView9);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        textView9.setText(formattedDate);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }

        }
    }


    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        //Получение текущих координат
        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
           if (location != null) {
               lat = location.getLatitude();
               lon = location.getLongitude();
               textView2.setText(lat.toString());
               textView.setText(lon.toString());

               String units = "metric";
               String key = WeatherAPI.KEY;

               Log.d(TAG, "OK");

               // Получение погоды на сегодня
               Call<WeatherDay> callToday = api.getToday(lat, lon, units, key);
               callToday.enqueue(new Callback<WeatherDay>() {
                   @Override
                   public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                       Log.e(TAG, "onResponse");
                       WeatherDay data = response.body();


                       if (response.isSuccessful()) {
                           tvTemp.setText( data.getTempWithDegree());

                       }
                   }

                   @Override
                   public void onFailure(Call<WeatherDay> call, Throwable t) {
                       Log.e(TAG, "onFailure");
                       Log.e(TAG, t.toString());
                   }
               });


           }
       }

       geocoder = new Geocoder(this, Locale.getDefault());
       try {
           addresses = geocoder.getFromLocation(lat,lon,1);

           String adress = addresses.get(0).getAddressLine(0);
         //  String area = addresses.get(0).getLocality();
         //  String city= addresses.get(0).getAdminArea();
         //  String country = addresses.get(0).getCountryName();


           String fullAddress = adress;

           textView3.setText(fullAddress);


       } catch (IOException e) {
           e.printStackTrace();
       }


  }

    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            //Переход на активити истории
            Intent intent =new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings2) {
            //Очистка базы данных
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.delete(DBHelper.TABLE_PLASES, null, null);
            Toast.makeText(this,"Data deleted",
                    Toast.LENGTH_SHORT)
                    .show();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }



    // Сохранение в базу данных
    public void savedataBase(View view) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_ADDRESS,      textView3.getText().toString());
        contentValues.put(DBHelper.KEY_WEATHER,      tvTemp.getText().toString());
        contentValues.put(DBHelper.KEY_LONGTITUDE,   textView.getText().toString());
        contentValues.put(DBHelper.KEY_LATITUDE ,    textView2.getText().toString());
        contentValues.put(DBHelper.KEY_DATEANDTIME , textView9.getText().toString());

        database.insert(DBHelper.TABLE_PLASES, null, contentValues);
        Toast.makeText(this,"Data saved",
                Toast.LENGTH_SHORT)
                .show();


    }
}
