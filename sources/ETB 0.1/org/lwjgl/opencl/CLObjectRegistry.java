package org.lwjgl.opencl;

import org.lwjgl.LWJGLUtil;






class CLObjectRegistry<T extends CLObjectChild>
{
  private FastLongMap<T> registry;
  
  CLObjectRegistry() {}
  
  final boolean isEmpty()
  {
    return (registry == null) || (registry.isEmpty());
  }
  
  final T getObject(long id) {
    return registry == null ? null : (CLObjectChild)registry.get(id);
  }
  
  final boolean hasObject(long id) {
    return (registry != null) && (registry.containsKey(id));
  }
  
  final Iterable<FastLongMap.Entry<T>> getAll() {
    return registry;
  }
  
  void registerObject(T object) {
    FastLongMap<T> map = getMap();
    Long key = Long.valueOf(object.getPointer());
    
    if ((LWJGLUtil.DEBUG) && (map.containsKey(key.longValue()))) {
      throw new IllegalStateException("Duplicate object found: " + object.getClass() + " - " + key);
    }
    getMap().put(object.getPointer(), object);
  }
  
  void unregisterObject(T object) {
    getMap().remove(object.getPointerUnsafe());
  }
  
  private FastLongMap<T> getMap() {
    if (registry == null) {
      registry = new FastLongMap();
    }
    return registry;
  }
}
