package org.spongycastle.crypto;

import java.math.BigInteger;

public abstract interface BasicAgreement
{
  public abstract void init(CipherParameters paramCipherParameters);
  
  public abstract int getFieldSize();
  
  public abstract BigInteger calculateAgreement(CipherParameters paramCipherParameters);
}
