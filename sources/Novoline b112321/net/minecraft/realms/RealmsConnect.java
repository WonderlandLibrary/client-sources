package net.minecraft.realms;

import net.ap1;
import net.minecraft.network.NetworkManager;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RealmsScreen onlineScreen;
   private volatile boolean aborted = false;
   private NetworkManager connection;

   public RealmsConnect(RealmsScreen var1) {
      this.onlineScreen = var1;
   }

   public void connect(String var1, int var2) {
      Realms.setConnectedToRealms(true);
      (new ap1(this, "Realms-connect-task", var1, var2)).start();
   }

   public void abort() {
      this.aborted = true;
   }

   public void tick() {
      if(this.connection != null) {
         if(this.connection.isChannelOpen()) {
            this.connection.processReceivedPackets();
         } else {
            this.connection.checkDisconnected();
         }
      }

   }

   static boolean access$000(RealmsConnect var0) {
      return var0.aborted;
   }

   static NetworkManager access$102(RealmsConnect var0, NetworkManager var1) {
      return var0.connection = var1;
   }

   static NetworkManager access$100(RealmsConnect var0) {
      return var0.connection;
   }

   static RealmsScreen access$200(RealmsConnect var0) {
      return var0.onlineScreen;
   }

   static Logger access$300() {
      return LOGGER;
   }
}
