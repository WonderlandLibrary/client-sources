/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class Verify
extends Module {
    public Timer timer2;
    public boolean sendMessage = false;
    public Timer timer;
    public NumberSetting delay;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\udcd7\u71c4\ue78a\ua7e1"), Qprot0.0("\udcc8\u71ce\ue78a\ua7e1\ufe1a\u594b\u8c36"), Qprot0.0("\udcc8\u71ce\ue78a\ua7e1\ufe1a\u594b\u8c36"));

    public static {
        throw throwable;
    }

    public Verify() {
        super(Qprot0.0("\udccc\u71ce\ue79c\ua7ed\ufe0f\u5959"), 0, Module.Category.OTHER);
        Verify Nigga;
        Nigga.delay = new NumberSetting(Qprot0.0("\udcde\u71ce\ue782\ua7e5\ufe10"), 100.0, 0.0, 600.0, 10.0);
        Nigga.timer = new Timer();
        Nigga.timer2 = new Timer();
        Nigga.onDisable();
    }

    @Override
    public void onEvent(Event Nigga) {
        Verify Nigga2;
        if (Nigga2.mc.thePlayer != null) {
            String Nigga3 = Nigga2.mode.getMode();
            Nigga2.setSuffix(Nigga3);
            if (Nigga3.equalsIgnoreCase(Qprot0.0("\udcc8\u71ce\ue78a\ue268\u38b3\u594b\u8c36")) && Nigga2.mc.thePlayer.openContainer != null && Nigga2.mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest Nigga4 = (ContainerChest)Nigga2.mc.thePlayer.openContainer;
                for (int Nigga5 = 0; Nigga5 < Nigga4.inventorySlots.size(); ++Nigga5) {
                    ItemStack Nigga6;
                    if (Nigga4.getSlot(Nigga5) == null || !Nigga4.getSlot(Nigga5).getHasStack() || (Nigga6 = Nigga4.getSlot(Nigga5).getStack()) == null || !Nigga6.getDisplayName().contains(Qprot0.0("\udcd9\u71c7\ue787\ue27c\u3895\u5945\u8c6f\ub0b6\u129a\u93df\ud364"))) continue;
                    if (Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true)) {
                        Nigga2.mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(Nigga4.windowId, Nigga5, 0, 1, Nigga6, Nigga2.mc.thePlayer.openContainer.getNextTransactionID(Nigga2.mc.thePlayer.inventory)));
                    }
                    if (!Nigga2.sendMessage) {
                        Nigga2.sendMessage = true;
                        Nigga2.timer2.reset();
                    }
                    Nigga2.timer.reset();
                }
            }
            if (Nigga2.timer2.hasTimeElapsed((long)378022233 ^ 0x168828A4L, true) && Nigga2.sendMessage) {
                Nigga2.mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(Qprot0.0("\udcb5\u71d9\ue78b\ue26a\u3889\u5953\u8c3b\ub0b2\u1299\u938a\ud36c\uaf0e\u5e6f\u3795\u4ceb\u40ac\u42a9\u6a94\u528b\u27c2\u256d\u0198\uccf4")));
                Nigga2.mc.thePlayer.skizzleMessage(Qprot0.0("\udcbc\u719c\ue7ce\ue25f\u3885\u5947\u8c26\ub0a4\u129f\u93cf\ud37f\uaf09\u5e68\u3784\u4cae\u40f6\u42fd\u6a9d\u52c9\u27d1\u253d\u01d9\uccb4\uadf2\uff28\u61ef\u2f1a\u0986\u0798\ub258\u3d19\u8814\u5107\ue8a0\u221c\ufe96\uddec"));
                Nigga2.sendMessage = false;
            }
        }
    }
}

