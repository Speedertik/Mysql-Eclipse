package com.example.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class AllPvsActivity extends ListActivity {
	 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    static ArrayList<HashMap<String, String>> pvsList;
 
    // url to get all pvs list
    private static String url_all_products = "http://172.20.217.77/mysql.php";
 
    // JSON Node names
    private static final String TAG_PVS = "pv";
    private static final String TAG_ID = "id";
    private static final String TAG_TEM = "tem";
    private static final String TAG_PRESS = "press";
    private static final String TAG_CREATE = "created_at";
    //private static final String TAG_UPDATE = "update_at";
    // pvs JSONArray
    JSONArray pvs = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pvs);
 
        // Hashmap for ListView
        pvsList = new ArrayList<HashMap<String, String>>();
 
        // Loading products in Background Thread
        new LoadAllPvs().execute();
}
        
    public static double getTagId() {
		return Double.valueOf(TAG_ID).doubleValue();
	}

	

	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
  class LoadAllPvs extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllPvsActivity.this);
            pDialog.setMessage("Loading pvs. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
             // Check your log cat for JSON reponse
            Log.d("All Pvs: ", json.toString());
            
            
            try {
                   // Getting Array of Products
                    pvs = json.getJSONArray(TAG_PVS );
                    System.out.print(pvs.length());
                    // looping through All Products
                    for (int i = 0; i < pvs.length(); i++) {
                        JSONObject c = pvs.getJSONObject(i);
                        
                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String tem = c.getString(TAG_TEM);
                        String press =c.getString(TAG_PRESS);
                        String created_at =c.getString(TAG_CREATE);
                       // String update_at =c.getString(TAG_UPDATE);
                        //System.out.print(id);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_TEM, tem);
                        map.put(TAG_PRESS, press);
                        map.put(TAG_CREATE, created_at);
                       // map.put(TAG_UPDATE, update_at);
                        // adding HashList to ArrayList
                        pvsList.add(map);
                        }                
            } catch (JSONException e) {
               e.printStackTrace();
          }
 					return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	        ListAdapter adapter = new SimpleAdapter(
                            AllPvsActivity.this, pvsList,
                            R.layout.list_item, new String[] { TAG_ID,
                                    TAG_TEM,TAG_PRESS,TAG_CREATE},
                           new int[] { R.id.id, R.id.tem ,R.id.press,R.id.created_at});
                    // updating listview
                    setListAdapter(adapter);
                   
                }
                    
                    
                
            });
            
 
        }
        
    	}
 
    }
  
