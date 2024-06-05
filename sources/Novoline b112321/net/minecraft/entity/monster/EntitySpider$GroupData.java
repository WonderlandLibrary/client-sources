package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.potion.Potion;

public class EntitySpider$GroupData implements IEntityLivingData {
   public int potionEffectId;

   public void func_111104_a(Random var1) {
      int var2 = var1.nextInt(5);
      if(var2 <= 1) {
         this.potionEffectId = Potion.moveSpeed.getId();
      } else if(var2 <= 2) {
         this.potionEffectId = Potion.damageBoost.getId();
      } else if(var2 <= 3) {
         this.potionEffectId = Potion.regeneration.getId();
      } else if(var2 <= 4) {
         this.potionEffectId = Potion.invisibility.getId();
      }

   }
}
