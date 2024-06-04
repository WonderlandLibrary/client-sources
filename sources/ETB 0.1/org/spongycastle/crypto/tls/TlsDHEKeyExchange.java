package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.util.io.TeeInputStream;

public class TlsDHEKeyExchange
  extends TlsDHKeyExchange
{
  protected TlsSignerCredentials serverCredentials = null;
  
  public TlsDHEKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, DHParameters dhParameters)
  {
    super(keyExchange, supportedSignatureAlgorithms, dhParameters);
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
    if (dhParameters == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    DigestInputBuffer buf = new DigestInputBuffer();
    
    dhAgreePrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(context.getSecureRandom(), dhParameters, buf);
    




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
    
    ServerDHParams dhParams = ServerDHParams.parse(teeIn);
    
    DigitallySigned signed_params = parseSignature(input);
    
    Signer signer = initVerifyer(tlsSigner, signed_params.getAlgorithm(), securityParameters);
    buf.updateSigner(signer);
    if (!signer.verifySignature(signed_params.getSignature()))
    {
      throw new TlsFatalAlert((short)51);
    }
    
    dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(dhParams.getPublicKey());
    dhParameters = validateDHParameters(dhAgreePublicKey.getParameters());
  }
  
  protected Signer initVerifyer(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters)
  {
    Signer signer = tlsSigner.createVerifyer(algorithm, serverPublicKey);
    signer.update(clientRandom, 0, clientRandom.length);
    signer.update(serverRandom, 0, serverRandom.length);
    return signer;
  }
}
