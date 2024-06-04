package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.KeyGenerationParameters;











public final class XMSSKeyPairGenerator
{
  private XMSSParameters params;
  private SecureRandom prng;
  
  public XMSSKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    XMSSKeyGenerationParameters parameters = (XMSSKeyGenerationParameters)param;
    
    prng = parameters.getRandom();
    params = parameters.getParameters();
  }
  




  public AsymmetricCipherKeyPair generateKeyPair()
  {
    XMSSPrivateKeyParameters privateKey = generatePrivateKey(params, prng);
    XMSSNode root = privateKey.getBDSState().getRoot();
    



    privateKey = new XMSSPrivateKeyParameters.Builder(params).withSecretKeySeed(privateKey.getSecretKeySeed()).withSecretKeyPRF(privateKey.getSecretKeyPRF()).withPublicSeed(privateKey.getPublicSeed()).withRoot(root.getValue()).withBDSState(privateKey.getBDSState()).build();
    

    XMSSPublicKeyParameters publicKey = new XMSSPublicKeyParameters.Builder(params).withRoot(root.getValue()).withPublicSeed(privateKey.getPublicSeed()).build();
    
    return new AsymmetricCipherKeyPair(publicKey, privateKey);
  }
  





  private XMSSPrivateKeyParameters generatePrivateKey(XMSSParameters params, SecureRandom prng)
  {
    int n = params.getDigestSize();
    byte[] secretKeySeed = new byte[n];
    prng.nextBytes(secretKeySeed);
    byte[] secretKeyPRF = new byte[n];
    prng.nextBytes(secretKeyPRF);
    byte[] publicSeed = new byte[n];
    prng.nextBytes(publicSeed);
    


    XMSSPrivateKeyParameters privateKey = new XMSSPrivateKeyParameters.Builder(params).withSecretKeySeed(secretKeySeed).withSecretKeyPRF(secretKeyPRF).withPublicSeed(publicSeed).withBDSState(new BDS(params, publicSeed, secretKeySeed, (OTSHashAddress)new OTSHashAddress.Builder().build())).build();
    
    return privateKey;
  }
}
