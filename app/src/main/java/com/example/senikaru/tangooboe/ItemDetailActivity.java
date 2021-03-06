package com.example.senikaru.tangooboe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    Context context;
    String chp_id;
    Boolean mine;
    ArrayList<HashMap<String, String>> dataArr;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        context=this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent it_test =new Intent(context, TestSettingActivity.class);
                it_test.putExtra("chp_id", chp_id);
                it_test.putExtra("Mine", mine);
                startActivity(it_test);
            }
        });


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        Intent intent=getIntent();
        chp_id=intent.getStringExtra("chp_id");
        mine=intent.getBooleanExtra("Mine", true);

        dataArr=new ArrayList<HashMap<String, String>>();
        dbManager=new DBManager(this);
        if(mine)
            dataArr=dbManager.getMine(chp_id);
        else
            dataArr=dbManager.getChap(chp_id);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tango_list);
        recyclerView.setHasFixedSize(true);
        TangoRecyclerViewAdapter tangoRecyclerViewAdapter=new TangoRecyclerViewAdapter(dataArr, R.layout.tango_item);
        tangoRecyclerViewAdapter.setContext(context);
        recyclerView.setAdapter(tangoRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setTitle(dbManager.getChapTitle(chp_id));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mine)
            dataArr=dbManager.getMine(chp_id);
        else
            dataArr=dbManager.getChap(chp_id);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tango_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TangoRecyclerViewAdapter(dataArr, R.layout.tango_item));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setTitle(dbManager.getChapTitle(chp_id));
    }
}
