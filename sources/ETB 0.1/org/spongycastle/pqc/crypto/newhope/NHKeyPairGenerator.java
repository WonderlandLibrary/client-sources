package org.spongycastle.pqc.crypto.newhope;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

public class NHKeyPairGenerator implements AsymmetricCipherKeyPairGenerator
{
  private SecureRandom random;
  
  public NHKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    random = param.getRandom();
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    byte[] pubData = new byte['ܠ'];
    short[] secData = new short['Ѐ'];
    
    NewHope.keygen(random, pubData, secData);
    
    return new AsymmetricCipherKeyPair(new NHPublicKeyParameters(pubData), new NHPrivateKeyParameters(secData));
  }
}
