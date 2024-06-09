package net.minecraft.world.gen.structure;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;











public class StructureBoundingBox
{
  public int minX;
  public int minY;
  public int minZ;
  public int maxX;
  public int maxY;
  public int maxZ;
  private static final String __OBFID = "CL_00000442";
  
  public StructureBoundingBox() {}
  
  public StructureBoundingBox(int[] p_i43000_1_)
  {
    if (p_i43000_1_.length == 6)
    {
      minX = p_i43000_1_[0];
      minY = p_i43000_1_[1];
      minZ = p_i43000_1_[2];
      maxX = p_i43000_1_[3];
      maxY = p_i43000_1_[4];
      maxZ = p_i43000_1_[5];
    }
  }
  



  public static StructureBoundingBox getNewBoundingBox()
  {
    return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
  }
  
  public static StructureBoundingBox func_175897_a(int p_175897_0_, int p_175897_1_, int p_175897_2_, int p_175897_3_, int p_175897_4_, int p_175897_5_, int p_175897_6_, int p_175897_7_, int p_175897_8_, EnumFacing p_175897_9_)
  {
    switch (SwitchEnumFacing.field_175895_a[p_175897_9_.ordinal()])
    {
    case 1: 
      return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ - p_175897_8_ + 1 + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_5_);
    
    case 2: 
      return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);
    
    case 3: 
      return new StructureBoundingBox(p_175897_0_ - p_175897_8_ + 1 + p_175897_5_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_5_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);
    
    case 4: 
      return new StructureBoundingBox(p_175897_0_ + p_175897_5_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_8_ - 1 + p_175897_5_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);
    }
    
    return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);
  }
  

  public static StructureBoundingBox func_175899_a(int p_175899_0_, int p_175899_1_, int p_175899_2_, int p_175899_3_, int p_175899_4_, int p_175899_5_)
  {
    return new StructureBoundingBox(Math.min(p_175899_0_, p_175899_3_), Math.min(p_175899_1_, p_175899_4_), Math.min(p_175899_2_, p_175899_5_), Math.max(p_175899_0_, p_175899_3_), Math.max(p_175899_1_, p_175899_4_), Math.max(p_175899_2_, p_175899_5_));
  }
  
  public StructureBoundingBox(StructureBoundingBox p_i2031_1_)
  {
    minX = minX;
    minY = minY;
    minZ = minZ;
    maxX = maxX;
    maxY = maxY;
    maxZ = maxZ;
  }
  
  public StructureBoundingBox(int p_i2032_1_, int p_i2032_2_, int p_i2032_3_, int p_i2032_4_, int p_i2032_5_, int p_i2032_6_)
  {
    minX = p_i2032_1_;
    minY = p_i2032_2_;
    minZ = p_i2032_3_;
    maxX = p_i2032_4_;
    maxY = p_i2032_5_;
    maxZ = p_i2032_6_;
  }
  
  public StructureBoundingBox(Vec3i p_i45626_1_, Vec3i p_i45626_2_)
  {
    minX = Math.min(p_i45626_1_.getX(), p_i45626_2_.getX());
    minY = Math.min(p_i45626_1_.getY(), p_i45626_2_.getY());
    minZ = Math.min(p_i45626_1_.getZ(), p_i45626_2_.getZ());
    maxX = Math.max(p_i45626_1_.getX(), p_i45626_2_.getX());
    maxY = Math.max(p_i45626_1_.getY(), p_i45626_2_.getY());
    maxZ = Math.max(p_i45626_1_.getZ(), p_i45626_2_.getZ());
  }
  
  public StructureBoundingBox(int p_i2033_1_, int p_i2033_2_, int p_i2033_3_, int p_i2033_4_)
  {
    minX = p_i2033_1_;
    minZ = p_i2033_2_;
    maxX = p_i2033_3_;
    maxZ = p_i2033_4_;
    minY = 1;
    maxY = 512;
  }
  



  public boolean intersectsWith(StructureBoundingBox p_78884_1_)
  {
    return (maxX >= minX) && (minX <= maxX) && (maxZ >= minZ) && (minZ <= maxZ) && (maxY >= minY) && (minY <= maxY);
  }
  



  public boolean intersectsWith(int p_78885_1_, int p_78885_2_, int p_78885_3_, int p_78885_4_)
  {
    return (maxX >= p_78885_1_) && (minX <= p_78885_3_) && (maxZ >= p_78885_2_) && (minZ <= p_78885_4_);
  }
  



  public void expandTo(StructureBoundingBox p_78888_1_)
  {
    minX = Math.min(minX, minX);
    minY = Math.min(minY, minY);
    minZ = Math.min(minZ, minZ);
    maxX = Math.max(maxX, maxX);
    maxY = Math.max(maxY, maxY);
    maxZ = Math.max(maxZ, maxZ);
  }
  



  public void offset(int p_78886_1_, int p_78886_2_, int p_78886_3_)
  {
    minX += p_78886_1_;
    minY += p_78886_2_;
    minZ += p_78886_3_;
    maxX += p_78886_1_;
    maxY += p_78886_2_;
    maxZ += p_78886_3_;
  }
  
  public boolean func_175898_b(Vec3i p_175898_1_)
  {
    return (p_175898_1_.getX() >= minX) && (p_175898_1_.getX() <= maxX) && (p_175898_1_.getZ() >= minZ) && (p_175898_1_.getZ() <= maxZ) && (p_175898_1_.getY() >= minY) && (p_175898_1_.getY() <= maxY);
  }
  
  public Vec3i func_175896_b()
  {
    return new Vec3i(maxX - minX, maxY - minY, maxZ - minZ);
  }
  



  public int getXSize()
  {
    return maxX - minX + 1;
  }
  



  public int getYSize()
  {
    return maxY - minY + 1;
  }
  



  public int getZSize()
  {
    return maxZ - minZ + 1;
  }
  
  public Vec3i func_180717_f()
  {
    return new BlockPos(minX + (maxX - minX + 1) / 2, minY + (maxY - minY + 1) / 2, minZ + (maxZ - minZ + 1) / 2);
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("x0", minX).add("y0", minY).add("z0", minZ).add("x1", maxX).add("y1", maxY).add("z1", maxZ).toString();
  }
  
  public NBTTagIntArray func_151535_h()
  {
    return new NBTTagIntArray(new int[] { minX, minY, minZ, maxX, maxY, maxZ });
  }
  
  static final class SwitchEnumFacing
  {
    static final int[] field_175895_a = new int[EnumFacing.values().length];
    private static final String __OBFID = "CL_00001999";
    
    static
    {
      try
      {
        field_175895_a[EnumFacing.NORTH.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_175895_a[EnumFacing.SOUTH.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_175895_a[EnumFacing.WEST.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_175895_a[EnumFacing.EAST.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
    }
    
    SwitchEnumFacing() {}
  }
}
