package com.oakfusion.security.serial;

import org.springframework.security.core.Authentication;

import java.io.*;

public class JavaAuthSerializer implements AuthSerializer {

	public byte[] write(final Authentication object) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutput outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			outputStream.writeObject(object);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Authentication read(final byte[] bytes) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		try (ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream)) {
			return (Authentication) inputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
