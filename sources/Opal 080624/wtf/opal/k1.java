package wtf.opal;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class k1 extends kv implements Iterable<kv> {
  private final List<kv> L = new ArrayList<>();
  
  private static final long a = on.a(8113634362929259911L, -7926898907714205759L, MethodHandles.lookup().lookupClass()).a(24512611963886L);
  
  private static final String[] g;
  
  private static final String[] h;
  
  private static final Map i = new HashMap<>(13);
  
  public k1() {}
  
  public k1(k1 paramk1, long paramLong) {
    this(l, paramk1, false);
  }
  
  private k1(long paramLong, k1 paramk1, boolean paramBoolean) {
    // Byte code:
    //   0: getstatic wtf/opal/k1.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: invokestatic O : ()I
    //   9: aload_0
    //   10: invokespecial <init> : ()V
    //   13: istore #5
    //   15: aload_3
    //   16: ifnonnull -> 44
    //   19: new java/lang/NullPointerException
    //   22: dup
    //   23: sipush #31737
    //   26: ldc2_w 6957678164488688328
    //   29: lload_1
    //   30: lxor
    //   31: <illegal opcode> k : (IJ)Ljava/lang/String;
    //   36: invokespecial <init> : (Ljava/lang/String;)V
    //   39: athrow
    //   40: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   43: athrow
    //   44: lload_1
    //   45: lconst_0
    //   46: lcmp
    //   47: ifle -> 93
    //   50: iload #4
    //   52: ifeq -> 78
    //   55: aload_0
    //   56: aload_3
    //   57: getfield L : Ljava/util/List;
    //   60: invokestatic unmodifiableList : (Ljava/util/List;)Ljava/util/List;
    //   63: putfield L : Ljava/util/List;
    //   66: iload #5
    //   68: ifne -> 100
    //   71: goto -> 78
    //   74: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   77: athrow
    //   78: aload_0
    //   79: new java/util/ArrayList
    //   82: dup
    //   83: aload_3
    //   84: getfield L : Ljava/util/List;
    //   87: invokespecial <init> : (Ljava/util/Collection;)V
    //   90: putfield L : Ljava/util/List;
    //   93: goto -> 100
    //   96: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   99: athrow
    //   100: return
    // Exception table:
    //   from	to	target	type
    //   15	40	40	java/lang/NullPointerException
    //   44	71	74	java/lang/NullPointerException
    //   55	93	96	java/lang/NullPointerException
  }
  
  public static k1 O(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    k1 k11 = (k1)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x338A570B3B03L;
    return new k1(l2, k11, true);
  }
  
  public k1 u(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x6C0D14F644CAL;
    (new Object[2])[1] = Integer.valueOf(i);
    new Object[2];
    this.L.add(u4.C(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 p(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = ((Long)paramArrayOfObject[1]).longValue();
    l2 = a ^ l2;
    long l3 = l2 ^ 0x4E429790324BL;
    new Object[2];
    (new Object[2])[1] = Long.valueOf(l3);
    new Object[2];
    this.L.add(u4.V(new Object[] { Long.valueOf(l1) }));
    return this;
  }
  
  public k1 P(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    float f = ((Float)paramArrayOfObject[1]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2F1F56D0A813L;
    new Object[2];
    this.L.add(u4.T(new Object[] { null, Long.valueOf(l2), Float.valueOf(f) }));
    return this;
  }
  
  public k1 W(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x1FEA91C98B08L;
    new Object[2];
    (new Object[2])[1] = Long.valueOf(l2);
    new Object[2];
    this.L.add(u4.n(new Object[] { Double.valueOf(d) }));
    return this;
  }
  
  public k1 E(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x100FAEFD9658L;
    (new Object[2])[1] = Boolean.valueOf(bool);
    new Object[2];
    this.L.add(u4.J(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 Q(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x261C8DC965CAL;
    (new Object[2])[1] = str;
    new Object[2];
    this.L.add(u4.L(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/kv
    //   7: astore #5
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore_2
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore #4
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Integer
    //   36: invokevirtual intValue : ()I
    //   39: istore_3
    //   40: pop
    //   41: iload_2
    //   42: i2l
    //   43: bipush #48
    //   45: lshl
    //   46: iload #4
    //   48: i2l
    //   49: bipush #48
    //   51: lshl
    //   52: bipush #16
    //   54: lushr
    //   55: lor
    //   56: iload_3
    //   57: i2l
    //   58: bipush #32
    //   60: lshl
    //   61: bipush #32
    //   63: lushr
    //   64: lor
    //   65: getstatic wtf/opal/k1.a : J
    //   68: lxor
    //   69: lstore #6
    //   71: invokestatic T : ()I
    //   74: istore #8
    //   76: iload #8
    //   78: ifne -> 131
    //   81: aload #5
    //   83: ifnonnull -> 119
    //   86: goto -> 93
    //   89: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   92: athrow
    //   93: new java/lang/NullPointerException
    //   96: dup
    //   97: sipush #30279
    //   100: ldc2_w 2116633160359405209
    //   103: lload #6
    //   105: lxor
    //   106: <illegal opcode> k : (IJ)Ljava/lang/String;
    //   111: invokespecial <init> : (Ljava/lang/String;)V
    //   114: athrow
    //   115: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   118: athrow
    //   119: aload_0
    //   120: getfield L : Ljava/util/List;
    //   123: aload #5
    //   125: invokeinterface add : (Ljava/lang/Object;)Z
    //   130: pop
    //   131: aload_0
    //   132: areturn
    // Exception table:
    //   from	to	target	type
    //   76	86	89	java/lang/NullPointerException
    //   81	115	115	java/lang/NullPointerException
  }
  
  public k1 B(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2A3BD3785B8CL;
    (new Object[2])[1] = Integer.valueOf(i);
    new Object[2];
    this.L.set(j, u4.C(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 j(Object[] paramArrayOfObject) {
    long l2 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l2 = a ^ l2;
    long l3 = l2 ^ 0x4C33D6DC7D62L;
    new Object[2];
    (new Object[2])[1] = Long.valueOf(l3);
    new Object[2];
    this.L.set(i, u4.V(new Object[] { Long.valueOf(l1) }));
    return this;
  }
  
  public k1 G(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    float f = ((Float)paramArrayOfObject[2]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x22F2A1B8FD70L;
    new Object[2];
    this.L.set(i, u4.T(new Object[] { null, Long.valueOf(l2), Float.valueOf(f) }));
    return this;
  }
  
  public k1 A(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    double d = ((Double)paramArrayOfObject[2]).doubleValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x6BB895888FDL;
    new Object[2];
    (new Object[2])[1] = Long.valueOf(l2);
    new Object[2];
    this.L.set(i, u4.n(new Object[] { Double.valueOf(d) }));
    return this;
  }
  
  public k1 v(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    boolean bool = ((Boolean)paramArrayOfObject[1]).booleanValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x23109CBA3CE9L;
    (new Object[2])[1] = Boolean.valueOf(bool);
    new Object[2];
    this.L.set(i, u4.J(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 o(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    String str = (String)paramArrayOfObject[2];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x1F4784730414L;
    (new Object[2])[1] = str;
    new Object[2];
    this.L.set(i, u4.L(new Object[] { Long.valueOf(l2) }));
    return this;
  }
  
  public k1 b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore #5
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast wtf/opal/kv
    //   18: astore_2
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Long
    //   25: invokevirtual longValue : ()J
    //   28: lstore_3
    //   29: pop
    //   30: getstatic wtf/opal/k1.a : J
    //   33: lload_3
    //   34: lxor
    //   35: lstore_3
    //   36: invokestatic T : ()I
    //   39: istore #6
    //   41: aload_2
    //   42: iload #6
    //   44: ifne -> 94
    //   47: ifnonnull -> 82
    //   50: goto -> 57
    //   53: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   56: athrow
    //   57: new java/lang/NullPointerException
    //   60: dup
    //   61: sipush #31416
    //   64: ldc2_w 6155646344139272774
    //   67: lload_3
    //   68: lxor
    //   69: <illegal opcode> k : (IJ)Ljava/lang/String;
    //   74: invokespecial <init> : (Ljava/lang/String;)V
    //   77: athrow
    //   78: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   81: athrow
    //   82: aload_0
    //   83: getfield L : Ljava/util/List;
    //   86: iload #5
    //   88: aload_2
    //   89: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   94: pop
    //   95: aload_0
    //   96: areturn
    // Exception table:
    //   from	to	target	type
    //   41	50	53	java/lang/NullPointerException
    //   47	78	78	java/lang/NullPointerException
  }
  
  public k1 x(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.L.remove(i);
    return this;
  }
  
  public int k(Object[] paramArrayOfObject) {
    return this.L.size();
  }
  
  public boolean U(Object[] paramArrayOfObject) {
    return this.L.isEmpty();
  }
  
  public kv P(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    return this.L.get(i);
  }
  
  public List q(Object[] paramArrayOfObject) {
    return Collections.unmodifiableList(this.L);
  }
  
  public Iterator<kv> iterator() {
    Iterator<kv> iterator = this.L.iterator();
    return new xe(this, iterator);
  }
  
  void x(Object[] paramArrayOfObject) throws IOException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l6 l6 = (l6)paramArrayOfObject[1];
    long l2 = l1 ^ 0x7C9B61342E67L;
    long l3 = l1 ^ 0x17ED83442928L;
    long l4 = l1 ^ 0x603193F13F9AL;
    long l5 = l1 ^ 0x0L;
    new Object[1];
    l6.O(new Object[] { Long.valueOf(l3) });
    Iterator<kv> iterator = iterator();
    int i = lq.O();
    try {
      if (i != 0) {
        try {
          if (iterator.hasNext()) {
            (new Object[2])[1] = l6;
            new Object[2];
            ((kv)iterator.next()).x(new Object[] { Long.valueOf(l5) });
            label26: while (iterator.hasNext()) {
              try {
                new Object[1];
                l6.T(new Object[] { Long.valueOf(l2) });
                (new Object[2])[1] = l6;
                new Object[2];
                ((kv)iterator.next()).x(new Object[] { Long.valueOf(l5) });
                while (true) {
                  if (l1 > 0L)
                    if (i != 0) {
                    
                    } else {
                      break;
                    }  
                  if (i == 0) {
                    if (l1 > 0L)
                      break label26; 
                    continue;
                  } 
                  continue label26;
                } 
                // Byte code: goto -> 253
              } catch (IOException iOException) {
                throw a(null);
              } 
            } 
          } 
        } catch (IOException iOException) {
          throw a(null);
        } 
        new Object[1];
        l6.s(new Object[] { Long.valueOf(l4) });
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
  }
  
  public boolean P(Object[] paramArrayOfObject) {
    return true;
  }
  
  public k1 D(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return this;
  }
  
  public int hashCode() {
    return this.L.hashCode();
  }
  
  public boolean equals(Object paramObject) {
    long l = a ^ 0x34FE6ADD86E8L;
    int i = lq.O();
    try {
      if (i != 0)
        try {
          if (this == paramObject)
            return true; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramObject == null)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (i != 0)
        try {
          if (getClass() != paramObject.getClass())
            return false; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    k1 k11 = (k1)paramObject;
    return this.L.equals(k11.L);
  }
  
  static {
    long l = a ^ 0x1F5D8D1F75B6L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = ";A\036ö~Û\025±~d ýTc\007Ü\bãr^+e^#ªêë\030/¿¶*ÕàÒyÙÔº¤ÚËc=$6\0078á\030Ã\0051#\b¾¡>hÿFßÎ;\027.&Yj«\\").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  private static String b(byte[] paramArrayOfbyte) {
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
  
  private static String b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7F49;
    if (h[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])i.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          i.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/k1", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = g[i].getBytes("ISO-8859-1");
      h[i] = b(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return h[i];
  }
  
  private static Object b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
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
    //   65: ldc_w 'wtf/opal/k1'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */