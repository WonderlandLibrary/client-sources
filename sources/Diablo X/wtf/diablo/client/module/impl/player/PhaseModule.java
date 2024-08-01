package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(
    name = "Phase",
    description = "Allows you to phase through blocks",
    category = ModuleCategoryEnum.PLAYER
)
public final class PhaseModule extends AbstractModule {

    private boolean startBlink = false;

    private int dummyPlayerID;
    private int tickCount;

    @Override
    protected void onEnable() {
        this.tickCount = 0;
        this.startBlink = true;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }


    @EventHandler
    private final Listener<MotionEvent> motionEventListener = motionEvent -> {
        for (int i = 8; i > 4; i--) {
            if(!mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ)) && mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i - 1, mc.thePlayer.posZ))) {
                mc.thePlayer.vClip(-i - 1);
                toggle();
                return;
            }
        }

        if (this.startBlink) {
            Diablo.getInstance().getModuleRepository().getModuleInstance(BlinkModule.class).toggle();
        }

        mc.thePlayer.vClip(5);
        this.toggle();

    };

    @EventHandler
    private final Listener<RecievePacketEvent> recievePacketEventListener = recievePacketEvent -> {
        if (recievePacketEvent.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat) recievePacketEvent.getPacket();

            final String message = packet.getChatComponent().getUnformattedText();

            if (message.contains("FIGHT") || message.contains("Cages")) {
                this.startBlink = false;
            }
        }
    };
}