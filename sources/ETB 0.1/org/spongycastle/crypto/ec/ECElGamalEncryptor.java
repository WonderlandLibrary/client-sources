package org.spongycastle.crypto.ec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;








public class ECElGamalEncryptor
  implements ECEncryptor
{
  private ECPublicKeyParameters key;
  private SecureRandom random;
  
  public ECElGamalEncryptor() {}
  
  public void init(CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom p = (ParametersWithRandom)param;
      
      if (!(p.getParameters() instanceof ECPublicKeyParameters))
      {
        throw new IllegalArgumentException("ECPublicKeyParameters are required for encryption.");
      }
      key = ((ECPublicKeyParameters)p.getParameters());
      random = p.getRandom();
    }
    else
    {
      if (!(param instanceof ECPublicKeyParameters))
      {
        throw new IllegalArgumentException("ECPublicKeyParameters are required for encryption.");
      }
      
      key = ((ECPublicKeyParameters)param);
      random = new SecureRandom();
    }
  }
  






  public ECPair encrypt(ECPoint point)
  {
    if (key == null)
    {
      throw new IllegalStateException("ECElGamalEncryptor not initialised");
    }
    
    ECDomainParameters ec = key.getParameters();
    BigInteger k = ECUtil.generateK(ec.getN(), random);
    
    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    


    ECPoint[] gamma_phi = {basePointMultiplier.multiply(ec.getG(), k), key.getQ().multiply(k).add(point) };
    

    ec.getCurve().normalizeAll(gamma_phi);
    
    return new ECPair(gamma_phi[0], gamma_phi[1]);
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
}
