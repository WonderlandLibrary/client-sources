package net.minecraft.client.network;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class LanServerDetector
{
    private static final AtomicInteger field_148551_a;
    private static final Logger logger;
    
    static {
        field_148551_a = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public static class LanServer
    {
        private String lanServerMotd;
        private String lanServerIpPort;
        private long timeLastSeen;
        
        public LanServer(final String p_i1319_1_, final String p_i1319_2_) {
            this.lanServerMotd = p_i1319_1_;
            this.lanServerIpPort = p_i1319_2_;
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public String getServerMotd() {
            return this.lanServerMotd;
        }
        
        public String getServerIpPort() {
            return this.lanServerIpPort;
        }
        
        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
    }
    
    public static class LanServerList
    {
        boolean wasUpdated;
        private List listOfLanServers;
        
        public LanServerList() {
            this.listOfLanServers = Lists.newArrayList();
        }
        
        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }
        
        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }
        
        public synchronized List getLanServers() {
            return Collections.unmodifiableList((List<?>)this.listOfLanServers);
        }
        
        public synchronized void func_77551_a(final String p_77551_1_, final InetAddress p_77551_2_) {
            final String var3 = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
            String var4 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);
            if (var4 != null) {
                var4 = p_77551_2_.getHostAddress() + ":" + var4;
                boolean var5 = false;
                for (final LanServer var7 : this.listOfLanServers) {
                    if (var7.getServerIpPort().equals(var4)) {
                        var7.updateLastSeen();
                        var5 = true;
                        break;
                    }
                }
                if (!var5) {
                    this.listOfLanServers.add(new LanServer(var3, var4));
                    this.wasUpdated = true;
                }
            }
        }
    }
    
    public static class ThreadLanServerFind extends Thread
    {
        private final LanServerList localServerList;
        private final InetAddress broadcastAddress;
        private final MulticastSocket socket;
        
        public ThreadLanServerFind(final LanServerList p_i1320_1_) throws IOException {
            super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
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
                    LanServerDetector.logger.error("Couldn't ping server", (Throwable)var4);
                    break;
                }
                final String var5 = new String(var3.getData(), var3.getOffset(), var3.getLength());
                LanServerDetector.logger.debug(var3.getAddress() + ": " + var5);
                this.localServerList.func_77551_a(var5, var3.getAddress());
            }
            try {
                this.socket.leaveGroup(this.broadcastAddress);
            }
            catch (IOException ex) {}
            this.socket.close();
        }
    }
}
