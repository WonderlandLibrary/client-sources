package md;

import com.sun.jna.z.a.e.a.a.a.a.*;
import com.sun.jna.z.a.d.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import java.util.*;
import org.lwjgl.opengl.*;
import com.sun.jna.z.a.e.a.a.a.f.*;
import net.minecraft.client.renderer.entity.*;
import java.awt.*;
import net.minecraft.block.*;

public class i extends c
{
    t n;
    boolean o;
    j p;
    CopyOnWriteArrayList<com.sun.jna.z.a.f.c> q;
    CopyOnWriteArrayList<k> r;
    Queue<com.sun.jna.z.a.f.c> s;
    k t;
    k u;
    k v;
    k w;
    k x;
    public static int y;
    public static boolean z;
    public static int A;
    public static int B;
    public static int C;
    public static boolean D;
    public static boolean E;
    public static int F;
    public static boolean G;
    public static boolean H;
    public static int I;
    public static boolean J;
    public static boolean K;
    public static int L;
    public static boolean M;
    public static int N;
    public static boolean O;
    public static boolean P;
    public static boolean Q;
    private static final String[] R;
    
    public i() {
        final String[] a = i.R;
        super(a[6], com.sun.jna.z.a.d.b.Render, -16737793);
        this.n = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[3], 60.0, 10.0, 100.0, 0.01, com.sun.jna.z.a.e.a.a.a.a.b.INTEGER);
        final int q = i.Q ? 1 : 0;
        this.q = new CopyOnWriteArrayList<com.sun.jna.z.a.f.c>();
        final int a2 = q;
        this.r = new CopyOnWriteArrayList<k>();
        this.s = new ConcurrentLinkedDeque<com.sun.jna.z.a.f.c>();
        this.t = new k(a[0], true, 56, -9379585, this.q);
        this.u = new k(a[1], false, 15, 15773043, this.q);
        this.v = new k(a[2], true, 14, 16772608, this.q);
        this.w = new k(a[5], false, 16, 2368548, this.q);
        this.x = new k(a[4], false, 11, 16742144, this.q);
        if (i.y != 0) {
            i.Q = (a2 == 0);
        }
    }
    
    @Override
    public void a(final float a) {
        final int q = i.Q ? 1 : 0;
        final int a2 = (int)this.n.a();
        final int a3 = q;
        for (final com.sun.jna.z.a.f.c a4 : this.q) {
            i i = this;
            Label_0135: {
                if (a3 == 0) {
                    if (this.c().func_70011_f((double)a4.a, (double)a4.b, (double)a4.c) > a2) {
                        break Label_0135;
                    }
                    i = this;
                }
                i.a(AxisAlignedBB.func_72330_a((double)a4.a, (double)a4.b, (double)a4.c, (double)(a4.a + 1), (double)(a4.b + 1), (double)(a4.c + 1)), a4.e);
            }
            if (a3 != 0) {
                break;
            }
        }
    }
    
    public void a(final AxisAlignedBB a, final int a) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        final int q = i.Q ? 1 : 0;
        GL11.glDepthMask(false);
        final Color a2 = a.a(a);
        GL11.glColor4d((double)(a2.getBlue() / 255.0f), (double)(a2.getGreen() / 255.0f), (double)(a2.getRed() / 255.0f), 1.0);
        com.sun.jna.z.a.i.f.e.b();
        final double a3 = a.field_72336_d - RenderManager.field_78725_b;
        final double a4 = a.field_72340_a - RenderManager.field_78725_b;
        final double a5 = a.field_72337_e - RenderManager.field_78726_c;
        final double a6 = a.field_72338_b - RenderManager.field_78726_c;
        final double a7 = a.field_72334_f - RenderManager.field_78723_d;
        final double a8 = a.field_72339_c - RenderManager.field_78723_d;
        com.sun.jna.z.a.i.f.e.a(a4, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a4, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a8);
        com.sun.jna.z.a.i.f.e.a(a3, a5, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a7);
        final int a9 = q;
        com.sun.jna.z.a.i.f.e.a(a3, a5, a7);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a5, a8);
        com.sun.jna.z.a.i.f.e.a(a3, a5, a7);
        com.sun.jna.z.a.i.f.e.a(a4, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a8);
        com.sun.jna.z.a.i.f.e.a(a4, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a4, a5, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a5, a7);
        com.sun.jna.z.a.i.f.e.a(a3, a6, a8);
        com.sun.jna.z.a.i.f.e.a(a3, a5, a8);
        com.sun.jna.z.a.i.f.e.c();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        if (a9 != 0) {
            int a10 = i.y;
            i.y = ++a10;
        }
    }
    
    @Override
    public void e() {
        final int q = i.Q ? 1 : 0;
        this.o = true;
        final int a = q;
        i i = this;
        if (a == 0) {
            if (this.p != null) {
                return;
            }
            i = this;
        }
        i.p = new j(this);
        new Thread(this.p).start();
    }
    
    @Override
    public void g() {
        this.o = false;
        final int a = i.Q ? 1 : 0;
        i i = this;
        if (a == 0) {
            if (this.p != null) {
                this.p.a = null;
            }
            this.q.clear();
            this.r.clear();
            i = this;
        }
        i.p = null;
    }
    
    public void m() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        md/i.o:Z
        //     8: ifeq            404
        //    11: ldc2_w          50
        //    14: invokestatic    java/lang/Thread.sleep:(J)V
        //    17: aload_0         /* a */
        //    18: getfield        md/i.s:Ljava/util/Queue;
        //    21: invokeinterface java/util/Queue.isEmpty:()Z
        //    26: ifne            111
        //    29: aload_0         /* a */
        //    30: getfield        md/i.s:Ljava/util/Queue;
        //    33: invokeinterface java/util/Queue.poll:()Ljava/lang/Object;
        //    38: checkcast       Lcom/sun/jna/z/a/f/c;
        //    41: astore_2        /* a */
        //    42: aload_0         /* a */
        //    43: aload_2         /* a */
        //    44: getfield        com/sun/jna/z/a/f/c.a:I
        //    47: aload_2         /* a */
        //    48: getfield        com/sun/jna/z/a/f/c.b:I
        //    51: aload_2         /* a */
        //    52: getfield        com/sun/jna/z/a/f/c.c:I
        //    55: invokevirtual   md/i.a:(III)Lcom/sun/jna/z/a/f/c;
        //    58: astore_3        /* a */
        //    59: iload_1         /* a */
        //    60: ifne            4
        //    63: aload_3         /* a */
        //    64: ifnonnull       107
        //    67: aload_0         /* a */
        //    68: aload_2         /* a */
        //    69: getfield        com/sun/jna/z/a/f/c.d:I
        //    72: invokevirtual   md/i.d:(I)Lcom/sun/jna/z/a/d/k;
        //    75: astore          a
        //    77: iload_1         /* a */
        //    78: ifne            103
        //    81: aload           a
        //    83: ifnull          107
        //    86: goto            90
        //    89: athrow         
        //    90: aload_0         /* a */
        //    91: getfield        md/i.q:Ljava/util/concurrent/CopyOnWriteArrayList;
        //    94: aload_2         /* a */
        //    95: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.add:(Ljava/lang/Object;)Z
        //    98: pop            
        //    99: goto            103
        //   102: athrow         
        //   103: goto            107
        //   106: athrow         
        //   107: iload_1         /* a */
        //   108: ifeq            17
        //   111: iconst_5       
        //   112: istore_2        /* a */
        //   113: iload_2         /* a */
        //   114: ineg           
        //   115: istore_3        /* a */
        //   116: iload_3         /* a */
        //   117: iload_2         /* a */
        //   118: if_icmpge       392
        //   121: iload_2         /* a */
        //   122: ineg           
        //   123: iload_1         /* a */
        //   124: ifne            8
        //   127: istore          a
        //   129: iload           a
        //   131: iload_2         /* a */
        //   132: if_icmpge       385
        //   135: iload_2         /* a */
        //   136: ineg           
        //   137: iload_1         /* a */
        //   138: ifne            117
        //   141: istore          a
        //   143: iload           a
        //   145: iload_2         /* a */
        //   146: if_icmpge       378
        //   149: aload_0         /* a */
        //   150: invokevirtual   md/i.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   153: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70165_t:D
        //   156: d2i            
        //   157: iload_3         /* a */
        //   158: iadd           
        //   159: istore          a
        //   161: aload_0         /* a */
        //   162: invokevirtual   md/i.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   165: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70163_u:D
        //   168: d2i            
        //   169: iload           a
        //   171: iadd           
        //   172: istore          a
        //   174: aload_0         /* a */
        //   175: invokevirtual   md/i.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   178: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70161_v:D
        //   181: d2i            
        //   182: iload           a
        //   184: iadd           
        //   185: istore          a
        //   187: aload_0         /* a */
        //   188: invokevirtual   md/i.b:()Lnet/minecraft/client/Minecraft;
        //   191: getfield        net/minecraft/client/Minecraft.field_71441_e:Lnet/minecraft/client/multiplayer/WorldClient;
        //   194: iload           a
        //   196: iload           a
        //   198: iload           a
        //   200: invokevirtual   net/minecraft/client/multiplayer/WorldClient.func_147439_a:(III)Lnet/minecraft/block/Block;
        //   203: invokestatic    net/minecraft/block/Block.func_149682_b:(Lnet/minecraft/block/Block;)I
        //   206: istore          a
        //   208: aload_0         /* a */
        //   209: iload           a
        //   211: iload           a
        //   213: iload           a
        //   215: invokevirtual   md/i.a:(III)Lcom/sun/jna/z/a/f/c;
        //   218: astore          a
        //   220: aload           a
        //   222: iload_1         /* a */
        //   223: ifne            41
        //   226: ifnull          235
        //   229: iconst_1       
        //   230: goto            236
        //   233: athrow         
        //   234: athrow         
        //   235: iconst_0       
        //   236: istore          a
        //   238: iload           a
        //   240: iload_1         /* a */
        //   241: ifne            314
        //   244: ifne            312
        //   247: goto            251
        //   250: athrow         
        //   251: aload_0         /* a */
        //   252: iload           a
        //   254: invokevirtual   md/i.d:(I)Lcom/sun/jna/z/a/d/k;
        //   257: astore          a
        //   259: iload_1         /* a */
        //   260: ifne            304
        //   263: aload           a
        //   265: ifnull          308
        //   268: goto            272
        //   271: athrow         
        //   272: aload_0         /* a */
        //   273: getfield        md/i.q:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   276: new             Lcom/sun/jna/z/a/f/c;
        //   279: dup            
        //   280: iload           a
        //   282: iload           a
        //   284: iload           a
        //   286: aload           a
        //   288: getfield        com/sun/jna/z/a/d/k.t:I
        //   291: iload           a
        //   293: invokespecial   com/sun/jna/z/a/f/c.<init>:(IIIII)V
        //   296: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.add:(Ljava/lang/Object;)Z
        //   299: pop            
        //   300: goto            304
        //   303: athrow         
        //   304: goto            308
        //   307: athrow         
        //   308: iload_1         /* a */
        //   309: ifeq            371
        //   312: iload           a
        //   314: iload_1         /* a */
        //   315: ifne            366
        //   318: aload           a
        //   320: getfield        com/sun/jna/z/a/f/c.d:I
        //   323: if_icmpne       357
        //   326: goto            330
        //   329: athrow         
        //   330: aload_0         /* a */
        //   331: invokevirtual   md/i.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   334: iload           a
        //   336: i2d            
        //   337: iload           a
        //   339: i2d            
        //   340: iload           a
        //   342: i2d            
        //   343: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_70011_f:(DDD)D
        //   346: ldc2_w          200.0
        //   349: dcmpl          
        //   350: ifle            371
        //   353: goto            357
        //   356: athrow         
        //   357: aload_0         /* a */
        //   358: getfield        md/i.q:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   361: aload           a
        //   363: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.remove:(Ljava/lang/Object;)Z
        //   366: pop            
        //   367: goto            371
        //   370: athrow         
        //   371: iinc            a, 1
        //   374: iload_1         /* a */
        //   375: ifeq            143
        //   378: iinc            a, 1
        //   381: iload_1         /* a */
        //   382: ifeq            129
        //   385: iinc            a, 1
        //   388: iload_1         /* a */
        //   389: ifeq            116
        //   392: goto            4
        //   395: astore_2        /* a */
        //   396: aload_2         /* a */
        //   397: invokevirtual   java/lang/InterruptedException.printStackTrace:()V
        //   400: iload_1         /* a */
        //   401: ifeq            4
        //   404: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  81     99     102    103    Ljava/lang/InterruptedException;
        //  77     86     89     90     Ljava/lang/InterruptedException;
        //  330    367    370    371    Ljava/lang/InterruptedException;
        //  314    326    329    330    Ljava/lang/InterruptedException;
        //  312    353    356    357    Ljava/lang/InterruptedException;
        //  263    300    303    304    Ljava/lang/InterruptedException;
        //  259    268    271    272    Ljava/lang/InterruptedException;
        //  259    304    307    308    Ljava/lang/InterruptedException;
        //  226    233    233    234    Ljava/lang/InterruptedException;
        //  220    234    234    235    Ljava/lang/InterruptedException;
        //  77     103    106    107    Ljava/lang/InterruptedException;
        //  238    247    250    251    Ljava/lang/InterruptedException;
        //  11     392    395    404    Ljava/lang/InterruptedException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0330:
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
    
    @Override
    public void h() {
        final int a = i.Q ? 1 : 0;
    Label_0004:
        while (true) {
            final boolean o = this.o;
            int i = 0;
        Label_0008:
            while (i != 0) {
                try {
                    final int a2 = (int)this.n.a();
                    Thread.sleep(3L);
                    final int a3 = (int)this.c().field_70165_t;
                    final int a4 = (int)this.c().field_70163_u;
                    final int a5 = (int)this.c().field_70161_v;
                    int a6 = -a2;
                    do {
                        int j = 0;
                    Label_0063:
                        while (j < a2) {
                            i = -a2;
                            if (a != 0) {
                                continue Label_0008;
                            }
                            int a7 = i;
                        Label_0215_Outer:
                            while (a7 < a2) {
                                j = (int)(-(a2 * 1.3));
                                if (a == 0) {
                                    int a8 = j;
                                Label_0215:
                                    while (true) {
                                        while (a8 < a2) {
                                            final int a9 = a3 + a6;
                                            final int a10 = a4 + a8;
                                            final int a11 = a5 + a7;
                                            final int a12 = Block.func_149682_b(this.b().field_71441_e.func_147439_a(a9, a10, a11));
                                            final k a13 = this.d(a12);
                                            Label_0208: {
                                                Label_0205: {
                                                    try {
                                                        Label_0162: {
                                                            try {
                                                                if (a != 0) {
                                                                    break Label_0215;
                                                                }
                                                                final int n = a;
                                                                if (n == 0) {
                                                                    break Label_0162;
                                                                }
                                                                break Label_0208;
                                                            }
                                                            catch (InterruptedException ex) {
                                                                throw ex;
                                                            }
                                                            try {
                                                                final int n = a;
                                                                if (n != 0) {
                                                                    break Label_0208;
                                                                }
                                                                if (a13 == null) {
                                                                    break Label_0205;
                                                                }
                                                            }
                                                            catch (InterruptedException ex2) {
                                                                throw ex2;
                                                            }
                                                        }
                                                        this.s.add(new com.sun.jna.z.a.f.c(a9, a10, a11, a13.t, a12));
                                                    }
                                                    catch (InterruptedException ex3) {
                                                        throw ex3;
                                                    }
                                                }
                                                ++a8;
                                            }
                                            if (a != 0) {
                                                break;
                                            }
                                            continue Label_0215_Outer;
                                            if (a != 0) {
                                                break Label_0215_Outer;
                                            }
                                            continue Label_0215_Outer;
                                        }
                                        ++a7;
                                        continue Label_0215;
                                    }
                                }
                                continue Label_0063;
                            }
                            ++a6;
                        }
                        break;
                    } while (a == 0);
                    continue Label_0004;
                }
                catch (InterruptedException a14) {
                    a14.printStackTrace();
                    if (a == 0) {
                        continue Label_0004;
                    }
                }
                break;
            }
            break;
        }
    }
    
    public k d(final int a) {
        final int q = i.Q ? 1 : 0;
        final Iterator<k> iterator = this.r.iterator();
        final int a2 = q;
        while (iterator.hasNext()) {
            final k a3 = iterator.next();
            int n;
            final boolean b = (n = (a3.a() ? 1 : 0)) != 0;
            Label_0060: {
                final k k;
                if (a2 == 0) {
                    if (!b) {
                        break Label_0060;
                    }
                    k = a3;
                    if (a2 != 0) {
                        return k;
                    }
                    n = k.s;
                }
                if (n == a) {
                    return k;
                }
            }
            if (a2 != 0) {
                break;
            }
        }
        return null;
    }
    
    public com.sun.jna.z.a.f.c a(final int a, final int a, final int a) {
        final int q = i.Q ? 1 : 0;
        final Iterator<com.sun.jna.z.a.f.c> iterator = this.q.iterator();
        final int a2 = q;
        while (iterator.hasNext()) {
            final com.sun.jna.z.a.f.c a3 = iterator.next();
            int n2;
            final int n = n2 = a3.a;
            int n3 = a;
            int n4 = a;
            Label_0081: {
                if (a2 == 0) {
                    if (n != a) {
                        break Label_0081;
                    }
                    final int b;
                    n2 = (b = a3.b);
                    n3 = a;
                    n4 = a;
                }
                final com.sun.jna.z.a.f.c c;
                if (a2 == 0) {
                    if (n != n4) {
                        break Label_0081;
                    }
                    c = a3;
                    if (a2 != 0) {
                        return c;
                    }
                    n2 = c.c;
                    n3 = a;
                }
                if (n2 != n3) {
                    break Label_0081;
                }
                return c;
            }
            if (a2 != 0) {
                break;
            }
        }
        return null;
    }
    
    @Override
    public com.sun.jna.z.a.e.a.a.a.a.k[] k() {
        this.r.add(this.t);
        this.r.add(this.u);
        this.r.add(this.v);
        this.r.add(this.w);
        this.r.add(this.x);
        return new com.sun.jna.z.a.e.a.a.a.a.k[] { this.x, this.w, this.v, this.u, this.t, this.n };
    }
    
    static {
        final String[] r = new String[7];
        int n = 0;
        String s;
        int n2 = (s = "\t\u00cf\f\u00ed\u0000\u0006¾\u0004\u0004\u00d4\u0002\u00ee\u0004\n\u00c9\u0001\u00e4\u0006\u001f\u00c7\t\u00e9\u001a\u001b\u0004\u0001\u00c7\u001b\u00e1").length();
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
                        Label_0247: {
                            if (n7 > 1) {
                                break Label_0247;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = 'M';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '¦';
                                        break;
                                    }
                                    case 2: {
                                        c2 = 'm';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u0080';
                                        break;
                                    }
                                    case 4: {
                                        c2 = 'o';
                                        break;
                                    }
                                    case 5: {
                                        c2 = 'h';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00da';
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
                            r[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "\u000e\u00c9\f\u00ec\u0006\u001e\u00c3\f\u00f2\f\u0000").length();
                            n3 = 4;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            r[n++] = intern;
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
        R = r;
    }
}
