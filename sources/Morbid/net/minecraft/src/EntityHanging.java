package net.minecraft.src;

import java.util.*;

public abstract class EntityHanging extends Entity
{
    private int tickCounter1;
    public int hangingDirection;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    
    public EntityHanging(final World par1World) {
        super(par1World);
        this.tickCounter1 = 0;
        this.hangingDirection = 0;
        this.yOffset = 0.0f;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityHanging(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this(par1World);
        this.xPosition = par2;
        this.yPosition = par3;
        this.zPosition = par4;
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void setDirection(final int par1) {
        this.hangingDirection = par1;
        final float n = par1 * 90;
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        float var2 = this.func_82329_d();
        float var3 = this.func_82330_g();
        float var4 = this.func_82329_d();
        if (par1 != 2 && par1 != 0) {
            var2 = 0.5f;
        }
        else {
            var4 = 0.5f;
            final float n2 = Direction.rotateOpposite[par1] * 90;
            this.prevRotationYaw = n2;
            this.rotationYaw = n2;
        }
        var2 /= 32.0f;
        var3 /= 32.0f;
        var4 /= 32.0f;
        float var5 = this.xPosition + 0.5f;
        float var6 = this.yPosition + 0.5f;
        float var7 = this.zPosition + 0.5f;
        final float var8 = 0.5625f;
        if (par1 == 2) {
            var7 -= var8;
        }
        if (par1 == 1) {
            var5 -= var8;
        }
        if (par1 == 0) {
            var7 += var8;
        }
        if (par1 == 3) {
            var5 += var8;
        }
        if (par1 == 2) {
            var5 -= this.func_70517_b(this.func_82329_d());
        }
        if (par1 == 1) {
            var7 += this.func_70517_b(this.func_82329_d());
        }
        if (par1 == 0) {
            var5 += this.func_70517_b(this.func_82329_d());
        }
        if (par1 == 3) {
            var7 -= this.func_70517_b(this.func_82329_d());
        }
        var6 += this.func_70517_b(this.func_82330_g());
        this.setPosition(var5, var6, var7);
        final float var9 = -0.03125f;
        this.boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
    }
    
    private float func_70517_b(final int par1) {
        return (par1 == 32) ? 0.5f : ((par1 == 64) ? 0.5f : 0.0f);
    }
    
    @Override
    public void onUpdate() {
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
            this.tickCounter1 = 0;
            if (!this.isDead && !this.onValidSurface()) {
                this.setDead();
                this.dropItemStack();
            }
        }
    }
    
    public boolean onValidSurface() {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            return false;
        }
        final int var1 = Math.max(1, this.func_82329_d() / 16);
        final int var2 = Math.max(1, this.func_82330_g() / 16);
        int var3 = this.xPosition;
        int var4 = this.yPosition;
        int var5 = this.zPosition;
        if (this.hangingDirection == 2) {
            var3 = MathHelper.floor_double(this.posX - this.func_82329_d() / 32.0f);
        }
        if (this.hangingDirection == 1) {
            var5 = MathHelper.floor_double(this.posZ - this.func_82329_d() / 32.0f);
        }
        if (this.hangingDirection == 0) {
            var3 = MathHelper.floor_double(this.posX - this.func_82329_d() / 32.0f);
        }
        if (this.hangingDirection == 3) {
            var5 = MathHelper.floor_double(this.posZ - this.func_82329_d() / 32.0f);
        }
        var4 = MathHelper.floor_double(this.posY - this.func_82330_g() / 32.0f);
        for (int var6 = 0; var6 < var1; ++var6) {
            for (int var7 = 0; var7 < var2; ++var7) {
                Material var8;
                if (this.hangingDirection != 2 && this.hangingDirection != 0) {
                    var8 = this.worldObj.getBlockMaterial(this.xPosition, var4 + var7, var5 + var6);
                }
                else {
                    var8 = this.worldObj.getBlockMaterial(var3 + var6, var4 + var7, this.zPosition);
                }
                if (!var8.isSolid()) {
                    return false;
                }
            }
        }
        final List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        for (final Entity var11 : var9) {
            if (var11 instanceof EntityHanging) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean func_85031_j(final Entity par1Entity) {
        return par1Entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par1Entity), 0);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            EntityPlayer var3 = null;
            if (par1DamageSource.getEntity() instanceof EntityPlayer) {
                var3 = (EntityPlayer)par1DamageSource.getEntity();
            }
            if (var3 != null && var3.capabilities.isCreativeMode) {
                return true;
            }
            this.dropItemStack();
        }
        return true;
    }
    
    @Override
    public void moveEntity(final double par1, final double par3, final double par5) {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0) {
            this.setDead();
            this.dropItemStack();
        }
    }
    
    @Override
    public void addVelocity(final double par1, final double par3, final double par5) {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0) {
            this.setDead();
            this.dropItemStack();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Direction", (byte)this.hangingDirection);
        par1NBTTagCompound.setInteger("TileX", this.xPosition);
        par1NBTTagCompound.setInteger("TileY", this.yPosition);
        par1NBTTagCompound.setInteger("TileZ", this.zPosition);
        switch (this.hangingDirection) {
            case 0: {
                par1NBTTagCompound.setByte("Dir", (byte)2);
                break;
            }
            case 1: {
                par1NBTTagCompound.setByte("Dir", (byte)1);
                break;
            }
            case 2: {
                par1NBTTagCompound.setByte("Dir", (byte)0);
                break;
            }
            case 3: {
                par1NBTTagCompound.setByte("Dir", (byte)3);
                break;
            }
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.hasKey("Direction")) {
            this.hangingDirection = par1NBTTagCompound.getByte("Direction");
        }
        else {
            switch (par1NBTTagCompound.getByte("Dir")) {
                case 0: {
                    this.hangingDirection = 2;
                    break;
                }
                case 1: {
                    this.hangingDirection = 1;
                    break;
                }
                case 2: {
                    this.hangingDirection = 0;
                    break;
                }
                case 3: {
                    this.hangingDirection = 3;
                    break;
                }
            }
        }
        this.xPosition = par1NBTTagCompound.getInteger("TileX");
        this.yPosition = par1NBTTagCompound.getInteger("TileY");
        this.zPosition = par1NBTTagCompound.getInteger("TileZ");
        this.setDirection(this.hangingDirection);
    }
    
    public abstract int func_82329_d();
    
    public abstract int func_82330_g();
    
    public abstract void dropItemStack();
}
