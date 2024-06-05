package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label = "Fast Use", category = ModuleCategory.SELF)
public final class FastUse extends Module {

    private final DoubleProperty ticks = new DoubleProperty("Ticks", 14, 1, 20, 1);

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            Item heldItem = Wrapper.getPlayer().getCurrentEquippedItem().getItem();
            if (Wrapper.getPlayer().isUsingItem() && (heldItem instanceof ItemFood || heldItem instanceof ItemPotion) &&
                    Wrapper.getPlayer().getItemInUseDuration() > ticks.getValue().intValue() && MovementUtils.isOnGround()) {
                for (int i = 0; i < 10; i++) {
                    Wrapper.sendPacketDirect(new C03PacketPlayer(true));
                }
            }
        }
    }

}
