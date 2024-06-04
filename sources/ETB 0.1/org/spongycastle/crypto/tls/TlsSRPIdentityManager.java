package org.spongycastle.crypto.tls;

public abstract interface TlsSRPIdentityManager
{
  public abstract TlsSRPLoginParameters getLoginParameters(byte[] paramArrayOfByte);
}
