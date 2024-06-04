package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;





public abstract class LongDigest
  implements ExtendedDigest, Memoable, EncodableDigest
{
  private static final int BYTE_LENGTH = 128;
  private byte[] xBuf = new byte[8];
  private int xBufOff;
  private long byteCount1;
  private long byteCount2;
  protected long H1;
  protected long H2;
  protected long H3;
  protected long H4;
  protected long H5; protected long H6; protected long H7; protected long H8; private long[] W = new long[80];
  

  private int wOff;
  

  protected LongDigest()
  {
    xBufOff = 0;
    
    reset();
  }
  





  protected LongDigest(LongDigest t)
  {
    copyIn(t);
  }
  
  protected void copyIn(LongDigest t)
  {
    System.arraycopy(xBuf, 0, xBuf, 0, xBuf.length);
    
    xBufOff = xBufOff;
    byteCount1 = byteCount1;
    byteCount2 = byteCount2;
    
    H1 = H1;
    H2 = H2;
    H3 = H3;
    H4 = H4;
    H5 = H5;
    H6 = H6;
    H7 = H7;
    H8 = H8;
    
    System.arraycopy(W, 0, W, 0, W.length);
    wOff = wOff;
  }
  
  protected void populateState(byte[] state)
  {
    System.arraycopy(xBuf, 0, state, 0, xBufOff);
    Pack.intToBigEndian(xBufOff, state, 8);
    Pack.longToBigEndian(byteCount1, state, 12);
    Pack.longToBigEndian(byteCount2, state, 20);
    Pack.longToBigEndian(H1, state, 28);
    Pack.longToBigEndian(H2, state, 36);
    Pack.longToBigEndian(H3, state, 44);
    Pack.longToBigEndian(H4, state, 52);
    Pack.longToBigEndian(H5, state, 60);
    Pack.longToBigEndian(H6, state, 68);
    Pack.longToBigEndian(H7, state, 76);
    Pack.longToBigEndian(H8, state, 84);
    
    Pack.intToBigEndian(wOff, state, 92);
    for (int i = 0; i < wOff; i++)
    {
      Pack.longToBigEndian(W[i], state, 96 + i * 8);
    }
  }
  
  protected void restoreState(byte[] encodedState)
  {
    xBufOff = Pack.bigEndianToInt(encodedState, 8);
    System.arraycopy(encodedState, 0, xBuf, 0, xBufOff);
    byteCount1 = Pack.bigEndianToLong(encodedState, 12);
    byteCount2 = Pack.bigEndianToLong(encodedState, 20);
    
    H1 = Pack.bigEndianToLong(encodedState, 28);
    H2 = Pack.bigEndianToLong(encodedState, 36);
    H3 = Pack.bigEndianToLong(encodedState, 44);
    H4 = Pack.bigEndianToLong(encodedState, 52);
    H5 = Pack.bigEndianToLong(encodedState, 60);
    H6 = Pack.bigEndianToLong(encodedState, 68);
    H7 = Pack.bigEndianToLong(encodedState, 76);
    H8 = Pack.bigEndianToLong(encodedState, 84);
    
    wOff = Pack.bigEndianToInt(encodedState, 92);
    for (int i = 0; i < wOff; i++)
    {
      W[i] = Pack.bigEndianToLong(encodedState, 96 + i * 8);
    }
  }
  
  protected int getEncodedStateSize()
  {
    return 96 + wOff * 8;
  }
  

  public void update(byte in)
  {
    xBuf[(xBufOff++)] = in;
    
    if (xBufOff == xBuf.length)
    {
      processWord(xBuf, 0);
      xBufOff = 0;
    }
    
    byteCount1 += 1L;
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
      processWord(in, inOff);
      
      inOff += xBuf.length;
      len -= xBuf.length;
      byteCount1 += xBuf.length;
    }
    



    while (len > 0)
    {
      update(in[inOff]);
      
      inOff++;
      len--;
    }
  }
  
  public void finish()
  {
    adjustByteCounts();
    
    long lowBitLength = byteCount1 << 3;
    long hiBitLength = byteCount2;
    



    update((byte)Byte.MIN_VALUE);
    
    while (xBufOff != 0)
    {
      update((byte)0);
    }
    
    processLength(lowBitLength, hiBitLength);
    
    processBlock();
  }
  
  public void reset()
  {
    byteCount1 = 0L;
    byteCount2 = 0L;
    
    xBufOff = 0;
    for (int i = 0; i < xBuf.length; i++)
    {
      xBuf[i] = 0;
    }
    
    wOff = 0;
    for (int i = 0; i != W.length; i++)
    {
      W[i] = 0L;
    }
  }
  
  public int getByteLength()
  {
    return 128;
  }
  


  protected void processWord(byte[] in, int inOff)
  {
    W[wOff] = Pack.bigEndianToLong(in, inOff);
    
    if (++wOff == 16)
    {
      processBlock();
    }
  }
  




  private void adjustByteCounts()
  {
    if (byteCount1 > 2305843009213693951L)
    {
      byteCount2 += (byteCount1 >>> 61);
      byteCount1 &= 0x1FFFFFFFFFFFFFFF;
    }
  }
  


  protected void processLength(long lowW, long hiW)
  {
    if (wOff > 14)
    {
      processBlock();
    }
    
    W[14] = hiW;
    W[15] = lowW;
  }
  
  protected void processBlock()
  {
    adjustByteCounts();
    



    for (int t = 16; t <= 79; t++)
    {
      W[t] = (Sigma1(W[(t - 2)]) + W[(t - 7)] + Sigma0(W[(t - 15)]) + W[(t - 16)]);
    }
    



    long a = H1;
    long b = H2;
    long c = H3;
    long d = H4;
    long e = H5;
    long f = H6;
    long g = H7;
    long h = H8;
    
    int t = 0;
    for (int i = 0; i < 10; i++)
    {

      h += Sum1(e) + Ch(e, f, g) + K[t] + W[(t++)];
      d += h;
      h += Sum0(a) + Maj(a, b, c);
      

      g += Sum1(d) + Ch(d, e, f) + K[t] + W[(t++)];
      c += g;
      g += Sum0(h) + Maj(h, a, b);
      

      f += Sum1(c) + Ch(c, d, e) + K[t] + W[(t++)];
      b += f;
      f += Sum0(g) + Maj(g, h, a);
      

      e += Sum1(b) + Ch(b, c, d) + K[t] + W[(t++)];
      a += e;
      e += Sum0(f) + Maj(f, g, h);
      

      d += Sum1(a) + Ch(a, b, c) + K[t] + W[(t++)];
      h += d;
      d += Sum0(e) + Maj(e, f, g);
      

      c += Sum1(h) + Ch(h, a, b) + K[t] + W[(t++)];
      g += c;
      c += Sum0(d) + Maj(d, e, f);
      

      b += Sum1(g) + Ch(g, h, a) + K[t] + W[(t++)];
      f += b;
      b += Sum0(c) + Maj(c, d, e);
      

      a += Sum1(f) + Ch(f, g, h) + K[t] + W[(t++)];
      e += a;
      a += Sum0(b) + Maj(b, c, d);
    }
    
    H1 += a;
    H2 += b;
    H3 += c;
    H4 += d;
    H5 += e;
    H6 += f;
    H7 += g;
    H8 += h;
    



    wOff = 0;
    for (int i = 0; i < 16; i++)
    {
      W[i] = 0L;
    }
  }
  




  private long Ch(long x, long y, long z)
  {
    return x & y ^ (x ^ 0xFFFFFFFFFFFFFFFF) & z;
  }
  



  private long Maj(long x, long y, long z)
  {
    return x & y ^ x & z ^ y & z;
  }
  

  private long Sum0(long x)
  {
    return (x << 36 | x >>> 28) ^ (x << 30 | x >>> 34) ^ (x << 25 | x >>> 39);
  }
  

  private long Sum1(long x)
  {
    return (x << 50 | x >>> 14) ^ (x << 46 | x >>> 18) ^ (x << 23 | x >>> 41);
  }
  

  private long Sigma0(long x)
  {
    return (x << 63 | x >>> 1) ^ (x << 56 | x >>> 8) ^ x >>> 7;
  }
  

  private long Sigma1(long x)
  {
    return (x << 45 | x >>> 19) ^ (x << 3 | x >>> 61) ^ x >>> 6;
  }
  




  static final long[] K = { 4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L };
}
