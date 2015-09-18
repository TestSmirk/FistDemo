package com.example.gamedemo.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * feel nothing TODO egg pain
 * 
 * @author Smirk
 *
 */
public class MySpinnerArrayAdapter extends ArrayAdapter<String> {
	private Context context;

	public MySpinnerArrayAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

}
