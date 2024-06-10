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

public final class ju extends d {
  public final ke r;
  
  public final ke g;
  
  public final ke I;
  
  public final ke x;
  
  private final gm<l7> y;
  
  private static final long a = on.a(3294514092585563439L, -7773773383655307215L, MethodHandles.lookup().lookupClass()).a(204991154799954L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public ju(int paramInt, char paramChar, short paramShort) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/ju.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 20714994803128
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 66698821794029
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #29786
    //   50: ldc2_w 964132042705897754
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #11857
    //   66: ldc2_w 3414478941890171675
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: new wtf/opal/ke
    //   87: dup
    //   88: sipush #12018
    //   91: ldc2_w 6479589843713479612
    //   94: lload #4
    //   96: lxor
    //   97: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   102: iconst_1
    //   103: invokespecial <init> : (Ljava/lang/String;Z)V
    //   106: putfield r : Lwtf/opal/ke;
    //   109: aload_0
    //   110: new wtf/opal/ke
    //   113: dup
    //   114: sipush #9556
    //   117: ldc2_w 4104782729013057565
    //   120: lload #4
    //   122: lxor
    //   123: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   128: iconst_1
    //   129: invokespecial <init> : (Ljava/lang/String;Z)V
    //   132: putfield g : Lwtf/opal/ke;
    //   135: aload_0
    //   136: new wtf/opal/ke
    //   139: dup
    //   140: sipush #16037
    //   143: ldc2_w 5910531221502018538
    //   146: lload #4
    //   148: lxor
    //   149: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   154: iconst_1
    //   155: invokespecial <init> : (Ljava/lang/String;Z)V
    //   158: putfield I : Lwtf/opal/ke;
    //   161: aload_0
    //   162: new wtf/opal/ke
    //   165: dup
    //   166: sipush #16893
    //   169: ldc2_w 2596659412256341169
    //   172: lload #4
    //   174: lxor
    //   175: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   180: iconst_0
    //   181: invokespecial <init> : (Ljava/lang/String;Z)V
    //   184: putfield x : Lwtf/opal/ke;
    //   187: aload_0
    //   188: aload_0
    //   189: <illegal opcode> H : (Lwtf/opal/ju;)Lwtf/opal/gm;
    //   194: putfield y : Lwtf/opal/gm;
    //   197: aload_0
    //   198: iconst_4
    //   199: anewarray wtf/opal/k3
    //   202: dup
    //   203: iconst_0
    //   204: aload_0
    //   205: getfield r : Lwtf/opal/ke;
    //   208: aastore
    //   209: dup
    //   210: iconst_1
    //   211: aload_0
    //   212: getfield g : Lwtf/opal/ke;
    //   215: aastore
    //   216: dup
    //   217: iconst_2
    //   218: aload_0
    //   219: getfield I : Lwtf/opal/ke;
    //   222: aastore
    //   223: dup
    //   224: iconst_3
    //   225: aload_0
    //   226: getfield x : Lwtf/opal/ke;
    //   229: aastore
    //   230: lload #6
    //   232: dup2_x1
    //   233: pop2
    //   234: iconst_2
    //   235: anewarray java/lang/Object
    //   238: dup_x1
    //   239: swap
    //   240: iconst_1
    //   241: swap
    //   242: aastore
    //   243: dup_x2
    //   244: dup_x2
    //   245: pop
    //   246: invokestatic valueOf : (J)Ljava/lang/Long;
    //   249: iconst_0
    //   250: swap
    //   251: aastore
    //   252: invokevirtual o : ([Ljava/lang/Object;)V
    //   255: return
  }
  
  public String S(Object[] paramArrayOfObject) {
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
    //   20: getstatic wtf/opal/ju.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: invokestatic S : ()Ljava/lang/String;
    //   29: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   32: invokevirtual method_1548 : ()Lnet/minecraft/class_320;
    //   35: invokevirtual method_1676 : ()Ljava/lang/String;
    //   38: astore #6
    //   40: astore #5
    //   42: aload_0
    //   43: getfield I : Lwtf/opal/ke;
    //   46: invokevirtual z : ()Ljava/lang/Object;
    //   49: checkcast java/lang/Boolean
    //   52: invokevirtual booleanValue : ()Z
    //   55: aload #5
    //   57: ifnonnull -> 165
    //   60: ifeq -> 152
    //   63: goto -> 70
    //   66: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: aload #6
    //   72: aload #5
    //   74: ifnonnull -> 159
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: ifnull -> 152
    //   87: goto -> 94
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: aload #6
    //   96: invokevirtual trim : ()Ljava/lang/String;
    //   99: invokevirtual isEmpty : ()Z
    //   102: lload_2
    //   103: lconst_0
    //   104: lcmp
    //   105: ifle -> 165
    //   108: aload #5
    //   110: ifnonnull -> 165
    //   113: goto -> 120
    //   116: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: ifne -> 152
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: aload #4
    //   132: aload #6
    //   134: sipush #26077
    //   137: ldc2_w 1355182866967679901
    //   140: lload_2
    //   141: lxor
    //   142: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   147: invokestatic replaceIgnoreCase : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   150: astore #4
    //   152: aload_0
    //   153: getfield x : Lwtf/opal/ke;
    //   156: invokevirtual z : ()Ljava/lang/Object;
    //   159: checkcast java/lang/Boolean
    //   162: invokevirtual booleanValue : ()Z
    //   165: ifeq -> 326
    //   168: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   171: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   174: ifnull -> 326
    //   177: goto -> 184
    //   180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   183: athrow
    //   184: iconst_1
    //   185: istore #7
    //   187: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   190: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   193: invokevirtual method_2880 : ()Ljava/util/Collection;
    //   196: invokeinterface iterator : ()Ljava/util/Iterator;
    //   201: astore #8
    //   203: aload #8
    //   205: invokeinterface hasNext : ()Z
    //   210: ifeq -> 326
    //   213: aload #8
    //   215: invokeinterface next : ()Ljava/lang/Object;
    //   220: checkcast net/minecraft/class_640
    //   223: astore #9
    //   225: aload #9
    //   227: invokevirtual method_2966 : ()Lcom/mojang/authlib/GameProfile;
    //   230: invokevirtual getName : ()Ljava/lang/String;
    //   233: astore #10
    //   235: aload #10
    //   237: aload #5
    //   239: lload_2
    //   240: lconst_0
    //   241: lcmp
    //   242: iflt -> 250
    //   245: ifnonnull -> 328
    //   248: aload #6
    //   250: aload #5
    //   252: lload_2
    //   253: lconst_0
    //   254: lcmp
    //   255: ifle -> 313
    //   258: ifnonnull -> 292
    //   261: goto -> 268
    //   264: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   267: athrow
    //   268: invokevirtual equals : (Ljava/lang/Object;)Z
    //   271: ifne -> 321
    //   274: goto -> 281
    //   277: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   280: athrow
    //   281: aload #4
    //   283: aload #10
    //   285: goto -> 292
    //   288: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: iload #7
    //   294: sipush #30165
    //   297: ldc2_w 7453820072494547871
    //   300: lload_2
    //   301: lxor
    //   302: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   307: swap
    //   308: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;I)Ljava/lang/String;
    //   313: invokestatic replaceIgnoreCase : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   316: astore #4
    //   318: iinc #7, 1
    //   321: aload #5
    //   323: ifnull -> 203
    //   326: aload #4
    //   328: areturn
    // Exception table:
    //   from	to	target	type
    //   42	63	66	wtf/opal/x5
    //   60	77	80	wtf/opal/x5
    //   70	87	90	wtf/opal/x5
    //   84	113	116	wtf/opal/x5
    //   94	123	126	wtf/opal/x5
    //   165	177	180	wtf/opal/x5
    //   235	261	264	wtf/opal/x5
    //   248	274	277	wtf/opal/x5
    //   268	285	288	wtf/opal/x5
  }
  
