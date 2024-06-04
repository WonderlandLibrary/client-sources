package org.spongycastle.crypto.modes;

import java.util.Vector;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;




























public class OCBBlockCipher
  implements AEADBlockCipher
{
  private static final int BLOCK_SIZE = 16;
  private BlockCipher hashCipher;
  private BlockCipher mainCipher;
  private boolean forEncryption;
  private int macSize;
  private byte[] initialAssociatedText;
  private Vector L;
  private byte[] L_Asterisk;
  private byte[] L_Dollar;
  private byte[] KtopInput = null;
  private byte[] Stretch = new byte[24];
  private byte[] OffsetMAIN_0 = new byte[16];
  
  private byte[] hashBlock;
  private byte[] mainBlock;
  private int hashBlockPos;
  private int mainBlockPos;
  private long hashBlockCount;
  private long mainBlockCount;
  private byte[] OffsetHASH;
  private byte[] Sum;
  private byte[] OffsetMAIN = new byte[16];
  
  private byte[] Checksum;
  
  private byte[] macBlock;
  
  public OCBBlockCipher(BlockCipher hashCipher, BlockCipher mainCipher)
  {
    if (hashCipher == null)
    {
      throw new IllegalArgumentException("'hashCipher' cannot be null");
    }
    if (hashCipher.getBlockSize() != 16)
    {
      throw new IllegalArgumentException("'hashCipher' must have a block size of 16");
    }
    
    if (mainCipher == null)
    {
      throw new IllegalArgumentException("'mainCipher' cannot be null");
    }
    if (mainCipher.getBlockSize() != 16)
    {
      throw new IllegalArgumentException("'mainCipher' must have a block size of 16");
    }
    

    if (!hashCipher.getAlgorithmName().equals(mainCipher.getAlgorithmName()))
    {
      throw new IllegalArgumentException("'hashCipher' and 'mainCipher' must be the same algorithm");
    }
    

    this.hashCipher = hashCipher;
    this.mainCipher = mainCipher;
  }
  
  public BlockCipher getUnderlyingCipher()
  {
    return mainCipher;
  }
  
  public String getAlgorithmName()
  {
    return mainCipher.getAlgorithmName() + "/OCB";
  }
  
  public void init(boolean forEncryption, CipherParameters parameters)
    throws IllegalArgumentException
  {
    boolean oldForEncryption = this.forEncryption;
    this.forEncryption = forEncryption;
    macBlock = null;
    

    KeyParameter keyParameter;
    
    if ((parameters instanceof AEADParameters))
    {
      AEADParameters aeadParameters = (AEADParameters)parameters;
      
      byte[] N = aeadParameters.getNonce();
      initialAssociatedText = aeadParameters.getAssociatedText();
      
      int macSizeBits = aeadParameters.getMacSize();
      if ((macSizeBits < 64) || (macSizeBits > 128) || (macSizeBits % 8 != 0))
      {
        throw new IllegalArgumentException("Invalid value for MAC size: " + macSizeBits);
      }
      
      macSize = (macSizeBits / 8);
      keyParameter = aeadParameters.getKey();
    } else { KeyParameter keyParameter;
      if ((parameters instanceof ParametersWithIV))
      {
        ParametersWithIV parametersWithIV = (ParametersWithIV)parameters;
        
        byte[] N = parametersWithIV.getIV();
        initialAssociatedText = null;
        macSize = 16;
        keyParameter = (KeyParameter)parametersWithIV.getParameters();
      }
      else
      {
        throw new IllegalArgumentException("invalid parameters passed to OCB"); } }
    byte[] N;
    KeyParameter keyParameter;
    hashBlock = new byte[16];
    mainBlock = new byte[forEncryption ? 16 : 16 + macSize];
    
    if (N == null)
    {
      N = new byte[0];
    }
    
    if (N.length > 15)
    {
      throw new IllegalArgumentException("IV must be no more than 15 bytes");
    }
    




    if (keyParameter != null)
    {

      hashCipher.init(true, keyParameter);
      mainCipher.init(forEncryption, keyParameter);
      KtopInput = null;
    }
    else if (oldForEncryption != forEncryption)
    {
      throw new IllegalArgumentException("cannot change encrypting state without providing key.");
    }
    
    L_Asterisk = new byte[16];
    hashCipher.processBlock(L_Asterisk, 0, L_Asterisk, 0);
    
    L_Dollar = OCB_double(L_Asterisk);
    
    L = new Vector();
    L.addElement(OCB_double(L_Dollar));
    




    int bottom = processNonce(N);
    
    int bits = bottom % 8;int bytes = bottom / 8;
    if (bits == 0)
    {
      System.arraycopy(Stretch, bytes, OffsetMAIN_0, 0, 16);
    }
    else
    {
      for (int i = 0; i < 16; i++)
      {
        int b1 = Stretch[bytes] & 0xFF;
        int b2 = Stretch[(++bytes)] & 0xFF;
        OffsetMAIN_0[i] = ((byte)(b1 << bits | b2 >>> 8 - bits));
      }
    }
    
    hashBlockPos = 0;
    mainBlockPos = 0;
    
    hashBlockCount = 0L;
    mainBlockCount = 0L;
    
    OffsetHASH = new byte[16];
    Sum = new byte[16];
    System.arraycopy(OffsetMAIN_0, 0, OffsetMAIN, 0, 16);
    Checksum = new byte[16];
    
    if (initialAssociatedText != null)
    {
      processAADBytes(initialAssociatedText, 0, initialAssociatedText.length);
    }
  }
  
  protected int processNonce(byte[] N)
  {
    byte[] nonce = new byte[16];
    System.arraycopy(N, 0, nonce, nonce.length - N.length, N.length);
    nonce[0] = ((byte)(macSize << 4)); int 
      tmp34_33 = (15 - N.length); byte[] tmp34_28 = nonce;tmp34_28[tmp34_33] = ((byte)(tmp34_28[tmp34_33] | 0x1));
    
    int bottom = nonce[15] & 0x3F; byte[] 
      tmp51_48 = nonce;tmp51_48[15] = ((byte)(tmp51_48[15] & 0xC0));
    



    if ((KtopInput == null) || (!Arrays.areEqual(nonce, KtopInput)))
    {
      byte[] Ktop = new byte[16];
      KtopInput = nonce;
      hashCipher.processBlock(KtopInput, 0, Ktop, 0);
      System.arraycopy(Ktop, 0, Stretch, 0, 16);
      for (int i = 0; i < 8; i++)
      {
        Stretch[(16 + i)] = ((byte)(Ktop[i] ^ Ktop[(i + 1)]));
      }
    }
    
    return bottom;
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
    int totalData = len + mainBlockPos;
    if (forEncryption)
    {
      return totalData + macSize;
    }
    return totalData < macSize ? 0 : totalData - macSize;
  }
  
  public int getUpdateOutputSize(int len)
  {
    int totalData = len + mainBlockPos;
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
  
  public void processAADByte(byte input)
  {
    hashBlock[hashBlockPos] = input;
    if (++hashBlockPos == hashBlock.length)
    {
      processHashBlock();
    }
  }
  
  public void processAADBytes(byte[] input, int off, int len)
  {
    for (int i = 0; i < len; i++)
    {
      hashBlock[hashBlockPos] = input[(off + i)];
      if (++hashBlockPos == hashBlock.length)
      {
        processHashBlock();
      }
    }
  }
  
  public int processByte(byte input, byte[] output, int outOff)
    throws DataLengthException
  {
    mainBlock[mainBlockPos] = input;
    if (++mainBlockPos == mainBlock.length)
    {
      processMainBlock(output, outOff);
      return 16;
    }
    return 0;
  }
  
  public int processBytes(byte[] input, int inOff, int len, byte[] output, int outOff)
    throws DataLengthException
  {
    if (input.length < inOff + len)
    {
      throw new DataLengthException("Input buffer too short");
    }
    int resultLen = 0;
    
    for (int i = 0; i < len; i++)
    {
      mainBlock[mainBlockPos] = input[(inOff + i)];
      if (++mainBlockPos == mainBlock.length)
      {
        processMainBlock(output, outOff + resultLen);
        resultLen += 16;
      }
    }
    
    return resultLen;
  }
  




  public int doFinal(byte[] output, int outOff)
    throws IllegalStateException, InvalidCipherTextException
  {
    byte[] tag = null;
    if (!forEncryption)
    {
      if (mainBlockPos < macSize)
      {
        throw new InvalidCipherTextException("data too short");
      }
      mainBlockPos -= macSize;
      tag = new byte[macSize];
      System.arraycopy(mainBlock, mainBlockPos, tag, 0, macSize);
    }
    



    if (hashBlockPos > 0)
    {
      OCB_extend(hashBlock, hashBlockPos);
      updateHASH(L_Asterisk);
    }
    



    if (mainBlockPos > 0)
    {
      if (forEncryption)
      {
        OCB_extend(mainBlock, mainBlockPos);
        xor(Checksum, mainBlock);
      }
      
      xor(OffsetMAIN, L_Asterisk);
      
      byte[] Pad = new byte[16];
      hashCipher.processBlock(OffsetMAIN, 0, Pad, 0);
      
      xor(mainBlock, Pad);
      
      if (output.length < outOff + mainBlockPos)
      {
        throw new OutputLengthException("Output buffer too short");
      }
      System.arraycopy(mainBlock, 0, output, outOff, mainBlockPos);
      
      if (!forEncryption)
      {
        OCB_extend(mainBlock, mainBlockPos);
        xor(Checksum, mainBlock);
      }
    }
    



    xor(Checksum, OffsetMAIN);
    xor(Checksum, L_Dollar);
    hashCipher.processBlock(Checksum, 0, Checksum, 0);
    xor(Checksum, Sum);
    
    macBlock = new byte[macSize];
    System.arraycopy(Checksum, 0, macBlock, 0, macSize);
    



    int resultLen = mainBlockPos;
    
    if (forEncryption)
    {
      if (output.length < outOff + resultLen + macSize)
      {
        throw new OutputLengthException("Output buffer too short");
      }
      
      System.arraycopy(macBlock, 0, output, outOff + resultLen, macSize);
      resultLen += macSize;



    }
    else if (!Arrays.constantTimeAreEqual(macBlock, tag))
    {
      throw new InvalidCipherTextException("mac check in OCB failed");
    }
    

    reset(false);
    
    return resultLen;
  }
  
  public void reset()
  {
    reset(true);
  }
  
  protected void clear(byte[] bs)
  {
    if (bs != null)
    {
      Arrays.fill(bs, (byte)0);
    }
  }
  
  protected byte[] getLSub(int n)
  {
    while (n >= L.size())
    {
      L.addElement(OCB_double((byte[])L.lastElement()));
    }
    return (byte[])L.elementAt(n);
  }
  



  protected void processHashBlock()
  {
    updateHASH(getLSub(OCB_ntz(++hashBlockCount)));
    hashBlockPos = 0;
  }
  
  protected void processMainBlock(byte[] output, int outOff)
  {
    if (output.length < outOff + 16)
    {
      throw new OutputLengthException("Output buffer too short");
    }
    




    if (forEncryption)
    {
      xor(Checksum, mainBlock);
      mainBlockPos = 0;
    }
    
    xor(OffsetMAIN, getLSub(OCB_ntz(++mainBlockCount)));
    
    xor(mainBlock, OffsetMAIN);
    mainCipher.processBlock(mainBlock, 0, mainBlock, 0);
    xor(mainBlock, OffsetMAIN);
    
    System.arraycopy(mainBlock, 0, output, outOff, 16);
    
    if (!forEncryption)
    {
      xor(Checksum, mainBlock);
      System.arraycopy(mainBlock, 16, mainBlock, 0, macSize);
      mainBlockPos = macSize;
    }
  }
  
  protected void reset(boolean clearMac)
  {
    hashCipher.reset();
    mainCipher.reset();
    
    clear(hashBlock);
    clear(mainBlock);
    
    hashBlockPos = 0;
    mainBlockPos = 0;
    
    hashBlockCount = 0L;
    mainBlockCount = 0L;
    
    clear(OffsetHASH);
    clear(Sum);
    System.arraycopy(OffsetMAIN_0, 0, OffsetMAIN, 0, 16);
    clear(Checksum);
    
    if (clearMac)
    {
      macBlock = null;
    }
    
    if (initialAssociatedText != null)
    {
      processAADBytes(initialAssociatedText, 0, initialAssociatedText.length);
    }
  }
  
  protected void updateHASH(byte[] LSub)
  {
    xor(OffsetHASH, LSub);
    xor(hashBlock, OffsetHASH);
    hashCipher.processBlock(hashBlock, 0, hashBlock, 0);
    xor(Sum, hashBlock);
  }
  
  protected static byte[] OCB_double(byte[] block)
  {
    byte[] result = new byte[16];
    int carry = shiftLeft(block, result); byte[] 
    



      tmp14_11 = result;tmp14_11[15] = ((byte)(tmp14_11[15] ^ 135 >>> (1 - carry << 3)));
    
    return result;
  }
  
  protected static void OCB_extend(byte[] block, int pos)
  {
    block[pos] = Byte.MIN_VALUE;
    for (;;) { pos++; if (pos >= 16)
        break;
      block[pos] = 0;
    }
  }
  
  protected static int OCB_ntz(long x)
  {
    if (x == 0L)
    {
      return 64;
    }
    
    int n = 0;
    while ((x & 1L) == 0L)
    {
      n++;
      x >>>= 1;
    }
    return n;
  }
  
  protected static int shiftLeft(byte[] block, byte[] output)
  {
    int i = 16;
    int bit = 0;
    for (;;) { i--; if (i < 0)
        break;
      int b = block[i] & 0xFF;
      output[i] = ((byte)(b << 1 | bit));
      bit = b >>> 7 & 0x1;
    }
    return bit;
  }
  
  protected static void xor(byte[] block, byte[] val)
  {
    for (int i = 15; i >= 0; i--)
    {
      int tmp9_8 = i;block[tmp9_8] = ((byte)(block[tmp9_8] ^ val[i]));
    }
  }
}
