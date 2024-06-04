package net.java.games.input;











final class DataQueue
{
  private final Object[] elements;
  









  private int position;
  









  private int limit;
  










  public DataQueue(int size, Class element_type)
  {
    elements = new Object[size];
    for (int i = 0; i < elements.length; i++) {
      try {
        elements[i] = element_type.newInstance();
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    clear();
  }
  
  public final void clear() {
    position = 0;
    limit = elements.length;
  }
  
  public final int position() {
    return position;
  }
  
  public final int limit() {
    return limit;
  }
  
  public final Object get(int index) {
    assert (index < limit);
    return elements[index];
  }
  
  public final Object get() {
    if (!hasRemaining())
      return null;
    return get(position++);
  }
  
  public final void compact() {
    int index = 0;
    while (hasRemaining()) {
      swap(position, index);
      position += 1;
      index++;
    }
    position = index;
    limit = elements.length;
  }
  
  private final void swap(int index1, int index2) {
    Object temp = elements[index1];
    elements[index1] = elements[index2];
    elements[index2] = temp;
  }
  
  public final void flip() {
    limit = position;
    position = 0;
  }
  
  public final boolean hasRemaining() {
    return remaining() > 0;
  }
  
  public final int remaining() {
    return limit - position;
  }
  
  public final void position(int position) {
    this.position = position;
  }
  
  public final Object[] getElements() {
    return elements;
  }
}
