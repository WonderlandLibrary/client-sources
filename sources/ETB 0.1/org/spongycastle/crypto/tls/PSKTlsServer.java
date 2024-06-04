package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.agreement.DHStandardGroups;
import org.spongycastle.crypto.params.DHParameters;


public class PSKTlsServer
  extends AbstractTlsServer
{
  protected TlsPSKIdentityManager pskIdentityManager;
  
  public PSKTlsServer(TlsPSKIdentityManager pskIdentityManager)
  {
    this(new DefaultTlsCipherFactory(), pskIdentityManager);
  }
  
  public PSKTlsServer(TlsCipherFactory cipherFactory, TlsPSKIdentityManager pskIdentityManager)
  {
    super(cipherFactory);
    this.pskIdentityManager = pskIdentityManager;
  }
  
  protected TlsEncryptionCredentials getRSAEncryptionCredentials() throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected DHParameters getDHParameters()
  {
    return DHStandardGroups.rfc7919_ffdhe2048;
  }
  
  protected int[] getCipherSuites()
  {
    return new int[] { 49207, 49205, 178, 144 };
  }
  





  public TlsCredentials getCredentials()
    throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 13: 
    case 14: 
    case 24: 
      return null;
    
    case 15: 
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
    case 13: 
    case 14: 
    case 15: 
    case 24: 
      return createPSKKeyExchange(keyExchangeAlgorithm);
    }
    
    




    throw new TlsFatalAlert((short)80);
  }
  

  protected TlsKeyExchange createPSKKeyExchange(int keyExchange)
  {
    return new TlsPSKKeyExchange(keyExchange, supportedSignatureAlgorithms, null, pskIdentityManager, 
      getDHParameters(), namedCurves, clientECPointFormats, serverECPointFormats);
  }
}
