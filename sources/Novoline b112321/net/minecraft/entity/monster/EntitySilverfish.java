package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySilverfish$AIHideInStone;
import net.minecraft.entity.monster.EntitySilverfish$AISummonSilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntitySilverfish extends EntityMob {
   private EntitySilverfish$AISummonSilverfish summonSilverfish;

   public EntitySilverfish(World var1) {
      super(var1);
      this.setSize(0.4F, 0.3F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(3, this.summonSilverfish = new EntitySilverfish$AISummonSilverfish(this));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
      this.tasks.addTask(5, new EntitySilverfish$AIHideInStone(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   public double getYOffset() {
      return 0.2D;
   }

   public float getEyeHeight() {
      return 0.1F;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected String getLivingSound() {
      return "mob.silverfish.say";
   }

   protected String getHurtSound() {
      return "mob.silverfish.hit";
   }

   protected String getDeathSound() {
      return "mob.silverfish.kill";
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else {
         if(var1 instanceof EntityDamageSource || var1 == DamageSource.magic) {
            this.summonSilverfish.func_179462_f();
         }

         return super.attackEntityFrom(var1, var2);
      }
   }

   protected void playStepSound(BlockPos var1, Block var2) {
      this.playSound("mob.silverfish.step", 0.15F, 1.0F);
   }

   protected Item getDropItem() {
      return null;
   }

   public void onUpdate() {
      this.renderYawOffset = this.rotationYaw;
      super.onUpdate();
   }

   public float getBlockPathWeight(BlockPos var1) {
      return this.worldObj.getBlockState(var1.down()).getBlock() == Blocks.stone?10.0F:super.getBlockPathWeight(var1);
   }

   protected boolean isValidLightLevel() {
      return true;
   }

   public boolean getCanSpawnHere() {
      if(super.getCanSpawnHere()) {
         EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 5.0D);
         return true;
      } else {
         return false;
      }
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.ARTHROPOD;
   }
}
