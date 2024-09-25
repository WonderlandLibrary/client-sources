/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import java.io.File;
import java.io.IOException;
import net.minecraft.util.Util;
import skizzle.Client;
import skizzle.commands.Command;
import skizzle.files.ProfileManager;

public class Profile
extends Command {
    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        Object Nigga3;
        Profile Nigga4;
        if (Nigga.length == 1) {
            if (Nigga[0].equalsIgnoreCase(Qprot0.0("\u1551\u71c4\u2e52\u45fb\u26e0\u90ee\u8c20\u7966"))) {
                Nigga3 = new File(String.valueOf(Nigga4.mc.mcDataDir.getAbsolutePath()) + Qprot0.0("\u1561\u71f8\u2e5a\u45f3\u26ee\u90fd\u8c23\u796d\ub520\u8dae\u1ad8\uaf03\u97b5\u905a\u52c1\u895d\u42fa"));
                String Nigga5 = ((File)Nigga3).getAbsolutePath();
                if (Util.getOSType() == Util.EnumOS.OSX) {
                    try {
                        Runtime.getRuntime().exec(new String[]{Qprot0.0("\u1512\u71de\u2e42\u45e8\u26bb\u90e5\u8c26\u7966\ub553\u8db1\u1ada\uaf09\u97bd"), Nigga5});
                        return;
                    }
                    catch (IOException iOException) {}
                } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                    String Nigga6 = String.format(Qprot0.0("\u155e\u71c6\u2e55\u45b4\u26f1\u90ff\u8c2a\u7928\ub553\u8d9d\u1a8a\uaf1f\u97a7\u9052\u52df\u894c\u42a9\ua308\uf531\u39a5\uec9e\u01c4\u0538\u0a74\ue15a\ua856\u2f1b\uc041\ua00f\uac28\uf4f9\u8806\u9898"), Nigga5);
                    try {
                        Runtime.getRuntime().exec(Nigga6);
                        return;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
            if (Nigga[0].equalsIgnoreCase(Qprot0.0("\u154f\u71ce\u2e5d\u45f5\u26f5\u90e3"))) {
                ProfileManager.updateProfiles();
            }
        }
        if (Nigga.length == 2) {
            Nigga3 = Nigga[1];
            if (Nigga[0].equalsIgnoreCase(Qprot0.0("\u154e\u71ca\u2e47\u45ff"))) {
                if (ProfileManager.isProfile((String)Nigga3)) {
                    Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45f9\u26c0\u90ef\u8c26\u797b\ub55c\u8dae\u1ad8\uaf03\u97b5\u905a\u52c1\u895d\u42a9\ua34b\uf512\u39a7\uec9e\u01cb\u057c\u0a6b\ue113\ua85f\u2f06\uc00a\ua05c\uac7e\uf4af\u8854"));
                } else {
                    ProfileManager.saveProfile((String)Nigga3);
                    Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45ad\u26c7\u90e6\u8c39\u796d\ub518\u8dfe\u1ada\uaf1e\u97bc\u9055\u52c4\u8954\u42ec\ua30a\uf51f\u39a6\uecdb\u018c\u057a") + (String)Nigga3 + Qprot0.0("\u151b\u719c\u2e1f\u4593"));
                }
            }
            if (Nigga[0].equalsIgnoreCase(Qprot0.0("\u1551\u71c4\u2e50\u45fe"))) {
                if (ProfileManager.isProfile((String)Nigga3)) {
                    Client.currentProfile = Nigga3;
                } else {
                    Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45f9\u26c0\u90ef\u8c26\u797b\ub55c\u8dae\u1ad8\uaf03\u97b5\u905a\u52c1\u895d\u42a9\ua34e\uf511\u39b0\uec88\u01c4\u053f\u0a66\ue113\ua85f\u2f06\uc00a\ua05c\uac7e\uf4fd"));
                }
            }
            if (Nigga[0].equalsIgnoreCase(Qprot0.0("\u1559\u71ce\u2e5d\u45ff\u26e0\u90e2"))) {
                if (ProfileManager.getProfiles().length == 1) {
                    Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45f9\u26cd\u90e8\u8c3a\u7928\ub51f\u8dbf\u1ac4\uaf02\u97bc\u9047\u528d\u895c\u42ec\ua346\uf51b\u39a1\uec9e\u018a\u0579\u0a7c\ue14a\ua81a\u2f13\uc00c\ua05d\uac6f\uf4fc\u8805\u98c8\u4f3b\u3c3f\u376a\uddb3\u7fbc\ua8f9\u0231\u1643\u3c58\u0def\udbd6\uc672\ub37a\ua14b\u2699\uf229\u6576\u39cc\uf41e\ua805\ud2ab\u734b\u304d\u690b\u599e\u57ae\u83a9\ubf17\u09d2\u771a\udca8"));
                    return;
                }
                if (ProfileManager.isProfile((String)Nigga3)) {
                    if (Client.currentProfile.equals(Nigga3)) {
                        ProfileManager.setProfile(Qprot0.0("\u1559\u71ce\u2e57\u45fb\u26e1\u90eb\u8c3b"));
                    }
                    if (ProfileManager.deleteProfile((String)Nigga3)) {
                        Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45ad\u26d0\u90e2\u8c23\u796d\ub508\u8dbb\u1ace\uaf4c\u97a7\u905b\u52c8\u8918\u42f9\ua358\uf511\u39b3\uec92\u01c6\u057d\u0a76\ue113\ua859\u2f1f\uc00f\ua043\uac6f\uf4b8\u8855\u989c\u4f36") + (String)Nigga3 + Qprot0.0("\u151b\u719c\u2e1f"));
                    } else {
                        Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45f9\u26d2\u90e6\u8c26\u7964\ub519\u8dba\u1a8a\uaf18\u97bc\u9013\u52c9\u895d\u42e5\ua34f\uf50a\u39b0\uecdb\u01da\u056a\u0a7d\ue155\ua853\u2f12\uc006\ua001\uac24\uf4f2"));
                    }
                } else {
                    Nigga4.mc.thePlayer.skizzleMessage(Qprot0.0("\u151d\u718b\u2e17\u45f9\u26c0\u90ef\u8c26\u797b\ub55c\u8dae\u1ad8\uaf03\u97b5\u905a\u52c1\u895d\u42a9\ua34e\uf511\u39b0\uec88\u01c4\u053f\u0a66\ue113\ua85f\u2f06\uc00a\ua05c\uac7e\uf4fd"));
                }
            }
        }
    }

    public static {
        throw throwable;
    }

    public Profile() {
        super(Qprot0.0("\u156d\u71d9\u2e5e\ua7e2\u04fb\u90eb\u8c2a"), Qprot0.0("\u157e\u71c4\u2e5c\ua7e9\u04f3\u90e9\u8c2b\u797b\u5742\uafac\u1ac5\uaf4c\u97be\u724c\u70c5\u8959\u42ee\ua34f\u1712\u1bf3\uec82\u01c5\u056d\ue87e\uc315\ua84a\u2f0c\uc00c\u4257\u8e65\uf4b0\u8810\u98c9"), Qprot0.0("\u154d\u71d9\u2e5e\ua7e2\u04fb\u90eb\u8c2a\u7928\u575e\uafab\u1acb\uaf1a\u97b6\u7202\u70c7\u8957\u42e8\ua34e\u174f\u1bb7\uec9e\u01c6\u057d\ue878\uc350\ua815\u2f12\uc00c\u4252\u8e6d\uf4a8\u881c\u98d5\uad24\u1e70\u3771\uddba\u7fb5\u4afb\u2076\u1645\u3c03\u0dac\u3995\ue471\ub37b\ua141\u26df\u1027\u4773\u39dc\uf41e\ua804\u30ba\u514c\u3051\u6915"), "");
        Profile Nigga;
    }
}

