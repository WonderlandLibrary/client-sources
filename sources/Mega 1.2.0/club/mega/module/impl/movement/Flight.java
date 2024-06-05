package club.mega.module.impl.movement;

import club.mega.event.impl.*;
import club.mega.module.Category;
import club.mega.module.Module;

import club.mega.module.setting.impl.*;
import club.mega.util.MovementUtil;
import club.mega.util.PlayerUtil;
import club.mega.util.RandomUtil;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Flight", description = "Flight", category = Category.MOVEMENT)
public class Flight extends Module {

    public boolean dmg;
    private final ListSetting mode = new ListSetting("Mode", this, new String[]{"Vanilla", "Verus", "MushMC"});
    private final NumberSetting speed = new NumberSetting("Speed", this, 0.1, 9, 1, 0.1);

    @Handler
    public final void tick(final EventTick event) {
    }

    @Handler
    public final void preTick(final EventPreTick event) {
    }

    @Handler
    public final void posTick(final EventPostTick event) {
    }

    @Handler
    public final void packet(final EventPacket event) {
    }

    @Handler
    public final void movePlayer(final EventMovePlayer event) {
        if(MC.thePlayer.hurtTime > 0)
            dmg = true;

        switch (mode.getCurrent())
        {
            case "MushMC":
            case "Vanilla":
                event.setY(MC.thePlayer.motionY = 0);
                MovementUtil.setSpeed(event, speed.getAsDouble());
                if (MC.gameSettings.keyBindJump.pressed) event.setY(MC.thePlayer.motionY = event.getY() + speed.getAsDouble() / 1.2D);
                if (MC.gameSettings.keyBindSneak.pressed) event.setY(MC.thePlayer.motionY = event.getY() - speed.getAsDouble() / 1.2D);
                break;
            case"Verus":
                if(!dmg)
                    MovementUtil.setSpeed(event,0);
                 else {
                    event.setY(MC.thePlayer.motionY *= 0);
                    MovementUtil.setSpeed(event, speed.getAsDouble());
                    MC.timer.timerSpeed = (float) RandomUtil.getRandomNumber(0.4,0.44);
                }
                break;

        }
    }
    @Override
    public void onEnable() {
        super.onEnable();
        switch (mode.getCurrent())
        {
            case "MushMC":
                MC.thePlayer.jump();
                break;
            case "Verus":
                dmg = false;
                PlayerUtil.verusdmg();
                break;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        dmg = false;
        MC.timer.timerSpeed = 1;
        MC.thePlayer.motionX = 0;
        MC.thePlayer.motionY = 0;
    }

}
