package com.you_tube.auto_subscribers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.NewRegistration.RegistrationMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Splashscreen extends AppCompatActivity {

    String email, password;
    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "http://livemcxrates.in/appontube/youtube_login_example/login.php";
    ProgressDialog progressDialog;



    private FirebaseAnalytics mFirebaseAnalytics;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

    }//** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
        login();

        // [START shared_app_measurement]
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // [END shared_app_measurement]

        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Home");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // [END image_view_event]

        logSentFriendRequestEvent ();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }




    public void logSentFriendRequestEvent () {
       //logger.logEvent("sentFriendRequest");
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }
    private void login() {
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        email= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        password=sp.getString("displayp",null);
        //Toast.makeText(SplashscreenActivity.this,"email :" +email+"password"+password,Toast.LENGTH_LONG).show();

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 70;
                    }


                    if (password == null) {
                        //doLogin(userName, password);
                        Intent intent = new Intent(Splashscreen.this, RegistrationMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        //SplashscreenActivity.this.finish();
                    }
                    else
                    {
                        loginUser(email.toString(),
                                password.toString());
                    }

                    //Intent intent = new Intent(SplashscreenActivity.this, LoginActivity.class);


                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //SplashscreenActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }

    private void loginUser(final String uniqueid, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        // progressDialog.setMessage("Logging you in...");
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String user = jObj.getJSONObject("user").getString("name");
                        String email = jObj.getJSONObject("user").getString("email");
                        String displayp = jObj.getJSONObject("user").getString("displayp");
                        String age = jObj.getJSONObject("user").getString("age");
                        String entry = jObj.getJSONObject("user").getString("entry");
                        String end = jObj.getJSONObject("user").getString("end");
                        String contact = jObj.getJSONObject("user").getString("contact");

                        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sharedPreferences.edit();
                        ed.putString("name",user);
                        ed.putString("email",email);
                        ed.putString("displayp",displayp);
                        ed.putString("age",age);
                        ed.putString("entry",entry);
                        ed.putString("end",end);
                        ed.putString("contact",contact);
                        //ed.putString("pass","123");
                        ed.commit();

                        // Launch User activity
                        Intent intent = new Intent(
                                Splashscreen.this,
                                HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        // SplashscreenActivity.this.finish();

                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");

                        Intent intent = new Intent(Splashscreen.this, RegistrationMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        //SplashscreenActivity.this.finish();
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Please Check Internet Connection"
                        , Toast.LENGTH_LONG).show();
                // hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                //params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }
//    private void showDialog() {
//        if (!progressDialog.isShowing())
//            progressDialog.show();
//    }
//    private void hideDialog() {
//        if (progressDialog.isShowing())
//            progressDialog.dismiss();
//    }

}