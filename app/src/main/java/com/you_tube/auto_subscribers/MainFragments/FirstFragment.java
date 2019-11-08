package com.you_tube.auto_subscribers.MainFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.gson.Gson;
import com.you_tube.auto_subscribers.BonusPoint.Bonus;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.ExtraUtils.UploadData;
import com.you_tube.auto_subscribers.PositiveStarRating.StarRatingDialog;
import com.you_tube.auto_subscribers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FirstFragment extends Fragment {
    private static final String URL_FOR_LOGIN = "http://livemcxrates.in/appontube/youtube_login_example/login.php";
    public static final String ARG_PAGE = "ARG_PAGE";
    private int n;
    TextView tutorial, bonus, showpoints,how;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    static List<UploadData> aLList;
    String uri,gender;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    private ListView lv;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager recyce;
    RecyclerView recycle;



    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    public FirstFragment() {
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
        View view = inflater.inflate(R.layout.fragment_first_newlist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
      //  MobileAds.initialize(getActivity(), "ca-app-pub-5468516408296733~1946495237");

        recycle = (RecyclerView) v.findViewById(R.id.recycle);
        tutorial = (TextView) v.findViewById(R.id.tutorial);
        showpoints = (TextView) v.findViewById(R.id.showpoints);
        bonus = (TextView) v.findViewById(R.id.bonus);
        how = (TextView) v.findViewById(R.id.how);


        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "https://www.youtube.com/watch?v=ZrcVZh3n0cY";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url1));
                startActivity(i);
            }
        });
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

        SharedPreferences sp = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String email = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String password = sp.getString("displayp", null);

        String clicknumber = sp.getString("end", "1");
        SharedPreferences sp1 = getActivity().getSharedPreferences("newclicknumber", Context.MODE_PRIVATE);
        String s1 = sp1.getString("newclicknumber", clicknumber);
        //uri = "http://www.livemcxrates.in/appontube/youtubevideo_login_example/files/get_hint.php?first="+s1;
        uri = "http://www.livemcxrates.in/appontube/get_hint_all_channel_new.php";
        requestData(uri);
        loginUser(email.toString().trim(),
                password.toString().trim());



        mPublisherInterstitialAd = new PublisherInterstitialAd(getActivity());
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-5468516408296733/8005471274");//AS2019_second
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        mPublisherInterstitialAd.setAdListener(new AdListener()
        {
            public void onAdLoaded()
            {
                showInterstitial();
            }
        });

    }



    private void showInterstitial()
    {
        if (mPublisherInterstitialAd.isLoaded()) {
            mPublisherInterstitialAd.show();
        } else {
            Log.d("ad_home1", "The interstitial wasn't loaded yet.");
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

    public void requestData (String uri) {
        //getting the progressbar

        //making the progressbar visible


        StringRequest request = new StringRequest(uri, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //hiding the progressbar after completion


                //progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONArray jsonArray = null;
                    UploadData liverates1 = null;
                    jsonArray = new JSONArray(response);
                    aLList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        liverates1 = new UploadData();
                        String id = obj.getString("usernameid");
                        SharedPreferences settings;
                        settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        String jsonFavorites = settings.getString(FAVORITES, null);
                        Gson gson = new Gson();
                        UploadData[] favoriteItems = gson.fromJson(jsonFavorites, UploadData[].class);
                        if (favoriteItems != null) {
                            n = 0;
                            for (UploadData info : favoriteItems) {
                                if (info.getUsernameid().equals(id)) {

                                    n = 1;
                                    break;
                                }
                            }
                            if (n == 0) {
                                liverates1.setId(obj.getString("id"));
                                liverates1.setUniqueid(obj.getString("uniqueid"));
                                liverates1.setTitle(obj.getString("title"));
                                liverates1.setUsernameid(obj.getString("usernameid"));
                                liverates1.setCpc(obj.getString("cpc"));
                                liverates1.setCountry(obj.getString("country"));
                                liverates1.setEarnpoint(obj.getString("earnpoint"));
                                liverates1.setTotalclick(obj.getString("totalclick"));
                                liverates1.setYorn(obj.getString("yorn"));
                                aLList.add(liverates1);
                            }

                        } else {
                            liverates1.setId(obj.getString("id"));
                            liverates1.setUniqueid(obj.getString("uniqueid"));
                            liverates1.setTitle(obj.getString("title"));
                            liverates1.setUsernameid(obj.getString("usernameid"));
                            liverates1.setCpc(obj.getString("cpc"));
                            liverates1.setCountry(obj.getString("country"));
                            liverates1.setEarnpoint(obj.getString("earnpoint"));
                            liverates1.setTotalclick(obj.getString("totalclick"));
                            liverates1.setYorn(obj.getString("yorn"));
                            aLList.add(liverates1);
                        }

                    }
                    //aAlist = FavouriteJsonParser.parseData(response);

                    try {
                        RecyclerAdapterList recyclerAdapter1 = new RecyclerAdapterList(aLList, getActivity());
                        //RecyclerView.LayoutManager recyce = new GridLayoutManager(getActivity(), 3);
                        recyce = new LinearLayoutManager(getActivity());
                        //recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        recycle.setHasFixedSize(true);
                        recycle.setLayoutManager(recyce);
                        recycle.setItemAnimator(new DefaultItemAnimator());
                        recyce.setAutoMeasureEnabled(false);

                        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                visibleItemCount = recycle.getChildCount();
                                totalItemCount = recyce.getItemCount();
                                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                                if (loading) {
                                    if (totalItemCount > previousTotal) {
                                        loading = false;
                                        previousTotal = totalItemCount;
                                    }
                                }
                                if (!loading && (totalItemCount - visibleItemCount)
                                        <= (firstVisibleItem + visibleThreshold)) {
                                    loading = true;
                                }
                            }
                        });
                        recycle.setAdapter(recyclerAdapter1);
                        recyclerAdapter1.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                }
            }
        },

                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                                         //Toast.makeText(getActivity().getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);
    }

    private void loginUser(final String uniqueid, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        // progressDialog.setMessage("Logging you in...");
        //showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String user = jObj.getJSONObject("user").getString("name");
                        String email = jObj.getJSONObject("user").getString("email");
                        gender = jObj.getJSONObject("user").getString("gender");
                        String displayp = jObj.getJSONObject("user").getString("displayp");
                        String age = jObj.getJSONObject("user").getString("age");
                        String entry = jObj.getJSONObject("user").getString("entry");
                        String end = jObj.getJSONObject("user").getString("end");
                        String contact = jObj.getJSONObject("user").getString("contact");

                        showpoints.setText(gender);
                        //sharepoint.setText(entry);
                        //bonuspoint.setText(end);
                        int end_int = Integer.parseInt(end);
                        if(end_int>50)
                        {
                            bonus.setText("Earn Bonus");
                            bonus.setBackgroundResource(R.drawable.roun_rect_gray);
                            bonus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), Bonus.class);
                                    startActivity(i);
                                    //allgroups();
                                }
                            });
//                            claimbonus.setEnabled(true);
//                            claimbonus.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else
                        {
                            //int remaining = 50 - end_int;
                            bonus.setText("Bonus : "+end);
                            bonus.setBackgroundResource(R.drawable.roun_rect_gray_youtube);
//                            claimbonus.setEnabled(false);
//                            claimbonus.setBackgroundColor(Color.GRAY);
                        }

                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sharedPreferences.edit();
                        ed.putString("name",user);
                        ed.putString("email",email);
                        ed.putString("gender",gender);
                        ed.putString("displayp",displayp);
                        ed.putString("age",age);
                        ed.putString("entry",entry);
                        ed.putString("end",end);
                        ed.putString("contact",contact);
                        //ed.putString("pass","123");
                        ed.commit();

                        // Launch User activity

                    } else {

                        String errorMsg = jObj.getString("error_msg");


                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),"Please Check Internet Connection"
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
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }


}
