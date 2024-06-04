package org.spongycastle.jcajce.spec;

import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.util.Arrays;













public class AEADParameterSpec
  extends IvParameterSpec
{
  private final byte[] associatedData;
  private final int macSizeInBits;
  
  public AEADParameterSpec(byte[] nonce, int macSizeInBits)
  {
    this(nonce, macSizeInBits, null);
  }
  







  public AEADParameterSpec(byte[] nonce, int macSizeInBits, byte[] associatedData)
  {
    super(nonce);
    
    this.macSizeInBits = macSizeInBits;
    this.associatedData = Arrays.clone(associatedData);
  }
  





  public int getMacSizeInBits()
  {
    return macSizeInBits;
  }
  





  public byte[] getAssociatedData()
  {
    return Arrays.clone(associatedData);
  }
  





  public byte[] getNonce()
  {
    return getIV();
  }
}
