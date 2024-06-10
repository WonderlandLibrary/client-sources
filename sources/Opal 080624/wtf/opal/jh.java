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

public final class jh extends d {
  private final kt b;
  
  private final kr d;
  
  private final gm<b6> t;
  
  private static final long a = on.a(-6430630144095769922L, -1126947283816118494L, MethodHandles.lookup().lookupClass()).a(93245632299916L);
  
  private static final String[] f;
  
  private static final String[] g;
  
  private static final Map k = new HashMap<>(13);
  
  private static final long l;
  
  public jh(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jh.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 13433286236851
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 37697938605030
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #28099
    //   25: ldc2_w 1897339949314570177
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #3558
    //   40: ldc2_w 6119243866673682405
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.OTHER : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #8237
    //   64: ldc2_w 95178596337302060
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   74: ldc2_w 10.0
    //   77: dconst_1
    //   78: ldc2_w 30.0
    //   81: dconst_1
    //   82: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   85: putfield b : Lwtf/opal/kt;
    //   88: aload_0
    //   89: new wtf/opal/kr
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: putfield d : Lwtf/opal/kr;
    //   99: aload_0
    //   100: aload_0
    //   101: <illegal opcode> H : (Lwtf/opal/jh;)Lwtf/opal/gm;
    //   106: putfield t : Lwtf/opal/gm;
    //   109: aload_0
    //   110: iconst_1
    //   111: anewarray wtf/opal/k3
    //   114: dup
    //   115: iconst_0
    //   116: aload_0
    //   117: getfield b : Lwtf/opal/kt;
    //   120: aastore
    //   121: lload_3
    //   122: dup2_x1
    //   123: pop2
    //   124: iconst_2
    //   125: anewarray java/lang/Object
    //   128: dup_x1
    //   129: swap
    //   130: iconst_1
    //   131: swap
    //   132: aastore
    //   133: dup_x2
    //   134: dup_x2
    //   135: pop
    //   136: invokestatic valueOf : (J)Ljava/lang/Long;
    //   139: iconst_0
    //   140: swap
    //   141: aastore
    //   142: invokevirtual o : ([Ljava/lang/Object;)V
    //   145: return
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.d.z(new Object[0]);
    new Object[1];
    super.K(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$0(b6 paramb6) {
    long l1 = a ^ 0x50E6D9D33D88L;
    long l2 = l1 ^ 0x17BD443E44FCL;
    String str = j2.z();
    try {
      if (str == null)
        try {
          if (paramb6.W(new Object[0])) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (str == null)
        try {
          if (paramb6.W(new Object[0])) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (str == null)
        try {
          if (paramb6.W(new Object[0])) {
            (new Object[3])[2] = Boolean.valueOf(true);
            new Object[3];
            (new Object[3])[1] = Long.valueOf(l2);
            new Object[3];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (paramb6.W(new Object[0]))
        b9.c.field_1724.method_6043(); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  static {
    long l = a ^ 0x28A76415F1E7L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "\034\bº¢\016u«üG{Â­[kN³ìC'\034jÎ´ÑZ3T6ÿhUü·n*øxa·*Pb\036:ÄÈ£\005\013C ^\024Å¯âoÄ¢ÙAu·ã0ÇÑ°\035×­|¢æ·ö\000þ\030Àq¥º\000\034³\033\021!i½_Gaf!\033ý\022` Äq ³\003ÓO´©¨â\021§Dç¶:t\034.Aæ\002\024I£oP").length();
    byte b2 = 88;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x38C;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])k.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          k.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jh", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = f[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/jh'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jh.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */