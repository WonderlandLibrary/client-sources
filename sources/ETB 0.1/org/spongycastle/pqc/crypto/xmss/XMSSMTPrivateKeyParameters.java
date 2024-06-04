package org.spongycastle.pqc.crypto.xmss;

import java.io.IOException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;






public final class XMSSMTPrivateKeyParameters
  extends AsymmetricKeyParameter
  implements XMSSStoreableObjectInterface
{
  private final XMSSMTParameters params;
  private final long index;
  private final byte[] secretKeySeed;
  private final byte[] secretKeyPRF;
  private final byte[] publicSeed;
  private final byte[] root;
  private final BDSStateMap bdsState;
  
  private XMSSMTPrivateKeyParameters(Builder builder)
  {
    super(true);
    params = params;
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    int n = params.getDigestSize();
    byte[] privateKey = privateKey;
    if (privateKey != null)
    {
      if (xmss == null)
      {
        throw new NullPointerException("xmss == null");
      }
      
      int totalHeight = params.getHeight();
      int indexSize = (totalHeight + 7) / 8;
      int secretKeySize = n;
      int secretKeyPRFSize = n;
      int publicSeedSize = n;
      int rootSize = n;
      





      int position = 0;
      index = XMSSUtil.bytesToXBigEndian(privateKey, position, indexSize);
      if (!XMSSUtil.isIndexValid(totalHeight, index))
      {
        throw new IllegalArgumentException("index out of bounds");
      }
      position += indexSize;
      secretKeySeed = XMSSUtil.extractBytesAtOffset(privateKey, position, secretKeySize);
      position += secretKeySize;
      secretKeyPRF = XMSSUtil.extractBytesAtOffset(privateKey, position, secretKeyPRFSize);
      position += secretKeyPRFSize;
      publicSeed = XMSSUtil.extractBytesAtOffset(privateKey, position, publicSeedSize);
      position += publicSeedSize;
      root = XMSSUtil.extractBytesAtOffset(privateKey, position, rootSize);
      position += rootSize;
      
      byte[] bdsStateBinary = XMSSUtil.extractBytesAtOffset(privateKey, position, privateKey.length - position);
      
      BDSStateMap bdsImport = null;
      try
      {
        bdsImport = (BDSStateMap)XMSSUtil.deserialize(bdsStateBinary);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      bdsImport.setXMSS(xmss);
      bdsState = bdsImport;

    }
    else
    {
      index = index;
      byte[] tmpSecretKeySeed = secretKeySeed;
      if (tmpSecretKeySeed != null)
      {
        if (tmpSecretKeySeed.length != n)
        {
          throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
        }
        secretKeySeed = tmpSecretKeySeed;
      }
      else
      {
        secretKeySeed = new byte[n];
      }
      byte[] tmpSecretKeyPRF = secretKeyPRF;
      if (tmpSecretKeyPRF != null)
      {
        if (tmpSecretKeyPRF.length != n)
        {
          throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
        }
        secretKeyPRF = tmpSecretKeyPRF;
      }
      else
      {
        secretKeyPRF = new byte[n];
      }
      byte[] tmpPublicSeed = publicSeed;
      if (tmpPublicSeed != null)
      {
        if (tmpPublicSeed.length != n)
        {
          throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
        }
        publicSeed = tmpPublicSeed;
      }
      else
      {
        publicSeed = new byte[n];
      }
      byte[] tmpRoot = root;
      if (tmpRoot != null)
      {
        if (tmpRoot.length != n)
        {
          throw new IllegalArgumentException("size of root needs to be equal size of digest");
        }
        root = tmpRoot;
      }
      else
      {
        root = new byte[n];
      }
      BDSStateMap tmpBDSState = bdsState;
      if (tmpBDSState != null)
      {
        bdsState = tmpBDSState;
      }
      else
      {
        long globalIndex = index;
        int totalHeight = params.getHeight();
        
        if ((XMSSUtil.isIndexValid(totalHeight, globalIndex)) && (tmpPublicSeed != null) && (tmpSecretKeySeed != null))
        {
          bdsState = new BDSStateMap(params, index, tmpPublicSeed, tmpSecretKeySeed);
        }
        else
        {
          bdsState = new BDSStateMap();
        }
      }
    }
  }
  


  public static class Builder
  {
    private final XMSSMTParameters params;
    
    private long index = 0L;
    private byte[] secretKeySeed = null;
    private byte[] secretKeyPRF = null;
    private byte[] publicSeed = null;
    private byte[] root = null;
    private BDSStateMap bdsState = null;
    private byte[] privateKey = null;
    private XMSSParameters xmss = null;
    

    public Builder(XMSSMTParameters params)
    {
      this.params = params;
    }
    
    public Builder withIndex(long val)
    {
      index = val;
      return this;
    }
    
    public Builder withSecretKeySeed(byte[] val)
    {
      secretKeySeed = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withSecretKeyPRF(byte[] val)
    {
      secretKeyPRF = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withPublicSeed(byte[] val)
    {
      publicSeed = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withRoot(byte[] val)
    {
      root = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withBDSState(BDSStateMap val)
    {
      bdsState = val;
      return this;
    }
    
    public Builder withPrivateKey(byte[] privateKeyVal, XMSSParameters xmssVal)
    {
      privateKey = XMSSUtil.cloneArray(privateKeyVal);
      xmss = xmssVal;
      return this;
    }
    
    public XMSSMTPrivateKeyParameters build()
    {
      return new XMSSMTPrivateKeyParameters(this, null);
    }
  }
  

  public byte[] toByteArray()
  {
    int n = params.getDigestSize();
    int indexSize = (params.getHeight() + 7) / 8;
    int secretKeySize = n;
    int secretKeyPRFSize = n;
    int publicSeedSize = n;
    int rootSize = n;
    int totalSize = indexSize + secretKeySize + secretKeyPRFSize + publicSeedSize + rootSize;
    byte[] out = new byte[totalSize];
    int position = 0;
    
    byte[] indexBytes = XMSSUtil.toBytesBigEndian(index, indexSize);
    XMSSUtil.copyBytesAtOffset(out, indexBytes, position);
    position += indexSize;
    
    XMSSUtil.copyBytesAtOffset(out, secretKeySeed, position);
    position += secretKeySize;
    
    XMSSUtil.copyBytesAtOffset(out, secretKeyPRF, position);
    position += secretKeyPRFSize;
    
    XMSSUtil.copyBytesAtOffset(out, publicSeed, position);
    position += publicSeedSize;
    
    XMSSUtil.copyBytesAtOffset(out, root, position);
    
    byte[] bdsStateOut = null;
    try
    {
      bdsStateOut = XMSSUtil.serialize(bdsState);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new RuntimeException("error serializing bds state");
    }
    return Arrays.concatenate(out, bdsStateOut);
  }
  
  public long getIndex()
  {
    return index;
  }
  
  public byte[] getSecretKeySeed()
  {
    return XMSSUtil.cloneArray(secretKeySeed);
  }
  
  public byte[] getSecretKeyPRF()
  {
    return XMSSUtil.cloneArray(secretKeyPRF);
  }
  
  public byte[] getPublicSeed()
  {
    return XMSSUtil.cloneArray(publicSeed);
  }
  
  public byte[] getRoot()
  {
    return XMSSUtil.cloneArray(root);
  }
  
  BDSStateMap getBDSState()
  {
    return bdsState;
  }
  
  public XMSSMTParameters getParameters()
  {
    return params;
  }
  
  public XMSSMTPrivateKeyParameters getNextKey()
  {
    BDSStateMap newState = new BDSStateMap(bdsState, params, getIndex(), publicSeed, secretKeySeed);
    
    return new Builder(params).withIndex(index + 1L)
      .withSecretKeySeed(secretKeySeed).withSecretKeyPRF(secretKeyPRF)
      .withPublicSeed(publicSeed).withRoot(root)
      .withBDSState(newState).build();
  }
}
