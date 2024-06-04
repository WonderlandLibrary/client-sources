package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.util.Pack;






final class HashTreeAddress
  extends XMSSAddress
{
  private static final int TYPE = 2;
  private static final int PADDING = 0;
  private final int padding;
  private final int treeHeight;
  private final int treeIndex;
  
  private HashTreeAddress(Builder builder)
  {
    super(builder);
    padding = 0;
    treeHeight = treeHeight;
    treeIndex = treeIndex;
  }
  


  protected static class Builder
    extends XMSSAddress.Builder<Builder>
  {
    private int treeHeight = 0;
    private int treeIndex = 0;
    
    protected Builder()
    {
      super();
    }
    
    protected Builder withTreeHeight(int val)
    {
      treeHeight = val;
      return this;
    }
    
    protected Builder withTreeIndex(int val)
    {
      treeIndex = val;
      return this;
    }
    

    protected XMSSAddress build()
    {
      return new HashTreeAddress(this, null);
    }
    

    protected Builder getThis()
    {
      return this;
    }
  }
  

  protected byte[] toByteArray()
  {
    byte[] byteRepresentation = super.toByteArray();
    Pack.intToBigEndian(padding, byteRepresentation, 16);
    Pack.intToBigEndian(treeHeight, byteRepresentation, 20);
    Pack.intToBigEndian(treeIndex, byteRepresentation, 24);
    return byteRepresentation;
  }
  
  protected int getPadding()
  {
    return padding;
  }
  
  protected int getTreeHeight()
  {
    return treeHeight;
  }
  
  protected int getTreeIndex()
  {
    return treeIndex;
  }
}
