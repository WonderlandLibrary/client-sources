package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;



public class SecT131Field
{
  private static final long M03 = 7L;
  private static final long M44 = 17592186044415L;
  private static final long[] ROOT_Z = { 2791191049453778211L, 2791191049453778402L, 6L };
  
  public SecT131Field() {}
  
  public static void add(long[] x, long[] y, long[] z) { x[0] ^= y[0];
    x[1] ^= y[1];
    x[2] ^= y[2];
  }
  
  public static void addExt(long[] xx, long[] yy, long[] zz)
  {
    xx[0] ^= yy[0];
    xx[1] ^= yy[1];
    xx[2] ^= yy[2];
    xx[3] ^= yy[3];
    xx[4] ^= yy[4];
  }
  
  public static void addOne(long[] x, long[] z)
  {
    x[0] ^= 1L;
    z[1] = x[1];
    z[2] = x[2];
  }
  
  public static long[] fromBigInteger(BigInteger x)
  {
    long[] z = Nat192.fromBigInteger64(x);
    reduce61(z, 0);
    return z;
  }
  
  public static void invert(long[] x, long[] z)
  {
    if (Nat192.isZero64(x))
    {
      throw new IllegalStateException();
    }
    


    long[] t0 = Nat192.create64();
    long[] t1 = Nat192.create64();
    
    square(x, t0);
    multiply(t0, x, t0);
    squareN(t0, 2, t1);
    multiply(t1, t0, t1);
    squareN(t1, 4, t0);
    multiply(t0, t1, t0);
    squareN(t0, 8, t1);
    multiply(t1, t0, t1);
    squareN(t1, 16, t0);
    multiply(t0, t1, t0);
    squareN(t0, 32, t1);
    multiply(t1, t0, t1);
    square(t1, t1);
    multiply(t1, x, t1);
    squareN(t1, 65, t0);
    multiply(t0, t1, t0);
    square(t0, z);
  }
  
