package com.you_tube.auto_subscribers.MyChannelList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.you_tube.auto_subscribers.ExtraUtils.UploadData;
import com.you_tube.auto_subscribers.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YourVideoFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int n;
    TextView tutorial,bonus;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    static List<UploadData> aLList,listx;
    String uri,url1;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private ListView lv;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager recyce;
    RecyclerView recycle,recyclegrid;


    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public YourVideoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_yourlist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        recycle = (RecyclerView) v.findViewById(R.id.recycle);
        String s1 = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        //uri = "http://www.livemcxrates.in/appontube/youtubevideo_login_example/files/get_hint.php?first="+s1;
        uri = "http://www.livemcxrates.in/appontube/get_channel_list.php?first="+s1;
       requestData(uri);

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
        StringRequest request = new StringRequest(uri, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = null;
                    UploadData liverates1 = null;
                    jsonArray = new JSONArray(response);
                    aLList = new ArrayList<>();



                    for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject obj = jsonArray.getJSONObject(i);
                        liverates1 = new UploadData();

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
                    //aAlist = FavouriteJsonParser.parseData(response);

                    try {
                        YourVideoAdapterList recyclerAdapter1 = new YourVideoAdapterList(aLList, getActivity());
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


}
