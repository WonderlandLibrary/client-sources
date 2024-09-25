/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.Client;
import skizzle.commands.Command;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;

public class Settings
extends Command {
    public Settings() {
        super(Qprot0.0("\ue23c\u71ce\ud977\ua7f0\ucdd5\u67bb\u8c28"), Qprot0.0("\ue22c\u71c3\ud962\ua7ea\ucddb\u67b0\u8c3c\u8e1a\u5716\u669e\ued9d\uaf4c\u6092\u7248\ub9f1\u7e1e\u42e0\u5476\u1707\ud28e\u1b89\u01c5\uf24c\ue82c\u0a7a\u5f48\u2f13\u373e\u4255\u4757\u03e2\u8810"), Qprot0.0("\ue21c\u71ce\ud977\ua7f0\ucdd5\u67bb\u8c28\u8e1a\u575e\u669b\ued97\uaf08\u6094\u7241\ub9e0\u7e54\u42a9\u5424\u1713\ud298\u1bdd\u01de\uf243\ue862\u0a7c\u5f56\u2f5e\u376d\u4247\u4743\u03e2\u8800\u6fed\uad74"), "s");
        Settings Nigga;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        if (Nigga.length > 0) {
            String Nigga3 = Nigga[0];
            String Nigga4 = Nigga[1];
            String Nigga5 = Nigga[2];
            for (Module Nigga6 : Client.modules) {
                if (!Nigga6.name.equalsIgnoreCase(Nigga3)) continue;
                for (Setting Nigga7 : Nigga6.settings) {
                    Settings Nigga8;
                    int Nigga9;
                    if (!Nigga7.name.replace(" ", "").equalsIgnoreCase(Nigga4)) continue;
                    if (Nigga7 instanceof BooleanSetting) {
                        if (Nigga5.equalsIgnoreCase(Qprot0.0("\ue20b\u71c2\ud970\u45fb\uee24\u67b9\u8c2a\u8e5e")) || Nigga5.equalsIgnoreCase(Qprot0.0("\ue20a\u71c5\ud962\u45f8\uee2a\u67b0\u8c2b"))) {
                            Nigga9 = Nigga5.equalsIgnoreCase(Qprot0.0("\ue20a\u71c5\ud962\u45f8\uee2a\u67b0\u8c2b"));
                            ((BooleanSetting)Nigga7).setEnabled(Nigga9 != 0);
                            Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud9a4\u45ad\uee15\u67b0\u8c3b\u8e1a") + Nigga7.name + Qprot0.0("\ue24f\u71c4\ud965\u45ba") + Nigga6.name + Qprot0.0("\ue24f\u71df\ud96c\u45ba") + Nigga5.toLowerCase());
                        } else {
                            Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud925\u45f9\uee0f\u67bb\u8c39\u8e5b\ub510\u4565\ued9c\uaf4c\u60b4\u9040\u9a1e\u7e0d\u42ec\u5436\uf55e\uf121\u1b9e\u01ff\uf259\u0a73\u2986\u5f0d\u2f44\u3771") + Nigga8.syntax);
                        }
                    }
                    if (Nigga7 instanceof NumberSetting) {
                        try {
                            Double Nigga10 = Double.parseDouble(Nigga5);
                            ((NumberSetting)Nigga7).setValue(Nigga10);
                            Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud9a4\u45ad\uee15\u67b0\u8c3b\u8e1a") + Nigga7.name + Qprot0.0("\ue24f\u71c4\ud965\u45ba") + Nigga6.name + Qprot0.0("\ue24f\u71df\ud96c\u45ba") + Nigga10);
                        }
                        catch (Exception exception) {
                            Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud925\u45f9\uee0f\u67bb\u8c39\u8e5b\ub510\u4565\ued9c\uaf4c\u60b4\u9040\u9a1e\u7e0d\u42ec\u5436\uf55e\uf121\u1b9e\u01ff\uf259\u0a73\u2986\u5f0d\u2f44\u3771") + Nigga8.syntax);
                        }
                    }
                    if (!(Nigga7 instanceof ModeSetting)) continue;
                    Nigga9 = 0;
                    boolean Nigga11 = false;
                    for (String Nigga12 : ((ModeSetting)Nigga7).modes) {
                        if (Nigga12.replace(" ", "").equalsIgnoreCase(Nigga5)) {
                            Nigga11 = true;
                            ((ModeSetting)Nigga7).setMode(Nigga9);
                            Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud9a4\u45ad\uee15\u67b0\u8c3b\u8e1a") + Nigga7.name + Qprot0.0("\ue24f\u71c4\ud965\u45ba") + Nigga6.name + Qprot0.0("\ue24f\u71df\ud96c\u45ba") + ((ModeSetting)Nigga7).getMode());
                        }
                        ++Nigga9;
                    }
                    if (Nigga11) continue;
                    Nigga8.mc.thePlayer.skizzleMessage(Qprot0.0("\ue24f\u718b\ud925\u45f9\uee0f\u67bb\u8c39\u8e5b\ub510\u4565\ued9c\uaf4c\u60b4\u9040\u9a1e\u7e0d\u42ec\u5436\uf55e\uf121\u1b9e\u01ff\uf259\u0a73\u2986\u5f0d\u2f44\u3771") + Nigga8.syntax);
                }
            }
        }
    }

    public static {
        throw throwable;
    }
}

