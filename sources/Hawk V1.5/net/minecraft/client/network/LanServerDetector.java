package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector {
   private static final String __OBFID = "CL_00001133";
   private static final AtomicInteger field_148551_a = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();

   static Logger access$1() {
      return logger;
   }

   static AtomicInteger access$0() {
      return field_148551_a;
   }

   public static class LanServerList {
      boolean wasUpdated;
      private static final String __OBFID = "CL_00001136";
      private List listOfLanServers = Lists.newArrayList();

      public synchronized boolean getWasUpdated() {
         return this.wasUpdated;
      }

      public synchronized List getLanServers() {
         return Collections.unmodifiableList(this.listOfLanServers);
      }

      public synchronized void func_77551_a(String var1, InetAddress var2) {
         String var3 = ThreadLanServerPing.getMotdFromPingResponse(var1);
         String var4 = ThreadLanServerPing.getAdFromPingResponse(var1);
         if (var4 != null) {
            var4 = String.valueOf((new StringBuilder(String.valueOf(var2.getHostAddress()))).append(":").append(var4));
            boolean var5 = false;
            Iterator var6 = this.listOfLanServers.iterator();

            while(var6.hasNext()) {
               LanServerDetector.LanServer var7 = (LanServerDetector.LanServer)var6.next();
               if (var7.getServerIpPort().equals(var4)) {
                  var7.updateLastSeen();
                  var5 = true;
                  break;
               }
            }

            if (!var5) {
               this.listOfLanServers.add(new LanServerDetector.LanServer(var3, var4));
               this.wasUpdated = true;
            }
         }

      }

      public synchronized void setWasNotUpdated() {
         this.wasUpdated = false;
      }
   }

   public static class ThreadLanServerFind extends Thread {
      private static final String __OBFID = "CL_00001135";
      private final InetAddress broadcastAddress;
      private final MulticastSocket socket;
      private final LanServerDetector.LanServerList localServerList;

      public ThreadLanServerFind(LanServerDetector.LanServerList var1) throws IOException {
         super(String.valueOf((new StringBuilder("LanServerDetector #")).append(LanServerDetector.access$0().incrementAndGet())));
         this.localServerList = var1;
         this.setDaemon(true);
         this.socket = new MulticastSocket(4445);
         this.broadcastAddress = InetAddress.getByName("224.0.2.60");
         this.socket.setSoTimeout(5000);
         this.socket.joinGroup(this.broadcastAddress);
      }

      public void run() {
         byte[] var1 = new byte[1024];

         while(!this.isInterrupted()) {
            DatagramPacket var2 = new DatagramPacket(var1, var1.length);

            try {
               this.socket.receive(var2);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException var6) {
               LanServerDetector.access$1().error("Couldn't ping server", var6);
               break;
            }

            String var3 = new String(var2.getData(), var2.getOffset(), var2.getLength());
            LanServerDetector.access$1().debug(String.valueOf((new StringBuilder()).append(var2.getAddress()).append(": ").append(var3)));
            this.localServerList.func_77551_a(var3, var2.getAddress());
         }

         try {
            this.socket.leaveGroup(this.broadcastAddress);
         } catch (IOException var4) {
         }

         this.socket.close();
      }
   }

   public static class LanServer {
      private static final String __OBFID = "CL_00001134";
      private String lanServerMotd;
      private String lanServerIpPort;
      private long timeLastSeen;

      public LanServer(String var1, String var2) {
         this.lanServerMotd = var1;
         this.lanServerIpPort = var2;
         this.timeLastSeen = Minecraft.getSystemTime();
      }

      public void updateLastSeen() {
         this.timeLastSeen = Minecraft.getSystemTime();
      }

      public String getServerIpPort() {
         return this.lanServerIpPort;
      }

      public String getServerMotd() {
         return this.lanServerMotd;
      }
   }
}
