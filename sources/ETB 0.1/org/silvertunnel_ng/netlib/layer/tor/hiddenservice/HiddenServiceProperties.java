package org.silvertunnel_ng.netlib.layer.tor.hiddenservice;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashSet;
import java.util.Set;
import org.silvertunnel_ng.netlib.layer.tor.directory.SDIntroductionPoint;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.RSAKeyPair;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public class HiddenServiceProperties
{
  private static final Logger LOG = LoggerFactory.getLogger(HiddenServiceProperties.class);
  
  private RSAPublicKey pub;
  private RSAPrivateKey priv;
  private byte[] pubKeyHash;
  private int port;
  private Set<SDIntroductionPoint> introPoints;
  private int minimumNumberOfIntroPoints;
  
  public HiddenServiceProperties(int port, RSAKeyPair keyPair)
    throws TorException
  {
    init(port, new HashSet(), keyPair);
  }
  



  public HiddenServiceProperties(int port, Set<SDIntroductionPoint> introPoints, RSAKeyPair keyPair)
    throws TorException
  {
    init(port, introPoints, keyPair);
  }
  





















  private void init(int port, Set<SDIntroductionPoint> introPoints, RSAKeyPair keyPair)
    throws TorException
  {
    this.port = port;
    this.introPoints = introPoints;
    minimumNumberOfIntroPoints = 3;
    
    pub = keyPair.getPublic();
    priv = keyPair.getPrivate();
    
    pubKeyHash = Encryption.getDigest(Encryption.getPKCS1EncodingFromRSAPublicKey(pub));
  }
  

  public HiddenServiceProperties(String filename)
    throws IOException
  {
    throw new IOException("not implemented yet");
  }
  

  void writeToFile(String filename)
    throws IOException
  {
    throw new IOException("not implemented yet");
  }
  
  public void addIntroPoint(SDIntroductionPoint introPoint)
  {
    introPoints.add(introPoint);
  }
  




  public RSAPublicKey getPublicKey()
  {
    return pub;
  }
  
  public RSAPrivateKey getPrivateKey()
  {
    return priv;
  }
  
  public byte[] getPubKeyHash()
  {
    return pubKeyHash;
  }
  
  public int getPort()
  {
    return port;
  }
  
  public Set<SDIntroductionPoint> getIntroPoints()
  {
    return introPoints;
  }
  
  public int getNumberOfIntroPoints()
  {
    return introPoints.size();
  }
  
  public int getMinimumNumberOfIntroPoints()
  {
    return minimumNumberOfIntroPoints;
  }
}
