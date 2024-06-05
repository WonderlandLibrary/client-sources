package net.minecraft.src;

import java.util.*;

public class EntityItem extends Entity
{
    public int age;
    public int delayBeforeCanPickup;
    private int health;
    public float hoverStart;
    
    public EntityItem(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntityItem(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack) {
        this(par1World, par2, par4, par6);
        this.setEntityItemStack(par8ItemStack);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityItem(final World par1World) {
        super(par1World);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.noClip = this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        final boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;
        if (var1 || this.ticksExisted % 25 == 0) {
            if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
                this.motionY = 0.20000000298023224;
                this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
            }
            if (!this.worldObj.isRemote) {
                this.searchForOtherItemsNearby();
            }
        }
        float var2 = 0.98f;
        if (this.onGround) {
            var2 = 0.58800006f;
            final int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (var3 > 0) {
                var2 = Block.blocksList[var3].slipperiness * 0.98f;
            }
        }
        this.motionX *= var2;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= var2;
        if (this.onGround) {
            this.motionY *= -0.5;
        }
        ++this.age;
        if (!this.worldObj.isRemote && this.age >= 6000) {
            this.setDead();
        }
    }
    
    private void searchForOtherItemsNearby() {
        for (final EntityItem var2 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5, 0.0, 0.5))) {
            this.combineItems(var2);
        }
    }
    
    public boolean combineItems(final EntityItem par1EntityItem) {
        if (par1EntityItem == this) {
            return false;
        }
        if (!par1EntityItem.isEntityAlive() || !this.isEntityAlive()) {
            return false;
        }
        final ItemStack var2 = this.getEntityItem();
        final ItemStack var3 = par1EntityItem.getEntityItem();
        if (var3.getItem() != var2.getItem()) {
            return false;
        }
        if (var3.hasTagCompound() ^ var2.hasTagCompound()) {
            return false;
        }
        if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
            return false;
        }
        if (var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage()) {
            return false;
        }
        if (var3.stackSize < var2.stackSize) {
            return par1EntityItem.combineItems(this);
        }
        if (var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
            return false;
        }
        final ItemStack itemStack = var3;
        itemStack.stackSize += var2.stackSize;
        par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
        par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
        par1EntityItem.setEntityItemStack(var3);
        this.setDead();
        return true;
    }
    
    public void setAgeToCreativeDespawnTime() {
        this.age = 4800;
    }
    
    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }
    
    @Override
    protected void dealFireDamage(final int par1) {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.getEntityItem() != null && this.getEntityItem().itemID == Item.netherStar.itemID && par1DamageSource.isExplosion()) {
            return false;
        }
        this.setBeenAttacked();
        this.health -= par2;
        if (this.health <= 0) {
            this.setDead();
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.health);
        par1NBTTagCompound.setShort("Age", (short)this.age);
        if (this.getEntityItem() != null) {
            par1NBTTagCompound.setCompoundTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.health = (par1NBTTagCompound.getShort("Health") & 0xFF);
        this.age = par1NBTTagCompound.getShort("Age");
        final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote) {
            final ItemStack var2 = this.getEntityItem();
            final int var3 = var2.stackSize;
            if (this.delayBeforeCanPickup == 0 && par1EntityPlayer.inventory.addItemStackToInventory(var2)) {
                if (var2.itemID == Block.wood.blockID) {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (var2.itemID == Item.leather.itemID) {
                    par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                }
                if (var2.itemID == Item.diamond.itemID) {
                    par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                }
                if (var2.itemID == Item.blazeRod.itemID) {
                    par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                }
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                par1EntityPlayer.onItemPickup(this, var3);
                if (var2.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    public String getEntityName() {
        return StatCollector.translateToLocal("item." + this.getEntityItem().getItemName());
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    @Override
    public void travelToDimension(final int par1) {
        super.travelToDimension(par1);
        if (!this.worldObj.isRemote) {
            this.searchForOtherItemsNearby();
        }
    }
    
    public ItemStack getEntityItem() {
        final ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);
        if (var1 == null) {
            if (this.worldObj != null) {
                this.worldObj.getWorldLogAgent().logSevere("Item entity " + this.entityId + " has no item?!");
            }
            return new ItemStack(Block.stone);
        }
        return var1;
    }
    
    public void setEntityItemStack(final ItemStack par1ItemStack) {
        this.getDataWatcher().updateObject(10, par1ItemStack);
        this.getDataWatcher().setObjectWatched(10);
    }
}
