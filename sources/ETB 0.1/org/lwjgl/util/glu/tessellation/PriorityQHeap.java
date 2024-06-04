package org.lwjgl.util.glu.tessellation;












class PriorityQHeap
  extends PriorityQ
{
  PriorityQ.PQnode[] nodes;
  










  PriorityQ.PQhandleElem[] handles;
  










  int size;
  









  int max;
  









  int freeList;
  









  boolean initialized;
  









  PriorityQ.Leq leq;
  










  PriorityQHeap(PriorityQ.Leq leq)
  {
    size = 0;
    max = 32;
    nodes = new PriorityQ.PQnode[33];
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = new PriorityQ.PQnode();
    }
    handles = new PriorityQ.PQhandleElem[33];
    for (int i = 0; i < handles.length; i++) {
      handles[i] = new PriorityQ.PQhandleElem();
    }
    initialized = false;
    freeList = 0;
    this.leq = leq;
    
    nodes[1].handle = 1;
    handles[1].key = null;
  }
  
  void pqDeletePriorityQ()
  {
    handles = null;
    nodes = null;
  }
  
  void FloatDown(int curr) {
    PriorityQ.PQnode[] n = nodes;
    PriorityQ.PQhandleElem[] h = handles;
    


    int hCurr = handle;
    for (;;) {
      int child = curr << 1;
      if ((child < size) && (LEQ(leq, 1handle].key, handle].key)))
      {
        child++;
      }
      
      assert (child <= max);
      
      int hChild = handle;
      if ((child > size) || (LEQ(leq, key, key))) {
        handle = hCurr;
        node = curr;
        break;
      }
      handle = hChild;
      node = curr;
      curr = child;
    }
  }
  
  void FloatUp(int curr)
  {
    PriorityQ.PQnode[] n = nodes;
    PriorityQ.PQhandleElem[] h = handles;
    


    int hCurr = handle;
    for (;;) {
      int parent = curr >> 1;
      int hParent = handle;
      if ((parent == 0) || (LEQ(leq, key, key))) {
        handle = hCurr;
        node = curr;
        break;
      }
      handle = hParent;
      node = curr;
      curr = parent;
    }
  }
  




  boolean pqInit()
  {
    for (int i = size; i >= 1; i--) {
      FloatDown(i);
    }
    initialized = true;
    
    return true;
  }
  




  int pqInsert(Object keyNew)
  {
    int curr = ++size;
    if (curr * 2 > max) {
      PriorityQ.PQnode[] saveNodes = nodes;
      PriorityQ.PQhandleElem[] saveHandles = handles;
      

      max <<= 1;
      
      PriorityQ.PQnode[] pqNodes = new PriorityQ.PQnode[max + 1];
      System.arraycopy(nodes, 0, pqNodes, 0, nodes.length);
      for (int i = nodes.length; i < pqNodes.length; i++) {
        pqNodes[i] = new PriorityQ.PQnode();
      }
      nodes = pqNodes;
      if (nodes == null) {
        nodes = saveNodes;
        return Integer.MAX_VALUE;
      }
      

      PriorityQ.PQhandleElem[] pqHandles = new PriorityQ.PQhandleElem[max + 1];
      System.arraycopy(handles, 0, pqHandles, 0, handles.length);
      for (int i = handles.length; i < pqHandles.length; i++) {
        pqHandles[i] = new PriorityQ.PQhandleElem();
      }
      handles = pqHandles;
      if (handles == null) {
        handles = saveHandles;
        return Integer.MAX_VALUE;
      } }
    int free;
    int free;
    if (freeList == 0) {
      free = curr;
    } else {
      free = freeList;
      freeList = handles[free].node;
    }
    
    nodes[curr].handle = free;
    handles[free].node = curr;
    handles[free].key = keyNew;
    
    if (initialized) {
      FloatUp(curr);
    }
    assert (free != Integer.MAX_VALUE);
    return free;
  }
  
  Object pqExtractMin()
  {
    PriorityQ.PQnode[] n = nodes;
    PriorityQ.PQhandleElem[] h = handles;
    int hMin = 1handle;
    Object min = key;
    
    if (size > 0) {
      1handle = size].handle;
      1handle].node = 1;
      
      key = null;
      node = freeList;
      freeList = hMin;
      
      if (--size > 0) {
        FloatDown(1);
      }
    }
    return min;
  }
  
  void pqDelete(int hCurr)
  {
    PriorityQ.PQnode[] n = nodes;
    PriorityQ.PQhandleElem[] h = handles;
    

    assert ((hCurr >= 1) && (hCurr <= max) && (key != null));
    
    int curr = node;
    handle = size].handle;
    handle].node = curr;
    
    if (curr <= --size) {
      if ((curr <= 1) || (LEQ(leq, 1handle].key, handle].key))) {
        FloatDown(curr);
      } else {
        FloatUp(curr);
      }
    }
    key = null;
    node = freeList;
    freeList = hCurr;
  }
  
  Object pqMinimum() {
    return handles[nodes[1].handle].key;
  }
  
  boolean pqIsEmpty() {
    return size == 0;
  }
}
