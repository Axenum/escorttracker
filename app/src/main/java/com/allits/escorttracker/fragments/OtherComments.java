package com.allits.escorttracker.fragments;

import com.allits.escorttracker.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class OtherComments extends ReadComments {

	@Override
	public View onCreate(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Test", Toast.LENGTH_LONG).show();
		return super.onCreate(inflater, container, savedInstanceState);
		
		
		
		
	}

}
