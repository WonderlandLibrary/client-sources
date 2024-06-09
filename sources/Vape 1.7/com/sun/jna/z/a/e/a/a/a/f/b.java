package com.sun.jna.z.a.e.a.a.a.f;

import com.sun.jna.z.a.a.*;
import net.minecraft.client.*;
import com.sun.jna.z.a.e.a.a.a.b.*;
import com.sun.jna.z.a.e.a.a.a.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.sun.jna.z.a.*;
import org.lwjgl.input.*;

public class b extends GuiScreen
{
    private final j a;
    public d b;
    public e c;
    private String d;
    private boolean e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    Minecraft l;
    boolean m;
    int n;
    FontRenderer o;
    int p;
    private static final String[] q;
    
    public b(final j a) {
        this.d = "";
        this.e = false;
        this.f = true;
        this.k = 130;
        this.l = Minecraft.func_71410_x();
        this.m = false;
        this.n = 0;
        this.o = new a(new com.sun.jna.z.a.f.d(com.sun.jna.z.a.e.a.a.a.f.b.q[2], 34.0f));
        this.p = 5;
        this.a = a;
        this.b = new d();
    }
    
    public void func_73866_w_() {
        this.g = 350;
        this.h = 20;
        this.i = 130;
        this.j = 12;
        Keyboard.enableRepeatEvents(true);
        (this.c = new e(this.field_146289_q, this.g + 2, this.h + 2, this.i, this.j)).f(100);
        this.c.a(false);
        this.c.b(false);
        this.c.d(true);
        this.n = 0;
    }
    
    public int a() {
        return this.g;
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        this.l.field_71456_v.func_146158_b().func_146240_d();
        this.f = true;
    }
    
    public void func_73876_c() {
        this.c.a();
    }
    
    protected void func_73869_a(final char a, final int a) {
        final int b = a.b;
        this.f = false;
        final int a2 = b;
        int n = a;
        int n2 = a;
        int n3 = a;
        int n4 = a;
        int n8;
        int n7;
        int n6;
        final int n5 = n6 = (n7 = (n8 = 15));
        if (a2 == 0) {
            if (a == n5) {
                this.m = true;
            }
            n = a;
            n2 = a;
            n3 = a;
            n4 = a;
            final int n9;
            n6 = (n9 = (n7 = (n8 = 14)));
        }
        Label_0051: {
            Label_0049: {
                Label_0044: {
                    if (a2 == 0) {
                        if (n4 == n5) {
                            break Label_0044;
                        }
                        n = a;
                        n2 = a;
                        n3 = a;
                        n7 = (n6 = (n8 = 57));
                    }
                    if (a2 != 0) {
                        break Label_0051;
                    }
                    if (n3 != n6) {
                        break Label_0049;
                    }
                }
                this.n = 0;
            }
            n = a;
            n2 = a;
            n8 = (n7 = 1);
        }
        if (a2 == 0) {
            if (n2 == n7) {
                this.l.func_147108_a((GuiScreen)null);
                if (a2 == 0) {
                    return;
                }
            }
            n = a;
            if (a2 != 0) {
                return;
            }
            n8 = 28;
        }
        if (n == n8) {
            final String a3 = this.c.b().trim();
            Label_0148: {
                if (a2 == 0) {
                    if (a3.length() <= 0) {
                        break Label_0148;
                    }
                    this.b.a("." + a3);
                    this.c.a("");
                }
                this.n = 0;
            }
            if (a2 == 0) {
                return;
            }
        }
        this.c.a(a, a);
    }
    
