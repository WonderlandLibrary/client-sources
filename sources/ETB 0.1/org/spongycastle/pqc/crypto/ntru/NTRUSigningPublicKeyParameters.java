package org.spongycastle.pqc.crypto.ntru;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;











public class NTRUSigningPublicKeyParameters
  extends AsymmetricKeyParameter
{
  private NTRUSigningParameters params;
  public IntegerPolynomial h;
  
  public NTRUSigningPublicKeyParameters(IntegerPolynomial h, NTRUSigningParameters params)
  {
    super(false);
    this.h = h;
    this.params = params;
  }
  






  public NTRUSigningPublicKeyParameters(byte[] b, NTRUSigningParameters params)
  {
    super(false);
    h = IntegerPolynomial.fromBinary(b, N, q);
    this.params = params;
  }
  






  public NTRUSigningPublicKeyParameters(InputStream is, NTRUSigningParameters params)
    throws IOException
  {
    super(false);
    h = IntegerPolynomial.fromBinary(is, N, q);
    this.params = params;
  }
  






  public byte[] getEncoded()
  {
    return h.toBinary(params.q);
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
    result = 31 * result + (h == null ? 0 : h.hashCode());
    result = 31 * result + (params == null ? 0 : params.hashCode());
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
    if (getClass() != obj.getClass())
    {
      return false;
    }
    NTRUSigningPublicKeyParameters other = (NTRUSigningPublicKeyParameters)obj;
    if (h == null)
    {
      if (h != null)
      {
        return false;
      }
    }
    else if (!h.equals(h))
    {
      return false;
    }
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
    return true;
  }
}
