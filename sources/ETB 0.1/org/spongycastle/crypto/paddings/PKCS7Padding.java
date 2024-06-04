package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;















public class PKCS7Padding
  implements BlockCipherPadding
{
  public PKCS7Padding() {}
  
  public void init(SecureRandom random)
    throws IllegalArgumentException
  {}
  
  public String getPaddingName()
  {
    return "PKCS7";
  }
  






  public int addPadding(byte[] in, int inOff)
  {
    byte code = (byte)(in.length - inOff);
    
    while (inOff < in.length)
    {
      in[inOff] = code;
      inOff++;
    }
    
    return code;
  }
  



  public int padCount(byte[] in)
    throws InvalidCipherTextException
  {
    int count = in[(in.length - 1)] & 0xFF;
    byte countAsbyte = (byte)count;
    

    boolean failed = (count > in.length ? 1 : 0) | (count == 0 ? 1 : 0);
    
    for (int i = 0; i < in.length; i++)
    {
      failed |= (in.length - i <= count ? 1 : 0) & (in[i] != countAsbyte ? 1 : 0);
    }
    
    if (failed)
    {
      throw new InvalidCipherTextException("pad block corrupted");
    }
    
    return count;
  }
}
