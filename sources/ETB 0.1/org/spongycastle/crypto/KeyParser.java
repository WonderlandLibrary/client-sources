package org.spongycastle.crypto;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface KeyParser
{
  public abstract AsymmetricKeyParameter readKey(InputStream paramInputStream)
    throws IOException;
}
