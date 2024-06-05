package xyz.vergoclient.modules.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.StepEvent;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.util.main.ChatUtils;

public class Step extends Module {

    public Step() {
        super("Step", Category.MOVEMENT);
    }

    public BooleanSetting smooth = new BooleanSetting("Smoothing", false);

    @Override
    public void loadSettings() {
        addSettings(smooth);
    }

    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable() {

    }

    public static boolean stepping = false;

    public void onEvent(Event event) {

        ChatUtils.addChatMessage("Flag");

        if(event instanceof StepEvent) {
            StepEvent e = (StepEvent) event;

            if (!mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedVertically) {
                e.setStepHeight(1.0D);
            } else if (!event.isPre() && e.getRealHeight() > 0.5D && e.getStepHeight() > 0.0D && !mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedVertically) {
                stepping = true;
                if (e.getRealHeight() >= 0.87D) {
                    double realHeight = e.getRealHeight();
                    double height1 = realHeight * 0.42D;
                    double height2 = realHeight * 0.75D;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + height1, mc.thePlayer.posZ, mc.thePlayer.onGround));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + height2, mc.thePlayer.posZ, mc.thePlayer.onGround));

                }

            if (smooth.isEnabled()) {
                mc.timer.timerSpeed = 0.55F;
                (new Thread(() -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var1) {
                        ;
                    }
                    stepping = false;
                    mc.timer.timerSpeed = 1.0F;
                })).start();
            } else {
                stepping = false;
            }
            }
        }
    }

}
