package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal
{
  private AIAvoidEntity field_175539_bk;
  private int field_175540_bm = 0;
  private int field_175535_bn = 0;
  private boolean field_175536_bo = false;
  private boolean field_175537_bp = false;
  private int field_175538_bq = 0;
  private EnumMoveType field_175542_br;
  private int field_175541_bs;
  private EntityPlayer field_175543_bt;
  private static final String __OBFID = "CL_00002242";
  
  public EntityRabbit(World worldIn)
  {
    super(worldIn);
    field_175542_br = EnumMoveType.HOP;
    field_175541_bs = 0;
    field_175543_bt = null;
    setSize(0.6F, 0.7F);
    jumpHelper = new RabbitJumpHelper(this);
    moveHelper = new RabbitMoveHelper();
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    navigator.func_179678_a(2.5F);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(1, new AIPanic(1.33D));
    tasks.addTask(2, new net.minecraft.entity.ai.EntityAITempt(this, 1.0D, Items.carrot, false));
    tasks.addTask(3, new EntityAIMate(this, 0.8D));
    tasks.addTask(5, new AIRaidFarm());
    tasks.addTask(5, new net.minecraft.entity.ai.EntityAIWander(this, 0.6D));
    tasks.addTask(11, new net.minecraft.entity.ai.EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
    field_175539_bk = new AIAvoidEntity(new Predicate()
    {
      private static final String __OBFID = "CL_00002241";
      
      public boolean func_180086_a(Entity p_180086_1_) {
        return p_180086_1_ instanceof EntityWolf;
      }
      
      public boolean apply(Object p_apply_1_) {
        return func_180086_a((Entity)p_apply_1_);
      }
    }, 16.0F, 1.33D, 1.33D);
    tasks.addTask(4, field_175539_bk);
    func_175515_b(0.0D);
  }
  
  protected float func_175134_bD()
  {
    return (moveHelper.isUpdating()) && (moveHelper.func_179919_e() > posY + 0.5D) ? 0.5F : field_175542_br.func_180074_b();
  }
  
  public void func_175522_a(EnumMoveType p_175522_1_)
  {
    field_175542_br = p_175522_1_;
  }
  
  public float func_175521_o(float p_175521_1_)
  {
    return field_175535_bn == 0 ? 0.0F : (field_175540_bm + p_175521_1_) / field_175535_bn;
  }
  
  public void func_175515_b(double p_175515_1_)
  {
    getNavigator().setSpeed(p_175515_1_);
    moveHelper.setMoveTo(moveHelper.func_179917_d(), moveHelper.func_179919_e(), moveHelper.func_179918_f(), p_175515_1_);
  }
  
  public void func_175519_a(boolean p_175519_1_, EnumMoveType p_175519_2_)
  {
    super.setJumping(p_175519_1_);
    
    if (!p_175519_1_)
    {
      if (field_175542_br == EnumMoveType.ATTACK)
      {
        field_175542_br = EnumMoveType.HOP;
      }
    }
    else
    {
      func_175515_b(1.5D * p_175519_2_.func_180072_a());
      playSound(func_175516_ck(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }
    
    field_175536_bo = p_175519_1_;
  }
  
  public void func_175524_b(EnumMoveType p_175524_1_)
  {
    func_175519_a(true, p_175524_1_);
    field_175535_bn = p_175524_1_.func_180073_d();
    field_175540_bm = 0;
  }
  
  public boolean func_175523_cj()
  {
    return field_175536_bo;
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(18, Byte.valueOf((byte)0));
  }
  
  public void updateAITasks()
  {
    if (moveHelper.getSpeed() > 0.8D)
    {
      func_175522_a(EnumMoveType.SPRINT);
    }
    else if (field_175542_br != EnumMoveType.ATTACK)
    {
      func_175522_a(EnumMoveType.HOP);
    }
    
    if (field_175538_bq > 0)
    {
      field_175538_bq -= 1;
    }
    
    if (field_175541_bs > 0)
    {
      field_175541_bs -= rand.nextInt(3);
      
      if (field_175541_bs < 0)
      {
        field_175541_bs = 0;
      }
    }
    
    if (onGround)
    {
      if (!field_175537_bp)
      {
        func_175519_a(false, EnumMoveType.NONE);
        func_175517_cu();
      }
      
      if ((func_175531_cl() == 99) && (field_175538_bq == 0))
      {
        EntityLivingBase var1 = getAttackTarget();
        
        if ((var1 != null) && (getDistanceSqToEntity(var1) < 16.0D))
        {
          func_175533_a(posX, posZ);
          moveHelper.setMoveTo(posX, posY, posZ, moveHelper.getSpeed());
          func_175524_b(EnumMoveType.ATTACK);
          field_175537_bp = true;
        }
      }
      
      RabbitJumpHelper var4 = (RabbitJumpHelper)jumpHelper;
      
      if (!var4.func_180067_c())
      {
        if ((moveHelper.isUpdating()) && (field_175538_bq == 0))
        {
          PathEntity var2 = navigator.getPath();
          Vec3 var3 = new Vec3(moveHelper.func_179917_d(), moveHelper.func_179919_e(), moveHelper.func_179918_f());
          
          if ((var2 != null) && (var2.getCurrentPathIndex() < var2.getCurrentPathLength()))
          {
            var3 = var2.getPosition(this);
          }
          
          func_175533_a(xCoord, zCoord);
          func_175524_b(field_175542_br);
        }
      }
      else if (!var4.func_180065_d())
      {
        func_175518_cr();
      }
    }
    
    field_175537_bp = onGround;
  }
  
  public void func_174830_Y() {}
  
  private void func_175533_a(double p_175533_1_, double p_175533_3_)
  {
    rotationYaw = ((float)(Math.atan2(p_175533_3_ - posZ, p_175533_1_ - posX) * 180.0D / 3.141592653589793D) - 90.0F);
  }
  
  private void func_175518_cr()
  {
    ((RabbitJumpHelper)jumpHelper).func_180066_a(true);
  }
  
  private void func_175520_cs()
  {
    ((RabbitJumpHelper)jumpHelper).func_180066_a(false);
  }
  
  private void func_175530_ct()
  {
    field_175538_bq = func_175532_cm();
  }
  
  private void func_175517_cu()
  {
    func_175530_ct();
    func_175520_cs();
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if (field_175540_bm != field_175535_bn)
    {
      if ((field_175540_bm == 0) && (!worldObj.isRemote))
      {
        worldObj.setEntityState(this, (byte)1);
      }
      
      field_175540_bm += 1;
    }
    else if (field_175535_bn != 0)
    {
      field_175540_bm = 0;
      field_175535_bn = 0;
    }
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("RabbitType", func_175531_cl());
    tagCompound.setInteger("MoreCarrotTicks", field_175541_bs);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    func_175529_r(tagCompund.getInteger("RabbitType"));
    field_175541_bs = tagCompund.getInteger("MoreCarrotTicks");
  }
  
  protected String func_175516_ck()
  {
    return "mob.rabbit.hop";
  }
  



  protected String getLivingSound()
  {
    return "mob.rabbit.idle";
  }
  



  protected String getHurtSound()
  {
    return "mob.rabbit.hurt";
  }
  



  protected String getDeathSound()
  {
    return "mob.rabbit.death";
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    if (func_175531_cl() == 99)
    {
      playSound("mob.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
      return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }
    

    return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
  }
  




  public int getTotalArmorValue()
  {
    return func_175531_cl() == 99 ? 8 : super.getTotalArmorValue();
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    return func_180431_b(source) ? false : super.attackEntityFrom(source, amount);
  }
  



  protected void addRandomArmor()
  {
    entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(2) + rand.nextInt(1 + p_70628_2_);
    

    for (int var4 = 0; var4 < var3; var4++)
    {
      dropItem(Items.rabbit_hide, 1);
    }
    
    var3 = rand.nextInt(2);
    
    for (var4 = 0; var4 < var3; var4++)
    {
      if (isBurning())
      {
        dropItem(Items.cooked_rabbit, 1);
      }
      else
      {
        dropItem(Items.rabbit, 1);
      }
    }
  }
  
  private boolean func_175525_a(Item p_175525_1_)
  {
    return (p_175525_1_ == Items.carrot) || (p_175525_1_ == Items.golden_carrot) || (p_175525_1_ == Item.getItemFromBlock(Blocks.yellow_flower));
  }
  
  public EntityRabbit func_175526_b(EntityAgeable p_175526_1_)
  {
    EntityRabbit var2 = new EntityRabbit(worldObj);
    
    if ((p_175526_1_ instanceof EntityRabbit))
    {
      var2.func_175529_r(rand.nextBoolean() ? func_175531_cl() : ((EntityRabbit)p_175526_1_).func_175531_cl());
    }
    
    return var2;
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return (p_70877_1_ != null) && (func_175525_a(p_70877_1_.getItem()));
  }
  
  public int func_175531_cl()
  {
    return dataWatcher.getWatchableObjectByte(18);
  }
  
  public void func_175529_r(int p_175529_1_)
  {
    if (p_175529_1_ == 99)
    {
      tasks.removeTask(field_175539_bk);
      tasks.addTask(4, new AIEvilAttack());
      targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
      targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
      
      if (!hasCustomName())
      {
        setCustomNameTag(net.minecraft.util.StatCollector.translateToLocal("entity.KillerBunny.name"));
      }
    }
    
    dataWatcher.updateObject(18, Byte.valueOf((byte)p_175529_1_));
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);
    int var3 = rand.nextInt(6);
    boolean var4 = false;
    
    if ((p_180482_2_1 instanceof RabbitTypeData))
    {
      var3 = field_179427_a;
      var4 = true;
    }
    else
    {
      p_180482_2_1 = new RabbitTypeData(var3);
    }
    
    func_175529_r(var3);
    
    if (var4)
    {
      setGrowingAge(41536);
    }
    
    return (IEntityLivingData)p_180482_2_1;
  }
  
  private boolean func_175534_cv()
  {
    return field_175541_bs == 0;
  }
  
  protected int func_175532_cm()
  {
    return field_175542_br.func_180075_c();
  }
  
  protected void func_175528_cn()
  {
    worldObj.spawnParticle(net.minecraft.util.EnumParticleTypes.BLOCK_DUST, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(Blocks.carrots.getStateFromMeta(7)) });
    field_175541_bs = 100;
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 1)
    {
      func_174808_Z();
      field_175535_bn = 10;
      field_175540_bm = 0;
    }
    else
    {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  
  public EntityAgeable createChild(EntityAgeable p_90011_1_)
  {
    return func_175526_b(p_90011_1_);
  }
  
  class AIAvoidEntity extends EntityAIAvoidEntity
  {
    private EntityRabbit field_179511_d = EntityRabbit.this;
    private static final String __OBFID = "CL_00002238";
    
    public AIAvoidEntity(Predicate p_i45865_2_, float p_i45865_3_, double p_i45865_4_, double p_i45865_6_)
    {
      super(p_i45865_2_, p_i45865_3_, p_i45865_4_, p_i45865_6_);
    }
    
    public void updateTask()
    {
      super.updateTask();
    }
  }
  
  class AIEvilAttack extends EntityAIAttackOnCollide
  {
    private static final String __OBFID = "CL_00002240";
    
    public AIEvilAttack()
    {
      super(EntityLivingBase.class, 1.4D, true);
    }
    
    protected double func_179512_a(EntityLivingBase p_179512_1_)
    {
      return 4.0F + width;
    }
  }
  
  class AIPanic extends EntityAIPanic
  {
    private EntityRabbit field_179486_b = EntityRabbit.this;
    private static final String __OBFID = "CL_00002234";
    
    public AIPanic(double p_i45861_2_)
    {
      super(p_i45861_2_);
    }
    
    public void updateTask()
    {
      super.updateTask();
      field_179486_b.func_175515_b(speed);
    }
  }
  
  class AIRaidFarm extends EntityAIMoveToBlock
  {
    private boolean field_179498_d;
    private boolean field_179499_e = false;
    private static final String __OBFID = "CL_00002233";
    
    public AIRaidFarm()
    {
      super(0.699999988079071D, 16);
    }
    
    public boolean shouldExecute()
    {
      if (field_179496_a <= 0)
      {
        if (!worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
        {
          return false;
        }
        
        field_179499_e = false;
        field_179498_d = EntityRabbit.this.func_175534_cv();
      }
      
      return super.shouldExecute();
    }
    
    public boolean continueExecuting()
    {
      return (field_179499_e) && (super.continueExecuting());
    }
    
    public void startExecuting()
    {
      super.startExecuting();
    }
    
    public void resetTask()
    {
      super.resetTask();
    }
    
    public void updateTask()
    {
      super.updateTask();
      getLookHelper().setLookPosition(field_179494_b.getX() + 0.5D, field_179494_b.getY() + 1, field_179494_b.getZ() + 0.5D, 10.0F, getVerticalFaceSpeed());
      
      if (func_179487_f())
      {
        World var1 = worldObj;
        BlockPos var2 = field_179494_b.offsetUp();
        IBlockState var3 = var1.getBlockState(var2);
        Block var4 = var3.getBlock();
        
        if ((field_179499_e) && ((var4 instanceof BlockCarrot)) && (((Integer)var3.getValue(BlockCarrot.AGE)).intValue() == 7))
        {
          var1.setBlockState(var2, Blocks.air.getDefaultState(), 2);
          var1.destroyBlock(var2, true);
          func_175528_cn();
        }
        
        field_179499_e = false;
        field_179496_a = 10;
      }
    }
    
    protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
    {
      Block var3 = worldIn.getBlockState(p_179488_2_).getBlock();
      
      if (var3 == Blocks.farmland)
      {
        p_179488_2_ = p_179488_2_.offsetUp();
        IBlockState var4 = worldIn.getBlockState(p_179488_2_);
        var3 = var4.getBlock();
        
        if (((var3 instanceof BlockCarrot)) && (((Integer)var4.getValue(BlockCarrot.AGE)).intValue() == 7) && (field_179498_d) && (!field_179499_e))
        {
          field_179499_e = true;
          return true;
        }
      }
      
      return false;
    }
  }
  
  static enum EnumMoveType
  {
    NONE("NONE", 0, 0.0F, 0.0F, 30, 1), 
    HOP("HOP", 1, 0.8F, 0.2F, 20, 10), 
    STEP("STEP", 2, 1.0F, 0.45F, 14, 14), 
    SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8), 
    ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);
    
    private final float field_180076_f;
    private final float field_180077_g;
    private final int field_180084_h;
    private final int field_180085_i;
    private static final EnumMoveType[] $VALUES = { NONE, HOP, STEP, SPRINT, ATTACK };
    private static final String __OBFID = "CL_00002239";
    
    private EnumMoveType(String p_i45866_1_, int p_i45866_2_, float p_i45866_3_, float p_i45866_4_, int p_i45866_5_, int p_i45866_6_)
    {
      field_180076_f = p_i45866_3_;
      field_180077_g = p_i45866_4_;
      field_180084_h = p_i45866_5_;
      field_180085_i = p_i45866_6_;
    }
    
    public float func_180072_a()
    {
      return field_180076_f;
    }
    
    public float func_180074_b()
    {
      return field_180077_g;
    }
    
    public int func_180075_c()
    {
      return field_180084_h;
    }
    
    public int func_180073_d()
    {
      return field_180085_i;
    }
  }
  
  public class RabbitJumpHelper extends EntityJumpHelper
  {
    private EntityRabbit field_180070_c;
    private boolean field_180068_d = false;
    private static final String __OBFID = "CL_00002236";
    
    public RabbitJumpHelper(EntityRabbit p_i45863_2_)
    {
      super();
      field_180070_c = p_i45863_2_;
    }
    
    public boolean func_180067_c()
    {
      return isJumping;
    }
    
    public boolean func_180065_d()
    {
      return field_180068_d;
    }
    
    public void func_180066_a(boolean p_180066_1_)
    {
      field_180068_d = p_180066_1_;
    }
    
    public void doJump()
    {
      if (isJumping)
      {
        field_180070_c.func_175524_b(EntityRabbit.EnumMoveType.STEP);
        isJumping = false;
      }
    }
  }
  
  class RabbitMoveHelper extends EntityMoveHelper
  {
    private EntityRabbit field_179929_g = EntityRabbit.this;
    private static final String __OBFID = "CL_00002235";
    
    public RabbitMoveHelper()
    {
      super();
    }
    
    public void onUpdateMoveHelper()
    {
      if ((field_179929_g.onGround) && (!field_179929_g.func_175523_cj()))
      {
        field_179929_g.func_175515_b(0.0D);
      }
      
      super.onUpdateMoveHelper();
    }
  }
  
  public static class RabbitTypeData implements IEntityLivingData
  {
    public int field_179427_a;
    private static final String __OBFID = "CL_00002237";
    
    public RabbitTypeData(int p_i45864_1_)
    {
      field_179427_a = p_i45864_1_;
    }
  }
}
