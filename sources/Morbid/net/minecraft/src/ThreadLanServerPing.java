package net.minecraft.src;

import java.io.*;
import java.net.*;
import net.minecraft.client.*;

public class ThreadLanServerPing extends Thread
{
    private final String motd;
    private final DatagramSocket socket;
    private boolean isStopping;
    private final String address;
    
    public ThreadLanServerPing(final String par1Str, final String par2Str) throws IOException {
        super("LanServerPinger");
        this.isStopping = true;
        this.motd = par1Str;
        this.address = par2Str;
        this.setDaemon(true);
        this.socket = new DatagramSocket();
    }
    
    @Override
    public void run() {
        final String var1 = getPingResponse(this.motd, this.address);
        final byte[] var2 = var1.getBytes();
        while (!this.isInterrupted() && this.isStopping) {
            try {
                final InetAddress var3 = InetAddress.getByName("224.0.2.60");
                final DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
                this.socket.send(var4);
            }
            catch (IOException var5) {
                Minecraft.getMinecraft().getLogAgent().logWarning("LanServerPinger: " + var5.getMessage());
                break;
            }
            try {
                Thread.sleep(1500L);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }
    
    public static String getPingResponse(final String par0Str, final String par1Str) {
        return "[MOTD]" + par0Str + "[/MOTD][AD]" + par1Str + "[/AD]";
    }
    
    public static String getMotdFromPingResponse(final String par0Str) {
        final int var1 = par0Str.indexOf("[MOTD]");
        if (var1 < 0) {
            return "missing no";
        }
        final int var2 = par0Str.indexOf("[/MOTD]", var1 + "[MOTD]".length());
        return (var2 < var1) ? "missing no" : par0Str.substring(var1 + "[MOTD]".length(), var2);
    }
    
    public static String getAdFromPingResponse(final String par0Str) {
        final int var1 = par0Str.indexOf("[/MOTD]");
        if (var1 < 0) {
            return null;
        }
        final int var2 = par0Str.indexOf("[/MOTD]", var1 + "[/MOTD]".length());
        if (var2 >= 0) {
            return null;
        }
        final int var3 = par0Str.indexOf("[AD]", var1 + "[/MOTD]".length());
        if (var3 < 0) {
            return null;
        }
        final int var4 = par0Str.indexOf("[/AD]", var3 + "[AD]".length());
        return (var4 < var3) ? null : par0Str.substring(var3 + "[AD]".length(), var4);
    }
}
