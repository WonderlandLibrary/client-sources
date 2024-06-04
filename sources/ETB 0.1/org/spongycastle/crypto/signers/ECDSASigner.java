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
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;









public class ECDSASigner
  implements ECConstants, DSA
{
  private final DSAKCalculator kCalculator;
  private ECKeyParameters key;
  private SecureRandom random;
  
  public ECDSASigner()
  {
    kCalculator = new RandomDSAKCalculator();
  }
  





  public ECDSASigner(DSAKCalculator kCalculator)
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
        
        key = ((ECPrivateKeyParameters)rParam.getParameters());
        providedRandom = rParam.getRandom();
      }
      else
      {
        key = ((ECPrivateKeyParameters)param);
      }
      
    }
    else {
      key = ((ECPublicKeyParameters)param);
    }
    
    random = initSecureRandom((forSigning) && (!kCalculator.isDeterministic()), providedRandom);
  }
  









  public BigInteger[] generateSignature(byte[] message)
  {
    ECDomainParameters ec = key.getParameters();
    BigInteger n = ec.getN();
    BigInteger e = calculateE(n, message);
    BigInteger d = ((ECPrivateKeyParameters)key).getD();
    
    if (kCalculator.isDeterministic())
    {
      kCalculator.init(n, d, message);
    }
    else
    {
      kCalculator.init(n, random);
    }
    


    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    BigInteger r;
    BigInteger s;
    do
    {
      BigInteger k;
      do
      {
        k = kCalculator.nextK();
        
        ECPoint p = basePointMultiplier.multiply(ec.getG(), k).normalize();
        

        r = p.getAffineXCoord().toBigInteger().mod(n);
      }
      while (r.equals(ZERO));
      
      s = k.modInverse(n).multiply(e.add(d.multiply(r))).mod(n);
    }
    while (s.equals(ZERO));
    
    return new BigInteger[] { r, s };
  }
  









  public boolean verifySignature(byte[] message, BigInteger r, BigInteger s)
  {
    ECDomainParameters ec = key.getParameters();
    BigInteger n = ec.getN();
    BigInteger e = calculateE(n, message);
    

    if ((r.compareTo(ONE) < 0) || (r.compareTo(n) >= 0))
    {
      return false;
    }
    

    if ((s.compareTo(ONE) < 0) || (s.compareTo(n) >= 0))
    {
      return false;
    }
    
    BigInteger c = s.modInverse(n);
    
    BigInteger u1 = e.multiply(c).mod(n);
    BigInteger u2 = r.multiply(c).mod(n);
    
    ECPoint G = ec.getG();
    ECPoint Q = ((ECPublicKeyParameters)key).getQ();
    
    ECPoint point = ECAlgorithms.sumOfTwoMultiplies(G, u1, Q, u2);
    

    if (point.isInfinity())
    {
      return false;
    }
    













    ECCurve curve = point.getCurve();
    if (curve != null)
    {
      BigInteger cofactor = curve.getCofactor();
      if ((cofactor != null) && (cofactor.compareTo(EIGHT) <= 0))
      {
        ECFieldElement D = getDenominator(curve.getCoordinateSystem(), point);
        if ((D != null) && (!D.isZero()))
        {
          ECFieldElement X = point.getXCoord();
          while (curve.isValidFieldElement(r))
          {
            ECFieldElement R = curve.fromBigInteger(r).multiply(D);
            if (R.equals(X))
            {
              return true;
            }
            r = r.add(n);
          }
          return false;
        }
      }
    }
    
    BigInteger v = point.normalize().getAffineXCoord().toBigInteger().mod(n);
    return v.equals(r);
  }
  
  protected BigInteger calculateE(BigInteger n, byte[] message)
  {
    int log2n = n.bitLength();
    int messageBitLength = message.length * 8;
    
    BigInteger e = new BigInteger(1, message);
    if (log2n < messageBitLength)
    {
      e = e.shiftRight(messageBitLength - log2n);
    }
    return e;
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
  
  protected ECFieldElement getDenominator(int coordinateSystem, ECPoint p)
  {
    switch (coordinateSystem)
    {
    case 1: 
    case 6: 
    case 7: 
      return p.getZCoord(0);
    case 2: 
    case 3: 
    case 4: 
      return p.getZCoord(0).square();
    }
    return null;
  }
  

  protected SecureRandom initSecureRandom(boolean needed, SecureRandom provided)
  {
    return provided != null ? provided : !needed ? null : new SecureRandom();
  }
}
