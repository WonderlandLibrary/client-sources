package me.superskidder.lune.utils.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Random;

public class HWIDUtil {

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String getHWID(){
		try {
			StringBuilder sb = new StringBuilder();
			String computerName = System.getenv("COMPUTERNAME");
			String processIdentifier = System.getenv("PROCESS_IDENTIFIER");
			String main =  processIdentifier + computerName;
			byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5 = md.digest(bytes);
			for (byte b : md5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x300), 0, 2);
			}
			char[] wow = sb.toString().toCharArray();
			for (char c : wow) {
				try {
					sb.insert(computerName.length(), c ^ 555 & 114 & 514 ^ 233);
				}catch(Exception e){
					// oh shit
				}
			}
			String lastNumber = sb.substring(sb.toString().length() - 1);
			try {
				int num = Integer.parseInt(lastNumber);
				sb.append(getShitString(num));
			}catch (Exception e){
				//System.out.println("唉");
				return sb.toString();
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException ignored) {
		}
		return null;
	}

	public static String getShitString(int length){
		String str = "456g4fdgh98637156df4g69874dfgf44gfd56g4f5d6g";
		return str.substring(0, length);
	}
}
