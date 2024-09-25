/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.Client;
import skizzle.commands.Command;
import skizzle.modules.Module;

public class test
extends Command {
    public static {
        throw throwable;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length >= 1 && Nigga[0].equals("2")) {
            for (Module Nigga3 : Client.modules) {
                if (Nigga3.category == Module.Category.PROFILES || !Nigga3.toggled) continue;
                Nigga3.toggle();
                Nigga3.toggle();
            }
        }
    }

    public test() {
        super(Qprot0.0("\u186a\u71ce\u2321\ua7f0"), Qprot0.0("\u186a\u71ce\u2321\ua7f0\u339e\u9d84\u8c3b\u7403\u570b\u98c9\u17ee\uaf1f"), Qprot0.0("\u186a\u71ce\u2321\ua7f0\u33cd\u9d98\u8c3b\u740e\u5711\u98d3\u17b7"), "b");
        test Nigga;
    }
}

