package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;





public class ECGOST3410Signer
  implements DSA
{
  ECKeyParameters key;
  SecureRandom random;
  
  public ECGOST3410Signer() {}
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        random = rParam.getRandom();
        key = ((ECPrivateKeyParameters)rParam.getParameters());
      }
      else
      {
        random = new SecureRandom();
        key = ((ECPrivateKeyParameters)param);
      }
      
    }
    else {
      key = ((ECPublicKeyParameters)param);
    }
  }
  








  public BigInteger[] generateSignature(byte[] message)
  {
    byte[] mRev = new byte[message.length];
    for (int i = 0; i != mRev.length; i++)
    {
      mRev[i] = message[(mRev.length - 1 - i)];
    }
    
    BigInteger e = new BigInteger(1, mRev);
    
    ECDomainParameters ec = key.getParameters();
    BigInteger n = ec.getN();
    BigInteger d = ((ECPrivateKeyParameters)key).getD();
    


    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    BigInteger r;
    BigInteger s;
    do
    {
      BigInteger k;
      do
      {
        do {
          k = new BigInteger(n.bitLength(), random);
        }
        while (k.equals(ECConstants.ZERO));
        
        ECPoint p = basePointMultiplier.multiply(ec.getG(), k).normalize();
        
        r = p.getAffineXCoord().toBigInteger().mod(n);
      }
      while (r.equals(ECConstants.ZERO));
      
      s = k.multiply(e).add(d.multiply(r)).mod(n);
    }
    while (s.equals(ECConstants.ZERO));
    
    return new BigInteger[] { r, s };
  }
  








  public boolean verifySignature(byte[] message, BigInteger r, BigInteger s)
  {
    byte[] mRev = new byte[message.length];
    for (int i = 0; i != mRev.length; i++)
    {
      mRev[i] = message[(mRev.length - 1 - i)];
    }
    
    BigInteger e = new BigInteger(1, mRev);
    BigInteger n = key.getParameters().getN();
    

    if ((r.compareTo(ECConstants.ONE) < 0) || (r.compareTo(n) >= 0))
    {
      return false;
    }
    

    if ((s.compareTo(ECConstants.ONE) < 0) || (s.compareTo(n) >= 0))
    {
      return false;
    }
    
    BigInteger v = e.modInverse(n);
    
    BigInteger z1 = s.multiply(v).mod(n);
    BigInteger z2 = n.subtract(r).multiply(v).mod(n);
    
    ECPoint G = key.getParameters().getG();
    ECPoint Q = ((ECPublicKeyParameters)key).getQ();
    
    ECPoint point = ECAlgorithms.sumOfTwoMultiplies(G, z1, Q, z2).normalize();
    

    if (point.isInfinity())
    {
      return false;
    }
    
    BigInteger R = point.getAffineXCoord().toBigInteger().mod(n);
    
    return R.equals(r);
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
}
