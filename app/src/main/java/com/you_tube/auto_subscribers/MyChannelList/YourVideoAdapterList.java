package com.you_tube.auto_subscribers.MyChannelList;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.you_tube.auto_subscribers.ExtraUtils.AppSingleton;
import com.you_tube.auto_subscribers.ExtraUtils.SharedPreference;
import com.you_tube.auto_subscribers.ExtraUtils.UploadData;
import com.you_tube.auto_subscribers.R;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csa on 3/7/2017.
 */

public class YourVideoAdapterList extends RecyclerView.Adapter<YourVideoAdapterList.MyHoder>{

    List<UploadData> list;
    Context context;
    SharedPreference sharedPreference;
    //SendMessage SM;
    Button btn;
    String x;
    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    AdRequest adRequest;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    SendMessage SM;
    private static final String URL_FOR_REGISTRATION = "http://livemcxrates.in/appontube/youtube_login_example/delete.php";

    public YourVideoAdapterList(List<UploadData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_list_yourvideo,parent,false);
        MyHoder myHoder = new MyHoder(view);
        adRequest = new AdRequest.Builder().build();
        return myHoder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final MyHoder holder, final int position) {
        final UploadData mylist = list.get(position);
        sharedPreference = new SharedPreference();
        holder.itemname2.setText(mylist.getCpc()+ " Points");
        String text = StringEscapeUtils.unescapeJava(mylist.getTitle());
        holder.itemname.setText(text);
        //holder.card_userpoints.setText(mylist.getEarnpoint());
        //holder.updownline.setBackground(context.getResources().getDrawable(R.color.border));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
                firebaseUser(mylist.getId());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                holder.itemView.setVisibility(View.GONE);
             //     mInterstitialAd = new InterstitialAd(context);
//                mInterstitialAd.setAdUnitId("ca-app-pub-5468516408296733/2905487914");
//                mInterstitialAd.loadAd(adRequest);
//                mInterstitialAd.setAdListener(new AdListener()
//                {
//                    public void onAdLoaded()
//                    {
//                        showInterstitial();
//                    }
//                });
            }
        });
    }
    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }

        }catch (Exception e){
        }
        return arr;
    }

    public interface SendMessage {
        void sendData(String id, String channelname, String channelcode, String earnpoint, String cpc, String uniqueid);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            SM = (SendMessage) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }


    class MyHoder extends RecyclerView.ViewHolder{
        TextView itemname,itemname1,itemname2,card_userpoints, updownline;
        ImageView updownimage;
        Button claimbonus,sharebonus;
        public View btn;


        public MyHoder(View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.card_itemname);
            //itemname1 = itemView.findViewById(R.id.card_itemname1);
            itemname2 = (TextView) itemView.findViewById(R.id.card_itemname2);
            //card_userpoints = itemView.findViewById(R.id.card_userpoints);
            //updownline =  itemView.findViewById(R.id.card_updownline);
        }
    }
    private void showInterstitial()
    {
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }
    public boolean checkFavoriteItem(UploadData product1 ) {
        boolean check = false;
        List<UploadData> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (UploadData product : favorites) {
                if (product.getId().equals(product1.getId())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    private void firebaseUser(final String end) {
        Toast.makeText(context,"Delete Channel", Toast.LENGTH_LONG).show();

        // Tag used to cancel the request
        String cancel_req_tag = "register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Log.d(TAG, "Register Response: " + response.toString());
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
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                //Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("uniqueid", uniqueid);
                params.put("end", end);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(context).addToRequestQueue(strReq, cancel_req_tag);
    }
}