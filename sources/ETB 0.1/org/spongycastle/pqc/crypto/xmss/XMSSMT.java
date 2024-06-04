package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import java.text.ParseException;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.util.Arrays;













public final class XMSSMT
{
  private XMSSMTParameters params;
  private XMSSParameters xmssParams;
  private SecureRandom prng;
  private XMSSMTPrivateKeyParameters privateKey;
  private XMSSMTPublicKeyParameters publicKey;
  
  public XMSSMT(XMSSMTParameters params, SecureRandom prng)
  {
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    this.params = params;
    xmssParams = params.getXMSSParameters();
    this.prng = prng;
    
    privateKey = new XMSSMTPrivateKeyParameters.Builder(params).build();
    publicKey = new XMSSMTPublicKeyParameters.Builder(params).build();
  }
  



  public void generateKeys()
  {
    XMSSMTKeyPairGenerator kpGen = new XMSSMTKeyPairGenerator();
    
    kpGen.init(new XMSSMTKeyGenerationParameters(getParams(), prng));
    
    AsymmetricCipherKeyPair kp = kpGen.generateKeyPair();
    
    privateKey = ((XMSSMTPrivateKeyParameters)kp.getPrivate());
    publicKey = ((XMSSMTPublicKeyParameters)kp.getPublic());
    
    importState(privateKey, publicKey);
  }
  

  private void importState(XMSSMTPrivateKeyParameters privateKey, XMSSMTPublicKeyParameters publicKey)
  {
    xmssParams.getWOTSPlus().importKeys(new byte[params.getDigestSize()], this.privateKey.getPublicSeed());
    
    this.privateKey = privateKey;
    this.publicKey = publicKey;
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
    
    XMSSMTPrivateKeyParameters xmssMTPrivateKey = new XMSSMTPrivateKeyParameters.Builder(params).withPrivateKey(privateKey, xmssParams).build();
    
    XMSSMTPublicKeyParameters xmssMTPublicKey = new XMSSMTPublicKeyParameters.Builder(params).withPublicKey(publicKey).build();
    if (!Arrays.areEqual(xmssMTPrivateKey.getRoot(), xmssMTPublicKey.getRoot()))
    {
      throw new IllegalStateException("root of private key and public key do not match");
    }
    if (!Arrays.areEqual(xmssMTPrivateKey.getPublicSeed(), xmssMTPublicKey.getPublicSeed()))
    {
      throw new IllegalStateException("public seed of private key and public key do not match");
    }
    

    xmssParams.getWOTSPlus().importKeys(new byte[params.getDigestSize()], xmssMTPrivateKey.getPublicSeed());
    
    this.privateKey = xmssMTPrivateKey;
    this.publicKey = xmssMTPublicKey;
  }
  






  public byte[] sign(byte[] message)
  {
    if (message == null)
    {
      throw new NullPointerException("message == null");
    }
    
    XMSSMTSigner signer = new XMSSMTSigner();
    
    signer.init(true, privateKey);
    
    byte[] signature = signer.generateSignature(message);
    
    privateKey = ((XMSSMTPrivateKeyParameters)signer.getUpdatedPrivateKey());
    
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
    
    XMSSMTSigner signer = new XMSSMTSigner();
    
    signer.init(false, new XMSSMTPublicKeyParameters.Builder(getParams()).withPublicKey(publicKey).build());
    
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
  





  public XMSSMTParameters getParams()
  {
    return params;
  }
  






  public byte[] getPublicSeed()
  {
    return privateKey.getPublicSeed();
  }
  
  protected XMSSParameters getXMSS()
  {
    return xmssParams;
  }
}
