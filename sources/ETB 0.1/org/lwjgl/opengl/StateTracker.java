package org.lwjgl.opengl;

import java.nio.IntBuffer;







































final class StateTracker
{
  private ReferencesStack references_stack;
  private final StateStack attrib_stack;
  private boolean insideBeginEnd;
  private final FastIntMap<VAOState> vaoMap = new FastIntMap();
  
  StateTracker() {
    attrib_stack = new StateStack(0);
  }
  
  void init()
  {
    references_stack = new ReferencesStack();
  }
  
  static void setBeginEnd(ContextCapabilities caps, boolean inside) {
    tracker.insideBeginEnd = inside;
  }
  
  boolean isBeginEnd() {
    return insideBeginEnd;
  }
  
  static void popAttrib(ContextCapabilities caps) {
    tracker.doPopAttrib();
  }
  
  private void doPopAttrib() {
    references_stack.popState(attrib_stack.popState());
  }
  
  static void pushAttrib(ContextCapabilities caps, int mask) {
    tracker.doPushAttrib(mask);
  }
  
  private void doPushAttrib(int mask) {
    attrib_stack.pushState(mask);
    references_stack.pushState();
  }
  
  static References getReferences(ContextCapabilities caps) {
    return tracker.references_stack.getReferences();
  }
  
  static void bindBuffer(ContextCapabilities caps, int target, int buffer) {
    BaseReferences references = getReferences(caps);
    switch (target) {
    case 34962: 
      arrayBuffer = buffer;
      break;
    

    case 34963: 
      if (vertexArrayObject != 0) {
        tracker.vaoMap.get(vertexArrayObject)).elementArrayBuffer = buffer;
      } else
        elementArrayBuffer = buffer;
      break;
    case 35051: 
      pixelPackBuffer = buffer;
      break;
    case 35052: 
      pixelUnpackBuffer = buffer;
      break;
    case 36671: 
      indirectBuffer = buffer;
    }
  }
  
  static void bindVAO(ContextCapabilities caps, int array)
  {
    FastIntMap<VAOState> vaoMap = tracker.vaoMap;
    if (!vaoMap.containsKey(array)) {
      vaoMap.put(array, new VAOState(null));
    }
    getReferencesvertexArrayObject = array;
  }
  
  static void deleteVAO(ContextCapabilities caps, IntBuffer arrays) {
    for (int i = arrays.position(); i < arrays.limit(); i++)
      deleteVAO(caps, arrays.get(i));
  }
  
  static void deleteVAO(ContextCapabilities caps, int array) {
    tracker.vaoMap.remove(array);
    
    BaseReferences references = getReferences(caps);
    if (vertexArrayObject == array) {
      vertexArrayObject = 0;
    }
  }
  





  static int getElementArrayBufferBound(ContextCapabilities caps)
  {
    BaseReferences references = getReferences(caps);
    
    if (vertexArrayObject == 0) {
      return elementArrayBuffer;
    }
    return tracker.vaoMap.get(vertexArrayObject)).elementArrayBuffer;
  }
  
  private static class VAOState
  {
    int elementArrayBuffer;
    
    private VAOState() {}
  }
}
