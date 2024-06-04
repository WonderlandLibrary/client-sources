package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public abstract interface GOST3410PublicKey
  extends GOST3410Key, PublicKey
{
  public abstract BigInteger getY();
}
