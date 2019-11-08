package com.you_tube.auto_subscribers;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.you_tube.auto_subscribers.ChannelPlus.Addgroupss;
import com.you_tube.auto_subscribers.ExtraUtils.ViewPagerAdapter;
import com.you_tube.auto_subscribers.MainFragments.RecyclerAdapterList;
import com.you_tube.auto_subscribers.MainFragments.SecondFragment;
import com.you_tube.auto_subscribers.MainFragments.ThirdFragment;
import com.you_tube.auto_subscribers.PositiveStarRating.StarRatingDialog;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , RecyclerAdapterList.SendMessage ,SecondFragment.SendMessage1
{
    private TabLayout mTabLayout;
    public ViewPager viewPager,viewPager1;
    ImageView home,list,adjust,adver;
    private PublisherInterstitialAd mPublisherInterstitialAd;

    private FirebaseAnalytics mFirebaseAnalytics;

    private int[] mTabsIcons = {
            R.drawable.home,
            R.drawable.profile,
            R.drawable.bell,
            R.drawable.adjust};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this, "ca-app-pub-5468516408296733~1946495237");


        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-5468516408296733/1527036290");//AutoSubscribers2019
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        mPublisherInterstitialAd.setAdListener(new AdListener()
        {
            public void onAdLoaded()
            {
                showInterstitial();
            }
        });

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



        adver = findViewById(R.id.adver);
        adver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("market://details?id=com.crystalonweb.You_Tube_Video_Liker");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }catch (Exception e){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.crystalonweb.You_Tube_Video_Liker");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }
            }
        });
        home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
//                Snackbar.make(view, "Powered By: Trading Mantra's Live Price API ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        list = (ImageView) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(4);
//                Snackbar.make(view, "Powered By: Trading Mantra's Live Price API ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        adjust = (ImageView) findViewById(R.id.adjust);
        adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
//                Snackbar.make(view, "Powered By: Trading Mantra's Live Price API ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, Addgroupss.class);
                //Intent i = new Intent(HomeActivity.this, StarRatingDialog.class);
                startActivity(i);
//                Snackbar.make(view, "Powered By: Trading Mantra's Live Price API ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        // Setup the viewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
//        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//        if (viewPager != null)
//            viewPager.setAdapter(pagerAdapter);
//
//        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        if (mTabLayout != null) {
//           mTabLayout.setupWithViewPager(viewPager);
//
//            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
//                TabLayout.Tab tab = mTabLayout.getTabAt(i);
//                if (tab != null)
//                    tab.setCustomView(pagerAdapter.getTabView(i));
//            }
//
//            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
//        }
    }

    private void showInterstitial()
    {
        if (mPublisherInterstitialAd.isLoaded()) {
            mPublisherInterstitialAd.show();
        } else {
            Log.d("ad_home", "The interstitial wasn't loaded yet.");
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void sendData(String id, String channelname, String channelcode, String earnpoint, String cpc, String uniqueid) {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        SecondFragment f = (SecondFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(id, channelname,channelcode,earnpoint,cpc,uniqueid);
    }

    @Override
    public void sendData1(String channelname, String channelcode, String rec1_earnpoint, String rec1_cpc, String rec1_uniqueid) {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 2;
        ThirdFragment f = (ThirdFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData1(channelname,channelcode,rec1_earnpoint,rec1_cpc,rec1_uniqueid);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.star_on)
                .setTitle("Give 5 Star Rating")
                .setMessage("Are you sure you want to close this App?")
                .setNegativeButton("No", null)
                .setNeutralButton("Give 5 Star rating", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(HomeActivity.this, StarRatingDialog.class);
                        startActivity(i);

                    }

                })
                .setPositiveButton("exit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })

                .show();
    }
}
