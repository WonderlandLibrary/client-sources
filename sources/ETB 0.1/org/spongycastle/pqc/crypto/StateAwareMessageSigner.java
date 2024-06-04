package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface StateAwareMessageSigner
  extends MessageSigner
{
  public abstract AsymmetricKeyParameter getUpdatedPrivateKey();
}
