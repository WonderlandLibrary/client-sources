package lunadevs.luna.main.clientprotection;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

import com.mojang.authlib.GameProfile;

import lunadevs.luna.main.Luna;
import net.minecraft.client.Minecraft;

import lunadevs.luna.login.Alt;
import lunadevs.luna.main.Luna;
import net.minecraft.client.Minecraft;

public class Whitelist {

	public static StringBuilder hwid = new StringBuilder();

	/*
	 * public static String HWID() throws Exception { String hwid =
	 * HashHWID(System.getenv("PROCESSOR_IDENTIFIER")); StringSelection
	 * stringSelection = new StringSelection(hwid); Clipboard clpbrd =
	 * Toolkit.getDefaultToolkit().getSystemClipboard();
	 * clpbrd.setContents(stringSelection, null); return hwid; }
	 * 
	 * private static String HashHWID(String text) throws
	 * NoSuchAlgorithmException, UnsupportedEncodingException { MessageDigest md
	 * = MessageDigest.getInstance("SHA-1"); byte[] sha1hash = new byte[40];
	 * md.update(text.getBytes("iso-8859-1"), 0, text.length()); sha1hash =
	 * md.digest(); return HWIDData(sha1hash); }
	 * 
	 * private static String HWIDData(byte[] data) { StringBuffer buf = new
	 * StringBuffer(); for (int i = 0; i < data.length; i++) { int halfbyte =
	 * data[i] >>> 4 & 0xF; int two_halfs = 0; do { if ((halfbyte >= 0) &&
	 * (halfbyte <= 9)) { buf.append((char)(48 + halfbyte)); } else {
	 * buf.append((char)(97 + (halfbyte - 10))); } halfbyte = data[i] & 0xF; }
	 * while (
	 * 
	 * two_halfs++ < 1); } return buf.toString(); }
	 * 
	 * public static void whitelist(){
	 * 
	 * try {
	 * 
	 * URL url = new URL("https://lunaclient.igbaemu.com/raw.txt");
	 * ArrayList<Object> lines = new ArrayList(); URLConnection connection =
	 * url.openConnection(); BufferedReader in = new BufferedReader(new
	 * InputStreamReader(connection.getInputStream())); String line; while
	 * ((line = in.readLine()) != null) { lines.add(line); } if
	 * (!lines.contains(HWID())) { JOptionPane.showMessageDialog(null,
	 * "Your HWID is not whitelisted!"); JOptionPane.showMessageDialog(null,
	 * "HWID Copied to clipboard!"); System.exit(1); } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	public static void whitelist() {
	}

	public static void getHWID() {

		try {

			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			List<String> macAddresses = new ArrayList();
			while (networkInterfaces.hasMoreElements()) {
				byte[] macAddress = ((NetworkInterface) networkInterfaces.nextElement()).getHardwareAddress();
				if (macAddress != null) {
					macAddresses.add(DatatypeConverter.printBase64Binary(macAddress));
				}
			}
			for (int i = 0; i < macAddresses.size(); i++) {
				hwid.append((String) macAddresses.get(i));
				if (i + 1 < macAddresses.size()) {
					hwid.append("-");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getLicense() throws Exception {
		final String hwid = SHA1(String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")) + System.getenv("COMPUTERNAME")
				+ System.getProperty("user.name"));
		return hwid;
	}

	private static String SHA1(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	private static String convertToHex(final byte[] data) {
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; ++i) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if (halfbyte >= 0 && halfbyte <= 9) {
					buf.append((char) (48 + halfbyte));
				} else {
					buf.append((char) (97 + (halfbyte - 10)));
				}
				halfbyte = (data[i] & 0xF);
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
}
// public static void whitelist() {
// try {
// final URL url = new URL("http://lunaurlservers.x10.mx/connector/HWID/");
// final ArrayList<Object> lines = new ArrayList<Object>();
// final URLConnection connection = url.openConnection();
// final BufferedReader in = new BufferedReader(new
// InputStreamReader(connection.getInputStream()));
// String line;
// while ((line = in.readLine()) != null) {
// lines.add(line);
// }
// if (!lines.contains(getLicense())) {
// System.out.print("ERROR: NOT_WHITELISTED, You are not allowed to use Luna!
// Purchase it at https://discord.gg/kGCRzgM \n");
// Minecraft.getMinecraft().shutdown();
// Minecraft.getMinecraft().shutdownMinecraftApplet();
// System.exit(0);
// }
// }
// catch (Exception e) {
// Minecraft.getMinecraft().shutdown();
// Minecraft.getMinecraft().shutdownMinecraftApplet();
// System.exit(0);
//
// }}
// }
