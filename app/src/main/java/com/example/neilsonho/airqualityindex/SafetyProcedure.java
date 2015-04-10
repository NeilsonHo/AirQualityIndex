package com.example.neilsonho.airqualityindex;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;


public class SafetyProcedure extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_procedure);
        ListView listView = (ListView) findViewById(R.id.listView);
        final String[] html_array = getResources().getStringArray(R.array.links_array);
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new myWebView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        String html = "http://www.google.ca";
        webView.loadUrl(html);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, html_array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String c = (String) parent.getItemAtPosition(position);
                int index = Arrays.asList(html_array).indexOf(c);
                webView.loadUrl(html_array[index]);
            }
        });
    }

    private class myWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.setInitialScale(10);
            view.loadUrl(url);
            return true;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_safety_procedure, menu);
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
