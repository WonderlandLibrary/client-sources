package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;













public final class XMSSMTParameters
{
  private final XMSSOid oid;
  private final XMSSParameters xmssParams;
  private final int height;
  private final int layers;
  
  public XMSSMTParameters(int height, int layers, Digest digest)
  {
    this.height = height;
    this.layers = layers;
    xmssParams = new XMSSParameters(xmssTreeHeight(height, layers), digest);
    oid = DefaultXMSSMTOid.lookup(getDigest().getAlgorithmName(), getDigestSize(), getWinternitzParameter(), 
      getLen(), getHeight(), layers);
  }
  



  private static int xmssTreeHeight(int height, int layers)
    throws IllegalArgumentException
  {
    if (height < 2)
    {
      throw new IllegalArgumentException("totalHeight must be > 1");
    }
    if (height % layers != 0)
    {
      throw new IllegalArgumentException("layers must divide totalHeight without remainder");
    }
    if (height / layers == 1)
    {
      throw new IllegalArgumentException("height / layers must be greater than 1");
    }
    return height / layers;
  }
  





  public int getHeight()
  {
    return height;
  }
  





  public int getLayers()
  {
    return layers;
  }
  
  protected XMSSParameters getXMSSParameters()
  {
    return xmssParams;
  }
  
  protected WOTSPlus getWOTSPlus()
  {
    return xmssParams.getWOTSPlus();
  }
  
  protected Digest getDigest()
  {
    return xmssParams.getDigest();
  }
  





  public int getDigestSize()
  {
    return xmssParams.getDigestSize();
  }
  





  public int getWinternitzParameter()
  {
    return xmssParams.getWinternitzParameter();
  }
  
  protected int getLen()
  {
    return xmssParams.getWOTSPlus().getParams().getLen();
  }
}
