package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import net.z9;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public abstract class BlockStateBase implements IBlockState {
   private static final Joiner COMMA_JOINER = Joiner.on(',');
   private static final Function MAP_ENTRY_TO_STRING = new z9();
   private static final String d = "CL_00002032";
   private int blockId = -1;
   private int blockStateId = -1;
   private int metadata = -1;
   private ResourceLocation blockLocation = null;

   public int getBlockId() {
      if(this.blockId < 0) {
         this.blockId = Block.getIdFromBlock(this.getBlock());
      }

      return this.blockId;
   }

   public int getBlockStateId() {
      if(this.blockStateId < 0) {
         this.blockStateId = Block.getStateId(this);
      }

      return this.blockStateId;
   }

   public int getMetadata() {
      if(this.metadata < 0) {
         this.metadata = this.getBlock().getMetaFromState(this);
      }

      return this.metadata;
   }

   public ResourceLocation getBlockLocation() {
      if(this.blockLocation == null) {
         this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.getBlock());
      }

      return this.blockLocation;
   }

   public IBlockState cycleProperty(IProperty var1) {
      return this.withProperty(var1, (Comparable)cyclePropertyValue(var1.getAllowedValues(), this.getValue(var1)));
   }

   protected static Object cyclePropertyValue(Collection var0, Object var1) {
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         if(var2.next().equals(var1)) {
            if(var2.hasNext()) {
               return var2.next();
            }

            return var0.iterator().next();
         }
      }

      return var2.next();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(Block.blockRegistry.getNameForObject(this.getBlock()));
      if(!this.getProperties().isEmpty()) {
         var1.append("[");
         COMMA_JOINER.appendTo(var1, Iterables.transform(this.getProperties().entrySet(), MAP_ENTRY_TO_STRING));
         var1.append("]");
      }

      return var1.toString();
   }

   public ImmutableTable getPropertyValueTable() {
      return null;
   }
}
