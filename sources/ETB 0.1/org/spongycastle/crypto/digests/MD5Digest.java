package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;




public class MD5Digest
  extends GeneralDigest
  implements EncodableDigest
{
  private static final int DIGEST_LENGTH = 16;
  private int H1;
  private int H2;
  private int H3;
  private int H4;
  private int[] X = new int[16];
  private int xOff;
  private static final int S11 = 7;
  private static final int S12 = 12;
  private static final int S13 = 17;
  
  public MD5Digest()
  {
    reset();
  }
  
  public MD5Digest(byte[] encodedState)
  {
    super(encodedState);
    
    H1 = Pack.bigEndianToInt(encodedState, 16);
    H2 = Pack.bigEndianToInt(encodedState, 20);
    H3 = Pack.bigEndianToInt(encodedState, 24);
    H4 = Pack.bigEndianToInt(encodedState, 28);
    
    xOff = Pack.bigEndianToInt(encodedState, 32);
    for (int i = 0; i != xOff; i++)
    {
      X[i] = Pack.bigEndianToInt(encodedState, 36 + i * 4);
    }
  }
  




  public MD5Digest(MD5Digest t)
  {
    super(t);
    
    copyIn(t);
  }
  
  private void copyIn(MD5Digest t)
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
    return "MD5";
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
  


  private static final int S14 = 22;
  

  private static final int S21 = 5;
  

  private static final int S22 = 9;
  

  private static final int S23 = 14;
  

  private static final int S24 = 20;
  

  private static final int S31 = 4;
  

  private static final int S32 = 11;
  

  private static final int S33 = 16;
  

  private static final int S34 = 23;
  

  private static final int S41 = 6;
  
  private static final int S42 = 10;
  
  private static final int S43 = 15;
  
  private static final int S44 = 21;
  
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
    return u & w | v & (w ^ 0xFFFFFFFF);
  }
  



  private int H(int u, int v, int w)
  {
    return u ^ v ^ w;
  }
  



  private int K(int u, int v, int w)
  {
    return v ^ (u | w ^ 0xFFFFFFFF);
  }
  
  protected void processBlock()
  {
    int a = H1;
    int b = H2;
    int c = H3;
    int d = H4;
    



    a = rotateLeft(a + F(b, c, d) + X[0] + -680876936, 7) + b;
    d = rotateLeft(d + F(a, b, c) + X[1] + -389564586, 12) + a;
    c = rotateLeft(c + F(d, a, b) + X[2] + 606105819, 17) + d;
    b = rotateLeft(b + F(c, d, a) + X[3] + -1044525330, 22) + c;
    a = rotateLeft(a + F(b, c, d) + X[4] + -176418897, 7) + b;
    d = rotateLeft(d + F(a, b, c) + X[5] + 1200080426, 12) + a;
    c = rotateLeft(c + F(d, a, b) + X[6] + -1473231341, 17) + d;
    b = rotateLeft(b + F(c, d, a) + X[7] + -45705983, 22) + c;
    a = rotateLeft(a + F(b, c, d) + X[8] + 1770035416, 7) + b;
    d = rotateLeft(d + F(a, b, c) + X[9] + -1958414417, 12) + a;
    c = rotateLeft(c + F(d, a, b) + X[10] + -42063, 17) + d;
    b = rotateLeft(b + F(c, d, a) + X[11] + -1990404162, 22) + c;
    a = rotateLeft(a + F(b, c, d) + X[12] + 1804603682, 7) + b;
    d = rotateLeft(d + F(a, b, c) + X[13] + -40341101, 12) + a;
    c = rotateLeft(c + F(d, a, b) + X[14] + -1502002290, 17) + d;
    b = rotateLeft(b + F(c, d, a) + X[15] + 1236535329, 22) + c;
    



    a = rotateLeft(a + G(b, c, d) + X[1] + -165796510, 5) + b;
    d = rotateLeft(d + G(a, b, c) + X[6] + -1069501632, 9) + a;
    c = rotateLeft(c + G(d, a, b) + X[11] + 643717713, 14) + d;
    b = rotateLeft(b + G(c, d, a) + X[0] + -373897302, 20) + c;
    a = rotateLeft(a + G(b, c, d) + X[5] + -701558691, 5) + b;
    d = rotateLeft(d + G(a, b, c) + X[10] + 38016083, 9) + a;
    c = rotateLeft(c + G(d, a, b) + X[15] + -660478335, 14) + d;
    b = rotateLeft(b + G(c, d, a) + X[4] + -405537848, 20) + c;
    a = rotateLeft(a + G(b, c, d) + X[9] + 568446438, 5) + b;
    d = rotateLeft(d + G(a, b, c) + X[14] + -1019803690, 9) + a;
    c = rotateLeft(c + G(d, a, b) + X[3] + -187363961, 14) + d;
    b = rotateLeft(b + G(c, d, a) + X[8] + 1163531501, 20) + c;
    a = rotateLeft(a + G(b, c, d) + X[13] + -1444681467, 5) + b;
    d = rotateLeft(d + G(a, b, c) + X[2] + -51403784, 9) + a;
    c = rotateLeft(c + G(d, a, b) + X[7] + 1735328473, 14) + d;
    b = rotateLeft(b + G(c, d, a) + X[12] + -1926607734, 20) + c;
    



    a = rotateLeft(a + H(b, c, d) + X[5] + -378558, 4) + b;
    d = rotateLeft(d + H(a, b, c) + X[8] + -2022574463, 11) + a;
    c = rotateLeft(c + H(d, a, b) + X[11] + 1839030562, 16) + d;
    b = rotateLeft(b + H(c, d, a) + X[14] + -35309556, 23) + c;
    a = rotateLeft(a + H(b, c, d) + X[1] + -1530992060, 4) + b;
    d = rotateLeft(d + H(a, b, c) + X[4] + 1272893353, 11) + a;
    c = rotateLeft(c + H(d, a, b) + X[7] + -155497632, 16) + d;
    b = rotateLeft(b + H(c, d, a) + X[10] + -1094730640, 23) + c;
    a = rotateLeft(a + H(b, c, d) + X[13] + 681279174, 4) + b;
    d = rotateLeft(d + H(a, b, c) + X[0] + -358537222, 11) + a;
    c = rotateLeft(c + H(d, a, b) + X[3] + -722521979, 16) + d;
    b = rotateLeft(b + H(c, d, a) + X[6] + 76029189, 23) + c;
    a = rotateLeft(a + H(b, c, d) + X[9] + -640364487, 4) + b;
    d = rotateLeft(d + H(a, b, c) + X[12] + -421815835, 11) + a;
    c = rotateLeft(c + H(d, a, b) + X[15] + 530742520, 16) + d;
    b = rotateLeft(b + H(c, d, a) + X[2] + -995338651, 23) + c;
    



    a = rotateLeft(a + K(b, c, d) + X[0] + -198630844, 6) + b;
    d = rotateLeft(d + K(a, b, c) + X[7] + 1126891415, 10) + a;
    c = rotateLeft(c + K(d, a, b) + X[14] + -1416354905, 15) + d;
    b = rotateLeft(b + K(c, d, a) + X[5] + -57434055, 21) + c;
    a = rotateLeft(a + K(b, c, d) + X[12] + 1700485571, 6) + b;
    d = rotateLeft(d + K(a, b, c) + X[3] + -1894986606, 10) + a;
    c = rotateLeft(c + K(d, a, b) + X[10] + -1051523, 15) + d;
    b = rotateLeft(b + K(c, d, a) + X[1] + -2054922799, 21) + c;
    a = rotateLeft(a + K(b, c, d) + X[8] + 1873313359, 6) + b;
    d = rotateLeft(d + K(a, b, c) + X[15] + -30611744, 10) + a;
    c = rotateLeft(c + K(d, a, b) + X[6] + -1560198380, 15) + d;
    b = rotateLeft(b + K(c, d, a) + X[13] + 1309151649, 21) + c;
    a = rotateLeft(a + K(b, c, d) + X[4] + -145523070, 6) + b;
    d = rotateLeft(d + K(a, b, c) + X[11] + -1120210379, 10) + a;
    c = rotateLeft(c + K(d, a, b) + X[2] + 718787259, 15) + d;
    b = rotateLeft(b + K(c, d, a) + X[9] + -343485551, 21) + c;
    
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
    return new MD5Digest(this);
  }
  
  public void reset(Memoable other)
  {
    MD5Digest d = (MD5Digest)other;
    
    copyIn(d);
  }
  
  public byte[] getEncodedState()
  {
    byte[] state = new byte[36 + xOff * 4];
    
    super.populateState(state);
    
    Pack.intToBigEndian(H1, state, 16);
    Pack.intToBigEndian(H2, state, 20);
    Pack.intToBigEndian(H3, state, 24);
    Pack.intToBigEndian(H4, state, 28);
    Pack.intToBigEndian(xOff, state, 32);
    
    for (int i = 0; i != xOff; i++)
    {
      Pack.intToBigEndian(X[i], state, 36 + i * 4);
    }
    
    return state;
  }
}
