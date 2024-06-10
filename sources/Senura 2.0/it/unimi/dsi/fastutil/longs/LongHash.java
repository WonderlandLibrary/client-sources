package it.unimi.dsi.fastutil.longs;

public interface LongHash {
  public static interface Strategy {
    int hashCode(long param1Long);
    
    boolean equals(long param1Long1, long param1Long2);
  }
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */