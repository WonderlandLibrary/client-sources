package net.minecraft.client.multiplayer;

import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import net.af_;

public class ServerAddress {
   private final String ipAddress;
   private final int serverPort;

   private ServerAddress(String var1, int var2) {
      this.ipAddress = var1;
      this.serverPort = var2;
   }

   public String getIP() {
      return IDN.toASCII(this.ipAddress);
   }

   public int getPort() {
      return this.serverPort;
   }

   public static ServerAddress b(String var0) {
      return null;
   }

   private static String[] a(String var0) {
      String var1 = "com.sun.jndi.dns.DnsContextFactory";
      String var10000 = "com.sun.jndi.dns.DnsContextFactory";

      try {
         af_.a(var10000);
         Hashtable var2 = new Hashtable();
         var2.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         var2.put("java.naming.provider.url", "dns:");
         var2.put("com.sun.jndi.dns.timeout.retries", "1");
         InitialDirContext var3 = new InitialDirContext(var2);
         Attributes var4 = var3.getAttributes("_minecraft._tcp." + var0, new String[]{"SRV"});
         String[] var5 = var4.get("srv").get().toString().split(" ", 4);
         return new String[]{var5[3], var5[2]};
      } catch (Throwable var6) {
         return new String[]{var0, Integer.toString(25565)};
      }
   }

   private static int parseIntWithDefault(String var0, int var1) {
      String var10000 = var0;

      try {
         return Integer.parseInt(var10000.trim());
      } catch (Exception var3) {
         return var1;
      }
   }
}
