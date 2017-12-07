package com.crsc.otdr;

import java.nio.ByteBuffer;

public class BitConverter {
	public static byte[] LongToByte(long num) {
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte) (num >> ((7 - i) * 8) & 65535);
		}
		byte[] bytes1 = new byte[4];
		for (int i = 7; i > 3; i--) {
			bytes1[7 - i] = bytes[i];
		}

		return bytes1;
	}

	public static byte[] FloatToByte(float value) {

		byte[] bt = new byte[4];
		bt = ByteBuffer.allocate(4).putFloat(value).array();
		byte[] bt2 = new byte[4];
		for (int i = 0; i < bt2.length; i++) {
			bt2[i] = bt[3 - i];
		}
		return bt2;
	}
	
	public static int GetUnsignedByte(byte b){
		if(b<0){
			return 256+b;
		}else{
			return b;
		}
	}
	
	public static long ByteToLong(Byte[] bt){
		return ByteToLong(bt,0);
	}
	public static long ByteToLong(Byte[] bytes,int startIndex){
		long l;
		byte []b=new byte[8];
		for (int i = 0; i < 4; i++) {				
			b[7-i]=bytes[startIndex+i];
		}

		l=GetUnsignedByte(b[7]);

		for (int i = 6; i >= 0; i--) {
			l=l|(GetUnsignedByte(b[i])<<8*(7-i));
		}			
		return l;
	}
	public static float ByteToFloat(Byte[] bt,int startIndex){
		byte []b=new byte[4];
		for (int i = 0; i < 4; i++) {				
			b[i]=bt[startIndex+i];
		}		
		 int l = 0;  
	        l = b[0];  
	        l &= 0xff;  
	        l |= ((int) b[1] << 8);  
	        l &= 0xffff;  
	        l |= ((int) b[2] << 16);  
	        l &= 0xffffff;  
	        l |= ((int) b[3] << 24);  
	        l &= 0xffffffffl;  
	        return Float.intBitsToFloat(l);  
	}
	
	public static byte[] IntToByte(int n) {
		byte []b=new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (n >> ((3 - i) * 8) & 0x00FF);
		}
		 byte []bt=new byte[2];
		 bt[1]=b[2];
		 bt[0]=b[3];
		return bt;
	}
	
	public static int ByteToInt(Byte[] bytes,int startIndex){
		byte []bt=new byte[4];
		bt[3]=bytes[startIndex];
		bt[2]=bytes[startIndex+1];
		
		    return   GetUnsignedByte(bt[3]) & 0xFF |  
		            (GetUnsignedByte(bt[2]) & 0xFF) << 8 |  
		            (GetUnsignedByte(bt[1]) & 0xFF) << 16 |  
		            (GetUnsignedByte(bt[0]) & 0xFF) << 24;  
	}

	public static long ByteToLong(byte[] bytes, int startIndex) {
		long l;
		byte []b=new byte[8];
		for (int i = 0; i < 4; i++) {				
			b[7-i]=bytes[startIndex+i];
		}

		l=GetUnsignedByte(b[7]);

		for (int i = 6; i >= 0; i--) {
			l=l|(GetUnsignedByte(b[i])<<8*(7-i));
		}			
		return l;
	}
}
