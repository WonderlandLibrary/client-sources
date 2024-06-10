package dev.jnic.eEZCNM;

import java.util.Arrays;

abstract class k {
  final int v;
  
  final int[] w = new int[4];
  
  final t x = new t();
  
  final short[][] y = new short[12][16];
  
  final short[] z = new short[12];
  
  final short[] A = new short[12];
  
  final short[] B = new short[12];
  
  final short[] C = new short[12];
  
  final short[][] D = new short[12][16];
  
  final short[][] E = new short[4][64];
  
  final short[][] F = new short[][] { new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32] };
  
  final short[] G = new short[16];
  
  k(int paramInt) {
    this.v = (1 << paramInt) - 1;
  }
  
  void c() {
    this.w[0] = 0;
    this.w[1] = 0;
    this.w[2] = 0;
    this.w[3] = 0;
    this.x.S = 0;
    byte b;
    for (b = 0; b < this.y.length; b++)
      Arrays.fill(this.y[b], (short)1024); 
    Arrays.fill(this.z, (short)1024);
    Arrays.fill(this.A, (short)1024);
    Arrays.fill(this.B, (short)1024);
    Arrays.fill(this.C, (short)1024);
    for (b = 0; b < this.D.length; b++)
      Arrays.fill(this.D[b], (short)1024); 
    for (b = 0; b < this.E.length; b++)
      Arrays.fill(this.E[b], (short)1024); 
    for (b = 0; b < this.F.length; b++)
      Arrays.fill(this.F[b], (short)1024); 
    Arrays.fill(this.G, (short)1024);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\k.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */