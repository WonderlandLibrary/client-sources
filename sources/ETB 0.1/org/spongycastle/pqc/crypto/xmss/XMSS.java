package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import java.text.ParseException;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.util.Arrays;





























public class XMSS
{
  private final XMSSParameters params;
  private WOTSPlus wotsPlus;
  private SecureRandom prng;
  private XMSSPrivateKeyParameters privateKey;
  private XMSSPublicKeyParameters publicKey;
  
  public XMSS(XMSSParameters params, SecureRandom prng)
  {
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    this.params = params;
    wotsPlus = params.getWOTSPlus();
    this.prng = prng;
  }
  















































  public void generateKeys()
  {
    XMSSKeyPairGenerator kpGen = new XMSSKeyPairGenerator();
    
    kpGen.init(new XMSSKeyGenerationParameters(getParams(), prng));
    
    AsymmetricCipherKeyPair kp = kpGen.generateKeyPair();
    
    privateKey = ((XMSSPrivateKeyParameters)kp.getPrivate());
    publicKey = ((XMSSPublicKeyParameters)kp.getPublic());
    
    wotsPlus.importKeys(new byte[params.getDigestSize()], privateKey.getPublicSeed());
  }
  
  void importState(XMSSPrivateKeyParameters privateKey, XMSSPublicKeyParameters publicKey)
  {
    if (!Arrays.areEqual(privateKey.getRoot(), publicKey.getRoot()))
    {
      throw new IllegalStateException("root of private key and public key do not match");
    }
    if (!Arrays.areEqual(privateKey.getPublicSeed(), publicKey.getPublicSeed()))
    {
      throw new IllegalStateException("public seed of private key and public key do not match");
    }
    
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    
    wotsPlus.importKeys(new byte[params.getDigestSize()], this.privateKey.getPublicSeed());
  }
  








  public void importState(byte[] privateKey, byte[] publicKey)
  {
    if (privateKey == null)
    {
      throw new NullPointerException("privateKey == null");
    }
    if (publicKey == null)
    {
      throw new NullPointerException("publicKey == null");
    }
    

    XMSSPrivateKeyParameters tmpPrivateKey = new XMSSPrivateKeyParameters.Builder(params).withPrivateKey(privateKey, getParams()).build();
    
    XMSSPublicKeyParameters tmpPublicKey = new XMSSPublicKeyParameters.Builder(params).withPublicKey(publicKey).build();
    if (!Arrays.areEqual(tmpPrivateKey.getRoot(), tmpPublicKey.getRoot()))
    {
      throw new IllegalStateException("root of private key and public key do not match");
    }
    if (!Arrays.areEqual(tmpPrivateKey.getPublicSeed(), tmpPublicKey.getPublicSeed()))
    {
      throw new IllegalStateException("public seed of private key and public key do not match");
    }
    
    this.privateKey = tmpPrivateKey;
    this.publicKey = tmpPublicKey;
    wotsPlus.importKeys(new byte[params.getDigestSize()], this.privateKey.getPublicSeed());
  }
  






  public byte[] sign(byte[] message)
  {
    if (message == null)
    {
      throw new NullPointerException("message == null");
    }
    XMSSSigner signer = new XMSSSigner();
    
    signer.init(true, privateKey);
    
    byte[] signature = signer.generateSignature(message);
    
    privateKey = ((XMSSPrivateKeyParameters)signer.getUpdatedPrivateKey());
    
    importState(privateKey, publicKey);
    
    return signature;
  }
  









  public boolean verifySignature(byte[] message, byte[] signature, byte[] publicKey)
    throws ParseException
  {
    if (message == null)
    {
      throw new NullPointerException("message == null");
    }
    if (signature == null)
    {
      throw new NullPointerException("signature == null");
    }
    if (publicKey == null)
    {
      throw new NullPointerException("publicKey == null");
    }
    
    XMSSSigner signer = new XMSSSigner();
    
    signer.init(false, new XMSSPublicKeyParameters.Builder(getParams()).withPublicKey(publicKey).build());
    
    return signer.verifySignature(message, signature);
  }
  





  public byte[] exportPrivateKey()
  {
    return privateKey.toByteArray();
  }
  





  public byte[] exportPublicKey()
  {
    return publicKey.toByteArray();
  }
  








  protected WOTSPlusSignature wotsSign(byte[] messageDigest, OTSHashAddress otsHashAddress)
  {
    if (messageDigest.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    
    wotsPlus.importKeys(wotsPlus.getWOTSPlusSecretKey(privateKey.getSecretKeySeed(), otsHashAddress), getPublicSeed());
    
    return wotsPlus.sign(messageDigest, otsHashAddress);
  }
  





  public XMSSParameters getParams()
  {
    return params;
  }
  





  protected WOTSPlus getWOTSPlus()
  {
    return wotsPlus;
  }
  





  public byte[] getRoot()
  {
    return privateKey.getRoot();
  }
  


  protected void setRoot(byte[] root)
  {
    privateKey = new XMSSPrivateKeyParameters.Builder(params).withSecretKeySeed(privateKey.getSecretKeySeed()).withSecretKeyPRF(privateKey.getSecretKeyPRF()).withPublicSeed(getPublicSeed()).withRoot(root).withBDSState(privateKey.getBDSState()).build();
    
    publicKey = new XMSSPublicKeyParameters.Builder(params).withRoot(root).withPublicSeed(getPublicSeed()).build();
  }
  





  public int getIndex()
  {
    return privateKey.getIndex();
  }
  



  protected void setIndex(int index)
  {
    privateKey = new XMSSPrivateKeyParameters.Builder(params).withSecretKeySeed(privateKey.getSecretKeySeed()).withSecretKeyPRF(privateKey.getSecretKeyPRF()).withPublicSeed(privateKey.getPublicSeed()).withRoot(privateKey.getRoot()).withBDSState(privateKey.getBDSState()).build();
  }
  





  public byte[] getPublicSeed()
  {
    return privateKey.getPublicSeed();
  }
  


  protected void setPublicSeed(byte[] publicSeed)
  {
    privateKey = new XMSSPrivateKeyParameters.Builder(params).withSecretKeySeed(privateKey.getSecretKeySeed()).withSecretKeyPRF(privateKey.getSecretKeyPRF()).withPublicSeed(publicSeed).withRoot(getRoot()).withBDSState(privateKey.getBDSState()).build();
    
    publicKey = new XMSSPublicKeyParameters.Builder(params).withRoot(getRoot()).withPublicSeed(publicSeed).build();
    
    wotsPlus.importKeys(new byte[params.getDigestSize()], publicSeed);
  }
  
  public XMSSPrivateKeyParameters getPrivateKey()
  {
    return privateKey;
  }
}
