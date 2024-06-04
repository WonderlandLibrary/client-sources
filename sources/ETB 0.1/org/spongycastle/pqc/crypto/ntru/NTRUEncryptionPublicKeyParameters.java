package org.spongycastle.pqc.crypto.ntru;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;











public class NTRUEncryptionPublicKeyParameters
  extends NTRUEncryptionKeyParameters
{
  public IntegerPolynomial h;
  
  public NTRUEncryptionPublicKeyParameters(IntegerPolynomial h, NTRUEncryptionParameters params)
  {
    super(false, params);
    
    this.h = h;
  }
  







  public NTRUEncryptionPublicKeyParameters(byte[] b, NTRUEncryptionParameters params)
  {
    super(false, params);
    
    h = IntegerPolynomial.fromBinary(b, N, q);
  }
  







  public NTRUEncryptionPublicKeyParameters(InputStream is, NTRUEncryptionParameters params)
    throws IOException
  {
    super(false, params);
    
    h = IntegerPolynomial.fromBinary(is, N, q);
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
    if (!(obj instanceof NTRUEncryptionPublicKeyParameters))
    {
      return false;
    }
    NTRUEncryptionPublicKeyParameters other = (NTRUEncryptionPublicKeyParameters)obj;
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
