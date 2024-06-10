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

public final class xr extends x6 {
  private static final long b = on.a(-1746054920654002717L, -4322363256797394164L, MethodHandles.lookup().lookupClass()).a(93233214258485L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xr(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xr.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 66794789056653
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: lload_3
    //   16: sipush #10413
    //   19: ldc2_w 4295306520353911795
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   29: sipush #32322
    //   32: ldc2_w 6873127625793594641
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   42: iconst_1
    //   43: anewarray java/lang/String
    //   46: dup
    //   47: iconst_0
    //   48: ldc 'f'
    //   50: aastore
    //   51: invokespecial <init> : (JLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   54: return
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
    //   20: invokestatic H : ()[I
    //   23: aload #4
    //   25: sipush #31923
    //   28: ldc2_w 8717630708620157980
    //   31: lload_2
    //   32: lxor
    //   33: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   38: iconst_1
    //   39: anewarray java/lang/Object
    //   42: dup_x1
    //   43: swap
    //   44: iconst_0
    //   45: swap
    //   46: aastore
    //   47: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   50: sipush #17271
    //   53: ldc2_w 5211005165555833820
    //   56: lload_2
    //   57: lxor
    //   58: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokestatic J : ([Ljava/lang/Object;)Lwtf/opal/dd;
    //   70: iconst_2
    //   71: anewarray java/lang/Object
    //   74: dup_x1
    //   75: swap
    //   76: iconst_1
    //   77: swap
    //   78: aastore
    //   79: dup_x1
    //   80: swap
    //   81: iconst_0
    //   82: swap
    //   83: aastore
    //   84: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   87: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   92: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   95: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   98: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   101: pop
    //   102: aload #4
    //   104: sipush #2493
    //   107: ldc2_w 3050778891486269721
    //   110: lload_2
    //   111: lxor
    //   112: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   117: iconst_1
    //   118: anewarray java/lang/Object
    //   121: dup_x1
    //   122: swap
    //   123: iconst_0
    //   124: swap
    //   125: aastore
    //   126: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   129: sipush #17271
    //   132: ldc2_w 5211005165555833820
    //   135: lload_2
    //   136: lxor
    //   137: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   142: iconst_0
    //   143: anewarray java/lang/Object
    //   146: invokestatic J : ([Ljava/lang/Object;)Lwtf/opal/dd;
    //   149: iconst_2
    //   150: anewarray java/lang/Object
    //   153: dup_x1
    //   154: swap
    //   155: iconst_1
    //   156: swap
    //   157: aastore
    //   158: dup_x1
    //   159: swap
    //   160: iconst_0
    //   161: swap
    //   162: aastore
    //   163: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   166: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   171: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   174: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   177: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   180: pop
    //   181: astore #5
    //   183: lload_2
    //   184: lconst_0
    //   185: lcmp
    //   186: iflt -> 201
    //   189: invokestatic D : ()[Lwtf/opal/d;
    //   192: ifnull -> 208
    //   195: iconst_3
    //   196: newarray int
    //   198: invokestatic A : ([I)V
    //   201: goto -> 208
    //   204: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   207: athrow
    //   208: return
    // Exception table:
    //   from	to	target	type
    //   183	201	204	wtf/opal/x5
  }
  
  private static int lambda$onCommand$1(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xr.b : J
    //   3: ldc2_w 284855848246
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 129526298017599
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic H : ()[I
    //   19: aload_0
    //   20: sipush #17271
    //   23: ldc2_w 5210954658924317544
    //   26: lload_1
    //   27: lxor
    //   28: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   33: ldc java/lang/String
    //   35: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   38: checkcast java/lang/String
    //   41: astore #6
    //   43: iconst_0
    //   44: anewarray java/lang/Object
    //   47: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   50: iconst_0
    //   51: anewarray java/lang/Object
    //   54: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/l2;
    //   57: astore #7
    //   59: astore #5
    //   61: aload #7
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual m : ([Ljava/lang/Object;)Ljava/util/List;
    //   70: aload #6
    //   72: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   75: invokeinterface contains : (Ljava/lang/Object;)Z
    //   80: aload #5
    //   82: ifnonnull -> 210
    //   85: ifeq -> 165
    //   88: goto -> 95
    //   91: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   94: athrow
    //   95: aload #7
    //   97: iconst_0
    //   98: anewarray java/lang/Object
    //   101: invokevirtual m : ([Ljava/lang/Object;)Ljava/util/List;
    //   104: aload #6
    //   106: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   109: invokeinterface remove : (Ljava/lang/Object;)Z
    //   114: pop
    //   115: aload #6
    //   117: sipush #21852
    //   120: ldc2_w 7076608008307629381
    //   123: lload_1
    //   124: lxor
    //   125: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   130: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   135: lload_3
    //   136: dup2_x1
    //   137: pop2
    //   138: iconst_2
    //   139: anewarray java/lang/Object
    //   142: dup_x1
    //   143: swap
    //   144: iconst_1
    //   145: swap
    //   146: aastore
    //   147: dup_x2
    //   148: dup_x2
    //   149: pop
    //   150: invokestatic valueOf : (J)Ljava/lang/Long;
    //   153: iconst_0
    //   154: swap
    //   155: aastore
    //   156: invokestatic g : ([Ljava/lang/Object;)V
    //   159: iconst_1
    //   160: ireturn
    //   161: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   164: athrow
    //   165: aload #6
    //   167: sipush #29964
    //   170: ldc2_w 3433633779008437521
    //   173: lload_1
    //   174: lxor
    //   175: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   180: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   185: lload_3
    //   186: dup2_x1
    //   187: pop2
    //   188: iconst_2
    //   189: anewarray java/lang/Object
    //   192: dup_x1
    //   193: swap
    //   194: iconst_1
    //   195: swap
    //   196: aastore
    //   197: dup_x2
    //   198: dup_x2
    //   199: pop
    //   200: invokestatic valueOf : (J)Ljava/lang/Long;
    //   203: iconst_0
    //   204: swap
    //   205: aastore
    //   206: invokestatic g : ([Ljava/lang/Object;)V
    //   209: iconst_1
    //   210: ireturn
    // Exception table:
    //   from	to	target	type
    //   61	88	91	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   85	161	161	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  private static int lambda$onCommand$0(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xr.b : J
    //   3: ldc2_w 139894922410286
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 11769039276839
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic H : ()[I
    //   19: aload_0
    //   20: sipush #25124
    //   23: ldc2_w 8380905994873134630
    //   26: lload_1
    //   27: lxor
    //   28: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   33: ldc java/lang/String
    //   35: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   38: checkcast java/lang/String
    //   41: astore #6
    //   43: astore #5
    //   45: iconst_0
    //   46: anewarray java/lang/Object
    //   49: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   52: iconst_0
    //   53: anewarray java/lang/Object
    //   56: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/l2;
    //   59: astore #7
    //   61: aload #7
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual m : ([Ljava/lang/Object;)Ljava/util/List;
    //   70: aload #6
    //   72: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   75: invokeinterface contains : (Ljava/lang/Object;)Z
    //   80: aload #5
    //   82: ifnonnull -> 210
    //   85: ifeq -> 145
    //   88: goto -> 95
    //   91: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   94: athrow
    //   95: aload #6
    //   97: sipush #28395
    //   100: ldc2_w 4768001117310440173
    //   103: lload_1
    //   104: lxor
    //   105: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   110: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   115: lload_3
    //   116: dup2_x1
    //   117: pop2
    //   118: iconst_2
    //   119: anewarray java/lang/Object
    //   122: dup_x1
    //   123: swap
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokestatic g : ([Ljava/lang/Object;)V
    //   139: iconst_1
    //   140: ireturn
    //   141: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   144: athrow
    //   145: aload #7
    //   147: iconst_0
    //   148: anewarray java/lang/Object
    //   151: invokevirtual m : ([Ljava/lang/Object;)Ljava/util/List;
    //   154: aload #6
    //   156: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   159: invokeinterface add : (Ljava/lang/Object;)Z
    //   164: pop
    //   165: aload #6
    //   167: sipush #6374
    //   170: ldc2_w 8782106462961640678
    //   173: lload_1
    //   174: lxor
    //   175: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   180: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   185: lload_3
    //   186: dup2_x1
    //   187: pop2
    //   188: iconst_2
    //   189: anewarray java/lang/Object
    //   192: dup_x1
    //   193: swap
    //   194: iconst_1
    //   195: swap
    //   196: aastore
    //   197: dup_x2
    //   198: dup_x2
    //   199: pop
    //   200: invokestatic valueOf : (J)Ljava/lang/Long;
    //   203: iconst_0
    //   204: swap
    //   205: aastore
    //   206: invokestatic g : ([Ljava/lang/Object;)V
    //   209: iconst_1
    //   210: ireturn
    // Exception table:
    //   from	to	target	type
    //   61	88	91	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   85	141	141	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  static {
    long l = b ^ 0x3BE42ED8DA8BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "½\027±\"´Ó÷\001\024h%O\023í\020ÝuUh\032\033ÁÊ¤ø\\KæÙÔ¾U%ÆmdòTYÏ:¼Ö®\bÐ\bÎs9Ôû8\027Ê­¡<\fÞ÷\036TÀ£ûDNlð@-WÐÐ¡ä6±\021\016hXo%árG\001eª¨ÒÁõ3\036êP\006 ¼;-à»ÑÔR\n\026Øþw\013~ÌWÖ?¨w®\020ä<äF>T1±\031S9-çiâ\020\027$\031qtÏ2²\002¶s±\"0³,©î¹Úú!¦aÂÃ\022/ú\006òPCgººìsÈÑý\025\032©¡UÄLýA¾(}U\005rO\\»\020B\\ÖJÝÖÒÇÌ$WÀ/'x@\020\0344OO÷Ü%²Ä÷qJ¼r@õÁí¦|\022ÈÒ¡\007aÇ\006\037èfÆSîqUK·ä2lÎ¬Þù2BT\017æ\004F\007=|ì0AÃUxîÄl\037[9Ypd^)\001").length();
    byte b2 = 80;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x151E;
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
        throw new RuntimeException("wtf/opal/xr", exception);
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
    //   66: ldc_w 'wtf/opal/xr'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */