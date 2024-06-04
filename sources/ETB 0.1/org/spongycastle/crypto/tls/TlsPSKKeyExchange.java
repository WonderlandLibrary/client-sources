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
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;







public class TlsPSKKeyExchange
  extends AbstractTlsKeyExchange
{
  protected TlsPSKIdentity pskIdentity;
  protected TlsPSKIdentityManager pskIdentityManager;
  protected DHParameters dhParameters;
  protected int[] namedCurves;
  protected short[] clientECPointFormats;
  protected short[] serverECPointFormats;
  protected byte[] psk_identity_hint = null;
  protected byte[] psk = null;
  
  protected DHPrivateKeyParameters dhAgreePrivateKey = null;
  protected DHPublicKeyParameters dhAgreePublicKey = null;
  
  protected ECPrivateKeyParameters ecAgreePrivateKey = null;
  protected ECPublicKeyParameters ecAgreePublicKey = null;
  
  protected AsymmetricKeyParameter serverPublicKey = null;
  protected RSAKeyParameters rsaServerPublicKey = null;
  protected TlsEncryptionCredentials serverCredentials = null;
  
  protected byte[] premasterSecret;
  

  public TlsPSKKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, TlsPSKIdentity pskIdentity, TlsPSKIdentityManager pskIdentityManager, DHParameters dhParameters, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats)
  {
    super(keyExchange, supportedSignatureAlgorithms);
    
    switch (keyExchange)
    {
    case 13: 
    case 14: 
    case 15: 
    case 24: 
      break;
    default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    }
    
    this.pskIdentity = pskIdentity;
    this.pskIdentityManager = pskIdentityManager;
    this.dhParameters = dhParameters;
    this.namedCurves = namedCurves;
    this.clientECPointFormats = clientECPointFormats;
    this.serverECPointFormats = serverECPointFormats;
  }
  
  public void skipServerCredentials() throws IOException
  {
    if (keyExchange == 15)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void processServerCredentials(TlsCredentials serverCredentials) throws IOException
  {
    if (!(serverCredentials instanceof TlsEncryptionCredentials))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    processServerCertificate(serverCredentials.getCertificate());
    
    this.serverCredentials = ((TlsEncryptionCredentials)serverCredentials);
  }
  
  public byte[] generateServerKeyExchange() throws IOException
  {
    psk_identity_hint = pskIdentityManager.getHint();
    
    if ((psk_identity_hint == null) && (!requiresServerKeyExchange()))
    {
      return null;
    }
    
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    
    if (psk_identity_hint == null)
    {
      TlsUtils.writeOpaque16(TlsUtils.EMPTY_BYTES, buf);
    }
    else
    {
      TlsUtils.writeOpaque16(psk_identity_hint, buf);
    }
    
    if (keyExchange == 14)
    {
      if (dhParameters == null)
      {
        throw new TlsFatalAlert((short)80);
      }
      
      dhAgreePrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), dhParameters, buf);

    }
    else if (keyExchange == 24)
    {
      ecAgreePrivateKey = TlsECCUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), namedCurves, clientECPointFormats, buf);
    }
    

    return buf.toByteArray();
  }
  
  public void processServerCertificate(Certificate serverCertificate) throws IOException
  {
    if (keyExchange != 15)
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
    

    if (serverPublicKey.isPrivate())
    {
      throw new TlsFatalAlert((short)80);
    }
    
    rsaServerPublicKey = validateRSAPublicKey((RSAKeyParameters)serverPublicKey);
    
    TlsUtils.validateKeyUsage(x509Cert, 32);
    
    super.processServerCertificate(serverCertificate);
  }
  
  public boolean requiresServerKeyExchange()
  {
    switch (keyExchange)
    {
    case 14: 
    case 24: 
      return true;
    }
    return false;
  }
  
  public void processServerKeyExchange(InputStream input)
    throws IOException
  {
    psk_identity_hint = TlsUtils.readOpaque16(input);
    
    if (keyExchange == 14)
    {
      ServerDHParams serverDHParams = ServerDHParams.parse(input);
      
      dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(serverDHParams.getPublicKey());
      dhParameters = dhAgreePublicKey.getParameters();
    }
    else if (keyExchange == 24)
    {
      ECDomainParameters ecParams = TlsECCUtils.readECParameters(namedCurves, clientECPointFormats, input);
      
      byte[] point = TlsUtils.readOpaque8(input);
      
      ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(clientECPointFormats, ecParams, point));
    }
  }
  
  public void validateCertificateRequest(CertificateRequest certificateRequest)
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void processClientCredentials(TlsCredentials clientCredentials) throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  public void generateClientKeyExchange(OutputStream output) throws IOException
  {
    if (psk_identity_hint == null)
    {
      pskIdentity.skipIdentityHint();
    }
    else
    {
      pskIdentity.notifyIdentityHint(psk_identity_hint);
    }
    
    byte[] psk_identity = pskIdentity.getPSKIdentity();
    if (psk_identity == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    psk = pskIdentity.getPSK();
    if (psk == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    TlsUtils.writeOpaque16(psk_identity, output);
    
    context.getSecurityParameters().pskIdentity = Arrays.clone(psk_identity);
    
    if (keyExchange == 14)
    {
      dhAgreePrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(context.getSecureRandom(), dhParameters, output);

    }
    else if (keyExchange == 24)
    {
      ecAgreePrivateKey = TlsECCUtils.generateEphemeralClientKeyExchange(context.getSecureRandom(), serverECPointFormats, ecAgreePublicKey
        .getParameters(), output);
    }
    else if (keyExchange == 15)
    {
      premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(context, rsaServerPublicKey, output);
    }
  }
  
  public void processClientKeyExchange(InputStream input)
    throws IOException
  {
    byte[] psk_identity = TlsUtils.readOpaque16(input);
    
    psk = pskIdentityManager.getPSK(psk_identity);
    if (psk == null)
    {
      throw new TlsFatalAlert((short)115);
    }
    
    context.getSecurityParameters().pskIdentity = psk_identity;
    
    if (keyExchange == 14)
    {
      BigInteger Yc = TlsDHUtils.readDHParameter(input);
      
      dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(new DHPublicKeyParameters(Yc, dhParameters));
    }
    else if (keyExchange == 24)
    {
      byte[] point = TlsUtils.readOpaque8(input);
      
      ECDomainParameters curve_params = ecAgreePrivateKey.getParameters();
      
      ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(serverECPointFormats, curve_params, point));

    }
    else if (keyExchange == 15) {
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
  }
  
  public byte[] generatePremasterSecret() throws IOException
  {
    byte[] other_secret = generateOtherSecret(psk.length);
    
    ByteArrayOutputStream buf = new ByteArrayOutputStream(4 + other_secret.length + psk.length);
    TlsUtils.writeOpaque16(other_secret, buf);
    TlsUtils.writeOpaque16(psk, buf);
    
    Arrays.fill(psk, (byte)0);
    psk = null;
    
    return buf.toByteArray();
  }
  
  protected byte[] generateOtherSecret(int pskLength) throws IOException
  {
    if (keyExchange == 14)
    {
      if (dhAgreePrivateKey != null)
      {
        return TlsDHUtils.calculateDHBasicAgreement(dhAgreePublicKey, dhAgreePrivateKey);
      }
      
      throw new TlsFatalAlert((short)80);
    }
    
    if (keyExchange == 24)
    {
      if (ecAgreePrivateKey != null)
      {
        return TlsECCUtils.calculateECDHBasicAgreement(ecAgreePublicKey, ecAgreePrivateKey);
      }
      
      throw new TlsFatalAlert((short)80);
    }
    
    if (keyExchange == 15)
    {
      return premasterSecret;
    }
    
    return new byte[pskLength];
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
