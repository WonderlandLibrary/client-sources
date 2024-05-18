package net.minecraft.src;

public class MovingObjectPosition
{
    public EnumMovingObjectType typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vec3 hitVec;
    public Entity entityHit;
    
    public MovingObjectPosition(final int par1, final int par2, final int par3, final int par4, final Vec3 par5Vec3) {
        this.typeOfHit = EnumMovingObjectType.TILE;
        this.blockX = par1;
        this.blockY = par2;
        this.blockZ = par3;
        this.sideHit = par4;
        this.hitVec = par5Vec3.myVec3LocalPool.getVecFromPool(par5Vec3.xCoord, par5Vec3.yCoord, par5Vec3.zCoord);
    }
    
    public MovingObjectPosition(final Entity par1Entity) {
        this.typeOfHit = EnumMovingObjectType.ENTITY;
        this.entityHit = par1Entity;
        this.hitVec = par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
    }
}
