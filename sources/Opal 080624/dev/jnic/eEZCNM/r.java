package dev.jnic.eEZCNM;

final class r extends c {
  final x[] U;
  
  r(U paramU, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    this.U = new x[1 << paramInt1 + paramInt2];
    for (byte b = 0; b < this.U.length; b++)
      this.U[b] = new x(this, (byte)0); 
  }
  
  final void c() {
    for (byte b = 0; b < this.U.length; b++)
      this.U[b].c(); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\r.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */