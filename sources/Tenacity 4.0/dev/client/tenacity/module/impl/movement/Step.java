package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.player.BlockUtils;
import dev.client.tenacity.utils.player.MovementUtils;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.player.StepConfirmEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.ChatComponentText;

@SuppressWarnings("unused")
public final class Step extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "NCP", "NCP", "Smooth");
    private final double[] ncpOffsets = new double[]{0.42F, 0.75F}, smoothOffsets = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7};
    private boolean resetTimer;

    private final EventListener<MotionEvent> onUpdate = e -> {
        if (mc.thePlayer == null) return;
        this.setSuffix(mode.getMode());

        mc.thePlayer.stepHeight = BlockUtils.isInLiquid() || BlockUtils.isOnLiquid()
                || Tenacity.INSTANCE.getModuleCollection().get(Speed.class).isToggled()
                || Tenacity.INSTANCE.getModuleCollection().get(Scaffold.class).isToggled()
                || !mc.thePlayer.onGround ? 0.625F : mode.is("Watchdog") ? 1.5F : 1.0F;

        if (resetTimer && mc.timer.timerSpeed != 1) {
            mc.timer.timerSpeed = 1.0F;
            resetTimer = false;
        }
    };

    private final EventListener<StepConfirmEvent> onStepConfirm = e -> {
        if (mc.thePlayer == null
                || Tenacity.INSTANCE.getModuleCollection().get(Scaffold.class).isToggled()
                || Tenacity.INSTANCE.getModuleCollection().get(Speed.class).isToggled())
            return;
        double diffY = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
        double posX = mc.thePlayer.posX, posY = mc.thePlayer.posY, posZ = mc.thePlayer.posZ;

        if (MovementUtils.isMoving() && mc.thePlayer.onGround && diffY > 0.625 && diffY <= 1.5) {
            switch (mode.getMode()) {
                case "NCP":
                    if (diffY <= 1) {
                        for (double o : this.ncpOffsets) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + o, posZ, false));
                        }
                        for (int i = 0; i < 4; i++) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer(true));
                        }
                    }
                    PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                    break;
                case "Smooth":
                    PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    if (diffY <= 1) {
                        for (double o : this.smoothOffsets) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + o, posZ, false));
                        }
                        for (int i = 0; i < 4; i++) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer(true));
                        }
                    }
                    mc.timer.timerSpeed = 0.191f;
                    PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    break;
            }
            mc.thePlayer.stepHeight = 0.625F;
            resetTimer = true;
        }
    };

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.stepHeight = 0.625F;
        super.onDisable();
    }

    public Step() {
        super("Step", Category.MOVEMENT, "step up blocks");
        this.addSettings(mode);
    }
}
