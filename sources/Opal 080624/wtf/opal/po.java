package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONObject;

public class po {
  private String l;
  
  private String H;
  
  private String e;
  
  private static String L;
  
  private static final long a = on.a(-8937854595973973290L, 2660068418905008572L, MethodHandles.lookup().lookupClass()).a(254903429030195L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public po(JSONObject paramJSONObject, long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/po.a : J
    //   3: lload_2
    //   4: lxor
    //   5: lstore_2
    //   6: invokestatic Z : ()Ljava/lang/String;
    //   9: aload_0
    //   10: invokespecial <init> : ()V
    //   13: astore #4
    //   15: aload_1
    //   16: sipush #11307
    //   19: ldc2_w 6105819694481698985
    //   22: lload_2
    //   23: lxor
    //   24: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   29: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   32: astore #5
    //   34: aload #5
    //   36: sipush #31593
    //   39: ldc2_w 273142116134954991
    //   42: lload_2
    //   43: lxor
    //   44: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   49: invokevirtual getJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   52: astore #6
    //   54: aload #6
    //   56: iconst_0
    //   57: invokevirtual getJSONObject : (I)Lorg/json/JSONObject;
    //   60: astore #7
    //   62: aload_0
    //   63: aload #5
    //   65: sipush #14676
    //   68: ldc2_w 1603257982388364757
    //   71: lload_2
    //   72: lxor
    //   73: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   78: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   81: putfield l : Ljava/lang/String;
    //   84: aload_0
    //   85: aload #7
    //   87: sipush #701
    //   90: ldc2_w 1319374265179692606
    //   93: lload_2
    //   94: lxor
    //   95: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   100: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   103: putfield H : Ljava/lang/String;
    //   106: aload_0
    //   107: aload #7
    //   109: sipush #684
    //   112: ldc2_w 8224141737401048620
    //   115: lload_2
    //   116: lxor
    //   117: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   122: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   125: putfield e : Ljava/lang/String;
    //   128: aload #4
    //   130: ifnull -> 147
    //   133: iconst_1
    //   134: anewarray wtf/opal/d
    //   137: invokestatic p : ([Lwtf/opal/d;)V
    //   140: goto -> 147
    //   143: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   146: athrow
    //   147: return
    // Exception table:
    //   from	to	target	type
    //   62	140	143	wtf/opal/x5
  }
  
  public String l(Object[] paramArrayOfObject) {
    return this.l;
  }
  
  public String W(Object[] paramArrayOfObject) {
    return this.H;
  }
  
  public String v(Object[] paramArrayOfObject) {
    return this.e;
  }
  
  public static void K(String paramString) {
    L = paramString;
  }
  
  public static String Z() {
    return L;
  }
  
  static {
    long l = a ^ 0x8347F58037FL;
    K(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "q·òy'Ï°½Tj¸9Ø Z\003é¬\002\nå.º¼UðÊÄQi(N\007=J^ã\024\020£\002á£]NQv\030áN0").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x45B;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/po", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 102
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc 'wtf/opal/po'
    //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: ldc_w ' : '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_1
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: ldc_w ' : '
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_2
    //   87: invokevirtual toString : ()Ljava/lang/String;
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: aload #4
    //   98: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: aload_3
    //   103: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\po.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */