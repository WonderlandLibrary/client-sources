package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.util.HttpUtil;

public class PlayerUsageSnooper {
   private final Map field_152774_b = Maps.newHashMap();
   private final Map field_152773_a = Maps.newHashMap();
   private static final String __OBFID = "CL_00001515";
   private int selfCounter;
   private final long minecraftStartTimeMilis;
   private boolean isRunning;
   private final String uniqueID = UUID.randomUUID().toString();
   private final Object syncLock = new Object();
   private final URL serverUrl;
   private final IPlayerUsage playerStatsCollector;
   private final Timer threadTrigger = new Timer("Snooper Timer", true);

   static URL access$6(PlayerUsageSnooper var0) {
      return var0.serverUrl;
   }

   static Object access$1(PlayerUsageSnooper var0) {
      return var0.syncLock;
   }

   public void startSnooper() {
      if (!this.isRunning) {
         this.isRunning = true;
         this.func_152766_h();
         this.threadTrigger.schedule(new TimerTask(this) {
            final PlayerUsageSnooper this$0;
            private static final String __OBFID = "CL_00001516";

            {
               this.this$0 = var1;
            }

            public void run() {
               if (PlayerUsageSnooper.access$0(this.this$0).isSnooperEnabled()) {
                  HashMap var1;
                  synchronized(PlayerUsageSnooper.access$1(this.this$0)) {
                     var1 = Maps.newHashMap(PlayerUsageSnooper.access$2(this.this$0));
                     if (PlayerUsageSnooper.access$3(this.this$0) == 0) {
                        var1.putAll(PlayerUsageSnooper.access$4(this.this$0));
                     }

                     var1.put("snooper_count", PlayerUsageSnooper.access$308(this.this$0));
                     var1.put("snooper_token", PlayerUsageSnooper.access$5(this.this$0));
                  }

                  HttpUtil.postMap(PlayerUsageSnooper.access$6(this.this$0), var1, true);
               }

            }
         }, 0L, 900000L);
      }

   }

   static int access$3(PlayerUsageSnooper var0) {
      return var0.selfCounter;
   }

   public long getMinecraftStartTimeMillis() {
      return this.minecraftStartTimeMilis;
   }

   static String access$5(PlayerUsageSnooper var0) {
      return var0.uniqueID;
   }

   public boolean isSnooperRunning() {
      return this.isRunning;
   }

   public void addClientStat(String var1, Object var2) {
      Object var3 = this.syncLock;
      synchronized(this.syncLock) {
         this.field_152774_b.put(var1, var2);
      }
   }

   static int access$308(PlayerUsageSnooper var0) {
      return var0.selfCounter++;
   }

   public PlayerUsageSnooper(String var1, IPlayerUsage var2, long var3) {
      try {
         this.serverUrl = new URL(String.valueOf((new StringBuilder("http://snoop.minecraft.net/")).append(var1).append("?version=").append(2)));
      } catch (MalformedURLException var6) {
         throw new IllegalArgumentException();
      }

      this.playerStatsCollector = var2;
      this.minecraftStartTimeMilis = var3;
   }

   public void addStatToSnooper(String var1, Object var2) {
      Object var3 = this.syncLock;
      synchronized(this.syncLock) {
         this.field_152773_a.put(var1, var2);
      }
   }

   public void stopSnooper() {
      this.threadTrigger.cancel();
   }

   public String getUniqueID() {
      return this.uniqueID;
   }

   public Map getCurrentStats() {
      LinkedHashMap var1 = Maps.newLinkedHashMap();
      Object var2 = this.syncLock;
      synchronized(this.syncLock) {
         this.addMemoryStatsToSnooper();
         Iterator var4 = this.field_152773_a.entrySet().iterator();

         Entry var5;
         while(var4.hasNext()) {
            var5 = (Entry)var4.next();
            var1.put(var5.getKey(), var5.getValue().toString());
         }

         var4 = this.field_152774_b.entrySet().iterator();

         while(var4.hasNext()) {
            var5 = (Entry)var4.next();
            var1.put(var5.getKey(), var5.getValue().toString());
         }

         return var1;
      }
   }

   static IPlayerUsage access$0(PlayerUsageSnooper var0) {
      return var0.playerStatsCollector;
   }

   public void addMemoryStatsToSnooper() {
      this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
      this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
      this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
      this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
      this.playerStatsCollector.addServerStatsToSnooper(this);
   }

   private void func_152766_h() {
      this.addJvmArgsToSnooper();
      this.addClientStat("snooper_token", this.uniqueID);
      this.addStatToSnooper("snooper_token", this.uniqueID);
      this.addStatToSnooper("os_name", System.getProperty("os.name"));
      this.addStatToSnooper("os_version", System.getProperty("os.version"));
      this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
      this.addStatToSnooper("java_version", System.getProperty("java.version"));
      this.addStatToSnooper("version", "1.8");
      this.playerStatsCollector.addServerTypeToSnooper(this);
   }

   static Map access$4(PlayerUsageSnooper var0) {
      return var0.field_152773_a;
   }

   private void addJvmArgsToSnooper() {
      RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
      List var2 = var1.getInputArguments();
      int var3 = 0;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         if (var5.startsWith("-X")) {
            this.addClientStat(String.valueOf((new StringBuilder("jvm_arg[")).append(var3++).append("]")), var5);
         }
      }

      this.addClientStat("jvm_args", var3);
   }

   static Map access$2(PlayerUsageSnooper var0) {
      return var0.field_152774_b;
   }
}
