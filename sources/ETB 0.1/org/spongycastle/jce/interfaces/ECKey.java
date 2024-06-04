package org.spongycastle.jce.interfaces;

import org.spongycastle.jce.spec.ECParameterSpec;

public abstract interface ECKey
{
  public abstract ECParameterSpec getParameters();
}
