package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

public abstract interface DSAKCalculator
{
  public abstract boolean isDeterministic();
  
  public abstract void init(BigInteger paramBigInteger, SecureRandom paramSecureRandom);
  
  public abstract void init(BigInteger paramBigInteger1, BigInteger paramBigInteger2, byte[] paramArrayOfByte);
  
  public abstract BigInteger nextK();
}
