package org.spongycastle.jce.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public abstract interface MQVPrivateKey
  extends PrivateKey
{
  public abstract PrivateKey getStaticPrivateKey();
  
  public abstract PrivateKey getEphemeralPrivateKey();
  
  public abstract PublicKey getEphemeralPublicKey();
}
