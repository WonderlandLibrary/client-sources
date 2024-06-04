package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;








public class MD4Digest
  extends GeneralDigest
{
  private static final int DIGEST_LENGTH = 16;
  private int H1;
  private int H2;
  private int H3;
  private int H4;
  private int[] X = new int[16];
  private int xOff;
  private static final int S11 = 3;
  private static final int S12 = 7;
  private static final int S13 = 11;
  private static final int S14 = 19;
  private static final int S21 = 3;
  
  public MD4Digest() { reset(); }
  





  public MD4Digest(MD4Digest t)
  {
    super(t);
    
    copyIn(t);
  }
  
  private void copyIn(MD4Digest t)
  {
    super.copyIn(t);
    
    H1 = H1;
    H2 = H2;
    H3 = H3;
    H4 = H4;
    
    System.arraycopy(X, 0, X, 0, X.length);
    xOff = xOff;
  }
  
  public String getAlgorithmName()
  {
    return "MD4";
  }
  
  public int getDigestSize()
  {
    return 16;
  }
  


  protected void processWord(byte[] in, int inOff)
  {
    X[(xOff++)] = (in[inOff] & 0xFF | (in[(inOff + 1)] & 0xFF) << 8 | (in[(inOff + 2)] & 0xFF) << 16 | (in[(inOff + 3)] & 0xFF) << 24);
    

    if (xOff == 16)
    {
      processBlock();
    }
  }
  

  protected void processLength(long bitLength)
  {
    if (xOff > 14)
    {
      processBlock();
    }
    
    X[14] = ((int)(bitLength & 0xFFFFFFFFFFFFFFFF));
    X[15] = ((int)(bitLength >>> 32));
  }
  



  private void unpackWord(int word, byte[] out, int outOff)
  {
    out[outOff] = ((byte)word);
    out[(outOff + 1)] = ((byte)(word >>> 8));
    out[(outOff + 2)] = ((byte)(word >>> 16));
    out[(outOff + 3)] = ((byte)(word >>> 24));
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    finish();
    
    unpackWord(H1, out, outOff);
    unpackWord(H2, out, outOff + 4);
    unpackWord(H3, out, outOff + 8);
    unpackWord(H4, out, outOff + 12);
    
    reset();
    
    return 16;
  }
  



  public void reset()
  {
    super.reset();
    
    H1 = 1732584193;
    H2 = -271733879;
    H3 = -1732584194;
    H4 = 271733878;
    
    xOff = 0;
    
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
  }
  



  private static final int S22 = 5;
  


  private static final int S23 = 9;
  


  private static final int S24 = 13;
  


  private static final int S31 = 3;
  


  private static final int S32 = 9;
  


  private static final int S33 = 11;
  

  private static final int S34 = 15;
  

  private int rotateLeft(int x, int n)
  {
    return x << n | x >>> 32 - n;
  }
  






  private int F(int u, int v, int w)
  {
    return u & v | (u ^ 0xFFFFFFFF) & w;
  }
  



  private int G(int u, int v, int w)
  {
    return u & v | u & w | v & w;
  }
  



  private int H(int u, int v, int w)
  {
    return u ^ v ^ w;
  }
  
  protected void processBlock()
  {
    int a = H1;
    int b = H2;
    int c = H3;
    int d = H4;
    



    a = rotateLeft(a + F(b, c, d) + X[0], 3);
    d = rotateLeft(d + F(a, b, c) + X[1], 7);
    c = rotateLeft(c + F(d, a, b) + X[2], 11);
    b = rotateLeft(b + F(c, d, a) + X[3], 19);
    a = rotateLeft(a + F(b, c, d) + X[4], 3);
    d = rotateLeft(d + F(a, b, c) + X[5], 7);
    c = rotateLeft(c + F(d, a, b) + X[6], 11);
    b = rotateLeft(b + F(c, d, a) + X[7], 19);
    a = rotateLeft(a + F(b, c, d) + X[8], 3);
    d = rotateLeft(d + F(a, b, c) + X[9], 7);
    c = rotateLeft(c + F(d, a, b) + X[10], 11);
    b = rotateLeft(b + F(c, d, a) + X[11], 19);
    a = rotateLeft(a + F(b, c, d) + X[12], 3);
    d = rotateLeft(d + F(a, b, c) + X[13], 7);
    c = rotateLeft(c + F(d, a, b) + X[14], 11);
    b = rotateLeft(b + F(c, d, a) + X[15], 19);
    



    a = rotateLeft(a + G(b, c, d) + X[0] + 1518500249, 3);
    d = rotateLeft(d + G(a, b, c) + X[4] + 1518500249, 5);
    c = rotateLeft(c + G(d, a, b) + X[8] + 1518500249, 9);
    b = rotateLeft(b + G(c, d, a) + X[12] + 1518500249, 13);
    a = rotateLeft(a + G(b, c, d) + X[1] + 1518500249, 3);
    d = rotateLeft(d + G(a, b, c) + X[5] + 1518500249, 5);
    c = rotateLeft(c + G(d, a, b) + X[9] + 1518500249, 9);
    b = rotateLeft(b + G(c, d, a) + X[13] + 1518500249, 13);
    a = rotateLeft(a + G(b, c, d) + X[2] + 1518500249, 3);
    d = rotateLeft(d + G(a, b, c) + X[6] + 1518500249, 5);
    c = rotateLeft(c + G(d, a, b) + X[10] + 1518500249, 9);
    b = rotateLeft(b + G(c, d, a) + X[14] + 1518500249, 13);
    a = rotateLeft(a + G(b, c, d) + X[3] + 1518500249, 3);
    d = rotateLeft(d + G(a, b, c) + X[7] + 1518500249, 5);
    c = rotateLeft(c + G(d, a, b) + X[11] + 1518500249, 9);
    b = rotateLeft(b + G(c, d, a) + X[15] + 1518500249, 13);
    



    a = rotateLeft(a + H(b, c, d) + X[0] + 1859775393, 3);
    d = rotateLeft(d + H(a, b, c) + X[8] + 1859775393, 9);
    c = rotateLeft(c + H(d, a, b) + X[4] + 1859775393, 11);
    b = rotateLeft(b + H(c, d, a) + X[12] + 1859775393, 15);
    a = rotateLeft(a + H(b, c, d) + X[2] + 1859775393, 3);
    d = rotateLeft(d + H(a, b, c) + X[10] + 1859775393, 9);
    c = rotateLeft(c + H(d, a, b) + X[6] + 1859775393, 11);
    b = rotateLeft(b + H(c, d, a) + X[14] + 1859775393, 15);
    a = rotateLeft(a + H(b, c, d) + X[1] + 1859775393, 3);
    d = rotateLeft(d + H(a, b, c) + X[9] + 1859775393, 9);
    c = rotateLeft(c + H(d, a, b) + X[5] + 1859775393, 11);
    b = rotateLeft(b + H(c, d, a) + X[13] + 1859775393, 15);
    a = rotateLeft(a + H(b, c, d) + X[3] + 1859775393, 3);
    d = rotateLeft(d + H(a, b, c) + X[11] + 1859775393, 9);
    c = rotateLeft(c + H(d, a, b) + X[7] + 1859775393, 11);
    b = rotateLeft(b + H(c, d, a) + X[15] + 1859775393, 15);
    
    H1 += a;
    H2 += b;
    H3 += c;
    H4 += d;
    



    xOff = 0;
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
  }
  
  public Memoable copy()
  {
    return new MD4Digest(this);
  }
  
  public void reset(Memoable other)
  {
    MD4Digest d = (MD4Digest)other;
    
    copyIn(d);
  }
}
