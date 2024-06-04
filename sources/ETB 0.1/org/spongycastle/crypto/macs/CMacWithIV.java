package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;





public class CMacWithIV
  extends CMac
{
  public CMacWithIV(BlockCipher cipher)
  {
    super(cipher);
  }
  
  public CMacWithIV(BlockCipher cipher, int macSizeInBits)
  {
    super(cipher, macSizeInBits);
  }
  
  void validate(CipherParameters params) {}
}
