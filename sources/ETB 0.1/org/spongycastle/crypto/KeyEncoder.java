package org.spongycastle.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface KeyEncoder
{
  public abstract byte[] getEncoded(AsymmetricKeyParameter paramAsymmetricKeyParameter);
}
