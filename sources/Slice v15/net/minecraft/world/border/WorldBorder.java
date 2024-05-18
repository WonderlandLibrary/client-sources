package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldBorder
{
  private final List listeners = Lists.newArrayList();
  private double centerX = 0.0D;
  private double centerZ = 0.0D;
  private double startDiameter = 6.0E7D;
  private double endDiameter;
  private long endTime;
  private long startTime;
  private int worldSize;
  private double damageAmount;
  private double damageBuffer;
  private int warningTime;
  private int warningDistance;
  private static final String __OBFID = "CL_00002012";
  
  public WorldBorder()
  {
    endDiameter = startDiameter;
    worldSize = 29999984;
    damageAmount = 0.2D;
    damageBuffer = 5.0D;
    warningTime = 15;
    warningDistance = 5;
  }
  
  public boolean contains(BlockPos pos)
  {
    return (pos.getX() + 1 > minX()) && (pos.getX() < maxX()) && (pos.getZ() + 1 > minZ()) && (pos.getZ() < maxZ());
  }
  
  public boolean contains(ChunkCoordIntPair range)
  {
    return (range.getXEnd() > minX()) && (range.getXStart() < maxX()) && (range.getZEnd() > minZ()) && (range.getZStart() < maxZ());
  }
  
  public boolean contains(AxisAlignedBB bb)
  {
    return (maxX > minX()) && (minX < maxX()) && (maxZ > minZ()) && (minZ < maxZ());
  }
  
  public double getClosestDistance(Entity p_177745_1_)
  {
    return getClosestDistance(posX, posZ);
  }
  
  public double getClosestDistance(double x, double z)
  {
    double var5 = z - minZ();
    double var7 = maxZ() - z;
    double var9 = x - minX();
    double var11 = maxX() - x;
    double var13 = Math.min(var9, var11);
    var13 = Math.min(var13, var5);
    return Math.min(var13, var7);
  }
  
  public EnumBorderStatus getStatus()
  {
    return endDiameter > startDiameter ? EnumBorderStatus.GROWING : endDiameter < startDiameter ? EnumBorderStatus.SHRINKING : EnumBorderStatus.STATIONARY;
  }
  
  public double minX()
  {
    double var1 = getCenterX() - getDiameter() / 2.0D;
    
    if (var1 < -worldSize)
    {
      var1 = -worldSize;
    }
    
    return var1;
  }
  
  public double minZ()
  {
    double var1 = getCenterZ() - getDiameter() / 2.0D;
    
    if (var1 < -worldSize)
    {
      var1 = -worldSize;
    }
    
    return var1;
  }
  
  public double maxX()
  {
    double var1 = getCenterX() + getDiameter() / 2.0D;
    
    if (var1 > worldSize)
    {
      var1 = worldSize;
    }
    
    return var1;
  }
  
  public double maxZ()
  {
    double var1 = getCenterZ() + getDiameter() / 2.0D;
    
    if (var1 > worldSize)
    {
      var1 = worldSize;
    }
    
    return var1;
  }
  
  public double getCenterX()
  {
    return centerX;
  }
  
  public double getCenterZ()
  {
    return centerZ;
  }
  
  public void setCenter(double x, double z)
  {
    centerX = x;
    centerZ = z;
    Iterator var5 = getListeners().iterator();
    
    while (var5.hasNext())
    {
      IBorderListener var6 = (IBorderListener)var5.next();
      var6.onCenterChanged(this, x, z);
    }
  }
  
  public double getDiameter()
  {
    if (getStatus() != EnumBorderStatus.STATIONARY)
    {
      double var1 = (float)(System.currentTimeMillis() - startTime) / (float)(endTime - startTime);
      
      if (var1 < 1.0D)
      {
        return startDiameter + (endDiameter - startDiameter) * var1;
      }
      
      setTransition(endDiameter);
    }
    
    return startDiameter;
  }
  
  public long getTimeUntilTarget()
  {
    return getStatus() != EnumBorderStatus.STATIONARY ? endTime - System.currentTimeMillis() : 0L;
  }
  
  public double getTargetSize()
  {
    return endDiameter;
  }
  
  public void setTransition(double newSize)
  {
    startDiameter = newSize;
    endDiameter = newSize;
    endTime = System.currentTimeMillis();
    startTime = endTime;
    Iterator var3 = getListeners().iterator();
    
    while (var3.hasNext())
    {
      IBorderListener var4 = (IBorderListener)var3.next();
      var4.onSizeChanged(this, newSize);
    }
  }
  
  public void setTransition(double p_177738_1_, double p_177738_3_, long p_177738_5_)
  {
    startDiameter = p_177738_1_;
    endDiameter = p_177738_3_;
    startTime = System.currentTimeMillis();
    endTime = (startTime + p_177738_5_);
    Iterator var7 = getListeners().iterator();
    
    while (var7.hasNext())
    {
      IBorderListener var8 = (IBorderListener)var7.next();
      var8.func_177692_a(this, p_177738_1_, p_177738_3_, p_177738_5_);
    }
  }
  
  protected List getListeners()
  {
    return Lists.newArrayList(listeners);
  }
  
  public void addListener(IBorderListener listener)
  {
    listeners.add(listener);
  }
  
  public void setSize(int size)
  {
    worldSize = size;
  }
  
  public int getSize()
  {
    return worldSize;
  }
  
  public double getDamageBuffer()
  {
    return damageBuffer;
  }
  
  public void setDamageBuffer(double p_177724_1_)
  {
    damageBuffer = p_177724_1_;
    Iterator var3 = getListeners().iterator();
    
    while (var3.hasNext())
    {
      IBorderListener var4 = (IBorderListener)var3.next();
      var4.func_177695_c(this, p_177724_1_);
    }
  }
  
  public double func_177727_n()
  {
    return damageAmount;
  }
  
  public void func_177744_c(double p_177744_1_)
  {
    damageAmount = p_177744_1_;
    Iterator var3 = getListeners().iterator();
    
    while (var3.hasNext())
    {
      IBorderListener var4 = (IBorderListener)var3.next();
      var4.func_177696_b(this, p_177744_1_);
    }
  }
  
  public double func_177749_o()
  {
    return endTime == startTime ? 0.0D : Math.abs(startDiameter - endDiameter) / (endTime - startTime);
  }
  
  public int getWarningTime()
  {
    return warningTime;
  }
  
  public void setWarningTime(int warningTime)
  {
    this.warningTime = warningTime;
    Iterator var2 = getListeners().iterator();
    
    while (var2.hasNext())
    {
      IBorderListener var3 = (IBorderListener)var2.next();
      var3.onWarningTimeChanged(this, warningTime);
    }
  }
  
  public int getWarningDistance()
  {
    return warningDistance;
  }
  
  public void setWarningDistance(int warningDistance)
  {
    this.warningDistance = warningDistance;
    Iterator var2 = getListeners().iterator();
    
    while (var2.hasNext())
    {
      IBorderListener var3 = (IBorderListener)var2.next();
      var3.onWarningDistanceChanged(this, warningDistance);
    }
  }
}
