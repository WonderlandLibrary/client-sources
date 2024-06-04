package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.Arrays;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;








































public final class RouterStatusDescription
{
  private String nickname;
  private Fingerprint fingerprint;
  private byte[] digestDescriptor;
  private long lastPublication;
  private String ip;
  private int orPort;
  private int dirPort;
  private RouterFlags routerFlags;
  
  public RouterStatusDescription() {}
  
  public String getNickname()
  {
    return nickname;
  }
  
  public void setNickname(String nickname)
  {
    this.nickname = nickname;
  }
  
  public Fingerprint getFingerprint()
  {
    return fingerprint;
  }
  
  public void setFingerprint(String fingerprint)
  {
    this.fingerprint = new FingerprintImpl(fingerprint);
  }
  
  public void setFingerprint(byte[] fingerprint)
  {
    this.fingerprint = new FingerprintImpl(fingerprint);
  }
  
  public void setFingerprint(Fingerprint fingerprint)
  {
    this.fingerprint = fingerprint;
  }
  
  public byte[] getDigestDescriptor()
  {
    return digestDescriptor;
  }
  




  public String getDigestDescriptorAsHex()
  {
    return DatatypeConverter.printHexBinary(digestDescriptor).toUpperCase();
  }
  





  public void setDigestDescriptor(String digestDescriptorBase64)
  {
    String base64 = digestDescriptorBase64;
    while (base64.length() % 4 != 0)
    {
      base64 = base64 + "=";
    }
    setDigestDescriptor(DatatypeConverter.parseBase64Binary(base64));
  }
  
  public void setDigestDescriptor(byte[] digestDescriptor)
  {
    this.digestDescriptor = digestDescriptor;
  }
  
  public long getLastPublication()
  {
    return lastPublication;
  }
  
  public void setLastPublication(long lastPublication)
  {
    this.lastPublication = lastPublication;
  }
  
  public String getIp()
  {
    return ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public int getOrPort()
  {
    return orPort;
  }
  
  public void setOrPort(int orPort)
  {
    this.orPort = orPort;
  }
  




  public int getDirPort()
  {
    return dirPort;
  }
  




  public void setDirPort(int dirPort)
  {
    this.dirPort = dirPort;
  }
  








  public String toString()
  {
    return "RouterStatusDescription [nickname=" + nickname + ", fingerprint=" + fingerprint + ", digestDescriptor=" + Arrays.toString(digestDescriptor) + ", lastPublication=" + lastPublication + ", ip=" + ip + ", orPort=" + orPort + ", dirPort=" + dirPort + "]";
  }
  





  public RouterFlags getRouterFlags()
  {
    return routerFlags;
  }
  



  public void setRouterFlags(RouterFlags routerFlags)
  {
    this.routerFlags = routerFlags;
  }
  


  public void setRouterFlags(String routerFlags)
  {
    this.routerFlags = new RouterFlags(routerFlags);
  }
}
