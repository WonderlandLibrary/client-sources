package wtf.opal;

public final class bp {
  private float J;
  
  private float X;
  
  public bp(float paramFloat1, float paramFloat2) {
    this.J = paramFloat1;
    this.X = paramFloat2;
  }
  
  public float v(Object[] paramArrayOfObject) {
    return this.J;
  }
  
  public float u(Object[] paramArrayOfObject) {
    return this.X;
  }
  
  public void U(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.X = f;
  }
  
  public void f(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.J = f;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */