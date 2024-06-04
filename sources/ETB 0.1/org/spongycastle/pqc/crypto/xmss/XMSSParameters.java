package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;













public final class XMSSParameters
{
  private final XMSSOid oid;
  private final WOTSPlus wotsPlus;
  private final int height;
  private final int k;
  
  public XMSSParameters(int height, Digest digest)
  {
    if (height < 2)
    {
      throw new IllegalArgumentException("height must be >= 2");
    }
    if (digest == null)
    {
      throw new NullPointerException("digest == null");
    }
    
    wotsPlus = new WOTSPlus(new WOTSPlusParameters(digest));
    this.height = height;
    k = determineMinK();
    oid = DefaultXMSSOid.lookup(getDigest().getAlgorithmName(), getDigestSize(), getWinternitzParameter(), wotsPlus
      .getParams().getLen(), height);
  }
  



  private int determineMinK()
  {
    for (int k = 2; k <= height; k++)
    {
      if ((height - k) % 2 == 0)
      {
        return k;
      }
    }
    throw new IllegalStateException("should never happen...");
  }
  
  protected Digest getDigest()
  {
    return wotsPlus.getParams().getDigest();
  }
  





  public int getDigestSize()
  {
    return wotsPlus.getParams().getDigestSize();
  }
  





  public int getWinternitzParameter()
  {
    return wotsPlus.getParams().getWinternitzParameter();
  }
  





  public int getHeight()
  {
    return height;
  }
  
  WOTSPlus getWOTSPlus()
  {
    return wotsPlus;
  }
  
  int getK()
  {
    return k;
  }
}
