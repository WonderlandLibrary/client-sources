package org.spongycastle.jce.interfaces;

import java.security.PublicKey;
import org.spongycastle.math.ec.ECPoint;

public abstract interface ECPublicKey
  extends ECKey, PublicKey
{
  public abstract ECPoint getQ();
}
