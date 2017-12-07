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
	private String Eve;//事件
	private String[] Det=new String[13];//详细
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
	 * 图表的每个点的显示
	 */
	private void getAxisPoints() {
		mPointValues.clear();
		for (int i = 0; i < f.length; i++) {
			mPointValues.add(new PointValue(i, f[i]));
		}
	}

	private void initLineChart() {
		Line line = new Line(mPointValues)
				.setColor(Color.parseColor("#FFCD41")); // 折线的颜色（橙色）
		List<Line> lines = new ArrayList<Line>();
		line.setStrokeWidth(1);
	//	line.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
											// ：ValueShape.SQUARE
											// ValueShape.CIRCLE
											// ValueShape.DIAMOND）
		line.setCubic(false);// 曲线是否平滑，即是曲线还是折线
		line.setFilled(false);// 是否填充曲线的面积
	//	line.setHasLabels(true);// 曲线的数据坐标是否加上备注
		// line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);// 是否用线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(false);// 是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		// 坐标轴
		Axis axisX = new Axis(); // X轴
		axisX.setHasTiltedLabels(true); // X坐标轴字体是斜的显示还是直的，true是斜的显示
	//	axisX.setTextColor(Color.WHITE); // 设置字体颜色
		// axisX.setName("date"); //表格名称
		axisX.setTextSize(10);// 设置字体大小
		//axisX.setMaxLabelChars(8); // 最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
		axisX.setValues(mAxisXValues); // 填充X轴的坐标名称
		data.setAxisXBottom(axisX); // x 轴在底部
		// data.setAxisXTop(axisX); //x 轴在顶部
		axisX.setHasLines(true); // x 轴分割线

		// Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
		Axis axisY = new Axis(); // Y轴
		axisY.setName("");// y轴标注
		axisY.setTextSize(10);// 设置字体大小
		data.setAxisYLeft(axisY); // Y轴设置在左边
		// data.setAxisYRight(axisY); //y轴设置在右边

		// 设置行为属性，支持缩放、滑动以及平移
		//lineChart.setInteractive(true);
		lineChart.setZoomType(ZoomType.HORIZONTAL);
	//	lineChart.setMaxZoom((float) 2);// 最大方法比例
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
					Toast.makeText(getApplicationContext(), "正在测试，请稍后...", Toast.LENGTH_SHORT).show();
				}else{
					start();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//开始测量
		}
		if (id == R.id.item2) {
			set();//设置参数
		}
		if (id == R.id.item3) {
			save();//保存数据
		}
		if (id == R.id.item4) {
			event();//事件列表
		}
		if (id == R.id.item5) {
			details();//详细数据
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
	    * 获取和保存当前屏幕的截图
	    */
	private void save() {
		// TODO Auto-generated method stub

		    //构建Bitmap  
		    WindowManager windowManager = getWindowManager();  
		    Display display = windowManager.getDefaultDisplay();  
		    int w = display.getWidth();  
		    int h = display.getHeight();  
		    Bitmap Bmp = Bitmap.createBitmap( w, h, Config.ARGB_8888 );      
		    //获取屏幕  
		    View decorview = this.getWindow().getDecorView();   
		    decorview.setDrawingCacheEnabled(true);   
		    Bmp = decorview.getDrawingCache();   
		    //图片存储路径
		    String SavePath = getSDCardPath()+"/OTDR/ScreenImages";  //这里是截图保存的路径
		    //保存Bitmap   
		    try {  
		    File path = new File(SavePath);  
		    SimpleDateFormat formatter=new  SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");     
		    Date curDate =  new Date(System.currentTimeMillis());  
		    String str = formatter.format(curDate);  
		    //文件  
		    String filepath=SavePath+"/" +str+".png";
		    File file = new File(filepath);  
		    if(!path.exists()){   //判断路径是否存在
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
		    Toast.makeText(MainActivity.this, "截屏文件已保存至SDCard/OTDR/ScreenImages/目录下",Toast.LENGTH_LONG).show();  
		    }  
		    } catch (Exception e) {  
		    e.printStackTrace();  
		    }  
		    }  
		    /**
		    * 获取SDCard的目录路径功能
		    * @return
		    */
		    private String getSDCardPath(){
		    File sdcardDir = null;
		    //判断SDCard是否存在
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
				Toast.makeText(getApplicationContext(), "开始测试...", Toast.LENGTH_SHORT).show();
				OutputStream out = mSocketClient.getOutputStream();
				out.write(c);

				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "发送异常：" + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getApplicationContext(), "没有连接!", Toast.LENGTH_SHORT).show();
		}
	}

	public long getUint32(long l) {
		return l & 0x00000000ffffffff;
	}

	public int getUint16(int i) {
		return i & 0x0000ffff;
	}

	// 对UI操作的线程
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getAxisPoints();// 获取坐标点
			initLineChart();// 初始化
		}

	};
	public float[] f =new float[0];
	// Lock lock = new ReentrantLock();

	// 线程:监听服务器发来的消息
	private Runnable mRunnable = new Runnable() {
		public void run() {
			String sIP =IP;
			int port = 5000;

			try {
				// 连接服务器
				mSocketClient = new Socket(sIP, port); // portnum
				// 取得输入、输出流
				mBufferedReaderClient = new BufferedReader(
						new InputStreamReader(mSocketClient.getInputStream()));

				mPrintWriterClient = new PrintWriter(
						mSocketClient.getOutputStream(), true);

				//Toast.makeText(getApplicationContext(), "连接成功!", Toast.LENGTH_SHORT).show();
//				recvMessageClient = "已经连接server!\n";// 消息换行
//				Message msg = new Message();
//				mHandler.sendMessage(msg);
				// break;
			} catch (Exception e) {
				//Toast.makeText(getApplicationContext(), "无法连接IP！", Toast.LENGTH_SHORT).show();
//				recvMessageClient = "连接IP异常:" + e.toString() + e.getMessage()
//						+ "\n";// 消息换行
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
							Toast.makeText(getApplicationContext(), "发送异常：" + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
						recvMessageClient = getInfoBuff(bytes
								.toArray(new Byte[bytes.size()])) + "\n";// 消息换行
						Message msg = new Message();
						mHandler.sendMessage(msg);
					} else {
						Thread.sleep(500);
					}
				} catch (Exception e) {
					recvMessageClient = "接收异常:" + e.getMessage() + "\n";// 消息换行
					Message msg = new Message();
					mHandler.sendMessage(msg);
				}
			}

		}
	};

	// 解析接收到的byte【】
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
			 * 事件
			 */
			int SumEvent=(int)BitConverter.ByteToLong(bytes, 108 + sum * 2);
//			StringBuilder Even=new StringBuilder();
//			Even.append("距离\t\t\t\t\t\t类型\t\t\t\t回波损耗\t\t\t\t插入损耗\t\t\t\t衰减系数\t\t\t\t累计损耗\n\n");
            
			for (int i = 0; i < SumEvent; i++)
            {
				 int shijianxuhao = (int)BitConverter.ByteToLong(bytes, 108 + sum * 2 + 4 + 24 * i);
				 float juli = (shijianxuhao * (2997.92458F) * 100000) / (2 * zheshelv * (int)BitConverter.ByteToLong(bytes, 52));
				 int shijianleixing = (int)BitConverter.ByteToLong(bytes, 108 + sum * 2 + 8 + 24 * i);
				 String leixing; 
				 if (shijianleixing == 0) { leixing = "起始事件"; }
	                else if (shijianleixing == 1) { leixing = "反射事件"; }
	                else if (shijianleixing == 2) { leixing = "非反射事件"; }
	                else {   leixing = "结束事件"; }
				 float huibosunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 12 + 24 * i);
	             float charusunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 16 + 24 * i);
	             float shuaijianxishu = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 20 + 24 * i);
	             float leijisunhao = BitConverter.ByteToFloat(bytes, 108 + sum * 2 + 24 + 24 * i);
	            
	             list.add(new Event(juli+"", leixing, huibosunhao+"",charusunhao+"",shuaijianxishu+"",leijisunhao+""));
            }					
			/*
			 * 数据
			 */
			Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
			t.setToNow(); // 取得系统时间。
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
			Det[11]=year+"年"+month+"月"+day+"日";
			Det[12]=hour+":"+minute+":"+second;
			fanhui = "3";
			isTesting=false;
		}
		return fanhui;
	}
}
