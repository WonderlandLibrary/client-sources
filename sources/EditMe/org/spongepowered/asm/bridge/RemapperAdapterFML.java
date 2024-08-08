package org.spongepowered.asm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public final class RemapperAdapterFML extends RemapperAdapter {
   private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String INSTANCE_FIELD = "INSTANCE";
   private static final String UNMAP_METHOD = "unmap";
   private final Method mdUnmap;

   private RemapperAdapterFML(Remapper var1, Method var2) {
      super(var1);
      this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[]{var1});
      this.mdUnmap = var2;
   }

   public String unmap(String var1) {
      try {
         return this.mdUnmap.invoke(this.remapper, var1).toString();
      } catch (Exception var3) {
         return var1;
      }
   }

   public static IRemapper create() {
      try {
         Class var0 = getFMLDeobfuscatingRemapper();
         Field var1 = var0.getDeclaredField("INSTANCE");
         Method var2 = var0.getDeclaredMethod("unmap", String.class);
         Remapper var3 = (Remapper)var1.get((Object)null);
         return new RemapperAdapterFML(var3, var2);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static Class getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
      try {
         return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
      } catch (ClassNotFoundException var1) {
         return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
      }
   }
}
