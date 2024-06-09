package dev.vertic.module.impl.render;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.api.Priority;
import dev.vertic.event.impl.other.TeleportEvent;
import dev.vertic.event.impl.render.Render2DEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class FreeLook extends Module {

    public final BooleanSetting invertPitch = new BooleanSetting("Invert Pitch", false);

    private int previousPerspective;
    public float originalYaw, originalPitch, lastYaw, lastPitch;

    public FreeLook() {
        super("FreeLook", "Freely move camera in 3rd person without player rotating.", Category.RENDER);
        this.addSettings(invertPitch);
    }

    @Override
    protected void onEnable() {
        previousPerspective = mc.gameSettings.thirdPersonView;
        originalYaw = lastYaw = mc.thePlayer.rotationYaw;
        originalPitch = lastPitch = mc.thePlayer.rotationPitch;

        if (invertPitch.isEnabled()) lastPitch *= -1;
    }

    @Override
    protected void onDisable() {
        mc.thePlayer.rotationYaw = originalYaw;
        mc.thePlayer.rotationPitch = originalPitch;
        mc.gameSettings.thirdPersonView = previousPerspective;
    }
    @EventLink(Priority.LOW)
    public void onRender2D(Render2DEvent event) {

        if (this.getKey() == Keyboard.KEY_NONE || !Keyboard.isKeyDown(this.getKey()) || mc.currentScreen != null) {
            this.disable();
            return;
        }

        this.mc.mouseHelper.mouseXYChange();
        final float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float f1 = (float) (f * f * f * 1.5);
        lastYaw += this.mc.mouseHelper.deltaX * f1;
        lastPitch -= this.mc.mouseHelper.deltaY * f1;

        lastPitch = MathHelper.clamp_float(lastPitch, -90, 90);
        mc.gameSettings.thirdPersonView = 1;
    };

    @EventLink(Priority.LOW)
    public void onTeleport(TeleportEvent event) {
        originalYaw = event.getYaw();
        originalPitch = event.getPitch();
    };

}
