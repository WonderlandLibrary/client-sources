/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import net.minecraft.client.Minecraft;
import skizzle.commands.Command;
import skizzle.util.Timer;

public class Spammer
extends Command {
    public Thread spammerThread = null;

    public static {
        throw throwable;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        Spammer Nigga3;
        if (Nigga.length == 1 && Nigga[0].equals(Qprot0.0("\u1e86\u71df\u25e6\u45ea"))) {
            if (Nigga3.spammerThread != null) {
                Nigga3.spammerThread.stop();
                Nigga3.spammerThread = null;
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u1ed5\u718b\u25af\u45ad\ud29f\u9b3b\u8c20\u72c0\ub50c\u79e3\u1106\uaf4c\u9c18\u9043\ua694\u829d\u42e4\ua8f7\uf50c\ucdad\ue747\u01c2\u0ed2\u0a77\u150a\ua396\u2f5f"));
            } else {
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u1ed5\u718b\u25af\u45ad\ud28d\u9b6f\u8c3c\u72c0\ub51d\u79eb\u110f\uaf09\u9c19\u9013\ua69c\u8283\u42e7\ua8b5\uf50a\ucdad\ue750\u01df\u0ed2\u0a60\u150e\ua39c\u2f0a\ucbb7\ua056\u5872\uff66\u8800\u936c\u4f3a\uc868\u3ca5\uddb8\u744f\ua8a4\uf667"));
            }
        }
        if (Nigga.length >= 2) {
            if (Nigga3.spammerThread != null) {
                Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u1ed5\u718b\u25af\u45ad\ud298\u9b27\u8c2a\u72c2\ub519\u79a6\u110b\uaf1f\u9c4b\u9052\ua699\u8282\u42ec\ua8f3\uf51a\ucdf4\ue713\u01cb\u0e80\u0a61\u151b\ua393\u2f13\ucbb6\ua04a\u5820\uff34\u8807\u9377\u4f3a\uc86f\u3ca2\uddb1\u7406\ua8ab"));
                return;
            }
            try {
                double Nigga4 = Double.parseDouble(Nigga[0]);
                String Nigga5 = "";
                for (int Nigga6 = 1; Nigga6 < Nigga.length; ++Nigga6) {
                    Nigga5 = String.valueOf(Nigga5) + " " + Nigga[Nigga6];
                }
                Nigga3.spammerThread = Nigga3.spamThread(Nigga5, Nigga4);
            }
            catch (Exception Nigga7) {
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u1ed5\u718b\u25af\u45ad\ud29c\u9b23\u8c2a\u72d1\ub50f\u79e3\u1142\uaf19\u9c18\u9056\ua6cf\u82d0\u42af\ua8f0\uf550") + Nigga3.syntax);
            }
        }
    }

    public Spammer() {
        super(Qprot0.0("\u1ea6\u71db\u25e8\ua7e9\u30a7\u9b2a\u8c3d"), Qprot0.0("\u1ea6\u71db\u25e8\ua7e9\u30b9\u9b6f\u8c2e\u7290\u570f\u9be5\u1111\uaf1f\u9c0a\u724a\u4496"), Qprot0.0("\u1e86\u71db\u25e8\ua7e9\u30ea\u9b67\u8c73\u72d4\u5707\u9bec\u1103\uaf15\u9c4b\u7244\u449d\u82d0\u42e4\ua8fb\u170c\u2fe2\ue740\u0194\u0e80\ue830\uf700\ua397\u2f0d\ucba8\u4250\uba33\uff71\u884b\u9322\uad36\u2a27\u3cb8\uddab\u740e\u4ae4\u1466"), Qprot0.0("\u1e86\u71db\u25e8\ua7e9"));
        Spammer Nigga;
    }

    public static void lambda$0(String string, double d) {
        Timer Nigga = new Timer();
        String Nigga2 = string;
        long Nigga3 = (long)d;
        while (true) {
            if (!Nigga.hasTimeElapsed(Nigga3, true)) {
                continue;
            }
            Minecraft.getMinecraft().thePlayer.sendChatMessage(Nigga2);
        }
    }

    public Thread spamThread(String Nigga, double Nigga2) {
        Thread Nigga3 = new Thread(() -> Spammer.lambda$0(Nigga, Nigga2));
        Nigga3.start();
        return Nigga3;
    }
}

