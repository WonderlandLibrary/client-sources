package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256Signer;


public class SignatureSpi
  extends java.security.SignatureSpi
{
  private Digest digest;
  private SPHINCS256Signer signer;
  private SecureRandom random;
  
  protected SignatureSpi(Digest digest, SPHINCS256Signer signer)
  {
    this.digest = digest;
    this.signer = signer;
  }
  
  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    if ((publicKey instanceof BCSphincs256PublicKey))
    {
      CipherParameters param = ((BCSphincs256PublicKey)publicKey).getKeyParams();
      
      digest.reset();
      signer.init(false, param);
    }
    else
    {
      throw new InvalidKeyException("unknown public key passed to SPHINCS-256");
    }
  }
  
  protected void engineInitSign(PrivateKey privateKey, SecureRandom random)
    throws InvalidKeyException
  {
    this.random = random;
    engineInitSign(privateKey);
  }
  
  protected void engineInitSign(PrivateKey privateKey)
    throws InvalidKeyException
  {
    if ((privateKey instanceof BCSphincs256PrivateKey))
    {
      CipherParameters param = ((BCSphincs256PrivateKey)privateKey).getKeyParams();
      
      if (random != null)
      {
        param = new ParametersWithRandom(param, random);
      }
      
      digest.reset();
      signer.init(true, param);
    }
    else
    {
      throw new InvalidKeyException("unknown private key passed to SPHINCS-256");
    }
  }
  
  protected void engineUpdate(byte b)
    throws SignatureException
  {
    digest.update(b);
  }
  
  protected void engineUpdate(byte[] b, int off, int len)
    throws SignatureException
  {
    digest.update(b, off, len);
  }
  
  protected byte[] engineSign()
    throws SignatureException
  {
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    try
    {
      return signer.generateSignature(hash);

    }
    catch (Exception e)
    {

      throw new SignatureException(e.toString());
    }
  }
  
  protected boolean engineVerify(byte[] sigBytes)
    throws SignatureException
  {
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    return signer.verifySignature(hash, sigBytes);
  }
  

  protected void engineSetParameter(AlgorithmParameterSpec params)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  


  /**
   * @deprecated
   */
  protected void engineSetParameter(String param, Object value)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  /**
   * @deprecated
   */
  protected Object engineGetParameter(String param)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  public static class withSha512
    extends SignatureSpi
  {
    public withSha512()
    {
      super(new SPHINCS256Signer(new SHA512tDigest(256), new SHA512Digest()));
    }
  }
  
  public static class withSha3_512
    extends SignatureSpi
  {
    public withSha3_512()
    {
      super(new SPHINCS256Signer(new SHA3Digest(256), new SHA3Digest(512)));
    }
  }
}
