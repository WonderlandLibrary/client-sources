package com.sun.jna.z;

import java.net.*;
import java.util.concurrent.atomic.*;
import com.sun.jna.z.a.*;
import java.lang.reflect.*;
import java.util.*;
import java.security.*;
import sun.font.*;

public class Main
{
    public URLClassLoader a;
    public b b;
    public static Main c;
    boolean d;
    public AtomicBoolean e;
    h f;
    public static boolean g;
    private static final String[] h;
    
    public static void entry() {
        new Main();
    }
    
    private Main() {
        this.e = new AtomicBoolean(false);
        Main.c = this;
        this.a = (URLClassLoader)Main.class.getClassLoader();
        try {
            this.b = new b(this);
            this.f = new h();
            this.e.set(true);
        }
        catch (Exception a) {
            a.printStackTrace();
        }
    }
    
    void i() throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: astore          a
        //     7: aload           a
        //     9: bipush          10
        //    11: aaload         
        //    12: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    15: astore_2       
        //    16: getstatic       com/sun/jna/z/Main.g:Z
        //    19: istore_1        /* a */
        //    20: aload_2         /* a */
        //    21: iconst_1       
        //    22: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    25: new             Ljava/util/HashSet;
        //    28: dup            
        //    29: sipush          1000
        //    32: invokespecial   java/util/HashSet.<init>:(I)V
        //    35: astore_3        /* a */
        //    36: aload_2         /* a */
        //    37: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //    40: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    43: checkcast       Ljava/util/Set;
        //    46: astore          a
        //    48: aload           a
        //    50: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    55: astore          5
        //    57: aload           5
        //    59: invokeinterface java/util/Iterator.hasNext:()Z
        //    64: ifeq            185
        //    67: aload           5
        //    69: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    74: checkcast       Ljava/lang/String;
        //    77: astore          a
        //    79: iload_1         /* a */
        //    80: ifne            198
        //    83: aload           a
        //    85: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //    88: astore          a
        //    90: aload           a
        //    92: bipush          6
        //    94: aaload         
        //    95: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    98: iload_1         /* a */
        //    99: ifne            132
        //   102: goto            106
        //   105: athrow         
        //   106: ifne            181
        //   109: goto            113
        //   112: athrow         
        //   113: aload           a
        //   115: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   118: astore          a
        //   120: aload           a
        //   122: bipush          7
        //   124: aaload         
        //   125: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   128: goto            132
        //   131: athrow         
        //   132: iload_1         /* a */
        //   133: ifne            157
        //   136: ifne            181
        //   139: goto            143
        //   142: athrow         
        //   143: aload           a
        //   145: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   148: iconst_4       
        //   149: aaload         
        //   150: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   153: goto            157
        //   156: athrow         
        //   157: iload_1         /* a */
        //   158: ifne            180
        //   161: ifne            181
        //   164: goto            168
        //   167: athrow         
        //   168: aload_3         /* a */
        //   169: aload           a
        //   171: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   176: goto            180
        //   179: athrow         
        //   180: pop            
        //   181: iload_1         /* a */
        //   182: ifeq            57
        //   185: aload_2         /* a */
        //   186: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //   189: aload_3         /* a */
        //   190: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   193: aload_2         /* a */
        //   194: iconst_0       
        //   195: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //   198: return         
        //    Exceptions:
        //  throws java.lang.Exception
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  79     102    105    106    Ljava/lang/Exception;
        //  83     109    112    113    Ljava/lang/Exception;
        //  106    128    131    132    Ljava/lang/Exception;
        //  132    139    142    143    Ljava/lang/Exception;
        //  136    153    156    157    Ljava/lang/Exception;
        //  157    164    167    168    Ljava/lang/Exception;
        //  161    176    179    180    Ljava/lang/Exception;
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
    
