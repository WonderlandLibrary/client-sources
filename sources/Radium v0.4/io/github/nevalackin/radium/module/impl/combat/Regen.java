package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label = "Regen", category = ModuleCategory.COMBAT)
public final class Regen extends Module {

    private final DoubleProperty packetsProperty = new DoubleProperty("Packets", 10, 0, 100, 1);

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre() && MovementUtils.isOnGround() && Wrapper.getPlayer().getHealth() < Wrapper.getPlayer().getMaxHealth())
            for (int i = 0; i < packetsProperty.getValue().intValue(); i++)
                Wrapper.sendPacketDirect(new C03PacketPlayer(true));
    }
}
