package org.spongepowered.asm.mixin.refmap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;

public final class RemappingReferenceMapper implements IReferenceMapper {
   private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
   private static final String DEFAULT_MAPPING_ENV = "searge";
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final Map srgs = new HashMap();
   private final IReferenceMapper refMap;
   private final Map mappings;
   private final Map cache = new HashMap();

   private RemappingReferenceMapper(MixinEnvironment var1, IReferenceMapper var2) {
      this.refMap = var2;
      this.refMap.setContext(getMappingEnv(var1));
      String var3 = getResource(var1);
      this.mappings = loadSrgs(var3);
      logger.info("Remapping refMap {} using {}", new Object[]{var2.getResourceName(), var3});
   }

   public boolean isDefault() {
      return this.refMap.isDefault();
   }

   public String getResourceName() {
      return this.refMap.getResourceName();
   }

   public String getStatus() {
      return this.refMap.getStatus();
   }

   public String getContext() {
      return this.refMap.getContext();
   }

   public void setContext(String var1) {
   }

   public String remap(String var1, String var2) {
      Map var3 = this.getCache(var1);
      String var4 = (String)var3.get(var2);
      if (var4 == null) {
         var4 = this.refMap.remap(var1, var2);

         Entry var6;
         for(Iterator var5 = this.mappings.entrySet().iterator(); var5.hasNext(); var4 = var4.replace((CharSequence)var6.getKey(), (CharSequence)var6.getValue())) {
            var6 = (Entry)var5.next();
         }

         var3.put(var2, var4);
      }

      return var4;
   }

   private Map getCache(String var1) {
      Object var2 = (Map)this.cache.get(var1);
      if (var2 == null) {
         var2 = new HashMap();
         this.cache.put(var1, var2);
      }

      return (Map)var2;
   }

   public String remapWithContext(String var1, String var2, String var3) {
      return this.refMap.remapWithContext(var1, var2, var3);
   }

   private static Map loadSrgs(String var0) {
      if (srgs.containsKey(var0)) {
         return (Map)srgs.get(var0);
      } else {
         HashMap var1 = new HashMap();
         srgs.put(var0, var1);
         File var2 = new File(var0);
         if (!var2.isFile()) {
            return var1;
         } else {
            try {
               Files.readLines(var2, Charsets.UTF_8, new LineProcessor(var1) {
                  final Map val$map;

                  {
                     this.val$map = var1;
                  }

                  public Object getResult() {
                     return null;
                  }

                  public boolean processLine(String var1) throws IOException {
                     if (!Strings.isNullOrEmpty(var1) && !var1.startsWith("#")) {
                        byte var2 = 0;
                        boolean var3 = false;
                        int var5;
                        if ((var5 = var1.startsWith("MD: ") ? 2 : (var1.startsWith("FD: ") ? 1 : 0)) > 0) {
                           String[] var4 = var1.substring(4).split(" ", 4);
                           this.val$map.put(var4[var2].substring(var4[var2].lastIndexOf(47) + 1), var4[var5].substring(var4[var5].lastIndexOf(47) + 1));
                        }

                        return true;
                     } else {
                        return true;
                     }
                  }
               });
            } catch (IOException var4) {
               logger.warn("Could not read input SRG file: {}", new Object[]{var0});
               logger.catching(var4);
            }

            return var1;
         }
      }
   }

   public static IReferenceMapper of(MixinEnvironment var0, IReferenceMapper var1) {
      return (IReferenceMapper)(!var1.isDefault() && hasData(var0) ? new RemappingReferenceMapper(var0, var1) : var1);
   }

   private static boolean hasData(MixinEnvironment var0) {
      String var1 = getResource(var0);
      return var1 != null && (new File(var1)).exists();
   }

   private static String getResource(MixinEnvironment var0) {
      String var1 = var0.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
      return Strings.isNullOrEmpty(var1) ? System.getProperty("net.minecraftforge.gradle.GradleStart.srg.srg-mcp") : var1;
   }

   private static String getMappingEnv(MixinEnvironment var0) {
      String var1 = var0.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
      return Strings.isNullOrEmpty(var1) ? "searge" : var1;
   }
}
