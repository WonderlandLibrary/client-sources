package org.spongycastle.pqc.crypto.xmss;

import java.util.ArrayList;
import java.util.List;






public class XMSSReducedSignature
  implements XMSSStoreableObjectInterface
{
  private final XMSSParameters params;
  private final WOTSPlusSignature wotsPlusSignature;
  private final List<XMSSNode> authPath;
  
  protected XMSSReducedSignature(Builder builder)
  {
    params = params;
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    int n = params.getDigestSize();
    int len = params.getWOTSPlus().getParams().getLen();
    int height = params.getHeight();
    byte[] reducedSignature = reducedSignature;
    if (reducedSignature != null)
    {

      int signatureSize = len * n;
      int authPathSize = height * n;
      int totalSize = signatureSize + authPathSize;
      if (reducedSignature.length != totalSize)
      {
        throw new IllegalArgumentException("signature has wrong size");
      }
      int position = 0;
      byte[][] wotsPlusSignature = new byte[len][];
      for (int i = 0; i < wotsPlusSignature.length; i++)
      {
        wotsPlusSignature[i] = XMSSUtil.extractBytesAtOffset(reducedSignature, position, n);
        position += n;
      }
      this.wotsPlusSignature = new WOTSPlusSignature(params.getWOTSPlus().getParams(), wotsPlusSignature);
      
      List<XMSSNode> nodeList = new ArrayList();
      for (int i = 0; i < height; i++)
      {
        nodeList.add(new XMSSNode(i, XMSSUtil.extractBytesAtOffset(reducedSignature, position, n)));
        position += n;
      }
      authPath = nodeList;

    }
    else
    {
      WOTSPlusSignature tmpSignature = wotsPlusSignature;
      if (tmpSignature != null)
      {
        this.wotsPlusSignature = tmpSignature;
      }
      else
      {
        this.wotsPlusSignature = new WOTSPlusSignature(params.getWOTSPlus().getParams(), new byte[len][n]);
      }
      List<XMSSNode> tmpAuthPath = authPath;
      if (tmpAuthPath != null)
      {
        if (tmpAuthPath.size() != height)
        {
          throw new IllegalArgumentException("size of authPath needs to be equal to height of tree");
        }
        authPath = tmpAuthPath;
      }
      else
      {
        authPath = new ArrayList();
      }
    }
  }
  


  public static class Builder
  {
    private final XMSSParameters params;
    
    private WOTSPlusSignature wotsPlusSignature = null;
    private List<XMSSNode> authPath = null;
    private byte[] reducedSignature = null;
    

    public Builder(XMSSParameters params)
    {
      this.params = params;
    }
    
    public Builder withWOTSPlusSignature(WOTSPlusSignature val)
    {
      wotsPlusSignature = val;
      return this;
    }
    
    public Builder withAuthPath(List<XMSSNode> val)
    {
      authPath = val;
      return this;
    }
    
    public Builder withReducedSignature(byte[] val)
    {
      reducedSignature = XMSSUtil.cloneArray(val);
      return this;
    }
    
    public XMSSReducedSignature build()
    {
      return new XMSSReducedSignature(this);
    }
  }
  

  public byte[] toByteArray()
  {
    int n = params.getDigestSize();
    int signatureSize = params.getWOTSPlus().getParams().getLen() * n;
    int authPathSize = params.getHeight() * n;
    int totalSize = signatureSize + authPathSize;
    byte[] out = new byte[totalSize];
    int position = 0;
    
    byte[][] signature = wotsPlusSignature.toByteArray();
    for (int i = 0; i < signature.length; i++)
    {
      XMSSUtil.copyBytesAtOffset(out, signature[i], position);
      position += n;
    }
    
    for (int i = 0; i < authPath.size(); i++)
    {
      byte[] value = ((XMSSNode)authPath.get(i)).getValue();
      XMSSUtil.copyBytesAtOffset(out, value, position);
      position += n;
    }
    return out;
  }
  
  public XMSSParameters getParams()
  {
    return params;
  }
  
  public WOTSPlusSignature getWOTSPlusSignature()
  {
    return wotsPlusSignature;
  }
  
  public List<XMSSNode> getAuthPath()
  {
    return authPath;
  }
}
