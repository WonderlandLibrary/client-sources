package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.event.impl.render.Render2DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Keyboard;

@Module.Info(name = "FreeLook", category = Module.Category.VISUAL)
public class FreeLook extends Module {
    private int previousPerspective;
    public float originalYaw, originalPitch, lastYaw, lastPitch;
    @SubscribeEvent
    private final EventListener<Render2DEvent> onRender2D = e -> {
        if (this.getKeyBind() == Keyboard.KEY_NONE || !Keyboard.isKeyDown(getKeyBind())) {
            this.setToggled(false);
            return;
        }

        this.mc.mouseHelper.mouseXYChange();
        final float f = this.mc.settings.mouseSensitivity * 0.6F + 0.2F;
        final float f1 = (float) (f * f * f * 1.5);
        lastYaw += this.mc.mouseHelper.deltaX * f1;
        lastPitch -= this.mc.mouseHelper.deltaY * f1;

        lastPitch = MathHelper.clamp_float(lastPitch, -90, 90);
        mc.settings.thirdPersonView = 1;
    };

    @Override
    protected void onEnable() {
        previousPerspective = mc.settings.thirdPersonView;
        originalYaw = lastYaw = mc.player.rotationYaw;
        originalPitch = lastPitch = mc.player.rotationPitch;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.player.rotationYaw = originalYaw;
        mc.player.rotationPitch = originalPitch;
        mc.settings.thirdPersonView = previousPerspective;
        super.onDisable();
    }
    @SubscribeEvent
    private final EventListener<PacketEvent> cum = event -> {
        if(event.getType().equals(PacketEvent.Type.RECEIVED)) {
            if(event.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook wrapper = (S08PacketPlayerPosLook) event.getPacket();
                ChatUtil.display("sad");
                originalYaw = wrapper.getYaw();
                originalPitch = wrapper.getPitch();
            }
        }
    };
}
