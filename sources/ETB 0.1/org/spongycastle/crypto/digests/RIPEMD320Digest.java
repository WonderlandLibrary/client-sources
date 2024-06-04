package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;

public class RIPEMD320Digest
  extends GeneralDigest
{
  private static final int DIGEST_LENGTH = 40;
  private int H0;
  private int H1;
  private int H2;
  private int H3;
  private int H4;
  private int H5;
  private int H6;
  private int H7;
  private int H8;
  private int H9;
  private int[] X = new int[16];
  

  private int xOff;
  

  public RIPEMD320Digest()
  {
    reset();
  }
  




  public RIPEMD320Digest(RIPEMD320Digest t)
  {
    super(t);
    
    doCopy(t);
  }
  
  private void doCopy(RIPEMD320Digest t)
  {
    super.copyIn(t);
    H0 = H0;
    H1 = H1;
    H2 = H2;
    H3 = H3;
    H4 = H4;
    H5 = H5;
    H6 = H6;
    H7 = H7;
    H8 = H8;
    H9 = H9;
    
    System.arraycopy(X, 0, X, 0, X.length);
    xOff = xOff;
  }
  
  public String getAlgorithmName()
  {
    return "RIPEMD320";
  }
  
  public int getDigestSize()
  {
    return 40;
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
    
    unpackWord(H0, out, outOff);
    unpackWord(H1, out, outOff + 4);
    unpackWord(H2, out, outOff + 8);
    unpackWord(H3, out, outOff + 12);
    unpackWord(H4, out, outOff + 16);
    unpackWord(H5, out, outOff + 20);
    unpackWord(H6, out, outOff + 24);
    unpackWord(H7, out, outOff + 28);
    unpackWord(H8, out, outOff + 32);
    unpackWord(H9, out, outOff + 36);
    
    reset();
    
    return 40;
  }
  



  public void reset()
  {
    super.reset();
    
    H0 = 1732584193;
    H1 = -271733879;
    H2 = -1732584194;
    H3 = 271733878;
    H4 = -1009589776;
    H5 = 1985229328;
    H6 = -19088744;
    H7 = -1985229329;
    H8 = 19088743;
    H9 = 1009589775;
    
    xOff = 0;
    
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
  }
  





  private int RL(int x, int n)
  {
    return x << n | x >>> 32 - n;
  }
  










  private int f1(int x, int y, int z)
  {
    return x ^ y ^ z;
  }
  






  private int f2(int x, int y, int z)
  {
    return x & y | (x ^ 0xFFFFFFFF) & z;
  }
  






  private int f3(int x, int y, int z)
  {
    return (x | y ^ 0xFFFFFFFF) ^ z;
  }
  






  private int f4(int x, int y, int z)
  {
    return x & z | y & (z ^ 0xFFFFFFFF);
  }
  






  private int f5(int x, int y, int z)
  {
    return x ^ (y | z ^ 0xFFFFFFFF);
  }
  







  protected void processBlock()
  {
    int a = H0;
    int b = H1;
    int c = H2;
    int d = H3;
    int e = H4;
    int aa = H5;
    int bb = H6;
    int cc = H7;
    int dd = H8;
    int ee = H9;
    




    a = RL(a + f1(b, c, d) + X[0], 11) + e;c = RL(c, 10);
    e = RL(e + f1(a, b, c) + X[1], 14) + d;b = RL(b, 10);
    d = RL(d + f1(e, a, b) + X[2], 15) + c;a = RL(a, 10);
    c = RL(c + f1(d, e, a) + X[3], 12) + b;e = RL(e, 10);
    b = RL(b + f1(c, d, e) + X[4], 5) + a;d = RL(d, 10);
    a = RL(a + f1(b, c, d) + X[5], 8) + e;c = RL(c, 10);
    e = RL(e + f1(a, b, c) + X[6], 7) + d;b = RL(b, 10);
    d = RL(d + f1(e, a, b) + X[7], 9) + c;a = RL(a, 10);
    c = RL(c + f1(d, e, a) + X[8], 11) + b;e = RL(e, 10);
    b = RL(b + f1(c, d, e) + X[9], 13) + a;d = RL(d, 10);
    a = RL(a + f1(b, c, d) + X[10], 14) + e;c = RL(c, 10);
    e = RL(e + f1(a, b, c) + X[11], 15) + d;b = RL(b, 10);
    d = RL(d + f1(e, a, b) + X[12], 6) + c;a = RL(a, 10);
    c = RL(c + f1(d, e, a) + X[13], 7) + b;e = RL(e, 10);
    b = RL(b + f1(c, d, e) + X[14], 9) + a;d = RL(d, 10);
    a = RL(a + f1(b, c, d) + X[15], 8) + e;c = RL(c, 10);
    

    aa = RL(aa + f5(bb, cc, dd) + X[5] + 1352829926, 8) + ee;cc = RL(cc, 10);
    ee = RL(ee + f5(aa, bb, cc) + X[14] + 1352829926, 9) + dd;bb = RL(bb, 10);
    dd = RL(dd + f5(ee, aa, bb) + X[7] + 1352829926, 9) + cc;aa = RL(aa, 10);
    cc = RL(cc + f5(dd, ee, aa) + X[0] + 1352829926, 11) + bb;ee = RL(ee, 10);
    bb = RL(bb + f5(cc, dd, ee) + X[9] + 1352829926, 13) + aa;dd = RL(dd, 10);
    aa = RL(aa + f5(bb, cc, dd) + X[2] + 1352829926, 15) + ee;cc = RL(cc, 10);
    ee = RL(ee + f5(aa, bb, cc) + X[11] + 1352829926, 15) + dd;bb = RL(bb, 10);
    dd = RL(dd + f5(ee, aa, bb) + X[4] + 1352829926, 5) + cc;aa = RL(aa, 10);
    cc = RL(cc + f5(dd, ee, aa) + X[13] + 1352829926, 7) + bb;ee = RL(ee, 10);
    bb = RL(bb + f5(cc, dd, ee) + X[6] + 1352829926, 7) + aa;dd = RL(dd, 10);
    aa = RL(aa + f5(bb, cc, dd) + X[15] + 1352829926, 8) + ee;cc = RL(cc, 10);
    ee = RL(ee + f5(aa, bb, cc) + X[8] + 1352829926, 11) + dd;bb = RL(bb, 10);
    dd = RL(dd + f5(ee, aa, bb) + X[1] + 1352829926, 14) + cc;aa = RL(aa, 10);
    cc = RL(cc + f5(dd, ee, aa) + X[10] + 1352829926, 14) + bb;ee = RL(ee, 10);
    bb = RL(bb + f5(cc, dd, ee) + X[3] + 1352829926, 12) + aa;dd = RL(dd, 10);
    aa = RL(aa + f5(bb, cc, dd) + X[12] + 1352829926, 6) + ee;cc = RL(cc, 10);
    
    int t = a;a = aa;aa = t;
    




    e = RL(e + f2(a, b, c) + X[7] + 1518500249, 7) + d;b = RL(b, 10);
    d = RL(d + f2(e, a, b) + X[4] + 1518500249, 6) + c;a = RL(a, 10);
    c = RL(c + f2(d, e, a) + X[13] + 1518500249, 8) + b;e = RL(e, 10);
    b = RL(b + f2(c, d, e) + X[1] + 1518500249, 13) + a;d = RL(d, 10);
    a = RL(a + f2(b, c, d) + X[10] + 1518500249, 11) + e;c = RL(c, 10);
    e = RL(e + f2(a, b, c) + X[6] + 1518500249, 9) + d;b = RL(b, 10);
    d = RL(d + f2(e, a, b) + X[15] + 1518500249, 7) + c;a = RL(a, 10);
    c = RL(c + f2(d, e, a) + X[3] + 1518500249, 15) + b;e = RL(e, 10);
    b = RL(b + f2(c, d, e) + X[12] + 1518500249, 7) + a;d = RL(d, 10);
    a = RL(a + f2(b, c, d) + X[0] + 1518500249, 12) + e;c = RL(c, 10);
    e = RL(e + f2(a, b, c) + X[9] + 1518500249, 15) + d;b = RL(b, 10);
    d = RL(d + f2(e, a, b) + X[5] + 1518500249, 9) + c;a = RL(a, 10);
    c = RL(c + f2(d, e, a) + X[2] + 1518500249, 11) + b;e = RL(e, 10);
    b = RL(b + f2(c, d, e) + X[14] + 1518500249, 7) + a;d = RL(d, 10);
    a = RL(a + f2(b, c, d) + X[11] + 1518500249, 13) + e;c = RL(c, 10);
    e = RL(e + f2(a, b, c) + X[8] + 1518500249, 12) + d;b = RL(b, 10);
    

    ee = RL(ee + f4(aa, bb, cc) + X[6] + 1548603684, 9) + dd;bb = RL(bb, 10);
    dd = RL(dd + f4(ee, aa, bb) + X[11] + 1548603684, 13) + cc;aa = RL(aa, 10);
    cc = RL(cc + f4(dd, ee, aa) + X[3] + 1548603684, 15) + bb;ee = RL(ee, 10);
    bb = RL(bb + f4(cc, dd, ee) + X[7] + 1548603684, 7) + aa;dd = RL(dd, 10);
    aa = RL(aa + f4(bb, cc, dd) + X[0] + 1548603684, 12) + ee;cc = RL(cc, 10);
    ee = RL(ee + f4(aa, bb, cc) + X[13] + 1548603684, 8) + dd;bb = RL(bb, 10);
    dd = RL(dd + f4(ee, aa, bb) + X[5] + 1548603684, 9) + cc;aa = RL(aa, 10);
    cc = RL(cc + f4(dd, ee, aa) + X[10] + 1548603684, 11) + bb;ee = RL(ee, 10);
    bb = RL(bb + f4(cc, dd, ee) + X[14] + 1548603684, 7) + aa;dd = RL(dd, 10);
    aa = RL(aa + f4(bb, cc, dd) + X[15] + 1548603684, 7) + ee;cc = RL(cc, 10);
    ee = RL(ee + f4(aa, bb, cc) + X[8] + 1548603684, 12) + dd;bb = RL(bb, 10);
    dd = RL(dd + f4(ee, aa, bb) + X[12] + 1548603684, 7) + cc;aa = RL(aa, 10);
    cc = RL(cc + f4(dd, ee, aa) + X[4] + 1548603684, 6) + bb;ee = RL(ee, 10);
    bb = RL(bb + f4(cc, dd, ee) + X[9] + 1548603684, 15) + aa;dd = RL(dd, 10);
    aa = RL(aa + f4(bb, cc, dd) + X[1] + 1548603684, 13) + ee;cc = RL(cc, 10);
    ee = RL(ee + f4(aa, bb, cc) + X[2] + 1548603684, 11) + dd;bb = RL(bb, 10);
    
    t = b;b = bb;bb = t;
    




    d = RL(d + f3(e, a, b) + X[3] + 1859775393, 11) + c;a = RL(a, 10);
    c = RL(c + f3(d, e, a) + X[10] + 1859775393, 13) + b;e = RL(e, 10);
    b = RL(b + f3(c, d, e) + X[14] + 1859775393, 6) + a;d = RL(d, 10);
    a = RL(a + f3(b, c, d) + X[4] + 1859775393, 7) + e;c = RL(c, 10);
    e = RL(e + f3(a, b, c) + X[9] + 1859775393, 14) + d;b = RL(b, 10);
    d = RL(d + f3(e, a, b) + X[15] + 1859775393, 9) + c;a = RL(a, 10);
    c = RL(c + f3(d, e, a) + X[8] + 1859775393, 13) + b;e = RL(e, 10);
    b = RL(b + f3(c, d, e) + X[1] + 1859775393, 15) + a;d = RL(d, 10);
    a = RL(a + f3(b, c, d) + X[2] + 1859775393, 14) + e;c = RL(c, 10);
    e = RL(e + f3(a, b, c) + X[7] + 1859775393, 8) + d;b = RL(b, 10);
    d = RL(d + f3(e, a, b) + X[0] + 1859775393, 13) + c;a = RL(a, 10);
    c = RL(c + f3(d, e, a) + X[6] + 1859775393, 6) + b;e = RL(e, 10);
    b = RL(b + f3(c, d, e) + X[13] + 1859775393, 5) + a;d = RL(d, 10);
    a = RL(a + f3(b, c, d) + X[11] + 1859775393, 12) + e;c = RL(c, 10);
    e = RL(e + f3(a, b, c) + X[5] + 1859775393, 7) + d;b = RL(b, 10);
    d = RL(d + f3(e, a, b) + X[12] + 1859775393, 5) + c;a = RL(a, 10);
    

    dd = RL(dd + f3(ee, aa, bb) + X[15] + 1836072691, 9) + cc;aa = RL(aa, 10);
    cc = RL(cc + f3(dd, ee, aa) + X[5] + 1836072691, 7) + bb;ee = RL(ee, 10);
    bb = RL(bb + f3(cc, dd, ee) + X[1] + 1836072691, 15) + aa;dd = RL(dd, 10);
    aa = RL(aa + f3(bb, cc, dd) + X[3] + 1836072691, 11) + ee;cc = RL(cc, 10);
    ee = RL(ee + f3(aa, bb, cc) + X[7] + 1836072691, 8) + dd;bb = RL(bb, 10);
    dd = RL(dd + f3(ee, aa, bb) + X[14] + 1836072691, 6) + cc;aa = RL(aa, 10);
    cc = RL(cc + f3(dd, ee, aa) + X[6] + 1836072691, 6) + bb;ee = RL(ee, 10);
    bb = RL(bb + f3(cc, dd, ee) + X[9] + 1836072691, 14) + aa;dd = RL(dd, 10);
    aa = RL(aa + f3(bb, cc, dd) + X[11] + 1836072691, 12) + ee;cc = RL(cc, 10);
    ee = RL(ee + f3(aa, bb, cc) + X[8] + 1836072691, 13) + dd;bb = RL(bb, 10);
    dd = RL(dd + f3(ee, aa, bb) + X[12] + 1836072691, 5) + cc;aa = RL(aa, 10);
    cc = RL(cc + f3(dd, ee, aa) + X[2] + 1836072691, 14) + bb;ee = RL(ee, 10);
    bb = RL(bb + f3(cc, dd, ee) + X[10] + 1836072691, 13) + aa;dd = RL(dd, 10);
    aa = RL(aa + f3(bb, cc, dd) + X[0] + 1836072691, 13) + ee;cc = RL(cc, 10);
    ee = RL(ee + f3(aa, bb, cc) + X[4] + 1836072691, 7) + dd;bb = RL(bb, 10);
    dd = RL(dd + f3(ee, aa, bb) + X[13] + 1836072691, 5) + cc;aa = RL(aa, 10);
    
    t = c;c = cc;cc = t;
    




    c = RL(c + f4(d, e, a) + X[1] + -1894007588, 11) + b;e = RL(e, 10);
    b = RL(b + f4(c, d, e) + X[9] + -1894007588, 12) + a;d = RL(d, 10);
    a = RL(a + f4(b, c, d) + X[11] + -1894007588, 14) + e;c = RL(c, 10);
    e = RL(e + f4(a, b, c) + X[10] + -1894007588, 15) + d;b = RL(b, 10);
    d = RL(d + f4(e, a, b) + X[0] + -1894007588, 14) + c;a = RL(a, 10);
    c = RL(c + f4(d, e, a) + X[8] + -1894007588, 15) + b;e = RL(e, 10);
    b = RL(b + f4(c, d, e) + X[12] + -1894007588, 9) + a;d = RL(d, 10);
    a = RL(a + f4(b, c, d) + X[4] + -1894007588, 8) + e;c = RL(c, 10);
    e = RL(e + f4(a, b, c) + X[13] + -1894007588, 9) + d;b = RL(b, 10);
    d = RL(d + f4(e, a, b) + X[3] + -1894007588, 14) + c;a = RL(a, 10);
    c = RL(c + f4(d, e, a) + X[7] + -1894007588, 5) + b;e = RL(e, 10);
    b = RL(b + f4(c, d, e) + X[15] + -1894007588, 6) + a;d = RL(d, 10);
    a = RL(a + f4(b, c, d) + X[14] + -1894007588, 8) + e;c = RL(c, 10);
    e = RL(e + f4(a, b, c) + X[5] + -1894007588, 6) + d;b = RL(b, 10);
    d = RL(d + f4(e, a, b) + X[6] + -1894007588, 5) + c;a = RL(a, 10);
    c = RL(c + f4(d, e, a) + X[2] + -1894007588, 12) + b;e = RL(e, 10);
    

    cc = RL(cc + f2(dd, ee, aa) + X[8] + 2053994217, 15) + bb;ee = RL(ee, 10);
    bb = RL(bb + f2(cc, dd, ee) + X[6] + 2053994217, 5) + aa;dd = RL(dd, 10);
    aa = RL(aa + f2(bb, cc, dd) + X[4] + 2053994217, 8) + ee;cc = RL(cc, 10);
    ee = RL(ee + f2(aa, bb, cc) + X[1] + 2053994217, 11) + dd;bb = RL(bb, 10);
    dd = RL(dd + f2(ee, aa, bb) + X[3] + 2053994217, 14) + cc;aa = RL(aa, 10);
    cc = RL(cc + f2(dd, ee, aa) + X[11] + 2053994217, 14) + bb;ee = RL(ee, 10);
    bb = RL(bb + f2(cc, dd, ee) + X[15] + 2053994217, 6) + aa;dd = RL(dd, 10);
    aa = RL(aa + f2(bb, cc, dd) + X[0] + 2053994217, 14) + ee;cc = RL(cc, 10);
    ee = RL(ee + f2(aa, bb, cc) + X[5] + 2053994217, 6) + dd;bb = RL(bb, 10);
    dd = RL(dd + f2(ee, aa, bb) + X[12] + 2053994217, 9) + cc;aa = RL(aa, 10);
    cc = RL(cc + f2(dd, ee, aa) + X[2] + 2053994217, 12) + bb;ee = RL(ee, 10);
    bb = RL(bb + f2(cc, dd, ee) + X[13] + 2053994217, 9) + aa;dd = RL(dd, 10);
    aa = RL(aa + f2(bb, cc, dd) + X[9] + 2053994217, 12) + ee;cc = RL(cc, 10);
    ee = RL(ee + f2(aa, bb, cc) + X[7] + 2053994217, 5) + dd;bb = RL(bb, 10);
    dd = RL(dd + f2(ee, aa, bb) + X[10] + 2053994217, 15) + cc;aa = RL(aa, 10);
    cc = RL(cc + f2(dd, ee, aa) + X[14] + 2053994217, 8) + bb;ee = RL(ee, 10);
    
    t = d;d = dd;dd = t;
    




    b = RL(b + f5(c, d, e) + X[4] + -1454113458, 9) + a;d = RL(d, 10);
    a = RL(a + f5(b, c, d) + X[0] + -1454113458, 15) + e;c = RL(c, 10);
    e = RL(e + f5(a, b, c) + X[5] + -1454113458, 5) + d;b = RL(b, 10);
    d = RL(d + f5(e, a, b) + X[9] + -1454113458, 11) + c;a = RL(a, 10);
    c = RL(c + f5(d, e, a) + X[7] + -1454113458, 6) + b;e = RL(e, 10);
    b = RL(b + f5(c, d, e) + X[12] + -1454113458, 8) + a;d = RL(d, 10);
    a = RL(a + f5(b, c, d) + X[2] + -1454113458, 13) + e;c = RL(c, 10);
    e = RL(e + f5(a, b, c) + X[10] + -1454113458, 12) + d;b = RL(b, 10);
    d = RL(d + f5(e, a, b) + X[14] + -1454113458, 5) + c;a = RL(a, 10);
    c = RL(c + f5(d, e, a) + X[1] + -1454113458, 12) + b;e = RL(e, 10);
    b = RL(b + f5(c, d, e) + X[3] + -1454113458, 13) + a;d = RL(d, 10);
    a = RL(a + f5(b, c, d) + X[8] + -1454113458, 14) + e;c = RL(c, 10);
    e = RL(e + f5(a, b, c) + X[11] + -1454113458, 11) + d;b = RL(b, 10);
    d = RL(d + f5(e, a, b) + X[6] + -1454113458, 8) + c;a = RL(a, 10);
    c = RL(c + f5(d, e, a) + X[15] + -1454113458, 5) + b;e = RL(e, 10);
    b = RL(b + f5(c, d, e) + X[13] + -1454113458, 6) + a;d = RL(d, 10);
    

    bb = RL(bb + f1(cc, dd, ee) + X[12], 8) + aa;dd = RL(dd, 10);
    aa = RL(aa + f1(bb, cc, dd) + X[15], 5) + ee;cc = RL(cc, 10);
    ee = RL(ee + f1(aa, bb, cc) + X[10], 12) + dd;bb = RL(bb, 10);
    dd = RL(dd + f1(ee, aa, bb) + X[4], 9) + cc;aa = RL(aa, 10);
    cc = RL(cc + f1(dd, ee, aa) + X[1], 12) + bb;ee = RL(ee, 10);
    bb = RL(bb + f1(cc, dd, ee) + X[5], 5) + aa;dd = RL(dd, 10);
    aa = RL(aa + f1(bb, cc, dd) + X[8], 14) + ee;cc = RL(cc, 10);
    ee = RL(ee + f1(aa, bb, cc) + X[7], 6) + dd;bb = RL(bb, 10);
    dd = RL(dd + f1(ee, aa, bb) + X[6], 8) + cc;aa = RL(aa, 10);
    cc = RL(cc + f1(dd, ee, aa) + X[2], 13) + bb;ee = RL(ee, 10);
    bb = RL(bb + f1(cc, dd, ee) + X[13], 6) + aa;dd = RL(dd, 10);
    aa = RL(aa + f1(bb, cc, dd) + X[14], 5) + ee;cc = RL(cc, 10);
    ee = RL(ee + f1(aa, bb, cc) + X[0], 15) + dd;bb = RL(bb, 10);
    dd = RL(dd + f1(ee, aa, bb) + X[3], 13) + cc;aa = RL(aa, 10);
    cc = RL(cc + f1(dd, ee, aa) + X[9], 11) + bb;ee = RL(ee, 10);
    bb = RL(bb + f1(cc, dd, ee) + X[11], 11) + aa;dd = RL(dd, 10);
    




    H0 += a;
    H1 += b;
    H2 += c;
    H3 += d;
    H4 += ee;
    H5 += aa;
    H6 += bb;
    H7 += cc;
    H8 += dd;
    H9 += e;
    



    xOff = 0;
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
  }
  
  public Memoable copy()
  {
    return new RIPEMD320Digest(this);
  }
  
  public void reset(Memoable other)
  {
    RIPEMD320Digest d = (RIPEMD320Digest)other;
    
    doCopy(d);
  }
}
