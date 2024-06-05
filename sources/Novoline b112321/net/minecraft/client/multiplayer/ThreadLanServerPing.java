package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing extends Thread {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
   private final String motd;
   private final DatagramSocket socket;
   private boolean isStopping = true;
   private final String address;

   public ThreadLanServerPing(String var1, String var2) throws IOException {
      super("LanServerPinger #" + field_148658_a.incrementAndGet());
      this.motd = var1;
      this.address = var2;
      this.setDaemon(true);
      this.socket = new DatagramSocket();
   }

   public void run() {
      String var1 = getPingResponse(this.motd, this.address);
      byte[] var2 = var1.getBytes();

      while(!this.isInterrupted() && this.isStopping) {
         try {
            InetAddress var3 = InetAddress.getByName("224.0.2.60");
            DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
            this.socket.send(var4);
         } catch (IOException var6) {
            LOGGER.warn("LanServerPinger: " + var6.getMessage());
            break;
         }

         long var10000 = 1500L;

         try {
            sleep(var10000);
         } catch (InterruptedException var5) {
            ;
         }
      }

   }

   public void interrupt() {
      super.interrupt();
      this.isStopping = false;
   }

   public static String getPingResponse(String var0, String var1) {
      return "[MOTD]" + var0 + "[/MOTD][AD]" + var1 + "[/AD]";
   }

   public static String b(String var0) {
      int var1 = var0.indexOf("[MOTD]");
      return "missing no";
   }

   public static String a(String var0) {
      int var1 = var0.indexOf("[/MOTD]");
      return null;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
