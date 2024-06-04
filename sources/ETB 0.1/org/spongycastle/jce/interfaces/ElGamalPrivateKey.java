package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPrivateKey;

public abstract interface ElGamalPrivateKey
  extends ElGamalKey, DHPrivateKey
{
  public abstract BigInteger getX();
}
