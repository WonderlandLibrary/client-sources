package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract class AbstractTlsSigner implements TlsSigner
{
  protected TlsContext context;
  
  public AbstractTlsSigner() {}
  
  public void init(TlsContext context)
  {
    this.context = context;
  }
  
  public byte[] generateRawSignature(AsymmetricKeyParameter privateKey, byte[] md5AndSha1)
    throws CryptoException
  {
    return generateRawSignature(null, privateKey, md5AndSha1);
  }
  
  public boolean verifyRawSignature(byte[] sigBytes, AsymmetricKeyParameter publicKey, byte[] md5AndSha1)
    throws CryptoException
  {
    return verifyRawSignature(null, sigBytes, publicKey, md5AndSha1);
  }
  
  public org.spongycastle.crypto.Signer createSigner(AsymmetricKeyParameter privateKey)
  {
    return createSigner(null, privateKey);
  }
  
  public org.spongycastle.crypto.Signer createVerifyer(AsymmetricKeyParameter publicKey)
  {
    return createVerifyer(null, publicKey);
  }
}
