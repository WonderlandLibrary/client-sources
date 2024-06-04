package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;








public class TlsDHKeyExchange
  extends AbstractTlsKeyExchange
{
  protected TlsSigner tlsSigner;
  protected DHParameters dhParameters;
  protected AsymmetricKeyParameter serverPublicKey;
  protected TlsAgreementCredentials agreementCredentials;
  protected DHPrivateKeyParameters dhAgreePrivateKey;
  protected DHPublicKeyParameters dhAgreePublicKey;
  
  public TlsDHKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, DHParameters dhParameters)
  {
    super(keyExchange, supportedSignatureAlgorithms);
    
    switch (keyExchange)
    {
    case 7: 
    case 9: 
    case 11: 
      tlsSigner = null;
      break;
    case 5: 
      tlsSigner = new TlsRSASigner();
      break;
    case 3: 
      tlsSigner = new TlsDSSSigner();
      break;
    case 4: case 6: case 8: case 10: default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    }
    
    this.dhParameters = dhParameters;
  }
  
  public void init(TlsContext context)
  {
    super.init(context);
    
    if (tlsSigner != null)
    {
      tlsSigner.init(context);
    }
  }
  
  public void skipServerCredentials()
    throws IOException
  {
    if (keyExchange != 11)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void processServerCertificate(Certificate serverCertificate)
    throws IOException
  {
    if (keyExchange == 11)
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
        dhAgreePublicKey = TlsDHUtils.validateDHPublicKey((DHPublicKeyParameters)serverPublicKey);
        dhParameters = validateDHParameters(dhAgreePublicKey.getParameters());
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
    case 3: 
    case 5: 
    case 11: 
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
    dhAgreePrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), dhParameters, buf);
    
    return buf.toByteArray();
  }
  
  public void processServerKeyExchange(InputStream input) throws IOException
  {
    if (!requiresServerKeyExchange())
    {
      throw new TlsFatalAlert((short)10);
    }
    


    ServerDHParams dhParams = ServerDHParams.parse(input);
    
    dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(dhParams.getPublicKey());
    dhParameters = validateDHParameters(dhAgreePublicKey.getParameters());
  }
  
  public void validateCertificateRequest(CertificateRequest certificateRequest)
    throws IOException
  {
    if (keyExchange == 11)
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
      case 3: 
      case 4: 
      case 64: 
        break;
      default: 
        throw new TlsFatalAlert((short)47);
      }
    }
  }
  
  public void processClientCredentials(TlsCredentials clientCredentials)
    throws IOException
  {
    if (keyExchange == 11)
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
  





  public void generateClientKeyExchange(OutputStream output)
    throws IOException
  {
    if (agreementCredentials == null)
    {
      dhAgreePrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(context.getSecureRandom(), dhParameters, output);
    }
  }
  
  public void processClientCertificate(Certificate clientCertificate)
    throws IOException
  {
    if (keyExchange == 11)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  


  public void processClientKeyExchange(InputStream input)
    throws IOException
  {
    if (dhAgreePublicKey != null)
    {

      return;
    }
    
    BigInteger Yc = TlsDHUtils.readDHParameter(input);
    
    dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(new DHPublicKeyParameters(Yc, dhParameters));
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    if (agreementCredentials != null)
    {
      return agreementCredentials.generateAgreement(dhAgreePublicKey);
    }
    
    if (dhAgreePrivateKey != null)
    {
      return TlsDHUtils.calculateDHBasicAgreement(dhAgreePublicKey, dhAgreePrivateKey);
    }
    
    throw new TlsFatalAlert((short)80);
  }
  
  protected int getMinimumPrimeBits()
  {
    return 1024;
  }
  
  protected DHParameters validateDHParameters(DHParameters params) throws IOException
  {
    if (params.getP().bitLength() < getMinimumPrimeBits())
    {
      throw new TlsFatalAlert((short)71);
    }
    
    return TlsDHUtils.validateDHParameters(params);
  }
}
