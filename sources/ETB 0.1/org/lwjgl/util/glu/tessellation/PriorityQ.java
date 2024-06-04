package org.lwjgl.util.glu.tessellation;



































abstract class PriorityQ
{
  public static final int INIT_SIZE = 32;
  

































  PriorityQ() {}
  

































  public static boolean LEQ(Leq leq, Object x, Object y)
  {
    return Geom.VertLeq((GLUvertex)x, (GLUvertex)y);
  }
  
  static PriorityQ pqNewPriorityQ(Leq leq) {
    return new PriorityQSort(leq);
  }
  
  abstract void pqDeletePriorityQ();
  
  abstract boolean pqInit();
  
  abstract int pqInsert(Object paramObject);
  
  abstract Object pqExtractMin();
  
  abstract void pqDelete(int paramInt);
  
  abstract Object pqMinimum();
  
  abstract boolean pqIsEmpty();
  
  public static abstract interface Leq
  {
    public abstract boolean leq(Object paramObject1, Object paramObject2);
  }
  
  public static class PQhandleElem
  {
    Object key;
    int node;
    
    public PQhandleElem() {}
  }
  
  public static class PQnode
  {
    int handle;
    
    public PQnode() {}
  }
}
