package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.Digest;

public abstract interface GMSSDigestProvider
{
  public abstract Digest get();
}
