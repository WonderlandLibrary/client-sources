package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2m;































public class McElieceCCA2PrivateKeyParameters
  extends McElieceCCA2KeyParameters
{
  private int n;
  private int k;
  private GF2mField field;
  private PolynomialGF2mSmallM goppaPoly;
  private Permutation p;
  private GF2Matrix h;
  private PolynomialGF2mSmallM[] qInv;
  
  public McElieceCCA2PrivateKeyParameters(int n, int k, GF2mField field, PolynomialGF2mSmallM gp, Permutation p, String digest)
  {
    this(n, k, field, gp, GoppaCode.createCanonicalCheckMatrix(field, gp), p, digest);
  }
  












  public McElieceCCA2PrivateKeyParameters(int n, int k, GF2mField field, PolynomialGF2mSmallM gp, GF2Matrix canonicalCheckMatrix, Permutation p, String digest)
  {
    super(true, digest);
    
    this.n = n;
    this.k = k;
    this.field = field;
    goppaPoly = gp;
    h = canonicalCheckMatrix;
    this.p = p;
    
    PolynomialRingGF2m ring = new PolynomialRingGF2m(field, gp);
    

    qInv = ring.getSquareRootMatrix();
  }
  



  public int getN()
  {
    return n;
  }
  



  public int getK()
  {
    return k;
  }
  



  public int getT()
  {
    return goppaPoly.getDegree();
  }
  



  public GF2mField getField()
  {
    return field;
  }
  



  public PolynomialGF2mSmallM getGoppaPoly()
  {
    return goppaPoly;
  }
  



  public Permutation getP()
  {
    return p;
  }
  



  public GF2Matrix getH()
  {
    return h;
  }
  



  public PolynomialGF2mSmallM[] getQInv()
  {
    return qInv;
  }
}
