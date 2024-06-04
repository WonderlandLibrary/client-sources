package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;







public class TlsECDHKeyExchange
  extends AbstractTlsKeyExchange
{
  protected TlsSigner tlsSigner;
  protected int[] namedCurves;
  protected short[] clientECPointFormats;
  protected short[] serverECPointFormats;
  protected AsymmetricKeyParameter serverPublicKey;
  protected TlsAgreementCredentials agreementCredentials;
  protected ECPrivateKeyParameters ecAgreePrivateKey;
  protected ECPublicKeyParameters ecAgreePublicKey;
  
  public TlsECDHKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats)
  {
    super(keyExchange, supportedSignatureAlgorithms);
    
    switch (keyExchange)
    {
    case 19: 
      tlsSigner = new TlsRSASigner();
      break;
    case 17: 
      tlsSigner = new TlsECDSASigner();
      break;
    case 16: 
    case 18: 
    case 20: 
      tlsSigner = null;
      break;
    default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    }
    
    this.namedCurves = namedCurves;
    this.clientECPointFormats = clientECPointFormats;
    this.serverECPointFormats = serverECPointFormats;
  }
  
  public void init(TlsContext context)
  {
    super.init(context);
    
    if (tlsSigner != null)
    {
      tlsSigner.init(context);
    }
  }
  
  public void skipServerCredentials() throws IOException
  {
    if (keyExchange != 20)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void processServerCertificate(Certificate serverCertificate) throws IOException
  {
    if (keyExchange == 20)
    {
      throw new TlsFatalAlert((short)10);
    }
    if (serverCertificate.isEmpty())
    {
      throw new TlsFatalAlert((short)42);
    }
    
    org.spongycastle.asn1.x509.Certificate x509Cert = serverCertificate.getCertificateAt(0);
    
    SubjectPublicKeyInfo keyInfo = x509Cert.getSubjectPublicKeyInfo();
    try
    {
      serverPublicKey = PublicKeyFactory.createKey(keyInfo);
    }
    catch (RuntimeException e)
    {
      throw new TlsFatalAlert((short)43, e);
    }
    
    if (tlsSigner == null)
    {
      try
      {
        ecAgreePublicKey = TlsECCUtils.validateECPublicKey((ECPublicKeyParameters)serverPublicKey);
      }
      catch (ClassCastException e)
      {
        throw new TlsFatalAlert((short)46, e);
      }
      
      TlsUtils.validateKeyUsage(x509Cert, 8);
    }
    else
    {
      if (!tlsSigner.isValidPublicKey(serverPublicKey))
      {
        throw new TlsFatalAlert((short)46);
      }
      
      TlsUtils.validateKeyUsage(x509Cert, 128);
    }
    
    super.processServerCertificate(serverCertificate);
  }
  
  public boolean requiresServerKeyExchange()
  {
    switch (keyExchange)
    {
    case 17: 
    case 19: 
    case 20: 
      return true;
    }
    return false;
  }
  

  public byte[] generateServerKeyExchange()
    throws IOException
  {
    if (!requiresServerKeyExchange())
    {
      return null;
    }
    


    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    ecAgreePrivateKey = TlsECCUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), namedCurves, clientECPointFormats, buf);
    
    return buf.toByteArray();
  }
  
  public void processServerKeyExchange(InputStream input)
    throws IOException
  {
    if (!requiresServerKeyExchange())
    {
      throw new TlsFatalAlert((short)10);
    }
    


    ECDomainParameters curve_params = TlsECCUtils.readECParameters(namedCurves, clientECPointFormats, input);
    
    byte[] point = TlsUtils.readOpaque8(input);
    
    ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(clientECPointFormats, curve_params, point));
  }
  
  public void validateCertificateRequest(CertificateRequest certificateRequest)
    throws IOException
  {
    if (keyExchange == 20)
    {
      throw new TlsFatalAlert((short)40);
    }
    






    short[] types = certificateRequest.getCertificateTypes();
    for (int i = 0; i < types.length; i++)
    {
      switch (types[i])
      {
      case 1: 
      case 2: 
      case 64: 
      case 65: 
      case 66: 
        break;
      default: 
        throw new TlsFatalAlert((short)47);
      }
    }
  }
  
  public void processClientCredentials(TlsCredentials clientCredentials) throws IOException
  {
    if (keyExchange == 20)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    if ((clientCredentials instanceof TlsAgreementCredentials))
    {


      agreementCredentials = ((TlsAgreementCredentials)clientCredentials);
    }
    else if (!(clientCredentials instanceof TlsSignerCredentials))
    {




      throw new TlsFatalAlert((short)80);
    }
  }
  
  public void generateClientKeyExchange(OutputStream output) throws IOException
  {
    if (agreementCredentials == null)
    {
      ecAgreePrivateKey = TlsECCUtils.generateEphemeralClientKeyExchange(context.getSecureRandom(), serverECPointFormats, ecAgreePublicKey
        .getParameters(), output);
    }
  }
  
  public void processClientCertificate(Certificate clientCertificate) throws IOException
  {
    if (keyExchange == 20)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  


  public void processClientKeyExchange(InputStream input)
    throws IOException
  {
    if (ecAgreePublicKey != null)
    {

      return;
    }
    
    byte[] point = TlsUtils.readOpaque8(input);
    
    ECDomainParameters curve_params = ecAgreePrivateKey.getParameters();
    
    ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(serverECPointFormats, curve_params, point));
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    if (agreementCredentials != null)
    {
      return agreementCredentials.generateAgreement(ecAgreePublicKey);
    }
    
    if (ecAgreePrivateKey != null)
    {
      return TlsECCUtils.calculateECDHBasicAgreement(ecAgreePublicKey, ecAgreePrivateKey);
    }
    
    throw new TlsFatalAlert((short)80);
  }
}
