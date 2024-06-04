package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class RendezvousServiceDescriptorUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(RendezvousServiceDescriptorUtil.class);
  




  private static final int TIMEPERIOD_V2_DESC_VALIDITY_SECONDS = 86400;
  





  public RendezvousServiceDescriptorUtil() {}
  




  public static RendezvousServiceDescriptorKeyValues getRendezvousDescriptorId(String hiddenServicePermanentIdBase32, int replica, Long now)
  {
    RendezvousServiceDescriptorKeyValues result = new RendezvousServiceDescriptorKeyValues();
    


    byte[] descriptorCookie = null;
    

    byte[] hiddenServicePermanentId = Encoding.parseBase32(hiddenServicePermanentIdBase32);
    
    result.setTimePeriod(getRendezvousTimePeriod(hiddenServicePermanentId, now));
    


    result.setSecretIdPart(getRendezvousSecretIdPart(result.getTimePeriod(), descriptorCookie, replica));
    

    byte[] unhashedDescriptorId = ByteArrayUtil.concatByteArrays(new byte[][] { hiddenServicePermanentId, result.getSecretIdPart() });
    if (hiddenServicePermanentId.length != 10)
    {
      LOG.warn("wrong length of hiddenServicePermanentId=" + Arrays.toString(hiddenServicePermanentId));
    }
    result.setDescriptorId(Encryption.getDigest(unhashedDescriptorId));
    
    return result;
  }
  













  public static String getRendezvousDescriptorIdBase32(String hiddenServicePermanentIdBase32, int replica, Long now)
  {
    return Encoding.toBase32(getRendezvousDescriptorId(hiddenServicePermanentIdBase32, replica, now).getDescriptorId());
  }
  











  public static int getRendezvousTimePeriod(byte[] hiddenServicePermanentId, Long now)
  {
    int nowInSeconds = (int)(now.longValue() / 1000L);
    
    int serviceIdHighestByte = (256 + hiddenServicePermanentId[0]) % 256;
    int result = (nowInSeconds + serviceIdHighestByte * 86400 / 256) / 86400;
    
    return result;
  }
  











  public static byte[] getRendezvousSecretIdPart(int timePeriod, byte[] descriptorCookieBytes, int replica)
  {
    int BYTES4 = 4;
    byte[] timePeriodBytes = Encoding.intToNByteArray(timePeriod, 4);
    if (descriptorCookieBytes == null)
    {
      descriptorCookieBytes = new byte[0];
    }
    int BYTES1 = 1;
    byte[] replicaBytes = Encoding.intToNByteArray(replica, 1);
    

    byte[] allBytes = ByteArrayUtil.concatByteArrays(new byte[][] { timePeriodBytes, descriptorCookieBytes, replicaBytes });
    return Encryption.getDigest(allBytes);
  }
  








  public static String calculateZFromPublicKey(RSAPublicKey publicKey)
  {
    byte[] publicKeyHash = Encryption.getDigest(Encryption.getPKCS1EncodingFromRSAPublicKey(publicKey));
    byte[] zBytes = new byte[10];
    System.arraycopy(publicKeyHash, 0, zBytes, 0, 10);
    return Encoding.toBase32(zBytes);
  }
}
