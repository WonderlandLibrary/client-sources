package dev.star.module.impl.player;

import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.utils.server.PacketUtils;

import net.minecraft.network.play.client.C03PacketPlayer;

@SuppressWarnings("unused")
public final class NoFall extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Blink", "Noground", "Packet", "Edit", "Vulcan","Watchdog");
    private double fallDistance;
    private boolean wasDMG = false;


    public NoFall() {
        super("NoFall", Category.PLAYER, "Prevents fall damage");
        this.addSettings(mode);
    }

    @Override
    public void onEnable() {
        wasDMG = false;
        super.onEnable();
    }

    @Override
    public void onMotionEvent(MotionEvent event) {
        this.setSuffix(mode.getMode());

        if(mc.thePlayer.onGround) {
            fallDistance = 0;
        } else if(mc.thePlayer.motionY < 0) {
            fallDistance += -mc.thePlayer.motionY;
        }

        switch (mode.getMode()) {
            case "Packet":
                if(fallDistance >= 3) {
                    PacketUtils.sendPacket(new C03PacketPlayer(true));
                    fallDistance = 0;
                }
                break;
            case "Edit":
                if(fallDistance >= 3) {
                    event.setOnGround(true);
                    fallDistance = 0;
                }
                break;
            case "Noground":
                event.setOnGround(false);
                break;
            case "Watchdog":
                if (fallDistance > 3.5) {
                    wasDMG = true;
                }
                if(fallDistance < 3.5 && !mc.thePlayer.onGround && wasDMG) {
                    wasDMG = false;
                }
                if (mc.thePlayer.onGround && wasDMG) {
                    fallDistance = 0;
                    mc.thePlayer.jump();
                    event.setOnGround(false);
                    wasDMG = false;
                }
                break;
            case "Vulcan":
                if(fallDistance > 3) {
                   PacketUtils.sendPacket(new C03PacketPlayer(true));

                    mc.timer.timerSpeed = 0.5f;
                    fallDistance = 0;
                } else if(fallDistance < 3){
                    mc.timer.timerSpeed = 1f;
                }
                break;
        }
    }
}
