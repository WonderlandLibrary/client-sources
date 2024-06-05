package net.minecraft.client.resources.model;

import com.google.common.collect.ComparisonChain;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom$Item;

class WeightedBakedModel$MyWeighedRandomItem extends WeightedRandom$Item implements Comparable {
   protected final IBakedModel model;

   public WeightedBakedModel$MyWeighedRandomItem(IBakedModel var1, int var2) {
      super(var2);
      this.model = var1;
   }

   public int compareTo(WeightedBakedModel$MyWeighedRandomItem var1) {
      return ComparisonChain.start().compare(var1.itemWeight, this.itemWeight).compare(this.getCountQuads(), var1.getCountQuads()).result();
   }

   protected int getCountQuads() {
      int var1 = this.model.getGeneralQuads().size();

      for(EnumFacing var5 : EnumFacing.values()) {
         var1 += this.model.getFaceQuads(var5).size();
      }

      return var1;
   }

   public String toString() {
      return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
   }
}
