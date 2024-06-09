package lunadevs.luna.Connector.URLPremium;


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

import lunadevs.luna.login.Alt;
import lunadevs.luna.main.Luna;
import net.minecraft.client.Minecraft;
public class URLHWID {
	
    public static void whitelist() {
        try
        {
        	/**
        	 * FIXME: Beta HWID System
        	 * 
        	 * http://lunaclient.pw/api/hwid/HWID.php?id=
        	 */
          URL url = new URL("" + getHwid());
          ArrayList<Object> lines = new ArrayList();
          URLConnection connection = url.openConnection();
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String line;
          while ((line = in.readLine()) != null)
          {
            lines.add(line);
          }
          if (!lines.contains("true")) {
            Minecraft.getMinecraft().shutdown();
            Minecraft.getMinecraft().shutdownMinecraftApplet();
            System.exit(0);
          }
        }
        catch (Exception e)
        {
                Minecraft.getMinecraft().shutdown();
                Minecraft.getMinecraft().shutdownMinecraftApplet();
                System.exit(0);
        }
    }
	
    public static String getHwid()
    	    throws Exception
    	  {
    	    String hwid = SHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name"));
    	    return hwid;
    	  }
    	  
    	  private static String SHA1(String text)
    	    throws NoSuchAlgorithmException, UnsupportedEncodingException
    	  {
    	    MessageDigest md = MessageDigest.getInstance("SHA-1");
    	    byte[] sha1hash = new byte[40];
    	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    	    sha1hash = md.digest();
    	    return convertToHex(sha1hash);
    	  }
    	  
    	  private static String convertToHex(byte[] data)
    	  {
    	    StringBuffer buf = new StringBuffer();
    	    for (int i = 0; i < data.length; i++)
    	    {
    	      int halfbyte = data[i] >>> 4 & 0xF;
    	      int two_halfs = 0;
    	      do
    	      {
    	        if ((halfbyte >= 0) && (halfbyte <= 9)) {
    	          buf.append((char)(48 + halfbyte));
    	        } else {
    	          buf.append((char)(97 + (halfbyte - 10)));
    	        }
    	        halfbyte = data[i] & 0xF;
    	      } while (
    	      
    	        two_halfs++ < 1);
    	    }
    	    return buf.toString();
    	  }
}