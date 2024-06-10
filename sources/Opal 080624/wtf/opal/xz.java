package wtf.opal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

public final class xz extends x6 {
  private final Map<String, Integer> U;
  
  private static final long b = on.a(-1633977049273425430L, 5946505717358696782L, MethodHandles.lookup().lookupClass()).a(111589419090756L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  private static final long f;
  
  public xz(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xz.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 126994350468786
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: invokestatic V : ()[Lwtf/opal/d;
    //   17: aload_0
    //   18: lload_3
    //   19: sipush #23843
    //   22: ldc2_w 8490147561419166136
    //   25: lload_1
    //   26: lxor
    //   27: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   32: sipush #9881
    //   35: ldc2_w 2844277752521173511
    //   38: lload_1
    //   39: lxor
    //   40: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   45: iconst_1
    //   46: anewarray java/lang/String
    //   49: dup
    //   50: iconst_0
    //   51: ldc 'b'
    //   53: aastore
    //   54: invokespecial <init> : (JLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   57: astore #5
    //   59: aload_0
    //   60: new java/util/HashMap
    //   63: dup
    //   64: invokespecial <init> : ()V
    //   67: putfield U : Ljava/util/Map;
    //   70: ldc org/lwjgl/glfw/GLFW
    //   72: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   75: astore #6
    //   77: aload #6
    //   79: arraylength
    //   80: istore #7
    //   82: iconst_0
    //   83: istore #8
    //   85: iload #8
    //   87: iload #7
    //   89: if_icmpge -> 199
    //   92: aload #6
    //   94: iload #8
    //   96: aaload
    //   97: astore #9
    //   99: aload #5
    //   101: lload_1
    //   102: lconst_0
    //   103: lcmp
    //   104: ifle -> 196
    //   107: ifnull -> 194
    //   110: aload #9
    //   112: invokevirtual getName : ()Ljava/lang/String;
    //   115: aload #5
    //   117: ifnull -> 231
    //   120: goto -> 127
    //   123: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   126: athrow
    //   127: sipush #8828
    //   130: ldc2_w 8222964239102750438
    //   133: lload_1
    //   134: lxor
    //   135: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   140: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   143: ifeq -> 191
    //   146: goto -> 153
    //   149: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   152: athrow
    //   153: aload_0
    //   154: getfield U : Ljava/util/Map;
    //   157: aload #9
    //   159: invokevirtual getName : ()Ljava/lang/String;
    //   162: getstatic wtf/opal/xz.f : J
    //   165: l2i
    //   166: invokevirtual substring : (I)Ljava/lang/String;
    //   169: aload #9
    //   171: aconst_null
    //   172: invokevirtual getInt : (Ljava/lang/Object;)I
    //   175: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   178: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   183: pop
    //   184: goto -> 191
    //   187: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   190: athrow
    //   191: iinc #8, 1
    //   194: aload #5
    //   196: ifnonnull -> 85
    //   199: aload_0
    //   200: getfield U : Ljava/util/Map;
    //   203: lload_1
    //   204: lconst_0
    //   205: lcmp
    //   206: ifle -> 231
    //   209: sipush #21343
    //   212: ldc2_w 6164233231179276235
    //   215: lload_1
    //   216: lxor
    //   217: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   222: iconst_m1
    //   223: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   226: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   231: pop
    //   232: goto -> 242
    //   235: astore #6
    //   237: aload #6
    //   239: invokevirtual printStackTrace : ()V
    //   242: return
    // Exception table:
    //   from	to	target	type
    //   70	232	235	java/lang/IllegalAccessException
    //   99	120	123	java/lang/IllegalAccessException
    //   110	146	149	java/lang/IllegalAccessException
    //   127	184	187	java/lang/IllegalAccessException
  }
  
  protected void Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/builder/LiteralArgumentBuilder
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: invokestatic V : ()[Lwtf/opal/d;
    //   23: aload #4
    //   25: sipush #23637
    //   28: ldc2_w 760899031040101635
    //   31: lload_2
    //   32: lxor
    //   33: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   38: iconst_0
    //   39: anewarray java/lang/Object
    //   42: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/d_;
    //   45: iconst_2
    //   46: anewarray java/lang/Object
    //   49: dup_x1
    //   50: swap
    //   51: iconst_1
    //   52: swap
    //   53: aastore
    //   54: dup_x1
    //   55: swap
    //   56: iconst_0
    //   57: swap
    //   58: aastore
    //   59: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   62: sipush #26315
    //   65: ldc2_w 1850386588269655962
    //   68: lload_2
    //   69: lxor
    //   70: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokestatic D : ([Ljava/lang/Object;)Lwtf/opal/ku;
    //   82: iconst_2
    //   83: anewarray java/lang/Object
    //   86: dup_x1
    //   87: swap
    //   88: iconst_1
    //   89: swap
    //   90: aastore
    //   91: dup_x1
    //   92: swap
    //   93: iconst_0
    //   94: swap
    //   95: aastore
    //   96: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   99: aload_0
    //   100: <illegal opcode> run : (Lwtf/opal/xz;)Lcom/mojang/brigadier/Command;
    //   105: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   108: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   111: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   114: pop
    //   115: astore #5
    //   117: aload #5
    //   119: lload_2
    //   120: lconst_0
    //   121: lcmp
    //   122: iflt -> 132
    //   125: ifnonnull -> 142
    //   128: iconst_4
    //   129: anewarray wtf/opal/d
    //   132: invokestatic p : ([Lwtf/opal/d;)V
    //   135: goto -> 142
    //   138: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   141: athrow
    //   142: return
    // Exception table:
    //   from	to	target	type
    //   117	135	138	wtf/opal/x5
  }
  
  private int lambda$onCommand$0(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xz.b : J
    //   3: ldc2_w 29518861192290
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 68590053595496
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 121677849909008
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic V : ()[Lwtf/opal/d;
    //   27: aload_1
    //   28: sipush #23136
    //   31: ldc2_w 816412108527336927
    //   34: lload_2
    //   35: lxor
    //   36: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   41: ldc wtf/opal/d
    //   43: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   46: checkcast wtf/opal/d
    //   49: astore #9
    //   51: astore #8
    //   53: aload_1
    //   54: sipush #27235
    //   57: ldc2_w 3554887464526630367
    //   60: lload_2
    //   61: lxor
    //   62: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   67: ldc java/lang/String
    //   69: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   72: checkcast java/lang/String
    //   75: astore #10
    //   77: aload #10
    //   79: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   82: astore #11
    //   84: aload_0
    //   85: getfield U : Ljava/util/Map;
    //   88: aload #11
    //   90: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   95: checkcast java/lang/Integer
    //   98: astore #12
    //   100: aload #8
    //   102: ifnull -> 275
    //   105: aload #12
    //   107: ifnonnull -> 169
    //   110: goto -> 117
    //   113: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   116: athrow
    //   117: aload #11
    //   119: sipush #16464
    //   122: ldc2_w 1420132561512392678
    //   125: lload_2
    //   126: lxor
    //   127: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   132: swap
    //   133: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   138: lload #6
    //   140: dup2_x1
    //   141: pop2
    //   142: iconst_2
    //   143: anewarray java/lang/Object
    //   146: dup_x1
    //   147: swap
    //   148: iconst_1
    //   149: swap
    //   150: aastore
    //   151: dup_x2
    //   152: dup_x2
    //   153: pop
    //   154: invokestatic valueOf : (J)Ljava/lang/Long;
    //   157: iconst_0
    //   158: swap
    //   159: aastore
    //   160: invokestatic m : ([Ljava/lang/Object;)V
    //   163: iconst_1
    //   164: ireturn
    //   165: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   168: athrow
    //   169: aload #9
    //   171: iconst_0
    //   172: anewarray java/lang/Object
    //   175: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/k8;
    //   178: aload #12
    //   180: iconst_1
    //   181: anewarray java/lang/Object
    //   184: dup_x1
    //   185: swap
    //   186: iconst_0
    //   187: swap
    //   188: aastore
    //   189: invokevirtual V : ([Ljava/lang/Object;)V
    //   192: aload #9
    //   194: iconst_0
    //   195: anewarray java/lang/Object
    //   198: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   201: aload #11
    //   203: sipush #22236
    //   206: ldc2_w 4627579562331795819
    //   209: lload_2
    //   210: lxor
    //   211: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   216: dup_x2
    //   217: pop
    //   218: sipush #20477
    //   221: ldc2_w 3617537042190493760
    //   224: lload_2
    //   225: lxor
    //   226: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   231: swap
    //   232: sipush #29492
    //   235: ldc2_w 7835795626634040455
    //   238: lload_2
    //   239: lxor
    //   240: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   245: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   250: lload #4
    //   252: dup2_x1
    //   253: pop2
    //   254: iconst_2
    //   255: anewarray java/lang/Object
    //   258: dup_x1
    //   259: swap
    //   260: iconst_1
    //   261: swap
    //   262: aastore
    //   263: dup_x2
    //   264: dup_x2
    //   265: pop
    //   266: invokestatic valueOf : (J)Ljava/lang/Long;
    //   269: iconst_0
    //   270: swap
    //   271: aastore
    //   272: invokestatic g : ([Ljava/lang/Object;)V
    //   275: iconst_1
    //   276: ireturn
    // Exception table:
    //   from	to	target	type
    //   100	110	113	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   105	165	165	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  static {
    long l = b ^ 0x6E2A1366BFE7L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = "îG³yNàI\f¾îVr9\035§UXh Ú¤d,J/\b\026/.(:\\\025/}îo\016­\030kïödòÕý´ÂnGÝ(Y|,d?ª$¯:\016¥}¥)b¡Æ XÖ~Q\007äùUßôMh¾?o°¿ oò\020<c'¾Ñ%3áxL\027\005áé(ìq<VÅ#r\033±ÊþàiÎ\017$[{&è\031B\r#sõZïðÃ9\020y¬\021·^K§\025½Ä\001  \001×\016ÔqÜvõúE]¾$2\021\nY\021a\nçþ\"Þ¥ÇB\020ø¹>ÜðéxÀÜ±Vð\"\035µ&\020\bYøÉ\fÕYÖôi /x(À&»ßàÔ·ùä\021°¡~ÃjS,Kè\034ý\027^ÁT\024'0/¬ÁI©V\020îÌýÒT¬=]óô°N{").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5CE5;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xz", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
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
    //   65: ldc_w 'wtf/opal/xz'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */