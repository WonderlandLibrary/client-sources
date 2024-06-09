package com.sun.jna.z.a.b;

import com.sun.jna.z.a.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import java.util.*;

public class f
{
    e a;
    public boolean b;
    boolean c;
    Properties d;
    Properties e;
    Properties f;
    private static final String[] g;
    
    public f(final e e) {
    }
    
    public void a() {
        final int a = com.sun.jna.z.a.b.e.e;
        try {
            f f = null;
            Label_0019: {
                try {
                    f = this;
                    if (a != 0) {
                        break Label_0019;
                    }
                    final boolean b = this.b;
                    if (b) {
                        break Label_0019;
                    }
                    return;
                }
                catch (Exception ex) {
                    throw ex;
                }
                try {
                    final boolean b = this.b;
                    if (!b) {
                        return;
                    }
                    this.a.a(4);
                    this.c();
                    this.d();
                    this.e();
                    this.f();
                    f = this;
                }
                catch (Exception ex2) {
                    throw ex2;
                }
            }
            f.b();
        }
        catch (Exception a2) {
            a2.printStackTrace();
        }
    }
    
    private void b() {
        this.a.a(6);
        this.a.b.write(i.f.d.a.size());
        final int a = com.sun.jna.z.a.b.e.e;
        this.a.b.flush();
        for (final String a2 : i.f.d.a) {
            this.a.b.println(a2);
            if (a != 0) {
                break;
            }
        }
    }
    
    void c() {
        this.a.b.write(h.d.a.a.values().size());
        final int e = com.sun.jna.z.a.b.e.e;
        final Iterator<c> iterator = h.d.a.a.values().iterator();
        final int a = e;
        while (iterator.hasNext()) {
            final c a2 = iterator.next();
            this.a.a(a2.toString());
            this.a.a(String.valueOf(a2.b()));
            if (a != 0) {
                break;
            }
        }
    }
    
    void d() {
        final int a = h.d.a.b().size();
        final int e = com.sun.jna.z.a.b.e.e;
        this.a.b.write(a);
        final int a2 = e;
        final Iterator<c> iterator = h.d.a.a.values().iterator();
    Label_0198:
        do {
            iterator.hasNext();
            boolean b = false;
        Label_0056:
            while (b) {
                final c a3 = iterator.next();
                final k[] k = a3.k();
                if (a2 == 0) {
                    if (k == null) {
                        continue Label_0198;
                    }
                    a3.k();
                }
                final k[] a4 = k;
                final int a5 = a4.length;
                int a6 = 0;
                while (a6 < a5) {
                    final k a7 = a4[a6];
                    if (a2 == 0) {
                        b = (a7 instanceof t);
                        if (a2 != 0) {
                            continue Label_0056;
                        }
                        if (b) {
                            final t a8 = (t)a7;
                            this.a.a(a3.a() + ":" + a8.b());
                            this.a.a(String.valueOf(a8.a()));
                        }
                        ++a6;
                    }
                    if (a2 != 0) {
                        break;
                    }
                }
                continue Label_0198;
            }
            break;
        } while (a2 == 0);
    }
    
