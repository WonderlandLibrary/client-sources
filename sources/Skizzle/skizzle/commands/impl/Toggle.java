/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.Client;
import skizzle.commands.Command;
import skizzle.modules.Module;

public class Toggle
extends Command {
    public Toggle() {
        super(Qprot0.0("\u186c\u71c4\u232b\ua7e3\u33e3\u9de7"), Qprot0.0("\u186c\u71c4\u232b\ua7e3\u33e3\u9de7\u8c3c\u7455\u5703\u98e5\u17c2\uaf03\u9aca\u7258\u47da\u8458\u42a9\uae35\u1719\u2cee\ue190\u01cb\u0808\ue869"), Qprot0.0("\u184c\u71c4\u232b\ua7e3\u33e3\u9de7\u8c6f\u7449\u570c\u98a4\u17c2\uaf09\u9a90"), "t");
        Toggle Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length > 0) {
            Toggle Nigga3;
            String Nigga4 = Nigga[0];
            boolean Nigga5 = false;
            for (Module Nigga6 : Client.modules) {
                if (!Nigga6.name.equalsIgnoreCase(Nigga4)) continue;
                Nigga6.toggle();
                Nigga3.mc.thePlayer.skizzleMessage(String.valueOf(Nigga6.isEnabled() ? Qprot0.0("\u1818\u718b\u236a\u45ad\ud5d4\u9dec\u8c2e\u7417\ub510\u7ebe\u17cb") : Qprot0.0("\u1818\u718b\u236a\u45ad\ud5d5\u9deb\u8c3c\u7414\ub51e\u7eb7\u17ca\uaf08")) + " " + Nigga6.name);
                Nigga5 = true;
                break;
            }
            if (!Nigga5) {
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u181e\u71c8\u230f\u45f5\ud5e4\u9dee\u8c2b\u741b\ub55b\u7eaf\u178f\uaf0a\u9ac7\u905d\ua1cc\u841d\u42e4\uae38\uf51a\ucaa5\ue192\u01cf\u0844"));
            }
        }
    }
}

