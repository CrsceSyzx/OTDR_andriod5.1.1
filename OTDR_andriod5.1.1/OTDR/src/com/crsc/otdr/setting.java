package com.crsc.otdr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class setting extends Activity{
	
	private Spinner liangchengitem,maikuanitem,bochangitem,zhouqiitem,celiangshijianitem;
	private TextView zheshelvtxt,iptxt,duankoutxt;
	private long liangcheng,maikuan,bochang,zhouqi,celiangshijian;
	private float zheshelv;
	private String IP;
	private int duankou;
	
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bunde = this.getIntent().getExtras();  
		liangcheng = bunde.getLong("liangcheng");  
		maikuan = bunde.getLong("maikuan"); 
		bochang=bunde.getLong("bochang");
		zhouqi=bunde.getLong("zhouqi");
		celiangshijian=bunde.getLong("celiangshijian");
		zheshelv=bunde.getFloat("zheshelv");
		IP=bunde.getString("IP");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		liangchengitem=(Spinner)findViewById(R.id.liangchengitem);
		 ArrayAdapter<CharSequence> adapter = 
			        ArrayAdapter.createFromResource(this, R.array.liangcheng, android.R.layout.simple_spinner_item);
			        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        liangchengitem.setAdapter(adapter);
			        liangchengitem.setPrompt("测试");
			        liangchengitem.setOnItemSelectedListener(new liangchengListener());
			        liangchengitem.setSelection(1);
		maikuanitem=(Spinner)findViewById(R.id.maikuanitem);
		 ArrayAdapter<CharSequence> adapter1 = 
			        ArrayAdapter.createFromResource(this, R.array.maikuan, android.R.layout.simple_spinner_item);
			        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
			        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        maikuanitem.setAdapter(adapter1);
			        maikuanitem.setPrompt("测试");
			        maikuanitem.setOnItemSelectedListener(new maikuanListener());
			        maikuanitem.setSelection(1);
		bochangitem=(Spinner)findViewById(R.id.bochangitem);
		 ArrayAdapter<CharSequence> adapter2 = 
			        ArrayAdapter.createFromResource(this, R.array.bochang, android.R.layout.simple_spinner_item);
			        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
			        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        bochangitem.setAdapter(adapter2);
			        bochangitem.setPrompt("测试");
			        bochangitem.setOnItemSelectedListener(new bochangListener());
			        bochangitem.setSelection(0);
		zhouqiitem=(Spinner)findViewById(R.id.zhouqiitem);		
		 ArrayAdapter<CharSequence> adapter3 = 
			        ArrayAdapter.createFromResource(this, R.array.zhouqi, android.R.layout.simple_spinner_item);
			        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
			        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        zhouqiitem.setAdapter(adapter3);
			        zhouqiitem.setPrompt("测试");
			        zhouqiitem.setOnItemSelectedListener(new zhouqiListener());
			        zhouqiitem.setSelection(4);
		celiangshijianitem=(Spinner)findViewById(R.id.celiangshijianitem);
		 ArrayAdapter<CharSequence> adapter4 = 
			        ArrayAdapter.createFromResource(this, R.array.celiangshijian, android.R.layout.simple_spinner_item);
			        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
			        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        celiangshijianitem.setAdapter(adapter4);
			        celiangshijianitem.setPrompt("测试");
			        celiangshijianitem.setOnItemSelectedListener(new celiangshijianListener());
			        celiangshijianitem.setSelection(3);
		zheshelvtxt=(TextView)findViewById(R.id.zheshelvtex);
		zheshelvtxt.setText(zheshelv+"");
		iptxt=(TextView)findViewById(R.id.iptex);
		iptxt.setText(IP);
		duankoutxt=(TextView)findViewById(R.id.duankoutex);
		
		Button setbutton = (Button) findViewById(R.id.Butre);
		setbutton.setOnClickListener(setButtonClick);
	}
	
	 class liangchengListener implements android.widget.AdapterView.OnItemSelectedListener{


	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	            liangcheng =Long.parseLong( parent.getItemAtPosition(position).toString());
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	            liangcheng=5000;
	        }
	    }
	 class maikuanListener implements android.widget.AdapterView.OnItemSelectedListener{


	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	            maikuan =Long.parseLong( parent.getItemAtPosition(position).toString());
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	            maikuan=20;
	        }
	    }
	 class bochangListener implements android.widget.AdapterView.OnItemSelectedListener{


	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	            bochang =Long.parseLong( parent.getItemAtPosition(position).toString());
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	            bochang=1550;
	        }
	    }
	 class zhouqiListener implements android.widget.AdapterView.OnItemSelectedListener{


	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	        	zhouqi =Long.parseLong( parent.getItemAtPosition(position).toString());
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	zhouqi=2000;
	        }
	    }
	 class celiangshijianListener implements android.widget.AdapterView.OnItemSelectedListener{


	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	        	celiangshijian =Long.parseLong( parent.getItemAtPosition(position).toString());
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	celiangshijian=15000;
	        }
	    }
	private OnClickListener setButtonClick = new OnClickListener() {
		public void onClick(View v){
			float xxx=Float.parseFloat(zheshelvtxt.getText().toString());
			IP=iptxt.getText().toString();
			duankou=Integer.parseInt(duankoutxt.getText().toString());
			Intent intent=new Intent();
			intent.setClass(setting.this, MainActivity.class);
			
			//Intent intentSimple = new Intent();  
			Bundle bundleSimple = new Bundle();  
			bundleSimple.putLong("liangcheng", liangcheng);  
			bundleSimple.putLong("maikuan", maikuan);  
			bundleSimple.putLong("bochang", bochang);  
			bundleSimple.putLong("zhouqi", zhouqi); 
			bundleSimple.putLong("celiangshijian", celiangshijian);  
			bundleSimple.putFloat("zheshelv", xxx);
			bundleSimple.putString("IP", IP);
			intent.putExtras(bundleSimple); 
			setting.this.startActivity(intent);
			
		}
	};
}
