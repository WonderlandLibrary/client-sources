package club.bluezenith.module.modules.movement.flight;

import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.movement.Flight;
import club.bluezenith.util.player.MovementUtil;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class Vanilla implements IFlight {
    int ticks;
    @Override
    public void onCollision(CollisionEvent event, Flight flight) {

    }

    @Override
    public void onPlayerUpdate(UpdatePlayerEvent event, Flight flight) {
        if (MovementUtil.areMovementKeysPressed()) {
            MovementUtil.setSpeed(flight.speed.get());
        } else {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
        if (mc.gameSettings.keyBindJump.isKeyDown() && flight.canFlyUp()) {
            mc.thePlayer.motionY = flight.speed.get()/2;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -flight.speed.get()/2;
        } else {
            mc.thePlayer.motionY = 0;
        }
        if(event.isPre() && flight.kickByepase.get() && mc.thePlayer.motionY == 0) {
            final Aura aura = getBlueZenith().getModuleManager().getAndCast(Aura.class);
            if(aura.getState() && aura.getTarget() != null) return;
            ticks++;
            if(ticks == 2) {
                event.y += 0.0625;
            } else if(ticks == 3) {
                event.y -= 0.0625;
            }
        }
    }

    @Override
    public void onMoveEvent(MoveEvent event, Flight flight) {

    }

    @Override
    public void onEnable(Flight flight) {
        if(mc.thePlayer.onGround)
        mc.thePlayer.motionY = 0.42;
    }

    @Override
    public void onDisable(Flight flight) {
       mc.thePlayer.motionX = 0;
       mc.thePlayer.motionY = 0;
       mc.thePlayer.motionZ = 0;
    }
}
