package com.allits.escorttracker.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allits.escorttracker.R;
import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.function.ReadEvents;
import com.allits.escorttracker.function.ReadEvents.LoadCategories;
import com.allits.escorttracker.function.ReadEvents.LoadEvents;

public class RegisterdEvents_old extends ListFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View rootView = inflater.inflate(R.layout.read_comments, container, false);
		
		
        return rootView;
   }
	
	public void startTask(){
		
		ReadEvents SO = new ReadEvents();
        LoadCategories LCat = SO.new LoadCategories(Constants.URL_PREFIX + "categorys.txt", getActivity());
        LCat.execute();
		
		LoadEvents LEvt = SO.new LoadEvents(getActivity(), this, "OLD");
		LEvt.execute();
		

		
		//setListAdapter(adapter);
		
		// Optional: when the user clicks a list item we 
		//could do something.  However, we will choose
		//to do nothing...
		//ListView lv = getListView();
		
	}

	
	public void onResume() {
		startTask();
		super.onResume();
	}
	

}


