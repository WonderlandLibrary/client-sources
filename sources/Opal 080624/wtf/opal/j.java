package wtf.opal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

class j {
  @Expose
  @SerializedName("items")
  private uv[] F;
  
  private static final long a = on.a(-717464020493935740L, 1191669386699036119L, MethodHandles.lookup().lookupClass()).a(181268123812495L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private boolean m(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic X : ()[I
    //   21: iconst_0
    //   22: istore #5
    //   24: astore #4
    //   26: iconst_0
    //   27: istore #6
    //   29: aload_0
    //   30: getfield F : [Lwtf/opal/uv;
    //   33: astore #7
    //   35: aload #7
    //   37: arraylength
    //   38: istore #8
    //   40: iconst_0
    //   41: istore #9
    //   43: iload #9
    //   45: iload #8
    //   47: if_icmpge -> 185
    //   50: aload #7
    //   52: iload #9
    //   54: aaload
    //   55: astore #10
    //   57: aload #10
    //   59: getfield k : Ljava/lang/String;
    //   62: sipush #9122
    //   65: ldc2_w 4519589029544613983
    //   68: lload_2
    //   69: lxor
    //   70: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   75: invokevirtual equals : (Ljava/lang/Object;)Z
    //   78: aload #4
    //   80: lload_2
    //   81: lconst_0
    //   82: lcmp
    //   83: ifle -> 195
    //   86: ifnonnull -> 193
    //   89: aload #4
    //   91: lload_2
    //   92: lconst_0
    //   93: lcmp
    //   94: ifle -> 161
    //   97: ifnonnull -> 159
    //   100: goto -> 107
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: ifeq -> 131
    //   110: goto -> 117
    //   113: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   116: athrow
    //   117: iconst_1
    //   118: istore #5
    //   120: aload #4
    //   122: lload_2
    //   123: lconst_0
    //   124: lcmp
    //   125: ifle -> 182
    //   128: ifnull -> 177
    //   131: aload #10
    //   133: getfield k : Ljava/lang/String;
    //   136: sipush #9908
    //   139: ldc2_w 2690114323792321864
    //   142: lload_2
    //   143: lxor
    //   144: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   149: invokevirtual equals : (Ljava/lang/Object;)Z
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: aload #4
    //   161: ifnonnull -> 175
    //   164: ifeq -> 177
    //   167: goto -> 174
    //   170: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   173: athrow
    //   174: iconst_1
    //   175: istore #6
    //   177: iinc #9, 1
    //   180: aload #4
    //   182: ifnull -> 43
    //   185: lload_2
    //   186: lconst_0
    //   187: lcmp
    //   188: ifle -> 235
    //   191: iload #5
    //   193: aload #4
    //   195: lload_2
    //   196: lconst_0
    //   197: lcmp
    //   198: ifle -> 218
    //   201: ifnonnull -> 216
    //   204: ifeq -> 235
    //   207: goto -> 214
    //   210: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   213: athrow
    //   214: iload #6
    //   216: aload #4
    //   218: ifnonnull -> 232
    //   221: ifeq -> 235
    //   224: goto -> 231
    //   227: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   230: athrow
    //   231: iconst_1
    //   232: goto -> 236
    //   235: iconst_0
    //   236: ireturn
    // Exception table:
    //   from	to	target	type
    //   57	100	103	wtf/opal/x5
    //   89	110	113	wtf/opal/x5
    //   120	152	155	wtf/opal/x5
    //   159	167	170	wtf/opal/x5
    //   193	207	210	wtf/opal/x5
    //   216	224	227	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x617DF95F2B1AL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "Ô§*\003Ð&{ï¯65M<.}£ü5¥\001\026\ba\032¤ªÓ¬cËp\"Nídé®, ±ä¢þ\tÅÏLm\031¾\016ÞÌP)åñôóÖÐ¡âë\021Ñ ").length();
    byte b2 = 40;
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
      int k;
      if ((k = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)k;
      } else if (k < 224) {
        char c = (char)((char)(k & 0x1F) << 6);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(k & 0xF) << 12);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F) << 6);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x96;
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
        throw new RuntimeException("wtf/opal/j", exception);
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
    //   64: ldc 'wtf/opal/j'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */