package com.example.android.mikva3.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.mikva3.R;
import com.example.android.mikva3.adapter.HelpStatusListAdapter;
import com.example.android.mikva3.model.FireBaseDBInstanceModel;
import com.example.android.mikva3.model.Help;
import com.example.android.mikva3.model.NextRoomHelp;
import com.example.android.mikva3.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class RoomListActivity extends BaseActivity {


    private DatabaseReference mFireBaseDatabase;
    private List<Help> mHelpsFromServerList = new ArrayList<>();

    private LinearLayout llRoom1;
    private RelativeLayout llRoom2;
    private LinearLayout llRoom3;
    private LinearLayout llRoom4;
    private LinearLayout llRoom5;
    private LinearLayout llRoom6;
    private LinearLayout llRoom7;
    private LinearLayout llRoom8;
    private LinearLayout llRoom9;
    private LinearLayout llRoom10;
    private LinearLayout llRoom11;
    private LinearLayout llRoom12;
    private LinearLayout llRoom13;
    private LinearLayout llRoom14;
    private RelativeLayout llRoom15;
    private RelativeLayout llRoom16;
    private RelativeLayout llRoom17;
    private LinearLayout llRoom18;
    private LinearLayout llRoom19;
    private LinearLayout llRoom20;

    private ImageView ivRoom1;
    private ImageView ivRoom2;
    private ImageView ivRoom3;
    private ImageView ivRoom4;
    private ImageView ivRoom5;
    private ImageView ivRoom6;
    private ImageView ivRoom7;
    private ImageView ivRoom8;
    private ImageView ivRoom9;
    private ImageView ivRoom10;
    private ImageView ivRoom11;
    private ImageView ivRoom12;
    private ImageView ivRoom13;
    private ImageView ivRoom14;
    private ImageView ivRoom15;
    private ImageView ivRoom16;
    private ImageView ivRoom17;
    private ImageView ivRoom18;
    private ImageView ivRoom19;
    private ImageView ivRoom20;

    private List<NextRoomHelp> mNextRoomHelpList = new ArrayList<>();
    private FirebaseDatabase rootRef;
    private DatabaseReference dbNextRoomHelp;
    private String mDate;
    private TextView tvRoomHelp;
    private ProgressDialog progressDialog;
    private ImageView ivLanguage, ivList;
    private AVLoadingIndicatorView avi;


    //private List<Help> helpList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HelpStatusListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room_layout1);
        // mBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_layout1);

        mDate = AppUtils.getDate();
        bindViews();
        initialization();
        AppUtils.hideKeyboard(this);
        setListener();


    }

    private void setListener() {
        ivLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.setLanguage(RoomListActivity.this);
            }
        });


    }


    private void bindViews() {

        //LinearLayout
        llRoom1 = findViewById(R.id.llRoom1);
        llRoom2 = findViewById(R.id.llRoom2);
        llRoom3 = findViewById(R.id.llRoom3);
        llRoom4 = findViewById(R.id.llRoom4);
        llRoom5 = findViewById(R.id.llRoom5);
        llRoom6 = findViewById(R.id.llRoom6);
        llRoom7 = findViewById(R.id.llRoom7);
        llRoom8 = findViewById(R.id.llRoom8);
        llRoom9 = findViewById(R.id.llRoom9);
        llRoom10 = findViewById(R.id.llRoom10);
        llRoom11 = findViewById(R.id.llRoom11);
        llRoom12 = findViewById(R.id.llRoom12);
        llRoom13 = findViewById(R.id.llRoom13);
        llRoom14 = findViewById(R.id.llRoom14);
        llRoom15 = findViewById(R.id.llRoom15);
        llRoom16 = findViewById(R.id.llRoom16);
        llRoom17 = findViewById(R.id.llRoom17);
        llRoom18 = findViewById(R.id.llRoom18);


        ivRoom1 = findViewById(R.id.ivRoom1);
        ivRoom2 = findViewById(R.id.ivRoom2);
        ivRoom3 = findViewById(R.id.ivRoom3);
        ivRoom4 = findViewById(R.id.ivRoom4);
        ivRoom5 = findViewById(R.id.ivRoom5);
        ivRoom6 = findViewById(R.id.ivRoom6);
        ivRoom7 = findViewById(R.id.ivRoom7);
        ivRoom8 = findViewById(R.id.ivRoom8);
        ivRoom9 = findViewById(R.id.ivRoom9);
        ivRoom10 = findViewById(R.id.ivRoom10);
        ivRoom11 = findViewById(R.id.ivRoom11);
        ivRoom12 = findViewById(R.id.ivRoom12);
        ivRoom13 = findViewById(R.id.ivRoom13);
        ivRoom14 = findViewById(R.id.ivRoom14);
        ivRoom15 = findViewById(R.id.ivRoom15);
        ivRoom16 = findViewById(R.id.ivRoom16);
        ivRoom17 = findViewById(R.id.ivRoom17);
        ivRoom18 = findViewById(R.id.ivRoom18);

        avi = findViewById(R.id.avi);


        ivLanguage = findViewById(R.id.ivLanguage);
        ivList = findViewById(R.id.ivList);
        tvRoomHelp = findViewById(R.id.tvRoomHelp);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initialization() {
        rootRef = FireBaseDBInstanceModel.getInstance().getmFirebaseInstance();
        mFireBaseDatabase = rootRef.getReference(AppUtils.HELP_TABLE);
        dbNextRoomHelp = rootRef.getReference(AppUtils.NEXT_ROOM_HELP);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getHelpDataList();

            }
        });
    }

    private void getHelpData() {

        showProgressBar(true);
        mNextRoomHelpList.clear();


        rootRef.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(AppUtils.NEXT_ROOM_HELP)) {


                    dbNextRoomHelp.child(mDate).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    NextRoomHelp help = data.getValue(NextRoomHelp.class);
                                    mNextRoomHelpList.add(help);

                                }


                                setNextRoomData();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            showProgressBar(false);
                        }
                    });
                } else {
                    mNextRoomHelpList.clear();
                    showProgressBar(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showProgressBar(false);
            }
        });


    }

    private void setNextRoomData() {
        int size = mNextRoomHelpList.size();
        int minValue = 0;
        String mRoomNumber = "";
        for (int i = 0; i < size; i++) {

            if (mNextRoomHelpList.get(i).getHelp_status().equalsIgnoreCase(Help.HELP_PRESS)) {

                if (minValue == 0) {
                    minValue = mNextRoomHelpList.get(i).getHelp_count_number();
                    mRoomNumber = mNextRoomHelpList.get(i).getRoom_key();
                } else {

                    if (minValue > mNextRoomHelpList.get(i).getHelp_count_number()) {
                        minValue = mNextRoomHelpList.get(i).getHelp_count_number();
                        mRoomNumber = mNextRoomHelpList.get(i).getRoom_key();
                    }

                }
            }

            if (i == (size - 1)) {
                if (mRoomNumber != null && !mRoomNumber.equalsIgnoreCase("")) {
                    tvRoomHelp.setVisibility(View.VISIBLE);
                    tvRoomHelp.setText(getString(R.string.next_room_help) + mRoomNumber);
                    setOnlyNext(mRoomNumber);


                } else {
                    tvRoomHelp.setVisibility(View.INVISIBLE);
                }

                showProgressBar(false);
            }

        }

        showProgressBar(false);
    }


    public void getHelpDataList() {

        showProgressBar(true);


        rootRef.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(AppUtils.HELP_TABLE)) {
                    mHelpsFromServerList.clear();
                    final List<Help> helpsFromServerList = new ArrayList<>();

                    mFireBaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                    Help help = datas.getValue(Help.class);
                                    mHelpsFromServerList.add(help);


                                    if (Help.HELP_PRESS.equalsIgnoreCase(help.getStatus()) || Help.READY_PRESS.equalsIgnoreCase(help.getStatus())) {
                                        helpsFromServerList.add(help);
                                    }
                                    //  Toast.makeText(RoomListActivity.this, "Data Successfully ", Toast.LENGTH_SHORT).show();
                                }


                                getHelpData();
                                setData();
                                setRecyclerViewData(helpsFromServerList);

                            } else {

                                recyclerView.setVisibility(View.GONE);
                            }

                            showProgressBar(false);
                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                            showProgressBar(false);
                            Log.e(TAG, "Failed to read devices", error.toException());
                            //hideProgressDialog();
                        }
                    });
                } else {
                    mHelpsFromServerList.clear();
                    showProgressBar(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showProgressBar(false);
            }
        });


    }

    private void setRecyclerViewData(List<Help> helpsFromServerList) {


        mAdapter = new HelpStatusListAdapter(helpsFromServerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void setData() {

        if (mHelpsFromServerList.size() > 0) {

            for (int i = 0; i < mHelpsFromServerList.size(); i++) {


                switch (mHelpsFromServerList.get(i).getRoom_key()) {
                    case "1":


                        setStatus(ivRoom1, llRoom1, mHelpsFromServerList.get(i).getStatus());

                        break;

                    case "2":
                        setStatus(ivRoom2, llRoom2, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "3":
                        setStatus(ivRoom3, llRoom3, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "4":
                        setStatus(ivRoom4, llRoom4, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "5":
                        setStatus(ivRoom5, llRoom5, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "6":

                        setStatus(ivRoom6, llRoom6, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "7":

                        setStatus(ivRoom7, llRoom7, mHelpsFromServerList.get(i).getStatus());
                        break;

                    case "8":

                        setStatus(ivRoom8, llRoom8, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "9":

                        setStatus(ivRoom9, llRoom9, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "10":

                        setStatus(ivRoom10, llRoom10, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "11":

                        setStatus(ivRoom11, llRoom11, mHelpsFromServerList.get(i).getStatus());
                        break;

                    case "12":

                        setStatus(ivRoom12, llRoom12, mHelpsFromServerList.get(i).getStatus());
                        break;

                    case "13":

                        setStatus(ivRoom13, llRoom13, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "14":

                        setStatus(ivRoom14, llRoom14, mHelpsFromServerList.get(i).getStatus());
                        break;


                    case "15":

                        setStatus(ivRoom15, llRoom15, mHelpsFromServerList.get(i).getStatus());
                        break;

                    case "16":

                        setStatus(ivRoom16, llRoom16, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "17":

                        setStatus(ivRoom17, llRoom17, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "18":

                        setStatus(ivRoom18, llRoom18, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "19":

                        setStatus(ivRoom19, llRoom19, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "20":

                        setStatus(ivRoom20, llRoom20, mHelpsFromServerList.get(i).getStatus());
                        break;
                   /* case "21":
                         ivRoom21.setVisibility(View.VISIBLE);
                        setStatus( ivRoom21,  llRoom21, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "22":
                        mBinding.row4.ivRoom22.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom22, mBinding.row4.llRoom22, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "23":
                        mBinding.row4.ivRoom23.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom23, mBinding.row4.llRoom23, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "24":
                        mBinding.row4.ivRoom24.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom24, mBinding.row4.llRoom24, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "25":
                        mBinding.row4.ivRoom25.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom25, mBinding.row4.llRoom25, mHelpsFromServerList.get(i).getStatus());
                        break;
*/

                }


            }
            showProgressBar(false);
        }
    }


    private void setOnlyNext(String roomKey) {


        switch (roomKey) {
            case "1":


                setStatus(ivRoom1, llRoom1);

                break;

            case "2":
                setStatus(ivRoom2, llRoom2);
                break;
            case "3":
                setStatus(ivRoom3, llRoom3);
                break;
            case "4":
                setStatus(ivRoom4, llRoom4);
                break;
            case "5":
                setStatus(ivRoom5, llRoom5);
                break;
            case "6":

                setStatus(ivRoom6, llRoom6);
                break;
            case "7":

                setStatus(ivRoom7, llRoom7);
                break;

            case "8":

                setStatus(ivRoom8, llRoom8);
                break;
            case "9":

                setStatus(ivRoom9, llRoom9);
                break;
            case "10":

                setStatus(ivRoom10, llRoom10);
                break;
            case "11":

                setStatus(ivRoom11, llRoom11);
                break;

            case "12":

                setStatus(ivRoom12, llRoom12);
                break;

            case "13":

                setStatus(ivRoom13, llRoom13);
                break;
            case "14":

                setStatus(ivRoom14, llRoom14);
                break;


            case "15":

                setStatus(ivRoom15, llRoom15);
                break;

            case "16":

                setStatus(ivRoom16, llRoom16);
                break;
            case "17":

                setStatus(ivRoom17, llRoom17);
                break;
            case "18":

                setStatus(ivRoom18, llRoom18);
                break;
            case "19":

                setStatus(ivRoom19, llRoom19);
                break;
            case "20":

                setStatus(ivRoom20, llRoom20);
                break;
                   /* case "21":
                         ivRoom21.setVisibility(View.VISIBLE);
                        setStatus( ivRoom21,  llRoom21, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "22":
                        mBinding.row4.ivRoom22.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom22, mBinding.row4.llRoom22, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "23":
                        mBinding.row4.ivRoom23.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom23, mBinding.row4.llRoom23, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "24":
                        mBinding.row4.ivRoom24.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom24, mBinding.row4.llRoom24, mHelpsFromServerList.get(i).getStatus());
                        break;
                    case "25":
                        mBinding.row4.ivRoom25.setVisibility(View.VISIBLE);
                        setStatus(mBinding.row4.ivRoom25, mBinding.row4.llRoom25, mHelpsFromServerList.get(i).getStatus());
                        break;
*/

        }


    }

    private void setStatus(ImageView imageView, View linearLayout) {
        imageView.setVisibility(View.VISIBLE);
        setHelp(imageView, linearLayout);
    }

    public void setStatus(ImageView imageView, View linearLayout, String status) {


        if (status.equalsIgnoreCase(Help.HELP_PRESS)) {
            /*imageView.setVisibility(View.VISIBLE);
            setHelp(imageView, linearLayout);*/

        } else if (status.equalsIgnoreCase(Help.READY_PRESS)) {
            imageView.setVisibility(View.VISIBLE);

            setRedy(imageView, linearLayout);

        } else if (status.equalsIgnoreCase(Help.DONE)) {
            imageView.setVisibility(View.GONE);
            setDone(imageView, linearLayout);
        } else {
            imageView.setVisibility(View.GONE);
        }


    }

    public void setHelp(ImageView imageView, View linearLayout) {
        linearLayout.setBackgroundResource(R.drawable.room_background);
        imageView.setImageDrawable(ContextCompat.getDrawable(RoomListActivity.this, R.drawable.circle_red));
        setAnimation(imageView, 1);


    }


    public void setRedy(ImageView imageView, View linearLayout) {
        linearLayout.setBackgroundResource(R.drawable.room_background);
        imageView.setImageDrawable(ContextCompat.getDrawable(RoomListActivity.this, R.drawable.circle_green));
        setAnimation(imageView, 1);


    }


    public void setDone(ImageView imageView, View linearLayout) {
        getHelpData();
        imageView.setVisibility(View.GONE);
        linearLayout.setBackgroundResource(R.drawable.blue_squere);
        setAnimation(imageView, 0);


    }

    private void setAnimation(ImageView imageView, int animation) {

        Animation mAnimation = new AlphaAnimation(animation, 0);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(mAnimation);


    }

    /*public void showProgressBar(boolean isShow) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this, R.style.AppTheme_ProgressDialog_Theme);

        progressDialog.setCancelable(false);
        // progressDialog.setTitle("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        if (isShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }*/


    public void showProgressBar(boolean isProgress) {
        if (isProgress) {
            startAnim();
        } else {
            stopAnim();
        }
    }


    void startAnim() {
        avi.setVisibility(View.VISIBLE);
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim() {
        avi.setVisibility(View.GONE);
        avi.hide();
        // or avi.smoothToHide();
    }
}
