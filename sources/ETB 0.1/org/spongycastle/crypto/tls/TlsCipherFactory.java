package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsCipherFactory
{
  public abstract TlsCipher createCipher(TlsContext paramTlsContext, int paramInt1, int paramInt2)
    throws IOException;
}
