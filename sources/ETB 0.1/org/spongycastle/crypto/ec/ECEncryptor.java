package org.spongycastle.crypto.ec;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.math.ec.ECPoint;

public abstract interface ECEncryptor
{
  public abstract void init(CipherParameters paramCipherParameters);
  
  public abstract ECPair encrypt(ECPoint paramECPoint);
}
