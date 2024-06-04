package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;

public class TlsECDSASigner extends TlsDSASigner
{
  public TlsECDSASigner() {}
  
  public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
  {
    return publicKey instanceof ECPublicKeyParameters;
  }
  
  protected org.spongycastle.crypto.DSA createDSAImpl(short hashAlgorithm)
  {
    return new ECDSASigner(new HMacDSAKCalculator(TlsUtils.createHash(hashAlgorithm)));
  }
  
  protected short getSignatureAlgorithm()
  {
    return 3;
  }
}
