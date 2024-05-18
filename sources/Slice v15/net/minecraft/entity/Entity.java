package net.minecraft.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.SliceClient.Slice;
import net.SliceClient.event.EventPostMotion;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.SliceClient.modules.MaxRotations;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

public abstract class Entity implements ICommandSender
{
  private static final AxisAlignedBB field_174836_a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
  

  private static int nextEntityID;
  

  private int entityId;
  

  public double renderDistanceWeight;
  

  public boolean preventEntitySpawning;
  

  public Entity riddenByEntity;
  

  public Entity ridingEntity;
  

  public boolean forceSpawn;
  

  public World worldObj;
  

  public double prevPosX;
  

  public double prevPosY;
  

  public double prevPosZ;
  

  public double posX;
  

  public double posY;
  

  public double posZ;
  

  public double motionX;
  

  public double motionY;
  

  public double motionZ;
  

  public float rotationYaw;
  

  public float rotationPitch;
  

  public float prevRotationYaw;
  

  public float prevRotationPitch;
  

  public AxisAlignedBB boundingBox;
  

  public boolean onGround;
  

  public boolean isCollidedHorizontally;
  

  public boolean isCollidedVertically;
  

  public boolean isCollided;
  

  public boolean velocityChanged;
  

  public boolean isInWeb;
  

  private boolean isOutsideBorder;
  

  public boolean isDead;
  

  public float width;
  

  public float height;
  

  public float prevDistanceWalkedModified;
  

  public float distanceWalkedModified;
  
  public float distanceWalkedOnStepModified;
  
  public float fallDistance;
  
  private int nextStepDistance;
  
  public double lastTickPosX;
  
  public double lastTickPosY;
  
  public double lastTickPosZ;
  
  public float stepHeight;
  
  public boolean noClip;
  
  public float entityCollisionReduction;
  
  protected Random rand;
  
  public int ticksExisted;
  
  public int fireResistance;
  
  private int fire;
  
  public boolean inWater;
  
  public int hurtResistantTime;
  
  protected boolean firstUpdate;
  
  protected boolean isImmuneToFire;
  
  protected DataWatcher dataWatcher;
  
  private double entityRiderPitchDelta;
  
  private double entityRiderYawDelta;
  
  public boolean addedToChunk;
  
  public int chunkCoordX;
  
  public int chunkCoordY;
  
  public int chunkCoordZ;
  
  public int serverPosX;
  
  public int serverPosY;
  
  public int serverPosZ;
  
  public boolean ignoreFrustumCheck;
  
  public boolean isAirBorne;
  
  public int timeUntilPortal;
  
  protected boolean inPortal;
  
  protected int portalCounter;
  
  public int dimension;
  
  protected int teleportDirection;
  
  private boolean invulnerable;
  
  public UUID entityUniqueID;
  
  private final CommandResultStats field_174837_as;
  
  private static final String __OBFID = "CL_00001533";
  

  public int getEntityId()
  {
    return entityId;
  }
  
  public void setEntityId(int id)
  {
    entityId = id;
  }
  
  public void func_174812_G()
  {
    setDead();
  }
  
  public Entity(World worldIn)
  {
    entityId = (nextEntityID++);
    renderDistanceWeight = 1.0D;
    boundingBox = field_174836_a;
    width = 0.6F;
    height = 1.8F;
    nextStepDistance = 1;
    rand = new Random();
    fireResistance = 1;
    firstUpdate = true;
    entityUniqueID = MathHelper.func_180182_a(rand);
    field_174837_as = new CommandResultStats();
    worldObj = worldIn;
    setPosition(0.0D, 0.0D, 0.0D);
    
    if (worldIn != null)
    {
      dimension = provider.getDimensionId();
    }
    
    dataWatcher = new DataWatcher(this);
    dataWatcher.addObject(0, Byte.valueOf((byte)0));
    dataWatcher.addObject(1, Short.valueOf((short)300));
    dataWatcher.addObject(3, Byte.valueOf((byte)0));
    dataWatcher.addObject(2, "");
    dataWatcher.addObject(4, Byte.valueOf((byte)0));
    entityInit();
  }
  
  protected abstract void entityInit();
  
  public DataWatcher getDataWatcher()
  {
    return dataWatcher;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    return entityId == entityId;
  }
  
  public int hashCode()
  {
    return entityId;
  }
  




  protected void preparePlayerToSpawn()
  {
    if (worldObj != null)
    {
      while ((posY > 0.0D) && (posY < 256.0D))
      {
        setPosition(posX, posY, posZ);
        
        if (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
          break;
        }
        

        posY += 1.0D;
      }
      
      motionX = (this.motionY = this.motionZ = 0.0D);
      rotationPitch = 0.0F;
    }
  }
  



  public void setDead()
  {
    isDead = true;
  }
  



  protected void setSize(float width, float height)
  {
    if ((width != this.width) || (height != this.height))
    {
      float var3 = this.width;
      this.width = width;
      this.height = height;
      func_174826_a(new AxisAlignedBB(getEntityBoundingBoxminX, getEntityBoundingBoxminY, getEntityBoundingBoxminZ, getEntityBoundingBoxminX + this.width, getEntityBoundingBoxminY + this.height, getEntityBoundingBoxminZ + this.width));
      
      if ((this.width > var3) && (!firstUpdate) && (!worldObj.isRemote))
      {
        moveEntity(var3 - this.width, 0.0D, var3 - this.width);
      }
    }
  }
  



  protected void setRotation(float yaw, float pitch)
  {
    rotationYaw = (yaw % 360.0F);
    rotationPitch = (pitch % 360.0F);
  }
  



  public void setPosition(double x, double y, double z)
  {
    posX = x;
    posY = y;
    posZ = z;
    float var7 = width / 2.0F;
    float var8 = height;
    func_174826_a(new AxisAlignedBB(x - var7, y, z - var7, x + var7, y + var8, z + var7));
  }
  




  public void setAngles(float yaw, float pitch)
  {
    float var3 = rotationPitch;
    float var4 = rotationYaw;
    rotationYaw = ((float)(rotationYaw + yaw * 0.15D));
    rotationPitch = ((float)(rotationPitch - pitch * 0.15D));
    if (!ModuleManager.getModule(MaxRotations.class).isEnabled()) {
      rotationPitch = MathHelper.clamp_float(rotationPitch, -90.0F, 90.0F);
    }
    prevRotationPitch += rotationPitch - var3;
    prevRotationYaw += rotationYaw - var4;
  }
  



  public void onUpdate()
  {
    onEntityUpdate();
  }
  



  public void onEntityUpdate()
  {
    worldObj.theProfiler.startSection("entityBaseTick");
    
    if ((ridingEntity != null) && (ridingEntity.isDead))
    {
      ridingEntity = null;
    }
    
    prevDistanceWalkedModified = distanceWalkedModified;
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    prevRotationPitch = rotationPitch;
    prevRotationYaw = rotationYaw;
    
    if ((!worldObj.isRemote) && ((worldObj instanceof WorldServer)))
    {
      worldObj.theProfiler.startSection("portal");
      MinecraftServer var1 = ((WorldServer)worldObj).func_73046_m();
      int var2 = getMaxInPortalTime();
      
      if (inPortal)
      {
        if (var1.getAllowNether())
        {
          if ((ridingEntity == null) && (portalCounter++ >= var2))
          {
            portalCounter = var2;
            timeUntilPortal = getPortalCooldown();
            byte var3;
            byte var3;
            if (worldObj.provider.getDimensionId() == -1)
            {
              var3 = 0;
            }
            else
            {
              var3 = -1;
            }
            
            travelToDimension(var3);
          }
          
          inPortal = false;
        }
      }
      else
      {
        if (portalCounter > 0)
        {
          portalCounter -= 4;
        }
        
        if (portalCounter < 0)
        {
          portalCounter = 0;
        }
      }
      
      if (timeUntilPortal > 0)
      {
        timeUntilPortal -= 1;
      }
      
      worldObj.theProfiler.endSection();
    }
    
    func_174830_Y();
    handleWaterMovement();
    
    if (worldObj.isRemote)
    {
      fire = 0;
    }
    else if (fire > 0)
    {
      if (isImmuneToFire)
      {
        fire -= 4;
        
        if (fire < 0)
        {
          fire = 0;
        }
      }
      else
      {
        if (fire % 20 == 0)
        {
          attackEntityFrom(DamageSource.onFire, 1.0F);
        }
        
        fire -= 1;
      }
    }
    
    if (func_180799_ab())
    {
      setOnFireFromLava();
      fallDistance *= 0.5F;
    }
    
    if (posY < -64.0D)
    {
      kill();
    }
    
    if (!worldObj.isRemote)
    {
      setFlag(0, fire > 0);
    }
    
    firstUpdate = false;
    worldObj.theProfiler.endSection();
  }
  



