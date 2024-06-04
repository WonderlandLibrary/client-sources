package org.spongycastle.jce.interfaces;

import javax.crypto.interfaces.DHKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public abstract interface ElGamalKey
  extends DHKey
{
  public abstract ElGamalParameterSpec getParameters();
}