    void j() throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: astore          a
        //     7: aload           a
        //     9: iconst_0       
        //    10: aaload         
        //    11: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    14: astore_3       
        //    15: getstatic       com/sun/jna/z/Main.g:Z
        //    18: aload_3         /* a */
        //    19: iconst_1       
        //    20: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    23: new             Ljava/util/Vector;
        //    26: dup            
        //    27: invokespecial   java/util/Vector.<init>:()V
        //    30: astore          a
        //    32: aload_3         /* a */
        //    33: aload_0         /* a */
        //    34: getfield        com/sun/jna/z/Main.a:Ljava/net/URLClassLoader;
        //    37: aload           a
        //    39: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //    42: ldc             Ljava/security/SecureClassLoader;.class
        //    44: aload           a
        //    46: iconst_5       
        //    47: aaload         
        //    48: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    51: astore          a
        //    53: aload           a
        //    55: iconst_1       
        //    56: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    59: istore_1        /* a */
        //    60: aload           a
        //    62: aload_0         /* a */
        //    63: getfield        com/sun/jna/z/Main.a:Ljava/net/URLClassLoader;
        //    66: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    69: checkcast       Ljava/util/HashMap;
        //    72: astore          a
        //    74: aload           a
        //    76: invokevirtual   java/util/HashMap.values:()Ljava/util/Collection;
        //    79: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    84: astore          7
        //    86: aload           7
        //    88: invokeinterface java/util/Iterator.hasNext:()Z
        //    93: ifeq            162
        //    96: aload           7
        //    98: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   103: checkcast       Ljava/security/ProtectionDomain;
        //   106: astore          a
        //   108: ldc             Ljava/security/ProtectionDomain;.class
        //   110: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   113: astore          a
        //   115: aload           a
        //   117: bipush          15
        //   119: aaload         
        //   120: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   123: astore          a
        //   125: aload           a
        //   127: iconst_1       
        //   128: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //   131: aload           a
        //   133: iload_1         /* a */
        //   134: ifne            172
        //   137: aload           a
        //   139: aconst_null    
        //   140: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   143: iload_1         /* a */
        //   144: ifeq            86
        //   147: goto            151
        //   150: athrow         
        //   151: getstatic       com/sun/jna/z/a/i.g:I
        //   154: istore_2        /* a */
        //   155: iinc            a, 1
        //   158: iload_2         /* a */
        //   159: putstatic       com/sun/jna/z/a/i.g:I
        //   162: ldc             Ljava/lang/ClassLoader;.class
        //   164: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   167: iconst_3       
        //   168: aaload         
        //   169: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   172: astore          a
        //   174: aload           a
        //   176: iconst_1       
        //   177: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //   180: aload           a
        //   182: aload_0         /* a */
        //   183: getfield        com/sun/jna/z/Main.a:Ljava/net/URLClassLoader;
        //   186: aconst_null    
        //   187: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   190: aload_0         /* a */
        //   191: ldc             Lcom/sun/jna/z/Main;.class
        //   193: invokevirtual   com/sun/jna/z/Main.a:(Ljava/lang/Class;)V
        //   196: aload_0         /* a */
        //   197: ldc             Lcom/sun/jna/z/a/c/b;.class
        //   199: invokevirtual   com/sun/jna/z/Main.a:(Ljava/lang/Class;)V
        //   202: return         
        //    Exceptions:
        //  throws java.lang.Exception
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  125    147    150    151    Ljava/lang/Exception;
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
    
    void a(final Class a) throws Exception {
        final Package a2 = a.getPackage();
        final Field a3 = Package.class.getDeclaredField(Main.h[1]);
        a3.setAccessible(true);
        a3.set(a2, null);
    }
    
