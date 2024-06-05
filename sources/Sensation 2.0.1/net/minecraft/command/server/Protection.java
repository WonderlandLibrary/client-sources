package net.minecraft.command.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class Protection {
	
	public static byte[] createChecksum(String filename) throws Exception {
	       InputStream fis =  new FileInputStream(filename);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

	   public static String getMD5Checksum(String filename) throws Exception {
	       byte[] b = createChecksum(filename);
	       String result = "";

	       for (int i=0; i < b.length; i++) {
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       }
	       return result;
	   }
	   
 
	   public static void sendRequest(String request) throws IOException {
		   URL url = new URL(request); 
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
		   connection.setDoOutput(true); 
		   connection.setInstanceFollowRedirects(false); 
		   connection.setRequestMethod("GET"); 
		   connection.setRequestProperty("Content-Type", "text/plain"); 
		   connection.setRequestProperty("charset", "utf-8");
		   connection.connect(); 
 
	   }
	   
	    public static String getResponse(String URL) {
	        String out = null;
	        try {
	            URLConnection connection = new URL(URL).openConnection();
	            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	            connection.connect();
	            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
	            StringBuilder sb = new StringBuilder();
	            String line;
	            while ((line = r.readLine()) != null) {
	                sb.append(line);
	            }
	            String value = sb.toString();
	            out = value;
	        } catch (IOException ex) {
	            out = "[ERROR] - " + ex.getMessage();
	        }
	        return out;
	    }
}
