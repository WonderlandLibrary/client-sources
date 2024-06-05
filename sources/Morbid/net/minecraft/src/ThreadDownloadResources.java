package net.minecraft.src;

import net.minecraft.client.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class ThreadDownloadResources extends Thread
{
    public File resourcesFolder;
    private Minecraft mc;
    private boolean closing;
    
    public ThreadDownloadResources(final File par1File, final Minecraft par2Minecraft) {
        this.closing = false;
        this.mc = par2Minecraft;
        this.setName("Resource download thread");
        this.setDaemon(true);
        this.resourcesFolder = new File(par1File, "resources/");
        if (!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
        }
    }
    
    @Override
    public void run() {
        try {
            final URL var1 = new URL("http://s3.amazonaws.com/MinecraftResources/");
            final DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
            final DocumentBuilder var3 = var2.newDocumentBuilder();
            final Document var4 = var3.parse(var1.openStream());
            final NodeList var5 = var4.getElementsByTagName("Contents");
            for (int var6 = 0; var6 < 2; ++var6) {
                for (int var7 = 0; var7 < var5.getLength(); ++var7) {
                    final Node var8 = var5.item(var7);
                    if (var8.getNodeType() == 1) {
                        final Element var9 = (Element)var8;
                        final String var10 = var9.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
                        final long var11 = Long.parseLong(var9.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
                        if (var11 > 0L) {
                            this.downloadAndInstallResource(var1, var10, var11, var6);
                            if (this.closing) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception var12) {
            this.loadResource(this.resourcesFolder, "");
            var12.printStackTrace();
        }
    }
    
    public void reloadResources() {
        this.loadResource(this.resourcesFolder, "");
    }
    
    private void loadResource(final File par1File, final String par2Str) {
        final File[] var3 = par1File.listFiles();
        for (int var4 = 0; var4 < var3.length; ++var4) {
            if (var3[var4].isDirectory()) {
                this.loadResource(var3[var4], String.valueOf(par2Str) + var3[var4].getName() + "/");
            }
            else {
                try {
                    this.mc.installResource(String.valueOf(par2Str) + var3[var4].getName(), var3[var4]);
                }
                catch (Exception var5) {
                    this.mc.getLogAgent().logWarning("Failed to add " + par2Str + var3[var4].getName() + " in resources");
                }
            }
        }
    }
    
    private void downloadAndInstallResource(final URL par1URL, final String par2Str, final long par3, final int par5) {
        try {
            final int var6 = par2Str.indexOf("/");
            final String var7 = par2Str.substring(0, var6);
            if (var7.equalsIgnoreCase("sound3")) {
                if (par5 != 0) {
                    return;
                }
            }
            else if (par5 != 1) {
                return;
            }
            final File var8 = new File(this.resourcesFolder, par2Str);
            if (!var8.exists() || var8.length() != par3) {
                var8.getParentFile().mkdirs();
                final String var9 = par2Str.replaceAll(" ", "%20");
                this.downloadResource(new URL(par1URL, var9), var8, par3);
                if (this.closing) {
                    return;
                }
            }
            this.mc.installResource(par2Str, var8);
        }
        catch (Exception var10) {
            var10.printStackTrace();
        }
    }
    
    private void downloadResource(final URL par1URL, final File par2File, final long par3) throws IOException {
        final byte[] var5 = new byte[4096];
        final DataInputStream var6 = new DataInputStream(par1URL.openStream());
        final DataOutputStream var7 = new DataOutputStream(new FileOutputStream(par2File));
        final boolean var8 = false;
        int var9;
        while ((var9 = var6.read(var5)) >= 0) {
            var7.write(var5, 0, var9);
            if (this.closing) {
                var7.close();
                return;
            }
        }
        var6.close();
        var7.close();
    }
    
    public void closeMinecraft() {
        this.closing = true;
    }
}
