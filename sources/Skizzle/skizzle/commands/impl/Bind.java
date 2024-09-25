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

public class Bind
extends Command {
    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length == 2) {
            Bind Nigga3;
            String Nigga4 = Nigga[0];
            String Nigga5 = Nigga[1];
            boolean Nigga6 = false;
            for (Module Nigga7 : Client.modules) {
                if (!Nigga7.name.equalsIgnoreCase(Nigga4)) continue;
                if (Nigga5.equalsIgnoreCase(Qprot0.0("\u6c32\u71c7\u5740\u45fb\u61da"))) {
                    Nigga7.keyCode.setKeyCode(0);
                    Nigga3.mc.thePlayer.skizzleMessage(String.format(Qprot0.0("\u6c71\u718b\u5703\u45ad\u61fd\ue985\u8c2ds\ub509\uca8c\u63a2\uaf4c\ueee2\u9040"), Nigga7.name));
                    return;
                }
                if (Keyboard.getKeyIndex((String)Nigga5.toUpperCase()) != 0) {
                    Nigga7.keyCode.setKeyCode(Keyboard.getKeyIndex((String)Nigga5.toUpperCase()));
                    Nigga3.mc.thePlayer.skizzleMessage(String.format(Qprot0.0("\u6c71\u718b\u5703\u45ad\u61ea\ue984\u8c3ar\ub518\ucac2\u63e0\uaf0e\ueee2\u9040\u15b1\uf072\u42be\uda4a\uf511\u7ec9\u95b1\u01c8\u7c29\u0a61"), Nigga7.name, Keyboard.getKeyName((int)Nigga7.getKey())));
                    Nigga6 = true;
                    break;
                }
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u6c71\u718b\u5703\u45f9\u61e1\ue985\u8c39}\ub510\uca8b\u63a2\uaf4c\uee8c\u9056\u15e8\uf075"));
                return;
            }
            if (!Nigga6) {
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u6c71\u718b\u5703\u45f9\u61eb\ue984\u8c3ap\ub518\ucac2\u63a8\uaf03\ueeb3\u9013\u15f7\uf03d\u42e7\uda5a\uf55e\u7e88\u95b7\u01c7\u7c63\u0a76\ua67a\ud13a\u2f1b\ub957\ua041\ueb57\u8ddd\u8810\ue1ca\u4f74") + Nigga4);
            }
        }
    }

    public Bind() {
        super(Qprot0.0("\u6c13\u71c2\u574b\ua7e0"), Qprot0.0("\u6c13\u71c2\u574b\ua7e0\u4fd5\ue9cb\u8c2e<\u570f\ue483\u63a2\uaf19\ueeab\u7248\u3bbf\uf036\u42f0\uda1e\u170e\u5086\u95fa\u01cf"), Qprot0.0("\u6c33\u71c2\u574b\ua7e0\u4f86\ue9d7\u8c21}\u570f\ue489\u63f8\uaf4c\ueefb\u7246\u3bfa\uf02d\u42b7"), "b");
        Bind Nigga;
    }

    public static {
        throw throwable;
    }
}

