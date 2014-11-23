package com.oakfusion.security.crypto;


public interface Codec {

	public byte[] encrypt(byte[] data);

	public byte[] decrypt(byte[] data);
}
