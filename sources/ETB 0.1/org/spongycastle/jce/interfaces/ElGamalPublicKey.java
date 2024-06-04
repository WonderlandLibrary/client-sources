package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;

public abstract interface ElGamalPublicKey
  extends ElGamalKey, DHPublicKey
{
  public abstract BigInteger getY();
}
