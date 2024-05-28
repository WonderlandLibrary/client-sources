package dev.vertic.module.impl.player;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.motion.PreMotionEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.NumberSetting;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Module {

    private final NumberSetting delay = new NumberSetting("Delay", 2, 0, 3, 1);

    public FastPlace() {
        super("FastPlace", "Decreases block placement delay.", Category.PLAYER);
        this.addSettings(delay);
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, this.delay.getInt());
        }
    }

    @Override
    public String getSuffix() {
        return String.valueOf(delay.getInt());
    }
}
