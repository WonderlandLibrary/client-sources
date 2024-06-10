package wtf.opal;

import dev.jnic.eEZCNM.JNICLoader;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;

public final class bf {
  private static final char[] G;
  
  private static int u;
  
  private static final long a;
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d;
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public static native String S(Object[] paramArrayOfObject);
  
  public static native byte[] f(Object[] paramArrayOfObject);
  
  public static native byte[] e(Object[] paramArrayOfObject);
  
  public static native String Q(Object[] paramArrayOfObject);
  
  public static native void N(int paramInt);
  
  public static native int O();
  
  public static native int Z();
  
  private static native x5 a(x5 paramx5);
  
  private static native String a(byte[] paramArrayOfbyte);
  
  private static native String a(int paramInt, long paramLong);
  
  private static native Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject);
  
  private static native CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType);
  
  private static native int b(int paramInt, long paramLong);
  
  private static native int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject);
  
  private static native CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType);
  
  static {
    JNICLoader.init();
    $jnicLoader();
    $jnicClinit();
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */