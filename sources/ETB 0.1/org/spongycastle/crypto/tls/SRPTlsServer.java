package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;


public class SRPTlsServer
  extends AbstractTlsServer
{
  protected TlsSRPIdentityManager srpIdentityManager;
  protected byte[] srpIdentity = null;
  protected TlsSRPLoginParameters loginParameters = null;
  
  public SRPTlsServer(TlsSRPIdentityManager srpIdentityManager)
  {
    this(new DefaultTlsCipherFactory(), srpIdentityManager);
  }
  
  public SRPTlsServer(TlsCipherFactory cipherFactory, TlsSRPIdentityManager srpIdentityManager)
  {
    super(cipherFactory);
    this.srpIdentityManager = srpIdentityManager;
  }
  
  protected TlsSignerCredentials getDSASignerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsSignerCredentials getRSASignerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected int[] getCipherSuites()
  {
    return new int[] { 49186, 49183, 49185, 49182, 49184, 49181 };
  }
  







  public void processClientExtensions(Hashtable clientExtensions)
    throws IOException
  {
    super.processClientExtensions(clientExtensions);
    
    srpIdentity = TlsSRPUtils.getSRPExtension(clientExtensions);
  }
  
  public int getSelectedCipherSuite() throws IOException
  {
    int cipherSuite = super.getSelectedCipherSuite();
    
    if (TlsSRPUtils.isSRPCipherSuite(cipherSuite))
    {
      if (srpIdentity != null)
      {
        loginParameters = srpIdentityManager.getLoginParameters(srpIdentity);
      }
      
      if (loginParameters == null)
      {
        throw new TlsFatalAlert((short)115);
      }
    }
    
    return cipherSuite;
  }
  
  public TlsCredentials getCredentials() throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 21: 
      return null;
    
    case 22: 
      return getDSASignerCredentials();
    
    case 23: 
      return getRSASignerCredentials();
    }
    
    
    throw new TlsFatalAlert((short)80);
  }
  

  public TlsKeyExchange getKeyExchange()
    throws IOException
  {
    int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(selectedCipherSuite);
    
    switch (keyExchangeAlgorithm)
    {
    case 21: 
    case 22: 
    case 23: 
      return createSRPKeyExchange(keyExchangeAlgorithm);
    }
    
    




    throw new TlsFatalAlert((short)80);
  }
  

  protected TlsKeyExchange createSRPKeyExchange(int keyExchange)
  {
    return new TlsSRPKeyExchange(keyExchange, supportedSignatureAlgorithms, srpIdentity, loginParameters);
  }
}
