package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.Serializable;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;































public class SDIntroductionPoint
  implements Serializable
{
  private static final Logger LOG = LoggerFactory.getLogger(SDIntroductionPoint.class);
  


  private static Pattern patternSingle;
  


  private final String identifier;
  

  private final TcpipNetAddress ipAddressAndOnionPort;
  

  private final RSAPublicKey onionPublicKey;
  

  private final RSAPublicKey servicePublicKey;
  


  static
  {
    try
    {
      patternSingle = Pattern.compile("introduction-point ([a-z2-7]+)\nip-address (\\d+\\.\\d+\\.\\d+\\.\\d+)\nonion-port (\\d+)\nonion-key\n(-----BEGIN RSA PUBLIC KEY-----\n.*?-----END RSA PUBLIC KEY-----)\nservice-key\n(-----BEGIN RSA PUBLIC KEY-----\n.*?-----END RSA PUBLIC KEY-----)", 43);




    }
    catch (Exception e)
    {




      LOG.error("could not initialze class " + SDIntroductionPoint.class
        .getName(), e);
    }
  }
  







  public static Collection<SDIntroductionPoint> parseMultipleIntroductionPoints(String introductionPointsStr)
  {
    Collection<SDIntroductionPoint> result = new ArrayList();
    
    Matcher m = patternSingle.matcher(introductionPointsStr);
    for (int i = 0; m.find(); i++)
    {
      try
      {

        SDIntroductionPoint ip = new SDIntroductionPoint(m);
        result.add(ip);

      }
      catch (Exception e)
      {
        LOG.debug("invalid introduction-point i={} skipped", Integer.valueOf(i), e);
      }
    }
    
    return result;
  }
  







  public static String formatMultipleIntroductionPoints(Collection<SDIntroductionPoint> introPoints)
  {
    StringBuffer result = new StringBuffer();
    for (SDIntroductionPoint introPoint : introPoints)
    {
      result.append(introPoint.toIntroductionPoint());
    }
    return result.toString();
  }
  
  public Fingerprint getIdentifierAsFingerprint()
  {
    return new FingerprintImpl(Encoding.parseBase32(identifier));
  }
  






  private SDIntroductionPoint(Matcher m)
  {
    identifier = m.group(1);
    

    String ipAddress = m.group(2);
    int onionPort = Integer.parseInt(m.group(3));
    ipAddressAndOnionPort = new TcpipNetAddress(ipAddress + ":" + onionPort);
    

    String onionKeyStr = m.group(4);
    onionPublicKey = Encryption.extractPublicRSAKey(onionKeyStr);
    String serviceKeyStr = m.group(5);
    servicePublicKey = Encryption.extractPublicRSAKey(serviceKeyStr);
  }
  
















  public SDIntroductionPoint(String identifier, TcpipNetAddress ipAddressAndOnionPort, RSAPublicKey onionPublicKey, RSAPublicKey servicePublicKey)
  {
    this.identifier = identifier;
    this.ipAddressAndOnionPort = ipAddressAndOnionPort;
    this.onionPublicKey = onionPublicKey;
    this.servicePublicKey = servicePublicKey;
  }
  

  public String toString()
  {
    return identifier + "-" + ipAddressAndOnionPort;
  }
  











  public String toIntroductionPoint()
  {
    return "introduction-point " + identifier + "\n" + "ip-address " + ipAddressAndOnionPort.getIpaddressAsString() + "\n" + "onion-port " + ipAddressAndOnionPort.getPort() + "\n" + "onion-key\n" + Encryption.getPEMStringFromRSAPublicKey(onionPublicKey) + "service-key\n" + Encryption.getPEMStringFromRSAPublicKey(servicePublicKey);
  }
  




  public String getIdentifier()
  {
    return identifier;
  }
  
  public TcpipNetAddress getIpAddressAndOnionPort()
  {
    return ipAddressAndOnionPort;
  }
  
  public RSAPublicKey getOnionPublicKey()
  {
    return onionPublicKey;
  }
  
  public RSAPublicKey getServicePublicKey()
  {
    return servicePublicKey;
  }
}
