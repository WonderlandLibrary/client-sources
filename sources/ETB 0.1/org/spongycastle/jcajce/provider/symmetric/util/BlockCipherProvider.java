package org.spongycastle.jcajce.provider.symmetric.util;

import org.spongycastle.crypto.BlockCipher;

public abstract interface BlockCipherProvider
{
  public abstract BlockCipher get();
}
