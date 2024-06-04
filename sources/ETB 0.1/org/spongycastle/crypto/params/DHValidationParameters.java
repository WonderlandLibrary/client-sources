package org.spongycastle.crypto.params;

import org.spongycastle.util.Arrays;



public class DHValidationParameters
{
  private byte[] seed;
  private int counter;
  
  public DHValidationParameters(byte[] seed, int counter)
  {
    this.seed = seed;
    this.counter = counter;
  }
  
  public int getCounter()
  {
    return counter;
  }
  
  public byte[] getSeed()
  {
    return seed;
  }
  

  public boolean equals(Object o)
  {
    if (!(o instanceof DHValidationParameters))
    {
      return false;
    }
    
    DHValidationParameters other = (DHValidationParameters)o;
    
    if (counter != counter)
    {
      return false;
    }
    
    return Arrays.areEqual(seed, seed);
  }
  
  public int hashCode()
  {
    return counter ^ Arrays.hashCode(seed);
  }
}
