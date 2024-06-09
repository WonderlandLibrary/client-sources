package com.sun.jna.z.a.f;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.scoreboard.*;

public class f
{
    public List<String> a;
    public static boolean b;
    private static final String c;
    
    public f() {
        this.a = new ArrayList<String>();
    }
    
    public boolean a(final Entity a) {
        final int a2 = f.b ? 1 : 0;
        final boolean b = a instanceof EntityOtherPlayerMP;
        if (a2 != 0 || !b) {
            return b;
        }
        final EntityOtherPlayerMP a3 = (EntityOtherPlayerMP)a;
        if (a2 != 0) {
            goto Label_0024;
        }
        final String a4 = StringUtils.func_76338_a(a3.func_70005_c_().toLowerCase().trim());
        final boolean contains = this.a.contains(a4);
        if (a2 != 0 || contains) {
            return contains;
        }
        final boolean contains2 = a4.contains(f.c);
        if (a2 == 0 && contains2) {
            goto Label_0074;
        }
        return contains2;
    }
    
    public boolean b(final Entity a) {
        final int a2 = f.b ? 1 : 0;
        final boolean b = a instanceof EntityOtherPlayerMP;
        if (a2 != 0 || !b) {
            return b;
        }
        final EntityOtherPlayerMP a3 = (EntityOtherPlayerMP)a;
        if (a2 != 0) {
            goto Label_0024;
        }
        Team team2;
        final Team team = team2 = a3.func_96124_cp();
        if (a2 == 0) {
            if (team == null) {
                return false;
            }
            final Team func_96124_cp;
            team2 = (func_96124_cp = Minecraft.func_71410_x().field_71439_g.func_96124_cp());
        }
        if (a2 == 0) {
            if (team == null) {
                return false;
            }
            team2 = Minecraft.func_71410_x().field_71439_g.func_96124_cp();
        }
        return team2.func_142054_a(a3.func_96124_cp());
    }
    
    static {
        final char[] charArray = "UR\u001c#ª¯".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0122: {
                if (n > 1) {
                    break Label_0122;
                }
                length = (n2 = n3);
                do {
                    final char c2 = charArray[n2];
                    char c3 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c3 = '\u0018';
                            break;
                        }
                        case 1: {
                            c3 = '3';
                            break;
                        }
                        case 2: {
                            c3 = 'r';
                            break;
                        }
                        case 3: {
                            c3 = 'W';
                            break;
                        }
                        case 4: {
                            c3 = '\u00c2';
                            break;
                        }
                        case 5: {
                            c3 = '\u00ca';
                            break;
                        }
                        default: {
                            c3 = '\r';
                            break;
                        }
                    }
                    charArray[length] = (char)(c2 ^ c3);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                c = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
