package com.example.neilsonho.airqualityindex;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class ZoneList extends ActionBarActivity {
    final List<Zone> zones = new ArrayList<Zone>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_list);
        ListView lv = (ListView) findViewById(R.id.listView2);
        new AccessWebServiceTask().execute();

        ArrayAdapter<Zone> adapter = new ArrayAdapter<Zone>(this, android.R.layout.simple_list_item_1, zones);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(ZoneList.this, RegionList.class);
                int size = zones.get(position).getRegionList().size();
                intent.putExtra("zoneArea",zones.get(position));
                startActivity(intent);
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

    //this function from the example opens and check if the urlString exists
    private InputStream OpenHttpConnection(String urlString)
            throws IOException {
        InputStream in = null;
        int response = -1;

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
    private String xmlParser() throws XmlPullParserException {
        //ignore any list and Word objects
        InputStream in = null;
        String s1 = "";
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        //List<Word> defList = null;
        try {
            in = OpenHttpConnection("http://dd.weather.gc.ca/air_quality/doc/AQHI_XML_File_List.xml");
            xpp.setInput(in, null);
            int eventType = xpp.getEventType();

            int regCount = -1 ;
            int zoneCount = -1;

            //basically the eventType parses everything in the document from top to bottom
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Word temp = new Word();
                //String tagName = xpp.getName();

                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.END_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    //this block checks if the eventType = to any <start tag>
                    //also the following the if checks eventType equals to the following tags
                    if (xpp.getName().equalsIgnoreCase("EC_administrativeZone")) {
                        Zone tempZone = new Zone(xpp.getAttributeValue(null, "name_en_CA"));
                        zones.add(tempZone);
                        zoneCount++;
                        regCount=-1;
                    } else if (xpp.getName().equalsIgnoreCase("region")) {
                        Region tempReg = new Region();
                        tempReg.set_name(xpp.getAttributeValue(null, "nameEn"));
                        zones.get(zoneCount).getRegionList().add(tempReg);
                        regCount++;
                    } else if (xpp.getName().equalsIgnoreCase("pathToCurrentObservation")) {
                        xpp.next();
                        zones.get(zoneCount).getRegionList().get(regCount).set_currentURL(xpp.getText());
                    } else if (xpp.getName().equalsIgnoreCase("pathToCurrentForecast")) {
                        xpp.next();
                        zones.get(zoneCount).getRegionList().get(regCount).set_forecastURL(xpp.getText());
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                } else if (eventType == XmlPullParser.TEXT) {
                }
                eventType = xpp.next();
            }

        } catch (IOException e) {
            //Log.d("NetworkingActivity", e.getLocalizedMessage());
        }
        return s1;//returns the string
    }

    private class AccessWebServiceTask extends
            AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            String str = "";
            try {
                str = xmlParser();
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
            }
            return str;
        }
        protected void onPostExecute(String result) {
        }
    }//end of AccessWebServiceTask class
}