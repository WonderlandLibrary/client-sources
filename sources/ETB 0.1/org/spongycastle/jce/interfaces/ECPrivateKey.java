package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public abstract interface ECPrivateKey
  extends ECKey, PrivateKey
{
  public abstract BigInteger getD();
}
