package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.Counter;

public class MethodMapper {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final List classes = new ArrayList();
   private static final Map methods = new HashMap();
   private final ClassInfo info;

   public MethodMapper(MixinEnvironment var1, ClassInfo var2) {
      this.info = var2;
   }

   public ClassInfo getClassInfo() {
      return this.info;
   }

   public void remapHandlerMethod(MixinInfo var1, MethodNode var2, ClassInfo.Method var3) {
      if (var2 instanceof MixinInfo.MixinMethodNode && ((MixinInfo.MixinMethodNode)var2).isInjector()) {
         if (var3.isUnique()) {
            logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[]{var3, var1});
         }

         if (var3.isRenamed()) {
            var2.name = var3.getName();
         } else {
            String var4 = this.getHandlerName((MixinInfo.MixinMethodNode)var2);
            var2.name = var3.renameTo(var4);
         }
      }
   }

   public String getHandlerName(MixinInfo.MixinMethodNode var1) {
      String var2 = InjectionInfo.getInjectorPrefix(var1.getInjectorAnnotation());
      String var3 = getClassUID(var1.getOwner().getClassRef());
      String var4 = getMethodUID(var1.name, var1.desc, !var1.isSurrogate());
      return String.format("%s$%s$%s%s", var2, var1.name, var3, var4);
   }

   private static String getClassUID(String var0) {
      int var1 = classes.indexOf(var0);
      if (var1 < 0) {
         var1 = classes.size();
         classes.add(var0);
      }

      return finagle(var1);
   }

   private static String getMethodUID(String var0, String var1, boolean var2) {
      String var3 = String.format("%s%s", var0, var1);
      Counter var4 = (Counter)methods.get(var3);
      if (var4 == null) {
         var4 = new Counter();
         methods.put(var3, var4);
      } else if (var2) {
         ++var4.value;
      }

      return String.format("%03x", var4.value);
   }

   private static String finagle(int var0) {
      String var1 = Integer.toHexString(var0);
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         char var4 = var1.charAt(var3);
         var2.append(var4 = (char)(var4 + (var4 < ':' ? 49 : 10)));
      }

      return Strings.padStart(var2.toString(), 3, 'z');
   }
}
