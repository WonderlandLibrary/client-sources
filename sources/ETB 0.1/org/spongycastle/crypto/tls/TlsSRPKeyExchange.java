package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.agreement.srp.SRP6Client;
import org.spongycastle.crypto.agreement.srp.SRP6Server;
import org.spongycastle.crypto.agreement.srp.SRP6Util;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.SRP6GroupParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.io.TeeInputStream;

public class TlsSRPKeyExchange extends AbstractTlsKeyExchange
{
  protected TlsSigner tlsSigner;
  protected TlsSRPGroupVerifier groupVerifier;
  protected byte[] identity;
  protected byte[] password;
  
  protected static TlsSigner createSigner(int keyExchange)
  {
    switch (keyExchange)
    {
    case 21: 
      return null;
    case 23: 
      return new TlsRSASigner();
    case 22: 
      return new TlsDSSSigner();
    }
    throw new IllegalArgumentException("unsupported key exchange algorithm");
  }
  






  protected AsymmetricKeyParameter serverPublicKey = null;
  
  protected SRP6GroupParameters srpGroup = null;
  protected SRP6Client srpClient = null;
  protected SRP6Server srpServer = null;
  protected BigInteger srpPeerCredentials = null;
  protected BigInteger srpVerifier = null;
  protected byte[] srpSalt = null;
  
  protected TlsSignerCredentials serverCredentials = null;
  
  /**
   * @deprecated
   */
  public TlsSRPKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, byte[] identity, byte[] password)
  {
    this(keyExchange, supportedSignatureAlgorithms, new DefaultTlsSRPGroupVerifier(), identity, password);
  }
  

  public TlsSRPKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, TlsSRPGroupVerifier groupVerifier, byte[] identity, byte[] password)
  {
    super(keyExchange, supportedSignatureAlgorithms);
    
    tlsSigner = createSigner(keyExchange);
    this.groupVerifier = groupVerifier;
    this.identity = identity;
    this.password = password;
    srpClient = new SRP6Client();
  }
  

  public TlsSRPKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, byte[] identity, TlsSRPLoginParameters loginParameters)
  {
    super(keyExchange, supportedSignatureAlgorithms);
    
    tlsSigner = createSigner(keyExchange);
    this.identity = identity;
    srpServer = new SRP6Server();
    srpGroup = loginParameters.getGroup();
    srpVerifier = loginParameters.getVerifier();
    srpSalt = loginParameters.getSalt();
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
    if (tlsSigner != null)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  public void processServerCertificate(Certificate serverCertificate) throws IOException
  {
    if (tlsSigner == null)
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
    
    if (!tlsSigner.isValidPublicKey(serverPublicKey))
    {
      throw new TlsFatalAlert((short)46);
    }
    
    TlsUtils.validateKeyUsage(x509Cert, 128);
    
    super.processServerCertificate(serverCertificate);
  }
  
  public void processServerCredentials(TlsCredentials serverCredentials)
    throws IOException
  {
    if ((keyExchange == 21) || (!(serverCredentials instanceof TlsSignerCredentials)))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    processServerCertificate(serverCredentials.getCertificate());
    
    this.serverCredentials = ((TlsSignerCredentials)serverCredentials);
  }
  
  public boolean requiresServerKeyExchange()
  {
    return true;
  }
  
  public byte[] generateServerKeyExchange() throws IOException
  {
    srpServer.init(srpGroup, srpVerifier, TlsUtils.createHash((short)2), context.getSecureRandom());
    BigInteger B = srpServer.generateServerCredentials();
    
    ServerSRPParams srpParams = new ServerSRPParams(srpGroup.getN(), srpGroup.getG(), srpSalt, B);
    
    DigestInputBuffer buf = new DigestInputBuffer();
    
    srpParams.encode(buf);
    
    if (serverCredentials != null)
    {



      SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.getSignatureAndHashAlgorithm(context, serverCredentials);
      

      Digest d = TlsUtils.createHash(signatureAndHashAlgorithm);
      
      SecurityParameters securityParameters = context.getSecurityParameters();
      d.update(clientRandom, 0, clientRandom.length);
      d.update(serverRandom, 0, serverRandom.length);
      buf.updateDigest(d);
      
      byte[] hash = new byte[d.getDigestSize()];
      d.doFinal(hash, 0);
      
      byte[] signature = serverCredentials.generateCertificateSignature(hash);
      
      DigitallySigned signed_params = new DigitallySigned(signatureAndHashAlgorithm, signature);
      signed_params.encode(buf);
    }
    
    return buf.toByteArray();
  }
  
  public void processServerKeyExchange(InputStream input) throws IOException
  {
    SecurityParameters securityParameters = context.getSecurityParameters();
    
    SignerInputBuffer buf = null;
    InputStream teeIn = input;
    
    if (tlsSigner != null)
    {
      buf = new SignerInputBuffer();
      teeIn = new TeeInputStream(input, buf);
    }
    
    ServerSRPParams srpParams = ServerSRPParams.parse(teeIn);
    
    if (buf != null)
    {
      DigitallySigned signed_params = parseSignature(input);
      
      Signer signer = initVerifyer(tlsSigner, signed_params.getAlgorithm(), securityParameters);
      buf.updateSigner(signer);
      if (!signer.verifySignature(signed_params.getSignature()))
      {
        throw new TlsFatalAlert((short)51);
      }
    }
    
    srpGroup = new SRP6GroupParameters(srpParams.getN(), srpParams.getG());
    
    if (!groupVerifier.accept(srpGroup))
    {
      throw new TlsFatalAlert((short)71);
    }
    
    srpSalt = srpParams.getS();
    




    try
    {
      srpPeerCredentials = SRP6Util.validatePublicValue(srpGroup.getN(), srpParams.getB());
    }
    catch (CryptoException e)
    {
      throw new TlsFatalAlert((short)47, e);
    }
    
    srpClient.init(srpGroup, TlsUtils.createHash((short)2), context.getSecureRandom());
  }
  
  public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void processClientCredentials(TlsCredentials clientCredentials) throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  public void generateClientKeyExchange(OutputStream output) throws IOException
  {
    BigInteger A = srpClient.generateClientCredentials(srpSalt, identity, password);
    TlsSRPUtils.writeSRPParameter(A, output);
    
    context.getSecurityParameters().srpIdentity = Arrays.clone(identity);
  }
  



  public void processClientKeyExchange(InputStream input)
    throws IOException
  {
    try
    {
      srpPeerCredentials = SRP6Util.validatePublicValue(srpGroup.getN(), TlsSRPUtils.readSRPParameter(input));
    }
    catch (CryptoException e)
    {
      throw new TlsFatalAlert((short)47, e);
    }
    
    context.getSecurityParameters().srpIdentity = Arrays.clone(identity);
  }
  

  public byte[] generatePremasterSecret()
    throws IOException
  {
    try
    {
      BigInteger S = srpServer != null ? srpServer.calculateSecret(srpPeerCredentials) : srpClient.calculateSecret(srpPeerCredentials);
      

      return BigIntegers.asUnsignedByteArray(S);
    }
    catch (CryptoException e)
    {
      throw new TlsFatalAlert((short)47, e);
    }
  }
  
  protected Signer initVerifyer(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters)
  {
    Signer signer = tlsSigner.createVerifyer(algorithm, serverPublicKey);
    signer.update(clientRandom, 0, clientRandom.length);
    signer.update(serverRandom, 0, serverRandom.length);
    return signer;
  }
}
