package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class StateMap extends StateMapperBase {
   private final List field_178140_d;
   private final IProperty field_178142_a;
   private final String field_178141_c;
   private static final String __OBFID = "CL_00002476";

   StateMap(IProperty var1, String var2, List var3, Object var4) {
      this(var1, var2, var3);
   }

   private StateMap(IProperty var1, String var2, List var3) {
      this.field_178142_a = var1;
      this.field_178141_c = var2;
      this.field_178140_d = var3;
   }

   protected ModelResourceLocation func_178132_a(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
      String var3;
      if (this.field_178142_a == null) {
         var3 = ((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock())).toString();
      } else {
         var3 = this.field_178142_a.getName((Comparable)var2.remove(this.field_178142_a));
      }

      if (this.field_178141_c != null) {
         var3 = String.valueOf((new StringBuilder(String.valueOf(var3))).append(this.field_178141_c));
      }

      Iterator var4 = this.field_178140_d.iterator();

      while(var4.hasNext()) {
         IProperty var5 = (IProperty)var4.next();
         var2.remove(var5);
      }

      return new ModelResourceLocation(var3, this.func_178131_a(var2));
   }

   public static class Builder {
      private final List field_178444_c = Lists.newArrayList();
      private IProperty field_178445_a;
      private static final String __OBFID = "CL_00002474";
      private String field_178443_b;

      public StateMap build() {
         return new StateMap(this.field_178445_a, this.field_178443_b, this.field_178444_c, (Object)null);
      }

      public StateMap.Builder func_178439_a(String var1) {
         this.field_178443_b = var1;
         return this;
      }

      public StateMap.Builder func_178440_a(IProperty var1) {
         this.field_178445_a = var1;
         return this;
      }

      public StateMap.Builder func_178442_a(IProperty... var1) {
         Collections.addAll(this.field_178444_c, var1);
         return this;
      }
   }
}
