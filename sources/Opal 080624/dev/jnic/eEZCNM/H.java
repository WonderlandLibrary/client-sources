package dev.jnic.eEZCNM;

public abstract class H {
  public int W = 0;
  
  public int X = 0;
  
  public abstract void e();
  
  public final int a(short[] paramArrayOfshort, int paramInt) {
    boolean bool;
    e();
    short s = paramArrayOfshort[paramInt];
    int i = (this.W >>> 11) * s;
    if ((this.X ^ Integer.MIN_VALUE) < (i ^ Integer.MIN_VALUE)) {
      this.W = i;
      paramArrayOfshort[paramInt] = (short)(s + (2048 - s >>> 5));
      bool = false;
    } else {
      this.W -= i;
      this.X -= i;
      bool[paramInt] = (short)(s - (s >>> 5));
      bool = true;
    } 
    return bool;
  }
  
  public final int a(short[] paramArrayOfshort) {
    int i = 1;
    do {
    
    } while ((i = i << 1 | a(paramArrayOfshort, i)) < paramArrayOfshort.length);
    return i - paramArrayOfshort.length;
  }
  
  public final int b(short[] paramArrayOfshort) {
    int i = 1;
    byte b = 0;
    int j = 0;
    while (true) {
      int k = a(paramArrayOfshort, i);
      i = i << 1 | k;
      j |= k << b++;
      if (i >= paramArrayOfshort.length)
        return j; 
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\H.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */