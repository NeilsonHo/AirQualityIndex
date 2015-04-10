package com.example.neilsonho.airqualityindex;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class RegionList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_list);
        ListView lv = (ListView) findViewById(R.id.listView);
        final Zone zoneArea = getIntent().getParcelableExtra("zoneArea");
        //String [] strNames = getIntent().getStringArrayExtra("regionNames");

        //ArrayList<String> list = strNames;
        ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(this, android.R.layout.simple_list_item_1,zoneArea.getRegionList());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent i = new Intent(RegionList.this,RegionDetail.class);
                i.putExtra("regionName", zoneArea.getRegionList().get(position));
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_about){
            Intent i = new Intent(this,About.class);
            startActivity(i);
        }
        else if(id==R.id.action_help){
            Intent i = new Intent(this,Help.class);
            startActivity(i);
        }
        else if(id==R.id.action_home){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
