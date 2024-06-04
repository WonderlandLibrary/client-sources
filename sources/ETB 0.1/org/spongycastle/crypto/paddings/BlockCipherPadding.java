package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public abstract interface BlockCipherPadding
{
  public abstract void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException;
  
  public abstract String getPaddingName();
  
  public abstract int addPadding(byte[] paramArrayOfByte, int paramInt);
  
  public abstract int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException;
}
