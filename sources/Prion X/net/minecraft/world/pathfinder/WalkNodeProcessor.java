package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor
{
  private boolean field_176180_f;
  private boolean field_176181_g;
  private boolean field_176183_h;
  private boolean field_176184_i;
  private boolean field_176182_j;
  private static final String __OBFID = "CL_00001965";
  
  public WalkNodeProcessor() {}
  
  public void func_176162_a(IBlockAccess p_176162_1_, Entity p_176162_2_)
  {
    super.func_176162_a(p_176162_1_, p_176162_2_);
    field_176182_j = field_176183_h;
  }
  
  public void func_176163_a()
  {
    super.func_176163_a();
    field_176183_h = field_176182_j;
  }
  

  public PathPoint func_176161_a(Entity p_176161_1_)
  {
    int var2;
    if ((field_176184_i) && (p_176161_1_.isInWater()))
    {
      int var2 = (int)getEntityBoundingBoxminY;
      
      for (Block var3 = field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(posX), var2, MathHelper.floor_double(posZ))).getBlock(); (var3 == Blocks.flowing_water) || (var3 == Blocks.water); var3 = field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(posX), var2, MathHelper.floor_double(posZ))).getBlock())
      {
        var2++;
      }
      
      field_176183_h = false;
    }
    else
    {
      var2 = MathHelper.floor_double(getEntityBoundingBoxminY + 0.5D);
    }
    
    return func_176159_a(MathHelper.floor_double(getEntityBoundingBoxminX), var2, MathHelper.floor_double(getEntityBoundingBoxminZ));
  }
  
  public PathPoint func_176160_a(Entity p_176160_1_, double p_176160_2_, double p_176160_4_, double p_176160_6_)
  {
    return func_176159_a(MathHelper.floor_double(p_176160_2_ - width / 2.0F), MathHelper.floor_double(p_176160_4_), MathHelper.floor_double(p_176160_6_ - width / 2.0F));
  }
  
  public int func_176164_a(PathPoint[] p_176164_1_, Entity p_176164_2_, PathPoint p_176164_3_, PathPoint p_176164_4_, float p_176164_5_)
  {
    int var6 = 0;
    byte var7 = 0;
    
    if (func_176177_a(p_176164_2_, xCoord, yCoord + 1, zCoord) == 1)
    {
      var7 = 1;
    }
    
    PathPoint var8 = func_176171_a(p_176164_2_, xCoord, yCoord, zCoord + 1, var7);
    PathPoint var9 = func_176171_a(p_176164_2_, xCoord - 1, yCoord, zCoord, var7);
    PathPoint var10 = func_176171_a(p_176164_2_, xCoord + 1, yCoord, zCoord, var7);
    PathPoint var11 = func_176171_a(p_176164_2_, xCoord, yCoord, zCoord - 1, var7);
    
    if ((var8 != null) && (!visited) && (var8.distanceTo(p_176164_4_) < p_176164_5_))
    {
      p_176164_1_[(var6++)] = var8;
    }
    
    if ((var9 != null) && (!visited) && (var9.distanceTo(p_176164_4_) < p_176164_5_))
    {
      p_176164_1_[(var6++)] = var9;
    }
    
    if ((var10 != null) && (!visited) && (var10.distanceTo(p_176164_4_) < p_176164_5_))
    {
      p_176164_1_[(var6++)] = var10;
    }
    
    if ((var11 != null) && (!visited) && (var11.distanceTo(p_176164_4_) < p_176164_5_))
    {
      p_176164_1_[(var6++)] = var11;
    }
    
    return var6;
  }
  
  private PathPoint func_176171_a(Entity p_176171_1_, int p_176171_2_, int p_176171_3_, int p_176171_4_, int p_176171_5_)
  {
    PathPoint var6 = null;
    int var7 = func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_, p_176171_4_);
    
    if (var7 == 2)
    {
      return func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_);
    }
    

    if (var7 == 1)
    {
      var6 = func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_);
    }
    
    if ((var6 == null) && (p_176171_5_ > 0) && (var7 != -3) && (var7 != -4) && (func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_) == 1))
    {
      var6 = func_176159_a(p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_);
      p_176171_3_ += p_176171_5_;
    }
    
    if (var6 != null)
    {
      int var8 = 0;
      

      for (int var9 = 0; p_176171_3_ > 0; var6 = func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_))
      {
        var9 = func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_ - 1, p_176171_4_);
        
        if ((field_176183_h) && (var9 == -1))
        {
          return null;
        }
        
        if (var9 != 1) {
          break;
        }
        

        if (var8++ >= p_176171_1_.getMaxFallHeight())
        {
          return null;
        }
        
        p_176171_3_--;
        
        if (p_176171_3_ <= 0)
        {
          return null;
        }
      }
      
      if (var9 == -2)
      {
        return null;
      }
    }
    
    return var6;
  }
  

  private int func_176177_a(Entity p_176177_1_, int p_176177_2_, int p_176177_3_, int p_176177_4_)
  {
    return func_176170_a(field_176169_a, p_176177_1_, p_176177_2_, p_176177_3_, p_176177_4_, field_176168_c, field_176165_d, field_176166_e, field_176183_h, field_176181_g, field_176180_f);
  }
  
  public static int func_176170_a(IBlockAccess p_176170_0_, Entity p_176170_1_, int p_176170_2_, int p_176170_3_, int p_176170_4_, int p_176170_5_, int p_176170_6_, int p_176170_7_, boolean p_176170_8_, boolean p_176170_9_, boolean p_176170_10_)
  {
    boolean var11 = false;
    BlockPos var12 = new BlockPos(p_176170_1_);
    
    for (int var13 = p_176170_2_; var13 < p_176170_2_ + p_176170_5_; var13++)
    {
      for (int var14 = p_176170_3_; var14 < p_176170_3_ + p_176170_6_; var14++)
      {
        for (int var15 = p_176170_4_; var15 < p_176170_4_ + p_176170_7_; var15++)
        {
          BlockPos var16 = new BlockPos(var13, var14, var15);
          Block var17 = p_176170_0_.getBlockState(var16).getBlock();
          
          if (var17.getMaterial() != Material.air)
          {
            if ((var17 != Blocks.trapdoor) && (var17 != Blocks.iron_trapdoor))
            {
              if ((var17 != Blocks.flowing_water) && (var17 != Blocks.water))
              {
                if ((!p_176170_10_) && ((var17 instanceof net.minecraft.block.BlockDoor)) && (var17.getMaterial() == Material.wood))
                {
                  return 0;
                }
              }
              else
              {
                if (p_176170_8_)
                {
                  return -1;
                }
                
                var11 = true;
              }
              
            }
            else {
              var11 = true;
            }
            
            if ((worldObj.getBlockState(var16).getBlock() instanceof BlockRailBase))
            {
              if ((!(worldObj.getBlockState(var12).getBlock() instanceof BlockRailBase)) && (!(worldObj.getBlockState(var12.offsetDown()).getBlock() instanceof BlockRailBase)))
              {
                return -3;
              }
            }
            else if ((!var17.isPassable(p_176170_0_, var16)) && ((!p_176170_9_) || (!(var17 instanceof net.minecraft.block.BlockDoor)) || (var17.getMaterial() != Material.wood)))
            {
              if (((var17 instanceof net.minecraft.block.BlockFence)) || ((var17 instanceof net.minecraft.block.BlockFenceGate)) || ((var17 instanceof net.minecraft.block.BlockWall)))
              {
                return -3;
              }
              
              if ((var17 == Blocks.trapdoor) || (var17 == Blocks.iron_trapdoor))
              {
                return -4;
              }
              
              Material var18 = var17.getMaterial();
              
              if (var18 != Material.lava)
              {
                return 0;
              }
              
              if (!p_176170_1_.func_180799_ab())
              {
                return -2;
              }
            }
          }
        }
      }
    }
    
    return var11 ? 2 : 1;
  }
  
  public void func_176175_a(boolean p_176175_1_)
  {
    field_176180_f = p_176175_1_;
  }
  
  public void func_176172_b(boolean p_176172_1_)
  {
    field_176181_g = p_176172_1_;
  }
  
  public void func_176176_c(boolean p_176176_1_)
  {
    field_176183_h = p_176176_1_;
  }
  
  public void func_176178_d(boolean p_176178_1_)
  {
    field_176184_i = p_176178_1_;
  }
  
  public boolean func_176179_b()
  {
    return field_176180_f;
  }
  
  public boolean func_176174_d()
  {
    return field_176184_i;
  }
  
  public boolean func_176173_e()
  {
    return field_176183_h;
  }
}
