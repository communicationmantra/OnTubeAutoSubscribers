package com.you_tube.auto_subscribers.PositiveStarRating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StarRatingFeedBack extends Activity {
    EditText editgrouplink;
    TextView titleTextView,addgrpbtn1;
    Button cancel;
    Spinner catspinner;
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/feedback.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starratingfeedback);
        editgrouplink = (EditText) findViewById(R.id.editText);
        addgrpbtn1 = (TextView) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);

        addgrpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String rating = intent.getExtras().getString("rating");
                Toast.makeText(StarRatingFeedBack.this,"rate : "+ rating , Toast.LENGTH_LONG).show();
                String uniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String feedback = editgrouplink.getText().toString().trim();
                registerUser(uniqueId,rating,feedback);
                finish();
                //allgroups();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showToastMessage("Cancel Button Clicked");
                finish();
            }
        });
    }
    void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    private void registerUser(final String uniqueid , final String rating, final String feedback ) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        //progressDialog.setMessage("Adding you ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("TAG", "Register Response: " + response.toString());
                //hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("uniqueid");
                        Toast.makeText(StarRatingFeedBack.this, "Thanks For Your Feedback", Toast.LENGTH_LONG).show();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(StarRatingFeedBack.this,errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "Registration Error: " + error.getMessage());
                Toast.makeText(StarRatingFeedBack.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                // hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("rating", rating);
                params.put("feedback", feedback);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(StarRatingFeedBack.this).addToRequestQueue(strReq, cancel_req_tag);
    }


}
//Android | PHP | MySql - Save emoji to Mysql server from Android ...