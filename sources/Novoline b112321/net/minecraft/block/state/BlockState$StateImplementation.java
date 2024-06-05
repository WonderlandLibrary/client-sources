package net.minecraft.block.state;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.aFI;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;

class BlockState$StateImplementation extends BlockStateBase {
   private final Block block;
   private final ImmutableMap properties;
   private ImmutableTable propertyValueTable;

   private BlockState$StateImplementation(Block var1, ImmutableMap var2) {
      this.block = var1;
      this.properties = var2;
   }

   public Collection getPropertyNames() {
      return Collections.unmodifiableCollection(this.properties.keySet());
   }

   public Comparable getValue(IProperty var1) {
      if(!this.properties.containsKey(var1)) {
         throw new IllegalArgumentException("Cannot get property " + var1 + " as it does not exist in " + this.block.getBlockState());
      } else {
         return (Comparable)var1.getValueClass().cast(this.properties.get(var1));
      }
   }

   public IBlockState withProperty(IProperty var1, Comparable var2) {
      if(!this.properties.containsKey(var1)) {
         throw new IllegalArgumentException("Cannot set property " + var1 + " as it does not exist in " + this.block.getBlockState());
      } else if(!var1.getAllowedValues().contains(var2)) {
         throw new IllegalArgumentException("Cannot set property " + var1 + " to " + var2 + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
      } else {
         return (IBlockState)(this.properties.get(var1) == var2?this:(IBlockState)this.propertyValueTable.get(var1, var2));
      }
   }

   public ImmutableMap getProperties() {
      return this.properties;
   }

   public Block getBlock() {
      return this.block;
   }

   public boolean equals(Object var1) {
      return this == var1;
   }

   public int hashCode() {
      return this.properties.hashCode();
   }

   public void buildPropertyValueTable(Map var1) {
      if(this.propertyValueTable != null) {
         throw new IllegalStateException();
      } else {
         HashBasedTable var2 = HashBasedTable.create();
         UnmodifiableIterator var3 = this.properties.keySet().iterator();

         while(var3.hasNext()) {
            IProperty var4 = (IProperty)var3.next();

            for(Comparable var6 : var4.getAllowedValues()) {
               if(var6 != this.properties.get(var4)) {
                  var2.put(var4, var6, var1.get(this.getPropertiesWithValue(var4, var6)));
               }
            }
         }

         this.propertyValueTable = ImmutableTable.copyOf(var2);
      }
   }

   private Map getPropertiesWithValue(IProperty var1, Comparable var2) {
      HashMap var3 = Maps.newHashMap(this.properties);
      var3.put(var1, var2);
      return var3;
   }

   BlockState$StateImplementation(Block var1, ImmutableMap var2, aFI var3) {
      this(var1, var2);
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
