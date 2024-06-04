package org.spongycastle.jce.interfaces;

import java.security.PublicKey;

public abstract interface MQVPublicKey
  extends PublicKey
{
  public abstract PublicKey getStaticKey();
  
  public abstract PublicKey getEphemeralKey();
}
