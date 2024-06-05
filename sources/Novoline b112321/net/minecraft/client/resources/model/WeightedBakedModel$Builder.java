package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel$MyWeighedRandomItem;

public class WeightedBakedModel$Builder {
   private List listItems = Lists.newArrayList();

   public WeightedBakedModel$Builder add(IBakedModel var1, int var2) {
      this.listItems.add(new WeightedBakedModel$MyWeighedRandomItem(var1, var2));
      return this;
   }

   public WeightedBakedModel build() {
      Collections.sort(this.listItems);
      return new WeightedBakedModel(this.listItems);
   }

   public IBakedModel first() {
      return ((WeightedBakedModel$MyWeighedRandomItem)this.listItems.get(0)).model;
   }
}
