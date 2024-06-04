package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.RandomGenerator;

public abstract interface TlsContext
{
  public abstract RandomGenerator getNonceRandomGenerator();
  
  public abstract SecureRandom getSecureRandom();
  
  public abstract SecurityParameters getSecurityParameters();
  
  public abstract boolean isServer();
  
  public abstract ProtocolVersion getClientVersion();
  
  public abstract ProtocolVersion getServerVersion();
  
  public abstract TlsSession getResumableSession();
  
  public abstract Object getUserObject();
  
  public abstract void setUserObject(Object paramObject);
  
  public abstract byte[] exportKeyingMaterial(String paramString, byte[] paramArrayOfByte, int paramInt);
}
