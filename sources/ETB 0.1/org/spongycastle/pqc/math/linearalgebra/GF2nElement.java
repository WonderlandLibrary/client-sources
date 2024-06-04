package org.spongycastle.pqc.math.linearalgebra;








public abstract class GF2nElement
  implements GFElement
{
  protected GF2nField mField;
  






  protected int mDegree;
  







  public GF2nElement() {}
  







  public abstract Object clone();
  







  abstract void assignZero();
  







  abstract void assignOne();
  







  public abstract boolean testRightmostBit();
  






  abstract boolean testBit(int paramInt);
  






  public final GF2nField getField()
  {
    return mField;
  }
  







  public abstract GF2nElement increase();
  






  public abstract void increaseThis();
  






  public final GFElement subtract(GFElement minuend)
    throws RuntimeException
  {
    return add(minuend);
  }
  







  public final void subtractFromThis(GFElement minuend)
  {
    addToThis(minuend);
  }
  






  public abstract GF2nElement square();
  





  public abstract void squareThis();
  





  public abstract GF2nElement squareRoot();
  





  public abstract void squareRootThis();
  





  public final GF2nElement convert(GF2nField basis)
    throws RuntimeException
  {
    return mField.convert(this, basis);
  }
  
  public abstract int trace();
  
  public abstract GF2nElement solveQuadraticEquation()
    throws RuntimeException;
}
