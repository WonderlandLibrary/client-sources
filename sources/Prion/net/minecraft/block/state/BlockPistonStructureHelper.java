package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPistonStructureHelper
{
  private final World field_177261_a;
  private final BlockPos field_177259_b;
  private final BlockPos field_177260_c;
  private final EnumFacing field_177257_d;
  private final List field_177258_e = Lists.newArrayList();
  private final List field_177256_f = Lists.newArrayList();
  private static final String __OBFID = "CL_00002033";
  
  public BlockPistonStructureHelper(World worldIn, BlockPos p_i45664_2_, EnumFacing p_i45664_3_, boolean p_i45664_4_)
  {
    field_177261_a = worldIn;
    field_177259_b = p_i45664_2_;
    
    if (p_i45664_4_)
    {
      field_177257_d = p_i45664_3_;
      field_177260_c = p_i45664_2_.offset(p_i45664_3_);
    }
    else
    {
      field_177257_d = p_i45664_3_.getOpposite();
      field_177260_c = p_i45664_2_.offset(p_i45664_3_, 2);
    }
  }
  
  public boolean func_177253_a()
  {
    field_177258_e.clear();
    field_177256_f.clear();
    Block var1 = field_177261_a.getBlockState(field_177260_c).getBlock();
    
    if (!BlockPistonBase.func_180696_a(var1, field_177261_a, field_177260_c, field_177257_d, false))
    {
      if (var1.getMobilityFlag() != 1)
      {
        return false;
      }
      

      field_177256_f.add(field_177260_c);
      return true;
    }
    
    if (!func_177251_a(field_177260_c))
    {
      return false;
    }
    

    for (int var2 = 0; var2 < field_177258_e.size(); var2++)
    {
      BlockPos var3 = (BlockPos)field_177258_e.get(var2);
      
      if ((field_177261_a.getBlockState(var3).getBlock() == Blocks.slime_block) && (!func_177250_b(var3)))
      {
        return false;
      }
    }
    
    return true;
  }
  

  private boolean func_177251_a(BlockPos p_177251_1_)
  {
    Block var2 = field_177261_a.getBlockState(p_177251_1_).getBlock();
    
    if (var2.getMaterial() == Material.air)
    {
      return true;
    }
    if (!BlockPistonBase.func_180696_a(var2, field_177261_a, p_177251_1_, field_177257_d, false))
    {
      return true;
    }
    if (p_177251_1_.equals(field_177259_b))
    {
      return true;
    }
    if (field_177258_e.contains(p_177251_1_))
    {
      return true;
    }
    

    int var3 = 1;
    
    if (var3 + field_177258_e.size() > 12)
    {
      return false;
    }
    

    while (var2 == Blocks.slime_block)
    {
      BlockPos var4 = p_177251_1_.offset(field_177257_d.getOpposite(), var3);
      var2 = field_177261_a.getBlockState(var4).getBlock();
      
      if ((var2.getMaterial() == Material.air) || (!BlockPistonBase.func_180696_a(var2, field_177261_a, var4, field_177257_d, false)) || (var4.equals(field_177259_b))) {
        break;
      }
      

      var3++;
      
      if (var3 + field_177258_e.size() > 12)
      {
        return false;
      }
    }
    
    int var10 = 0;
    

    for (int var5 = var3 - 1; var5 >= 0; var5--)
    {
      field_177258_e.add(p_177251_1_.offset(field_177257_d.getOpposite(), var5));
      var10++;
    }
    
    var5 = 1;
    
    for (;;)
    {
      BlockPos var6 = p_177251_1_.offset(field_177257_d, var5);
      int var7 = field_177258_e.indexOf(var6);
      
      if (var7 > -1)
      {
        func_177255_a(var10, var7);
        
        for (int var8 = 0; var8 <= var7 + var10; var8++)
        {
          BlockPos var9 = (BlockPos)field_177258_e.get(var8);
          
          if ((field_177261_a.getBlockState(var9).getBlock() == Blocks.slime_block) && (!func_177250_b(var9)))
          {
            return false;
          }
        }
        
        return true;
      }
      
      var2 = field_177261_a.getBlockState(var6).getBlock();
      
      if (var2.getMaterial() == Material.air)
      {
        return true;
      }
      
      if ((!BlockPistonBase.func_180696_a(var2, field_177261_a, var6, field_177257_d, true)) || (var6.equals(field_177259_b)))
      {
        return false;
      }
      
      if (var2.getMobilityFlag() == 1)
      {
        field_177256_f.add(var6);
        return true;
      }
      
      if (field_177258_e.size() >= 12)
      {
        return false;
      }
      
      field_177258_e.add(var6);
      var10++;
      var5++;
    }
  }
  


  private void func_177255_a(int p_177255_1_, int p_177255_2_)
  {
    ArrayList var3 = Lists.newArrayList();
    ArrayList var4 = Lists.newArrayList();
    ArrayList var5 = Lists.newArrayList();
    var3.addAll(field_177258_e.subList(0, p_177255_2_));
    var4.addAll(field_177258_e.subList(field_177258_e.size() - p_177255_1_, field_177258_e.size()));
    var5.addAll(field_177258_e.subList(p_177255_2_, field_177258_e.size() - p_177255_1_));
    field_177258_e.clear();
    field_177258_e.addAll(var3);
    field_177258_e.addAll(var4);
    field_177258_e.addAll(var5);
  }
  
  private boolean func_177250_b(BlockPos p_177250_1_)
  {
    EnumFacing[] var2 = EnumFacing.values();
    int var3 = var2.length;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      EnumFacing var5 = var2[var4];
      
      if ((var5.getAxis() != field_177257_d.getAxis()) && (!func_177251_a(p_177250_1_.offset(var5))))
      {
        return false;
      }
    }
    
    return true;
  }
  
  public List func_177254_c()
  {
    return field_177258_e;
  }
  
  public List func_177252_d()
  {
    return field_177256_f;
  }
}
