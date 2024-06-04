package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.util.Pack;






final class OTSHashAddress
  extends XMSSAddress
{
  private static final int TYPE = 0;
  private final int otsAddress;
  private final int chainAddress;
  private final int hashAddress;
  
  private OTSHashAddress(Builder builder)
  {
    super(builder);
    otsAddress = otsAddress;
    chainAddress = chainAddress;
    hashAddress = hashAddress;
  }
  


  protected static class Builder
    extends XMSSAddress.Builder<Builder>
  {
    private int otsAddress = 0;
    private int chainAddress = 0;
    private int hashAddress = 0;
    
    protected Builder()
    {
      super();
    }
    
    protected Builder withOTSAddress(int val)
    {
      otsAddress = val;
      return this;
    }
    
    protected Builder withChainAddress(int val)
    {
      chainAddress = val;
      return this;
    }
    
    protected Builder withHashAddress(int val)
    {
      hashAddress = val;
      return this;
    }
    

    protected XMSSAddress build()
    {
      return new OTSHashAddress(this, null);
    }
    

    protected Builder getThis()
    {
      return this;
    }
  }
  

  protected byte[] toByteArray()
  {
    byte[] byteRepresentation = super.toByteArray();
    Pack.intToBigEndian(otsAddress, byteRepresentation, 16);
    Pack.intToBigEndian(chainAddress, byteRepresentation, 20);
    Pack.intToBigEndian(hashAddress, byteRepresentation, 24);
    return byteRepresentation;
  }
  
  protected int getOTSAddress()
  {
    return otsAddress;
  }
  
  protected int getChainAddress()
  {
    return chainAddress;
  }
  
  protected int getHashAddress()
  {
    return hashAddress;
  }
}
