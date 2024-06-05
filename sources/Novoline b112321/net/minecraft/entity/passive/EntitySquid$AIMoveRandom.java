package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.MathHelper;

class EntitySquid$AIMoveRandom extends EntityAIBase {
   private EntitySquid squid;

   public EntitySquid$AIMoveRandom(EntitySquid var1) {
      this.squid = var1;
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      int var1 = this.squid.getAge();
      if(var1 > 100) {
         this.squid.func_175568_b(0.0F, 0.0F, 0.0F);
      } else if(this.squid.getRNG().nextInt(50) == 0 || !EntitySquid.access$000(this.squid) || !this.squid.func_175567_n()) {
         float var2 = this.squid.getRNG().nextFloat() * 3.1415927F * 2.0F;
         float var3 = MathHelper.cos(var2) * 0.2F;
         float var4 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
         float var5 = MathHelper.sin(var2) * 0.2F;
         this.squid.func_175568_b(var3, var4, var5);
      }

   }
}
