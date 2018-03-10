package com.example.senikaru.tangooboe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.senikaru.tangooboe.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ArrayList<String> chp_name;
    ListView listview;
    String chp_id;
    Context context;
    ChapterListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.tango_list) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        Intent intent= getIntent();
        chp_id=intent.getStringExtra("chap");
        context=this;

        listview=(ListView) findViewById(R.id.item_list);

        //Database
        chp_name=new ArrayList<String>();
        DBManager dbManager=new DBManager(this);
        chp_name=dbManager.getChapName(chp_id);

        adapter=new ChapterListAdapter(this, R.layout.item_list_content, chp_name);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(mTwoPane){
            ArrayList<HashMap<String, String>> dataArr;
            GridView listview;
            Tango_Adapter adapter;
            listview=(GridView) findViewById(R.id.tango_list);

            dataArr=new ArrayList<HashMap<String, String>>();
            DBManager dbManager=new DBManager(this);
            dataArr=dbManager.getChap(chp_id+Integer.toString(i+1));

            adapter=new Tango_Adapter(this, R.layout.tango_item, dataArr);
            listview.setAdapter(adapter);

        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);

            intent.putExtra("chp_id", chp_id+Integer.toString(i+1));

            context.startActivity(intent);
        }
    }

}
