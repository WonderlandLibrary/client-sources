package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import net.ami;
import net.minecraft.profiler.IPlayerUsage;

public class PlayerUsageSnooper {
   private final Map field_152773_a = Maps.newHashMap();
   private final Map field_152774_b = Maps.newHashMap();
   private final String uniqueID = UUID.randomUUID().toString();
   private final URL serverUrl;
   private final IPlayerUsage playerStatsCollector;
   private final Timer threadTrigger = new Timer("Snooper Timer", true);
   private final Object syncLock = new Object();
   private final long minecraftStartTimeMilis;
   private boolean isRunning;
   private int selfCounter;

   public PlayerUsageSnooper(String var1, IPlayerUsage var2, long var3) {
      try {
         this.serverUrl = new URL("http://snoop.minecraft.net/" + var1 + "?version=" + 2);
      } catch (MalformedURLException var6) {
         throw new IllegalArgumentException();
      }

      this.playerStatsCollector = var2;
      this.minecraftStartTimeMilis = var3;
   }

   public void startSnooper() {
      if(!this.isRunning) {
         this.isRunning = true;
         this.func_152766_h();
         this.threadTrigger.schedule(new ami(this), 0L, 900000L);
      }

   }

   private void func_152766_h() {
      this.addJvmArgsToSnooper();
      this.addClientStat("snooper_token", this.uniqueID);
      this.addStatToSnooper("snooper_token", this.uniqueID);
      this.addStatToSnooper("os_name", System.getProperty("os.name"));
      this.addStatToSnooper("os_version", System.getProperty("os.version"));
      this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
      this.addStatToSnooper("java_version", System.getProperty("java.version"));
      this.addClientStat("version", "1.8.8");
      this.playerStatsCollector.addServerTypeToSnooper(this);
   }

   private void addJvmArgsToSnooper() {
      RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
      List var2 = var1.getInputArguments();
      int var3 = 0;

      for(String var5 : var2) {
         if(var5.startsWith("-X")) {
            this.addClientStat("jvm_arg[" + var3++ + "]", var5);
         }
      }

      this.addClientStat("jvm_args", Integer.valueOf(var3));
   }

   public void addMemoryStatsToSnooper() {
      this.addStatToSnooper("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
      this.addStatToSnooper("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
      this.addStatToSnooper("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
      this.addStatToSnooper("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
      this.playerStatsCollector.addServerStatsToSnooper(this);
   }

   public void addClientStat(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void addStatToSnooper(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public Map getCurrentStats() {
      // $FF: Couldn't be decompiled
   }

   public boolean isSnooperRunning() {
      return this.isRunning;
   }

   public void stopSnooper() {
      this.threadTrigger.cancel();
   }

   public String getUniqueID() {
      return this.uniqueID;
   }

   public long getMinecraftStartTimeMillis() {
      return this.minecraftStartTimeMilis;
   }

   static IPlayerUsage access$000(PlayerUsageSnooper var0) {
      return var0.playerStatsCollector;
   }

   static Object access$100(PlayerUsageSnooper var0) {
      return var0.syncLock;
   }

   static Map access$200(PlayerUsageSnooper var0) {
      return var0.field_152774_b;
   }

   static int access$300(PlayerUsageSnooper var0) {
      return var0.selfCounter;
   }

   static Map access$400(PlayerUsageSnooper var0) {
      return var0.field_152773_a;
   }

   static int access$308(PlayerUsageSnooper var0) {
      return var0.selfCounter++;
   }

   static String access$500(PlayerUsageSnooper var0) {
      return var0.uniqueID;
   }

   static URL access$600(PlayerUsageSnooper var0) {
      return var0.serverUrl;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
