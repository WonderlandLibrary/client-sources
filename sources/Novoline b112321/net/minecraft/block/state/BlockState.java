package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.aFI;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState$2;
import net.minecraft.block.state.BlockState$StateImplementation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
   private static final Joiner COMMA_JOINER = Joiner.on(", ");
   private static final Function GET_NAME_FUNC = new aFI();
   private final Block block;
   private final ImmutableList properties;
   private final ImmutableList validStates;

   public BlockState(Block var1, IProperty... var2) {
      this.block = var1;
      Arrays.sort(var2, new BlockState$2(this));
      this.properties = ImmutableList.copyOf(var2);
      LinkedHashMap var3 = Maps.newLinkedHashMap();
      ArrayList var4 = Lists.newArrayList();

      for(List var6 : Cartesian.cartesianProduct(this.getAllowedValues())) {
         Map var7 = MapPopulator.createMap(this.properties, var6);
         BlockState$StateImplementation var8 = new BlockState$StateImplementation(var1, ImmutableMap.copyOf(var7), (aFI)null);
         var3.put(var7, var8);
         var4.add(var8);
      }

      for(BlockState$StateImplementation var10 : var4) {
         var10.buildPropertyValueTable(var3);
      }

      this.validStates = ImmutableList.copyOf(var4);
   }

   public ImmutableList getValidStates() {
      return this.validStates;
   }

   private List getAllowedValues() {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 < this.properties.size(); ++var2) {
         var1.add(((IProperty)this.properties.get(var2)).getAllowedValues());
      }

      return var1;
   }

   public IBlockState getBaseState() {
      return (IBlockState)this.validStates.get(0);
   }

   public Block getBlock() {
      return this.block;
   }

   public Collection getProperties() {
      return this.properties;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties, GET_NAME_FUNC)).toString();
   }
}
