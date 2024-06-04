package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;



public abstract class AbstractTlsClient
  extends AbstractTlsPeer
  implements TlsClient
{
  protected TlsCipherFactory cipherFactory;
  protected TlsClientContext context;
  protected Vector supportedSignatureAlgorithms;
  protected int[] namedCurves;
  protected short[] clientECPointFormats;
  protected short[] serverECPointFormats;
  protected int selectedCipherSuite;
  protected short selectedCompressionMethod;
  
  public AbstractTlsClient()
  {
    this(new DefaultTlsCipherFactory());
  }
  
  public AbstractTlsClient(TlsCipherFactory cipherFactory)
  {
    this.cipherFactory = cipherFactory;
  }
  
  protected boolean allowUnexpectedServerExtension(Integer extensionType, byte[] extensionData)
    throws IOException
  {
    switch (extensionType.intValue())
    {





    case 10: 
      TlsECCUtils.readSupportedEllipticCurvesExtension(extensionData);
      return true; }
    
    return false;
  }
  

  protected void checkForUnexpectedServerExtension(Hashtable serverExtensions, Integer extensionType)
    throws IOException
  {
    byte[] extensionData = TlsUtils.getExtensionData(serverExtensions, extensionType);
    if ((extensionData != null) && (!allowUnexpectedServerExtension(extensionType, extensionData)))
    {
      throw new TlsFatalAlert((short)47);
    }
  }
  
  public void init(TlsClientContext context)
  {
    this.context = context;
  }
  
  public TlsSession getSessionToResume()
  {
    return null;
  }
  







  public ProtocolVersion getClientHelloRecordLayerVersion()
  {
    return getClientVersion();
  }
  
  public ProtocolVersion getClientVersion()
  {
    return ProtocolVersion.TLSv12;
  }
  





  public boolean isFallback()
  {
    return false;
  }
  
  public Hashtable getClientExtensions()
    throws IOException
  {
    Hashtable clientExtensions = null;
    
    ProtocolVersion clientVersion = context.getClientVersion();
    




    if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(clientVersion))
    {


      supportedSignatureAlgorithms = TlsUtils.getDefaultSupportedSignatureAlgorithms();
      
      clientExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(clientExtensions);
      
      TlsUtils.addSignatureAlgorithmsExtension(clientExtensions, supportedSignatureAlgorithms);
    }
    
    if (TlsECCUtils.containsECCCipherSuites(getCipherSuites()))
    {










      namedCurves = new int[] { 23, 24 };
      clientECPointFormats = new short[] { 0, 1, 2 };
      

      clientExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(clientExtensions);
      
      TlsECCUtils.addSupportedEllipticCurvesExtension(clientExtensions, namedCurves);
      TlsECCUtils.addSupportedPointFormatsExtension(clientExtensions, clientECPointFormats);
    }
    
    return clientExtensions;
  }
  
  public ProtocolVersion getMinimumVersion()
  {
    return ProtocolVersion.TLSv10;
  }
  
  public void notifyServerVersion(ProtocolVersion serverVersion)
    throws IOException
  {
    if (!getMinimumVersion().isEqualOrEarlierVersionOf(serverVersion))
    {
      throw new TlsFatalAlert((short)70);
    }
  }
  
  public short[] getCompressionMethods()
  {
    return new short[] { 0 };
  }
  


  public void notifySessionID(byte[] sessionID) {}
  

  public void notifySelectedCipherSuite(int selectedCipherSuite)
  {
    this.selectedCipherSuite = selectedCipherSuite;
  }
  
  public void notifySelectedCompressionMethod(short selectedCompressionMethod)
  {
    this.selectedCompressionMethod = selectedCompressionMethod;
  }
  




  public void processServerExtensions(Hashtable serverExtensions)
    throws IOException
  {
    if (serverExtensions != null)
    {



      checkForUnexpectedServerExtension(serverExtensions, TlsUtils.EXT_signature_algorithms);
      
      checkForUnexpectedServerExtension(serverExtensions, TlsECCUtils.EXT_elliptic_curves);
      
      if (TlsECCUtils.isECCCipherSuite(selectedCipherSuite))
      {
        serverECPointFormats = TlsECCUtils.getSupportedPointFormatsExtension(serverExtensions);
      }
      else
      {
        checkForUnexpectedServerExtension(serverExtensions, TlsECCUtils.EXT_ec_point_formats);
      }
      



      checkForUnexpectedServerExtension(serverExtensions, TlsExtensionsUtils.EXT_padding);
    }
  }
  
  public void processServerSupplementalData(Vector serverSupplementalData)
    throws IOException
  {
    if (serverSupplementalData != null)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public Vector getClientSupplementalData()
    throws IOException
  {
    return null;
  }
  
  public TlsCompression getCompression()
    throws IOException
  {
    switch (selectedCompressionMethod)
    {
    case 0: 
      return new TlsNullCompression();
    }
    
    




    throw new TlsFatalAlert((short)80);
  }
  

  public TlsCipher getCipher()
    throws IOException
  {
    int encryptionAlgorithm = TlsUtils.getEncryptionAlgorithm(selectedCipherSuite);
    int macAlgorithm = TlsUtils.getMACAlgorithm(selectedCipherSuite);
    
    return cipherFactory.createCipher(context, encryptionAlgorithm, macAlgorithm);
  }
  
  public void notifyNewSessionTicket(NewSessionTicket newSessionTicket)
    throws IOException
  {}
}
