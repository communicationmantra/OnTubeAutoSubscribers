package com.you_tube.auto_subscribers.MainFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
import com.facebook.ads.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.ExtraUtils.HttpHandler;
import com.you_tube.auto_subscribers.HomeActivity;
import com.you_tube.auto_subscribers.PositiveStarRating.StarRatingDialog;
import com.you_tube.auto_subscribers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ThirdFragment extends Fragment implements RewardedVideoAdListener {

    private static final String TAG = "RootFragment1";
    TextView thirdpage_textView,thirdpage_textView1,thirdpage_textView2,thirdpage_textView3,thirdpage_textView4,thirdpage_textView5,thirdpage_textView6;
    Button btn;
    String data1,data2,rec2_earnpoint,rec2_cpc,rec2_uniqueid;
    private ProgressDialog pDialog;
    private ListView lv;
    String name,subscriberCount;
    String name1;
    String url;
    String id1, x;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private AdView mAdView;
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/loginearnpoint.php";
    private static final String URL_FOR_REGISTRATION1 = "http://livemcxrates.in/appontube/youtube_login_example/userearnpoint.php";
    ArrayList<HashMap<String, String>> contactList;
    TextView tutorial;
    private NativeAd nativeAd;
    private RewardedVideoAd mRewardedVideoAd;

    private com.facebook.ads.InterstitialAd interstitialAd;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_new, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactList = new ArrayList<>();
        adRequest = new AdRequest.Builder().build();
        thirdpage_textView = (TextView) view.findViewById(R.id.thirdpage_textView);
        thirdpage_textView1 = (TextView) view.findViewById(R.id.thirdpage_textView1);
        thirdpage_textView2 = (TextView) view.findViewById(R.id.thirdpage_textView2);
        thirdpage_textView3 = (TextView) view.findViewById(R.id.thirdpage_textView3);
        tutorial = (TextView) view.findViewById(R.id.tutorial);
        btn = (Button) view.findViewById(R.id.button2);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StarRatingDialog.class);
                startActivity(i);
//                Snackbar.make(view, "Powered By: Trading Mantra's Live Price API ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //allgroups();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                mInterstitialAd = new InterstitialAd(getActivity());
//                mInterstitialAd.setAdUnitId("ca-app-pub-5468516408296733/2905487914");
//                mInterstitialAd.loadAd(adRequest);
//                mInterstitialAd.setAdListener(new AdListener()
//                {
//                    public void onAdLoaded()
//                    {
//                        showInterstitial();
//                    }
//                });
   //App next code
               ((HomeActivity) getActivity()).viewPager.setCurrentItem(0);
                MobileAds.initialize(getActivity(), "ca-app-pub-5468516408296733~1946495237");
                // Use an activity context to get the rewarded video instance.
                mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
                mRewardedVideoAd.setRewardedVideoAdListener(new com.google.android.gms.ads.reward.RewardedVideoAdListener() {
                                                                @Override
                                                                public void onRewardedVideoAdLoaded() {
                                                                 mRewardedVideoAd.show();
                                                                 Log.i("reads","1");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoAdOpened() {
                                                                    Log.i("reads","2");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoStarted() {
                                                                    Log.i("reads","3");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoAdClosed() {
                                                                    Log.i("reads","4");
                                                                }

                                                                @Override
                                                                public void onRewarded(RewardItem rewardItem) {
                                                                    Log.i("reads","5");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoAdLeftApplication() {
                                                                    Log.i("reads","6");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoAdFailedToLoad(int i) {
                                                                    Log.i("reads","7");
                                                                }

                                                                @Override
                                                                public void onRewardedVideoCompleted() {
                                                                    Log.i("reads","8");
                                                                }
                                                            });


                        loadRewardedVideoAd();


//                Appnext.init(getActivity());
//                Interstitial interstitial_Ad = new Interstitial(getActivity(), "2a326d8a-21fd-4255-acb4-c9a21d608b92");
//                interstitial_Ad.loadAd();
//                interstitial_Ad.showAd();
   //App Next code End

//                interstitialAd = new com.facebook.ads.InterstitialAd(getActivity(), "1954924691231443_1954946337895945");
//                interstitialAd.setAdListener(new InterstitialAdListener() {
//                    @Override
//                    public void onInterstitialDisplayed(Ad ad) {
//                        // Interstitial displayed callback
//                    }
//
//                    @Override
//                    public void onInterstitialDismissed(Ad ad) {
//                        // Interstitial dismissed callback
//                    }
//
//                    @Override
//                    public void onError(Ad ad, AdError adError) {
//                        // Ad error callback
//                        Toast.makeText(getActivity(),  adError.getErrorMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onAdLoaded(Ad ad) {
//                        // Show the ad when it's done loading.
//                        interstitialAd.show();
//                    }
//
//                    @Override
//                    public void onAdClicked(Ad ad) {
//                        // Ad clicked callback
//                    }
//
//                    @Override
//                    public void onLoggingImpression(Ad ad) {
//                        // Ad impression logged callback
//                    }
//                });
//                AdSettings.addTestDevice("e8827c98-aa2f-4e14-946d-82f32d3f5f7b");
//                interstitialAd.loadAd();

            }
        });

       //Native ad

        //AdSettings.addTestDevice("7d79a67b-f739-4005-81a3-7d14164b3b8f");
//        nativeAd.loadAd();
        //end of facebook native
    }

    private void showInterstitial()
    {
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-5468516408296733/3587572208",
                new AdRequest.Builder().build());
    }
    public void bindGridview()
    {

        new GetContacts(getActivity(), lv).execute();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void onError(Ad ad, AdError adError) {

    }

    @Override
    public void onAdLoaded(Ad ad) {

    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }

    @Override
    public void onRewardedVideoClosed() {

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        ListView mGridView;
        Activity mContex;

        public GetContacts(Activity contex, ListView gview) {
            this.mGridView=gview;
            this.mContex=contex;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        id1 = c.getString("id");
                        JSONObject snippet = c.getJSONObject("snippet");
                        // String id = c.getString("id");
                        name = snippet.getString("title");
                        // String email = c.getString("email");
                        // String address = c.getString("address");
                        // String gender = c.getString("gender");
                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("statistics");
                        subscriberCount = phone.getString("subscriberCount");
                        /*JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        // adding each child node to HashMap key => value
                        // contact.put("id", id);
                        contact.put("name", name);
                        //contact.put("email", email);
                        contact.put("subscriberCount", subscriberCount);
                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

//            thirdpage_textView.setText(subscriberCount);
//            thirdpage_textView2.setText(data2);

            SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            name1 = sp.getString("name", "0");

            int newsub = Integer.parseInt(subscriberCount);
            int oldsub = Integer.parseInt(data2);
            if (newsub > oldsub)
            {
                thirdpage_textView.setText("Thanks For Subscribing");
                thirdpage_textView1.setText(name);
                thirdpage_textView2.setText("You Earn");
                //thirdpage_textView3.setText("100");

                try {
                    submitForm();
                    submitForm1();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            else
            if (newsub < oldsub)
            {

                thirdpage_textView.setText("Oops You Unsubscribe");
                thirdpage_textView1.setText(name);
                thirdpage_textView2.setText("You Loss");
                //thirdpage_textView3.setText("100");
                try {
                    submitFormdown();
                    submitFormdown1();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else
            {

                thirdpage_textView.setText("Do Nothing");
                thirdpage_textView1.setText(name);
                thirdpage_textView2.setText("You Earn");
                //thirdpage_textView3.setText("0");
                //Toast.makeText(getActivity(), "equal" + subscriberCount + data2 + name1, Toast.LENGTH_SHORT).show();
                //txtData2.setText(subscriberCount+data2);
//                try {
//                    submitForm();
//                    submitForm1();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }

        }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submitForm() throws ParseException
    {
        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        //String clicknumber = sp.getString("gender", "0");
        x = sp.getString("gender", "0");
//        SharedPreferences sp1 = getActivity().getSharedPreferences("newclick", Context.MODE_PRIVATE);
//        x = sp1.getString("end1", clicknumber);
//        int int_rec1_cpc = Integer.parseInt(x);
//        int newearnpoint = 100+int_rec1_cpc;
        int int_rec1_earnpoint = Integer.parseInt(x);
        int int_rec1_cpc = Integer.parseInt(rec2_cpc);
        int newearnpoint = int_rec1_earnpoint+int_rec1_cpc;
        SharedPreferences sharedPreferences1=getActivity().getSharedPreferences("newclick",MODE_PRIVATE);
        SharedPreferences.Editor ed1 = sharedPreferences1.edit();
        ed1.putString("end1", Integer.toString(newearnpoint));
        ed1.commit();
        String gender = Integer.toString(newearnpoint);
        //----- IMEI Number
        String uniqueId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        //thirdpage_textView3.setText(rec2_cpc);
        //thirdpage_textView5.setText(gender);
        firebaseUser(gender,uniqueId);
        UserEarnPoint(gender,uniqueId);
    }

    private void submitForm1() throws ParseException
    {

        int int_rec1_earnpoint = Integer.parseInt(rec2_earnpoint);
        int int_rec1_cpc = Integer.parseInt(rec2_cpc);
        int newearnpoint = int_rec1_earnpoint-int_rec1_cpc;
        String gender = Integer.toString(newearnpoint);
        String uniqueId1 = rec2_uniqueid;
        //Toast.makeText(getActivity(), "rec2_uniqueid"+ rec2_uniqueid, Toast.LENGTH_SHORT).show();
        //thirdpage_textView4.setText("Channel Point  :  " + gender);
        thirdpage_textView3.setText(rec2_cpc+ " Points");
        //thirdpage_textView5.setText(gender);
        firebaseUser(gender,uniqueId1);
        UserEarnPoint(gender,uniqueId1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submitFormdown() throws ParseException
    {

        int int_rec1_earnpoint = Integer.parseInt(rec2_earnpoint);
        int int_rec1_cpc = Integer.parseInt(rec2_cpc);
        int newearnpoint = int_rec1_earnpoint+int_rec1_cpc;
        String gender = Integer.toString(newearnpoint);
        String uniqueId = rec2_uniqueid;
        //Toast.makeText(getActivity(), "rec2_uniqueid"+ rec2_uniqueid, Toast.LENGTH_SHORT).show();
        //thirdpage_textView4.setText("Channel Point  :  " + gender);
        //thirdpage_textView3.setText(rec2_cpc);
        //thirdpage_textView5.setText(gender);
        firebaseUser(gender,uniqueId);
        UserEarnPoint(gender,uniqueId);
    }

    private void submitFormdown1() throws ParseException
    {
        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String clicknumber = sp.getString("gender", "0");
        x = sp.getString("gender", "0");
//        SharedPreferences sp1 = getActivity().getSharedPreferences("newclick", Context.MODE_PRIVATE);
//        x = sp1.getString("end1", clicknumber);
//        int int_rec1_cpc = Integer.parseInt(x);
//        int newearnpoint = int_rec1_cpc-100;
        int int_rec1_earnpoint = Integer.parseInt(x);
        int int_rec1_cpc = Integer.parseInt(rec2_cpc);
        int newearnpoint = int_rec1_earnpoint-int_rec1_cpc;
        SharedPreferences sharedPreferences1=getActivity().getSharedPreferences("newclick",MODE_PRIVATE);
        SharedPreferences.Editor ed1 = sharedPreferences1.edit();
        ed1.putString("end1", Integer.toString(newearnpoint));
        ed1.commit();
        String gender = Integer.toString(newearnpoint);
        //----- IMEI Number
        String uniqueId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        thirdpage_textView3.setText(rec2_cpc + " Points");
        //thirdpage_textView5.setText(gender);

        firebaseUser(gender,uniqueId);
        UserEarnPoint(gender,uniqueId);
    }

    private void firebaseUser(final String gender, final String uniqueid) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        //progressDialog.setMessage("Adding you ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());
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
                        // Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                //Toast.makeText(getActivity(),       error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("gender", gender);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void UserEarnPoint(final String earnpoint, final String uniqueid) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        //progressDialog.setMessage("Adding you ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        //Toast.makeText(getActivity(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                //  Toast.makeText(getActivity(),                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("earnpoint", earnpoint);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }

    public void displayReceivedData1(String channelname, String channelcode, String rec1_earnpoint, String rec1_cpc, String rec1_uniqueid)
    {
        data1 = channelname;
        data2 = channelcode;
        rec2_earnpoint = rec1_earnpoint;
        rec2_cpc = rec1_cpc;
        rec2_uniqueid = rec1_uniqueid;
        Log.i("ThirdFragment", " cn: "+data1+" cid: "+data2+" ep: "+rec1_earnpoint+" "+rec1_cpc+" "+rec1_uniqueid);
        url = "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id="+data1+"&key=AIzaSyA7JJtdSVfVCfnzlFy_4BEGeQ1vSwUHd-Y";
        bindGridview();
    }

    public void facebooknativead()
    {

    }

}
