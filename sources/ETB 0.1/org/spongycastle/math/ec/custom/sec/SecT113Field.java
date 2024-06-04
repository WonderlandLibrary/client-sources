package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat128;

public class SecT113Field
{
  private static final long M49 = 562949953421311L;
  private static final long M57 = 144115188075855871L;
  
  public SecT113Field() {}
  
  public static void add(long[] x, long[] y, long[] z)
  {
    x[0] ^= y[0];
    x[1] ^= y[1];
  }
  
  public static void addExt(long[] xx, long[] yy, long[] zz)
  {
    xx[0] ^= yy[0];
    xx[1] ^= yy[1];
    xx[2] ^= yy[2];
    xx[3] ^= yy[3];
  }
  
  public static void addOne(long[] x, long[] z)
  {
    x[0] ^= 1L;
    z[1] = x[1];
  }
  
  public static long[] fromBigInteger(java.math.BigInteger x)
  {
    long[] z = Nat128.fromBigInteger64(x);
    reduce15(z, 0);
    return z;
  }
  
  public static void invert(long[] x, long[] z)
  {
    if (Nat128.isZero64(x))
    {
      throw new IllegalStateException();
    }
    


    long[] t0 = Nat128.create64();
    long[] t1 = Nat128.create64();
    
    square(x, t0);
    multiply(t0, x, t0);
    square(t0, t0);
    multiply(t0, x, t0);
    squareN(t0, 3, t1);
    multiply(t1, t0, t1);
    square(t1, t1);
    multiply(t1, x, t1);
    squareN(t1, 7, t0);
    multiply(t0, t1, t0);
    squareN(t0, 14, t1);
    multiply(t1, t0, t1);
    squareN(t1, 28, t0);
    multiply(t0, t1, t0);
    squareN(t0, 56, t1);
    multiply(t1, t0, t1);
    square(t1, z);
  }
  
  public static void multiply(long[] x, long[] y, long[] z)
  {
    long[] tt = Nat128.createExt64();
    implMultiply(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(long[] x, long[] y, long[] zz)
  {
    long[] tt = Nat128.createExt64();
    implMultiply(x, y, tt);
    addExt(zz, tt, zz);
  }
  
  public static void reduce(long[] xx, long[] z)
  {
    long x0 = xx[0];long x1 = xx[1];long x2 = xx[2];long x3 = xx[3];
    
    x1 ^= x3 << 15 ^ x3 << 24;
    x2 ^= x3 >>> 49 ^ x3 >>> 40;
    
    x0 ^= x2 << 15 ^ x2 << 24;
    x1 ^= x2 >>> 49 ^ x2 >>> 40;
    
    long t = x1 >>> 49;
    z[0] = (x0 ^ t ^ t << 9);
    z[1] = (x1 & 0x1FFFFFFFFFFFF);
  }
  
  public static void reduce15(long[] z, int zOff)
  {
    long z1 = z[(zOff + 1)];long t = z1 >>> 49;
    z[zOff] ^= t ^ t << 9;
    z[(zOff + 1)] = (z1 & 0x1FFFFFFFFFFFF);
  }
  
  public static void sqrt(long[] x, long[] z)
  {
    long u0 = Interleave.unshuffle(x[0]);long u1 = Interleave.unshuffle(x[1]);
    long e0 = u0 & 0xFFFFFFFF | u1 << 32;
    long c0 = u0 >>> 32 | u1 & 0xFFFFFFFF00000000;
    
    z[0] = (e0 ^ c0 << 57 ^ c0 << 5);
    z[1] = (c0 >>> 7 ^ c0 >>> 59);
  }
  
  public static void square(long[] x, long[] z)
  {
    long[] tt = Nat128.createExt64();
    implSquare(x, tt);
    reduce(tt, z);
  }
  
  public static void squareAddToExt(long[] x, long[] zz)
  {
    long[] tt = Nat128.createExt64();
    implSquare(x, tt);
    addExt(zz, tt, zz);
  }
  


  public static void squareN(long[] x, int n, long[] z)
  {
    long[] tt = Nat128.createExt64();
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
    return (int)x[0] & 0x1;
  }
  




  protected static void implMultiply(long[] x, long[] y, long[] zz)
  {
    long f0 = x[0];long f1 = x[1];
    f1 = (f0 >>> 57 ^ f1 << 7) & 0x1FFFFFFFFFFFFFF;
    f0 &= 0x1FFFFFFFFFFFFFF;
    
    long g0 = y[0];long g1 = y[1];
    g1 = (g0 >>> 57 ^ g1 << 7) & 0x1FFFFFFFFFFFFFF;
    g0 &= 0x1FFFFFFFFFFFFFF;
    
    long[] H = new long[6];
    
    implMulw(f0, g0, H, 0);
    implMulw(f1, g1, H, 2);
    implMulw(f0 ^ f1, g0 ^ g1, H, 4);
    
    long r = H[1] ^ H[2];
    long z0 = H[0];
    long z3 = H[3];
    long z1 = H[4] ^ z0 ^ r;
    long z2 = H[5] ^ z3 ^ r;
    
    zz[0] = (z0 ^ z1 << 57);
    zz[1] = (z1 >>> 7 ^ z2 << 50);
    zz[2] = (z2 >>> 14 ^ z3 << 43);
    zz[3] = (z3 >>> 21);
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
    long h = 0L;long l = u[(j & 0x7)];
    int k = 48;
    do
    {
      j = (int)(x >>> k);
      long g = u[(j & 0x7)] ^ u[(j >>> 3 & 0x7)] << 3 ^ u[(j >>> 6 & 0x7)] << 6;
      

      l ^= g << k;
      h ^= g >>> -k;
      
      k -= 9; } while (k > 0);
    
    h ^= (x & 0x100804020100800 & y << 7 >> 63) >>> 8;
    


    z[zOff] = (l & 0x1FFFFFFFFFFFFFF);
    z[(zOff + 1)] = (l >>> 57 ^ h << 7);
  }
  
  protected static void implSquare(long[] x, long[] zz)
  {
    Interleave.expand64To128(x[0], zz, 0);
    Interleave.expand64To128(x[1], zz, 2);
  }
}
