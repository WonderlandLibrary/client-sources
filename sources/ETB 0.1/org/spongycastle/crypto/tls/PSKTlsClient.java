package org.spongycastle.crypto.tls;

import java.io.IOException;

public class PSKTlsClient
  extends AbstractTlsClient
{
  protected TlsPSKIdentity pskIdentity;
  
  public PSKTlsClient(TlsPSKIdentity pskIdentity)
  {
    this(new DefaultTlsCipherFactory(), pskIdentity);
  }
  
  public PSKTlsClient(TlsCipherFactory cipherFactory, TlsPSKIdentity pskIdentity)
  {
    super(cipherFactory);
    this.pskIdentity = pskIdentity;
  }
  
  public int[] getCipherSuites()
  {
    return new int[] { 49207, 49205, 178, 144 };
  }
  





  public TlsKeyExchange getKeyExchange()
    throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 13: 
    case 14: 
    case 15: 
    case 24: 
      return createPSKKeyExchange(keyExchangeAlgorithm);
    }
    
    




    throw new TlsFatalAlert((short)80);
  }
  




  public TlsAuthentication getAuthentication()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsKeyExchange createPSKKeyExchange(int keyExchange)
  {
    return new TlsPSKKeyExchange(keyExchange, supportedSignatureAlgorithms, pskIdentity, null, null, namedCurves, clientECPointFormats, serverECPointFormats);
  }
}
