package org.spongycastle.math.raw;











public class Interleave
{
  private static final long M32 = 1431655765L;
  









  private static final long M64 = 6148914691236517205L;
  










  public Interleave() {}
  










  public static int expand8to16(int x)
  {
    x &= 0xFF;
    x = (x | x << 4) & 0xF0F;
    x = (x | x << 2) & 0x3333;
    x = (x | x << 1) & 0x5555;
    return x;
  }
  
  public static int expand16to32(int x)
  {
    x &= 0xFFFF;
    x = (x | x << 8) & 0xFF00FF;
    x = (x | x << 4) & 0xF0F0F0F;
    x = (x | x << 2) & 0x33333333;
    x = (x | x << 1) & 0x55555555;
    return x;
  }
  


  public static long expand32to64(int x)
  {
    int t = (x ^ x >>> 8) & 0xFF00;x ^= t ^ t << 8;
    t = (x ^ x >>> 4) & 0xF000F0;x ^= t ^ t << 4;
    t = (x ^ x >>> 2) & 0xC0C0C0C;x ^= t ^ t << 2;
    t = (x ^ x >>> 1) & 0x22222222;x ^= t ^ t << 1;
    
    return (x >>> 1 & 0x55555555) << 32 | x & 0x55555555;
  }
  


  public static void expand64To128(long x, long[] z, int zOff)
  {
    long t = (x ^ x >>> 16) & 0xFFFF0000;x ^= t ^ t << 16;
    t = (x ^ x >>> 8) & 0xFF000000FF00;x ^= t ^ t << 8;
    t = (x ^ x >>> 4) & 0xF000F000F000F0;x ^= t ^ t << 4;
    t = (x ^ x >>> 2) & 0xC0C0C0C0C0C0C0C;x ^= t ^ t << 2;
    t = (x ^ x >>> 1) & 0x2222222222222222;x ^= t ^ t << 1;
    
    z[zOff] = (x & 0x5555555555555555);
    z[(zOff + 1)] = (x >>> 1 & 0x5555555555555555);
  }
  


  public static long unshuffle(long x)
  {
    long t = (x ^ x >>> 1) & 0x2222222222222222;x ^= t ^ t << 1;
    t = (x ^ x >>> 2) & 0xC0C0C0C0C0C0C0C;x ^= t ^ t << 2;
    t = (x ^ x >>> 4) & 0xF000F000F000F0;x ^= t ^ t << 4;
    t = (x ^ x >>> 8) & 0xFF000000FF00;x ^= t ^ t << 8;
    t = (x ^ x >>> 16) & 0xFFFF0000;x ^= t ^ t << 16;
    return x;
  }
}
