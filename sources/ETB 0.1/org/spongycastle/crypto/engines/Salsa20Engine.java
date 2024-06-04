package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.MaxBytesExceededException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.SkippingStreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;
import org.spongycastle.util.Strings;







public class Salsa20Engine
  implements SkippingStreamCipher
{
  public static final int DEFAULT_ROUNDS = 20;
  private static final int STATE_SIZE = 16;
  private static final int[] TAU_SIGMA = Pack.littleEndianToInt(Strings.toByteArray("expand 16-byte kexpand 32-byte k"), 0, 8);
  
  protected void packTauOrSigma(int keyLength, int[] state, int stateOffset)
  {
    int tsOff = (keyLength - 16) / 4;
    state[stateOffset] = TAU_SIGMA[tsOff];
    state[(stateOffset + 1)] = TAU_SIGMA[(tsOff + 1)];
    state[(stateOffset + 2)] = TAU_SIGMA[(tsOff + 2)];
    state[(stateOffset + 3)] = TAU_SIGMA[(tsOff + 3)]; }
  
  /**
   * @deprecated
   */
  protected static final byte[] sigma = Strings.toByteArray("expand 32-byte k"); /**
   * @deprecated
   */
  protected static final byte[] tau = Strings.toByteArray("expand 16-byte k");
  


  protected int rounds;
  


  private int index = 0;
  protected int[] engineState = new int[16];
  protected int[] x = new int[16];
  private byte[] keyStream = new byte[64];
  private boolean initialised = false;
  

  private int cW0;
  
  private int cW1;
  
  private int cW2;
  

  public Salsa20Engine()
  {
    this(20);
  }
  




  public Salsa20Engine(int rounds)
  {
    if ((rounds <= 0) || ((rounds & 0x1) != 0))
    {
      throw new IllegalArgumentException("'rounds' must be a positive, even number");
    }
    
    this.rounds = rounds;
  }
  
















  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof ParametersWithIV))
    {
      throw new IllegalArgumentException(getAlgorithmName() + " Init parameters must include an IV");
    }
    
    ParametersWithIV ivParams = (ParametersWithIV)params;
    
    byte[] iv = ivParams.getIV();
    if ((iv == null) || (iv.length != getNonceSize()))
    {
      throw new IllegalArgumentException(getAlgorithmName() + " requires exactly " + getNonceSize() + " bytes of IV");
    }
    

    CipherParameters keyParam = ivParams.getParameters();
    if (keyParam == null)
    {
      if (!initialised)
      {
        throw new IllegalStateException(getAlgorithmName() + " KeyParameter can not be null for first initialisation");
      }
      
      setKey(null, iv);
    }
    else if ((keyParam instanceof KeyParameter))
    {
      setKey(((KeyParameter)keyParam).getKey(), iv);
    }
    else
    {
      throw new IllegalArgumentException(getAlgorithmName() + " Init parameters must contain a KeyParameter (or null for re-init)");
    }
    
    reset();
    
    initialised = true;
  }
  
  protected int getNonceSize()
  {
    return 8;
  }
  
  public String getAlgorithmName()
  {
    String name = "Salsa20";
    if (rounds != 20)
    {
      name = name + "/" + rounds;
    }
    return name;
  }
  
  public byte returnByte(byte in)
  {
    if (limitExceeded())
    {
      throw new MaxBytesExceededException("2^70 byte limit per IV; Change IV");
    }
    
    byte out = (byte)(keyStream[index] ^ in);
    index = (index + 1 & 0x3F);
    
    if (index == 0)
    {
      advanceCounter();
      generateKeyStream(keyStream);
    }
    
    return out;
  }
  
  protected void advanceCounter(long diff)
  {
    int hi = (int)(diff >>> 32);
    int lo = (int)diff;
    
    if (hi > 0)
    {
      engineState[9] += hi;
    }
    
    int oldState = engineState[8];
    
    engineState[8] += lo;
    
    if ((oldState != 0) && (engineState[8] < oldState))
    {
      engineState[9] += 1;
    }
  }
  
  protected void advanceCounter()
  {
    if (engineState[8] += 1 == 0)
    {
      engineState[9] += 1;
    }
  }
  
  protected void retreatCounter(long diff)
  {
    int hi = (int)(diff >>> 32);
    int lo = (int)diff;
    
    if (hi != 0)
    {
      if ((engineState[9] & 0xFFFFFFFF) >= (hi & 0xFFFFFFFF))
      {
        engineState[9] -= hi;
      }
      else
      {
        throw new IllegalStateException("attempt to reduce counter past zero.");
      }
    }
    
    if ((engineState[8] & 0xFFFFFFFF) >= (lo & 0xFFFFFFFF))
    {
      engineState[8] -= lo;


    }
    else if (engineState[9] != 0)
    {
      engineState[9] -= 1;
      engineState[8] -= lo;
    }
    else
    {
      throw new IllegalStateException("attempt to reduce counter past zero.");
    }
  }
  

  protected void retreatCounter()
  {
    if ((engineState[8] == 0) && (engineState[9] == 0))
    {
      throw new IllegalStateException("attempt to reduce counter past zero.");
    }
    
    if (engineState[8] -= 1 == -1)
    {
      engineState[9] -= 1;
    }
  }
  





  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
  {
    if (!initialised)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
    if (inOff + len > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + len > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    if (limitExceeded(len))
    {
      throw new MaxBytesExceededException("2^70 byte limit per IV would be exceeded; Change IV");
    }
    
    for (int i = 0; i < len; i++)
    {
      out[(i + outOff)] = ((byte)(keyStream[index] ^ in[(i + inOff)]));
      index = (index + 1 & 0x3F);
      
      if (index == 0)
      {
        advanceCounter();
        generateKeyStream(keyStream);
      }
    }
    
    return len;
  }
  
  public long skip(long numberOfBytes)
  {
    if (numberOfBytes >= 0L)
    {
      long remaining = numberOfBytes;
      
      if (remaining >= 64L)
      {
        long count = remaining / 64L;
        
        advanceCounter(count);
        
        remaining -= count * 64L;
      }
      
      int oldIndex = index;
      
      index = (index + (int)remaining & 0x3F);
      
      if (index < oldIndex)
      {
        advanceCounter();
      }
    }
    else
    {
      long remaining = -numberOfBytes;
      
      if (remaining >= 64L)
      {
        long count = remaining / 64L;
        
        retreatCounter(count);
        
        remaining -= count * 64L;
      }
      
      for (long i = 0L; i < remaining; i += 1L)
      {
        if (index == 0)
        {
          retreatCounter();
        }
        
        index = (index - 1 & 0x3F);
      }
    }
    
    generateKeyStream(keyStream);
    
    return numberOfBytes;
  }
  
  public long seekTo(long position)
  {
    reset();
    
    return skip(position);
  }
  
  public long getPosition()
  {
    return getCounter() * 64L + index;
  }
  
  public void reset()
  {
    index = 0;
    resetLimitCounter();
    resetCounter();
    
    generateKeyStream(keyStream);
  }
  
  protected long getCounter()
  {
    return engineState[9] << 32 | engineState[8] & 0xFFFFFFFF;
  }
  
  protected void resetCounter()
  {
    int tmp13_12 = 0;engineState[9] = tmp13_12;engineState[8] = tmp13_12;
  }
  
  protected void setKey(byte[] keyBytes, byte[] ivBytes)
  {
    if (keyBytes != null)
    {
      if ((keyBytes.length != 16) && (keyBytes.length != 32))
      {
        throw new IllegalArgumentException(getAlgorithmName() + " requires 128 bit or 256 bit key");
      }
      
      int tsOff = (keyBytes.length - 16) / 4;
      engineState[0] = TAU_SIGMA[tsOff];
      engineState[5] = TAU_SIGMA[(tsOff + 1)];
      engineState[10] = TAU_SIGMA[(tsOff + 2)];
      engineState[15] = TAU_SIGMA[(tsOff + 3)];
      

      Pack.littleEndianToInt(keyBytes, 0, engineState, 1, 4);
      Pack.littleEndianToInt(keyBytes, keyBytes.length - 16, engineState, 11, 4);
    }
    

    Pack.littleEndianToInt(ivBytes, 0, engineState, 6, 2);
  }
  
  protected void generateKeyStream(byte[] output)
  {
    salsaCore(rounds, engineState, x);
    Pack.intToLittleEndian(x, output, 0);
  }
  





  public static void salsaCore(int rounds, int[] input, int[] x)
  {
    if (input.length != 16)
    {
      throw new IllegalArgumentException();
    }
    if (x.length != 16)
    {
      throw new IllegalArgumentException();
    }
    if (rounds % 2 != 0)
    {
      throw new IllegalArgumentException("Number of rounds must be even");
    }
    
    int x00 = input[0];
    int x01 = input[1];
    int x02 = input[2];
    int x03 = input[3];
    int x04 = input[4];
    int x05 = input[5];
    int x06 = input[6];
    int x07 = input[7];
    int x08 = input[8];
    int x09 = input[9];
    int x10 = input[10];
    int x11 = input[11];
    int x12 = input[12];
    int x13 = input[13];
    int x14 = input[14];
    int x15 = input[15];
    
    for (int i = rounds; i > 0; i -= 2)
    {
      x04 ^= rotl(x00 + x12, 7);
      x08 ^= rotl(x04 + x00, 9);
      x12 ^= rotl(x08 + x04, 13);
      x00 ^= rotl(x12 + x08, 18);
      x09 ^= rotl(x05 + x01, 7);
      x13 ^= rotl(x09 + x05, 9);
      x01 ^= rotl(x13 + x09, 13);
      x05 ^= rotl(x01 + x13, 18);
      x14 ^= rotl(x10 + x06, 7);
      x02 ^= rotl(x14 + x10, 9);
      x06 ^= rotl(x02 + x14, 13);
      x10 ^= rotl(x06 + x02, 18);
      x03 ^= rotl(x15 + x11, 7);
      x07 ^= rotl(x03 + x15, 9);
      x11 ^= rotl(x07 + x03, 13);
      x15 ^= rotl(x11 + x07, 18);
      
      x01 ^= rotl(x00 + x03, 7);
      x02 ^= rotl(x01 + x00, 9);
      x03 ^= rotl(x02 + x01, 13);
      x00 ^= rotl(x03 + x02, 18);
      x06 ^= rotl(x05 + x04, 7);
      x07 ^= rotl(x06 + x05, 9);
      x04 ^= rotl(x07 + x06, 13);
      x05 ^= rotl(x04 + x07, 18);
      x11 ^= rotl(x10 + x09, 7);
      x08 ^= rotl(x11 + x10, 9);
      x09 ^= rotl(x08 + x11, 13);
      x10 ^= rotl(x09 + x08, 18);
      x12 ^= rotl(x15 + x14, 7);
      x13 ^= rotl(x12 + x15, 9);
      x14 ^= rotl(x13 + x12, 13);
      x15 ^= rotl(x14 + x13, 18);
    }
    
    x[0] = (x00 + input[0]);
    x[1] = (x01 + input[1]);
    x[2] = (x02 + input[2]);
    x[3] = (x03 + input[3]);
    x[4] = (x04 + input[4]);
    x[5] = (x05 + input[5]);
    x[6] = (x06 + input[6]);
    x[7] = (x07 + input[7]);
    x[8] = (x08 + input[8]);
    x[9] = (x09 + input[9]);
    x[10] = (x10 + input[10]);
    x[11] = (x11 + input[11]);
    x[12] = (x12 + input[12]);
    x[13] = (x13 + input[13]);
    x[14] = (x14 + input[14]);
    x[15] = (x15 + input[15]);
  }
  








  protected static int rotl(int x, int y)
  {
    return x << y | x >>> -y;
  }
  
  private void resetLimitCounter()
  {
    cW0 = 0;
    cW1 = 0;
    cW2 = 0;
  }
  
  private boolean limitExceeded()
  {
    if (++cW0 == 0)
    {
      if (++cW1 == 0)
      {
        return (++cW2 & 0x20) != 0;
      }
    }
    
    return false;
  }
  



  private boolean limitExceeded(int len)
  {
    cW0 += len;
    if ((cW0 < len) && (cW0 >= 0))
    {
      if (++cW1 == 0)
      {
        return (++cW2 & 0x20) != 0;
      }
    }
    
    return false;
  }
}
