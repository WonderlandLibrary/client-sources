package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2743;
import net.minecraft.class_6373;

public final class f extends d {
  private class_2743 o;
  
  private class_6373 U;
  
  private final Deque<le> L;
  
  private final gm<lu> X;
  
  private final gm<l3> M;
  
  private static final long a = on.a(5120876914159485474L, -1766093372360239268L, MethodHandles.lookup().lookupClass()).a(12268236987027L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public f(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/f.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 14515323134621
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: invokestatic n : ()Ljava/lang/String;
    //   17: aload_0
    //   18: sipush #24179
    //   21: ldc2_w 4700805141995295513
    //   24: lload_1
    //   25: lxor
    //   26: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   31: lload_3
    //   32: sipush #875
    //   35: ldc2_w 3395407550833905152
    //   38: lload_1
    //   39: lxor
    //   40: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   45: getstatic wtf/opal/kn.EXPLOIT : Lwtf/opal/kn;
    //   48: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   51: aload_0
    //   52: new java/util/LinkedList
    //   55: dup
    //   56: invokespecial <init> : ()V
    //   59: putfield L : Ljava/util/Deque;
    //   62: aload_0
    //   63: aload_0
    //   64: <illegal opcode> H : (Lwtf/opal/f;)Lwtf/opal/gm;
    //   69: putfield X : Lwtf/opal/gm;
    //   72: astore #5
    //   74: aload_0
    //   75: aload_0
    //   76: <illegal opcode> H : (Lwtf/opal/f;)Lwtf/opal/gm;
    //   81: putfield M : Lwtf/opal/gm;
    //   84: invokestatic D : ()[Lwtf/opal/d;
    //   87: ifnull -> 102
    //   90: ldc 'TXa3Oc'
    //   92: invokestatic J : (Ljava/lang/String;)V
    //   95: goto -> 102
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: return
    // Exception table:
    //   from	to	target	type
    //   74	95	98	wtf/opal/x5
  }
  
  private void lambda$new$1(l3 paraml3) {
    // Byte code:
    //   0: getstatic wtf/opal/f.a : J
    //   3: ldc2_w 74373413957487
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_0
    //   14: aload #4
    //   16: ifnonnull -> 40
    //   19: getfield U : Lnet/minecraft/class_6373;
    //   22: ifnull -> 111
    //   25: goto -> 32
    //   28: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   31: athrow
    //   32: aload_0
    //   33: goto -> 40
    //   36: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   39: athrow
    //   40: aload #4
    //   42: ifnonnull -> 107
    //   45: getfield o : Lnet/minecraft/class_2743;
    //   48: ifnull -> 111
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: aload_0
    //   59: getfield L : Ljava/util/Deque;
    //   62: new wtf/opal/le
    //   65: dup
    //   66: aload_0
    //   67: getfield o : Lnet/minecraft/class_2743;
    //   70: invokevirtual method_11816 : ()I
    //   73: i2d
    //   74: ldc2_w 8000.0
    //   77: ddiv
    //   78: aload_0
    //   79: getfield U : Lnet/minecraft/class_6373;
    //   82: invokevirtual method_36950 : ()I
    //   85: invokespecial <init> : (DI)V
    //   88: invokeinterface add : (Ljava/lang/Object;)Z
    //   93: pop
    //   94: aload_0
    //   95: aconst_null
    //   96: putfield o : Lnet/minecraft/class_2743;
    //   99: aload_0
    //   100: goto -> 107
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: aconst_null
    //   108: putfield U : Lnet/minecraft/class_6373;
    //   111: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   114: getfield field_1690 : Lnet/minecraft/class_315;
    //   117: getfield field_1903 : Lnet/minecraft/class_304;
    //   120: invokevirtual method_1434 : ()Z
    //   123: aload #4
    //   125: ifnonnull -> 166
    //   128: ifeq -> 230
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: aload_0
    //   139: getfield L : Ljava/util/Deque;
    //   142: aload #4
    //   144: ifnonnull -> 185
    //   147: goto -> 154
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: invokeinterface isEmpty : ()Z
    //   159: goto -> 166
    //   162: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: ifne -> 230
    //   169: aload_0
    //   170: getfield L : Ljava/util/Deque;
    //   173: invokeinterface poll : ()Ljava/lang/Object;
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: checkcast wtf/opal/le
    //   188: astore #5
    //   190: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   193: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   196: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   199: new net/minecraft/class_6374
    //   202: dup
    //   203: aload #5
    //   205: getfield Q : I
    //   208: invokespecial <init> : (I)V
    //   211: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   214: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   217: getfield field_1724 : Lnet/minecraft/class_746;
    //   220: dconst_0
    //   221: aload #5
    //   223: getfield U : D
    //   226: dconst_0
    //   227: invokevirtual method_18800 : (DDD)V
    //   230: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	wtf/opal/x5
    //   19	33	36	wtf/opal/x5
    //   40	51	54	wtf/opal/x5
    //   45	100	103	wtf/opal/x5
    //   111	131	134	wtf/opal/x5
    //   128	147	150	wtf/opal/x5
    //   138	159	162	wtf/opal/x5
    //   166	178	181	wtf/opal/x5
  }
  
  private void lambda$new$0(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/f.a : J
    //   3: ldc2_w 39770016707772
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Ljava/lang/String;
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #7
    //   21: astore #4
    //   23: aload #7
    //   25: instanceof net/minecraft/class_2743
    //   28: aload #4
    //   30: ifnonnull -> 132
    //   33: ifeq -> 105
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #7
    //   45: checkcast net/minecraft/class_2743
    //   48: astore #5
    //   50: aload #4
    //   52: ifnonnull -> 100
    //   55: aload #5
    //   57: invokevirtual method_11818 : ()I
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: getfield field_1724 : Lnet/minecraft/class_746;
    //   66: invokevirtual method_5628 : ()I
    //   69: if_icmpne -> 92
    //   72: goto -> 79
    //   75: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: aload_0
    //   80: aload #5
    //   82: putfield o : Lnet/minecraft/class_2743;
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload_1
    //   93: iconst_0
    //   94: anewarray java/lang/Object
    //   97: invokevirtual Z : ([Ljava/lang/Object;)V
    //   100: aload #4
    //   102: ifnull -> 174
    //   105: aload_1
    //   106: iconst_0
    //   107: anewarray java/lang/Object
    //   110: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   113: astore #7
    //   115: aload #7
    //   117: aload #4
    //   119: ifnonnull -> 137
    //   122: instanceof net/minecraft/class_6373
    //   125: goto -> 132
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: ifeq -> 174
    //   135: aload #7
    //   137: checkcast net/minecraft/class_6373
    //   140: astore #6
    //   142: aload_0
    //   143: aload #4
    //   145: ifnonnull -> 169
    //   148: getfield o : Lnet/minecraft/class_2743;
    //   151: ifnull -> 174
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: aload_0
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: aload #6
    //   171: putfield U : Lnet/minecraft/class_6373;
    //   174: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   50	72	75	wtf/opal/x5
    //   55	85	88	wtf/opal/x5
    //   115	125	128	wtf/opal/x5
    //   142	154	157	wtf/opal/x5
    //   148	162	165	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x4B7B6671773L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "²~»^âÙ<z¬Mg_»\\\031(íæ{£\030j3`¦G\036\fh$H\035P\026ÚØ<èÂükjM\002Ë").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x759E;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])f.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/f", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
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
    //   66: ldc_w 'wtf/opal/f'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */