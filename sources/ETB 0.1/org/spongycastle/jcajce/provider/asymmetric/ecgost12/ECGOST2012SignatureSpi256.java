package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_256Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECGOST3410_2012Signer;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;




public class ECGOST2012SignatureSpi256
  extends SignatureSpi
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers
{
  private Digest digest;
  private DSA signer;
  private int size = 64;
  private int halfSize = size / 2;
  
  public ECGOST2012SignatureSpi256()
  {
    digest = new GOST3411_2012_256Digest();
    signer = new ECGOST3410_2012Signer();
  }
  

  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    CipherParameters param;
    
    if ((publicKey instanceof ECPublicKey))
    {
      param = generatePublicKeyParameter(publicKey);
    }
    else
    {
      try
      {
        byte[] bytes = publicKey.getEncoded();
        
        publicKey = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(bytes));
        
        param = ECUtil.generatePublicKeyParameter(publicKey);
      }
      catch (Exception e) {
        CipherParameters param;
        throw new InvalidKeyException("cannot recognise key type in ECGOST-2012-256 signer");
      }
    }
    CipherParameters param;
    digest.reset();
    signer.init(false, param);
  }
  

  protected void engineInitSign(PrivateKey privateKey)
    throws InvalidKeyException
  {
    CipherParameters param;
    
    if ((privateKey instanceof ECKey))
    {
      param = ECUtil.generatePrivateKeyParameter(privateKey);
    }
    else
    {
      throw new InvalidKeyException("cannot recognise key type in ECGOST-2012-256 signer");
    }
    CipherParameters param;
    digest.reset();
    
    if (appRandom != null)
    {
      signer.init(true, new ParametersWithRandom(param, appRandom));
    }
    else
    {
      signer.init(true, param);
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
      byte[] sigBytes = new byte[size];
      BigInteger[] sig = signer.generateSignature(hash);
      byte[] r = sig[0].toByteArray();
      byte[] s = sig[1].toByteArray();
      
      if (s[0] != 0)
      {
        System.arraycopy(s, 0, sigBytes, halfSize - s.length, s.length);
      }
      else
      {
        System.arraycopy(s, 1, sigBytes, halfSize - (s.length - 1), s.length - 1);
      }
      
      if (r[0] != 0)
      {
        System.arraycopy(r, 0, sigBytes, size - r.length, r.length);
      }
      else
      {
        System.arraycopy(r, 1, sigBytes, size - (r.length - 1), r.length - 1);
      }
      
      return sigBytes;
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
      byte[] r = new byte[halfSize];
      byte[] s = new byte[halfSize];
      
      System.arraycopy(sigBytes, 0, s, 0, halfSize);
      
      System.arraycopy(sigBytes, halfSize, r, 0, halfSize);
      
      BigInteger[] sig = new BigInteger[2];
      sig[0] = new BigInteger(1, r);
      sig[1] = new BigInteger(1, s);
    }
    catch (Exception e)
    {
      throw new SignatureException("error decoding signature bytes.");
    }
    BigInteger[] sig;
    return signer.verifySignature(hash, sig[0], sig[1]);
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
  

  static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey key)
    throws InvalidKeyException
  {
    return (key instanceof BCECGOST3410_2012PublicKey) ? ((BCECGOST3410_2012PublicKey)key).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter(key);
  }
}
