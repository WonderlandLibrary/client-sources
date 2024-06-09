package lunadevs.luna.utils.faithsminiutils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils {

	private static final Random rng = new Random();
	  
	  public static float clampValue(float value, float floor, float cap)
	  {
	    if (value < floor) {
	      return floor;
	    }
	    if (value > cap) {
	      return cap;
	    }
	    return value;
	  }
	  
	  public static double round(double value, int places)
	  {
	    if (places < 0) {
	      throw new IllegalArgumentException();
	    }
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	  }
	  
	  public static int customRandInt(int min, int max)
	  {
	    return new Random().nextInt(max - min + 1) + min;
	  }
	  
	  private static StringBuffer getCommonCharacters(String string1, String string2, int distanceSep)
	  {
	    StringBuffer returnCommons = new StringBuffer();
	    
	    StringBuffer copy = new StringBuffer(string2);
	    
	    int n = string1.length();
	    int m = string2.length();
	    for (int i = 0; i < n; i++)
	    {
	      char ch = string1.charAt(i);
	      
	      boolean foundIt = false;
	      for (int j = Math.max(0, i - distanceSep); (!foundIt) && (j < Math.min(i + distanceSep, m - 1)); j++) {
	        if (copy.charAt(j) == ch)
	        {
	          foundIt = true;
	          
	          returnCommons.append(ch);
	          
	          copy.setCharAt(j, '\000');
	        }
	      }
	    }
	    return returnCommons;
	  }
	  
	  public static float getRandom()
	  {
	    return rng.nextFloat();
	  }
	  
	  public static int getRandom(int cap)
	  {
	    return rng.nextInt(cap);
	  }
	  
	  public static int getRandom(int floor, int cap)
	  {
	    return floor + rng.nextInt(cap - floor + 1);
	  }
	  
	  public static Random getRng()
	  {
	    return rng;
	  }
	  
	  public static float getSimilarity(String string1, String string2)
	  {
	    int halflen = Math.min(string1.length(), string2.length()) / 2 + 
	      Math.min(string1.length(), string2.length()) % 2;
	    
	    StringBuffer common1 = getCommonCharacters(string1, string2, halflen);
	    StringBuffer common2 = getCommonCharacters(string2, string1, halflen);
	    if ((common1.length() == 0) || (common2.length() == 0)) {
	      return 0.0F;
	    }
	    if (common1.length() != common2.length()) {
	      return 0.0F;
	    }
	    int transpositions = 0;
	    int n = common1.length();
	    for (int i = 0; i < n; i++) {
	      if (common1.charAt(i) != common2.charAt(i)) {
	        transpositions++;
	      }
	    }
	    transpositions = (int)(transpositions / 2.0F);
	    
	    return (common1.length() / string1.length() + common2.length() / string2.length() + 
	      (common1.length() - transpositions) / common1.length()) / 3.0F;
	  }
	  
	  public static int randInt(int min, int max)
	  {
	    return new Random().nextInt(max - min + 1) + min;
	  }
	
}
