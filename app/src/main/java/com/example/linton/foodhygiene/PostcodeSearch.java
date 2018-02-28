package com.example.linton.foodhygiene;

import android.content.Context;
import android.os.AsyncTask;
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
    String URL = "";
    RadioButton bNameSearch;
    RadioButton pCodeSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcode_search);
//        textView = (TextView) findViewById(R.id.textView2);
        restSearch = (EditText) findViewById(R.id.restSearch);
        listView = (ListView) findViewById(R.id.listView);
        bNameSearch = (RadioButton) findViewById(R.id.bNameSearch);
        pCodeSearch = (RadioButton) findViewById(R.id.pCodeSearch);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.bNameSearch:
                if (checked)
                    URL = "op=s_name&name=";
                    pCodeSearch.setChecked(false);
                    break;
            case R.id.pCodeSearch:
                if (checked)
                    URL = "op=s_postcode&postcode=";
                    bNameSearch.setChecked(false);
                    break;
        }
    }

    public void getResults(View view) {
        Log.i("restSearch", restSearch.getText().toString());
        GetRating task = new GetRating();
        task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?" + URL + restSearch.getText().toString());
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
                    Restaurant RestaurantObj = new Restaurant(
                            (jsonObject.getString("BusinessName")),
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

                RestaurantAdapter adapter = new RestaurantAdapter(PostcodeSearch.this, restaurantArrayList);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Result", result);
        }
    }
}
