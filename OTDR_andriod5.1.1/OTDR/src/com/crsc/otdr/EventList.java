package com.crsc.otdr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EventList extends Activity{
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);		
		//设置表格标题的背景颜色
		ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
		tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
		Intent intent = getIntent();  
	//	Bundle bunde = this.getIntent().getExtras();  
		
		
        ArrayList<Event> list = intent.getParcelableArrayListExtra("events"); 
		
		ListView tableListView = (ListView) findViewById(R.id.list);
		
		TableAdapter adapter = new TableAdapter(this, list);
		
		tableListView.setAdapter(adapter);
		
	}
}
