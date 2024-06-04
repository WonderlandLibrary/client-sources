package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class NTRUEncryptionKeyParameters
  extends AsymmetricKeyParameter
{
  protected final NTRUEncryptionParameters params;
  
  public NTRUEncryptionKeyParameters(boolean privateKey, NTRUEncryptionParameters params)
  {
    super(privateKey);
    this.params = params;
  }
  
  public NTRUEncryptionParameters getParameters()
  {
    return params;
  }
}