    void e() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokespecial   java/util/Properties.<init>:()V
        //     7: astore_3       
        //     8: getstatic       com/sun/jna/z/a/b/e.e:I
        //    11: istore_1        /* a */
        //    12: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    15: getfield        com/sun/jna/z/a/h.a:Lcom/sun/jna/z/a/d/e;
        //    18: invokevirtual   com/sun/jna/z/a/d/e.c:()Ljava/util/List;
        //    21: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    26: astore          4
        //    28: aload           4
        //    30: invokeinterface java/util/Iterator.hasNext:()Z
        //    35: ifeq            87
        //    38: aload           4
        //    40: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    45: checkcast       Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //    48: astore          a
        //    50: aload_3         /* a */
        //    51: aload           a
        //    53: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.b:()Ljava/lang/String;
        //    58: aload           a
        //    60: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //    65: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //    68: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //    71: pop            
        //    72: iload_1         /* a */
        //    73: ifeq            28
        //    76: getstatic       com/sun/jna/z/a/i.g:I
        //    79: istore_2        /* a */
        //    80: iinc            a, 1
        //    83: iload_2         /* a */
        //    84: putstatic       com/sun/jna/z/a/i.g:I
        //    87: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //    90: getfield        com/sun/jna/z/a/i.c:Lcom/sun/jna/z/a/e/a/a/a/h;
        //    93: astore          a
        //    95: aload_3         /* a */
        //    96: getstatic       com/sun/jna/z/a/b/f.g:[Ljava/lang/String;
        //    99: astore          a
        //   101: aload           a
        //   103: iconst_3       
        //   104: aaload         
        //   105: aload           a
        //   107: getfield        com/sun/jna/z/a/e/a/a/a/h.j:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   110: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   115: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //   118: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //   121: pop            
        //   122: aload_3         /* a */
        //   123: aload           a
        //   125: iconst_5       
        //   126: aaload         
        //   127: aload           a
        //   129: getfield        com/sun/jna/z/a/e/a/a/a/h.k:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   132: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   137: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //   140: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //   143: pop            
        //   144: aload_3         /* a */
        //   145: aload           a
        //   147: iconst_1       
        //   148: aaload         
        //   149: aload           a
        //   151: getfield        com/sun/jna/z/a/e/a/a/a/h.l:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   154: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   159: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //   162: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //   165: pop            
        //   166: aload_3         /* a */
        //   167: aload           a
        //   169: bipush          6
        //   171: aaload         
        //   172: aload           a
        //   174: getfield        com/sun/jna/z/a/e/a/a/a/h.m:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   177: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   182: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //   185: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //   188: pop            
        //   189: aload_0         /* a */
        //   190: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //   193: getfield        com/sun/jna/z/a/b/e.b:Ljava/io/PrintWriter;
        //   196: aload_3         /* a */
        //   197: invokevirtual   java/util/Properties.keySet:()Ljava/util/Set;
        //   200: invokeinterface java/util/Set.size:()I
        //   205: invokevirtual   java/io/PrintWriter.write:(I)V
        //   208: aload_3         /* a */
        //   209: invokevirtual   java/util/Properties.keySet:()Ljava/util/Set;
        //   212: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   217: astore          5
        //   219: aload           5
        //   221: invokeinterface java/util/Iterator.hasNext:()Z
        //   226: ifeq            271
        //   229: aload           5
        //   231: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   236: astore          a
        //   238: aload           a
        //   240: checkcast       Ljava/lang/String;
        //   243: astore          a
        //   245: aload_0         /* a */
        //   246: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //   249: aload           a
        //   251: invokevirtual   com/sun/jna/z/a/b/e.a:(Ljava/lang/String;)V
        //   254: aload_0         /* a */
        //   255: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //   258: aload_3         /* a */
        //   259: aload           a
        //   261: invokevirtual   java/util/Properties.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   264: invokevirtual   com/sun/jna/z/a/b/e.a:(Ljava/lang/String;)V
        //   267: iload_1         /* a */
        //   268: ifeq            219
        //   271: return         
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
    
    void f() {
        final int e = com.sun.jna.z.a.b.e.e;
        final Properties a = new Properties();
        final n[] a2 = i.f.c.b();
        final int length = a2.length;
        int i = 0;
        int a3 = e;
        while (true) {
            while (i < length) {
                final n a4 = a2[i];
                a.setProperty(a4.a() + "X", String.valueOf(a4.d()));
                a.setProperty(a4.a() + "Y", String.valueOf(a4.e()));
                final Properties properties = a;
                final StringBuilder append = new StringBuilder().append(a4.a());
                final String[] a5 = com.sun.jna.z.a.b.f.g;
                properties.setProperty(append.append(a5[0]).toString(), String.valueOf(a4.b()));
                a.setProperty(a4.a() + a5[2], String.valueOf(a4.d()));
                a.setProperty(a4.a() + a5[4], String.valueOf(a4.p()));
                ++i;
                if (a3 != 0) {
                    for (final Object a6 : ((Hashtable<Object, V>)a).keySet()) {
                        final String a7 = (String)a6;
                        this.a.a(a7);
                        this.a.a(a.getProperty(a7));
                        if (a3 != 0) {
                            break;
                        }
                    }
                    if (com.sun.jna.z.a.i.g != 0) {
                        com.sun.jna.z.a.b.e.e = ++a3;
                    }
                    return;
                }
                if (a3 != 0) {
                    break;
                }
            }
            this.a.b.write(((Hashtable<Object, V>)a).keySet().size());
            continue;
        }
    }
    