    protected void func_73864_a(int a, int a, final int a) {
        final int a2 = a.b;
        if (Minecraft.func_71410_x().field_71462_r == null) {
            return;
        }
        final float a3 = com.sun.jna.z.a.e.a.a.a.h.a();
        a /= (int)a3;
        a /= (int)a3;
        super.func_73864_a(a, a, a);
        n[] a4 = this.a.b();
        int a5 = a4.length;
        int a6 = 0;
        while (true) {
            do {
                int i = 0;
            Label_0062:
                while (i < a5) {
                    n a7 = a4[a6];
                    final boolean p;
                    boolean contains;
                    final boolean b = contains = (p = a7.p());
                    if (a2 != 0) {
                        a6 = (p ? 1 : 0);
                        do {
                            int j = 0;
                        Label_0350:
                            while (j < a5) {
                                a7 = a4[a6];
                                final boolean p2;
                                final boolean b2 = p2 = a7.p();
                                Label_0601: {
                                    if (a2 == 0) {
                                        if (!b2 && a2 == 0) {
                                            break Label_0601;
                                        }
                                        a7.d();
                                    }
                                    final n n;
                                    Label_0476: {
                                        final boolean contains2;
                                        Label_0471: {
                                            if (a2 == 0) {
                                                if (!b2) {
                                                    contains2 = a7.j().contains(a, a);
                                                    if (a2 != 0) {
                                                        break Label_0471;
                                                    }
                                                    if (contains2) {
                                                        a7.a(a - a7.d(), a - a7.e(), a);
                                                        this.a.c(a7);
                                                        if (a2 == 0) {
                                                            break;
                                                        }
                                                    }
                                                }
                                                n = a7;
                                                if (a2 != 0) {
                                                    break Label_0476;
                                                }
                                                n.d();
                                            }
                                        }
                                        if (!contains2) {
                                            break Label_0601;
                                        }
                                    }
                                    final Rectangle[] e = n.a().a(a7).e(a7);
                                    final int a8 = e.length;
                                    int a9 = 0;
                                    while (a9 < a8) {
                                        final Rectangle a10 = e[a9];
                                        if (a2 == 0) {
                                            j = (a10.contains(a - a7.d(), a - a7.e()) ? 1 : 0);
                                            if (a2 != 0) {
                                                continue Label_0350;
                                            }
                                            if (j != 0) {
                                                a7.a(a - a7.d(), a - a7.e(), a);
                                                this.a.c(a7);
                                                return;
                                            }
                                            ++a9;
                                        }
                                        if (a2 != 0) {
                                            break;
                                        }
                                    }
                                }
                                ++a6;
                            }
                            break;
                        } while (a2 == 0);
                        return;
                    }
                    Label_0321: {
                        if (a2 == 0) {
                            if (!b && a2 == 0) {
                                break Label_0321;
                            }
                            final boolean d;
                            contains = (d = a7.d());
                        }
                        final n n2;
                        Label_0136: {
                            if (a2 == 0) {
                                if (b) {
                                    break Label_0321;
                                }
                                n2 = a7;
                                if (a2 != 0) {
                                    break Label_0136;
                                }
                                contains = n2.j().contains(a, a);
                            }
                            if (contains) {
                                break Label_0321;
                            }
                        }
                        final k[] a11 = n2.b();
                        final int a8 = a11.length;
                        int a9 = 0;
                        do {
                            int k = 0;
                        Label_0153:
                            while (k < a8) {
                                final k a12 = a11[a9];
                                final Rectangle[] a13 = a12.a().a(a12).e(a12);
                                final int a14 = a13.length;
                                i = 0;
                                if (a2 != 0) {
                                    continue Label_0062;
                                }
                                int a15 = i;
                                while (a15 < a14) {
                                    final Rectangle a16 = a13[a15];
                                    if (a2 == 0) {
                                        k = (a16.contains(a - a7.d() - a12.d(), a - a7.e() - a12.e()) ? 1 : 0);
                                        if (a2 != 0) {
                                            continue Label_0153;
                                        }
                                        if (k != 0) {
                                            a7.a(a - a7.d(), a - a7.e(), a);
                                            this.a.c(a7);
                                            return;
                                        }
                                        ++a15;
                                    }
                                    if (a2 != 0) {
                                        break;
                                    }
                                }
                                ++a9;
                            }
                            break;
                        } while (a2 == 0);
                    }
                    ++a6;
                }
                break;
            } while (a2 == 0);
            a4 = this.a.b();
            a5 = a4.length;
            boolean p = false;
            continue;
        }
    }
    
