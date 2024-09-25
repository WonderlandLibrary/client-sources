/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import skizzle.events.Event;
import skizzle.events.listeners.EventChat;
import skizzle.modules.Module;
import skizzle.users.ServerManager;
import skizzle.users.ServerReciever;

public class SkizzleChat
extends Module {
    @Override
    public void onEvent(Event Nigga) {
        EventChat Nigga2;
        String Nigga3;
        if (Nigga instanceof EventChat && (Nigga3 = (Nigga2 = (EventChat)Nigga).getMessage()).startsWith("#")) {
            Nigga2.setCancelled(true);
            ServerManager.sendPacket(Qprot0.0("\u6dc2\u71c3\u5694\ue279\u8984\ue85e\u8c3c\u01bf\u128a\u22e4\u6273"), Nigga3);
        }
    }

    public static void lambda$0() {
        ServerReciever.start();
    }

    @Override
    public void onEnable() {
        SkizzleChat Nigga;
        ServerManager.sendPacket(Qprot0.0("\u6dc2\u71c3\u5694\u59c0\u4545\ue854\u8c21\u01a2\ua937\uee2f\u6262"), Nigga.mc.thePlayer.getName());
        ServerReciever.running = true;
        new Thread(SkizzleChat::lambda$0).start();
    }

    @Override
    public void onDisable() {
        SkizzleChat Nigga;
        ServerManager.sendPacket(Qprot0.0("\u6dc2\u71c3\u5694\u818b\uad7b\ue852\u8c3c\u01af\u7176\u061b\u6278\uaf09\uef74\u5422"), Nigga.mc.thePlayer.getName());
    }

    public static {
        throw throwable;
    }

    public SkizzleChat() {
        super(Qprot0.0("\u6dd2\u71c0\u569c\ua7fe\u4f2c\ue857\u8c2a\u018f\u570a\ue47d\u6262"), 25, Module.Category.OTHER);
        SkizzleChat Nigga;
    }
}

