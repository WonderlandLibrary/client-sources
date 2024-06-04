package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;




























public class RC6Engine
  implements BlockCipher
{
  private static final int wordSize = 32;
  private static final int bytesPerWord = 4;
  private static final int _noRounds = 20;
  private int[] _S;
  private static final int P32 = -1209970333;
  private static final int Q32 = -1640531527;
  private static final int LGW = 5;
  private boolean forEncryption;
  
  public RC6Engine()
  {
    _S = null;
  }
  
  public String getAlgorithmName()
  {
    return "RC6";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  










  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to RC6 init - " + params.getClass().getName());
    }
    
    KeyParameter p = (KeyParameter)params;
    this.forEncryption = forEncryption;
    setKey(p.getKey());
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int blockSize = getBlockSize();
    if (_S == null)
    {
      throw new IllegalStateException("RC6 engine not initialised");
    }
    if (inOff + blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    if (outOff + blockSize > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    return forEncryption ? 
      encryptBlock(in, inOff, out, outOff) : 
      decryptBlock(in, inOff, out, outOff);
  }
  











  public void reset() {}
  











  private void setKey(byte[] key)
  {
    int c = (key.length + 3) / 4;
    if (c == 0)
    {
      c = 1;
    }
    int[] L = new int[(key.length + 4 - 1) / 4];
    

    for (int i = key.length - 1; i >= 0; i--)
    {
      L[(i / 4)] = ((L[(i / 4)] << 8) + (key[i] & 0xFF));
    }
    







    _S = new int[44];
    
    _S[0] = -1209970333;
    for (int i = 1; i < _S.length; i++)
    {
      _S[i] = (_S[(i - 1)] + -1640531527);
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
    
    int A = 0;
    int B = 0;
    int i = 0;int j = 0;
    
    for (int k = 0; k < iter; k++)
    {
      A = _S[i] = rotateLeft(_S[i] + A + B, 3);
      B = L[j] = rotateLeft(L[j] + A + B, A + B);
      i = (i + 1) % _S.length;
      j = (j + 1) % L.length;
    }
  }
  





  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int A = bytesToWord(in, inOff);
    int B = bytesToWord(in, inOff + 4);
    int C = bytesToWord(in, inOff + 8);
    int D = bytesToWord(in, inOff + 12);
    

    B += _S[0];
    D += _S[1];
    

    for (int i = 1; i <= 20; i++)
    {
      int t = 0;int u = 0;
      
      t = B * (2 * B + 1);
      t = rotateLeft(t, 5);
      
      u = D * (2 * D + 1);
      u = rotateLeft(u, 5);
      
      A ^= t;
      A = rotateLeft(A, u);
      A += _S[(2 * i)];
      
      C ^= u;
      C = rotateLeft(C, t);
      C += _S[(2 * i + 1)];
      
      int temp = A;
      A = B;
      B = C;
      C = D;
      D = temp;
    }
    
    A += _S[42];
    C += _S[43];
    

    wordToBytes(A, out, outOff);
    wordToBytes(B, out, outOff + 4);
    wordToBytes(C, out, outOff + 8);
    wordToBytes(D, out, outOff + 12);
    
    return 16;
  }
  





  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int A = bytesToWord(in, inOff);
    int B = bytesToWord(in, inOff + 4);
    int C = bytesToWord(in, inOff + 8);
    int D = bytesToWord(in, inOff + 12);
    

    C -= _S[43];
    A -= _S[42];
    

    for (int i = 20; i >= 1; i--)
    {
      int t = 0;int u = 0;
      
      int temp = D;
      D = C;
      C = B;
      B = A;
      A = temp;
      
      t = B * (2 * B + 1);
      t = rotateLeft(t, 5);
      
      u = D * (2 * D + 1);
      u = rotateLeft(u, 5);
      
      C -= _S[(2 * i + 1)];
      C = rotateRight(C, t);
      C ^= u;
      
      A -= _S[(2 * i)];
      A = rotateRight(A, u);
      A ^= t;
    }
    

    D -= _S[1];
    B -= _S[0];
    
    wordToBytes(A, out, outOff);
    wordToBytes(B, out, outOff + 4);
    wordToBytes(C, out, outOff + 8);
    wordToBytes(D, out, outOff + 12);
    
    return 16;
  }
  

















  private int rotateLeft(int x, int y)
  {
    return x << y | x >>> -y;
  }
  










  private int rotateRight(int x, int y)
  {
    return x >>> y | x << -y;
  }
  


  private int bytesToWord(byte[] src, int srcOff)
  {
    int word = 0;
    
    for (int i = 3; i >= 0; i--)
    {
      word = (word << 8) + (src[(i + srcOff)] & 0xFF);
    }
    
    return word;
  }
  



  private void wordToBytes(int word, byte[] dst, int dstOff)
  {
    for (int i = 0; i < 4; i++)
    {
      dst[(i + dstOff)] = ((byte)word);
      word >>>= 8;
    }
  }
}
