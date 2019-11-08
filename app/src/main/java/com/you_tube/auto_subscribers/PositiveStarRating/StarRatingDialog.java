package com.you_tube.auto_subscribers.PositiveStarRating;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.you_tube.auto_subscribers.R;

public class StarRatingDialog extends Activity {
    EditText editgrouplink;
    TextView titleTextView,addgrpbtn1;
    Button cancel;
    Spinner catspinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starrating);

        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        addgrpbtn1 = (TextView) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);

        addgrpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(StarRatingDialog.this,"hello",Toast.LENGTH_LONG).show();
                float rate = simpleRatingBar.getRating();

                if(rate <= 3.5)
                {
                    Intent i = new Intent(StarRatingDialog.this, StarRatingFeedBack.class);
                    i.putExtra("rating", ""+simpleRatingBar.getRating());
                    startActivity(i);
                    Toast.makeText(StarRatingDialog.this,"rate : "+ rate, Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        Uri uri = Uri.parse("market://details?id="+getPackageName()+"");
                        Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goMarket);
                    }catch (Exception e){
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()+"");
                        Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goMarket);
                    }
                }
                finish();
                //allgroups();
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


}
//Android | PHP | MySql - Save emoji to Mysql server from Android ...