/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.Client;
import skizzle.commands.Command;

public class Help
extends Command {
    public static {
        throw throwable;
    }

    public Help() {
        super(Qprot0.0("\u1b05\u71ce\u204d\ua7f4"), Qprot0.0("\u1b14\u71c4\u2054\ua7a4\u32c9\u9e99\u8c20\u776f\u5742\u999c\u14b2\uaf05\u99b0\u720d\u46f4\u8726\u42ec\uad14\u174e\u2dcd"), Qprot0.0("\u1b25\u71ce\u204d\ua7f4"), "h");
        Help Nigga;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        Client.commandManager.showHelpMenu();
    }
}

