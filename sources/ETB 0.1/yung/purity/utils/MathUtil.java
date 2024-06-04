package yung.purity.utils;

import net.minecraft.util.MathHelper;





public class MathUtil
{
  public MathUtil() {}
  
  public static double toDecimalLength(double in, int places)
  {
    return Double.parseDouble(String.format("%." + places + "f", new Object[] { Double.valueOf(in) }));
  }
  


  public static double round(double in, int places)
  {
    places = (int)MathHelper.clamp_double(places, 0.0D, 2.147483647E9D);
    return Double.parseDouble(String.format("%." + places + "f", new Object[] { Double.valueOf(in) }));
  }
  

  public static boolean parsable(String s, byte type)
  {
    try
    {
      switch (type) {
      case 0: 
        Short.parseShort(s);
        break;
      case 1: 
        Byte.parseByte(s);
        break;
      case 2: 
        Integer.parseInt(s);
        break;
      case 3: 
        Float.parseFloat(s);
        break;
      case 4: 
        Double.parseDouble(s);
        break;
      case 5: 
        Long.parseLong(s);
      }
    }
    catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  public static class NumberType {
    public static final byte SHORT = 0;
    public static final byte BYTE = 1;
    public static final byte INT = 2;
    
    public NumberType() {}
    
    public static byte getByType(Class cls) { if (cls == Short.class)
        return 0;
      if (cls == Byte.class)
        return 1;
      if (cls == Integer.class)
        return 2;
      if (cls == Float.class)
        return 3;
      if (cls == Double.class)
        return 4;
      if (cls == Long.class) {
        return 5;
      }
      return -1;
    }
    
    public static final byte FLOAT = 3;
    public static final byte DOUBLE = 4;
    public static final byte LONG = 5;
  }
}
