/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.renderer.entity.RenderItem;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;

public class ArmorStats
extends Module {
    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventRenderGUI) {
            ArmorStats Nigga2;
            RenderItem Nigga3 = Nigga2.mc.getRenderItem();
            for (int Nigga4 = 0; Nigga4 < 4; ++Nigga4) {
                if (Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga4) == null) continue;
                int Nigga5 = Nigga4 + 1;
                Nigga3.renderItemOnScreen(Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga4), Nigga2.mc.displayWidth / 4 + 15 * Nigga5 - 7, Nigga2.mc.displayHeight / 2 - 54);
            }
            if (Nigga2.mc.thePlayer.getHeldItem() != null) {
                Nigga3.renderItemOnScreen(Nigga2.mc.thePlayer.getHeldItem(), Nigga2.mc.displayWidth / 4 + 74, Nigga2.mc.displayHeight / 2 - 54);
            }
        }
    }

    public ArmorStats() {
        super(Qprot0.0("\u9d6c\u71d9\ua62c\ua7eb\ubcf0\u18c4\u8c3b\uf119\u5716\u17bb"), 0, Module.Category.RENDER);
        ArmorStats Nigga;
    }

    public static {
        throw throwable;
    }
}

