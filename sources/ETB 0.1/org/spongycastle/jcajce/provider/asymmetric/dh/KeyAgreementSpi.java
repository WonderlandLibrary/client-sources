package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;








public class KeyAgreementSpi
  extends BaseAgreementSpi
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  
  private BigInteger x;
  
  private BigInteger p;
  private BigInteger g;
  private BigInteger result;
  
  public KeyAgreementSpi()
  {
    super("Diffie-Hellman", null);
  }
  


  public KeyAgreementSpi(String kaAlgorithm, DerivationFunction kdf)
  {
    super(kaAlgorithm, kdf);
  }
  





  protected byte[] bigIntToBytes(BigInteger r)
  {
    int expectedLength = (p.bitLength() + 7) / 8;
    
    byte[] tmp = r.toByteArray();
    
    if (tmp.length == expectedLength)
    {
      return tmp;
    }
    
    if ((tmp[0] == 0) && (tmp.length == expectedLength + 1))
    {
      byte[] rv = new byte[tmp.length - 1];
      
      System.arraycopy(tmp, 1, rv, 0, rv.length);
      return rv;
    }
    


    byte[] rv = new byte[expectedLength];
    
    System.arraycopy(tmp, 0, rv, rv.length - tmp.length, tmp.length);
    
    return rv;
  }
  


  protected Key engineDoPhase(Key key, boolean lastPhase)
    throws InvalidKeyException, IllegalStateException
  {
    if (x == null)
    {
      throw new IllegalStateException("Diffie-Hellman not initialised.");
    }
    
    if (!(key instanceof DHPublicKey))
    {
      throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
    }
    DHPublicKey pubKey = (DHPublicKey)key;
    
    if ((!pubKey.getParams().getG().equals(g)) || (!pubKey.getParams().getP().equals(p)))
    {
      throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
    }
    
    BigInteger peerY = ((DHPublicKey)key).getY();
    if ((peerY == null) || (peerY.compareTo(TWO) < 0) || 
      (peerY.compareTo(p.subtract(ONE)) >= 0))
    {
      throw new InvalidKeyException("Invalid DH PublicKey");
    }
    
    result = peerY.modPow(x, p);
    if (result.compareTo(ONE) == 0)
    {
      throw new InvalidKeyException("Shared key can't be 1");
    }
    
    if (lastPhase)
    {
      return null;
    }
    
    return new BCDHPublicKey(result, pubKey.getParams());
  }
  
  protected byte[] engineGenerateSecret()
    throws IllegalStateException
  {
    if (x == null)
    {
      throw new IllegalStateException("Diffie-Hellman not initialised.");
    }
    
    return super.engineGenerateSecret();
  }
  


  protected int engineGenerateSecret(byte[] sharedSecret, int offset)
    throws IllegalStateException, ShortBufferException
  {
    if (x == null)
    {
      throw new IllegalStateException("Diffie-Hellman not initialised.");
    }
    
    return super.engineGenerateSecret(sharedSecret, offset);
  }
  

  protected SecretKey engineGenerateSecret(String algorithm)
    throws NoSuchAlgorithmException
  {
    if (x == null)
    {
      throw new IllegalStateException("Diffie-Hellman not initialised.");
    }
    
    byte[] res = bigIntToBytes(result);
    

    if (algorithm.equals("TlsPremasterSecret"))
    {
      return new SecretKeySpec(trimZeroes(res), algorithm);
    }
    
    return super.engineGenerateSecret(algorithm);
  }
  



  protected void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if (!(key instanceof DHPrivateKey))
    {
      throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
    }
    DHPrivateKey privKey = (DHPrivateKey)key;
    
    if (params != null)
    {
      if ((params instanceof DHParameterSpec))
      {
        DHParameterSpec p = (DHParameterSpec)params;
        
        this.p = p.getP();
        g = p.getG();
      }
      else if ((params instanceof UserKeyingMaterialSpec))
      {
        this.p = privKey.getParams().getP();
        g = privKey.getParams().getG();
        ukmParameters = ((UserKeyingMaterialSpec)params).getUserKeyingMaterial();
      }
      else
      {
        throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
      }
    }
    else
    {
      this.p = privKey.getParams().getP();
      g = privKey.getParams().getG();
    }
    
    x = (this.result = privKey.getX());
  }
  


  protected void engineInit(Key key, SecureRandom random)
    throws InvalidKeyException
  {
    if (!(key instanceof DHPrivateKey))
    {
      throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
    }
    
    DHPrivateKey privKey = (DHPrivateKey)key;
    
    p = privKey.getParams().getP();
    g = privKey.getParams().getG();
    x = (this.result = privKey.getX());
  }
  
  protected byte[] calcSecret()
  {
    return bigIntToBytes(result);
  }
  
  public static class DHwithRFC2631KDF
    extends KeyAgreementSpi
  {
    public DHwithRFC2631KDF()
    {
      super(new DHKEKGenerator(DigestFactory.createSHA1()));
    }
  }
}
