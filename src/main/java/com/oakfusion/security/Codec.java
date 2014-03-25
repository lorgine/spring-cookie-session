package com.oakfusion.security;


public interface Codec {

	public byte[] encrypt(byte[] data);

	public byte[] decrypt(byte[] data);
}
