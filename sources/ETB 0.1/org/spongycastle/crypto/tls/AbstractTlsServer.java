package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.util.Arrays;





public abstract class AbstractTlsServer
  extends AbstractTlsPeer
  implements TlsServer
{
  protected TlsCipherFactory cipherFactory;
  protected TlsServerContext context;
  protected ProtocolVersion clientVersion;
  protected int[] offeredCipherSuites;
  protected short[] offeredCompressionMethods;
  protected Hashtable clientExtensions;
  protected boolean encryptThenMACOffered;
  protected short maxFragmentLengthOffered;
  protected boolean truncatedHMacOffered;
  protected Vector supportedSignatureAlgorithms;
  protected boolean eccCipherSuitesOffered;
  protected int[] namedCurves;
  protected short[] clientECPointFormats;
  protected short[] serverECPointFormats;
  protected ProtocolVersion serverVersion;
  protected int selectedCipherSuite;
  protected short selectedCompressionMethod;
  protected Hashtable serverExtensions;
  
  public AbstractTlsServer()
  {
    this(new DefaultTlsCipherFactory());
  }
  
  public AbstractTlsServer(TlsCipherFactory cipherFactory)
  {
    this.cipherFactory = cipherFactory;
  }
  
  protected boolean allowEncryptThenMAC()
  {
    return true;
  }
  
  protected boolean allowTruncatedHMac()
  {
    return false;
  }
  
  protected Hashtable checkServerExtensions()
  {
    return this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(serverExtensions);
  }
  
  protected abstract int[] getCipherSuites();
  
  protected short[] getCompressionMethods()
  {
    return new short[] { 0 };
  }
  
  protected ProtocolVersion getMaximumVersion()
  {
    return ProtocolVersion.TLSv11;
  }
  
  protected ProtocolVersion getMinimumVersion()
  {
    return ProtocolVersion.TLSv10;
  }
  


  protected boolean supportsClientECCCapabilities(int[] namedCurves, short[] ecPointFormats)
  {
    if (namedCurves == null)
    {





      return TlsECCUtils.hasAnySupportedNamedCurves();
    }
    
    for (int i = 0; i < namedCurves.length; i++)
    {
      int namedCurve = namedCurves[i];
      if ((NamedCurve.isValid(namedCurve)) && (
        (!NamedCurve.refersToASpecificNamedCurve(namedCurve)) || (TlsECCUtils.isSupportedNamedCurve(namedCurve))))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public void init(TlsServerContext context)
  {
    this.context = context;
  }
  
  public void notifyClientVersion(ProtocolVersion clientVersion)
    throws IOException
  {
    this.clientVersion = clientVersion;
  }
  





  public void notifyFallback(boolean isFallback)
    throws IOException
  {
    if ((isFallback) && (getMaximumVersion().isLaterVersionOf(clientVersion)))
    {
      throw new TlsFatalAlert((short)86);
    }
  }
  
  public void notifyOfferedCipherSuites(int[] offeredCipherSuites)
    throws IOException
  {
    this.offeredCipherSuites = offeredCipherSuites;
    eccCipherSuitesOffered = TlsECCUtils.containsECCCipherSuites(this.offeredCipherSuites);
  }
  
  public void notifyOfferedCompressionMethods(short[] offeredCompressionMethods)
    throws IOException
  {
    this.offeredCompressionMethods = offeredCompressionMethods;
  }
  
  public void processClientExtensions(Hashtable clientExtensions)
    throws IOException
  {
    this.clientExtensions = clientExtensions;
    
    if (clientExtensions != null)
    {
      encryptThenMACOffered = TlsExtensionsUtils.hasEncryptThenMACExtension(clientExtensions);
      
      maxFragmentLengthOffered = TlsExtensionsUtils.getMaxFragmentLengthExtension(clientExtensions);
      if ((maxFragmentLengthOffered >= 0) && (!MaxFragmentLength.isValid(maxFragmentLengthOffered)))
      {
        throw new TlsFatalAlert((short)47);
      }
      
      truncatedHMacOffered = TlsExtensionsUtils.hasTruncatedHMacExtension(clientExtensions);
      
      supportedSignatureAlgorithms = TlsUtils.getSignatureAlgorithmsExtension(clientExtensions);
      if (supportedSignatureAlgorithms != null)
      {




        if (!TlsUtils.isSignatureAlgorithmsExtensionAllowed(clientVersion))
        {
          throw new TlsFatalAlert((short)47);
        }
      }
      
      namedCurves = TlsECCUtils.getSupportedEllipticCurvesExtension(clientExtensions);
      clientECPointFormats = TlsECCUtils.getSupportedPointFormatsExtension(clientExtensions);
    }
  }
  













  public ProtocolVersion getServerVersion()
    throws IOException
  {
    if (getMinimumVersion().isEqualOrEarlierVersionOf(clientVersion))
    {
      ProtocolVersion maximumVersion = getMaximumVersion();
      if (clientVersion.isEqualOrEarlierVersionOf(maximumVersion))
      {
        return this.serverVersion = clientVersion;
      }
      if (clientVersion.isLaterVersionOf(maximumVersion))
      {
        return this.serverVersion = maximumVersion;
      }
    }
    throw new TlsFatalAlert((short)70);
  }
  






  public int getSelectedCipherSuite()
    throws IOException
  {
    Vector sigAlgs = TlsUtils.getUsableSignatureAlgorithms(supportedSignatureAlgorithms);
    







    boolean eccCipherSuitesEnabled = supportsClientECCCapabilities(namedCurves, clientECPointFormats);
    
    int[] cipherSuites = getCipherSuites();
    for (int i = 0; i < cipherSuites.length; i++)
    {
      int cipherSuite = cipherSuites[i];
      
      if ((Arrays.contains(offeredCipherSuites, cipherSuite)) && ((eccCipherSuitesEnabled) || 
        (!TlsECCUtils.isECCCipherSuite(cipherSuite))) && 
        (TlsUtils.isValidCipherSuiteForVersion(cipherSuite, serverVersion)) && 
        (TlsUtils.isValidCipherSuiteForSignatureAlgorithms(cipherSuite, sigAlgs)))
      {
        return this.selectedCipherSuite = cipherSuite;
      }
    }
    throw new TlsFatalAlert((short)40);
  }
  
  public short getSelectedCompressionMethod()
    throws IOException
  {
    short[] compressionMethods = getCompressionMethods();
    for (int i = 0; i < compressionMethods.length; i++)
    {
      if (Arrays.contains(offeredCompressionMethods, compressionMethods[i]))
      {
        return this.selectedCompressionMethod = compressionMethods[i];
      }
    }
    throw new TlsFatalAlert((short)40);
  }
  

  public Hashtable getServerExtensions()
    throws IOException
  {
    if ((encryptThenMACOffered) && (allowEncryptThenMAC()))
    {






      if (TlsUtils.isBlockCipherSuite(selectedCipherSuite))
      {
        TlsExtensionsUtils.addEncryptThenMACExtension(checkServerExtensions());
      }
    }
    
    if ((maxFragmentLengthOffered >= 0) && (MaxFragmentLength.isValid(maxFragmentLengthOffered)))
    {
      TlsExtensionsUtils.addMaxFragmentLengthExtension(checkServerExtensions(), maxFragmentLengthOffered);
    }
    
    if ((truncatedHMacOffered) && (allowTruncatedHMac()))
    {
      TlsExtensionsUtils.addTruncatedHMacExtension(checkServerExtensions());
    }
    
    if ((clientECPointFormats != null) && (TlsECCUtils.isECCCipherSuite(selectedCipherSuite)))
    {





      serverECPointFormats = new short[] { 0, 1, 2 };
      

      TlsECCUtils.addSupportedPointFormatsExtension(checkServerExtensions(), serverECPointFormats);
    }
    
    return serverExtensions;
  }
  
  public Vector getServerSupplementalData()
    throws IOException
  {
    return null;
  }
  
  public CertificateStatus getCertificateStatus()
    throws IOException
  {
    return null;
  }
  
  public CertificateRequest getCertificateRequest()
    throws IOException
  {
    return null;
  }
  
  public void processClientSupplementalData(Vector clientSupplementalData)
    throws IOException
  {
    if (clientSupplementalData != null)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void notifyClientCertificate(Certificate clientCertificate)
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
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
  





  public NewSessionTicket getNewSessionTicket()
    throws IOException
  {
    return new NewSessionTicket(0L, TlsUtils.EMPTY_BYTES);
  }
}
