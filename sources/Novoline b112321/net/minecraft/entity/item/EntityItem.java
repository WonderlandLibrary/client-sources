package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem extends Entity {
   private static final Logger LOGGER = LogManager.getLogger();
   private int age;
   private int delayBeforeCanPickup;
   private int health;
   private String thrower;
   private String owner;
   public float hoverStart;

   public EntityItem(World var1, double var2, double var4, double var6) {
      super(var1);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.setSize(0.25F, 0.25F);
      this.setPosition(var2, var4, var6);
      this.rotationYaw = (float)(Math.random() * 360.0D);
      this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
      this.motionY = 0.20000000298023224D;
      this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
   }

   public EntityItem(World var1, double var2, double var4, double var6, ItemStack var8) {
      this(var1, var2, var4, var6);
      this.setEntityItemStack(var8);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public EntityItem(World var1) {
      super(var1);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.setSize(0.25F, 0.25F);
      this.setEntityItemStack(new ItemStack(Blocks.air, 0));
   }

   protected void entityInit() {
      this.k().a(10, 5);
   }

   public void onUpdate() {
      if(this.getEntityItem() == null) {
         this.setDead();
      } else {
         super.onUpdate();
         if(this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767) {
            --this.delayBeforeCanPickup;
         }

         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.motionY -= 0.03999999910593033D;
         this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         if((int)this.prevPosX == (int)this.posX && (int)this.prevPosY == (int)this.posY && (int)this.prevPosZ == (int)this.posZ) {
            boolean var3 = false;
         } else {
            boolean var10000 = true;
         }

         if(this.ticksExisted % 25 == 0) {
            if(this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
               this.motionY = 0.20000000298023224D;
               this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
               this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
               this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
            }

            if(!this.worldObj.isRemote) {
               this.searchForOtherItemsNearby();
            }
         }

         float var2 = 0.98F;
         if(this.onGround) {
            var2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98F;
         }

         this.motionX *= (double)var2;
         this.motionY *= 0.9800000190734863D;
         this.motionZ *= (double)var2;
         if(this.onGround) {
            this.motionY *= -0.5D;
         }

         if(this.age != -32768) {
            ++this.age;
         }

         this.handleWaterMovement();
         if(!this.worldObj.isRemote && this.age >= 6000) {
            this.setDead();
         }
      }

   }

   private void searchForOtherItemsNearby() {
      for(EntityItem var2 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D))) {
         this.combineItems(var2);
      }

   }

   private boolean combineItems(EntityItem var1) {
      if(var1 == this) {
         return false;
      } else if(var1.isEntityAlive() && this.isEntityAlive()) {
         ItemStack var2 = this.getEntityItem();
         ItemStack var3 = var1.getEntityItem();
         if(this.delayBeforeCanPickup != 32767 && var1.delayBeforeCanPickup != 32767) {
            if(this.age != -32768 && var1.age != -32768) {
               if(var3.getItem() != var2.getItem()) {
                  return false;
               } else if(var3.hasTagCompound() ^ var2.hasTagCompound()) {
                  return false;
               } else if(var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
                  return false;
               } else if(var3.getItem() == null) {
                  return false;
               } else if(var3.getItem().getHasSubtypes() && var3.getMetadata() != var2.getMetadata()) {
                  return false;
               } else if(var3.stackSize < var2.stackSize) {
                  return var1.combineItems(this);
               } else if(var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
                  return false;
               } else {
                  var3.stackSize += var2.stackSize;
                  var1.delayBeforeCanPickup = Math.max(var1.delayBeforeCanPickup, this.delayBeforeCanPickup);
                  var1.age = Math.min(var1.age, this.age);
                  var1.setEntityItemStack(var3);
                  this.setDead();
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void setAgeToCreativeDespawnTime() {
      this.age = 4800;
   }

   public void handleWaterMovement() {
      if(this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this)) {
         if(!this.inWater && !this.firstUpdate) {
            this.resetHeight();
         }

         this.inWater = true;
      } else {
         this.inWater = false;
      }

   }

   protected void dealFireDamage(int var1) {
      this.attackEntityFrom(DamageSource.inFire, (float)var1);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if(this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && var1.isExplosion()) {
         return false;
      } else {
         this.setBeenAttacked();
         this.health = (int)((float)this.health - var2);
         if(this.health <= 0) {
            this.setDead();
         }

         return false;
      }
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setShort("Health", (short)((byte)this.health));
      var1.setShort("Age", (short)this.age);
      var1.setShort("PickupDelay", (short)this.delayBeforeCanPickup);
      if(this.getThrower() != null) {
         var1.setString("Thrower", this.thrower);
      }

      if(this.getOwner() != null) {
         var1.setString("Owner", this.owner);
      }

      if(this.getEntityItem() != null) {
         var1.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      this.health = var1.getShort("Health") & 255;
      this.age = var1.getShort("Age");
      if(var1.hasKey("PickupDelay")) {
         this.delayBeforeCanPickup = var1.getShort("PickupDelay");
      }

      if(var1.hasKey("Owner")) {
         this.owner = var1.getString("Owner");
      }

      if(var1.hasKey("Thrower")) {
         this.thrower = var1.getString("Thrower");
      }

      NBTTagCompound var2 = var1.getCompoundTag("Item");
      this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
      if(this.getEntityItem() == null) {
         this.setDead();
      }

   }

   public void onCollideWithPlayer(EntityPlayer var1) {
      if(!this.worldObj.isRemote) {
         ItemStack var2 = this.getEntityItem();
         int var3 = var2.stackSize;
         if(this.delayBeforeCanPickup == 0 && (this.owner == null || 6000 - this.age <= 200 || this.owner.equals(var1.getName())) && var1.inventory.addItemStackToInventory(var2)) {
            if(var2.getItem() == Item.getItemFromBlock(Blocks.log)) {
               var1.triggerAchievement(AchievementList.mineWood);
            }

            if(var2.getItem() == Item.getItemFromBlock(Blocks.log2)) {
               var1.triggerAchievement(AchievementList.mineWood);
            }

            if(var2.getItem() == Items.leather) {
               var1.triggerAchievement(AchievementList.killCow);
            }

            if(var2.getItem() == Items.diamond) {
               var1.triggerAchievement(AchievementList.diamonds);
            }

            if(var2.getItem() == Items.blaze_rod) {
               var1.triggerAchievement(AchievementList.blazeRod);
            }

            if(var2.getItem() == Items.diamond && this.getThrower() != null) {
               EntityPlayer var4 = this.worldObj.getPlayerEntityByName(this.getThrower());
               if(var4 != var1) {
                  var4.triggerAchievement(AchievementList.diamondsToYou);
               }
            }

            if(!this.isSilent()) {
               this.worldObj.playSoundAtEntity(var1, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }

            var1.onItemPickup(this, var3);
            if(var2.stackSize <= 0) {
               this.setDead();
            }
         }
      }

   }

   public String getName() {
      return this.hasCustomName()?this.getCustomNameTag():StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public void travelToDimension(int var1) {
      super.travelToDimension(var1);
      if(!this.worldObj.isRemote) {
         this.searchForOtherItemsNearby();
      }

   }

   public ItemStack getEntityItem() {
      ItemStack var1 = this.k().d(10);
      return new ItemStack(Blocks.stone);
   }

   public void setEntityItemStack(ItemStack var1) {
      this.k().a(10, var1);
      this.k().f(10);
   }

   public String getOwner() {
      return this.owner;
   }

   public void setOwner(String var1) {
      this.owner = var1;
   }

   public String getThrower() {
      return this.thrower;
   }

   public void setThrower(String var1) {
      this.thrower = var1;
   }

   public int getAge() {
      return this.age;
   }

   public void setDefaultPickupDelay() {
      this.delayBeforeCanPickup = 10;
   }

   public void setNoPickupDelay() {
      this.delayBeforeCanPickup = 0;
   }

   public void setInfinitePickupDelay() {
      this.delayBeforeCanPickup = 32767;
   }

   public void setPickupDelay(int var1) {
      this.delayBeforeCanPickup = var1;
   }

   public boolean cannotPickup() {
      return this.delayBeforeCanPickup > 0;
   }

   public void setNoDespawn() {
      this.age = -6000;
   }

   public void func_174870_v() {
      this.setInfinitePickupDelay();
      this.age = 5999;
   }
}
