package com.you_tube.auto_subscribers.NewRegistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import androidx.percentlayout.widget.PercentLayoutHelper;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class RegistrationMainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "http://livemcxrates.in/appontube/youtube_login_example/login.php";
    private static final String URL_FOR_FORGET = "http://livemcxrates.in/appontube/youtube_login_example/forget.php";
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/register.php";
    ProgressDialog progressDialog;
    VerticalTextView txtSignIn, txtRegister;
    LinearLayout llSignIn, llRegister;
    EditText signupInputName, signupInputEmail, signupInputAge;
    Button btnRegister, btnSignIn, btnForgotPassword;
    private EditText et1,et2,et3,et4, rt1,rt2,rt3,rt4;
    TextView text;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_main);
        llSignIn = (LinearLayout) findViewById(R.id.llSign_in);
        llRegister = (LinearLayout) findViewById(R.id.llRegister);
        txtRegister = (VerticalTextView) findViewById(R.id.txtRegister);
        txtSignIn = (VerticalTextView) findViewById(R.id.txtSignIn);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        signupInputName = (EditText) findViewById(R.id.signup_input_name);
        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        //signupInputReferral = (EditText) findViewById(R.id.signup_input_referral);
        signupInputAge = (EditText) findViewById(R.id.signup_input_age);

        txtSignIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);

        et1 = (EditText) findViewById(R.id.pin_1);
        et2 = (EditText) findViewById(R.id.pin_2);
        et3 = (EditText) findViewById(R.id.pin_3);
        et4 = (EditText) findViewById(R.id.pin_4);

        SharedPreferences sp=getSharedPreferences("register",MODE_PRIVATE);
        String t1 = sp.getString("et1",null);
        String t2 = sp.getString("et2",null);
        String t3 = sp.getString("et3",null);
        String t4 = sp.getString("et4",null);

        et1.setText(t1);
        et2.setText(t2);
        et3.setText(t3);
        et4.setText(t4);

        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custumtoast,
                (ViewGroup) findViewById(R.id.custom_toast_layout));
        //ImageView image = (ImageView) layout.findViewById(R.id.image);
        // image.setImageResource(R.drawable.android);
        text = (TextView) layout.findViewById(R.id.custom_toast_message);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        rt1 = (EditText) findViewById(R.id.rpin_1);
        rt2 = (EditText) findViewById(R.id.rpin_2);
        rt3 = (EditText) findViewById(R.id.rpin_3);
        rt4 = (EditText) findViewById(R.id.rpin_4);

        rt1.addTextChangedListener(new GenericTextWatcher1(rt1));
        rt2.addTextChangedListener(new GenericTextWatcher1(rt2));
        rt3.addTextChangedListener(new GenericTextWatcher1(rt3));
        rt4.addTextChangedListener(new GenericTextWatcher1(rt4));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSignIn:
                hideSoftKeyBoard();
                //Toast.makeText(RegistrationMainActivity.this,"hello",Toast.LENGTH_LONG).show();
                showSignInForm();
                break;
            case R.id.txtRegister:
                hideSoftKeyBoard();
                //Toast.makeText(RegistrationMainActivity.this,"Register",Toast.LENGTH_LONG).show();
                showRegisterForm();
                break;
            case R.id.btnSignIn:
                //Toast.makeText(RegistrationMainActivity.this,"Login",Toast.LENGTH_LONG).show();
                if (isEmptyField(et1)) return;
                if (isEmptyField(et2)) return;
                if (isEmptyField(et3)) return;
                if (isEmptyField(et4)) return;
                login();
                break;
            case R.id.btnRegister:
                //Toast.makeText(RegistrationMainActivity.this,"Register",Toast.LENGTH_LONG).show();
                if (isEmptyField(signupInputName)) return;
                if (isEmptyField(signupInputEmail)) return;
                if (isEmptyField(signupInputAge)) return;
                if (isEmptyField(rt1)) return;
                if (isEmptyField(rt2)) return;
                if (isEmptyField(rt3)) return;
                if (isEmptyField(rt4)) return;
                try {
                    submitForm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnForgotPassword:
                //Toast.makeText(RegistrationMainActivity.this,"Register",Toast.LENGTH_LONG).show();
                String email1= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String x = "0";
                forget(email1.toString(),x);
                break;
        }
    }



    private void showSignInForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llRegister.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llRegister.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignIn.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignIn.requestLayout();

        txtRegister.setVisibility(View.VISIBLE);
        txtSignIn.setVisibility(View.GONE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignIn.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_translate_rotate_left_to_right);
        btnSignIn.startAnimation(clockwise);

    }

    private void showRegisterForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignIn.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignIn.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llRegister.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llRegister.requestLayout();

        txtRegister.setVisibility(View.GONE);
        txtSignIn.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llRegister.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_translate_rotate_right_to_left);
        btnRegister.startAnimation(clockwise);

    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void login() {

        String email= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String password=et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString();
        Log.i("login123",email);
        //Toast.makeText(Splashscreen.this,"email :" +email+"password"+password,Toast.LENGTH_LONG).show();


        if (password == null) {
//            //doLogin(userName, password);
//            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            //Splashscreen.this.finish();

            Toast.makeText(this,"Please Register !", Toast.LENGTH_LONG).show();
        }
        else
        {
            loginUser(email.toString(),
                    password.toString());
        }

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
                Log.i(TAG, "Register Response: " + response.toString());
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
                                RegistrationMainActivity.this,
                                HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Toast.makeText(RegistrationMainActivity.this,"Welcome ... Don't Forget To Add Your YouTube Channel", Toast.LENGTH_LONG).show();
                        RegistrationMainActivity.this.finish();

                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");

                        Intent intent = new Intent(RegistrationMainActivity.this, RegistrationMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        RegistrationMainActivity.this.finish();
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
                Log.i(TAG, "Login Error: " + error.getMessage());
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

    private void forget(final String uniqueid, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        // progressDialog.setMessage("Logging you in...");
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_FORGET, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Register Response: " + response.toString());
                //hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String displayp = jObj.getJSONObject("user").getString("displayp");

                        text.setText(displayp);


                        Toast toast = new Toast(getApplicationContext());
                        // toast.setGravity(Gravity.C, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        //Toast.makeText(LoginActivity.this,"PIN : " + displayp, Toast.LENGTH_LONG).show();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Intent intent = new Intent(RegistrationMainActivity.this, RegistrationMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        RegistrationMainActivity.this.finish();
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
                Log.i(TAG, "Login Error: " + error.getMessage());
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

    private void submitForm() throws ParseException {
        String date2 = "sam";
        //String refreshedToken = signupInputReferral.getText().toString();
        String refreshedToken = "Ontube";
        //----- IMEI Number
        String uniqueId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(this, "uniqur id :"+ uniqueId, Toast.LENGTH_SHORT).show();
        String displayp = rt1.getText().toString()+rt2.getText().toString()+rt3.getText().toString()+rt4.getText().toString();
        String gender= "0";
        String end ="0";

        SharedPreferences sharedPreferences=getSharedPreferences("register",MODE_PRIVATE);
        SharedPreferences.Editor ed=sharedPreferences.edit();
        ed.putString("rt1",rt1.getText().toString());
        ed.putString("rt2",rt2.getText().toString());
        ed.putString("rt3",rt3.getText().toString());
        ed.putString("rt4",rt4.getText().toString());
        ed.commit();


        registerUser(uniqueId,signupInputName.getText().toString(),
                signupInputEmail.getText().toString(),
                displayp,
                gender,
                signupInputAge.getText().toString(),date2, end,refreshedToken,displayp);
    }

    private void registerUser(final String uniqueid , final String name, final String email, final String password,
                              final String gender, final String dob, final String entry, final String end, final String firebaseid, final String displayp ) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added! & Your PIN : "+ displayp , Toast.LENGTH_SHORT).show();

//                        // Launch login activity
//                        Intent intent = new Intent(
//                                RegistrationMainActivity.this,
//                                LoginActivity.class);
//                        startActivity(intent);
//                        finish();
                        showSignInForm();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                params.put("age", dob);
                params.put("entry", entry);
                params.put("end", end);
                params.put("firebaseid", firebaseid);
                params.put("displayp", displayp);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }


    public class GenericTextWatcher implements TextWatcher {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.pin_1:
                    if(text.length()==1)
                        et2.requestFocus();
                    break;
                case R.id.pin_2:
                    if(text.length()==1)
                        et3.requestFocus();
                    break;
                case R.id.pin_3:
                    if(text.length()==1)
                        et4.requestFocus();
                    break;
                case R.id.pin_4:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }


    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public class GenericTextWatcher1 implements TextWatcher
    {
        private View view;
        private GenericTextWatcher1(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.rpin_1:
                    if(text.length()==1)
                        rt2.requestFocus();
                    break;
                case R.id.rpin_2:
                    if(text.length()==1)
                        rt3.requestFocus();
                    break;
                case R.id.rpin_3:
                    if(text.length()==1)
                        rt4.requestFocus();
                    break;
                case R.id.rpin_4:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }


    }

    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(this, "Fill Properly !", Toast.LENGTH_SHORT).show();
        return result;
    }
}
