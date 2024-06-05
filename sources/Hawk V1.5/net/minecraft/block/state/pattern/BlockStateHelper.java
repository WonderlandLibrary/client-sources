package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

public class BlockStateHelper implements Predicate {
   private static final String __OBFID = "CL_00002019";
   private final Map field_177640_b = Maps.newHashMap();
   private final BlockState field_177641_a;

   public boolean apply(Object var1) {
      return this.func_177639_a((IBlockState)var1);
   }

   public static BlockStateHelper forBlock(Block var0) {
      return new BlockStateHelper(var0.getBlockState());
   }

   public boolean func_177639_a(IBlockState var1) {
      if (var1 != null && var1.getBlock().equals(this.field_177641_a.getBlock())) {
         Iterator var2 = this.field_177640_b.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Comparable var4 = var1.getValue((IProperty)var3.getKey());
            if (!((Predicate)var3.getValue()).apply(var4)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public BlockStateHelper func_177637_a(IProperty var1, Predicate var2) {
      if (!this.field_177641_a.getProperties().contains(var1)) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append(this.field_177641_a).append(" cannot support property ").append(var1)));
      } else {
         this.field_177640_b.put(var1, var2);
         return this;
      }
   }

   private BlockStateHelper(BlockState var1) {
      this.field_177641_a = var1;
   }
}
