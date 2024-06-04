package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;





public class X931Signer
  implements Signer
{
  /**
   * @deprecated
   */
  public static final int TRAILER_IMPLICIT = 188;
  /**
   * @deprecated
   */
  public static final int TRAILER_RIPEMD160 = 12748;
  /**
   * @deprecated
   */
  public static final int TRAILER_RIPEMD128 = 13004;
  /**
   * @deprecated
   */
  public static final int TRAILER_SHA1 = 13260;
  /**
   * @deprecated
   */
  public static final int TRAILER_SHA256 = 13516;
  /**
   * @deprecated
   */
  public static final int TRAILER_SHA512 = 13772;
  /**
   * @deprecated
   */
  public static final int TRAILER_SHA384 = 14028;
  /**
   * @deprecated
   */
  public static final int TRAILER_WHIRLPOOL = 14284;
  /**
   * @deprecated
   */
  public static final int TRAILER_SHA224 = 14540;
  private Digest digest;
  private AsymmetricBlockCipher cipher;
  private RSAKeyParameters kParam;
  private int trailer;
  private int keyBits;
  private byte[] block;
  
  public X931Signer(AsymmetricBlockCipher cipher, Digest digest, boolean implicit)
  {
    this.cipher = cipher;
    this.digest = digest;
    
    if (implicit)
    {
      trailer = 188;
    }
    else
    {
      Integer trailerObj = ISOTrailers.getTrailer(digest);
      
      if (trailerObj != null)
      {
        trailer = trailerObj.intValue();
      }
      else
      {
        throw new IllegalArgumentException("no valid trailer for digest: " + digest.getAlgorithmName());
      }
    }
  }
  








  public X931Signer(AsymmetricBlockCipher cipher, Digest digest)
  {
    this(cipher, digest, false);
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    kParam = ((RSAKeyParameters)param);
    
    cipher.init(forSigning, kParam);
    
    keyBits = kParam.getModulus().bitLength();
    
    block = new byte[(keyBits + 7) / 8];
    
    reset();
  }
  




  private void clearBlock(byte[] block)
  {
    for (int i = 0; i != block.length; i++)
    {
      block[i] = 0;
    }
  }
  




  public void update(byte b)
  {
    digest.update(b);
  }
  






  public void update(byte[] in, int off, int len)
  {
    digest.update(in, off, len);
  }
  



  public void reset()
  {
    digest.reset();
  }
  




  public byte[] generateSignature()
    throws CryptoException
  {
    createSignatureBlock();
    
    BigInteger t = new BigInteger(1, cipher.processBlock(block, 0, block.length));
    clearBlock(block);
    
    t = t.min(kParam.getModulus().subtract(t));
    
    return BigIntegers.asUnsignedByteArray((kParam.getModulus().bitLength() + 7) / 8, t);
  }
  
  private void createSignatureBlock()
  {
    int digSize = digest.getDigestSize();
    
    int delta;
    
    if (trailer == 188)
    {
      int delta = block.length - digSize - 1;
      digest.doFinal(block, delta);
      block[(block.length - 1)] = -68;
    }
    else
    {
      delta = block.length - digSize - 2;
      digest.doFinal(block, delta);
      block[(block.length - 2)] = ((byte)(trailer >>> 8));
      block[(block.length - 1)] = ((byte)trailer);
    }
    
    block[0] = 107;
    for (int i = delta - 2; i != 0; i--)
    {
      block[i] = -69;
    }
    block[(delta - 1)] = -70;
  }
  





  public boolean verifySignature(byte[] signature)
  {
    try
    {
      block = cipher.processBlock(signature, 0, signature.length);
    }
    catch (Exception e)
    {
      return false;
    }
    
    BigInteger t = new BigInteger(1, block);
    
    BigInteger f;
    if ((t.intValue() & 0xF) == 12)
    {
      f = t;
    }
    else
    {
      t = kParam.getModulus().subtract(t);
      BigInteger f; if ((t.intValue() & 0xF) == 12)
      {
        f = t;
      }
      else
      {
        return false;
      }
    }
    BigInteger f;
    createSignatureBlock();
    
    byte[] fBlock = BigIntegers.asUnsignedByteArray(block.length, f);
    
    boolean rv = Arrays.constantTimeAreEqual(block, fBlock);
    
    clearBlock(block);
    clearBlock(fBlock);
    
    return rv;
  }
}
