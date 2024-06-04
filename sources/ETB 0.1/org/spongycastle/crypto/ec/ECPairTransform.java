package org.spongycastle.crypto.ec;

import org.spongycastle.crypto.CipherParameters;

public abstract interface ECPairTransform
{
  public abstract void init(CipherParameters paramCipherParameters);
  
  public abstract ECPair transform(ECPair paramECPair);
}
