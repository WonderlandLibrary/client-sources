package org.spongycastle.pqc.crypto.xmss;




final class WOTSPlusPrivateKeyParameters
{
  private final byte[][] privateKey;
  


  protected WOTSPlusPrivateKeyParameters(WOTSPlusParameters params, byte[][] privateKey)
  {
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    if (privateKey == null)
    {
      throw new NullPointerException("privateKey == null");
    }
    if (XMSSUtil.hasNullPointer(privateKey))
    {
      throw new NullPointerException("privateKey byte array == null");
    }
    if (privateKey.length != params.getLen())
    {
      throw new IllegalArgumentException("wrong privateKey format");
    }
    for (int i = 0; i < privateKey.length; i++)
    {
      if (privateKey[i].length != params.getDigestSize())
      {
        throw new IllegalArgumentException("wrong privateKey format");
      }
    }
    this.privateKey = XMSSUtil.cloneArray(privateKey);
  }
  
  protected byte[][] toByteArray()
  {
    return XMSSUtil.cloneArray(privateKey);
  }
}