    public void g() {
    }
    
    private void h() {
        this.a.b.write(7);
        final int e = com.sun.jna.z.a.b.e.e;
        this.a.b.flush();
        final int a = e;
        try {
            final int a2 = Integer.parseInt(this.a.c.readLine());
            int a3 = 0;
            while (a3 < a2) {
                try {
                    i.f.d.a.add(this.a.c.readLine());
                    ++a3;
                    if (a == 0) {
                        if (a == 0) {
                            continue;
                        }
                    }
                }
                catch (Exception ex) {
                    throw ex;
                }
                break;
            }
        }
        catch (Exception ex2) {}
    }
    
    void i() {
        final int a = com.sun.jna.z.a.b.e.e;
        boolean c = false;
        Label_0020: {
            try {
                final boolean b = c = this.c;
                if (a != 0) {
                    break Label_0020;
                }
                if (b) {
                    return;
                }
            }
            catch (Exception ex) {
                throw ex;
            }
            c = false;
        }
        int a2 = c ? 1 : 0;
        try {
            a2 = Integer.parseInt(this.a.a());
        }
        catch (Exception a3) {
            a3.printStackTrace();
        }
        int n = 0;
        Label_0053: {
            try {
                n = a2;
                if (a != 0) {
                    break Label_0053;
                }
                if (n <= 0) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw ex2;
            }
        }
        int a4 = n;
        while (a4 < a2) {
            try {
                final String a5 = this.a.a();
                final int a6 = Integer.parseInt(this.a.a());
                h.d.a.a(a5).b(a6);
            }
            catch (Exception ex3) {}
            ++a4;
            if (a != 0) {
                break;
            }
        }
    }
    
