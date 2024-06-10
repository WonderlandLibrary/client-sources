package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public final class pl {
  private final Map<Type, SortedSet<li<?>>> v = new HashMap<>();
  
  private static int[] n;
  
  private static final long a = on.a(4968580379370787371L, 7185555691644137776L, MethodHandles.lookup().lookupClass()).a(167490544324126L);
  
  public void a(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Object
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/pl.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 107564358073009
    //   31: lxor
    //   32: lstore #5
    //   34: pop2
    //   35: invokestatic O : ()[I
    //   38: aload #4
    //   40: invokevirtual getClass : ()Ljava/lang/Class;
    //   43: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   46: astore #8
    //   48: aload #8
    //   50: arraylength
    //   51: istore #9
    //   53: astore #7
    //   55: iconst_0
    //   56: istore #10
    //   58: iload #10
    //   60: iload #9
    //   62: if_icmpge -> 297
    //   65: aload #8
    //   67: iload #10
    //   69: aaload
    //   70: astore #11
    //   72: aload #7
    //   74: lload_2
    //   75: lconst_0
    //   76: lcmp
    //   77: iflt -> 294
    //   80: ifnull -> 292
    //   83: aload #11
    //   85: invokevirtual getType : ()Ljava/lang/Class;
    //   88: ldc wtf/opal/gm
    //   90: invokevirtual isAssignableFrom : (Ljava/lang/Class;)Z
    //   93: ifeq -> 289
    //   96: goto -> 103
    //   99: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   102: athrow
    //   103: aload #11
    //   105: lload_2
    //   106: lconst_0
    //   107: lcmp
    //   108: ifle -> 158
    //   111: aload #4
    //   113: aload #7
    //   115: ifnull -> 155
    //   118: goto -> 125
    //   121: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   124: athrow
    //   125: invokevirtual canAccess : (Ljava/lang/Object;)Z
    //   128: ifne -> 151
    //   131: goto -> 138
    //   134: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   137: athrow
    //   138: aload #11
    //   140: iconst_1
    //   141: invokevirtual setAccessible : (Z)V
    //   144: goto -> 151
    //   147: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   150: athrow
    //   151: aload #11
    //   153: aload #4
    //   155: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   158: checkcast wtf/opal/gm
    //   161: astore #12
    //   163: iconst_0
    //   164: istore #13
    //   166: aload #11
    //   168: ldc wtf/opal/b1
    //   170: invokevirtual isAnnotationPresent : (Ljava/lang/Class;)Z
    //   173: aload #7
    //   175: ifnull -> 210
    //   178: ifeq -> 212
    //   181: goto -> 188
    //   184: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   187: athrow
    //   188: aload #11
    //   190: ldc wtf/opal/b1
    //   192: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
    //   195: checkcast wtf/opal/b1
    //   198: invokeinterface value : ()B
    //   203: goto -> 210
    //   206: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   209: athrow
    //   210: istore #13
    //   212: aload #12
    //   214: ifnull -> 274
    //   217: aload #11
    //   219: invokevirtual getGenericType : ()Ljava/lang/reflect/Type;
    //   222: checkcast java/lang/reflect/ParameterizedType
    //   225: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
    //   230: iconst_0
    //   231: aaload
    //   232: astore #14
    //   234: aload_0
    //   235: getfield v : Ljava/util/Map;
    //   238: aload #14
    //   240: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   245: invokeinterface computeIfAbsent : (Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;
    //   250: checkcast java/util/SortedSet
    //   253: new wtf/opal/li
    //   256: dup
    //   257: aload #4
    //   259: lload #5
    //   261: aload #12
    //   263: iload #13
    //   265: invokespecial <init> : (Ljava/lang/Object;JLwtf/opal/gm;I)V
    //   268: invokeinterface add : (Ljava/lang/Object;)Z
    //   273: pop
    //   274: goto -> 289
    //   277: astore #12
    //   279: new java/lang/RuntimeException
    //   282: dup
    //   283: aload #12
    //   285: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   288: athrow
    //   289: iinc #10, 1
    //   292: aload #7
    //   294: ifnonnull -> 58
    //   297: lload_2
    //   298: lconst_0
    //   299: lcmp
    //   300: iflt -> 315
    //   303: invokestatic D : ()[Lwtf/opal/d;
    //   306: ifnull -> 322
    //   309: iconst_2
    //   310: newarray int
    //   312: invokestatic c : ([I)V
    //   315: goto -> 322
    //   318: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   321: athrow
    //   322: return
    // Exception table:
    //   from	to	target	type
    //   72	96	99	java/lang/IllegalAccessException
    //   83	118	121	java/lang/IllegalAccessException
    //   103	131	134	java/lang/IllegalAccessException
    //   125	144	147	java/lang/IllegalAccessException
    //   151	274	277	java/lang/IllegalAccessException
    //   166	181	184	java/lang/IllegalAccessException
    //   178	203	206	java/lang/IllegalAccessException
    //   297	315	318	java/lang/IllegalAccessException
  }
  
  public void N(Object[] paramArrayOfObject) {
    Object object = paramArrayOfObject[0];
    this.v.values().forEach(object::lambda$unsubscribe$2);
  }
  
  public void R(Object[] paramArrayOfObject) {
    Object object = paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = a ^ l;
    SortedSet sortedSet = this.v.get(object.getClass());
    int[] arrayOfInt = O();
    try {
      if (arrayOfInt != null)
        if (sortedSet != null) {
        
        } else {
          return;
        }  
    } catch (RuntimeException runtimeException) {
      throw a(null);
    } 
    try {
      if (l >= 0L)
        if (arrayOfInt != null)
          try {
            if (!sortedSet.isEmpty()) {
            
            } else {
              return;
            } 
          } catch (RuntimeException runtimeException) {
            throw a(null);
          }   
    } catch (RuntimeException runtimeException) {
      throw a(null);
    } 
    sortedSet.forEach(object::lambda$dispatch$3);
  }
  
  private static void lambda$dispatch$3(Object paramObject, li paramli) {
    long l = a ^ 0x34CB2134AB44L;
    li li1 = paramli;
    int[] arrayOfInt = O();
    try {
      if (arrayOfInt != null) {
        try {
          if (b9.c.field_1724 == null)
            return; 
        } catch (RuntimeException runtimeException) {
          throw a(null);
        } 
        li1.O(new Object[0]).H(paramObject);
      } 
    } catch (RuntimeException runtimeException) {
      throw a(null);
    } 
  }
  
  private static void lambda$unsubscribe$2(Object paramObject, SortedSet paramSortedSet) {
    paramSortedSet.removeIf(paramObject::lambda$unsubscribe$1);
  }
  
  private static boolean lambda$unsubscribe$1(Object paramObject, li paramli) {
    return paramli.i(new Object[0]).equals(paramObject);
  }
  
  private static SortedSet lambda$subscribe$0(Type paramType) {
    return new ConcurrentSkipListSet();
  }
  
  public static void c(int[] paramArrayOfint) {
    n = paramArrayOfint;
  }
  
  public static int[] O() {
    return n;
  }
  
  static {
    if (O() == null)
      c(new int[3]); 
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */