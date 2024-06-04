package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.util.io.TeeInputStream;





public class TlsECDHEKeyExchange
  extends TlsECDHKeyExchange
{
  protected TlsSignerCredentials serverCredentials = null;
  

  public TlsECDHEKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats)
  {
    super(keyExchange, supportedSignatureAlgorithms, namedCurves, clientECPointFormats, serverECPointFormats);
  }
  
  public void processServerCredentials(TlsCredentials serverCredentials)
    throws IOException
  {
    if (!(serverCredentials instanceof TlsSignerCredentials))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    processServerCertificate(serverCredentials.getCertificate());
    
    this.serverCredentials = ((TlsSignerCredentials)serverCredentials);
  }
  
  public byte[] generateServerKeyExchange()
    throws IOException
  {
    DigestInputBuffer buf = new DigestInputBuffer();
    
    ecAgreePrivateKey = TlsECCUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), namedCurves, clientECPointFormats, buf);
    




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
    
    return buf.toByteArray();
  }
  
  public void processServerKeyExchange(InputStream input)
    throws IOException
  {
    SecurityParameters securityParameters = context.getSecurityParameters();
    
    SignerInputBuffer buf = new SignerInputBuffer();
    InputStream teeIn = new TeeInputStream(input, buf);
    
    ECDomainParameters curve_params = TlsECCUtils.readECParameters(namedCurves, clientECPointFormats, teeIn);
    
    byte[] point = TlsUtils.readOpaque8(teeIn);
    
    DigitallySigned signed_params = parseSignature(input);
    
    Signer signer = initVerifyer(tlsSigner, signed_params.getAlgorithm(), securityParameters);
    buf.updateSigner(signer);
    if (!signer.verifySignature(signed_params.getSignature()))
    {
      throw new TlsFatalAlert((short)51);
    }
    
    ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(clientECPointFormats, curve_params, point));
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
  
  protected Signer initVerifyer(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters)
  {
    Signer signer = tlsSigner.createVerifyer(algorithm, serverPublicKey);
    signer.update(clientRandom, 0, clientRandom.length);
    signer.update(serverRandom, 0, serverRandom.length);
    return signer;
  }
}
