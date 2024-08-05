package fr.dog.component.impl.player;

import fr.dog.component.Component;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.player.move.JumpEvent;
import fr.dog.event.impl.player.move.MoveInputEvent;
import fr.dog.event.impl.player.move.StrafeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.util.player.MoveUtil;
import fr.dog.util.player.RotationUtil;
import fr.dog.util.player.movecorrect.MoveCorrect;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;

public class RotationComponent extends Component {
    private static MoveCorrect moveCorrect;
    @Setter
    public static boolean active, smoothReset;

    public static float rotationYaw;
    public static float rotationPitch;
    public static float prevRotationYaw;
    public static float prevRotationPitch;
    public RotationComponent(){
        smoothReset = false;
    }
    public static void setRotations(float yaw, float pitch, MoveCorrect move) {
        moveCorrect = move;

        float[] rota = RotationUtil.applySensitivity(new float[]{yaw, pitch}, new float[]{rotationYaw, rotationPitch});


        rotationYaw = rota[0];
        rotationPitch = rota[1];
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent event){
        ScaledResolution sr = new ScaledResolution(mc);
    }

    @SubscribeEvent
    private void onTick(TickEvent event) {
        //Still doing 360 but idc for now
        if(active) {
            smoothReset = false;
        }

        if(!active) {
            rotationYaw = RotationUtil.normalizeAngleF(mc.thePlayer.rotationYaw);
            rotationPitch = RotationUtil.normalizeAngleF(mc.thePlayer.rotationPitch);
            prevRotationYaw = RotationUtil.normalizeAngleF(mc.thePlayer.prevRotationYaw);
            prevRotationPitch = RotationUtil.normalizeAngleF(mc.thePlayer.prevRotationPitch);
        }

    }

    @SubscribeEvent
    private void onMoveInput(MoveInputEvent event) {
        if ((active || smoothReset) && moveCorrect == MoveCorrect.SILENT) {
            MoveUtil.fixMovement(event, rotationYaw);
        }
    }

    @SubscribeEvent
    public void onStrafe(StrafeEvent event) {
        if ((active || smoothReset) && (moveCorrect == MoveCorrect.STRICT || moveCorrect == MoveCorrect.SILENT)) {
            event.setYaw(rotationYaw);
        }
    }

    @SubscribeEvent
    public void onJump(JumpEvent event) {
        if ((active || smoothReset) && (moveCorrect != MoveCorrect.OFF)) {
            event.setYaw(rotationYaw);
        }
    }

    @SubscribeEvent
    private void onPlayerNetworkTick(PlayerNetworkTickEvent event) {
        if (active || smoothReset) {

            event.setYaw(rotationYaw);
            event.setPitch(rotationPitch);

            mc.thePlayer.renderPitchHead = rotationPitch;
            mc.thePlayer.rotationYawHead = rotationYaw;

            if (Math.abs((rotationYaw - mc.thePlayer.rotationYawHead) % 360) < 1 && Math.abs(rotationPitch - mc.thePlayer.rotationPitch) < 1) {
                active = false;

                final float[] rotations = new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch},
                        fixedRotations = RotationUtil.reset(RotationUtil.applySensitivity(rotations, new float[]{prevRotationYaw, prevRotationPitch}));

                mc.thePlayer.rotationYaw = fixedRotations[0];
                mc.thePlayer.rotationPitch = fixedRotations[1];
            }
            mc.thePlayer.renderYawOffset = rotationYaw;
            mc.thePlayer.prevRenderYawOffset = prevRotationYaw;
        }

        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;

    }
}
