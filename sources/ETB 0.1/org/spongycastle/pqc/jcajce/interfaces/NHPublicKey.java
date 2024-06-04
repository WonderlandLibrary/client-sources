package org.spongycastle.pqc.jcajce.interfaces;

import java.security.PublicKey;

public abstract interface NHPublicKey
  extends NHKey, PublicKey
{
  public abstract byte[] getPublicData();
}
