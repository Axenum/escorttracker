package com.allits.escorttracker;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.allits.escorttracker.function.JSONParser;
import com.allits.escorttracker.user.UserHolder;

public class AddComment extends Activity implements OnClickListener {

    private EditText title, message, date, time, user;
    Spinner gspinner, cspinner;
    private Button mSubmit;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script

    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String POST_COMMENT_URL = "http://xxx.xxx.x.x:1234/webservice/addcomment.php";

    //testing on Emulator:
    //private static final String POST_COMMENT_URL = "http://10.0.2.2:1234/webservice/addcomment.php";

    //testing from a real server:
    private static final String POST_COMMENT_URL = "http://www.allits.de/wtd/addcomment.php";
    //private static final String POST_COMMENT_URL = "http://www.allits.de/app/addcomment.php";
    private ArrayList<HashMap<Integer, String>> mCategoryList = new ArrayList<HashMap<Integer, String>>();
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);

        mCategoryList = (ArrayList<HashMap<Integer, String>>) getIntent().getSerializableExtra("Categorys");

        addItemOnSpinnerG();
        addItemOnSpinnerC();

        Button button = (Button) findViewById(R.id.button1);
        title = (EditText) findViewById(R.id.title);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        user = (EditText) findViewById(R.id.user);
        gspinner = (Spinner) findViewById(R.id.gspinner);
        cspinner = (Spinner) findViewById(R.id.cspinner);

        message = (EditText) findViewById(R.id.context);

        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.slidedown);
                v.startAnimation(slide);


            }
        });

    }

    @Override
    public void onClick(View v) {
        new PostComment().execute();
    }

    public void addItemOnSpinnerG() {


        Spinner spinner = (Spinner) findViewById(R.id.gspinner);
        List<String> list = new ArrayList<String>();


        list.add("Männlich");
        list.add("Weiblich");
        list.add("Egal");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    public void addItemOnSpinnerC() {
        Spinner spinner = (Spinner) findViewById(R.id.cspinner);
        List<String> list = new ArrayList<String>();

        if (mCategoryList != null) {
            for (HashMap<Integer, String> map : mCategoryList) {
                for (Entry<Integer, String> entry : map.entrySet()) {
                    list.add(entry.getValue());
                }
            }
        }


        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    class PostComment extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddComment.this);
            pDialog.setMessage("Posting Comment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            //Integer spin = new Integer(cspinner.getSelectedItemPosition());
            String post_title = title.getText().toString();
            String post_date = date.getText().toString();
            String post_time = time.getText().toString();
            String post_gspinner = gspinner.getSelectedItem().toString();
            String post_cspinner = "B";
            if (cspinner.getSelectedItem() != null) {
                post_cspinner = cspinner.getSelectedItem().toString();
                post_cspinner = post_cspinner.replace("\t", "");
            }
            String post_message = message.getText().toString();
            String post_user = user.getText().toString();

            switch (post_gspinner) {
                case "Männlich":
                    post_gspinner = "M";
                    break;
                case "Weiblich":
                    post_gspinner = "W";
                    break;

                default:
                    post_gspinner = "B";
                    break;
            }


            //Retrieving Saved Username Data:


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                //params.add(new BasicNameValuePair("username", "testuer"));
//                params.add(new BasicNameValuePair("title", "testtitel"));
                //params.add(new BasicNameValuePair("date", "2014-10-01"));
                //params.add(new BasicNameValuePair("time", "03:33:12"));
                //params.add(new BasicNameValuePair("user", "5"));
                //params.add(new BasicNameValuePair("gspinner", "b"));
                //params.add(new BasicNameValuePair("cspinner", "0"));
                //params.add(new BasicNameValuePair("message", "testmessage"));

                params.add(new BasicNameValuePair("username", UserHolder.getInstance().getCurrentUser().getUserName()));
                params.add(new BasicNameValuePair("title", post_title));
                params.add(new BasicNameValuePair("date", post_date));
                params.add(new BasicNameValuePair("time", post_time));
                params.add(new BasicNameValuePair("user", post_user));
                params.add(new BasicNameValuePair("gspinner", post_gspinner));
                params.add(new BasicNameValuePair("cspinner", post_cspinner.toString()));
                params.add(new BasicNameValuePair("message", post_message));

                Log.d("cspinner!", post_cspinner);

                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                        POST_COMMENT_URL, "POST", params);


                Log.d("Test", json.getString(TAG_MESSAGE));
                // full json response
                Log.d("Post Comment attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Comment Added wa!", json.getString(TAG_MESSAGE));

                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Comment Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(AddComment.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}

