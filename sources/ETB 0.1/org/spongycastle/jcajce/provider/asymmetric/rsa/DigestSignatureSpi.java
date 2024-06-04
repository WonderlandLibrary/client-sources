package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.RIPEMD256Digest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;






public class DigestSignatureSpi
  extends SignatureSpi
{
  private Digest digest;
  private AsymmetricBlockCipher cipher;
  private AlgorithmIdentifier algId;
  
  protected DigestSignatureSpi(Digest digest, AsymmetricBlockCipher cipher)
  {
    this.digest = digest;
    this.cipher = cipher;
    algId = null;
  }
  




  protected DigestSignatureSpi(ASN1ObjectIdentifier objId, Digest digest, AsymmetricBlockCipher cipher)
  {
    this.digest = digest;
    this.cipher = cipher;
    algId = new AlgorithmIdentifier(objId, DERNull.INSTANCE);
  }
  

  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    if (!(publicKey instanceof RSAPublicKey))
    {
      throw new InvalidKeyException("Supplied key (" + getType(publicKey) + ") is not a RSAPublicKey instance");
    }
    
    CipherParameters param = RSAUtil.generatePublicKeyParameter((RSAPublicKey)publicKey);
    
    digest.reset();
    cipher.init(false, param);
  }
  

  protected void engineInitSign(PrivateKey privateKey)
    throws InvalidKeyException
  {
    if (!(privateKey instanceof RSAPrivateKey))
    {
      throw new InvalidKeyException("Supplied key (" + getType(privateKey) + ") is not a RSAPrivateKey instance");
    }
    
    CipherParameters param = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)privateKey);
    
    digest.reset();
    
    cipher.init(true, param);
  }
  

  private String getType(Object o)
  {
    if (o == null)
    {
      return null;
    }
    
    return o.getClass().getName();
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
      byte[] bytes = derEncode(hash);
      
      return cipher.processBlock(bytes, 0, bytes.length);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new SignatureException("key too small for signature type");
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
    



    try
    {
      byte[] sig = cipher.processBlock(sigBytes, 0, sigBytes.length);
      
      expected = derEncode(hash);
    }
    catch (Exception e) {
      byte[] expected;
      return false; }
    byte[] expected;
    byte[] sig;
    if (sig.length == expected.length)
    {
      return Arrays.constantTimeAreEqual(sig, expected);
    }
    if (sig.length == expected.length - 2)
    {
      int tmp80_79 = 1; byte[] tmp80_77 = expected;tmp80_77[tmp80_79] = ((byte)(tmp80_77[tmp80_79] - 2)); int 
        tmp89_88 = 3; byte[] tmp89_86 = expected;tmp89_86[tmp89_88] = ((byte)(tmp89_86[tmp89_88] - 2));
      
      int sigOffset = 4 + expected[3];
      int expectedOffset = sigOffset + 2;
      int nonEqual = 0;
      
      for (int i = 0; i < expected.length - expectedOffset; i++)
      {
        nonEqual |= sig[(sigOffset + i)] ^ expected[(expectedOffset + i)];
      }
      
      for (int i = 0; i < sigOffset; i++)
      {
        nonEqual |= sig[i] ^ expected[i];
      }
      
      return nonEqual == 0;
    }
    

    Arrays.constantTimeAreEqual(expected, expected);
    
    return false;
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
    return null;
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    return null;
  }
  

  private byte[] derEncode(byte[] hash)
    throws IOException
  {
    if (algId == null)
    {

      return hash;
    }
    
    DigestInfo dInfo = new DigestInfo(algId, hash);
    
    return dInfo.getEncoded("DER");
  }
  
  public static class SHA1
    extends DigestSignatureSpi
  {
    public SHA1()
    {
      super(DigestFactory.createSHA1(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA224
    extends DigestSignatureSpi
  {
    public SHA224()
    {
      super(DigestFactory.createSHA224(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA256
    extends DigestSignatureSpi
  {
    public SHA256()
    {
      super(DigestFactory.createSHA256(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA384
    extends DigestSignatureSpi
  {
    public SHA384()
    {
      super(DigestFactory.createSHA384(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA512
    extends DigestSignatureSpi
  {
    public SHA512()
    {
      super(DigestFactory.createSHA512(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA512_224
    extends DigestSignatureSpi
  {
    public SHA512_224()
    {
      super(DigestFactory.createSHA512_224(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA512_256
    extends DigestSignatureSpi
  {
    public SHA512_256()
    {
      super(DigestFactory.createSHA512_256(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA3_224
    extends DigestSignatureSpi
  {
    public SHA3_224()
    {
      super(DigestFactory.createSHA3_224(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA3_256
    extends DigestSignatureSpi
  {
    public SHA3_256()
    {
      super(DigestFactory.createSHA3_256(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA3_384
    extends DigestSignatureSpi
  {
    public SHA3_384()
    {
      super(DigestFactory.createSHA3_384(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA3_512
    extends DigestSignatureSpi
  {
    public SHA3_512()
    {
      super(DigestFactory.createSHA3_512(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class MD2
    extends DigestSignatureSpi
  {
    public MD2()
    {
      super(new MD2Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class MD4
    extends DigestSignatureSpi
  {
    public MD4()
    {
      super(new MD4Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class MD5
    extends DigestSignatureSpi
  {
    public MD5()
    {
      super(DigestFactory.createMD5(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD160
    extends DigestSignatureSpi
  {
    public RIPEMD160()
    {
      super(new RIPEMD160Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD128
    extends DigestSignatureSpi
  {
    public RIPEMD128()
    {
      super(new RIPEMD128Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD256
    extends DigestSignatureSpi
  {
    public RIPEMD256()
    {
      super(new RIPEMD256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class noneRSA
    extends DigestSignatureSpi
  {
    public noneRSA()
    {
      super(new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
}