    public void func_146286_b(int a, int a, final int a) {
        final int a2 = a.b;
        final Minecraft func_71410_x = Minecraft.func_71410_x();
        if (a2 == 0) {
            if (func_71410_x.field_71462_r == null) {
                return;
            }
            Minecraft.func_71410_x();
        }
        final Minecraft a3 = func_71410_x;
        final ScaledResolution a4 = new ScaledResolution(a3, a3.field_71443_c, a3.field_71440_d);
        final int a5 = a4.func_78325_e();
        final float a6 = com.sun.jna.z.a.e.a.a.a.h.a();
        a /= (int)a6;
        a /= (int)a6;
        super.func_146286_b(a, a, a);
        n[] a7 = this.a.b();
        int a8 = a7.length;
        int a9 = 0;
        while (true) {
            do {
                int i = 0;
            Label_0100:
                while (i < a8) {
                    n a10 = a7[a9];
                    final boolean p;
                    boolean contains;
                    final boolean b = contains = (p = a10.p());
                    if (a2 != 0) {
                        a9 = (p ? 1 : 0);
                        do {
                            int j = 0;
                        Label_0388:
                            while (j < a8) {
                                a10 = a7[a9];
                                boolean b4;
                                final boolean b3;
                                final boolean b2 = b3 = (b4 = a10.p());
                                Label_0774: {
                                    if (a2 == 0) {
                                        if (!b2 && a2 == 0) {
                                            break Label_0774;
                                        }
                                        a10.equals(com.sun.jna.z.a.i.f.c.n);
                                    }
                                    boolean b5 = false;
                                    Label_0562: {
                                        if (a2 == 0) {
                                            if (b2) {
                                                final Rectangle[] e = a10.a().a(a10).e(a10);
                                                final int a11 = e.length;
                                                int a12 = 0;
                                                while (a12 < a11) {
                                                    final Rectangle a13 = e[a12];
                                                    Label_0547: {
                                                        Label_0539: {
                                                            if (a2 == 0) {
                                                                b5 = (b4 = a13.contains(a - a10.d(), a - (a10.e() + 12)));
                                                                if (a2 != 0) {
                                                                    break Label_0562;
                                                                }
                                                                if (!b5) {
                                                                    break Label_0539;
                                                                }
                                                                this.c.b(true);
                                                            }
                                                            if (a2 == 0) {
                                                                break Label_0547;
                                                            }
                                                        }
                                                        this.c.b(false);
                                                    }
                                                    ++a12;
                                                    if (a2 != 0) {
                                                        break;
                                                    }
                                                }
                                            }
                                            a10.d();
                                        }
                                    }
                                    final n n;
                                    Label_0649: {
                                        final boolean contains2;
                                        Label_0644: {
                                            if (a2 == 0) {
                                                if (!b5) {
                                                    contains2 = a10.j().contains(a, a);
                                                    if (a2 != 0) {
                                                        break Label_0644;
                                                    }
                                                    if (contains2) {
                                                        a10.b(a - a10.d(), a - a10.e(), a);
                                                        this.a.c(a10);
                                                        if (a2 == 0) {
                                                            break;
                                                        }
                                                    }
                                                }
                                                n = a10;
                                                if (a2 != 0) {
                                                    break Label_0649;
                                                }
                                                n.d();
                                            }
                                        }
                                        if (!contains2) {
                                            break Label_0774;
                                        }
                                    }
                                    final Rectangle[] e2 = n.a().a(a10).e(a10);
                                    final int a11 = e2.length;
                                    int a12 = 0;
                                    while (a12 < a11) {
                                        final Rectangle a13 = e2[a12];
                                        if (a2 == 0) {
                                            j = (a13.contains(a - a10.d(), a - a10.e()) ? 1 : 0);
                                            if (a2 != 0) {
                                                continue Label_0388;
                                            }
                                            if (j != 0) {
                                                a10.b(a - a10.d(), a - a10.e(), a);
                                                this.a.c(a10);
                                                return;
                                            }
                                            ++a12;
                                        }
                                        if (a2 != 0) {
                                            break;
                                        }
                                    }
                                }
                                ++a9;
                            }
                            break;
                        } while (a2 == 0);
                        return;
                    }
                    Label_0359: {
                        if (a2 == 0) {
                            if (!b && a2 == 0) {
                                break Label_0359;
                            }
                            final boolean d;
                            contains = (d = a10.d());
                        }
                        final n n2;
                        Label_0174: {
                            if (a2 == 0) {
                                if (b) {
                                    break Label_0359;
                                }
                                n2 = a10;
                                if (a2 != 0) {
                                    break Label_0174;
                                }
                                contains = n2.j().contains(a, a);
                            }
                            if (contains) {
                                break Label_0359;
                            }
                        }
                        final k[] a14 = n2.b();
                        final int a11 = a14.length;
                        int a12 = 0;
                        do {
                            int k = 0;
                        Label_0191:
                            while (k < a11) {
                                final k a15 = a14[a12];
                                final Rectangle[] a16 = a15.a().a(a15).e(a15);
                                final int a17 = a16.length;
                                i = 0;
                                if (a2 != 0) {
                                    continue Label_0100;
                                }
                                int a18 = i;
                                while (a18 < a17) {
                                    final Rectangle a19 = a16[a18];
                                    if (a2 == 0) {
                                        k = (a19.contains(a - a10.d() - a15.d(), a - a10.e() - a15.e()) ? 1 : 0);
                                        if (a2 != 0) {
                                            continue Label_0191;
                                        }
                                        if (k != 0) {
                                            a10.b(a - a10.d(), a - a10.e(), a);
                                            this.a.c(a10);
                                            return;
                                        }
                                        ++a18;
                                    }
                                    if (a2 != 0) {
                                        break;
                                    }
                                }
                                ++a12;
                            }
                            break;
                        } while (a2 == 0);
                    }
                    ++a9;
                }
                break;
            } while (a2 == 0);
            a7 = this.a.b();
            a8 = a7.length;
            boolean p = false;
            continue;
        }
    }
    
