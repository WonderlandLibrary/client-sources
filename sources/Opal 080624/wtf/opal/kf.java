package wtf.opal;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

class kf extends kv {
  private final String g;
  
  private final boolean o;
  
  private final boolean x;
  
  private final boolean q;
  
  private static final long a = on.a(2687192445985674640L, 1624614398823822078L, MethodHandles.lookup().lookupClass()).a(45385014767303L);
  
  kf(String paramString) {
    this.g = paramString;
    this.o = "null".equals(paramString);
    this.x = "true".equals(paramString);
    this.q = "false".equals(paramString);
  }
  
  void x(Object[] paramArrayOfObject) throws IOException {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l6 l6 = (l6)paramArrayOfObject[1];
    l6.b(new Object[] { this.g });
  }
  
  public String toString() {
    return this.g;
  }
  
  public int hashCode() {
    return this.g.hashCode();
  }
  
  public boolean w(Object[] paramArrayOfObject) {
    return this.o;
  }
  
  public boolean e(Object[] paramArrayOfObject) {
    return this.x;
  }
  
  public boolean G(Object[] paramArrayOfObject) {
    return this.q;
  }
  
  public boolean o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: invokestatic T : ()I
    //   15: istore #4
    //   17: aload_0
    //   18: getfield x : Z
    //   21: iload #4
    //   23: ifne -> 63
    //   26: ifne -> 62
    //   29: goto -> 36
    //   32: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   35: athrow
    //   36: aload_0
    //   37: getfield q : Z
    //   40: iload #4
    //   42: ifne -> 63
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: ifeq -> 66
    //   55: goto -> 62
    //   58: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   61: athrow
    //   62: iconst_1
    //   63: goto -> 67
    //   66: iconst_0
    //   67: ireturn
    // Exception table:
    //   from	to	target	type
    //   17	29	32	wtf/opal/x5
    //   26	45	48	wtf/opal/x5
    //   36	55	58	wtf/opal/x5
  }
  
  public boolean C(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    int i = lq.T();
    try {
      if (i == 0) {
        try {
          if (this.o) {
            new Object[1];
          } else {
          
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
        return this.x;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public boolean equals(Object paramObject) {
    long l = a ^ 0x23FEA6B9C3E5L;
    int i = lq.T();
    try {
      if (i == 0)
        try {
          if (this == paramObject)
            return true; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (paramObject == null)
        return false; 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (i == 0)
        try {
          if (getClass() != paramObject.getClass())
            return false; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    kf kf1 = (kf)paramObject;
    return this.g.equals(kf1.g);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */