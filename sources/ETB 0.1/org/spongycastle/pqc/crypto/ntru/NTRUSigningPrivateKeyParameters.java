package org.spongycastle.pqc.crypto.ntru;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.SparseTernaryPolynomial;












public class NTRUSigningPrivateKeyParameters
  extends AsymmetricKeyParameter
{
  private List<Basis> bases;
  private NTRUSigningPublicKeyParameters publicKey;
  
  public NTRUSigningPrivateKeyParameters(byte[] b, NTRUSigningKeyGenerationParameters params)
    throws IOException
  {
    this(new ByteArrayInputStream(b), params);
  }
  






  public NTRUSigningPrivateKeyParameters(InputStream is, NTRUSigningKeyGenerationParameters params)
    throws IOException
  {
    super(true);
    bases = new ArrayList();
    for (int i = 0; i <= B; i++)
    {

      add(new Basis(is, params, i != 0));
    }
    publicKey = new NTRUSigningPublicKeyParameters(is, params.getSigningParameters());
  }
  
  public NTRUSigningPrivateKeyParameters(List<Basis> bases, NTRUSigningPublicKeyParameters publicKey)
  {
    super(true);
    this.bases = new ArrayList(bases);
    this.publicKey = publicKey;
  }
  





  private void add(Basis b)
  {
    bases.add(b);
  }
  






  public Basis getBasis(int i)
  {
    return (Basis)bases.get(i);
  }
  
  public NTRUSigningPublicKeyParameters getPublicKey()
  {
    return publicKey;
  }
  





  public byte[] getEncoded()
    throws IOException
  {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    for (int i = 0; i < bases.size(); i++)
    {

      ((Basis)bases.get(i)).encode(os, i != 0);
    }
    
    os.write(publicKey.getEncoded());
    
    return os.toByteArray();
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
    result = 31 * result;
    if (bases == null) return result;
    result += bases.hashCode();
    for (Basis basis : bases)
    {
      result += basis.hashCode();
    }
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
    NTRUSigningPrivateKeyParameters other = (NTRUSigningPrivateKeyParameters)obj;
    if ((bases == null ? 1 : 0) != (bases == null ? 1 : 0))
    {
      return false;
    }
    if (bases == null)
    {
      return true;
    }
    if (bases.size() != bases.size())
    {
      return false;
    }
    for (int i = 0; i < bases.size(); i++)
    {
      Basis basis1 = (Basis)bases.get(i);
      Basis basis2 = (Basis)bases.get(i);
      if (!f.equals(f))
      {
        return false;
      }
      if (!fPrime.equals(fPrime))
      {
        return false;
      }
      if ((i != 0) && (!h.equals(h)))
      {
        return false;
      }
      if (!params.equals(params))
      {
        return false;
      }
    }
    return true;
  }
  



  public static class Basis
  {
    public Polynomial f;
    

    public Polynomial fPrime;
    

    public IntegerPolynomial h;
    

    NTRUSigningKeyGenerationParameters params;
    


    protected Basis(Polynomial f, Polynomial fPrime, IntegerPolynomial h, NTRUSigningKeyGenerationParameters params)
    {
      this.f = f;
      this.fPrime = fPrime;
      this.h = h;
      this.params = params;
    }
    







    Basis(InputStream is, NTRUSigningKeyGenerationParameters params, boolean include_h)
      throws IOException
    {
      int N = N;
      int q = q;
      int d1 = d1;
      int d2 = d2;
      int d3 = d3;
      boolean sparse = sparse;
      this.params = params;
      
      if (polyType == 1)
      {
        f = ProductFormPolynomial.fromBinary(is, N, d1, d2, d3 + 1, d3);
      }
      else
      {
        IntegerPolynomial fInt = IntegerPolynomial.fromBinary3Tight(is, N);
        f = (sparse ? new SparseTernaryPolynomial(fInt) : new DenseTernaryPolynomial(fInt));
      }
      
      if (basisType == 0)
      {
        IntegerPolynomial fPrimeInt = IntegerPolynomial.fromBinary(is, N, q);
        for (int i = 0; i < coeffs.length; i++)
        {
          coeffs[i] -= q / 2;
        }
        fPrime = fPrimeInt;
      }
      else if (polyType == 1)
      {
        fPrime = ProductFormPolynomial.fromBinary(is, N, d1, d2, d3 + 1, d3);
      }
      else
      {
        fPrime = IntegerPolynomial.fromBinary3Tight(is, N);
      }
      
      if (include_h)
      {
        h = IntegerPolynomial.fromBinary(is, N, q);
      }
    }
    







    void encode(OutputStream os, boolean include_h)
      throws IOException
    {
      int q = params.q;
      
      os.write(getEncoded(f));
      if (params.basisType == 0)
      {
        IntegerPolynomial fPrimeInt = fPrime.toIntegerPolynomial();
        for (int i = 0; i < coeffs.length; i++)
        {
          coeffs[i] += q / 2;
        }
        os.write(fPrimeInt.toBinary(q));
      }
      else
      {
        os.write(getEncoded(fPrime));
      }
      if (include_h)
      {
        os.write(h.toBinary(q));
      }
    }
    
    private byte[] getEncoded(Polynomial p)
    {
      if ((p instanceof ProductFormPolynomial))
      {
        return ((ProductFormPolynomial)p).toBinary();
      }
      

      return p.toIntegerPolynomial().toBinary3Tight();
    }
    


    public int hashCode()
    {
      int prime = 31;
      int result = 1;
      result = 31 * result + (f == null ? 0 : f.hashCode());
      result = 31 * result + (fPrime == null ? 0 : fPrime.hashCode());
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
      if (!(obj instanceof Basis))
      {
        return false;
      }
      Basis other = (Basis)obj;
      if (f == null)
      {
        if (f != null)
        {
          return false;
        }
      }
      else if (!f.equals(f))
      {
        return false;
      }
      if (fPrime == null)
      {
        if (fPrime != null)
        {
          return false;
        }
      }
      else if (!fPrime.equals(fPrime))
      {
        return false;
      }
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
}
