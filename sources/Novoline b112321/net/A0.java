package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.LanServerDetector$LanServerList;

public class A0 extends Thread {
   private final LanServerDetector$LanServerList b;
   private final InetAddress a;
   private final MulticastSocket c;

   public A0(LanServerDetector$LanServerList var1) throws IOException {
      super("LanServerDetector #" + LanServerDetector.access$000().incrementAndGet());
      this.b = var1;
      this.setDaemon(true);
      this.c = new MulticastSocket(4445);
      this.a = InetAddress.getByName("224.0.2.60");
      this.c.setSoTimeout(5000);
      this.c.joinGroup(this.a);
   }

   public void run() {
      byte[] var1 = new byte[1024];

      while(!this.isInterrupted()) {
         DatagramPacket var2 = new DatagramPacket(var1, var1.length);

         try {
            this.c.receive(var2);
         } catch (SocketTimeoutException var5) {
            continue;
         } catch (IOException var6) {
            LanServerDetector.access$100().error("Couldn\'t ping server", var6);
            break;
         }

         String var3 = new String(var2.getData(), var2.getOffset(), var2.getLength());
         LanServerDetector.access$100().debug(var2.getAddress() + ": " + var3);
         this.b.func_77551_a(var3, var2.getAddress());
      }

      try {
         this.c.leaveGroup(this.a);
      } catch (IOException var4) {
         ;
      }

      this.c.close();
   }
}
