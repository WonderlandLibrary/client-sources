package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;

































final class WOTSPlusParameters
{
  private final XMSSOid oid;
  private final Digest digest;
  private final int digestSize;
  private final int winternitzParameter;
  private final int len;
  private final int len1;
  private final int len2;
  
  protected WOTSPlusParameters(Digest digest)
  {
    if (digest == null)
    {
      throw new NullPointerException("digest == null");
    }
    this.digest = digest;
    digestSize = XMSSUtil.getDigestSize(digest);
    winternitzParameter = 16;
    len1 = ((int)Math.ceil(8 * digestSize / XMSSUtil.log2(winternitzParameter)));
    len2 = ((int)Math.floor(XMSSUtil.log2(len1 * (winternitzParameter - 1)) / XMSSUtil.log2(winternitzParameter)) + 1);
    
    len = (len1 + len2);
    oid = WOTSPlusOid.lookup(digest.getAlgorithmName(), digestSize, winternitzParameter, len);
    if (oid == null)
    {
      throw new IllegalArgumentException("cannot find OID for digest algorithm: " + digest.getAlgorithmName());
    }
  }
  





  protected XMSSOid getOid()
  {
    return oid;
  }
  





  protected Digest getDigest()
  {
    return digest;
  }
  





  protected int getDigestSize()
  {
    return digestSize;
  }
  





  protected int getWinternitzParameter()
  {
    return winternitzParameter;
  }
  





  protected int getLen()
  {
    return len;
  }
  





  protected int getLen1()
  {
    return len1;
  }
  





  protected int getLen2()
  {
    return len2;
  }
}
