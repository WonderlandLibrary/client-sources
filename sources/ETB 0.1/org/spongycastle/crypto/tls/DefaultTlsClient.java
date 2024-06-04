package org.spongycastle.crypto.tls;

import java.io.IOException;




public abstract class DefaultTlsClient
  extends AbstractTlsClient
{
  public DefaultTlsClient() {}
  
  public DefaultTlsClient(TlsCipherFactory cipherFactory)
  {
    super(cipherFactory);
  }
  
  public int[] getCipherSuites()
  {
    return new int[] { 49195, 49187, 49161, 49199, 49191, 49171, 162, 64, 50, 158, 103, 51, 156, 60, 47 };
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
    return new TlsDHKeyExchange(keyExchange, supportedSignatureAlgorithms, null);
  }
  
  protected TlsKeyExchange createDHEKeyExchange(int keyExchange)
  {
    return new TlsDHEKeyExchange(keyExchange, supportedSignatureAlgorithms, null);
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
