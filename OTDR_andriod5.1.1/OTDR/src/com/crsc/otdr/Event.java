package com.crsc.otdr;

import android.os.Parcel;  
import android.os.Parcelable;

public class Event implements Parcelable{
	private String juli;
	private String leixing;
	private String huibosunhao;
	private String charusunhao;
	private String shuaijianxishu;
	private String leijisunhao;
	public Event(String juli, String leixing, String huibosunhao,
			String charusunhao, String shuaijianxishu, String leijisunhao) {
		super();
		this.juli = juli;
		this.leixing = leixing;
		this.huibosunhao = huibosunhao;
		this.charusunhao = charusunhao;
		this.shuaijianxishu = shuaijianxishu;
		this.leijisunhao = leijisunhao;
	}
	protected Event(Parcel in) {  
        juli = in.readString();  
        leixing = in.readString();  
        huibosunhao = in.readString();  
        charusunhao = in.readString();  
        shuaijianxishu = in.readString();  
        leijisunhao = in.readString();  
    }
	public String getJuli() {
		return juli;
	}
	public void setJuli(String juli) {
		this.juli = juli;
	}
	public String getLeixing() {
		return leixing;
	}
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
	public String getHuibosunhao() {
		return huibosunhao;
	}
	public void setHuibosunhao(String huibosunhao) {
		this.huibosunhao = huibosunhao;
	}
	public String getCharusunhao() {
		return charusunhao;
	}
	public void setCharusunhao(String charusunhao) {
		this.charusunhao = charusunhao;
	}
	public String getShuaijianxishu() {
		return shuaijianxishu;
	}
	public void setShuaijianxishu(String shuaijianxishu) {
		this.shuaijianxishu = shuaijianxishu;
	}
	public String getLeijisunhao() {
		return leijisunhao;
	}
	public void setLeijisunhao(String leijisunhao) {
		this.leijisunhao = leijisunhao;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(juli);  
		dest.writeString(leixing);  
		dest.writeString(huibosunhao);  
		dest.writeString(charusunhao);  
		dest.writeString(shuaijianxishu);  
		dest.writeString(leijisunhao);  
        
	}
	
}
