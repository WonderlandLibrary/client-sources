package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;














public class McEliecePublicKeyParameters
  extends McElieceKeyParameters
{
  private int n;
  private int t;
  private GF2Matrix g;
  
  public McEliecePublicKeyParameters(int n, int t, GF2Matrix g)
  {
    super(false, null);
    this.n = n;
    this.t = t;
    this.g = new GF2Matrix(g);
  }
  



  public int getN()
  {
    return n;
  }
  



  public int getT()
  {
    return t;
  }
  



  public GF2Matrix getG()
  {
    return g;
  }
  



  public int getK()
  {
    return g.getNumRows();
  }
}
