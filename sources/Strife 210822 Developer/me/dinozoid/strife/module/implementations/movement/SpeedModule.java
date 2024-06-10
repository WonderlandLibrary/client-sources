package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.MovePlayerEvent;
import me.dinozoid.strife.event.implementations.player.StrafePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.exploit.BlinkModule;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Speed", renderName = "Speed", description = "Move faster.", category = Category.MOVEMENT)
public class SpeedModule extends Module {

    private EnumProperty<SpeedMode> modeProperty = new EnumProperty<>("Mode", SpeedMode.HYPIXEL);
    private int stage;
    private double moveSpeed;

    @Override
    public void init() {
        super.init();
        addValueChangeListener(modeProperty);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    private final Listener<MovePlayerEvent> movePlayerListener = new Listener<>(event -> {
        switch (modeProperty.value()) {
            case HYPIXEL:
                if(MovementUtil.isMoving()) {
                    if (MovementUtil.isOnGround()) stage = 0;
                    switch (stage) {
                        case 0:
//                            event.y(event.y() + 0.3992E-199 * Math.random());
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.3992E-199 * Math.random(), mc.thePlayer.posZ);
                            moveSpeed = MovementUtil.getBaseMoveSpeed() * 2.139992;
                            break;
                        case 1:
                            moveSpeed *= 0.57;
                            break;
                        case 4:
                            mc.thePlayer.motionY -= 0.0789992;
                            break;
                        default:
                            moveSpeed *= 0.96572;
                            break;
                    }
                    stage++;
                    MovementUtil.setSpeed(event, moveSpeed);
                }
                break;
            case VANILLA:
                break;
        }
    });

    @EventHandler
    private final Listener<StrafePlayerEvent> strafePlayerListener = new Listener<>(event -> {
        PlayerUtil.sendMessage("sex");
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.13992E-199 * Math.random(), mc.thePlayer.posZ);
        event.jump(true);
    });

    public static SpeedModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(SpeedModule.class);
    }

    private enum SpeedMode {
        HYPIXEL, VANILLA
    }

}
