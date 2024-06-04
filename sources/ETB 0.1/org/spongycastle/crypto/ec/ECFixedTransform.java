package org.spongycastle.crypto.ec;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;





public class ECFixedTransform
  implements ECPairFactorTransform
{
  private ECPublicKeyParameters key;
  private BigInteger k;
  
  public ECFixedTransform(BigInteger k)
  {
    this.k = k;
  }
  






  public void init(CipherParameters param)
  {
    if (!(param instanceof ECPublicKeyParameters))
    {
      throw new IllegalArgumentException("ECPublicKeyParameters are required for fixed transform.");
    }
    
    key = ((ECPublicKeyParameters)param);
  }
  








  public ECPair transform(ECPair cipherText)
  {
    if (key == null)
    {
      throw new IllegalStateException("ECFixedTransform not initialised");
    }
    
    ECDomainParameters ec = key.getParameters();
    BigInteger n = ec.getN();
    
    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    BigInteger k = this.k.mod(n);
    


    ECPoint[] gamma_phi = {basePointMultiplier.multiply(ec.getG(), k).add(cipherText.getX()), key.getQ().multiply(k).add(cipherText.getY()) };
    

    ec.getCurve().normalizeAll(gamma_phi);
    
    return new ECPair(gamma_phi[0], gamma_phi[1]);
  }
  





  public BigInteger getTransformValue()
  {
    return k;
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
}