    void k() throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: astore          a
        //     7: aload           a
        //     9: bipush          9
        //    11: aaload         
        //    12: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    15: astore_2       
        //    16: getstatic       com/sun/jna/z/Main.g:Z
        //    19: aload_2         /* a */
        //    20: iconst_1       
        //    21: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    24: aload_2         /* a */
        //    25: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //    28: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    31: checkcast       Ljava/util/Set;
        //    34: astore_3       
        //    35: istore_1        /* a */
        //    36: aload_3         /* a */
        //    37: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    42: astore          4
        //    44: aload           4
        //    46: invokeinterface java/util/Iterator.hasNext:()Z
        //    51: ifeq            164
        //    54: aload           4
        //    56: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    61: checkcast       Ljava/lang/String;
        //    64: astore          a
        //    66: aload           a
        //    68: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //    71: astore          a
        //    73: aload           a
        //    75: bipush          6
        //    77: aaload         
        //    78: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    81: iload_1         /* a */
        //    82: ifne            159
        //    85: ifne            147
        //    88: goto            92
        //    91: athrow         
        //    92: aload           a
        //    94: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //    97: astore          a
        //    99: aload           a
        //   101: bipush          7
        //   103: aaload         
        //   104: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   107: iload_1         /* a */
        //   108: ifne            159
        //   111: goto            115
        //   114: athrow         
        //   115: ifne            147
        //   118: goto            122
        //   121: athrow         
        //   122: aload           a
        //   124: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   127: iconst_4       
        //   128: aaload         
        //   129: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   132: iload_1         /* a */
        //   133: ifne            159
        //   136: goto            140
        //   139: athrow         
        //   140: ifeq            160
        //   143: goto            147
        //   146: athrow         
        //   147: aload_3         /* a */
        //   148: aload           a
        //   150: invokeinterface java/util/Set.remove:(Ljava/lang/Object;)Z
        //   155: goto            159
        //   158: athrow         
        //   159: pop            
        //   160: iload_1         /* a */
        //   161: ifeq            44
        //   164: return         
        //    Exceptions:
        //  throws java.lang.Exception
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  66     88     91     92     Ljava/lang/Exception;
        //  85     111    114    115    Ljava/lang/Exception;
        //  92     118    121    122    Ljava/lang/Exception;
        //  115    136    139    140    Ljava/lang/Exception;
        //  122    143    146    147    Ljava/lang/Exception;
        //  140    155    158    159    Ljava/lang/Exception;
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
    
    void l() throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //     8: astore          a
        //    10: aload           a
        //    12: iconst_2       
        //    13: aaload         
        //    14: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    17: astore_2        /* a */
        //    18: istore_1        /* a */
        //    19: aload_2         /* a */
        //    20: iconst_1       
        //    21: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    24: aload_2         /* a */
        //    25: aconst_null    
        //    26: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    29: checkcast       Lcom/google/common/collect/ImmutableList;
        //    32: astore_3        /* a */
        //    33: aload_3         /* a */
        //    34: invokevirtual   com/google/common/collect/ImmutableList.iterator:()Lcom/google/common/collect/UnmodifiableIterator;
        //    37: astore          4
        //    39: aload           4
        //    41: invokeinterface java/util/Iterator.hasNext:()Z
        //    46: ifeq            235
        //    49: aload           4
        //    51: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    56: checkcast       Lcpw/mods/fml/common/eventhandler/ListenerList;
        //    59: astore          a
        //    61: aload           a
        //    63: iconst_0       
        //    64: invokevirtual   cpw/mods/fml/common/eventhandler/ListenerList.getListeners:(I)[Lcpw/mods/fml/common/eventhandler/IEventListener;
        //    67: astore          a
        //    69: aload           a
        //    71: arraylength    
        //    72: istore          a
        //    74: iconst_0       
        //    75: istore          a
        //    77: iload           a
        //    79: iload           a
        //    81: if_icmpge       150
        //    84: aload           a
        //    86: iload           a
        //    88: aaload         
        //    89: astore          a
        //    91: iload_1         /* a */
        //    92: ifne            146
        //    95: aload           a
        //    97: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   100: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   103: astore          a
        //   105: aload           a
        //   107: bipush          6
        //   109: aaload         
        //   110: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   113: iload_1         /* a */
        //   114: ifne            164
        //   117: goto            121
        //   120: athrow         
        //   121: ifeq            143
        //   124: goto            128
        //   127: athrow         
        //   128: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   131: aload           a
        //   133: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   136: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   139: goto            143
        //   142: athrow         
        //   143: iinc            a, 1
        //   146: iload_1         /* a */
        //   147: ifeq            77
        //   150: aload           a
        //   152: iconst_1       
        //   153: invokevirtual   cpw/mods/fml/common/eventhandler/ListenerList.getListeners:(I)[Lcpw/mods/fml/common/eventhandler/IEventListener;
        //   156: astore          a
        //   158: aload           a
        //   160: arraylength    
        //   161: istore          a
        //   163: iconst_0       
        //   164: istore          a
        //   166: iload           a
        //   168: iload           a
        //   170: if_icmpge       231
        //   173: aload           a
        //   175: iload           a
        //   177: aaload         
        //   178: astore          a
        //   180: iload_1         /* a */
        //   181: ifne            227
        //   184: aload           a
        //   186: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   189: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   192: bipush          6
        //   194: aaload         
        //   195: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   198: iload_1         /* a */
        //   199: ifne            46
        //   202: goto            206
        //   205: athrow         
        //   206: ifeq            224
        //   209: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   212: aload           a
        //   214: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   217: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   220: goto            224
        //   223: athrow         
        //   224: iinc            a, 1
        //   227: iload_1         /* a */
        //   228: ifeq            166
        //   231: iload_1         /* a */
        //   232: ifeq            39
        //   235: return         
        //    Exceptions:
        //  throws java.lang.Exception
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  91     117    120    121    Ljava/lang/Exception;
        //  95     124    127    128    Ljava/lang/Exception;
        //  121    139    142    143    Ljava/lang/Exception;
        //  180    202    205    206    Ljava/lang/Exception;
        //  206    220    223    224    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0121:
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
    
