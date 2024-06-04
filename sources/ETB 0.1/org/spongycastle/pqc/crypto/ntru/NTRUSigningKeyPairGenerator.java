package org.spongycastle.pqc.crypto.ntru;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.pqc.math.ntru.polynomial.BigDecimalPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.BigIntPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ModularResultant;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Resultant;


public class NTRUSigningKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private NTRUSigningKeyGenerationParameters params;
  
  public NTRUSigningKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    params = ((NTRUSigningKeyGenerationParameters)param);
  }
  





  public AsymmetricCipherKeyPair generateKeyPair()
  {
    NTRUSigningPublicKeyParameters pub = null;
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Future<NTRUSigningPrivateKeyParameters.Basis>> bases = new ArrayList();
    for (int k = params.B; k >= 0; k--)
    {
      bases.add(executor.submit(new BasisGenerationTask(null)));
    }
    executor.shutdown();
    
    List<NTRUSigningPrivateKeyParameters.Basis> basises = new ArrayList();
    
    for (int k = params.B; k >= 0; k--)
    {
      Future<NTRUSigningPrivateKeyParameters.Basis> basis = (Future)bases.get(k);
      try
      {
        basises.add(basis.get());
        if (k == params.B)
        {
          pub = new NTRUSigningPublicKeyParameters(geth, params.getSigningParameters());
        }
      }
      catch (Exception e)
      {
        throw new IllegalStateException(e);
      }
    }
    NTRUSigningPrivateKeyParameters priv = new NTRUSigningPrivateKeyParameters(basises, pub);
    AsymmetricCipherKeyPair kp = new AsymmetricCipherKeyPair(pub, priv);
    return kp;
  }
  





  public AsymmetricCipherKeyPair generateKeyPairSingleThread()
  {
    List<NTRUSigningPrivateKeyParameters.Basis> basises = new ArrayList();
    NTRUSigningPublicKeyParameters pub = null;
    for (int k = params.B; k >= 0; k--)
    {
      NTRUSigningPrivateKeyParameters.Basis basis = generateBoundedBasis();
      basises.add(basis);
      if (k == 0)
      {
        pub = new NTRUSigningPublicKeyParameters(h, params.getSigningParameters());
      }
    }
    NTRUSigningPrivateKeyParameters priv = new NTRUSigningPrivateKeyParameters(basises, pub);
    return new AsymmetricCipherKeyPair(pub, priv);
  }
  








  private void minimizeFG(IntegerPolynomial f, IntegerPolynomial g, IntegerPolynomial F, IntegerPolynomial G, int N)
  {
    int E = 0;
    for (int j = 0; j < N; j++)
    {
      E += 2 * N * (coeffs[j] * coeffs[j] + coeffs[j] * coeffs[j]);
    }
    

    E -= 4;
    
    IntegerPolynomial u = (IntegerPolynomial)f.clone();
    IntegerPolynomial v = (IntegerPolynomial)g.clone();
    int j = 0;
    int k = 0;
    int maxAdjustment = N;
    while ((k < maxAdjustment) && (j < N))
    {
      int D = 0;
      int i = 0;
      while (i < N)
      {
        int D1 = coeffs[i] * coeffs[i];
        int D2 = coeffs[i] * coeffs[i];
        int D3 = 4 * N * (D1 + D2);
        D += D3;
        i++;
      }
      
      int D1 = 4 * (F.sumCoeffs() + G.sumCoeffs());
      D -= D1;
      
      if (D > E)
      {
        F.sub(u);
        G.sub(v);
        k++;
        j = 0;
      }
      else if (D < -E)
      {
        F.add(u);
        G.add(v);
        k++;
        j = 0;
      }
      j++;
      u.rotate1();
      v.rotate1();
    }
  }
  






  private FGBasis generateBasis()
  {
    int N = params.N;
    int q = params.q;
    int d = params.d;
    int d1 = params.d1;
    int d2 = params.d2;
    int d3 = params.d3;
    int basisType = params.basisType;
    









    int _2n1 = 2 * N + 1;
    boolean primeCheck = params.primeCheck;
    Polynomial f;
    IntegerPolynomial fInt;
    IntegerPolynomial fq;
    do {
      do {
        f = params.polyType == 0 ? DenseTernaryPolynomial.generateRandom(N, d + 1, d, new SecureRandom()) : ProductFormPolynomial.generateRandom(N, d1, d2, d3 + 1, d3, new SecureRandom());
        fInt = f.toIntegerPolynomial();
      }
      while ((primeCheck) && (resultantres.equals(BigInteger.ZERO)));
      fq = fInt.invertFq(q);
    }
    while (fq == null);
    Resultant rf = fInt.resultant();
    Polynomial g;
    IntegerPolynomial gInt;
    Resultant rg;
    BigIntEuclidean r;
    do
    {
      do {
        g = params.polyType == 0 ? DenseTernaryPolynomial.generateRandom(N, d + 1, d, new SecureRandom()) : ProductFormPolynomial.generateRandom(N, d1, d2, d3 + 1, d3, new SecureRandom());
        gInt = g.toIntegerPolynomial();
      }
      while (((primeCheck) && (resultantres.equals(BigInteger.ZERO))) || 
      
        (gInt.invertFq(q) == null));
      rg = gInt.resultant();
      r = BigIntEuclidean.calculate(res, res);
    }
    while (!gcd.equals(BigInteger.ONE));
    
    BigIntPolynomial A = (BigIntPolynomial)rho.clone();
    A.mult(x.multiply(BigInteger.valueOf(q)));
    BigIntPolynomial B = (BigIntPolynomial)rho.clone();
    B.mult(y.multiply(BigInteger.valueOf(-q)));
    
    BigIntPolynomial C;
    if (params.keyGenAlg == 0)
    {
      int[] fRevCoeffs = new int[N];
      int[] gRevCoeffs = new int[N];
      fRevCoeffs[0] = coeffs[0];
      gRevCoeffs[0] = coeffs[0];
      for (int i = 1; i < N; i++)
      {
        fRevCoeffs[i] = coeffs[(N - i)];
        gRevCoeffs[i] = coeffs[(N - i)];
      }
      IntegerPolynomial fRev = new IntegerPolynomial(fRevCoeffs);
      IntegerPolynomial gRev = new IntegerPolynomial(gRevCoeffs);
      
      IntegerPolynomial t = f.mult(fRev);
      t.add(g.mult(gRev));
      Resultant rt = t.resultant();
      BigIntPolynomial C = fRev.mult(B);
      C.add(gRev.mult(A));
      C = C.mult(rho);
      C.div(res);

    }
    else
    {
      int log10N = 0;
      for (int i = 1; i < N; i *= 10)
      {
        log10N++;
      }
      




      BigDecimalPolynomial fInv = rho.div(new BigDecimal(res), B.getMaxCoeffLength() + 1 + log10N);
      BigDecimalPolynomial gInv = rho.div(new BigDecimal(res), A.getMaxCoeffLength() + 1 + log10N);
      
      BigDecimalPolynomial Cdec = fInv.mult(B);
      Cdec.add(gInv.mult(A));
      Cdec.halve();
      C = Cdec.round();
    }
    
    BigIntPolynomial F = (BigIntPolynomial)B.clone();
    F.sub(f.mult(C));
    BigIntPolynomial G = (BigIntPolynomial)A.clone();
    G.sub(g.mult(C));
    
    IntegerPolynomial FInt = new IntegerPolynomial(F);
    IntegerPolynomial GInt = new IntegerPolynomial(G);
    minimizeFG(fInt, gInt, FInt, GInt, N);
    IntegerPolynomial h;
    Polynomial fPrime;
    IntegerPolynomial h;
    if (basisType == 0)
    {
      Polynomial fPrime = FInt;
      h = g.mult(fq, q);
    }
    else
    {
      fPrime = g;
      h = FInt.mult(fq, q);
    }
    h.modPositive(q);
    
    return new FGBasis(f, fPrime, h, FInt, GInt, params);
  }
  





  public NTRUSigningPrivateKeyParameters.Basis generateBoundedBasis()
  {
    for (;;)
    {
      FGBasis basis = generateBasis();
      if (basis.isNormOk())
      {
        return basis;
      }
    }
  }
  
  private class BasisGenerationTask
    implements Callable<NTRUSigningPrivateKeyParameters.Basis>
  {
    private BasisGenerationTask() {}
    
    public NTRUSigningPrivateKeyParameters.Basis call()
      throws Exception
    {
      return generateBoundedBasis();
    }
  }
  

  public class FGBasis
    extends NTRUSigningPrivateKeyParameters.Basis
  {
    public IntegerPolynomial F;
    
    public IntegerPolynomial G;
    

    FGBasis(Polynomial f, Polynomial fPrime, IntegerPolynomial h, IntegerPolynomial F, IntegerPolynomial G, NTRUSigningKeyGenerationParameters params)
    {
      super(fPrime, h, params);
      this.F = F;
      this.G = G;
    }
    




    boolean isNormOk()
    {
      double keyNormBoundSq = params.keyNormBoundSq;
      int q = params.q;
      return (F.centeredNormSq(q) < keyNormBoundSq) && (G.centeredNormSq(q) < keyNormBoundSq);
    }
  }
}
