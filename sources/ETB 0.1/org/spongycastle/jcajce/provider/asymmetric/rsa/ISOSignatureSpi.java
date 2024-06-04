package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.WhirlpoolDigest;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.signers.ISO9796d2Signer;
import org.spongycastle.crypto.util.DigestFactory;




public class ISOSignatureSpi
  extends SignatureSpi
{
  private ISO9796d2Signer signer;
  
  protected ISOSignatureSpi(Digest digest, AsymmetricBlockCipher cipher)
  {
    signer = new ISO9796d2Signer(cipher, digest, true);
  }
  

  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    CipherParameters param = RSAUtil.generatePublicKeyParameter((RSAPublicKey)publicKey);
    
    signer.init(false, param);
  }
  

  protected void engineInitSign(PrivateKey privateKey)
    throws InvalidKeyException
  {
    CipherParameters param = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)privateKey);
    
    signer.init(true, param);
  }
  

  protected void engineUpdate(byte b)
    throws SignatureException
  {
    signer.update(b);
  }
  



  protected void engineUpdate(byte[] b, int off, int len)
    throws SignatureException
  {
    signer.update(b, off, len);
  }
  
  protected byte[] engineSign()
    throws SignatureException
  {
    try
    {
      return signer.generateSignature();

    }
    catch (Exception e)
    {

      throw new SignatureException(e.toString());
    }
  }
  

  protected boolean engineVerify(byte[] sigBytes)
    throws SignatureException
  {
    boolean yes = signer.verifySignature(sigBytes);
    
    return yes;
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
  
  public static class SHA1WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA1WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class MD5WithRSAEncryption
    extends ISOSignatureSpi
  {
    public MD5WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class RIPEMD160WithRSAEncryption
    extends ISOSignatureSpi
  {
    public RIPEMD160WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA224WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA224WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA256WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA256WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA384WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA384WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA512WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA512WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA512_224WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA512_224WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class SHA512_256WithRSAEncryption
    extends ISOSignatureSpi
  {
    public SHA512_256WithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
  
  public static class WhirlpoolWithRSAEncryption
    extends ISOSignatureSpi
  {
    public WhirlpoolWithRSAEncryption()
    {
      super(new RSABlindedEngine());
    }
  }
}
