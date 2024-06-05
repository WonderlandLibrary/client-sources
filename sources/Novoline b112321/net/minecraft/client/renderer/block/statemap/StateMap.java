package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap$1;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class StateMap extends StateMapperBase {
   private final IProperty name;
   private final String suffix;
   private final List ignored;

   private StateMap(IProperty var1, String var2, List var3) {
      this.name = var1;
      this.suffix = var2;
      this.ignored = var3;
   }

   protected ModelResourceLocation getModelResourceLocation(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
      String var3;
      if(this.name == null) {
         var3 = ((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock())).toString();
      } else {
         var3 = this.name.getName((Comparable)var2.remove(this.name));
      }

      if(this.suffix != null) {
         var3 = var3 + this.suffix;
      }

      for(IProperty var5 : this.ignored) {
         var2.remove(var5);
      }

      return new ModelResourceLocation(var3, this.getPropertyString(var2));
   }

   StateMap(IProperty var1, String var2, List var3, StateMap$1 var4) {
      this(var1, var2, var3);
   }
}
