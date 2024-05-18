package net.SliceClient.Utils;

public final class Box
{
  public final double minX;
  public final double minY;
  public final double minZ;
  public final double maxX;
  public final double maxY;
  public final double maxZ;
  
  public Box(double x, double y, double z, double x1, double y1, double z1)
  {
    minX = x;
    minY = y;
    minZ = z;
    maxX = x1;
    maxY = y1;
    maxZ = z1;
  }
}
