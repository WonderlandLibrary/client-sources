/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.commands.impl;

import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.commands.Command;
import skizzle.modules.Module;

public class Login
extends Command {
    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length == 2) {
            Login Nigga3;
            String Nigga4 = Nigga[0];
            String Nigga5 = Nigga[1];
            boolean Nigga6 = false;
            for (Module Nigga7 : Client.modules) {
                if (!Nigga7.name.equalsIgnoreCase(Nigga4)) continue;
                if (Nigga5.equalsIgnoreCase(Qprot0.0("\uf01e\u71c7\ucb94\u45fb\ufc26"))) {
                    Nigga7.keyCode.setKeyCode(0);
                    Nigga3.mc.thePlayer.skizzleMessage(String.format(Qprot0.0("\uf05d\u718b\ucbd7\u45ad\ufc01\u75a9\u8c2d\u9ca7\ub509\u5770\uff8e\uaf4c\u7236\u9040"), Nigga7.name));
                    return;
                }
                if (Keyboard.getKeyIndex((String)Nigga5.toUpperCase()) != 0) {
                    Nigga7.keyCode.setKeyCode(Keyboard.getKeyIndex((String)Nigga5.toUpperCase()));
                    Nigga3.mc.thePlayer.skizzleMessage(String.format(Qprot0.0("\uf05d\u718b\ucbd7\u45ad\ufc16\u75a8\u8c3a\u9ca6\ub518\u573e\uffcc\uaf0e\u7236\u9040\u884d\u6c5e\u42be\u469e\uf511\ue335\u099d\u01c8\ue0fd\u0a61"), Nigga7.name, Keyboard.getKeyName((int)Nigga7.getKey())));
                    Nigga6 = true;
                    break;
                }
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\uf05d\u718b\ucbd7\u45f9\ufc1d\u75a9\u8c39\u9ca9\ub510\u5777\uff8e\uaf4c\u7258\u9056\u8814\u6c59"));
                return;
            }
            if (!Nigga6) {
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\uf05d\u718b\ucbd7\u45f9\ufc17\u75a8\u8c3a\u9ca4\ub518\u573e\uff84\uaf03\u7267\u9013\u880b\u6c11\u42e7\u468e\uf55e\ue374\u099b\u01c7\ue0b7\u0a76\u3b86\u4d16\u2f1b\u2583\ua041\u76ab\u11f1\u8810\u7d1e\u4f74") + Nigga4);
            }
        }
    }

    public Login() {
        super(Qprot0.0("\uf031\u71c4\ucb96\ua7ed\uda3c"), Qprot0.0("\uf031\u71c4\ucb96\ua7f7\uda72\u75ae\u8c21\u9cbc\u570d\u7138\uff8b\uaf02\u7233\u724c\uae08\u6c1b\u42e6\u469f\u170e\uc567"), Qprot0.0("\uf01f\u71c2\ucb9f\ua7e0\uda72\u75fb\u8c21\u9ca9\u570f\u717d\uffd4\uaf4c\u722f\u7246\uae0e\u6c01\u42b7"), "b");
        Login Nigga;
    }

    public static {
        throw throwable;
    }
}

