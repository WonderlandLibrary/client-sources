package com.darkcart.xdolf.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import net.minecraft.util.math.MathHelper;

public class MathUtils
{
  private static final Random rng = new Random();
  
  public static double round(double value, int places)
  {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
  
  public static Random getRng()
  {
    return rng;
  }
  public static int clamp(int num, int min, int max)
	{
		return num < min ? min : num > max ? max : num;
	}
	
	public static float clamp(float num, float min, float max)
	{
		return num < min ? min : num > max ? max : num;
	}
	
	public static double clamp(double num, double min, double max)
	{
		return num < min ? min : num > max ? max : num;
	}
	
	public static float sin(float value)
	{
		return MathHelper.sin(value);
	}
	
	public static float cos(float value)
	{
		return MathHelper.cos(value);
	}
  public static float wrapDegrees(float value)
	{
		return MathHelper.wrapDegrees(value);
	}
	
	public static double wrapDegrees(double value)
	{
		return MathHelper.wrapDegrees(value);
}
  
  public static int customRandInt(int min, int max)
  {
    return new Random().nextInt(max - min + 1) + min;
  }
  
  public static int getRandom(int cap)
  {
    return rng.nextInt(cap);
  }
  
  public static int getRandom(int floor, int cap)
  {
    return floor + rng.nextInt(cap - floor + 1);
  }
  
  public static double roundToPlace(double value, int places)
  {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}