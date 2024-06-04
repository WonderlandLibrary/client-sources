package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.signers.DSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;

public class TlsDSSSigner extends TlsDSASigner
{
  public TlsDSSSigner() {}
  
  public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
  {
    return publicKey instanceof DSAPublicKeyParameters;
  }
  
  protected org.spongycastle.crypto.DSA createDSAImpl(short hashAlgorithm)
  {
    return new DSASigner(new HMacDSAKCalculator(TlsUtils.createHash(hashAlgorithm)));
  }
  
  protected short getSignatureAlgorithm()
  {
    return 2;
  }
}
