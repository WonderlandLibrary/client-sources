package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;







public final class XMSSNode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private final int height;
  private final byte[] value;
  
  protected XMSSNode(int height, byte[] value)
  {
    this.height = height;
    this.value = value;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public byte[] getValue()
  {
    return XMSSUtil.cloneArray(value);
  }
  
  protected XMSSNode clone()
  {
    return new XMSSNode(getHeight(), getValue());
  }
}
