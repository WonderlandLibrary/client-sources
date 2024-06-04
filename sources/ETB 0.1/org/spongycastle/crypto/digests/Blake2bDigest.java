package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;






































public class Blake2bDigest
  implements ExtendedDigest
{
  private static final long[] blake2b_IV = { 7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L };
  








  private static final byte[][] blake2b_sigma = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }, { 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 }, { 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4 }, { 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 }, { 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13 }, { 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 }, { 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11 }, { 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10 }, { 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5 }, { 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0 }, { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }, { 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 } };
  














  private static int rOUNDS = 12;
  
  private static final int BLOCK_LENGTH_BYTES = 128;
  
  private int digestLength = 64;
  private int keyLength = 0;
  private byte[] salt = null;
  private byte[] personalization = null;
  

  private byte[] key = null;
  












  private byte[] buffer = null;
  
  private int bufferPos = 0;
  
  private long[] internalState = new long[16];
  
  private long[] chainValue = null;
  

  private long t0 = 0L;
  private long t1 = 0L;
  private long f0 = 0L;
  



  public Blake2bDigest()
  {
    this(512);
  }
  
  public Blake2bDigest(Blake2bDigest digest)
  {
    bufferPos = bufferPos;
    buffer = Arrays.clone(buffer);
    keyLength = keyLength;
    key = Arrays.clone(key);
    digestLength = digestLength;
    chainValue = Arrays.clone(chainValue);
    personalization = Arrays.clone(personalization);
    salt = Arrays.clone(salt);
    t0 = t0;
    t1 = t1;
    f0 = f0;
  }
  





  public Blake2bDigest(int digestSize)
  {
    if ((digestSize != 160) && (digestSize != 256) && (digestSize != 384) && (digestSize != 512))
    {
      throw new IllegalArgumentException("Blake2b digest restricted to one of [160, 256, 384, 512]");
    }
    
    buffer = new byte[''];
    keyLength = 0;
    digestLength = (digestSize / 8);
    init();
  }
  









  public Blake2bDigest(byte[] key)
  {
    buffer = new byte[''];
    if (key != null)
    {
      this.key = new byte[key.length];
      System.arraycopy(key, 0, this.key, 0, key.length);
      
      if (key.length > 64)
      {
        throw new IllegalArgumentException("Keys > 64 are not supported");
      }
      
      keyLength = key.length;
      System.arraycopy(key, 0, buffer, 0, key.length);
      bufferPos = 128;
    }
    digestLength = 64;
    init();
  }
  













  public Blake2bDigest(byte[] key, int digestLength, byte[] salt, byte[] personalization)
  {
    buffer = new byte[''];
    if ((digestLength < 1) || (digestLength > 64))
    {
      throw new IllegalArgumentException("Invalid digest length (required: 1 - 64)");
    }
    
    this.digestLength = digestLength;
    if (salt != null)
    {
      if (salt.length != 16)
      {
        throw new IllegalArgumentException("salt length must be exactly 16 bytes");
      }
      
      this.salt = new byte[16];
      System.arraycopy(salt, 0, this.salt, 0, salt.length);
    }
    if (personalization != null)
    {
      if (personalization.length != 16)
      {
        throw new IllegalArgumentException("personalization length must be exactly 16 bytes");
      }
      
      this.personalization = new byte[16];
      System.arraycopy(personalization, 0, this.personalization, 0, personalization.length);
    }
    
    if (key != null)
    {
      this.key = new byte[key.length];
      System.arraycopy(key, 0, this.key, 0, key.length);
      
      if (key.length > 64)
      {
        throw new IllegalArgumentException("Keys > 64 are not supported");
      }
      
      keyLength = key.length;
      System.arraycopy(key, 0, buffer, 0, key.length);
      bufferPos = 128;
    }
    init();
  }
  

  private void init()
  {
    if (chainValue == null)
    {
      chainValue = new long[8];
      
      chainValue[0] = (blake2b_IV[0] ^ (digestLength | keyLength << 8 | 0x1010000));
      



      chainValue[1] = blake2b_IV[1];
      chainValue[2] = blake2b_IV[2];
      


      chainValue[3] = blake2b_IV[3];
      
      chainValue[4] = blake2b_IV[4];
      chainValue[5] = blake2b_IV[5];
      if (salt != null)
      {
        chainValue[4] ^= bytes2long(salt, 0);
        chainValue[5] ^= bytes2long(salt, 8);
      }
      
      chainValue[6] = blake2b_IV[6];
      chainValue[7] = blake2b_IV[7];
      if (personalization != null)
      {
        chainValue[6] ^= bytes2long(personalization, 0);
        chainValue[7] ^= bytes2long(personalization, 8);
      }
    }
  }
  

  private void initializeInternalState()
  {
    System.arraycopy(chainValue, 0, internalState, 0, chainValue.length);
    System.arraycopy(blake2b_IV, 0, internalState, chainValue.length, 4);
    internalState[12] = (t0 ^ blake2b_IV[4]);
    internalState[13] = (t1 ^ blake2b_IV[5]);
    internalState[14] = (f0 ^ blake2b_IV[6]);
    internalState[15] = blake2b_IV[7];
  }
  





  public void update(byte b)
  {
    int remainingLength = 0;
    

    remainingLength = 128 - bufferPos;
    if (remainingLength == 0)
    {
      t0 += 128L;
      if (t0 == 0L)
      {
        t1 += 1L;
      }
      compress(buffer, 0);
      Arrays.fill(buffer, (byte)0);
      buffer[0] = b;
      bufferPos = 1;
    }
    else
    {
      buffer[bufferPos] = b;
      bufferPos += 1;
      return;
    }
  }
  








  public void update(byte[] message, int offset, int len)
  {
    if ((message == null) || (len == 0))
    {
      return;
    }
    
    int remainingLength = 0;
    
    if (bufferPos != 0)
    {


      remainingLength = 128 - bufferPos;
      if (remainingLength < len)
      {
        System.arraycopy(message, offset, buffer, bufferPos, remainingLength);
        
        t0 += 128L;
        if (t0 == 0L)
        {
          t1 += 1L;
        }
        compress(buffer, 0);
        bufferPos = 0;
        Arrays.fill(buffer, (byte)0);
      }
      else
      {
        System.arraycopy(message, offset, buffer, bufferPos, len);
        bufferPos += len;
        return;
      }
    }
    


    int blockWiseLastPos = offset + len - 128;
    for (int messagePos = offset + remainingLength; messagePos < blockWiseLastPos; messagePos += 128)
    {

      t0 += 128L;
      if (t0 == 0L)
      {
        t1 += 1L;
      }
      compress(message, messagePos);
    }
    

    System.arraycopy(message, messagePos, buffer, 0, offset + len - messagePos);
    
    bufferPos += offset + len - messagePos;
  }
  









  public int doFinal(byte[] out, int outOffset)
  {
    f0 = -1L;
    t0 += bufferPos;
    

    if ((t0 < 0L) && (bufferPos > -t0))
    {
      t1 += 1L;
    }
    compress(buffer, 0);
    Arrays.fill(buffer, (byte)0);
    Arrays.fill(internalState, 0L);
    
    for (int i = 0; (i < chainValue.length) && (i * 8 < digestLength); i++)
    {
      byte[] bytes = long2bytes(chainValue[i]);
      
      if (i * 8 < digestLength - 8)
      {
        System.arraycopy(bytes, 0, out, outOffset + i * 8, 8);
      }
      else
      {
        System.arraycopy(bytes, 0, out, outOffset + i * 8, digestLength - i * 8);
      }
    }
    
    Arrays.fill(chainValue, 0L);
    
    reset();
    
    return digestLength;
  }
  





  public void reset()
  {
    bufferPos = 0;
    f0 = 0L;
    t0 = 0L;
    t1 = 0L;
    chainValue = null;
    Arrays.fill(buffer, (byte)0);
    if (key != null)
    {
      System.arraycopy(key, 0, buffer, 0, key.length);
      bufferPos = 128;
    }
    init();
  }
  

  private void compress(byte[] message, int messagePos)
  {
    initializeInternalState();
    
    long[] m = new long[16];
    for (int j = 0; j < 16; j++)
    {
      m[j] = bytes2long(message, messagePos + j * 8);
    }
    
    for (int round = 0; round < rOUNDS; round++)
    {



      G(m[blake2b_sigma[round][0]], m[blake2b_sigma[round][1]], 0, 4, 8, 12);
      
      G(m[blake2b_sigma[round][2]], m[blake2b_sigma[round][3]], 1, 5, 9, 13);
      
      G(m[blake2b_sigma[round][4]], m[blake2b_sigma[round][5]], 2, 6, 10, 14);
      
      G(m[blake2b_sigma[round][6]], m[blake2b_sigma[round][7]], 3, 7, 11, 15);
      

      G(m[blake2b_sigma[round][8]], m[blake2b_sigma[round][9]], 0, 5, 10, 15);
      
      G(m[blake2b_sigma[round][10]], m[blake2b_sigma[round][11]], 1, 6, 11, 12);
      
      G(m[blake2b_sigma[round][12]], m[blake2b_sigma[round][13]], 2, 7, 8, 13);
      
      G(m[blake2b_sigma[round][14]], m[blake2b_sigma[round][15]], 3, 4, 9, 14);
    }
    


    for (int offset = 0; offset < chainValue.length; offset++)
    {
      chainValue[offset] = (chainValue[offset] ^ internalState[offset] ^ internalState[(offset + 8)]);
    }
  }
  


  private void G(long m1, long m2, int posA, int posB, int posC, int posD)
  {
    internalState[posA] = (internalState[posA] + internalState[posB] + m1);
    internalState[posD] = rotr64(internalState[posD] ^ internalState[posA], 32);
    
    internalState[posC] += internalState[posD];
    internalState[posB] = rotr64(internalState[posB] ^ internalState[posC], 24);
    
    internalState[posA] = (internalState[posA] + internalState[posB] + m2);
    internalState[posD] = rotr64(internalState[posD] ^ internalState[posA], 16);
    
    internalState[posC] += internalState[posD];
    internalState[posB] = rotr64(internalState[posB] ^ internalState[posC], 63);
  }
  

  private long rotr64(long x, int rot)
  {
    return x >>> rot | x << 64 - rot;
  }
  


  private final byte[] long2bytes(long longValue)
  {
    return new byte[] { (byte)(int)longValue, (byte)(int)(longValue >> 8), (byte)(int)(longValue >> 16), (byte)(int)(longValue >> 24), (byte)(int)(longValue >> 32), (byte)(int)(longValue >> 40), (byte)(int)(longValue >> 48), (byte)(int)(longValue >> 56) };
  }
  






  private final long bytes2long(byte[] byteArray, int offset)
  {
    return byteArray[offset] & 0xFF | (byteArray[(offset + 1)] & 0xFF) << 8 | (byteArray[(offset + 2)] & 0xFF) << 16 | (byteArray[(offset + 3)] & 0xFF) << 24 | (byteArray[(offset + 4)] & 0xFF) << 32 | (byteArray[(offset + 5)] & 0xFF) << 40 | (byteArray[(offset + 6)] & 0xFF) << 48 | (byteArray[(offset + 7)] & 0xFF) << 56;
  }
  












  public String getAlgorithmName()
  {
    return "Blake2b";
  }
  





  public int getDigestSize()
  {
    return digestLength;
  }
  






  public int getByteLength()
  {
    return 128;
  }
  




  public void clearKey()
  {
    if (key != null)
    {
      Arrays.fill(key, (byte)0);
      Arrays.fill(buffer, (byte)0);
    }
  }
  




  public void clearSalt()
  {
    if (salt != null)
    {
      Arrays.fill(salt, (byte)0);
    }
  }
}