  public static void multiply(long[] x, long[] y, long[] z)
  {
    long[] tt = Nat192.createExt64();
    implMultiply(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(long[] x, long[] y, long[] zz)
  {
    long[] tt = Nat192.createExt64();
    implMultiply(x, y, tt);
    addExt(zz, tt, zz);
  }
  
  public static void reduce(long[] xx, long[] z)
  {
    long x0 = xx[0];long x1 = xx[1];long x2 = xx[2];long x3 = xx[3];long x4 = xx[4];
    
    x1 ^= x4 << 61 ^ x4 << 63;
    x2 ^= x4 >>> 3 ^ x4 >>> 1 ^ x4 ^ x4 << 5;
    x3 ^= x4 >>> 59;
    
    x0 ^= x3 << 61 ^ x3 << 63;
    x1 ^= x3 >>> 3 ^ x3 >>> 1 ^ x3 ^ x3 << 5;
    x2 ^= x3 >>> 59;
    
    long t = x2 >>> 3;
    z[0] = (x0 ^ t ^ t << 2 ^ t << 3 ^ t << 8);
    z[1] = (x1 ^ t >>> 56);
    z[2] = (x2 & 0x7);
  }
  
  public static void reduce61(long[] z, int zOff)
  {
    long z2 = z[(zOff + 2)];long t = z2 >>> 3;
    z[zOff] ^= t ^ t << 2 ^ t << 3 ^ t << 8;
    z[(zOff + 1)] ^= t >>> 56;
    z[(zOff + 2)] = (z2 & 0x7);
  }
  
  public static void sqrt(long[] x, long[] z)
  {
    long[] odd = Nat192.create64();
    

    long u0 = Interleave.unshuffle(x[0]);long u1 = Interleave.unshuffle(x[1]);
    long e0 = u0 & 0xFFFFFFFF | u1 << 32;
    odd[0] = (u0 >>> 32 | u1 & 0xFFFFFFFF00000000);
    
    u0 = Interleave.unshuffle(x[2]);
    long e1 = u0 & 0xFFFFFFFF;
    odd[1] = (u0 >>> 32);
    
    multiply(odd, ROOT_Z, z);
    
    z[0] ^= e0;
    z[1] ^= e1;
  }
  
  public static void square(long[] x, long[] z)
  {
    long[] tt = Nat.create64(5);
    implSquare(x, tt);
    reduce(tt, z);
  }
  
  public static void squareAddToExt(long[] x, long[] zz)
  {
    long[] tt = Nat.create64(5);
    implSquare(x, tt);
    addExt(zz, tt, zz);
  }
  


  public static void squareN(long[] x, int n, long[] z)
  {
    long[] tt = Nat.create64(5);
    implSquare(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      implSquare(z, tt);
      reduce(tt, z);
    }
  }
  

  public static int trace(long[] x)
  {
    return (int)(x[0] ^ x[1] >>> 59 ^ x[2] >>> 1) & 0x1;
  }
  
  protected static void implCompactExt(long[] zz)
  {
    long z0 = zz[0];long z1 = zz[1];long z2 = zz[2];long z3 = zz[3];long z4 = zz[4];long z5 = zz[5];
    zz[0] = (z0 ^ z1 << 44);
    zz[1] = (z1 >>> 20 ^ z2 << 24);
    zz[2] = (z2 >>> 40 ^ z3 << 4 ^ z4 << 48);
    
    zz[3] = (z3 >>> 60 ^ z5 << 28 ^ z4 >>> 16);
    
    zz[4] = (z5 >>> 36);
    zz[5] = 0L;
  }
  




  protected static void implMultiply(long[] x, long[] y, long[] zz)
  {
    long f0 = x[0];long f1 = x[1];long f2 = x[2];
    f2 = (f1 >>> 24 ^ f2 << 40) & 0xFFFFFFFFFFF;
    f1 = (f0 >>> 44 ^ f1 << 20) & 0xFFFFFFFFFFF;
    f0 &= 0xFFFFFFFFFFF;
    
    long g0 = y[0];long g1 = y[1];long g2 = y[2];
    g2 = (g1 >>> 24 ^ g2 << 40) & 0xFFFFFFFFFFF;
    g1 = (g0 >>> 44 ^ g1 << 20) & 0xFFFFFFFFFFF;
    g0 &= 0xFFFFFFFFFFF;
    
    long[] H = new long[10];
    
    implMulw(f0, g0, H, 0);
    implMulw(f2, g2, H, 2);
    
    long t0 = f0 ^ f1 ^ f2;
    long t1 = g0 ^ g1 ^ g2;
    
    implMulw(t0, t1, H, 4);
    
    long t2 = f1 << 1 ^ f2 << 2;
    long t3 = g1 << 1 ^ g2 << 2;
    
    implMulw(f0 ^ t2, g0 ^ t3, H, 6);
    implMulw(t0 ^ t2, t1 ^ t3, H, 8);
    
    long t4 = H[6] ^ H[8];
    long t5 = H[7] ^ H[9];
    



    long v0 = t4 << 1 ^ H[6];
    long v1 = t4 ^ t5 << 1 ^ H[7];
    long v2 = t5;
    

    long u0 = H[0];
    long u1 = H[1] ^ H[0] ^ H[4];
    long u2 = H[1] ^ H[5];
    

    long w0 = u0 ^ v0 ^ H[2] << 4 ^ H[2] << 1;
    long w1 = u1 ^ v1 ^ H[3] << 4 ^ H[3] << 1;
    long w2 = u2 ^ v2;
    

    w1 ^= w0 >>> 44;w0 &= 0xFFFFFFFFFFF;
    w2 ^= w1 >>> 44;w1 &= 0xFFFFFFFFFFF;
    




    w0 = w0 >>> 1 ^ (w1 & 1L) << 43;
    w1 = w1 >>> 1 ^ (w2 & 1L) << 43;
    w2 >>>= 1;
    


    w0 ^= w0 << 1;
    w0 ^= w0 << 2;
    w0 ^= w0 << 4;
    w0 ^= w0 << 8;
    w0 ^= w0 << 16;
    w0 ^= w0 << 32;
    
    w0 &= 0xFFFFFFFFFFF;w1 ^= w0 >>> 43;
    
    w1 ^= w1 << 1;
    w1 ^= w1 << 2;
    w1 ^= w1 << 4;
    w1 ^= w1 << 8;
    w1 ^= w1 << 16;
    w1 ^= w1 << 32;
    
    w1 &= 0xFFFFFFFFFFF;w2 ^= w1 >>> 43;
    
    w2 ^= w2 << 1;
    w2 ^= w2 << 2;
    w2 ^= w2 << 4;
    w2 ^= w2 << 8;
    w2 ^= w2 << 16;
    w2 ^= w2 << 32;
    


    zz[0] = u0;
    zz[1] = (u1 ^ w0 ^ H[2]);
    zz[2] = (u2 ^ w1 ^ w0 ^ H[3]);
    zz[3] = (w2 ^ w1);
    zz[4] = (w2 ^ H[2]);
    zz[5] = H[3];
    
    implCompactExt(zz);
  }
  



  protected static void implMulw(long x, long y, long[] z, int zOff)
  {
    long[] u = new long[8];
    
    u[1] = y;
    u[2] = (u[1] << 1);
    u[3] = (u[2] ^ y);
    u[4] = (u[2] << 1);
    u[5] = (u[4] ^ y);
    u[6] = (u[3] << 1);
    u[7] = (u[6] ^ y);
    
    int j = (int)x;
    long h = 0L;long l = u[(j & 0x7)] ^ u[(j >>> 3 & 0x7)] << 3 ^ u[(j >>> 6 & 0x7)] << 6;
    

    int k = 33;
    do
    {
      j = (int)(x >>> k);
      long g = u[(j & 0x7)] ^ u[(j >>> 3 & 0x7)] << 3 ^ u[(j >>> 6 & 0x7)] << 6 ^ u[(j >>> 9 & 0x7)] << 9;
      


      l ^= g << k;
      h ^= g >>> -k;
      
      k -= 12; } while (k > 0);
    


    z[zOff] = (l & 0xFFFFFFFFFFF);
    z[(zOff + 1)] = (l >>> 44 ^ h << 20);
  }
  
  protected static void implSquare(long[] x, long[] zz)
  {
    Interleave.expand64To128(x[0], zz, 0);
    Interleave.expand64To128(x[1], zz, 2);
    
    zz[4] = (Interleave.expand8to16((int)x[2]) & 0xFFFFFFFF);
  }
}
