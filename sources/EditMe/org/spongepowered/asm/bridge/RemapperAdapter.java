package org.spongepowered.asm.bridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import org.spongepowered.asm.util.ObfuscationUtil;

public abstract class RemapperAdapter implements IRemapper, ObfuscationUtil.IClassRemapper {
   protected final Logger logger = LogManager.getLogger("mixin");
   protected final Remapper remapper;

   public RemapperAdapter(Remapper var1) {
      this.remapper = var1;
   }

   public String toString() {
      return this.getClass().getSimpleName();
   }

   public String mapMethodName(String var1, String var2, String var3) {
      this.logger.debug("{} is remapping method {}{} for {}", new Object[]{this, var2, var3, var1});
      String var4 = this.remapper.mapMethodName(var1, var2, var3);
      if (!var4.equals(var2)) {
         return var4;
      } else {
         String var5 = this.unmap(var1);
         String var6 = this.unmapDesc(var3);
         this.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[]{this, var2, var6, var5});
         return this.remapper.mapMethodName(var5, var2, var6);
      }
   }

   public String mapFieldName(String var1, String var2, String var3) {
      this.logger.debug("{} is remapping field {}{} for {}", new Object[]{this, var2, var3, var1});
      String var4 = this.remapper.mapFieldName(var1, var2, var3);
      if (!var4.equals(var2)) {
         return var4;
      } else {
         String var5 = this.unmap(var1);
         String var6 = this.unmapDesc(var3);
         this.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[]{this, var2, var6, var5});
         return this.remapper.mapFieldName(var5, var2, var6);
      }
   }

   public String map(String var1) {
      this.logger.debug("{} is remapping class {}", new Object[]{this, var1});
      return this.remapper.map(var1);
   }

   public String unmap(String var1) {
      return var1;
   }

   public String mapDesc(String var1) {
      return this.remapper.mapDesc(var1);
   }

   public String unmapDesc(String var1) {
      return ObfuscationUtil.unmapDescriptor(var1, this);
   }
}
