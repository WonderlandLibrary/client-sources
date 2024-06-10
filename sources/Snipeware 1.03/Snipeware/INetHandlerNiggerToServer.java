package Snipeware;

import java.security.MessageDigest;
import Snipeware.security.Comparator;
import Snipeware.security.Transmitter;
import net.minecraft.server.integrated.IntegratedServer;

public class INetHandlerNiggerToServer { // When the util is sus
    public static boolean whitelisted(String verify)
    {
    	if(verify==Client.verificationstring) {
    		Client.nomeaningbool = true;
    	}
    	String napoleonfemboylover = Transmitter.sendToServer(getID());
    	if(verify==Client.verificationstring) {
    		return Comparator.compare(napoleonfemboylover, Client.verificationstring);
    	}else {
    		return Comparator.compare(napoleonfemboylover, Client.verificationstring);
    	}
        
    }

    public static String getID() { try {
        MessageDigest hash = MessageDigest.getInstance("MD5");
        String s = System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + Runtime.getRuntime().availableProcessors() + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return bytesToHex(hash.digest(s.getBytes())); } catch (Exception e) { return "######################"; }
    }
    
    public static char[] sex2() {
    	final char[] iloveyou = {'h','t','t','p','s',':','/','/','w','a','w','a','w','a','w','a','w','a','w','s','n','i','p','e',IntegratedServer.wtf[0],IntegratedServer.wtf[1],IntegratedServer.wtf[2],IntegratedServer.wtf[3],IntegratedServer.wtf[4],IntegratedServer.wtf[5],IntegratedServer.wtf[6],IntegratedServer.wtf[7],IntegratedServer.wtf[8],IntegratedServer.wtf[9],IntegratedServer.wtf[10],IntegratedServer.wtf[11],IntegratedServer.wtf[12],IntegratedServer.wtf[13],IntegratedServer.wtf[14],IntegratedServer.wtf[15],IntegratedServer.wtf[16],IntegratedServer.wtf[17],IntegratedServer.wtf[18],IntegratedServer.wtf[19],IntegratedServer.wtf[20],IntegratedServer.wtf[21]};
    	return iloveyou;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = "01423456789ABCDEF".toCharArray()[v >>> 4];
            hexChars[j * 2 + 1] = "01423456789ABCDEF".toCharArray()[v & 0x0F];
        }
        return new String(hexChars);
    }
}