    void m() throws Exception {
        final Field a = Enum.class.getDeclaredField(Main.h[11]);
        a.setAccessible(true);
        final int g = Main.g ? 1 : 0;
        final com.sun.jna.z.a.d.b[] a2 = com.sun.jna.z.a.d.b.values();
        final int a3 = g;
        final int a4 = a2.length;
        int a5 = 0;
        while (a5 < a4) {
            final com.sun.jna.z.a.d.b a6 = a2[a5];
            a.set(a6, null);
            ++a5;
            if (a3 != 0) {
                break;
            }
        }
    }
    
    void n() throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: astore          a
        //     7: aload           a
        //     9: bipush          14
        //    11: aaload         
        //    12: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    15: astore_2        /* a */
        //    16: aload_2         /* a */
        //    17: iconst_1       
        //    18: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    21: getstatic       com/sun/jna/z/Main.g:Z
        //    24: aload_2         /* a */
        //    25: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //    28: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    31: checkcast       Ljava/util/Map;
        //    34: astore_3        /* a */
        //    35: aload_3         /* a */
        //    36: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //    41: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    46: astore          4
        //    48: istore_1        /* a */
        //    49: aload           4
        //    51: invokeinterface java/util/Iterator.hasNext:()Z
        //    56: ifeq            182
        //    59: aload           4
        //    61: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    66: checkcast       Ljava/lang/Package;
        //    69: astore          a
        //    71: aload           a
        //    73: invokevirtual   java/lang/Package.getName:()Ljava/lang/String;
        //    76: iload_1         /* a */
        //    77: ifne            177
        //    80: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //    83: astore          a
        //    85: aload           a
        //    87: bipush          6
        //    89: aaload         
        //    90: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    93: ifne            165
        //    96: goto            100
        //    99: athrow         
        //   100: aload           a
        //   102: invokevirtual   java/lang/Package.getName:()Ljava/lang/String;
        //   105: iload_1         /* a */
        //   106: ifne            177
        //   109: goto            113
        //   112: athrow         
        //   113: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   116: astore          a
        //   118: aload           a
        //   120: bipush          7
        //   122: aaload         
        //   123: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   126: ifne            165
        //   129: goto            133
        //   132: athrow         
        //   133: aload           a
        //   135: invokevirtual   java/lang/Package.getName:()Ljava/lang/String;
        //   138: iload_1         /* a */
        //   139: ifne            177
        //   142: goto            146
        //   145: athrow         
        //   146: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   149: astore          a
        //   151: aload           a
        //   153: iconst_4       
        //   154: aaload         
        //   155: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   158: ifeq            178
        //   161: goto            165
        //   164: athrow         
        //   165: aload_3         /* a */
        //   166: aload           a
        //   168: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   173: goto            177
        //   176: athrow         
        //   177: pop            
        //   178: iload_1         /* a */
        //   179: ifeq            49
        //   182: ldc             Ljava/lang/ClassLoader;.class
        //   184: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   187: astore          a
        //   189: aload           a
        //   191: bipush          13
        //   193: aaload         
        //   194: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   197: astore          a
        //   199: aload           a
        //   201: iconst_1       
        //   202: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //   205: aload           a
        //   207: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //   210: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   213: checkcast       Ljava/util/HashMap;
        //   216: astore          a
        //   218: new             Ljava/util/HashMap;
        //   221: dup            
        //   222: invokespecial   java/util/HashMap.<init>:()V
        //   225: astore          a
        //   227: aload           a
        //   229: invokevirtual   java/util/HashMap.keySet:()Ljava/util/Set;
        //   232: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   237: astore          7
        //   239: aload           7
        //   241: invokeinterface java/util/Iterator.hasNext:()Z
        //   246: ifeq            373
        //   249: aload           7
        //   251: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   256: checkcast       Ljava/lang/String;
        //   259: astore          a
        //   261: iload_1         /* a */
        //   262: ifne            383
        //   265: aload           a
        //   267: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   270: astore          a
        //   272: aload           a
        //   274: bipush          6
        //   276: aaload         
        //   277: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   280: iload_1         /* a */
        //   281: ifne            314
        //   284: goto            288
        //   287: athrow         
        //   288: ifne            369
        //   291: goto            295
        //   294: athrow         
        //   295: aload           a
        //   297: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   300: astore          a
        //   302: aload           a
        //   304: bipush          7
        //   306: aaload         
        //   307: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   310: goto            314
        //   313: athrow         
        //   314: iload_1         /* a */
        //   315: ifne            347
        //   318: ifne            369
        //   321: goto            325
        //   324: athrow         
        //   325: aload           a
        //   327: iload_1         /* a */
        //   328: ifne            368
        //   331: goto            335
        //   334: athrow         
        //   335: getstatic       com/sun/jna/z/Main.h:[Ljava/lang/String;
        //   338: iconst_4       
        //   339: aaload         
        //   340: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   343: goto            347
        //   346: athrow         
        //   347: ifne            369
        //   350: aload           a
        //   352: aload           a
        //   354: aload           a
        //   356: aload           a
        //   358: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   361: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   364: goto            368
        //   367: athrow         
        //   368: pop            
        //   369: iload_1         /* a */
        //   370: ifeq            239
        //   373: aload           a
        //   375: getstatic       net/minecraft/launchwrapper/Launch.classLoader:Lnet/minecraft/launchwrapper/LaunchClassLoader;
        //   378: aload           a
        //   380: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   383: getstatic       com/sun/jna/z/a/i.g:I
        //   386: ifeq            406
        //   389: iload_1         /* a */
        //   390: ifeq            402
        //   393: goto            397
        //   396: athrow         
        //   397: iconst_0       
        //   398: goto            403
        //   401: athrow         
        //   402: iconst_1       
        //   403: putstatic       com/sun/jna/z/Main.g:Z
        //   406: return         
        //    Exceptions:
        //  throws java.lang.Exception
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  71     96     99     100    Ljava/lang/Exception;
        //  80     109    112    113    Ljava/lang/Exception;
        //  100    129    132    133    Ljava/lang/Exception;
        //  113    142    145    146    Ljava/lang/Exception;
        //  133    161    164    165    Ljava/lang/Exception;
        //  146    173    176    177    Ljava/lang/Exception;
        //  261    284    287    288    Ljava/lang/Exception;
        //  265    291    294    295    Ljava/lang/Exception;
        //  288    310    313    314    Ljava/lang/Exception;
        //  314    321    324    325    Ljava/lang/Exception;
        //  318    331    334    335    Ljava/lang/Exception;
        //  325    343    346    347    Ljava/lang/Exception;
        //  347    364    367    368    Ljava/lang/Exception;
        //  383    393    396    397    Ljava/lang/Exception;
        //  389    401    401    402    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0100:
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
    
