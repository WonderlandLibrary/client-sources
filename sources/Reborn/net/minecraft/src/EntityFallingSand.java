package net.minecraft.src;

import java.util.*;

public class EntityFallingSand extends Entity
{
    public int blockID;
    public int metadata;
    public int fallTime;
    public boolean shouldDropItem;
    private boolean isBreakingAnvil;
    private boolean isAnvil;
    private int fallHurtMax;
    private float fallHurtAmount;
    public NBTTagCompound fallingBlockTileEntityData;
    
    public EntityFallingSand(final World par1World) {
        super(par1World);
        this.fallTime = 0;
        this.shouldDropItem = true;
        this.isBreakingAnvil = false;
        this.isAnvil = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.fallingBlockTileEntityData = null;
    }
    
    public EntityFallingSand(final World par1World, final double par2, final double par4, final double par6, final int par8) {
        this(par1World, par2, par4, par6, par8, 0);
    }
    
    public EntityFallingSand(final World par1World, final double par2, final double par4, final double par6, final int par8, final int par9) {
        super(par1World);
        this.fallTime = 0;
        this.shouldDropItem = true;
        this.isBreakingAnvil = false;
        this.isAnvil = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.fallingBlockTileEntityData = null;
        this.blockID = par8;
        this.metadata = par9;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void onUpdate() {
        if (this.blockID == 0) {
            this.setDead();
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isRemote) {
                final int var1 = MathHelper.floor_double(this.posX);
                final int var2 = MathHelper.floor_double(this.posY);
                final int var3 = MathHelper.floor_double(this.posZ);
                if (this.fallTime == 1) {
                    if (this.worldObj.getBlockId(var1, var2, var3) != this.blockID) {
                        this.setDead();
                        return;
                    }
                    this.worldObj.setBlockToAir(var1, var2, var3);
                }
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlockId(var1, var2, var3) != Block.pistonMoving.blockID) {
                        this.setDead();
                        if (!this.isBreakingAnvil && this.worldObj.canPlaceEntityOnSide(this.blockID, var1, var2, var3, true, 1, null, null) && !BlockSand.canFallBelow(this.worldObj, var1, var2 - 1, var3) && this.worldObj.setBlock(var1, var2, var3, this.blockID, this.metadata, 3)) {
                            if (Block.blocksList[this.blockID] instanceof BlockSand) {
                                ((BlockSand)Block.blocksList[this.blockID]).onFinishFalling(this.worldObj, var1, var2, var3, this.metadata);
                            }
                            if (this.fallingBlockTileEntityData != null && Block.blocksList[this.blockID] instanceof ITileEntityProvider) {
                                final TileEntity var4 = this.worldObj.getBlockTileEntity(var1, var2, var3);
                                if (var4 != null) {
                                    final NBTTagCompound var5 = new NBTTagCompound();
                                    var4.writeToNBT(var5);
                                    for (final NBTBase var7 : this.fallingBlockTileEntityData.getTags()) {
                                        if (!var7.getName().equals("x") && !var7.getName().equals("y") && !var7.getName().equals("z")) {
                                            var5.setTag(var7.getName(), var7.copy());
                                        }
                                    }
                                    var4.readFromNBT(var5);
                                    var4.onInventoryChanged();
                                }
                            }
                        }
                        else if (this.shouldDropItem && !this.isBreakingAnvil) {
                            this.entityDropItem(new ItemStack(this.blockID, 1, Block.blocksList[this.blockID].damageDropped(this.metadata)), 0.0f);
                        }
                    }
                }
                else if ((this.fallTime > 100 && !this.worldObj.isRemote && (var2 < 1 || var2 > 256)) || this.fallTime > 600) {
                    if (this.shouldDropItem) {
                        this.entityDropItem(new ItemStack(this.blockID, 1, Block.blocksList[this.blockID].damageDropped(this.metadata)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    protected void fall(final float par1) {
        if (this.isAnvil) {
            final int var2 = MathHelper.ceiling_float_int(par1 - 1.0f);
            if (var2 > 0) {
                final ArrayList var3 = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
                final DamageSource var4 = (this.blockID == Block.anvil.blockID) ? DamageSource.anvil : DamageSource.fallingBlock;
                for (final Entity var6 : var3) {
                    var6.attackEntityFrom(var4, Math.min(MathHelper.floor_float(var2 * this.fallHurtAmount), this.fallHurtMax));
                }
                if (this.blockID == Block.anvil.blockID && this.rand.nextFloat() < 0.05000000074505806 + var2 * 0.05) {
                    int var7 = this.metadata >> 2;
                    final int var8 = this.metadata & 0x3;
                    if (++var7 > 2) {
                        this.isBreakingAnvil = true;
                    }
                    else {
                        this.metadata = (var8 | var7 << 2);
                    }
                }
            }
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Tile", (byte)this.blockID);
        par1NBTTagCompound.setInteger("TileID", this.blockID);
        par1NBTTagCompound.setByte("Data", (byte)this.metadata);
        par1NBTTagCompound.setByte("Time", (byte)this.fallTime);
        par1NBTTagCompound.setBoolean("DropItem", this.shouldDropItem);
        par1NBTTagCompound.setBoolean("HurtEntities", this.isAnvil);
        par1NBTTagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        par1NBTTagCompound.setInteger("FallHurtMax", this.fallHurtMax);
        if (this.fallingBlockTileEntityData != null) {
            par1NBTTagCompound.setCompoundTag("TileEntityData", this.fallingBlockTileEntityData);
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.hasKey("TileID")) {
            this.blockID = par1NBTTagCompound.getInteger("TileID");
        }
        else {
            this.blockID = (par1NBTTagCompound.getByte("Tile") & 0xFF);
        }
        this.metadata = (par1NBTTagCompound.getByte("Data") & 0xFF);
        this.fallTime = (par1NBTTagCompound.getByte("Time") & 0xFF);
        if (par1NBTTagCompound.hasKey("HurtEntities")) {
            this.isAnvil = par1NBTTagCompound.getBoolean("HurtEntities");
            this.fallHurtAmount = par1NBTTagCompound.getFloat("FallHurtAmount");
            this.fallHurtMax = par1NBTTagCompound.getInteger("FallHurtMax");
        }
        else if (this.blockID == Block.anvil.blockID) {
            this.isAnvil = true;
        }
        if (par1NBTTagCompound.hasKey("DropItem")) {
            this.shouldDropItem = par1NBTTagCompound.getBoolean("DropItem");
        }
        if (par1NBTTagCompound.hasKey("TileEntityData")) {
            this.fallingBlockTileEntityData = par1NBTTagCompound.getCompoundTag("TileEntityData");
        }
        if (this.blockID == 0) {
            this.blockID = Block.sand.blockID;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public void setIsAnvil(final boolean par1) {
        this.isAnvil = par1;
    }
    
    @Override
    public boolean canRenderOnFire() {
        return false;
    }
    
    @Override
    public void func_85029_a(final CrashReportCategory par1CrashReportCategory) {
        super.func_85029_a(par1CrashReportCategory);
        par1CrashReportCategory.addCrashSection("Immitating block ID", this.blockID);
        par1CrashReportCategory.addCrashSection("Immitating block data", this.metadata);
    }
}
