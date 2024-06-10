package wtf.opal;

import java.io.IOException;
import java.io.Writer;
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

class l6 {
  private static final int J;
  
  private static final char[] x;
  
  private static final char[] s;
  
  private static final char[] k;
  
  private static final char[] Y;
  
  private static final char[] L;
  
  private static final char[] q;
  
  private static final char[] Q;
  
  private static final char[] a;
  
  protected final Writer I;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map e;
  
  l6(Writer paramWriter) {
    this.I = paramWriter;
  }
  
  private static char[] Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_2
    //   21: pop
    //   22: getstatic wtf/opal/l6.b : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: invokestatic T : ()I
    //   31: istore #4
    //   33: iload_1
    //   34: sipush #5281
    //   37: ldc2_w 8627666518643779028
    //   40: lload_2
    //   41: lxor
    //   42: <illegal opcode> h : (IJ)I
    //   47: iload #4
    //   49: ifne -> 197
    //   52: if_icmple -> 183
    //   55: goto -> 62
    //   58: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   61: athrow
    //   62: iload_1
    //   63: sipush #25000
    //   66: ldc2_w 2635253931460546754
    //   69: lload_2
    //   70: lxor
    //   71: <illegal opcode> h : (IJ)I
    //   76: iload #4
    //   78: lload_2
    //   79: lconst_0
    //   80: lcmp
    //   81: ifle -> 133
    //   84: ifne -> 125
    //   87: goto -> 94
    //   90: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   93: athrow
    //   94: if_icmplt -> 146
    //   97: goto -> 104
    //   100: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   103: athrow
    //   104: iload_1
    //   105: sipush #2647
    //   108: ldc2_w 7768933724919511867
    //   111: lload_2
    //   112: lxor
    //   113: <illegal opcode> h : (IJ)I
    //   118: goto -> 125
    //   121: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   124: athrow
    //   125: lload_2
    //   126: lconst_0
    //   127: lcmp
    //   128: iflt -> 166
    //   131: iload #4
    //   133: ifne -> 166
    //   136: if_icmple -> 152
    //   139: goto -> 146
    //   142: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   145: athrow
    //   146: aconst_null
    //   147: areturn
    //   148: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   151: athrow
    //   152: iload_1
    //   153: sipush #18858
    //   156: ldc2_w 1283378063384410321
    //   159: lload_2
    //   160: lxor
    //   161: <illegal opcode> h : (IJ)I
    //   166: if_icmpne -> 179
    //   169: getstatic wtf/opal/l6.q : [C
    //   172: goto -> 182
    //   175: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   178: athrow
    //   179: getstatic wtf/opal/l6.Q : [C
    //   182: areturn
    //   183: iload_1
    //   184: sipush #30663
    //   187: ldc2_w 6436032291878946462
    //   190: lload_2
    //   191: lxor
    //   192: <illegal opcode> h : (IJ)I
    //   197: iload #4
    //   199: lload_2
    //   200: lconst_0
    //   201: lcmp
    //   202: ifle -> 242
    //   205: ifne -> 240
    //   208: if_icmpne -> 226
    //   211: goto -> 218
    //   214: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   217: athrow
    //   218: getstatic wtf/opal/l6.s : [C
    //   221: areturn
    //   222: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   225: athrow
    //   226: iload_1
    //   227: sipush #6971
    //   230: ldc2_w 8497071209138268773
    //   233: lload_2
    //   234: lxor
    //   235: <illegal opcode> h : (IJ)I
    //   240: iload #4
    //   242: lload_2
    //   243: lconst_0
    //   244: lcmp
    //   245: iflt -> 283
    //   248: ifne -> 281
    //   251: if_icmple -> 267
    //   254: goto -> 261
    //   257: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   260: athrow
    //   261: aconst_null
    //   262: areturn
    //   263: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   266: athrow
    //   267: iload_1
    //   268: sipush #6971
    //   271: ldc2_w 8497071209138268773
    //   274: lload_2
    //   275: lxor
    //   276: <illegal opcode> h : (IJ)I
    //   281: iload #4
    //   283: lload_2
    //   284: lconst_0
    //   285: lcmp
    //   286: iflt -> 326
    //   289: ifne -> 324
    //   292: if_icmpne -> 310
    //   295: goto -> 302
    //   298: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   301: athrow
    //   302: getstatic wtf/opal/l6.x : [C
    //   305: areturn
    //   306: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   309: athrow
    //   310: iload_1
    //   311: sipush #4130
    //   314: ldc2_w 4989603866853417290
    //   317: lload_2
    //   318: lxor
    //   319: <illegal opcode> h : (IJ)I
    //   324: iload #4
    //   326: lload_2
    //   327: lconst_0
    //   328: lcmp
    //   329: iflt -> 367
    //   332: ifne -> 365
    //   335: if_icmple -> 351
    //   338: goto -> 345
    //   341: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   344: athrow
    //   345: aconst_null
    //   346: areturn
    //   347: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   350: athrow
    //   351: iload_1
    //   352: sipush #14174
    //   355: ldc2_w 4545736869619172895
    //   358: lload_2
    //   359: lxor
    //   360: <illegal opcode> h : (IJ)I
    //   365: iload #4
    //   367: lload_2
    //   368: lconst_0
    //   369: lcmp
    //   370: iflt -> 416
    //   373: ifne -> 408
    //   376: if_icmpne -> 394
    //   379: goto -> 386
    //   382: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   385: athrow
    //   386: getstatic wtf/opal/l6.k : [C
    //   389: areturn
    //   390: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   393: athrow
    //   394: iload_1
    //   395: sipush #25078
    //   398: ldc2_w 8902215022888112318
    //   401: lload_2
    //   402: lxor
    //   403: <illegal opcode> h : (IJ)I
    //   408: lload_2
    //   409: lconst_0
    //   410: lcmp
    //   411: ifle -> 469
    //   414: iload #4
    //   416: ifne -> 469
    //   419: if_icmpne -> 437
    //   422: goto -> 429
    //   425: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   428: athrow
    //   429: getstatic wtf/opal/l6.Y : [C
    //   432: areturn
    //   433: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   436: athrow
    //   437: iload_1
    //   438: iload #4
    //   440: lload_2
    //   441: lconst_0
    //   442: lcmp
    //   443: ifle -> 462
    //   446: ifne -> 493
    //   449: sipush #5343
    //   452: ldc2_w 7892568236466021771
    //   455: lload_2
    //   456: lxor
    //   457: <illegal opcode> h : (IJ)I
    //   462: goto -> 469
    //   465: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   468: athrow
    //   469: if_icmpne -> 480
    //   472: getstatic wtf/opal/l6.L : [C
    //   475: areturn
    //   476: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   479: athrow
    //   480: sipush #29532
    //   483: ldc2_w 3633986184639562298
    //   486: lload_2
    //   487: lxor
    //   488: <illegal opcode> h : (IJ)I
    //   493: newarray char
    //   495: dup
    //   496: iconst_0
    //   497: sipush #30663
    //   500: ldc2_w 6436032291878946462
    //   503: lload_2
    //   504: lxor
    //   505: <illegal opcode> h : (IJ)I
    //   510: castore
    //   511: dup
    //   512: iconst_1
    //   513: sipush #16667
    //   516: ldc2_w 7112899961586413646
    //   519: lload_2
    //   520: lxor
    //   521: <illegal opcode> h : (IJ)I
    //   526: castore
    //   527: dup
    //   528: iconst_2
    //   529: sipush #30789
    //   532: ldc2_w 1627671227480262954
    //   535: lload_2
    //   536: lxor
    //   537: <illegal opcode> h : (IJ)I
    //   542: castore
    //   543: dup
    //   544: iconst_3
    //   545: sipush #24175
    //   548: ldc2_w 9019232017548013367
    //   551: lload_2
    //   552: lxor
    //   553: <illegal opcode> h : (IJ)I
    //   558: castore
    //   559: dup
    //   560: iconst_4
    //   561: getstatic wtf/opal/l6.a : [C
    //   564: iload_1
    //   565: iconst_4
    //   566: ishr
    //   567: sipush #6563
    //   570: ldc2_w 4840483121526745306
    //   573: lload_2
    //   574: lxor
    //   575: <illegal opcode> h : (IJ)I
    //   580: iand
    //   581: caload
    //   582: castore
    //   583: dup
    //   584: iconst_5
    //   585: getstatic wtf/opal/l6.a : [C
    //   588: iload_1
    //   589: sipush #5634
    //   592: ldc2_w 1748650352714852209
    //   595: lload_2
    //   596: lxor
    //   597: <illegal opcode> h : (IJ)I
    //   602: iand
    //   603: caload
    //   604: castore
    //   605: areturn
    // Exception table:
    //   from	to	target	type
    //   33	55	58	wtf/opal/x5
    //   52	87	90	wtf/opal/x5
    //   62	97	100	wtf/opal/x5
    //   94	118	121	wtf/opal/x5
    //   125	139	142	wtf/opal/x5
    //   136	148	148	wtf/opal/x5
    //   166	175	175	wtf/opal/x5
    //   197	211	214	wtf/opal/x5
    //   208	222	222	wtf/opal/x5
    //   240	254	257	wtf/opal/x5
    //   251	263	263	wtf/opal/x5
    //   281	295	298	wtf/opal/x5
    //   292	306	306	wtf/opal/x5
    //   324	338	341	wtf/opal/x5
    //   335	347	347	wtf/opal/x5
    //   365	379	382	wtf/opal/x5
    //   376	390	390	wtf/opal/x5
    //   408	422	425	wtf/opal/x5
    //   419	433	433	wtf/opal/x5
    //   437	462	465	wtf/opal/x5
    //   469	476	476	wtf/opal/x5
  }
  
  protected void b(Object[] paramArrayOfObject) throws IOException {
    String str = (String)paramArrayOfObject[0];
    this.I.write(str);
  }
  
  protected void C(Object[] paramArrayOfObject) throws IOException {
    String str = (String)paramArrayOfObject[0];
    this.I.write(str);
  }
  
  protected void S(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/l6.b : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 46488123053765
    //   31: lxor
    //   32: lstore #5
    //   34: pop2
    //   35: aload_0
    //   36: getfield I : Ljava/io/Writer;
    //   39: sipush #6971
    //   42: ldc2_w 8497052894885791182
    //   45: lload_2
    //   46: lxor
    //   47: <illegal opcode> h : (IJ)I
    //   52: invokevirtual write : (I)V
    //   55: aload_0
    //   56: aload #4
    //   58: lload #5
    //   60: iconst_2
    //   61: anewarray java/lang/Object
    //   64: dup_x2
    //   65: dup_x2
    //   66: pop
    //   67: invokestatic valueOf : (J)Ljava/lang/Long;
    //   70: iconst_1
    //   71: swap
    //   72: aastore
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual U : ([Ljava/lang/Object;)V
    //   81: aload_0
    //   82: getfield I : Ljava/io/Writer;
    //   85: sipush #6971
    //   88: ldc2_w 8497052894885791182
    //   91: lload_2
    //   92: lxor
    //   93: <illegal opcode> h : (IJ)I
    //   98: invokevirtual write : (I)V
    //   101: return
  }
  
  protected void O(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #30311
    //   25: ldc2_w 173246048493892200
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void s(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #14432
    //   25: ldc2_w 2814514999586073310
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void T(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #27894
    //   25: ldc2_w 2534371938730962864
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void D(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #24902
    //   25: ldc2_w 1923201845859121747
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void t(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #25182
    //   25: ldc2_w 2491626806292998476
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void P(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/l6.b : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 104871508350503
    //   31: lxor
    //   32: lstore #5
    //   34: pop2
    //   35: aload_0
    //   36: getfield I : Ljava/io/Writer;
    //   39: sipush #26545
    //   42: ldc2_w 7362672463617538484
    //   45: lload_2
    //   46: lxor
    //   47: <illegal opcode> h : (IJ)I
    //   52: invokevirtual write : (I)V
    //   55: aload_0
    //   56: aload #4
    //   58: lload #5
    //   60: iconst_2
    //   61: anewarray java/lang/Object
    //   64: dup_x2
    //   65: dup_x2
    //   66: pop
    //   67: invokestatic valueOf : (J)Ljava/lang/Long;
    //   70: iconst_1
    //   71: swap
    //   72: aastore
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual U : ([Ljava/lang/Object;)V
    //   81: aload_0
    //   82: getfield I : Ljava/io/Writer;
    //   85: sipush #6971
    //   88: ldc2_w 8497146463146493228
    //   91: lload_2
    //   92: lxor
    //   93: <illegal opcode> h : (IJ)I
    //   98: invokevirtual write : (I)V
    //   101: return
  }
  
  protected void y(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #24691
    //   25: ldc2_w 4559285585250856861
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void E(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/l6.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: aload_0
    //   19: getfield I : Ljava/io/Writer;
    //   22: sipush #14068
    //   25: ldc2_w 6848541761597737386
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)I
    //   35: invokevirtual write : (I)V
    //   38: return
  }
  
  protected void U(Object[] paramArrayOfObject) throws IOException {
    String str = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x1BEFBAB2796EL;
    int j = str.length();
    int i = lq.O();
    int k = 0;
    byte b = 0;
    while (b < j) {
      new Object[2];
      char[] arrayOfChar = Z(new Object[] { null, Long.valueOf(l2), Integer.valueOf(str.charAt(b)) });
      try {
        if (l1 >= 0L) {
          if (i != 0) {
            try {
              if (l1 > 0L)
                if (i != 0) {
                  if (arrayOfChar != null) {
                    this.I.write(str, k, b - k);
                    this.I.write(arrayOfChar);
                    k = b + 1;
                  } 
                  b++;
                }  
            } catch (IOException iOException) {
              throw a(null);
            } 
            if (i == 0)
              break; 
            continue;
          } 
          return;
        } 
        if (l1 > 0L)
          if (i != 0) {
            if (arrayOfChar != null) {
              this.I.write(str, k, b - k);
              this.I.write(arrayOfChar);
              k = b + 1;
            } 
            b++;
          }  
      } catch (IOException iOException) {
        throw a(null);
      } 
    } 
    this.I.write(str, k, j - k);
    if (l1 > 0L);
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -1832891341703501635
    //   3: ldc2_w 7264545796912626679
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 72513961828644
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/l6.b : J
    //   26: getstatic wtf/opal/l6.b : J
    //   29: ldc2_w 57319025732385
    //   32: lxor
    //   33: lstore #11
    //   35: new java/util/HashMap
    //   38: dup
    //   39: bipush #13
    //   41: invokespecial <init> : (I)V
    //   44: putstatic wtf/opal/l6.e : Ljava/util/Map;
    //   47: ldc 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore_0
    //   54: iconst_2
    //   55: ldc 'DES'
    //   57: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   60: bipush #8
    //   62: newarray byte
    //   64: dup
    //   65: iconst_0
    //   66: lload #11
    //   68: bipush #56
    //   70: lushr
    //   71: l2i
    //   72: i2b
    //   73: bastore
    //   74: iconst_1
    //   75: istore_1
    //   76: iload_1
    //   77: bipush #8
    //   79: if_icmpge -> 103
    //   82: dup
    //   83: iload_1
    //   84: lload #11
    //   86: iload_1
    //   87: bipush #8
    //   89: imul
    //   90: lshl
    //   91: bipush #56
    //   93: lushr
    //   94: l2i
    //   95: i2b
    //   96: bastore
    //   97: iinc #1, 1
    //   100: goto -> 76
    //   103: new javax/crypto/spec/DESKeySpec
    //   106: dup_x1
    //   107: swap
    //   108: invokespecial <init> : ([B)V
    //   111: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   114: new javax/crypto/spec/IvParameterSpec
    //   117: dup
    //   118: bipush #8
    //   120: newarray byte
    //   122: invokespecial <init> : ([B)V
    //   125: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   128: bipush #57
    //   130: newarray long
    //   132: astore #6
    //   134: iconst_0
    //   135: istore_3
    //   136: ldc_w 'ô1úf¤è±@øÐ]DmÑ\\ngüHÜÙÚ]ÅÂÕ{¬?ànòiÆ'Ô$\\fØdV,¼«áWàÄ¬}"-ÔGê\Âß­7¼P¢Cê'hÀûhA¾Á36V×)"ð0Pþ¹ÂNÆàóâ~Âo\\fôqø nr·­!R©Y.|%V`¼~X`æçûÄüôù\\bô,Pj×ÈÎmÝ¨LO\\fFîI8&b]¸%ÊKE2 Oß¿uãÜtPºZ_8¹\\rïÆ@à ü³Ñm÷\\rgÈÐÁP~Ø=cAÑ¼ür\\nkÄÉËZ§z'­S¼¼âô~Ö=A&\\fø ÔD\\nmòÔÝõ±M¢bÞ=iòiIx¯é÷÷Èýùm(9Æ¨\\tÆñâÌÂL^wîlCðòPÇÒóR·l=\\râÂÞo³Ð>Ó|ªÉI°k°pÿ~¾8md°½¨ìì¥©1]­ÔÏIV[kÏ7÷bµdS%Z»ôt2¶æ2#¼á«ã \\n'
    //   139: dup
    //   140: astore #4
    //   142: invokevirtual length : ()I
    //   145: istore #5
    //   147: iconst_0
    //   148: istore_2
    //   149: aload #4
    //   151: iload_2
    //   152: iinc #2, 8
    //   155: iload_2
    //   156: invokevirtual substring : (II)Ljava/lang/String;
    //   159: ldc_w 'ISO-8859-1'
    //   162: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   165: astore #7
    //   167: aload #6
    //   169: iload_3
    //   170: iinc #3, 1
    //   173: aload #7
    //   175: iconst_0
    //   176: baload
    //   177: i2l
    //   178: ldc2_w 255
    //   181: land
    //   182: bipush #56
    //   184: lshl
    //   185: aload #7
    //   187: iconst_1
    //   188: baload
    //   189: i2l
    //   190: ldc2_w 255
    //   193: land
    //   194: bipush #48
    //   196: lshl
    //   197: lor
    //   198: aload #7
    //   200: iconst_2
    //   201: baload
    //   202: i2l
    //   203: ldc2_w 255
    //   206: land
    //   207: bipush #40
    //   209: lshl
    //   210: lor
    //   211: aload #7
    //   213: iconst_3
    //   214: baload
    //   215: i2l
    //   216: ldc2_w 255
    //   219: land
    //   220: bipush #32
    //   222: lshl
    //   223: lor
    //   224: aload #7
    //   226: iconst_4
    //   227: baload
    //   228: i2l
    //   229: ldc2_w 255
    //   232: land
    //   233: bipush #24
    //   235: lshl
    //   236: lor
    //   237: aload #7
    //   239: iconst_5
    //   240: baload
    //   241: i2l
    //   242: ldc2_w 255
    //   245: land
    //   246: bipush #16
    //   248: lshl
    //   249: lor
    //   250: aload #7
    //   252: bipush #6
    //   254: baload
    //   255: i2l
    //   256: ldc2_w 255
    //   259: land
    //   260: bipush #8
    //   262: lshl
    //   263: lor
    //   264: aload #7
    //   266: bipush #7
    //   268: baload
    //   269: i2l
    //   270: ldc2_w 255
    //   273: land
    //   274: lor
    //   275: iconst_m1
    //   276: goto -> 469
    //   279: lastore
    //   280: iload_2
    //   281: iload #5
    //   283: if_icmplt -> 149
    //   286: ldc_w 'ÔJ)#_ømÒ§Åk'
    //   289: dup
    //   290: astore #4
    //   292: invokevirtual length : ()I
    //   295: istore #5
    //   297: iconst_0
    //   298: istore_2
    //   299: aload #4
    //   301: iload_2
    //   302: iinc #2, 8
    //   305: iload_2
    //   306: invokevirtual substring : (II)Ljava/lang/String;
    //   309: ldc_w 'ISO-8859-1'
    //   312: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   315: astore #7
    //   317: aload #6
    //   319: iload_3
    //   320: iinc #3, 1
    //   323: aload #7
    //   325: iconst_0
    //   326: baload
    //   327: i2l
    //   328: ldc2_w 255
    //   331: land
    //   332: bipush #56
    //   334: lshl
    //   335: aload #7
    //   337: iconst_1
    //   338: baload
    //   339: i2l
    //   340: ldc2_w 255
    //   343: land
    //   344: bipush #48
    //   346: lshl
    //   347: lor
    //   348: aload #7
    //   350: iconst_2
    //   351: baload
    //   352: i2l
    //   353: ldc2_w 255
    //   356: land
    //   357: bipush #40
    //   359: lshl
    //   360: lor
    //   361: aload #7
    //   363: iconst_3
    //   364: baload
    //   365: i2l
    //   366: ldc2_w 255
    //   369: land
    //   370: bipush #32
    //   372: lshl
    //   373: lor
    //   374: aload #7
    //   376: iconst_4
    //   377: baload
    //   378: i2l
    //   379: ldc2_w 255
    //   382: land
    //   383: bipush #24
    //   385: lshl
    //   386: lor
    //   387: aload #7
    //   389: iconst_5
    //   390: baload
    //   391: i2l
    //   392: ldc2_w 255
    //   395: land
    //   396: bipush #16
    //   398: lshl
    //   399: lor
    //   400: aload #7
    //   402: bipush #6
    //   404: baload
    //   405: i2l
    //   406: ldc2_w 255
    //   409: land
    //   410: bipush #8
    //   412: lshl
    //   413: lor
    //   414: aload #7
    //   416: bipush #7
    //   418: baload
    //   419: i2l
    //   420: ldc2_w 255
    //   423: land
    //   424: lor
    //   425: iconst_0
    //   426: goto -> 469
    //   429: lastore
    //   430: iload_2
    //   431: iload #5
    //   433: if_icmplt -> 299
    //   436: aload #6
    //   438: putstatic wtf/opal/l6.c : [J
    //   441: bipush #57
    //   443: anewarray java/lang/Integer
    //   446: putstatic wtf/opal/l6.d : [Ljava/lang/Integer;
    //   449: sipush #30195
    //   452: lload #11
    //   454: ldc2_w 849942106473675386
    //   457: lxor
    //   458: <illegal opcode> h : (IJ)I
    //   463: putstatic wtf/opal/l6.J : I
    //   466: goto -> 684
    //   469: dup_x2
    //   470: pop
    //   471: lstore #8
    //   473: bipush #8
    //   475: newarray byte
    //   477: dup
    //   478: iconst_0
    //   479: lload #8
    //   481: bipush #56
    //   483: lushr
    //   484: l2i
    //   485: i2b
    //   486: bastore
    //   487: dup
    //   488: iconst_1
    //   489: lload #8
    //   491: bipush #48
    //   493: lushr
    //   494: l2i
    //   495: i2b
    //   496: bastore
    //   497: dup
    //   498: iconst_2
    //   499: lload #8
    //   501: bipush #40
    //   503: lushr
    //   504: l2i
    //   505: i2b
    //   506: bastore
    //   507: dup
    //   508: iconst_3
    //   509: lload #8
    //   511: bipush #32
    //   513: lushr
    //   514: l2i
    //   515: i2b
    //   516: bastore
    //   517: dup
    //   518: iconst_4
    //   519: lload #8
    //   521: bipush #24
    //   523: lushr
    //   524: l2i
    //   525: i2b
    //   526: bastore
    //   527: dup
    //   528: iconst_5
    //   529: lload #8
    //   531: bipush #16
    //   533: lushr
    //   534: l2i
    //   535: i2b
    //   536: bastore
    //   537: dup
    //   538: bipush #6
    //   540: lload #8
    //   542: bipush #8
    //   544: lushr
    //   545: l2i
    //   546: i2b
    //   547: bastore
    //   548: dup
    //   549: bipush #7
    //   551: lload #8
    //   553: l2i
    //   554: i2b
    //   555: bastore
    //   556: aload_0
    //   557: swap
    //   558: invokevirtual doFinal : ([B)[B
    //   561: astore #10
    //   563: aload #10
    //   565: iconst_0
    //   566: baload
    //   567: i2l
    //   568: ldc2_w 255
    //   571: land
    //   572: bipush #56
    //   574: lshl
    //   575: aload #10
    //   577: iconst_1
    //   578: baload
    //   579: i2l
    //   580: ldc2_w 255
    //   583: land
    //   584: bipush #48
    //   586: lshl
    //   587: lor
    //   588: aload #10
    //   590: iconst_2
    //   591: baload
    //   592: i2l
    //   593: ldc2_w 255
    //   596: land
    //   597: bipush #40
    //   599: lshl
    //   600: lor
    //   601: aload #10
    //   603: iconst_3
    //   604: baload
    //   605: i2l
    //   606: ldc2_w 255
    //   609: land
    //   610: bipush #32
    //   612: lshl
    //   613: lor
    //   614: aload #10
    //   616: iconst_4
    //   617: baload
    //   618: i2l
    //   619: ldc2_w 255
    //   622: land
    //   623: bipush #24
    //   625: lshl
    //   626: lor
    //   627: aload #10
    //   629: iconst_5
    //   630: baload
    //   631: i2l
    //   632: ldc2_w 255
    //   635: land
    //   636: bipush #16
    //   638: lshl
    //   639: lor
    //   640: aload #10
    //   642: bipush #6
    //   644: baload
    //   645: i2l
    //   646: ldc2_w 255
    //   649: land
    //   650: bipush #8
    //   652: lshl
    //   653: lor
    //   654: aload #10
    //   656: bipush #7
    //   658: baload
    //   659: i2l
    //   660: ldc2_w 255
    //   663: land
    //   664: lor
    //   665: dup2_x1
    //   666: pop2
    //   667: tableswitch default -> 279, 0 -> 429
    //   684: iconst_2
    //   685: newarray char
    //   687: dup
    //   688: iconst_0
    //   689: sipush #30663
    //   692: ldc2_w 6435990853972113479
    //   695: lload #11
    //   697: lxor
    //   698: <illegal opcode> h : (IJ)I
    //   703: castore
    //   704: dup
    //   705: iconst_1
    //   706: sipush #6971
    //   709: ldc2_w 8497153328833778876
    //   712: lload #11
    //   714: lxor
    //   715: <illegal opcode> h : (IJ)I
    //   720: castore
    //   721: putstatic wtf/opal/l6.x : [C
    //   724: iconst_2
    //   725: newarray char
    //   727: dup
    //   728: iconst_0
    //   729: sipush #30663
    //   732: ldc2_w 6435990853972113479
    //   735: lload #11
    //   737: lxor
    //   738: <illegal opcode> h : (IJ)I
    //   743: castore
    //   744: dup
    //   745: iconst_1
    //   746: sipush #30663
    //   749: ldc2_w 6435990853972113479
    //   752: lload #11
    //   754: lxor
    //   755: <illegal opcode> h : (IJ)I
    //   760: castore
    //   761: putstatic wtf/opal/l6.s : [C
    //   764: iconst_2
    //   765: newarray char
    //   767: dup
    //   768: iconst_0
    //   769: sipush #30663
    //   772: ldc2_w 6435990853972113479
    //   775: lload #11
    //   777: lxor
    //   778: <illegal opcode> h : (IJ)I
    //   783: castore
    //   784: dup
    //   785: iconst_1
    //   786: sipush #10411
    //   789: ldc2_w 6484761627965581087
    //   792: lload #11
    //   794: lxor
    //   795: <illegal opcode> h : (IJ)I
    //   800: castore
    //   801: putstatic wtf/opal/l6.k : [C
    //   804: iconst_2
    //   805: newarray char
    //   807: dup
    //   808: iconst_0
    //   809: sipush #30663
    //   812: ldc2_w 6435990853972113479
    //   815: lload #11
    //   817: lxor
    //   818: <illegal opcode> h : (IJ)I
    //   823: castore
    //   824: dup
    //   825: iconst_1
    //   826: sipush #8210
    //   829: ldc2_w 5664825830040924048
    //   832: lload #11
    //   834: lxor
    //   835: <illegal opcode> h : (IJ)I
    //   840: castore
    //   841: putstatic wtf/opal/l6.Y : [C
    //   844: iconst_2
    //   845: newarray char
    //   847: dup
    //   848: iconst_0
    //   849: sipush #30663
    //   852: ldc2_w 6435990853972113479
    //   855: lload #11
    //   857: lxor
    //   858: <illegal opcode> h : (IJ)I
    //   863: castore
    //   864: dup
    //   865: iconst_1
    //   866: sipush #23641
    //   869: ldc2_w 3533554213052027869
    //   872: lload #11
    //   874: lxor
    //   875: <illegal opcode> h : (IJ)I
    //   880: castore
    //   881: putstatic wtf/opal/l6.L : [C
    //   884: sipush #14980
    //   887: ldc2_w 6060712307854832907
    //   890: lload #11
    //   892: lxor
    //   893: <illegal opcode> h : (IJ)I
    //   898: newarray char
    //   900: dup
    //   901: iconst_0
    //   902: sipush #30663
    //   905: ldc2_w 6435990853972113479
    //   908: lload #11
    //   910: lxor
    //   911: <illegal opcode> h : (IJ)I
    //   916: castore
    //   917: dup
    //   918: iconst_1
    //   919: sipush #10155
    //   922: ldc2_w 362047615320483843
    //   925: lload #11
    //   927: lxor
    //   928: <illegal opcode> h : (IJ)I
    //   933: castore
    //   934: dup
    //   935: iconst_2
    //   936: sipush #6891
    //   939: ldc2_w 1662263300695905627
    //   942: lload #11
    //   944: lxor
    //   945: <illegal opcode> h : (IJ)I
    //   950: castore
    //   951: dup
    //   952: iconst_3
    //   953: sipush #24175
    //   956: ldc2_w 9019273455313326574
    //   959: lload #11
    //   961: lxor
    //   962: <illegal opcode> h : (IJ)I
    //   967: castore
    //   968: dup
    //   969: iconst_4
    //   970: sipush #21397
    //   973: ldc2_w 3563477186046925876
    //   976: lload #11
    //   978: lxor
    //   979: <illegal opcode> h : (IJ)I
    //   984: castore
    //   985: dup
    //   986: iconst_5
    //   987: sipush #11729
    //   990: ldc2_w 2052965430098420340
    //   993: lload #11
    //   995: lxor
    //   996: <illegal opcode> h : (IJ)I
    //   1001: castore
    //   1002: putstatic wtf/opal/l6.q : [C
    //   1005: sipush #14980
    //   1008: ldc2_w 6060712307854832907
    //   1011: lload #11
    //   1013: lxor
    //   1014: <illegal opcode> h : (IJ)I
    //   1019: newarray char
    //   1021: dup
    //   1022: iconst_0
    //   1023: sipush #30663
    //   1026: ldc2_w 6435990853972113479
    //   1029: lload #11
    //   1031: lxor
    //   1032: <illegal opcode> h : (IJ)I
    //   1037: castore
    //   1038: dup
    //   1039: iconst_1
    //   1040: sipush #10155
    //   1043: ldc2_w 362047615320483843
    //   1046: lload #11
    //   1048: lxor
    //   1049: <illegal opcode> h : (IJ)I
    //   1054: castore
    //   1055: dup
    //   1056: iconst_2
    //   1057: sipush #21397
    //   1060: ldc2_w 3563477186046925876
    //   1063: lload #11
    //   1065: lxor
    //   1066: <illegal opcode> h : (IJ)I
    //   1071: castore
    //   1072: dup
    //   1073: iconst_3
    //   1074: sipush #24175
    //   1077: ldc2_w 9019273455313326574
    //   1080: lload #11
    //   1082: lxor
    //   1083: <illegal opcode> h : (IJ)I
    //   1088: castore
    //   1089: dup
    //   1090: iconst_4
    //   1091: sipush #21397
    //   1094: ldc2_w 3563477186046925876
    //   1097: lload #11
    //   1099: lxor
    //   1100: <illegal opcode> h : (IJ)I
    //   1105: castore
    //   1106: dup
    //   1107: iconst_5
    //   1108: sipush #1022
    //   1111: ldc2_w 7121709962312731733
    //   1114: lload #11
    //   1116: lxor
    //   1117: <illegal opcode> h : (IJ)I
    //   1122: castore
    //   1123: putstatic wtf/opal/l6.Q : [C
    //   1126: sipush #20127
    //   1129: ldc2_w 4846998755942433078
    //   1132: lload #11
    //   1134: lxor
    //   1135: <illegal opcode> h : (IJ)I
    //   1140: newarray char
    //   1142: dup
    //   1143: iconst_0
    //   1144: sipush #24175
    //   1147: ldc2_w 9019273455313326574
    //   1150: lload #11
    //   1152: lxor
    //   1153: <illegal opcode> h : (IJ)I
    //   1158: castore
    //   1159: dup
    //   1160: iconst_1
    //   1161: sipush #16562
    //   1164: ldc2_w 5297048546534297404
    //   1167: lload #11
    //   1169: lxor
    //   1170: <illegal opcode> h : (IJ)I
    //   1175: castore
    //   1176: dup
    //   1177: iconst_2
    //   1178: sipush #21397
    //   1181: ldc2_w 3563477186046925876
    //   1184: lload #11
    //   1186: lxor
    //   1187: <illegal opcode> h : (IJ)I
    //   1192: castore
    //   1193: dup
    //   1194: iconst_3
    //   1195: sipush #15936
    //   1198: ldc2_w 7016609559234441669
    //   1201: lload #11
    //   1203: lxor
    //   1204: <illegal opcode> h : (IJ)I
    //   1209: castore
    //   1210: dup
    //   1211: iconst_4
    //   1212: sipush #30175
    //   1215: ldc2_w 1245351976317041275
    //   1218: lload #11
    //   1220: lxor
    //   1221: <illegal opcode> h : (IJ)I
    //   1226: castore
    //   1227: dup
    //   1228: iconst_5
    //   1229: sipush #30551
    //   1232: ldc2_w 3314150749833312468
    //   1235: lload #11
    //   1237: lxor
    //   1238: <illegal opcode> h : (IJ)I
    //   1243: castore
    //   1244: dup
    //   1245: sipush #14980
    //   1248: ldc2_w 6060712307854832907
    //   1251: lload #11
    //   1253: lxor
    //   1254: <illegal opcode> h : (IJ)I
    //   1259: sipush #8282
    //   1262: ldc2_w 6410466323116650436
    //   1265: lload #11
    //   1267: lxor
    //   1268: <illegal opcode> h : (IJ)I
    //   1273: castore
    //   1274: dup
    //   1275: sipush #31414
    //   1278: ldc2_w 7239187321469154598
    //   1281: lload #11
    //   1283: lxor
    //   1284: <illegal opcode> h : (IJ)I
    //   1289: sipush #18328
    //   1292: ldc2_w 5196490777807489054
    //   1295: lload #11
    //   1297: lxor
    //   1298: <illegal opcode> h : (IJ)I
    //   1303: castore
    //   1304: dup
    //   1305: sipush #25587
    //   1308: ldc2_w 3174096451226971230
    //   1311: lload #11
    //   1313: lxor
    //   1314: <illegal opcode> h : (IJ)I
    //   1319: sipush #17179
    //   1322: ldc2_w 4558073667881241744
    //   1325: lload #11
    //   1327: lxor
    //   1328: <illegal opcode> h : (IJ)I
    //   1333: castore
    //   1334: dup
    //   1335: sipush #3972
    //   1338: ldc2_w 5571879261753458727
    //   1341: lload #11
    //   1343: lxor
    //   1344: <illegal opcode> h : (IJ)I
    //   1349: sipush #7468
    //   1352: ldc2_w 1014580526293635768
    //   1355: lload #11
    //   1357: lxor
    //   1358: <illegal opcode> h : (IJ)I
    //   1363: castore
    //   1364: dup
    //   1365: sipush #25351
    //   1368: ldc2_w 8467867608007355540
    //   1371: lload #11
    //   1373: lxor
    //   1374: <illegal opcode> h : (IJ)I
    //   1379: sipush #22351
    //   1382: ldc2_w 3895198965674678520
    //   1385: lload #11
    //   1387: lxor
    //   1388: <illegal opcode> h : (IJ)I
    //   1393: castore
    //   1394: dup
    //   1395: sipush #26693
    //   1398: ldc2_w 4924254851161335763
    //   1401: lload #11
    //   1403: lxor
    //   1404: <illegal opcode> h : (IJ)I
    //   1409: sipush #27426
    //   1412: ldc2_w 7738649997525329072
    //   1415: lload #11
    //   1417: lxor
    //   1418: <illegal opcode> h : (IJ)I
    //   1423: castore
    //   1424: dup
    //   1425: sipush #24692
    //   1428: ldc2_w 5731656481441034211
    //   1431: lload #11
    //   1433: lxor
    //   1434: <illegal opcode> h : (IJ)I
    //   1439: sipush #14778
    //   1442: ldc2_w 6327196779105252912
    //   1445: lload #11
    //   1447: lxor
    //   1448: <illegal opcode> h : (IJ)I
    //   1453: castore
    //   1454: dup
    //   1455: sipush #3685
    //   1458: ldc2_w 7813853867682251202
    //   1461: lload #11
    //   1463: lxor
    //   1464: <illegal opcode> h : (IJ)I
    //   1469: sipush #13974
    //   1472: ldc2_w 4420109388102531364
    //   1475: lload #11
    //   1477: lxor
    //   1478: <illegal opcode> h : (IJ)I
    //   1483: castore
    //   1484: dup
    //   1485: sipush #15130
    //   1488: ldc2_w 126812559241409671
    //   1491: lload #11
    //   1493: lxor
    //   1494: <illegal opcode> h : (IJ)I
    //   1499: sipush #6234
    //   1502: ldc2_w 1317144406497253313
    //   1505: lload #11
    //   1507: lxor
    //   1508: <illegal opcode> h : (IJ)I
    //   1513: castore
    //   1514: dup
    //   1515: sipush #5634
    //   1518: ldc2_w 1748574142869199272
    //   1521: lload #11
    //   1523: lxor
    //   1524: <illegal opcode> h : (IJ)I
    //   1529: sipush #6969
    //   1532: ldc2_w 3722670204530644145
    //   1535: lload #11
    //   1537: lxor
    //   1538: <illegal opcode> h : (IJ)I
    //   1543: castore
    //   1544: putstatic wtf/opal/l6.a : [C
    //   1547: return
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3293;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])e.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/l6", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      d[i] = Integer.valueOf(j);
    } 
    return d[i].intValue();
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
    //   65: ldc_w 'wtf/opal/l6'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */