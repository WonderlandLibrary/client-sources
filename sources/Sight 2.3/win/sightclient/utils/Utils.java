package win.sightclient.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import io.netty.util.internal.ThreadLocalRandom;

public class Utils {

    public static void clip(String input) {
        StringSelection selection = new StringSelection(input);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static String getLatestPaste() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String result = "";
        try {
            result = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.equalsIgnoreCase("") ? "error" : result;
    }
    
    public static String getRandomString(int size) {
    	String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < size; i++) {
    		sb.append(letters.charAt(ThreadLocalRandom.current().nextInt(letters.length())));
    	}
    	return sb.toString();
    }
    
   public static interface User32 extends Library {
	   User32 INSTANCE = (User32) Native.loadLibrary("user32",User32.class,W32APIOptions.DEFAULT_OPTIONS);        
       boolean SystemParametersInfo (int one, int two, String s ,int three);         
   }
   public static void setBackground(String location) {   
      User32.INSTANCE.SystemParametersInfo(0x0014, 0, location , 1);
   }
}
