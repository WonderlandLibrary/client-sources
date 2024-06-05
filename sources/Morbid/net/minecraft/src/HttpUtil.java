package net.minecraft.src;

import java.util.logging.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class HttpUtil
{
    public static String buildPostString(final Map par0Map) {
        final StringBuilder var1 = new StringBuilder();
        for (final Map.Entry var3 : par0Map.entrySet()) {
            if (var1.length() > 0) {
                var1.append('&');
            }
            try {
                var1.append(URLEncoder.encode(var3.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }
            if (var3.getValue() != null) {
                var1.append('=');
                try {
                    var1.append(URLEncoder.encode(var3.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException var5) {
                    var5.printStackTrace();
                }
            }
        }
        return var1.toString();
    }
    
    public static String sendPost(final ILogAgent par0ILogAgent, final URL par1URL, final Map par2Map, final boolean par3) {
        return sendPost(par0ILogAgent, par1URL, buildPostString(par2Map), par3);
    }
    
    public static String sendPost(final ILogAgent par0ILogAgent, final URL par1URL, final String par2Str, final boolean par3) {
        try {
            final HttpURLConnection var4 = (HttpURLConnection)par1URL.openConnection();
            var4.setRequestMethod("POST");
            var4.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            var4.setRequestProperty("Content-Length", new StringBuilder().append(par2Str.getBytes().length).toString());
            var4.setRequestProperty("Content-Language", "en-US");
            var4.setUseCaches(false);
            var4.setDoInput(true);
            var4.setDoOutput(true);
            final DataOutputStream var5 = new DataOutputStream(var4.getOutputStream());
            var5.writeBytes(par2Str);
            var5.flush();
            var5.close();
            final BufferedReader var6 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
            final StringBuffer var7 = new StringBuffer();
            String var8;
            while ((var8 = var6.readLine()) != null) {
                var7.append(var8);
                var7.append('\r');
            }
            var6.close();
            return var7.toString();
        }
        catch (Exception var9) {
            if (!par3) {
                if (par0ILogAgent != null) {
                    par0ILogAgent.logSevereException("Could not post to " + par1URL, var9);
                }
                else {
                    Logger.getAnonymousLogger().log(Level.SEVERE, "Could not post to " + par1URL, var9);
                }
            }
            return "";
        }
    }
    
    public static void downloadTexturePack(final File par0File, final String par1Str, final IDownloadSuccess par2IDownloadSuccess, final Map par3Map, final int par4, final IProgressUpdate par5IProgressUpdate) {
        final Thread var6 = new Thread(new HttpUtilRunnable(par5IProgressUpdate, par1Str, par3Map, par0File, par2IDownloadSuccess, par4));
        var6.setDaemon(true);
        var6.start();
    }
    
    public static int func_76181_a() throws IOException {
        ServerSocket var0 = null;
        final boolean var2 = true;
        int var3;
        try {
            var0 = new ServerSocket(0);
            var3 = var0.getLocalPort();
        }
        finally {
            try {
                if (var0 != null) {
                    var0.close();
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (var0 != null) {
                var0.close();
            }
        }
        catch (IOException ex2) {}
        return var3;
    }
    
    public static String[] loginToMinecraft(final ILogAgent par0ILogAgent, final String par1Str, final String par2Str) {
        final HashMap var3 = new HashMap();
        var3.put("user", par1Str);
        var3.put("password", par2Str);
        var3.put("version", 13);
        String var4;
        try {
            var4 = sendPost(par0ILogAgent, new URL("http://login.minecraft.net/"), var3, false);
        }
        catch (MalformedURLException var5) {
            var5.printStackTrace();
            return null;
        }
        if (var4 == null || var4.length() == 0) {
            if (par0ILogAgent == null) {
                System.out.println("Failed to authenticate: Can't connect to minecraft.net");
            }
            else {
                par0ILogAgent.logWarning("Failed to authenticate: Can't connect to minecraft.net");
            }
            return null;
        }
        if (!var4.contains(":")) {
            if (par0ILogAgent == null) {
                System.out.println("Failed to authenticate: " + var4);
            }
            else {
                par0ILogAgent.logWarning("Failed to authenticae: " + var4);
            }
            return null;
        }
        final String[] var6 = var4.split(":");
        return new String[] { var6[2], var6[3] };
    }
    
    public static String func_104145_a(final URL par0URL) throws IOException {
        final HttpURLConnection var1 = (HttpURLConnection)par0URL.openConnection();
        var1.setRequestMethod("GET");
        final BufferedReader var2 = new BufferedReader(new InputStreamReader(var1.getInputStream()));
        final StringBuilder var3 = new StringBuilder();
        String var4;
        while ((var4 = var2.readLine()) != null) {
            var3.append(var4);
            var3.append('\r');
        }
        var2.close();
        return var3.toString();
    }
}
