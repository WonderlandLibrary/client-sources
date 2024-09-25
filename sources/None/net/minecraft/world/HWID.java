package net.minecraft.world;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import none.Client;

/**
 * Created by Hexeption on 16/01/2017.
 */
public final class HWID {

	public static List<Character> key = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
	public static List<Character> index = Arrays.asList('7', '6', '8', '4', '9', '5', '1', '3', '2', '0');
	public static String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static HWID hwid = null;
	public static File data = new File("C:/", "None");
	public static File file = new File(data, "ASkSdii2msigosoaosd");

	/**
	 * Gives a HWID I.E
	 * (350-30a-3ae-30e-304-3d6-37d-359-371-3e0-3d8-3e1-369-3b2-34a-314) - Hexeption
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	
	public static boolean isHWID() {
		return hwid == null ? false : true;
	}

	public static String format() {
		try {
			String str = "";
			String temp = "";
			str = getHWID();
			for (int i = 0; i < str.length(); i++) {
				if (key.contains(str.charAt(i))) {
					char test1 = str.charAt(i);
					for (int j = 0; j < index.size(); j++) {
						char test2 = index.get(j);
						try {
							if (test2 == test1) {
								int fesk = j + 4;
								if (fesk >= index.size()) {
									fesk -= index.size();
								}
								char newchar = index.get(fesk);
								temp = temp + newchar;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					char test1 = str.charAt(i);
					for (int a = 0; a < abc.length(); a++) {
						char test2 = abc.charAt(a);
						if (test2 == test1) {
							int index = a + 10;
							if (index >= abc.length()) {
								index -= abc.length();
							}
							char newchar = abc.charAt(index);
							temp = temp + newchar;
						}
					}
				}
			}
			return temp;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String s = "";
		final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME")
				+ System.getProperty("user.name").trim();
		final byte[] bytes = main.getBytes("UTF-8");
		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		final byte[] md5 = messageDigest.digest(bytes);
		int i = 0;
		for (final byte b : md5) {
			s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
			i++;
		}
		return s;
	}
	
	public static void CheckFile() {
		if (!data.exists()) {
			data.mkdirs();
		}
		if (!file.exists()) {
			return;
		}else {
			load();
		}
	}
	
	public static void save(String id) {
		List<String> list = new ArrayList<>();
		list.add(id);
		write(file, list, false);
	}
	
	public static void load() {
		List<String> list = read(file);
		for (String text : list) {
			if (text.contains(HWID.format())) {
				hwid = new HWID();
			}
		}
	}
	
	public static List<String> read(final File inputFile) {

        final List<String> readContent = new ArrayList<String>();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
            String str;
            while ((str = in.readLine()) != null) {
                readContent.add(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readContent;
    }

    public static void write(final File outputFile, final List<String> writeContent, final boolean overrideContent) {
        try {
            if (!overrideContent && file.exists()) {
            	for (int i = 0; i < 2; i++) {
            		Client.instance.notification.show(Client.notification("HWID", "....."));
            	}
            	Client.instance.notification.show(Client.notification("HWID", "$$@!#Error??!!$"));
            	return;
            }
            Client.instance.notification.show(Client.notification("HWID", "Please Wait...."));
            for (int i = 0; i < 5; i++) {
        		Client.instance.notification.show(Client.notification("HWID", "....."));
        	}
            final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
            for (final String outputLine : writeContent) {
                out.write(String.valueOf(outputLine) + System.getProperty("line.separator"));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            for (int i = 0; i < 2; i++) {
        		Client.instance.notification.show(Client.notification("HWID", "....."));
        	}
            Client.instance.notification.show(Client.notification("HWID", "$$@!#Error??!!$"));
            Client.instance.notification.show(Client.notification("HWID", e.getMessage()));
        }
    }
}