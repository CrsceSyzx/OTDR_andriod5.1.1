package com.crsc.otdr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private boolean isConnecting = false;
	private Thread mThreadClient = null;
	private Socket mSocketClient = null;
	private boolean isTesting=false; 
	long liangcheng =5000;  
	long maikuan=20; 
	long bochang=1310;
	long zhouqi=2000;
	long celiangshijian=15000;
	ArrayList<Event> list ;
	private String IP="192.168.1.249";
	private float zheshelv=1.4685F;
	static PrintWriter mPrintWriterClient = null;
	static BufferedReader mBufferedReaderClient = null;
	private String recvMessageClient = "";
	private LineChartView lineChart;
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
	private String Eve;//�¼�
	private String[] Det=new String[13];//��ϸ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		lineChart = (LineChartView) findViewById(R.id.chart);
		
		try {
			Bundle bunde = this.getIntent().getExtras();  
			liangcheng = bunde.getLong("liangcheng");  
			maikuan = bunde.getLong("maikuan"); 
			bochang=bunde.getLong("bochang");
			zhouqi=bunde.getLong("zhouqi");
			celiangshijian=bunde.getLong("celiangshijian");
			zheshelv=bunde.getFloat("zheshelv");
			IP=bunde.getString("IP");
		}
		catch(Exception e){
			
		}
	}
	
	/**
	 * ͼ���ÿ�������ʾ
	 */
	private void getAxisPoints() {
		mPointValues.clear();
		for (int i = 0; i < f.length; i++) {
			mPointValues.add(new PointValue(i, f[i]));
		}
	}

	private void initLineChart() {
		Line line = new Line(mPointValues)
				.setColor(Color.parseColor("#FFCD41")); // ���ߵ���ɫ����ɫ��
		List<Line> lines = new ArrayList<Line>();
		line.setStrokeWidth(1);
	//	line.setShape(ValueShape.CIRCLE);// ����ͼ��ÿ�����ݵ����״ ������Բ�� ��������
											// ��ValueShape.SQUARE
											// ValueShape.CIRCLE
											// ValueShape.DIAMOND��
		line.setCubic(false);// �����Ƿ�ƽ�����������߻�������
		line.setFilled(false);// �Ƿ�������ߵ����
	//	line.setHasLabels(true);// ���ߵ����������Ƿ���ϱ�ע
		// line.setHasLabelsOnlyForSelected(true);//�������������ʾ���ݣ����������line.setHasLabels(true);����Ч��
		line.setHasLines(true);// �Ƿ�������ʾ�����Ϊfalse ��û������ֻ�е���ʾ
		line.setHasPoints(false);// �Ƿ���ʾԲ�� ���Ϊfalse ��û��ԭ��ֻ�е���ʾ��ÿ�����ݵ㶼�Ǹ����Բ�㣩
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		// ������
		Axis axisX = new Axis(); // X��
		axisX.setHasTiltedLabels(true); // X������������б����ʾ����ֱ�ģ�true��б����ʾ
	//	axisX.setTextColor(Color.WHITE); // ����������ɫ
		// axisX.setName("date"); //�������
		axisX.setTextSize(10);// ���������С
		//axisX.setMaxLabelChars(8); // ��༸��X�����꣬��˼�������������X�������ݵĸ���7<=x<=mAxisXValues.length
		axisX.setValues(mAxisXValues); // ���X�����������
		data.setAxisXBottom(axisX); // x ���ڵײ�
		// data.setAxisXTop(axisX); //x ���ڶ���
		axisX.setHasLines(true); // x ��ָ���

		// Y���Ǹ������ݵĴ�С�Զ�����Y������(�������һ�����̶�Y�����ݸ����Ľ������)
		Axis axisY = new Axis(); // Y��
		axisY.setName("");// y���ע
		axisY.setTextSize(10);// ���������С
		data.setAxisYLeft(axisY); // Y�����������
		// data.setAxisYRight(axisY); //y���������ұ�

		// ������Ϊ���ԣ�֧�����š������Լ�ƽ��
		//lineChart.setInteractive(true);
		lineChart.setZoomType(ZoomType.HORIZONTAL);
	//	lineChart.setMaxZoom((float) 2);// ��󷽷�����
		lineChart.setContainerScrollEnabled(true,
				ContainerScrollType.HORIZONTAL);
		lineChart.setLineChartData(data);
		lineChart.setVisibility(View.VISIBLE);
		
		//Viewport v = new Viewport(lineChart.getMaximumViewport());
		Viewport v = new Viewport();
		v.left = 0;
		v.top=30;
		v.right = f.length-1000;
		v.bottom=-5;
	//	lineChart.setCurrentViewport(v);
		lineChart.setMaximumViewport(v);
	//	lineChart.setCurrentViewport(v);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.item1) {
			try {
				if(isTesting){
					Toast.makeText(getApplicationContext(), "���ڲ��ԣ����Ժ�...", Toast.LENGTH_SHORT).show();
				}else{
					start();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//��ʼ����
		}
		if (id == R.id.item2) {
			set();//���ò���
		}
		if (id == R.id.item3) {
			save();//��������
		}
		if (id == R.id.item4) {
			event();//�¼��б�
		}
		if (id == R.id.item5) {
			details();//��ϸ����
		}
		return super.onOptionsItemSelected(item);
	}
	private void details() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, Details.class);
		Bundle bundleSimple = new Bundle(); 
		bundleSimple.putStringArray("Detail", Det);
		intent.putExtras(bundleSimple); 
		MainActivity.this.startActivity(intent);
	}

	private void event() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, EventList.class);
		Bundle bundleSimple = new Bundle(); 
		bundleSimple.putParcelableArrayList("events", list);
		intent.putExtras(bundleSimple); 
		MainActivity.this.startActivity(intent);
	}
	/**
	    * ��ȡ�ͱ��浱ǰ��Ļ�Ľ�ͼ
	    */
	private void save() {
		// TODO Auto-generated method stub

		    //����Bitmap  
		    WindowManager windowManager = getWindowManager();  
		    Display display = windowManager.getDefaultDisplay();  
		    int w = display.getWidth();  
		    int h = display.getHeight();  
		    Bitmap Bmp = Bitmap.createBitmap( w, h, Config.ARGB_8888 );      
		    //��ȡ��Ļ  
		    View decorview = this.getWindow().getDecorView();   
		    decorview.setDrawingCacheEnabled(true);   
		    Bmp = decorview.getDrawingCache();   
		    //ͼƬ�洢·��
		    String SavePath = getSDCardPath()+"/OTDR/ScreenImages";  //�����ǽ�ͼ�����·��
		    //����Bitmap   
		    try {  
		    File path = new File(SavePath);  
		    SimpleDateFormat formatter=new  SimpleDateFormat   ("yyyy��MM��dd��   HH:mm:ss");     
		    Date curDate =  new Date(System.currentTimeMillis());  
		    String str = formatter.format(curDate);  
		    //�ļ�  
		    String filepath=SavePath+"/" +str+".png";
		    File file = new File(filepath);  
		    if(!path.exists()){   //�ж�·���Ƿ����
		    path.mkdirs();  
		    }  
		    if (!file.exists()) {  
		    file.createNewFile();    
		    }  
		    FileOutputStream fos = null;  
		    fos = new FileOutputStream(file);  
		    if (null != fos) {  
		    Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);  
		    fos.flush();  
		    fos.close();    
		    Toast.makeText(MainActivity.this, "�����ļ��ѱ�����SDCard/OTDR/ScreenImages/Ŀ¼��",Toast.LENGTH_LONG).show();  
		    }  
		    } catch (Exception e) {  
		    e.printStackTrace();  
		    }  
		    }  
		    /**
		    * ��ȡSDCard��Ŀ¼·������
		    * @return
		    */
		    private String getSDCardPath(){
		    File sdcardDir = null;
		    //�ж�SDCard�Ƿ����
		    boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		    if(sdcardExist){
		    sdcardDir = Environment.getExternalStorageDirectory();
		    }
		    return sdcardDir.toString();
	}

	private void set() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, setting.class);
		Bundle bundleSimple = new Bundle(); 
		bundleSimple.putLong("liangcheng", liangcheng);  
		bundleSimple.putLong("maikuan", maikuan);  
		bundleSimple.putLong("bochang", bochang);  
		bundleSimple.putLong("zhouqi", zhouqi); 
		bundleSimple.putLong("celiangshijian", celiangshijian);  
		bundleSimple.putFloat("zheshelv", zheshelv);
		bundleSimple.putString("IP", IP);
		intent.putExtras(bundleSimple); 
		MainActivity.this.startActivity(intent);
	}

	private void start() throws InterruptedException {
		// TODO Auto-generated method stub
		if (mSocketClient == null) {
			mThreadClient = new Thread(mRunnable);
			mThreadClient.start();
			Thread.sleep(100);
		}
		if (mSocketClient != null) {
			byte c[];
			c = SendStartMsg.Start(liangcheng,maikuan,bochang,zhouqi,celiangshijian,zheshelv,IP);

			try {
				Toast.makeText(getApplicationContext(), "��ʼ����...", Toast.LENGTH_SHORT).show();
				OutputStream out = mSocketClient.getOutputStream();
				out.write(c);

				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "�����쳣��" + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getApplicationContext(), "û������!", Toast.LENGTH_SHORT).show();
		}
	}

	public long getUint32(long l) {
		return l & 0x00000000ffffffff;
	}

	public int getUint16(int i) {
		return i & 0x0000ffff;
	}

	// ��UI�������߳�
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getAxisPoints();// ��ȡ�����
			initLineChart();// ��ʼ��
		}

	};
	public float[] f =new float[0];
	// Lock lock = new ReentrantLock();

	// �߳�:������������������Ϣ
	private Runnable mRunnable = new Runnable() {
		public void run() {
			String sIP =IP;
			int port = 5000;

			try {
				// ���ӷ�����
				mSocketClient = new Socket(sIP, port); // portnum
				// ȡ�����롢�����
				mBufferedReaderClient = new BufferedReader(
						new InputStreamReader(mSocketClient.getInputStream()));

				mPrintWriterClient = new PrintWriter(
						mSocketClient.getOutputStream(), true);

				//Toast.makeText(getApplicationContext(), "���ӳɹ�!", Toast.LENGTH_SHORT).show();
//				recvMessageClient = "�Ѿ�����server!\n";// ��Ϣ����
//				Message msg = new Message();
//				mHandler.sendMessage(msg);
				// break;
			} catch (Exception e) {
				//Toast.makeText(getApplicationContext(), "�޷�����IP��", Toast.LENGTH_SHORT).show();
//				recvMessageClient = "����IP�쳣:" + e.toString() + e.getMessage()
//						+ "\n";// ��Ϣ����
//				Message msg = new Message();
//				mHandler.sendMessage(msg);
				return;
			}

			byte[] buffer = new byte[1024];
			List<Byte> bytes = null;
			int count;
			int recieveLength;

			while (true) {

				try {
					InputStream in = mSocketClient.getInputStream();
					OutputStream out = mSocketClient.getOutputStream();

					bytes = new ArrayList<Byte>();

					recieveLength = 0;
					count = in.read(buffer);
					Thread.sleep(10);
					if (count >= 20) {
						for (int i = 0; i < count; i++) {
							bytes.add(buffer[i]);
						}

						recieveLength = (int) BitConverter.ByteToLong(buffer,
								16);
						recieveLength = recieveLength - count;
					}

					while (recieveLength > 0) {
						count = in.read(buffer);
						Thread.sleep(10);

						for (int i = 0; i < count; i++) {
							bytes.add(buffer[i]);
						}

						recieveLength = recieveLength - count;
					}

					if (bytes.size() > 0) {
						try {
							out.write(SendRespond.Respond());

							mPrintWriterClient.flush();
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(getApplicationContext(), "�����쳣��" + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
						recvMessageClient = getInfoBuff(bytes
								.toArray(new Byte[bytes.size()])) + "\n";// ��Ϣ����
						Message msg = new Message();
						mHandler.sendMessage(msg);
					} else {
						Thread.sleep(500);
					}
				} catch (Exception e) {
					recvMessageClient = "�����쳣:" + e.getMessage() + "\n";// ��Ϣ����
					Message msg = new Message();
					mHandler.sendMessage(msg);
				}
			}

		}
	};

	// �������յ���byte����
	private String getInfoBuff(Byte[] bytes) {
		StringBuilder rec = new StringBuilder();
		String fanhui = "";
		int sum = 0;
		int cmd = 0;

		cmd = (int) BitConverter.ByteToLong(bytes, 44);
		System.out.print(cmd);
		if (cmd == -1610612736) {
			fanhui = "1";
			isTesting=true;
		}

		if (cmd == -1879048191) {
			sum = (int) BitConverter.ByteToLong(bytes, 52);
			f=new float[sum];
			for (int i = 0; i < sum; i++) {
				float point = (float) BitConverter.ByteToInt(bytes, 56 + 2 * i);
				rec.append((point / 1000 - 5) + "\n");
				f[i] = point / 1000 - 5;
			}
			fanhui = "2";
		}
		
		if (cmd == -1879048192) {
			list = new ArrayList<Event>();
			sum = (int) BitConverter.ByteToLong(bytes, 104);
			f=new float[sum];
			for (int i = 0; i < sum; i++) {
				float point = (float) BitConverter.ByteToInt(bytes, 108 + 2 * i);
				rec.append((point / 1000 - 5) + "\n");
				f[i] = point / 1000 - 5;
			}
			
			/*
			 * �¼�
			 */
			int SumEvent=(int)BitConverter.ByteToLong(bytes, 108 + sum * 2);
//			StringBuilder Even=new StringBuilder();
//			Even.append("����\t\t\t\t\t\t����\t\t\t\t�ز����\t\t\t\t�������\t\t\t\t˥��ϵ��\t\t\t\t�ۼ����\n\n");
            
			for (int i = 0; i < SumEvent; i++)
            {
				 int shijianxuhao = (int)BitConverter.ByteToLong(bytes, 108 + sum * 2 + 4 + 24 * i);
				 float juli = (shijianxuhao * (2997.92458F) * 100000) / (2 * zheshelv * (int)BitConverter.ByteToLong(bytes, 52));
				 int shijianleixing = (int)BitConverter.ByteToLong(bytes, 108 + sum * 2 + 8 + 24 * i);
				 String leixing; 
				 if (shijianleixing == 0) { leixing = "��ʼ�¼�"; }
	                else if (shijianleixing == 1) { leixing = "�����¼�"; }
	                else if (shijianleixing == 2) { leixing = "�Ƿ����¼�"; }
	                else {   leixing = "�����¼�"; }
				 float huibosunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 12 + 24 * i);
	             float charusunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 16 + 24 * i);
	             float shuaijianxishu = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 20 + 24 * i);
	             float leijisunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 24 + 24 * i);
	            
	             list.add(new Event(juli+"", leixing, huibosunhao+"",charusunhao+"",shuaijianxishu+"",leijisunhao+""));
            }					
			/*
			 * ����
			 */
			Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
			t.setToNow(); // ȡ��ϵͳʱ�䡣
			int year = t.year;
			int month = t.month;
			int day = t.monthDay;
			int hour = t.hour; // 0-23
			int minute = t.minute;
			int second = t.second;
			for (int i = 0; i < 5; i++) {
				Det[i] = ""+((int)BitConverter.ByteToLong(bytes, 52+i*4));
			}
			for (int i = 5; i < 11; i++) {
				Det[i] = ""+(BitConverter.ByteToFloat(bytes, 52+i*4));
			}
			Det[11]=year+"��"+month+"��"+day+"��";
			Det[12]=hour+":"+minute+":"+second;
			fanhui = "3";
			isTesting=false;
		}
		return fanhui;
	}
}
