package org.spongycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.math.BigInteger;

public abstract interface DSAEncoder
{
  public abstract byte[] encode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
    throws IOException;
  
  public abstract BigInteger[] decode(byte[] paramArrayOfByte)
    throws IOException;
}
