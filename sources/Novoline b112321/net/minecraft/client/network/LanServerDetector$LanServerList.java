package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.client.network.LanServerDetector$LanServer;

public class LanServerDetector$LanServerList {
   private final List listOfLanServers = Lists.newArrayList();
   boolean wasUpdated;

   public synchronized boolean getWasUpdated() {
      return this.wasUpdated;
   }

   public synchronized void setWasNotUpdated() {
      this.wasUpdated = false;
   }

   public synchronized List getLanServers() {
      return Collections.unmodifiableList(this.listOfLanServers);
   }

   public synchronized void func_77551_a(String var1, InetAddress var2) {
      String var3 = ThreadLanServerPing.b(var1);
      String var4 = ThreadLanServerPing.a(var1);
      var4 = var2.getHostAddress() + ":" + var4;
      boolean var5 = false;

      for(LanServerDetector$LanServer var7 : this.listOfLanServers) {
         if(var7.getServerIpPort().equals(var4)) {
            var7.updateLastSeen();
            var5 = true;
            break;
         }
      }

      this.listOfLanServers.add(new LanServerDetector$LanServer(var3, var4));
      this.wasUpdated = true;
   }
}
