package com.allits.escorttracker.function;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allits.escorttracker.DetailComment;
import com.allits.escorttracker.R;
import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.rest.HeadersHelper;

public class ReadEvents{
	
	private ProgressDialog pDialog;
	public final ArrayList<HashMap<Integer, String>> mCategoryList = new ArrayList<HashMap<Integer,String>>();

	 
	//php read comments script
    
    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String READ_COMMENTS_URL = "http://xxx.xxx.x.x:1234/webservice/comments.php";
    
    //testing on Emulator:
    //private static final String READ_COMMENTS_URL = "http://10.0.2.2:1234/webservice/comments.php";
    
  //testing from a real server:
	//private static final String READ_COMMENTS_URL = "http://www.allits.de/app/events.php";
  private static final String READ_COMMENTS_URL = Constants.URL_PREFIX + "events.php";
   
  //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static String TAG_PAGE = "ALL";

    
  
        
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
    
    List<NameValuePair> params;
    
    String [] from;
    int [] to;
    String [] category = new String[]{
        	"Sport","Nightlife","Reisen", "Technik"	
        };
    int [] PIC = new int[]{
        		
        		R.drawable.sport,
        		R.drawable.nightlife,
        		R.drawable.reisen,
        		R.drawable.technik
        		
        		
        		
        };
    
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    public ArrayList<HashMap<String, String>> mCommentList;
    
    private RelativeLayout Rl;
    private FragmentActivity fa;
    ListAdapter adapter;
	
	public class LoadCategories extends AsyncTask<Void, Void, Boolean>{
		
	    
		

		String urltxt;
		Activity activity;
		
		
		public LoadCategories(String s, Activity a) {
			super();
			this.activity = a;
			this.urltxt = s;
		}

		protected Boolean doInBackground(Void... params) {
			
			try {
		        
				Log.d("DoInBackGround", "Running");
		        URL url = new URL(urltxt);
		        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HeadersHelper.addHeadersToConnection(urlConnection);

		        urlConnection.setRequestMethod("GET");
		        urlConnection.setDoOutput(true);
		        urlConnection.connect();

		     
		        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+activity.getResources().getString(R.string.app_name)+"/";
		        File appdir = new File(path);
		        appdir.mkdirs();
		        
		        //File SDCardRoot = Environment.getExternalStorageDirectory();
		        File file = new File(appdir, "categorys.txt");
		        
		        Log.d("DoInBackGround", "Save File");

		        FileOutputStream fileOutput = new FileOutputStream(file);
		        InputStream inputStream = urlConnection.getInputStream();
		        int totalSize = urlConnection.getContentLength();
		        int downloadedSize = 0;


		        byte[] buffer = new byte[1024];
		        int bufferLength = 0; 

		        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

		                fileOutput.write(buffer, 0, bufferLength);

		                downloadedSize += bufferLength;


		        }
		        //close the output stream when done
		        fileOutput.close();

		//catch some possible errors...
		} catch (MalformedURLException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
			
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+activity.getResources().getString(R.string.app_name)+"/";
			File filecheck = new File(path, "categorys.txt");
			if(filecheck.exists()){
				Log.d("App","Datei existiert");
				try {
					BufferedReader in = new BufferedReader(new FileReader(path+"categorys.txt"));
					String zeile = null;
					int i = 1;

					while ((zeile = in.readLine()) != null) {
						map.put(i, zeile);
						Log.d("App",zeile);
						i++;
						
					}
					mCategoryList.add(map);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				Log.d("App","Pech");
			}
			
			
				
			
			

			
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
		    super.onPostExecute(result);
		    Log.d("Post", "Running");
		  
		}

	}

	public class LoadEvents extends AsyncTask<Void, Void, Boolean>{

			Activity activity;
			ListFragment LF;
			String TAG;
			
			public LoadEvents(Activity A, ListFragment lf, String TAG) {

				this.TAG = TAG;
				this.LF = lf;
				this.activity = A;
			}

