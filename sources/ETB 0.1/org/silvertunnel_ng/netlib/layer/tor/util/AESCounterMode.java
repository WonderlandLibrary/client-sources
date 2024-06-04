package org.silvertunnel_ng.netlib.layer.tor.util;

import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





























public final class AESCounterMode
{
  private static final Logger LOG = LoggerFactory.getLogger(AESCounterMode.class);
  

  private static final String ALGORITHM = "AES";
  

  public static final int KEY_LEN = 16;
  
  private final Cipher cipher;
  
  private int blockSize;
  
  private byte[] counterBuffer;
  
  private byte[] streamBuffer;
  
  private int streamNext;
  

  public AESCounterMode(Key key)
  {
    if (key.getEncoded().length != 16)
    {
      String msg = "AESCounterMode.<init>: HINT: key.length!=16 bytes/128 bits";
      LOG.warn("AESCounterMode.<init>: HINT: key.length!=16 bytes/128 bits");
      LOG.debug("AESCounterMode.<init>: HINT: key.length!=16 bytes/128 bits", new Exception("Exception to log a stack trace"));
    }
    

    try
    {
      cipher = Cipher.getInstance("AES/ECB/NoPadding");
      cipher.init(1, key);
      blockSize = cipher.getBlockSize();
      

      counterBuffer = new byte[blockSize];
      streamBuffer = new byte[blockSize];
      streamNext = blockSize;
    }
    catch (GeneralSecurityException e)
    {
      throw new RuntimeException(e);
    }
  }
  






  public AESCounterMode(byte[] key)
  {
    this(new SecretKeySpec(key, "AES"));
  }
  






  private byte nextStreamByte()
  {
    streamNext += 1;
    
    if (streamNext >= blockSize)
    {

      streamBuffer = cipher.update(counterBuffer);
      streamNext = 0;
      
      int j = blockSize - 1;
      do
      {
        int tmp53_52 = j; byte[] tmp53_49 = counterBuffer;tmp53_49[tmp53_52] = ((byte)(tmp53_49[tmp53_52] + 1));
        j--;
      }
      while ((counterBuffer[(j + 1)] == 0) && (j >= 0));
    }
    
    return streamBuffer[streamNext];
  }
  









  public byte[] processStream(byte[] input)
  {
    byte[] out = new byte[input.length];
    for (int i = 0; i < input.length; i++)
    {
      byte cipherBytes = nextStreamByte();
      out[i] = ((byte)(input[i] + 256 ^ cipherBytes + 256));
    }
    
    return out;
  }
}
