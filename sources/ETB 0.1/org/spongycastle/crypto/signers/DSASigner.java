package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.DSAKeyParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;










public class DSASigner
  implements DSA
{
  private final DSAKCalculator kCalculator;
  private DSAKeyParameters key;
  private SecureRandom random;
  
  public DSASigner()
  {
    kCalculator = new RandomDSAKCalculator();
  }
  





  public DSASigner(DSAKCalculator kCalculator)
  {
    this.kCalculator = kCalculator;
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    SecureRandom providedRandom = null;
    
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        key = ((DSAPrivateKeyParameters)rParam.getParameters());
        providedRandom = rParam.getRandom();
      }
      else
      {
        key = ((DSAPrivateKeyParameters)param);
      }
      
    }
    else {
      key = ((DSAPublicKeyParameters)param);
    }
    
    random = initSecureRandom((forSigning) && (!kCalculator.isDeterministic()), providedRandom);
  }
  








  public BigInteger[] generateSignature(byte[] message)
  {
    DSAParameters params = key.getParameters();
    BigInteger q = params.getQ();
    BigInteger m = calculateE(q, message);
    BigInteger x = ((DSAPrivateKeyParameters)key).getX();
    
    if (kCalculator.isDeterministic())
    {
      kCalculator.init(q, x, message);
    }
    else
    {
      kCalculator.init(q, random);
    }
    
    BigInteger k = kCalculator.nextK();
    

    BigInteger r = params.getG().modPow(k.add(getRandomizer(q, random)), params.getP()).mod(q);
    
    k = k.modInverse(q).multiply(m.add(x.multiply(r)));
    
    BigInteger s = k.mod(q);
    
    return new BigInteger[] { r, s };
  }
  








  public boolean verifySignature(byte[] message, BigInteger r, BigInteger s)
  {
    DSAParameters params = key.getParameters();
    BigInteger q = params.getQ();
    BigInteger m = calculateE(q, message);
    BigInteger zero = BigInteger.valueOf(0L);
    
    if ((zero.compareTo(r) >= 0) || (q.compareTo(r) <= 0))
    {
      return false;
    }
    
    if ((zero.compareTo(s) >= 0) || (q.compareTo(s) <= 0))
    {
      return false;
    }
    
    BigInteger w = s.modInverse(q);
    
    BigInteger u1 = m.multiply(w).mod(q);
    BigInteger u2 = r.multiply(w).mod(q);
    
    BigInteger p = params.getP();
    u1 = params.getG().modPow(u1, p);
    u2 = ((DSAPublicKeyParameters)key).getY().modPow(u2, p);
    
    BigInteger v = u1.multiply(u2).mod(p).mod(q);
    
    return v.equals(r);
  }
  
  private BigInteger calculateE(BigInteger n, byte[] message)
  {
    if (n.bitLength() >= message.length * 8)
    {
      return new BigInteger(1, message);
    }
    

    byte[] trunc = new byte[n.bitLength() / 8];
    
    System.arraycopy(message, 0, trunc, 0, trunc.length);
    
    return new BigInteger(1, trunc);
  }
  

  protected SecureRandom initSecureRandom(boolean needed, SecureRandom provided)
  {
    return provided != null ? provided : !needed ? null : new SecureRandom();
  }
  

  private BigInteger getRandomizer(BigInteger q, SecureRandom provided)
  {
    int randomBits = 7;
    
    return new BigInteger(randomBits, provided != null ? provided : new SecureRandom()).add(BigInteger.valueOf(128L)).multiply(q);
  }
}
