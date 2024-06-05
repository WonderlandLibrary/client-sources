package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.cj;
import net.optifine.Config;
import net.optifine.Lagometer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List sectionList = Lists.newArrayList();
   private final List timestampList = Lists.newArrayList();
   public boolean profilingEnabled;
   private String profilingSection = "";
   private final Map profilingMap = Maps.newHashMap();
   public boolean profilerGlobalEnabled = true;
   private boolean profilerLocalEnabled;
   private static final String i = "scheduledExecutables";
   private static final String f = "tick";
   private static final String e = "preRenderErrors";
   private static final String p = "render";
   private static final String q = "display";
   private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
   private static final int HASH_TICK = "tick".hashCode();
   private static final int HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
   private static final int n = "render".hashCode();
   private static final int j = "display".hashCode();

   public Profiler() {
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
   }

   public void clearProfiling() {
      this.profilingMap.clear();
      this.profilingSection = "";
      this.sectionList.clear();
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
   }

   public void startSection(String var1) {
      if(Lagometer.isActive()) {
         int var2 = var1.hashCode();
         if(var2 == HASH_SCHEDULED_EXECUTABLES && var1.equals("scheduledExecutables")) {
            Lagometer.timerScheduledExecutables.start();
         } else if(var2 == HASH_TICK && var1.equals("tick") && Config.isMinecraftThread()) {
            Lagometer.timerScheduledExecutables.end();
            Lagometer.timerTick.start();
         } else if(var2 == HASH_PRE_RENDER_ERRORS && var1.equals("preRenderErrors")) {
            Lagometer.timerTick.end();
         }
      }

      if(this.profilerLocalEnabled && this.profilingEnabled) {
         if(!this.profilingSection.isEmpty()) {
            this.profilingSection = this.profilingSection + ".";
         }

         this.profilingSection = this.profilingSection + var1;
         this.sectionList.add(this.profilingSection);
         this.timestampList.add(Long.valueOf(System.nanoTime()));
      }

   }

   public void endSection() {
      if(this.profilerLocalEnabled && this.profilingEnabled) {
         long var1 = System.nanoTime();
         long var3 = ((Long)this.timestampList.remove(this.timestampList.size() - 1)).longValue();
         this.sectionList.remove(this.sectionList.size() - 1);
         long var5 = var1 - var3;
         if(this.profilingMap.containsKey(this.profilingSection)) {
            this.profilingMap.put(this.profilingSection, Long.valueOf(((Long)this.profilingMap.get(this.profilingSection)).longValue() + var5));
         } else {
            this.profilingMap.put(this.profilingSection, Long.valueOf(var5));
         }

         if(var5 > 100000000L) {
            LOGGER.warn("Something\'s taking too long! \'" + this.profilingSection + "\' took aprox " + (double)var5 / 1000000.0D + " ms");
         }

         this.profilingSection = !this.sectionList.isEmpty()?(String)this.sectionList.get(this.sectionList.size() - 1):"";
      }

   }

   public List getProfilingData(String var1) {
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
      if(!this.profilerLocalEnabled) {
         return new ArrayList(Collections.singletonList(new cj("root", 0.0D, 0.0D)));
      } else if(!this.profilingEnabled) {
         return null;
      } else {
         long var2 = ((Long)this.profilingMap.getOrDefault("root", Long.valueOf(0L))).longValue();
         long var4 = ((Long)this.profilingMap.getOrDefault(var1, Long.valueOf(-1L))).longValue();
         ArrayList var6 = Lists.newArrayList();
         if(!var1.isEmpty()) {
            var1 = var1 + ".";
         }

         long var7 = 0L;

         for(Object var10 : this.profilingMap.keySet()) {
            if(((String)var10).length() > var1.length() && ((String)var10).startsWith(var1) && ((String)var10).indexOf(".", var1.length() + 1) < 0) {
               var7 += ((Long)this.profilingMap.get(var10)).longValue();
            }
         }

         float var20 = (float)var7;
         if(var7 < var4) {
            var7 = var4;
         }

         if(var2 < var7) {
            var2 = var7;
         }

         for(Object var11 : this.profilingMap.keySet()) {
            String var12 = (String)var11;
            if(var12.length() > var1.length() && var12.startsWith(var1) && var12.indexOf(".", var1.length() + 1) < 0) {
               long var13 = ((Long)this.profilingMap.get(var12)).longValue();
               double var15 = (double)var13 * 100.0D / (double)var7;
               double var17 = (double)var13 * 100.0D / (double)var2;
               String var19 = var12.substring(var1.length());
               var6.add(new cj(var19, var15, var17));
            }
         }

         this.profilingMap.replaceAll(this::lambda$getProfilingData$0);
         if((float)var7 > var20) {
            var6.add(new cj("unspecified", (double)((float)var7 - var20) * 100.0D / (double)var7, (double)((float)var7 - var20) * 100.0D / (double)var2));
         }

         Collections.sort(var6);
         var6.add(0, new cj(var1, 100.0D, (double)var7 * 100.0D / (double)var2));
         return var6;
      }
   }

   public void endStartSection(String var1) {
      if(this.profilerLocalEnabled) {
         this.endSection();
         this.startSection(var1);
      }

   }

   public String getNameOfLastSection() {
      return this.sectionList.isEmpty()?"[UNKNOWN]":(String)this.sectionList.get(this.sectionList.size() - 1);
   }

   private Long lambda$getProfilingData$0(String var1, Long var2) {
      return Long.valueOf(((Long)this.profilingMap.get(var1)).longValue() * 950L / 1000L);
   }
}
