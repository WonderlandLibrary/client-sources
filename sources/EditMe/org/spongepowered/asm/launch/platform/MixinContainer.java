package org.spongepowered.asm.launch.platform;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.service.MixinService;

public class MixinContainer {
   private static final List agentClasses = new ArrayList();
   private final Logger logger = LogManager.getLogger("mixin");
   private final URI uri;
   private final List agents = new ArrayList();

   public MixinContainer(MixinPlatformManager var1, URI var2) {
      this.uri = var2;
      Iterator var3 = agentClasses.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();

         try {
            Class var5 = Class.forName(var4);
            Constructor var6 = var5.getDeclaredConstructor(MixinPlatformManager.class, URI.class);
            this.logger.debug("Instancing new {} for {}", new Object[]{var5.getSimpleName(), this.uri});
            IMixinPlatformAgent var7 = (IMixinPlatformAgent)var6.newInstance(var1, var2);
            this.agents.add(var7);
         } catch (Exception var8) {
            this.logger.catching(var8);
         }
      }

   }

   public URI getURI() {
      return this.uri;
   }

   public Collection getPhaseProviders() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.agents.iterator();

      while(var2.hasNext()) {
         IMixinPlatformAgent var3 = (IMixinPlatformAgent)var2.next();
         String var4 = var3.getPhaseProvider();
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public void prepare() {
      Iterator var1 = this.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent var2 = (IMixinPlatformAgent)var1.next();
         this.logger.debug("Processing prepare() for {}", new Object[]{var2});
         var2.prepare();
      }

   }

   public void initPrimaryContainer() {
      Iterator var1 = this.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent var2 = (IMixinPlatformAgent)var1.next();
         this.logger.debug("Processing launch tasks for {}", new Object[]{var2});
         var2.initPrimaryContainer();
      }

   }

   public void inject() {
      Iterator var1 = this.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent var2 = (IMixinPlatformAgent)var1.next();
         this.logger.debug("Processing inject() for {}", new Object[]{var2});
         var2.inject();
      }

   }

   public String getLaunchTarget() {
      Iterator var1 = this.agents.iterator();

      String var3;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         IMixinPlatformAgent var2 = (IMixinPlatformAgent)var1.next();
         var3 = var2.getLaunchTarget();
      } while(var3 == null);

      return var3;
   }

   static {
      GlobalProperties.put("mixin.agents", agentClasses);
      Iterator var0 = MixinService.getService().getPlatformAgents().iterator();

      while(var0.hasNext()) {
         String var1 = (String)var0.next();
         agentClasses.add(var1);
      }

      agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
   }
}
