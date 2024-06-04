package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.util.Util;








public class NTRUEncryptionKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private NTRUEncryptionKeyGenerationParameters params;
  
  public NTRUEncryptionKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    params = ((NTRUEncryptionKeyGenerationParameters)param);
  }
  





  public AsymmetricCipherKeyPair generateKeyPair()
  {
    int N = params.N;
    int q = params.q;
    int df = params.df;
    int df1 = params.df1;
    int df2 = params.df2;
    int df3 = params.df3;
    int dg = params.dg;
    boolean fastFp = params.fastFp;
    boolean sparse = params.sparse;
    


    IntegerPolynomial fp = null;
    Polynomial t;
    IntegerPolynomial fq;
    do
    {
      IntegerPolynomial f;
      do
      {
        if (fastFp)
        {

          Polynomial t = params.polyType == 0 ? Util.generateRandomTernary(N, df, df, sparse, params.getRandom()) : ProductFormPolynomial.generateRandom(N, df1, df2, df3, df3, params.getRandom());
          IntegerPolynomial f = t.toIntegerPolynomial();
          f.mult(3);
          coeffs[0] += 1; break;
        }
        

        t = params.polyType == 0 ? Util.generateRandomTernary(N, df, df - 1, sparse, params.getRandom()) : ProductFormPolynomial.generateRandom(N, df1, df2, df3, df3 - 1, params.getRandom());
        f = t.toIntegerPolynomial();
        fp = f.invertF3();
      } while (fp == null);
      




      fq = f.invertFq(q);
    } while (fq == null);
    






    if (fastFp)
    {
      fp = new IntegerPolynomial(N);
      coeffs[0] = 1;
    }
    
    DenseTernaryPolynomial g;
    
    for (;;)
    {
      g = DenseTernaryPolynomial.generateRandom(N, dg, dg - 1, params.getRandom());
      if (g.invertFq(q) != null) {
        break;
      }
    }
    

    IntegerPolynomial h = g.mult(fq, q);
    h.mult3(q);
    h.ensurePositive(q);
    g.clear();
    fq.clear();
    
    NTRUEncryptionPrivateKeyParameters priv = new NTRUEncryptionPrivateKeyParameters(h, t, fp, params.getEncryptionParameters());
    NTRUEncryptionPublicKeyParameters pub = new NTRUEncryptionPublicKeyParameters(h, params.getEncryptionParameters());
    return new AsymmetricCipherKeyPair(pub, priv);
  }
}
