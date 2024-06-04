package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;






public class KeccakDigest
  implements ExtendedDigest
{
  private static long[] KeccakRoundConstants = ;
  
  private static int[] KeccakRhoOffsets = keccakInitializeRhoOffsets();
  
  private static long[] keccakInitializeRoundConstants()
  {
    long[] keccakRoundConstants = new long[24];
    byte[] LFSRstate = new byte[1];
    
    LFSRstate[0] = 1;
    

    for (int i = 0; i < 24; i++)
    {
      keccakRoundConstants[i] = 0L;
      for (int j = 0; j < 7; j++)
      {
        int bitPosition = (1 << j) - 1;
        if (LFSR86540(LFSRstate))
        {
          keccakRoundConstants[i] ^= 1L << bitPosition;
        }
      }
    }
    
    return keccakRoundConstants;
  }
  
  private static boolean LFSR86540(byte[] LFSR)
  {
    boolean result = (LFSR[0] & 0x1) != 0;
    if ((LFSR[0] & 0x80) != 0)
    {
      LFSR[0] = ((byte)(LFSR[0] << 1 ^ 0x71));
    }
    else
    {
      int tmp41_40 = 0;LFSR[tmp41_40] = ((byte)(LFSR[tmp41_40] << 1));
    }
    
    return result;
  }
  
  private static int[] keccakInitializeRhoOffsets()
  {
    int[] keccakRhoOffsets = new int[25];
    

    keccakRhoOffsets[0] = 0;
    int x = 1;
    int y = 0;
    for (int t = 0; t < 24; t++)
    {
      keccakRhoOffsets[(x % 5 + 5 * (y % 5))] = ((t + 1) * (t + 2) / 2 % 64);
      int newX = (0 * x + 1 * y) % 5;
      int newY = (2 * x + 3 * y) % 5;
      x = newX;
      y = newY;
    }
    return keccakRhoOffsets;
  }
  
  protected long[] state = new long[25];
  protected byte[] dataQueue = new byte['À'];
  protected int rate;
  protected int bitsInQueue;
  protected int fixedOutputLength;
  protected boolean squeezing;
  
  public KeccakDigest()
  {
    this(288);
  }
  
  public KeccakDigest(int bitLength)
  {
    init(bitLength);
  }
  
  public KeccakDigest(KeccakDigest source)
  {
    System.arraycopy(state, 0, state, 0, state.length);
    System.arraycopy(dataQueue, 0, dataQueue, 0, dataQueue.length);
    rate = rate;
    bitsInQueue = bitsInQueue;
    fixedOutputLength = fixedOutputLength;
    squeezing = squeezing;
  }
  
  public String getAlgorithmName()
  {
    return "Keccak-" + fixedOutputLength;
  }
  
  public int getDigestSize()
  {
    return fixedOutputLength / 8;
  }
  
  public void update(byte in)
  {
    absorb(new byte[] { in }, 0, 1);
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    absorb(in, inOff, len);
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    squeeze(out, outOff, fixedOutputLength);
    
    reset();
    
    return getDigestSize();
  }
  



  protected int doFinal(byte[] out, int outOff, byte partialByte, int partialBits)
  {
    if (partialBits > 0)
    {
      absorbBits(partialByte, partialBits);
    }
    
    squeeze(out, outOff, fixedOutputLength);
    
    reset();
    
    return getDigestSize();
  }
  
  public void reset()
  {
    init(fixedOutputLength);
  }
  





  public int getByteLength()
  {
    return rate / 8;
  }
  
  private void init(int bitLength)
  {
    switch (bitLength)
    {
    case 128: 
    case 224: 
    case 256: 
    case 288: 
    case 384: 
    case 512: 
      initSponge(1600 - (bitLength << 1));
      break;
    default: 
      throw new IllegalArgumentException("bitLength must be one of 128, 224, 256, 288, 384, or 512.");
    }
  }
  
  private void initSponge(int rate)
  {
    if ((rate <= 0) || (rate >= 1600) || (rate % 64 != 0))
    {
      throw new IllegalStateException("invalid rate value");
    }
    
    this.rate = rate;
    for (int i = 0; i < state.length; i++)
    {
      state[i] = 0L;
    }
    Arrays.fill(dataQueue, (byte)0);
    bitsInQueue = 0;
    squeezing = false;
    fixedOutputLength = ((1600 - rate) / 2);
  }
  
  protected void absorb(byte[] data, int off, int len)
  {
    if (bitsInQueue % 8 != 0)
    {
      throw new IllegalStateException("attempt to absorb with odd length queue");
    }
    if (squeezing)
    {
      throw new IllegalStateException("attempt to absorb while squeezing");
    }
    
    int bytesInQueue = bitsInQueue >> 3;
    int rateBytes = rate >> 3;
    
    int count = 0;
    while (count < len)
    {
      if ((bytesInQueue == 0) && (count <= len - rateBytes))
      {
        do
        {
          KeccakAbsorb(data, off + count);
          count += rateBytes;
        }
        while (count <= len - rateBytes);
      }
      else
      {
        int partialBlock = Math.min(rateBytes - bytesInQueue, len - count);
        System.arraycopy(data, off + count, dataQueue, bytesInQueue, partialBlock);
        
        bytesInQueue += partialBlock;
        count += partialBlock;
        
        if (bytesInQueue == rateBytes)
        {
          KeccakAbsorb(dataQueue, 0);
          bytesInQueue = 0;
        }
      }
    }
    
    bitsInQueue = (bytesInQueue << 3);
  }
  
  protected void absorbBits(int data, int bits)
  {
    if ((bits < 1) || (bits > 7))
    {
      throw new IllegalArgumentException("'bits' must be in the range 1 to 7");
    }
    if (bitsInQueue % 8 != 0)
    {
      throw new IllegalStateException("attempt to absorb with odd length queue");
    }
    if (squeezing)
    {
      throw new IllegalStateException("attempt to absorb while squeezing");
    }
    
    int mask = (1 << bits) - 1;
    dataQueue[(bitsInQueue >> 3)] = ((byte)(data & mask));
    

    bitsInQueue += bits;
  }
  
  private void padAndSwitchToSqueezingPhase()
  {
    int tmp10_9 = (bitsInQueue >> 3); byte[] tmp10_1 = dataQueue;tmp10_1[tmp10_9] = ((byte)(tmp10_1[tmp10_9] | (byte)(int)(1L << (bitsInQueue & 0x7))));
    
    if (++bitsInQueue == rate)
    {
      KeccakAbsorb(dataQueue, 0);
      bitsInQueue = 0;
    }
    

    int full = bitsInQueue >> 6;int partial = bitsInQueue & 0x3F;
    int off = 0;
    for (int i = 0; i < full; i++)
    {
      state[i] ^= Pack.littleEndianToLong(dataQueue, off);
      off += 8;
    }
    if (partial > 0)
    {
      long mask = (1L << partial) - 1L;
      state[full] ^= Pack.littleEndianToLong(dataQueue, off) & mask;
    }
    state[(rate - 1 >> 6)] ^= 0x8000000000000000;
    

    KeccakPermutation();
    

    KeccakExtract();
    bitsInQueue = rate;
    

    squeezing = true;
  }
  
  protected void squeeze(byte[] output, int offset, long outputLength)
  {
    if (!squeezing)
    {
      padAndSwitchToSqueezingPhase();
    }
    if (outputLength % 8L != 0L)
    {
      throw new IllegalStateException("outputLength not a multiple of 8");
    }
    
    long i = 0L;
    while (i < outputLength)
    {
      if (bitsInQueue == 0)
      {
        KeccakPermutation();
        KeccakExtract();
        bitsInQueue = rate;
      }
      
      int partialBlock = (int)Math.min(bitsInQueue, outputLength - i);
      System.arraycopy(dataQueue, (rate - bitsInQueue) / 8, output, offset + (int)(i / 8L), partialBlock / 8);
      bitsInQueue -= partialBlock;
      i += partialBlock;
    }
  }
  
  private void KeccakAbsorb(byte[] data, int off)
  {
    int count = rate >> 6;
    for (int i = 0; i < count; i++)
    {
      state[i] ^= Pack.littleEndianToLong(data, off);
      off += 8;
    }
    
    KeccakPermutation();
  }
  
  private void KeccakExtract()
  {
    Pack.longToLittleEndian(state, 0, rate >> 6, dataQueue, 0);
  }
  


  private void KeccakPermutation()
  {
    for (int i = 0; i < 24; i++)
    {


      theta(state);
      

      rho(state);
      

      pi(state);
      

      chi(state);
      

      iota(state, i);
    }
  }
  

  private static long leftRotate(long v, int r)
  {
    return v << r | v >>> -r;
  }
  
  private static void theta(long[] A)
  {
    long C0 = A[0] ^ A[5] ^ A[10] ^ A[15] ^ A[20];
    long C1 = A[1] ^ A[6] ^ A[11] ^ A[16] ^ A[21];
    long C2 = A[2] ^ A[7] ^ A[12] ^ A[17] ^ A[22];
    long C3 = A[3] ^ A[8] ^ A[13] ^ A[18] ^ A[23];
    long C4 = A[4] ^ A[9] ^ A[14] ^ A[19] ^ A[24];
    
    long dX = leftRotate(C1, 1) ^ C4;
    
    A[0] ^= dX;
    A[5] ^= dX;
    A[10] ^= dX;
    A[15] ^= dX;
    A[20] ^= dX;
    
    dX = leftRotate(C2, 1) ^ C0;
    
    A[1] ^= dX;
    A[6] ^= dX;
    A[11] ^= dX;
    A[16] ^= dX;
    A[21] ^= dX;
    
    dX = leftRotate(C3, 1) ^ C1;
    
    A[2] ^= dX;
    A[7] ^= dX;
    A[12] ^= dX;
    A[17] ^= dX;
    A[22] ^= dX;
    
    dX = leftRotate(C4, 1) ^ C2;
    
    A[3] ^= dX;
    A[8] ^= dX;
    A[13] ^= dX;
    A[18] ^= dX;
    A[23] ^= dX;
    
    dX = leftRotate(C0, 1) ^ C3;
    
    A[4] ^= dX;
    A[9] ^= dX;
    A[14] ^= dX;
    A[19] ^= dX;
    A[24] ^= dX;
  }
  

  private static void rho(long[] A)
  {
    for (int x = 1; x < 25; x++)
    {
      A[x] = leftRotate(A[x], KeccakRhoOffsets[x]);
    }
  }
  
  private static void pi(long[] A)
  {
    long a1 = A[1];
    A[1] = A[6];
    A[6] = A[9];
    A[9] = A[22];
    A[22] = A[14];
    A[14] = A[20];
    A[20] = A[2];
    A[2] = A[12];
    A[12] = A[13];
    A[13] = A[19];
    A[19] = A[23];
    A[23] = A[15];
    A[15] = A[4];
    A[4] = A[24];
    A[24] = A[21];
    A[21] = A[8];
    A[8] = A[16];
    A[16] = A[5];
    A[5] = A[3];
    A[3] = A[18];
    A[18] = A[17];
    A[17] = A[11];
    A[11] = A[7];
    A[7] = A[10];
    A[10] = a1;
  }
  


  private static void chi(long[] A)
  {
    for (int yBy5 = 0; yBy5 < 25; yBy5 += 5)
    {
      long chiC0 = A[(0 + yBy5)] ^ (A[(1 + yBy5)] ^ 0xFFFFFFFFFFFFFFFF) & A[(2 + yBy5)];
      long chiC1 = A[(1 + yBy5)] ^ (A[(2 + yBy5)] ^ 0xFFFFFFFFFFFFFFFF) & A[(3 + yBy5)];
      long chiC2 = A[(2 + yBy5)] ^ (A[(3 + yBy5)] ^ 0xFFFFFFFFFFFFFFFF) & A[(4 + yBy5)];
      long chiC3 = A[(3 + yBy5)] ^ (A[(4 + yBy5)] ^ 0xFFFFFFFFFFFFFFFF) & A[(0 + yBy5)];
      long chiC4 = A[(4 + yBy5)] ^ (A[(0 + yBy5)] ^ 0xFFFFFFFFFFFFFFFF) & A[(1 + yBy5)];
      
      A[(0 + yBy5)] = chiC0;
      A[(1 + yBy5)] = chiC1;
      A[(2 + yBy5)] = chiC2;
      A[(3 + yBy5)] = chiC3;
      A[(4 + yBy5)] = chiC4;
    }
  }
  
  private static void iota(long[] A, int indexRound)
  {
    A[0] ^= KeccakRoundConstants[indexRound];
  }
}