    void o() throws Exception {
        final Field a = ClassLoader.class.getDeclaredField(Main.h[8]);
        a.setAccessible(true);
        final Set<ProtectionDomain> a2 = (Set<ProtectionDomain>)a.get(this.a);
        a2.clear();
    }
    
    void p() throws Exception {
        final Field a = GlyphLayout.class.getDeclaredField(Main.h[12]);
        a.setAccessible(true);
        a.set(null, null);
    }
    
    private static native void freeLib();
    
    static void access$000() {
        freeLib();
    }
    
    static {
        final String[] h2 = new String[16];
        int n = 0;
        String s;
        int n2 = (s = "\u00f7\u00de\u00fa;\u009et§\u0006\u00f8\u00dd\u00fa,\u0088c\b\u00f5\u00de\u00f7\u0004\u0084b \u00e7\r\u00f0\u00d7\u00fd)\u0098} \u00d0\u00dd\u00f6)\u0084\u007f\u000b\u00fb\u00c0\u00fcf\u0083t£\u00f0\u00d3\u00ec&\u0007\u00e4\u00d6\u00f8)\u008ey±\u0003\u00fe\u00dc\u00fa\u0002\u00f9\u00d6\u0007\u00f0\u00dd\u00f6)\u0084\u007f§\u0015\u00fa\u00d7\u00fc)\u0099x¢\u00f1\u00e0\u00fe;\u0082d¦\u00f7\u00d7\u00d8)\u008ey±\u000e\u00fd\u00dc\u00ed)\u0081x°\u00d7\u00de\u00fa;\u009et§\u0004\u00fa\u00d3\u00f6-\u0005\u00f7\u00d3\u00f8 \u0088\b\u00e4\u00d3\u00f8#\u008cv±\u00e7").length();
        int n3 = 7;
        int n4 = -1;
    Label_0023:
        while (true) {
            while (true) {
                ++n4;
                final String s2 = s;
                final int n5 = n4;
                String s3 = s2.substring(n5, n5 + n3);
                int n6 = -1;
                while (true) {
                    final char[] charArray = s3.toCharArray();
                    int length;
                    int n8;
                    final int n7 = n8 = (length = charArray.length);
                    int n9 = 0;
                    while (true) {
                        Label_0249: {
                            if (n7 > 1) {
                                break Label_0249;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u0094';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '²';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u009b';
                                        break;
                                    }
                                    case 3: {
                                        c2 = 'H';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u00ed';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u0011';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00d4';
                                        break;
                                    }
                                }
                                charArray[length] = (char)(c ^ c2);
                                ++n9;
                            } while (n7 == 0);
                        }
                        if (n7 > n9) {
                            continue;
                        }
                        break;
                    }
                    final String intern = new String(charArray).intern();
                    switch (n6) {
                        default: {
                            h2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "\u00e4\u00d3\u00f8#\u008cv±\u00d9\u00d3\u00f5!\u008bt§\u00e0\u00c1\u000b\u00f7\u00de\u00fa;\u009e}»\u00f5\u00d6\u00fe:").length();
                            n3 = 16;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            h2[n++] = intern;
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
        h = h2;
    }
}
