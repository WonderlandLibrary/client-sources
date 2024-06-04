package org.lwjgl.util.mapped;






public class MappedSet4
{
  private final MappedObject a;
  




  private final MappedObject b;
  




  private final MappedObject c;
  



  private final MappedObject d;
  



  public int view;
  




  MappedSet4(MappedObject a, MappedObject b, MappedObject c, MappedObject d)
  {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  

  void view(int view)
  {
    a.setViewAddress(a.getViewAddress(view));
    b.setViewAddress(b.getViewAddress(view));
    c.setViewAddress(c.getViewAddress(view));
    d.setViewAddress(d.getViewAddress(view));
  }
  
  public void next() {
    a.next();
    b.next();
    c.next();
    d.next();
  }
}
