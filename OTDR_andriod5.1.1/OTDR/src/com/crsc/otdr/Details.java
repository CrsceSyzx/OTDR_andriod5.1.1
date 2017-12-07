package com.crsc.otdr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Details extends Activity{
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		EditText t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
		t1=(EditText)findViewById(R.id.T1);
		t2=(EditText)findViewById(R.id.T2);
		t3=(EditText)findViewById(R.id.T3);
		t4=(EditText)findViewById(R.id.T4);
		t5=(EditText)findViewById(R.id.T5);
		t6=(EditText)findViewById(R.id.T6);
		t7=(EditText)findViewById(R.id.T7);
		t8=(EditText)findViewById(R.id.T8);
		t9=(EditText)findViewById(R.id.T9);
		t10=(EditText)findViewById(R.id.T10);
		t11=(EditText)findViewById(R.id.T11);
		t12=(EditText)findViewById(R.id.T12);
		t13=(EditText)findViewById(R.id.T13);
		Bundle bunde = this.getIntent().getExtras();  
		String []Detail = bunde.getStringArray("Detail");  
		t1.setText(Detail[1]);
		t2.setText(Detail[2]);
		t3.setText(Detail[3]);
		t4.setText(Detail[4]);
		t5.setText(Detail[5]);
		t6.setText(Detail[9]);
		t7.setText(Detail[10]);
		t8.setText(Detail[6]);
		t9.setText(Detail[7]);
		t10.setText(Detail[8]);
		t11.setText(Detail[0]);
		t12.setText(Detail[11]);
		t13.setText(Detail[12]);
	}
}
