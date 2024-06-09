package md;

import net.minecraft.entity.player.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.client.settings.*;
import net.minecraft.item.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class n extends c
{
    g n;
    boolean o;
    InventoryPlayer p;
    int q;
    t r;
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
    
    public n() {
        final String[] a = md.n.L;
        super(a[1], com.sun.jna.z.a.d.b.Utility, 0);
        final int a2 = md.n.K ? 1 : 0;
        this.r = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[0], 100.0, 0.0, 200.0, 1);
        if (md.n.s != 0) {
            md.n.K = (a2 == 0);
        }
    }
    
    @Override
    public void h() {
        final int a = this.b().field_71474_y.field_74313_G.func_151463_i();
        KeyBinding.func_74510_a(a, true);
        KeyBinding.func_74507_a(a);
        KeyBinding.func_74510_a(a, false);
        try {
            Thread.sleep((long)this.r.a());
        }
        catch (InterruptedException ex) {}
        this.p.field_70461_c = this.q;
    }
    
    @Override
    public void e() {
        final int k = md.n.K ? 1 : 0;
        this.o = true;
        final int a = k;
        n n = this;
        if (a == 0) {
            if (this.n == null) {
                this.n = new g(this);
            }
            this.p = this.c().field_71071_by;
            n = this;
        }
        n.q = this.p.field_70461_c;
        int a2 = 0;
        while (a2 < 9) {
            final ItemStack a3 = this.p.field_70462_a[a2];
            if (a != 0) {
                return;
            }
            final ItemStack itemStack = a3;
            Label_0142: {
                if (a == 0 && itemStack == null) {
                    if (a != 0) {
                        int a4 = md.n.s;
                        md.n.s = ++a4;
                        goto Label_0101;
                    }
                }
                else {
                    final Item a5 = itemStack.func_77973_b();
                    if (a != 0) {
                        break Label_0142;
                    }
                    if (a5 instanceof ItemEnderPearl) {
                        this.p.field_70461_c = a2;
                        this.n.a();
                        if (a == 0) {
                            break;
                        }
                    }
                }
                ++a2;
            }
            if (a != 0) {
                break;
            }
        }
        this.a(false);
    }
    
    @Override
    public k[] k() {
        return new k[] { this.r };
    }
    
    @Override
    public void g() {
        final int k = md.n.K ? 1 : 0;
        this.o = false;
        final int a = k;
        n n = this;
        if (a == 0) {
            if (this.n != null) {
                this.n.a = null;
            }
            n = this;
        }
        n.n = null;
    }
    
    static {
        final String[] l = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "Z\u009b¤:\u0082\nN\u009b©)\u0097\u00cc9l\u0091¿").length();
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
                    Label_0188: {
                        if (n4 > 1) {
                            break Label_0188;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = '\u001e';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u00fe';
                                    break;
                                }
                                case 2: {
                                    c2 = '\u00c8';
                                    break;
                                }
                                case 3: {
                                    c2 = '[';
                                    break;
                                }
                                case 4: {
                                    c2 = '\u00fb';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u0098';
                                    break;
                                }
                                default: {
                                    c2 = 'Q';
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
