package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;


















public class McElieceCCA2PublicKeyParameters
  extends McElieceCCA2KeyParameters
{
  private int n;
  private int t;
  private GF2Matrix matrixG;
  
  public McElieceCCA2PublicKeyParameters(int n, int t, GF2Matrix matrix, String digest)
  {
    super(false, digest);
    
    this.n = n;
    this.t = t;
    matrixG = new GF2Matrix(matrix);
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
    return matrixG;
  }
  



  public int getK()
  {
    return matrixG.getNumRows();
  }
}
