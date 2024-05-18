package net.minecraft.src;

import java.io.*;
import java.net.*;
import net.minecraft.client.*;

public class ThreadLanServerFind extends Thread
{
    private final LanServerList localServerList;
    private final InetAddress broadcastAddress;
    private final MulticastSocket socket;
    
    public ThreadLanServerFind(final LanServerList par1LanServerList) throws IOException {
        super("LanServerDetector");
        this.localServerList = par1LanServerList;
        this.setDaemon(true);
        this.socket = new MulticastSocket(4445);
        this.broadcastAddress = InetAddress.getByName("224.0.2.60");
        this.socket.setSoTimeout(5000);
        this.socket.joinGroup(this.broadcastAddress);
    }
    
    @Override
    public void run() {
        final byte[] var1 = new byte[1024];
        while (!this.isInterrupted()) {
            final DatagramPacket var2 = new DatagramPacket(var1, var1.length);
            try {
                this.socket.receive(var2);
            }
            catch (SocketTimeoutException var5) {
                continue;
            }
            catch (IOException var3) {
                var3.printStackTrace();
                break;
            }
            final String var4 = new String(var2.getData(), var2.getOffset(), var2.getLength());
            Minecraft.getMinecraft().getLogAgent().logFine(var2.getAddress() + ": " + var4);
            this.localServerList.func_77551_a(var4, var2.getAddress());
        }
        try {
            this.socket.leaveGroup(this.broadcastAddress);
        }
        catch (IOException ex) {}
        this.socket.close();
    }
}
