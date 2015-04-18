package com.allits.escorttracker;


import android.app.Activity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.function.JSONParser;



public class DetailComment extends Activity {

    private static final String DETAILS_URL = Constants.URL_PREFIX + "event_d.php?event_id=";

    private static final String TAG_TITLE = "title";
	 private static final String TAG_POSTS = "posts";
	 private static final String TAG_POST_ID = "event_id";
	 private static final String TAG_USERNAME = "username";
	 private static final String TAG_MESSAGE = "context";
	 private static final String TAG_DATUM = "datum";
	 private static final String TAG_AKTUSR = "aktusr";
	 private static final String TAG_MAXUSR = "maxusr";
	 private static final String TAG_GENDER = "gender";
	 private static final String TAG_CATEGORY = "cname";
	 private static final String TAG_ACTIVITY = "aname";    		
	 private static final String TAG_CAT_ID = "catid";
	 private static final String TAG_PIC_ID = "0";
	
	 private JSONArray mComments = null;
    public ArrayList<HashMap<String, String>> mCommentList;
    
    public final ArrayList<HashMap<Integer, String>> mCategoryList = new ArrayList<HashMap<Integer,String>>();

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_comment);
		
		int id = getIntent().getExtras().getInt("ID");
		
		updateJSONdata(id);
		
		
	}
	
	public void updateJSONdata(int id) {

        // Instantiate the arraylist to contain all the JSON data.
    	// we are going to use a bunch of key-value pairs, referring
    	// to the json element name, and the content, for example,
    	// message it the tag, and "I'm awesome" as the content..
    	
		mCommentList = new ArrayList<HashMap<String, String>>();
		
    
	
        
        // Bro, it's time to power up the J parser 
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(DETAILS_URL+id);

        //when parsing JSON stuff, we should probably
        
        //try to catch any exceptions:
        try {
            
        	//I know I said we would check if "Posts were Avail." (success==1)
        	//before we tried to read the individual posts, but I lied...
        	//mComments will tell us how many "posts" or comments are
        	//available
            mComments = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

                //gets the content of each tag
                String username = c.getString(TAG_USERNAME);
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_MESSAGE);
                String datum = c.getString(TAG_DATUM);
                String aktusr = c.getString(TAG_AKTUSR);
                String maxusr = c.getString(TAG_MAXUSR);
                String gender = c.getString(TAG_GENDER);
                String activity = c.getString(TAG_ACTIVITY);
                String category = c.getString(TAG_CATEGORY);
                
                String cat = c.getString(TAG_CAT_ID);
                
                int catid = Integer.parseInt(cat);
                

               
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        //String[] from = {TAG_CAT, TAG_POST_ID, TAG_TITLE, TAG_MESSAGE, TAG_USERNAME};
    }

}
