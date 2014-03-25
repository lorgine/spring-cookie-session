package pl.oakfusion.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESCodec implements Codec {

	public static final String ALGORITHM = "AES";

	private static final int KEY_SIZE_IN_BITS = 128;
	private final String key;

	public AESCodec(String key) {
		this.key = key;
	}

	@Override
	public byte[] encrypt(byte[] data) {
		return code(data, Cipher.ENCRYPT_MODE);
	}

	@Override
	public byte[] decrypt(byte[] data) {
		return code(data, Cipher.DECRYPT_MODE);
	}

	private byte[] code(byte[] data, int encryptMode) {
		try {
			SecretKey secretKey = generateSecretKey();
			byte[] secretKeyByteRepresentation = secretKey.getEncoded();

			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyByteRepresentation, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(encryptMode, secretKeySpec);

			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private SecretKey generateSecretKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			keyGenerator.init(KEY_SIZE_IN_BITS, secureRandom);

			return keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
