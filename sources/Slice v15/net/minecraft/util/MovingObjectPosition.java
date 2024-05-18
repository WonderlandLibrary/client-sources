package net.minecraft.util;

import net.minecraft.entity.Entity;







public class MovingObjectPosition
{
  private BlockPos field_178783_e;
  public MovingObjectType typeOfHit;
  public EnumFacing field_178784_b;
  public Vec3 hitVec;
  public Entity entityHit;
  private static final String __OBFID = "CL_00000610";
  
  public MovingObjectPosition(Vec3 p_i45551_1_, EnumFacing p_i45551_2_, BlockPos p_i45551_3_)
  {
    this(MovingObjectType.BLOCK, p_i45551_1_, p_i45551_2_, p_i45551_3_);
  }
  
  public MovingObjectPosition(Vec3 p_i45552_1_, EnumFacing p_i45552_2_)
  {
    this(MovingObjectType.BLOCK, p_i45552_1_, p_i45552_2_, BlockPos.ORIGIN);
  }
  
  public MovingObjectPosition(Entity p_i2304_1_)
  {
    this(p_i2304_1_, new Vec3(posX, posY, posZ));
  }
  
  public MovingObjectPosition(MovingObjectType p_i45553_1_, Vec3 p_i45553_2_, EnumFacing p_i45553_3_, BlockPos p_i45553_4_)
  {
    typeOfHit = p_i45553_1_;
    field_178783_e = p_i45553_4_;
    field_178784_b = p_i45553_3_;
    hitVec = new Vec3(xCoord, yCoord, zCoord);
  }
  
  public MovingObjectPosition(Entity p_i45482_1_, Vec3 p_i45482_2_)
  {
    typeOfHit = MovingObjectType.ENTITY;
    entityHit = p_i45482_1_;
    hitVec = p_i45482_2_;
  }
  
  public BlockPos func_178782_a()
  {
    return field_178783_e;
  }
  
  public String toString()
  {
    return "HitResult{type=" + typeOfHit + ", blockpos=" + field_178783_e + ", f=" + field_178784_b + ", pos=" + hitVec + ", entity=" + entityHit + '}';
  }
  
  public static enum MovingObjectType
  {
    MISS("MISS", 0), 
    BLOCK("BLOCK", 1), 
    ENTITY("ENTITY", 2);
    
    private static final MovingObjectType[] $VALUES = { MISS, BLOCK, ENTITY };
    private static final String __OBFID = "CL_00000611";
    
    private MovingObjectType(String p_i2302_1_, int p_i2302_2_) {}
  }
}
