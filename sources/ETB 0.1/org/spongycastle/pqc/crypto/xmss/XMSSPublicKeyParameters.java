package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;









public final class XMSSPublicKeyParameters
  extends AsymmetricKeyParameter
  implements XMSSStoreableObjectInterface
{
  private final XMSSParameters params;
  private final byte[] root;
  private final byte[] publicSeed;
  
  private XMSSPublicKeyParameters(Builder builder)
  {
    super(false);
    params = params;
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    int n = params.getDigestSize();
    byte[] publicKey = publicKey;
    if (publicKey != null)
    {


      int rootSize = n;
      int publicSeedSize = n;
      
      int totalSize = rootSize + publicSeedSize;
      if (publicKey.length != totalSize)
      {
        throw new IllegalArgumentException("public key has wrong size");
      }
      int position = 0;
      





      root = XMSSUtil.extractBytesAtOffset(publicKey, position, rootSize);
      position += rootSize;
      publicSeed = XMSSUtil.extractBytesAtOffset(publicKey, position, publicSeedSize);

    }
    else
    {
      byte[] tmpRoot = root;
      if (tmpRoot != null)
      {
        if (tmpRoot.length != n)
        {
          throw new IllegalArgumentException("length of root must be equal to length of digest");
        }
        root = tmpRoot;
      }
      else
      {
        root = new byte[n];
      }
      byte[] tmpPublicSeed = publicSeed;
      if (tmpPublicSeed != null)
      {
        if (tmpPublicSeed.length != n)
        {
          throw new IllegalArgumentException("length of publicSeed must be equal to length of digest");
        }
        publicSeed = tmpPublicSeed;
      }
      else
      {
        publicSeed = new byte[n];
      }
    }
  }
  


  public static class Builder
  {
    private final XMSSParameters params;
    
    private byte[] root = null;
    private byte[] publicSeed = null;
    private byte[] publicKey = null;
    

    public Builder(XMSSParameters params)
    {
      this.params = params;
    }
    
    public Builder withRoot(byte[] val)
    {
      root = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withPublicSeed(byte[] val)
    {
      publicSeed = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withPublicKey(byte[] val)
    {
      publicKey = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public XMSSPublicKeyParameters build()
    {
      return new XMSSPublicKeyParameters(this, null);
    }
  }
  

  public byte[] toByteArray()
  {
    int n = params.getDigestSize();
    
    int rootSize = n;
    int publicSeedSize = n;
    
    int totalSize = rootSize + publicSeedSize;
    byte[] out = new byte[totalSize];
    int position = 0;
    





    XMSSUtil.copyBytesAtOffset(out, root, position);
    position += rootSize;
    
    XMSSUtil.copyBytesAtOffset(out, publicSeed, position);
    return out;
  }
  
  public byte[] getRoot()
  {
    return XMSSUtil.cloneArray(root);
  }
  
  public byte[] getPublicSeed()
  {
    return XMSSUtil.cloneArray(publicSeed);
  }
  
  public XMSSParameters getParameters()
  {
    return params;
  }
}
