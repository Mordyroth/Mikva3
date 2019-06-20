package com.example.android.mikva3.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.mikva3.R;
import com.example.android.mikva3.adapter.HelpStatusListAdapter;
import com.example.android.mikva3.model.FireBaseDBInstanceModel;
import com.example.android.mikva3.model.Help;
import com.example.android.mikva3.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HelpStatusListActivity extends BaseActivity{

    private ProgressDialog progressDialog;
    private List<Help> helpList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HelpStatusListAdapter mAdapter;

    private FirebaseDatabase rootRef;
    private DatabaseReference dbNextRoomHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_status_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        //mAdapter = new HelpStatusListAdapter(helpList);
        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/

        initialization();
        //showProgressBar(true);
    }

    private void initialization() {
        rootRef = FireBaseDBInstanceModel.getInstance().getmFirebaseInstance();
        dbNextRoomHelp = rootRef.getReference(AppUtils.HELP_TABLE);



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getHelpData();

            }
        });


    }


    private void getHelpData() {

        showProgressBar(true);
        helpList.clear();


        rootRef.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(AppUtils.HELP_TABLE)) {


                    dbNextRoomHelp.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    Help help = data.getValue(Help.class);
                                    helpList.add(help);

                                }


                                mAdapter = new HelpStatusListAdapter(helpList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            showProgressBar(false);
                        }
                    });
                } else {
                    helpList.clear();
                    showProgressBar(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showProgressBar(false);
            }
        });


    }
    public void showProgressBar(boolean isShow) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this, R.style.AppTheme_ProgressDialog_Theme);

        progressDialog.setCancelable(false);
        // progressDialog.setTitle("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        if (isShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}
