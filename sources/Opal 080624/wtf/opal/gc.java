package wtf.opal;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import oshi.util.tuples.Pair;

public final class gc {
  private final File y;
  
  private final File T;
  
  private final Gson Y;
  
  private static String L;
  
  private static final long a = on.a(6012925491870980058L, -1969561516803200857L, MethodHandles.lookup().lookupClass()).a(241157504883515L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public gc(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/gc.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: new java/io/File
    //   14: dup
    //   15: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   18: getfield field_1697 : Ljava/io/File;
    //   21: sipush #19935
    //   24: ldc2_w 7584662547634655801
    //   27: lload_1
    //   28: lxor
    //   29: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   34: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   37: putfield y : Ljava/io/File;
    //   40: aload_0
    //   41: new java/io/File
    //   44: dup
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1697 : Ljava/io/File;
    //   51: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   54: sipush #21142
    //   57: ldc2_w 2493037636908649845
    //   60: lload_1
    //   61: lxor
    //   62: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   67: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   72: invokespecial <init> : (Ljava/lang/String;)V
    //   75: putfield T : Ljava/io/File;
    //   78: invokestatic O : ()Ljava/lang/String;
    //   81: aload_0
    //   82: new com/google/gson/GsonBuilder
    //   85: dup
    //   86: invokespecial <init> : ()V
    //   89: invokevirtual setPrettyPrinting : ()Lcom/google/gson/GsonBuilder;
    //   92: invokevirtual excludeFieldsWithoutExposeAnnotation : ()Lcom/google/gson/GsonBuilder;
    //   95: invokevirtual create : ()Lcom/google/gson/Gson;
    //   98: putfield Y : Lcom/google/gson/Gson;
    //   101: astore_3
    //   102: aload_0
    //   103: getfield T : Ljava/io/File;
    //   106: invokevirtual getParentFile : ()Ljava/io/File;
    //   109: invokevirtual isDirectory : ()Z
    //   112: aload_3
    //   113: ifnonnull -> 151
    //   116: ifne -> 144
    //   119: goto -> 126
    //   122: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   125: athrow
    //   126: aload_0
    //   127: getfield T : Ljava/io/File;
    //   130: invokevirtual getParentFile : ()Ljava/io/File;
    //   133: invokevirtual mkdirs : ()Z
    //   136: pop
    //   137: goto -> 144
    //   140: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: aload_0
    //   145: getfield y : Ljava/io/File;
    //   148: invokevirtual mkdirs : ()Z
    //   151: pop
    //   152: lload_1
    //   153: lconst_0
    //   154: lcmp
    //   155: iflt -> 169
    //   158: invokestatic D : ()[Lwtf/opal/d;
    //   161: ifnull -> 176
    //   164: ldc 'qz0TU'
    //   166: invokestatic G : (Ljava/lang/String;)V
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: return
    // Exception table:
    //   from	to	target	type
    //   102	119	122	wtf/opal/x5
    //   116	137	140	wtf/opal/x5
    //   151	169	172	wtf/opal/x5
  }
  
  public File s(Object[] paramArrayOfObject) {
    return this.y;
  }
  
  public List I(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/gc.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: new java/io/File
    //   21: dup
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1697 : Ljava/io/File;
    //   28: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   31: sipush #19935
    //   34: ldc2_w 7584726012491466012
    //   37: lload_2
    //   38: lxor
    //   39: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   44: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   49: invokespecial <init> : (Ljava/lang/String;)V
    //   52: astore #4
    //   54: aload #4
    //   56: invokevirtual exists : ()Z
    //   59: ifeq -> 112
    //   62: new java/util/ArrayList
    //   65: dup
    //   66: aload #4
    //   68: invokevirtual listFiles : ()[Ljava/io/File;
    //   71: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
    //   74: invokespecial <init> : (Ljava/util/Collection;)V
    //   77: astore #5
    //   79: aload #5
    //   81: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   86: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   91: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   96: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   101: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   106: invokeinterface toList : ()Ljava/util/List;
    //   111: areturn
    //   112: new java/util/ArrayList
    //   115: dup
    //   116: invokespecial <init> : ()V
    //   119: areturn
  }
  
  public List q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/gc.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: new java/util/ArrayList
    //   21: dup
    //   22: invokespecial <init> : ()V
    //   25: astore #5
    //   27: invokestatic O : ()Ljava/lang/String;
    //   30: new java/io/File
    //   33: dup
    //   34: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   37: getfield field_1697 : Ljava/io/File;
    //   40: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   43: sipush #5750
    //   46: ldc2_w 8835859025240340913
    //   49: lload_2
    //   50: lxor
    //   51: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   56: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   61: invokespecial <init> : (Ljava/lang/String;)V
    //   64: astore #6
    //   66: astore #4
    //   68: new java/text/SimpleDateFormat
    //   71: dup
    //   72: sipush #17870
    //   75: ldc2_w 2351248402285652495
    //   78: lload_2
    //   79: lxor
    //   80: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   85: invokespecial <init> : (Ljava/lang/String;)V
    //   88: astore #7
    //   90: aload #5
    //   92: aload #6
    //   94: invokevirtual listFiles : ()[Ljava/io/File;
    //   97: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   100: checkcast [Ljava/io/File;
    //   103: invokestatic addAll : (Ljava/util/Collection;[Ljava/lang/Object;)Z
    //   106: pop
    //   107: aload #5
    //   109: <illegal opcode> applyAsLong : ()Ljava/util/function/ToLongFunction;
    //   114: invokestatic comparingLong : (Ljava/util/function/ToLongFunction;)Ljava/util/Comparator;
    //   117: invokeinterface reversed : ()Ljava/util/Comparator;
    //   122: invokeinterface sort : (Ljava/util/Comparator;)V
    //   127: aload #5
    //   129: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   134: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   139: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   144: aload #7
    //   146: <illegal opcode> apply : (Ljava/text/SimpleDateFormat;)Ljava/util/function/Function;
    //   151: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   156: invokestatic toList : ()Ljava/util/stream/Collector;
    //   159: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
    //   164: checkcast java/util/List
    //   167: aload #4
    //   169: ifnull -> 186
    //   172: iconst_1
    //   173: anewarray wtf/opal/d
    //   176: invokestatic p : ([Lwtf/opal/d;)V
    //   179: goto -> 186
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: areturn
    // Exception table:
    //   from	to	target	type
    //   90	179	182	wtf/opal/x5
  }
  
