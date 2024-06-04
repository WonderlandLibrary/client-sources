package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import org.spongycastle.util.Arrays;



public class SRPTlsClient
  extends AbstractTlsClient
{
  protected TlsSRPGroupVerifier groupVerifier;
  protected byte[] identity;
  protected byte[] password;
  
  public SRPTlsClient(byte[] identity, byte[] password)
  {
    this(new DefaultTlsCipherFactory(), new DefaultTlsSRPGroupVerifier(), identity, password);
  }
  
  public SRPTlsClient(TlsCipherFactory cipherFactory, byte[] identity, byte[] password)
  {
    this(cipherFactory, new DefaultTlsSRPGroupVerifier(), identity, password);
  }
  

  public SRPTlsClient(TlsCipherFactory cipherFactory, TlsSRPGroupVerifier groupVerifier, byte[] identity, byte[] password)
  {
    super(cipherFactory);
    this.groupVerifier = groupVerifier;
    this.identity = Arrays.clone(identity);
    this.password = Arrays.clone(password);
  }
  

  protected boolean requireSRPServerExtension()
  {
    return false;
  }
  
  public int[] getCipherSuites()
  {
    return new int[] { 49182 };
  }
  



  public Hashtable getClientExtensions()
    throws IOException
  {
    Hashtable clientExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(super.getClientExtensions());
    TlsSRPUtils.addSRPExtension(clientExtensions, identity);
    return clientExtensions;
  }
  
  public void processServerExtensions(Hashtable serverExtensions)
    throws IOException
  {
    if (!TlsUtils.hasExpectedEmptyExtensionData(serverExtensions, TlsSRPUtils.EXT_SRP, (short)47))
    {

      if (requireSRPServerExtension())
      {
        throw new TlsFatalAlert((short)47);
      }
    }
    
    super.processServerExtensions(serverExtensions);
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
  




  public TlsAuthentication getAuthentication()
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  protected TlsKeyExchange createSRPKeyExchange(int keyExchange)
  {
    return new TlsSRPKeyExchange(keyExchange, supportedSignatureAlgorithms, groupVerifier, identity, password);
  }
}
