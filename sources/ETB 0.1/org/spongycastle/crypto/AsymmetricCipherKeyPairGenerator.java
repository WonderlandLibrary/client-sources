package org.spongycastle.crypto;

public abstract interface AsymmetricCipherKeyPairGenerator
{
  public abstract void init(KeyGenerationParameters paramKeyGenerationParameters);
  
  public abstract AsymmetricCipherKeyPair generateKeyPair();
}
