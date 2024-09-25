/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class AutoJoin
extends Module {
    public Timer timer2;
    public Timer timer;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u3d97\u71c4\u06ca\ua7e1"), Qprot0.0("\u3d88\u71ce\u06ca\ua7e1\u1f5a\ub80b\u8c36"), Qprot0.0("\u3d88\u71ce\u06ca\ua7e1\u1f5a\ub80b\u8c36"));
    public boolean sendMessage = false;
    public NumberSetting delay = new NumberSetting(Qprot0.0("\u3d9e\u71ce\u06c2\ua7e5\u1f50"), 100.0, 0.0, 600.0, 10.0);

    public AutoJoin() {
        super(Qprot0.0("\u3d9b\u71de\u06da\ua7eb\u1f63\ub80f\u8c26\u51f9"), 0, Module.Category.OTHER);
        AutoJoin Nigga;
        Nigga.timer = new Timer();
        Nigga.timer2 = new Timer();
        Nigga.onDisable();
    }

    @Override
    public void onEvent(Event Nigga) {
        AutoJoin Nigga2;
        if (Nigga2.mc.thePlayer != null) {
            String Nigga3 = Nigga2.mode.getMode();
            Nigga2.setSuffix(Nigga3);
            if (Nigga3.equalsIgnoreCase(Qprot0.0("\u3d88\u71ce\u06ca\ue268\u59d3\ub80b\u8c36"))) {
                if (Nigga2.mc.thePlayer.inventory.getStackInSlot(6) != null && Nigga2.mc.thePlayer.openContainer != null && Nigga2.mc.thePlayer.inventory.getStackInSlot(6).getDisplayName().equals(Qprot0.0("\u3d7d\u71ca\u06e4\ue262\u59c7\ub801\u8c3d\u51b7\u1285\uf285\u323b\uaf0d\ubf21\u37c1\u2df7\ua1ab\u42ec\u8b95\u524e\u46d6\uc434\u01e9\u2deb\uadec\u9e76\u80a8\u2f1b\ue8dc\u07dc\ud357\udc49\u8810\ub04c\ue8b7\u4302\u1fcd"))) {
                    Nigga2.timer.hasTimeElapsed((long)811411095 ^ 0x305D2707L, true);
                }
                if (Nigga2.mc.thePlayer.openContainer != null && Nigga2.mc.thePlayer.openContainer instanceof ContainerChest) {
                    ContainerChest Nigga4 = (ContainerChest)Nigga2.mc.thePlayer.openContainer;
                    for (int Nigga5 = 0; Nigga5 < Nigga4.inventorySlots.size(); ++Nigga5) {
                        ItemStack Nigga6;
                        if (Nigga4.getSlot(Nigga5) == null || !Nigga4.getSlot(Nigga5).getHasStack() || !(Nigga6 = Nigga4.getSlot(Nigga5).getStack()).getDisplayName().contains(Nigga4.getLowerChestInventory().getName())) continue;
                        Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true);
                    }
                }
            }
        }
        if (Nigga2.timer2.hasTimeElapsed((long)187268692 ^ 0xB297FA9L, true) && Nigga2.sendMessage) {
            Nigga2.mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(Qprot0.0("\u3df5\u71d9\u06cb\ue26a\u59c9\ub813\u8c3b\u51f2\u1299\uf2ca\u322c\uaf0e\ubf2f\u3795\u2dab\ua1ec\u42a9\u8bd4\u528b\u4682\uc42d\u0198\u2db4")));
            Nigga2.mc.thePlayer.skizzleMessage(Qprot0.0("\u3dfc\u719c\u068e\ue25f\u59c5\ub807\u8c26\u51e4\u129f\uf28f\u323f\uaf09\ubf28\u3784\u2dee\ua1b6\u42fd\u8bdd\u52c9\u4691\uc47d\u01d9\u2df4\uadf2\u9e68\u80af\u2f1a\ue8c6\u0798\ud318\udc59\u8814\ub047\ue8a0\u435c\u1fd6\uddec"));
            Nigga2.sendMessage = false;
        }
    }

    public static {
        throw throwable;
    }
}

