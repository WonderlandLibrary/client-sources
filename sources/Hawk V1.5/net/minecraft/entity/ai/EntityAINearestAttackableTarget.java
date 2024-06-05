package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINearestAttackableTarget extends EntityAITarget {
   protected final Class targetClass;
   private final int targetChance;
   protected Predicate targetEntitySelector;
   protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
   private static final String __OBFID = "CL_00001620";
   protected EntityLivingBase targetEntity;

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, boolean var3) {
      this(var1, var2, var3, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, boolean var3, boolean var4) {
      this(var1, var2, 10, var3, var4, (Predicate)null);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, int var3, boolean var4, boolean var5, Predicate var6) {
      super(var1, var4, var5);
      this.targetClass = var2;
      this.targetChance = var3;
      this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(var1);
      this.setMutexBits(1);
      this.targetEntitySelector = new Predicate(this, var6) {
         final EntityAINearestAttackableTarget this$0;
         private static final String __OBFID = "CL_00001621";
         private final Predicate val$p_i45880_6_;

         {
            this.this$0 = var1;
            this.val$p_i45880_6_ = var2;
         }

         public boolean func_179878_a(EntityLivingBase var1) {
            if (this.val$p_i45880_6_ != null && !this.val$p_i45880_6_.apply(var1)) {
               return false;
            } else {
               if (var1 instanceof EntityPlayer) {
                  double var2 = this.this$0.getTargetDistance();
                  if (var1.isSneaking()) {
                     var2 *= 0.800000011920929D;
                  }

                  if (var1.isInvisible()) {
                     float var4 = ((EntityPlayer)var1).getArmorVisibility();
                     if (var4 < 0.1F) {
                        var4 = 0.1F;
                     }

                     var2 *= (double)(0.7F * var4);
                  }

                  if ((double)var1.getDistanceToEntity(this.this$0.taskOwner) > var2) {
                     return false;
                  }
               }

               return this.this$0.isSuitableTarget(var1, false);
            }
         }

         public boolean apply(Object var1) {
            return this.func_179878_a((EntityLivingBase)var1);
         }
      };
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.targetEntity);
      super.startExecuting();
   }

   public boolean shouldExecute() {
      if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         double var1 = this.getTargetDistance();
         List var3 = this.taskOwner.worldObj.func_175647_a(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(var1, 4.0D, var1), Predicates.and(this.targetEntitySelector, IEntitySelector.field_180132_d));
         Collections.sort(var3, this.theNearestAttackableTargetSorter);
         if (var3.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)var3.get(0);
            return true;
         }
      }
   }

   public static class Sorter implements Comparator {
      private static final String __OBFID = "CL_00001622";
      private final Entity theEntity;

      public Sorter(Entity var1) {
         this.theEntity = var1;
      }

      public int compare(Object var1, Object var2) {
         return this.compare((Entity)var1, (Entity)var2);
      }

      public int compare(Entity var1, Entity var2) {
         double var3 = this.theEntity.getDistanceSqToEntity(var1);
         double var5 = this.theEntity.getDistanceSqToEntity(var2);
         return var3 < var5 ? -1 : (var3 > var5 ? 1 : 0);
      }
   }
}
