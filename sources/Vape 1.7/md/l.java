package md;

import net.minecraft.entity.projectile.*;
import com.sun.jna.z.a.f.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import cpw.mods.fml.common.gameevent.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class l extends c
{
    int n;
    ItemStack o;
    boolean p;
    EntityFishHook q;
    m r;
    t s;
    boolean t;
    g u;
    public static int v;
    public static boolean w;
    public static int x;
    public static int y;
    public static int z;
    public static boolean A;
    public static boolean B;
    public static int C;
    public static boolean D;
    public static boolean E;
    public static int F;
    public static boolean G;
    public static boolean H;
    public static int I;
    public static boolean J;
    public static int K;
    public static boolean L;
    public static boolean M;
    public static boolean N;
    private static final String[] O;
    
    public l() {
        final int n = l.N ? 1 : 0;
        final String[] a = l.O;
        super(a[1], com.sun.jna.z.a.d.b.Utility, -16777216);
        final int a2 = n;
        this.r = new m();
        this.s = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[0], 500.0, 100.0, 1000.0, 1.0, com.sun.jna.z.a.e.a.a.a.a.b.INTEGER);
        if (a2 != 0) {
            int a3 = l.v;
            l.v = ++a3;
        }
    }
    
    @Override
    public void a(final TickEvent$PlayerTickEvent a) {
        final int a2 = l.N ? 1 : 0;
        l l = this;
        if (a2 == 0) {
            if (this.o != null) {
                l i = this;
                if (a2 == 0) {
                    if (!this.p) {
                        this.b().field_71442_b.func_78769_a((EntityPlayer)this.c(), (World)this.b().field_71441_e, this.o);
                        this.p = true;
                    }
                    i = this;
                }
                i.q = this.c().field_71104_cf;
                return;
            }
            l = this;
        }
        final InventoryPlayer a3 = l.c().field_71071_by;
        int a4 = 0;
        while (a4 < 9) {
            this.o = a3.field_70462_a[a4];
            final ItemStack o = this.o;
            Label_0165: {
                Label_0162: {
                    if (a2 == 0) {
                        if (o == null && a2 == 0) {
                            break Label_0162;
                        }
                        final ItemStack o2 = this.o;
                    }
                    final Item a5 = o.func_77973_b();
                    if (a2 != 0) {
                        break Label_0165;
                    }
                    if (a5 != null && a5 instanceof ItemFishingRod) {
                        this.c().field_71071_by.field_70461_c = a4;
                        this.r.c();
                        if (a2 == 0) {
                            break;
                        }
                    }
                }
                ++a4;
            }
            if (a2 != 0) {
                break;
            }
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
        //     5: getfield        md/l.t:Z
        //     8: ifeq            127
        //    11: ldc2_w          5
        //    14: invokestatic    java/lang/Thread.sleep:(J)V
        //    17: aload_0         /* a */
        //    18: invokevirtual   md/l.c:()Z
        //    21: iload_1         /* a */
        //    22: ifne            33
        //    25: ifne            32
        //    28: iload_1         /* a */
        //    29: ifeq            4
        //    32: iconst_0       
        //    33: istore_2        /* a */
        //    34: aload_0         /* a */
        //    35: iload_1         /* a */
        //    36: ifne            71
        //    39: getfield        md/l.q:Lnet/minecraft/entity/projectile/EntityFishHook;
        //    42: ifnull          70
        //    45: goto            49
        //    48: athrow         
        //    49: aload_0         /* a */
        //    50: getfield        md/l.q:Lnet/minecraft/entity/projectile/EntityFishHook;
        //    53: getfield        net/minecraft/entity/projectile/EntityFishHook.field_146043_c:Lnet/minecraft/entity/Entity;
        //    56: ifnull          68
        //    59: goto            63
        //    62: athrow         
        //    63: iconst_1       
        //    64: goto            69
        //    67: athrow         
        //    68: iconst_0       
        //    69: istore_2        /* a */
        //    70: aload_0         /* a */
        //    71: iload_1         /* a */
        //    72: ifne            107
        //    75: getfield        md/l.r:Lcom/sun/jna/z/a/f/m;
        //    78: aload_0         /* a */
        //    79: getfield        md/l.s:Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //    82: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:()D
        //    87: d2l            
        //    88: invokevirtual   com/sun/jna/z/a/f/m.a:(J)Z
        //    91: ifne            106
        //    94: goto            98
        //    97: athrow         
        //    98: iload_2         /* a */
        //    99: ifeq            115
        //   102: goto            106
        //   105: athrow         
        //   106: aload_0         /* a */
        //   107: iconst_0       
        //   108: invokevirtual   md/l.a:(Z)V
        //   111: goto            115
        //   114: athrow         
        //   115: goto            4
        //   118: astore_2        /* a */
        //   119: aload_2         /* a */
        //   120: invokevirtual   java/lang/InterruptedException.printStackTrace:()V
        //   123: iload_1         /* a */
        //   124: ifeq            4
        //   127: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  11     28     118    127    Ljava/lang/InterruptedException;
        //  34     45     48     49     Ljava/lang/InterruptedException;
        //  98     111    114    115    Ljava/lang/InterruptedException;
        //  71     94     97     98     Ljava/lang/InterruptedException;
        //  70     102    105    106    Ljava/lang/InterruptedException;
        //  49     67     67     68     Ljava/lang/InterruptedException;
        //  34     59     62     63     Ljava/lang/InterruptedException;
        //  32     115    118    127    Ljava/lang/InterruptedException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0049:
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
    public void g() {
        final int n = l.N ? 1 : 0;
        this.t = false;
        final int a = n;
        l l = this;
        if (a == 0) {
            if (this.u != null) {
                this.u.a = null;
            }
            l = this;
        }
        l.u = null;
    }
    
    @Override
    public void e() {
        final int n = l.N ? 1 : 0;
        this.t = true;
        final int a = n;
        l l = this;
        if (a == 0) {
            if (this.u == null) {
                (this.u = new g(this)).a();
            }
            l = this;
        }
        l.n = this.c().field_71071_by.field_70461_c;
    }
    
    @Override
    public void f() {
        this.c().field_71071_by.field_70461_c = this.n;
        final int n = l.N ? 1 : 0;
        this.n = 0;
        final int a = n;
        this.o = null;
        this.p = false;
        this.q = null;
        if (l.v != 0) {
            l.N = (a == 0);
        }
    }
    
    @Override
    public k[] k() {
        return new k[] { this.s };
    }
    
    static {
        final String[] o = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "0\u0004SS8\b&\u000e[f3\u008d\u00fb\u001f").length();
        int char1 = 5;
        int n2 = -1;
        Label_0021: {
            break Label_0021;
            do {
                char1 = s.charAt(n2);
                ++n2;
                final String s2 = s;
                final int n3 = n2;
                final char[] charArray = s2.substring(n3, n3 + char1).toCharArray();
                int length2;
                int n5;
                final int n4 = n5 = (length2 = charArray.length);
                int n6 = 0;
                while (true) {
                    Label_0186: {
                        if (n4 > 1) {
                            break Label_0186;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 't';
                                    break;
                                }
                                case 1: {
                                    c2 = 'a';
                                    break;
                                }
                                case 2: {
                                    c2 = '?';
                                    break;
                                }
                                case 3: {
                                    c2 = '2';
                                    break;
                                }
                                case 4: {
                                    c2 = 'A';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u00e4';
                                    break;
                                }
                                default: {
                                    c2 = '\u0098';
                                    break;
                                }
                            }
                            charArray[length2] = (char)(c ^ c2);
                            ++n6;
                        } while (n4 == 0);
                    }
                    if (n4 > n6) {
                        continue;
                    }
                    break;
                }
                o[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        O = o;
    }
}
