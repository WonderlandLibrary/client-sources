package wtf.opal;

import java.lang.invoke.MethodHandles;

class l4 extends lq<k1, kq> {
  protected kv s;
  
  private static final long a = on.a(7436403907814005985L, -4569022964153673586L, MethodHandles.lookup().lookupClass()).a(133260202468107L);
  
  public k1 d(Object[] paramArrayOfObject) {
    return new k1();
  }
  
  public kq N(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x378E4D89F5C4L;
    return new kq(l2);
  }
  
  public void Z(Object[] paramArrayOfObject) {
    this.s = u4.t;
  }
  
  public void S(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    try {
    
    } catch (x5 x5) {
      throw b(null);
    } 
    this.s = bool ? u4.h : u4.s;
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    String str = (String)paramArrayOfObject[1];
    long l2 = l1 ^ 0xDDFD38CC847L;
    this.s = new k4(str, l2);
  }
  
  public void b(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    long l2 = l1 ^ 0xE97FE3FB25BL;
    this.s = new k5(l2, str);
  }
  
  public void G(Object[] paramArrayOfObject) {
    k1 k1 = (k1)paramArrayOfObject[0];
    this.s = k1;
  }
  
  public void K(Object[] paramArrayOfObject) {
    kq kq = (kq)paramArrayOfObject[0];
    this.s = kq;
  }
  
  public void h(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    k1 k1 = (k1)paramArrayOfObject[1];
    l = a ^ l;
    int i = (int)((l ^ 0x6FC072427A1BL) >>> 48L);
    int j = (int)((l ^ 0x6FC072427A1BL) << 16L >>> 48L);
    int k = (int)((l ^ 0x6FC072427A1BL) << 32L >>> 32L);
    l ^ 0x6FC072427A1BL;
    k1.q(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf((short)j), Integer.valueOf((short)i), this.s });
  }
  
  public void R(Object[] paramArrayOfObject) {
    kq kq = (kq)paramArrayOfObject[0];
    String str = (String)paramArrayOfObject[1];
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x6FCB07A15D76L;
    new Object[3];
    kq.c(new Object[] { null, null, Long.valueOf(l2), this.s, str });
  }
  
  kv Q(Object[] paramArrayOfObject) {
    return this.s;
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */