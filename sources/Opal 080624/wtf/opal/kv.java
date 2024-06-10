package wtf.opal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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

public abstract class kv {
  @Deprecated
  public static final kv C;
  
  @Deprecated
  public static final kv H;
  
  @Deprecated
  public static final kv f;
  
  private static final long b = on.a(-8935251570390008025L, 1276931158053372759L, MethodHandles.lookup().lookupClass()).a(87113170665476L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  private static final long j;
  
  @Deprecated
  public static kv s(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x5E70A56D2FF2L;
    new Object[2];
    return u4.T(new Object[] { null, Long.valueOf(l2), Float.valueOf(f) });
  }
  
  @Deprecated
  public static kv r(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x4D40B88F40ECL;
    new Object[2];
    (new Object[2])[1] = Long.valueOf(l2);
    new Object[2];
    return u4.n(new Object[] { Double.valueOf(d) });
  }
  
  @Deprecated
  public static kv M(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x2B076DFC88B7L;
    (new Object[2])[1] = str;
    new Object[2];
    return u4.L(new Object[] { Long.valueOf(l2) });
  }
  
  @Deprecated
  public static kv T(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x1E9ED87A8EF7L;
    (new Object[2])[1] = Boolean.valueOf(bool);
    new Object[2];
    return u4.J(new Object[] { Long.valueOf(l2) });
  }
  
  public boolean c(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean P(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean O(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean l(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return false;
  }
  
  public boolean e(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean G(Object[] paramArrayOfObject) {
    return false;
  }
  
  public boolean w(Object[] paramArrayOfObject) {
    return false;
  }
  
  public kq w() {
    // Byte code:
    //   0: getstatic wtf/opal/kv.b : J
    //   3: ldc2_w 51047355097819
    //   6: lxor
    //   7: lstore_1
    //   8: new java/lang/UnsupportedOperationException
    //   11: dup
    //   12: aload_0
    //   13: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   16: sipush #25040
    //   19: ldc2_w 3861558413084425317
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   29: swap
    //   30: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   35: invokespecial <init> : (Ljava/lang/String;)V
    //   38: athrow
  }
  
  public k1 D(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #10861
    //   23: ldc2_w 7770187703212263073
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public int v(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #7926
    //   23: ldc2_w 8286244637090817127
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public long J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #7926
    //   23: ldc2_w 8286283368603188240
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public float v(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #30930
    //   23: ldc2_w 112213901660867804
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public double K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #7926
    //   23: ldc2_w 8286322544087587853
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public String F() {
    // Byte code:
    //   0: getstatic wtf/opal/kv.b : J
    //   3: ldc2_w 87229547238808
    //   6: lxor
    //   7: lstore_1
    //   8: new java/lang/UnsupportedOperationException
    //   11: dup
    //   12: aload_0
    //   13: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   16: sipush #18987
    //   19: ldc2_w 1850169095581868254
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   29: swap
    //   30: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   35: invokespecial <init> : (Ljava/lang/String;)V
    //   38: athrow
  }
  
  public boolean C(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: new java/lang/UnsupportedOperationException
    //   15: dup
    //   16: aload_0
    //   17: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   20: sipush #10953
    //   23: ldc2_w 2338016274715389889
    //   26: lload_2
    //   27: lxor
    //   28: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   33: swap
    //   34: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
  }
  
  public void R(Object[] paramArrayOfObject) throws IOException {
    Writer writer = (Writer)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x2B1230684029L;
    new Object[3];
    Z(new Object[] { null, null, Long.valueOf(l2), x_.N, writer });
  }
  
  public void Z(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/io/Writer
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast wtf/opal/x_
    //   14: astore #5
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast java/lang/Long
    //   22: invokevirtual longValue : ()J
    //   25: lstore_3
    //   26: pop
    //   27: getstatic wtf/opal/kv.b : J
    //   30: lload_3
    //   31: lxor
    //   32: lstore_3
    //   33: lload_3
    //   34: dup2
    //   35: ldc2_w 46799770682206
    //   38: lxor
    //   39: lstore #6
    //   41: pop2
    //   42: aload_2
    //   43: ifnonnull -> 71
    //   46: new java/lang/NullPointerException
    //   49: dup
    //   50: sipush #13873
    //   53: ldc2_w 8206169587841142793
    //   56: lload_3
    //   57: lxor
    //   58: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   63: invokespecial <init> : (Ljava/lang/String;)V
    //   66: athrow
    //   67: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   70: athrow
    //   71: aload #5
    //   73: ifnonnull -> 101
    //   76: new java/lang/NullPointerException
    //   79: dup
    //   80: sipush #5635
    //   83: ldc2_w 2606469106220478520
    //   86: lload_3
    //   87: lxor
    //   88: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   93: invokespecial <init> : (Ljava/lang/String;)V
    //   96: athrow
    //   97: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   100: athrow
    //   101: new wtf/opal/x
    //   104: dup
    //   105: aload_2
    //   106: getstatic wtf/opal/kv.j : J
    //   109: l2i
    //   110: invokespecial <init> : (Ljava/io/Writer;I)V
    //   113: astore #8
    //   115: aload_0
    //   116: aload #5
    //   118: aload #8
    //   120: iconst_1
    //   121: anewarray java/lang/Object
    //   124: dup_x1
    //   125: swap
    //   126: iconst_0
    //   127: swap
    //   128: aastore
    //   129: invokevirtual O : ([Ljava/lang/Object;)Lwtf/opal/l6;
    //   132: lload #6
    //   134: dup2_x1
    //   135: pop2
    //   136: iconst_2
    //   137: anewarray java/lang/Object
    //   140: dup_x1
    //   141: swap
    //   142: iconst_1
    //   143: swap
    //   144: aastore
    //   145: dup_x2
    //   146: dup_x2
    //   147: pop
    //   148: invokestatic valueOf : (J)Ljava/lang/Long;
    //   151: iconst_0
    //   152: swap
    //   153: aastore
    //   154: invokevirtual x : ([Ljava/lang/Object;)V
    //   157: aload #8
    //   159: invokevirtual flush : ()V
    //   162: return
    // Exception table:
    //   from	to	target	type
    //   42	67	67	java/io/IOException
    //   71	97	97	java/io/IOException
  }
  
  public String toString() {
    long l1 = b ^ 0x3C6F53032C98L;
    long l2 = l1 ^ 0x32C36883A851L;
    new Object[2];
    return s(new Object[] { null, Long.valueOf(l2), x_.N });
  }
  
  public String s(Object[] paramArrayOfObject) {
    x_ x_ = (x_)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x2A5E718B1D9EL;
    StringWriter stringWriter = new StringWriter();
    try {
      new Object[3];
      Z(new Object[] { null, null, Long.valueOf(l2), x_, stringWriter });
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
    return stringWriter.toString();
  }
  
  public boolean equals(Object paramObject) {
    return super.equals(paramObject);
  }
  
  public int hashCode() {
    return super.hashCode();
  }
  
  abstract void x(Object[] paramArrayOfObject) throws IOException;
  
  static {
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[11];
    boolean bool = false;
    String str;
    int i = (str = "þN V¼ÀGMÏü¥\030ÿÅ ó{3-$+¤\037.?Øn}Ì°1ßC&\030jýÑ\007Ñcâ \032´7\020?Í¾ð<ÄyÒÉh®\032\030EÇûXý÷\037i´à6 h¾«ø\002<+\030g \016<´ã§aD&·CbÉ\023TÌòÉ\030,pI,\026jÛ¸fw\020{k6OÍå\020\003Å=\030\0071&à>¤Í¹ìmôu2\023y>_9sí 1àªH:\fòÙOO²?ø^½ñ¿!vÊ=´âsc.^\037\000=\030És_³çdÈp¹éØ¢vÙùÝÛ*pëe\000\0203\032£\0302¯õÆ×Ã#?Î`È").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static IOException a(IOException paramIOException) {
    return paramIOException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2CFD;
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
        throw new RuntimeException("wtf/opal/kv", exception);
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
    //   65: ldc_w 'wtf/opal/kv'
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
  
  static {
    long l = b ^ 0x6839AE209045L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */