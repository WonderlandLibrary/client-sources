package org.silvertunnel_ng.netlib.layer.tor;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptorUtil;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.RSAKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;































public class TorHiddenServicePrivateNetAddress
{
  private static final Logger LOG = LoggerFactory.getLogger(TorHiddenServicePrivateNetAddress.class);
  

  private final RSAPublicKey publicKey;
  

  private final RSAPrivateCrtKey privateKey;
  

  private final byte[] publicKeyHash;
  

  private final String publicOnionHostname;
  


  public TorHiddenServicePrivateNetAddress(RSAPublicKey publicKey, RSAPrivateCrtKey privateKey)
  {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    

    publicKeyHash = Encryption.getDigest(Encryption.getPKCS1EncodingFromRSAPublicKey(publicKey));
    
    String hiddenServicePermanentIdBase32 = RendezvousServiceDescriptorUtil.calculateZFromPublicKey(publicKey);
    publicOnionHostname = (hiddenServicePermanentIdBase32 + ".onion");
  }
  



  public byte[] getPublicKeyHash()
  {
    return publicKeyHash;
  }
  





  public String getPublicOnionHostname()
  {
    return publicOnionHostname;
  }
  



  protected String getId()
  {
    return "TorHiddenServicePrivateNetAddress(hostname=" + getPublicOnionHostname() + ")";
  }
  

  public String toString()
  {
    return getId();
  }
  
  public String toStringDetails()
  {
    return "TorHiddenServicePrivateNetAddress(publicKey=" + publicKey + ", privateKey=" + privateKey + ")";
  }
  


  public int hashCode()
  {
    return getId().hashCode();
  }
  

  public boolean equals(Object obj)
  {
    if ((obj == null) || (!(obj instanceof TorHiddenServicePrivateNetAddress)))
    {
      return false;
    }
    
    TorHiddenServicePrivateNetAddress other = (TorHiddenServicePrivateNetAddress)obj;
    return getId().equals(other.getId());
  }
  
  public RSAKeyPair getKeyPair()
  {
    return new RSAKeyPair(publicKey, privateKey);
  }
  




  public RSAPrivateCrtKey getPrivateKey()
  {
    return privateKey;
  }
  
  public RSAPublicKey getPublicKey()
  {
    return publicKey;
  }
}
