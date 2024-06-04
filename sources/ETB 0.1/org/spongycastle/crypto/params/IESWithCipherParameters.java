package org.spongycastle.crypto.params;







public class IESWithCipherParameters
  extends IESParameters
{
  private int cipherKeySize;
  





  public IESWithCipherParameters(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize)
  {
    super(derivation, encoding, macKeySize);
    
    this.cipherKeySize = cipherKeySize;
  }
  
  public int getCipherKeySize()
  {
    return cipherKeySize;
  }
}
