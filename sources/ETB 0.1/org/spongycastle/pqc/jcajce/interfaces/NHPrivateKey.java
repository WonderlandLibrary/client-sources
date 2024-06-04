package org.spongycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

public abstract interface NHPrivateKey
  extends NHKey, PrivateKey
{
  public abstract short[] getSecretData();
}
