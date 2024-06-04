package org.spongycastle.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;












public class AsymmetricCipherKeyPair
{
  private AsymmetricKeyParameter publicParam;
  private AsymmetricKeyParameter privateParam;
  
  public AsymmetricCipherKeyPair(AsymmetricKeyParameter publicParam, AsymmetricKeyParameter privateParam)
  {
    this.publicParam = publicParam;
    this.privateParam = privateParam;
  }
  






  /**
   * @deprecated
   */
  public AsymmetricCipherKeyPair(CipherParameters publicParam, CipherParameters privateParam)
  {
    this.publicParam = ((AsymmetricKeyParameter)publicParam);
    this.privateParam = ((AsymmetricKeyParameter)privateParam);
  }
  





  public AsymmetricKeyParameter getPublic()
  {
    return publicParam;
  }
  





  public AsymmetricKeyParameter getPrivate()
  {
    return privateParam;
  }
}
