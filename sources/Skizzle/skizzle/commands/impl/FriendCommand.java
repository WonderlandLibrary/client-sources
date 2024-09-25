/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import skizzle.Client;
import skizzle.commands.Command;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.ui.notifications.Notification;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super(Qprot0.0("\u9f5f\u71d9\ua404\ua7e1\ube80\u1ac7\u8c3c"), Qprot0.0("\u9f58\u71cf\ua409\ua7f7\ubece\u1acc\u8c3d\uf374\u5710\u15c1\u90e3\uaf03\u1df9\u7248\ucaa4\u033c\u42ef\u2904\u1709\ua1ca\u66b1\u01ce\u8f37"), Qprot0.0("\u9f7f\u71d9\ua404\ua7e1\ube80\u1ac7\u8c3c\uf374\u575e\u15c5\u90ea\uaf08\u1da0\u725f\ucab2\u0371\u42e6\u2900\u1705\ua191\u66ff\u0196\u8f31\ue87f\u792c\u226c\u2f10\u4a5e\u425c\u3415\u7ec6\u8855\u12ce\uad24\ua44a\ubd44\uddb4\uf5eb\u4af5\u9a06\u9c60\u3c14"), "f");
        FriendCommand Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        FriendCommand Nigga3;
        if (Nigga.length > 0 && Nigga[0].equalsIgnoreCase(Qprot0.0("\u9f78\u71cf\ua409")) && Nigga.length >= 2) {
            String Nigga4;
            Friend Nigga5;
            String Nigga6;
            String Nigga7 = Nigga6 = Nigga[1];
            if (Nigga.length > 2) {
                Nigga7 = Nigga[2];
            }
            if (FriendManager.addFriend(Nigga5 = new Friend(Nigga4 = Nigga6, Nigga7))) {
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9\ua44b\u45f6\u50a3\u1ac8\u8c26\uf32e\ub506\ufbd6\u90eb"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u719c\ua44d\u45ba\u50b1\u1ac7\u8c2b\uf331\ub518\ufb9a\u90a8\uaf0e") + Nigga5.getNickname() + Qprot0.0("\u9f3f\u719c\ua44d\u45ee\u509f\u1a83\u8c36\uf33b\ub509\ufbc8\u90ae\uaf0a\u1dfd\u905a\u24ac\u0372\u42ed\u2905\uf55e\u4fdd\u66b6\u01d9\u8f30\u0a33"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9"));
            } else {
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9\ua44b\u45f6\u50a3\u1ac8\u8c26\uf32e\ub506\ufbd6\u90eb"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c8\ua44d\u45ba\u50a4\u1acb\u8c26\uf327\ub55c\ufbca\u90e2\uaf0d\u1df6\u9056\u24bb\u033c\u42e0\u2905\uf55e\u4fd0\u66b3\u01d8\u8f21\u0a73\u9733\u2267\u2f5e\u4a50\ua041\uda4e\u7e81\u881a\u1293\u4f26\u4a1d\ubd41\uddad\uf5ec\ua8ef\u741b\u9c61\u3c4e\u87f0\udbdb\ub00a\u395e\ua15a\uacc4"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9"));
            }
        } else if (Nigga.length > 0 && Nigga[0].equalsIgnoreCase(Qprot0.0("\u9f6b\u71ce\ua400\u45f5\u5086\u1ac6")) && Nigga.length >= 2) {
            if (FriendManager.getFriendsFromFile() == null) {
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9\ua44b\u45f6\u50a3\u1ac8\u8c26\uf32e\ub506\ufbd6\u90eb"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c8\ua44d\u45ba\u50a9\u1acc\u8c3a\uf374\ub518\ufbd5\u90e0\uaf4b\u1dfb\u9013\u24a1\u037d\u42ff\u2913\uf55e\u4fd0\u66b1\u01d3\u8f64\u0a74\u9725\u2277\u2f1b\u4a51\ua04b\uda1d\u7ed6\u885b\u12c8"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9"));
                return;
            }
            boolean Nigga8 = false;
            if (FriendManager.friends != null && FriendManager.friends.size() != 0 && FriendManager.getFriendsFromFile() != null) {
                String Nigga9 = Nigga[1];
                for (Friend Nigga10 : FriendManager.friends) {
                    if (!Nigga10.name.equals(Nigga9) && !Nigga10.nickname.equals(Nigga9) || !FriendManager.removeFriend(Nigga10)) continue;
                    Nigga8 = true;
                    Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9\ua44b\u45f6\u50a3\u1ac8\u8c26\uf32e\ub506\ufbd6\u90eb"));
                    Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u719c\ua44d\u45ba\u50a2\u1ac6\u8c22\uf33b\ub50a\ufbdf\u90ea\uaf4c\u1da9\u9051") + Nigga10.getNickname() + Qprot0.0("\u9f39\u718d\ua45a\u45fc\u5082\u1acc\u8c22\uf374\ub505\ufbd5\u90fb\uaf1e\u1daf\u9055\u24bb\u0375\u42ec\u2918\uf51a\u4fc2\u66fe"));
                    Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9"));
                    Client.notifications.notifs.add(new Notification(Qprot0.0("\u9f5f\u71d9\ua404\u45ff\u509e\u1ac7\u8c3c"), Qprot0.0("\u9f4b\u71ce\ua400\u45f5\u5086\u1ac6\u8c2b\uf374") + Nigga10.getNickname() + Qprot0.0("\u9f39\u71cd\ua41f\u45f5\u509d\u1a83\u8c36\uf33b\ub509\ufbc8\u90ae\uaf0a\u1dfd\u905a\u24ac\u0372\u42ed\u2905\uf55f"), Float.intBitsToFloat(1.05795757E9f ^ 0x7F03EA71), Float.intBitsToFloat(2.07664269E9f ^ 0x7BC70D7F), Notification.notificationType.INFO));
                    break;
                }
            }
            if (!Nigga8) {
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9\ua44b\u45f6\u50a3\u1ac8\u8c26\uf32e\ub506\ufbd6\u90eb"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c8\ua44d\u45ba\u50a9\u1acc\u8c3a\uf374\ub518\ufbd5\u90e0\uaf4b\u1dfb\u9013\u24a1\u037d\u42ff\u2913\uf55e\u4fd0\u66ff\u01cc\u8f36\u0a7b\u9732\u2270\u2f1a\u4a1f\ua058\uda07\u7e8c\u881d\u12c6\u4f20\u4a55\ubd4e\uddac\uf5a5\ua8e4\u7414\u9c68\u3c58\u87f1"));
                Nigga3.mc.thePlayer.messagePlayer(Qprot0.0("\u9f3f\u71c9"));
            }
        } else {
            Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u9f39\u718b\ua44b\u45f9\u50b9\u1acd\u8c39\uf335\ub510\ufbd3\u90ea\uaf4c\u1dda\u9040\u24a8\u037b\u42ec\u2957\uf55e\u4f97\u66e8\u01fa\u8f28\u0a77\u9736\u226d\u2f1b\u4a1f\ua05a\uda1d\u7e9d\u884f\u12c6") + Nigga3.syntax);
        }
    }
}

