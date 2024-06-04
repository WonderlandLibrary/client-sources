package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.io.Streams;





public class TlsRSAKeyExchange
  extends AbstractTlsKeyExchange
{
  protected AsymmetricKeyParameter serverPublicKey = null;
  
  protected RSAKeyParameters rsaServerPublicKey = null;
  
  protected TlsEncryptionCredentials serverCredentials = null;
  
  protected byte[] premasterSecret;
  
  public TlsRSAKeyExchange(Vector supportedSignatureAlgorithms)
  {
    super(1, supportedSignatureAlgorithms);
  }
  
  public void skipServerCredentials()
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void processServerCredentials(TlsCredentials serverCredentials)
    throws IOException
  {
    if (!(serverCredentials instanceof TlsEncryptionCredentials))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    processServerCertificate(serverCredentials.getCertificate());
    
    this.serverCredentials = ((TlsEncryptionCredentials)serverCredentials);
  }
  
  public void processServerCertificate(Certificate serverCertificate)
    throws IOException
  {
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
    

    if (serverPublicKey.isPrivate())
    {
      throw new TlsFatalAlert((short)80);
    }
    
    rsaServerPublicKey = validateRSAPublicKey((RSAKeyParameters)serverPublicKey);
    
    TlsUtils.validateKeyUsage(x509Cert, 32);
    
    super.processServerCertificate(serverCertificate);
  }
  
  public void validateCertificateRequest(CertificateRequest certificateRequest)
    throws IOException
  {
    short[] types = certificateRequest.getCertificateTypes();
    for (int i = 0; i < types.length; i++)
    {
      switch (types[i])
      {
      case 1: 
      case 2: 
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
    if (!(clientCredentials instanceof TlsSignerCredentials))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public void generateClientKeyExchange(OutputStream output)
    throws IOException
  {
    premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(context, rsaServerPublicKey, output);
  }
  
  public void processClientKeyExchange(InputStream input) throws IOException
  {
    byte[] encryptedPreMasterSecret;
    byte[] encryptedPreMasterSecret;
    if (TlsUtils.isSSL(context))
    {

      encryptedPreMasterSecret = Streams.readAll(input);
    }
    else
    {
      encryptedPreMasterSecret = TlsUtils.readOpaque16(input);
    }
    
    premasterSecret = serverCredentials.decryptPreMasterSecret(encryptedPreMasterSecret);
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    if (premasterSecret == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    byte[] tmp = premasterSecret;
    premasterSecret = null;
    return tmp;
  }
  
































  protected RSAKeyParameters validateRSAPublicKey(RSAKeyParameters key)
    throws IOException
  {
    if (!key.getExponent().isProbablePrime(2))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    return key;
  }
}
