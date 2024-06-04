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












public class ECDHBasicAgreement
  implements BasicAgreement
{
  private ECPrivateKeyParameters key;
  
  public ECDHBasicAgreement() {}
  
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
    if (!pub.getParameters().equals(key.getParameters()))
    {
      throw new IllegalStateException("ECDH public key has wrong domain parameters");
    }
    
    ECPoint P = pub.getQ().multiply(key.getD()).normalize();
    
    if (P.isInfinity())
    {
      throw new IllegalStateException("Infinity is not a valid agreement value for ECDH");
    }
    
    return P.getAffineXCoord().toBigInteger();
  }
}
