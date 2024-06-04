package org.lwjgl.util.glu.tessellation;












class PriorityQSort
  extends PriorityQ
{
  PriorityQHeap heap;
  









  Object[] keys;
  









  int[] order;
  









  int size;
  









  int max;
  









  boolean initialized;
  









  PriorityQ.Leq leq;
  










  PriorityQSort(PriorityQ.Leq leq)
  {
    heap = new PriorityQHeap(leq);
    
    keys = new Object[32];
    
    size = 0;
    max = 32;
    initialized = false;
    this.leq = leq;
  }
  
  void pqDeletePriorityQ()
  {
    if (heap != null) heap.pqDeletePriorityQ();
    order = null;
    keys = null;
  }
  
  private static boolean LT(PriorityQ.Leq leq, Object x, Object y) {
    return !PriorityQHeap.LEQ(leq, y, x);
  }
  
  private static boolean GT(PriorityQ.Leq leq, Object x, Object y) {
    return !PriorityQHeap.LEQ(leq, x, y);
  }
  
  private static void Swap(int[] array, int a, int b)
  {
    int tmp = array[a];
    array[a] = array[b];
    array[b] = tmp;
  }
  









  boolean pqInit()
  {
    Stack[] stack = new Stack[50];
    for (int k = 0; k < stack.length; k++) {
      stack[k] = new Stack(null);
    }
    int top = 0;
    
    int seed = 2016473283;
    



    order = new int[size + 1];
    



    int p = 0;
    int r = size - 1;
    int piv = 0; for (int i = p; i <= r; i++)
    {
      order[i] = piv;piv++;
    }
    



    p = p;
    r = r;
    top++;
    for (;;) { top--; if (top < 0) break;
      p = p;
      r = r;
      while (r > p + 10) {
        seed = Math.abs(seed * 1539415821 + 1);
        i = p + seed % (r - p + 1);
        piv = order[i];
        order[i] = order[p];
        order[p] = piv;
        i = p - 1;
        int j = r + 1;
        do {
          do {
            i++;
          } while (GT(leq, keys[order[i]], keys[piv]));
          do {
            j--;
          } while (LT(leq, keys[order[j]], keys[piv]));
          Swap(order, i, j);
        } while (i < j);
        Swap(order, i, j);
        if (i - p < r - j) {
          p = (j + 1);
          r = r;
          top++;
          r = i - 1;
        } else {
          p = p;
          r = (i - 1);
          top++;
          p = j + 1;
        }
      }
      
      for (i = p + 1; i <= r; i++) {
        piv = order[i];
        for (int j = i; (j > p) && (LT(leq, keys[order[(j - 1)]], keys[piv])); j--) {
          order[j] = order[(j - 1)];
        }
        order[j] = piv;
      }
    }
    max = size;
    initialized = true;
    heap.pqInit();
    








    return true;
  }
  



  int pqInsert(Object keyNew)
  {
    if (initialized) {
      return heap.pqInsert(keyNew);
    }
    int curr = size;
    if (++size >= max) {
      Object[] saveKey = keys;
      

      max <<= 1;
      
      Object[] pqKeys = new Object[max];
      System.arraycopy(keys, 0, pqKeys, 0, keys.length);
      keys = pqKeys;
      if (keys == null) {
        keys = saveKey;
        return Integer.MAX_VALUE;
      }
    }
    assert (curr != Integer.MAX_VALUE);
    keys[curr] = keyNew;
    

    return -(curr + 1);
  }
  


  Object pqExtractMin()
  {
    if (size == 0) {
      return heap.pqExtractMin();
    }
    Object sortMin = keys[order[(size - 1)]];
    if (!heap.pqIsEmpty()) {
      Object heapMin = heap.pqMinimum();
      if (LEQ(leq, heapMin, sortMin)) {
        return heap.pqExtractMin();
      }
    }
    do {
      size -= 1;
    } while ((size > 0) && (keys[order[(size - 1)]] == null));
    return sortMin;
  }
  


  Object pqMinimum()
  {
    if (size == 0) {
      return heap.pqMinimum();
    }
    Object sortMin = keys[order[(size - 1)]];
    if (!heap.pqIsEmpty()) {
      Object heapMin = heap.pqMinimum();
      if (PriorityQHeap.LEQ(leq, heapMin, sortMin)) {
        return heapMin;
      }
    }
    return sortMin;
  }
  
  boolean pqIsEmpty()
  {
    return (size == 0) && (heap.pqIsEmpty());
  }
  
  void pqDelete(int curr)
  {
    if (curr >= 0) {
      heap.pqDelete(curr);
      return;
    }
    curr = -(curr + 1);
    assert ((curr < max) && (keys[curr] != null));
    
    keys[curr] = null;
    while ((size > 0) && (keys[order[(size - 1)]] == null)) {
      size -= 1;
    }
  }
  
  private static class Stack
  {
    int p;
    int r;
    
    private Stack() {}
  }
}
