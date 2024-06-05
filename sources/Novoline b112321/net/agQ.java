package net;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMap$Builder;

public class agQ {
   public static StateMap$Builder a(StateMap$Builder var0, IProperty var1) {
      return var0.withName(var1);
   }

   public static StateMap a(StateMap$Builder var0) {
      return var0.build();
   }

   public static StateMap$Builder a(StateMap$Builder var0, String var1) {
      return var0.withSuffix(var1);
   }

   public static StateMap$Builder a(StateMap$Builder var0, IProperty[] var1) {
      return var0.ignore(var1);
   }
}
