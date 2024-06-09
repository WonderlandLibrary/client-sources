package me.teus.eclipse.modules.impl.movement;

import me.teus.eclipse.events.player.EventMove;
import me.teus.eclipse.events.player.EventPreUpdate;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.MoveUtils;
import net.minecraft.potion.Potion;
import xyz.lemon.event.bus.Listener;

@Info(name = "Speed", displayName = "Speed", category = Category.MOVEMENT)
public class Speed extends Module {

    private ModeValue mode = new ModeValue("Speed Mode", "BlocksMC", "Vanilla", "BlocksMC");
    private NumberValue vanillaSpeed = new NumberValue("Vanilla Speed", 4, 1, 10, 1);
    private double speed;
    private boolean prevOnGround;

    public Speed(){
        addValues(mode, vanillaSpeed);
    }

    @Override
    public void onEnable() {
        if(mode.is("BlocksMC")) speed = MoveUtils.getBaseMoveSpeed();
    }

    @Override
    public void onDisable() {
        if(mode.is("BlocksMC")) mc.timer.timerSpeed = 1;
    }

    public Listener<EventMove> eventMoveListener = e -> {
        switch (mode.getMode()) {
            case "BlocksMC":
                if (MoveUtils.isWalking()) {
                    mc.timer.timerSpeed = 1.15f;
                    if (mc.thePlayer.onGround) {
                        e.setMotionY(0.41999998688698);
                        mc.thePlayer.motionY = 0.42;
                        mc.thePlayer.jump();

                        speed = MoveUtils.getBaseMoveSpeed() + 0.295;

                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            speed += 0.04 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.08;
                        }
                        prevOnGround = true;
                    } else {
                        if (prevOnGround) {
                            speed *= 0.4 - Math.random() * 0.0001;
                            prevOnGround = false;
                        } else {
                            speed -= speed / 159;
                        }
                    }
                }

                speed = Math.max(speed, MoveUtils.getBaseMoveSpeed());

                if(mc.thePlayer.hurtTime > 0 && mc.thePlayer.hurtTime < 2) {
                    speed += 0.05;
                }

                MoveUtils.setSpeed(speed + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.05 : 0.035));
                break;
        }
    };

    public Listener<EventPreUpdate> eventPreUpdateListener = eventPreUpdate -> {
        switch (mode.getMode()) {
            case "BlocksMC":
                if(MoveUtils.isWalking()) mc.thePlayer.setSprinting(true);
                break;
        }
    };
}
