package net.minecraft.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIHurtByTarget extends EntityAITarget {
   private int revengeTimerOld;
   private final Class[] field_179447_c;
   private static final String __OBFID = "CL_00001619";
   private boolean entityCallsForHelp;

   protected void func_179446_a(EntityCreature var1, EntityLivingBase var2) {
      var1.setAttackTarget(var2);
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
      this.revengeTimerOld = this.taskOwner.getRevengeTimer();
      if (this.entityCallsForHelp) {
         double var1 = this.getTargetDistance();
         List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).expand(var1, 10.0D, var1));
         Iterator var4 = var3.iterator();

         label54:
         while(true) {
            EntityCreature var5;
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        break label54;
                     }

                     var5 = (EntityCreature)var4.next();
                  } while(this.taskOwner == var5);
               } while(var5.getAttackTarget() != null);
            } while(var5.isOnSameTeam(this.taskOwner.getAITarget()));

            boolean var6 = false;
            Class[] var7 = this.field_179447_c;
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Class var10 = var7[var9];
               if (var5.getClass() == var10) {
                  var6 = true;
                  break;
               }
            }

            if (!var6) {
               this.func_179446_a(var5, this.taskOwner.getAITarget());
            }
         }
      }

      super.startExecuting();
   }

   public boolean shouldExecute() {
      int var1 = this.taskOwner.getRevengeTimer();
      return var1 != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
   }

   public EntityAIHurtByTarget(EntityCreature var1, boolean var2, Class... var3) {
      super(var1, false);
      this.entityCallsForHelp = var2;
      this.field_179447_c = var3;
      this.setMutexBits(1);
   }
}
