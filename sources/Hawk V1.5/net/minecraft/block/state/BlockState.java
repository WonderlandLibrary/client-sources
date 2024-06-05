package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
   private final ImmutableList properties;
   private static final Joiner COMMA_JOINER = Joiner.on(", ");
   private final Block block;
   private final ImmutableList validStates;
   private static final String __OBFID = "CL_00002030";
   private static final Function GET_NAME_FUNC = new Function() {
      private static final String __OBFID = "CL_00002029";

      public String apply(IProperty var1) {
         return var1 == null ? "<NULL>" : var1.getName();
      }

      public Object apply(Object var1) {
         return this.apply((IProperty)var1);
      }
   };

   public Collection getProperties() {
      return this.properties;
   }

   public ImmutableList getValidStates() {
      return this.validStates;
   }

   public IBlockState getBaseState() {
      return (IBlockState)this.validStates.get(0);
   }

   private List getAllowedValues() {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 < this.properties.size(); ++var2) {
         var1.add(((IProperty)this.properties.get(var2)).getAllowedValues());
      }

      return var1;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties, GET_NAME_FUNC)).toString();
   }

   public BlockState(Block var1, IProperty... var2) {
      this.block = var1;
      Arrays.sort(var2, new Comparator(this) {
         final BlockState this$0;
         private static final String __OBFID = "CL_00002028";

         public int compare(IProperty var1, IProperty var2) {
            return var1.getName().compareTo(var2.getName());
         }

         public int compare(Object var1, Object var2) {
            return this.compare((IProperty)var1, (IProperty)var2);
         }

         {
            this.this$0 = var1;
         }
      });
      this.properties = ImmutableList.copyOf(var2);
      LinkedHashMap var3 = Maps.newLinkedHashMap();
      ArrayList var4 = Lists.newArrayList();
      Iterable var5 = Cartesian.cartesianProduct(this.getAllowedValues());
      Iterator var6 = var5.iterator();

      while(var6.hasNext()) {
         List var7 = (List)var6.next();
         Map var8 = MapPopulator.createMap(this.properties, var7);
         BlockState.StateImplemenation var9 = new BlockState.StateImplemenation(var1, ImmutableMap.copyOf(var8), (Object)null);
         var3.put(var8, var9);
         var4.add(var9);
      }

      var6 = var4.iterator();

      while(var6.hasNext()) {
         BlockState.StateImplemenation var10 = (BlockState.StateImplemenation)var6.next();
         var10.buildPropertyValueTable(var3);
      }

      this.validStates = ImmutableList.copyOf(var4);
   }

   public Block getBlock() {
      return this.block;
   }

   static class StateImplemenation extends BlockStateBase {
      private static final String __OBFID = "CL_00002027";
      private ImmutableTable propertyValueTable;
      private final ImmutableMap properties;
      private final Block block;

      StateImplemenation(Block var1, ImmutableMap var2, Object var3) {
         this(var1, var2);
      }

      public void buildPropertyValueTable(Map var1) {
         if (this.propertyValueTable != null) {
            throw new IllegalStateException();
         } else {
            HashBasedTable var2 = HashBasedTable.create();
            UnmodifiableIterator var3 = this.properties.keySet().iterator();

            while(var3.hasNext()) {
               IProperty var4 = (IProperty)var3.next();
               Iterator var5 = var4.getAllowedValues().iterator();

               while(var5.hasNext()) {
                  Comparable var6 = (Comparable)var5.next();
                  if (var6 != this.properties.get(var4)) {
                     var2.put(var4, var6, var1.get(this.setPropertyValue(var4, var6)));
                  }
               }
            }

            this.propertyValueTable = ImmutableTable.copyOf(var2);
         }
      }

      private StateImplemenation(Block var1, ImmutableMap var2) {
         this.block = var1;
         this.properties = var2;
      }

      public boolean equals(Object var1) {
         return this == var1;
      }

      public Comparable getValue(IProperty var1) {
         if (!this.properties.containsKey(var1)) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Cannot get property ")).append(var1).append(" as it does not exist in ").append(this.block.getBlockState())));
         } else {
            return (Comparable)var1.getValueClass().cast(this.properties.get(var1));
         }
      }

      private Map setPropertyValue(IProperty var1, Comparable var2) {
         HashMap var3 = Maps.newHashMap(this.properties);
         var3.put(var1, var2);
         return var3;
      }

      public ImmutableMap getProperties() {
         return this.properties;
      }

      public IBlockState withProperty(IProperty var1, Comparable var2) {
         if (!this.properties.containsKey(var1)) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Cannot set property ")).append(var1).append(" as it does not exist in ").append(this.block.getBlockState())));
         } else if (!var1.getAllowedValues().contains(var2)) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Cannot set property ")).append(var1).append(" to ").append(var2).append(" on block ").append(Block.blockRegistry.getNameForObject(this.block)).append(", it is not an allowed value")));
         } else {
            return (IBlockState)(this.properties.get(var1) == var2 ? this : (IBlockState)this.propertyValueTable.get(var1, var2));
         }
      }

      public Block getBlock() {
         return this.block;
      }

      public Collection getPropertyNames() {
         return Collections.unmodifiableCollection(this.properties.keySet());
      }

      public int hashCode() {
         return this.properties.hashCode();
      }
   }
}
