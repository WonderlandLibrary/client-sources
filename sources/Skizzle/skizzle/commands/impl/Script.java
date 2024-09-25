/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.commands.Command;
import skizzle.scripts.ScriptManager;

public class Script
extends Command {
    public Script() {
        super(Qprot0.0("\u2294\u71c8\u19c9\ua7ed\u0c64\ua709"), Qprot0.0("\u2294\u71c8\u19c9\ua7ed\u0c64\ua709\u8c6f\u4eef\u5703\ua730\u2d31\uaf0b\ua030\u7243\u784a\ubee2\u42ea\u94cf\u170d\u1338\udb60\u01c4\u32e1\ue868"), Qprot0.0("\u22b4\u71c8\u19c9\ua7ed\u0c64\ua709\u8c6f\u4ebe\u5710\ua73b\u2d3c\uaf03\ua038\u7249\u7813"), Qprot0.0("\u22b4\u71c8\u19c9\ua7ed\u0c64\ua709\u8c3c"));
        Script Nigga;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length == 1) {
            Script Nigga3;
            long Nigga4 = System.currentTimeMillis();
            Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u22e1\u71c9\u199d\u45f6\u2e4d\ua716\u8c26\u4ef8\ub506\u8538\u2d35"));
            Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u22e7\u718b\u199d\u45ad\u2e4c\ua718\u8c23\u4eed\ub51d\u8530\u2d39\uaf02\ua03e\u9013\u5a54\ubea1\u42fb\u94c9\uf50e\u312b\udb72\u0184\u32bc\u0a3c"));
            ScriptManager.addScripts();
            Nigga3.mc.thePlayer.messagePlayer(String.format(Qprot0.0("\u22e7\u718b\u199d\u45ad\u2e58\ua714\u8c21\u4eeb\ub50f\u853c\u2d35\uaf08\ua079\u9041\u5a42\ubeae\u42e6\u94c1\uf51a\u317f\udb72\u01c9\u32e0\u0a7b\ue9c9\u9fb4\u2f0d\uf7c8\ua00f\ua4a6\uc31e\u885d\uaf15\u4f27\u34a0\u00d0"), (double)(System.currentTimeMillis() - Nigga4) / 1000.0));
            Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u22e7\u718b\u199d\u45ad"));
        }
    }

    public static {
        throw throwable;
    }
}