    public void func_146274_d() {
        super.func_146274_d();
        final int b = com.sun.jna.z.a.e.a.a.a.f.a.b;
        int a = Mouse.getEventDWheel();
        final int a2 = b;
        int n3;
        int n2;
        final int n = n2 = (n3 = a);
        if (a2 == 0) {
            if (n == 0) {
                return;
            }
            final int n4;
            n2 = (n4 = (n3 = a));
        }
        int n6;
        final int n5 = n6 = 1;
        if (a2 == 0) {
            if (n > n5) {
                a = 1;
            }
            n3 = (n2 = a);
            final int n7;
            n6 = (n7 = -1);
        }
        if (a2 == 0) {
            if (n2 < n5) {
                a = -1;
            }
            n3 = a;
            n6 = 7;
        }
        a = n3 * n6;
    }
    
    public void func_73863_a(final int a, final int a, final float a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          5
        //     5: getstatic       com/sun/jna/z/a/e/a/a/a/f/a.b:I
        //     8: new             Lnet/minecraft/client/gui/ScaledResolution;
        //    11: dup            
        //    12: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //    15: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //    18: getfield        net/minecraft/client/Minecraft.field_71443_c:I
        //    21: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //    24: getfield        net/minecraft/client/Minecraft.field_71440_d:I
        //    27: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;II)V
        //    30: astore          a
        //    32: aload_0         /* a */
        //    33: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //    36: getfield        com/sun/jna/z/a/i.c:Lcom/sun/jna/z/a/e/a/a/a/h;
        //    39: getfield        com/sun/jna/z/a/e/a/a/a/h.n:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    42: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.d:()I
        //    47: putfield        com/sun/jna/z/a/e/a/a/a/f/b.g:I
        //    50: istore          a
        //    52: aload_0         /* a */
        //    53: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //    56: getfield        com/sun/jna/z/a/i.c:Lcom/sun/jna/z/a/e/a/a/a/h;
        //    59: getfield        com/sun/jna/z/a/e/a/a/a/h.n:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //    62: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.e:()I
        //    67: iconst_4       
        //    68: isub           
        //    69: putfield        com/sun/jna/z/a/e/a/a/a/f/b.h:I
        //    72: invokestatic    com/sun/jna/z/a/e/a/a/a/h.a:()F
        //    75: fstore          a
        //    77: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //    80: fload           a
        //    82: fload           a
        //    84: fload           a
        //    86: invokestatic    org/lwjgl/opengl/GL11.glScalef:(FFF)V
        //    89: new             Ljava/awt/Color;
        //    92: dup            
        //    93: getstatic       com/sun/jna/z/a/e/a/a/a/h.e:Ljava/awt/Color;
        //    96: invokevirtual   java/awt/Color.getBlue:()I
        //    99: getstatic       com/sun/jna/z/a/e/a/a/a/h.e:Ljava/awt/Color;
        //   102: invokevirtual   java/awt/Color.getGreen:()I
        //   105: getstatic       com/sun/jna/z/a/e/a/a/a/h.e:Ljava/awt/Color;
        //   108: invokevirtual   java/awt/Color.getRed:()I
        //   111: invokespecial   java/awt/Color.<init>:(III)V
        //   114: astore          a
        //   116: aload_0         /* a */
        //   117: getfield        com/sun/jna/z/a/e/a/a/a/f/b.o:Lnet/minecraft/client/gui/FontRenderer;
        //   120: getstatic       com/sun/jna/z/a/e/a/a/a/f/b.q:[Ljava/lang/String;
        //   123: astore          a
        //   125: aload           a
        //   127: iconst_0       
        //   128: aaload         
        //   129: iconst_5       
        //   130: aload           a
        //   132: getfield        net/minecraft/client/Minecraft.field_71440_d:I
        //   135: iconst_2       
        //   136: idiv           
        //   137: bipush          25
        //   139: isub           
        //   140: aload           a
        //   142: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.b:(Ljava/awt/Color;)I
        //   145: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78261_a:(Ljava/lang/String;III)I
        //   148: pop            
        //   149: aload_0         /* a */
        //   150: getfield        com/sun/jna/z/a/e/a/a/a/f/b.o:Lnet/minecraft/client/gui/FontRenderer;
        //   153: new             Ljava/lang/StringBuilder;
        //   156: dup            
        //   157: invokespecial   java/lang/StringBuilder.<init>:()V
        //   160: aload           a
        //   162: iconst_3       
        //   163: aaload         
        //   164: iload           a
        //   166: ifne            194
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   172: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //   175: getfield        com/sun/jna/z/a/h.g:D
        //   178: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   181: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //   184: getfield        com/sun/jna/z/a/h.h:Z
        //   187: ifeq            197
        //   190: aload           a
        //   192: iconst_1       
        //   193: aaload         
        //   194: goto            199
        //   197: ldc             ""
        //   199: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   202: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   205: iconst_5       
        //   206: aload_0         /* a */
        //   207: getfield        com/sun/jna/z/a/e/a/a/a/f/b.o:Lnet/minecraft/client/gui/FontRenderer;
        //   210: getstatic       com/sun/jna/z/a/e/a/a/a/f/b.q:[Ljava/lang/String;
        //   213: iconst_4       
        //   214: aaload         
        //   215: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78256_a:(Ljava/lang/String;)I
        //   218: iadd           
        //   219: aload           a
        //   221: getfield        net/minecraft/client/Minecraft.field_71440_d:I
        //   224: iconst_2       
        //   225: idiv           
        //   226: bipush          25
        //   228: isub           
        //   229: iconst_m1      
        //   230: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78276_b:(Ljava/lang/String;III)I
        //   233: pop            
        //   234: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //   237: getfield        com/sun/jna/z/a/i.c:Lcom/sun/jna/z/a/e/a/a/a/h;
        //   240: iload           a
        //   242: ifne            403
        //   245: getfield        com/sun/jna/z/a/e/a/a/a/h.n:Lcom/sun/jna/z/a/e/a/a/a/a/n;
        //   248: invokeinterface com/sun/jna/z/a/e/a/a/a/a/n.p:()Z
        //   253: ifeq            392
        //   256: sipush          3042
        //   259: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   262: sipush          2884
        //   265: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   268: sipush          3553
        //   271: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   274: new             Ljava/awt/Color;
        //   277: dup            
        //   278: bipush          10
        //   280: bipush          10
        //   282: bipush          10
        //   284: sipush          200
        //   287: invokespecial   java/awt/Color.<init>:(IIII)V
        //   290: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.a:(Ljava/awt/Color;)V
        //   293: bipush          7
        //   295: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   298: aload_0         /* a */
        //   299: getfield        com/sun/jna/z/a/e/a/a/a/f/b.h:I
        //   302: bipush          19
        //   304: iadd           
        //   305: istore          a
        //   307: aload_0         /* a */
        //   308: getfield        com/sun/jna/z/a/e/a/a/a/f/b.g:I
        //   311: i2d            
        //   312: iload           a
        //   314: i2d            
        //   315: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   318: aload_0         /* a */
        //   319: getfield        com/sun/jna/z/a/e/a/a/a/f/b.g:I
        //   322: aload_0         /* a */
        //   323: getfield        com/sun/jna/z/a/e/a/a/a/f/b.i:I
        //   326: iadd           
        //   327: i2d            
        //   328: iload           a
        //   330: i2d            
        //   331: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   334: aload_0         /* a */
        //   335: getfield        com/sun/jna/z/a/e/a/a/a/f/b.g:I
        //   338: aload_0         /* a */
        //   339: getfield        com/sun/jna/z/a/e/a/a/a/f/b.i:I
        //   342: iadd           
        //   343: i2d            
        //   344: iload           a
        //   346: aload_0         /* a */
        //   347: getfield        com/sun/jna/z/a/e/a/a/a/f/b.j:I
        //   350: iadd           
        //   351: i2d            
        //   352: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   355: aload_0         /* a */
        //   356: getfield        com/sun/jna/z/a/e/a/a/a/f/b.g:I
        //   359: i2d            
        //   360: iload           a
        //   362: aload_0         /* a */
        //   363: getfield        com/sun/jna/z/a/e/a/a/a/f/b.j:I
        //   366: iadd           
        //   367: i2d            
        //   368: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   371: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   374: sipush          3553
        //   377: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   380: sipush          2884
        //   383: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   386: sipush          3042
        //   389: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   392: aload_0         /* a */
        //   393: getfield        com/sun/jna/z/a/e/a/a/a/f/b.c:Lcom/sun/jna/z/a/a/e;
        //   396: invokevirtual   com/sun/jna/z/a/a/e.f:()V
        //   399: aload_0         /* a */
        //   400: getfield        com/sun/jna/z/a/e/a/a/a/f/b.a:Lcom/sun/jna/z/a/e/a/a/a/j;
        //   403: invokeinterface com/sun/jna/z/a/e/a/a/a/j.d:()V
        //   408: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   411: aload_0         /* a */
        //   412: iload_1         /* a */
        //   413: iload_2         /* a */
        //   414: fload_3         /* a */
        //   415: invokespecial   net/minecraft/client/gui/GuiScreen.func_73863_a:(IIF)V
        //   418: getstatic       com/sun/jna/z/a/i.g:I
        //   421: ifeq            432
        //   424: iinc            a, 1
        //   427: iload           a
        //   429: putstatic       com/sun/jna/z/a/e/a/a/a/f/a.b:I
        //   432: return         
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
        final String[] q2 = new String[5];
        int n = 0;
        String s;
        int n2 = (s = "\u0019¡¢1\to¯§ \u0016««*¤\r\f¯¿=\u0011\u00ea\u008c.®¡t?\u0099").length();
        int n3 = 4;
        int n4 = -1;
    Label_0021:
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
                        Label_0244: {
                            if (n7 > 1) {
                                break Label_0244;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = 'O';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u00c0';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u00d2';
                                        break;
                                    }
                                    case 3: {
                                        c2 = 'T';
                                        break;
                                    }
                                    case 4: {
                                        c2 = 'r';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u00ca';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u00df';
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
                            q2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0021;
                            }
                            n2 = (s = "9\u00ed\u0005\u0019¡¢1R").length();
                            n3 = 2;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            q2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0021;
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
        q = q2;
    }
}
