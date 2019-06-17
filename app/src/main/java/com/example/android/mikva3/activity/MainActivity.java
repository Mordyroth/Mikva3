package com.example.android.mikva3.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.mikva3.R;
import com.example.android.mikva3.api.HttpGetRequest;
import com.example.android.mikva3.model.FireBaseDBInstanceModel;
import com.example.android.mikva3.model.Room;
import com.example.android.mikva3.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends BaseActivity {

    private DatabaseReference mFireBaseDatabase;
    private ArrayList<Room> mRoomsFromServerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase mFirebaseInstance = FireBaseDBInstanceModel.getInstance().getmFirebaseInstance();
        mFireBaseDatabase = mFirebaseInstance.getReference(AppUtils.ROOM_TABLE);
        initialization();
        getDataFromServer();
    }

    private void initialization() {
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
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
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
                            result = performBackgroundTask.execute(myUrl).get();
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

    public void getDataFromServer() {
        //showProgressDialog();
        mFireBaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Room devicesEvent = datas.getValue(Room.class);
                        devicesEvent.setRoom_key(datas.getKey());
                        mRoomsFromServerList.add(devicesEvent);

                        Toast.makeText(MainActivity.this, "Data Successfully ", Toast.LENGTH_SHORT).show();
                    }
                }
                //hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read devices", error.toException());
                //hideProgressDialog();
            }
        });
    }


}
