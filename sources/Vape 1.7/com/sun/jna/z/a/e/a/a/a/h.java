package com.sun.jna.z.a.e.a.a.a;

import java.util.concurrent.atomic.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.c.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import java.util.*;
import java.awt.*;

public final class h extends g
{
    private final AtomicBoolean d;
    public static Color e;
    public static Color f;
    final Color g;
    final Color h;
    n i;
    public i j;
    public i k;
    public i l;
    public i m;
    public n n;
    public n o;
    public n p;
    public static boolean q;
    private static final String[] r;
    
    public h() {
        this.g = new Color(10, 10, 10, 255);
        final int c = com.sun.jna.z.a.e.a.a.a.g.c ? 1 : 0;
        this.h = new Color(0, 0, 0, 130);
        final String[] a = com.sun.jna.z.a.e.a.a.a.h.r;
        this.i = new a(a[5]);
        final int a2 = c;
        this.j = new d(a[9]);
        this.k = new d(a[7]);
        this.l = new d(a[3]);
        this.m = new d(a[11]);
        this.n = new a(a[2]);
        this.o = new q(this, a[6]);
        this.p = new r(this, a[4]);
        this.d = new AtomicBoolean();
        if (a2 != 0) {
            int a3 = com.sun.jna.z.a.i.g;
            com.sun.jna.z.a.i.g = ++a3;
        }
    }
    