  public int getMaxInPortalTime()
  {
    return 0;
  }
  



  protected void setOnFireFromLava()
  {
    if (!isImmuneToFire)
    {
      attackEntityFrom(DamageSource.lava, 4.0F);
      setFire(15);
    }
  }
  



  public void setFire(int seconds)
  {
    int var2 = seconds * 20;
    var2 = EnchantmentProtection.getFireTimeForEntity(this, var2);
    
    if (fire < var2)
    {
      fire = var2;
    }
  }
  



  public void extinguish()
  {
    fire = 0;
  }
  



  protected void kill()
  {
    setDead();
  }
  



  public boolean isOffsetPositionInLiquid(double x, double y, double z)
  {
    AxisAlignedBB var7 = getEntityBoundingBox().offset(x, y, z);
    return func_174809_b(var7);
  }
  
  private boolean func_174809_b(AxisAlignedBB p_174809_1_)
  {
    return (worldObj.getCollidingBoundingBoxes(this, p_174809_1_).isEmpty()) && (!worldObj.isAnyLiquid(p_174809_1_));
  }
  



  public void moveEntity(double x, double y, double z)
  {
    for (Module m : ModuleManager.activeModules) {
      if (m.getState()) {
        m.onPreMotionUpdate();
      }
    }
    if (!noClip) { Slice.getTrap(); if ((!Slice.getState("Freecam")) || (!(this instanceof EntityPlayer))) {}
    } else {
      if ((this instanceof EntityPlayer)) { Slice.getTrap(); if (Slice.getState("Freecam")) {
          func_174826_a(getEntityBoundingBox().offset(x * 10.0D, y, z * 10.0D));
          break label134; } }
      func_174826_a(getEntityBoundingBox().offset(x, y, z));
      label134:
      func_174829_m();
      return;
    }
    
    worldObj.theProfiler.startSection("move");
    double var7 = posX;
    double var9 = posY;
    double var11 = posZ;
    
    if (isInWeb)
    {
      isInWeb = false;
      x *= 0.25D;
      y *= 0.05000000074505806D;
      z *= 0.25D;
      motionX = 0.0D;
      motionY = 0.0D;
      motionZ = 0.0D;
    }
    
    double var13 = x;
    double var15 = y;
    double var17 = z;
    boolean var19 = (onGround) && (isSneaking()) && ((this instanceof EntityPlayer));
    
    if ((var19) || ((ModuleManager.getModule(net.SliceClient.modules.ScaffoldWalk.class).isEnabled()) && (onGround) && ((this instanceof EntityPlayer))))
    {


      double var20 = 0.05D;
      for (;;) {
        if ((x < var20) && (x >= -var20))
        {
          x = 0.0D;
        }
        else if (x > 0.0D)
        {
          x -= var20;
        }
        else
        {
          x += var20;
        }
        var13 = x; if (x != 0.0D) { if (!worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty()) {
            break;
          }
        }
      }
      










      do
      {
        if ((z < var20) && (z >= -var20))
        {
          z = 0.0D;
        }
        else if (z > 0.0D)
        {
          z -= var20;
        }
        else
        {
          z += var20;
        }
        var17 = z; if (z == 0.0D) break; } while (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty());
      for (; 
          













          (x != 0.0D) && (z != 0.0D) && (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty()); var17 = z)
      {
        if ((x < var20) && (x >= -var20))
        {
          x = 0.0D;
        }
        else if (x > 0.0D)
        {
          x -= var20;
        }
        else
        {
          x += var20;
        }
        
        var13 = x;
        
        if ((z < var20) && (z >= -var20))
        {
          z = 0.0D;
        }
        else if (z > 0.0D)
        {
          z -= var20;
        }
        else
        {
          z += var20;
        }
      }
    }
    
    List var53 = worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(x, y, z));
    AxisAlignedBB var21 = getEntityBoundingBox();
    
    AxisAlignedBB var23;
    for (Iterator var22 = var53.iterator(); var22.hasNext(); y = var23.calculateYOffset(getEntityBoundingBox(), y))
    {
      var23 = (AxisAlignedBB)var22.next();
    }
    
    func_174826_a(getEntityBoundingBox().offset(0.0D, y, 0.0D));
    boolean var54 = (onGround) || ((var15 != y) && (var15 < 0.0D));
    
    AxisAlignedBB var24;
    
    for (Iterator var55 = var53.iterator(); var55.hasNext(); x = var24.calculateXOffset(getEntityBoundingBox(), x))
    {
      var24 = (AxisAlignedBB)var55.next();
    }
    
    func_174826_a(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
    AxisAlignedBB var24;
    for (var55 = var53.iterator(); var55.hasNext(); z = var24.calculateZOffset(getEntityBoundingBox(), z))
    {
      var24 = (AxisAlignedBB)var55.next();
    }
    
    func_174826_a(getEntityBoundingBox().offset(0.0D, 0.0D, z));
    
    if ((stepHeight > 0.0F) && (var54) && ((var13 != x) || (var17 != z)))
    {
      double var56 = x;
      double var25 = y;
      double var27 = z;
      AxisAlignedBB var29 = getEntityBoundingBox();
      func_174826_a(var21);
      y = stepHeight;
      List var30 = worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(var13, y, var17));
      AxisAlignedBB var31 = getEntityBoundingBox();
      AxisAlignedBB var32 = var31.addCoord(var13, 0.0D, var17);
      double var33 = y;
      
      AxisAlignedBB var36;
      for (Iterator var35 = var30.iterator(); var35.hasNext(); var33 = var36.calculateYOffset(var32, var33))
      {
        var36 = (AxisAlignedBB)var35.next();
      }
      
      var31 = var31.offset(0.0D, var33, 0.0D);
      double var67 = var13;
      
      AxisAlignedBB var38;
      for (Iterator var37 = var30.iterator(); var37.hasNext(); var67 = var38.calculateXOffset(var31, var67))
      {
        var38 = (AxisAlignedBB)var37.next();
      }
      
      var31 = var31.offset(var67, 0.0D, 0.0D);
      double var68 = var17;
      
      AxisAlignedBB var40;
      for (Iterator var39 = var30.iterator(); var39.hasNext(); var68 = var40.calculateZOffset(var31, var68))
      {
        var40 = (AxisAlignedBB)var39.next();
      }
      
      var31 = var31.offset(0.0D, 0.0D, var68);
      AxisAlignedBB var69 = getEntityBoundingBox();
      double var70 = y;
      
      AxisAlignedBB var43;
      for (Iterator var42 = var30.iterator(); var42.hasNext(); var70 = var43.calculateYOffset(var69, var70))
      {
        var43 = (AxisAlignedBB)var42.next();
      }
      
      var69 = var69.offset(0.0D, var70, 0.0D);
      double var71 = var13;
      
      AxisAlignedBB var45;
      for (Iterator var44 = var30.iterator(); var44.hasNext(); var71 = var45.calculateXOffset(var69, var71))
      {
        var45 = (AxisAlignedBB)var44.next();
      }
      
      var69 = var69.offset(var71, 0.0D, 0.0D);
      double var72 = var17;
      
      AxisAlignedBB var47;
      for (Iterator var46 = var30.iterator(); var46.hasNext(); var72 = var47.calculateZOffset(var69, var72))
      {
        var47 = (AxisAlignedBB)var46.next();
      }
      
      var69 = var69.offset(0.0D, 0.0D, var72);
      double var73 = var67 * var67 + var68 * var68;
      double var48 = var71 * var71 + var72 * var72;
      
      if (var73 > var48)
      {
        x = var67;
        z = var68;
        func_174826_a(var31);
      }
      else
      {
        x = var71;
        z = var72;
        func_174826_a(var69);
      }
      
      y = -stepHeight;
      
      AxisAlignedBB var51;
      for (Iterator var50 = var30.iterator(); var50.hasNext(); y = var51.calculateYOffset(getEntityBoundingBox(), y))
      {
        var51 = (AxisAlignedBB)var50.next();
      }
      
      func_174826_a(getEntityBoundingBox().offset(0.0D, y, 0.0D));
      
      if (var56 * var56 + var27 * var27 >= x * x + z * z)
      {
        x = var56;
        y = var25;
        z = var27;
        func_174826_a(var29);
      }
    }
    
