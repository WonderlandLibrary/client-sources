/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.world;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class FastPlace
extends Module {
    public NumberSetting delay;
    public Timer timer;
    public Minecraft mc = Minecraft.getMinecraft();

    public FastPlace() {
        super(Qprot0.0("\uc332\u71ca\uf87b\ua7f0\ued1b\u46a2\u8c2e\uaf52\u5707"), 0, Module.Category.WORLD);
        FastPlace Nigga;
        Nigga.delay = new NumberSetting(Qprot0.0("\uc330\u71ce\uf864\ua7e5\ued32"), 100.0, 0.0, 400.0, 10.0);
        Nigga.timer = new Timer();
        Nigga.addSettings(Nigga.delay);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        Block Nigga2;
        FastPlace Nigga3;
        if (Nigga instanceof EventUpdate && Nigga.isPre() && !Client.ghostMode && Nigga3.mc.thePlayer.getHeldItem() != null && (Nigga2 = Block.getBlockFromItem(Nigga3.mc.thePlayer.getHeldItem().getItem())) != null && Nigga2.isFullBlock() && Nigga3.timer.hasTimeElapsed((long)Nigga3.delay.getValue(), true)) {
            Nigga3.mc.rightClickDelayTimer = 0;
        }
    }
}