  public void w(Object[] paramArrayOfObject) {
    try {
      Files.writeString(this.T.toPath(), this.Y.toJson(d1.q(new Object[0]).x(new Object[0]).g(new Object[0])), new java.nio.file.OpenOption[0]);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void P(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/gc.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: new java/io/File
    //   28: dup
    //   29: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   32: getfield field_1697 : Ljava/io/File;
    //   35: aload_2
    //   36: sipush #19935
    //   39: ldc2_w 7584744224942192529
    //   42: lload_3
    //   43: lxor
    //   44: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   49: swap
    //   50: sipush #29338
    //   53: ldc2_w 2695118311304874194
    //   56: lload_3
    //   57: lxor
    //   58: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   63: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   68: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   71: invokevirtual toPath : ()Ljava/nio/file/Path;
    //   74: aload_0
    //   75: getfield Y : Lcom/google/gson/Gson;
    //   78: iconst_0
    //   79: anewarray java/lang/Object
    //   82: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   85: iconst_0
    //   86: anewarray java/lang/Object
    //   89: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   92: iconst_0
    //   93: anewarray java/lang/Object
    //   96: invokevirtual g : ([Ljava/lang/Object;)Ljava/util/Collection;
    //   99: invokevirtual toJson : (Ljava/lang/Object;)Ljava/lang/String;
    //   102: iconst_0
    //   103: anewarray java/nio/file/OpenOption
    //   106: invokestatic writeString : (Ljava/nio/file/Path;Ljava/lang/CharSequence;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;
    //   109: pop
    //   110: goto -> 120
    //   113: astore #5
    //   115: aload #5
    //   117: invokevirtual printStackTrace : ()V
    //   120: return
    // Exception table:
    //   from	to	target	type
    //   25	110	113	java/io/IOException
  }
  
  public void N(Object[] paramArrayOfObject) {
    d[] arrayOfD;
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x3929778B4120L;
    try {
      arrayOfD = (d[])this.Y.fromJson(new FileReader(this.T), d[].class);
    } catch (FileNotFoundException fileNotFoundException) {
      return;
    } 
    (new Object[4])[3] = Boolean.valueOf(true);
    (new Object[4])[2] = Boolean.valueOf(true);
    new Object[4];
    G(new Object[] { null, Long.valueOf(l2), arrayOfD });
  }
  
  public void I(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0xF3A71EA3085L;
    (new Object[3])[2] = Boolean.valueOf(false);
    (new Object[3])[1] = str;
    new Object[3];
    J(new Object[] { Long.valueOf(l2) });
  }
  
  public void J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore #4
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/String
    //   18: astore_3
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Boolean
    //   25: invokevirtual booleanValue : ()Z
    //   28: istore_2
    //   29: pop
    //   30: getstatic wtf/opal/gc.a : J
    //   33: lload #4
    //   35: lxor
    //   36: lstore #4
    //   38: lload #4
    //   40: dup2
    //   41: ldc2_w 88783084925656
    //   44: lxor
    //   45: lstore #6
    //   47: pop2
    //   48: aload_0
    //   49: getfield Y : Lcom/google/gson/Gson;
    //   52: new java/io/FileReader
    //   55: dup
    //   56: new java/io/File
    //   59: dup
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: getfield field_1697 : Ljava/io/File;
    //   66: aload_3
    //   67: sipush #19935
    //   70: ldc2_w 7584721214603373798
    //   73: lload #4
    //   75: lxor
    //   76: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   81: swap
    //   82: sipush #29338
    //   85: ldc2_w 2695053352121144229
    //   88: lload #4
    //   90: lxor
    //   91: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   96: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   101: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   104: invokespecial <init> : (Ljava/io/File;)V
    //   107: ldc_w [Lwtf/opal/d;
    //   110: invokevirtual fromJson : (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;
    //   113: checkcast [Lwtf/opal/d;
    //   116: astore #8
    //   118: goto -> 124
    //   121: astore #9
    //   123: return
    //   124: aload_0
    //   125: aload #8
    //   127: lload #6
    //   129: iconst_0
    //   130: iload_2
    //   131: iconst_4
    //   132: anewarray java/lang/Object
    //   135: dup_x1
    //   136: swap
    //   137: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   140: iconst_3
    //   141: swap
    //   142: aastore
    //   143: dup_x1
    //   144: swap
    //   145: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   148: iconst_2
    //   149: swap
    //   150: aastore
    //   151: dup_x2
    //   152: dup_x2
    //   153: pop
    //   154: invokestatic valueOf : (J)Ljava/lang/Long;
    //   157: iconst_1
    //   158: swap
    //   159: aastore
    //   160: dup_x1
    //   161: swap
    //   162: iconst_0
    //   163: swap
    //   164: aastore
    //   165: invokevirtual G : ([Ljava/lang/Object;)V
    //   168: return
    // Exception table:
    //   from	to	target	type
    //   48	118	121	java/io/FileNotFoundException
  }
  
  private void G(Object[] paramArrayOfObject) {
    d[] arrayOfD1 = (d[])paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    boolean bool1 = ((Boolean)paramArrayOfObject[2]).booleanValue();
    boolean bool2 = ((Boolean)paramArrayOfObject[3]).booleanValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x7ABE51D4193AL;
    long l3 = l1 ^ 0x3C33F6F2CC27L;
    d[] arrayOfD2 = arrayOfD1;
    int i = arrayOfD2.length;
    byte b = 0;
    String str = O();
    while (true) {
      while (b < i) {
        d d = arrayOfD2[b];
        Iterator iterator = d1.q(new Object[0]).x(new Object[0]).g(new Object[0]).iterator();
        continue;
        while (true) {
          while (true);
          break;
        } 
        if (SYNTHETIC_LOCAL_VARIABLE_16.hasNext() != null)
          break; 
      } 
      break;
    } 
  }
  
  private void y(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    k3 k3 = (k3)paramArrayOfObject[1];
    String str1 = (String)paramArrayOfObject[2];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x69013D55E162L;
    ky ky = (ky)k3;
    String str2 = O();
    Enum[] arrayOfEnum = ky.A(new Object[0]);
    int i = arrayOfEnum.length;
    byte b = 0;
    while (b < i) {
      Enum enum_ = arrayOfEnum[b];
      try {
        if (l1 >= 0L)
          if (str2 == null) {
            try {
              if (enum_.name().equals(String.valueOf(str1))) {
                (new Object[2])[1] = Integer.valueOf(enum_.ordinal());
                new Object[2];
                ky.U(new Object[] { Long.valueOf(l2) });
              } 
            } catch (x5 x5) {
              throw a(null);
            } 
            b++;
          }  
      } catch (x5 x5) {
        throw a(null);
      } 
      if (str2 != null)
        break; 
    } 
  }
  
  private static Pair lambda$getConfigPairList$3(SimpleDateFormat paramSimpleDateFormat, File paramFile) {
    // Byte code:
    //   0: getstatic wtf/opal/gc.a : J
    //   3: ldc2_w 135773528626804
    //   6: lxor
    //   7: lstore_2
    //   8: new oshi/util/tuples/Pair
    //   11: dup
    //   12: aload_1
    //   13: invokevirtual getName : ()Ljava/lang/String;
    //   16: sipush #29338
    //   19: ldc2_w 2694996230586547548
    //   22: lload_2
    //   23: lxor
    //   24: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   29: ldc_w ''
    //   32: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   35: aload_0
    //   36: aload_1
    //   37: invokevirtual lastModified : ()J
    //   40: invokestatic valueOf : (J)Ljava/lang/Long;
    //   43: invokevirtual format : (Ljava/lang/Object;)Ljava/lang/String;
    //   46: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   49: areturn
  }
  
  private static boolean lambda$getConfigPairList$2(File paramFile) {
    // Byte code:
    //   0: getstatic wtf/opal/gc.a : J
    //   3: ldc2_w 103996014669285
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokevirtual getName : ()Ljava/lang/String;
    //   12: sipush #29338
    //   15: ldc2_w 2695027391768692429
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   25: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   28: ireturn
  }
  
  private static String lambda$getConfigList$1(File paramFile) {
    // Byte code:
    //   0: getstatic wtf/opal/gc.a : J
    //   3: ldc2_w 71849608145821
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokevirtual getName : ()Ljava/lang/String;
    //   12: sipush #25746
    //   15: ldc2_w 8917056375653717690
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   25: ldc_w ''
    //   28: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   31: areturn
  }
  
  private static boolean lambda$getConfigList$0(File paramFile) {
    // Byte code:
    //   0: getstatic wtf/opal/gc.a : J
    //   3: ldc2_w 82919625094121
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokevirtual getName : ()Ljava/lang/String;
    //   12: sipush #29338
    //   15: ldc2_w 2695049084487424193
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   25: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   28: ireturn
  }
  
  public static void G(String paramString) {
    L = paramString;
  }
  
  public static String O() {
    return L;
  }
  
  static {
    G((String)null);
    long l = a ^ 0x1628DE9E8AB8L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "²h«#2ÉØ±dS5Ï\022éï0í·]\006â+Ñ[º\023 \037(ó:ðuz.p©tÒn\"ql\0130ôtÙñS\003\000ª\037ª'£ú£F\013\035\020m\nÉÄ¬]\000S\000­J\000áä\024\020\007Ø¾9¦mì«&Ì¾©Û) n5<]Â(Ñ³µ%1A)ÙÜA\005«\023{r8Ú%e\017êØ\034").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x212E;
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
        throw new RuntimeException("wtf/opal/gc", exception);
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
    //   66: ldc_w 'wtf/opal/gc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\gc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */