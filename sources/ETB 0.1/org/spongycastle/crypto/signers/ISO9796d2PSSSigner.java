package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSalt;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;







public class ISO9796d2PSSSigner
  implements SignerWithRecovery
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
  private Digest digest;
  private AsymmetricBlockCipher cipher;
  private SecureRandom random;
  private byte[] standardSalt;
  private int hLen;
  private int trailer;
  private int keyBits;
  private byte[] block;
  private byte[] mBuf;
  private int messageLength;
  private int saltLength;
  private boolean fullMessage;
  private byte[] recoveredMessage;
  private byte[] preSig;
  private byte[] preBlock;
  private int preMStart;
  private int preTLength;
  
  public ISO9796d2PSSSigner(AsymmetricBlockCipher cipher, Digest digest, int saltLength, boolean implicit)
  {
    this.cipher = cipher;
    this.digest = digest;
    hLen = digest.getDigestSize();
    this.saltLength = saltLength;
    
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
  










  public ISO9796d2PSSSigner(AsymmetricBlockCipher cipher, Digest digest, int saltLength)
  {
    this(cipher, digest, saltLength, false);
  }
  














  public void init(boolean forSigning, CipherParameters param)
  {
    int lengthOfSalt = saltLength;
    RSAKeyParameters kParam;
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom p = (ParametersWithRandom)param;
      
      RSAKeyParameters kParam = (RSAKeyParameters)p.getParameters();
      if (forSigning)
      {
        random = p.getRandom();
      }
    }
    else if ((param instanceof ParametersWithSalt))
    {
      ParametersWithSalt p = (ParametersWithSalt)param;
      
      RSAKeyParameters kParam = (RSAKeyParameters)p.getParameters();
      standardSalt = p.getSalt();
      lengthOfSalt = standardSalt.length;
      if (standardSalt.length != saltLength)
      {
        throw new IllegalArgumentException("Fixed salt is of wrong length");
      }
    }
    else
    {
      kParam = (RSAKeyParameters)param;
      if (forSigning)
      {
        random = new SecureRandom();
      }
    }
    
    cipher.init(forSigning, kParam);
    
    keyBits = kParam.getModulus().bitLength();
    
    block = new byte[(keyBits + 7) / 8];
    
    if (trailer == 188)
    {
      mBuf = new byte[block.length - digest.getDigestSize() - lengthOfSalt - 1 - 1];
    }
    else
    {
      mBuf = new byte[block.length - digest.getDigestSize() - lengthOfSalt - 1 - 2];
    }
    
    reset();
  }
  





  private boolean isSameAs(byte[] a, byte[] b)
  {
    boolean isOkay = true;
    
    if (messageLength != b.length)
    {
      isOkay = false;
    }
    
    for (int i = 0; i != b.length; i++)
    {
      if (a[i] != b[i])
      {
        isOkay = false;
      }
    }
    
    return isOkay;
  }
  




  private void clearBlock(byte[] block)
  {
    for (int i = 0; i != block.length; i++)
    {
      block[i] = 0;
    }
  }
  
  public void updateWithRecoveredMessage(byte[] signature)
    throws InvalidCipherTextException
  {
    byte[] block = cipher.processBlock(signature, 0, signature.length);
    



    if (block.length < (keyBits + 7) / 8)
    {
      byte[] tmp = new byte[(keyBits + 7) / 8];
      
      System.arraycopy(block, 0, tmp, tmp.length - block.length, block.length);
      clearBlock(block);
      block = tmp;
    }
    
    int tLength;
    int tLength;
    if ((block[(block.length - 1)] & 0xFF ^ 0xBC) == 0)
    {
      tLength = 1;
    }
    else
    {
      int sigTrail = (block[(block.length - 2)] & 0xFF) << 8 | block[(block.length - 1)] & 0xFF;
      
      Integer trailerObj = ISOTrailers.getTrailer(digest);
      
      if (trailerObj != null)
      {
        if (sigTrail != trailerObj.intValue())
        {
          throw new IllegalStateException("signer initialised with wrong digest for trailer " + sigTrail);
        }
        
      }
      else {
        throw new IllegalArgumentException("unrecognised hash in signature");
      }
      
      tLength = 2;
    }
    



    byte[] m2Hash = new byte[hLen];
    digest.doFinal(m2Hash, 0);
    



    byte[] dbMask = maskGeneratorFunction1(block, block.length - hLen - tLength, hLen, block.length - hLen - tLength);
    for (int i = 0; i != dbMask.length; i++)
    {
      int tmp238_236 = i; byte[] tmp238_235 = block;tmp238_235[tmp238_236] = ((byte)(tmp238_235[tmp238_236] ^ dbMask[i]));
    }
    
    int tmp256_255 = 0; byte[] tmp256_254 = block;tmp256_254[tmp256_255] = ((byte)(tmp256_254[tmp256_255] & 0x7F));
    



    for (int mStart = 0; 
        mStart != block.length; mStart++)
    {
      if (block[mStart] == 1) {
        break;
      }
    }
    

    mStart++;
    
    if (mStart >= block.length)
    {
      clearBlock(block);
    }
    
    fullMessage = (mStart > 1);
    
    recoveredMessage = new byte[dbMask.length - mStart - saltLength];
    
    System.arraycopy(block, mStart, recoveredMessage, 0, recoveredMessage.length);
    System.arraycopy(recoveredMessage, 0, mBuf, 0, recoveredMessage.length);
    
    preSig = signature;
    preBlock = block;
    preMStart = mStart;
    preTLength = tLength;
  }
  




  public void update(byte b)
  {
    if ((preSig == null) && (messageLength < mBuf.length))
    {
      mBuf[(messageLength++)] = b;
    }
    else
    {
      digest.update(b);
    }
  }
  






  public void update(byte[] in, int off, int len)
  {
    if (preSig == null)
    {
      while ((len > 0) && (messageLength < mBuf.length))
      {
        update(in[off]);
        off++;
        len--;
      }
    }
    
    if (len > 0)
    {
      digest.update(in, off, len);
    }
  }
  



  public void reset()
  {
    digest.reset();
    messageLength = 0;
    if (mBuf != null)
    {
      clearBlock(mBuf);
    }
    if (recoveredMessage != null)
    {
      clearBlock(recoveredMessage);
      recoveredMessage = null;
    }
    fullMessage = false;
    if (preSig != null)
    {
      preSig = null;
      clearBlock(preBlock);
      preBlock = null;
    }
  }
  




  public byte[] generateSignature()
    throws CryptoException
  {
    int digSize = digest.getDigestSize();
    
    byte[] m2Hash = new byte[digSize];
    
    digest.doFinal(m2Hash, 0);
    
    byte[] C = new byte[8];
    LtoOSP(messageLength * 8, C);
    
    digest.update(C, 0, C.length);
    
    digest.update(mBuf, 0, messageLength);
    
    digest.update(m2Hash, 0, m2Hash.length);
    
    byte[] salt;
    byte[] salt;
    if (standardSalt != null)
    {
      salt = standardSalt;
    }
    else
    {
      salt = new byte[saltLength];
      random.nextBytes(salt);
    }
    
    digest.update(salt, 0, salt.length);
    
    byte[] hash = new byte[digest.getDigestSize()];
    
    digest.doFinal(hash, 0);
    
    int tLength = 2;
    if (trailer == 188)
    {
      tLength = 1;
    }
    
    int off = block.length - messageLength - salt.length - hLen - tLength - 1;
    
    block[off] = 1;
    
    System.arraycopy(mBuf, 0, block, off + 1, messageLength);
    System.arraycopy(salt, 0, block, off + 1 + messageLength, salt.length);
    
    byte[] dbMask = maskGeneratorFunction1(hash, 0, hash.length, block.length - hLen - tLength);
    for (int i = 0; i != dbMask.length; i++)
    {
      int tmp296_294 = i; byte[] tmp296_291 = block;tmp296_291[tmp296_294] = ((byte)(tmp296_291[tmp296_294] ^ dbMask[i]));
    }
    
    System.arraycopy(hash, 0, block, block.length - hLen - tLength, hLen);
    
    if (trailer == 188)
    {
      block[(block.length - 1)] = -68;
    }
    else
    {
      block[(block.length - 2)] = ((byte)(trailer >>> 8));
      block[(block.length - 1)] = ((byte)trailer);
    }
    
    int tmp408_407 = 0; byte[] tmp408_404 = block;tmp408_404[tmp408_407] = ((byte)(tmp408_404[tmp408_407] & 0x7F));
    
    byte[] b = cipher.processBlock(block, 0, block.length);
    
    recoveredMessage = new byte[messageLength];
    
    fullMessage = (messageLength <= mBuf.length);
    System.arraycopy(mBuf, 0, recoveredMessage, 0, recoveredMessage.length);
    
    clearBlock(mBuf);
    clearBlock(block);
    messageLength = 0;
    
    return b;
  }
  








  public boolean verifySignature(byte[] signature)
  {
    byte[] m2Hash = new byte[hLen];
    digest.doFinal(m2Hash, 0);
    


    int mStart = 0;
    
    if (preSig == null)
    {
      try
      {
        updateWithRecoveredMessage(signature);
      }
      catch (Exception e)
      {
        return false;

      }
      
    }
    else if (!Arrays.areEqual(preSig, signature))
    {
      throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
    }
    

    byte[] block = preBlock;
    mStart = preMStart;
    int tLength = preTLength;
    
    preSig = null;
    preBlock = null;
    



    byte[] C = new byte[8];
    LtoOSP(recoveredMessage.length * 8, C);
    
    digest.update(C, 0, C.length);
    
    if (recoveredMessage.length != 0)
    {
      digest.update(recoveredMessage, 0, recoveredMessage.length);
    }
    
    digest.update(m2Hash, 0, m2Hash.length);
    

    if (standardSalt != null)
    {
      digest.update(standardSalt, 0, standardSalt.length);
    }
    else
    {
      digest.update(block, mStart + recoveredMessage.length, saltLength);
    }
    
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    
    int off = block.length - tLength - hash.length;
    
    boolean isOkay = true;
    
    for (int i = 0; i != hash.length; i++)
    {
      if (hash[i] != block[(off + i)])
      {
        isOkay = false;
      }
    }
    
    clearBlock(block);
    clearBlock(hash);
    
    if (!isOkay)
    {
      fullMessage = false;
      messageLength = 0;
      clearBlock(recoveredMessage);
      return false;
    }
    




    if (messageLength != 0)
    {
      if (!isSameAs(mBuf, recoveredMessage))
      {
        messageLength = 0;
        clearBlock(mBuf);
        return false;
      }
    }
    

    messageLength = 0;
    
    clearBlock(mBuf);
    return true;
  }
  






  public boolean hasFullMessage()
  {
    return fullMessage;
  }
  








  public byte[] getRecoveredMessage()
  {
    return recoveredMessage;
  }
  





  private void ItoOSP(int i, byte[] sp)
  {
    sp[0] = ((byte)(i >>> 24));
    sp[1] = ((byte)(i >>> 16));
    sp[2] = ((byte)(i >>> 8));
    sp[3] = ((byte)(i >>> 0));
  }
  





  private void LtoOSP(long l, byte[] sp)
  {
    sp[0] = ((byte)(int)(l >>> 56));
    sp[1] = ((byte)(int)(l >>> 48));
    sp[2] = ((byte)(int)(l >>> 40));
    sp[3] = ((byte)(int)(l >>> 32));
    sp[4] = ((byte)(int)(l >>> 24));
    sp[5] = ((byte)(int)(l >>> 16));
    sp[6] = ((byte)(int)(l >>> 8));
    sp[7] = ((byte)(int)(l >>> 0));
  }
  







  private byte[] maskGeneratorFunction1(byte[] Z, int zOff, int zLen, int length)
  {
    byte[] mask = new byte[length];
    byte[] hashBuf = new byte[hLen];
    byte[] C = new byte[4];
    int counter = 0;
    
    digest.reset();
    
    while (counter < length / hLen)
    {
      ItoOSP(counter, C);
      
      digest.update(Z, zOff, zLen);
      digest.update(C, 0, C.length);
      digest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * hLen, hLen);
      
      counter++;
    }
    
    if (counter * hLen < length)
    {
      ItoOSP(counter, C);
      
      digest.update(Z, zOff, zLen);
      digest.update(C, 0, C.length);
      digest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * hLen, mask.length - counter * hLen);
    }
    
    return mask;
  }
}
