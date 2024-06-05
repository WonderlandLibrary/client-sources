package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.util.Tuple;

class EntityVillager$PriceInfo extends Tuple {
   public EntityVillager$PriceInfo(int var1, int var2) {
      super(Integer.valueOf(var1), Integer.valueOf(var2));
   }

   public int getPrice(Random var1) {
      return ((Integer)this.getFirst()).intValue() >= ((Integer)this.getSecond()).intValue()?((Integer)this.getFirst()).intValue():((Integer)this.getFirst()).intValue() + var1.nextInt(((Integer)this.getSecond()).intValue() - ((Integer)this.getFirst()).intValue() + 1);
   }
}
