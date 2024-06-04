package org.silvertunnel_ng.netlib.layer.tor;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.RSAKeyPair;
import org.silvertunnel_ng.netlib.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public final class TorNetLayerUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(TorNetLayerUtil.class);
  
  private static final String FILENAME_HOSTNAME = "hostname";
  
  private static final String FILENAME_PRIVATE_KEY = "private_key";
  private static TorNetLayerUtil instance = new TorNetLayerUtil();
  



  public static TorNetLayerUtil getInstance()
  {
    return instance;
  }
  













  protected TorNetLayerUtil() {}
  













  public TorHiddenServicePrivateNetAddress readTorHiddenServicePrivateNetAddressFromFiles(File directory, boolean checkHostname)
    throws UnknownHostException, IOException
  {
    File privateKeyFile = new File(directory, "private_key");
    String privateKeyStr = FileUtil.readFile(privateKeyFile);
    

    File hostnameFile = new File(directory, "hostname");
    String hostnameStr = FileUtil.readFile(hostnameFile);
    

    return parseTorHiddenServicePrivateNetAddressFromStrings(privateKeyStr, hostnameStr, checkHostname);
  }
  






















  public TorHiddenServicePrivateNetAddress parseTorHiddenServicePrivateNetAddressFromStrings(String privateKeyPEMStr, String hostnameStr, boolean checkHostname)
    throws UnknownHostException, IOException
  {
    RSAKeyPair keyPair = Encryption.extractRSAKeyPair(privateKeyPEMStr);
    TorHiddenServicePrivateNetAddress result = new TorHiddenServicePrivateNetAddress(keyPair.getPublic(), keyPair.getPrivate());
    

    String hostnameLowerStr = hostnameStr == null ? null : hostnameStr.toLowerCase();
    boolean isHostnameOK = result.getPublicOnionHostname().equals(hostnameLowerStr);
    if (!isHostnameOK)
    {


      String msg = "hostname=" + hostnameStr + " does not belong to hidden service - \"" + result.getPublicOnionHostname() + "\" was expected with PEM=" + privateKeyPEMStr;
      
      if (checkHostname)
      {
        throw new UnknownHostException(msg);
      }
      

      if (hostnameStr != null)
      {
        LOG.debug(msg);
      }
    }
    

    return result;
  }
  




















  public void writeTorHiddenServicePrivateNetAddressToFiles(File directory, TorHiddenServicePrivateNetAddress netAddress)
    throws IOException
  {
    String pemStr = Encryption.getPEMStringFromRSAKeyPair(new RSAKeyPair(netAddress.getPublicKey(), netAddress
      .getPrivateKey()));
    File privateKeyFile = new File(directory, "private_key");
    FileUtil.writeFile(privateKeyFile, pemStr);
    

    File hostnameFile = new File(directory, "hostname");
    FileUtil.writeFile(hostnameFile, netAddress.getPublicOnionHostname());
  }
  








  public TorHiddenServicePrivateNetAddress createNewTorHiddenServicePrivateNetAddress()
  {
    RSAKeyPair keyPair = Encryption.createNewRSAKeyPair();
    return new TorHiddenServicePrivateNetAddress(keyPair.getPublic(), keyPair.getPrivate());
  }
}
