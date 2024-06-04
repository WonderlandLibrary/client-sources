package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.RC5Parameters;

































public class RC564Engine
  implements BlockCipher
{
  private static final int wordSize = 64;
  private static final int bytesPerWord = 8;
  private int _noRounds;
  private long[] _S;
  private static final long P64 = -5196783011329398165L;
  private static final long Q64 = -7046029254386353131L;
  private boolean forEncryption;
  
  public RC564Engine()
  {
    _noRounds = 12;
    _S = null;
  }
  
  public String getAlgorithmName()
  {
    return "RC5-64";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  










  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof RC5Parameters))
    {
      throw new IllegalArgumentException("invalid parameter passed to RC564 init - " + params.getClass().getName());
    }
    
    RC5Parameters p = (RC5Parameters)params;
    
    this.forEncryption = forEncryption;
    
    _noRounds = p.getRounds();
    
    setKey(p.getKey());
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    return forEncryption ? encryptBlock(in, inOff, out, outOff) : 
      decryptBlock(in, inOff, out, outOff);
  }
  










  public void reset() {}
  










  private void setKey(byte[] key)
  {
    long[] L = new long[(key.length + 7) / 8];
    
    for (int i = 0; i != key.length; i++)
    {
      L[(i / 8)] += ((key[i] & 0xFF) << 8 * (i % 8));
    }
    






    _S = new long[2 * (_noRounds + 1)];
    
    _S[0] = -5196783011329398165L;
    for (int i = 1; i < _S.length; i++)
    {
      _S[i] = (_S[(i - 1)] + -7046029254386353131L);
    }
    

    int iter;
    

    int iter;
    

    if (L.length > _S.length)
    {
      iter = 3 * L.length;
    }
    else
    {
      iter = 3 * _S.length;
    }
    
    long A = 0L;long B = 0L;
    int i = 0;int j = 0;
    
    for (int k = 0; k < iter; k++)
    {
      A = _S[i] = rotateLeft(_S[i] + A + B, 3L);
      B = L[j] = rotateLeft(L[j] + A + B, A + B);
      i = (i + 1) % _S.length;
      j = (j + 1) % L.length;
    }
  }
  













  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    long A = bytesToWord(in, inOff) + _S[0];
    long B = bytesToWord(in, inOff + 8) + _S[1];
    
    for (int i = 1; i <= _noRounds; i++)
    {
      A = rotateLeft(A ^ B, B) + _S[(2 * i)];
      B = rotateLeft(B ^ A, A) + _S[(2 * i + 1)];
    }
    
    wordToBytes(A, out, outOff);
    wordToBytes(B, out, outOff + 8);
    
    return 16;
  }
  




  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    long A = bytesToWord(in, inOff);
    long B = bytesToWord(in, inOff + 8);
    
    for (int i = _noRounds; i >= 1; i--)
    {
      B = rotateRight(B - _S[(2 * i + 1)], A) ^ A;
      A = rotateRight(A - _S[(2 * i)], B) ^ B;
    }
    
    wordToBytes(A - _S[0], out, outOff);
    wordToBytes(B - _S[1], out, outOff + 8);
    
    return 16;
  }
  

















  private long rotateLeft(long x, long y)
  {
    return x << (int)(y & 0x3F) | x >>> (int)(64L - (y & 0x3F));
  }
  










  private long rotateRight(long x, long y)
  {
    return x >>> (int)(y & 0x3F) | x << (int)(64L - (y & 0x3F));
  }
  


  private long bytesToWord(byte[] src, int srcOff)
  {
    long word = 0L;
    
    for (int i = 7; i >= 0; i--)
    {
      word = (word << 8) + (src[(i + srcOff)] & 0xFF);
    }
    
    return word;
  }
  



  private void wordToBytes(long word, byte[] dst, int dstOff)
  {
    for (int i = 0; i < 8; i++)
    {
      dst[(i + dstOff)] = ((byte)(int)word);
      word >>>= 8;
    }
  }
}
