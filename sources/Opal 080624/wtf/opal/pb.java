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

enum pb {
  OPAL, EDGE, FIREFOX, EXHIBITION_1, EXHIBITION_2, KETAMINE_1, KETAMINE_2, COBALT, MIGRATOR, MINECON_2011, MINECON_2012, MINECON_2013, MINECON_2015, MINECON_2016, MOJANG_STUDIOS, MOJANG, PRISMARINE;
  
  private final String m;
  
  private static final pb[] P;
  
  private static final long a = on.a(-3366393724061096946L, -3302164229111894753L, MethodHandles.lookup().lookupClass()).a(279174121696925L);
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  pb(String paramString1) {
    this.m = paramString1;
  }
  
  public String toString() {
    return this.m;
  }
  
  private static pb[] d(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/pb.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: sipush #13968
    //   21: ldc2_w 29373618540834387
    //   24: lload_1
    //   25: lxor
    //   26: <illegal opcode> g : (IJ)I
    //   31: anewarray wtf/opal/pb
    //   34: dup
    //   35: iconst_0
    //   36: getstatic wtf/opal/pb.OPAL : Lwtf/opal/pb;
    //   39: aastore
    //   40: dup
    //   41: iconst_1
    //   42: getstatic wtf/opal/pb.EDGE : Lwtf/opal/pb;
    //   45: aastore
    //   46: dup
    //   47: iconst_2
    //   48: getstatic wtf/opal/pb.FIREFOX : Lwtf/opal/pb;
    //   51: aastore
    //   52: dup
    //   53: iconst_3
    //   54: getstatic wtf/opal/pb.EXHIBITION_1 : Lwtf/opal/pb;
    //   57: aastore
    //   58: dup
    //   59: iconst_4
    //   60: getstatic wtf/opal/pb.EXHIBITION_2 : Lwtf/opal/pb;
    //   63: aastore
    //   64: dup
    //   65: iconst_5
    //   66: getstatic wtf/opal/pb.KETAMINE_1 : Lwtf/opal/pb;
    //   69: aastore
    //   70: dup
    //   71: sipush #21363
    //   74: ldc2_w 2390543064171711407
    //   77: lload_1
    //   78: lxor
    //   79: <illegal opcode> g : (IJ)I
    //   84: getstatic wtf/opal/pb.KETAMINE_2 : Lwtf/opal/pb;
    //   87: aastore
    //   88: dup
    //   89: sipush #3121
    //   92: ldc2_w 86449802301035745
    //   95: lload_1
    //   96: lxor
    //   97: <illegal opcode> g : (IJ)I
    //   102: getstatic wtf/opal/pb.COBALT : Lwtf/opal/pb;
    //   105: aastore
    //   106: dup
    //   107: sipush #29210
    //   110: ldc2_w 1616258437988854490
    //   113: lload_1
    //   114: lxor
    //   115: <illegal opcode> g : (IJ)I
    //   120: getstatic wtf/opal/pb.MIGRATOR : Lwtf/opal/pb;
    //   123: aastore
    //   124: dup
    //   125: sipush #32737
    //   128: ldc2_w 8828473509137852198
    //   131: lload_1
    //   132: lxor
    //   133: <illegal opcode> g : (IJ)I
    //   138: getstatic wtf/opal/pb.MINECON_2011 : Lwtf/opal/pb;
    //   141: aastore
    //   142: dup
    //   143: sipush #15824
    //   146: ldc2_w 4904014105817703689
    //   149: lload_1
    //   150: lxor
    //   151: <illegal opcode> g : (IJ)I
    //   156: getstatic wtf/opal/pb.MINECON_2012 : Lwtf/opal/pb;
    //   159: aastore
    //   160: dup
    //   161: sipush #17947
    //   164: ldc2_w 7198297842309996233
    //   167: lload_1
    //   168: lxor
    //   169: <illegal opcode> g : (IJ)I
    //   174: getstatic wtf/opal/pb.MINECON_2013 : Lwtf/opal/pb;
    //   177: aastore
    //   178: dup
    //   179: sipush #14568
    //   182: ldc2_w 5695411107248201790
    //   185: lload_1
    //   186: lxor
    //   187: <illegal opcode> g : (IJ)I
    //   192: getstatic wtf/opal/pb.MINECON_2015 : Lwtf/opal/pb;
    //   195: aastore
    //   196: dup
    //   197: sipush #22706
    //   200: ldc2_w 3518809551147892837
    //   203: lload_1
    //   204: lxor
    //   205: <illegal opcode> g : (IJ)I
    //   210: getstatic wtf/opal/pb.MINECON_2016 : Lwtf/opal/pb;
    //   213: aastore
    //   214: dup
    //   215: sipush #24442
    //   218: ldc2_w 8353397890425194424
    //   221: lload_1
    //   222: lxor
    //   223: <illegal opcode> g : (IJ)I
    //   228: getstatic wtf/opal/pb.MOJANG_STUDIOS : Lwtf/opal/pb;
    //   231: aastore
    //   232: dup
    //   233: sipush #11859
    //   236: ldc2_w 4886989895867463303
    //   239: lload_1
    //   240: lxor
    //   241: <illegal opcode> g : (IJ)I
    //   246: getstatic wtf/opal/pb.MOJANG : Lwtf/opal/pb;
    //   249: aastore
    //   250: dup
    //   251: sipush #17587
    //   254: ldc2_w 1391879973246409832
    //   257: lload_1
    //   258: lxor
    //   259: <illegal opcode> g : (IJ)I
    //   264: getstatic wtf/opal/pb.PRISMARINE : Lwtf/opal/pb;
    //   267: aastore
    //   268: areturn
  }
  
  static {
    long l1 = a ^ 0x2F5AEECC43CFL;
    long l2 = l1 ^ 0x5B27C9BF9A55L;
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[34];
    boolean bool = false;
    String str;
    int i = (str = "léë°ÃnÆL\020ÈG5G!È\030­j*ô\000*ú\b¬S{¿ûE\020À¥ÁË\020Â`\fHÊÎ8C®\bn,¥\024e^+\0207®3u9\003\034#Ï\b\000u\tóo\020À¥ÁË\020Â`­\005l\017ØB\020o*u°ú~\037$\016ðµÚ ÊÀa\020À¥ÁË\020Â`\006\0040È5«F\020öÖ|Ãü#\030ÚN²µ6Ü¼Ñ\b\003\bü°½¬\020ws<lJ/÷ \013íÝcò\020À¥ÁË\020Â``<ÉÚ\020\036\r\016\bc°`­HÌ\002\020ws<lJ/÷ ø|d×O2Ii\bñR1d(ª\020*P½ãÇÃÇ¨Î($­<\020:Óäùê·Éÿ=Ìâlµ\024Âh\020ws<lJ/÷ n\036MZµ\034ï\017\020ws<lJ/÷ '<«XZ\020ws<lJ/÷ ub#Ó\007øoó\020$\037~î ç¦IV §ì\020\016Ý\0133\003ì\033ö?leÎ\020*P½ãÇM »BW¹yx\b­lD\006yÕ\bãXàUÖ¦\020À¥ÁË\020Â`m\000\033Ü\fª&ª\020¥Ê;Ë\0166m\033Ë®»·V\003\b\036öýÖ%ó\017\020ÈG5G!È\016,\004à(ía®\bB&s\007\002³\020$\037~î ç\037\035ÿ{ïP'").length();
    byte b2 = 8;
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x14C;
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
        throw new RuntimeException("wtf/opal/pb", exception);
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
    //   66: ldc_w 'wtf/opal/pb'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */