package client.metaware.impl.utils.util.player;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.impl.event.Event;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public final class PlayerInfo {

    private static double lastDist;
    private static double prevLastDist;
    private static double baseMoveSpeed;

    static {
        Metaware.INSTANCE.getEventBus().post(new PlayerUpdatePositionSubscriber());
    }

    public static double getPrevLastDist() {
        return prevLastDist;
    }

    public static double getLastDist() {
        return lastDist;
    }

    public static double getBaseMoveSpeed() {
        return baseMoveSpeed;
    }

    public static double getFriction(double moveSpeed) {
        return MovementUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
    }

    private static class PlayerUpdatePositionSubscriber extends Event {
        @EventHandler
        private Listener<MovePlayerEvent> eventListener = event -> {
            baseMoveSpeed = MovementUtils.getSpeed();
        };


        @EventHandler
        private Listener<UpdatePlayerEvent> updatePlayerEventListener = event ->{
            if (event.isPre()) {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                double xDif = player.posX - player.lastTickPosX;
                double zDif = player.posZ - player.lastTickPosZ;
                prevLastDist = lastDist;
                lastDist = StrictMath.sqrt(xDif * xDif + zDif * zDif);
            }
        };
    }

}