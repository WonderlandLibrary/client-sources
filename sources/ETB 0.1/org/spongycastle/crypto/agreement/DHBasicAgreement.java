package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;









public class DHBasicAgreement
  implements BasicAgreement
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private DHPrivateKeyParameters key;
  private DHParameters dhParams;
  
  public DHBasicAgreement() {}
  
  public void init(CipherParameters param)
  {
    AsymmetricKeyParameter kParam;
    AsymmetricKeyParameter kParam;
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      kParam = (AsymmetricKeyParameter)rParam.getParameters();
    }
    else
    {
      kParam = (AsymmetricKeyParameter)param;
    }
    
    if (!(kParam instanceof DHPrivateKeyParameters))
    {
      throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
    }
    
    key = ((DHPrivateKeyParameters)kParam);
    dhParams = key.getParameters();
  }
  
  public int getFieldSize()
  {
    return (key.getParameters().getP().bitLength() + 7) / 8;
  }
  





  public BigInteger calculateAgreement(CipherParameters pubKey)
  {
    DHPublicKeyParameters pub = (DHPublicKeyParameters)pubKey;
    
    if (!pub.getParameters().equals(dhParams))
    {
      throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }
    
    BigInteger p = dhParams.getP();
    
    BigInteger peerY = pub.getY();
    if ((peerY == null) || (peerY.compareTo(ONE) <= 0) || (peerY.compareTo(p.subtract(ONE)) >= 0))
    {
      throw new IllegalArgumentException("Diffie-Hellman public key is weak");
    }
    
    BigInteger result = peerY.modPow(key.getX(), p);
    if (result.equals(ONE))
    {
      throw new IllegalStateException("Shared key can't be 1");
    }
    
    return result;
  }
}
