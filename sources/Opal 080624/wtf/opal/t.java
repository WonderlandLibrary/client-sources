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
import net.minecraft.class_1268;
import net.minecraft.class_2596;
import net.minecraft.class_2886;

public final class t extends d {
  private final kt x;
  
  private final kt t;
  
  private final kr k;
  
  private final gm<u0> d;
  
  private static final long a = on.a(-1261682908490126528L, 152329889330592147L, MethodHandles.lookup().lookupClass()).a(136100303539766L);
  
  private static final String[] b;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long l;
  
  public t(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/t.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 110564152144982
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 82395382361347
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #32458
    //   25: ldc2_w 3476481563657563928
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #13140
    //   40: ldc2_w 2438523586029331076
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #7951
    //   64: ldc2_w 6270857501821963998
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   74: ldc2_w 750.0
    //   77: dconst_0
    //   78: ldc2_w 3000.0
    //   81: ldc2_w 50.0
    //   84: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   87: putfield x : Lwtf/opal/kt;
    //   90: aload_0
    //   91: new wtf/opal/kt
    //   94: dup
    //   95: sipush #26400
    //   98: ldc2_w 6393829722151810805
    //   101: lload_1
    //   102: lxor
    //   103: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   108: ldc2_w 50.0
    //   111: ldc2_w 5.0
    //   114: ldc2_w 95.0
    //   117: ldc2_w 5.0
    //   120: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   123: putfield t : Lwtf/opal/kt;
    //   126: aload_0
    //   127: new wtf/opal/kr
    //   130: dup
    //   131: invokespecial <init> : ()V
    //   134: putfield k : Lwtf/opal/kr;
    //   137: aload_0
    //   138: aload_0
    //   139: <illegal opcode> H : (Lwtf/opal/t;)Lwtf/opal/gm;
    //   144: putfield d : Lwtf/opal/gm;
    //   147: aload_0
    //   148: iconst_2
    //   149: anewarray wtf/opal/k3
    //   152: dup
    //   153: iconst_0
    //   154: aload_0
    //   155: getfield x : Lwtf/opal/kt;
    //   158: aastore
    //   159: dup
    //   160: iconst_1
    //   161: aload_0
    //   162: getfield t : Lwtf/opal/kt;
    //   165: aastore
    //   166: lload_3
    //   167: dup2_x1
    //   168: pop2
    //   169: iconst_2
    //   170: anewarray java/lang/Object
    //   173: dup_x1
    //   174: swap
    //   175: iconst_1
    //   176: swap
    //   177: aastore
    //   178: dup_x2
    //   179: dup_x2
    //   180: pop
    //   181: invokestatic valueOf : (J)Ljava/lang/Long;
    //   184: iconst_0
    //   185: swap
    //   186: aastore
    //   187: invokevirtual o : ([Ljava/lang/Object;)V
    //   190: return
  }
  