	    	@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(activity);
				pDialog.setMessage("Loading Comments...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
	        @Override
	        protected Boolean doInBackground(Void... arg0) {
	        	//we will develop this method in version 2
	            updateJSONdata(TAG);
	         
	           /* ReadComments i = new ReadComments();
	            i.updateJSONdata(); */
	            
	            return null;

	        }


	        @Override
	        protected void onPostExecute(Boolean result) {
	            super.onPostExecute(result);
	            pDialog.dismiss();
	            updateList(activity, LF);
	           
	          
	        }
	    

	}
	
	public void updateJSONdata(String tag) {

        // Instantiate the arraylist to contain all the JSON data.
    	// we are going to use a bunch of key-value pairs, referring
    	// to the json element name, and the content, for example,
    	// message it the tag, and "I'm awesome" as the content..
		TAG_PAGE = tag;
		mCommentList = new ArrayList<HashMap<String, String>>();
		
    
	
        
        // Bro, it's time to power up the J parser 
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.
       //JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Page",TAG_PAGE));

        JSONObject json = jParser.makeHttpRequest(READ_COMMENTS_URL, "POST", params);

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
                String id = c.getString(TAG_POST_ID);
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
                

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
             
              
                
                map.put(TAG_POST_ID, id);
                map.put(TAG_USERNAME, username);
                map.put(TAG_TITLE, title);
                map.put(TAG_MESSAGE, content);
                map.put(TAG_DATUM, datum);
                map.put(TAG_AKTUSR, aktusr); 
                map.put(TAG_MAXUSR, maxusr); 
                map.put(TAG_GENDER, gender);
                map.put(TAG_ACTIVITY, activity);
                map.put(TAG_CATEGORY, category);
                
                
                //map.put(TAG_CAT, category[catid-1]);
                map.put(TAG_PIC_ID, Integer.toString(PIC[catid-1]));
             	
                // adding HashList to ArrayList
                mCommentList.add(map);
                
                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //String[] from = {TAG_CAT, TAG_POST_ID, TAG_TITLE, TAG_MESSAGE, TAG_USERNAME};
    }

    /**
     * Inserts the parsed data into our listview
     */
	public void updateList(final Activity a, ListFragment L) {
		// For a ListActivity we need to set the List Adapter, and in order to do
		//that, we need to create a ListAdapter.  This SimpleAdapter,
		//will utilize our updated Hashmapped ArrayList, 
		//use our single_post xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.

		adapter = new SimpleAdapter(a, mCommentList,
				R.layout.single_comment, new String[] {TAG_PIC_ID,TAG_CATEGORY, TAG_ACTIVITY, TAG_DATUM, TAG_AKTUSR, TAG_MAXUSR, TAG_GENDER, /*TAG_POST_ID,*/ TAG_TITLE, TAG_MESSAGE,
						TAG_USERNAME }, new int[] { R.id.imgrow, R.id.category, R.id.activity/*R.id.id*/ , R.id.datum, R.id.aktusr, R.id.maxusr, R.id.gender, /*R.id.category,*/ R.id.title, R.id.message,
						R.id.username });

		L.setListAdapter(adapter);
		// Optional: when the user clicks a list item we 
		//could do something.  However, we will choose
		//to do nothing...
		ListView lv = L.getListView();	
		// I shouldn't have to comment on this one:
			
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				int intid = (int)id;
				Toast.makeText(a, mCommentList.get(intid).get(TAG_POST_ID).toString(), Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(a, DetailComment.class);
				i.putExtra("ID", mCommentList.get(intid).get(TAG_POST_ID).toString());
				i.putExtra("TITLE", mCommentList.get(intid).get(TAG_TITLE).toString());
				i.putExtra("MESSAGE", mCommentList.get(intid).get(TAG_MESSAGE).toString());
				i.putExtra("DATUM", mCommentList.get(intid).get(TAG_DATUM).toString());
				i.putExtra("AKTUSR", mCommentList.get(intid).get(TAG_AKTUSR).toString());
				i.putExtra("MAXUSR", mCommentList.get(intid).get(TAG_MAXUSR).toString());
				i.putExtra("GENDER", mCommentList.get(intid).get(TAG_GENDER).toString());
				i.putExtra("ACTIVITY", mCommentList.get(intid).get(TAG_ACTIVITY).toString());
				i.putExtra("CATEGORY", mCommentList.get(intid).get(TAG_CATEGORY).toString());
		       // startActivity(i);
				
				
				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.

			} 
		});
	}
	
}




