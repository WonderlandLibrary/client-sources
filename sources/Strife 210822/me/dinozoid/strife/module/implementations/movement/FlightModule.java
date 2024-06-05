package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.MovePlayerEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.exploit.BlinkModule;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Flight", renderName = "Flight", description = "Fly like a bird.", aliases = "Fly", category = Category.MOVEMENT)
public class FlightModule extends Module {

    private final EnumProperty<FlightMode> modeProperty = new EnumProperty<>("Mode", FlightMode.HYPIXEL_NEW);

    private DoubleProperty flightSpeedProperty = new DoubleProperty("Flight Speed", 5, 1, 5, 0.1);

    private double initalX, initialZ;

    private BlinkModule blinkModule;

    private int waitTicks = 0;

    @Override
    public void onEnable() {
        if(blinkModule == null)
            blinkModule = StrifeClient.INSTANCE.moduleRepository().moduleBy(BlinkModule.class);
        super.onEnable();
        initalX = mc.thePlayer.posX;
        initialZ = mc.thePlayer.posZ;
//        if(Math.log(initalX - mc.thePlayer.posX + initialZ - mc.thePlayer.posZ) >= 3) {
//        }
        waitTicks = 8;
        for(int i = 0; i <= 4 / 0.0625; i++) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        }
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;
        blinkModule.toggled(false);
    }

    @Override
    public void init() {
        super.init();
        addValueChangeListener(modeProperty);
    }

    @EventHandler
    private final Listener<MovePlayerEvent> movePlayerListener = new Listener<>(event -> {
        switch (modeProperty.value()) {
            case VANILLA: {
                event.y(mc.thePlayer.motionY = 0);
                event.x(0);
                event.z(0);
                if (GameSettings.isKeyDown(mc.gameSettings.keyBindJump)) event.y(event.y() + 2);
                if (GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) event.y(event.y() - 2);
                MovementUtil.setSpeed(event, flightSpeedProperty.value());
            }
            break;
            case HYPIXEL_NEW: {
                if(waitTicks > 0)
                    waitTicks--;
                event.y(mc.thePlayer.motionY = 1E-8);
                if(waitTicks == 0 && !blinkModule.toggled())
                    blinkModule.toggled(true);
                if(blinkModule.toggled()) {
//                    mc.timer.timerSpeed = 1.695042f;
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
                }
            }
            break;
        }
    });

    public static FlightModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(FlightModule.class);
    }

    private enum FlightMode {
        VANILLA, HYPIXEL_NEW
    }

}
