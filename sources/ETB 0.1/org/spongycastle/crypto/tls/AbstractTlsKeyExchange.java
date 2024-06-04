package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public abstract class AbstractTlsKeyExchange
  implements TlsKeyExchange
{
  protected int keyExchange;
  protected Vector supportedSignatureAlgorithms;
  protected TlsContext context;
  
  protected AbstractTlsKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms)
  {
    this.keyExchange = keyExchange;
    this.supportedSignatureAlgorithms = supportedSignatureAlgorithms;
  }
  
  protected DigitallySigned parseSignature(InputStream input) throws IOException
  {
    DigitallySigned signature = DigitallySigned.parse(context, input);
    SignatureAndHashAlgorithm signatureAlgorithm = signature.getAlgorithm();
    if (signatureAlgorithm != null)
    {
      TlsUtils.verifySupportedSignatureAlgorithm(supportedSignatureAlgorithms, signatureAlgorithm);
    }
    return signature;
  }
  
  public void init(TlsContext context)
  {
    this.context = context;
    
    ProtocolVersion clientVersion = context.getClientVersion();
    
    if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(clientVersion))
    {













      if (supportedSignatureAlgorithms == null)
      {
        switch (keyExchange)
        {

        case 3: 
        case 7: 
        case 22: 
          supportedSignatureAlgorithms = TlsUtils.getDefaultDSSSignatureAlgorithms();
          break;
        


        case 16: 
        case 17: 
          supportedSignatureAlgorithms = TlsUtils.getDefaultECDSASignatureAlgorithms();
          break;
        


        case 1: 
        case 5: 
        case 9: 
        case 15: 
        case 18: 
        case 19: 
        case 23: 
          supportedSignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
          break;
        case 13: case 14: 
        case 21: case 24: 
          break;
        case 2: case 4: 
        case 6: case 8: 
        case 10: case 11: 
        case 12: 
        case 20: 
        default: 
          throw new IllegalStateException("unsupported key exchange algorithm");
        }
        
      }
    } else if (supportedSignatureAlgorithms != null)
    {
      throw new IllegalStateException("supported_signature_algorithms not allowed for " + clientVersion);
    }
  }
  
  public void processServerCertificate(Certificate serverCertificate)
    throws IOException
  {
    if (supportedSignatureAlgorithms == null) {}
  }
  














  public void processServerCredentials(TlsCredentials serverCredentials)
    throws IOException
  {
    processServerCertificate(serverCredentials.getCertificate());
  }
  
  public boolean requiresServerKeyExchange()
  {
    return false;
  }
  
  public byte[] generateServerKeyExchange()
    throws IOException
  {
    if (requiresServerKeyExchange())
    {
      throw new TlsFatalAlert((short)80);
    }
    return null;
  }
  
  public void skipServerKeyExchange()
    throws IOException
  {
    if (requiresServerKeyExchange())
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void processServerKeyExchange(InputStream input)
    throws IOException
  {
    if (!requiresServerKeyExchange())
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  

  public void skipClientCredentials()
    throws IOException
  {}
  

  public void processClientCertificate(Certificate clientCertificate)
    throws IOException
  {}
  

  public void processClientKeyExchange(InputStream input)
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
}
