package org.spongycastle.pqc.jcajce.provider.xmss;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHAKEDigest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSSigner;

public class XMSSSignatureSpi extends Signature implements org.spongycastle.pqc.jcajce.interfaces.StateAwareSignature
{
  private Digest digest;
  private XMSSSigner signer;
  private SecureRandom random;
  private ASN1ObjectIdentifier treeDigest;
  
  protected XMSSSignatureSpi(String algorithm)
  {
    super(algorithm);
  }
  





  protected XMSSSignatureSpi(String sigName, Digest digest, XMSSSigner signer)
  {
    super(sigName);
    
    this.digest = digest;
    this.signer = signer;
  }
  
  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    if ((publicKey instanceof BCXMSSPublicKey))
    {
      CipherParameters param = ((BCXMSSPublicKey)publicKey).getKeyParams();
      
      treeDigest = null;
      digest.reset();
      signer.init(false, param);
    }
    else
    {
      throw new InvalidKeyException("unknown public key passed to XMSS");
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
    if ((privateKey instanceof BCXMSSPrivateKey))
    {
      CipherParameters param = ((BCXMSSPrivateKey)privateKey).getKeyParams();
      
      treeDigest = ((BCXMSSPrivateKey)privateKey).getTreeDigestOID();
      if (random != null)
      {
        param = new ParametersWithRandom(param, random);
      }
      
      digest.reset();
      signer.init(true, param);
    }
    else
    {
      throw new InvalidKeyException("unknown private key passed to XMSS");
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
    byte[] hash = DigestUtil.getDigestResult(digest);
    
    try
    {
      return signer.generateSignature(hash);

    }
    catch (Exception e)
    {

      if ((e instanceof IllegalStateException))
      {
        throw new SignatureException(e.getMessage());
      }
      throw new SignatureException(e.toString());
    }
  }
  
  protected boolean engineVerify(byte[] sigBytes)
    throws SignatureException
  {
    byte[] hash = DigestUtil.getDigestResult(digest);
    
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
  
  public PrivateKey getUpdatedPrivateKey()
  {
    if (treeDigest == null)
    {
      throw new IllegalStateException("signature object not in a signing state");
    }
    PrivateKey rKey = new BCXMSSPrivateKey(treeDigest, (XMSSPrivateKeyParameters)signer.getUpdatedPrivateKey());
    
    treeDigest = null;
    
    return rKey;
  }
  
  public static class withSha256
    extends XMSSSignatureSpi
  {
    public withSha256()
    {
      super(new SHA256Digest(), new XMSSSigner());
    }
  }
  
  public static class withShake128
    extends XMSSSignatureSpi
  {
    public withShake128()
    {
      super(new SHAKEDigest(128), new XMSSSigner());
    }
  }
  
  public static class withSha512
    extends XMSSSignatureSpi
  {
    public withSha512()
    {
      super(new org.spongycastle.crypto.digests.SHA512Digest(), new XMSSSigner());
    }
  }
  
  public static class withShake256
    extends XMSSSignatureSpi
  {
    public withShake256()
    {
      super(new SHAKEDigest(256), new XMSSSigner());
    }
  }
}
