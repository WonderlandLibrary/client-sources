package xyz.vergoclient.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HWID {

	public static String getHWIDForWindows() throws NoSuchAlgorithmException {
		String main = System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("NUMBER_OF_PROCESSORS") + System.getenv("PROCESSOR_REVISION") +  System.getProperty("user.name").trim() + System.getenv("COMPUTERNAME") +
				System.getenv("PROCESSOR_LEVEL");
		return new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(main.getBytes(StandardCharsets.UTF_8))));
	}
	
}