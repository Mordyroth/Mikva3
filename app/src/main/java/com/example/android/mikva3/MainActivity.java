package com.example.android.mikva3;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import static android.support.constraint.Constraints.TAG;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView circle = findViewById(R.id.room_image_2);
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        circle.startAnimation(mAnimation);
        callAsynchronousTask();
        ImageView circle1 = findViewById(R.id.room_image_6);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_green, getApplicationContext().getTheme()));
        } else {
            circle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_green));
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Toast.makeText(getApplicationContext() , value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        ImageView circle13 = findViewById(R.id.room_image_13);
        circle13.setVisibility(View.VISIBLE);

        ImageView circle9 = findViewById(R.id.room_image_9);
        circle9.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle9.setImageDrawable(getResources().getDrawable(R.drawable.circle_green, getApplicationContext().getTheme()));
        } else {
            circle9.setImageDrawable(getResources().getDrawable(R.drawable.circle_green));
        }

        ImageView circle10 = findViewById(R.id.room_image_10);
        circle10.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle10.setImageDrawable(getResources().getDrawable(R.drawable.circle_green, getApplicationContext().getTheme()));
        } else {
            circle10.setImageDrawable(getResources().getDrawable(R.drawable.circle_green));
        }
        circle10.startAnimation(mAnimation);

        ImageView circle4 = findViewById(R.id.room_image_4);
        circle4.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_orange, getApplicationContext().getTheme()));
        } else {
            circle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_orange));
        }
    }

    public void callAsynchronousTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String myUrl = "http://54.218.240.133/mikva/get_info.php";
                            //String to place our result in
                            String result = null;
                            HttpGetRequest performBackgroundTask = new HttpGetRequest();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                           result =  performBackgroundTask.execute(myUrl).get();
                            //TextView text_view_1 = findViewById(R.id.textView1);
                           // text_view_1.setText(result);
                            Log.v("LOGGGG", result);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000); //execute in every 50000 ms
    }
}