  private void lambda$new$0(l7 paraml7) {
    // Byte code:
    //   0: getstatic wtf/opal/ju.a : J
    //   3: ldc2_w 74711133115735
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_0
    //   14: getfield g : Lwtf/opal/ke;
    //   17: invokevirtual z : ()Ljava/lang/Object;
    //   20: checkcast java/lang/Boolean
    //   23: invokevirtual booleanValue : ()Z
    //   26: ifne -> 34
    //   29: return
    //   30: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: aload_1
    //   35: iconst_0
    //   36: anewarray java/lang/Object
    //   39: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   42: invokeinterface getString : ()Ljava/lang/String;
    //   47: astore #5
    //   49: aload #5
    //   51: sipush #1857
    //   54: ldc2_w 356111913950706559
    //   57: lload_2
    //   58: lxor
    //   59: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   64: aload #4
    //   66: ifnonnull -> 119
    //   69: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   72: ifeq -> 175
    //   75: goto -> 82
    //   78: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: aload_1
    //   83: iconst_0
    //   84: anewarray java/lang/Object
    //   87: invokevirtual Z : ([Ljava/lang/Object;)V
    //   90: aload #5
    //   92: sipush #3650
    //   95: ldc2_w 8871022619015410294
    //   98: lload_2
    //   99: lxor
    //   100: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   105: ldc ''
    //   107: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   110: ldc '!'
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: ldc ''
    //   121: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   124: astore #6
    //   126: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   129: getfield field_1705 : Lnet/minecraft/class_329;
    //   132: invokevirtual method_1743 : ()Lnet/minecraft/class_338;
    //   135: aload #6
    //   137: sipush #16798
    //   140: ldc2_w 5802276113266163110
    //   143: lload_2
    //   144: lxor
    //   145: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   150: swap
    //   151: sipush #9706
    //   154: ldc2_w 3796764578932349404
    //   157: lload_2
    //   158: lxor
    //   159: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   164: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   169: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   172: invokevirtual method_1812 : (Lnet/minecraft/class_2561;)V
    //   175: return
    // Exception table:
    //   from	to	target	type
    //   13	30	30	wtf/opal/x5
    //   49	75	78	wtf/opal/x5
    //   69	112	115	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x1A13283067CAL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = "TLÔkädt<%Üå[ë1Ì\037ÏNfB6î|à=<ÁLô=x\020ÕÅ&ÜÜ±%6¸¹%/ð \017\030£{&P_¾`Ód\027q±\023Z÷%0Ù@ÛCó¤*Ü8M+ç#I'¾i\033äb­\017\\]\033!ÙÂ-Îzº»x+#üA\f\\gtaÝ\007å\0265eTýÔæ\034ÈÀh(xTå[ÿ¤$ÊßÐÑ¥\031ÏO\003\013ÿ4w?3\017·\000ïz>)xg\023úÅ\005 \016\001(¾v±Foù²SÌÝu)):__ÆíöHSóÊ\031ß\f|Ô|\031NÄ4y¥( ÉØ\031ò·®E÷já\033Ùl\016\032678K\\\nÛÁ8ê2ý¿ÆÎ\022(]«ÍÅc\027\020YÖ\004<À)F\004ªý\032[ín¾\023×wóÃöÃ­@hÝï^\005\030Ê£Í7öÝÒqñ>I\004\024Yxp\026R\f- ãïs.W.\033¡Èe¬.èöª\006\tM\0230íÅUNeØ$bõ").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5FCD;
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
        throw new RuntimeException("wtf/opal/ju", exception);
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
    //   65: ldc_w 'wtf/opal/ju'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ju.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */