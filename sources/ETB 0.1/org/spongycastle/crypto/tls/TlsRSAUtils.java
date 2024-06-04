package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;


public class TlsRSAUtils
{
  public TlsRSAUtils() {}
  
  public static byte[] generateEncryptedPreMasterSecret(TlsContext context, RSAKeyParameters rsaServerPublicKey, OutputStream output)
    throws IOException
  {
    byte[] premasterSecret = new byte[48];
    context.getSecureRandom().nextBytes(premasterSecret);
    TlsUtils.writeVersion(context.getClientVersion(), premasterSecret, 0);
    
    PKCS1Encoding encoding = new PKCS1Encoding(new RSABlindedEngine());
    encoding.init(true, new ParametersWithRandom(rsaServerPublicKey, context.getSecureRandom()));
    
    try
    {
      byte[] encryptedPreMasterSecret = encoding.processBlock(premasterSecret, 0, premasterSecret.length);
      
      if (TlsUtils.isSSL(context))
      {

        output.write(encryptedPreMasterSecret);
      }
      else
      {
        TlsUtils.writeOpaque16(encryptedPreMasterSecret, output);
      }
      

    }
    catch (InvalidCipherTextException e)
    {

      throw new TlsFatalAlert((short)80, e);
    }
    
    return premasterSecret;
  }
  




  public static byte[] safeDecryptPreMasterSecret(TlsContext context, RSAKeyParameters rsaServerPrivateKey, byte[] encryptedPreMasterSecret)
  {
    ProtocolVersion clientVersion = context.getClientVersion();
    

    boolean versionNumberCheckDisabled = false;
    




    byte[] fallback = new byte[48];
    context.getSecureRandom().nextBytes(fallback);
    
    byte[] M = Arrays.clone(fallback);
    try
    {
      PKCS1Encoding encoding = new PKCS1Encoding(new RSABlindedEngine(), fallback);
      encoding.init(false, new ParametersWithRandom(rsaServerPrivateKey, context
        .getSecureRandom()));
      
      M = encoding.processBlock(encryptedPreMasterSecret, 0, encryptedPreMasterSecret.length);
    }
    catch (Exception localException) {}
    















    if ((!versionNumberCheckDisabled) || (!clientVersion.isEqualOrEarlierVersionOf(ProtocolVersion.TLSv10)))
    {
















      int correct = clientVersion.getMajorVersion() ^ M[0] & 0xFF | clientVersion.getMinorVersion() ^ M[1] & 0xFF;
      correct |= correct >> 1;
      correct |= correct >> 2;
      correct |= correct >> 4;
      int mask = (correct & 0x1) - 1 ^ 0xFFFFFFFF;
      



      for (int i = 0; i < 48; i++)
      {
        M[i] = ((byte)(M[i] & (mask ^ 0xFFFFFFFF) | fallback[i] & mask));
      }
    }
    return M;
  }
}
