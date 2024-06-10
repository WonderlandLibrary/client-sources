package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class bb {
  private final String n;
  
  private final kv l;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  bb(String paramString, kv paramkv) {
    this.n = paramString;
    this.l = paramkv;
  }
  
  public String J(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public kv W(Object[] paramArrayOfObject) {
    return this.l;
  }
  
  public int hashCode() {
    // Byte code:
    //   0: getstatic wtf/opal/bb.a : J
    //   3: ldc2_w 29633278979102
    //   6: lxor
    //   7: lstore_1
    //   8: iconst_1
    //   9: istore_3
    //   10: sipush #15798
    //   13: ldc2_w 488981803540482701
    //   16: lload_1
    //   17: lxor
    //   18: <illegal opcode> b : (IJ)I
    //   23: iload_3
    //   24: imul
    //   25: aload_0
    //   26: getfield n : Ljava/lang/String;
    //   29: invokevirtual hashCode : ()I
    //   32: iadd
    //   33: istore_3
    //   34: sipush #31150
    //   37: ldc2_w 7001592544326274708
    //   40: lload_1
    //   41: lxor
    //   42: <illegal opcode> b : (IJ)I
    //   47: iload_3
    //   48: imul
    //   49: aload_0
    //   50: getfield l : Lwtf/opal/kv;
    //   53: invokevirtual hashCode : ()I
    //   56: iadd
    //   57: istore_3
    //   58: iload_3
    //   59: ireturn
  }
  
  public boolean equals(Object paramObject) {
    // Byte code:
    //   0: getstatic wtf/opal/bb.a : J
    //   3: ldc2_w 95611610807550
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic T : ()I
    //   11: istore #4
    //   13: aload_0
    //   14: iload #4
    //   16: ifne -> 37
    //   19: aload_1
    //   20: if_acmpne -> 36
    //   23: goto -> 30
    //   26: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   29: athrow
    //   30: iconst_1
    //   31: ireturn
    //   32: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   35: athrow
    //   36: aload_1
    //   37: ifnonnull -> 46
    //   40: iconst_0
    //   41: ireturn
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: invokevirtual getClass : ()Ljava/lang/Class;
    //   50: iload #4
    //   52: ifne -> 76
    //   55: aload_1
    //   56: invokevirtual getClass : ()Ljava/lang/Class;
    //   59: if_acmpeq -> 75
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: iconst_0
    //   70: ireturn
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: aload_1
    //   76: checkcast wtf/opal/bb
    //   79: astore #5
    //   81: aload_0
    //   82: getfield n : Ljava/lang/String;
    //   85: aload #5
    //   87: getfield n : Ljava/lang/String;
    //   90: invokevirtual equals : (Ljava/lang/Object;)Z
    //   93: iload #4
    //   95: ifne -> 127
    //   98: ifeq -> 146
    //   101: goto -> 108
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: aload_0
    //   109: getfield l : Lwtf/opal/kv;
    //   112: aload #5
    //   114: getfield l : Lwtf/opal/kv;
    //   117: invokevirtual equals : (Ljava/lang/Object;)Z
    //   120: goto -> 127
    //   123: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   126: athrow
    //   127: iload #4
    //   129: ifne -> 143
    //   132: ifeq -> 146
    //   135: goto -> 142
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: iconst_1
    //   143: goto -> 147
    //   146: iconst_0
    //   147: ireturn
    // Exception table:
    //   from	to	target	type
    //   13	23	26	wtf/opal/x5
    //   19	32	32	wtf/opal/x5
    //   37	42	42	wtf/opal/x5
    //   46	62	65	wtf/opal/x5
    //   55	71	71	wtf/opal/x5
    //   81	101	104	wtf/opal/x5
    //   98	120	123	wtf/opal/x5
    //   127	135	138	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -667213107700693311
    //   3: ldc2_w 4681095385572808693
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 220199322703163
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/bb.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/bb.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/bb.a : J
    //   41: ldc2_w 23985685277034
    //   44: lxor
    //   45: lstore_0
    //   46: ldc 'DES/CBC/NoPadding'
    //   48: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   51: dup
    //   52: astore_2
    //   53: iconst_2
    //   54: ldc 'DES'
    //   56: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   59: bipush #8
    //   61: newarray byte
    //   63: dup
    //   64: iconst_0
    //   65: lload_0
    //   66: bipush #56
    //   68: lushr
    //   69: l2i
    //   70: i2b
    //   71: bastore
    //   72: iconst_1
    //   73: istore_3
    //   74: iload_3
    //   75: bipush #8
    //   77: if_icmpge -> 100
    //   80: dup
    //   81: iload_3
    //   82: lload_0
    //   83: iload_3
    //   84: bipush #8
    //   86: imul
    //   87: lshl
    //   88: bipush #56
    //   90: lushr
    //   91: l2i
    //   92: i2b
    //   93: bastore
    //   94: iinc #3, 1
    //   97: goto -> 74
    //   100: new javax/crypto/spec/DESKeySpec
    //   103: dup_x1
    //   104: swap
    //   105: invokespecial <init> : ([B)V
    //   108: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   111: new javax/crypto/spec/IvParameterSpec
    //   114: dup
    //   115: bipush #8
    //   117: newarray byte
    //   119: invokespecial <init> : ([B)V
    //   122: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   125: iconst_2
    //   126: newarray long
    //   128: astore #8
    //   130: iconst_0
    //   131: istore #5
    //   133: ldc '¿OC;s°[%ûÆMU+ÿ{'
    //   135: dup
    //   136: astore #6
    //   138: invokevirtual length : ()I
    //   141: istore #7
    //   143: iconst_0
    //   144: istore #4
    //   146: aload #6
    //   148: iload #4
    //   150: iinc #4, 8
    //   153: iload #4
    //   155: invokevirtual substring : (II)Ljava/lang/String;
    //   158: ldc 'ISO-8859-1'
    //   160: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   163: astore #9
    //   165: aload #8
    //   167: iload #5
    //   169: iinc #5, 1
    //   172: aload #9
    //   174: iconst_0
    //   175: baload
    //   176: i2l
    //   177: ldc2_w 255
    //   180: land
    //   181: bipush #56
    //   183: lshl
    //   184: aload #9
    //   186: iconst_1
    //   187: baload
    //   188: i2l
    //   189: ldc2_w 255
    //   192: land
    //   193: bipush #48
    //   195: lshl
    //   196: lor
    //   197: aload #9
    //   199: iconst_2
    //   200: baload
    //   201: i2l
    //   202: ldc2_w 255
    //   205: land
    //   206: bipush #40
    //   208: lshl
    //   209: lor
    //   210: aload #9
    //   212: iconst_3
    //   213: baload
    //   214: i2l
    //   215: ldc2_w 255
    //   218: land
    //   219: bipush #32
    //   221: lshl
    //   222: lor
    //   223: aload #9
    //   225: iconst_4
    //   226: baload
    //   227: i2l
    //   228: ldc2_w 255
    //   231: land
    //   232: bipush #24
    //   234: lshl
    //   235: lor
    //   236: aload #9
    //   238: iconst_5
    //   239: baload
    //   240: i2l
    //   241: ldc2_w 255
    //   244: land
    //   245: bipush #16
    //   247: lshl
    //   248: lor
    //   249: aload #9
    //   251: bipush #6
    //   253: baload
    //   254: i2l
    //   255: ldc2_w 255
    //   258: land
    //   259: bipush #8
    //   261: lshl
    //   262: lor
    //   263: aload #9
    //   265: bipush #7
    //   267: baload
    //   268: i2l
    //   269: ldc2_w 255
    //   272: land
    //   273: lor
    //   274: iconst_m1
    //   275: goto -> 301
    //   278: lastore
    //   279: iload #4
    //   281: iload #7
    //   283: if_icmplt -> 146
    //   286: aload #8
    //   288: putstatic wtf/opal/bb.b : [J
    //   291: iconst_2
    //   292: anewarray java/lang/Integer
    //   295: putstatic wtf/opal/bb.c : [Ljava/lang/Integer;
    //   298: goto -> 503
    //   301: dup_x2
    //   302: pop
    //   303: lstore #10
    //   305: bipush #8
    //   307: newarray byte
    //   309: dup
    //   310: iconst_0
    //   311: lload #10
    //   313: bipush #56
    //   315: lushr
    //   316: l2i
    //   317: i2b
    //   318: bastore
    //   319: dup
    //   320: iconst_1
    //   321: lload #10
    //   323: bipush #48
    //   325: lushr
    //   326: l2i
    //   327: i2b
    //   328: bastore
    //   329: dup
    //   330: iconst_2
    //   331: lload #10
    //   333: bipush #40
    //   335: lushr
    //   336: l2i
    //   337: i2b
    //   338: bastore
    //   339: dup
    //   340: iconst_3
    //   341: lload #10
    //   343: bipush #32
    //   345: lushr
    //   346: l2i
    //   347: i2b
    //   348: bastore
    //   349: dup
    //   350: iconst_4
    //   351: lload #10
    //   353: bipush #24
    //   355: lushr
    //   356: l2i
    //   357: i2b
    //   358: bastore
    //   359: dup
    //   360: iconst_5
    //   361: lload #10
    //   363: bipush #16
    //   365: lushr
    //   366: l2i
    //   367: i2b
    //   368: bastore
    //   369: dup
    //   370: bipush #6
    //   372: lload #10
    //   374: bipush #8
    //   376: lushr
    //   377: l2i
    //   378: i2b
    //   379: bastore
    //   380: dup
    //   381: bipush #7
    //   383: lload #10
    //   385: l2i
    //   386: i2b
    //   387: bastore
    //   388: aload_2
    //   389: swap
    //   390: invokevirtual doFinal : ([B)[B
    //   393: astore #12
    //   395: aload #12
    //   397: iconst_0
    //   398: baload
    //   399: i2l
    //   400: ldc2_w 255
    //   403: land
    //   404: bipush #56
    //   406: lshl
    //   407: aload #12
    //   409: iconst_1
    //   410: baload
    //   411: i2l
    //   412: ldc2_w 255
    //   415: land
    //   416: bipush #48
    //   418: lshl
    //   419: lor
    //   420: aload #12
    //   422: iconst_2
    //   423: baload
    //   424: i2l
    //   425: ldc2_w 255
    //   428: land
    //   429: bipush #40
    //   431: lshl
    //   432: lor
    //   433: aload #12
    //   435: iconst_3
    //   436: baload
    //   437: i2l
    //   438: ldc2_w 255
    //   441: land
    //   442: bipush #32
    //   444: lshl
    //   445: lor
    //   446: aload #12
    //   448: iconst_4
    //   449: baload
    //   450: i2l
    //   451: ldc2_w 255
    //   454: land
    //   455: bipush #24
    //   457: lshl
    //   458: lor
    //   459: aload #12
    //   461: iconst_5
    //   462: baload
    //   463: i2l
    //   464: ldc2_w 255
    //   467: land
    //   468: bipush #16
    //   470: lshl
    //   471: lor
    //   472: aload #12
    //   474: bipush #6
    //   476: baload
    //   477: i2l
    //   478: ldc2_w 255
    //   481: land
    //   482: bipush #8
    //   484: lshl
    //   485: lor
    //   486: aload #12
    //   488: bipush #7
    //   490: baload
    //   491: i2l
    //   492: ldc2_w 255
    //   495: land
    //   496: lor
    //   497: dup2_x1
    //   498: pop2
    //   499: pop
    //   500: goto -> 278
    //   503: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x258D;
    if (c[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = b[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])d.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bb", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      c[i] = Integer.valueOf(j);
    } 
    return c[i].intValue();
  }
  
  private static int a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc
    //   12: ldc [Ljava/lang/Object;
    //   14: aload_2
    //   15: invokevirtual parameterCount : ()I
    //   18: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   21: iconst_0
    //   22: iconst_3
    //   23: anewarray java/lang/Object
    //   26: dup
    //   27: iconst_0
    //   28: aload_0
    //   29: aastore
    //   30: dup
    //   31: iconst_1
    //   32: aload_3
    //   33: aastore
    //   34: dup
    //   35: iconst_2
    //   36: aload_1
    //   37: aastore
    //   38: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   41: aload_2
    //   42: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   45: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   48: goto -> 101
    //   51: astore #4
    //   53: new java/lang/RuntimeException
    //   56: dup
    //   57: new java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: ldc 'wtf/opal/bb'
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc_w ' : '
    //   72: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: aload_1
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: ldc_w ' : '
    //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: aload_2
    //   86: invokevirtual toString : ()Ljava/lang/String;
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: invokevirtual toString : ()Ljava/lang/String;
    //   95: aload #4
    //   97: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   100: athrow
    //   101: aload_3
    //   102: areturn
    // Exception table:
    //   from	to	target	type
    //   9	48	51	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */