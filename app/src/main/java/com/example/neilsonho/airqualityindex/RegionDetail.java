package com.example.neilsonho.airqualityindex;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class RegionDetail extends ActionBarActivity {
    AirObsIndex todayIndex =  new AirObsIndex();
    AirForeIndex forecastIndex = new AirForeIndex();
    Region reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);
        reg = getIntent().getParcelableExtra("regionName");
        //XML Pull parser
        new AccessWebServiceTask().execute("0");
        new AccessWebServiceTask().execute("1");
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
    //this function from the example opens and check if the urlString exists
    private InputStream OpenHttpConnection(String urlString)
            throws IOException {
        InputStream in = null;
        int response = 0;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            //Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    //xmlParser function
    private void xmlParser(int type) throws XmlPullParserException {
        //ignore any list and Word objects
        InputStream in = null;
        String url="";
        int forecastCount = -1;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
       if(type ==0) {
           url = reg.get_currentURLl();
       }
        else if(type==1){
           url = reg.get_forecastURL();
       }
        try {

            in = OpenHttpConnection(url);
            xpp.setInput(in, null);
            int eventType = xpp.getEventType();
            String updateDate="";

            //basically the eventType parses everything in the document from top to bottom
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                //this block checks if the eventType = to any <start tag>
                //also the following the if checks eventType equals to the following tags
                    if (xpp.getName().equalsIgnoreCase("textSummary")) {
                        String lang = xpp.getAttributeValue(null,"lang");
                        if(lang.equals("EN")) {
                            xpp.next();
                            if(type == 0) {
                                todayIndex.set_textSummary(xpp.getText());
                            }
                            else if(type == 1){
                                forecastIndex.set_textSummary(xpp.getText());
                            }
                        }//end of "EN" if
                    }//end of textSummary if
                    else if(xpp.getName().equalsIgnoreCase("period") && xpp.getAttributeValue(null,"lang").equals("EN")){
                        xpp.next();
                        forecastCount++;
                        forecastIndex.get_forecastIndexes().add(new Forecast(xpp.getText()));
                    }
                    else if (xpp.getName().equalsIgnoreCase("airQualityHealthIndex")) {
                        xpp.next();
                        if (type == 0) {
                            todayIndex.set_foreAirIndex(xpp.getText());
                        }
                        else if(type == 1){
                            forecastIndex.get_forecastIndexes().get(forecastCount).set_airIndex(xpp.getText());
                        }
                    }
                }//end of START_tag for currentObs
                eventType = xpp.next();
            }//end of eventType while loop
        } catch (IOException e) {
            //Log.d("NetworkingActivity", e.getLocalizedMessage());
        }//end of try&catch
    }//end of xmlParser method

    private class AccessWebServiceTask extends
            AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                xmlParser(Integer.parseInt(urls[0]));
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
            }
            return "";
        }
        protected void onPostExecute(String result) {
            TextView tv = (TextView)findViewById(R.id.textView);
            String forecast="";
            for(int i =0;i<forecastIndex.get_forecastIndexes().size();i++){
                if(i==0){
                    forecast+="Forecasts\n\nTonight :  "+forecastIndex.get_forecastIndexes().get(i).get_name()+
                            "\nAirIndex :  "+forecastIndex.get_forecastIndexes().get(0).get_airIndex();
                }
                else if(i==1){
                    forecast+="\n\nTomorrow :  "+forecastIndex.get_forecastIndexes().get(1).get_name()+
                            "\nAirIndex : "+forecastIndex.get_forecastIndexes().get(1).get_airIndex();
                }
            }
            if(forecast!=""){
                forecast+="\nUpdated at : "+forecastIndex.get_textSummary();
            }
            String obs = reg.getName()+
                    "\n\n\nToday\n\nAirIndex :  "+todayIndex.get_foreAirIndex() +
                    "\nUpdated at :  " +todayIndex.get_textSummary();
            tv.setText(obs+"\n\n\n" +forecast);
        }
    }//end of AccessWebServiceTask class

}