    public static float a() {
        Minecraft.func_71410_x();
        final int c = g.c ? 1 : 0;
        final ScaledResolution a = new ScaledResolution(Minecraft.func_71410_x(), Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        final int a2 = c;
        final int a3 = a.func_78325_e();
        int n3;
        int n2;
        final int n = n2 = (n3 = a3);
        int n6;
        int n5;
        final int n4 = n5 = (n6 = 4);
        if (a2 == 0) {
            if (n == n4) {
                final double n7 = 0.5;
                return (float)n7;
            }
            final int n8;
            n2 = (n8 = (n3 = a3));
            final int n9;
            n5 = (n9 = (n6 = 3));
        }
        if (a2 == 0) {
            if (n == n4) {
                final double n7 = 0.66666;
                return (float)n7;
            }
            n3 = (n2 = a3);
            n6 = (n5 = 2);
        }
        int n10 = 0;
        Label_0095: {
            if (a2 == 0) {
                if (n2 == n5) {
                    final double n7 = 1.0;
                    return (float)n7;
                }
                n10 = (n3 = a3);
                if (a2 != 0) {
                    break Label_0095;
                }
                n6 = 1;
            }
            if (n3 == n6) {
                final double n7 = 2.0;
                return (float)n7;
            }
            n10 = a3;
        }
        final double n7 = (n10 == 0) ? 0.5 : 1.0;
        return (float)n7;
    }
    
    @Override
    public void a() {
        final int a = com.sun.jna.z.a.e.a.a.a.g.c ? 1 : 0;
        h h = this;
        if (a == 0) {
            if (!this.d.compareAndSet(false, true)) {
                return;
            }
            h = this;
        }
        h.h();
        final Map<b, s> a2 = new HashMap<b, s>();
        while (true) {
            for (final c a3 : com.sun.jna.z.a.h.d.a.a.values()) {
                s a4 = a2.get(a3.i());
                final n n;
                final s s = (s)(n = a4);
                if (a == 0) {
                    if (s == null) {
                        String a5 = a3.i().name().toLowerCase();
                        a5 = Character.toUpperCase(a5.charAt(0)) + a5.substring(1);
                        a4 = new s(this, a5, null);
                        a4.a(this.c());
                        a4.a(new e(1, 0));
                        a4.c(true);
                        a4.h(false);
                        a4.f(true);
                        a4.e(false);
                        final String a21 = a4.a();
                        if (a == 0) {
                            final String[] a6 = com.sun.jna.z.a.e.a.a.a.h.r;
                            if (!a21.equals(a6[1])) {
                                this.a(a4);
                            }
                            a2.put(a3.i(), a4);
                        }
                    }
                    final c a7 = a3;
                    final com.sun.jna.z.a.e.a.a.a.a.g a8 = new t(this, a7);
                    a8.a(a7.k());
                    a8.a(new l(this, a7));
                    final Color a9 = new Color(0, 255, 255, 255);
                    a8.b(a7.toString());
                    a4.a(a8, com.sun.jna.z.a.e.a.a.a.c.a.FILL);
                    final k[] k = a7.k();
                    Label_0474: {
                        if (a == 0) {
                            if (k == null) {
                                break Label_0474;
                            }
                            a7.k();
                        }
                        final k[] a10 = k;
                        final int a11 = a10.length;
                        int a12 = 0;
                        while (true) {
                            while (a12 < a11) {
                                final k a13 = a10[a12];
                                a8.k().a(a13, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
                                a8.k().c(a13);
                                ++a12;
                                if (a != 0) {
                                    a8.b(a8.b() + " ");
                                    break Label_0474;
                                }
                                if (a != 0) {
                                    break;
                                }
                            }
                            a8.b(true);
                            a8.a(false);
                            continue;
                        }
                    }
                    if (a != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    n.d(130);
                    this.b();
                    final Minecraft a14 = Minecraft.func_71410_x();
                    final Dimension a15 = this.l();
                    int a16 = a15.width + 10;
                    int a17 = 5;
                    final int field_74335_Z;
                    int a18 = field_74335_Z = a14.field_71474_y.field_74335_Z;
                    if (a == 0 && field_74335_Z == 0) {
                        a18 = 1000;
                        goto Label_0556;
                    }
                    int a19 = field_74335_Z;
                    while (a19 < a18) {
                        final int n2 = a14.field_71443_c / (a19 + 1);
                        final int n3 = 320;
                        if (a == 0) {
                            if (n2 < n3) {
                                break;
                            }
                            final int n4 = a14.field_71440_d / (a19 + 1);
                        }
                        if (n2 < n3) {
                            break;
                        }
                        ++a19;
                        if (a != 0) {
                            break;
                        }
                    }
                    final n[] b = this.b();
                    final int a11 = b.length;
                    int a12 = 0;
                    while (true) {
                        while (a12 < a11) {
                            final n a20 = b[a12];
                            Label_0775: {
                                if (a == 0) {
                                    final n n5 = a20;
                                    if (a != 0) {
                                        n5.d(100);
                                        return;
                                    }
                                    final String a22 = n5.a();
                                    final String[] a6 = com.sun.jna.z.a.e.a.a.a.h.r;
                                    Label_0772: {
                                        if (a22 != a6[5]) {
                                            final n n6 = a20;
                                            if (a == 0) {
                                                if (n6.a() == a6[4]) {
                                                    break Label_0772;
                                                }
                                                a20.b(a16);
                                            }
                                            n6.c(a17);
                                            a16 += a20.f() + 10;
                                            a16 += a15.width + 10;
                                            if (a != 0) {
                                                break Label_0775;
                                            }
                                            if (a16 + a15.width + 5 > a14.field_71443_c / a19) {
                                                a16 = a15.width + 10;
                                                a17 += a15.height + 5;
                                            }
                                        }
                                    }
                                    ++a12;
                                }
                            }
                            if (a != 0) {
                                break;
                            }
                        }
                        this.i.b(5);
                        this.i.c(40);
                        this.i.f(false);
                        this.p.b(5);
                        this.p.c(5);
                        final n o = this.o;
                        continue;
                    }
                }
            }
            this.g();
            this.k();
            this.c();
            this.j();
            n n = this.n;
            continue;
        }
    }
    
    private void c() {
        final int c = com.sun.jna.z.a.e.a.a.a.g.c ? 1 : 0;
        final com.sun.jna.z.a.e.a.a.a.e.e a = this.c();
        this.i.a(a);
        final int a2 = c;
        this.i.h(false);
        final n[] a3 = this.b();
        final int a4 = a3.length;
        int a5 = 0;
        while (a5 < a4) {
            final n a6 = a3[a5];
            final com.sun.jna.z.a.e.a.a.a.a.g a7 = new u(this, a6);
            a7.a(new m(this, a6));
            a7.d(a6.p());
            this.i.a(a7, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
            ++a5;
            if (a2 != 0) {
                return;
            }
            if (a2 != 0) {
                break;
            }
        }
        this.i.d();
        this.i.c(true);
        this.i.f(true);
        this.i.e(false);
        this.a(this.i);
    }
    
    private void g() {
        final com.sun.jna.z.a.e.a.a.a.e.e a = this.c();
        final String[] a2 = com.sun.jna.z.a.e.a.a.a.h.r;
        final n a3 = new a(a2[10]);
        a3.a(a);
        a3.h(false);
        a3.a(this.j, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a3.a(this.k, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a3.a(this.l, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a3.a(this.m, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        final com.sun.jna.z.a.e.a.a.a.a.g a4 = new com.sun.jna.z.a.e.a.a.a.a.a.c(a2[13]);
        a4.a(new com.sun.jna.z.a.e.a.a.a.n(this, a4));
        a4.a(this.c());
        a3.a(a4, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        this.k.e(true);
        this.j.e(true);
        a3.d();
        a3.c(true);
        a3.f(true);
        a3.e(false);
        this.a(a3);
    }
    
    private void h() {
        final com.sun.jna.z.a.e.a.a.a.e.e a = this.c();
        this.n.a(a);
        this.n.h(false);
        final Dimension a2 = a.a(this.n).b(this.n);
        this.n.d(a2.width);
        this.n.e(a2.height);
        this.n.d();
        this.n.c(true);
        this.n.g(false);
        this.n.f(false);
        this.n.e(false);
        this.n.b(false);
        this.a.add(this.n);
    }
    
    private void i() {
        final com.sun.jna.z.a.e.a.a.a.e.e a = this.c();
        this.o.a(a);
        this.o.h(false);
        final Dimension a2 = a.a(this.o).b(this.o);
        this.o.d(a2.width);
        this.o.e(a2.height);
        this.o.d();
        this.o.c(true);
        this.o.g(false);
        this.o.e(true);
        this.o.b(false);
        this.a.add(this.o);
    }
    
    public void j() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/sun/jna/z/a/e/a/a/a/h.c:()Lcom/sun/jna/z/a/e/a/a/a/e/e;
        //     4: checkcast       Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //     7: astore_2       
        //     8: getstatic       com/sun/jna/z/a/e/a/a/a/g.c:Z
        //    11: aload_0         /* a */
        //    12: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    15: aload_2         /* a */
        //    16: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.a:(Lcom/sun/jna/z/a/e/a/a/a/e/e;)V
        //    21: istore_1        /* a */
        //    22: aload_0         /* a */
        //    23: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    26: iconst_0       
        //    27: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.h:(Z)V
        //    32: aload_2         /* a */
        //    33: aload_0         /* a */
        //    34: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    37: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //    40: aload_0         /* a */
        //    41: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    44: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.b:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Ljava/awt/Dimension;
        //    49: astore_3        /* a */
        //    50: aload_0         /* a */
        //    51: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    54: aload_3         /* a */
        //    55: getfield        java/awt/Dimension.width:I
        //    58: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:(I)V
        //    63: aload_0         /* a */
        //    64: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    67: aload_3         /* a */
        //    68: getfield        java/awt/Dimension.height:I
        //    71: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.e:(I)V
        //    76: aload_0         /* a */
        //    77: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    80: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:()V
        //    85: aload_0         /* a */
        //    86: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    89: iconst_1       
        //    90: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.c:(Z)V
        //    95: aload_0         /* a */
        //    96: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    99: iconst_0       
        //   100: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.g:(Z)V
        //   105: aload_0         /* a */
        //   106: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //   109: iconst_1       
        //   110: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.e:(Z)V
        //   115: aload_0         /* a */
        //   116: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //   119: iconst_1       
        //   120: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.b:(Z)V
        //   125: aload_0         /* a */
        //   126: aload_0         /* a */
        //   127: getfield        com/sun/jna/z/a/e/a/a/a/h.p:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //   130: invokevirtual   com/sun/jna/z/a/e/a/a/a/h.a:(Lcom/sun/jna/z/a/e/a/a/a/a/n;)V
        //   133: getstatic       com/sun/jna/z/a/i.g:I
        //   136: ifeq            151
        //   139: iload_1         /* a */
        //   140: ifeq            147
        //   143: iconst_0       
        //   144: goto            148
        //   147: iconst_1       
        //   148: putstatic       com/sun/jna/z/a/e/a/a/a/g.c:Z
        //   151: return         
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
    
    private void k() {
        final com.sun.jna.z.a.e.a.a.a.e.e a = this.c();
        final n a2 = new a(com.sun.jna.z.a.e.a.a.a.h.r[8]);
        a2.a(a);
        a2.h(false);
        final com.sun.jna.z.a.e.a.a.a.k a3 = new com.sun.jna.z.a.e.a.a.a.k();
        a2.a(a3.a, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a2.a(a3.b, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a2.a(a3.c, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a2.a(a3.d, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a2.a(a3.e, new com.sun.jna.z.a.e.a.a.a.c.d[0]);
        a2.c(50);
        final Dimension a4 = a.a(a2).b(a2);
        a2.d();
        a2.c(true);
        a2.f(true);
        this.a(a2);
    }
    
    @Override
    protected void b() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/sun/jna/z/a/e/a/a/a/h.c:()Lcom/sun/jna/z/a/e/a/a/a/e/e;
        //     4: astore_2       
        //     5: getstatic       com/sun/jna/z/a/e/a/a/a/g.c:Z
        //     8: aload_0         /* a */
        //     9: invokevirtual   com/sun/jna/z/a/e/a/a/a/h.b:()[Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    12: astore_3        /* a */
        //    13: istore_1        /* a */
        //    14: new             Lcom/sun/jna/z/a/e/a/a/a/a/a/c;
        //    17: dup            
        //    18: getstatic       com/sun/jna/z/a/e/a/a/a/h.r:[Ljava/lang/String;
        //    21: astore          a
        //    23: aload           a
        //    25: bipush          12
        //    27: aaload         
        //    28: invokespecial   com/sun/jna/z/a/e/a/a/a/a/a/c.<init>:(Ljava/lang/String;)V
        //    31: astore          a
        //    33: new             Lcom/sun/jna/z/a/e/a/a/a/a/a/c;
        //    36: dup            
        //    37: aload           a
        //    39: iconst_0       
        //    40: aaload         
        //    41: invokespecial   com/sun/jna/z/a/e/a/a/a/a/a/c.<init>:(Ljava/lang/String;)V
        //    44: astore          a
        //    46: aload_2         /* a */
        //    47: aload           a
        //    49: invokeinterface com/sun/jna/z/a/e/a/a/a/e/e.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //    54: aload           a
        //    56: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.b:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Ljava/awt/Dimension;
        //    61: astore          a
        //    63: aload_2         /* a */
        //    64: aload           a
        //    66: invokeinterface com/sun/jna/z/a/e/a/a/a/e/e.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //    71: aload           a
        //    73: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.b:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Ljava/awt/Dimension;
        //    78: astore          a
        //    80: aload           a
        //    82: getfield        java/awt/Dimension.width:I
        //    85: aload           a
        //    87: getfield        java/awt/Dimension.width:I
        //    90: invokestatic    java/lang/Math.max:(II)I
        //    93: istore          a
        //    95: aload           a
        //    97: getfield        java/awt/Dimension.height:I
        //   100: aload           a
        //   102: getfield        java/awt/Dimension.height:I
        //   105: invokestatic    java/lang/Math.max:(II)I
        //   108: istore          a
        //   110: aload_3         /* a */
        //   111: astore          a
        //   113: aload           a
        //   115: arraylength    
        //   116: istore          a
        //   118: iconst_0       
        //   119: istore          a
        //   121: iload           a
        //   123: iload           a
        //   125: if_icmpge       242
        //   128: aload           a
        //   130: iload           a
        //   132: aaload         
        //   133: astore          a
        //   135: iload_1         /* a */
        //   136: ifne            247
        //   139: iload_1         /* a */
        //   140: ifne            238
        //   143: aload           a
        //   145: instanceof      Lcom/sun/jna/z/a/e/a/a/a/s;
        //   148: ifeq            235
        //   151: aload           a
        //   153: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.b:()[Lcom/sun/jna/z/a/e/a/a/a/a/k;
        //   158: astore          a
        //   160: aload           a
        //   162: arraylength    
        //   163: istore          a
        //   165: iconst_0       
        //   166: istore          a
        //   168: iload           a
        //   170: iload           a
        //   172: if_icmpge       235
        //   175: aload           a
        //   177: iload           a
        //   179: aaload         
        //   180: astore          a
        //   182: iload_1         /* a */
        //   183: ifne            231
        //   186: aload           a
        //   188: instanceof      Lcom/sun/jna/z/a/e/a/a/a/a/g;
        //   191: iload_1         /* a */
        //   192: ifne            123
        //   195: ifeq            228
        //   198: aload           a
        //   200: iload_1         /* a */
        //   201: ifne            221
        //   204: instanceof      Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   207: ifne            228
        //   210: aload           a
        //   212: iload           a
        //   214: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.d:(I)V
        //   219: aload           a
        //   221: iload           a
        //   223: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.e:(I)V
        //   228: iinc            a, 1
        //   231: iload_1         /* a */
        //   232: ifeq            168
        //   235: iinc            a, 1
        //   238: iload_1         /* a */
        //   239: ifeq            121
        //   242: aload_0         /* a */
        //   243: invokespecial   com/sun/jna/z/a/e/a/a/a/h.l:()Ljava/awt/Dimension;
        //   246: pop            
        //   247: return         
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
    
    private Dimension l() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* a */
        //     4: invokevirtual   com/sun/jna/z/a/e/a/a/a/h.b:()[Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //     7: astore_2       
        //     8: istore_1        /* a */
        //     9: iconst_0       
        //    10: istore_3        /* a */
        //    11: iconst_0       
        //    12: istore          a
        //    14: aload_2         /* a */
        //    15: astore          a
        //    17: aload           a
        //    19: arraylength    
        //    20: istore          a
        //    22: iconst_0       
        //    23: istore          a
        //    25: iload           a
        //    27: iload           a
        //    29: if_icmpge       195
        //    32: aload           a
        //    34: iload           a
        //    36: aaload         
        //    37: astore          a
        //    39: aload           a
        //    41: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.a:()Lcom/sun/jna/z/a/e/a/a/a/e/e;
        //    46: aload           a
        //    48: invokeinterface com/sun/jna/z/a/e/a/a/a/e/e.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //    53: aload           a
        //    55: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.b:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Ljava/awt/Dimension;
        //    60: astore          a
        //    62: iload_3         /* a */
        //    63: aload           a
        //    65: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.f:()I
        //    70: invokestatic    java/lang/Math.max:(II)I
        //    73: istore_3        /* a */
        //    74: aload           a
        //    76: aload           a
        //    78: getfield        java/awt/Dimension.height:I
        //    81: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.e:(I)V
        //    86: aload           a
        //    88: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:()Z
        //    93: iload_1         /* a */
        //    94: ifne            204
        //    97: iload_1         /* a */
        //    98: ifne            186
        //   101: ifeq            176
        //   104: aload           a
        //   106: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.a:()Lcom/sun/jna/z/a/e/a/a/a/e/e;
        //   111: aload           a
        //   113: invokeinterface com/sun/jna/z/a/e/a/a/a/e/e.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //   118: aload           a
        //   120: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.e:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)[Ljava/awt/Rectangle;
        //   125: astore          a
        //   127: aload           a
        //   129: arraylength    
        //   130: istore          a
        //   132: iconst_0       
        //   133: istore          a
        //   135: iload           a
        //   137: iload           a
        //   139: if_icmpge       172
        //   142: aload           a
        //   144: iload           a
        //   146: aaload         
        //   147: astore          a
        //   149: iload           a
        //   151: aload           a
        //   153: getfield        java/awt/Rectangle.height:I
        //   156: invokestatic    java/lang/Math.max:(II)I
        //   159: istore          a
        //   161: iinc            a, 1
        //   164: iload_1         /* a */
        //   165: ifne            191
        //   168: iload_1         /* a */
        //   169: ifeq            135
        //   172: iload_1         /* a */
        //   173: ifeq            188
        //   176: iload           a
        //   178: aload           a
        //   180: getfield        java/awt/Dimension.height:I
        //   183: invokestatic    java/lang/Math.max:(II)I
        //   186: istore          a
        //   188: iinc            a, 1
        //   191: iload_1         /* a */
        //   192: ifeq            25
        //   195: aload_2         /* a */
        //   196: astore          a
        //   198: aload           a
        //   200: arraylength    
        //   201: istore          a
        //   203: iconst_0       
        //   204: istore          a
        //   206: iload           a
        //   208: iload           a
        //   210: if_icmpge       295
        //   213: aload           a
        //   215: iload           a
        //   217: aaload         
        //   218: astore          a
        //   220: iload_1         /* a */
        //   221: ifne            291
        //   224: aload           a
        //   226: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.a:()Ljava/lang/String;
        //   231: getstatic       com/sun/jna/z/a/e/a/a/a/h.r:[Ljava/lang/String;
        //   234: astore          a
        //   236: aload           a
        //   238: iconst_2       
        //   239: aaload         
        //   240: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   243: ifne            288
        //   246: aload           a
        //   248: iload_1         /* a */
        //   249: ifne            283
        //   252: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.a:()Ljava/lang/String;
        //   257: aload           a
        //   259: bipush          6
        //   261: aaload         
        //   262: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   265: ifeq            272
        //   268: iload_1         /* a */
        //   269: ifeq            288
        //   272: aload           a
        //   274: bipush          85
        //   276: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:(I)V
        //   281: aload           a
        //   283: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:()V
        //   288: iinc            a, 1
        //   291: iload_1         /* a */
        //   292: ifeq            206
        //   295: new             Ljava/awt/Dimension;
        //   298: dup            
        //   299: bipush          85
        //   301: iload           a
        //   303: invokespecial   java/awt/Dimension.<init>:(II)V
        //   306: areturn        
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
        final String[] r2 = new String[14];
        int n = 0;
        String s;
        int n2 = (s = "\u0087\u00db\u00e4\u00e1¥\u0011\u008f\u0004\u008d\u00dd\u00f9\u00e5\b\u0080\u00dd\u00fa\u00ed¦\u0013\u008e°\u0005\u0097\u00d7\u00f6\u00ed´\b\u0097\u00d7\u00ef\u00f4\u00e7:\u009fª\u0003\u0084\u00c7\u00fe\n\u0097\u00d7\u00ef\u00f4\u00e7/\u008b§\u00d3\u00e5\u0004\u008e\u00dd\u00f5\u00f3\u0004\u008a\u00dc\u00f1\u00ef\u0007\u0093\u00de\u00f6\u00f9¢\u000f\u0099\u0006\u0095\u00d3\u00fb\u00f5¢\u000e\u000f\u008c\u00dc\u00fb\u00f9\u00e7.\u009d¬\u00c0\u00f3¯\u0086\u0005\u008f°").length();
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
                        Label_0250: {
                            if (n7 > 1) {
                                break Label_0250;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u00c3';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '²';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u0097';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u0080';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u00c7';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '}';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00ea';
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
                            r2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "\u0086\u00dc\u00f6\u00e2«\u0018\r\u0090\u00cb\u00f9\u00e3\u00e7\u000e\u008f·\u00c6\u00fe\u00ee \u000e").length();
                            n3 = 6;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            r2[n++] = intern;
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
        r = r2;
        h.e = new Color(0, 240, 152);
        h.f = new Color(90, 90, 90, 70);
    }
}
