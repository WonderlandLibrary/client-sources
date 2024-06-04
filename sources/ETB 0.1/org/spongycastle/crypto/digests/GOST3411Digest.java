package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;






public class GOST3411Digest
  implements ExtendedDigest, Memoable
{
  private byte[] H = new byte[32]; private byte[] L = new byte[32]; private byte[] M = new byte[32]; private byte[] Sum = new byte[32];
  
  private byte[][] C = new byte[4][32];
  
  private byte[] xBuf = new byte[32];
  


  private BlockCipher cipher = new GOST28147Engine();
  




  public GOST3411Digest()
  {
    sBox = GOST28147Engine.getSBox("D-A");
    cipher.init(true, new ParametersWithSBox(null, sBox));
    
    reset();
  }
  




  public GOST3411Digest(byte[] sBoxParam)
  {
    sBox = Arrays.clone(sBoxParam);
    cipher.init(true, new ParametersWithSBox(null, sBox));
    
    reset();
  }
  




  public GOST3411Digest(GOST3411Digest t)
  {
    reset(t);
  }
  
  public String getAlgorithmName()
  {
    return "GOST3411";
  }
  
  public int getDigestSize()
  {
    return 32;
  }
  
  public void update(byte in)
  {
    xBuf[(xBufOff++)] = in;
    if (xBufOff == xBuf.length)
    {
      sumByteArray(xBuf);
      processBlock(xBuf, 0);
      xBufOff = 0;
    }
    byteCount += 1L;
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    while ((xBufOff != 0) && (len > 0))
    {
      update(in[inOff]);
      inOff++;
      len--;
    }
    
    while (len > xBuf.length)
    {
      System.arraycopy(in, inOff, xBuf, 0, xBuf.length);
      
      sumByteArray(xBuf);
      processBlock(xBuf, 0);
      inOff += xBuf.length;
      len -= xBuf.length;
      byteCount += xBuf.length;
    }
    

    while (len > 0)
    {
      update(in[inOff]);
      inOff++;
      len--;
    }
  }
  

  private byte[] K = new byte[32];
  
  private byte[] P(byte[] in)
  {
    for (int k = 0; k < 8; k++)
    {
      K[(4 * k)] = in[k];
      K[(1 + 4 * k)] = in[(8 + k)];
      K[(2 + 4 * k)] = in[(16 + k)];
      K[(3 + 4 * k)] = in[(24 + k)];
    }
    
    return K;
  }
  

  byte[] a = new byte[8];
  
  private byte[] A(byte[] in) {
    for (int j = 0; j < 8; j++)
    {
      a[j] = ((byte)(in[j] ^ in[(j + 8)]));
    }
    
    System.arraycopy(in, 8, in, 0, 24);
    System.arraycopy(a, 0, in, 24, 8);
    
    return in;
  }
  

  private void E(byte[] key, byte[] s, int sOff, byte[] in, int inOff)
  {
    cipher.init(true, new KeyParameter(key));
    
    cipher.processBlock(in, inOff, s, sOff);
  }
  

  short[] wS = new short[16]; short[] w_S = new short[16];
  
  private void fw(byte[] in)
  {
    cpyBytesToShort(in, wS);
    w_S[15] = ((short)(wS[0] ^ wS[1] ^ wS[2] ^ wS[3] ^ wS[12] ^ wS[15]));
    System.arraycopy(wS, 1, w_S, 0, 15);
    cpyShortToBytes(w_S, in);
  }
  

  byte[] S = new byte[32];
  byte[] U = new byte[32]; byte[] V = new byte[32]; byte[] W = new byte[32];
  
  protected void processBlock(byte[] in, int inOff)
  {
    System.arraycopy(in, inOff, M, 0, 32);
    




    System.arraycopy(H, 0, U, 0, 32);
    System.arraycopy(M, 0, V, 0, 32);
    for (int j = 0; j < 32; j++)
    {
      W[j] = ((byte)(U[j] ^ V[j]));
    }
    
    E(P(W), S, 0, H, 0);
    

    for (int i = 1; i < 4; i++)
    {
      byte[] tmpA = A(U);
      for (int j = 0; j < 32; j++)
      {
        U[j] = ((byte)(tmpA[j] ^ C[i][j]));
      }
      V = A(A(V));
      for (int j = 0; j < 32; j++)
      {
        W[j] = ((byte)(U[j] ^ V[j]));
      }
      
      E(P(W), S, i * 8, H, i * 8);
    }
    

    for (int n = 0; n < 12; n++)
    {
      fw(S);
    }
    for (int n = 0; n < 32; n++)
    {
      S[n] = ((byte)(S[n] ^ M[n]));
    }
    
    fw(S);
    
    for (int n = 0; n < 32; n++)
    {
      S[n] = ((byte)(H[n] ^ S[n]));
    }
    for (int n = 0; n < 61; n++)
    {
      fw(S);
    }
    System.arraycopy(S, 0, H, 0, H.length);
  }
  
  private void finish()
  {
    Pack.longToLittleEndian(byteCount * 8L, L, 0);
    
    while (xBufOff != 0)
    {
      update((byte)0);
    }
    
    processBlock(L, 0);
    processBlock(Sum, 0);
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    finish();
    
    System.arraycopy(H, 0, out, outOff, H.length);
    
    reset();
    
    return 32;
  }
  



  private static final byte[] C2 = { 0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1 };
  private static final int DIGEST_LENGTH = 32;
  private int xBufOff;
  private long byteCount;
  private byte[] sBox;
  
  public void reset()
  {
    byteCount = 0L;
    xBufOff = 0;
    
    for (int i = 0; i < H.length; i++)
    {
      H[i] = 0;
    }
    for (int i = 0; i < L.length; i++)
    {
      L[i] = 0;
    }
    for (int i = 0; i < M.length; i++)
    {
      M[i] = 0;
    }
    for (int i = 0; i < C[1].length; i++)
    {
      C[1][i] = 0;
    }
    for (int i = 0; i < C[3].length; i++)
    {
      C[3][i] = 0;
    }
    for (int i = 0; i < Sum.length; i++)
    {
      Sum[i] = 0;
    }
    for (int i = 0; i < xBuf.length; i++)
    {
      xBuf[i] = 0;
    }
    
    System.arraycopy(C2, 0, C[2], 0, C2.length);
  }
  

  private void sumByteArray(byte[] in)
  {
    int carry = 0;
    
    for (int i = 0; i != Sum.length; i++)
    {
      int sum = (Sum[i] & 0xFF) + (in[i] & 0xFF) + carry;
      
      Sum[i] = ((byte)sum);
      
      carry = sum >>> 8;
    }
  }
  
  private void cpyBytesToShort(byte[] S, short[] wS)
  {
    for (int i = 0; i < S.length / 2; i++)
    {
      wS[i] = ((short)(S[(i * 2 + 1)] << 8 & 0xFF00 | S[(i * 2)] & 0xFF));
    }
  }
  
  private void cpyShortToBytes(short[] wS, byte[] S)
  {
    for (int i = 0; i < S.length / 2; i++)
    {
      S[(i * 2 + 1)] = ((byte)(wS[i] >> 8));
      S[(i * 2)] = ((byte)wS[i]);
    }
  }
  
  public int getByteLength()
  {
    return 32;
  }
  
  public Memoable copy()
  {
    return new GOST3411Digest(this);
  }
  
  public void reset(Memoable other)
  {
    GOST3411Digest t = (GOST3411Digest)other;
    
    sBox = sBox;
    cipher.init(true, new ParametersWithSBox(null, sBox));
    
    reset();
    
    System.arraycopy(H, 0, H, 0, H.length);
    System.arraycopy(L, 0, L, 0, L.length);
    System.arraycopy(M, 0, M, 0, M.length);
    System.arraycopy(Sum, 0, Sum, 0, Sum.length);
    System.arraycopy(C[1], 0, C[1], 0, C[1].length);
    System.arraycopy(C[2], 0, C[2], 0, C[2].length);
    System.arraycopy(C[3], 0, C[3], 0, C[3].length);
    System.arraycopy(xBuf, 0, xBuf, 0, xBuf.length);
    
    xBufOff = xBufOff;
    byteCount = byteCount;
  }
}
