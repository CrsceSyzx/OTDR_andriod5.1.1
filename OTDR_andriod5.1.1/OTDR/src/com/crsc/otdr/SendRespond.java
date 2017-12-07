package com.crsc.otdr;

public class SendRespond {
	public static long getUint32(long l) {
		return l & 0x00000000ffffffff;
	}

	public static byte[] Respond() {
		byte c[] = new byte[56];
		byte a[];
		// head ֡ͷ
		String b1 = "GLinkOtdr-3800M\0";

		long b2 = getUint32(56);// pklen
		long b3 = getUint32(0);// rev
		long b4 = getUint32(0);// pktyp
		long b5 = getUint32(0);// src
		long b6 = getUint32(0);// dst
		long b7 = getUint32(0);// pkid
		long b8 = getUint32(0);// rsvd
		long b9 = getUint32(0x10000004);// cmdcode
		long b10 = getUint32(0);// datlen
		long b11 = getUint32(0);

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
		return c;
	}

}
