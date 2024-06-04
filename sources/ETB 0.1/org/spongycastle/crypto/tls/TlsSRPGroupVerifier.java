package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.SRP6GroupParameters;

public abstract interface TlsSRPGroupVerifier
{
  public abstract boolean accept(SRP6GroupParameters paramSRP6GroupParameters);
}
