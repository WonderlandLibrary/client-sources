package dev.jnic.eEZCNM;

import java.util.Arrays;

abstract class m {
  final short[] H = new short[2];
  
  final short[][] I = new short[16][8];
  
  final short[][] J = new short[16][8];
  
  final short[] K = new short[256];
  
  final void c() {
    Arrays.fill(this.H, (short)1024);
    byte b;
    for (b = 0; b < this.I.length; b++)
      Arrays.fill(this.I[b], (short)1024); 
    for (b = 0; b < this.I.length; b++)
      Arrays.fill(this.J[b], (short)1024); 
    Arrays.fill(this.K, (short)1024);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\m.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */