package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsCipher
{
  public abstract int getPlaintextLimit(int paramInt);
  
  public abstract byte[] encodePlaintext(long paramLong, short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract byte[] decodeCiphertext(long paramLong, short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
}
