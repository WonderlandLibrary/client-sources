package org.spongycastle.util;

import java.io.IOException;

public abstract interface Encodable
{
  public abstract byte[] getEncoded()
    throws IOException;
}
