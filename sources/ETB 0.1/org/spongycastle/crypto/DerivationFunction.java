package org.spongycastle.crypto;

public abstract interface DerivationFunction
{
  public abstract void init(DerivationParameters paramDerivationParameters);
  
  public abstract int generateBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalArgumentException;
}