  private int s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/t.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic y : ()I
    //   21: iconst_0
    //   22: istore #5
    //   24: istore #4
    //   26: iload #5
    //   28: getstatic wtf/opal/t.l : J
    //   31: l2i
    //   32: if_icmpge -> 171
    //   35: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   38: getfield field_1724 : Lnet/minecraft/class_746;
    //   41: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   44: getfield field_7547 : Lnet/minecraft/class_2371;
    //   47: iload #5
    //   49: invokevirtual get : (I)Ljava/lang/Object;
    //   52: checkcast net/minecraft/class_1799
    //   55: astore #6
    //   57: aload #6
    //   59: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   62: astore #8
    //   64: iload #4
    //   66: lload_2
    //   67: lconst_0
    //   68: lcmp
    //   69: ifle -> 80
    //   72: ifeq -> 166
    //   75: aload #8
    //   77: instanceof net/minecraft/class_1809
    //   80: iload #4
    //   82: ifeq -> 172
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: ifeq -> 163
    //   95: goto -> 102
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: aload #8
    //   104: checkcast net/minecraft/class_1809
    //   107: astore #7
    //   109: iload #4
    //   111: lload_2
    //   112: lconst_0
    //   113: lcmp
    //   114: iflt -> 168
    //   117: ifeq -> 166
    //   120: aload #7
    //   122: invokevirtual method_7848 : ()Lnet/minecraft/class_2561;
    //   125: invokeinterface getString : ()Ljava/lang/String;
    //   130: sipush #12968
    //   133: ldc2_w 8160991466007838676
    //   136: lload_2
    //   137: lxor
    //   138: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   143: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   146: ifeq -> 163
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: iload #5
    //   158: ireturn
    //   159: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   162: athrow
    //   163: iinc #5, 1
    //   166: iload #4
    //   168: ifne -> 26
    //   171: iconst_m1
    //   172: ireturn
    // Exception table:
    //   from	to	target	type
    //   64	85	88	wtf/opal/x5
    //   75	95	98	wtf/opal/x5
    //   109	149	152	wtf/opal/x5
    //   120	159	159	wtf/opal/x5
  }
  
  private void lambda$new$1(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/t.a : J
    //   3: ldc2_w 33508381428701
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 67951053638747
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 83285967265816
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic F : ()I
    //   27: istore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual K : ([Ljava/lang/Object;)Z
    //   37: iload #8
    //   39: ifne -> 96
    //   42: ifne -> 346
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   55: getfield field_1724 : Lnet/minecraft/class_746;
    //   58: invokevirtual method_6032 : ()F
    //   61: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   64: getfield field_1724 : Lnet/minecraft/class_746;
    //   67: invokevirtual method_6063 : ()F
    //   70: fdiv
    //   71: ldc 100.0
    //   73: fmul
    //   74: f2d
    //   75: aload_0
    //   76: getfield t : Lwtf/opal/kt;
    //   79: invokevirtual z : ()Ljava/lang/Object;
    //   82: checkcast java/lang/Double
    //   85: invokevirtual doubleValue : ()D
    //   88: dcmpg
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: iload #8
    //   98: ifne -> 171
    //   101: ifgt -> 346
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: aload_0
    //   112: getfield k : Lwtf/opal/kr;
    //   115: aload_0
    //   116: getfield x : Lwtf/opal/kt;
    //   119: invokevirtual z : ()Ljava/lang/Object;
    //   122: checkcast java/lang/Double
    //   125: invokevirtual longValue : ()J
    //   128: lload #4
    //   130: iconst_0
    //   131: iconst_3
    //   132: anewarray java/lang/Object
    //   135: dup_x1
    //   136: swap
    //   137: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   140: iconst_2
    //   141: swap
    //   142: aastore
    //   143: dup_x2
    //   144: dup_x2
    //   145: pop
    //   146: invokestatic valueOf : (J)Ljava/lang/Long;
    //   149: iconst_1
    //   150: swap
    //   151: aastore
    //   152: dup_x2
    //   153: dup_x2
    //   154: pop
    //   155: invokestatic valueOf : (J)Ljava/lang/Long;
    //   158: iconst_0
    //   159: swap
    //   160: aastore
    //   161: invokevirtual v : ([Ljava/lang/Object;)Z
    //   164: goto -> 171
    //   167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: iload #8
    //   173: ifne -> 212
    //   176: ifeq -> 346
    //   179: goto -> 186
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: aload_0
    //   187: lload #6
    //   189: iconst_1
    //   190: anewarray java/lang/Object
    //   193: dup_x2
    //   194: dup_x2
    //   195: pop
    //   196: invokestatic valueOf : (J)Ljava/lang/Long;
    //   199: iconst_0
    //   200: swap
    //   201: aastore
    //   202: invokevirtual s : ([Ljava/lang/Object;)I
    //   205: goto -> 212
    //   208: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: istore #9
    //   214: iload #9
    //   216: iload #8
    //   218: ifne -> 260
    //   221: iconst_m1
    //   222: if_icmpne -> 237
    //   225: goto -> 232
    //   228: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   231: athrow
    //   232: return
    //   233: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   236: athrow
    //   237: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   240: iload #8
    //   242: ifne -> 310
    //   245: getfield field_1724 : Lnet/minecraft/class_746;
    //   248: invokevirtual method_6067 : ()F
    //   251: fconst_2
    //   252: fcmpl
    //   253: goto -> 260
    //   256: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   259: athrow
    //   260: ifle -> 264
    //   263: return
    //   264: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   267: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   270: new net/minecraft/class_2868
    //   273: dup
    //   274: iload #9
    //   276: invokespecial <init> : (I)V
    //   279: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   282: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   285: getfield field_1761 : Lnet/minecraft/class_636;
    //   288: checkcast wtf/opal/mixin/ClientPlayerInteractionManagerAccessor
    //   291: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   294: getfield field_1687 : Lnet/minecraft/class_638;
    //   297: <illegal opcode> predict : ()Lnet/minecraft/class_7204;
    //   302: invokeinterface callSendSequencedPacket : (Lnet/minecraft/class_638;Lnet/minecraft/class_7204;)V
    //   307: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   310: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   313: new net/minecraft/class_2868
    //   316: dup
    //   317: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   320: getfield field_1724 : Lnet/minecraft/class_746;
    //   323: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   326: getfield field_7545 : I
    //   329: invokespecial <init> : (I)V
    //   332: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   335: aload_0
    //   336: getfield k : Lwtf/opal/kr;
    //   339: iconst_0
    //   340: anewarray java/lang/Object
    //   343: invokevirtual z : ([Ljava/lang/Object;)V
    //   346: return
    // Exception table:
    //   from	to	target	type
    //   29	45	48	wtf/opal/x5
    //   42	89	92	wtf/opal/x5
    //   96	104	107	wtf/opal/x5
    //   101	164	167	wtf/opal/x5
    //   171	179	182	wtf/opal/x5
    //   176	205	208	wtf/opal/x5
    //   214	225	228	wtf/opal/x5
    //   221	233	233	wtf/opal/x5
    //   237	253	256	wtf/opal/x5
  }
  
  private static class_2596 lambda$new$0(int paramInt) {
    return (class_2596)new class_2886(class_1268.field_5808, paramInt);
  }
  
  static {
    long l = a ^ 0x5B1FD4DF8FF0L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "¡(ç\016±÷_\000¦\013aa8ýÚ\034{\025«%\020Tñá%ÒÚ\rHýo\006N§Ç¨e(YqÑWvÓo[\\ueß»owS4½<qm5\037ö-T±ò&b\004ÞÕc\t` ð¹Álâëô/1±gH0¬\027Ï8\020yWæ¶I`ÑdÆ$Í'u").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x66BB;
    if (f[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/t", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      f[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return f[i];
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
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/t'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\t.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */