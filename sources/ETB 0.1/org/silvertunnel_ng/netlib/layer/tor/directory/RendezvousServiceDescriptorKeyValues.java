package org.silvertunnel_ng.netlib.layer.tor.directory;

import org.silvertunnel_ng.netlib.util.ByteArrayUtil;











































public class RendezvousServiceDescriptorKeyValues
{
  private int timePeriod;
  private byte[] secretIdPart;
  private byte[] descriptorId;
  
  public RendezvousServiceDescriptorKeyValues() {}
  
  public String toString()
  {
    return "timePeriod=" + timePeriod + ",secretIdPart=" + ByteArrayUtil.showAsString(secretIdPart) + ",descriptorId=" + ByteArrayUtil.showAsString(descriptorId);
  }
  




  public int getTimePeriod()
  {
    return timePeriod;
  }
  
  public void setTimePeriod(int timePeriod)
  {
    this.timePeriod = timePeriod;
  }
  
  public byte[] getSecretIdPart()
  {
    return secretIdPart;
  }
  
  public void setSecretIdPart(byte[] secretIdPart)
  {
    this.secretIdPart = secretIdPart;
  }
  
  public byte[] getDescriptorId()
  {
    return descriptorId;
  }
  
  public void setDescriptorId(byte[] descriptorId)
  {
    this.descriptorId = descriptorId;
  }
}
