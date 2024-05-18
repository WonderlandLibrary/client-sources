package wwe;

import javax.net.ssl.*;
import java.io.*;
import java.util.*;
import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

public class wGez
{
    public static ArrayList<aRkr> VkYf;
    public static fIdW PrrU;
    public static final int vAnj = 25;
    
    public wGez() {
        super();
    }
    
    public static void MVoP(final aRkr aRkr) {
        wGez.VkYf.add(aRkr);
    }
    
    public static void DibB(final String s) {
        wGez.VkYf.add(new aRkr(s));
    }
    
    public static void TyxQ(final String s, final String s2) {
        wGez.VkYf.add(new aRkr(s, s2));
    }
    
    public static String SWOQ(final String s) {
        return s.replaceAll("(?s).", "*");
    }
    
    public static ArrayList<aRkr> kjOC() {
        return wGez.VkYf;
    }
    
    public static String QtPG(final String s, final String s2) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpsURLConnection)new URL(s).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Type", Integer.toString(s2.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language", "en-US");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(s2);
            dataOutputStream.flush();
            dataOutputStream.close();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
                if (wGez.\u200d) {
                    throw null;
                }
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
    
    public static String lzkq(final String s) {
        return QtPG("https://authserver.mojang.com/authenticate", "{\n \"agent\":{\n \"name\": \"Minecraft\",\n \"version\": \"1\"\n },\n \"username\": \"" + s.split(":")[0] + "\",\n \"password\": \"" + s.split(":")[1] + "\",\n \"clientToken\": \"" + UUID.randomUUID().toString() + "\"\n}");
    }
    
    public static String pemn(final String s, final String s2) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection)new URL(s).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(s2.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language", "en-US");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(s2);
            dataOutputStream.flush();
            dataOutputStream.close();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
                if (wGez.\u200d) {
                    throw null;
                }
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
    
    public static Session UlIb(final String username, final String password) {
        if (username == null || username.length() <= 0 || password == null || password.length() <= 0) {
            return null;
        }
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "LEGACY");
        }
        catch (AuthenticationException ex) {
            wGez.PrrU.EMav = "".concat("§cBad Login §7(").concat(username).concat(")");
            ex.printStackTrace();
            return null;
        }
    }
    
    static {
        wGez.VkYf = new ArrayList<aRkr>();
        wGez.PrrU = new fIdW();
    }
}
