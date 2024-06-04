package org.spongycastle.pqc.crypto.xmss;

import java.util.List;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.util.Arrays;



public class XMSSMTSigner
  implements StateAwareMessageSigner
{
  private XMSSMTPrivateKeyParameters privateKey;
  private XMSSMTPrivateKeyParameters nextKeyGenerator;
  private XMSSMTPublicKeyParameters publicKey;
  private XMSSMTParameters params;
  private XMSSParameters xmssParams;
  private WOTSPlus wotsPlus;
  private boolean hasGenerated;
  private boolean initSign;
  
  public XMSSMTSigner() {}
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      initSign = true;
      hasGenerated = false;
      privateKey = ((XMSSMTPrivateKeyParameters)param);
      nextKeyGenerator = privateKey;
      
      params = privateKey.getParameters();
      xmssParams = params.getXMSSParameters();
    }
    else
    {
      initSign = false;
      publicKey = ((XMSSMTPublicKeyParameters)param);
      
      params = publicKey.getParameters();
      xmssParams = params.getXMSSParameters();
    }
    
    wotsPlus = new WOTSPlus(new WOTSPlusParameters(params.getDigest()));
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
    if (privateKey.getBDSState().isEmpty())
    {
      throw new IllegalStateException("not initialized");
    }
    
    BDSStateMap bdsState = privateKey.getBDSState();
    

    long globalIndex = privateKey.getIndex();
    int totalHeight = params.getHeight();
    int xmssHeight = xmssParams.getHeight();
    if (!XMSSUtil.isIndexValid(totalHeight, globalIndex))
    {
      throw new IllegalStateException("index out of bounds");
    }
    

    byte[] random = wotsPlus.getKhf().PRF(privateKey.getSecretKeyPRF(), XMSSUtil.toBytesBigEndian(globalIndex, 32));
    byte[] concatenated = Arrays.concatenate(random, privateKey.getRoot(), 
      XMSSUtil.toBytesBigEndian(globalIndex, params.getDigestSize()));
    byte[] messageDigest = wotsPlus.getKhf().HMsg(concatenated, message);
    
    XMSSMTSignature signature = new XMSSMTSignature.Builder(params).withIndex(globalIndex).withRandom(random).build();
    


    long indexTree = XMSSUtil.getTreeIndex(globalIndex, xmssHeight);
    int indexLeaf = XMSSUtil.getLeafIndex(globalIndex, xmssHeight);
    

    wotsPlus.importKeys(new byte[params.getDigestSize()], privateKey.getPublicSeed());
    



    OTSHashAddress otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
    

    if ((bdsState.get(0) == null) || (indexLeaf == 0))
    {
      bdsState.put(0, new BDS(xmssParams, privateKey.getPublicSeed(), privateKey.getSecretKeySeed(), otsHashAddress));
    }
    

    WOTSPlusSignature wotsPlusSignature = wotsSign(messageDigest, otsHashAddress);
    


    XMSSReducedSignature reducedSignature = new XMSSReducedSignature.Builder(xmssParams).withWOTSPlusSignature(wotsPlusSignature).withAuthPath(bdsState.get(0).getAuthenticationPath()).build();
    
    signature.getReducedSignatures().add(reducedSignature);
    
    for (int layer = 1; layer < params.getLayers(); layer++)
    {

      XMSSNode root = bdsState.get(layer - 1).getRoot();
      
      indexLeaf = XMSSUtil.getLeafIndex(indexTree, xmssHeight);
      indexTree = XMSSUtil.getTreeIndex(indexTree, xmssHeight);
      


      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(layer)).withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
      

      wotsPlusSignature = wotsSign(root.getValue(), otsHashAddress);
      
      if ((bdsState.get(layer) == null) || (XMSSUtil.isNewBDSInitNeeded(globalIndex, xmssHeight, layer)))
      {
        bdsState.put(layer, new BDS(xmssParams, privateKey.getPublicSeed(), privateKey.getSecretKeySeed(), otsHashAddress));
      }
      


      reducedSignature = new XMSSReducedSignature.Builder(xmssParams).withWOTSPlusSignature(wotsPlusSignature).withAuthPath(bdsState.get(layer).getAuthenticationPath()).build();
      
      signature.getReducedSignatures().add(reducedSignature);
    }
    
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
    
    XMSSMTSignature sig = new XMSSMTSignature.Builder(params).withSignature(signature).build();
    
    byte[] concatenated = Arrays.concatenate(sig.getRandom(), publicKey.getRoot(), 
      XMSSUtil.toBytesBigEndian(sig.getIndex(), params.getDigestSize()));
    byte[] messageDigest = wotsPlus.getKhf().HMsg(concatenated, message);
    
    long globalIndex = sig.getIndex();
    int xmssHeight = xmssParams.getHeight();
    long indexTree = XMSSUtil.getTreeIndex(globalIndex, xmssHeight);
    int indexLeaf = XMSSUtil.getLeafIndex(globalIndex, xmssHeight);
    

    wotsPlus.importKeys(new byte[params.getDigestSize()], publicKey.getPublicSeed());
    


    OTSHashAddress otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
    

    XMSSReducedSignature xmssMTSignature = (XMSSReducedSignature)sig.getReducedSignatures().get(0);
    XMSSNode rootNode = XMSSVerifierUtil.getRootNodeFromSignature(wotsPlus, xmssHeight, messageDigest, xmssMTSignature, otsHashAddress, indexLeaf);
    for (int layer = 1; layer < params.getLayers(); layer++)
    {
      xmssMTSignature = (XMSSReducedSignature)sig.getReducedSignatures().get(layer);
      indexLeaf = XMSSUtil.getLeafIndex(indexTree, xmssHeight);
      indexTree = XMSSUtil.getTreeIndex(indexTree, xmssHeight);
      


      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(layer)).withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
      

      rootNode = XMSSVerifierUtil.getRootNodeFromSignature(wotsPlus, xmssHeight, rootNode.getValue(), xmssMTSignature, otsHashAddress, indexLeaf);
    }
    

    return Arrays.constantTimeAreEqual(rootNode.getValue(), publicKey.getRoot());
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
    
    wotsPlus.importKeys(wotsPlus.getWOTSPlusSecretKey(privateKey.getSecretKeySeed(), otsHashAddress), privateKey.getPublicSeed());
    
    return wotsPlus.sign(messageDigest, otsHashAddress);
  }
  


  public AsymmetricKeyParameter getUpdatedPrivateKey()
  {
    if (hasGenerated)
    {
      XMSSMTPrivateKeyParameters privKey = privateKey;
      
      privateKey = null;
      nextKeyGenerator = null;
      
      return privKey;
    }
    

    XMSSMTPrivateKeyParameters privKey = nextKeyGenerator.getNextKey();
    
    nextKeyGenerator = null;
    
    return privKey;
  }
}
