package net.minecraft.dispenser;

public class PositionImpl implements IPosition
{
  protected final double x;
  protected final double y;
  protected final double z;
  private static final String __OBFID = "CL_00001208";
  
  public PositionImpl(double xCoord, double yCoord, double zCoord)
  {
    x = xCoord;
    y = yCoord;
    z = zCoord;
  }
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public double getZ()
  {
    return z;
  }
}
