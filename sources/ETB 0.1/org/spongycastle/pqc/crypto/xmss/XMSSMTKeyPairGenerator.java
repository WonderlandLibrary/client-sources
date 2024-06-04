package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.KeyGenerationParameters;













public final class XMSSMTKeyPairGenerator
{
  private XMSSMTParameters params;
  private XMSSParameters xmssParams;
  private SecureRandom prng;
  
  public XMSSMTKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    XMSSMTKeyGenerationParameters parameters = (XMSSMTKeyGenerationParameters)param;
    
    prng = parameters.getRandom();
    params = parameters.getParameters();
    xmssParams = params.getXMSSParameters();
  }
  







  public AsymmetricCipherKeyPair generateKeyPair()
  {
    XMSSMTPrivateKeyParameters privateKey = generatePrivateKey(new XMSSMTPrivateKeyParameters.Builder(params).build().getBDSState());
    

    xmssParams.getWOTSPlus().importKeys(new byte[params.getDigestSize()], privateKey.getPublicSeed());
    

    int rootLayerIndex = params.getLayers() - 1;
    
    OTSHashAddress otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(rootLayerIndex)).build();
    

    BDS bdsRoot = new BDS(xmssParams, privateKey.getPublicSeed(), privateKey.getSecretKeySeed(), otsHashAddress);
    XMSSNode root = bdsRoot.getRoot();
    privateKey.getBDSState().put(rootLayerIndex, bdsRoot);
    



    privateKey = new XMSSMTPrivateKeyParameters.Builder(params).withSecretKeySeed(privateKey.getSecretKeySeed()).withSecretKeyPRF(privateKey.getSecretKeyPRF()).withPublicSeed(privateKey.getPublicSeed()).withRoot(root.getValue()).withBDSState(privateKey.getBDSState()).build();
    
    XMSSMTPublicKeyParameters publicKey = new XMSSMTPublicKeyParameters.Builder(params).withRoot(root.getValue()).withPublicSeed(privateKey.getPublicSeed()).build();
    
    return new AsymmetricCipherKeyPair(publicKey, privateKey);
  }
  
  private XMSSMTPrivateKeyParameters generatePrivateKey(BDSStateMap bdsState)
  {
    int n = params.getDigestSize();
    byte[] secretKeySeed = new byte[n];
    prng.nextBytes(secretKeySeed);
    byte[] secretKeyPRF = new byte[n];
    prng.nextBytes(secretKeyPRF);
    byte[] publicSeed = new byte[n];
    prng.nextBytes(publicSeed);
    
    XMSSMTPrivateKeyParameters privateKey = null;
    


    privateKey = new XMSSMTPrivateKeyParameters.Builder(params).withSecretKeySeed(secretKeySeed).withSecretKeyPRF(secretKeyPRF).withPublicSeed(publicSeed).withBDSState(bdsState).build();
    
    return privateKey;
  }
}
