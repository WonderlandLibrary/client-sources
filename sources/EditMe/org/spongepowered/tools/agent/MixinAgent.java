package org.spongepowered.tools.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public class MixinAgent implements IHotSwap {
   public static final byte[] ERROR_BYTECODE = new byte[]{1};
   static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
   static final Logger logger = LogManager.getLogger("mixin.agent");
   static Instrumentation instrumentation = null;
   private static List agents = new ArrayList();
   final MixinTransformer classTransformer;

   public MixinAgent(MixinTransformer var1) {
      this.classTransformer = var1;
      agents.add(this);
      if (instrumentation != null) {
         this.initTransformer();
      }

   }

   private void initTransformer() {
      instrumentation.addTransformer(new MixinAgent.Transformer(this), true);
   }

   public void registerMixinClass(String var1) {
      classLoader.addMixinClass(var1);
   }

   public void registerTargetClass(String var1, byte[] var2) {
      classLoader.addTargetClass(var1, var2);
   }

   public static void init(Instrumentation var0) {
      instrumentation = var0;
      if (!instrumentation.isRedefineClassesSupported()) {
         logger.error("The instrumentation doesn't support re-definition of classes");
      }

      Iterator var1 = agents.iterator();

      while(var1.hasNext()) {
         MixinAgent var2 = (MixinAgent)var1.next();
         var2.initTransformer();
      }

   }

   public static void premain(String var0, Instrumentation var1) {
      System.setProperty("mixin.hotSwap", "true");
      init(var1);
   }

   public static void agentmain(String var0, Instrumentation var1) {
      init(var1);
   }

   class Transformer implements ClassFileTransformer {
      final MixinAgent this$0;

      Transformer(MixinAgent var1) {
         this.this$0 = var1;
      }

      public byte[] transform(ClassLoader var1, String var2, Class var3, ProtectionDomain var4, byte[] var5) throws IllegalClassFormatException {
         if (var3 == null) {
            return null;
         } else {
            byte[] var6 = MixinAgent.classLoader.getFakeMixinBytecode(var3);
            if (var6 != null) {
               List var7 = this.reloadMixin(var2, var5);
               return var7 != null && this.reApplyMixins(var7) ? var6 : MixinAgent.ERROR_BYTECODE;
            } else {
               try {
                  MixinAgent.logger.info("Redefining class " + var2);
                  return this.this$0.classTransformer.transformClassBytes((String)null, var2, var5);
               } catch (Throwable var8) {
                  MixinAgent.logger.error("Error while re-transforming class " + var2, var8);
                  return MixinAgent.ERROR_BYTECODE;
               }
            }
         }
      }

      private List reloadMixin(String var1, byte[] var2) {
         MixinAgent.logger.info("Redefining mixin {}", new Object[]{var1});

         try {
            return this.this$0.classTransformer.reload(var1.replace('/', '.'), var2);
         } catch (MixinReloadException var4) {
            MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[]{var4.getMixinInfo(), var4.getMessage()});
         } catch (Throwable var5) {
            MixinAgent.logger.error("Error while finding targets for mixin " + var1, var5);
         }

         return null;
      }

      private boolean reApplyMixins(List var1) {
         IMixinService var2 = MixinService.getService();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            String var5 = var4.replace('/', '.');
            MixinAgent.logger.debug("Re-transforming target class {}", new Object[]{var4});

            try {
               Class var6 = var2.getClassProvider().findClass(var5);
               byte[] var7 = MixinAgent.classLoader.getOriginalTargetBytecode(var5);
               if (var7 == null) {
                  MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[]{var5});
                  return false;
               }

               var7 = this.this$0.classTransformer.transformClassBytes((String)null, var5, var7);
               MixinAgent.instrumentation.redefineClasses(new ClassDefinition[]{new ClassDefinition(var6, var7)});
            } catch (Throwable var8) {
               MixinAgent.logger.error("Error while re-transforming target class " + var4, var8);
               return false;
            }
         }

         return true;
      }
   }
}
