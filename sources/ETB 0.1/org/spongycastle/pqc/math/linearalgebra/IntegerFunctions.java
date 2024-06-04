package org.spongycastle.pqc.math.linearalgebra;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;





public final class IntegerFunctions
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  
  private static final BigInteger FOUR = BigInteger.valueOf(4L);
  
  private static final int[] SMALL_PRIMES = { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 };
  

  private static final long SMALL_PRIME_PRODUCT = 152125131763605L;
  

  private static SecureRandom sr = null;
  

  private static final int[] jacobiTable = { 0, 1, 0, -1, 0, -1, 0, 1 };
  











  private IntegerFunctions() {}
  











  public static int jacobi(BigInteger A, BigInteger B)
  {
    long k = 1L;
    
    k = 1L;
    

    if (B.equals(ZERO))
    {
      BigInteger a = A.abs();
      return a.equals(ONE) ? 1 : 0;
    }
    
    if ((!A.testBit(0)) && (!B.testBit(0)))
    {
      return 0;
    }
    
    BigInteger a = A;
    BigInteger b = B;
    
    if (b.signum() == -1)
    {
      b = b.negate();
      if (a.signum() == -1)
      {
        k = -1L;
      }
    }
    
    BigInteger v = ZERO;
    while (!b.testBit(0))
    {
      v = v.add(ONE);
      b = b.divide(TWO);
    }
    
    if (v.testBit(0))
    {
      k *= jacobiTable[(a.intValue() & 0x7)];
    }
    
    if (a.signum() < 0)
    {
      if (b.testBit(1))
      {
        k = -k;
      }
      a = a.negate();
    }
    

    while (a.signum() != 0)
    {
      v = ZERO;
      while (!a.testBit(0))
      {
        v = v.add(ONE);
        a = a.divide(TWO);
      }
      if (v.testBit(0))
      {
        k *= jacobiTable[(b.intValue() & 0x7)];
      }
      
      if (a.compareTo(b) < 0)
      {

        BigInteger x = a;
        a = b;
        b = x;
        if ((a.testBit(1)) && (b.testBit(1)))
        {
          k = -k;
        }
      }
      a = a.subtract(b);
    }
    
    return b.equals(ONE) ? (int)k : 0;
  }
  











  public static BigInteger ressol(BigInteger a, BigInteger p)
    throws IllegalArgumentException
  {
    BigInteger v = null;
    
    if (a.compareTo(ZERO) < 0)
    {
      a = a.add(p);
    }
    
    if (a.equals(ZERO))
    {
      return ZERO;
    }
    
    if (p.equals(TWO))
    {
      return a;
    }
    

    if ((p.testBit(0)) && (p.testBit(1)))
    {
      if (jacobi(a, p) == 1)
      {
        v = p.add(ONE);
        v = v.shiftRight(2);
        return a.modPow(v, p);
      }
      
      throw new IllegalArgumentException("No quadratic residue: " + a + ", " + p);
    }
    
    long t = 0L;
    



    BigInteger k = p.subtract(ONE);
    long s = 0L;
    while (!k.testBit(0))
    {
      s += 1L;
      k = k.shiftRight(1);
    }
    
    k = k.subtract(ONE);
    k = k.shiftRight(1);
    

    BigInteger r = a.modPow(k, p);
    
    BigInteger n = r.multiply(r).remainder(p);
    n = n.multiply(a).remainder(p);
    r = r.multiply(a).remainder(p);
    
    if (n.equals(ONE))
    {
      return r;
    }
    

    BigInteger z = TWO;
    while (jacobi(z, p) == 1)
    {

      z = z.add(ONE);
    }
    
    v = k;
    v = v.multiply(TWO);
    v = v.add(ONE);
    BigInteger c = z.modPow(v, p);
    

    while (n.compareTo(ONE) == 1)
    {
      k = n;
      t = s;
      s = 0L;
      
      while (!k.equals(ONE))
      {
        k = k.multiply(k).mod(p);
        s += 1L;
      }
      
      t -= s;
      if (t == 0L)
      {
        throw new IllegalArgumentException("No quadratic residue: " + a + ", " + p);
      }
      
      v = ONE;
      for (long i = 0L; i < t - 1L; i += 1L)
      {
        v = v.shiftLeft(1);
      }
      c = c.modPow(v, p);
      r = r.multiply(c).remainder(p);
      c = c.multiply(c).remainder(p);
      n = n.multiply(c).mod(p);
    }
    return r;
  }
  







  public static int gcd(int u, int v)
  {
    return BigInteger.valueOf(u).gcd(BigInteger.valueOf(v)).intValue();
  }
  







  public static int[] extGCD(int a, int b)
  {
    BigInteger ba = BigInteger.valueOf(a);
    BigInteger bb = BigInteger.valueOf(b);
    BigInteger[] bresult = extgcd(ba, bb);
    int[] result = new int[3];
    result[0] = bresult[0].intValue();
    result[1] = bresult[1].intValue();
    result[2] = bresult[2].intValue();
    return result;
  }
  
  public static BigInteger divideAndRound(BigInteger a, BigInteger b)
  {
    if (a.signum() < 0)
    {
      return divideAndRound(a.negate(), b).negate();
    }
    if (b.signum() < 0)
    {
      return divideAndRound(a, b.negate()).negate();
    }
    return a.shiftLeft(1).add(b).divide(b.shiftLeft(1));
  }
  
  public static BigInteger[] divideAndRound(BigInteger[] a, BigInteger b)
  {
    BigInteger[] out = new BigInteger[a.length];
    for (int i = 0; i < a.length; i++)
    {
      out[i] = divideAndRound(a[i], b);
    }
    return out;
  }
  







  public static int ceilLog(BigInteger a)
  {
    int result = 0;
    BigInteger p = ONE;
    while (p.compareTo(a) < 0)
    {
      result++;
      p = p.shiftLeft(1);
    }
    return result;
  }
  







  public static int ceilLog(int a)
  {
    int log = 0;
    int i = 1;
    while (i < a)
    {
      i <<= 1;
      log++;
    }
    return log;
  }
  







  public static int ceilLog256(int n)
  {
    if (n == 0)
    {
      return 1; }
    int m;
    int m;
    if (n < 0)
    {
      m = -n;
    }
    else
    {
      m = n;
    }
    
    int d = 0;
    while (m > 0)
    {
      d++;
      m >>>= 8;
    }
    return d;
  }
  







  public static int ceilLog256(long n)
  {
    if (n == 0L)
    {
      return 1; }
    long m;
    long m;
    if (n < 0L)
    {
      m = -n;
    }
    else
    {
      m = n;
    }
    
    int d = 0;
    while (m > 0L)
    {
      d++;
      m >>>= 8;
    }
    return d;
  }
  







  public static int floorLog(BigInteger a)
  {
    int result = -1;
    BigInteger p = ONE;
    while (p.compareTo(a) <= 0)
    {
      result++;
      p = p.shiftLeft(1);
    }
    return result;
  }
  







  public static int floorLog(int a)
  {
    int h = 0;
    if (a <= 0)
    {
      return -1;
    }
    int p = a >>> 1;
    while (p > 0)
    {
      h++;
      p >>>= 1;
    }
    
    return h;
  }
  







  public static int maxPower(int a)
  {
    int h = 0;
    if (a != 0)
    {
      int p = 1;
      while ((a & p) == 0)
      {
        h++;
        p <<= 1;
      }
    }
    
    return h;
  }
  





  public static int bitCount(int a)
  {
    int h = 0;
    while (a != 0)
    {
      h += (a & 0x1);
      a >>>= 1;
    }
    
    return h;
  }
  











  public static int order(int g, int p)
  {
    int b = g % p;
    int j = 1;
    

    if (b == 0)
    {
      throw new IllegalArgumentException(g + " is not an element of Z/(" + p + "Z)^*; it is not meaningful to compute its order.");
    }
    


    while (b != 1)
    {
      b *= g;
      b %= p;
      if (b < 0)
      {
        b += p;
      }
      j++;
    }
    
    return j;
  }
  









  public static BigInteger reduceInto(BigInteger n, BigInteger begin, BigInteger end)
  {
    return n.subtract(begin).mod(end.subtract(begin)).add(begin);
  }
  







  public static int pow(int a, int e)
  {
    int result = 1;
    while (e > 0)
    {
      if ((e & 0x1) == 1)
      {
        result *= a;
      }
      a *= a;
      e >>>= 1;
    }
    return result;
  }
  







  public static long pow(long a, int e)
  {
    long result = 1L;
    while (e > 0)
    {
      if ((e & 0x1) == 1)
      {
        result *= a;
      }
      a *= a;
      e >>>= 1;
    }
    return result;
  }
  








  public static int modPow(int a, int e, int n)
  {
    if ((n <= 0) || (n * n > Integer.MAX_VALUE) || (e < 0))
    {
      return 0;
    }
    int result = 1;
    a = (a % n + n) % n;
    while (e > 0)
    {
      if ((e & 0x1) == 1)
      {
        result = result * a % n;
      }
      a = a * a % n;
      e >>>= 1;
    }
    return result;
  }
  







  public static BigInteger[] extgcd(BigInteger a, BigInteger b)
  {
    BigInteger u = ONE;
    BigInteger v = ZERO;
    BigInteger d = a;
    if (b.signum() != 0)
    {
      BigInteger v1 = ZERO;
      BigInteger v3 = b;
      while (v3.signum() != 0)
      {
        BigInteger[] tmp = d.divideAndRemainder(v3);
        BigInteger q = tmp[0];
        BigInteger t3 = tmp[1];
        BigInteger t1 = u.subtract(q.multiply(v1));
        u = v1;
        d = v3;
        v1 = t1;
        v3 = t3;
      }
      v = d.subtract(a.multiply(u)).divide(b);
    }
    return new BigInteger[] { d, u, v };
  }
  






  public static BigInteger leastCommonMultiple(BigInteger[] numbers)
  {
    int n = numbers.length;
    BigInteger result = numbers[0];
    for (int i = 1; i < n; i++)
    {
      BigInteger gcd = result.gcd(numbers[i]);
      result = result.multiply(numbers[i]).divide(gcd);
    }
    return result;
  }
  









  public static long mod(long a, long m)
  {
    long result = a % m;
    if (result < 0L)
    {
      result += m;
    }
    return result;
  }
  







  public static int modInverse(int a, int mod)
  {
    return 
      BigInteger.valueOf(a).modInverse(BigInteger.valueOf(mod)).intValue();
  }
  







  public static long modInverse(long a, long mod)
  {
    return 
      BigInteger.valueOf(a).modInverse(BigInteger.valueOf(mod)).longValue();
  }
  








  public static int isPower(int a, int p)
  {
    if (a <= 0)
    {
      return -1;
    }
    int n = 0;
    int d = a;
    while (d > 1)
    {
      if (d % p != 0)
      {
        return -1;
      }
      d /= p;
      n++;
    }
    return n;
  }
  






  public static int leastDiv(int a)
  {
    if (a < 0)
    {
      a = -a;
    }
    if (a == 0)
    {
      return 1;
    }
    if ((a & 0x1) == 0)
    {
      return 2;
    }
    int p = 3;
    while (p <= a / p)
    {
      if (a % p == 0)
      {
        return p;
      }
      p += 2;
    }
    
    return a;
  }
  









  public static boolean isPrime(int n)
  {
    if (n < 2)
    {
      return false;
    }
    if (n == 2)
    {
      return true;
    }
    if ((n & 0x1) == 0)
    {
      return false;
    }
    if (n < 42)
    {
      for (int i = 0; i < SMALL_PRIMES.length; i++)
      {
        if (n == SMALL_PRIMES[i])
        {
          return true;
        }
      }
    }
    
    if ((n % 3 == 0) || (n % 5 == 0) || (n % 7 == 0) || (n % 11 == 0) || (n % 13 == 0) || (n % 17 == 0) || (n % 19 == 0) || (n % 23 == 0) || (n % 29 == 0) || (n % 31 == 0) || (n % 37 == 0) || (n % 41 == 0))
    {



      return false;
    }
    
    return BigInteger.valueOf(n).isProbablePrime(20);
  }
  








  public static boolean passesSmallPrimeTest(BigInteger candidate)
  {
    int[] smallPrime = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499 };
    




















    for (int i = 0; i < smallPrime.length; i++)
    {
      if (candidate.mod(BigInteger.valueOf(smallPrime[i])).equals(ZERO))
      {

        return false;
      }
    }
    return true;
  }
  







  public static int nextSmallerPrime(int n)
  {
    if (n <= 2)
    {
      return 1;
    }
    
    if (n == 3)
    {
      return 2;
    }
    
    if ((n & 0x1) == 0)
    {
      n--;
    }
    else
    {
      n -= 2;
    }
    
    while (((n > 3 ? 1 : 0) & (!isPrime(n) ? 1 : 0)) != 0)
    {
      n -= 2;
    }
    return n;
  }
  









  public static BigInteger nextProbablePrime(BigInteger n, int certainty)
  {
    if ((n.signum() < 0) || (n.signum() == 0) || (n.equals(ONE)))
    {
      return TWO;
    }
    
    BigInteger result = n.add(ONE);
    

    if (!result.testBit(0))
    {
      result = result.add(ONE);
    }
    

    for (;;)
    {
      if (result.bitLength() > 6)
      {

        long r = result.remainder(BigInteger.valueOf(152125131763605L)).longValue();
        if ((r % 3L == 0L) || (r % 5L == 0L) || (r % 7L == 0L) || (r % 11L == 0L) || (r % 13L == 0L) || (r % 17L == 0L) || (r % 19L == 0L) || (r % 23L == 0L) || (r % 29L == 0L) || (r % 31L == 0L) || (r % 37L == 0L) || (r % 41L == 0L))
        {



          result = result.add(TWO);
          continue;
        }
      }
      

      if (result.bitLength() < 4)
      {
        return result;
      }
      

      if (result.isProbablePrime(certainty))
      {
        return result;
      }
      
      result = result.add(TWO);
    }
  }
  







  public static BigInteger nextProbablePrime(BigInteger n)
  {
    return nextProbablePrime(n, 20);
  }
  







  public static BigInteger nextPrime(long n)
  {
    boolean found = false;
    long result = 0L;
    
    if (n <= 1L)
    {
      return BigInteger.valueOf(2L);
    }
    if (n == 2L)
    {
      return BigInteger.valueOf(3L);
    }
    
    for (long i = n + 1L + (n & 1L); (i <= n << 1) && (!found); i += 2L)
    {
      for (long j = 3L; (j <= i >> 1) && (!found); j += 2L)
      {
        if (i % j == 0L)
        {
          found = true;
        }
      }
      if (found)
      {
        found = false;
      }
      else
      {
        result = i;
        found = true;
      }
    }
    return BigInteger.valueOf(result);
  }
  













  public static BigInteger binomial(int n, int t)
  {
    BigInteger result = ONE;
    
    if (n == 0)
    {
      if (t == 0)
      {
        return result;
      }
      return ZERO;
    }
    

    if (t > n >>> 1)
    {
      t = n - t;
    }
    
    for (int i = 1; i <= t; i++)
    {

      result = result.multiply(BigInteger.valueOf(n - (i - 1))).divide(BigInteger.valueOf(i));
    }
    
    return result;
  }
  
  public static BigInteger randomize(BigInteger upperBound)
  {
    if (sr == null)
    {
      sr = new SecureRandom();
    }
    return randomize(upperBound, sr);
  }
  

  public static BigInteger randomize(BigInteger upperBound, SecureRandom prng)
  {
    int blen = upperBound.bitLength();
    BigInteger randomNum = BigInteger.valueOf(0L);
    
    if (prng == null)
    {
      prng = sr != null ? sr : new SecureRandom();
    }
    
    for (int i = 0; i < 20; i++)
    {
      randomNum = new BigInteger(blen, prng);
      if (randomNum.compareTo(upperBound) < 0)
      {
        return randomNum;
      }
    }
    return randomNum.mod(upperBound);
  }
  









  public static BigInteger squareRoot(BigInteger a)
  {
    if (a.compareTo(ZERO) < 0)
    {
      throw new ArithmeticException("cannot extract root of negative number" + a + ".");
    }
    

    int bl = a.bitLength();
    BigInteger result = ZERO;
    BigInteger remainder = ZERO;
    

    if ((bl & 0x1) != 0)
    {
      result = result.add(ONE);
      bl--;
    }
    
    while (bl > 0)
    {
      remainder = remainder.multiply(FOUR);
      remainder = remainder.add(BigInteger.valueOf((a.testBit(--bl) ? 2 : 0) + (a
      
        .testBit(--bl) ? 1 : 0)));
      BigInteger b = result.multiply(FOUR).add(ONE);
      result = result.multiply(TWO);
      if (remainder.compareTo(b) != -1)
      {
        result = result.add(ONE);
        remainder = remainder.subtract(b);
      }
    }
    
    return result;
  }
  







  public static float intRoot(int base, int root)
  {
    float gNew = base / root;
    float gOld = 0.0F;
    int counter = 0;
    while (Math.abs(gOld - gNew) > 1.0E-4D)
    {
      float gPow = floatPow(gNew, root);
      while (Float.isInfinite(gPow))
      {
        gNew = (gNew + gOld) / 2.0F;
        gPow = floatPow(gNew, root);
      }
      counter++;
      gOld = gNew;
      gNew = gOld - (gPow - base) / (root * floatPow(gOld, root - 1));
    }
    return gNew;
  }
  







  public static float floatPow(float f, int i)
  {
    float g = 1.0F;
    for (; i > 0; i--)
    {
      g *= f;
    }
    return g;
  }
  




  /**
   * @deprecated
   */
  public static double log(double x)
  {
    if ((x > 0.0D) && (x < 1.0D))
    {
      double d = 1.0D / x;
      double result = -log(d);
      return result;
    }
    
    int tmp = 0;
    double tmp2 = 1.0D;
    double d = x;
    
    while (d > 2.0D)
    {
      d /= 2.0D;
      tmp++;
      tmp2 *= 2.0D;
    }
    double rem = x / tmp2;
    rem = logBKM(rem);
    return tmp + rem;
  }
  




  /**
   * @deprecated
   */
  public static double log(long x)
  {
    int tmp = floorLog(BigInteger.valueOf(x));
    long tmp2 = 1 << tmp;
    double rem = x / tmp2;
    rem = logBKM(rem);
    return tmp + rem;
  }
  




  /**
   * @deprecated
   */
  private static double logBKM(double arg)
  {
    double[] ae = { 1.0D, 0.5849625007211562D, 0.32192809488736235D, 0.16992500144231237D, 0.0874628412503394D, 0.044394119358453436D, 0.02236781302845451D, 0.01122725542325412D, 0.005624549193878107D, 0.0028150156070540383D, 0.0014081943928083889D, 7.042690112466433E-4D, 3.5217748030102726E-4D, 1.7609948644250602E-4D, 8.80524301221769E-5D, 4.4026886827316716E-5D, 2.2013611360340496E-5D, 1.1006847667481442E-5D, 5.503434330648604E-6D, 2.751719789561283E-6D, 1.375860550841138E-6D, 6.879304394358497E-7D, 3.4396526072176454E-7D, 1.7198264061184464E-7D, 8.599132286866321E-8D, 4.299566207501687E-8D, 2.1497831197679756E-8D, 1.0748915638882709E-8D, 5.374457829452062E-9D, 2.687228917228708E-9D, 1.3436144592400231E-9D, 6.718072297764289E-10D, 3.3590361492731876E-10D, 1.6795180747343547E-10D, 8.397590373916176E-11D, 4.1987951870191886E-11D, 2.0993975935248694E-11D, 1.0496987967662534E-11D, 5.2484939838408146E-12D, 2.624246991922794E-12D, 1.3121234959619935E-12D, 6.56061747981146E-13D, 3.2803087399061026E-13D, 1.6401543699531447E-13D, 8.200771849765956E-14D, 4.1003859248830365E-14D, 2.0501929624415328E-14D, 1.02509648122077E-14D, 5.1254824061038595E-15D, 2.5627412030519317E-15D, 1.2813706015259665E-15D, 6.406853007629834E-16D, 3.203426503814917E-16D, 1.6017132519074588E-16D, 8.008566259537294E-17D, 4.004283129768647E-17D, 2.0021415648843235E-17D, 1.0010707824421618E-17D, 5.005353912210809E-18D, 2.5026769561054044E-18D, 1.2513384780527022E-18D, 6.256692390263511E-19D, 3.1283461951317555E-19D, 1.5641730975658778E-19D, 7.820865487829389E-20D, 3.9104327439146944E-20D, 1.9552163719573472E-20D, 9.776081859786736E-21D, 4.888040929893368E-21D, 2.444020464946684E-21D, 1.222010232473342E-21D, 6.11005116236671E-22D, 3.055025581183355E-22D, 1.5275127905916775E-22D, 7.637563952958387E-23D, 3.818781976479194E-23D, 1.909390988239597E-23D, 9.546954941197984E-24D, 4.773477470598992E-24D, 2.386738735299496E-24D, 1.193369367649748E-24D, 5.96684683824874E-25D, 2.98342341912437E-25D, 1.491711709562185E-25D, 7.458558547810925E-26D, 3.7292792739054626E-26D, 1.8646396369527313E-26D, 9.323198184763657E-27D, 4.661599092381828E-27D, 2.330799546190914E-27D, 1.165399773095457E-27D, 5.826998865477285E-28D, 2.9134994327386427E-28D, 1.4567497163693213E-28D, 7.283748581846607E-29D, 3.6418742909233034E-29D, 1.8209371454616517E-29D, 9.104685727308258E-30D, 4.552342863654129E-30D, 2.2761714318270646E-30D };
    




































































































    int n = 53;
    double x = 1.0D;
    double y = 0.0D;
    
    double s = 1.0D;
    

    for (int k = 0; k < n; k++)
    {
      double z = x + x * s;
      if (z <= arg)
      {
        x = z;
        y += ae[k];
      }
      s *= 0.5D;
    }
    return y;
  }
  
  public static boolean isIncreasing(int[] a)
  {
    for (int i = 1; i < a.length; i++)
    {
      if (a[(i - 1)] >= a[i])
      {
        System.out.println("a[" + (i - 1) + "] = " + a[(i - 1)] + " >= " + a[i] + " = a[" + i + "]");
        
        return false;
      }
    }
    return true;
  }
  
  public static byte[] integerToOctets(BigInteger val)
  {
    byte[] valBytes = val.abs().toByteArray();
    

    if ((val.bitLength() & 0x7) != 0)
    {
      return valBytes;
    }
    
    byte[] tmp = new byte[val.bitLength() >> 3];
    System.arraycopy(valBytes, 1, tmp, 0, tmp.length);
    return tmp;
  }
  

  public static BigInteger octetsToInteger(byte[] data, int offset, int length)
  {
    byte[] val = new byte[length + 1];
    
    val[0] = 0;
    System.arraycopy(data, offset, val, 1, length);
    return new BigInteger(val);
  }
  
  public static BigInteger octetsToInteger(byte[] data)
  {
    return octetsToInteger(data, 0, data.length);
  }
}
