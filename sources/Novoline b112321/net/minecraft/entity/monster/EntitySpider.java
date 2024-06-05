package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider$AISpiderAttack;
import net.minecraft.entity.monster.EntitySpider$AISpiderTarget;
import net.minecraft.entity.monster.EntitySpider$GroupData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob {
   public EntitySpider(World var1) {
      super(var1);
      this.setSize(1.4F, 0.9F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
      this.tasks.addTask(4, new EntitySpider$AISpiderAttack(this, EntityPlayer.class));
      this.tasks.addTask(4, new EntitySpider$AISpiderAttack(this, EntityIronGolem.class));
      this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(2, new EntitySpider$AISpiderTarget(this, EntityPlayer.class));
      this.targetTasks.addTask(3, new EntitySpider$AISpiderTarget(this, EntityIronGolem.class));
   }

   public double getMountedYOffset() {
      return (double)(this.height * 0.5F);
   }

   protected PathNavigate getNewNavigator(World var1) {
      return new PathNavigateClimber(this, var1);
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
   }

   public void onUpdate() {
      super.onUpdate();
      if(!this.worldObj.isRemote) {
         this.setBesideClimbableBlock(this.isCollidedHorizontally);
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
   }

   protected String getLivingSound() {
      return "mob.spider.say";
   }

   protected String getHurtSound() {
      return "mob.spider.say";
   }

   protected String getDeathSound() {
      return "mob.spider.death";
   }

   protected void playStepSound(BlockPos var1, Block var2) {
      this.playSound("mob.spider.step", 0.15F, 1.0F);
   }

   protected Item getDropItem() {
      return Items.string;
   }

   protected void dropFewItems(boolean var1, int var2) {
      super.dropFewItems(var1, var2);
      if(this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + var2) > 0) {
         this.dropItem(Items.spider_eye, 1);
      }

   }

   public boolean isOnLadder() {
      return this.isBesideClimbableBlock();
   }

   public void setInWeb() {
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public boolean isPotionApplicable(PotionEffect var1) {
      return var1.getPotionID() != Potion.poison.getId() && super.isPotionApplicable(var1);
   }

   public boolean isBesideClimbableBlock() {
      return (this.I.g(16) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean var1) {
      byte var2 = this.I.g(16);
      var2 = (byte)(var2 | 1);
      this.I.a(16, Byte.valueOf(var2));
   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.onInitialSpawn(var1, var2);
      if(this.worldObj.rand.nextInt(100) == 0) {
         EntitySkeleton var3 = new EntitySkeleton(this.worldObj);
         var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
         var3.onInitialSpawn(var1, (IEntityLivingData)null);
         this.worldObj.spawnEntityInWorld(var3);
         var3.mountEntity(this);
      }

      EntitySpider$GroupData var5 = new EntitySpider$GroupData();
      if(this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1F * var1.getClampedAdditionalDifficulty()) {
         ((EntitySpider$GroupData)var5).func_111104_a(this.worldObj.rand);
      }

      if(var5 instanceof EntitySpider$GroupData) {
         int var6 = ((EntitySpider$GroupData)var5).potionEffectId;
         if(Potion.potionTypes[var6] != null) {
            this.addPotionEffect(new PotionEffect(var6, Integer.MAX_VALUE));
         }
      }

      return var5;
   }

   public float getEyeHeight() {
      return 0.65F;
   }
}
