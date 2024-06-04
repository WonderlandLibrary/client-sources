package org.lwjgl.util.glu.tessellation;


















class Dict
{
  DictNode head;
  
















  Object frame;
  
















  DictLeq leq;
  
















  private Dict() {}
  
















  static Dict dictNewDict(Object frame, DictLeq leq)
  {
    Dict dict = new Dict();
    head = new DictNode();
    
    head.key = null;
    head.next = head;
    head.prev = head;
    
    frame = frame;
    leq = leq;
    
    return dict;
  }
  
  static void dictDeleteDict(Dict dict) {
    head = null;
    frame = null;
    leq = null;
  }
  
  static DictNode dictInsert(Dict dict, Object key) {
    return dictInsertBefore(dict, head, key);
  }
  
  static DictNode dictInsertBefore(Dict dict, DictNode node, Object key) {
    do {
      node = prev;
    } while ((key != null) && (!leq.leq(frame, key, key)));
    
    DictNode newNode = new DictNode();
    key = key;
    next = next;
    next.prev = newNode;
    prev = node;
    next = newNode;
    
    return newNode;
  }
  
  static Object dictKey(DictNode aNode) {
    return key;
  }
  
  static DictNode dictSucc(DictNode aNode) {
    return next;
  }
  
  static DictNode dictPred(DictNode aNode) {
    return prev;
  }
  
  static DictNode dictMin(Dict aDict) {
    return head.next;
  }
  
  static DictNode dictMax(Dict aDict) {
    return head.prev;
  }
  
  static void dictDelete(Dict dict, DictNode node) {
    next.prev = prev;
    prev.next = next;
  }
  
  static DictNode dictSearch(Dict dict, Object key) {
    DictNode node = head;
    do
    {
      node = next;
    } while ((key != null) && (!leq.leq(frame, key, key)));
    
    return node;
  }
  
  public static abstract interface DictLeq
  {
    public abstract boolean leq(Object paramObject1, Object paramObject2, Object paramObject3);
  }
}
