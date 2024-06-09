package md;

import com.sun.jna.z.a.d.*;
import cpw.mods.fml.common.gameevent.*;
import net.minecraft.potion.*;

public class k extends c
{
    public static int o;
    public static boolean p;
    public static int q;
    public static int r;
    public static int s;
    public static boolean t;
    public static boolean u;
    public static int v;
    public static boolean w;
    public static boolean x;
    public static int y;
    public static boolean z;
    public static boolean A;
    public static int B;
    public static boolean C;
    public static int D;
    public static boolean E;
    public static boolean F;
    public static boolean G;
    private static final String H;
    
    public k() {
        super(k.H, com.sun.jna.z.a.d.b.Render, -256);
    }
    
    @Override
    public void a(final TickEvent$PlayerTickEvent a) {
        final int g = k.G ? 1 : 0;
        this.a.field_71439_g.func_70690_d(new PotionEffect(16, 5200, 0));
        final int a2 = g;
        if (a2 != 0) {
            int a3 = k.o;
            k.o = ++a3;
        }
    }
    
    @Override
    public void f() {
        this.a.field_71439_g.func_82170_o(16);
    }
    
    static {
        final char[] charArray = ",O©\u00dds\u00cb\n\rR±".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0123: {
                if (n > 1) {
                    break Label_0123;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = 'j';
                            break;
                        }
                        case 1: {
                            c2 = ':';
                            break;
                        }
                        case 2: {
                            c2 = '\u00c5';
                            break;
                        }
                        case 3: {
                            c2 = '±';
                            break;
                        }
                        case 4: {
                            c2 = '\u0011';
                            break;
                        }
                        case 5: {
                            c2 = '¹';
                            break;
                        }
                        default: {
                            c2 = 'c';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                H = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