    void j() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: aload_0         /* a */
        //     5: getfield        com/sun/jna/z/a/b/f.c:Z
        //     8: iload_1         /* a */
        //     9: ifne            33
        //    12: ifne            102
        //    15: goto            19
        //    18: athrow         
        //    19: aload_0         /* a */
        //    20: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //    23: invokevirtual   com/sun/jna/z/a/b/e.a:()Ljava/lang/String;
        //    26: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    29: goto            33
        //    32: athrow         
        //    33: istore_2        /* a */
        //    34: iload_2         /* a */
        //    35: iload_1         /* a */
        //    36: ifne            47
        //    39: ifle            98
        //    42: goto            46
        //    45: athrow         
        //    46: iconst_0       
        //    47: istore_3        /* a */
        //    48: iload_3         /* a */
        //    49: iload_2         /* a */
        //    50: if_icmpge       98
        //    53: aload_0         /* a */
        //    54: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //    57: invokevirtual   com/sun/jna/z/a/b/e.a:()Ljava/lang/String;
        //    60: astore          a
        //    62: aload_0         /* a */
        //    63: getfield        com/sun/jna/z/a/b/f.a:Lcom/sun/jna/z/a/b/e;
        //    66: invokevirtual   com/sun/jna/z/a/b/e.a:()Ljava/lang/String;
        //    69: astore          a
        //    71: aload_0         /* a */
        //    72: getfield        com/sun/jna/z/a/b/f.d:Ljava/util/Properties;
        //    75: aload           a
        //    77: aload           a
        //    79: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //    82: pop            
        //    83: iinc            a, 1
        //    86: iload_1         /* a */
        //    87: ifne            342
        //    90: iload_1         /* a */
        //    91: ifeq            48
        //    94: goto            98
        //    97: athrow         
        //    98: iload_1         /* a */
        //    99: ifeq            342
        //   102: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //   105: getfield        com/sun/jna/z/a/h.a:Lcom/sun/jna/z/a/d/e;
        //   108: getfield        com/sun/jna/z/a/d/e.a:Ljava/util/Map;
        //   111: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   116: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   121: astore_2       
        //   122: aload_2        
        //   123: invokeinterface java/util/Iterator.hasNext:()Z
        //   128: ifeq            337
        //   131: aload_2        
        //   132: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   137: checkcast       Lcom/sun/jna/z/a/d/c;
        //   140: astore_3        /* a */
        //   141: iload_1         /* a */
        //   142: ifne            342
        //   145: aload_3         /* a */
        //   146: invokevirtual   com/sun/jna/z/a/d/c.k:()[Lcom/sun/jna/z/a/e/a/a/a/a/k;
        //   149: iload_1         /* a */
        //   150: ifne            172
        //   153: goto            157
        //   156: athrow         
        //   157: ifnull          333
        //   160: goto            164
        //   163: athrow         
        //   164: aload_3         /* a */
        //   165: invokevirtual   com/sun/jna/z/a/d/c.k:()[Lcom/sun/jna/z/a/e/a/a/a/a/k;
        //   168: goto            172
        //   171: athrow         
        //   172: astore          4
        //   174: aload           4
        //   176: arraylength    
        //   177: istore          5
        //   179: iconst_0       
        //   180: istore          a
        //   182: iload           a
        //   184: iload           5
        //   186: if_icmpge       333
        //   189: aload           4
        //   191: iload           a
        //   193: aaload         
        //   194: astore          a
        //   196: iload_1         /* a */
        //   197: ifne            329
        //   200: aload           a
        //   202: instanceof      Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //   205: iload_1         /* a */
        //   206: ifne            128
        //   209: goto            213
        //   212: athrow         
        //   213: ifeq            326
        //   216: aload           a
        //   218: checkcast       Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //   221: astore          a
        //   223: iload_1         /* a */
        //   224: ifne            329
        //   227: aload_0         /* a */
        //   228: getfield        com/sun/jna/z/a/b/f.d:Ljava/util/Properties;
        //   231: new             Ljava/lang/StringBuilder;
        //   234: dup            
        //   235: invokespecial   java/lang/StringBuilder.<init>:()V
        //   238: aload_3         /* a */
        //   239: invokevirtual   com/sun/jna/z/a/d/c.a:()Ljava/lang/String;
        //   242: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   245: ldc             ":"
        //   247: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   250: aload           a
        //   252: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.b:()Ljava/lang/String;
        //   257: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   260: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   263: invokevirtual   java/util/Properties.containsKey:(Ljava/lang/Object;)Z
        //   266: ifeq            326
        //   269: goto            273
        //   272: athrow         
        //   273: aload           a
        //   275: aload_0         /* a */
        //   276: getfield        com/sun/jna/z/a/b/f.d:Ljava/util/Properties;
        //   279: new             Ljava/lang/StringBuilder;
        //   282: dup            
        //   283: invokespecial   java/lang/StringBuilder.<init>:()V
        //   286: aload_3         /* a */
        //   287: invokevirtual   com/sun/jna/z/a/d/c.a:()Ljava/lang/String;
        //   290: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   293: ldc             ":"
        //   295: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   298: aload           a
        //   300: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.b:()Ljava/lang/String;
        //   305: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   308: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   311: invokevirtual   java/util/Properties.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   314: invokestatic    java/lang/Double.parseDouble:(Ljava/lang/String;)D
        //   317: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:(D)V
        //   322: goto            326
        //   325: athrow         
        //   326: iinc            a, 1
        //   329: iload_1         /* a */
        //   330: ifeq            182
        //   333: iload_1         /* a */
        //   334: ifeq            122
        //   337: aload_0         /* a */
        //   338: aconst_null    
        //   339: putfield        com/sun/jna/z/a/b/f.d:Ljava/util/Properties;
        //   342: goto            350
        //   345: astore_2        /* a */
        //   346: aload_2         /* a */
        //   347: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   350: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  227    322    325    326    Ljava/lang/Exception;
        //  223    269    272    273    Ljava/lang/Exception;
        //  196    209    212    213    Ljava/lang/Exception;
        //  157    168    171    172    Ljava/lang/Exception;
        //  145    160    163    164    Ljava/lang/Exception;
        //  141    153    156    157    Ljava/lang/Exception;
        //  71     94     97     98     Ljava/lang/Exception;
        //  34     42     45     46     Ljava/lang/Exception;
        //  12     29     32     33     Ljava/lang/Exception;
        //  4      15     18     19     Ljava/lang/Exception;
        //  4      342    345    350    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0157:
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
    
