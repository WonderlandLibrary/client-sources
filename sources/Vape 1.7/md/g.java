package md;

import net.minecraft.world.*;
import net.minecraft.client.entity.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraftforge.event.entity.living.*;
import java.util.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class g extends c
{
    t n;
    t o;
    private float p;
    World q;
    EntityPlayerSP r;
    public static int s;
    public static boolean t;
    public static int u;
    public static int v;
    public static int w;
    public static boolean x;
    public static boolean y;
    public static int z;
    public static boolean A;
    public static boolean B;
    public static int C;
    public static boolean D;
    public static boolean E;
    public static int F;
    public static boolean G;
    public static int H;
    public static boolean I;
    public static boolean J;
    public static boolean K;
    private static final String[] L;
    
    public g() {
        final String[] a = g.L;
        super(a[0], com.sun.jna.z.a.d.b.Combat, -65404);
        this.n = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[2], 100.0, 0.0, 100.0, 1.0, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        this.o = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[1], 100.0, 0.0, 100.0, 1.0, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        final int k = g.K ? 1 : 0;
        this.p = -999.0f;
        final int a2 = k;
        if (g.s != 0) {
            g.K = (a2 == 0);
        }
    }
    
    @Override
    public void a(final LivingEvent$LivingUpdateEvent a) {
        final int a2 = g.K ? 1 : 0;
        g g = this;
        if (a2 == 0) {
            Label_0036: {
                if (this.q != null) {
                    g g2 = this;
                    g g3 = this;
                    if (a2 == 0) {
                        if (!this.q.equals(this.b().field_71441_e)) {
                            break Label_0036;
                        }
                        g2 = this;
                        g3 = this;
                    }
                    if (a2 == 0) {
                        Label_0083: {
                            if (g3.r != null) {
                                final boolean equals = this.r.equals((Object)this.c());
                                if (a2 == 0) {
                                    if (!equals) {
                                        break Label_0083;
                                    }
                                    a.entity.equals((Object)this.c());
                                }
                                if (equals) {
                                    final float a3 = this.c().func_110143_aJ();
                                    g g4 = this;
                                    double n4 = 0.0;
                                    int n3 = 0;
                                    double n2 = 0.0;
                                    final int n;
                                    Label_0162: {
                                        Label_0156: {
                                            if (a2 == 0) {
                                                if (this.p != -999.0f) {
                                                    n = (int)(n2 = (n3 = (int)(n4 = fcmpl(a3, this.p))));
                                                    if (a2 != 0) {
                                                        break Label_0162;
                                                    }
                                                    if (n <= 0) {
                                                        break Label_0156;
                                                    }
                                                }
                                                g4 = this;
                                            }
                                            g4.p = a3;
                                            if (a2 == 0) {
                                                return;
                                            }
                                        }
                                        final int n5;
                                        n2 = (n5 = (n3 = (int)(n4 = fcmpg(a3, this.p))));
                                    }
                                    if (a2 == 0) {
                                        if (n >= 0) {
                                            return;
                                        }
                                        n3 = (int)(n2 = (n4 = dcmpl(this.c().field_70159_w, 0.0)));
                                    }
                                    Label_0213: {
                                        if (a2 == 0) {
                                            if (n2 != 0) {
                                                break Label_0213;
                                            }
                                            n4 = (n3 = dcmpl(this.c().field_70181_x, 0.0));
                                        }
                                        if (a2 == 0) {
                                            if (n3 != 0) {
                                                break Label_0213;
                                            }
                                            n4 = dcmpl(this.c().field_70179_y, 0.0);
                                        }
                                        if (n4 == 0) {
                                            return;
                                        }
                                    }
                                    final Random a4 = new Random();
                                    final double a5 = a4.nextDouble();
                                    final double a6 = this.o.a();
                                    final double n6;
                                    double a7 = n6 = a6 + (a6 + 5.0 - a6) * a5;
                                    if (a2 == 0) {
                                        if (n6 >= 100.0) {
                                            a7 = 100.0;
                                        }
                                        this.n.a();
                                    }
                                    final double a8 = n6;
                                    final double n7;
                                    double a9 = n7 = a8 + (a8 + 5.0 - a8) * a5;
                                    final double n8 = 100.0;
                                    if (a2 == 0 && n7 >= n8) {
                                        a9 = 100.0;
                                        goto Label_0321;
                                    }
                                    final double a10 = n7 / n8;
                                    final double a11 = a9 / 100.0;
                                    this.c().field_70159_w = this.a(this.c().field_70159_w, a10);
                                    this.c().field_70181_x = this.a(this.c().field_70181_x, a11);
                                    this.c().field_70179_y = this.a(this.c().field_70179_y, a10);
                                    final double n9 = dcmpg(a6, 1.0);
                                    if (a2 == 0) {
                                        if (n9 <= 0) {
                                            this.c().field_70159_w = 0.0;
                                            this.c().field_70179_y = 0.0;
                                        }
                                        final double n10 = dcmpg(a8, 1.0);
                                    }
                                    if (n9 <= 0) {
                                        this.c().field_70181_x = 0.0;
                                    }
                                    this.p = a3;
                                }
                                return;
                            }
                        }
                        this.r = this.c();
                        g2 = this;
                    }
                    g2.p = -999.0f;
                    return;
                }
            }
            this.q = (World)this.b().field_71441_e;
            g = this;
        }
        g.p = -999.0f;
    }
    
    double a(final double a, final double a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokestatic    java/lang/Math.abs:(D)D
        //     7: invokestatic    java/lang/Double.toString:(D)Ljava/lang/String;
        //    10: astore          7
        //    12: istore          a
        //    14: aload           a
        //    16: iload           a
        //    18: ifne            31
        //    21: ldc             ","
        //    23: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    26: ifeq            34
        //    29: ldc             ","
        //    31: goto            36
        //    34: ldc             "."
        //    36: astore          a
        //    38: aload           a
        //    40: aload           a
        //    42: invokevirtual   java/lang/String.indexOf:(Ljava/lang/String;)I
        //    45: istore          a
        //    47: aload           a
        //    49: invokevirtual   java/lang/String.length:()I
        //    52: iload           a
        //    54: isub           
        //    55: iconst_1       
        //    56: isub           
        //    57: istore          a
        //    59: aload           a
        //    61: astore          a
        //    63: iconst_0       
        //    64: istore          a
        //    66: iload           a
        //    68: iload           a
        //    70: if_icmpge       116
        //    73: new             Ljava/lang/StringBuilder;
        //    76: dup            
        //    77: invokespecial   java/lang/StringBuilder.<init>:()V
        //    80: aload           a
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: ldc             "#"
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    93: astore          a
        //    95: iinc            a, 1
        //    98: iload           a
        //   100: ifeq            66
        //   103: getstatic       md/g.s:I
        //   106: istore          a
        //   108: iinc            a, 1
        //   111: iload           a
        //   113: putstatic       md/g.s:I
        //   116: new             Ljava/text/DecimalFormat;
        //   119: dup            
        //   120: aload           a
        //   122: invokespecial   java/text/DecimalFormat.<init>:(Ljava/lang/String;)V
        //   125: astore          a
        //   127: aload           a
        //   129: dload_1         /* a */
        //   130: dload_3         /* a */
        //   131: dmul           
        //   132: invokevirtual   java/text/DecimalFormat.format:(D)Ljava/lang/String;
        //   135: ldc             ","
        //   137: ldc             "."
        //   139: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //   142: invokestatic    java/lang/Double.valueOf:(Ljava/lang/String;)Ljava/lang/Double;
        //   145: invokevirtual   java/lang/Double.doubleValue:()D
        //   148: dstore          a
        //   150: dload           a
        //   152: dreturn        
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
    
    @Override
    public k[] k() {
        return new k[] { this.n, this.o };
    }
    
    static {
        final String[] l = new String[3];
        int n = 0;
        final String s;
        final int length = (s = "\u00f3\u00d9H62¯\u001c\u00dc\f\u00ed\u00d3V0+©\u0006\u00d1\u00ddHyt\n\u00f3\u00d9V-8¥\t\u00c9\u009c\u0001").length();
        int char1 = 8;
        int n2 = -1;
        Label_0022: {
            break Label_0022;
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
                    Label_0187: {
                        if (n4 > 1) {
                            break Label_0187;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = '¥';
                                    break;
                                }
                                case 1: {
                                    c2 = '¼';
                                    break;
                                }
                                case 2: {
                                    c2 = '$';
                                    break;
                                }
                                case 3: {
                                    c2 = 'Y';
                                    break;
                                }
                                case 4: {
                                    c2 = 'Q';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u00c6';
                                    break;
                                }
                                default: {
                                    c2 = 'h';
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
                l[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        L = l;
    }
}
