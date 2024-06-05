package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal {
   private int field_175538_bq = 0;
   private boolean field_175537_bp = false;
   private int field_175535_bn = 0;
   private EntityRabbit.AIAvoidEntity field_175539_bk;
   private EntityPlayer field_175543_bt;
   private static final String __OBFID = "CL_00002242";
   private int field_175541_bs;
   private EntityRabbit.EnumMoveType field_175542_br;
   private boolean field_175536_bo = false;
   private int field_175540_bm = 0;

   protected float func_175134_bD() {
      return this.moveHelper.isUpdating() && this.moveHelper.func_179919_e() > this.posY + 0.5D ? 0.5F : this.field_175542_br.func_180074_b();
   }

   private void func_175530_ct() {
      this.field_175538_bq = this.func_175532_cm();
   }

   public boolean func_175523_cj() {
      return this.field_175536_bo;
   }

   public void func_174830_Y() {
   }

   public void func_175519_a(boolean var1, EntityRabbit.EnumMoveType var2) {
      super.setJumping(var1);
      if (!var1) {
         if (this.field_175542_br == EntityRabbit.EnumMoveType.ATTACK) {
            this.field_175542_br = EntityRabbit.EnumMoveType.HOP;
         }
      } else {
         this.func_175515_b(1.5D * (double)var2.func_180072_a());
         this.playSound(this.func_175516_ck(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }

      this.field_175536_bo = var1;
   }

   public void func_175515_b(double var1) {
      this.getNavigator().setSpeed(var1);
      this.moveHelper.setMoveTo(this.moveHelper.func_179917_d(), this.moveHelper.func_179919_e(), this.moveHelper.func_179918_f(), var1);
   }

   protected void addRandomArmor() {
      this.entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
   }

   private void func_175533_a(double var1, double var3) {
      this.rotationYaw = (float)(Math.atan2(var3 - this.posZ, var1 - this.posX) * 180.0D / 3.141592653589793D) - 90.0F;
   }

   public int getTotalArmorValue() {
      return this.func_175531_cl() == 99 ? 8 : super.getTotalArmorValue();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return this.func_180431_b(var1) ? false : super.attackEntityFrom(var1, var2);
   }

   public EntityRabbit(World var1) {
      super(var1);
      this.field_175542_br = EntityRabbit.EnumMoveType.HOP;
      this.field_175541_bs = 0;
      this.field_175543_bt = null;
      this.setSize(0.6F, 0.7F);
      this.jumpHelper = new EntityRabbit.RabbitJumpHelper(this, this);
      this.moveHelper = new EntityRabbit.RabbitMoveHelper(this);
      ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
      this.navigator.func_179678_a(2.5F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityRabbit.AIPanic(this, 1.33D));
      this.tasks.addTask(2, new EntityAITempt(this, 1.0D, Items.carrot, false));
      this.tasks.addTask(3, new EntityAIMate(this, 0.8D));
      this.tasks.addTask(5, new EntityRabbit.AIRaidFarm(this));
      this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
      this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.field_175539_bk = new EntityRabbit.AIAvoidEntity(this, new Predicate(this) {
         final EntityRabbit this$0;
         private static final String __OBFID = "CL_00002241";

         public boolean apply(Object var1) {
            return this.func_180086_a((Entity)var1);
         }

         {
            this.this$0 = var1;
         }

         public boolean func_180086_a(Entity var1) {
            return var1 instanceof EntityWolf;
         }
      }, 16.0F, 1.33D, 1.33D);
      this.tasks.addTask(4, this.field_175539_bk);
      this.func_175515_b(0.0D);
   }

   protected String func_175516_ck() {
      return "mob.rabbit.hop";
   }

   protected void func_175528_cn() {
      this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.carrots.getStateFromMeta(7)));
      this.field_175541_bs = 100;
   }

   private boolean func_175534_cv() {
      return this.field_175541_bs == 0;
   }

   protected String getDeathSound() {
      return "mob.rabbit.death";
   }

   public void func_175529_r(int var1) {
      if (var1 == 99) {
         this.tasks.removeTask(this.field_175539_bk);
         this.tasks.addTask(4, new EntityRabbit.AIEvilAttack(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
         if (!this.hasCustomName()) {
            this.setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
         }
      }

      this.dataWatcher.updateObject(18, (byte)var1);
   }

   public void func_175522_a(EntityRabbit.EnumMoveType var1) {
      this.field_175542_br = var1;
   }

   private void func_175517_cu() {
      this.func_175530_ct();
      this.func_175520_cs();
   }

   public boolean attackEntityAsMob(Entity var1) {
      if (this.func_175531_cl() == 99) {
         this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         return var1.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
      } else {
         return var1.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
      }
   }

   public boolean isBreedingItem(ItemStack var1) {
      return var1 != null && this.func_175525_a(var1.getItem());
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("RabbitType", this.func_175531_cl());
      var1.setInteger("MoreCarrotTicks", this.field_175541_bs);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, (byte)0);
   }

   protected int func_175532_cm() {
      return this.field_175542_br.func_180075_c();
   }

   public IEntityLivingData func_180482_a(DifficultyInstance var1, IEntityLivingData var2) {
      Object var3 = super.func_180482_a(var1, var2);
      int var4 = this.rand.nextInt(6);
      boolean var5 = false;
      if (var3 instanceof EntityRabbit.RabbitTypeData) {
         var4 = ((EntityRabbit.RabbitTypeData)var3).field_179427_a;
         var5 = true;
      } else {
         var3 = new EntityRabbit.RabbitTypeData(var4);
      }

      this.func_175529_r(var4);
      if (var5) {
         this.setGrowingAge(-24000);
      }

      return (IEntityLivingData)var3;
   }

   protected String getHurtSound() {
      return "mob.rabbit.hurt";
   }

   public int func_175531_cl() {
      return this.dataWatcher.getWatchableObjectByte(18);
   }

   protected String getLivingSound() {
      return "mob.rabbit.idle";
   }

   private boolean func_175525_a(Item var1) {
      return var1 == Items.carrot || var1 == Items.golden_carrot || var1 == Item.getItemFromBlock(Blocks.yellow_flower);
   }

   public void updateAITasks() {
      if (this.moveHelper.getSpeed() > 0.8D) {
         this.func_175522_a(EntityRabbit.EnumMoveType.SPRINT);
      } else if (this.field_175542_br != EntityRabbit.EnumMoveType.ATTACK) {
         this.func_175522_a(EntityRabbit.EnumMoveType.HOP);
      }

      if (this.field_175538_bq > 0) {
         --this.field_175538_bq;
      }

      if (this.field_175541_bs > 0) {
         this.field_175541_bs -= this.rand.nextInt(3);
         if (this.field_175541_bs < 0) {
            this.field_175541_bs = 0;
         }
      }

      if (this.onGround) {
         if (!this.field_175537_bp) {
            this.func_175519_a(false, EntityRabbit.EnumMoveType.NONE);
            this.func_175517_cu();
         }

         if (this.func_175531_cl() == 99 && this.field_175538_bq == 0) {
            EntityLivingBase var1 = this.getAttackTarget();
            if (var1 != null && this.getDistanceSqToEntity(var1) < 16.0D) {
               this.func_175533_a(var1.posX, var1.posZ);
               this.moveHelper.setMoveTo(var1.posX, var1.posY, var1.posZ, this.moveHelper.getSpeed());
               this.func_175524_b(EntityRabbit.EnumMoveType.ATTACK);
               this.field_175537_bp = true;
            }
         }

         EntityRabbit.RabbitJumpHelper var4 = (EntityRabbit.RabbitJumpHelper)this.jumpHelper;
         if (!var4.func_180067_c()) {
            if (this.moveHelper.isUpdating() && this.field_175538_bq == 0) {
               PathEntity var2 = this.navigator.getPath();
               Vec3 var3 = new Vec3(this.moveHelper.func_179917_d(), this.moveHelper.func_179919_e(), this.moveHelper.func_179918_f());
               if (var2 != null && var2.getCurrentPathIndex() < var2.getCurrentPathLength()) {
                  var3 = var2.getPosition(this);
               }

               this.func_175533_a(var3.xCoord, var3.zCoord);
               this.func_175524_b(this.field_175542_br);
            }
         } else if (!var4.func_180065_d()) {
            this.func_175518_cr();
         }
      }

      this.field_175537_bp = this.onGround;
   }

   private void func_175518_cr() {
      ((EntityRabbit.RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.field_175540_bm != this.field_175535_bn) {
         if (this.field_175540_bm == 0 && !this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)1);
         }

         ++this.field_175540_bm;
      } else if (this.field_175535_bn != 0) {
         this.field_175540_bm = 0;
         this.field_175535_bn = 0;
      }

   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(2) + this.rand.nextInt(1 + var2);

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.rabbit_hide, 1);
      }

      var3 = this.rand.nextInt(2);

      for(var4 = 0; var4 < var3; ++var4) {
         if (this.isBurning()) {
            this.dropItem(Items.cooked_rabbit, 1);
         } else {
            this.dropItem(Items.rabbit, 1);
         }
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.func_175529_r(var1.getInteger("RabbitType"));
      this.field_175541_bs = var1.getInteger("MoreCarrotTicks");
   }

   public void handleHealthUpdate(byte var1) {
      if (var1 == 1) {
         this.func_174808_Z();
         this.field_175535_bn = 10;
         this.field_175540_bm = 0;
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   public void func_175524_b(EntityRabbit.EnumMoveType var1) {
      this.func_175519_a(true, var1);
      this.field_175535_bn = var1.func_180073_d();
      this.field_175540_bm = 0;
   }

   public EntityRabbit func_175526_b(EntityAgeable var1) {
      EntityRabbit var2 = new EntityRabbit(this.worldObj);
      if (var1 instanceof EntityRabbit) {
         var2.func_175529_r(this.rand.nextBoolean() ? this.func_175531_cl() : ((EntityRabbit)var1).func_175531_cl());
      }

      return var2;
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return this.func_175526_b(var1);
   }

   private void func_175520_cs() {
      ((EntityRabbit.RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
   }

   static boolean access$0(EntityRabbit var0) {
      return var0.func_175534_cv();
   }

   public float func_175521_o(float var1) {
      return this.field_175535_bn == 0 ? 0.0F : ((float)this.field_175540_bm + var1) / (float)this.field_175535_bn;
   }

   class AIEvilAttack extends EntityAIAttackOnCollide {
      private static final String __OBFID = "CL_00002240";
      final EntityRabbit this$0;

      public AIEvilAttack(EntityRabbit var1) {
         super(var1, EntityLivingBase.class, 1.4D, true);
         this.this$0 = var1;
      }

      protected double func_179512_a(EntityLivingBase var1) {
         return (double)(4.0F + var1.width);
      }
   }

   class RabbitMoveHelper extends EntityMoveHelper {
      private static final String __OBFID = "CL_00002235";
      final EntityRabbit this$0;
      private EntityRabbit field_179929_g;

      public RabbitMoveHelper(EntityRabbit var1) {
         super(var1);
         this.this$0 = var1;
         this.field_179929_g = var1;
      }

      public void onUpdateMoveHelper() {
         if (this.field_179929_g.onGround && !this.field_179929_g.func_175523_cj()) {
            this.field_179929_g.func_175515_b(0.0D);
         }

         super.onUpdateMoveHelper();
      }
   }

   class AIPanic extends EntityAIPanic {
      final EntityRabbit this$0;
      private EntityRabbit field_179486_b;
      private static final String __OBFID = "CL_00002234";

      public AIPanic(EntityRabbit var1, double var2) {
         super(var1, var2);
         this.this$0 = var1;
         this.field_179486_b = var1;
      }

      public void updateTask() {
         super.updateTask();
         this.field_179486_b.func_175515_b(this.speed);
      }
   }

   static enum EnumMoveType {
      private final int field_180085_i;
      private final float field_180076_f;
      HOP("HOP", 1, 0.8F, 0.2F, 20, 10),
      NONE("NONE", 0, 0.0F, 0.0F, 30, 1);

      private static final String __OBFID = "CL_00002239";
      SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8);

      private final float field_180077_g;
      ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);

      private static final EntityRabbit.EnumMoveType[] $VALUES = new EntityRabbit.EnumMoveType[]{NONE, HOP, STEP, SPRINT, ATTACK};
      STEP("STEP", 2, 1.0F, 0.45F, 14, 14);

      private final int field_180084_h;
      private static final EntityRabbit.EnumMoveType[] ENUM$VALUES = new EntityRabbit.EnumMoveType[]{NONE, HOP, STEP, SPRINT, ATTACK};

      public int func_180075_c() {
         return this.field_180084_h;
      }

      private EnumMoveType(String var3, int var4, float var5, float var6, int var7, int var8) {
         this.field_180076_f = var5;
         this.field_180077_g = var6;
         this.field_180084_h = var7;
         this.field_180085_i = var8;
      }

      public int func_180073_d() {
         return this.field_180085_i;
      }

      public float func_180074_b() {
         return this.field_180077_g;
      }

      public float func_180072_a() {
         return this.field_180076_f;
      }
   }

   class AIRaidFarm extends EntityAIMoveToBlock {
      final EntityRabbit this$0;
      private boolean field_179498_d;
      private static final String __OBFID = "CL_00002233";
      private boolean field_179499_e;

      public boolean continueExecuting() {
         return this.field_179499_e && super.continueExecuting();
      }

      public void updateTask() {
         super.updateTask();
         this.this$0.getLookHelper().setLookPosition((double)this.field_179494_b.getX() + 0.5D, (double)(this.field_179494_b.getY() + 1), (double)this.field_179494_b.getZ() + 0.5D, 10.0F, (float)this.this$0.getVerticalFaceSpeed());
         if (this.func_179487_f()) {
            World var1 = this.this$0.worldObj;
            BlockPos var2 = this.field_179494_b.offsetUp();
            IBlockState var3 = var1.getBlockState(var2);
            Block var4 = var3.getBlock();
            if (this.field_179499_e && var4 instanceof BlockCarrot && (Integer)var3.getValue(BlockCarrot.AGE) == 7) {
               var1.setBlockState(var2, Blocks.air.getDefaultState(), 2);
               var1.destroyBlock(var2, true);
               this.this$0.func_175528_cn();
            }

            this.field_179499_e = false;
            this.field_179496_a = 10;
         }

      }

      protected boolean func_179488_a(World var1, BlockPos var2) {
         Block var3 = var1.getBlockState(var2).getBlock();
         if (var3 == Blocks.farmland) {
            var2 = var2.offsetUp();
            IBlockState var4 = var1.getBlockState(var2);
            var3 = var4.getBlock();
            if (var3 instanceof BlockCarrot && (Integer)var4.getValue(BlockCarrot.AGE) == 7 && this.field_179498_d && !this.field_179499_e) {
               this.field_179499_e = true;
               return true;
            }
         }

         return false;
      }

      public void startExecuting() {
         super.startExecuting();
      }

      public AIRaidFarm(EntityRabbit var1) {
         super(var1, 0.699999988079071D, 16);
         this.this$0 = var1;
         this.field_179499_e = false;
      }

      public void resetTask() {
         super.resetTask();
      }

      public boolean shouldExecute() {
         if (this.field_179496_a <= 0) {
            if (!this.this$0.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
               return false;
            }

            this.field_179499_e = false;
            this.field_179498_d = EntityRabbit.access$0(this.this$0);
         }

         return super.shouldExecute();
      }
   }

   public static class RabbitTypeData implements IEntityLivingData {
      public int field_179427_a;
      private static final String __OBFID = "CL_00002237";

      public RabbitTypeData(int var1) {
         this.field_179427_a = var1;
      }
   }

   class AIAvoidEntity extends EntityAIAvoidEntity {
      private EntityRabbit field_179511_d;
      final EntityRabbit this$0;
      private static final String __OBFID = "CL_00002238";

      public AIAvoidEntity(EntityRabbit var1, Predicate var2, float var3, double var4, double var6) {
         super(var1, var2, var3, var4, var6);
         this.this$0 = var1;
         this.field_179511_d = var1;
      }

      public void updateTask() {
         super.updateTask();
      }
   }

   public class RabbitJumpHelper extends EntityJumpHelper {
      final EntityRabbit this$0;
      private EntityRabbit field_180070_c;
      private boolean field_180068_d;
      private static final String __OBFID = "CL_00002236";

      public boolean func_180067_c() {
         return this.isJumping;
      }

      public void func_180066_a(boolean var1) {
         this.field_180068_d = var1;
      }

      public RabbitJumpHelper(EntityRabbit var1, EntityRabbit var2) {
         super(var2);
         this.this$0 = var1;
         this.field_180068_d = false;
         this.field_180070_c = var2;
      }

      public void doJump() {
         if (this.isJumping) {
            this.field_180070_c.func_175524_b(EntityRabbit.EnumMoveType.STEP);
            this.isJumping = false;
         }

      }

      public boolean func_180065_d() {
         return this.field_180068_d;
      }
   }
}
