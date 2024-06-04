package org.lwjgl.util.mapped;









public class MappedSet2
{
  private final MappedObject a;
  






  private final MappedObject b;
  






  public int view;
  







  MappedSet2(MappedObject a, MappedObject b)
  {
    this.a = a;
    this.b = b;
  }
  

  void view(int view)
  {
    a.setViewAddress(a.getViewAddress(view));
    b.setViewAddress(b.getViewAddress(view));
  }
  
  public void next() {
    a.next();
    b.next();
  }
}
