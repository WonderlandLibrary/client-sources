package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.agreement.DHStandardGroups;
import org.spongycastle.crypto.params.DHParameters;





public abstract class DefaultTlsServer
  extends AbstractTlsServer
{
  public DefaultTlsServer() {}
  
  public DefaultTlsServer(TlsCipherFactory cipherFactory)
  {
    super(cipherFactory);
  }
  
  protected TlsSignerCredentials getDSASignerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsSignerCredentials getECDSASignerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsEncryptionCredentials getRSAEncryptionCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsSignerCredentials getRSASignerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected DHParameters getDHParameters()
  {
    return DHStandardGroups.rfc7919_ffdhe2048;
  }
  
  protected int[] getCipherSuites()
  {
    return new int[] { 49200, 49199, 49192, 49191, 49172, 49171, 159, 158, 107, 103, 57, 51, 157, 156, 61, 60, 53, 47 };
  }
  




















  public TlsCredentials getCredentials()
    throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 3: 
      return getDSASignerCredentials();
    
    case 11: 
    case 20: 
      return null;
    
    case 17: 
      return getECDSASignerCredentials();
    
    case 5: 
    case 19: 
      return getRSASignerCredentials();
    
    case 1: 
      return getRSAEncryptionCredentials();
    }
    
    
    throw new TlsFatalAlert((short)80);
  }
  

  public TlsKeyExchange getKeyExchange()
    throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 7: 
    case 9: 
    case 11: 
      return createDHKeyExchange(keyExchangeAlgorithm);
    
    case 3: 
    case 5: 
      return createDHEKeyExchange(keyExchangeAlgorithm);
    
    case 16: 
    case 18: 
    case 20: 
      return createECDHKeyExchange(keyExchangeAlgorithm);
    
    case 17: 
    case 19: 
      return createECDHEKeyExchange(keyExchangeAlgorithm);
    
    case 1: 
      return createRSAKeyExchange();
    }
    
    




    throw new TlsFatalAlert((short)80);
  }
  

  protected TlsKeyExchange createDHKeyExchange(int keyExchange)
  {
    return new TlsDHKeyExchange(keyExchange, supportedSignatureAlgorithms, getDHParameters());
  }
  
  protected TlsKeyExchange createDHEKeyExchange(int keyExchange)
  {
    return new TlsDHEKeyExchange(keyExchange, supportedSignatureAlgorithms, getDHParameters());
  }
  
  protected TlsKeyExchange createECDHKeyExchange(int keyExchange)
  {
    return new TlsECDHKeyExchange(keyExchange, supportedSignatureAlgorithms, namedCurves, clientECPointFormats, serverECPointFormats);
  }
  

  protected TlsKeyExchange createECDHEKeyExchange(int keyExchange)
  {
    return new TlsECDHEKeyExchange(keyExchange, supportedSignatureAlgorithms, namedCurves, clientECPointFormats, serverECPointFormats);
  }
  

  protected TlsKeyExchange createRSAKeyExchange()
  {
    return new TlsRSAKeyExchange(supportedSignatureAlgorithms);
  }
}
