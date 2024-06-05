package net.minecraft.client.network;

import java.io.*;
import java.net.*;

public static class ThreadLanServerFind extends Thread
{
    private final LanServerList localServerList;
    private final InetAddress broadcastAddress;
    private final MulticastSocket socket;
    
    public ThreadLanServerFind(final LanServerList p_i1320_1_) throws IOException {
        super("LanServerDetector #" + LanServerDetector.access$000().incrementAndGet());
        this.localServerList = p_i1320_1_;
        this.setDaemon(true);
        this.socket = new MulticastSocket(4445);
        this.broadcastAddress = InetAddress.getByName("224.0.2.60");
        this.socket.setSoTimeout(5000);
        this.socket.joinGroup(this.broadcastAddress);
    }
    
    @Override
    public void run() {
        final byte[] var2 = new byte[1024];
        while (!this.isInterrupted()) {
            final DatagramPacket var3 = new DatagramPacket(var2, var2.length);
            try {
                this.socket.receive(var3);
            }
            catch (SocketTimeoutException var6) {
                continue;
            }
            catch (IOException var4) {
                LanServerDetector.access$100().error("Couldn't ping server", (Throwable)var4);
                break;
            }
            final String var5 = new String(var3.getData(), var3.getOffset(), var3.getLength());
            LanServerDetector.access$100().debug(var3.getAddress() + ": " + var5);
            this.localServerList.func_77551_a(var5, var3.getAddress());
        }
        try {
            this.socket.leaveGroup(this.broadcastAddress);
        }
        catch (IOException ex) {}
        this.socket.close();
    }
}
