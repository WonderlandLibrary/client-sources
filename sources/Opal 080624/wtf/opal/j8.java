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

public final class j8 extends d {
  public static final ke X;
  
  public static final ke z;
  
  public static final kt T;
  
  private static final long a = on.a(-1902873471377946373L, 8477525225093166806L, MethodHandles.lookup().lookupClass()).a(134734777942259L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public j8(short paramShort, int paramInt, char paramChar) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #32
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/j8.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 102148476661744
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 126005110257317
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #26692
    //   50: ldc2_w 6649103634152609836
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #15377
    //   66: ldc2_w 5850797153291897982
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: iconst_0
    //   85: anewarray java/lang/Object
    //   88: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/k8;
    //   91: getstatic wtf/opal/j8.g : J
    //   94: l2i
    //   95: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   98: iconst_1
    //   99: anewarray java/lang/Object
    //   102: dup_x1
    //   103: swap
    //   104: iconst_0
    //   105: swap
    //   106: aastore
    //   107: invokevirtual V : ([Ljava/lang/Object;)V
    //   110: aload_0
    //   111: iconst_3
    //   112: anewarray wtf/opal/k3
    //   115: dup
    //   116: iconst_0
    //   117: getstatic wtf/opal/j8.X : Lwtf/opal/ke;
    //   120: aastore
    //   121: dup
    //   122: iconst_1
    //   123: getstatic wtf/opal/j8.z : Lwtf/opal/ke;
    //   126: aastore
    //   127: dup
    //   128: iconst_2
    //   129: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   132: aastore
    //   133: lload #6
    //   135: dup2_x1
    //   136: pop2
    //   137: iconst_2
    //   138: anewarray java/lang/Object
    //   141: dup_x1
    //   142: swap
    //   143: iconst_1
    //   144: swap
    //   145: aastore
    //   146: dup_x2
    //   147: dup_x2
    //   148: pop
    //   149: invokestatic valueOf : (J)Ljava/lang/Long;
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual o : ([Ljava/lang/Object;)V
    //   158: return
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x69E937210498L;
    int i = (int)((l1 ^ 0x25A5BEFFE514L) >>> 48L);
    int j = (int)((l1 ^ 0x25A5BEFFE514L) << 16L >>> 48L);
    int k = (int)((l1 ^ 0x25A5BEFFE514L) << 32L >>> 32L);
    l1 ^ 0x25A5BEFFE514L;
    b9.c.method_1507(new x9(l2));
    D(new Object[] { null, null, Integer.valueOf(k), Integer.valueOf((short)j), Integer.valueOf((short)i) });
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "mïAuµL§fRHérT\027\027±pÂ\001qÍ fËT2½!ÊtIóÎ³\016|Î§mÊc¸\026îabº'¨ñ\005Ô6 ¾\nÑÐè¹.OÖ\002°\031'¶§\026\031¯t}¬Ue\035").length();
    byte b2 = 24;
    byte b = -1;
    while (true);
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5CA0;
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
        throw new RuntimeException("wtf/opal/j8", exception);
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
    //   66: ldc_w 'wtf/opal/j8'
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
  
  static {
    long l = a ^ 0x6393C4C8C726L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j8.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */