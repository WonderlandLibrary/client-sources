package it.unimi.dsi.fastutil.ints;

public interface IntHash {
  public static interface Strategy {
    int hashCode(int param1Int);
    
    boolean equals(int param1Int1, int param1Int2);
  }
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */