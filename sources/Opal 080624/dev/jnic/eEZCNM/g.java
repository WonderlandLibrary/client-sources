package dev.jnic.eEZCNM;

import java.io.IOException;

public final class g {
  public final byte[] m;
  
  public final int n;
  
  public int o = 0;
  
  public int p = 0;
  
  public int q = 0;
  
  public int r = 0;
  
  public int s = 0;
  
  public int t = 0;
  
  public g(int paramInt, byte[] paramArrayOfbyte) {
    this.n = paramInt;
    this.m = new byte[this.n];
    if (paramArrayOfbyte != null) {
      this.p = Math.min(paramArrayOfbyte.length, paramInt);
      this.q = this.p;
      this.o = this.p;
      System.arraycopy(paramArrayOfbyte, paramArrayOfbyte.length - this.p, this.m, 0, this.p);
    } 
  }
  
  public final int b(int paramInt) {
    int i = this.p - paramInt - 1;
    if (paramInt >= this.p)
      i += this.n; 
    return this.m[i] & 0xFF;
  }
  
  public final void a(int paramInt1, int paramInt2) {
    int j;
    if (paramInt1 < 0 || paramInt1 >= this.q)
      throw new IOException(); 
    int i = Math.min(this.r - this.p, paramInt2);
    this.s = paramInt2 - i;
    this.t = paramInt1;
    if ((paramInt2 = this.p - paramInt1 - 1) < 0) {
      if (!u && this.q != this.n)
        throw new AssertionError(); 
      paramInt2 += this.n;
      int k = Math.min(this.n - paramInt2, i);
      if (!u && k > paramInt1 + 1)
        throw new AssertionError(); 
      System.arraycopy(this.m, paramInt2, this.m, this.p, k);
      this.p += k;
      paramInt2 = 0;
      if ((i -= k) == 0)
        return; 
    } 
    if (!u && paramInt2 >= this.p)
      throw new AssertionError(); 
    if (!u && i <= 0)
      throw new AssertionError(); 
    do {
      j = Math.min(i, this.p - paramInt2);
      System.arraycopy(this.m, paramInt2, this.m, this.p, j);
      this.p += j;
    } while ((i -= j) > 0);
    if (this.q < this.p)
      this.q = this.p; 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\g.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */