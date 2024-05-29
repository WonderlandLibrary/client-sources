package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.player.SafeWalkEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.NumberSetting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;

public final class SafeWalk extends Module {

    private final BooleanSetting blocksOnly = new BooleanSetting("Blocks only", true);

    private final EventListener<SafeWalkEvent> onSafeWalk = e -> {
        if (canSafeWalk()) {
            e.setSafe(true);
        }
    };

    private boolean canSafeWalk() {
        if (!blocksOnly.isEnabled()) return true;
        return mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock;
    }

    public SafeWalk() {
        super("SafeWalk", Category.PLAYER, "prevents walking off blocks");
        this.addSettings(blocksOnly);
    }

}
