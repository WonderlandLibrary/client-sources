package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;












public class IESParameters
  implements CipherParameters
{
  private byte[] derivation;
  private byte[] encoding;
  private int macKeySize;
  
  public IESParameters(byte[] derivation, byte[] encoding, int macKeySize)
  {
    this.derivation = derivation;
    this.encoding = encoding;
    this.macKeySize = macKeySize;
  }
  
  public byte[] getDerivationV()
  {
    return derivation;
  }
  
  public byte[] getEncodingV()
  {
    return encoding;
  }
  
  public int getMacKeySize()
  {
    return macKeySize;
  }
}
