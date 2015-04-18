package com.allits.escorttracker.fragments;

import com.allits.escorttracker.R;
import com.allits.escorttracker.R.layout;
import com.allits.escorttracker.function.RangeSeekBar;
import com.allits.escorttracker.function.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.allits.escorttracker.function.SaveSharedPreference;
import android.support.v4.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Sucheinstellungen extends Fragment {
	
	SaveSharedPreference ssp;
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		int radioid;
		
		rg = (RadioGroup) rootView.findViewById(R.id.rg);
		radioid = rg.getCheckedRadioButtonId();
		
		int radius = Integer.parseInt(((String) tvRadius.getText()).substring(0, 2));
		SharedPreferences pref = getActivity().getPreferences(0);
		SharedPreferences.Editor edt = pref.edit();
		edt.putInt("preferences_gender", radioid);
		edt.putInt("preferences_radius", radius);
		edt.putInt("preferences_alterMin", aMin);
		edt.putInt("preferences_alterMax", aMax);
		edt.commit(); 
		
		Toast.makeText(getActivity(), aMin+" | "+aMax, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();

	}

	View rootView;
	SharedPreferences pref;
	RadioGroup rg;
	RadioButton rb;
	SeekBar seekBar;
	TextView tvRadius;
	TextView tvAlter;
	int aMin,aMax;
	RangeSeekBar<Integer> rSeekbar;
	
	public Sucheinstellungen(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.sucheinstellungen, container, false);
        pref = getActivity().getPreferences(0);
        rg = (RadioGroup) rootView.findViewById(R.id.rg);
        
        int gender = pref.getInt("preferences_gender", 0);
        int radius = pref.getInt("preferences_radius", 50);
        int alterMin = pref.getInt("preferences_alterMin", 16);
        int alterMax = pref.getInt("preferences_alterMax", 75);

        Toast.makeText(getActivity(), alterMin+" | "+alterMax, Toast.LENGTH_LONG).show();

        tvRadius = (TextView) rootView.findViewById(R.id.tvRadius);
        seekBar = (SeekBar) rootView.findViewById(R.id.sbRadius);
        tvAlter = (TextView) rootView.findViewById(R.id.tvAlter);
        rSeekbar = new RangeSeekBar<Integer>(16, 75, getActivity());
        
        rb = (RadioButton) rg.findViewById(gender);
  
        tvRadius.setText(radius + " km");
        seekBar.setProgress(radius);
        
        tvAlter.setText(alterMin + " - " + alterMax);
        rSeekbar.setSelectedMinValue(alterMin);
        rSeekbar.setSelectedMaxValue(alterMax);
        
        Toast.makeText(getActivity(), alterMin+" | "+alterMax, Toast.LENGTH_LONG).show();
        

        
        
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			int progress = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				this.progress = progress;
				tvRadius.setText(progress + " km");
				
			}
		});
        
        rSeekbar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
					aMax = maxValue;
					aMin = minValue;
					tvAlter.setText(minValue + " - " + maxValue);
					
					Toast.makeText(getActivity(), aMin+" | "+aMax, Toast.LENGTH_LONG).show();
				
			}
		});
        
       /* ViewGroup layout = (ViewGroup) rootView.findViewById(R.id.relativeLayout4);
        layout.addView(rSeekbar);
        
        rSeekbar.setId(999); */
        
        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.relativeLayout4);
        RangeSeekBar rsb = (RangeSeekBar) rootView.findViewById(999);
        
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

        
        lp.addRule(RelativeLayout.BELOW, R.id.tvAlter);
        rSeekbar.setLayoutParams(lp);
        rl.addView(rSeekbar);
        
        return rootView;
    }
}