    void k() {
        final int a = com.sun.jna.z.a.b.e.e;
        try {
            Label_0102: {
                int n = 0;
                Label_0019: {
                    boolean b;
                    try {
                        b = ((n = (this.c ? 1 : 0)) != 0);
                        if (a != 0) {
                            break Label_0019;
                        }
                        if (!b) {
                            break Label_0019;
                        }
                        break Label_0102;
                    }
                    catch (Exception ex) {
                        throw ex;
                    }
                    try {
                        if (b) {
                            break Label_0102;
                        }
                        n = Integer.parseInt(this.a.a());
                    }
                    catch (Exception ex2) {
                        throw ex2;
                    }
                }
                final int a2 = n;
                Label_0098: {
                    int n2 = 0;
                    Label_0047: {
                        try {
                            n2 = a2;
                            if (a != 0) {
                                break Label_0047;
                            }
                            if (n2 <= 0) {
                                break Label_0098;
                            }
                        }
                        catch (Exception ex3) {
                            throw ex3;
                        }
                    }
                    int a3 = n2;
                    while (a3 < a2) {
                        final String a4 = this.a.a();
                        final String a5 = this.a.a();
                        try {
                            this.e.setProperty(a4, a5);
                            ++a3;
                            if (a != 0) {
                                return;
                            }
                            if (a == 0) {
                                continue;
                            }
                        }
                        catch (Exception ex4) {
                            throw ex4;
                        }
                        break;
                    }
                }
                if (a == 0) {
                    return;
                }
            }
            final n[] b2 = i.f.c.b();
            final int length = b2.length;
            int i = 0;
        Label_0423:
            while (true) {
                while (i < length) {
                    final n a6 = b2[i];
                    Label_0418: {
                        Label_0415: {
                            Label_0143: {
                                try {
                                    if (a != 0) {
                                        break Label_0418;
                                    }
                                    final f f = this;
                                    final f f2 = this;
                                    final int n3 = a;
                                    if (n3 == 0) {
                                        break Label_0143;
                                    }
                                    break Label_0423;
                                }
                                catch (Exception ex5) {
                                    throw ex5;
                                }
                                try {
                                    final f f = this;
                                    final f f2 = this;
                                    final int n3 = a;
                                    if (n3 != 0) {
                                        break Label_0423;
                                    }
                                    if (!f2.e.containsKey(a6.a() + "X")) {
                                        break Label_0415;
                                    }
                                }
                                catch (Exception ex6) {
                                    throw ex6;
                                }
                            }
                            final int a7 = Integer.parseInt(this.e.getProperty(a6.a() + "X"));
                            final int a8 = Integer.parseInt(this.e.getProperty(a6.a() + "Y"));
                            final Properties e = this.e;
                            final StringBuilder append = new StringBuilder().append(a6.a());
                            final String[] a9 = com.sun.jna.z.a.b.f.g;
                            final boolean a10 = Boolean.parseBoolean(e.getProperty(append.append(a9[0]).toString()));
                            boolean boolean1 = false;
                            Label_0377: {
                                Label_0376: {
                                    Label_0373: {
                                        Label_0361: {
                                            boolean e2 = false;
                                            Label_0350: {
                                                boolean b3;
                                                try {
                                                    b3 = (e2 = (boolean1 = Boolean.parseBoolean(this.e.getProperty(a6.a() + a9[2]))));
                                                    if (a != 0) {
                                                        break Label_0361;
                                                    }
                                                    if (b3) {
                                                        break Label_0350;
                                                    }
                                                    break Label_0376;
                                                }
                                                catch (Exception ex7) {
                                                    throw ex7;
                                                }
                                                try {
                                                    if (!b3) {
                                                        break Label_0376;
                                                    }
                                                    boolean1 = (e2 = a6.e());
                                                }
                                                catch (Exception ex8) {
                                                    throw ex8;
                                                }
                                            }
                                            try {
                                                if (a != 0) {
                                                    break Label_0373;
                                                }
                                                if (!e2) {
                                                    break Label_0376;
                                                }
                                            }
                                            catch (Exception ex9) {
                                                throw ex9;
                                            }
                                        }
                                        boolean1 = true;
                                    }
                                    break Label_0377;
                                }
                                boolean1 = false;
                            }
                            final boolean a11 = boolean1;
                            a6.b(a7);
                            a6.c(a8);
                            a6.b(a10);
                            a6.f(a11);
                        }
                        ++i;
                    }
                    if (a != 0) {
                        break;
                    }
                    continue;
                    f f = null;
                    f.e = null;
                    return;
                }
                final f f = this;
                continue Label_0423;
            }
        }
        catch (Exception a12) {
            a12.printStackTrace();
        }
    }
    
