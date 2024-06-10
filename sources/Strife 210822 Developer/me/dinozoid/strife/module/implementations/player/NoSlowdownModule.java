package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(name = "NoSlowdown", renderName = "NoSlowdown", category = Category.PLAYER, aliases = "NoSlow")
public class NoSlowdownModule extends Module {

    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);

    private final EnumProperty<NoSlowdownMode> modeProperty = new EnumProperty<>("Mode", NoSlowdownMode.NCP);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(modeProperty, false);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEvent = new Listener<>(event -> {
        if(MovementUtil.isMoving() && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.thePlayer.isBlocking()) {
            if(event.state() == EventState.PRE) {
                switch (modeProperty.value()) {
                    case NCP:
                        mc.getNetHandler().getNetworkManager().sendPacket(PLAYER_DIGGING);
                        break;
                }
            } else {
                switch (modeProperty.value()) {
                    case NCP:
                        mc.getNetHandler().getNetworkManager().sendPacket(BLOCK_PLACEMENT);
                        break;
                }
            }
        }
    });

    public static NoSlowdownModule instance() {
        return (NoSlowdownModule) StrifeClient.INSTANCE.moduleRepository().moduleBy("NoSlowdown");
    }

    public enum NoSlowdownMode {
        VANILLA, NCP, AAC
    }

}
