package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.Util;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



















































public final class RendezvousServiceDescriptor
  implements Serializable
{
  private static final Logger LOG = LoggerFactory.getLogger(RendezvousServiceDescriptor.class);
  



  private static Pattern serviceDescriptorStringPattern;
  



  private static final long MAX_SERVICE_DESCRIPTOR_AGE_IN_MS = 172800000L;
  



  private byte[] descriptorId;
  


  private String version = "2";
  


  private RSAPublicKey permanentPublicKey;
  


  private String z;
  


  private byte[] secretIdPart;
  

  private Long publicationTime;
  

  private Collection<String> protocolVersions = Arrays.asList(new String[] { "2" });
  

  private Collection<SDIntroductionPoint> introductionPoints;
  

  private String url;
  

  private PrivateKey privateKey;
  
  private static final String DEFAULT_SERVICE_DESCRIPTOR_VERSION = "2";
  

  static
  {
    try
    {
      serviceDescriptorStringPattern = Pattern.compile("^(rendezvous-service-descriptor ([a-z2-7]+)\nversion (\\d+)\npermanent-key\n(-----BEGIN RSA PUBLIC KEY-----\n.*?-----END RSA PUBLIC KEY-----)\nsecret-id-part ([a-z2-7]+)\npublication-time (\\S+ \\S+)\nprotocol-versions (\\d+(?:,\\d+)?(?:,\\d+)?(?:,\\d+)?(?:,\\d+)?)\nintroduction-points\n-----BEGIN MESSAGE-----\n(.*?)-----END MESSAGE-----\nsignature\n)-----BEGIN SIGNATURE-----\n(.*?)-----END SIGNATURE-----", 43);


    }
    catch (Exception e)
    {

      LOG.error("could not initialze class RendezvousServiceDescriptor", e);
    }
  }
  
  public String toServiceDescriptorString()
  {
    StringBuffer protocolVersionsStrBuf = new StringBuffer(10);
    boolean firstProtocolVersion = true;
    for (String protocolVersion : protocolVersions) {
      if (!firstProtocolVersion) {
        protocolVersionsStrBuf.append(",");
      }
      protocolVersionsStrBuf.append(protocolVersion);
      firstProtocolVersion = false;
    }
    String protocolVersionsStr = protocolVersionsStrBuf.toString();
    

    String introductionPointsStr = SDIntroductionPoint.formatMultipleIntroductionPoints(introductionPoints) + "\n";
    byte[] introductionPointsBytes = null;
    try {
      introductionPointsBytes = introductionPointsStr.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      LOG.debug("got UnsupportedEncodingException : {}", e.getMessage(), e);
    }
    int BASE64_COLUMN_WITH = 64;
    String introductionPointsBase64 = Encoding.toBase64(introductionPointsBytes, 64);
    



    String dataToSignStr = "rendezvous-service-descriptor " + Encoding.toBase32(descriptorId) + "\n" + "version " + version + "\n" + "permanent-key\n" + Encryption.getPEMStringFromRSAPublicKey(permanentPublicKey) + "secret-id-part " + Encoding.toBase32(secretIdPart) + "\n" + "publication-time " + Util.formatUtcTimestamp(publicationTime) + "\n" + "protocol-versions " + protocolVersionsStr + "\n" + "introduction-points\n" + "-----BEGIN MESSAGE-----\n" + introductionPointsBase64 + "-----END MESSAGE-----\n" + "signature\n";
    



    String signatureStr = "";
    if (privateKey != null)
    {
      byte[] dataToSign = null;
      try {
        dataToSign = dataToSignStr.getBytes("UTF-8");
      } catch (UnsupportedEncodingException e) {
        LOG.warn("unexpected", e);
      }
      byte[] signature = Encryption.signData(dataToSign, privateKey);
      signatureStr = Encoding.toBase64(signature, 64);
    }
    

    return dataToSignStr + "-----BEGIN SIGNATURE-----\n" + signatureStr + "-----END SIGNATURE-----\n";
  }
  







  public RendezvousServiceDescriptor(String hiddenServicePermanentIdBase32, int replica, long now, RSAPublicKey publicKey, RSAPrivateKey privateKey, Collection<SDIntroductionPoint> givenIntroPoints)
    throws TorException
  {
    this("2", hiddenServicePermanentIdBase32, replica, Long.valueOf(now), publicKey, privateKey, givenIntroPoints);
  }
  









  public RendezvousServiceDescriptor(String version, String hiddenServicePermanentIdBase32, int replica, Long publicationTime, RSAPublicKey publicKey, RSAPrivateKey privateKey, Collection<SDIntroductionPoint> givenIntroPoints)
    throws TorException
  {
    if (!"2".equals(version))
    {
      throw new TorException("not implemented: service descriptors of version != 2 are not supported, yet");
    }
    
    this.version = version;
    
    RendezvousServiceDescriptorKeyValues calculatedValues = RendezvousServiceDescriptorUtil.getRendezvousDescriptorId(hiddenServicePermanentIdBase32, replica, publicationTime);
    descriptorId = calculatedValues.getDescriptorId();
    
    this.publicationTime = publicationTime;
    permanentPublicKey = publicKey;
    this.privateKey = privateKey;
    updateURL();
    

    introductionPoints = givenIntroPoints;
    




    secretIdPart = calculatedValues.getSecretIdPart();
  }
  














  protected RendezvousServiceDescriptor(String serviceDescriptorStr, Long currentDate)
    throws TorException
  {
    this(serviceDescriptorStr, currentDate, true);
  }
  








  protected RendezvousServiceDescriptor(String serviceDescriptorStr, Long currentTime, boolean checkSignature)
    throws TorException
  {
    try
    {
      Matcher m = serviceDescriptorStringPattern.matcher(serviceDescriptorStr);
      m.find();
      

      String descriptorIdBase32 = m.group(2);
      descriptorId = Encoding.parseBase32(descriptorIdBase32);
      
      version = m.group(3);
      

      String permanentKeyStr = m.group(4);
      permanentPublicKey = Encryption.extractPublicRSAKey(permanentKeyStr);
      z = RendezvousServiceDescriptorUtil.calculateZFromPublicKey(permanentPublicKey);
      
      String secretIdPartBase32 = m.group(5);
      secretIdPart = Encoding.parseBase32(secretIdPartBase32);
      

      publicationTime = Util.parseUtcTimestampAsLong(m.group(6));
      if (!isPublicationTimeValid(currentTime)) {
        throw new TorException("invalid publication-time=" + publicationTime);
      }
      


      String protocolVersionsStr = m.group(7);
      protocolVersions = Arrays.asList(protocolVersionsStr.split(","));
      

      String introductionPointsBase64 = m.group(8);
      while (introductionPointsBase64.length() % 4 != 0) {
        introductionPointsBase64 = introductionPointsBase64 + "=";
      }
      byte[] introductionPointsBytes = DatatypeConverter.parseBase64Binary(introductionPointsBase64);
      String introductionPointsStr = new String(introductionPointsBytes, "UTF-8");
      introductionPoints = SDIntroductionPoint.parseMultipleIntroductionPoints(introductionPointsStr);
      if (LOG.isDebugEnabled()) {
        LOG.debug("ips = " + introductionPoints);
      }
      

      String signatureStr = m.group(9);
      while (signatureStr.length() % 4 != 0) {
        signatureStr = signatureStr + "=";
      }
      byte[] signature = DatatypeConverter.parseBase64Binary(signatureStr);
      String signedDataStr = m.group(1);
      byte[] signedData = null;
      try {
        signedData = signedDataStr.getBytes("UTF-8");
      } catch (UnsupportedEncodingException e) {
        LOG.warn("unexpected", e);
      }
      if ((checkSignature) && (!Encryption.verifySignature(signature, permanentPublicKey, signedData))) {
        throw new TorException("dirKeyCertification check failed");
      }
    }
    catch (TorException e)
    {
      throw e;
    }
    catch (Exception e) {
      LOG.info("long log", e);
      throw new TorException("could not parse service descriptor:" + e);
    }
  }
  


  void updateSignature()
    throws TorException
  {
    throw new UnsupportedOperationException("not yet implemented");
  }
  




  byte[] toByteArray()
  {
    try
    {
      return toServiceDescriptorString().getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      LOG.warn("may not occur", e); }
    return null;
  }
  
  private void updateURL()
  {
    try
    {
      byte[] hash = Encryption.getDigest(Encryption.getPKCS1EncodingFromRSAPublicKey(permanentPublicKey));
      
      byte[] h1 = new byte[10];
      System.arraycopy(hash, 0, h1, 0, 10);
      
      url = (Encoding.toBase32(h1) + ".onion");
    }
    catch (Exception e) {
      LOG.error("ServiceDescriptor.updateURL(): " + e.getMessage(), e);
      url = null;
    }
  }
  





  public boolean isPublicationTimeValid(Long currentTime)
  {
    if (publicationTime == null) {
      return false;
    }
    if ((publicationTime.longValue() > currentTime.longValue()) || (currentTime.longValue() - publicationTime.longValue() > 172800000L)) {
      return false;
    }
    return true;
  }
  




  public boolean isPublicationTimeValid()
  {
    return isPublicationTimeValid(Long.valueOf(System.currentTimeMillis()));
  }
  
  public String toString()
  {
    return "RendezvousServiceDescriptor=(descriptorIdBase32=" + Encoding.toBase32(descriptorId) + ",publicationTime=" + publicationTime + ",introductionPoints=" + introductionPoints + ")";
  }
  







  public String getURL()
  {
    return url;
  }
  
  public RSAPublicKey getPermamentPublicKey() {
    return permanentPublicKey;
  }
  
  public byte[] getDescriptorId() {
    return descriptorId;
  }
  
  public String getVersion() {
    return version;
  }
  
  public RSAPublicKey getPermanentPublicKey() {
    return permanentPublicKey;
  }
  
  public String getZ() {
    return z;
  }
  
  public byte[] getSecretIdPart() {
    return secretIdPart;
  }
  




  public Long getPublicationTime()
  {
    return publicationTime;
  }
  
  public Collection<String> getProtocolVersions() {
    return protocolVersions;
  }
  
  public Collection<SDIntroductionPoint> getIntroductionPoints() {
    return introductionPoints;
  }
}
