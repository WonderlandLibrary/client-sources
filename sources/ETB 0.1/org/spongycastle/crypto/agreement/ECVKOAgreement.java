package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.BigIntegers;




public class ECVKOAgreement
{
  private final Digest digest;
  private ECPrivateKeyParameters key;
  private BigInteger ukm;
  
  public ECVKOAgreement(Digest digest)
  {
    this.digest = digest;
  }
  

  public void init(CipherParameters key)
  {
    ParametersWithUKM p = (ParametersWithUKM)key;
    
    this.key = ((ECPrivateKeyParameters)p.getParameters());
    ukm = toInteger(p.getUKM());
  }
  
  public int getFieldSize()
  {
    return (key.getParameters().getCurve().getFieldSize() + 7) / 8;
  }
  

  public byte[] calculateAgreement(CipherParameters pubKey)
  {
    ECPublicKeyParameters pub = (ECPublicKeyParameters)pubKey;
    ECDomainParameters params = pub.getParameters();
    if (!params.equals(key.getParameters()))
    {
      throw new IllegalStateException("ECVKO public key has wrong domain parameters");
    }
    
    BigInteger hd = params.getH().multiply(ukm).multiply(key.getD()).mod(params.getN());
    
    ECPoint P = pub.getQ().multiply(hd).normalize();
    
    if (P.isInfinity())
    {
      throw new IllegalStateException("Infinity is not a valid agreement value for ECVKO");
    }
    
    return fromPoint(P.normalize());
  }
  
  private static BigInteger toInteger(byte[] ukm)
  {
    byte[] v = new byte[ukm.length];
    
    for (int i = 0; i != v.length; i++)
    {
      v[i] = ukm[(ukm.length - i - 1)];
    }
    
    return new BigInteger(1, v);
  }
  
  private byte[] fromPoint(ECPoint v)
  {
    BigInteger bX = v.getAffineXCoord().toBigInteger();
    BigInteger bY = v.getAffineYCoord().toBigInteger();
    int size;
    int size;
    if (bX.toByteArray().length > 33)
    {
      size = 64;
    }
    else
    {
      size = 32;
    }
    
    byte[] bytes = new byte[2 * size];
    byte[] x = BigIntegers.asUnsignedByteArray(size, bX);
    byte[] y = BigIntegers.asUnsignedByteArray(size, bY);
    
    for (int i = 0; i != size; i++)
    {
      bytes[i] = x[(size - i - 1)];
    }
    for (int i = 0; i != size; i++)
    {
      bytes[(size + i)] = y[(size - i - 1)];
    }
    
    digest.update(bytes, 0, bytes.length);
    
    byte[] rv = new byte[digest.getDigestSize()];
    
    digest.doFinal(rv, 0);
    
    return rv;
  }
}
