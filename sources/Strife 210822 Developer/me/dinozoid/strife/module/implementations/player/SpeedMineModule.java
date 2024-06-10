package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "SpeedMine", renderName = "SpeedMine", aliases = "FastMine", description = "Mine blocks faster.", category = Category.EXPLOIT)
public class SpeedMineModule extends Module {

    private final DoubleProperty threshold = new DoubleProperty("Threshold", 5, 0, 10, 1, Property.Representation.INT);

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if(event.state() == EventState.PRE) {
            if (mc.thePlayer == null || mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null || mc.objectMouseOver.sideHit == null) return;
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            if(mc.gameSettings.keyBindAttack.isKeyDown() && mc.playerController.curBlockDamageMP > threshold.value().intValue() / 10f) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
                mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
                mc.playerController.curBlockDamageMP = 0;
            }
        }
    });
}
