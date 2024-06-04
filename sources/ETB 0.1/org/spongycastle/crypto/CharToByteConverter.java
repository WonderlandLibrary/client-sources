package org.spongycastle.crypto;

public abstract interface CharToByteConverter
{
  public abstract String getType();
  
  public abstract byte[] convert(char[] paramArrayOfChar);
}
