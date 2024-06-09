package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import com.sun.jna.z.a.d.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import com.sun.jna.z.a.*;
import com.sun.jna.z.a.a.*;
import net.minecraft.entity.*;
import java.util.*;

public class d implements IEventListener
{
    private static final String[] a;
    
    public void invoke(final Event a) {
        final int b = com.sun.jna.z.a.c.b.b ? 1 : 0;
        final MouseEvent a2 = (MouseEvent)a;
        final int a3 = b;
        int n2;
        int button;
        final int n = button = (n2 = (a2.buttonstate ? 1 : 0));
        if (a3 == 0) {
            if (n != 0) {
                h.d.a.a.values().stream().filter(d::a).forEach(c::d);
            }
            n2 = (button = a2.button);
        }
        Label_0240: {
            Label_0230: {
                if (a3 == 0) {
                    if (button != 2) {
                        break Label_0230;
                    }
                    n2 = (a2.buttonstate ? 1 : 0);
                }
                if (n2 != 0) {
                    final Entity a4 = Minecraft.func_71410_x().field_71476_x.field_72308_g;
                    if (a3 != 0) {
                        break Label_0240;
                    }
                    if (a4 instanceof EntityOtherPlayerMP) {
                        final String a5 = StringUtils.func_76338_a(a4.func_70005_c_().toLowerCase().trim());
                        final boolean a8 = i.f.d.a(a4);
                        if (a3 == 0) {
                            if (a8) {
                                i.f.d.a.remove(a5);
                                final StringBuilder append = new StringBuilder().append(a5);
                                final String[] a6 = d.a;
                                a.e(append.append(a6[0]).toString());
                                if (a3 == 0) {
                                    break Label_0230;
                                }
                            }
                            i.f.d.a.add(a5);
                        }
                        a.e(a5 + d.a[1]);
                    }
                }
            }
            h.d.k.a(a2);
        }
        for (final c c : h.d.a.a.values()) {
            final c a7 = c;
            if (a3 != 0 || c.c()) {
                c.a((MouseEvent)a);
            }
            if (a3 != 0) {
                break;
            }
        }
        if (i.g != 0) {
            com.sun.jna.z.a.c.b.b = (a3 == 0);
        }
    }
    
    private static boolean a(final MouseEvent a, final c a) {
        final int a2 = b.b ? 1 : 0;
        int b;
        final int n = b = a.b();
        if (a2 == 0) {
            if (n == a.button - 100) {
                b = (true ? 1 : 0);
            }
            else {
                b = (false ? 1 : 0);
            }
        }
        return b != 0;
    }
    
    static {
        final String[] a2 = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "\u008d³\u00d25?V\u00df\u00c9\u00e1\u00d1*?M\u009a\u00cb³\u00de=>D\u00c9\u0016\u008d \u00d3<5D\u009a\u00d9®\u0097>\"I\u00df\u00c3¥\u00c4x<I\u00c9\u00d9").length();
        int char1 = 21;
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
                                    c2 = '\u00ad';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u00c1';
                                    break;
                                }
                                case 2: {
                                    c2 = '·';
                                    break;
                                }
                                case 3: {
                                    c2 = 'X';
                                    break;
                                }
                                case 4: {
                                    c2 = 'P';
                                    break;
                                }
                                case 5: {
                                    c2 = ' ';
                                    break;
                                }
                                default: {
                                    c2 = 'º';
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
                a2[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        a = a2;
    }
}
