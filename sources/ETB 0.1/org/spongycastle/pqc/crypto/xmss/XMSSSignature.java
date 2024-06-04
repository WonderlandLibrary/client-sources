package org.spongycastle.pqc.crypto.xmss;

import java.util.List;
import org.spongycastle.util.Pack;




public final class XMSSSignature
  extends XMSSReducedSignature
  implements XMSSStoreableObjectInterface
{
  private final int index;
  private final byte[] random;
  
  private XMSSSignature(Builder builder)
  {
    super(builder);
    index = index;
    int n = getParams().getDigestSize();
    byte[] tmpRandom = random;
    if (tmpRandom != null)
    {
      if (tmpRandom.length != n)
      {
        throw new IllegalArgumentException("size of random needs to be equal to size of digest");
      }
      random = tmpRandom;
    }
    else
    {
      random = new byte[n];
    }
  }
  

  public static class Builder
    extends XMSSReducedSignature.Builder
  {
    private final XMSSParameters params;
    
    private int index = 0;
    private byte[] random = null;
    
    public Builder(XMSSParameters params)
    {
      super();
      this.params = params;
    }
    
    public Builder withIndex(int val)
    {
      index = val;
      return this;
    }
    
    public Builder withRandom(byte[] val)
    {
      random = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public Builder withSignature(byte[] val)
    {
      if (val == null)
      {
        throw new NullPointerException("signature == null");
      }
      int n = params.getDigestSize();
      int len = params.getWOTSPlus().getParams().getLen();
      int height = params.getHeight();
      int indexSize = 4;
      int randomSize = n;
      int signatureSize = len * n;
      int authPathSize = height * n;
      int position = 0;
      
      index = Pack.bigEndianToInt(val, position);
      position += indexSize;
      
      random = XMSSUtil.extractBytesAtOffset(val, position, randomSize);
      position += randomSize;
      withReducedSignature(XMSSUtil.extractBytesAtOffset(val, position, signatureSize + authPathSize));
      return this;
    }
    
    public XMSSSignature build()
    {
      return new XMSSSignature(this, null);
    }
  }
  

  public byte[] toByteArray()
  {
    int n = getParams().getDigestSize();
    int indexSize = 4;
    int randomSize = n;
    int signatureSize = getParams().getWOTSPlus().getParams().getLen() * n;
    int authPathSize = getParams().getHeight() * n;
    int totalSize = indexSize + randomSize + signatureSize + authPathSize;
    byte[] out = new byte[totalSize];
    int position = 0;
    
    Pack.intToBigEndian(index, out, position);
    position += indexSize;
    
    XMSSUtil.copyBytesAtOffset(out, random, position);
    position += randomSize;
    
    byte[][] signature = getWOTSPlusSignature().toByteArray();
    for (int i = 0; i < signature.length; i++)
    {
      XMSSUtil.copyBytesAtOffset(out, signature[i], position);
      position += n;
    }
    
    for (int i = 0; i < getAuthPath().size(); i++)
    {
      byte[] value = ((XMSSNode)getAuthPath().get(i)).getValue();
      XMSSUtil.copyBytesAtOffset(out, value, position);
      position += n;
    }
    return out;
  }
  
  public int getIndex()
  {
    return index;
  }
  
  public byte[] getRandom()
  {
    return XMSSUtil.cloneArray(random);
  }
}
