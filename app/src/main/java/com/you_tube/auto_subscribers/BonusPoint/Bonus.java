package com.you_tube.auto_subscribers.BonusPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.HomeActivity;
import com.you_tube.auto_subscribers.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Bonus extends Activity {
    private static final String URL_FOR_BONUS = "http://livemcxrates.in/appontube/youtube_login_example/clone.php";
    TextView addgrpbtn1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonus);
        addgrpbtn1 = (TextView) findViewById(R.id.button);

        addgrpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitForm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Bonus.this, HomeActivity.class);
                //Intent i = new Intent(HomeActivity.this, StarRatingDialog.class);
                startActivity(i);
                Toast.makeText(Bonus.this,"Your Bonus Added" , Toast.LENGTH_LONG).show();
                finish();
                //allgroups();
            }
        });

    }
    void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    private void submitForm() throws ParseException
    {
        String uniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        firebaseUser(uniqueId);
    }



    private void firebaseUser(final String uniqueid) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_BONUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("TAG", "Register Response: " + response.toString());
                //hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        // Toast.makeText(getActivity(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Registration Error: " + error.getMessage());
                //Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                //params.put("end", end);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(Bonus.this).addToRequestQueue(strReq, cancel_req_tag);
    }


}
//Android | PHP | MySql - Save emoji to Mysql server from Android ...