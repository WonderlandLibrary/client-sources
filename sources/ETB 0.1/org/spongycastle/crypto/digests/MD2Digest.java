package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;







public class MD2Digest
  implements ExtendedDigest, Memoable
{
  private static final int DIGEST_LENGTH = 16;
  private byte[] X = new byte[48];
  
  private int xOff;
  private byte[] M = new byte[16];
  
  private int mOff;
  private byte[] C = new byte[16];
  private int COff;
  
  public MD2Digest()
  {
    reset();
  }
  
  public MD2Digest(MD2Digest t)
  {
    copyIn(t);
  }
  
  private void copyIn(MD2Digest t)
  {
    System.arraycopy(X, 0, X, 0, X.length);
    xOff = xOff;
    System.arraycopy(M, 0, M, 0, M.length);
    mOff = mOff;
    System.arraycopy(C, 0, C, 0, C.length);
    COff = COff;
  }
  





  public String getAlgorithmName()
  {
    return "MD2";
  }
  




  public int getDigestSize()
  {
    return 16;
  }
  







  public int doFinal(byte[] out, int outOff)
  {
    byte paddingByte = (byte)(M.length - mOff);
    for (int i = mOff; i < M.length; i++)
    {
      M[i] = paddingByte;
    }
    
    processCheckSum(M);
    
    processBlock(M);
    
    processBlock(C);
    
    System.arraycopy(X, xOff, out, outOff, 16);
    
    reset();
    
    return 16;
  }
  


  public void reset()
  {
    xOff = 0;
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
    mOff = 0;
    for (int i = 0; i != M.length; i++)
    {
      M[i] = 0;
    }
    COff = 0;
    for (int i = 0; i != C.length; i++)
    {
      C[i] = 0;
    }
  }
  




  public void update(byte in)
  {
    M[(mOff++)] = in;
    
    if (mOff == 16)
    {
      processCheckSum(M);
      processBlock(M);
      mOff = 0;
    }
  }
  










  public void update(byte[] in, int inOff, int len)
  {
    while ((mOff != 0) && (len > 0))
    {
      update(in[inOff]);
      inOff++;
      len--;
    }
    



    while (len > 16)
    {
      System.arraycopy(in, inOff, M, 0, 16);
      processCheckSum(M);
      processBlock(M);
      len -= 16;
      inOff += 16;
    }
    



    while (len > 0)
    {
      update(in[inOff]);
      inOff++;
      len--;
    }
  }
  
  protected void processCheckSum(byte[] m) {
    int L = C[15];
    for (int i = 0; i < 16; i++)
    {
      int tmp21_20 = i; byte[] tmp21_17 = C;tmp21_17[tmp21_20] = ((byte)(tmp21_17[tmp21_20] ^ S[((m[i] ^ L) & 0xFF)]));
      L = C[i];
    }
  }
  
  protected void processBlock(byte[] m) {
    for (int i = 0; i < 16; i++)
    {
      X[(i + 16)] = m[i];
      X[(i + 32)] = ((byte)(m[i] ^ X[i]));
    }
    
    int t = 0;
    
    for (int j = 0; j < 18; j++)
    {
      for (int k = 0; k < 48; k++)
      {
        int tmp72_70 = k; byte[] tmp72_67 = X;t = tmp72_67[tmp72_70] = (byte)(tmp72_67[tmp72_70] ^ S[t]);
        t &= 0xFF;
      }
      t = (t + j) % 256;
    }
  }
  
  private static final byte[] S = { 41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, -68, 76, -126, -54, 30, -101, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, 16, -119, 11, 34, 95, 33, Byte.MIN_VALUE, Byte.MAX_VALUE, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80, -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, -125, 20 };
  






































  public int getByteLength()
  {
    return 16;
  }
  
  public Memoable copy()
  {
    return new MD2Digest(this);
  }
  
  public void reset(Memoable other)
  {
    MD2Digest d = (MD2Digest)other;
    
    copyIn(d);
  }
}
