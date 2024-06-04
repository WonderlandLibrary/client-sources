package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;




public class ElGamalUtil
{
  public ElGamalUtil() {}
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey key)
    throws InvalidKeyException
  {
    if ((key instanceof ElGamalPublicKey))
    {
      ElGamalPublicKey k = (ElGamalPublicKey)key;
      
      return new ElGamalPublicKeyParameters(k.getY(), new ElGamalParameters(k
        .getParameters().getP(), k.getParameters().getG()));
    }
    if ((key instanceof DHPublicKey))
    {
      DHPublicKey k = (DHPublicKey)key;
      
      return new ElGamalPublicKeyParameters(k.getY(), new ElGamalParameters(k
        .getParams().getP(), k.getParams().getG()));
    }
    
    throw new InvalidKeyException("can't identify public key for El Gamal.");
  }
  

  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey key)
    throws InvalidKeyException
  {
    if ((key instanceof ElGamalPrivateKey))
    {
      ElGamalPrivateKey k = (ElGamalPrivateKey)key;
      
      return new ElGamalPrivateKeyParameters(k.getX(), new ElGamalParameters(k
        .getParameters().getP(), k.getParameters().getG()));
    }
    if ((key instanceof DHPrivateKey))
    {
      DHPrivateKey k = (DHPrivateKey)key;
      
      return new ElGamalPrivateKeyParameters(k.getX(), new ElGamalParameters(k
        .getParams().getP(), k.getParams().getG()));
    }
    
    throw new InvalidKeyException("can't identify private key for El Gamal.");
  }
}
