package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSABlindingParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;



















public class PSSSigner
  implements Signer
{
  public static final byte TRAILER_IMPLICIT = -68;
  private Digest contentDigest;
  private Digest mgfDigest;
  private AsymmetricBlockCipher cipher;
  private SecureRandom random;
  private int hLen;
  private int mgfhLen;
  private boolean sSet;
  private int sLen;
  private int emBits;
  private byte[] salt;
  private byte[] mDash;
  private byte[] block;
  private byte trailer;
  
  public PSSSigner(AsymmetricBlockCipher cipher, Digest digest, int sLen)
  {
    this(cipher, digest, sLen, (byte)-68);
  }
  




  public PSSSigner(AsymmetricBlockCipher cipher, Digest contentDigest, Digest mgfDigest, int sLen)
  {
    this(cipher, contentDigest, mgfDigest, sLen, (byte)-68);
  }
  




  public PSSSigner(AsymmetricBlockCipher cipher, Digest digest, int sLen, byte trailer)
  {
    this(cipher, digest, digest, sLen, trailer);
  }
  





  public PSSSigner(AsymmetricBlockCipher cipher, Digest contentDigest, Digest mgfDigest, int sLen, byte trailer)
  {
    this.cipher = cipher;
    this.contentDigest = contentDigest;
    this.mgfDigest = mgfDigest;
    hLen = contentDigest.getDigestSize();
    mgfhLen = mgfDigest.getDigestSize();
    sSet = false;
    this.sLen = sLen;
    salt = new byte[sLen];
    mDash = new byte[8 + sLen + hLen];
    this.trailer = trailer;
  }
  



  public PSSSigner(AsymmetricBlockCipher cipher, Digest digest, byte[] salt)
  {
    this(cipher, digest, digest, salt, (byte)-68);
  }
  




  public PSSSigner(AsymmetricBlockCipher cipher, Digest contentDigest, Digest mgfDigest, byte[] salt)
  {
    this(cipher, contentDigest, mgfDigest, salt, (byte)-68);
  }
  





  public PSSSigner(AsymmetricBlockCipher cipher, Digest contentDigest, Digest mgfDigest, byte[] salt, byte trailer)
  {
    this.cipher = cipher;
    this.contentDigest = contentDigest;
    this.mgfDigest = mgfDigest;
    hLen = contentDigest.getDigestSize();
    mgfhLen = mgfDigest.getDigestSize();
    sSet = true;
    sLen = salt.length;
    this.salt = salt;
    mDash = new byte[8 + sLen + hLen];
    this.trailer = trailer;
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    CipherParameters params;
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom p = (ParametersWithRandom)param;
      
      CipherParameters params = p.getParameters();
      random = p.getRandom();
    }
    else
    {
      params = param;
      if (forSigning)
      {
        random = new SecureRandom();
      }
    }
    
    RSAKeyParameters kParam;
    
    if ((params instanceof RSABlindingParameters))
    {
      RSAKeyParameters kParam = ((RSABlindingParameters)params).getPublicKey();
      
      cipher.init(forSigning, param);
    }
    else
    {
      kParam = (RSAKeyParameters)params;
      
      cipher.init(forSigning, params);
    }
    
    emBits = (kParam.getModulus().bitLength() - 1);
    
    if (emBits < 8 * hLen + 8 * sLen + 9)
    {
      throw new IllegalArgumentException("key too small for specified hash and salt lengths");
    }
    
    block = new byte[(emBits + 7) / 8];
    
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
    contentDigest.update(b);
  }
  






  public void update(byte[] in, int off, int len)
  {
    contentDigest.update(in, off, len);
  }
  



  public void reset()
  {
    contentDigest.reset();
  }
  




  public byte[] generateSignature()
    throws CryptoException, DataLengthException
  {
    contentDigest.doFinal(mDash, mDash.length - hLen - sLen);
    
    if (sLen != 0)
    {
      if (!sSet)
      {
        random.nextBytes(salt);
      }
      
      System.arraycopy(salt, 0, mDash, mDash.length - sLen, sLen);
    }
    
    byte[] h = new byte[hLen];
    
    contentDigest.update(mDash, 0, mDash.length);
    
    contentDigest.doFinal(h, 0);
    
    block[(block.length - sLen - 1 - hLen - 1)] = 1;
    System.arraycopy(salt, 0, block, block.length - sLen - hLen - 1, sLen);
    
    byte[] dbMask = maskGeneratorFunction1(h, 0, h.length, block.length - hLen - 1);
    for (int i = 0; i != dbMask.length; i++)
    {
      int tmp210_209 = i; byte[] tmp210_206 = block;tmp210_206[tmp210_209] = ((byte)(tmp210_206[tmp210_209] ^ dbMask[i]));
    }
    
    int tmp229_228 = 0; byte[] tmp229_225 = block;tmp229_225[tmp229_228] = ((byte)(tmp229_225[tmp229_228] & 255 >> block.length * 8 - emBits));
    
    System.arraycopy(h, 0, block, block.length - hLen - 1, hLen);
    
    block[(block.length - 1)] = trailer;
    
    byte[] b = cipher.processBlock(block, 0, block.length);
    
    clearBlock(block);
    
    return b;
  }
  





  public boolean verifySignature(byte[] signature)
  {
    contentDigest.doFinal(mDash, mDash.length - hLen - sLen);
    
    try
    {
      byte[] b = cipher.processBlock(signature, 0, signature.length);
      System.arraycopy(b, 0, block, block.length - b.length, b.length);
    }
    catch (Exception e)
    {
      return false;
    }
    
    if (block[(block.length - 1)] != trailer)
    {
      clearBlock(block);
      return false;
    }
    
    byte[] dbMask = maskGeneratorFunction1(block, block.length - hLen - 1, hLen, block.length - hLen - 1);
    
    for (int i = 0; i != dbMask.length; i++)
    {
      int tmp147_146 = i; byte[] tmp147_143 = block;tmp147_143[tmp147_146] = ((byte)(tmp147_143[tmp147_146] ^ dbMask[i]));
    }
    
    int tmp166_165 = 0; byte[] tmp166_162 = block;tmp166_162[tmp166_165] = ((byte)(tmp166_162[tmp166_165] & 255 >> block.length * 8 - emBits));
    
    for (int i = 0; i != block.length - hLen - sLen - 2; i++)
    {
      if (block[i] != 0)
      {
        clearBlock(block);
        return false;
      }
    }
    
    if (block[(block.length - hLen - sLen - 2)] != 1)
    {
      clearBlock(block);
      return false;
    }
    
    if (sSet)
    {
      System.arraycopy(salt, 0, mDash, mDash.length - sLen, sLen);
    }
    else
    {
      System.arraycopy(block, block.length - sLen - hLen - 1, mDash, mDash.length - sLen, sLen);
    }
    
    contentDigest.update(mDash, 0, mDash.length);
    contentDigest.doFinal(mDash, mDash.length - hLen);
    
    int i = block.length - hLen - 1; for (int j = mDash.length - hLen; 
        j != mDash.length; j++)
    {
      if ((block[i] ^ mDash[j]) != 0)
      {
        clearBlock(mDash);
        clearBlock(block);
        return false;
      }
      i++;
    }
    







    clearBlock(mDash);
    clearBlock(block);
    
    return true;
  }
  





  private void ItoOSP(int i, byte[] sp)
  {
    sp[0] = ((byte)(i >>> 24));
    sp[1] = ((byte)(i >>> 16));
    sp[2] = ((byte)(i >>> 8));
    sp[3] = ((byte)(i >>> 0));
  }
  







  private byte[] maskGeneratorFunction1(byte[] Z, int zOff, int zLen, int length)
  {
    byte[] mask = new byte[length];
    byte[] hashBuf = new byte[mgfhLen];
    byte[] C = new byte[4];
    int counter = 0;
    
    mgfDigest.reset();
    
    while (counter < length / mgfhLen)
    {
      ItoOSP(counter, C);
      
      mgfDigest.update(Z, zOff, zLen);
      mgfDigest.update(C, 0, C.length);
      mgfDigest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * mgfhLen, mgfhLen);
      
      counter++;
    }
    
    if (counter * mgfhLen < length)
    {
      ItoOSP(counter, C);
      
      mgfDigest.update(Z, zOff, zLen);
      mgfDigest.update(C, 0, C.length);
      mgfDigest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * mgfhLen, mask.length - counter * mgfhLen);
    }
    
    return mask;
  }
}
