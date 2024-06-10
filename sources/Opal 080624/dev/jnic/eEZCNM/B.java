package dev.jnic.eEZCNM;

import java.io.IOException;

public final class B extends H {
  public final byte[] m = new byte[65531];
  
  public int p = this.m.length;
  
  public final void e() {
    if ((this.W & 0xFF000000) == 0)
      try {
        this.X = this.X << 8 | this.m[this.p++] & 0xFF;
        this.W <<= 8;
        return;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IOException();
      }  
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\B.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */