package org.spongycastle.util.io.pem;

import java.io.IOException;

public abstract interface PemObjectParser
{
  public abstract Object parseObject(PemObject paramPemObject)
    throws IOException;
}
