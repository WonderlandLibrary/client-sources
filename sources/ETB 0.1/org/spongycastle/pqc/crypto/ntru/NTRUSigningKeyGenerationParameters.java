package org.spongycastle.pqc.crypto.ntru;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;










public class NTRUSigningKeyGenerationParameters
  extends KeyGenerationParameters
  implements Cloneable
{
  public static final int BASIS_TYPE_STANDARD = 0;
  public static final int BASIS_TYPE_TRANSPOSE = 1;
  public static final int KEY_GEN_ALG_RESULTANT = 0;
  public static final int KEY_GEN_ALG_FLOAT = 1;
  public static final NTRUSigningKeyGenerationParameters APR2011_439 = new NTRUSigningKeyGenerationParameters(439, 2048, 146, 1, 1, 0.165D, 490.0D, 280.0D, false, true, 0, new SHA256Digest());
  



  public static final NTRUSigningKeyGenerationParameters APR2011_439_PROD = new NTRUSigningKeyGenerationParameters(439, 2048, 9, 8, 5, 1, 1, 0.165D, 490.0D, 280.0D, false, true, 0, new SHA256Digest());
  



  public static final NTRUSigningKeyGenerationParameters APR2011_743 = new NTRUSigningKeyGenerationParameters(743, 2048, 248, 1, 1, 0.127D, 560.0D, 360.0D, true, false, 0, new SHA512Digest());
  



  public static final NTRUSigningKeyGenerationParameters APR2011_743_PROD = new NTRUSigningKeyGenerationParameters(743, 2048, 11, 11, 15, 1, 1, 0.127D, 560.0D, 360.0D, true, false, 0, new SHA512Digest());
  



  public static final NTRUSigningKeyGenerationParameters TEST157 = new NTRUSigningKeyGenerationParameters(157, 256, 29, 1, 1, 0.38D, 200.0D, 80.0D, false, false, 0, new SHA256Digest());
  


  public static final NTRUSigningKeyGenerationParameters TEST157_PROD = new NTRUSigningKeyGenerationParameters(157, 256, 5, 5, 8, 1, 1, 0.38D, 200.0D, 80.0D, false, false, 0, new SHA256Digest());
  public int N;
  public int q;
  public int d;
  public int d1;
  public int d2;
  public int d3;
  public int B;
  double beta;
  public double betaSq;
  double normBound; public double normBoundSq; public int signFailTolerance = 100;
  double keyNormBound;
  public double keyNormBoundSq;
  public boolean primeCheck;
  public int basisType;
  int bitsF = 6;
  



  public boolean sparse;
  


  public int keyGenAlg;
  


  public Digest hashAlg;
  


  public int polyType;
  



  public NTRUSigningKeyGenerationParameters(int N, int q, int d, int B, int basisType, double beta, double normBound, double keyNormBound, boolean primeCheck, boolean sparse, int keyGenAlg, Digest hashAlg)
  {
    super(new SecureRandom(), N);
    this.N = N;
    this.q = q;
    this.d = d;
    this.B = B;
    this.basisType = basisType;
    this.beta = beta;
    this.normBound = normBound;
    this.keyNormBound = keyNormBound;
    this.primeCheck = primeCheck;
    this.sparse = sparse;
    this.keyGenAlg = keyGenAlg;
    this.hashAlg = hashAlg;
    polyType = 0;
    init();
  }
  


















  public NTRUSigningKeyGenerationParameters(int N, int q, int d1, int d2, int d3, int B, int basisType, double beta, double normBound, double keyNormBound, boolean primeCheck, boolean sparse, int keyGenAlg, Digest hashAlg)
  {
    super(new SecureRandom(), N);
    this.N = N;
    this.q = q;
    this.d1 = d1;
    this.d2 = d2;
    this.d3 = d3;
    this.B = B;
    this.basisType = basisType;
    this.beta = beta;
    this.normBound = normBound;
    this.keyNormBound = keyNormBound;
    this.primeCheck = primeCheck;
    this.sparse = sparse;
    this.keyGenAlg = keyGenAlg;
    this.hashAlg = hashAlg;
    polyType = 1;
    init();
  }
  
  private void init()
  {
    betaSq = (beta * beta);
    normBoundSq = (normBound * normBound);
    keyNormBoundSq = (keyNormBound * keyNormBound);
  }
  






  public NTRUSigningKeyGenerationParameters(InputStream is)
    throws IOException
  {
    super(new SecureRandom(), 0);
    DataInputStream dis = new DataInputStream(is);
    N = dis.readInt();
    q = dis.readInt();
    d = dis.readInt();
    d1 = dis.readInt();
    d2 = dis.readInt();
    d3 = dis.readInt();
    B = dis.readInt();
    basisType = dis.readInt();
    beta = dis.readDouble();
    normBound = dis.readDouble();
    keyNormBound = dis.readDouble();
    signFailTolerance = dis.readInt();
    primeCheck = dis.readBoolean();
    sparse = dis.readBoolean();
    bitsF = dis.readInt();
    keyGenAlg = dis.read();
    String alg = dis.readUTF();
    if ("SHA-512".equals(alg))
    {
      hashAlg = new SHA512Digest();
    }
    else if ("SHA-256".equals(alg))
    {
      hashAlg = new SHA256Digest();
    }
    polyType = dis.read();
    init();
  }
  






  public void writeTo(OutputStream os)
    throws IOException
  {
    DataOutputStream dos = new DataOutputStream(os);
    dos.writeInt(N);
    dos.writeInt(q);
    dos.writeInt(d);
    dos.writeInt(d1);
    dos.writeInt(d2);
    dos.writeInt(d3);
    dos.writeInt(B);
    dos.writeInt(basisType);
    dos.writeDouble(beta);
    dos.writeDouble(normBound);
    dos.writeDouble(keyNormBound);
    dos.writeInt(signFailTolerance);
    dos.writeBoolean(primeCheck);
    dos.writeBoolean(sparse);
    dos.writeInt(bitsF);
    dos.write(keyGenAlg);
    dos.writeUTF(hashAlg.getAlgorithmName());
    dos.write(polyType);
  }
  
  public NTRUSigningParameters getSigningParameters()
  {
    return new NTRUSigningParameters(N, q, d, B, beta, normBound, hashAlg);
  }
  
  public NTRUSigningKeyGenerationParameters clone()
  {
    if (polyType == 0)
    {
      return new NTRUSigningKeyGenerationParameters(N, q, d, B, basisType, beta, normBound, keyNormBound, primeCheck, sparse, keyGenAlg, hashAlg);
    }
    

    return new NTRUSigningKeyGenerationParameters(N, q, d1, d2, d3, B, basisType, beta, normBound, keyNormBound, primeCheck, sparse, keyGenAlg, hashAlg);
  }
  

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + B;
    result = 31 * result + N;
    result = 31 * result + basisType;
    
    long temp = Double.doubleToLongBits(beta);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(betaSq);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    result = 31 * result + bitsF;
    result = 31 * result + d;
    result = 31 * result + d1;
    result = 31 * result + d2;
    result = 31 * result + d3;
    result = 31 * result + (hashAlg == null ? 0 : hashAlg.getAlgorithmName().hashCode());
    result = 31 * result + keyGenAlg;
    temp = Double.doubleToLongBits(keyNormBound);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(keyNormBoundSq);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(normBound);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(normBoundSq);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    result = 31 * result + polyType;
    result = 31 * result + (primeCheck ? 1231 : 1237);
    result = 31 * result + q;
    result = 31 * result + signFailTolerance;
    result = 31 * result + (sparse ? 1231 : 1237);
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (!(obj instanceof NTRUSigningKeyGenerationParameters))
    {
      return false;
    }
    NTRUSigningKeyGenerationParameters other = (NTRUSigningKeyGenerationParameters)obj;
    if (B != B)
    {
      return false;
    }
    if (N != N)
    {
      return false;
    }
    if (basisType != basisType)
    {
      return false;
    }
    if (Double.doubleToLongBits(beta) != Double.doubleToLongBits(beta))
    {
      return false;
    }
    if (Double.doubleToLongBits(betaSq) != Double.doubleToLongBits(betaSq))
    {
      return false;
    }
    if (bitsF != bitsF)
    {
      return false;
    }
    if (d != d)
    {
      return false;
    }
    if (d1 != d1)
    {
      return false;
    }
    if (d2 != d2)
    {
      return false;
    }
    if (d3 != d3)
    {
      return false;
    }
    if (hashAlg == null)
    {
      if (hashAlg != null)
      {
        return false;
      }
    }
    else if (!hashAlg.getAlgorithmName().equals(hashAlg.getAlgorithmName()))
    {
      return false;
    }
    if (keyGenAlg != keyGenAlg)
    {
      return false;
    }
    if (Double.doubleToLongBits(keyNormBound) != Double.doubleToLongBits(keyNormBound))
    {
      return false;
    }
    if (Double.doubleToLongBits(keyNormBoundSq) != Double.doubleToLongBits(keyNormBoundSq))
    {
      return false;
    }
    if (Double.doubleToLongBits(normBound) != Double.doubleToLongBits(normBound))
    {
      return false;
    }
    if (Double.doubleToLongBits(normBoundSq) != Double.doubleToLongBits(normBoundSq))
    {
      return false;
    }
    if (polyType != polyType)
    {
      return false;
    }
    if (primeCheck != primeCheck)
    {
      return false;
    }
    if (q != q)
    {
      return false;
    }
    if (signFailTolerance != signFailTolerance)
    {
      return false;
    }
    if (sparse != sparse)
    {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    DecimalFormat format = new DecimalFormat("0.00");
    
    StringBuilder output = new StringBuilder("SignatureParameters(N=" + N + " q=" + q);
    if (polyType == 0)
    {
      output.append(" polyType=SIMPLE d=" + d);
    }
    else
    {
      output.append(" polyType=PRODUCT d1=" + d1 + " d2=" + d2 + " d3=" + d3);
    }
    output.append(" B=" + B + " basisType=" + basisType + " beta=" + format.format(beta) + " normBound=" + format
      .format(normBound) + " keyNormBound=" + format.format(keyNormBound) + " prime=" + primeCheck + " sparse=" + sparse + " keyGenAlg=" + keyGenAlg + " hashAlg=" + hashAlg + ")");
    
    return output.toString();
  }
}
