package org.spongycastle.crypto.ec;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.math.ec.ECPoint;








public class ECElGamalDecryptor
  implements ECDecryptor
{
  private ECPrivateKeyParameters key;
  
  public ECElGamalDecryptor() {}
  
  public void init(CipherParameters param)
  {
    if (!(param instanceof ECPrivateKeyParameters))
    {
      throw new IllegalArgumentException("ECPrivateKeyParameters are required for decryption.");
    }
    
    key = ((ECPrivateKeyParameters)param);
  }
  






  public ECPoint decrypt(ECPair pair)
  {
    if (key == null)
    {
      throw new IllegalStateException("ECElGamalDecryptor not initialised");
    }
    
    ECPoint tmp = pair.getX().multiply(key.getD());
    
    return pair.getY().subtract(tmp).normalize();
  }
}
