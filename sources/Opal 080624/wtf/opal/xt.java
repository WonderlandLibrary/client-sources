package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.NotNull;

public final class xt implements Iterable<py> {
  private final List<py> b = new CopyOnWriteArrayList<>();
  
  private static d[] Q;
  
  private static final long a = on.a(-4332580058912419908L, -3214495429838865102L, MethodHandles.lookup().lookupClass()).a(104378987439915L);
  
  public k2 O(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2113F030BCB5L;
    return new k2(l2, this, kj.INFORMATION, str);
  }
  
  public k2 z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    String str = (String)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0xDB49D503238L;
    (new Object[3])[2] = str;
    (new Object[3])[1] = kj.ERROR;
    new Object[3];
    return v(new Object[] { Long.valueOf(l2) });
  }
  
  public k2 h(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x6F970F33043CL;
    (new Object[3])[2] = str;
    (new Object[3])[1] = kj.SUCCESS;
    new Object[3];
    return v(new Object[] { Long.valueOf(l2) });
  }
  
  public Collection h(Object[] paramArrayOfObject) {
    return this.b;
  }
  
  public void T(Object[] paramArrayOfObject) {
    py py = (py)paramArrayOfObject[0];
    this.b.remove(py);
  }
  
  @NotNull
  public Iterator<py> iterator() {
    return this.b.iterator();
  }
  
  private k2 v(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    kj kj = (kj)paramArrayOfObject[1];
    String str = (String)paramArrayOfObject[2];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x16611558B071L;
    return new k2(l2, this, kj, str);
  }
  
  private py B(Object[] paramArrayOfObject) {
    py py = (py)paramArrayOfObject[0];
    this.b.add(py);
    return py;
  }
  
  public static void K(d[] paramArrayOfd) {
    Q = paramArrayOfd;
  }
  
  public static d[] P() {
    return Q;
  }
  
  static {
    if (P() != null)
      K(new d[3]); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */