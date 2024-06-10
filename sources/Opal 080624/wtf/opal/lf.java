package wtf.opal;

import dev.jnic.eEZCNM.JNICLoader;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import kong.unirest.core.UnirestException;

public final class lf {
  public final String h;
  
  public final String u;
  
  public final String a;
  
  public final String c;
  
  private String T;
  
  private boolean s;
  
  private po z;
  
  private final Map<String, String> k;
  
  private static int[] E;
  
  private static final long b;
  
  private static final String[] d;
  
  private static final String[] e;
  
  private static final Map f;
  
  public lf(String paramString1, short paramShort1, int paramInt, String paramString2, String paramString3, String paramString4, short paramShort2) {
    int[] arrayOfInt = l();
    try {
      this.k = new HashMap<>();
      this.h = paramString1;
      this.u = paramString2;
      this.a = paramString3;
      this.c = paramString4;
      if (arrayOfInt != null)
        d.p(new d[1]); 
    } catch (UnirestException unirestException) {
      throw a(null);
    } 
  }
  
  public native po c(Object[] paramArrayOfObject);
  
  public native boolean y(Object[] paramArrayOfObject);
  
  public native void k(Object[] paramArrayOfObject);
  
  public native void x(Object[] paramArrayOfObject);
  
  public String n(Object[] p1) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore #4
    //   19: pop
    //   20: getstatic wtf/opal/lf.b : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 110026732164850
    //   31: lxor
    //   32: lstore #5
    //   34: pop2
    //   35: invokestatic l : ()[I
    //   38: astore #7
    //   40: aload_0
    //   41: aload #7
    //   43: ifnonnull -> 114
    //   46: getfield s : Z
    //   49: ifne -> 85
    //   52: goto -> 59
    //   55: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   58: athrow
    //   59: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   62: sipush #17306
    //   65: ldc2_w 2080263765814672818
    //   68: lload_2
    //   69: lxor
    //   70: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   75: invokevirtual println : (Ljava/lang/String;)V
    //   78: ldc ''
    //   80: areturn
    //   81: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   84: athrow
    //   85: aload_0
    //   86: lload_2
    //   87: lconst_0
    //   88: lcmp
    //   89: iflt -> 143
    //   92: aload #7
    //   94: ifnonnull -> 143
    //   97: getfield k : Ljava/util/Map;
    //   100: aload #4
    //   102: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   107: goto -> 114
    //   110: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   113: athrow
    //   114: lload_2
    //   115: lconst_0
    //   116: lcmp
    //   117: ifle -> 134
    //   120: ifnull -> 142
    //   123: aload_0
    //   124: getfield k : Ljava/util/Map;
    //   127: aload #4
    //   129: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   134: checkcast java/lang/String
    //   137: areturn
    //   138: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   141: athrow
    //   142: aload_0
    //   143: lload #5
    //   145: aload #4
    //   147: iconst_2
    //   148: anewarray java/lang/Object
    //   151: dup_x1
    //   152: swap
    //   153: iconst_1
    //   154: swap
    //   155: aastore
    //   156: dup_x2
    //   157: dup_x2
    //   158: pop
    //   159: invokestatic valueOf : (J)Ljava/lang/Long;
    //   162: iconst_0
    //   163: swap
    //   164: aastore
    //   165: invokevirtual E : ([Ljava/lang/Object;)Ljava/lang/String;
    //   168: lload_2
    //   169: lconst_0
    //   170: lcmp
    //   171: iflt -> 186
    //   174: invokestatic D : ()[Lwtf/opal/d;
    //   177: ifnull -> 193
    //   180: iconst_4
    //   181: newarray int
    //   183: invokestatic q : ([I)V
    //   186: goto -> 193
    //   189: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   192: athrow
    //   193: areturn
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	194	0	this	Lwtf/opal/lf;
    //   0	194	1	p1	[Ljava/lang/Object;
    //   0	194	2	v3	J
    //   0	194	4	v2	Ljava/lang/String;
    //   0	194	5	v5	J
    //   0	194	7	v7	[I
    // Exception table:
    //   from	to	target	type
    //   40	52	55	kong/unirest/core/UnirestException
    //   46	81	81	kong/unirest/core/UnirestException
    //   85	107	110	kong/unirest/core/UnirestException
    //   114	138	138	kong/unirest/core/UnirestException
    //   143	186	189	kong/unirest/core/UnirestException
  }
  
  private native String E(Object[] paramArrayOfObject);
  
  public static native void q(int[] paramArrayOfint);
  
  public static native int[] l();
  
  private static native Exception a(Exception paramException);
  
  private static native String a(byte[] paramArrayOfbyte);
  
  private static native String a(int paramInt, long paramLong);
  
  private static native Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject);
  
  private static native CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType);
  
  static {
    JNICLoader.init();
    $jnicLoader();
    $jnicClinit();
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */