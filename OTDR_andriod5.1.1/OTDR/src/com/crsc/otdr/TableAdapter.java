package com.crsc.otdr;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class TableAdapter extends BaseAdapter {
	
	private List<Event> list;
	private LayoutInflater inflater;
	
	public TableAdapter(Context context, List<Event> list){
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(list!=null){
			ret = list.size();
		}
		return ret;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Event events = (Event) this.getItem(position);
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder.weizhi = (TextView) convertView.findViewById(R.id.text_weizhi);
			viewHolder.leixing = (TextView) convertView.findViewById(R.id.text_leixing);
			viewHolder.huibosunhao = (TextView) convertView.findViewById(R.id.text_huibosunhao);
			viewHolder.charusunhao = (TextView) convertView.findViewById(R.id.text_charusunhao);
			viewHolder.shuaijianxishu = (TextView) convertView.findViewById(R.id.text_shuaijianxishu);
			viewHolder.leijisunhao = (TextView) convertView.findViewById(R.id.text_leijishuaijian);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.weizhi.setText(events.getJuli());
		viewHolder.weizhi.setTextSize(13);
		viewHolder.leixing.setText(events.getLeixing());
		viewHolder.leixing.setTextSize(13);
		viewHolder.huibosunhao.setText(events.getCharusunhao());
		viewHolder.huibosunhao.setTextSize(13);
		viewHolder.charusunhao.setText(events.getHuibosunhao());
		viewHolder.charusunhao.setTextSize(13);
		viewHolder.shuaijianxishu.setText(events.getShuaijianxishu());
		viewHolder.shuaijianxishu.setTextSize(13);
		viewHolder.leijisunhao.setText(events.getLeijisunhao());
		viewHolder.leijisunhao.setTextSize(13);
		
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView weizhi;
		public TextView leixing;
		public TextView huibosunhao;
		public TextView charusunhao;
		public TextView shuaijianxishu;
		public TextView leijisunhao;
	}
	
}
