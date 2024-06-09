package lunadevs.luna.utils;

import java.util.Random;

public class MathUtil
{
  public static int getMiddle(int i, int i1)
  {
    return (i + i1) / 2;
  }
  
  public static double getMiddleint(double d, double e)
  {
    return (d + e) / 2.0D;
  }
  
  public static float getAngleDifference(float direction, float rotationYaw)
  {
    float phi = Math.abs(rotationYaw - direction) % 360.0F;
    float distance = phi > 180.0F ? 360.0F - phi : phi;
    return distance;
  }
  
  public static int getRandomInRange(int min, int max)
  {
    Random rand = new Random();
    int randomNum = rand.nextInt(max - min + 1) + min;
    
    return randomNum;
  }
  
  public static double getRandomInRange(double min, double max)
  {
    Random random = new Random();
    double range = max - min;
    double scaled = random.nextDouble() * range;
    double shifted = scaled + min;
    return shifted;
  }
  
  public static float getRandomInRange(float min, float max)
  {
    Random random = new Random();
    float range = max - min;
    float scaled = random.nextFloat() * range;
    float shifted = scaled + min;
    return shifted;
  }
}
