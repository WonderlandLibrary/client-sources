package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;

public class ISO9796d2Signer implements SignerWithRecovery
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
  private int trailer;
  private int keyBits;
  private byte[] block;
  private byte[] mBuf;
  private int messageLength;
  private boolean fullMessage;
  private byte[] recoveredMessage;
  private byte[] preSig;
  private byte[] preBlock;
  
  public ISO9796d2Signer(AsymmetricBlockCipher cipher, Digest digest, boolean implicit)
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
  








  public ISO9796d2Signer(AsymmetricBlockCipher cipher, Digest digest)
  {
    this(cipher, digest, false);
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    RSAKeyParameters kParam = (RSAKeyParameters)param;
    
    cipher.init(forSigning, kParam);
    
    keyBits = kParam.getModulus().bitLength();
    
    block = new byte[(keyBits + 7) / 8];
    
    if (trailer == 188)
    {
      mBuf = new byte[block.length - digest.getDigestSize() - 2];
    }
    else
    {
      mBuf = new byte[block.length - digest.getDigestSize() - 3];
    }
    
    reset();
  }
  





  private boolean isSameAs(byte[] a, byte[] b)
  {
    boolean isOkay = true;
    
    if (messageLength > mBuf.length)
    {
      if (mBuf.length > b.length)
      {
        isOkay = false;
      }
      
      for (int i = 0; i != mBuf.length; i++)
      {
        if (a[i] != b[i])
        {
          isOkay = false;
        }
      }
    }
    else
    {
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
    
    if ((block[0] & 0xC0 ^ 0x40) != 0)
    {
      throw new InvalidCipherTextException("malformed signature");
    }
    
    if ((block[(block.length - 1)] & 0xF ^ 0xC) != 0)
    {
      throw new InvalidCipherTextException("malformed signature");
    }
    
    int delta = 0;
    
    if ((block[(block.length - 1)] & 0xFF ^ 0xBC) == 0)
    {
      delta = 1;
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
      
      delta = 2;
    }
    



    int mStart = 0;
    
    for (mStart = 0; mStart != block.length; mStart++)
    {
      if ((block[mStart] & 0xF ^ 0xA) == 0) {
        break;
      }
    }
    

    mStart++;
    
    int off = block.length - delta - digest.getDigestSize();
    



    if (off - mStart <= 0)
    {
      throw new InvalidCipherTextException("malformed block");
    }
    



    if ((block[0] & 0x20) == 0)
    {
      fullMessage = true;
      
      recoveredMessage = new byte[off - mStart];
      System.arraycopy(block, mStart, recoveredMessage, 0, recoveredMessage.length);
    }
    else
    {
      fullMessage = false;
      
      recoveredMessage = new byte[off - mStart];
      System.arraycopy(block, mStart, recoveredMessage, 0, recoveredMessage.length);
    }
    
    preSig = signature;
    preBlock = block;
    
    digest.update(recoveredMessage, 0, recoveredMessage.length);
    messageLength = recoveredMessage.length;
    System.arraycopy(recoveredMessage, 0, mBuf, 0, recoveredMessage.length);
  }
  




  public void update(byte b)
  {
    digest.update(b);
    
    if (messageLength < mBuf.length)
    {
      mBuf[messageLength] = b;
    }
    
    messageLength += 1;
  }
  






  public void update(byte[] in, int off, int len)
  {
    while ((len > 0) && (messageLength < mBuf.length))
    {
      update(in[off]);
      off++;
      len--;
    }
    
    digest.update(in, off, len);
    messageLength += len;
  }
  



  public void reset()
  {
    digest.reset();
    messageLength = 0;
    clearBlock(mBuf);
    
    if (recoveredMessage != null)
    {
      clearBlock(recoveredMessage);
    }
    
    recoveredMessage = null;
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
    
    int t = 0;
    int delta = 0;
    
    if (trailer == 188)
    {
      t = 8;
      delta = block.length - digSize - 1;
      digest.doFinal(block, delta);
      block[(block.length - 1)] = -68;
    }
    else
    {
      t = 16;
      delta = block.length - digSize - 2;
      digest.doFinal(block, delta);
      block[(block.length - 2)] = ((byte)(trailer >>> 8));
      block[(block.length - 1)] = ((byte)trailer);
    }
    
    byte header = 0;
    int x = (digSize + messageLength) * 8 + t + 4 - keyBits;
    
    if (x > 0)
    {
      int mR = messageLength - (x + 7) / 8;
      header = 96;
      
      delta -= mR;
      
      System.arraycopy(mBuf, 0, block, delta, mR);
      
      recoveredMessage = new byte[mR];
    }
    else
    {
      header = 64;
      delta -= messageLength;
      
      System.arraycopy(mBuf, 0, block, delta, messageLength);
      
      recoveredMessage = new byte[messageLength];
    }
    
    if (delta - 1 > 0)
    {
      for (int i = delta - 1; i != 0; i--)
      {
        block[i] = -69;
      }
      int tmp288_287 = (delta - 1); byte[] tmp288_282 = block;tmp288_282[tmp288_287] = ((byte)(tmp288_282[tmp288_287] ^ 0x1));
      block[0] = 11; int 
        tmp307_306 = 0; byte[] tmp307_303 = block;tmp307_303[tmp307_306] = ((byte)(tmp307_303[tmp307_306] | header));
    }
    else
    {
      block[0] = 10; int 
        tmp330_329 = 0; byte[] tmp330_326 = block;tmp330_326[tmp330_329] = ((byte)(tmp330_326[tmp330_329] | header));
    }
    
    byte[] b = cipher.processBlock(block, 0, block.length);
    
    fullMessage = ((header & 0x20) == 0);
    System.arraycopy(mBuf, 0, recoveredMessage, 0, recoveredMessage.length);
    
    messageLength = 0;
    
    clearBlock(mBuf);
    clearBlock(block);
    
    return b;
  }
  





  public boolean verifySignature(byte[] signature)
  {
    byte[] block = null;
    
    if (preSig == null)
    {
      try
      {
        block = cipher.processBlock(signature, 0, signature.length);
      }
      catch (Exception e)
      {
        return false;
      }
    }
    else
    {
      if (!Arrays.areEqual(preSig, signature))
      {
        throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
      }
      
      block = preBlock;
      
      preSig = null;
      preBlock = null;
    }
    
    if ((block[0] & 0xC0 ^ 0x40) != 0)
    {
      return returnFalse(block);
    }
    
    if ((block[(block.length - 1)] & 0xF ^ 0xC) != 0)
    {
      return returnFalse(block);
    }
    
    int delta = 0;
    
    if ((block[(block.length - 1)] & 0xFF ^ 0xBC) == 0)
    {
      delta = 1;
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
      
      delta = 2;
    }
    



    int mStart = 0;
    
    for (mStart = 0; mStart != block.length; mStart++)
    {
      if ((block[mStart] & 0xF ^ 0xA) == 0) {
        break;
      }
    }
    

    mStart++;
    



    byte[] hash = new byte[digest.getDigestSize()];
    
    int off = block.length - delta - hash.length;
    



    if (off - mStart <= 0)
    {
      return returnFalse(block);
    }
    



    if ((block[0] & 0x20) == 0)
    {
      fullMessage = true;
      

      if (messageLength > off - mStart)
      {
        return returnFalse(block);
      }
      
      digest.reset();
      digest.update(block, mStart, off - mStart);
      digest.doFinal(hash, 0);
      
      boolean isOkay = true;
      
      for (int i = 0; i != hash.length; i++)
      {
        int tmp385_384 = (off + i); byte[] tmp385_379 = block;tmp385_379[tmp385_384] = ((byte)(tmp385_379[tmp385_384] ^ hash[i]));
        if (block[(off + i)] != 0)
        {
          isOkay = false;
        }
      }
      
      if (!isOkay)
      {
        return returnFalse(block);
      }
      
      recoveredMessage = new byte[off - mStart];
      System.arraycopy(block, mStart, recoveredMessage, 0, recoveredMessage.length);
    }
    else
    {
      fullMessage = false;
      
      digest.doFinal(hash, 0);
      
      boolean isOkay = true;
      
      for (int i = 0; i != hash.length; i++)
      {
        int tmp493_492 = (off + i); byte[] tmp493_487 = block;tmp493_487[tmp493_492] = ((byte)(tmp493_487[tmp493_492] ^ hash[i]));
        if (block[(off + i)] != 0)
        {
          isOkay = false;
        }
      }
      
      if (!isOkay)
      {
        return returnFalse(block);
      }
      
      recoveredMessage = new byte[off - mStart];
      System.arraycopy(block, mStart, recoveredMessage, 0, recoveredMessage.length);
    }
    




    if (messageLength != 0)
    {
      if (!isSameAs(mBuf, recoveredMessage))
      {
        return returnFalse(block);
      }
    }
    
    clearBlock(mBuf);
    clearBlock(block);
    
    messageLength = 0;
    
    return true;
  }
  
  private boolean returnFalse(byte[] block)
  {
    messageLength = 0;
    
    clearBlock(mBuf);
    clearBlock(block);
    
    return false;
  }
  






  public boolean hasFullMessage()
  {
    return fullMessage;
  }
  







  public byte[] getRecoveredMessage()
  {
    return recoveredMessage;
  }
}
