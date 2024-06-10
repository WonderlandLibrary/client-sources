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

public final class jw extends d {
  private final ky<x4> y;
  
  public final ke m;
  
  public final ke w;
  
  public final ke o;
  
  public final ke R;
  
  public final kt J;
  
  public final kt x;
  
  private static final long a = on.a(-2179730251263179463L, 153730600873807251L, MethodHandles.lookup().lookupClass()).a(172103861916144L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map l;
  
  public jw(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jw.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 22339499587523
    //   11: lxor
    //   12: dup2
    //   13: bipush #48
    //   15: lushr
    //   16: l2i
    //   17: istore_3
    //   18: dup2
    //   19: bipush #16
    //   21: lshl
    //   22: bipush #32
    //   24: lushr
    //   25: l2i
    //   26: istore #4
    //   28: dup2
    //   29: bipush #48
    //   31: lshl
    //   32: bipush #48
    //   34: lushr
    //   35: l2i
    //   36: istore #5
    //   38: pop2
    //   39: dup2
    //   40: ldc2_w 53367616102657
    //   43: lxor
    //   44: lstore #6
    //   46: dup2
    //   47: ldc2_w 34046468995156
    //   50: lxor
    //   51: lstore #8
    //   53: pop2
    //   54: aload_0
    //   55: sipush #10098
    //   58: ldc2_w 5887973146846910214
    //   61: lload_1
    //   62: lxor
    //   63: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   68: lload #8
    //   70: sipush #31466
    //   73: ldc2_w 7768065277094333080
    //   76: lload_1
    //   77: lxor
    //   78: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   83: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   86: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   89: invokestatic P : ()Ljava/lang/String;
    //   92: aload_0
    //   93: new wtf/opal/ky
    //   96: dup
    //   97: sipush #17866
    //   100: ldc2_w 8354419875677615551
    //   103: lload_1
    //   104: lxor
    //   105: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   110: aload_0
    //   111: getstatic wtf/opal/x4.VANILLA : Lwtf/opal/x4;
    //   114: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   117: putfield y : Lwtf/opal/ky;
    //   120: astore #10
    //   122: aload_0
    //   123: new wtf/opal/ke
    //   126: dup
    //   127: sipush #9513
    //   130: ldc2_w 7177033195023488350
    //   133: lload_1
    //   134: lxor
    //   135: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   140: iconst_1
    //   141: invokespecial <init> : (Ljava/lang/String;Z)V
    //   144: putfield m : Lwtf/opal/ke;
    //   147: aload_0
    //   148: new wtf/opal/ke
    //   151: dup
    //   152: sipush #18676
    //   155: ldc2_w 5613574379660490891
    //   158: lload_1
    //   159: lxor
    //   160: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   165: iconst_1
    //   166: invokespecial <init> : (Ljava/lang/String;Z)V
    //   169: putfield w : Lwtf/opal/ke;
    //   172: aload_0
    //   173: new wtf/opal/ke
    //   176: dup
    //   177: sipush #26565
    //   180: ldc2_w 5978768898162825140
    //   183: lload_1
    //   184: lxor
    //   185: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   190: iconst_1
    //   191: invokespecial <init> : (Ljava/lang/String;Z)V
    //   194: putfield o : Lwtf/opal/ke;
    //   197: aload_0
    //   198: new wtf/opal/ke
    //   201: dup
    //   202: sipush #29677
    //   205: ldc2_w 2718865978481116051
    //   208: lload_1
    //   209: lxor
    //   210: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   215: iconst_0
    //   216: invokespecial <init> : (Ljava/lang/String;Z)V
    //   219: putfield R : Lwtf/opal/ke;
    //   222: aload_0
    //   223: new wtf/opal/kt
    //   226: dup
    //   227: sipush #7323
    //   230: ldc2_w 5081464225388948712
    //   233: lload_1
    //   234: lxor
    //   235: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   240: dconst_0
    //   241: dconst_0
    //   242: ldc2_w 100.0
    //   245: dconst_1
    //   246: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   249: putfield J : Lwtf/opal/kt;
    //   252: aload_0
    //   253: new wtf/opal/kt
    //   256: dup
    //   257: sipush #23098
    //   260: ldc2_w 2144364809724078668
    //   263: lload_1
    //   264: lxor
    //   265: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   270: dconst_0
    //   271: dconst_0
    //   272: ldc2_w 100.0
    //   275: dconst_1
    //   276: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   279: putfield x : Lwtf/opal/kt;
    //   282: aload_0
    //   283: sipush #7415
    //   286: ldc2_w 3445292121863330414
    //   289: lload_1
    //   290: lxor
    //   291: <illegal opcode> f : (IJ)I
    //   296: anewarray wtf/opal/k3
    //   299: dup
    //   300: iconst_0
    //   301: aload_0
    //   302: getfield y : Lwtf/opal/ky;
    //   305: aastore
    //   306: dup
    //   307: iconst_1
    //   308: aload_0
    //   309: getfield J : Lwtf/opal/kt;
    //   312: aastore
    //   313: dup
    //   314: iconst_2
    //   315: aload_0
    //   316: getfield x : Lwtf/opal/kt;
    //   319: aastore
    //   320: dup
    //   321: iconst_3
    //   322: aload_0
    //   323: getfield m : Lwtf/opal/ke;
    //   326: aastore
    //   327: dup
    //   328: iconst_4
    //   329: aload_0
    //   330: getfield w : Lwtf/opal/ke;
    //   333: aastore
    //   334: dup
    //   335: iconst_5
    //   336: aload_0
    //   337: getfield o : Lwtf/opal/ke;
    //   340: aastore
    //   341: dup
    //   342: sipush #28206
    //   345: ldc2_w 1647372145188497590
    //   348: lload_1
    //   349: lxor
    //   350: <illegal opcode> f : (IJ)I
    //   355: aload_0
    //   356: getfield R : Lwtf/opal/ke;
    //   359: aastore
    //   360: lload #6
    //   362: dup2_x1
    //   363: pop2
    //   364: iconst_2
    //   365: anewarray java/lang/Object
    //   368: dup_x1
    //   369: swap
    //   370: iconst_1
    //   371: swap
    //   372: aastore
    //   373: dup_x2
    //   374: dup_x2
    //   375: pop
    //   376: invokestatic valueOf : (J)Ljava/lang/Long;
    //   379: iconst_0
    //   380: swap
    //   381: aastore
    //   382: invokevirtual o : ([Ljava/lang/Object;)V
    //   385: aload_0
    //   386: aload_0
    //   387: getfield y : Lwtf/opal/ky;
    //   390: iconst_1
    //   391: anewarray wtf/opal/u_
    //   394: dup
    //   395: iconst_0
    //   396: new wtf/opal/pm
    //   399: dup
    //   400: aload_0
    //   401: iload_3
    //   402: i2s
    //   403: iload #4
    //   405: iload #5
    //   407: invokespecial <init> : (Lwtf/opal/jw;SII)V
    //   410: aastore
    //   411: iconst_2
    //   412: anewarray java/lang/Object
    //   415: dup_x1
    //   416: swap
    //   417: iconst_1
    //   418: swap
    //   419: aastore
    //   420: dup_x1
    //   421: swap
    //   422: iconst_0
    //   423: swap
    //   424: aastore
    //   425: invokevirtual Y : ([Ljava/lang/Object;)V
    //   428: invokestatic D : ()[Lwtf/opal/d;
    //   431: ifnull -> 446
    //   434: ldc 'CB4EFb'
    //   436: invokestatic Y : (Ljava/lang/String;)V
    //   439: goto -> 446
    //   442: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   445: athrow
    //   446: return
    // Exception table:
    //   from	to	target	type
    //   122	439	442	wtf/opal/x5
  }
  
  public String o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: aload_0
    //   13: getfield J : Lwtf/opal/kt;
    //   16: invokevirtual z : ()Ljava/lang/Object;
    //   19: checkcast java/lang/Double
    //   22: invokevirtual intValue : ()I
    //   25: aload_0
    //   26: getfield x : Lwtf/opal/kt;
    //   29: invokevirtual z : ()Ljava/lang/Object;
    //   32: checkcast java/lang/Double
    //   35: invokevirtual intValue : ()I
    //   38: sipush #25350
    //   41: ldc2_w 3615429728991139514
    //   44: lload_2
    //   45: lxor
    //   46: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   51: swap
    //   52: ldc '%'
    //   54: <illegal opcode> makeConcatWithConstants : (ILjava/lang/String;ILjava/lang/String;)Ljava/lang/String;
    //   59: areturn
  }
  
  static {
    long l = a ^ 0x525D05F4C301L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "©'AÈ\"i¹¯Ù8æõ\034a¤Ù¥éÀnôH\031~]³dv\001ÝÑ\030^h`Ü\037³%Ó;\001±\033\005RSÒVÈ¶¶\020|2¸\032RÁ5-l\nEO\002õV\030?\025¾L¡ãñÎÑ\n4\033Iw\026pý\"@­vÙ %ð\033\023åÐRCk9aÎu*\034üT\"\032Æ»sF?«[Ðf\004@îâÄ®j{xÂÚ~\031´>ëm¾H*;ÂO\034\nM\020ü A3\b©:\"ëâ:}a\003\0230¾³@eE^x¸VïÇÌÊWQ¿Ú+^É\030qî\013{'»<iy\bFÔ\022`¨X4,]wô\020FL{c¤,,~yÄ\037¶v").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x124A;
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
        throw new RuntimeException("wtf/opal/jw", exception);
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
    //   66: ldc_w 'wtf/opal/jw'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x64A5;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])l.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          l.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jw", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      k[i] = Integer.valueOf(j);
    } 
    return k[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   66: ldc_w 'wtf/opal/jw'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */