package optifine;

import net.minecraft.util.Vec3;

public class CustomColorFader
{
  private Vec3 color = null;
  private long timeUpdate = System.currentTimeMillis();
  
  public CustomColorFader() {}
  
  public Vec3 getColor(double x, double y, double z) { if (color == null)
    {
      color = new Vec3(x, y, z);
      return color;
    }
    

    long timeNow = System.currentTimeMillis();
    long timeDiff = timeNow - timeUpdate;
    
    if (timeDiff == 0L)
    {
      return color;
    }
    

    timeUpdate = timeNow;
    
    if ((Math.abs(x - color.xCoord) < 0.004D) && (Math.abs(y - color.yCoord) < 0.004D) && (Math.abs(z - color.zCoord) < 0.004D))
    {
      return color;
    }
    

    double k = timeDiff * 0.001D;
    k = Config.limit(k, 0.0D, 1.0D);
    double dx = x - color.xCoord;
    double dy = y - color.yCoord;
    double dz = z - color.zCoord;
    double xn = color.xCoord + dx * k;
    double yn = color.yCoord + dy * k;
    double zn = color.zCoord + dz * k;
    color = new Vec3(xn, yn, zn);
    return color;
  }
}
