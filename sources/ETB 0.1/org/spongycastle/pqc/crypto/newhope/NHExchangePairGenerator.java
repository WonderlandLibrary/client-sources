package org.spongycastle.pqc.crypto.newhope;

import java.security.SecureRandom;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.crypto.ExchangePair;
import org.spongycastle.pqc.crypto.ExchangePairGenerator;


public class NHExchangePairGenerator
  implements ExchangePairGenerator
{
  private final SecureRandom random;
  
  public NHExchangePairGenerator(SecureRandom random)
  {
    this.random = random;
  }
  
  public ExchangePair GenerateExchange(AsymmetricKeyParameter senderPublicKey)
  {
    return generateExchange(senderPublicKey);
  }
  
  public ExchangePair generateExchange(AsymmetricKeyParameter senderPublicKey)
  {
    NHPublicKeyParameters pubKey = (NHPublicKeyParameters)senderPublicKey;
    
    byte[] sharedValue = new byte[32];
    byte[] publicKeyValue = new byte['ࠀ'];
    
    NewHope.sharedB(random, sharedValue, publicKeyValue, pubData);
    
    return new ExchangePair(new NHPublicKeyParameters(publicKeyValue), sharedValue);
  }
}
