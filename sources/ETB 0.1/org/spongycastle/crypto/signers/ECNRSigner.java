package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;



public class ECNRSigner
  implements DSA
{
  private boolean forSigning;
  private ECKeyParameters key;
  private SecureRandom random;
  
  public ECNRSigner() {}
  
  public void init(boolean forSigning, CipherParameters param)
  {
    this.forSigning = forSigning;
    
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
  











  public BigInteger[] generateSignature(byte[] digest)
  {
    if (!forSigning)
    {
      throw new IllegalStateException("not initialised for signing");
    }
    
    BigInteger n = ((ECPrivateKeyParameters)key).getParameters().getN();
    int nBitLength = n.bitLength();
    
    BigInteger e = new BigInteger(1, digest);
    int eBitLength = e.bitLength();
    
    ECPrivateKeyParameters privKey = (ECPrivateKeyParameters)key;
    
    if (eBitLength > nBitLength)
    {
      throw new DataLengthException("input too large for ECNR key.");
    }
    
    BigInteger r = null;
    BigInteger s = null;
    

    AsymmetricCipherKeyPair tempPair;
    
    do
    {
      ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
      
      keyGen.init(new ECKeyGenerationParameters(privKey.getParameters(), random));
      
      tempPair = keyGen.generateKeyPair();
      

      ECPublicKeyParameters V = (ECPublicKeyParameters)tempPair.getPublic();
      BigInteger Vx = V.getQ().getAffineXCoord().toBigInteger();
      
      r = Vx.add(e).mod(n);
    }
    while (r.equals(ECConstants.ZERO));
    

    BigInteger x = privKey.getD();
    BigInteger u = ((ECPrivateKeyParameters)tempPair.getPrivate()).getD();
    s = u.subtract(r.multiply(x)).mod(n);
    
    BigInteger[] res = new BigInteger[2];
    res[0] = r;
    res[1] = s;
    
    return res;
  }
  

















  public boolean verifySignature(byte[] digest, BigInteger r, BigInteger s)
  {
    if (forSigning)
    {
      throw new IllegalStateException("not initialised for verifying");
    }
    
    ECPublicKeyParameters pubKey = (ECPublicKeyParameters)key;
    BigInteger n = pubKey.getParameters().getN();
    int nBitLength = n.bitLength();
    
    BigInteger e = new BigInteger(1, digest);
    int eBitLength = e.bitLength();
    
    if (eBitLength > nBitLength)
    {
      throw new DataLengthException("input too large for ECNR key.");
    }
    

    if ((r.compareTo(ECConstants.ONE) < 0) || (r.compareTo(n) >= 0))
    {
      return false;
    }
    

    if ((s.compareTo(ECConstants.ZERO) < 0) || (s.compareTo(n) >= 0))
    {
      return false;
    }
    


    ECPoint G = pubKey.getParameters().getG();
    ECPoint W = pubKey.getQ();
    
    ECPoint P = ECAlgorithms.sumOfTwoMultiplies(G, s, W, r).normalize();
    

    if (P.isInfinity())
    {
      return false;
    }
    
    BigInteger x = P.getAffineXCoord().toBigInteger();
    BigInteger t = r.subtract(x).mod(n);
    
    return t.equals(e);
  }
}
