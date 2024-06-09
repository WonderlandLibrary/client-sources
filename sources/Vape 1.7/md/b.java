package md;

import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.concurrent.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class b extends c
{
    g n;
    boolean o;
    EntityLivingBase p;
    t q;
    t r;
    com.sun.jna.z.a.e.a.a.a.a.i s;
    com.sun.jna.z.a.e.a.a.a.a.i t;
    com.sun.jna.z.a.e.a.a.a.a.i u;
    t v;
    private float w;
    private float x;
    private MouseFilter y;
    double z;
    double A;
    double B;
    double C;
    double D;
    double E;
    double F;
    public static int G;
    public static boolean H;
    public static int I;
    public static int J;
    public static int K;
    public static boolean L;
    public static boolean M;
    public static int N;
    public static boolean O;
    public static boolean P;
    public static int Q;
    public static boolean R;
    public static boolean S;
    public static int T;
    public static boolean U;
    public static int V;
    public static boolean W;
    public static boolean X;
    public static boolean Y;
    private static final String[] Z;
    
    public b() {
        final String[] a = b.Z;
        super(a[2], com.sun.jna.z.a.d.b.Combat, -327674);
        this.q = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[5], 3.0, 1.0, 5.0, 0.01, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        final int y = b.Y ? 1 : 0;
        this.r = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[6], 4.2, 0.0, 8.0, 0.01, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        this.s = new d(a[3]);
        this.t = new d(a[0]);
        this.u = new d(a[1]);
        final int a2 = y;
        this.v = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[4], 180.0, 0.0, 360.0, 1.0, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        this.y = new MouseFilter();
        if (b.G != 0) {
            b.Y = (a2 == 0);
        }
    }
    
    @Override
    public void h() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_1        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        md/b.o:Z
        //     8: ifeq            462
        //    11: lconst_1       
        //    12: invokestatic    java/lang/Thread.sleep:(J)V
        //    15: aload_0         /* a */
        //    16: invokevirtual   md/b.c:()Z
        //    19: iload_1         /* a */
        //    20: ifne            34
        //    23: ifne            30
        //    26: iload_1         /* a */
        //    27: ifeq            4
        //    30: aload_0         /* a */
        //    31: invokevirtual   md/b.e:()Z
        //    34: iload_1         /* a */
        //    35: ifne            66
        //    38: ifeq            49
        //    41: goto            45
        //    44: athrow         
        //    45: iload_1         /* a */
        //    46: ifeq            4
        //    49: aload_0         /* a */
        //    50: iload_1         /* a */
        //    51: ifne            94
        //    54: getfield        md/b.t:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //    57: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //    62: goto            66
        //    65: athrow         
        //    66: ifeq            93
        //    69: iconst_0       
        //    70: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //    73: ifne            93
        //    76: goto            80
        //    79: athrow         
        //    80: goto            84
        //    83: athrow         
        //    84: aload_0         /* a */
        //    85: aconst_null    
        //    86: putfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //    89: iload_1         /* a */
        //    90: ifeq            4
        //    93: aload_0         /* a */
        //    94: iload_1         /* a */
        //    95: ifne            178
        //    98: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   101: ifnull          177
        //   104: goto            108
        //   107: athrow         
        //   108: aload_0         /* a */
        //   109: iload_1         /* a */
        //   110: ifne            169
        //   113: aload_0         /* a */
        //   114: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   117: invokevirtual   md/b.a:(Lnet/minecraft/entity/Entity;)Z
        //   120: ifne            168
        //   123: goto            127
        //   126: athrow         
        //   127: goto            131
        //   130: athrow         
        //   131: aload_0         /* a */
        //   132: invokevirtual   md/b.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   135: aload_0         /* a */
        //   136: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   139: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_70032_d:(Lnet/minecraft/entity/Entity;)F
        //   142: f2d            
        //   143: aload_0         /* a */
        //   144: getfield        md/b.r:Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //   147: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:()D
        //   152: dcmpl          
        //   153: iload_1         /* a */
        //   154: ifne            186
        //   157: ifle            177
        //   160: goto            164
        //   163: athrow         
        //   164: goto            168
        //   167: athrow         
        //   168: aload_0         /* a */
        //   169: aconst_null    
        //   170: putfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   173: goto            177
        //   176: athrow         
        //   177: aload_0         /* a */
        //   178: getfield        md/b.t:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   181: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   186: iload_1         /* a */
        //   187: ifne            252
        //   190: ifeq            235
        //   193: goto            197
        //   196: athrow         
        //   197: iconst_0       
        //   198: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //   201: iload_1         /* a */
        //   202: ifne            252
        //   205: ifeq            235
        //   208: goto            212
        //   211: athrow         
        //   212: goto            216
        //   215: athrow         
        //   216: aload_0         /* a */
        //   217: iload_1         /* a */
        //   218: ifne            260
        //   221: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   224: ifnull          259
        //   227: goto            231
        //   230: athrow         
        //   231: goto            235
        //   234: athrow         
        //   235: aload_0         /* a */
        //   236: iload_1         /* a */
        //   237: ifne            275
        //   240: getfield        md/b.t:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   243: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   248: goto            252
        //   251: athrow         
        //   252: ifne            274
        //   255: goto            259
        //   258: athrow         
        //   259: aload_0         /* a */
        //   260: aload_0         /* a */
        //   261: invokevirtual   md/b.m:()Lnet/minecraft/entity/Entity;
        //   264: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   267: putfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   270: goto            274
        //   273: athrow         
        //   274: aload_0         /* a */
        //   275: iload_1         /* a */
        //   276: ifne            444
        //   279: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   282: ifnull          439
        //   285: goto            289
        //   288: athrow         
        //   289: aload_0         /* a */
        //   290: iload_1         /* a */
        //   291: ifne            444
        //   294: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //   297: getfield        net/minecraft/client/Minecraft.field_71462_r:Lnet/minecraft/client/gui/GuiScreen;
        //   300: ifnonnull       439
        //   303: goto            307
        //   306: athrow         
        //   307: goto            311
        //   310: athrow         
        //   311: aload_0         /* a */
        //   312: iload_1         /* a */
        //   313: ifne            444
        //   316: aload_0         /* a */
        //   317: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   320: invokevirtual   md/b.a:(Lnet/minecraft/entity/EntityLivingBase;)Z
        //   323: ifne            439
        //   326: goto            330
        //   329: athrow         
        //   330: goto            334
        //   333: athrow         
        //   334: aload_0         /* a */
        //   335: getfield        md/b.q:Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //   338: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:()D
        //   343: dstore_2        /* a */
        //   344: aload_0         /* a */
        //   345: iload_1         /* a */
        //   346: ifne            414
        //   349: getfield        md/b.a:Lnet/minecraft/client/Minecraft;
        //   352: getfield        net/minecraft/client/Minecraft.field_71476_x:Lnet/minecraft/util/MovingObjectPosition;
        //   355: ifnull          413
        //   358: goto            362
        //   361: athrow         
        //   362: aload_0         /* a */
        //   363: getfield        md/b.a:Lnet/minecraft/client/Minecraft;
        //   366: getfield        net/minecraft/client/Minecraft.field_71476_x:Lnet/minecraft/util/MovingObjectPosition;
        //   369: getfield        net/minecraft/util/MovingObjectPosition.field_72308_g:Lnet/minecraft/entity/Entity;
        //   372: astore          a
        //   374: iload_1         /* a */
        //   375: ifne            435
        //   378: aload           a
        //   380: ifnull          413
        //   383: goto            387
        //   386: athrow         
        //   387: aload           a
        //   389: aload_0         /* a */
        //   390: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   393: invokevirtual   net/minecraft/entity/Entity.equals:(Ljava/lang/Object;)Z
        //   396: ifeq            413
        //   399: goto            403
        //   402: athrow         
        //   403: goto            407
        //   406: athrow         
        //   407: dload_2         /* a */
        //   408: ldc2_w          0.5
        //   411: dmul           
        //   412: dstore_2        /* a */
        //   413: aload_0         /* a */
        //   414: aload_0         /* a */
        //   415: getfield        md/b.p:Lnet/minecraft/entity/EntityLivingBase;
        //   418: dload_2         /* a */
        //   419: ldc2_w          7.0
        //   422: ddiv           
        //   423: aload_0         /* a */
        //   424: getfield        md/b.s:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   427: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   432: invokevirtual   md/b.a:(Lnet/minecraft/entity/Entity;DZ)V
        //   435: iload_1         /* a */
        //   436: ifeq            454
        //   439: aload_0         /* a */
        //   440: goto            444
        //   443: athrow         
        //   444: new             Lnet/minecraft/util/MouseFilter;
        //   447: dup            
        //   448: invokespecial   net/minecraft/util/MouseFilter.<init>:()V
        //   451: putfield        md/b.y:Lnet/minecraft/util/MouseFilter;
        //   454: goto            4
        //   457: astore_2       
        //   458: iload_1         /* a */
        //   459: ifeq            4
        //   462: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  11     26     457    462    Ljava/lang/Exception;
        //  34     41     44     45     Ljava/lang/Exception;
        //  30     45     457    462    Ljava/lang/Exception;
        //  66     76     79     80     Ljava/lang/Exception;
        //  49     62     65     66     Ljava/lang/Exception;
        //  49     80     83     84     Ljava/lang/Exception;
        //  49     89     457    462    Ljava/lang/Exception;
        //  378    399    402    403    Ljava/lang/Exception;
        //  374    383    386    387    Ljava/lang/Exception;
        //  108    123    126    127    Ljava/lang/Exception;
        //  94     104    107    108    Ljava/lang/Exception;
        //  374    403    406    407    Ljava/lang/Exception;
        //  311    326    329    330    Ljava/lang/Exception;
        //  289    303    306    307    Ljava/lang/Exception;
        //  289    330    333    334    Ljava/lang/Exception;
        //  275    285    288    289    Ljava/lang/Exception;
        //  274    307    310    311    Ljava/lang/Exception;
        //  235    248    251    252    Ljava/lang/Exception;
        //  235    270    273    274    Ljava/lang/Exception;
        //  216    227    230    231    Ljava/lang/Exception;
        //  216    255    258    259    Ljava/lang/Exception;
        //  197    208    211    212    Ljava/lang/Exception;
        //  197    231    234    235    Ljava/lang/Exception;
        //  186    193    196    197    Ljava/lang/Exception;
        //  177    212    215    216    Ljava/lang/Exception;
        //  131    160    163    164    Ljava/lang/Exception;
        //  131    173    176    177    Ljava/lang/Exception;
        //  108    164    167    168    Ljava/lang/Exception;
        //  93     127    130    131    Ljava/lang/Exception;
        //  435    440    443    444    Ljava/lang/Exception;
        //  344    358    361    362    Ljava/lang/Exception;
        //  93     454    457    462    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0108:
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
    
    public void a(final Entity a, double a, final boolean a) {
        this.B = this.z;
        final int y = b.Y ? 1 : 0;
        this.C = this.A;
        this.z = a.field_70142_S;
        this.A = a.field_70136_U;
        final int a2 = y;
        double a3 = a.field_70165_t - this.z;
        double a4 = a.field_70161_v - this.A;
        final double n = a3;
        final double n2 = 0.0;
        final double n3;
        final double n4;
        Label_0126: {
            Label_0105: {
                if (a2 == 0) {
                    if (n != n2) {
                        n3 = a4;
                        n4 = 0.0;
                        if (a2 != 0) {
                            break Label_0126;
                        }
                        if (n3 != n4) {
                            break Label_0105;
                        }
                    }
                    a3 = a.field_70165_t - this.B;
                    final double field_70161_v = a.field_70161_v;
                    final double c = this.C;
                }
                a4 = n - n2;
            }
            final double n5 = a.field_70165_t + a3 * 2.0;
            final double field_70165_t = this.b().field_71439_g.field_70165_t;
        }
        final double a5 = n3 - n4;
        final double a6 = a.field_70163_u - this.b().field_71439_g.field_70163_u + a.field_70131_O / 2.0f + 0.2;
        final double a7 = a.field_70161_v + a4 * 2.0 - this.b().field_71439_g.field_70161_v;
        final double a8 = MathHelper.func_76133_a(a5 * a5 + a7 * a7);
        final double a9 = a.field_70165_t + a3 * 2.0 - this.b().field_71439_g.field_70165_t;
        final double a10 = a.field_70161_v + a4 * 2.0 - this.b().field_71439_g.field_70161_v;
        final float a11 = (float)(-(Math.atan2(a6, a8) * 180.0 / 3.141592653589793));
        final int a12 = this.f((Entity)this.p);
        double n7;
        final int n6 = (int)(n7 = (this.t.a() ? 1 : 0));
        Label_0321: {
            if (a2 == 0) {
                if (n6 == 0) {
                    final double n9;
                    final int n8 = (int)(n9 = (n7 = dcmpl((double)a12, this.v.a() / 2.0)));
                    if (a2 != 0) {
                        break Label_0321;
                    }
                    if (n8 > 0) {
                        return;
                    }
                }
                n7 = (a ? 1 : 0);
            }
        }
        b b = null;
        Label_0503: {
            Label_0418: {
                if (a2 == 0) {
                    if (n6 != 0) {
                        final int n10 = (int)(n7 = this.g((Entity)this.p));
                        if (a2 != 0) {
                            break Label_0418;
                        }
                        if (n10 > 5) {
                            final float a13 = this.c().field_70125_A;
                            final float a14 = this.a(this.b().field_71439_g.field_70125_A, a11, (float)a / 15.0f) - a13;
                            final EntityPlayerSP c2 = this.c();
                            c2.field_70125_A += a14;
                        }
                    }
                    ++this.F;
                    b = this;
                    if (a2 != 0) {
                        break Label_0503;
                    }
                    n7 = dcmpl(this.F, 100.0);
                }
            }
            if (n7 > 0) {
                this.F = 0.0;
                this.D = ThreadLocalRandom.current().nextDouble(-5.0, 5.0) / 1000.0;
                this.E = ThreadLocalRandom.current().nextDouble(-5.0, 5.0) / 1000.0;
            }
            final EntityPlayerSP c3 = this.c();
            c3.field_70125_A += (float)this.D;
            final EntityPlayerSP c4 = this.c();
            c4.field_70177_z += (float)this.E;
            b = this;
        }
        final float a13 = b.c().field_70177_z;
        final float a14 = (float)(Math.atan2(a10, a9) * 180.0 / 3.141592653589793) - 90.0f;
        a += a12 / 100.0;
        final boolean a15 = this.h((Entity)this.p);
        b b2 = this;
        final float field_78902_a;
        final float n15;
        Label_0658: {
            if (a2 == 0) {
                Label_0635: {
                    if (this.u.a()) {
                        float n12;
                        final int n11 = (int)(n12 = (a15 ? 1 : 0));
                        Label_0629: {
                            Label_0601: {
                                if (a2 == 0) {
                                    if (n11 != 0) {
                                        final float n14;
                                        final int n13 = (int)(n14 = (n12 = fcmpl(this.c().field_71158_b.field_78902_a, 0.0f)));
                                        if (a2 != 0) {
                                            break Label_0601;
                                        }
                                        if (n13 > 0) {
                                            break Label_0629;
                                        }
                                    }
                                    float n14;
                                    n12 = (n14 = (a15 ? 1 : 0));
                                }
                            }
                            if (a2 == 0) {
                                if (n11 != 0) {
                                    break Label_0635;
                                }
                                field_78902_a = this.c().field_71158_b.field_78902_a;
                                n15 = 0.0f;
                                if (a2 != 0) {
                                    break Label_0658;
                                }
                                n12 = fcmpg(field_78902_a, n15);
                            }
                            if (n12 >= 0) {
                                break Label_0635;
                            }
                        }
                        a *= 2.0;
                    }
                }
                b2 = this;
            }
            b2.a(this.b().field_71439_g.field_70177_z, a14, (float)a / 10.0f);
        }
        float a16 = field_78902_a - n15;
        Label_0724: {
            if (a2 == 0) {
                if (a12 <= 0) {
                    break Label_0724;
                }
                this.w += a16;
                a16 = this.x;
                this.x = this.y.func_76333_a(this.w, 0.05f);
                this.w = 0.0f;
            }
            final EntityPlayerSP c5 = this.c();
            c5.field_70177_z += a16;
        }
        if (a2 != 0) {
            int a17 = md.b.G;
            md.b.G = ++a17;
        }
    }
    
    public float a(final float a, final float a, final float a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: fload_2         /* a */
        //     4: fload_1         /* a */
        //     5: fsub           
        //     6: f2d            
        //     7: invokestatic    net/minecraft/util/MathHelper.func_76138_g:(D)D
        //    10: d2f            
        //    11: fstore          5
        //    13: istore          a
        //    15: fload           a
        //    17: fload_3         /* a */
        //    18: fcmpl          
        //    19: iload           a
        //    21: ifne            40
        //    24: ifle            30
        //    27: fload_3         /* a */
        //    28: fstore          a
        //    30: fload           a
        //    32: fload_3         /* a */
        //    33: fneg           
        //    34: iload           a
        //    36: ifne            50
        //    39: fcmpg          
        //    40: ifge            47
        //    43: fload_3         /* a */
        //    44: fneg           
        //    45: fstore          a
        //    47: fload_1         /* a */
        //    48: fload           a
        //    50: fadd           
        //    51: freturn        
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
    
    private int f(final Entity a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dstore_3       
        //     2: getstatic       md/b.Y:Z
        //     5: aload_1         /* a */
        //     6: getfield        net/minecraft/entity/Entity.field_70165_t:D
        //     9: aload_0         /* a */
        //    10: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    13: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    16: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70165_t:D
        //    19: dsub           
        //    20: dstore          a
        //    22: aload_1         /* a */
        //    23: getfield        net/minecraft/entity/Entity.field_70161_v:D
        //    26: aload_0         /* a */
        //    27: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    30: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    33: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70161_v:D
        //    36: dsub           
        //    37: dstore          a
        //    39: aload_1         /* a */
        //    40: getfield        net/minecraft/entity/Entity.field_70163_u:D
        //    43: aload_0         /* a */
        //    44: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    47: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    50: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70163_u:D
        //    53: dsub           
        //    54: aload_1         /* a */
        //    55: getfield        net/minecraft/entity/Entity.field_70131_O:F
        //    58: f2d            
        //    59: dadd           
        //    60: dstore          9
        //    62: istore_2        /* a */
        //    63: dload           a
        //    65: dconst_0       
        //    66: dcmpl          
        //    67: iload_2         /* a */
        //    68: ifne            106
        //    71: ifle            102
        //    74: dload           a
        //    76: dconst_0       
        //    77: dcmpl          
        //    78: iload_2         /* a */
        //    79: ifne            106
        //    82: ifle            102
        //    85: dload           a
        //    87: dload           a
        //    89: ddiv           
        //    90: invokestatic    java/lang/Math.atan:(D)D
        //    93: dneg           
        //    94: invokestatic    java/lang/Math.toDegrees:(D)D
        //    97: dstore_3        /* a */
        //    98: iload_2         /* a */
        //    99: ifeq            221
        //   102: dload           a
        //   104: dconst_0       
        //   105: dcmpl          
        //   106: iload_2         /* a */
        //   107: ifne            145
        //   110: ifle            141
        //   113: dload           a
        //   115: dconst_0       
        //   116: dcmpg          
        //   117: iload_2         /* a */
        //   118: ifne            145
        //   121: ifge            141
        //   124: dload           a
        //   126: dload           a
        //   128: ddiv           
        //   129: invokestatic    java/lang/Math.atan:(D)D
        //   132: dneg           
        //   133: invokestatic    java/lang/Math.toDegrees:(D)D
        //   136: dstore_3        /* a */
        //   137: iload_2         /* a */
        //   138: ifeq            221
        //   141: dload           a
        //   143: dconst_0       
        //   144: dcmpg          
        //   145: iload_2         /* a */
        //   146: ifne            191
        //   149: ifge            183
        //   152: dload           a
        //   154: dconst_0       
        //   155: dcmpl          
        //   156: iload_2         /* a */
        //   157: ifne            191
        //   160: ifle            183
        //   163: ldc2_w          -90.0
        //   166: dload           a
        //   168: dload           a
        //   170: ddiv           
        //   171: invokestatic    java/lang/Math.atan:(D)D
        //   174: invokestatic    java/lang/Math.toDegrees:(D)D
        //   177: dadd           
        //   178: dstore_3        /* a */
        //   179: iload_2         /* a */
        //   180: ifeq            221
        //   183: dload           a
        //   185: dconst_0       
        //   186: iload_2         /* a */
        //   187: ifne            231
        //   190: dcmpg          
        //   191: ifge            221
        //   194: dload           a
        //   196: dconst_0       
        //   197: iload_2         /* a */
        //   198: ifne            231
        //   201: dcmpg          
        //   202: ifge            221
        //   205: ldc2_w          90.0
        //   208: dload           a
        //   210: dload           a
        //   212: ddiv           
        //   213: invokestatic    java/lang/Math.atan:(D)D
        //   216: invokestatic    java/lang/Math.toDegrees:(D)D
        //   219: dadd           
        //   220: dstore_3        /* a */
        //   221: dload           a
        //   223: dload           a
        //   225: dmul           
        //   226: dload           a
        //   228: dload           a
        //   230: dmul           
        //   231: dadd           
        //   232: invokestatic    java/lang/Math.sqrt:(D)D
        //   235: dstore          a
        //   237: dload           a
        //   239: dload           a
        //   241: ddiv           
        //   242: invokestatic    java/lang/Math.atan:(D)D
        //   245: invokestatic    java/lang/Math.toDegrees:(D)D
        //   248: dneg           
        //   249: dstore          a
        //   251: dload_3         /* a */
        //   252: aload_0         /* a */
        //   253: invokevirtual   md/b.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   256: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70177_z:F
        //   259: f2d            
        //   260: dsub           
        //   261: invokestatic    java/lang/Math.abs:(D)D
        //   264: ldc2_w          360.0
        //   267: drem           
        //   268: d2i            
        //   269: istore          a
        //   271: iload           a
        //   273: sipush          180
        //   276: iload_2         /* a */
        //   277: ifne            288
        //   280: if_icmple       292
        //   283: sipush          360
        //   286: iload           a
        //   288: isub           
        //   289: goto            294
        //   292: iload           a
        //   294: istore          a
        //   296: iload           a
        //   298: ireturn        
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
    
    private int g(final Entity a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dstore_3        /* a */
        //     2: aload_1         /* a */
        //     3: getfield        net/minecraft/entity/Entity.field_70165_t:D
        //     6: aload_0         /* a */
        //     7: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    10: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    13: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70165_t:D
        //    16: dsub           
        //    17: dstore          5
        //    19: getstatic       md/b.Y:Z
        //    22: aload_1         /* a */
        //    23: getfield        net/minecraft/entity/Entity.field_70161_v:D
        //    26: aload_0         /* a */
        //    27: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    30: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    33: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70161_v:D
        //    36: dsub           
        //    37: dstore          a
        //    39: istore_2        /* a */
        //    40: aload_1         /* a */
        //    41: getfield        net/minecraft/entity/Entity.field_70163_u:D
        //    44: aload_0         /* a */
        //    45: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    48: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    51: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70163_u:D
        //    54: dsub           
        //    55: aload_1         /* a */
        //    56: getfield        net/minecraft/entity/Entity.field_70131_O:F
        //    59: fconst_2       
        //    60: fdiv           
        //    61: f2d            
        //    62: dadd           
        //    63: dstore          a
        //    65: dload           a
        //    67: dload           a
        //    69: dmul           
        //    70: dload           a
        //    72: dload           a
        //    74: dmul           
        //    75: dadd           
        //    76: invokestatic    java/lang/Math.sqrt:(D)D
        //    79: dload           a
        //    81: invokestatic    java/lang/Math.atan2:(DD)D
        //    84: ldc2_w          3.141592653589793
        //    87: dadd           
        //    88: dstore_3        /* a */
        //    89: dload_3         /* a */
        //    90: aload_0         /* a */
        //    91: invokevirtual   md/b.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //    94: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70125_A:F
        //    97: f2d            
        //    98: dsub           
        //    99: invokestatic    java/lang/Math.abs:(D)D
        //   102: ldc2_w          360.0
        //   105: drem           
        //   106: d2i            
        //   107: istore          a
        //   109: iload           a
        //   111: sipush          180
        //   114: iload_2         /* a */
        //   115: ifne            126
        //   118: if_icmple       130
        //   121: sipush          360
        //   124: iload           a
        //   126: isub           
        //   127: goto            132
        //   130: iload           a
        //   132: istore          a
        //   134: iload           a
        //   136: ireturn        
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
    
    boolean h(final Entity a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: getstatic       md/b.Y:Z
        //     5: aload_1         /* a */
        //     6: getfield        net/minecraft/entity/Entity.field_70165_t:D
        //     9: aload_0         /* a */
        //    10: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    13: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    16: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70165_t:D
        //    19: dsub           
        //    20: dstore          5
        //    22: istore_2        /* a */
        //    23: aload_1         /* a */
        //    24: getfield        net/minecraft/entity/Entity.field_70161_v:D
        //    27: aload_0         /* a */
        //    28: invokevirtual   md/b.b:()Lnet/minecraft/client/Minecraft;
        //    31: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    34: getfield        net/minecraft/client/entity/EntityClientPlayerMP.field_70161_v:D
        //    37: dsub           
        //    38: dstore          a
        //    40: dload           a
        //    42: dconst_0       
        //    43: dcmpl          
        //    44: iload_2         /* a */
        //    45: ifne            83
        //    48: ifle            79
        //    51: dload           a
        //    53: dconst_0       
        //    54: dcmpl          
        //    55: iload_2         /* a */
        //    56: ifne            83
        //    59: ifle            79
        //    62: dload           a
        //    64: dload           a
        //    66: ddiv           
        //    67: invokestatic    java/lang/Math.atan:(D)D
        //    70: dneg           
        //    71: invokestatic    java/lang/Math.toDegrees:(D)D
        //    74: dstore_3        /* a */
        //    75: iload_2         /* a */
        //    76: ifeq            198
        //    79: dload           a
        //    81: dconst_0       
        //    82: dcmpl          
        //    83: iload_2         /* a */
        //    84: ifne            122
        //    87: ifle            118
        //    90: dload           a
        //    92: dconst_0       
        //    93: dcmpg          
        //    94: iload_2         /* a */
        //    95: ifne            122
        //    98: ifge            118
        //   101: dload           a
        //   103: dload           a
        //   105: ddiv           
        //   106: invokestatic    java/lang/Math.atan:(D)D
        //   109: dneg           
        //   110: invokestatic    java/lang/Math.toDegrees:(D)D
        //   113: dstore_3        /* a */
        //   114: iload_2         /* a */
        //   115: ifeq            198
        //   118: dload           a
        //   120: dconst_0       
        //   121: dcmpg          
        //   122: iload_2         /* a */
        //   123: ifne            164
        //   126: ifge            160
        //   129: dload           a
        //   131: dconst_0       
        //   132: dcmpl          
        //   133: iload_2         /* a */
        //   134: ifne            164
        //   137: ifle            160
        //   140: ldc2_w          -90.0
        //   143: dload           a
        //   145: dload           a
        //   147: ddiv           
        //   148: invokestatic    java/lang/Math.atan:(D)D
        //   151: invokestatic    java/lang/Math.toDegrees:(D)D
        //   154: dadd           
        //   155: dstore_3        /* a */
        //   156: iload_2         /* a */
        //   157: ifeq            198
        //   160: dload           a
        //   162: dconst_0       
        //   163: dcmpg          
        //   164: iload_2         /* a */
        //   165: ifne            216
        //   168: ifge            198
        //   171: dload           a
        //   173: dconst_0       
        //   174: dcmpg          
        //   175: iload_2         /* a */
        //   176: ifne            216
        //   179: ifge            198
        //   182: ldc2_w          90.0
        //   185: dload           a
        //   187: dload           a
        //   189: ddiv           
        //   190: invokestatic    java/lang/Math.atan:(D)D
        //   193: invokestatic    java/lang/Math.toDegrees:(D)D
        //   196: dadd           
        //   197: dstore_3        /* a */
        //   198: dload_3         /* a */
        //   199: aload_0         /* a */
        //   200: invokevirtual   md/b.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   203: getfield        net/minecraft/client/entity/EntityPlayerSP.field_70177_z:F
        //   206: f2d            
        //   207: dsub           
        //   208: invokestatic    java/lang/Math.abs:(D)D
        //   211: ldc2_w          360.0
        //   214: drem           
        //   215: d2i            
        //   216: istore          a
        //   218: iload           a
        //   220: iload_2         /* a */
        //   221: ifne            231
        //   224: sipush          180
        //   227: if_icmpge       234
        //   230: iconst_1       
        //   231: goto            235
        //   234: iconst_0       
        //   235: ireturn        
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
    
    public Entity m() {
        final int a = b.Y ? 1 : 0;
        double a2 = 360.0;
        Entity entity = null;
        for (final Object a3 : this.b().field_71441_e.field_72996_f) {
            entity = (Entity)a3;
            if (a != 0) {
                return entity;
            }
            final Entity a4 = entity;
            double n2;
            final int n = (int)(n2 = (this.b(a4) ? 1 : 0));
            if (a == 0) {
                if (n == 0 && a == 0) {
                    continue;
                }
                final int n3;
                n2 = (n3 = dcmpl((double)this.c().func_70032_d(a4), this.r.a()));
            }
            if (a == 0) {
                if (n > 0 && a == 0) {
                    continue;
                }
                n2 = this.f(a4);
            }
            final double a5 = n2;
            final double n4 = dcmpg(a5, a2);
            Label_0154: {
                final double n5;
                Label_0150: {
                    if (a == 0) {
                        if (n4 >= 0) {
                            break Label_0154;
                        }
                        n5 = a5;
                        if (a != 0) {
                            break Label_0150;
                        }
                        final double n6 = dcmpg(n5, this.v.a() / 2.0);
                    }
                    if (n4 > 0) {
                        break Label_0154;
                    }
                }
                a2 = n5;
                final Entity a6 = a4;
            }
            if (a != 0) {
                break;
            }
        }
        return entity;
    }
    
    public Entity n() {
        return (Entity)this.p;
    }
    
    @Override
    public void e() {
        final int y = b.Y ? 1 : 0;
        this.o = true;
        final int a = y;
        final g n = this.n;
        if (a == 0) {
            if (n != null) {
                return;
            }
            this.n = new g(this);
            final g n2 = this.n;
        }
        n.a();
    }
    
    @Override
    public void g() {
        this.o = false;
        final int a = b.Y ? 1 : 0;
        b b = this;
        if (a == 0) {
            if (this.n != null) {
                this.n.a = null;
            }
            b = this;
        }
        b.n = null;
    }
    
    @Override
    public k[] k() {
        return new k[] { this.v, this.q, this.r, this.u, this.s, this.t };
    }
    
    static {
        final String[] z = new String[7];
        int n = 0;
        String s;
        int n2 = (s = "\"_\u00e6\u00eaQ\u00cf\u00ad\b^\u000f2G\u00fd\u00e8\\\u0087\u00cc\b]\u00ec\u00fb_\u0083\u009f\u0004\t Z\u00e2\u00c8I\u0091\u0085\u0012G\b7V\u00fd\u00fdS\u0081\u008d\r\t,R\u00f7©[\u008c\u008b\rV").length();
        int n3 = 9;
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
                                        c2 = 'a';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '3';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u008f';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u0089';
                                        break;
                                    }
                                    case 4: {
                                        c2 = ':';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u00e2';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00ec';
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
                            z[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "3R\u00fb\u00ec\u00053R\u00e1\u00ee_").length();
                            n3 = 4;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            z[n++] = intern;
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
        Z = z;
    }
}
