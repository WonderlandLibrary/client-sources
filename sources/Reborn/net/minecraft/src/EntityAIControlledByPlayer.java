package net.minecraft.src;

public class EntityAIControlledByPlayer extends EntityAIBase
{
    private final EntityLiving thisEntity;
    private final float maxSpeed;
    private float currentSpeed;
    private boolean speedBoosted;
    private int speedBoostTime;
    private int maxSpeedBoostTime;
    
    public EntityAIControlledByPlayer(final EntityLiving par1EntityLiving, final float par2) {
        this.currentSpeed = 0.0f;
        this.speedBoosted = false;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = 0;
        this.thisEntity = par1EntityLiving;
        this.maxSpeed = par2;
        this.setMutexBits(7);
    }
    
    @Override
    public void startExecuting() {
        this.currentSpeed = 0.0f;
    }
    
    @Override
    public void resetTask() {
        this.speedBoosted = false;
        this.currentSpeed = 0.0f;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered());
    }
    
    @Override
    public void updateTask() {
        final EntityPlayer var1 = (EntityPlayer)this.thisEntity.riddenByEntity;
        final EntityCreature var2 = (EntityCreature)this.thisEntity;
        float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - this.thisEntity.rotationYaw) * 0.5f;
        if (var3 > 5.0f) {
            var3 = 5.0f;
        }
        if (var3 < -5.0f) {
            var3 = -5.0f;
        }
        this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + var3);
        if (this.currentSpeed < this.maxSpeed) {
            this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01f;
        }
        if (this.currentSpeed > this.maxSpeed) {
            this.currentSpeed = this.maxSpeed;
        }
        final int var4 = MathHelper.floor_double(this.thisEntity.posX);
        final int var5 = MathHelper.floor_double(this.thisEntity.posY);
        final int var6 = MathHelper.floor_double(this.thisEntity.posZ);
        float var7 = this.currentSpeed;
        if (this.speedBoosted) {
            if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
                this.speedBoosted = false;
            }
            var7 += var7 * 1.15f * MathHelper.sin(this.speedBoostTime / this.maxSpeedBoostTime * 3.1415927f);
        }
        float var8 = 0.91f;
        if (this.thisEntity.onGround) {
            var8 = 0.54600006f;
            final int var9 = this.thisEntity.worldObj.getBlockId(MathHelper.floor_float(var4), MathHelper.floor_float(var5) - 1, MathHelper.floor_float(var6));
            if (var9 > 0) {
                var8 = Block.blocksList[var9].slipperiness * 0.91f;
            }
        }
        final float var10 = 0.16277136f / (var8 * var8 * var8);
        final float var11 = MathHelper.sin(var2.rotationYaw * 3.1415927f / 180.0f);
        final float var12 = MathHelper.cos(var2.rotationYaw * 3.1415927f / 180.0f);
        final float var13 = var2.getAIMoveSpeed() * var10;
        float var14 = Math.max(var7, 1.0f);
        var14 = var13 / var14;
        final float var15 = var7 * var14;
        float var16 = -(var15 * var11);
        float var17 = var15 * var12;
        if (MathHelper.abs(var16) > MathHelper.abs(var17)) {
            if (var16 < 0.0f) {
                var16 -= this.thisEntity.width / 2.0f;
            }
            if (var16 > 0.0f) {
                var16 += this.thisEntity.width / 2.0f;
            }
            var17 = 0.0f;
        }
        else {
            var16 = 0.0f;
            if (var17 < 0.0f) {
                var17 -= this.thisEntity.width / 2.0f;
            }
            if (var17 > 0.0f) {
                var17 += this.thisEntity.width / 2.0f;
            }
        }
        final int var18 = MathHelper.floor_double(this.thisEntity.posX + var16);
        final int var19 = MathHelper.floor_double(this.thisEntity.posZ + var17);
        final PathPoint var20 = new PathPoint(MathHelper.floor_float(this.thisEntity.width + 1.0f), MathHelper.floor_float(this.thisEntity.height + var1.height + 1.0f), MathHelper.floor_float(this.thisEntity.width + 1.0f));
        if (var4 != var18 || var6 != var19) {
            final int var21 = this.thisEntity.worldObj.getBlockId(var4, var5, var6);
            final int var22 = this.thisEntity.worldObj.getBlockId(var4, var5 - 1, var6);
            final boolean var23 = this.func_98216_b(var21) || (Block.blocksList[var21] == null && this.func_98216_b(var22));
            if (!var23 && PathFinder.func_82565_a(this.thisEntity, var18, var5, var19, var20, false, false, true) == 0 && PathFinder.func_82565_a(this.thisEntity, var4, var5 + 1, var6, var20, false, false, true) == 1 && PathFinder.func_82565_a(this.thisEntity, var18, var5 + 1, var19, var20, false, false, true) == 1) {
                var2.getJumpHelper().setJumping();
            }
        }
        if (!var1.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5f && this.thisEntity.getRNG().nextFloat() < 0.006f && !this.speedBoosted) {
            final ItemStack var24 = var1.getHeldItem();
            if (var24 != null && var24.itemID == Item.carrotOnAStick.itemID) {
                var24.damageItem(1, var1);
                if (var24.stackSize == 0) {
                    final ItemStack var25 = new ItemStack(Item.fishingRod);
                    var25.setTagCompound(var24.stackTagCompound);
                    var1.inventory.mainInventory[var1.inventory.currentItem] = var25;
                }
            }
        }
        this.thisEntity.moveEntityWithHeading(0.0f, var7);
    }
    
    private boolean func_98216_b(final int par1) {
        return Block.blocksList[par1] != null && (Block.blocksList[par1].getRenderType() == 10 || Block.blocksList[par1] instanceof BlockHalfSlab);
    }
    
    public boolean isSpeedBoosted() {
        return this.speedBoosted;
    }
    
    public void boostSpeed() {
        this.speedBoosted = true;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
    }
    
    public boolean isControlledByPlayer() {
        return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3f;
    }
}
