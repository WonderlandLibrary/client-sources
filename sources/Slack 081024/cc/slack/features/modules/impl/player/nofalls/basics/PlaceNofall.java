package cc.slack.features.modules.impl.player.nofalls.basics;

import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class PlaceNofall implements INoFall {

    public static float distancecheck;

    @Override
    public void onMotion(MotionEvent event) {
        if (isPreState(event)) {
            final double fallDistance = calculateFallDistance();

            updateDistance(fallDistance);

            if (isGrounded(event)) {
                resetDistance();
            }

            float distance = distancecheck;

            if (shouldSendPackets(distance)) {
                sendPackets(event);
                distance = 0;
            }

            distancecheck = distance;
        }
    }

    private boolean isPreState(MotionEvent event) {
        return event.getState() == State.PRE;
    }

    private double calculateFallDistance() {
        return mc.thePlayer.lastTickPosY - mc.thePlayer.posY;
    }

    private void updateDistance(double fallDistance) {
        if (fallDistance > 0) {
            distancecheck += fallDistance;
        }
    }

    private boolean isGrounded(MotionEvent event) {
        return event.isGround();
    }

    private void resetDistance() {
        distancecheck = 0;
    }

    private boolean shouldSendPackets(float distance) {
        return distance > 3;
    }

    private void sendPackets(MotionEvent event) {
        PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), true));
        PacketUtil.send(new C08PacketPlayerBlockPlacement(getCurrentStack()));
    }

    private ItemStack getCurrentStack() {
        return mc.thePlayer == null || mc.thePlayer.inventoryContainer == null ? null : mc.thePlayer.inventoryContainer.getSlot(getItemIndex() + 36).getStack();
    }

    public int getItemIndex() {
        return mc.thePlayer.inventory.currentItem;
    }

    @Override
    public String toString() {
        return "Place Exploit";
    }
}