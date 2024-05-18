package net.minecraft.src;

import java.util.*;

public class EntityAIFleeSun extends EntityAIBase
{
    private EntityCreature theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private float movementSpeed;
    private World theWorld;
    
    public EntityAIFleeSun(final EntityCreature par1EntityCreature, final float par2) {
        this.theCreature = par1EntityCreature;
        this.movementSpeed = par2;
        this.theWorld = par1EntityCreature.worldObj;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }
        if (!this.theCreature.isBurning()) {
            return false;
        }
        if (!this.theWorld.canBlockSeeTheSky(MathHelper.floor_double(this.theCreature.posX), (int)this.theCreature.boundingBox.minY, MathHelper.floor_double(this.theCreature.posZ))) {
            return false;
        }
        final Vec3 var1 = this.findPossibleShelter();
        if (var1 == null) {
            return false;
        }
        this.shelterX = var1.xCoord;
        this.shelterY = var1.yCoord;
        this.shelterZ = var1.zCoord;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theCreature.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }
    
    private Vec3 findPossibleShelter() {
        final Random var1 = this.theCreature.getRNG();
        for (int var2 = 0; var2 < 10; ++var2) {
            final int var3 = MathHelper.floor_double(this.theCreature.posX + var1.nextInt(20) - 10.0);
            final int var4 = MathHelper.floor_double(this.theCreature.boundingBox.minY + var1.nextInt(6) - 3.0);
            final int var5 = MathHelper.floor_double(this.theCreature.posZ + var1.nextInt(20) - 10.0);
            if (!this.theWorld.canBlockSeeTheSky(var3, var4, var5) && this.theCreature.getBlockPathWeight(var3, var4, var5) < 0.0f) {
                return this.theWorld.getWorldVec3Pool().getVecFromPool(var3, var4, var5);
            }
        }
        return null;
    }
}
