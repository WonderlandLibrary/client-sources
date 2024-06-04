package org.spongycastle.pqc.crypto.ntru;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.SparseTernaryPolynomial;
















public class NTRUEncryptionPrivateKeyParameters
  extends NTRUEncryptionKeyParameters
{
  public Polynomial t;
  public IntegerPolynomial fp;
  public IntegerPolynomial h;
  
  public NTRUEncryptionPrivateKeyParameters(IntegerPolynomial h, Polynomial t, IntegerPolynomial fp, NTRUEncryptionParameters params)
  {
    super(true, params);
    
    this.h = h;
    this.t = t;
    this.fp = fp;
  }
  







  public NTRUEncryptionPrivateKeyParameters(byte[] b, NTRUEncryptionParameters params)
    throws IOException
  {
    this(new ByteArrayInputStream(b), params);
  }
  







  public NTRUEncryptionPrivateKeyParameters(InputStream is, NTRUEncryptionParameters params)
    throws IOException
  {
    super(true, params);
    
    if (polyType == 1)
    {
      int N = N;
      int df1 = df1;
      int df2 = df2;
      int df3Ones = df3;
      int df3NegOnes = fastFp ? df3 : df3 - 1;
      h = IntegerPolynomial.fromBinary(is, N, q);
      t = ProductFormPolynomial.fromBinary(is, N, df1, df2, df3Ones, df3NegOnes);
    }
    else
    {
      h = IntegerPolynomial.fromBinary(is, N, q);
      IntegerPolynomial fInt = IntegerPolynomial.fromBinary3Tight(is, N);
      t = (sparse ? new SparseTernaryPolynomial(fInt) : new DenseTernaryPolynomial(fInt));
    }
    
    init();
  }
  



  private void init()
  {
    if (params.fastFp)
    {
      fp = new IntegerPolynomial(params.N);
      fp.coeffs[0] = 1;
    }
    else
    {
      fp = t.toIntegerPolynomial().invertF3();
    }
  }
  






  public byte[] getEncoded()
  {
    byte[] hBytes = h.toBinary(params.q);
    byte[] tBytes;
    byte[] tBytes;
    if ((t instanceof ProductFormPolynomial))
    {
      tBytes = ((ProductFormPolynomial)t).toBinary();
    }
    else
    {
      tBytes = t.toIntegerPolynomial().toBinary3Tight();
    }
    
    byte[] res = new byte[hBytes.length + tBytes.length];
    
    System.arraycopy(hBytes, 0, res, 0, hBytes.length);
    System.arraycopy(tBytes, 0, res, hBytes.length, tBytes.length);
    
    return res;
  }
  







  public void writeTo(OutputStream os)
    throws IOException
  {
    os.write(getEncoded());
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (params == null ? 0 : params.hashCode());
    result = 31 * result + (t == null ? 0 : t.hashCode());
    result = 31 * result + (h == null ? 0 : h.hashCode());
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
    if (!(obj instanceof NTRUEncryptionPrivateKeyParameters))
    {
      return false;
    }
    NTRUEncryptionPrivateKeyParameters other = (NTRUEncryptionPrivateKeyParameters)obj;
    if (params == null)
    {
      if (params != null)
      {
        return false;
      }
    }
    else if (!params.equals(params))
    {
      return false;
    }
    if (t == null)
    {
      if (t != null)
      {
        return false;
      }
    }
    else if (!t.equals(t))
    {
      return false;
    }
    if (!h.equals(h))
    {
      return false;
    }
    return true;
  }
}
