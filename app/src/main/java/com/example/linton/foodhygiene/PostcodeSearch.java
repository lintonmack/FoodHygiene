package com.example.linton.foodhygiene;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostcodeSearch extends AppCompatActivity {

    EditText restSearch;
    ListView listView;
    static ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    public static RadioButton bNameSearch, pCodeSearch, gpsSearch, recentEntries;
    LocationManager locationManager;
    LocationListener locationListener;
    Double lat = 0.00, lng = 0.00;
    String latS = "", lngS = "";
    public static int indexVal;
    RestaurantAdapter adapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcode_search);
        restSearch = (EditText) findViewById(R.id.restSearch);
        listView = (ListView) findViewById(R.id.listView);
        bNameSearch = (RadioButton) findViewById(R.id.bNameSearch);
        pCodeSearch = (RadioButton) findViewById(R.id.pCodeSearch);
        gpsSearch = (RadioButton) findViewById(R.id.gpsSearch);
        recentEntries = (RadioButton) findViewById(R.id.recentEntries);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //fetch data
        } else {
            //display error
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());
                lng = location.getLongitude();
                lat = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 0, locationListener);
        }

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.bNameSearch:
                if (checked)
                    pCodeSearch.setChecked(false);
                gpsSearch.setChecked(false);
                recentEntries.setChecked(false);
                indexVal = 1;
                break;
            case R.id.pCodeSearch:
                if (checked)
                    bNameSearch.setChecked(false);
                gpsSearch.setChecked(false);
                recentEntries.setChecked(false);
                indexVal = 2;
                break;
            case R.id.gpsSearch:
                if (checked)
                    lngS = lng.toString();
                latS = lat.toString();
                bNameSearch.setChecked(false);
                pCodeSearch.setChecked(false);
                recentEntries.setChecked(false);
                indexVal = 3;
                break;
            case R.id.recentEntries:
                if (checked)
                    bNameSearch.setChecked(false);
                pCodeSearch.setChecked(false);
                gpsSearch.setChecked(false);
                indexVal = 4;
                break;
        }
    }

    public void getResults(View view) {
//        Log.i("restSearch", restSearch.getText().toString());
        GetRating task = new GetRating();
        if (indexVal == 1) {
            task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_name&name=" + restSearch.getText().toString());
        } else if (indexVal == 2) {
            task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_postcode&postcode=" + restSearch.getText().toString());
        } else if (indexVal == 3) {
            task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_loc&lat=" + latS + "&long=" + lngS);
        } else if (indexVal == 4) {
            task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_recent");
        }
    }

    public class GetRating extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject;
                for (int n = 0; n < jsonArray.length(); n++) {
                    jsonObject = jsonArray.getJSONObject(n);
                    if (indexVal == 3) {
                        Restaurant RestaurantObj = new Restaurant(
                                (jsonObject.getString("BusinessName")),
                                (jsonObject.getString("AddressLine1")),
                                (jsonObject.getString("AddressLine2")),
                                (jsonObject.getString("AddressLine3")),
                                (jsonObject.getString("PostCode")),
                                (jsonObject.getString("RatingValue")),
                                (jsonObject.getString("RatingDate")),
                                (jsonObject.getString("Longitude")),
                                (jsonObject.getString("Latitude")),
                                (jsonObject.getString("DistanceKM")));
                        restaurantArrayList.add(RestaurantObj);
                    } else {
                        Restaurant RestaurantObj = new Restaurant((jsonObject.getString("BusinessName")),
                                (jsonObject.getString("AddressLine1")),
                                (jsonObject.getString("AddressLine2")),
                                (jsonObject.getString("AddressLine3")),
                                (jsonObject.getString("PostCode")),
                                (jsonObject.getString("RatingValue")),
                                (jsonObject.getString("RatingDate")),
                                (jsonObject.getString("Longitude")),
                                (jsonObject.getString("Latitude")));
                        restaurantArrayList.add(RestaurantObj);

                    }
                }
                adapter = new RestaurantAdapter(PostcodeSearch.this, restaurantArrayList);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Result", result);
        }
    }
}
