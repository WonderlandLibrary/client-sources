package net.minecraft.src;

import java.util.*;

public class EntityLightningBolt extends EntityWeatherEffect
{
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;
    
    public EntityLightningBolt(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World);
        this.boltVertex = 0L;
        this.setLocationAndAngles(par2, par4, par6, 0.0f, 0.0f);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        if (!par1World.isRemote && par1World.difficultySetting >= 2 && par1World.doChunksNearChunkExist(MathHelper.floor_double(par2), MathHelper.floor_double(par4), MathHelper.floor_double(par6), 10)) {
            int var8 = MathHelper.floor_double(par2);
            int var9 = MathHelper.floor_double(par4);
            int var10 = MathHelper.floor_double(par6);
            if (par1World.getBlockId(var8, var9, var10) == 0 && Block.fire.canPlaceBlockAt(par1World, var8, var9, var10)) {
                par1World.setBlock(var8, var9, var10, Block.fire.blockID);
            }
            for (var8 = 0; var8 < 4; ++var8) {
                var9 = MathHelper.floor_double(par2) + this.rand.nextInt(3) - 1;
                var10 = MathHelper.floor_double(par4) + this.rand.nextInt(3) - 1;
                final int var11 = MathHelper.floor_double(par6) + this.rand.nextInt(3) - 1;
                if (par1World.getBlockId(var9, var10, var11) == 0 && Block.fire.canPlaceBlockAt(par1World, var9, var10, var11)) {
                    par1World.setBlock(var9, var10, var11, Block.fire.blockID);
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == 2) {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f);
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + this.rand.nextFloat() * 0.2f);
        }
        --this.lightningState;
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            }
            else if (this.lightningState < -this.rand.nextInt(10)) {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                if (!this.worldObj.isRemote && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10)) {
                    final int var1 = MathHelper.floor_double(this.posX);
                    final int var2 = MathHelper.floor_double(this.posY);
                    final int var3 = MathHelper.floor_double(this.posZ);
                    if (this.worldObj.getBlockId(var1, var2, var3) == 0 && Block.fire.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
                        this.worldObj.setBlock(var1, var2, var3, Block.fire.blockID);
                    }
                }
            }
        }
        if (this.lightningState >= 0) {
            if (this.worldObj.isRemote) {
                this.worldObj.lastLightningBolt = 2;
            }
            else {
                final double var4 = 3.0;
                final List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + 6.0 + var4, this.posZ + var4));
                for (int var6 = 0; var6 < var5.size(); ++var6) {
                    final Entity var7 = var5.get(var6);
                    var7.onStruckByLightning(this);
                }
            }
        }
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public boolean isInRangeToRenderVec3D(final Vec3 par1Vec3) {
        return this.lightningState >= 0;
    }
}