    void l() {
        final int a = com.sun.jna.z.a.b.e.e;
        try {
            Label_0102: {
                int n = 0;
                Label_0019: {
                    boolean b;
                    try {
                        b = ((n = (this.c ? 1 : 0)) != 0);
                        if (a != 0) {
                            break Label_0019;
                        }
                        if (!b) {
                            break Label_0019;
                        }
                        break Label_0102;
                    }
                    catch (Exception ex) {
                        throw ex;
                    }
                    try {
                        if (b) {
                            break Label_0102;
                        }
                        n = Integer.parseInt(this.a.a());
                    }
                    catch (Exception ex2) {
                        throw ex2;
                    }
                }
                final int a2 = n;
                Label_0098: {
                    int n2 = 0;
                    Label_0047: {
                        try {
                            n2 = a2;
                            if (a != 0) {
                                break Label_0047;
                            }
                            if (n2 <= 0) {
                                break Label_0098;
                            }
                        }
                        catch (Exception ex3) {
                            throw ex3;
                        }
                    }
                    int a3 = n2;
                    while (a3 < a2) {
                        final String a4 = this.a.a();
                        final String a5 = this.a.a();
                        try {
                            this.f.setProperty(a4, a5);
                            ++a3;
                            if (a != 0) {
                                return;
                            }
                            if (a == 0) {
                                continue;
                            }
                        }
                        catch (Exception ex4) {
                            throw ex4;
                        }
                        break;
                    }
                }
                if (a == 0) {
                    return;
                }
            }
            for (final com.sun.jna.z.a.e.a.a.a.a.i a6 : h.d.a.c()) {
                Label_0160: {
                    try {
                        if (a != 0) {
                            return;
                        }
                        final f f = this;
                        final Properties properties = f.f;
                        final com.sun.jna.z.a.e.a.a.a.a.i i = a6;
                        final String s = i.b();
                        final boolean b2 = properties.containsKey(s);
                        if (b2) {
                            break Label_0160;
                        }
                        break Label_0160;
                    }
                    catch (Exception ex5) {
                        throw ex5;
                    }
                    try {
                        final f f = this;
                        final Properties properties = f.f;
                        final com.sun.jna.z.a.e.a.a.a.a.i i = a6;
                        final String s = i.b();
                        final boolean b2 = properties.containsKey(s);
                        if (b2) {
                            a6.e(Boolean.parseBoolean(this.f.getProperty(a6.b())));
                        }
                    }
                    catch (Exception ex6) {
                        throw ex6;
                    }
                }
                if (a != 0) {
                    break;
                }
            }
            final com.sun.jna.z.a.e.a.a.a.h a7 = i.f.c;
            f f8 = null;
            Label_0432: {
                Label_0399: {
                    boolean b5 = false;
                    Label_0374: {
                        Label_0342: {
                            boolean containsKey = false;
                            Label_0331: {
                                Label_0283: {
                                    boolean b4 = false;
                                    Label_0272: {
                                        Label_0224: {
                                            boolean b3;
                                            try {
                                                final Properties f2 = this.f;
                                                final String[] a8 = com.sun.jna.z.a.b.f.g;
                                                b3 = (b4 = (containsKey = (b5 = f2.containsKey(a8[3]))));
                                                if (a != 0) {
                                                    break Label_0272;
                                                }
                                                if (b3) {
                                                    break Label_0224;
                                                }
                                                break Label_0224;
                                            }
                                            catch (Exception ex7) {
                                                throw ex7;
                                            }
                                            try {
                                                if (b3) {
                                                    final com.sun.jna.z.a.e.a.a.a.a.i j = a7.j;
                                                    final Properties f3 = this.f;
                                                    final String[] a8 = com.sun.jna.z.a.b.f.g;
                                                    j.e(Boolean.parseBoolean(f3.getProperty(a8[3])));
                                                }
                                            }
                                            catch (Exception ex8) {
                                                throw ex8;
                                            }
                                        }
                                        final Properties f4 = this.f;
                                        final String[] a8 = com.sun.jna.z.a.b.f.g;
                                        containsKey = (b4 = (b5 = f4.containsKey(a8[5])));
                                        try {
                                            if (a != 0) {
                                                break Label_0331;
                                            }
                                            if (b4) {
                                                break Label_0283;
                                            }
                                            break Label_0283;
                                        }
                                        catch (Exception ex9) {
                                            throw ex9;
                                        }
                                    }
                                    try {
                                        if (b4) {
                                            final com.sun.jna.z.a.e.a.a.a.a.i k = a7.k;
                                            final Properties f5 = this.f;
                                            final String[] a8 = com.sun.jna.z.a.b.f.g;
                                            k.e(Boolean.parseBoolean(f5.getProperty(a8[5])));
                                        }
                                    }
                                    catch (Exception ex10) {
                                        throw ex10;
                                    }
                                }
                                final Properties f6 = this.f;
                                final String[] a8 = com.sun.jna.z.a.b.f.g;
                                b5 = (containsKey = f6.containsKey(a8[1]));
                                try {
                                    if (a != 0) {
                                        break Label_0399;
                                    }
                                    if (containsKey) {
                                        break Label_0342;
                                    }
                                    break Label_0374;
                                }
                                catch (Exception ex11) {
                                    throw ex11;
                                }
                            }
                            try {
                                if (containsKey) {
                                    final com.sun.jna.z.a.e.a.a.a.a.i l = a7.l;
                                    final Properties f7 = this.f;
                                    final String[] a8 = com.sun.jna.z.a.b.f.g;
                                    l.e(Boolean.parseBoolean(f7.getProperty(a8[1])));
                                }
                            }
                            catch (Exception ex12) {
                                throw ex12;
                            }
                        }
                        try {
                            f8 = this;
                            if (a != 0) {
                                break Label_0432;
                            }
                            final Properties f9 = this.f;
                            final String[] a8 = com.sun.jna.z.a.b.f.g;
                            b5 = f9.containsKey(a8[6]);
                        }
                        catch (Exception ex13) {
                            throw ex13;
                        }
                    }
                    try {
                        if (b5) {
                            a7.m.e(Boolean.parseBoolean(this.f.getProperty(com.sun.jna.z.a.b.f.g[6])));
                        }
                    }
                    catch (Exception ex14) {
                        throw ex14;
                    }
                }
                f8 = this;
            }
            f8.f = null;
        }
        catch (Exception a9) {
            a9.printStackTrace();
        }
    }
    
    static {
        final String[] g2 = new String[7];
        int n = 0;
        String s;
        int n2 = (s = "o\u00da\u00ee\u0007\u00f3x\u0005k\u00d6\u00e1\u0004\u00e5\tr\u00da\u00ee\u0000\u00fbu\u0094z\u00d7\u0007o\u00df\u00e1\u0010\u00f3n\u009d\u0007i\u00da\u00f3\u0000\u00f4p\u008b").length();
        int n3 = 6;
        int n4 = -1;
    Label_0024:
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
                        Label_0248: {
                            if (n7 > 1) {
                                break Label_0248;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u001f';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '³';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u0080';
                                        break;
                                    }
                                    case 3: {
                                        c2 = 'i';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u0096';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u001c';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00ee';
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
                            g2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0024;
                            }
                            n2 = (s = "r\u00dc\u00e2\u001a\u000fl\u00db\u00ef\u001c\u00fax p\u00c7\u00c1\u001d\u00e2}\u008dt").length();
                            n3 = 4;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            g2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0024;
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
        g = g2;
    }
}
