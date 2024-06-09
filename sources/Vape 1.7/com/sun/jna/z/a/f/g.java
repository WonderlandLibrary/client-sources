package com.sun.jna.z.a.f;

import com.sun.jna.z.a.*;
import java.nio.*;
import java.util.zip.*;
import java.io.*;

public class g
{
    private final byte[] a;
    private final int b = 1229472850;
    private final int c = 1347179589;
    private final int d = 1951551059;
    private final int e = 1229209940;
    private final int f = 1229278788;
    private final byte g = 0;
    private final byte h = 2;
    private final byte i = 3;
    private final byte j = 4;
    private final byte k = 6;
    private final InputStream l;
    private final CRC32 m;
    private final byte[] n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private byte[] w;
    private byte[] x;
    private byte[] y;
    static final boolean z;
    private static final String[] A;
    
    public g(final InputStream a) throws IOException {
        this.a = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
        final int b = com.sun.jna.z.a.f.f.b ? 1 : 0;
        final int a2 = b;
        g g = null;
        Label_0201: {
            Label_0172: {
                try {
                    this.l = a;
                    this.m = new CRC32();
                    this.b(this.n = new byte[4096], 0, this.a.length);
                    g = this;
                    if (a2 != 0) {
                        break Label_0201;
                    }
                    final g g2 = this;
                    final byte[] array = g2.n;
                    final boolean b2 = this.b(array);
                    if (!b2) {
                        break Label_0172;
                    }
                    break Label_0172;
                }
                catch (IOException ex) {
                    throw ex;
                }
                try {
                    final g g2 = this;
                    final byte[] array = g2.n;
                    final boolean b2 = this.b(array);
                    if (!b2) {
                        final String[] a3 = com.sun.jna.z.a.f.g.A;
                        throw new IOException(a3[5]);
                    }
                }
                catch (IOException ex2) {
                    throw ex2;
                }
            }
            this.a(1229472850);
            this.f();
            g = this;
        }
        g.i();
        Label_0322: {
            g g3 = null;
            Label_0317: {
                Label_0301: {
                    do {
                        this.j();
                        Block_3: {
                            while (true) {
                                switch (this.p) {
                                    case 1229209940: {
                                        if (a2 != 0) {
                                            break Block_3;
                                        }
                                        break Label_0301;
                                    }
                                    case 1347179589: {
                                        Label_0263: {
                                            break Label_0263;
                                            int a4 = com.sun.jna.z.a.i.g;
                                            com.sun.jna.z.a.i.g = ++a4;
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            try {
                                                                                try {
                                                                                    try {
                                                                                        try {
                                                                                            try {
                                                                                                try {
                                                                                                    try {
                                                                                                        this.g();
                                                                                                        if (a2 != 0) {
                                                                                                            this.h();
                                                                                                            break;
                                                                                                        }
                                                                                                        break;
                                                                                                    }
                                                                                                    catch (IllegalArgumentException ex3) {
                                                                                                        throw ex3;
                                                                                                    }
                                                                                                }
                                                                                                catch (UnsupportedOperationException ex4) {
                                                                                                    throw ex4;
                                                                                                }
                                                                                            }
                                                                                            catch (IllegalArgumentException ex5) {
                                                                                                throw ex5;
                                                                                            }
                                                                                        }
                                                                                        catch (UnsupportedOperationException ex6) {
                                                                                            throw ex6;
                                                                                        }
                                                                                    }
                                                                                    catch (IllegalArgumentException ex7) {
                                                                                        throw ex7;
                                                                                    }
                                                                                }
                                                                                catch (UnsupportedOperationException ex8) {
                                                                                    throw ex8;
                                                                                }
                                                                            }
                                                                            catch (IllegalArgumentException ex9) {
                                                                                throw ex9;
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex10) {
                                                                            throw ex10;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex11) {
                                                                        throw ex11;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex12) {
                                                                    throw ex12;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex13) {
                                                                throw ex13;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex14) {
                                                            throw ex14;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex15) {
                                                        throw ex15;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex16) {
                                                    throw ex16;
                                                }
                                            }
                                            catch (IOException ex17) {
                                                throw ex17;
                                            }
                                        }
                                        break;
                                    }
                                    case 1951551059: {
                                        continue;
                                    }
                                }
                                break;
                            }
                        }
                        this.i();
                    } while (a2 == 0);
                    try {
                        g3 = this;
                        if (a2 != 0) {
                            break Label_0322;
                        }
                        final int n = this.u;
                        final int n2 = 3;
                        if (n == n2) {
                            break Label_0317;
                        }
                        return;
                    }
                    catch (IOException ex18) {
                        throw ex18;
                    }
                }
                try {
                    final int n = this.u;
                    final int n2 = 3;
                    if (n != n2) {
                        return;
                    }
                    g3 = this;
                }
                catch (IOException ex19) {
                    throw ex19;
                }
            }
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        if (g3.w == null) {
                                                                            throw new IOException(com.sun.jna.z.a.f.g.A[0]);
                                                                        }
                                                                    }
                                                                    catch (IOException ex20) {
                                                                        throw ex20;
                                                                    }
                                                                }
                                                                catch (IllegalArgumentException ex21) {
                                                                    throw ex21;
                                                                }
                                                            }
                                                            catch (IOException ex22) {
                                                                throw ex22;
                                                            }
                                                        }
                                                        catch (IllegalArgumentException ex23) {
                                                            throw ex23;
                                                        }
                                                    }
                                                    catch (IOException ex24) {
                                                        throw ex24;
                                                    }
                                                }
                                                catch (IllegalArgumentException ex25) {
                                                    throw ex25;
                                                }
                                            }
                                            catch (IOException ex26) {
                                                throw ex26;
                                            }
                                        }
                                        catch (IllegalArgumentException ex27) {
                                            throw ex27;
                                        }
                                    }
                                    catch (IOException ex28) {
                                        throw ex28;
                                    }
                                }
                                catch (IllegalArgumentException ex29) {
                                    throw ex29;
                                }
                            }
                            catch (IOException ex30) {
                                throw ex30;
                            }
                        }
                        catch (IllegalArgumentException ex31) {
                            throw ex31;
                        }
                    }
                    catch (IOException ex32) {
                        throw ex32;
                    }
                }
                catch (UnsupportedOperationException ex33) {
                    throw ex33;
                }
            }
            catch (IOException ex34) {
                throw ex34;
            }
        }
    }
    
    public int a() {
        return this.s;
    }
    
    public int b() {
        return this.r;
    }
    
    public boolean c() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.u:I
        //     8: iload_1         /* a */
        //     9: ifne            46
        //    12: bipush          6
        //    14: if_icmpeq       41
        //    17: goto            21
        //    20: athrow         
        //    21: aload_0         /* a */
        //    22: getfield        com/sun/jna/z/a/f/g.u:I
        //    25: iload_1         /* a */
        //    26: ifne            46
        //    29: goto            33
        //    32: athrow         
        //    33: iconst_4       
        //    34: if_icmpne       49
        //    37: goto            41
        //    40: athrow         
        //    41: iconst_1       
        //    42: goto            46
        //    45: athrow         
        //    46: goto            50
        //    49: iconst_0       
        //    50: ireturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      17     20     21     Ljava/lang/UnsupportedOperationException;
        //  12     29     32     33     Ljava/lang/UnsupportedOperationException;
        //  21     37     40     41     Ljava/lang/UnsupportedOperationException;
        //  33     42     45     46     Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0021:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean d() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: invokevirtual   com/sun/jna/z/a/f/g.c:()Z
        //     8: iload_1         /* a */
        //     9: ifne            50
        //    12: ifne            49
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: getfield        com/sun/jna/z/a/f/g.x:[B
        //    23: iload_1         /* a */
        //    24: ifne            46
        //    27: goto            31
        //    30: athrow         
        //    31: ifnonnull       49
        //    34: goto            38
        //    37: athrow         
        //    38: aload_0         /* a */
        //    39: getfield        com/sun/jna/z/a/f/g.y:[B
        //    42: goto            46
        //    45: athrow         
        //    46: ifnull          53
        //    49: iconst_1       
        //    50: goto            54
        //    53: iconst_0       
        //    54: ireturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      15     18     19     Ljava/lang/UnsupportedOperationException;
        //  12     27     30     31     Ljava/lang/UnsupportedOperationException;
        //  19     34     37     38     Ljava/lang/UnsupportedOperationException;
        //  31     42     45     46     Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0019:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean e() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.u:I
        //     8: iload_1         /* a */
        //     9: ifne            66
        //    12: bipush          6
        //    14: if_icmpeq       61
        //    17: goto            21
        //    20: athrow         
        //    21: aload_0         /* a */
        //    22: getfield        com/sun/jna/z/a/f/g.u:I
        //    25: iload_1         /* a */
        //    26: ifne            66
        //    29: goto            33
        //    32: athrow         
        //    33: iconst_2       
        //    34: if_icmpeq       61
        //    37: goto            41
        //    40: athrow         
        //    41: aload_0         /* a */
        //    42: getfield        com/sun/jna/z/a/f/g.u:I
        //    45: iload_1         /* a */
        //    46: ifne            66
        //    49: goto            53
        //    52: athrow         
        //    53: iconst_3       
        //    54: if_icmpne       69
        //    57: goto            61
        //    60: athrow         
        //    61: iconst_1       
        //    62: goto            66
        //    65: athrow         
        //    66: goto            70
        //    69: iconst_0       
        //    70: ireturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      17     20     21     Ljava/lang/UnsupportedOperationException;
        //  12     29     32     33     Ljava/lang/UnsupportedOperationException;
        //  21     37     40     41     Ljava/lang/UnsupportedOperationException;
        //  33     49     52     53     Ljava/lang/UnsupportedOperationException;
        //  41     57     60     61     Ljava/lang/UnsupportedOperationException;
        //  53     62     65     66     Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0021:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void a(final byte a, final byte a, final byte a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          a
        //     5: aload_0         /* a */
        //     6: iload           a
        //     8: ifne            37
        //    11: invokevirtual   com/sun/jna/z/a/f/g.c:()Z
        //    14: ifeq            36
        //    17: goto            21
        //    20: athrow         
        //    21: new             Ljava/lang/UnsupportedOperationException;
        //    24: dup            
        //    25: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //    28: bipush          10
        //    30: aaload         
        //    31: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //    34: athrow         
        //    35: athrow         
        //    36: aload_0         /* a */
        //    37: getfield        com/sun/jna/z/a/f/g.w:[B
        //    40: astore          a
        //    42: iload           a
        //    44: ifne            112
        //    47: aload           a
        //    49: ifnonnull       97
        //    52: goto            56
        //    55: athrow         
        //    56: aload_0         /* a */
        //    57: bipush          6
        //    59: newarray        B
        //    61: dup            
        //    62: iconst_0       
        //    63: iconst_0       
        //    64: bastore        
        //    65: dup            
        //    66: iconst_1       
        //    67: iload_1         /* a */
        //    68: bastore        
        //    69: dup            
        //    70: iconst_2       
        //    71: iconst_0       
        //    72: bastore        
        //    73: dup            
        //    74: iconst_3       
        //    75: iload_2         /* a */
        //    76: bastore        
        //    77: dup            
        //    78: iconst_4       
        //    79: iconst_0       
        //    80: bastore        
        //    81: dup            
        //    82: iconst_5       
        //    83: iload_3         /* a */
        //    84: bastore        
        //    85: putfield        com/sun/jna/z/a/f/g.y:[B
        //    88: iload           a
        //    90: ifeq            215
        //    93: goto            97
        //    96: athrow         
        //    97: aload_0         /* a */
        //    98: aload           a
        //   100: arraylength    
        //   101: iconst_3       
        //   102: idiv           
        //   103: newarray        B
        //   105: putfield        com/sun/jna/z/a/f/g.x:[B
        //   108: goto            112
        //   111: athrow         
        //   112: iconst_0       
        //   113: istore          a
        //   115: iconst_0       
        //   116: istore          a
        //   118: iload           a
        //   120: aload           a
        //   122: arraylength    
        //   123: if_icmpge       215
        //   126: aload           a
        //   128: iload           a
        //   130: iload           a
        //   132: ifne            202
        //   135: baload         
        //   136: iload_1         /* a */
        //   137: if_icmpne       192
        //   140: goto            144
        //   143: athrow         
        //   144: aload           a
        //   146: iload           a
        //   148: iconst_1       
        //   149: iadd           
        //   150: iload           a
        //   152: ifne            202
        //   155: goto            159
        //   158: athrow         
        //   159: baload         
        //   160: iload_2         /* a */
        //   161: if_icmpne       192
        //   164: goto            168
        //   167: athrow         
        //   168: aload           a
        //   170: iload           a
        //   172: iconst_2       
        //   173: iadd           
        //   174: iload           a
        //   176: ifne            202
        //   179: goto            183
        //   182: athrow         
        //   183: baload         
        //   184: iload_3         /* a */
        //   185: if_icmpeq       204
        //   188: goto            192
        //   191: athrow         
        //   192: aload_0         /* a */
        //   193: getfield        com/sun/jna/z/a/f/g.x:[B
        //   196: iload           a
        //   198: goto            202
        //   201: athrow         
        //   202: iconst_m1      
        //   203: bastore        
        //   204: iinc            a, 3
        //   207: iinc            a, 1
        //   210: iload           a
        //   212: ifeq            118
        //   215: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  5      17     20     21     Ljava/lang/UnsupportedOperationException;
        //  11     35     35     36     Ljava/lang/UnsupportedOperationException;
        //  42     52     55     56     Ljava/lang/UnsupportedOperationException;
        //  47     93     96     97     Ljava/lang/UnsupportedOperationException;
        //  56     108    111    112    Ljava/lang/UnsupportedOperationException;
        //  126    140    143    144    Ljava/lang/UnsupportedOperationException;
        //  135    155    158    159    Ljava/lang/UnsupportedOperationException;
        //  144    164    167    168    Ljava/lang/UnsupportedOperationException;
        //  159    179    182    183    Ljava/lang/UnsupportedOperationException;
        //  168    188    191    192    Ljava/lang/UnsupportedOperationException;
        //  183    198    201    202    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0056:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public a a(final a a) {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        Label_0241: {
            Label_0181: {
                Label_0121: {
                    Label_0069: {
                        int u = 0;
                        Label_0057: {
                            int n;
                            try {
                                n = (u = this.u);
                                if (a2 != 0) {
                                    break Label_0069;
                                }
                                switch (n) {
                                    case 2: {
                                        break Label_0057;
                                        break Label_0057;
                                    }
                                    case 6: {
                                        break Label_0121;
                                    }
                                    case 0: {
                                        break Label_0181;
                                    }
                                    case 4: {
                                        return a.LUMINANCE_ALPHA;
                                    }
                                    case 3: {
                                        break Label_0241;
                                    }
                                    default: {
                                        throw new UnsupportedOperationException(com.sun.jna.z.a.f.g.A[9]);
                                    }
                                }
                            }
                            catch (UnsupportedOperationException ex) {
                                throw ex;
                            }
                            try {
                                switch (n) {
                                    case 2: {
                                        u = com.sun.jna.z.a.f.h.a[a.ordinal()];
                                        break;
                                    }
                                    case 6: {
                                        break Label_0121;
                                    }
                                    case 0: {
                                        break Label_0181;
                                    }
                                    case 4: {
                                        return a.LUMINANCE_ALPHA;
                                    }
                                    case 3: {
                                        break Label_0241;
                                    }
                                    default: {
                                        throw new UnsupportedOperationException(com.sun.jna.z.a.f.g.A[9]);
                                    }
                                }
                            }
                            catch (UnsupportedOperationException ex2) {
                                throw ex2;
                            }
                        }
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            try {
                                                                                try {
                                                                                    switch (u) {
                                                                                        case 1:
                                                                                        case 2:
                                                                                        case 3:
                                                                                        case 4: {
                                                                                            return a;
                                                                                        }
                                                                                        default: {
                                                                                            return a.RGB;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                catch (UnsupportedOperationException ex3) {
                                                                                    throw ex3;
                                                                                }
                                                                            }
                                                                            catch (IllegalArgumentException ex4) {
                                                                                throw ex4;
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex5) {
                                                                            throw ex5;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex6) {
                                                                        throw ex6;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex7) {
                                                                    throw ex7;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex8) {
                                                                throw ex8;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex9) {
                                                            throw ex9;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex10) {
                                                        throw ex10;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex11) {
                                                    throw ex11;
                                                }
                                            }
                                            catch (IllegalArgumentException ex12) {
                                                throw ex12;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex13) {
                                            throw ex13;
                                        }
                                    }
                                    catch (IllegalArgumentException ex14) {
                                        throw ex14;
                                    }
                                }
                                catch (UnsupportedOperationException ex15) {
                                    throw ex15;
                                }
                            }
                            catch (IllegalArgumentException ex16) {
                                throw ex16;
                            }
                        }
                        catch (UnsupportedOperationException ex17) {
                            throw ex17;
                        }
                    }
                    return a.RGB;
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            try {
                                                                                switch (com.sun.jna.z.a.f.h.a[a.ordinal()]) {
                                                                                    case 1:
                                                                                    case 2:
                                                                                    case 3:
                                                                                    case 4: {
                                                                                        return a;
                                                                                    }
                                                                                    default: {
                                                                                        return a.RGBA;
                                                                                    }
                                                                                }
                                                                            }
                                                                            catch (UnsupportedOperationException ex18) {
                                                                                throw ex18;
                                                                            }
                                                                        }
                                                                        catch (IllegalArgumentException ex19) {
                                                                            throw ex19;
                                                                        }
                                                                    }
                                                                    catch (UnsupportedOperationException ex20) {
                                                                        throw ex20;
                                                                    }
                                                                }
                                                                catch (IllegalArgumentException ex21) {
                                                                    throw ex21;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex22) {
                                                                throw ex22;
                                                            }
                                                        }
                                                        catch (IllegalArgumentException ex23) {
                                                            throw ex23;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex24) {
                                                        throw ex24;
                                                    }
                                                }
                                                catch (IllegalArgumentException ex25) {
                                                    throw ex25;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex26) {
                                                throw ex26;
                                            }
                                        }
                                        catch (IllegalArgumentException ex27) {
                                            throw ex27;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex28) {
                                        throw ex28;
                                    }
                                }
                                catch (IllegalArgumentException ex29) {
                                    throw ex29;
                                }
                            }
                            catch (UnsupportedOperationException ex30) {
                                throw ex30;
                            }
                        }
                        catch (IllegalArgumentException ex31) {
                            throw ex31;
                        }
                    }
                    catch (UnsupportedOperationException ex32) {
                        throw ex32;
                    }
                }
                return a.RGBA;
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            switch (com.sun.jna.z.a.f.h.a[a.ordinal()]) {
                                                                                case 5:
                                                                                case 6: {
                                                                                    return a;
                                                                                }
                                                                                default: {
                                                                                    return a.LUMINANCE;
                                                                                }
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex33) {
                                                                            throw ex33;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex34) {
                                                                        throw ex34;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex35) {
                                                                    throw ex35;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex36) {
                                                                throw ex36;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex37) {
                                                            throw ex37;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex38) {
                                                        throw ex38;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex39) {
                                                    throw ex39;
                                                }
                                            }
                                            catch (IllegalArgumentException ex40) {
                                                throw ex40;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex41) {
                                            throw ex41;
                                        }
                                    }
                                    catch (IllegalArgumentException ex42) {
                                        throw ex42;
                                    }
                                }
                                catch (UnsupportedOperationException ex43) {
                                    throw ex43;
                                }
                            }
                            catch (IllegalArgumentException ex44) {
                                throw ex44;
                            }
                        }
                        catch (UnsupportedOperationException ex45) {
                            throw ex45;
                        }
                    }
                    catch (IllegalArgumentException ex46) {
                        throw ex46;
                    }
                }
                catch (UnsupportedOperationException ex47) {
                    throw ex47;
                }
            }
            return a.LUMINANCE;
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        switch (com.sun.jna.z.a.f.h.a[a.ordinal()]) {
                                                                            case 1:
                                                                            case 2:
                                                                            case 3: {
                                                                                return a;
                                                                            }
                                                                            default: {
                                                                                return a.RGBA;
                                                                            }
                                                                        }
                                                                    }
                                                                    catch (UnsupportedOperationException ex48) {
                                                                        throw ex48;
                                                                    }
                                                                }
                                                                catch (IllegalArgumentException ex49) {
                                                                    throw ex49;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex50) {
                                                                throw ex50;
                                                            }
                                                        }
                                                        catch (IllegalArgumentException ex51) {
                                                            throw ex51;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex52) {
                                                        throw ex52;
                                                    }
                                                }
                                                catch (IllegalArgumentException ex53) {
                                                    throw ex53;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex54) {
                                                throw ex54;
                                            }
                                        }
                                        catch (IllegalArgumentException ex55) {
                                            throw ex55;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex56) {
                                        throw ex56;
                                    }
                                }
                                catch (IllegalArgumentException ex57) {
                                    throw ex57;
                                }
                            }
                            catch (UnsupportedOperationException ex58) {
                                throw ex58;
                            }
                        }
                        catch (IllegalArgumentException ex59) {
                            throw ex59;
                        }
                    }
                    catch (UnsupportedOperationException ex60) {
                        throw ex60;
                    }
                }
                catch (IllegalArgumentException ex61) {
                    throw ex61;
                }
            }
            catch (UnsupportedOperationException ex62) {
                throw ex62;
            }
        }
        return a.RGBA;
    }
    
    public void a(final ByteBuffer a, final int a, final a a) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/nio/ByteBuffer.position:()I
        //     4: istore          5
        //     6: getstatic       com/sun/jna/z/a/f/f.b:Z
        //     9: aload_0         /* a */
        //    10: getfield        com/sun/jna/z/a/f/g.r:I
        //    13: aload_0         /* a */
        //    14: getfield        com/sun/jna/z/a/f/g.t:I
        //    17: imul           
        //    18: bipush          7
        //    20: iadd           
        //    21: bipush          8
        //    23: idiv           
        //    24: aload_0         /* a */
        //    25: getfield        com/sun/jna/z/a/f/g.v:I
        //    28: imul           
        //    29: istore          a
        //    31: iload           a
        //    33: iconst_1       
        //    34: iadd           
        //    35: newarray        B
        //    37: astore          a
        //    39: istore          a
        //    41: iload           a
        //    43: iconst_1       
        //    44: iadd           
        //    45: newarray        B
        //    47: astore          a
        //    49: aload_0         /* a */
        //    50: getfield        com/sun/jna/z/a/f/g.t:I
        //    53: bipush          8
        //    55: iload           a
        //    57: ifne            76
        //    60: if_icmpge       82
        //    63: goto            67
        //    66: athrow         
        //    67: aload_0         /* a */
        //    68: getfield        com/sun/jna/z/a/f/g.r:I
        //    71: iconst_1       
        //    72: goto            76
        //    75: athrow         
        //    76: iadd           
        //    77: newarray        B
        //    79: goto            83
        //    82: aconst_null    
        //    83: astore          a
        //    85: new             Ljava/util/zip/Inflater;
        //    88: dup            
        //    89: invokespecial   java/util/zip/Inflater.<init>:()V
        //    92: astore          a
        //    94: iconst_0       
        //    95: istore          a
        //    97: iload           a
        //    99: aload_0         /* a */
        //   100: getfield        com/sun/jna/z/a/f/g.s:I
        //   103: if_icmpge       837
        //   106: aload_0         /* a */
        //   107: aload           a
        //   109: aload           a
        //   111: iconst_0       
        //   112: aload           a
        //   114: arraylength    
        //   115: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/util/zip/Inflater;[BII)V
        //   118: aload_0         /* a */
        //   119: aload           a
        //   121: aload           a
        //   123: invokespecial   com/sun/jna/z/a/f/g.d:([B[B)V
        //   126: aload_1         /* a */
        //   127: iload           a
        //   129: iload           a
        //   131: iload_2         /* a */
        //   132: imul           
        //   133: iadd           
        //   134: invokevirtual   java/nio/ByteBuffer.position:(I)Ljava/nio/Buffer;
        //   137: pop            
        //   138: iload           a
        //   140: ifne            842
        //   143: aload_0         /* a */
        //   144: getfield        com/sun/jna/z/a/f/g.u:I
        //   147: iload           a
        //   149: ifne            213
        //   152: goto            156
        //   155: athrow         
        //   156: tableswitch {
        //                0: 447
        //                1: 803
        //                2: 201
        //                3: 575
        //                4: 515
        //                5: 803
        //                6: 327
        //          default: 803
        //        }
        //   200: athrow         
        //   201: getstatic       com/sun/jna/z/a/f/h.a:[I
        //   204: aload_3         /* a */
        //   205: invokevirtual   com/sun/jna/z/a/f/a.ordinal:()I
        //   208: iaload         
        //   209: goto            213
        //   212: athrow         
        //   213: tableswitch {
        //                2: 244
        //                3: 260
        //                4: 276
        //                5: 292
        //          default: 308
        //        }
        //   244: aload_0         /* a */
        //   245: aload_1         /* a */
        //   246: aload           a
        //   248: invokespecial   com/sun/jna/z/a/f/g.b:(Ljava/nio/ByteBuffer;[B)V
        //   251: iload           a
        //   253: ifeq            817
        //   256: goto            260
        //   259: athrow         
        //   260: aload_0         /* a */
        //   261: aload_1         /* a */
        //   262: aload           a
        //   264: invokespecial   com/sun/jna/z/a/f/g.c:(Ljava/nio/ByteBuffer;[B)V
        //   267: iload           a
        //   269: ifeq            817
        //   272: goto            276
        //   275: athrow         
        //   276: aload_0         /* a */
        //   277: aload_1         /* a */
        //   278: aload           a
        //   280: invokespecial   com/sun/jna/z/a/f/g.d:(Ljava/nio/ByteBuffer;[B)V
        //   283: iload           a
        //   285: ifeq            817
        //   288: goto            292
        //   291: athrow         
        //   292: aload_0         /* a */
        //   293: aload_1         /* a */
        //   294: aload           a
        //   296: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/nio/ByteBuffer;[B)V
        //   299: iload           a
        //   301: ifeq            817
        //   304: goto            308
        //   307: athrow         
        //   308: new             Ljava/lang/UnsupportedOperationException;
        //   311: dup            
        //   312: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   315: astore          a
        //   317: aload           a
        //   319: bipush          19
        //   321: aaload         
        //   322: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   325: athrow         
        //   326: athrow         
        //   327: getstatic       com/sun/jna/z/a/f/h.a:[I
        //   330: aload_3         /* a */
        //   331: invokevirtual   com/sun/jna/z/a/f/a.ordinal:()I
        //   334: iaload         
        //   335: tableswitch {
        //                2: 364
        //                3: 380
        //                4: 396
        //                5: 412
        //          default: 428
        //        }
        //   364: aload_0         /* a */
        //   365: aload_1         /* a */
        //   366: aload           a
        //   368: invokespecial   com/sun/jna/z/a/f/g.e:(Ljava/nio/ByteBuffer;[B)V
        //   371: iload           a
        //   373: ifeq            817
        //   376: goto            380
        //   379: athrow         
        //   380: aload_0         /* a */
        //   381: aload_1         /* a */
        //   382: aload           a
        //   384: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/nio/ByteBuffer;[B)V
        //   387: iload           a
        //   389: ifeq            817
        //   392: goto            396
        //   395: athrow         
        //   396: aload_0         /* a */
        //   397: aload_1         /* a */
        //   398: aload           a
        //   400: invokespecial   com/sun/jna/z/a/f/g.f:(Ljava/nio/ByteBuffer;[B)V
        //   403: iload           a
        //   405: ifeq            817
        //   408: goto            412
        //   411: athrow         
        //   412: aload_0         /* a */
        //   413: aload_1         /* a */
        //   414: aload           a
        //   416: invokespecial   com/sun/jna/z/a/f/g.g:(Ljava/nio/ByteBuffer;[B)V
        //   419: iload           a
        //   421: ifeq            817
        //   424: goto            428
        //   427: athrow         
        //   428: new             Ljava/lang/UnsupportedOperationException;
        //   431: dup            
        //   432: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   435: astore          a
        //   437: aload           a
        //   439: bipush          19
        //   441: aaload         
        //   442: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   445: athrow         
        //   446: athrow         
        //   447: getstatic       com/sun/jna/z/a/f/h.a:[I
        //   450: aload_3         /* a */
        //   451: invokevirtual   com/sun/jna/z/a/f/a.ordinal:()I
        //   454: iaload         
        //   455: lookupswitch {
        //                5: 480
        //                6: 480
        //          default: 496
        //        }
        //   480: aload_0         /* a */
        //   481: aload_1         /* a */
        //   482: aload           a
        //   484: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/nio/ByteBuffer;[B)V
        //   487: iload           a
        //   489: ifeq            817
        //   492: goto            496
        //   495: athrow         
        //   496: new             Ljava/lang/UnsupportedOperationException;
        //   499: dup            
        //   500: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   503: astore          a
        //   505: aload           a
        //   507: bipush          19
        //   509: aaload         
        //   510: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   513: athrow         
        //   514: athrow         
        //   515: getstatic       com/sun/jna/z/a/f/h.a:[I
        //   518: aload_3         /* a */
        //   519: invokevirtual   com/sun/jna/z/a/f/a.ordinal:()I
        //   522: iaload         
        //   523: lookupswitch {
        //                7: 540
        //          default: 556
        //        }
        //   540: aload_0         /* a */
        //   541: aload_1         /* a */
        //   542: aload           a
        //   544: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/nio/ByteBuffer;[B)V
        //   547: iload           a
        //   549: ifeq            817
        //   552: goto            556
        //   555: athrow         
        //   556: new             Ljava/lang/UnsupportedOperationException;
        //   559: dup            
        //   560: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   563: astore          a
        //   565: aload           a
        //   567: bipush          19
        //   569: aaload         
        //   570: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   573: athrow         
        //   574: athrow         
        //   575: aload_0         /* a */
        //   576: getfield        com/sun/jna/z/a/f/g.t:I
        //   579: tableswitch {
        //                2: 667
        //                3: 650
        //                4: 684
        //                5: 633
        //                6: 684
        //                7: 684
        //                8: 684
        //                9: 624
        //          default: 684
        //        }
        //   624: aload           a
        //   626: astore          a
        //   628: iload           a
        //   630: ifeq            702
        //   633: aload_0         /* a */
        //   634: aload           a
        //   636: aload           a
        //   638: invokespecial   com/sun/jna/z/a/f/g.a:([B[B)V
        //   641: iload           a
        //   643: ifeq            702
        //   646: goto            650
        //   649: athrow         
        //   650: aload_0         /* a */
        //   651: aload           a
        //   653: aload           a
        //   655: invokespecial   com/sun/jna/z/a/f/g.b:([B[B)V
        //   658: iload           a
        //   660: ifeq            702
        //   663: goto            667
        //   666: athrow         
        //   667: aload_0         /* a */
        //   668: aload           a
        //   670: aload           a
        //   672: invokespecial   com/sun/jna/z/a/f/g.c:([B[B)V
        //   675: iload           a
        //   677: ifeq            702
        //   680: goto            684
        //   683: athrow         
        //   684: new             Ljava/lang/UnsupportedOperationException;
        //   687: dup            
        //   688: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   691: astore          a
        //   693: aload           a
        //   695: iconst_1       
        //   696: aaload         
        //   697: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   700: athrow         
        //   701: athrow         
        //   702: getstatic       com/sun/jna/z/a/f/h.a:[I
        //   705: aload_3         /* a */
        //   706: invokevirtual   com/sun/jna/z/a/f/a.ordinal:()I
        //   709: iaload         
        //   710: tableswitch {
        //                2: 736
        //                3: 752
        //                4: 768
        //          default: 784
        //        }
        //   736: aload_0         /* a */
        //   737: aload_1         /* a */
        //   738: aload           a
        //   740: invokespecial   com/sun/jna/z/a/f/g.h:(Ljava/nio/ByteBuffer;[B)V
        //   743: iload           a
        //   745: ifeq            817
        //   748: goto            752
        //   751: athrow         
        //   752: aload_0         /* a */
        //   753: aload_1         /* a */
        //   754: aload           a
        //   756: invokespecial   com/sun/jna/z/a/f/g.i:(Ljava/nio/ByteBuffer;[B)V
        //   759: iload           a
        //   761: ifeq            817
        //   764: goto            768
        //   767: athrow         
        //   768: aload_0         /* a */
        //   769: aload_1         /* a */
        //   770: aload           a
        //   772: invokespecial   com/sun/jna/z/a/f/g.j:(Ljava/nio/ByteBuffer;[B)V
        //   775: iload           a
        //   777: ifeq            817
        //   780: goto            784
        //   783: athrow         
        //   784: new             Ljava/lang/UnsupportedOperationException;
        //   787: dup            
        //   788: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   791: astore          a
        //   793: aload           a
        //   795: bipush          19
        //   797: aaload         
        //   798: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   801: athrow         
        //   802: athrow         
        //   803: new             Ljava/lang/UnsupportedOperationException;
        //   806: dup            
        //   807: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   810: bipush          9
        //   812: aaload         
        //   813: invokespecial   java/lang/UnsupportedOperationException.<init>:(Ljava/lang/String;)V
        //   816: athrow         
        //   817: aload           a
        //   819: astore          a
        //   821: aload           a
        //   823: astore          a
        //   825: aload           a
        //   827: astore          a
        //   829: iinc            a, 1
        //   832: iload           a
        //   834: ifeq            97
        //   837: aload           a
        //   839: invokevirtual   java/util/zip/Inflater.end:()V
        //   842: goto            855
        //   845: astore          13
        //   847: aload           a
        //   849: invokevirtual   java/util/zip/Inflater.end:()V
        //   852: aload           13
        //   854: athrow         
        //   855: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  768    802    802    803    Ljava/io/IOException;
        //  752    780    783    784    Ljava/io/IOException;
        //  736    764    767    768    Ljava/io/IOException;
        //  702    748    751    752    Ljava/io/IOException;
        //  667    701    701    702    Ljava/io/IOException;
        //  650    680    683    684    Ljava/io/IOException;
        //  633    663    666    667    Ljava/io/IOException;
        //  628    646    649    650    Ljava/io/IOException;
        //  540    574    574    575    Ljava/io/IOException;
        //  515    552    555    556    Ljava/io/IOException;
        //  480    514    514    515    Ljava/io/IOException;
        //  447    492    495    496    Ljava/io/IOException;
        //  412    446    446    447    Ljava/io/IOException;
        //  396    424    427    428    Ljava/io/IOException;
        //  380    408    411    412    Ljava/io/IOException;
        //  364    392    395    396    Ljava/io/IOException;
        //  327    376    379    380    Ljava/io/IOException;
        //  292    326    326    327    Ljava/io/IOException;
        //  276    304    307    308    Ljava/io/IOException;
        //  260    288    291    292    Ljava/io/IOException;
        //  244    272    275    276    Ljava/io/IOException;
        //  213    256    259    260    Ljava/io/IOException;
        //  156    209    212    213    Ljava/io/IOException;
        //  143    200    200    201    Ljava/io/IOException;
        //  106    152    155    156    Ljava/io/IOException;
        //  60     72     75     76     Ljava/io/IOException;
        //  49     63     66     67     Ljava/io/IOException;
        //  94     837    845    855    Any
        //  845    847    845    855    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void b(final ByteBuffer a, final int a, final a a) throws IOException {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        int position = 0;
        Label_0037: {
            Label_0018: {
                try {
                    position = a;
                    if (a2 != 0) {
                        break Label_0037;
                    }
                    if (a <= 0) {
                        break Label_0018;
                    }
                    break Label_0018;
                }
                catch (IOException ex) {
                    throw ex;
                }
                try {
                    if (a <= 0) {
                        throw new IllegalArgumentException(com.sun.jna.z.a.f.g.A[13]);
                    }
                }
                catch (IOException ex2) {
                    throw ex2;
                }
            }
            position = a.position();
        }
        final int a3 = position;
        final int a4 = (this.s - 1) * a;
        a.position(a3 + a4);
        this.a(a, -a, a);
        a.position(a.position() + a4);
    }
    
    private void a(final ByteBuffer a, final byte[] a) {
        a.put(a, 1, a.length - 1);
    }
    
    private void b(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_3        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.y:[B
        //     8: iload_3         /* a */
        //     9: ifne            27
        //    12: ifnull          183
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: getfield        com/sun/jna/z/a/f/g.y:[B
        //    23: goto            27
        //    26: athrow         
        //    27: iconst_1       
        //    28: baload         
        //    29: istore          a
        //    31: aload_0         /* a */
        //    32: getfield        com/sun/jna/z/a/f/g.y:[B
        //    35: iconst_3       
        //    36: baload         
        //    37: istore          a
        //    39: aload_0         /* a */
        //    40: getfield        com/sun/jna/z/a/f/g.y:[B
        //    43: iconst_5       
        //    44: baload         
        //    45: istore          a
        //    47: iconst_1       
        //    48: istore          a
        //    50: aload_2         /* a */
        //    51: arraylength    
        //    52: istore          a
        //    54: iload           a
        //    56: iload           a
        //    58: if_icmpge       179
        //    61: aload_2         /* a */
        //    62: iload           a
        //    64: baload         
        //    65: istore          a
        //    67: aload_2         /* a */
        //    68: iload           a
        //    70: iconst_1       
        //    71: iadd           
        //    72: baload         
        //    73: istore          a
        //    75: aload_2         /* a */
        //    76: iload           a
        //    78: iconst_2       
        //    79: iadd           
        //    80: baload         
        //    81: istore          a
        //    83: iconst_m1      
        //    84: istore          a
        //    86: iload_3         /* a */
        //    87: ifne            235
        //    90: iload_3         /* a */
        //    91: ifne            175
        //    94: goto            98
        //    97: athrow         
        //    98: iload           a
        //   100: iload           a
        //   102: if_icmpne       150
        //   105: goto            109
        //   108: athrow         
        //   109: iload           a
        //   111: iload           a
        //   113: iload_3         /* a */
        //   114: ifne            144
        //   117: goto            121
        //   120: athrow         
        //   121: if_icmpne       150
        //   124: goto            128
        //   127: athrow         
        //   128: iload           a
        //   130: iload_3         /* a */
        //   131: ifne            148
        //   134: goto            138
        //   137: athrow         
        //   138: iload           a
        //   140: goto            144
        //   143: athrow         
        //   144: if_icmpne       150
        //   147: iconst_0       
        //   148: istore          a
        //   150: aload_1         /* a */
        //   151: iload           a
        //   153: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   156: iload           a
        //   158: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   161: iload           a
        //   163: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   166: iload           a
        //   168: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   171: pop            
        //   172: iinc            a, 3
        //   175: iload_3         /* a */
        //   176: ifeq            54
        //   179: iload_3         /* a */
        //   180: ifeq            235
        //   183: iconst_1       
        //   184: istore          a
        //   186: aload_2         /* a */
        //   187: arraylength    
        //   188: istore          a
        //   190: iload           a
        //   192: iload           a
        //   194: if_icmpge       235
        //   197: aload_1         /* a */
        //   198: iconst_m1      
        //   199: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   202: aload_2         /* a */
        //   203: iload           a
        //   205: iconst_2       
        //   206: iadd           
        //   207: baload         
        //   208: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   211: aload_2         /* a */
        //   212: iload           a
        //   214: iconst_1       
        //   215: iadd           
        //   216: baload         
        //   217: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   220: aload_2         /* a */
        //   221: iload           a
        //   223: baload         
        //   224: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   227: pop            
        //   228: iinc            a, 3
        //   231: iload_3         /* a */
        //   232: ifeq            190
        //   235: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      15     18     19     Ljava/lang/UnsupportedOperationException;
        //  12     23     26     27     Ljava/lang/UnsupportedOperationException;
        //  86     94     97     98     Ljava/lang/UnsupportedOperationException;
        //  90     105    108    109    Ljava/lang/UnsupportedOperationException;
        //  98     117    120    121    Ljava/lang/UnsupportedOperationException;
        //  109    124    127    128    Ljava/lang/UnsupportedOperationException;
        //  121    134    137    138    Ljava/lang/UnsupportedOperationException;
        //  128    140    143    144    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0098:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void c(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_3        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.y:[B
        //     8: iload_3         /* a */
        //     9: ifne            27
        //    12: ifnull          183
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: getfield        com/sun/jna/z/a/f/g.y:[B
        //    23: goto            27
        //    26: athrow         
        //    27: iconst_1       
        //    28: baload         
        //    29: istore          a
        //    31: aload_0         /* a */
        //    32: getfield        com/sun/jna/z/a/f/g.y:[B
        //    35: iconst_3       
        //    36: baload         
        //    37: istore          a
        //    39: aload_0         /* a */
        //    40: getfield        com/sun/jna/z/a/f/g.y:[B
        //    43: iconst_5       
        //    44: baload         
        //    45: istore          a
        //    47: iconst_1       
        //    48: istore          a
        //    50: aload_2         /* a */
        //    51: arraylength    
        //    52: istore          a
        //    54: iload           a
        //    56: iload           a
        //    58: if_icmpge       179
        //    61: aload_2         /* a */
        //    62: iload           a
        //    64: baload         
        //    65: istore          a
        //    67: aload_2         /* a */
        //    68: iload           a
        //    70: iconst_1       
        //    71: iadd           
        //    72: baload         
        //    73: istore          a
        //    75: aload_2         /* a */
        //    76: iload           a
        //    78: iconst_2       
        //    79: iadd           
        //    80: baload         
        //    81: istore          a
        //    83: iconst_m1      
        //    84: istore          a
        //    86: iload_3         /* a */
        //    87: ifne            235
        //    90: iload_3         /* a */
        //    91: ifne            175
        //    94: goto            98
        //    97: athrow         
        //    98: iload           a
        //   100: iload           a
        //   102: if_icmpne       150
        //   105: goto            109
        //   108: athrow         
        //   109: iload           a
        //   111: iload           a
        //   113: iload_3         /* a */
        //   114: ifne            144
        //   117: goto            121
        //   120: athrow         
        //   121: if_icmpne       150
        //   124: goto            128
        //   127: athrow         
        //   128: iload           a
        //   130: iload_3         /* a */
        //   131: ifne            148
        //   134: goto            138
        //   137: athrow         
        //   138: iload           a
        //   140: goto            144
        //   143: athrow         
        //   144: if_icmpne       150
        //   147: iconst_0       
        //   148: istore          a
        //   150: aload_1         /* a */
        //   151: iload           a
        //   153: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   156: iload           a
        //   158: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   161: iload           a
        //   163: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   166: iload           a
        //   168: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   171: pop            
        //   172: iinc            a, 3
        //   175: iload_3         /* a */
        //   176: ifeq            54
        //   179: iload_3         /* a */
        //   180: ifeq            235
        //   183: iconst_1       
        //   184: istore          a
        //   186: aload_2         /* a */
        //   187: arraylength    
        //   188: istore          a
        //   190: iload           a
        //   192: iload           a
        //   194: if_icmpge       235
        //   197: aload_1         /* a */
        //   198: aload_2         /* a */
        //   199: iload           a
        //   201: baload         
        //   202: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   205: aload_2         /* a */
        //   206: iload           a
        //   208: iconst_1       
        //   209: iadd           
        //   210: baload         
        //   211: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   214: aload_2         /* a */
        //   215: iload           a
        //   217: iconst_2       
        //   218: iadd           
        //   219: baload         
        //   220: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   223: iconst_m1      
        //   224: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   227: pop            
        //   228: iinc            a, 3
        //   231: iload_3         /* a */
        //   232: ifeq            190
        //   235: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      15     18     19     Ljava/lang/UnsupportedOperationException;
        //  12     23     26     27     Ljava/lang/UnsupportedOperationException;
        //  86     94     97     98     Ljava/lang/UnsupportedOperationException;
        //  90     105    108    109    Ljava/lang/UnsupportedOperationException;
        //  98     117    120    121    Ljava/lang/UnsupportedOperationException;
        //  109    124    127    128    Ljava/lang/UnsupportedOperationException;
        //  121    134    137    138    Ljava/lang/UnsupportedOperationException;
        //  128    140    143    144    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0098:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void d(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_3        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.y:[B
        //     8: iload_3         /* a */
        //     9: ifne            27
        //    12: ifnull          183
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: getfield        com/sun/jna/z/a/f/g.y:[B
        //    23: goto            27
        //    26: athrow         
        //    27: iconst_1       
        //    28: baload         
        //    29: istore          a
        //    31: aload_0         /* a */
        //    32: getfield        com/sun/jna/z/a/f/g.y:[B
        //    35: iconst_3       
        //    36: baload         
        //    37: istore          a
        //    39: aload_0         /* a */
        //    40: getfield        com/sun/jna/z/a/f/g.y:[B
        //    43: iconst_5       
        //    44: baload         
        //    45: istore          a
        //    47: iconst_1       
        //    48: istore          a
        //    50: aload_2         /* a */
        //    51: arraylength    
        //    52: istore          a
        //    54: iload           a
        //    56: iload           a
        //    58: if_icmpge       179
        //    61: aload_2         /* a */
        //    62: iload           a
        //    64: baload         
        //    65: istore          a
        //    67: aload_2         /* a */
        //    68: iload           a
        //    70: iconst_1       
        //    71: iadd           
        //    72: baload         
        //    73: istore          a
        //    75: aload_2         /* a */
        //    76: iload           a
        //    78: iconst_2       
        //    79: iadd           
        //    80: baload         
        //    81: istore          a
        //    83: iconst_m1      
        //    84: istore          a
        //    86: iload_3         /* a */
        //    87: ifne            235
        //    90: iload_3         /* a */
        //    91: ifne            175
        //    94: goto            98
        //    97: athrow         
        //    98: iload           a
        //   100: iload           a
        //   102: if_icmpne       150
        //   105: goto            109
        //   108: athrow         
        //   109: iload           a
        //   111: iload           a
        //   113: iload_3         /* a */
        //   114: ifne            144
        //   117: goto            121
        //   120: athrow         
        //   121: if_icmpne       150
        //   124: goto            128
        //   127: athrow         
        //   128: iload           a
        //   130: iload_3         /* a */
        //   131: ifne            148
        //   134: goto            138
        //   137: athrow         
        //   138: iload           a
        //   140: goto            144
        //   143: athrow         
        //   144: if_icmpne       150
        //   147: iconst_0       
        //   148: istore          a
        //   150: aload_1         /* a */
        //   151: iload           a
        //   153: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   156: iload           a
        //   158: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   161: iload           a
        //   163: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   166: iload           a
        //   168: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   171: pop            
        //   172: iinc            a, 3
        //   175: iload_3         /* a */
        //   176: ifeq            54
        //   179: iload_3         /* a */
        //   180: ifeq            235
        //   183: iconst_1       
        //   184: istore          a
        //   186: aload_2         /* a */
        //   187: arraylength    
        //   188: istore          a
        //   190: iload           a
        //   192: iload           a
        //   194: if_icmpge       235
        //   197: aload_1         /* a */
        //   198: aload_2         /* a */
        //   199: iload           a
        //   201: iconst_2       
        //   202: iadd           
        //   203: baload         
        //   204: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   207: aload_2         /* a */
        //   208: iload           a
        //   210: iconst_1       
        //   211: iadd           
        //   212: baload         
        //   213: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   216: aload_2         /* a */
        //   217: iload           a
        //   219: baload         
        //   220: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   223: iconst_m1      
        //   224: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   227: pop            
        //   228: iinc            a, 3
        //   231: iload_3         /* a */
        //   232: ifeq            190
        //   235: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  4      15     18     19     Ljava/lang/UnsupportedOperationException;
        //  12     23     26     27     Ljava/lang/UnsupportedOperationException;
        //  86     94     97     98     Ljava/lang/UnsupportedOperationException;
        //  90     105    108    109    Ljava/lang/UnsupportedOperationException;
        //  98     117    120    121    Ljava/lang/UnsupportedOperationException;
        //  109    124    127    128    Ljava/lang/UnsupportedOperationException;
        //  121    134    137    138    Ljava/lang/UnsupportedOperationException;
        //  128    140    143    144    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0098:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void e(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_1       
        //     4: istore          a
        //     6: aload_2         /* a */
        //     7: arraylength    
        //     8: istore          5
        //    10: istore_3        /* a */
        //    11: iload           a
        //    13: iload           a
        //    15: if_icmpge       61
        //    18: aload_1         /* a */
        //    19: aload_2         /* a */
        //    20: iload           a
        //    22: iconst_3       
        //    23: iadd           
        //    24: baload         
        //    25: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    28: aload_2         /* a */
        //    29: iload           a
        //    31: iconst_2       
        //    32: iadd           
        //    33: baload         
        //    34: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    37: aload_2         /* a */
        //    38: iload           a
        //    40: iconst_1       
        //    41: iadd           
        //    42: baload         
        //    43: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    46: aload_2         /* a */
        //    47: iload           a
        //    49: baload         
        //    50: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    53: pop            
        //    54: iinc            a, 4
        //    57: iload_3         /* a */
        //    58: ifeq            11
        //    61: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void f(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_1       
        //     4: istore          a
        //     6: aload_2         /* a */
        //     7: arraylength    
        //     8: istore          5
        //    10: istore_3        /* a */
        //    11: iload           a
        //    13: iload           a
        //    15: if_icmpge       61
        //    18: aload_1         /* a */
        //    19: aload_2         /* a */
        //    20: iload           a
        //    22: iconst_2       
        //    23: iadd           
        //    24: baload         
        //    25: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    28: aload_2         /* a */
        //    29: iload           a
        //    31: iconst_1       
        //    32: iadd           
        //    33: baload         
        //    34: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    37: aload_2         /* a */
        //    38: iload           a
        //    40: baload         
        //    41: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    44: aload_2         /* a */
        //    45: iload           a
        //    47: iconst_3       
        //    48: iadd           
        //    49: baload         
        //    50: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    53: pop            
        //    54: iinc            a, 4
        //    57: iload_3         /* a */
        //    58: ifeq            11
        //    61: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void g(final ByteBuffer a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_1       
        //     4: istore          4
        //     6: istore_3        /* a */
        //     7: aload_2         /* a */
        //     8: arraylength    
        //     9: istore          a
        //    11: iload           a
        //    13: iload           a
        //    15: if_icmpge       52
        //    18: aload_1         /* a */
        //    19: aload_2         /* a */
        //    20: iload           a
        //    22: baload         
        //    23: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    26: aload_2         /* a */
        //    27: iload           a
        //    29: iconst_1       
        //    30: iadd           
        //    31: baload         
        //    32: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    35: aload_2         /* a */
        //    36: iload           a
        //    38: iconst_2       
        //    39: iadd           
        //    40: baload         
        //    41: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //    44: pop            
        //    45: iinc            a, 4
        //    48: iload_3         /* a */
        //    49: ifeq            11
        //    52: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void h(final ByteBuffer a, final byte[] a) {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        if (this.x != null) {
            int a3 = 1;
            final int a4 = a.length;
            while (a3 < a4) {
                final int a5 = a[a3] & 0xFF;
                final byte a6 = this.w[a5 * 3 + 0];
                final byte a7 = this.w[a5 * 3 + 1];
                final byte a8 = this.w[a5 * 3 + 2];
                final byte a9 = this.x[a5];
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            a.put(a9).put(a8).put(a7).put(a6);
                                                                            ++a3;
                                                                            if (a2 != 0) {
                                                                                return;
                                                                            }
                                                                            if (a2 == 0) {
                                                                                continue;
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex) {
                                                                            throw ex;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex2) {
                                                                        throw ex2;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex3) {
                                                                    throw ex3;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex4) {
                                                                throw ex4;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex5) {
                                                            throw ex5;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex6) {
                                                        throw ex6;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex7) {
                                                    throw ex7;
                                                }
                                            }
                                            catch (IllegalArgumentException ex8) {
                                                throw ex8;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex9) {
                                            throw ex9;
                                        }
                                    }
                                    catch (IllegalArgumentException ex10) {
                                        throw ex10;
                                    }
                                }
                                catch (UnsupportedOperationException ex11) {
                                    throw ex11;
                                }
                            }
                            catch (IllegalArgumentException ex12) {
                                throw ex12;
                            }
                        }
                        catch (UnsupportedOperationException ex13) {
                            throw ex13;
                        }
                    }
                    catch (IllegalArgumentException ex14) {
                        throw ex14;
                    }
                }
                catch (UnsupportedOperationException ex15) {
                    throw ex15;
                }
                break;
            }
            if (a2 == 0) {
                return;
            }
        }
        int a3 = 1;
        final int a4 = a.length;
        while (a3 < a4) {
            final int a5 = a[a3] & 0xFF;
            final byte a6 = this.w[a5 * 3 + 0];
            final byte a7 = this.w[a5 * 3 + 1];
            final byte a8 = this.w[a5 * 3 + 2];
            final byte a9 = -1;
            a.put(a9).put(a8).put(a7).put(a6);
            ++a3;
            if (a2 != 0) {
                break;
            }
        }
    }
    
    private void i(final ByteBuffer a, final byte[] a) {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        if (this.x != null) {
            int a3 = 1;
            final int a4 = a.length;
            while (a3 < a4) {
                final int a5 = a[a3] & 0xFF;
                final byte a6 = this.w[a5 * 3 + 0];
                final byte a7 = this.w[a5 * 3 + 1];
                final byte a8 = this.w[a5 * 3 + 2];
                final byte a9 = this.x[a5];
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            a.put(a6).put(a7).put(a8).put(a9);
                                                                            ++a3;
                                                                            if (a2 != 0) {
                                                                                return;
                                                                            }
                                                                            if (a2 == 0) {
                                                                                continue;
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex) {
                                                                            throw ex;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex2) {
                                                                        throw ex2;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex3) {
                                                                    throw ex3;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex4) {
                                                                throw ex4;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex5) {
                                                            throw ex5;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex6) {
                                                        throw ex6;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex7) {
                                                    throw ex7;
                                                }
                                            }
                                            catch (IllegalArgumentException ex8) {
                                                throw ex8;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex9) {
                                            throw ex9;
                                        }
                                    }
                                    catch (IllegalArgumentException ex10) {
                                        throw ex10;
                                    }
                                }
                                catch (UnsupportedOperationException ex11) {
                                    throw ex11;
                                }
                            }
                            catch (IllegalArgumentException ex12) {
                                throw ex12;
                            }
                        }
                        catch (UnsupportedOperationException ex13) {
                            throw ex13;
                        }
                    }
                    catch (IllegalArgumentException ex14) {
                        throw ex14;
                    }
                }
                catch (UnsupportedOperationException ex15) {
                    throw ex15;
                }
                break;
            }
            if (a2 == 0) {
                return;
            }
        }
        int a3 = 1;
        final int a4 = a.length;
        while (a3 < a4) {
            final int a5 = a[a3] & 0xFF;
            final byte a6 = this.w[a5 * 3 + 0];
            final byte a7 = this.w[a5 * 3 + 1];
            final byte a8 = this.w[a5 * 3 + 2];
            final byte a9 = -1;
            a.put(a6).put(a7).put(a8).put(a9);
            ++a3;
            if (a2 != 0) {
                break;
            }
        }
    }
    
    private void j(final ByteBuffer a, final byte[] a) {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        if (this.x != null) {
            int a3 = 1;
            final int a4 = a.length;
            while (a3 < a4) {
                final int a5 = a[a3] & 0xFF;
                final byte a6 = this.w[a5 * 3 + 0];
                final byte a7 = this.w[a5 * 3 + 1];
                final byte a8 = this.w[a5 * 3 + 2];
                final byte a9 = this.x[a5];
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            a.put(a8).put(a7).put(a6).put(a9);
                                                                            ++a3;
                                                                            if (a2 != 0) {
                                                                                return;
                                                                            }
                                                                            if (a2 == 0) {
                                                                                continue;
                                                                            }
                                                                        }
                                                                        catch (UnsupportedOperationException ex) {
                                                                            throw ex;
                                                                        }
                                                                    }
                                                                    catch (IllegalArgumentException ex2) {
                                                                        throw ex2;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex3) {
                                                                    throw ex3;
                                                                }
                                                            }
                                                            catch (IllegalArgumentException ex4) {
                                                                throw ex4;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex5) {
                                                            throw ex5;
                                                        }
                                                    }
                                                    catch (IllegalArgumentException ex6) {
                                                        throw ex6;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex7) {
                                                    throw ex7;
                                                }
                                            }
                                            catch (IllegalArgumentException ex8) {
                                                throw ex8;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex9) {
                                            throw ex9;
                                        }
                                    }
                                    catch (IllegalArgumentException ex10) {
                                        throw ex10;
                                    }
                                }
                                catch (UnsupportedOperationException ex11) {
                                    throw ex11;
                                }
                            }
                            catch (IllegalArgumentException ex12) {
                                throw ex12;
                            }
                        }
                        catch (UnsupportedOperationException ex13) {
                            throw ex13;
                        }
                    }
                    catch (IllegalArgumentException ex14) {
                        throw ex14;
                    }
                }
                catch (UnsupportedOperationException ex15) {
                    throw ex15;
                }
                break;
            }
            if (a2 == 0) {
                return;
            }
        }
        int a3 = 1;
        final int a4 = a.length;
        while (a3 < a4) {
            final int a5 = a[a3] & 0xFF;
            final byte a6 = this.w[a5 * 3 + 0];
            final byte a7 = this.w[a5 * 3 + 1];
            final byte a8 = this.w[a5 * 3 + 2];
            final byte a9 = -1;
            a.put(a8).put(a7).put(a6).put(a9);
            ++a3;
            if (a2 != 0) {
                break;
            }
        }
    }
    
    private void a(final byte[] a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          4
        //     3: getstatic       com/sun/jna/z/a/f/f.b:Z
        //     6: istore_3        /* a */
        //     7: aload_2         /* a */
        //     8: arraylength    
        //     9: istore          a
        //    11: iload           a
        //    13: iload           a
        //    15: if_icmpge       93
        //    18: aload_1         /* a */
        //    19: iconst_1       
        //    20: iload           a
        //    22: iconst_1       
        //    23: ishr           
        //    24: iadd           
        //    25: baload         
        //    26: sipush          255
        //    29: iand           
        //    30: istore          a
        //    32: iload_3         /* a */
        //    33: ifne            89
        //    36: iload           a
        //    38: iload           a
        //    40: isub           
        //    41: lookupswitch {
        //                1: 77
        //          default: 61
        //        }
        //    60: athrow         
        //    61: aload_2         /* a */
        //    62: iload           a
        //    64: iconst_1       
        //    65: iadd           
        //    66: iload           a
        //    68: bipush          15
        //    70: iand           
        //    71: i2b            
        //    72: bastore        
        //    73: goto            77
        //    76: athrow         
        //    77: aload_2         /* a */
        //    78: iload           a
        //    80: iload           a
        //    82: iconst_4       
        //    83: ishr           
        //    84: i2b            
        //    85: bastore        
        //    86: iinc            a, 2
        //    89: iload_3         /* a */
        //    90: ifeq            11
        //    93: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  32     60     60     61     Ljava/lang/UnsupportedOperationException;
        //  36     73     76     77     Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void b(final byte[] a, final byte[] a) {
        final int b = com.sun.jna.z.a.f.f.b ? 1 : 0;
        int a2 = 1;
        final int a3 = b;
        final int a4 = a.length;
        while (a2 < a4) {
            final int a5 = a[1 + (a2 >> 2)] & 0xFF;
            Label_0123: {
                Label_0110: {
                    Label_0097: {
                        Label_0069: {
                            try {
                                if (a3 != 0) {
                                    break Label_0123;
                                }
                                final int n = a4;
                                final int n2 = a2;
                                final int n3 = n - n2;
                                switch (n3) {
                                    default: {
                                        break Label_0069;
                                    }
                                    case 3: {
                                        break Label_0069;
                                    }
                                    case 2: {
                                        break Label_0097;
                                    }
                                    case 1: {
                                        break Label_0110;
                                    }
                                }
                            }
                            catch (UnsupportedOperationException ex) {
                                throw ex;
                            }
                            try {
                                final int n = a4;
                                final int n2 = a2;
                                final int n3 = n - n2;
                                switch (n3) {
                                    default: {
                                        a[a2 + 3] = (byte)(a5 & 0x3);
                                        break;
                                    }
                                    case 3: {
                                        break;
                                    }
                                    case 2: {
                                        break Label_0097;
                                    }
                                    case 1: {
                                        break Label_0110;
                                    }
                                }
                            }
                            catch (UnsupportedOperationException ex2) {
                                throw ex2;
                            }
                        }
                        a[a2 + 2] = (byte)(a5 >> 2 & 0x3);
                    }
                    a[a2 + 1] = (byte)(a5 >> 4 & 0x3);
                }
                a[a2] = (byte)(a5 >> 6);
                a2 += 4;
            }
            if (a3 != 0) {
                break;
            }
        }
    }
    
    private void c(final byte[] a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          a
        //     3: getstatic       com/sun/jna/z/a/f/f.b:Z
        //     6: aload_2         /* a */
        //     7: arraylength    
        //     8: istore          5
        //    10: istore_3        /* a */
        //    11: iload           a
        //    13: iload           a
        //    15: if_icmpge       198
        //    18: aload_1         /* a */
        //    19: iconst_1       
        //    20: iload           a
        //    22: iconst_3       
        //    23: ishr           
        //    24: iadd           
        //    25: baload         
        //    26: sipush          255
        //    29: iand           
        //    30: istore          a
        //    32: iload_3         /* a */
        //    33: ifne            194
        //    36: iload           a
        //    38: iload           a
        //    40: isub           
        //    41: tableswitch {
        //                2: 181
        //                3: 167
        //                4: 154
        //                5: 141
        //                6: 128
        //                7: 115
        //                8: 101
        //          default: 85
        //        }
        //    84: athrow         
        //    85: aload_2         /* a */
        //    86: iload           a
        //    88: bipush          7
        //    90: iadd           
        //    91: iload           a
        //    93: iconst_1       
        //    94: iand           
        //    95: i2b            
        //    96: bastore        
        //    97: goto            101
        //   100: athrow         
        //   101: aload_2         /* a */
        //   102: iload           a
        //   104: bipush          6
        //   106: iadd           
        //   107: iload           a
        //   109: iconst_1       
        //   110: ishr           
        //   111: iconst_1       
        //   112: iand           
        //   113: i2b            
        //   114: bastore        
        //   115: aload_2         /* a */
        //   116: iload           a
        //   118: iconst_5       
        //   119: iadd           
        //   120: iload           a
        //   122: iconst_2       
        //   123: ishr           
        //   124: iconst_1       
        //   125: iand           
        //   126: i2b            
        //   127: bastore        
        //   128: aload_2         /* a */
        //   129: iload           a
        //   131: iconst_4       
        //   132: iadd           
        //   133: iload           a
        //   135: iconst_3       
        //   136: ishr           
        //   137: iconst_1       
        //   138: iand           
        //   139: i2b            
        //   140: bastore        
        //   141: aload_2         /* a */
        //   142: iload           a
        //   144: iconst_3       
        //   145: iadd           
        //   146: iload           a
        //   148: iconst_4       
        //   149: ishr           
        //   150: iconst_1       
        //   151: iand           
        //   152: i2b            
        //   153: bastore        
        //   154: aload_2         /* a */
        //   155: iload           a
        //   157: iconst_2       
        //   158: iadd           
        //   159: iload           a
        //   161: iconst_5       
        //   162: ishr           
        //   163: iconst_1       
        //   164: iand           
        //   165: i2b            
        //   166: bastore        
        //   167: aload_2         /* a */
        //   168: iload           a
        //   170: iconst_1       
        //   171: iadd           
        //   172: iload           a
        //   174: bipush          6
        //   176: ishr           
        //   177: iconst_1       
        //   178: iand           
        //   179: i2b            
        //   180: bastore        
        //   181: aload_2         /* a */
        //   182: iload           a
        //   184: iload           a
        //   186: bipush          7
        //   188: ishr           
        //   189: i2b            
        //   190: bastore        
        //   191: iinc            a, 8
        //   194: iload_3         /* a */
        //   195: ifeq            11
        //   198: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  32     84     84     85     Ljava/lang/UnsupportedOperationException;
        //  36     97     100    101    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void d(final byte[] a, final byte[] a) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_3        /* a */
        //     4: aload_1         /* a */
        //     5: iconst_0       
        //     6: baload         
        //     7: tableswitch {
        //                0: 40
        //                1: 48
        //                2: 61
        //                3: 75
        //                4: 89
        //          default: 103
        //        }
        //    40: iload_3         /* a */
        //    41: ifeq            136
        //    44: goto            48
        //    47: athrow         
        //    48: aload_0         /* a */
        //    49: aload_1         /* a */
        //    50: invokespecial   com/sun/jna/z/a/f/g.a:([B)V
        //    53: iload_3         /* a */
        //    54: ifeq            136
        //    57: goto            61
        //    60: athrow         
        //    61: aload_0         /* a */
        //    62: aload_1         /* a */
        //    63: aload_2         /* a */
        //    64: invokespecial   com/sun/jna/z/a/f/g.e:([B[B)V
        //    67: iload_3         /* a */
        //    68: ifeq            136
        //    71: goto            75
        //    74: athrow         
        //    75: aload_0         /* a */
        //    76: aload_1         /* a */
        //    77: aload_2         /* a */
        //    78: invokespecial   com/sun/jna/z/a/f/g.f:([B[B)V
        //    81: iload_3         /* a */
        //    82: ifeq            136
        //    85: goto            89
        //    88: athrow         
        //    89: aload_0         /* a */
        //    90: aload_1         /* a */
        //    91: aload_2         /* a */
        //    92: invokespecial   com/sun/jna/z/a/f/g.g:([B[B)V
        //    95: iload_3         /* a */
        //    96: ifeq            136
        //    99: goto            103
        //   102: athrow         
        //   103: new             Ljava/io/IOException;
        //   106: dup            
        //   107: new             Ljava/lang/StringBuilder;
        //   110: dup            
        //   111: invokespecial   java/lang/StringBuilder.<init>:()V
        //   114: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   117: iconst_2       
        //   118: aaload         
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: aload_1         /* a */
        //   123: iconst_0       
        //   124: baload         
        //   125: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   128: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   131: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   134: athrow         
        //   135: athrow         
        //   136: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      44     47     48     Ljava/io/IOException;
        //  40     57     60     61     Ljava/io/IOException;
        //  48     71     74     75     Ljava/io/IOException;
        //  61     85     88     89     Ljava/io/IOException;
        //  75     99     102    103    Ljava/io/IOException;
        //  89     135    135    136    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0040:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void a(final byte[] a) {
        final int b = com.sun.jna.z.a.f.f.b ? 1 : 0;
        final int a2 = this.v;
        final int a3 = b;
        int a4 = a2 + 1;
        final int a5 = a.length;
        while (a4 < a5) {
            final int n = a4;
            a[n] += a[a4 - a2];
            ++a4;
            if (a3 != 0) {
                break;
            }
        }
    }
    
    private void e(final byte[] a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* a */
        //     4: getfield        com/sun/jna/z/a/f/g.v:I
        //     7: istore          a
        //     9: iconst_1       
        //    10: istore          a
        //    12: aload_1         /* a */
        //    13: arraylength    
        //    14: istore          6
        //    16: istore_3        /* a */
        //    17: iload           a
        //    19: iload           a
        //    21: if_icmpge       43
        //    24: aload_1         /* a */
        //    25: iload           a
        //    27: dup2           
        //    28: baload         
        //    29: aload_2         /* a */
        //    30: iload           a
        //    32: baload         
        //    33: iadd           
        //    34: i2b            
        //    35: bastore        
        //    36: iinc            a, 1
        //    39: iload_3         /* a */
        //    40: ifeq            17
        //    43: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void f(final byte[] a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/sun/jna/z/a/f/g.v:I
        //     4: istore          4
        //     6: getstatic       com/sun/jna/z/a/f/f.b:Z
        //     9: istore_3        /* a */
        //    10: iconst_1       
        //    11: istore          a
        //    13: iload           a
        //    15: iload           a
        //    17: if_icmpgt       110
        //    20: aload_1         /* a */
        //    21: iload_3         /* a */
        //    22: ifne            111
        //    25: iload           a
        //    27: dup2           
        //    28: baload         
        //    29: aload_2         /* a */
        //    30: iload           a
        //    32: baload         
        //    33: sipush          255
        //    36: iand           
        //    37: iconst_1       
        //    38: iushr          
        //    39: i2b            
        //    40: iadd           
        //    41: i2b            
        //    42: bastore        
        //    43: iinc            a, 1
        //    46: iload_3         /* a */
        //    47: ifeq            13
        //    50: goto            54
        //    53: athrow         
        //    54: goto            58
        //    57: athrow         
        //    58: goto            62
        //    61: athrow         
        //    62: goto            66
        //    65: athrow         
        //    66: goto            70
        //    69: athrow         
        //    70: goto            74
        //    73: athrow         
        //    74: goto            78
        //    77: athrow         
        //    78: goto            82
        //    81: athrow         
        //    82: goto            86
        //    85: athrow         
        //    86: goto            90
        //    89: athrow         
        //    90: goto            94
        //    93: athrow         
        //    94: goto            98
        //    97: athrow         
        //    98: goto            102
        //   101: athrow         
        //   102: goto            106
        //   105: athrow         
        //   106: goto            110
        //   109: athrow         
        //   110: aload_1         /* a */
        //   111: arraylength    
        //   112: istore          a
        //   114: iload           a
        //   116: iload           a
        //   118: if_icmpge       159
        //   121: aload_1         /* a */
        //   122: iload           a
        //   124: dup2           
        //   125: baload         
        //   126: aload_2         /* a */
        //   127: iload           a
        //   129: baload         
        //   130: sipush          255
        //   133: iand           
        //   134: aload_1         /* a */
        //   135: iload           a
        //   137: iload           a
        //   139: isub           
        //   140: baload         
        //   141: sipush          255
        //   144: iand           
        //   145: iadd           
        //   146: iconst_1       
        //   147: iushr          
        //   148: i2b            
        //   149: iadd           
        //   150: i2b            
        //   151: bastore        
        //   152: iinc            a, 1
        //   155: iload_3         /* a */
        //   156: ifeq            114
        //   159: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  20     50     53     54     Ljava/lang/UnsupportedOperationException;
        //  20     54     57     58     Ljava/lang/IllegalArgumentException;
        //  20     58     61     62     Ljava/lang/UnsupportedOperationException;
        //  20     62     65     66     Ljava/lang/IllegalArgumentException;
        //  20     66     69     70     Ljava/lang/UnsupportedOperationException;
        //  20     70     73     74     Ljava/lang/IllegalArgumentException;
        //  20     74     77     78     Ljava/lang/UnsupportedOperationException;
        //  20     78     81     82     Ljava/lang/IllegalArgumentException;
        //  20     82     85     86     Ljava/lang/UnsupportedOperationException;
        //  20     86     89     90     Ljava/lang/IllegalArgumentException;
        //  20     90     93     94     Ljava/lang/UnsupportedOperationException;
        //  20     94     97     98     Ljava/lang/IllegalArgumentException;
        //  20     98     101    102    Ljava/lang/UnsupportedOperationException;
        //  20     102    105    106    Ljava/lang/IllegalArgumentException;
        //  20     106    109    110    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void g(final byte[] a, final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* a */
        //     4: getfield        com/sun/jna/z/a/f/g.v:I
        //     7: istore          a
        //     9: istore_3        /* a */
        //    10: iconst_1       
        //    11: istore          a
        //    13: iload           a
        //    15: iload           a
        //    17: if_icmpgt       103
        //    20: aload_1         /* a */
        //    21: iload_3         /* a */
        //    22: ifne            104
        //    25: iload           a
        //    27: dup2           
        //    28: baload         
        //    29: aload_2         /* a */
        //    30: iload           a
        //    32: baload         
        //    33: iadd           
        //    34: i2b            
        //    35: bastore        
        //    36: iinc            a, 1
        //    39: iload_3         /* a */
        //    40: ifeq            13
        //    43: goto            47
        //    46: athrow         
        //    47: goto            51
        //    50: athrow         
        //    51: goto            55
        //    54: athrow         
        //    55: goto            59
        //    58: athrow         
        //    59: goto            63
        //    62: athrow         
        //    63: goto            67
        //    66: athrow         
        //    67: goto            71
        //    70: athrow         
        //    71: goto            75
        //    74: athrow         
        //    75: goto            79
        //    78: athrow         
        //    79: goto            83
        //    82: athrow         
        //    83: goto            87
        //    86: athrow         
        //    87: goto            91
        //    90: athrow         
        //    91: goto            95
        //    94: athrow         
        //    95: goto            99
        //    98: athrow         
        //    99: goto            103
        //   102: athrow         
        //   103: aload_1         /* a */
        //   104: arraylength    
        //   105: istore          a
        //   107: iload           a
        //   109: iload           a
        //   111: if_icmpge       486
        //   114: aload_1         /* a */
        //   115: iload           a
        //   117: iload           a
        //   119: isub           
        //   120: baload         
        //   121: sipush          255
        //   124: iand           
        //   125: istore          a
        //   127: aload_2         /* a */
        //   128: iload           a
        //   130: baload         
        //   131: sipush          255
        //   134: iand           
        //   135: istore          a
        //   137: aload_2         /* a */
        //   138: iload           a
        //   140: iload           a
        //   142: isub           
        //   143: baload         
        //   144: sipush          255
        //   147: iand           
        //   148: istore          a
        //   150: iload           a
        //   152: iload           a
        //   154: iadd           
        //   155: iload           a
        //   157: isub           
        //   158: istore          a
        //   160: iload           a
        //   162: iload           a
        //   164: isub           
        //   165: istore          a
        //   167: iload           a
        //   169: iload_3         /* a */
        //   170: ifne            246
        //   173: ifge            241
        //   176: goto            180
        //   179: athrow         
        //   180: goto            184
        //   183: athrow         
        //   184: goto            188
        //   187: athrow         
        //   188: goto            192
        //   191: athrow         
        //   192: goto            196
        //   195: athrow         
        //   196: goto            200
        //   199: athrow         
        //   200: goto            204
        //   203: athrow         
        //   204: goto            208
        //   207: athrow         
        //   208: goto            212
        //   211: athrow         
        //   212: goto            216
        //   215: athrow         
        //   216: goto            220
        //   219: athrow         
        //   220: goto            224
        //   223: athrow         
        //   224: goto            228
        //   227: athrow         
        //   228: goto            232
        //   231: athrow         
        //   232: goto            236
        //   235: athrow         
        //   236: iload           a
        //   238: ineg           
        //   239: istore          a
        //   241: iload           a
        //   243: iload           a
        //   245: isub           
        //   246: istore          a
        //   248: iload           a
        //   250: iload_3         /* a */
        //   251: ifne            327
        //   254: ifge            322
        //   257: goto            261
        //   260: athrow         
        //   261: goto            265
        //   264: athrow         
        //   265: goto            269
        //   268: athrow         
        //   269: goto            273
        //   272: athrow         
        //   273: goto            277
        //   276: athrow         
        //   277: goto            281
        //   280: athrow         
        //   281: goto            285
        //   284: athrow         
        //   285: goto            289
        //   288: athrow         
        //   289: goto            293
        //   292: athrow         
        //   293: goto            297
        //   296: athrow         
        //   297: goto            301
        //   300: athrow         
        //   301: goto            305
        //   304: athrow         
        //   305: goto            309
        //   308: athrow         
        //   309: goto            313
        //   312: athrow         
        //   313: goto            317
        //   316: athrow         
        //   317: iload           a
        //   319: ineg           
        //   320: istore          a
        //   322: iload           a
        //   324: iload           a
        //   326: isub           
        //   327: istore          a
        //   329: iload           a
        //   331: iload_3         /* a */
        //   332: ifne            405
        //   335: ifge            403
        //   338: goto            342
        //   341: athrow         
        //   342: goto            346
        //   345: athrow         
        //   346: goto            350
        //   349: athrow         
        //   350: goto            354
        //   353: athrow         
        //   354: goto            358
        //   357: athrow         
        //   358: goto            362
        //   361: athrow         
        //   362: goto            366
        //   365: athrow         
        //   366: goto            370
        //   369: athrow         
        //   370: goto            374
        //   373: athrow         
        //   374: goto            378
        //   377: athrow         
        //   378: goto            382
        //   381: athrow         
        //   382: goto            386
        //   385: athrow         
        //   386: goto            390
        //   389: athrow         
        //   390: goto            394
        //   393: athrow         
        //   394: goto            398
        //   397: athrow         
        //   398: iload           a
        //   400: ineg           
        //   401: istore          a
        //   403: iload           a
        //   405: iload           a
        //   407: iload_3         /* a */
        //   408: ifne            461
        //   411: if_icmpgt       445
        //   414: goto            418
        //   417: athrow         
        //   418: iload           a
        //   420: iload           a
        //   422: iload_3         /* a */
        //   423: ifne            461
        //   426: goto            430
        //   429: athrow         
        //   430: if_icmpgt       445
        //   433: goto            437
        //   436: athrow         
        //   437: iload           a
        //   439: istore          a
        //   441: iload_3         /* a */
        //   442: ifeq            468
        //   445: iload           a
        //   447: iload_3         /* a */
        //   448: ifne            466
        //   451: goto            455
        //   454: athrow         
        //   455: iload           a
        //   457: goto            461
        //   460: athrow         
        //   461: if_icmpgt       468
        //   464: iload           a
        //   466: istore          a
        //   468: aload_1         /* a */
        //   469: iload           a
        //   471: dup2           
        //   472: baload         
        //   473: iload           a
        //   475: i2b            
        //   476: iadd           
        //   477: i2b            
        //   478: bastore        
        //   479: iinc            a, 1
        //   482: iload_3         /* a */
        //   483: ifeq            107
        //   486: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  20     43     46     47     Ljava/lang/UnsupportedOperationException;
        //  20     47     50     51     Ljava/lang/IllegalArgumentException;
        //  20     51     54     55     Ljava/lang/UnsupportedOperationException;
        //  20     55     58     59     Ljava/lang/IllegalArgumentException;
        //  20     59     62     63     Ljava/lang/UnsupportedOperationException;
        //  20     63     66     67     Ljava/lang/IllegalArgumentException;
        //  20     67     70     71     Ljava/lang/UnsupportedOperationException;
        //  20     71     74     75     Ljava/lang/IllegalArgumentException;
        //  20     75     78     79     Ljava/lang/UnsupportedOperationException;
        //  20     79     82     83     Ljava/lang/IllegalArgumentException;
        //  20     83     86     87     Ljava/lang/UnsupportedOperationException;
        //  20     87     90     91     Ljava/lang/IllegalArgumentException;
        //  20     91     94     95     Ljava/lang/UnsupportedOperationException;
        //  20     95     98     99     Ljava/lang/IllegalArgumentException;
        //  20     99     102    103    Ljava/lang/UnsupportedOperationException;
        //  167    176    179    180    Ljava/lang/UnsupportedOperationException;
        //  167    180    183    184    Ljava/lang/IllegalArgumentException;
        //  167    184    187    188    Ljava/lang/UnsupportedOperationException;
        //  167    188    191    192    Ljava/lang/IllegalArgumentException;
        //  167    192    195    196    Ljava/lang/UnsupportedOperationException;
        //  167    196    199    200    Ljava/lang/IllegalArgumentException;
        //  167    200    203    204    Ljava/lang/UnsupportedOperationException;
        //  167    204    207    208    Ljava/lang/IllegalArgumentException;
        //  167    208    211    212    Ljava/lang/UnsupportedOperationException;
        //  167    212    215    216    Ljava/lang/IllegalArgumentException;
        //  167    216    219    220    Ljava/lang/UnsupportedOperationException;
        //  167    220    223    224    Ljava/lang/IllegalArgumentException;
        //  167    224    227    228    Ljava/lang/UnsupportedOperationException;
        //  167    228    231    232    Ljava/lang/IllegalArgumentException;
        //  167    232    235    236    Ljava/lang/UnsupportedOperationException;
        //  248    257    260    261    Ljava/lang/UnsupportedOperationException;
        //  248    261    264    265    Ljava/lang/IllegalArgumentException;
        //  248    265    268    269    Ljava/lang/UnsupportedOperationException;
        //  248    269    272    273    Ljava/lang/IllegalArgumentException;
        //  248    273    276    277    Ljava/lang/UnsupportedOperationException;
        //  248    277    280    281    Ljava/lang/IllegalArgumentException;
        //  248    281    284    285    Ljava/lang/UnsupportedOperationException;
        //  248    285    288    289    Ljava/lang/IllegalArgumentException;
        //  248    289    292    293    Ljava/lang/UnsupportedOperationException;
        //  248    293    296    297    Ljava/lang/IllegalArgumentException;
        //  248    297    300    301    Ljava/lang/UnsupportedOperationException;
        //  248    301    304    305    Ljava/lang/IllegalArgumentException;
        //  248    305    308    309    Ljava/lang/UnsupportedOperationException;
        //  248    309    312    313    Ljava/lang/IllegalArgumentException;
        //  248    313    316    317    Ljava/lang/UnsupportedOperationException;
        //  329    338    341    342    Ljava/lang/UnsupportedOperationException;
        //  329    342    345    346    Ljava/lang/IllegalArgumentException;
        //  329    346    349    350    Ljava/lang/UnsupportedOperationException;
        //  329    350    353    354    Ljava/lang/IllegalArgumentException;
        //  329    354    357    358    Ljava/lang/UnsupportedOperationException;
        //  329    358    361    362    Ljava/lang/IllegalArgumentException;
        //  329    362    365    366    Ljava/lang/UnsupportedOperationException;
        //  329    366    369    370    Ljava/lang/IllegalArgumentException;
        //  329    370    373    374    Ljava/lang/UnsupportedOperationException;
        //  329    374    377    378    Ljava/lang/IllegalArgumentException;
        //  329    378    381    382    Ljava/lang/UnsupportedOperationException;
        //  329    382    385    386    Ljava/lang/IllegalArgumentException;
        //  329    386    389    390    Ljava/lang/UnsupportedOperationException;
        //  329    390    393    394    Ljava/lang/IllegalArgumentException;
        //  329    394    397    398    Ljava/lang/UnsupportedOperationException;
        //  405    414    417    418    Ljava/lang/UnsupportedOperationException;
        //  411    426    429    430    Ljava/lang/UnsupportedOperationException;
        //  418    433    436    437    Ljava/lang/UnsupportedOperationException;
        //  441    451    454    455    Ljava/lang/UnsupportedOperationException;
        //  445    457    460    461    Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0418:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void f() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* a */
        //     4: bipush          13
        //     6: invokespecial   com/sun/jna/z/a/f/g.b:(I)V
        //     9: istore_1        /* a */
        //    10: aload_0         /* a */
        //    11: aload_0         /* a */
        //    12: getfield        com/sun/jna/z/a/f/g.n:[B
        //    15: iconst_0       
        //    16: bipush          13
        //    18: invokespecial   com/sun/jna/z/a/f/g.a:([BII)I
        //    21: pop            
        //    22: aload_0         /* a */
        //    23: aload_0         /* a */
        //    24: aload_0         /* a */
        //    25: getfield        com/sun/jna/z/a/f/g.n:[B
        //    28: iconst_0       
        //    29: invokespecial   com/sun/jna/z/a/f/g.a:([BI)I
        //    32: putfield        com/sun/jna/z/a/f/g.r:I
        //    35: aload_0         /* a */
        //    36: aload_0         /* a */
        //    37: aload_0         /* a */
        //    38: getfield        com/sun/jna/z/a/f/g.n:[B
        //    41: iconst_4       
        //    42: invokespecial   com/sun/jna/z/a/f/g.a:([BI)I
        //    45: putfield        com/sun/jna/z/a/f/g.s:I
        //    48: aload_0         /* a */
        //    49: aload_0         /* a */
        //    50: getfield        com/sun/jna/z/a/f/g.n:[B
        //    53: bipush          8
        //    55: baload         
        //    56: sipush          255
        //    59: iand           
        //    60: putfield        com/sun/jna/z/a/f/g.t:I
        //    63: aload_0         /* a */
        //    64: aload_0         /* a */
        //    65: getfield        com/sun/jna/z/a/f/g.n:[B
        //    68: bipush          9
        //    70: baload         
        //    71: sipush          255
        //    74: iand           
        //    75: putfield        com/sun/jna/z/a/f/g.u:I
        //    78: aload_0         /* a */
        //    79: getfield        com/sun/jna/z/a/f/g.u:I
        //    82: iload_1         /* a */
        //    83: ifne            145
        //    86: tableswitch {
        //                0: 129
        //                1: 507
        //                2: 263
        //                3: 397
        //                4: 196
        //                5: 507
        //                6: 330
        //          default: 507
        //        }
        //   128: athrow         
        //   129: aload_0         /* a */
        //   130: iload_1         /* a */
        //   131: ifne            188
        //   134: goto            138
        //   137: athrow         
        //   138: getfield        com/sun/jna/z/a/f/g.t:I
        //   141: goto            145
        //   144: athrow         
        //   145: bipush          8
        //   147: if_icmpeq       187
        //   150: new             Ljava/io/IOException;
        //   153: dup            
        //   154: new             Ljava/lang/StringBuilder;
        //   157: dup            
        //   158: invokespecial   java/lang/StringBuilder.<init>:()V
        //   161: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   164: astore_2        /* a */
        //   165: aload_2         /* a */
        //   166: bipush          14
        //   168: aaload         
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   172: aload_0         /* a */
        //   173: getfield        com/sun/jna/z/a/f/g.t:I
        //   176: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   179: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   182: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   185: athrow         
        //   186: athrow         
        //   187: aload_0         /* a */
        //   188: iconst_1       
        //   189: putfield        com/sun/jna/z/a/f/g.v:I
        //   192: iload_1         /* a */
        //   193: ifeq            543
        //   196: aload_0         /* a */
        //   197: iload_1         /* a */
        //   198: ifne            255
        //   201: goto            205
        //   204: athrow         
        //   205: getfield        com/sun/jna/z/a/f/g.t:I
        //   208: bipush          8
        //   210: if_icmpeq       254
        //   213: goto            217
        //   216: athrow         
        //   217: new             Ljava/io/IOException;
        //   220: dup            
        //   221: new             Ljava/lang/StringBuilder;
        //   224: dup            
        //   225: invokespecial   java/lang/StringBuilder.<init>:()V
        //   228: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   231: astore_2        /* a */
        //   232: aload_2         /* a */
        //   233: bipush          14
        //   235: aaload         
        //   236: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   239: aload_0         /* a */
        //   240: getfield        com/sun/jna/z/a/f/g.t:I
        //   243: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   246: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   249: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   252: athrow         
        //   253: athrow         
        //   254: aload_0         /* a */
        //   255: iconst_2       
        //   256: putfield        com/sun/jna/z/a/f/g.v:I
        //   259: iload_1         /* a */
        //   260: ifeq            543
        //   263: aload_0         /* a */
        //   264: iload_1         /* a */
        //   265: ifne            322
        //   268: goto            272
        //   271: athrow         
        //   272: getfield        com/sun/jna/z/a/f/g.t:I
        //   275: bipush          8
        //   277: if_icmpeq       321
        //   280: goto            284
        //   283: athrow         
        //   284: new             Ljava/io/IOException;
        //   287: dup            
        //   288: new             Ljava/lang/StringBuilder;
        //   291: dup            
        //   292: invokespecial   java/lang/StringBuilder.<init>:()V
        //   295: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   298: astore_2        /* a */
        //   299: aload_2         /* a */
        //   300: bipush          14
        //   302: aaload         
        //   303: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   306: aload_0         /* a */
        //   307: getfield        com/sun/jna/z/a/f/g.t:I
        //   310: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   313: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   316: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   319: athrow         
        //   320: athrow         
        //   321: aload_0         /* a */
        //   322: iconst_3       
        //   323: putfield        com/sun/jna/z/a/f/g.v:I
        //   326: iload_1         /* a */
        //   327: ifeq            543
        //   330: aload_0         /* a */
        //   331: iload_1         /* a */
        //   332: ifne            389
        //   335: goto            339
        //   338: athrow         
        //   339: getfield        com/sun/jna/z/a/f/g.t:I
        //   342: bipush          8
        //   344: if_icmpeq       388
        //   347: goto            351
        //   350: athrow         
        //   351: new             Ljava/io/IOException;
        //   354: dup            
        //   355: new             Ljava/lang/StringBuilder;
        //   358: dup            
        //   359: invokespecial   java/lang/StringBuilder.<init>:()V
        //   362: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   365: astore_2        /* a */
        //   366: aload_2         /* a */
        //   367: bipush          14
        //   369: aaload         
        //   370: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   373: aload_0         /* a */
        //   374: getfield        com/sun/jna/z/a/f/g.t:I
        //   377: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   380: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   383: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   386: athrow         
        //   387: athrow         
        //   388: aload_0         /* a */
        //   389: iconst_4       
        //   390: putfield        com/sun/jna/z/a/f/g.v:I
        //   393: iload_1         /* a */
        //   394: ifeq            543
        //   397: aload_0         /* a */
        //   398: iload_1         /* a */
        //   399: ifne            462
        //   402: goto            406
        //   405: athrow         
        //   406: getfield        com/sun/jna/z/a/f/g.t:I
        //   409: tableswitch {
        //                2: 457
        //                3: 457
        //                4: 470
        //                5: 457
        //                6: 470
        //                7: 470
        //                8: 470
        //                9: 457
        //          default: 470
        //        }
        //   456: athrow         
        //   457: aload_0         /* a */
        //   458: goto            462
        //   461: athrow         
        //   462: iconst_1       
        //   463: putfield        com/sun/jna/z/a/f/g.v:I
        //   466: iload_1         /* a */
        //   467: ifeq            543
        //   470: new             Ljava/io/IOException;
        //   473: dup            
        //   474: new             Ljava/lang/StringBuilder;
        //   477: dup            
        //   478: invokespecial   java/lang/StringBuilder.<init>:()V
        //   481: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   484: astore_2        /* a */
        //   485: aload_2         /* a */
        //   486: bipush          14
        //   488: aaload         
        //   489: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   492: aload_0         /* a */
        //   493: getfield        com/sun/jna/z/a/f/g.t:I
        //   496: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   499: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   502: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   505: athrow         
        //   506: athrow         
        //   507: new             Ljava/io/IOException;
        //   510: dup            
        //   511: new             Ljava/lang/StringBuilder;
        //   514: dup            
        //   515: invokespecial   java/lang/StringBuilder.<init>:()V
        //   518: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   521: astore_2        /* a */
        //   522: aload_2         /* a */
        //   523: bipush          6
        //   525: aaload         
        //   526: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   529: aload_0         /* a */
        //   530: getfield        com/sun/jna/z/a/f/g.u:I
        //   533: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   536: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   539: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   542: athrow         
        //   543: aload_0         /* a */
        //   544: getfield        com/sun/jna/z/a/f/g.n:[B
        //   547: bipush          10
        //   549: baload         
        //   550: iload_1         /* a */
        //   551: ifne            585
        //   554: ifeq            578
        //   557: goto            561
        //   560: athrow         
        //   561: new             Ljava/io/IOException;
        //   564: dup            
        //   565: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   568: astore_2        /* a */
        //   569: aload_2         /* a */
        //   570: bipush          20
        //   572: aaload         
        //   573: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   576: athrow         
        //   577: athrow         
        //   578: aload_0         /* a */
        //   579: getfield        com/sun/jna/z/a/f/g.n:[B
        //   582: bipush          11
        //   584: baload         
        //   585: iload_1         /* a */
        //   586: ifne            620
        //   589: ifeq            613
        //   592: goto            596
        //   595: athrow         
        //   596: new             Ljava/io/IOException;
        //   599: dup            
        //   600: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   603: astore_2        /* a */
        //   604: aload_2         /* a */
        //   605: bipush          18
        //   607: aaload         
        //   608: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   611: athrow         
        //   612: athrow         
        //   613: aload_0         /* a */
        //   614: getfield        com/sun/jna/z/a/f/g.n:[B
        //   617: bipush          12
        //   619: baload         
        //   620: ifeq            652
        //   623: new             Ljava/io/IOException;
        //   626: dup            
        //   627: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   630: bipush          15
        //   632: aaload         
        //   633: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   636: athrow         
        //   637: athrow         
        //   638: athrow         
        //   639: athrow         
        //   640: athrow         
        //   641: athrow         
        //   642: athrow         
        //   643: athrow         
        //   644: athrow         
        //   645: athrow         
        //   646: athrow         
        //   647: athrow         
        //   648: athrow         
        //   649: athrow         
        //   650: athrow         
        //   651: athrow         
        //   652: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  10     128    128    129    Ljava/io/IOException;
        //  86     134    137    138    Ljava/io/IOException;
        //  129    141    144    145    Ljava/io/IOException;
        //  145    186    186    187    Ljava/io/IOException;
        //  188    201    204    205    Ljava/io/IOException;
        //  196    213    216    217    Ljava/io/IOException;
        //  205    253    253    254    Ljava/io/IOException;
        //  255    268    271    272    Ljava/io/IOException;
        //  263    280    283    284    Ljava/io/IOException;
        //  272    320    320    321    Ljava/io/IOException;
        //  322    335    338    339    Ljava/io/IOException;
        //  330    347    350    351    Ljava/io/IOException;
        //  339    387    387    388    Ljava/io/IOException;
        //  389    402    405    406    Ljava/io/IOException;
        //  397    456    456    457    Ljava/io/IOException;
        //  406    458    461    462    Ljava/io/IOException;
        //  462    506    506    507    Ljava/io/IOException;
        //  543    557    560    561    Ljava/io/IOException;
        //  554    577    577    578    Ljava/io/IOException;
        //  585    592    595    596    Ljava/io/IOException;
        //  589    612    612    613    Ljava/io/IOException;
        //  620    637    637    638    Ljava/io/IOException;
        //  620    638    638    639    Ljava/lang/UnsupportedOperationException;
        //  620    639    639    640    Ljava/io/IOException;
        //  620    640    640    641    Ljava/lang/UnsupportedOperationException;
        //  620    641    641    642    Ljava/io/IOException;
        //  620    642    642    643    Ljava/lang/UnsupportedOperationException;
        //  620    643    643    644    Ljava/io/IOException;
        //  620    644    644    645    Ljava/lang/UnsupportedOperationException;
        //  620    645    645    646    Ljava/io/IOException;
        //  620    646    646    647    Ljava/lang/UnsupportedOperationException;
        //  620    647    647    648    Ljava/io/IOException;
        //  620    648    648    649    Ljava/lang/UnsupportedOperationException;
        //  620    649    649    650    Ljava/io/IOException;
        //  620    650    650    651    Ljava/lang/UnsupportedOperationException;
        //  620    651    651    652    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0129:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void g() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/sun/jna/z/a/f/g.o:I
        //     4: iconst_3       
        //     5: idiv           
        //     6: istore_2       
        //     7: getstatic       com/sun/jna/z/a/f/f.b:Z
        //    10: istore_1        /* a */
        //    11: iload_2         /* a */
        //    12: iconst_1       
        //    13: iload_1         /* a */
        //    14: ifne            32
        //    17: if_icmplt       64
        //    20: goto            24
        //    23: athrow         
        //    24: iload_2         /* a */
        //    25: sipush          256
        //    28: goto            32
        //    31: athrow         
        //    32: iload_1         /* a */
        //    33: ifne            52
        //    36: if_icmpgt       64
        //    39: goto            43
        //    42: athrow         
        //    43: aload_0         /* a */
        //    44: getfield        com/sun/jna/z/a/f/g.o:I
        //    47: iconst_3       
        //    48: goto            52
        //    51: athrow         
        //    52: irem           
        //    53: iload_1         /* a */
        //    54: ifne            101
        //    57: ifeq            78
        //    60: goto            64
        //    63: athrow         
        //    64: new             Ljava/io/IOException;
        //    67: dup            
        //    68: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //    71: iconst_4       
        //    72: aaload         
        //    73: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //    76: athrow         
        //    77: athrow         
        //    78: aload_0         /* a */
        //    79: iload_2         /* a */
        //    80: iconst_3       
        //    81: imul           
        //    82: newarray        B
        //    84: putfield        com/sun/jna/z/a/f/g.w:[B
        //    87: aload_0         /* a */
        //    88: aload_0         /* a */
        //    89: getfield        com/sun/jna/z/a/f/g.w:[B
        //    92: iconst_0       
        //    93: aload_0         /* a */
        //    94: getfield        com/sun/jna/z/a/f/g.w:[B
        //    97: arraylength    
        //    98: invokespecial   com/sun/jna/z/a/f/g.a:([BII)I
        //   101: pop            
        //   102: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  11     20     23     24     Ljava/io/IOException;
        //  17     28     31     32     Ljava/io/IOException;
        //  32     39     42     43     Ljava/io/IOException;
        //  36     48     51     52     Ljava/io/IOException;
        //  52     60     63     64     Ljava/io/IOException;
        //  57     77     77     78     Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void h() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.u:I
        //     8: iload_1         /* a */
        //     9: ifne            71
        //    12: tableswitch {
        //                0: 45
        //                1: 183
        //                2: 76
        //                3: 110
        //          default: 183
        //        }
        //    44: athrow         
        //    45: aload_0         /* a */
        //    46: iconst_2       
        //    47: invokespecial   com/sun/jna/z/a/f/g.b:(I)V
        //    50: aload_0         /* a */
        //    51: iconst_2       
        //    52: newarray        B
        //    54: putfield        com/sun/jna/z/a/f/g.y:[B
        //    57: aload_0         /* a */
        //    58: aload_0         /* a */
        //    59: getfield        com/sun/jna/z/a/f/g.y:[B
        //    62: iconst_0       
        //    63: iconst_2       
        //    64: invokespecial   com/sun/jna/z/a/f/g.a:([BII)I
        //    67: goto            71
        //    70: athrow         
        //    71: pop            
        //    72: iload_1         /* a */
        //    73: ifeq            183
        //    76: aload_0         /* a */
        //    77: bipush          6
        //    79: invokespecial   com/sun/jna/z/a/f/g.b:(I)V
        //    82: aload_0         /* a */
        //    83: bipush          6
        //    85: newarray        B
        //    87: putfield        com/sun/jna/z/a/f/g.y:[B
        //    90: aload_0         /* a */
        //    91: aload_0         /* a */
        //    92: getfield        com/sun/jna/z/a/f/g.y:[B
        //    95: iconst_0       
        //    96: bipush          6
        //    98: invokespecial   com/sun/jna/z/a/f/g.a:([BII)I
        //   101: pop            
        //   102: iload_1         /* a */
        //   103: ifeq            183
        //   106: goto            110
        //   109: athrow         
        //   110: aload_0         /* a */
        //   111: getfield        com/sun/jna/z/a/f/g.w:[B
        //   114: iload_1         /* a */
        //   115: ifne            161
        //   118: goto            122
        //   121: athrow         
        //   122: ifnonnull       144
        //   125: goto            129
        //   128: athrow         
        //   129: new             Ljava/io/IOException;
        //   132: dup            
        //   133: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   136: bipush          11
        //   138: aaload         
        //   139: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   142: athrow         
        //   143: athrow         
        //   144: aload_0         /* a */
        //   145: aload_0         /* a */
        //   146: getfield        com/sun/jna/z/a/f/g.w:[B
        //   149: arraylength    
        //   150: iconst_3       
        //   151: idiv           
        //   152: newarray        B
        //   154: putfield        com/sun/jna/z/a/f/g.x:[B
        //   157: aload_0         /* a */
        //   158: getfield        com/sun/jna/z/a/f/g.x:[B
        //   161: iconst_m1      
        //   162: invokestatic    java/util/Arrays.fill:([BB)V
        //   165: aload_0         /* a */
        //   166: aload_0         /* a */
        //   167: getfield        com/sun/jna/z/a/f/g.x:[B
        //   170: iconst_0       
        //   171: aload_0         /* a */
        //   172: getfield        com/sun/jna/z/a/f/g.x:[B
        //   175: arraylength    
        //   176: invokespecial   com/sun/jna/z/a/f/g.a:([BII)I
        //   179: pop            
        //   180: goto            183
        //   183: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      44     44     45     Ljava/io/IOException;
        //  12     67     70     71     Ljava/io/IOException;
        //  71     106    109    110    Ljava/io/IOException;
        //  76     118    121    122    Ljava/io/IOException;
        //  110    125    128    129    Ljava/io/IOException;
        //  122    143    143    144    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0076:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void i() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/f/g.q:I
        //     8: iload_1         /* a */
        //     9: ifne            61
        //    12: ifle            38
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: aload_0         /* a */
        //    21: getfield        com/sun/jna/z/a/f/g.q:I
        //    24: iconst_4       
        //    25: iadd           
        //    26: i2l            
        //    27: invokespecial   com/sun/jna/z/a/f/g.a:(J)V
        //    30: iload_1         /* a */
        //    31: ifeq            99
        //    34: goto            38
        //    37: athrow         
        //    38: aload_0         /* a */
        //    39: aload_0         /* a */
        //    40: getfield        com/sun/jna/z/a/f/g.n:[B
        //    43: iconst_0       
        //    44: iconst_4       
        //    45: invokespecial   com/sun/jna/z/a/f/g.b:([BII)V
        //    48: aload_0         /* a */
        //    49: aload_0         /* a */
        //    50: getfield        com/sun/jna/z/a/f/g.n:[B
        //    53: iconst_0       
        //    54: invokespecial   com/sun/jna/z/a/f/g.a:([BI)I
        //    57: goto            61
        //    60: athrow         
        //    61: istore_2        /* a */
        //    62: aload_0         /* a */
        //    63: getfield        com/sun/jna/z/a/f/g.m:Ljava/util/zip/CRC32;
        //    66: invokevirtual   java/util/zip/CRC32.getValue:()J
        //    69: l2i            
        //    70: istore_3        /* a */
        //    71: iload_1         /* a */
        //    72: ifne            114
        //    75: iload_3         /* a */
        //    76: iload_2         /* a */
        //    77: if_icmpeq       99
        //    80: goto            84
        //    83: athrow         
        //    84: new             Ljava/io/IOException;
        //    87: dup            
        //    88: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //    91: bipush          17
        //    93: aaload         
        //    94: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //    97: athrow         
        //    98: athrow         
        //    99: aload_0         /* a */
        //   100: iconst_0       
        //   101: putfield        com/sun/jna/z/a/f/g.q:I
        //   104: aload_0         /* a */
        //   105: iconst_0       
        //   106: putfield        com/sun/jna/z/a/f/g.o:I
        //   109: aload_0         /* a */
        //   110: iconst_0       
        //   111: putfield        com/sun/jna/z/a/f/g.p:I
        //   114: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      15     18     19     Ljava/io/IOException;
        //  12     34     37     38     Ljava/io/IOException;
        //  19     57     60     61     Ljava/io/IOException;
        //  71     80     83     84     Ljava/io/IOException;
        //  75     98     98     99     Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0019:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void j() throws IOException {
        this.b(this.n, 0, 8);
        this.o = this.a(this.n, 0);
        this.p = this.a(this.n, 4);
        this.q = this.o;
        this.m.reset();
        this.m.update(this.n, 4, 4);
    }
    
    private void a(final int a) throws IOException {
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    this.j();
                                                                    if (this.p != a) {
                                                                        throw new IOException(com.sun.jna.z.a.f.g.A[8] + Integer.toHexString(a));
                                                                    }
                                                                }
                                                                catch (IOException ex) {
                                                                    throw ex;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex2) {
                                                                throw ex2;
                                                            }
                                                        }
                                                        catch (IOException ex3) {
                                                            throw ex3;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex4) {
                                                        throw ex4;
                                                    }
                                                }
                                                catch (IOException ex5) {
                                                    throw ex5;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex6) {
                                                throw ex6;
                                            }
                                        }
                                        catch (IOException ex7) {
                                            throw ex7;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex8) {
                                        throw ex8;
                                    }
                                }
                                catch (IOException ex9) {
                                    throw ex9;
                                }
                            }
                            catch (UnsupportedOperationException ex10) {
                                throw ex10;
                            }
                        }
                        catch (IOException ex11) {
                            throw ex11;
                        }
                    }
                    catch (UnsupportedOperationException ex12) {
                        throw ex12;
                    }
                }
                catch (IOException ex13) {
                    throw ex13;
                }
            }
            catch (UnsupportedOperationException ex14) {
                throw ex14;
            }
        }
        catch (IOException ex15) {
            throw ex15;
        }
    }
    
    private void b(final int a) throws IOException {
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    if (this.o != a) {
                                                                        throw new IOException(com.sun.jna.z.a.f.g.A[16]);
                                                                    }
                                                                }
                                                                catch (IOException ex) {
                                                                    throw ex;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex2) {
                                                                throw ex2;
                                                            }
                                                        }
                                                        catch (IOException ex3) {
                                                            throw ex3;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex4) {
                                                        throw ex4;
                                                    }
                                                }
                                                catch (IOException ex5) {
                                                    throw ex5;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex6) {
                                                throw ex6;
                                            }
                                        }
                                        catch (IOException ex7) {
                                            throw ex7;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex8) {
                                        throw ex8;
                                    }
                                }
                                catch (IOException ex9) {
                                    throw ex9;
                                }
                            }
                            catch (UnsupportedOperationException ex10) {
                                throw ex10;
                            }
                        }
                        catch (IOException ex11) {
                            throw ex11;
                        }
                    }
                    catch (UnsupportedOperationException ex12) {
                        throw ex12;
                    }
                }
                catch (IOException ex13) {
                    throw ex13;
                }
            }
            catch (UnsupportedOperationException ex14) {
                throw ex14;
            }
        }
        catch (IOException ex15) {
            throw ex15;
        }
    }
    
    private int a(final byte[] a, final int a, int a) throws IOException {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        int n = 0;
        Label_0083: {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        n = a;
                                                                        if (a2 != 0) {
                                                                            return n;
                                                                        }
                                                                        if (n <= this.q) {
                                                                            break Label_0083;
                                                                        }
                                                                    }
                                                                    catch (IOException ex) {
                                                                        throw ex;
                                                                    }
                                                                }
                                                                catch (UnsupportedOperationException ex2) {
                                                                    throw ex2;
                                                                }
                                                            }
                                                            catch (IOException ex3) {
                                                                throw ex3;
                                                            }
                                                        }
                                                        catch (UnsupportedOperationException ex4) {
                                                            throw ex4;
                                                        }
                                                    }
                                                    catch (IOException ex5) {
                                                        throw ex5;
                                                    }
                                                }
                                                catch (UnsupportedOperationException ex6) {
                                                    throw ex6;
                                                }
                                            }
                                            catch (IOException ex7) {
                                                throw ex7;
                                            }
                                        }
                                        catch (UnsupportedOperationException ex8) {
                                            throw ex8;
                                        }
                                    }
                                    catch (IOException ex9) {
                                        throw ex9;
                                    }
                                }
                                catch (UnsupportedOperationException ex10) {
                                    throw ex10;
                                }
                            }
                            catch (IOException ex11) {
                                throw ex11;
                            }
                        }
                        catch (UnsupportedOperationException ex12) {
                            throw ex12;
                        }
                    }
                    catch (IOException ex13) {
                        throw ex13;
                    }
                }
                catch (UnsupportedOperationException ex14) {
                    throw ex14;
                }
            }
            catch (IOException ex15) {
                throw ex15;
            }
            a = this.q;
        }
        this.b(a, a, a);
        this.m.update(a, a, a);
        this.q -= a;
        return n;
    }
    
    private void a(final Inflater a) throws IOException {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
    Label_0090:
        while (true) {
            while (this.q == 0) {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            this.i();
                                                                            final g g = this;
                                                                            if (a2 != 0) {
                                                                                break Label_0090;
                                                                            }
                                                                            this.a(1229209940);
                                                                            if (a2 == 0) {
                                                                                continue;
                                                                            }
                                                                        }
                                                                        catch (IOException ex) {
                                                                            throw ex;
                                                                        }
                                                                    }
                                                                    catch (UnsupportedOperationException ex2) {
                                                                        throw ex2;
                                                                    }
                                                                }
                                                                catch (IOException ex3) {
                                                                    throw ex3;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex4) {
                                                                throw ex4;
                                                            }
                                                        }
                                                        catch (IOException ex5) {
                                                            throw ex5;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex6) {
                                                        throw ex6;
                                                    }
                                                }
                                                catch (IOException ex7) {
                                                    throw ex7;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex8) {
                                                throw ex8;
                                            }
                                        }
                                        catch (IOException ex9) {
                                            throw ex9;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex10) {
                                        throw ex10;
                                    }
                                }
                                catch (IOException ex11) {
                                    throw ex11;
                                }
                            }
                            catch (UnsupportedOperationException ex12) {
                                throw ex12;
                            }
                        }
                        catch (IOException ex13) {
                            throw ex13;
                        }
                    }
                    catch (UnsupportedOperationException ex14) {
                        throw ex14;
                    }
                }
                catch (IOException ex15) {
                    throw ex15;
                }
                break;
                g g = null;
                final int a3 = g.a(this.n, 0, this.n.length);
                a.setInput(this.n, 0, a3);
                return;
            }
            final g g = this;
            continue Label_0090;
        }
    }
    
    private void a(final Inflater a, final byte[] a, final int a, final int a) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          a
        //     5: getstatic       com/sun/jna/z/a/f/g.z:Z
        //     8: iload           a
        //    10: ifne            49
        //    13: ifne            41
        //    16: goto            20
        //    19: athrow         
        //    20: aload_2         /* a */
        //    21: aload_0         /* a */
        //    22: getfield        com/sun/jna/z/a/f/g.n:[B
        //    25: if_acmpne       41
        //    28: goto            32
        //    31: athrow         
        //    32: new             Ljava/lang/AssertionError;
        //    35: dup            
        //    36: invokespecial   java/lang/AssertionError.<init>:()V
        //    39: athrow         
        //    40: athrow         
        //    41: aload_1         /* a */
        //    42: aload_2         /* a */
        //    43: iload_3         /* a */
        //    44: iload           a
        //    46: invokevirtual   java/util/zip/Inflater.inflate:([BII)I
        //    49: istore          a
        //    51: iload           a
        //    53: ifgt            150
        //    56: aload_1         /* a */
        //    57: invokevirtual   java/util/zip/Inflater.finished:()Z
        //    60: iload           a
        //    62: ifne            89
        //    65: goto            69
        //    68: athrow         
        //    69: ifeq            85
        //    72: goto            76
        //    75: athrow         
        //    76: new             Ljava/io/EOFException;
        //    79: dup            
        //    80: invokespecial   java/io/EOFException.<init>:()V
        //    83: athrow         
        //    84: athrow         
        //    85: aload_1         /* a */
        //    86: invokevirtual   java/util/zip/Inflater.needsInput:()Z
        //    89: ifeq            106
        //    92: aload_0         /* a */
        //    93: aload_1         /* a */
        //    94: invokespecial   com/sun/jna/z/a/f/g.a:(Ljava/util/zip/Inflater;)V
        //    97: iload           a
        //    99: ifeq            162
        //   102: goto            106
        //   105: athrow         
        //   106: new             Ljava/io/IOException;
        //   109: dup            
        //   110: new             Ljava/lang/StringBuilder;
        //   113: dup            
        //   114: invokespecial   java/lang/StringBuilder.<init>:()V
        //   117: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   120: astore          a
        //   122: aload           a
        //   124: bipush          7
        //   126: aaload         
        //   127: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   130: iload           a
        //   132: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   135: aload           a
        //   137: iconst_3       
        //   138: aaload         
        //   139: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   142: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   145: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   148: athrow         
        //   149: athrow         
        //   150: iload_3         /* a */
        //   151: iload           a
        //   153: iadd           
        //   154: istore_3        /* a */
        //   155: iload           a
        //   157: iload           a
        //   159: isub           
        //   160: istore          a
        //   162: iload           a
        //   164: ifgt            41
        //   167: iload           a
        //   169: ifne            85
        //   172: goto            202
        //   175: astore          a
        //   177: new             Ljava/io/IOException;
        //   180: dup            
        //   181: getstatic       com/sun/jna/z/a/f/g.A:[Ljava/lang/String;
        //   184: bipush          12
        //   186: aaload         
        //   187: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   190: aload           a
        //   192: invokevirtual   java/io/IOException.initCause:(Ljava/lang/Throwable;)Ljava/lang/Throwable;
        //   195: checkcast       Ljava/io/IOException;
        //   198: checkcast       Ljava/io/IOException;
        //   201: athrow         
        //   202: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                               
        //  -----  -----  -----  -----  -----------------------------------
        //  92     149    149    150    Ljava/util/zip/DataFormatException;
        //  89     102    105    106    Ljava/util/zip/DataFormatException;
        //  69     84     84     85     Ljava/util/zip/DataFormatException;
        //  56     72     75     76     Ljava/util/zip/DataFormatException;
        //  51     65     68     69     Ljava/util/zip/DataFormatException;
        //  20     40     40     41     Ljava/util/zip/DataFormatException;
        //  13     28     31     32     Ljava/util/zip/DataFormatException;
        //  5      16     19     20     Ljava/util/zip/DataFormatException;
        //  41     167    175    202    Ljava/util/zip/DataFormatException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0020:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void b(final byte[] a, int a, int a) throws IOException {
        do {
            final int a2 = this.l.read(a, a, a);
            if (a2 < 0) {
                throw new EOFException();
            }
            a += a2;
            a -= a2;
        } while (a > 0);
    }
    
    private int a(final byte[] a, final int a) {
        return a[a] << 24 | (a[a + 1] & 0xFF) << 16 | (a[a + 2] & 0xFF) << 8 | (a[a + 3] & 0xFF);
    }
    
    private void a(long a) throws IOException {
        final int a2 = com.sun.jna.z.a.f.f.b ? 1 : 0;
        while (a > 0L) {
            final long a3 = this.l.skip(a);
            long n2 = 0L;
            long n4 = 0L;
            Label_0047: {
                Label_0035: {
                    long n;
                    long n3;
                    try {
                        n = (n2 = a3);
                        n3 = (n4 = 0L);
                        if (a2 != 0) {
                            break Label_0047;
                        }
                        final long n5 = lcmp(n, n3);
                        if (n5 < 0) {
                            break Label_0035;
                        }
                        break Label_0035;
                    }
                    catch (IOException ex) {
                        throw ex;
                    }
                    try {
                        final long n5 = lcmp(n, n3);
                        if (n5 < 0) {
                            throw new EOFException();
                        }
                    }
                    catch (IOException ex2) {
                        throw ex2;
                    }
                }
                n2 = a;
                n4 = a3;
            }
            a = n2 - n4;
            if (a2 != 0) {
                break;
            }
        }
    }
    
    private boolean b(final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_0       
        //     4: istore_3       
        //     5: istore_2        /* a */
        //     6: iload_3         /* a */
        //     7: aload_0         /* a */
        //     8: getfield        com/sun/jna/z/a/f/g.a:[B
        //    11: arraylength    
        //    12: if_icmpge       56
        //    15: aload_1         /* a */
        //    16: iload_3         /* a */
        //    17: baload         
        //    18: iload_2         /* a */
        //    19: ifne            57
        //    22: iload_2         /* a */
        //    23: ifne            48
        //    26: goto            30
        //    29: athrow         
        //    30: aload_0         /* a */
        //    31: getfield        com/sun/jna/z/a/f/g.a:[B
        //    34: iload_3         /* a */
        //    35: baload         
        //    36: if_icmpeq       49
        //    39: goto            43
        //    42: athrow         
        //    43: iconst_0       
        //    44: goto            48
        //    47: athrow         
        //    48: ireturn        
        //    49: iinc            a, 1
        //    52: iload_2         /* a */
        //    53: ifeq            6
        //    56: iconst_1       
        //    57: ireturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  15     26     29     30     Ljava/lang/UnsupportedOperationException;
        //  22     39     42     43     Ljava/lang/UnsupportedOperationException;
        //  30     44     47     48     Ljava/lang/UnsupportedOperationException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        final String[] a = new String[21];
        int n = 0;
        String s;
        int n2 = (s = "Q\u0007c+r\u0082\u00fc<>\\\f^\u00cc\u00f8t\u001b~3#I\u0000c-k\u009c\u00f4n\u001au<;\u008e\u00f2h\nu(o\u0084»z\u0001bxo\u0084\u00f2oNy5z\u008b\u00fe\"u\u0000f9w\u0085\u00ffyNv1w\u0098\u00fenNd!k\u0089»u\u00000+x\u008d\u00f5p\u0007~=!\u00cc\u0006<\fi,~\u009f\u001bL\"D\u001d;\u008f\u00f3i\u0000{xs\u008d\u00e8<\u0019b7u\u008b»p\u000b~?o\u0084\u0014R\u0001dxz\u00cc\u00ed}\u0002y<;¼\u00d5[Nv1w\u0089\u001ai\u0000c-k\u009c\u00f4n\u001au<;\u008f\u00f4p\u0001bx}\u0083\u00e9q\u000fdb;\u000e_\u000f~\u007fo\u00cc\u00f2r\b|9o\u0089»\u0010Y\u0016`=x\u0098\u00fexNs0n\u0082\u00f0&N\u0013R\u0001dxb\u0089\u00ef<\u0007}(w\u0089\u00f6y\u0000d=\u007f\u001au\u0003q?~\u00cc\u00f3}\u001d09u\u00cc\u00fap\u001ex9;\u008f\u00f3}\u0000~=w\u001dh<^\u000b;\u008f\u00f3i\u0000{xl\u0085\u00eft\u0001e,;¼\u00d7H+0;s\u0099\u00f5w\ru\u0000v4z\u0098\u00fe<\u000bb*t\u009e\u0006o\u001ab1\u007f\u0089\u0017I\u0000c-k\u009c\u00f4n\u001au<;\u008e\u00f2hNt=k\u0098\u00f3&N\u001ci\u0000c-k\u009c\u00f4n\u001au<;\u0085\u00f5h\u000bb4z\u008f\u00fe<\u0003u,s\u0083\u00ff\u0014_\u0006e6p\u00cc\u00f3}\u001d0/i\u0083\u00f5{Nc1a\u0089\u000bU\u0000f9w\u0085\u00ff<-B\u001b\u001ci\u0000c-k\u009c\u00f4n\u001au<;\u008a\u00f2p\u001au*r\u0082\u00fc<\u0003u,s\u0083\u00ff").length();
        int n3 = 18;
        int n4 = -1;
    Label_0023:
        while (true) {
            while (true) {
                ++n4;
                final String s2 = s;
                final int n5 = n4;
                String s3 = s2.substring(n5, n5 + n3);
                int n6 = -1;
            Label_0147_Outer:
                while (true) {
                    final char[] charArray = s3.toCharArray();
                    int length;
                    int n8;
                    final int n7 = n8 = (length = charArray.length);
                    int n9 = 0;
                    while (true) {
                        Label_0264: {
                            Label_0155: {
                                try {
                                    try {
                                        if (n7 <= 1) {
                                            length = (n8 = n9);
                                            break Label_0155;
                                        }
                                        break Label_0264;
                                    }
                                    catch (IllegalArgumentException ex) {
                                        throw ex;
                                    }
                                }
                                catch (UnsupportedOperationException ex2) {
                                    throw ex2;
                                }
                            }
                            while (true) {
                                final char c = charArray[n8];
                                Label_0234: {
                                    switch (n9 % 7) {
                                        case 0: {
                                            final char c2 = '\u001c';
                                            break Label_0234;
                                        }
                                        case 1: {
                                            final char c2 = 'n';
                                            break Label_0234;
                                        }
                                        case 2: {
                                            final char c2 = '\u0010';
                                            break Label_0234;
                                        }
                                        case 3: {
                                            final char c2 = 'X';
                                            break Label_0234;
                                        }
                                        case 4: {
                                            final char c2 = '\u001b';
                                            break Label_0234;
                                        }
                                        case 5: {
                                            final char c2 = '\u00ec';
                                            break Label_0234;
                                        }
                                    }
                                    final char c2 = '\u009b';
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            try {
                                                                                try {
                                                                                    try {
                                                                                        try {
                                                                                            charArray[length] = (char)(c ^ c2);
                                                                                            ++n9;
                                                                                            if (n7 == 0) {
                                                                                                continue Label_0147_Outer;
                                                                                            }
                                                                                            break;
                                                                                        }
                                                                                        catch (IllegalArgumentException ex3) {
                                                                                            throw ex3;
                                                                                        }
                                                                                    }
                                                                                    catch (UnsupportedOperationException ex4) {
                                                                                        throw ex4;
                                                                                    }
                                                                                }
                                                                                catch (IllegalArgumentException ex5) {
                                                                                    throw ex5;
                                                                                }
                                                                            }
                                                                            catch (UnsupportedOperationException ex6) {
                                                                                throw ex6;
                                                                            }
                                                                        }
                                                                        catch (IllegalArgumentException ex7) {
                                                                            throw ex7;
                                                                        }
                                                                    }
                                                                    catch (UnsupportedOperationException ex8) {
                                                                        throw ex8;
                                                                    }
                                                                }
                                                                catch (IllegalArgumentException ex9) {
                                                                    throw ex9;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex10) {
                                                                throw ex10;
                                                            }
                                                        }
                                                        catch (IllegalArgumentException ex11) {
                                                            throw ex11;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex12) {
                                                        throw ex12;
                                                    }
                                                }
                                                catch (IllegalArgumentException ex13) {
                                                    throw ex13;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex14) {
                                                throw ex14;
                                            }
                                        }
                                        catch (IllegalArgumentException ex15) {
                                            throw ex15;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex16) {
                                        throw ex16;
                                    }
                                }
                                break;
                            }
                        }
                        if (n7 > n9) {
                            continue;
                        }
                        break;
                    }
                    final String intern = new String(charArray).intern();
                    switch (n6) {
                        default: {
                            a[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "I\u0000c-k\u009c\u00f4n\u001au<;\u008a\u00f4n\u0003q,;\u008a\u00f4nNd0r\u009f»u\u0003q?~\u001ei\u0000c-k\u009c\u00f4n\u001au<;\u008f\u00f4q\u001eb=h\u009f\u00f2s\u000005~\u0098\u00f3s\n").length();
                            n3 = 33;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            a[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0023;
                        }
                    }
                    ++n4;
                    final String s4 = s;
                    final int n10 = n4;
                    s3 = s4.substring(n10, n10 + n3);
                    n6 = 0;
                }
            }
            break;
        }
        A = a;
        boolean z2 = false;
        Label_0332: {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        if (!g.class.desiredAssertionStatus()) {
                                                                            z2 = true;
                                                                            break Label_0332;
                                                                        }
                                                                    }
                                                                    catch (UnsupportedOperationException ex17) {
                                                                        throw ex17;
                                                                    }
                                                                }
                                                                catch (IllegalArgumentException ex18) {
                                                                    throw ex18;
                                                                }
                                                            }
                                                            catch (UnsupportedOperationException ex19) {
                                                                throw ex19;
                                                            }
                                                        }
                                                        catch (IllegalArgumentException ex20) {
                                                            throw ex20;
                                                        }
                                                    }
                                                    catch (UnsupportedOperationException ex21) {
                                                        throw ex21;
                                                    }
                                                }
                                                catch (IllegalArgumentException ex22) {
                                                    throw ex22;
                                                }
                                            }
                                            catch (UnsupportedOperationException ex23) {
                                                throw ex23;
                                            }
                                        }
                                        catch (IllegalArgumentException ex24) {
                                            throw ex24;
                                        }
                                    }
                                    catch (UnsupportedOperationException ex25) {
                                        throw ex25;
                                    }
                                }
                                catch (IllegalArgumentException ex26) {
                                    throw ex26;
                                }
                            }
                            catch (UnsupportedOperationException ex27) {
                                throw ex27;
                            }
                        }
                        catch (IllegalArgumentException ex28) {
                            throw ex28;
                        }
                    }
                    catch (UnsupportedOperationException ex29) {
                        throw ex29;
                    }
                }
                catch (IllegalArgumentException ex30) {
                    throw ex30;
                }
            }
            catch (UnsupportedOperationException ex31) {
                throw ex31;
            }
            z2 = false;
        }
        z = z2;
    }
}