    worldObj.theProfiler.endSection();
    worldObj.theProfiler.startSection("rest");
    func_174829_m();
    isCollidedHorizontally = ((var13 != x) || (var17 != z));
    isCollidedVertically = (var15 != y);
    onGround = ((isCollidedVertically) && (var15 < 0.0D));
    isCollided = ((isCollidedHorizontally) || (isCollidedVertically));
    int var57 = MathHelper.floor_double(posX);
    int var58 = MathHelper.floor_double(posY - 0.20000000298023224D);
    int var59 = MathHelper.floor_double(posZ);
    BlockPos var26 = new BlockPos(var57, var58, var59);
    Block var60 = worldObj.getBlockState(var26).getBlock();
    
    if (var60.getMaterial() == Material.air)
    {
      Block var28 = worldObj.getBlockState(var26.offsetDown()).getBlock();
      
      if (((var28 instanceof BlockFence)) || ((var28 instanceof net.minecraft.block.BlockWall)) || ((var28 instanceof BlockFenceGate)))
      {
        var60 = var28;
        var26 = var26.offsetDown();
      }
    }
    
    func_180433_a(y, onGround, var60, var26);
    
    if (var13 != x)
    {
      motionX = 0.0D;
    }
    
    if (var17 != z)
    {
      motionZ = 0.0D;
    }
    
    if (var15 != y)
    {
      var60.onLanded(worldObj, this);
    }
    
    if ((canTriggerWalking()) && (!var19) && (ridingEntity == null))
    {
      double var61 = posX - var7;
      double var64 = posY - var9;
      double var66 = posZ - var11;
      
      if (var60 != Blocks.ladder)
      {
        var64 = 0.0D;
      }
      
      if ((var60 != null) && (onGround))
      {
        var60.onEntityCollidedWithBlock(worldObj, var26, this);
      }
      
      distanceWalkedModified = ((float)(distanceWalkedModified + MathHelper.sqrt_double(var61 * var61 + var66 * var66) * 0.6D));
      distanceWalkedOnStepModified = ((float)(distanceWalkedOnStepModified + MathHelper.sqrt_double(var61 * var61 + var64 * var64 + var66 * var66) * 0.6D));
      
      if ((distanceWalkedOnStepModified > nextStepDistance) && (var60.getMaterial() != Material.air))
      {
        nextStepDistance = ((int)distanceWalkedOnStepModified + 1);
        
        if (isInWater())
        {
          float var34 = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.35F;
          
          if (var34 > 1.0F)
          {
            var34 = 1.0F;
          }
          
          playSound(getSwimSound(), var34, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
        }
        
        func_180429_a(var26, var60);
      }
    }
    
    try
    {
      doBlockCollisions();
    }
    catch (Throwable var52)
    {
      CrashReport var63 = CrashReport.makeCrashReport(var52, "Checking entity block collision");
      CrashReportCategory var65 = var63.makeCategory("Entity being checked for collision");
      addEntityCrashInfo(var65);
      throw new ReportedException(var63);
    }
    boolean var62 = isWet();
    if (worldObj.func_147470_e(getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D)))
    {
      dealFireDamage(1);
      if (!var62)
      {
        fire += 1;
        if (fire == 0) {
          setFire(8);
        }
      }
    }
    else if (fire <= 0)
    {
      fire = (-fireResistance);
    }
    if ((var62) && (fire > 0))
    {
      playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
      fire = (-fireResistance);
    }
    worldObj.theProfiler.endSection();
    
    Slice.EVENT_POST_MOTION.setMotion(x, y, z);
    com.darkmagician6.eventapi.EventManager.call(Slice.EVENT_POST_MOTION);
    
    motionX = Slice.EVENT_POST_MOTION.getMotionX();
    motionY = Slice.EVENT_POST_MOTION.getMotionY();
    motionZ = Slice.EVENT_POST_MOTION.getMotionZ();
  }
  

  private void func_174829_m()
  {
    posX = ((getEntityBoundingBoxminX + getEntityBoundingBoxmaxX) / 2.0D);
    posY = getEntityBoundingBoxminY;
    posZ = ((getEntityBoundingBoxminZ + getEntityBoundingBoxmaxZ) / 2.0D);
  }
  
  protected String getSwimSound()
  {
    return "game.neutral.swim";
  }
  
  protected void doBlockCollisions()
  {
    BlockPos var1 = new BlockPos(getEntityBoundingBoxminX + 0.001D, getEntityBoundingBoxminY + 0.001D, getEntityBoundingBoxminZ + 0.001D);
    BlockPos var2 = new BlockPos(getEntityBoundingBoxmaxX - 0.001D, getEntityBoundingBoxmaxY - 0.001D, getEntityBoundingBoxmaxZ - 0.001D);
    
    if (worldObj.isAreaLoaded(var1, var2))
    {
      for (int var3 = var1.getX(); var3 <= var2.getX(); var3++)
      {
        for (int var4 = var1.getY(); var4 <= var2.getY(); var4++)
        {
          for (int var5 = var1.getZ(); var5 <= var2.getZ(); var5++)
          {
            BlockPos var6 = new BlockPos(var3, var4, var5);
            IBlockState var7 = worldObj.getBlockState(var6);
            
            try
            {
              var7.getBlock().onEntityCollidedWithBlock(worldObj, var6, var7, this);
            }
            catch (Throwable var11)
            {
              CrashReport var9 = CrashReport.makeCrashReport(var11, "Colliding entity with block");
              CrashReportCategory var10 = var9.makeCategory("Block being collided with");
              CrashReportCategory.addBlockInfo(var10, var6, var7);
              throw new ReportedException(var9);
            }
          }
        }
      }
    }
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    Block.SoundType var3 = stepSound;
    
    if (worldObj.getBlockState(p_180429_1_.offsetUp()).getBlock() == Blocks.snow_layer)
    {
      var3 = snow_layerstepSound;
      playSound(var3.getStepSound(), var3.getVolume() * 0.15F, var3.getFrequency());
    }
    else if (!p_180429_2_.getMaterial().isLiquid())
    {
      playSound(var3.getStepSound(), var3.getVolume() * 0.15F, var3.getFrequency());
    }
  }
  
  public void playSound(String name, float volume, float pitch)
  {
    if (!isSlient())
    {
      worldObj.playSoundAtEntity(this, name, volume, pitch);
    }
  }
  



  public boolean isSlient()
  {
    return dataWatcher.getWatchableObjectByte(4) == 1;
  }
  
  public void func_174810_b(boolean p_174810_1_)
  {
    dataWatcher.updateObject(4, Byte.valueOf((byte)(p_174810_1_ ? 1 : 0)));
  }
  




  protected boolean canTriggerWalking()
  {
    return true;
  }
  
  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_)
  {
    if (p_180433_3_)
    {
      if (fallDistance > 0.0F)
      {
        if (p_180433_4_ != null)
        {
          p_180433_4_.onFallenUpon(worldObj, p_180433_5_, this, fallDistance);
        }
        else
        {
          fall(fallDistance, 1.0F);
        }
        
        fallDistance = 0.0F;
      }
    }
    else if (p_180433_1_ < 0.0D)
    {
      fallDistance = ((float)(fallDistance - p_180433_1_));
    }
  }
  



  public AxisAlignedBB getBoundingBox()
  {
    return null;
  }
  




  protected void dealFireDamage(int amount)
  {
    if (!isImmuneToFire)
    {
      attackEntityFrom(DamageSource.inFire, amount);
    }
  }
  
  public final boolean isImmuneToFire()
  {
    return isImmuneToFire;
  }
  
  public void fall(float distance, float damageMultiplier)
  {
    if (riddenByEntity != null)
    {
      riddenByEntity.fall(distance, damageMultiplier);
    }
  }
  



  public boolean isWet()
  {
    return (inWater) || (worldObj.func_175727_C(new BlockPos(posX, posY, posZ))) || (worldObj.func_175727_C(new BlockPos(posX, posY + height, posZ)));
  }
  




  public boolean isInWater()
  {
    return inWater;
  }
  



  public boolean handleWaterMovement()
  {
    if (worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this))
    {
      if ((!inWater) && (!firstUpdate))
      {
        resetHeight();
      }
      
      fallDistance = 0.0F;
      inWater = true;
      fire = 0;
    }
    else
    {
      inWater = false;
    }
    
    return inWater;
  }
  



  protected void resetHeight()
  {
    float var1 = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
    
    if (var1 > 1.0F)
    {
      var1 = 1.0F;
    }
    
    playSound(getSplashSound(), var1, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
    float var2 = MathHelper.floor_double(getEntityBoundingBoxminY);
    



    for (int var3 = 0; var3 < 1.0F + width * 20.0F; var3++)
    {
      float var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
      float var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
      worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + var4, var2 + 1.0F, posZ + var5, motionX, motionY - rand.nextFloat() * 0.2F, motionZ, new int[0]);
    }
    
    for (var3 = 0; var3 < 1.0F + width * 20.0F; var3++)
    {
      float var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
      float var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
      worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + var4, var2 + 1.0F, posZ + var5, motionX, motionY, motionZ, new int[0]);
    }
  }
  
  public void func_174830_Y()
  {
    if ((isSprinting()) && (!isInWater()))
    {
      func_174808_Z();
    }
  }
  
  protected void func_174808_Z()
  {
    int var1 = MathHelper.floor_double(posX);
    int var2 = MathHelper.floor_double(posY - 0.20000000298023224D);
    int var3 = MathHelper.floor_double(posZ);
    BlockPos var4 = new BlockPos(var1, var2, var3);
    IBlockState var5 = worldObj.getBlockState(var4);
    Block var6 = var5.getBlock();
    
    if (var6.getRenderType() != -1)
    {
      worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX + (rand.nextFloat() - 0.5D) * width, getEntityBoundingBoxminY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width, -motionX * 4.0D, 1.5D, -motionZ * 4.0D, new int[] { Block.getStateId(var5) });
    }
  }
  
  protected String getSplashSound()
  {
    return "game.neutral.swim.splash";
  }
  



  public boolean isInsideOfMaterial(Material materialIn)
  {
    double var2 = posY + getEyeHeight();
    BlockPos var4 = new BlockPos(posX, var2, posZ);
    IBlockState var5 = worldObj.getBlockState(var4);
    Block var6 = var5.getBlock();
    
    if (var6.getMaterial() == materialIn)
    {
      float var7 = net.minecraft.block.BlockLiquid.getLiquidHeightPercent(var5.getBlock().getMetaFromState(var5)) - 0.11111111F;
      float var8 = var4.getY() + 1 - var7;
      boolean var9 = var2 < var8;
      return (!var9) && ((this instanceof EntityPlayer)) ? false : var9;
    }
    

    return false;
  }
  

  public boolean func_180799_ab()
  {
    return worldObj.isMaterialInBB(getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
  }
  



  public void moveFlying(float strafe, float forward, float friction)
  {
    float var4 = strafe * strafe + forward * forward;
    
    if (var4 >= 1.0E-4F)
    {
      var4 = MathHelper.sqrt_float(var4);
      
      if (var4 < 1.0F)
      {
        var4 = 1.0F;
      }
      
      var4 = friction / var4;
      strafe *= var4;
      forward *= var4;
      float var5 = MathHelper.sin(rotationYaw * 3.1415927F / 180.0F);
      float var6 = MathHelper.cos(rotationYaw * 3.1415927F / 180.0F);
      motionX += strafe * var6 - forward * var5;
      motionZ += forward * var6 + strafe * var5;
    }
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    BlockPos var2 = new BlockPos(posX, 0.0D, posZ);
    
    if (worldObj.isBlockLoaded(var2))
    {
      double var3 = (getEntityBoundingBoxmaxY - getEntityBoundingBoxminY) * 0.66D;
      int var5 = MathHelper.floor_double(posY + var3);
      return worldObj.getCombinedLight(var2.offsetUp(var5), 0);
    }
    

    return 0;
  }
  




  public float getBrightness(float p_70013_1_)
  {
    BlockPos var2 = new BlockPos(posX, 0.0D, posZ);
    
    if (worldObj.isBlockLoaded(var2))
    {
      double var3 = (getEntityBoundingBoxmaxY - getEntityBoundingBoxminY) * 0.66D;
      int var5 = MathHelper.floor_double(posY + var3);
      return worldObj.getLightBrightness(var2.offsetUp(var5));
    }
    

    return 0.0F;
  }
  




  public void setWorld(World worldIn)
  {
    worldObj = worldIn;
  }
  



  public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch)
  {
    prevPosX = (this.posX = x);
    prevPosY = (this.posY = y);
    prevPosZ = (this.posZ = z);
    prevRotationYaw = (this.rotationYaw = yaw);
    prevRotationPitch = (this.rotationPitch = pitch);
    double var9 = prevRotationYaw - yaw;
    
    if (var9 < -180.0D)
    {
      prevRotationYaw += 360.0F;
    }
    
    if (var9 >= 180.0D)
    {
      prevRotationYaw -= 360.0F;
    }
    
    setPosition(posX, posY, posZ);
    setRotation(yaw, pitch);
  }
  
  public void func_174828_a(BlockPos p_174828_1_, float p_174828_2_, float p_174828_3_)
  {
    setLocationAndAngles(p_174828_1_.getX() + 0.5D, p_174828_1_.getY(), p_174828_1_.getZ() + 0.5D, p_174828_2_, p_174828_3_);
  }
  



  public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
  {
    lastTickPosX = (this.prevPosX = this.posX = x);
    lastTickPosY = (this.prevPosY = this.posY = y);
    lastTickPosZ = (this.prevPosZ = this.posZ = z);
    rotationYaw = yaw;
    rotationPitch = pitch;
    setPosition(posX, posY, posZ);
  }
  



  public float getDistanceToEntity(Entity entityIn)
  {
    float var2 = (float)(posX - posX);
    float var3 = (float)(posY - posY);
    float var4 = (float)(posZ - posZ);
    return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
  }
  



  public double getDistanceSq(double x, double y, double z)
  {
    double var7 = posX - x;
    double var9 = posY - y;
    double var11 = posZ - z;
    return var7 * var7 + var9 * var9 + var11 * var11;
  }
  
  public double getDistanceSq(BlockPos p_174818_1_)
  {
    return p_174818_1_.distanceSq(posX, posY, posZ);
  }
  
  public double func_174831_c(BlockPos p_174831_1_)
  {
    return p_174831_1_.distanceSqToCenter(posX, posY, posZ);
  }
  



  public double getDistance(double x, double y, double z)
  {
    double var7 = posX - x;
    double var9 = posY - y;
    double var11 = posZ - z;
    return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
  }
  



  public double getDistanceSqToEntity(Entity entityIn)
  {
    double var2 = posX - posX;
    double var4 = posY - posY;
    double var6 = posZ - posZ;
    return var2 * var2 + var4 * var4 + var6 * var6;
  }
  



  public void onCollideWithPlayer(EntityPlayer entityIn) {}
  



  public void applyEntityCollision(Entity entityIn)
  {
    if ((riddenByEntity != this) && (ridingEntity != this))
    {
      if ((!noClip) && (!noClip))
      {
        double var2 = posX - posX;
        double var4 = posZ - posZ;
        double var6 = MathHelper.abs_max(var2, var4);
        
        if (var6 >= 0.009999999776482582D)
        {
          var6 = MathHelper.sqrt_double(var6);
          var2 /= var6;
          var4 /= var6;
          double var8 = 1.0D / var6;
          
          if (var8 > 1.0D)
          {
            var8 = 1.0D;
          }
          
          var2 *= var8;
          var4 *= var8;
          var2 *= 0.05000000074505806D;
          var4 *= 0.05000000074505806D;
          var2 *= (1.0F - entityCollisionReduction);
          var4 *= (1.0F - entityCollisionReduction);
          
          if (riddenByEntity == null)
          {
            addVelocity(-var2, 0.0D, -var4);
          }
          
          if (riddenByEntity == null)
          {
            entityIn.addVelocity(var2, 0.0D, var4);
          }
        }
      }
    }
  }
  



  public void addVelocity(double x, double y, double z)
  {
    motionX += x;
    motionY += y;
    motionZ += z;
    isAirBorne = true;
  }
  



  protected void setBeenAttacked()
  {
    velocityChanged = true;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    setBeenAttacked();
    return false;
  }
  




  public Vec3 getLook(float p_70676_1_)
  {
    if (p_70676_1_ == 1.0F)
    {
      return func_174806_f(rotationPitch, rotationYaw);
    }
    

    float var2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * p_70676_1_;
    float var3 = prevRotationYaw + (rotationYaw - prevRotationYaw) * p_70676_1_;
    return func_174806_f(var2, var3);
  }
  

  protected final Vec3 func_174806_f(float p_174806_1_, float p_174806_2_)
  {
    float var3 = MathHelper.cos(-p_174806_2_ * 0.017453292F - 3.1415927F);
    float var4 = MathHelper.sin(-p_174806_2_ * 0.017453292F - 3.1415927F);
    float var5 = -MathHelper.cos(-p_174806_1_ * 0.017453292F);
    float var6 = MathHelper.sin(-p_174806_1_ * 0.017453292F);
    return new Vec3(var4 * var5, var6, var3 * var5);
  }
  
  public Vec3 func_174824_e(float p_174824_1_)
  {
    if (p_174824_1_ == 1.0F)
    {
      return new Vec3(posX, posY + getEyeHeight(), posZ);
    }
    

    double var2 = prevPosX + (posX - prevPosX) * p_174824_1_;
    double var4 = prevPosY + (posY - prevPosY) * p_174824_1_ + getEyeHeight();
    double var6 = prevPosZ + (posZ - prevPosZ) * p_174824_1_;
    return new Vec3(var2, var4, var6);
  }
  

  public MovingObjectPosition func_174822_a(double p_174822_1_, float p_174822_3_)
  {
    Vec3 var4 = func_174824_e(p_174822_3_);
    Vec3 var5 = getLook(p_174822_3_);
    Vec3 var6 = var4.addVector(xCoord * p_174822_1_, yCoord * p_174822_1_, zCoord * p_174822_1_);
    return worldObj.rayTraceBlocks(var4, var6, false, false, true);
  }
  



  public boolean canBeCollidedWith()
  {
    return false;
  }
  



  public boolean canBePushed()
  {
    return false;
  }
  


  public void addToPlayerScore(Entity entityIn, int amount) {}
  


  public boolean isInRangeToRender3d(double x, double y, double z)
  {
    double var7 = posX - x;
    double var9 = posY - y;
    double var11 = posZ - z;
    double var13 = var7 * var7 + var9 * var9 + var11 * var11;
    return isInRangeToRenderDist(var13);
  }
  




  public boolean isInRangeToRenderDist(double distance)
  {
    double var3 = getEntityBoundingBox().getAverageEdgeLength();
    var3 *= 64.0D * renderDistanceWeight;
    return distance < var3 * var3;
  }
  




  public boolean writeMountToNBT(NBTTagCompound tagCompund)
  {
    String var2 = getEntityString();
    
    if ((!isDead) && (var2 != null))
    {
      tagCompund.setString("id", var2);
      writeToNBT(tagCompund);
      return true;
    }
    

    return false;
  }
  






  public boolean writeToNBTOptional(NBTTagCompound tagCompund)
  {
    String var2 = getEntityString();
    
    if ((!isDead) && (var2 != null) && (riddenByEntity == null))
    {
      tagCompund.setString("id", var2);
      writeToNBT(tagCompund);
      return true;
    }
    

    return false;
  }
  




  public void writeToNBT(NBTTagCompound tagCompund)
  {
    try
    {
      tagCompund.setTag("Pos", newDoubleNBTList(new double[] { posX, posY, posZ }));
      tagCompund.setTag("Motion", newDoubleNBTList(new double[] { motionX, motionY, motionZ }));
      tagCompund.setTag("Rotation", newFloatNBTList(new float[] { rotationYaw, rotationPitch }));
      tagCompund.setFloat("FallDistance", fallDistance);
      tagCompund.setShort("Fire", (short)fire);
      tagCompund.setShort("Air", (short)getAir());
      tagCompund.setBoolean("OnGround", onGround);
      tagCompund.setInteger("Dimension", dimension);
      tagCompund.setBoolean("Invulnerable", invulnerable);
      tagCompund.setInteger("PortalCooldown", timeUntilPortal);
      tagCompund.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
      tagCompund.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
      
      if ((getCustomNameTag() != null) && (getCustomNameTag().length() > 0))
      {
        tagCompund.setString("CustomName", getCustomNameTag());
        tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
      }
      
      field_174837_as.func_179670_b(tagCompund);
      
      if (isSlient())
      {
        tagCompund.setBoolean("Silent", isSlient());
      }
      
      writeEntityToNBT(tagCompund);
      
      if (ridingEntity != null)
      {
        NBTTagCompound var2 = new NBTTagCompound();
        
        if (ridingEntity.writeMountToNBT(var2))
        {
          tagCompund.setTag("Riding", var2);
        }
      }
    }
    catch (Throwable var5)
    {
      CrashReport var3 = CrashReport.makeCrashReport(var5, "Saving entity NBT");
      CrashReportCategory var4 = var3.makeCategory("Entity being saved");
      addEntityCrashInfo(var4);
      throw new ReportedException(var3);
    }
  }
  



  public void readFromNBT(NBTTagCompound tagCompund)
  {
    try
    {
      NBTTagList var2 = tagCompund.getTagList("Pos", 6);
      NBTTagList var6 = tagCompund.getTagList("Motion", 6);
      NBTTagList var7 = tagCompund.getTagList("Rotation", 5);
      motionX = var6.getDouble(0);
      motionY = var6.getDouble(1);
      motionZ = var6.getDouble(2);
      
      if (Math.abs(motionX) > 10.0D)
      {
        motionX = 0.0D;
      }
      
      if (Math.abs(motionY) > 10.0D)
      {
        motionY = 0.0D;
      }
      
      if (Math.abs(motionZ) > 10.0D)
      {
        motionZ = 0.0D;
      }
      
      prevPosX = (this.lastTickPosX = this.posX = var2.getDouble(0));
      prevPosY = (this.lastTickPosY = this.posY = var2.getDouble(1));
      prevPosZ = (this.lastTickPosZ = this.posZ = var2.getDouble(2));
      prevRotationYaw = (this.rotationYaw = var7.getFloat(0));
      prevRotationPitch = (this.rotationPitch = var7.getFloat(1));
      fallDistance = tagCompund.getFloat("FallDistance");
      fire = tagCompund.getShort("Fire");
      setAir(tagCompund.getShort("Air"));
      onGround = tagCompund.getBoolean("OnGround");
      dimension = tagCompund.getInteger("Dimension");
      invulnerable = tagCompund.getBoolean("Invulnerable");
      timeUntilPortal = tagCompund.getInteger("PortalCooldown");
      
      if ((tagCompund.hasKey("UUIDMost", 4)) && (tagCompund.hasKey("UUIDLeast", 4)))
      {
        entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
      }
      else if (tagCompund.hasKey("UUID", 8))
      {
        entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
      }
      
      setPosition(posX, posY, posZ);
      setRotation(rotationYaw, rotationPitch);
      
      if ((tagCompund.hasKey("CustomName", 8)) && (tagCompund.getString("CustomName").length() > 0))
      {
        setCustomNameTag(tagCompund.getString("CustomName"));
      }
      
      setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
      field_174837_as.func_179668_a(tagCompund);
      func_174810_b(tagCompund.getBoolean("Silent"));
      readEntityFromNBT(tagCompund);
      
      if (shouldSetPosAfterLoading())
      {
        setPosition(posX, posY, posZ);
      }
    }
    catch (Throwable var5)
    {
      CrashReport var3 = CrashReport.makeCrashReport(var5, "Loading entity NBT");
      CrashReportCategory var4 = var3.makeCategory("Entity being loaded");
      addEntityCrashInfo(var4);
      throw new ReportedException(var3);
    }
  }
  
  protected boolean shouldSetPosAfterLoading()
  {
    return true;
  }
  



  protected final String getEntityString()
  {
    return EntityList.getEntityString(this);
  }
  



  protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
  


  protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
  


  public void onChunkLoad() {}
  


  protected NBTTagList newDoubleNBTList(double... numbers)
  {
    NBTTagList var2 = new NBTTagList();
    double[] var3 = numbers;
    int var4 = numbers.length;
    
    for (int var5 = 0; var5 < var4; var5++)
    {
      double var6 = var3[var5];
      var2.appendTag(new net.minecraft.nbt.NBTTagDouble(var6));
    }
    
    return var2;
  }
  



  protected NBTTagList newFloatNBTList(float... numbers)
  {
    NBTTagList var2 = new NBTTagList();
    float[] var3 = numbers;
    int var4 = numbers.length;
    
    for (int var5 = 0; var5 < var4; var5++)
    {
      float var6 = var3[var5];
      var2.appendTag(new net.minecraft.nbt.NBTTagFloat(var6));
    }
    
    return var2;
  }
  
  public EntityItem dropItem(Item itemIn, int size)
  {
    return dropItemWithOffset(itemIn, size, 0.0F);
  }
  
  public EntityItem dropItemWithOffset(Item itemIn, int size, float p_145778_3_)
  {
    return entityDropItem(new ItemStack(itemIn, size, 0), p_145778_3_);
  }
  



  public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY)
  {
    if ((stackSize != 0) && (itemStackIn.getItem() != null))
    {
      EntityItem var3 = new EntityItem(worldObj, posX, posY + offsetY, posZ, itemStackIn);
      var3.setDefaultPickupDelay();
      worldObj.spawnEntityInWorld(var3);
      return var3;
    }
    

    return null;
  }
  




  public boolean isEntityAlive()
  {
    return !isDead;
  }
  



  public boolean isEntityInsideOpaqueBlock()
  {
    if (noClip)
    {
      return false;
    }
    

    for (int var1 = 0; var1 < 8; var1++)
    {
      double var2 = posX + ((var1 >> 0) % 2 - 0.5F) * width * 0.8F;
      double var4 = posY + ((var1 >> 1) % 2 - 0.5F) * 0.1F;
      double var6 = posZ + ((var1 >> 2) % 2 - 0.5F) * width * 0.8F;
      
      if (worldObj.getBlockState(new BlockPos(var2, var4 + getEyeHeight(), var6)).getBlock().isVisuallyOpaque())
      {
        return true;
      }
    }
    
    return false;
  }
  




  public boolean interactFirst(EntityPlayer playerIn)
  {
    return false;
  }
  




  public AxisAlignedBB getCollisionBox(Entity entityIn)
  {
    return null;
  }
  



  public void updateRidden()
  {
    if (ridingEntity.isDead)
    {
      ridingEntity = null;
    }
    else
    {
      motionX = 0.0D;
      motionY = 0.0D;
      motionZ = 0.0D;
      onUpdate();
      
      if (ridingEntity != null)
      {
        ridingEntity.updateRiderPosition();
        entityRiderYawDelta += ridingEntity.rotationYaw - ridingEntity.prevRotationYaw;
        
        for (entityRiderPitchDelta += ridingEntity.rotationPitch - ridingEntity.prevRotationPitch; entityRiderYawDelta >= 180.0D; entityRiderYawDelta -= 360.0D) {}
        



        while (entityRiderYawDelta < -180.0D)
        {
          entityRiderYawDelta += 360.0D;
        }
        
        while (entityRiderPitchDelta >= 180.0D)
        {
          entityRiderPitchDelta -= 360.0D;
        }
        
        while (entityRiderPitchDelta < -180.0D)
        {
          entityRiderPitchDelta += 360.0D;
        }
        
        double var1 = entityRiderYawDelta * 0.5D;
        double var3 = entityRiderPitchDelta * 0.5D;
        float var5 = 10.0F;
        
        if (var1 > var5)
        {
          var1 = var5;
        }
        
        if (var1 < -var5)
        {
          var1 = -var5;
        }
        
        if (var3 > var5)
        {
          var3 = var5;
        }
        
        if (var3 < -var5)
        {
          var3 = -var5;
        }
        
        entityRiderYawDelta -= var1;
        entityRiderPitchDelta -= var3;
      }
    }
  }
  
  public void updateRiderPosition()
  {
    if (riddenByEntity != null)
    {
      riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
    }
  }
  



  public double getYOffset()
  {
    return 0.0D;
  }
  



  public double getMountedYOffset()
  {
    return height * 0.75D;
  }
  



  public void mountEntity(Entity entityIn)
  {
    entityRiderPitchDelta = 0.0D;
    entityRiderYawDelta = 0.0D;
    
    if (entityIn == null)
    {
      if (ridingEntity != null)
      {
        setLocationAndAngles(ridingEntity.posX, ridingEntity.getEntityBoundingBox().minY + ridingEntity.height, ridingEntity.posZ, rotationYaw, rotationPitch);
        ridingEntity.riddenByEntity = null;
      }
      
      ridingEntity = null;
    }
    else
    {
      if (ridingEntity != null)
      {
        ridingEntity.riddenByEntity = null;
      }
      
      if (entityIn != null)
      {
        for (Entity var2 = ridingEntity; var2 != null; var2 = ridingEntity)
        {
          if (var2 == this)
          {
            return;
          }
        }
      }
      
      ridingEntity = entityIn;
      riddenByEntity = this;
    }
  }
  
  public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
  {
    setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
    setRotation(p_180426_7_, p_180426_8_);
    List var11 = worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().contract(0.03125D, 0.0D, 0.03125D));
    
    if (!var11.isEmpty())
    {
      double var12 = 0.0D;
      Iterator var14 = var11.iterator();
      
      while (var14.hasNext())
      {
        AxisAlignedBB var15 = (AxisAlignedBB)var14.next();
        
        if (maxY > var12)
        {
          var12 = maxY;
        }
      }
      
      p_180426_3_ += var12 - getEntityBoundingBoxminY;
      setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
    }
  }
  
  public float getCollisionBorderSize()
  {
    return 0.1F;
  }
  



  public Vec3 getLookVec()
  {
    return null;
  }
  



  public void setInPortal()
  {
    if (timeUntilPortal > 0)
    {
      timeUntilPortal = getPortalCooldown();
    }
    else
    {
      double var1 = prevPosX - posX;
      double var3 = prevPosZ - posZ;
      
      if ((!worldObj.isRemote) && (!inPortal))
      {
        int var5;
        int var5;
        if (MathHelper.abs((float)var1) > MathHelper.abs((float)var3))
        {
          var5 = var1 > 0.0D ? EnumFacing.WEST.getHorizontalIndex() : EnumFacing.EAST.getHorizontalIndex();
        }
        else
        {
          var5 = var3 > 0.0D ? EnumFacing.NORTH.getHorizontalIndex() : EnumFacing.SOUTH.getHorizontalIndex();
        }
        
        teleportDirection = var5;
      }
      
      inPortal = true;
    }
  }
  



  public int getPortalCooldown()
  {
    return 300;
  }
  



  public void setVelocity(double x, double y, double z)
  {
    motionX = x;
    motionY = y;
    motionZ = z;
  }
  


  public void handleHealthUpdate(byte p_70103_1_) {}
  


  public void performHurtAnimation() {}
  


  public ItemStack[] getInventory()
  {
    return null;
  }
  



  public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn) {}
  



  public boolean isBurning()
  {
    boolean var1 = (worldObj != null) && (worldObj.isRemote);
    return (!isImmuneToFire) && ((fire > 0) || ((var1) && (getFlag(0))));
  }
  




  public boolean isRiding()
  {
    return ridingEntity != null;
  }
  



  public boolean isSneaking()
  {
    return getFlag(1);
  }
  



  public void setSneaking(boolean sneaking)
  {
    setFlag(1, sneaking);
  }
  



  public boolean isSprinting()
  {
    return getFlag(3);
  }
  



  public void setSprinting(boolean sprinting)
  {
    setFlag(3, sprinting);
  }
  
  public boolean isInvisible()
  {
    return getFlag(5);
  }
  





  public boolean isInvisibleToPlayer(EntityPlayer playerIn)
  {
    return playerIn.func_175149_v() ? false : isInvisible();
  }
  
  public void setInvisible(boolean invisible)
  {
    setFlag(5, invisible);
  }
  
  public boolean isEating()
  {
    return getFlag(4);
  }
  
  public void setEating(boolean eating)
  {
    setFlag(4, eating);
  }
  




  protected boolean getFlag(int flag)
  {
    return (dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0;
  }
  



  protected void setFlag(int flag, boolean set)
  {
    byte var3 = dataWatcher.getWatchableObjectByte(0);
    
    if (set)
    {
      dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 | 1 << flag)));
    }
    else
    {
      dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 & (1 << flag ^ 0xFFFFFFFF))));
    }
  }
  
  public int getAir()
  {
    return dataWatcher.getWatchableObjectShort(1);
  }
  
  public void setAir(int air)
  {
    dataWatcher.updateObject(1, Short.valueOf((short)air));
  }
  



  public void onStruckByLightning(EntityLightningBolt lightningBolt)
  {
    attackEntityFrom(DamageSource.field_180137_b, 5.0F);
    fire += 1;
    
    if (fire == 0)
    {
      setFire(8);
    }
  }
  


  public void onKillEntity(EntityLivingBase entityLivingIn) {}
  

  protected boolean pushOutOfBlocks(double x, double y, double z)
  {
    BlockPos var7 = new BlockPos(x, y, z);
    double var8 = x - var7.getX();
    double var10 = y - var7.getY();
    double var12 = z - var7.getZ();
    List var14 = worldObj.func_147461_a(getEntityBoundingBox());
    
    if ((var14.isEmpty()) && (!worldObj.func_175665_u(var7)))
    {
      return false;
    }
    

    byte var15 = 3;
    double var16 = 9999.0D;
    
    if ((!worldObj.func_175665_u(var7.offsetWest())) && (var8 < var16))
    {
      var16 = var8;
      var15 = 0;
    }
    
    if ((!worldObj.func_175665_u(var7.offsetEast())) && (1.0D - var8 < var16))
    {
      var16 = 1.0D - var8;
      var15 = 1;
    }
    
    if ((!worldObj.func_175665_u(var7.offsetUp())) && (1.0D - var10 < var16))
    {
      var16 = 1.0D - var10;
      var15 = 3;
    }
    
    if ((!worldObj.func_175665_u(var7.offsetNorth())) && (var12 < var16))
    {
      var16 = var12;
      var15 = 4;
    }
    
    if ((!worldObj.func_175665_u(var7.offsetSouth())) && (1.0D - var12 < var16))
    {
      var16 = 1.0D - var12;
      var15 = 5;
    }
    
    float var18 = rand.nextFloat() * 0.2F + 0.1F;
    
    if (var15 == 0)
    {
      motionX = (-var18);
    }
    
    if (var15 == 1)
    {
      motionX = var18;
    }
    
    if (var15 == 3)
    {
      motionY = var18;
    }
    
    if (var15 == 4)
    {
      motionZ = (-var18);
    }
    
    if (var15 == 5)
    {
      motionZ = var18;
    }
    
    return true;
  }
  




  public void setInWeb()
  {
    isInWeb = true;
    fallDistance = 0.0F;
  }
  



  public String getName()
  {
    if (hasCustomName())
    {
      return getCustomNameTag();
    }
    

    String var1 = EntityList.getEntityString(this);
    
    if (var1 == null)
    {
      var1 = "generic";
    }
    
    return StatCollector.translateToLocal("entity." + var1 + ".name");
  }
  




  public Entity[] getParts()
  {
    return null;
  }
  



  public boolean isEntityEqual(Entity entityIn)
  {
    return this == entityIn;
  }
  
  public float getRotationYawHead()
  {
    return 0.0F;
  }
  



  public void setRotationYawHead(float rotation) {}
  



  public boolean canAttackWithItem()
  {
    return true;
  }
  



  public boolean hitByEntity(Entity entityIn)
  {
    return false;
  }
  
  public String toString()
  {
    return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(entityId), worldObj == null ? "~NULL~" : worldObj.getWorldInfo().getWorldName(), Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) });
  }
  
  public boolean func_180431_b(DamageSource p_180431_1_)
  {
    return (invulnerable) && (p_180431_1_ != DamageSource.outOfWorld) && (!p_180431_1_.func_180136_u());
  }
  



  public void copyLocationAndAnglesFrom(Entity entityIn)
  {
    setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
  }
  
  public void func_180432_n(Entity p_180432_1_)
  {
    NBTTagCompound var2 = new NBTTagCompound();
    p_180432_1_.writeToNBT(var2);
    readFromNBT(var2);
    timeUntilPortal = timeUntilPortal;
    teleportDirection = teleportDirection;
  }
  



  public void travelToDimension(int dimensionId)
  {
    if ((!worldObj.isRemote) && (!isDead))
    {
      worldObj.theProfiler.startSection("changeDimension");
      MinecraftServer var2 = MinecraftServer.getServer();
      int var3 = dimension;
      WorldServer var4 = var2.worldServerForDimension(var3);
      WorldServer var5 = var2.worldServerForDimension(dimensionId);
      dimension = dimensionId;
      
      if ((var3 == 1) && (dimensionId == 1))
      {
        var5 = var2.worldServerForDimension(0);
        dimension = 0;
      }
      
      worldObj.removeEntity(this);
      isDead = false;
      worldObj.theProfiler.startSection("reposition");
      var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
      worldObj.theProfiler.endStartSection("reloading");
      Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
      
      if (var6 != null)
      {
        var6.func_180432_n(this);
        
        if ((var3 == 1) && (dimensionId == 1))
        {
          BlockPos var7 = worldObj.func_175672_r(var5.getSpawnPoint());
          var6.func_174828_a(var7, rotationYaw, rotationPitch);
        }
        
        var5.spawnEntityInWorld(var6);
      }
      
      isDead = true;
      worldObj.theProfiler.endSection();
      var4.resetUpdateEntityTick();
      var5.resetUpdateEntityTick();
      worldObj.theProfiler.endSection();
    }
  }
  



  public float getExplosionResistance(Explosion p_180428_1_, World worldIn, BlockPos p_180428_3_, IBlockState p_180428_4_)
  {
    return p_180428_4_.getBlock().getExplosionResistance(this);
  }
  
  public boolean func_174816_a(Explosion p_174816_1_, World worldIn, BlockPos p_174816_3_, IBlockState p_174816_4_, float p_174816_5_)
  {
    return true;
  }
  



  public int getMaxFallHeight()
  {
    return 3;
  }
  
  public int getTeleportDirection()
  {
    return teleportDirection;
  }
  



  public boolean doesEntityNotTriggerPressurePlate()
  {
    return false;
  }
  
  public void addEntityCrashInfo(CrashReportCategory category)
  {
    category.addCrashSectionCallable("Entity Type", new Callable()
    {
      private static final String __OBFID = "CL_00001534";
      
      public String call() {
        return EntityList.getEntityString(Entity.this) + " (" + getClass().getCanonicalName() + ")";
      }
    });
    category.addCrashSection("Entity ID", Integer.valueOf(entityId));
    category.addCrashSectionCallable("Entity Name", new Callable()
    {
      private static final String __OBFID = "CL_00001535";
      
      public String call() {
        return getName();
      }
    });
    category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) }));
    category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
    category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(motionX), Double.valueOf(motionY), Double.valueOf(motionZ) }));
    category.addCrashSectionCallable("Entity's Rider", new Callable()
    {
      private static final String __OBFID = "CL_00002259";
      
      public String func_180118_a() {
        return riddenByEntity.toString();
      }
      
      public Object call() {
        return func_180118_a();
      }
    });
    category.addCrashSectionCallable("Entity's Vehicle", new Callable()
    {
      private static final String __OBFID = "CL_00002258";
      
      public String func_180116_a() {
        return ridingEntity.toString();
      }
      
      public Object call() {
        return func_180116_a();
      }
    });
  }
  



  public boolean canRenderOnFire()
  {
    return isBurning();
  }
  
  public UUID getUniqueID()
  {
    return entityUniqueID;
  }
  
  public boolean isPushedByWater()
  {
    return true;
  }
  
  public IChatComponent getDisplayName()
  {
    ChatComponentText var1 = new ChatComponentText(getName());
    var1.getChatStyle().setChatHoverEvent(func_174823_aP());
    var1.getChatStyle().setInsertion(getUniqueID().toString());
    return var1;
  }
  



  public void setCustomNameTag(String p_96094_1_)
  {
    dataWatcher.updateObject(2, p_96094_1_);
  }
  
  public String getCustomNameTag()
  {
    return dataWatcher.getWatchableObjectString(2);
  }
  



  public boolean hasCustomName()
  {
    return dataWatcher.getWatchableObjectString(2).length() > 0;
  }
  
  public void setAlwaysRenderNameTag(boolean p_174805_1_)
  {
    dataWatcher.updateObject(3, Byte.valueOf((byte)(p_174805_1_ ? 1 : 0)));
  }
  
  public boolean getAlwaysRenderNameTag()
  {
    return dataWatcher.getWatchableObjectByte(3) == 1;
  }
  



  public void setPositionAndUpdate(double p_70634_1_, double p_70634_3_, double p_70634_5_)
  {
    setLocationAndAngles(p_70634_1_, p_70634_3_, p_70634_5_, rotationYaw, rotationPitch);
  }
  
  public boolean getAlwaysRenderNameTagForRender()
  {
    return getAlwaysRenderNameTag();
  }
  
  public void func_145781_i(int p_145781_1_) {}
  
  public EnumFacing func_174811_aO()
  {
    return EnumFacing.getHorizontal(MathHelper.floor_double(rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3);
  }
  
  protected HoverEvent func_174823_aP()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    String var2 = EntityList.getEntityString(this);
    var1.setString("id", getUniqueID().toString());
    
    if (var2 != null)
    {
      var1.setString("type", var2);
    }
    
    var1.setString("name", getName());
    return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(var1.toString()));
  }
  
  public boolean func_174827_a(EntityPlayerMP p_174827_1_)
  {
    return true;
  }
  
  public AxisAlignedBB getEntityBoundingBox()
  {
    return boundingBox;
  }
  
  public void func_174826_a(AxisAlignedBB p_174826_1_)
  {
    boundingBox = p_174826_1_;
  }
  
  public float getEyeHeight()
  {
    return height * 0.85F;
  }
  
  public boolean isOutsideBorder()
  {
    return isOutsideBorder;
  }
  
  public void setOutsideBorder(boolean p_174821_1_)
  {
    isOutsideBorder = p_174821_1_;
  }
  
  public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_)
  {
    return false;
  }
  





  public void addChatMessage(IChatComponent message) {}
  




  public boolean canCommandSenderUseCommand(int permissionLevel, String command)
  {
    return true;
  }
  
  public BlockPos getPosition()
  {
    return new BlockPos(posX, posY + 0.5D, posZ);
  }
  
  public Vec3 getPositionVector()
  {
    return new Vec3(posX, posY, posZ);
  }
  
  public World getEntityWorld()
  {
    return worldObj;
  }
  
  public Entity getCommandSenderEntity()
  {
    return this;
  }
  
  public boolean sendCommandFeedback()
  {
    return false;
  }
  
  public void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_)
  {
    field_174837_as.func_179672_a(this, p_174794_1_, p_174794_2_);
  }
  
  public CommandResultStats func_174807_aT()
  {
    return field_174837_as;
  }
  
  public void func_174817_o(Entity p_174817_1_)
  {
    field_174837_as.func_179671_a(p_174817_1_.func_174807_aT());
  }
  
  public NBTTagCompound func_174819_aU()
  {
    return null;
  }
  
  public void func_174834_g(NBTTagCompound p_174834_1_) {}
  
  public boolean func_174825_a(EntityPlayer p_174825_1_, Vec3 p_174825_2_)
  {
    return false;
  }
  
  public boolean func_180427_aV()
  {
    return false;
  }
  
  protected void func_174815_a(EntityLivingBase p_174815_1_, Entity p_174815_2_)
  {
    if ((p_174815_2_ instanceof EntityLivingBase))
    {
      EnchantmentHelper.func_151384_a((EntityLivingBase)p_174815_2_, p_174815_1_);
    }
    
    EnchantmentHelper.func_151385_b(p_174815_1_, p_174815_2_);
  }
  
  public String getCommandSenderName()
  {
    if (hasCustomName()) {
      return getCustomNameTag();
    }
    String var1 = EntityList.getEntityString(this);
    if (var1 == null) {
      var1 = "generic";
    }
    return StatCollector.translateToLocal("entity." + var1 + ".name");
  }
}
