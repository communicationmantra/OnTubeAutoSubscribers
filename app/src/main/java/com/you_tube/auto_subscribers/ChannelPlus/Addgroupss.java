package com.you_tube.auto_subscribers.ChannelPlus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdView;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.you_tube.auto_subscribers.BuildConfig;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.R;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Addgroupss extends Activity {
    EditText editgrouplink;
    TextView titleTextView,addgrpbtn1;
    Button cancel;
    Spinner catspinner;
    TextCrawler textCrawler = new TextCrawler();
    private AdView mAdView;
    String category,title,url,imageurl;
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/userchannelregister.php";

    private LinkPreviewCallback linkPreviewCallback = new LinkPreviewCallback() {
        ProgressDialog nDialog;

        public void onPre() {
            this.nDialog = new ProgressDialog(Addgroupss.this);
            this.nDialog.setMessage("Loading..");
            this.nDialog.setIndeterminate(false);
            this.nDialog.setCancelable(true);
            this.nDialog.show();
        }
        @Override
        public void onPos(final SourceContent sourceContent, final boolean b) {
            String grouplink = editgrouplink.getText().toString().trim();
            final String spineer = catspinner.getSelectedItem().toString();
            this.nDialog.dismiss();
            final String grouptitle = sourceContent.getTitle();
            if (grouptitle.matches("YouTube")) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(Addgroupss.this);
                builder3.setTitle("Please Enter Valid Channel URL/Link");
                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editgrouplink.setText(BuildConfig.FLAVOR);
                        Spinner catspinner = (Spinner) findViewById(R.id.catspinner);
                        catspinner.setSelection(0);
                    }
                });
                builder3.create().show();
                return;
            }
            final AlertDialog.Builder builder = new AlertDialog.Builder(Addgroupss.this);
            builder.setMessage(sourceContent.getTitle());
            //builder.setMessage(spineer);
            builder.setPositiveButton("Add Channel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(spineer.contains(" ")){
                        String spineer_new = spineer.substring(0, spineer.indexOf(" "));
                        category = spineer_new;
                        //System.out.println(firstWord);
                    }
                    else
                    {
                        category = spineer;
                    }
                    String uniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    title =StringEscapeUtils.escapeJava(sourceContent.getTitle());
                    Log.i("sam7_sam",StringEscapeUtils.unescapeJava(title));
                    url = editgrouplink.getText().toString().trim();
                    String usernameid = url.substring(url.lastIndexOf("/") + 1);
                    String imagemain = sourceContent.getImages().toString();
                    imageurl= imagemain.substring(1, imagemain.length()-1);
                    SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                    String earnpoint = sp.getString("gender", null);
                    String totalclick = "100";
                    String yorn="yes";

                    registerUser(uniqueId,title,
                            usernameid,
                            category,
                            imageurl,earnpoint,totalclick,yorn);
                    Log.i("sam1", "uniqueId :" + uniqueId);
                    Log.i("sam2", "title :" +title);
                    Log.i("sam3", "userId :" +usernameid);
                    Log.i("sam4", "PPC :" +category);
                    Log.i("sam5", "imageurl :" + imageurl);
                    Log.i("sam6", "earnpoint :" +earnpoint);
                    Log.i("sam7", "totalclick :" +totalclick);
                    Log.i("sam8", "yorn :" +yorn);
                    editgrouplink.setText(BuildConfig.FLAVOR);
                    catspinner.setSelection(0);
                    //Toast.makeText(Addgroupss.this, "Group "+grouptitle+" Added Successfully", 0).show();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgroups);
        editgrouplink = (EditText) findViewById(R.id.editText);
        addgrpbtn1 = (TextView) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        catspinner = (Spinner) findViewById(R.id.catspinner);
        titleTextView = (TextView) findViewById(R.id.textview_xx);
        ArrayAdapter spinneradapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_layout);
        spinneradapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        catspinner.setAdapter(spinneradapter);
        addgrpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Addgroupss.this,"hello",Toast.LENGTH_LONG).show();
                allgroups();
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

    public void allgroups() {
        String grouplink = this.editgrouplink.getText().toString().trim();
        AlertDialog.Builder alert;
            if (URLUtil.isValidUrl(grouplink) && grouplink.matches("^(?:http(?:|s)):\\/\\/www\\.youtube\\.com\\/(?:|channel\\/)(?:[A-Za-z0-9\\-_#]+)$")) {
            if (AppStatus.getInstance(this).isOnline()) {
                this.textCrawler.makePreview(this.linkPreviewCallback, grouplink);
                return;
            }
            alert = new AlertDialog.Builder(this);
            alert.setMessage("Please Check Your Internet Connection.");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else if (TextUtils.isEmpty(grouplink)) {
            alert = new AlertDialog.Builder(this);
            alert.setMessage("Please Enter Channel Link");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setMessage("Please Enter Valid Channel URL/Link.");
            alert1.setPositiveButton("OK", null);
            alert1.show();
        }
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    protected void onDestroy() {
        super.onDestroy();
        textCrawler.cancel();
    }

    private void registerUser(final String uniqueid , final String title, final String usernameid, final String cpc,
                              final String country, final String earnpoint, final String totalclick, final String yorn ) {
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
                        String user = jObj.getJSONObject("user").getString("title");

                        Toast.makeText(Addgroupss.this, "Your Channel " + user +", successfully Added!", Toast.LENGTH_LONG).show();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(Addgroupss.this,errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "Registration Error: " + error.getMessage());
                Toast.makeText(Addgroupss.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                // hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("title", title);
                params.put("usernameid", usernameid);
                params.put("cpc", cpc);
                params.put("country", country);
                params.put("earnpoint", earnpoint);
                params.put("totalclick", totalclick);
                params.put("yorn", yorn);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(Addgroupss.this).addToRequestQueue(strReq, cancel_req_tag);
    }


}
//Android | PHP | MySql - Save emoji to Mysql server from Android ...