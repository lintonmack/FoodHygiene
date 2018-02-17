package com.example.linton.foodhygiene;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;

public class PostcodeSearch extends AppCompatActivity {

    String resultSet;
    String[] businessNames;
    String[] businessAddress;
    int[] hygieneRating;
    TextView textView;
    EditText restSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcode_search);
        textView = (TextView) findViewById(R.id.textView2);
        restSearch = (EditText) findViewById(R.id.restSearch);
    }

    public void getResults(View view){
        Log.i("restSearch", restSearch.getText().toString());
        GetRating task = new GetRating();
        task.execute("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_postcode&postcode=" + restSearch.getText().toString());
//                "LL6");
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
                while(data!=-1){
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
//              JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject;
                for(int n = 0; n < jsonArray.length(); n++)
                {
                    jsonObject = jsonArray.getJSONObject(n);
                    String businessN = jsonObject.getString("BusinessName");
                    String address1 = jsonObject.getString("AddressLine1");
                    String address2 = jsonObject.getString("AddressLine2");
                    String address3 = jsonObject.getString("AddressLine3");
                    String postCode = jsonObject.getString("PostCode");
                    String ratingValue = jsonObject.getString("RatingValue");

                    result = businessN + ", " + address1 + ", " + address2 + ", " + address3 + ", " +
                            postCode + ". Has a Hygiene Rating of: " + ratingValue + "\n\n";

                    resultSet += result;
                    textView.setText(resultSet);

                    Log.i("Result", result);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Result", result);

        }
    }
}
