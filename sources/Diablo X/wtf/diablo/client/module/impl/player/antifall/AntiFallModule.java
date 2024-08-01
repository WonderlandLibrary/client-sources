package wtf.diablo.client.module.impl.player.antifall;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.player.scaffoldrecode.ScaffoldRecodeModule;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.player.block.BlockUtils;

import java.util.LinkedList;
import java.util.Objects;

@ModuleMetaData(
        name = "Anti Fall",
        description = "Prevents fall damage",
        category = ModuleCategoryEnum.PLAYER
)
public final class AntiFallModule extends AbstractModule {

    private final ModeSetting<EnumAntiFallMode> enumAntiFallModeModeSetting = new ModeSetting<>("Mode", EnumAntiFallMode.BLINK);
    private final LinkedList<Packet> packetList = new LinkedList<>();
    private final BooleanSetting enableScaffold = new BooleanSetting("Enable Scaffold", false);
    private final NumberSetting<Integer> fallDistance = new NumberSetting<>("Fall Distance", 3, 1, 10, 1);
    private BlockPos sexY;

    public AntiFallModule()
    {
        this.registerSettings(
                this.enumAntiFallModeModeSetting,
                this.enableScaffold,
                this.fallDistance
        );
    }

    @EventHandler
    private final Listener<SendPacketEvent> sendPacketEventListener = event ->
    {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;
        this.setSuffix(enumAntiFallModeModeSetting.getValue().getName());
        if (Objects.requireNonNull(enumAntiFallModeModeSetting.getValue()) == EnumAntiFallMode.BLINK) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                if (BlockUtils.isBlockUnder()) {
                    if (mc.thePlayer.ticksExisted % 5 == 0) {
                        sexY = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    }
                }

                if (!BlockUtils.isBlockUnder()) {
                    event.setCancelled(true);
                    packetList.add(event.getPacket());
                }

                if (BlockUtils.isBlockUnder()) {
                    if (packetList.isEmpty())
                        return;

                    for (Packet packet : packetList) {
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent(packet);
                    }
                    packetList.clear();
                }

                if (!BlockUtils.isBlockUnder() && mc.thePlayer.fallDistance >= fallDistance.getValue()) {
                    mc.thePlayer.fallDistance = 0;
                    mc.thePlayer.setPosition(sexY.getX(), sexY.getY(), sexY.getZ());
                    packetList.clear();
                    if (enableScaffold.getValue()) {
                        if (!Diablo.getInstance().getModuleRepository().getModuleInstance(ScaffoldRecodeModule.class).isEnabled())
                            Diablo.getInstance().getModuleRepository().getModuleInstance(ScaffoldRecodeModule.class).toggle();
                    }
                }
            }
        }
    };

}
