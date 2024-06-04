package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;

public abstract class EntityAIMoveToBlock extends EntityAIBase
{
  private final EntityCreature field_179495_c;
  private final double field_179492_d;
  protected int field_179496_a;
  private int field_179493_e;
  private int field_179490_f;
  protected BlockPos field_179494_b;
  private boolean field_179491_g;
  private int field_179497_h;
  private static final String __OBFID = "CL_00002252";
  
  public EntityAIMoveToBlock(EntityCreature p_i45888_1_, double p_i45888_2_, int p_i45888_4_)
  {
    field_179494_b = BlockPos.ORIGIN;
    field_179495_c = p_i45888_1_;
    field_179492_d = p_i45888_2_;
    field_179497_h = p_i45888_4_;
    setMutexBits(5);
  }
  



  public boolean shouldExecute()
  {
    if (field_179496_a > 0)
    {
      field_179496_a -= 1;
      return false;
    }
    

    field_179496_a = (200 + field_179495_c.getRNG().nextInt(200));
    return func_179489_g();
  }
  




  public boolean continueExecuting()
  {
    return (field_179493_e >= -field_179490_f) && (field_179493_e <= 1200) && (func_179488_a(field_179495_c.worldObj, field_179494_b));
  }
  



  public void startExecuting()
  {
    field_179495_c.getNavigator().tryMoveToXYZ(field_179494_b.getX() + 0.5D, field_179494_b.getY() + 1, field_179494_b.getZ() + 0.5D, field_179492_d);
    field_179493_e = 0;
    field_179490_f = (field_179495_c.getRNG().nextInt(field_179495_c.getRNG().nextInt(1200) + 1200) + 1200);
  }
  



  public void resetTask() {}
  



  public void updateTask()
  {
    if (field_179495_c.func_174831_c(field_179494_b.offsetUp()) > 1.0D)
    {
      field_179491_g = false;
      field_179493_e += 1;
      
      if (field_179493_e % 40 == 0)
      {
        field_179495_c.getNavigator().tryMoveToXYZ(field_179494_b.getX() + 0.5D, field_179494_b.getY() + 1, field_179494_b.getZ() + 0.5D, field_179492_d);
      }
    }
    else
    {
      field_179491_g = true;
      field_179493_e -= 1;
    }
  }
  
  protected boolean func_179487_f()
  {
    return field_179491_g;
  }
  
  private boolean func_179489_g()
  {
    int var1 = field_179497_h;
    boolean var2 = true;
    BlockPos var3 = new BlockPos(field_179495_c);
    
    for (int var4 = 0; var4 <= 1; var4 = var4 > 0 ? -var4 : 1 - var4)
    {
      for (int var5 = 0; var5 < var1; var5++)
      {
        for (int var6 = 0; var6 <= var5; var6 = var6 > 0 ? -var6 : 1 - var6)
        {
          for (int var7 = (var6 < var5) && (var6 > -var5) ? var5 : 0; var7 <= var5; var7 = var7 > 0 ? -var7 : 1 - var7)
          {
            BlockPos var8 = var3.add(var6, var4 - 1, var7);
            
            if ((field_179495_c.func_180485_d(var8)) && (func_179488_a(field_179495_c.worldObj, var8)))
            {
              field_179494_b = var8;
              return true;
            }
          }
        }
      }
    }
    
    return false;
  }
  
  protected abstract boolean func_179488_a(net.minecraft.world.World paramWorld, BlockPos paramBlockPos);
}
