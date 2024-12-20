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
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.util.Arrays;








public class DSTU4145Signer
  implements DSA
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private ECKeyParameters key;
  private SecureRandom random;
  
  public DSTU4145Signer() {}
  
  public void init(boolean forSigning, CipherParameters param) {
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        random = rParam.getRandom();
        param = rParam.getParameters();
      }
      else
      {
        random = new SecureRandom();
      }
      
      key = ((ECPrivateKeyParameters)param);
    }
    else
    {
      key = ((ECPublicKeyParameters)param);
    }
  }
  

  public BigInteger[] generateSignature(byte[] message)
  {
    ECDomainParameters ec = key.getParameters();
    
    ECCurve curve = ec.getCurve();
    
    ECFieldElement h = hash2FieldElement(curve, message);
    if (h.isZero())
    {
      h = curve.fromBigInteger(ONE);
    }
    
    BigInteger n = ec.getN();
    


    BigInteger d = ((ECPrivateKeyParameters)key).getD();
    
    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    BigInteger r;
    BigInteger s;
    do {
      BigInteger e;
      do {
        ECFieldElement Fe;
        do {
          e = generateRandomInteger(n, random);
          Fe = basePointMultiplier.multiply(ec.getG(), e).normalize().getAffineXCoord();
        }
        while (Fe.isZero());
        
        ECFieldElement y = h.multiply(Fe);
        r = fieldElement2Integer(n, y);
      }
      while (r.signum() == 0);
      
      s = r.multiply(d).add(e).mod(n);
    }
    while (s.signum() == 0);
    
    return new BigInteger[] { r, s };
  }
  
  public boolean verifySignature(byte[] message, BigInteger r, BigInteger s)
  {
    if ((r.signum() <= 0) || (s.signum() <= 0))
    {
      return false;
    }
    
    ECDomainParameters parameters = key.getParameters();
    
    BigInteger n = parameters.getN();
    if ((r.compareTo(n) >= 0) || (s.compareTo(n) >= 0))
    {
      return false;
    }
    
    ECCurve curve = parameters.getCurve();
    
    ECFieldElement h = hash2FieldElement(curve, message);
    if (h.isZero())
    {
      h = curve.fromBigInteger(ONE);
    }
    
    ECPoint R = ECAlgorithms.sumOfTwoMultiplies(parameters.getG(), s, ((ECPublicKeyParameters)key).getQ(), r).normalize();
    

    if (R.isInfinity())
    {
      return false;
    }
    
    ECFieldElement y = h.multiply(R.getAffineXCoord());
    return fieldElement2Integer(n, y).compareTo(r) == 0;
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
  



  private static BigInteger generateRandomInteger(BigInteger n, SecureRandom random)
  {
    return new BigInteger(n.bitLength() - 1, random);
  }
  
  private static ECFieldElement hash2FieldElement(ECCurve curve, byte[] hash)
  {
    byte[] data = Arrays.reverse(hash);
    return curve.fromBigInteger(truncate(new BigInteger(1, data), curve.getFieldSize()));
  }
  
  private static BigInteger fieldElement2Integer(BigInteger n, ECFieldElement fe)
  {
    return truncate(fe.toBigInteger(), n.bitLength() - 1);
  }
  
  private static BigInteger truncate(BigInteger x, int bitLength)
  {
    if (x.bitLength() > bitLength)
    {
      x = x.mod(ONE.shiftLeft(bitLength));
    }
    return x;
  }
}
