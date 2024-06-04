package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.modes.gcm.GCMExponentiator;
import org.spongycastle.crypto.modes.gcm.GCMMultiplier;
import org.spongycastle.crypto.modes.gcm.GCMUtil;
import org.spongycastle.crypto.modes.gcm.Tables1kGCMExponentiator;
import org.spongycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;









public class GCMBlockCipher
  implements AEADBlockCipher
{
  private static final int BLOCK_SIZE = 16;
  private BlockCipher cipher;
  private GCMMultiplier multiplier;
  private GCMExponentiator exp;
  private boolean forEncryption;
  private boolean initialised;
  private int macSize;
  private byte[] lastKey;
  private byte[] nonce;
  private byte[] initialAssociatedText;
  private byte[] H;
  private byte[] J0;
  private byte[] bufBlock;
  private byte[] macBlock;
  private byte[] S;
  private byte[] S_at;
  private byte[] S_atPre;
  private byte[] counter;
  private int blocksRemaining;
  private int bufOff;
  private long totalLength;
  private byte[] atBlock;
  private int atBlockPos;
  private long atLength;
  private long atLengthPre;
  
  public GCMBlockCipher(BlockCipher c)
  {
    this(c, null);
  }
  
  public GCMBlockCipher(BlockCipher c, GCMMultiplier m)
  {
    if (c.getBlockSize() != 16)
    {
      throw new IllegalArgumentException("cipher required with a block size of 16.");
    }
    

    if (m == null)
    {

      m = new Tables8kGCMMultiplier();
    }
    
    cipher = c;
    multiplier = m;
  }
  
  public BlockCipher getUnderlyingCipher()
  {
    return cipher;
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/GCM";
  }
  




  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    this.forEncryption = forEncryption;
    macBlock = null;
    initialised = true;
    

    byte[] newNonce = null;
    KeyParameter keyParam;
    if ((params instanceof AEADParameters))
    {
      AEADParameters param = (AEADParameters)params;
      
      newNonce = param.getNonce();
      initialAssociatedText = param.getAssociatedText();
      
      int macSizeBits = param.getMacSize();
      if ((macSizeBits < 32) || (macSizeBits > 128) || (macSizeBits % 8 != 0))
      {
        throw new IllegalArgumentException("Invalid value for MAC size: " + macSizeBits);
      }
      
      macSize = (macSizeBits / 8);
      keyParam = param.getKey();
    } else { KeyParameter keyParam;
      if ((params instanceof ParametersWithIV))
      {
        ParametersWithIV param = (ParametersWithIV)params;
        
        newNonce = param.getIV();
        initialAssociatedText = null;
        macSize = 16;
        keyParam = (KeyParameter)param.getParameters();
      }
      else
      {
        throw new IllegalArgumentException("invalid parameters passed to GCM");
      } }
    KeyParameter keyParam;
    int bufLength = forEncryption ? 16 : 16 + macSize;
    bufBlock = new byte[bufLength];
    
    if ((newNonce == null) || (newNonce.length < 1))
    {
      throw new IllegalArgumentException("IV must be at least 1 byte");
    }
    
    if (forEncryption)
    {
      if ((nonce != null) && (Arrays.areEqual(nonce, newNonce)))
      {
        if (keyParam == null)
        {
          throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
        }
        if ((lastKey != null) && (Arrays.areEqual(lastKey, keyParam.getKey())))
        {
          throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
        }
      }
    }
    
    nonce = newNonce;
    if (keyParam != null)
    {
      lastKey = keyParam.getKey();
    }
    




    if (keyParam != null)
    {
      cipher.init(true, keyParam);
      
      H = new byte[16];
      cipher.processBlock(H, 0, H, 0);
      

      multiplier.init(H);
      exp = null;
    }
    else if (H == null)
    {
      throw new IllegalArgumentException("Key must be specified in initial init");
    }
    
    J0 = new byte[16];
    
    if (nonce.length == 12)
    {
      System.arraycopy(nonce, 0, J0, 0, nonce.length);
      J0[15] = 1;
    }
    else
    {
      gHASH(J0, nonce, nonce.length);
      byte[] X = new byte[16];
      Pack.longToBigEndian(nonce.length * 8L, X, 8);
      gHASHBlock(J0, X);
    }
    
    S = new byte[16];
    S_at = new byte[16];
    S_atPre = new byte[16];
    atBlock = new byte[16];
    atBlockPos = 0;
    atLength = 0L;
    atLengthPre = 0L;
    counter = Arrays.clone(J0);
    blocksRemaining = -2;
    bufOff = 0;
    totalLength = 0L;
    
    if (initialAssociatedText != null)
    {
      processAADBytes(initialAssociatedText, 0, initialAssociatedText.length);
    }
  }
  
  public byte[] getMac()
  {
    if (macBlock == null)
    {
      return new byte[macSize];
    }
    return Arrays.clone(macBlock);
  }
  
  public int getOutputSize(int len)
  {
    int totalData = len + bufOff;
    
    if (forEncryption)
    {
      return totalData + macSize;
    }
    
    return totalData < macSize ? 0 : totalData - macSize;
  }
  
  public int getUpdateOutputSize(int len)
  {
    int totalData = len + bufOff;
    if (!forEncryption)
    {
      if (totalData < macSize)
      {
        return 0;
      }
      totalData -= macSize;
    }
    return totalData - totalData % 16;
  }
  
  public void processAADByte(byte in)
  {
    checkStatus();
    
    atBlock[atBlockPos] = in;
    if (++atBlockPos == 16)
    {

      gHASHBlock(S_at, atBlock);
      atBlockPos = 0;
      atLength += 16L;
    }
  }
  
  public void processAADBytes(byte[] in, int inOff, int len)
  {
    checkStatus();
    
    for (int i = 0; i < len; i++)
    {
      atBlock[atBlockPos] = in[(inOff + i)];
      if (++atBlockPos == 16)
      {

        gHASHBlock(S_at, atBlock);
        atBlockPos = 0;
        atLength += 16L;
      }
    }
  }
  
  private void initCipher()
  {
    if (atLength > 0L)
    {
      System.arraycopy(S_at, 0, S_atPre, 0, 16);
      atLengthPre = atLength;
    }
    

    if (atBlockPos > 0)
    {
      gHASHPartial(S_atPre, atBlock, 0, atBlockPos);
      atLengthPre += atBlockPos;
    }
    
    if (atLengthPre > 0L)
    {
      System.arraycopy(S_atPre, 0, S, 0, 16);
    }
  }
  
  public int processByte(byte in, byte[] out, int outOff)
    throws DataLengthException
  {
    checkStatus();
    
    bufBlock[bufOff] = in;
    if (++bufOff == bufBlock.length)
    {
      outputBlock(out, outOff);
      return 16;
    }
    return 0;
  }
  
  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
    throws DataLengthException
  {
    checkStatus();
    
    if (in.length < inOff + len)
    {
      throw new DataLengthException("Input buffer too short");
    }
    int resultLen = 0;
    
    for (int i = 0; i < len; i++)
    {
      bufBlock[bufOff] = in[(inOff + i)];
      if (++bufOff == bufBlock.length)
      {
        outputBlock(out, outOff + resultLen);
        resultLen += 16;
      }
    }
    
    return resultLen;
  }
  
  private void outputBlock(byte[] output, int offset)
  {
    if (output.length < offset + 16)
    {
      throw new OutputLengthException("Output buffer too short");
    }
    if (totalLength == 0L)
    {
      initCipher();
    }
    gCTRBlock(bufBlock, output, offset);
    if (forEncryption)
    {
      bufOff = 0;
    }
    else
    {
      System.arraycopy(bufBlock, 16, bufBlock, 0, macSize);
      bufOff = macSize;
    }
  }
  
  public int doFinal(byte[] out, int outOff)
    throws IllegalStateException, InvalidCipherTextException
  {
    checkStatus();
    
    if (totalLength == 0L)
    {
      initCipher();
    }
    
    int extra = bufOff;
    
    if (forEncryption)
    {
      if (out.length < outOff + extra + macSize)
      {
        throw new OutputLengthException("Output buffer too short");
      }
    }
    else
    {
      if (extra < macSize)
      {
        throw new InvalidCipherTextException("data too short");
      }
      extra -= macSize;
      
      if (out.length < outOff + extra)
      {
        throw new OutputLengthException("Output buffer too short");
      }
    }
    
    if (extra > 0)
    {
      gCTRPartial(bufBlock, 0, extra, out, outOff);
    }
    
    atLength += atBlockPos;
    
    if (atLength > atLengthPre)
    {








      if (atBlockPos > 0)
      {
        gHASHPartial(S_at, atBlock, 0, atBlockPos);
      }
      

      if (atLengthPre > 0L)
      {
        GCMUtil.xor(S_at, S_atPre);
      }
      

      long c = totalLength * 8L + 127L >>> 7;
      

      byte[] H_c = new byte[16];
      if (exp == null)
      {
        exp = new Tables1kGCMExponentiator();
        exp.init(H);
      }
      exp.exponentiateX(c, H_c);
      

      GCMUtil.multiply(S_at, H_c);
      

      GCMUtil.xor(S, S_at);
    }
    

    byte[] X = new byte[16];
    Pack.longToBigEndian(atLength * 8L, X, 0);
    Pack.longToBigEndian(totalLength * 8L, X, 8);
    
    gHASHBlock(S, X);
    

    byte[] tag = new byte[16];
    cipher.processBlock(J0, 0, tag, 0);
    GCMUtil.xor(tag, S);
    
    int resultLen = extra;
    

    macBlock = new byte[macSize];
    System.arraycopy(tag, 0, macBlock, 0, macSize);
    
    if (forEncryption)
    {

      System.arraycopy(macBlock, 0, out, outOff + bufOff, macSize);
      resultLen += macSize;

    }
    else
    {
      byte[] msgMac = new byte[macSize];
      System.arraycopy(bufBlock, extra, msgMac, 0, macSize);
      if (!Arrays.constantTimeAreEqual(macBlock, msgMac))
      {
        throw new InvalidCipherTextException("mac check in GCM failed");
      }
    }
    
    reset(false);
    
    return resultLen;
  }
  
  public void reset()
  {
    reset(true);
  }
  

  private void reset(boolean clearMac)
  {
    cipher.reset();
    


    S = new byte[16];
    S_at = new byte[16];
    S_atPre = new byte[16];
    atBlock = new byte[16];
    atBlockPos = 0;
    atLength = 0L;
    atLengthPre = 0L;
    counter = Arrays.clone(J0);
    blocksRemaining = -2;
    bufOff = 0;
    totalLength = 0L;
    
    if (bufBlock != null)
    {
      Arrays.fill(bufBlock, (byte)0);
    }
    
    if (clearMac)
    {
      macBlock = null;
    }
    
    if (forEncryption)
    {
      initialised = false;


    }
    else if (initialAssociatedText != null)
    {
      processAADBytes(initialAssociatedText, 0, initialAssociatedText.length);
    }
  }
  

  private void gCTRBlock(byte[] block, byte[] out, int outOff)
  {
    byte[] tmp = getNextCounterBlock();
    
    GCMUtil.xor(tmp, block);
    System.arraycopy(tmp, 0, out, outOff, 16);
    
    gHASHBlock(S, forEncryption ? tmp : block);
    
    totalLength += 16L;
  }
  
  private void gCTRPartial(byte[] buf, int off, int len, byte[] out, int outOff)
  {
    byte[] tmp = getNextCounterBlock();
    
    GCMUtil.xor(tmp, buf, off, len);
    System.arraycopy(tmp, 0, out, outOff, len);
    
    gHASHPartial(S, forEncryption ? tmp : buf, 0, len);
    
    totalLength += len;
  }
  
  private void gHASH(byte[] Y, byte[] b, int len)
  {
    for (int pos = 0; pos < len; pos += 16)
    {
      int num = Math.min(len - pos, 16);
      gHASHPartial(Y, b, pos, num);
    }
  }
  
  private void gHASHBlock(byte[] Y, byte[] b)
  {
    GCMUtil.xor(Y, b);
    multiplier.multiplyH(Y);
  }
  
  private void gHASHPartial(byte[] Y, byte[] b, int off, int len)
  {
    GCMUtil.xor(Y, b, off, len);
    multiplier.multiplyH(Y);
  }
  
  private byte[] getNextCounterBlock()
  {
    if (blocksRemaining == 0)
    {
      throw new IllegalStateException("Attempt to process too many blocks");
    }
    blocksRemaining -= 1;
    
    int c = 1;
    c += (counter[15] & 0xFF);counter[15] = ((byte)c);c >>>= 8;
    c += (counter[14] & 0xFF);counter[14] = ((byte)c);c >>>= 8;
    c += (counter[13] & 0xFF);counter[13] = ((byte)c);c >>>= 8;
    c += (counter[12] & 0xFF);counter[12] = ((byte)c);
    
    byte[] tmp = new byte[16];
    
    cipher.processBlock(counter, 0, tmp, 0);
    return tmp;
  }
  
  private void checkStatus()
  {
    if (!initialised)
    {
      if (forEncryption)
      {
        throw new IllegalStateException("GCM cipher cannot be reused for encryption");
      }
      throw new IllegalStateException("GCM cipher needs to be initialised");
    }
  }
}
