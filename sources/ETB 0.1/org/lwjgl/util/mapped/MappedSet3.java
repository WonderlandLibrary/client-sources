package org.lwjgl.util.mapped;







public class MappedSet3
{
  private final MappedObject a;
  





  private final MappedObject b;
  





  private final MappedObject c;
  




  public int view;
  





  MappedSet3(MappedObject a, MappedObject b, MappedObject c)
  {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  

  void view(int view)
  {
    a.setViewAddress(a.getViewAddress(view));
    b.setViewAddress(b.getViewAddress(view));
    c.setViewAddress(c.getViewAddress(view));
  }
  
  public void next() {
    a.next();
    b.next();
    c.next();
  }
}
