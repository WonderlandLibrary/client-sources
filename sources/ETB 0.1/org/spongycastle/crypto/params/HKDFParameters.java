package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.util.Arrays;





public class HKDFParameters
  implements DerivationParameters
{
  private final byte[] ikm;
  private final boolean skipExpand;
  private final byte[] salt;
  private final byte[] info;
  
  private HKDFParameters(byte[] ikm, boolean skip, byte[] salt, byte[] info)
  {
    if (ikm == null)
    {
      throw new IllegalArgumentException("IKM (input keying material) should not be null");
    }
    

    this.ikm = Arrays.clone(ikm);
    
    skipExpand = skip;
    
    if ((salt == null) || (salt.length == 0))
    {
      this.salt = null;
    }
    else
    {
      this.salt = Arrays.clone(salt);
    }
    
    if (info == null)
    {
      this.info = new byte[0];
    }
    else
    {
      this.info = Arrays.clone(info);
    }
  }
  








  public HKDFParameters(byte[] ikm, byte[] salt, byte[] info)
  {
    this(ikm, false, salt, info);
  }
  











  public static HKDFParameters skipExtractParameters(byte[] ikm, byte[] info)
  {
    return new HKDFParameters(ikm, true, null, info);
  }
  
  public static HKDFParameters defaultParameters(byte[] ikm)
  {
    return new HKDFParameters(ikm, false, null, null);
  }
  





  public byte[] getIKM()
  {
    return Arrays.clone(ikm);
  }
  





  public boolean skipExtract()
  {
    return skipExpand;
  }
  






  public byte[] getSalt()
  {
    return Arrays.clone(salt);
  }
  





  public byte[] getInfo()
  {
    return Arrays.clone(info);
  }
}
