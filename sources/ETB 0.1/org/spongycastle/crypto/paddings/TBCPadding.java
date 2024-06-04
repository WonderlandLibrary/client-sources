package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;



















public class TBCPadding
  implements BlockCipherPadding
{
  public TBCPadding() {}
  
  public void init(SecureRandom random)
    throws IllegalArgumentException
  {}
  
  public String getPaddingName()
  {
    return "TBC";
  }
  












  public int addPadding(byte[] in, int inOff)
  {
    int count = in.length - inOff;
    byte code;
    byte code;
    if (inOff > 0)
    {
      code = (byte)((in[(inOff - 1)] & 0x1) == 0 ? 255 : 0);
    }
    else
    {
      code = (byte)((in[(in.length - 1)] & 0x1) == 0 ? 255 : 0);
    }
    
    while (inOff < in.length)
    {
      in[inOff] = code;
      inOff++;
    }
    
    return count;
  }
  



  public int padCount(byte[] in)
    throws InvalidCipherTextException
  {
    byte code = in[(in.length - 1)];
    
    int index = in.length - 1;
    while ((index > 0) && (in[(index - 1)] == code))
    {
      index--;
    }
    
    return in.length - index;
  }
}
