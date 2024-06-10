package it.unimi.dsi.fastutil.floats;

public interface FloatHash {
  public static interface Strategy {
    int hashCode(float param1Float);
    
    boolean equals(float param1Float1, float param1Float2);
  }
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */