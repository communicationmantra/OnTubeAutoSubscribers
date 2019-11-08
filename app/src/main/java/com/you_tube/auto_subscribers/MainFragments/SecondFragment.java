package com.you_tube.auto_subscribers.MainFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.ExtraUtils.HttpHandler;
import com.you_tube.auto_subscribers.ExtraUtils.RoundedCornersTransformation;
import com.you_tube.auto_subscribers.HomeActivity;
import com.you_tube.auto_subscribers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    private static final String TAG = "RootFragment";
    TextView channelname,subscribe,viewcount,comment,video;
    Button subscribebtn,confirmbtn;
    ImageView profileimage;
    String autoid, data1,data2,rec1_earnpoint,rec1_cpc,rec1_uniqueid;
    private ProgressDialog pDialog;
    private ListView lv;
    String name,subscriberCount,imageurl,viewCount,commentCount,videoCount;
    String clicknumber;
    String url;
    String id1;
    String x;
    //AQuery aQuery;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/firebaseid.php";
    public SendMessage1 SM1;
    ArrayList<HashMap<String, String>> contactList;
    private NativeAd nativeAd;


    public SecondFragment() {
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
        return inflater.inflate(R.layout.fragment_second_new, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactList = new ArrayList<>();
        channelname = (TextView) view.findViewById(R.id.channelname);
        subscribe = (TextView) view.findViewById(R.id.subscribe);
        viewcount = (TextView) view.findViewById(R.id.view);
        comment = (TextView) view.findViewById(R.id.comment);
        video = (TextView) view.findViewById(R.id.video);
        profileimage = (ImageView) view.findViewById(R.id.profileimage);
        //aQuery = new AQuery(getActivity());
        subscribebtn = (Button) view.findViewById(R.id.subscribebtn);
        confirmbtn = (Button) view.findViewById(R.id.confirmbtn);

//        //Native ad
//        nativeAd = new NativeAd(getActivity(), "1743331772409375_1743430849066134");
//        //nativeAd = new NativeAd(getActivity(), "1840515439334509_1840515862667800");
//        nativeAd.setAdListener(new com.facebook.ads.AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Toast.makeText(getActivity(), "Error: " + adError.getErrorMessage(),Toast.LENGTH_LONG).show();
//                Log.i("errorad",adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                View adView2 = NativeAdView.render(getActivity(), nativeAd, NativeAdView.Type.HEIGHT_120);
//                LinearLayout nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);
//                // Add the Native Ad View to your ad container
//                nativeAdContainer.addView(adView2);
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        });
//        //AdSettings.addTestDevice("7d79a67b-f739-4005-81a3-7d14164b3b8f");
//        nativeAd.loadAd();
//        //end of facebook native
//
//        //adRequest = new AdRequest.Builder().build();
    }



   

    public void bindGridview()
    {
        new GetContacts1(getActivity(), lv).execute();
    }

    public interface SendMessage1 {
        void sendData1(String id1, String subscriberCount, String channelname, String channelcode, String rec1_uniqueid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM1 = (SecondFragment.SendMessage1) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }


    private class GetContacts1 extends AsyncTask<Void, Void, Void> {
        ListView mGridView;
        Activity mContex;
        public GetContacts1(Activity contex, ListView gview) {
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
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        imageurl = medium.getString("url");

                        // String gender = c.getString("gender");
                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("statistics");
                        viewCount = phone.getString("viewCount");
                        commentCount = phone.getString("commentCount");
                        subscriberCount = phone.getString("subscriberCount");
                        videoCount = phone.getString("videoCount");

                        /*JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        // adding each child node to HashMap key => value
                        // contact.put("id", id);
                        contact.put("name", name);
                        contact.put("imageurl", imageurl);
                        contact.put("viewCount", viewCount);
                        contact.put("commentCount", commentCount);
                        contact.put("subscriberCount", subscriberCount);
                        contact.put("videoCount", videoCount);
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

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            channelname.setText(name);
            subscribe.setText(subscriberCount);
            viewcount.setText(viewCount);
            comment.setText(commentCount);
            video.setText(videoCount);
            int sCorner = 15;
            int sMargin = 2;
            Glide.with(getActivity()).load(imageurl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .bitmapTransform(new RoundedCornersTransformation( getActivity(),sCorner, sMargin))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileimage);
            //txtData2.setText(imageurl);
            //aQuery.id(profileimage).image(imageurl);
            confirmbtn.setEnabled(false);
            confirmbtn.setBackgroundResource(R.drawable.roun_rect_youtube_dis);

            subscribebtn.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    String url1 = "https://www.youtube.com/channel/"+ data2;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url1));
                    startActivity(i);

                    try {
                        submitForm();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    confirmbtn.setEnabled(true);
                    subscribebtn.setEnabled(false);
                    confirmbtn.setBackgroundResource(R.drawable.roun_rect_youtube_pad);
                    subscribebtn.setBackgroundResource(R.drawable.roun_rect_youtube_dis);
                }
            });

            confirmbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    SM1.sendData1(id1,subscriberCount,rec1_earnpoint,rec1_cpc,rec1_uniqueid);
                    ((HomeActivity) getActivity()).viewPager.setCurrentItem(2);
                    subscribebtn.setEnabled(true);
                    confirmbtn.setEnabled(false);
                    confirmbtn.setBackgroundResource(R.drawable.roun_rect_youtube_dis);
                    subscribebtn.setBackgroundResource(R.drawable.roun_rect_youtube_pad);
//                    mInterstitialAd = new InterstitialAd(getActivity());
//                    mInterstitialAd.setAdUnitId("ca-app-pub-1341683749178544/5476705158");
//                    mInterstitialAd.loadAd(adRequest);
//                    mInterstitialAd.setAdListener(new AdListener()
//                    {
//                        public void onAdLoaded()
//                        {
//                            showInterstitial();
//                        }
//                    });

                }
            });
        }
    }

    private void submitForm() throws ParseException
    {
        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        clicknumber = sp.getString("end", "0");
        int y = Integer.parseInt(clicknumber)+1;
        String end = String.valueOf(y) ;
        Toast.makeText(getActivity(), "Bonus Point :"+ end, Toast.LENGTH_SHORT).show();
        //----- IMEI Number
        String uniqueId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        firebaseUser(end,uniqueId);
    }



    private void firebaseUser(final String end, final String uniqueid) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
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
                //Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uniqueid);
                params.put("end", end);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }

    public void displayReceivedData(String id1, String channelname, String channelcode, String earnpoint, String cpc, String uniqueid)
    {
        autoid = id1;
        data1 = channelname;
        data2 = channelcode;
        rec1_earnpoint= earnpoint;
        rec1_cpc = cpc;
        rec1_uniqueid = uniqueid;
        Log.i("SecondFragment", autoid+" "+data1+" "+data2+" "+rec1_cpc+" "+rec1_uniqueid);
        url = "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id="+data2+"&key=AIzaSyA7JJtdSVfVCfnzlFy_4BEGeQ1vSwUHd-Y";
        bindGridview();

    }

}
