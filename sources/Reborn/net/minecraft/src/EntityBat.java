package net.minecraft.src;

import java.util.*;

public class EntityBat extends EntityAmbientCreature
{
    private ChunkCoordinates currentFlightTarget;
    
    public EntityBat(final World par1World) {
        super(par1World);
        this.texture = "/mob/bat.png";
        this.setSize(0.5f, 0.9f);
        this.setIsBatHanging(true);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }
    
    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }
    
    @Override
    protected String getLivingSound() {
        return (this.getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.bat.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.bat.death";
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    protected void collideWithEntity(final Entity par1Entity) {
    }
    
    @Override
    protected void func_85033_bc() {
    }
    
    @Override
    public int getMaxHealth() {
        return 6;
    }
    
    public boolean getIsBatHanging() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setIsBatHanging(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.posY = MathHelper.floor_double(this.posY) + 1.0 - this.height;
        }
        else {
            this.motionY *= 0.6000000238418579;
        }
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.getIsBatHanging()) {
            if (!this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ))) {
                this.setIsBatHanging(false);
                this.worldObj.playAuxSFXAtEntity(null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }
            else {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = this.rand.nextInt(360);
                }
                if (this.worldObj.getClosestPlayerToEntity(this, 4.0) != null) {
                    this.setIsBatHanging(false);
                    this.worldObj.playAuxSFXAtEntity(null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                }
            }
        }
        else {
            if (this.currentFlightTarget != null && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1)) {
                this.currentFlightTarget = null;
            }
            if (this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0f) {
                this.currentFlightTarget = new ChunkCoordinates((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }
            final double var1 = this.currentFlightTarget.posX + 0.5 - this.posX;
            final double var2 = this.currentFlightTarget.posY + 0.1 - this.posY;
            final double var3 = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.10000000149011612;
            this.motionY += (Math.signum(var2) * 0.699999988079071 - this.motionY) * 0.10000000149011612;
            this.motionZ += (Math.signum(var3) * 0.5 - this.motionZ) * 0.10000000149011612;
            final float var4 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
            final float var5 = MathHelper.wrapAngleTo180_float(var4 - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += var5;
            if (this.rand.nextInt(100) == 0 && this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ))) {
                this.setIsBatHanging(true);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected void updateFallState(final double par1, final boolean par3) {
    }
    
    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(16, par1NBTTagCompound.getByte("BatFlags"));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final int var1 = MathHelper.floor_double(this.boundingBox.minY);
        if (var1 >= 63) {
            return false;
        }
        final int var2 = MathHelper.floor_double(this.posX);
        final int var3 = MathHelper.floor_double(this.posZ);
        final int var4 = this.worldObj.getBlockLightValue(var2, var1, var3);
        byte var5 = 4;
        final Calendar var6 = this.worldObj.getCurrentDate();
        if ((var6.get(2) + 1 != 10 || var6.get(5) < 20) && (var6.get(2) + 1 != 11 || var6.get(5) > 3)) {
            if (this.rand.nextBoolean()) {
                return false;
            }
        }
        else {
            var5 = 7;
        }
        return var4 <= this.rand.nextInt(var5) && super.getCanSpawnHere();
    }
    
    @Override
    public void initCreature() {
    }
}
