package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public abstract interface GOST3410PrivateKey
  extends GOST3410Key, PrivateKey
{
  public abstract BigInteger getX();
}
