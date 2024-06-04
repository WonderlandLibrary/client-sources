package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.util.Arrays;

public class XMSSSigner implements StateAwareMessageSigner
{
  private XMSSPrivateKeyParameters privateKey;
  private XMSSPrivateKeyParameters nextKeyGenerator;
  private XMSSPublicKeyParameters publicKey;
  private XMSSParameters params;
  private KeyedHashFunctions khf;
  private boolean initSign;
  private boolean hasGenerated;
  
  public XMSSSigner() {}
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      initSign = true;
      hasGenerated = false;
      privateKey = ((XMSSPrivateKeyParameters)param);
      nextKeyGenerator = privateKey;
      
      params = privateKey.getParameters();
      khf = params.getWOTSPlus().getKhf();
    }
    else
    {
      initSign = false;
      publicKey = ((XMSSPublicKeyParameters)param);
      
      params = publicKey.getParameters();
      khf = params.getWOTSPlus().getKhf();
    }
  }
  
  public byte[] generateSignature(byte[] message)
  {
    if (message == null)
    {
      throw new NullPointerException("message == null");
    }
    if (initSign)
    {
      if (privateKey == null)
      {
        throw new IllegalStateException("signing key no longer usable");
      }
      
    }
    else {
      throw new IllegalStateException("signer not initialized for signature generation");
    }
    if (privateKey.getBDSState().getAuthenticationPath().isEmpty())
    {
      throw new IllegalStateException("not initialized");
    }
    int index = privateKey.getIndex();
    if (!XMSSUtil.isIndexValid(params.getHeight(), index))
    {
      throw new IllegalStateException("index out of bounds");
    }
    

    byte[] random = khf.PRF(privateKey.getSecretKeyPRF(), XMSSUtil.toBytesBigEndian(index, 32));
    byte[] concatenated = Arrays.concatenate(random, privateKey.getRoot(), 
      XMSSUtil.toBytesBigEndian(index, params.getDigestSize()));
    byte[] messageDigest = khf.HMsg(concatenated, message);
    

    OTSHashAddress otsHashAddress = (OTSHashAddress)new OTSHashAddress.Builder().withOTSAddress(index).build();
    WOTSPlusSignature wotsPlusSignature = wotsSign(messageDigest, otsHashAddress);
    

    XMSSSignature signature = (XMSSSignature)new XMSSSignature.Builder(params).withIndex(index).withRandom(random).withWOTSPlusSignature(wotsPlusSignature).withAuthPath(privateKey.getBDSState().getAuthenticationPath()).build();
    
    hasGenerated = true;
    
    if (nextKeyGenerator != null)
    {
      privateKey = nextKeyGenerator.getNextKey();
      nextKeyGenerator = privateKey;
    }
    else
    {
      privateKey = null;
    }
    
    return signature.toByteArray();
  }
  

  public boolean verifySignature(byte[] message, byte[] signature)
  {
    XMSSSignature sig = new XMSSSignature.Builder(params).withSignature(signature).build();
    

    int index = sig.getIndex();
    
    params.getWOTSPlus().importKeys(new byte[params.getDigestSize()], publicKey.getPublicSeed());
    

    byte[] concatenated = Arrays.concatenate(sig.getRandom(), publicKey.getRoot(), 
      XMSSUtil.toBytesBigEndian(index, params.getDigestSize()));
    byte[] messageDigest = khf.HMsg(concatenated, message);
    
    int xmssHeight = params.getHeight();
    int indexLeaf = XMSSUtil.getLeafIndex(index, xmssHeight);
    

    OTSHashAddress otsHashAddress = (OTSHashAddress)new OTSHashAddress.Builder().withOTSAddress(index).build();
    XMSSNode rootNodeFromSignature = XMSSVerifierUtil.getRootNodeFromSignature(params.getWOTSPlus(), xmssHeight, messageDigest, sig, otsHashAddress, indexLeaf);
    
    return Arrays.constantTimeAreEqual(rootNodeFromSignature.getValue(), publicKey.getRoot());
  }
  


  public AsymmetricKeyParameter getUpdatedPrivateKey()
  {
    if (hasGenerated)
    {
      XMSSPrivateKeyParameters privKey = privateKey;
      
      privateKey = null;
      nextKeyGenerator = null;
      
      return privKey;
    }
    

    XMSSPrivateKeyParameters privKey = nextKeyGenerator.getNextKey();
    
    nextKeyGenerator = null;
    
    return privKey;
  }
  

  private WOTSPlusSignature wotsSign(byte[] messageDigest, OTSHashAddress otsHashAddress)
  {
    if (messageDigest.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    
    params.getWOTSPlus().importKeys(params.getWOTSPlus().getWOTSPlusSecretKey(privateKey.getSecretKeySeed(), otsHashAddress), privateKey.getPublicSeed());
    
    return params.getWOTSPlus().sign(messageDigest, otsHashAddress);
  }
}
