package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.prng.drbg.SP80090DRBG;

abstract interface DRBGProvider
{
  public abstract SP80090DRBG get(EntropySource paramEntropySource);
}
