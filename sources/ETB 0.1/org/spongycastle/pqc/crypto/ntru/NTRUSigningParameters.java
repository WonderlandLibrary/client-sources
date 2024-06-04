package org.spongycastle.pqc.crypto.ntru;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUSigningParameters
  implements Cloneable
{
  public int N;
  public int q;
  public int d;
  public int d1;
  public int d2;
  public int d3;
  public int B;
  double beta;
  public double betaSq;
  double normBound;
  public double normBoundSq;
  public int signFailTolerance = 100;
  int bitsF = 6;
  





  public Digest hashAlg;
  





  public NTRUSigningParameters(int N, int q, int d, int B, double beta, double normBound, Digest hashAlg)
  {
    this.N = N;
    this.q = q;
    this.d = d;
    this.B = B;
    this.beta = beta;
    this.normBound = normBound;
    this.hashAlg = hashAlg;
    init();
  }
  














  public NTRUSigningParameters(int N, int q, int d1, int d2, int d3, int B, double beta, double normBound, double keyNormBound, Digest hashAlg)
  {
    this.N = N;
    this.q = q;
    this.d1 = d1;
    this.d2 = d2;
    this.d3 = d3;
    this.B = B;
    this.beta = beta;
    this.normBound = normBound;
    this.hashAlg = hashAlg;
    init();
  }
  
  private void init()
  {
    betaSq = (beta * beta);
    normBoundSq = (normBound * normBound);
  }
  






  public NTRUSigningParameters(InputStream is)
    throws IOException
  {
    DataInputStream dis = new DataInputStream(is);
    N = dis.readInt();
    q = dis.readInt();
    d = dis.readInt();
    d1 = dis.readInt();
    d2 = dis.readInt();
    d3 = dis.readInt();
    B = dis.readInt();
    beta = dis.readDouble();
    normBound = dis.readDouble();
    signFailTolerance = dis.readInt();
    bitsF = dis.readInt();
    String alg = dis.readUTF();
    if ("SHA-512".equals(alg))
    {
      hashAlg = new SHA512Digest();
    }
    else if ("SHA-256".equals(alg))
    {
      hashAlg = new SHA256Digest();
    }
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
    dos.writeDouble(beta);
    dos.writeDouble(normBound);
    dos.writeInt(signFailTolerance);
    dos.writeInt(bitsF);
    dos.writeUTF(hashAlg.getAlgorithmName());
  }
  
  public NTRUSigningParameters clone()
  {
    return new NTRUSigningParameters(N, q, d, B, beta, normBound, hashAlg);
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + B;
    result = 31 * result + N;
    
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
    temp = Double.doubleToLongBits(normBound);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(normBoundSq);
    result = 31 * result + (int)(temp ^ temp >>> 32);
    result = 31 * result + q;
    result = 31 * result + signFailTolerance;
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
    if (!(obj instanceof NTRUSigningParameters))
    {
      return false;
    }
    NTRUSigningParameters other = (NTRUSigningParameters)obj;
    if (B != B)
    {
      return false;
    }
    if (N != N)
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
    if (Double.doubleToLongBits(normBound) != Double.doubleToLongBits(normBound))
    {
      return false;
    }
    if (Double.doubleToLongBits(normBoundSq) != Double.doubleToLongBits(normBoundSq))
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
    
    return true;
  }
  
  public String toString()
  {
    DecimalFormat format = new DecimalFormat("0.00");
    
    StringBuilder output = new StringBuilder("SignatureParameters(N=" + N + " q=" + q);
    
    output.append(" B=" + B + " beta=" + format.format(beta) + " normBound=" + format
      .format(normBound) + " hashAlg=" + hashAlg + ")");
    
    return output.toString();
  }
}
