package org.spongycastle.crypto;

import java.math.BigInteger;

public abstract interface DSA
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract BigInteger[] generateSignature(byte[] paramArrayOfByte);
  
  public abstract boolean verifySignature(byte[] paramArrayOfByte, BigInteger paramBigInteger1, BigInteger paramBigInteger2);
}
