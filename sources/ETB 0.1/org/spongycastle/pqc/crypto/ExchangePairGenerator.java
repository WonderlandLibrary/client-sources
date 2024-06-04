package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface ExchangePairGenerator
{
  /**
   * @deprecated
   */
  public abstract ExchangePair GenerateExchange(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract ExchangePair generateExchange(AsymmetricKeyParameter paramAsymmetricKeyParameter);
}
