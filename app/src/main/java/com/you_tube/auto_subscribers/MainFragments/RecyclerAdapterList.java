package com.you_tube.auto_subscribers.MainFragments;

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

import com.facebook.ads.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.you_tube.auto_subscribers.ExtraUtils.SharedPreference;
import com.you_tube.auto_subscribers.ExtraUtils.UploadData;
import com.you_tube.auto_subscribers.HomeActivity;
import com.you_tube.auto_subscribers.R;

import java.util.List;

//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.InterstitialAdListener;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.MyHoder>{

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
    private com.facebook.ads.InterstitialAd interstitialAd;

    public RecyclerAdapterList(List<UploadData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_list_new,parent,false);
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
        //String text = StringEscapeUtils.unescapeJava();
        holder.itemname.setText(mylist.getTitle());
        //holder.card_userpoints.setText(mylist.getEarnpoint());
        //holder.updownline.setBackground(context.getResources().getDrawable(R.color.border));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = mylist.getId();
                String channelname = mylist.getTitle();
                String channelcode = mylist.getUsernameid();
                String earnpoint = mylist.getEarnpoint();
                String cpc = mylist.getCpc();
                String uniqueid = mylist.getUniqueid();

                SM.sendData(id1,channelname,channelcode,earnpoint,cpc,uniqueid);
                ((HomeActivity) context).viewPager.setCurrentItem(1);
                if (checkFavoriteItem(list.get(position))) {
                    //Toast.makeText(context,"All Ready " + mylist.getId()+" In Your Portfolio.", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(context,"Add " + mylist.getId()+" In Your Portfolio.", Toast.LENGTH_SHORT).show();
                    sharedPreference.addFavorite(v.getContext(), list.get(position));
                }
//                mInterstitialAd = new InterstitialAd(context);
//                mInterstitialAd.setAdUnitId("ca-app-pub-1341683749178544/8152417760");
//                mInterstitialAd.loadAd(adRequest);
//                mInterstitialAd.setAdListener(new AdListener()
//                {
//                    public void onAdLoaded()
//                    {
//                        showInterstitial();
//                    }
//                });


                interstitialAd = new com.facebook.ads.InterstitialAd(context, "314547456102170_314547926102123");
                interstitialAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial displayed callback
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Toast.makeText(context,  adError.getErrorMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Show the ad when it's done loading.
                        interstitialAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                    }
                });
                //AdSettings.addTestDevice("7d79a67b-f739-4005-81a3-7d14164b3b8f");
                interstitialAd.loadAd();


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
}