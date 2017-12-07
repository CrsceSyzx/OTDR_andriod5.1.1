package com.crsc.otdr;

public class SendStartMsg {

	public static long getUint32(long l) {
		return l & 0x00000000ffffffff;
	}

	public static byte[] Start(long liangcheng,long maikuan,long bochang,long zhouqi,long celiangshijian,float zheshelv,String ip) {
		byte c[] = new byte[104];
		byte a[];
		// head 帧头
		String b1 = "GLinkOtdr-3800M\0";

		long b2 = getUint32(104);// pklen
		long b3 = getUint32(0);// rev
		long b4 = getUint32(0);// pktyp
		long b5 = getUint32(0);// src
		long b6 = getUint32(0);// dst
		long b7 = getUint32(0);// pkid
		long b8 = getUint32(0xffffeeee);// rsvd
		long b9 = getUint32(0x10000000);// cmdcode
		long b10 = getUint32(48);// datlen

		// 数据区

		long b11 = getUint32(1);// 测量模式
		long b12 = getUint32(0);// 优化模式
		long b13 = getUint32(0);// 保留
		long b14 = getUint32(1);// 使能刷新
		long b15 = getUint32(zhouqi);// 刷新周期
		long b16 = getUint32(bochang);// 测试波长
		long b17 = getUint32(liangcheng);// 量程
		long b18 = getUint32(maikuan);// 脉宽
		long b19 = getUint32(celiangshijian);// 测量时间
		float b20 = zheshelv;
		float b21 = 5.0F;// 结束门限
		float b22 = 0.0F;// 非反射门限

		long b23 = getUint32(0);

		int ctemp = 0;
		a = b1.getBytes();
		for (int i = 0; i < 16; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b2);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b3);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b4);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b5);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b6);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b7);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b8);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b9);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b10);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b11);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b12);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b13);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b14);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b15);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b16);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b17);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b18);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b19);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.FloatToByte(b20);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.FloatToByte(b21);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.FloatToByte(b22);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}

		a = BitConverter.LongToByte(b23);
		for (int i = 0; i < 4; i++) {
			c[ctemp] = a[i];
			ctemp++;
		}
		return c;
	}

}
