package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

import java.util.ArrayList;
import java.util.List;

@ModuleMetaData(name = "Anti Void", description = "Prevents you from falling into the void", category = ModuleCategoryEnum.PLAYER)
public final class AntiVoidModule extends AbstractModule {
    private final NumberSetting<Double> setting = new NumberSetting<>("Distance", 5.0, 1.0, 6.0, 0.1);

    private final List<Packet<?>> packets = new ArrayList<>();
    private BlockPos lastPos;

    public AntiVoidModule() {
        this.registerSettings(this.setting);
    }

    @EventHandler
    private final Listener<SendPacketEvent> sendPacketEventListener = e -> {
        this.setSuffix(setting.getValue().toString());

        if (e.getPacket() instanceof C03PacketPlayer) {
            if (MovementUtil.isBlockUnder()) {
                if (mc.thePlayer.ticksExisted % 5 == 0) {
                    lastPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                }
            }

            if (!MovementUtil.isBlockUnder() && mc.thePlayer.lastTickPosY > mc.thePlayer.posY) {
                e.setCancelled(true);
                packets.add(e.getPacket());
            }

            if (MovementUtil.isBlockUnder()) {
                if (packets.isEmpty())
                    return;


                for (Packet<?> packet : packets) {
                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(packet);
                }
                packets.clear();
            }

            if (!MovementUtil.isBlockUnder() && mc.thePlayer.fallDistance >= setting.getValue()) {
                mc.thePlayer.fallDistance = 0;
                mc.thePlayer.setPosition(lastPos.getX(), lastPos.getY(), lastPos.getZ());
                packets.clear();
                /*
                if (enableScaffold.getValue()) {
                    if(!Diablo.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled())
                        Diablo.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).toggle();
                }

                 */
            }
        }
    };

    public boolean isQueueEmpty() {
        return packets.isEmpty();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        packets.clear();
    }
}
