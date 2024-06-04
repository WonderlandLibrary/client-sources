package org.spongycastle.crypto.ec;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.math.ec.ECPoint;

public abstract interface ECDecryptor
{
  public abstract void init(CipherParameters paramCipherParameters);
  
  public abstract ECPoint decrypt(ECPair paramECPair);
}
