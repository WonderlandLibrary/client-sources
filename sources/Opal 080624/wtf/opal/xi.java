package wtf.opal;

import java.util.Iterator;

class xi implements Iterator<bb> {
  final Iterator z;
  
  final Iterator o;
  
  xi(kq paramkq, Iterator paramIterator1, Iterator paramIterator2) {}
  
  public boolean hasNext() {
    return this.z.hasNext();
  }
  
  public bb Y(Object[] paramArrayOfObject) {
    String str = this.z.next();
    kv kv = this.o.next();
    return new bb(str, kv);
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */