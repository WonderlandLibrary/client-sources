package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;


















public class ECDHCBasicAgreement
  implements BasicAgreement
{
  ECPrivateKeyParameters key;
  
  public ECDHCBasicAgreement() {}
  
  public void init(CipherParameters key)
  {
    this.key = ((ECPrivateKeyParameters)key);
  }
  
  public int getFieldSize()
  {
    return (key.getParameters().getCurve().getFieldSize() + 7) / 8;
  }
  

  public BigInteger calculateAgreement(CipherParameters pubKey)
  {
    ECPublicKeyParameters pub = (ECPublicKeyParameters)pubKey;
    ECDomainParameters params = pub.getParameters();
    if (!params.equals(key.getParameters()))
    {
      throw new IllegalStateException("ECDHC public key has wrong domain parameters");
    }
    
    BigInteger hd = params.getH().multiply(key.getD()).mod(params.getN());
    
    ECPoint P = pub.getQ().multiply(hd).normalize();
    
    if (P.isInfinity())
    {
      throw new IllegalStateException("Infinity is not a valid agreement value for ECDHC");
    }
    
    return P.getAffineXCoord().toBigInteger();
  }
}
