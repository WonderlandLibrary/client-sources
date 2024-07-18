package com.alan.clients.module.impl.combat.mimic;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.combat.Mimic;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationsMimic extends Mode<Mimic> {

    public RotationsMimic(String name, Mimic parent) {
        super(name, parent);
    }

    Vector2f axis = new Vector2f(0, 0);
    Vector2f offset = new Vector2f(0, 0);
    Animation yawAnimation = new Animation(Easing.LINEAR, 150);
    Animation pitchAnimation = new Animation(Easing.LINEAR, 150);

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        EntityLivingBase target = TargetComponent.getTarget(6);
        if (target == null || !mc.gameSettings.keyBindAttack.isKeyDown()) {
            yawAnimation.setValue(mc.thePlayer.rotationYaw);
            pitchAnimation.setValue(mc.thePlayer.rotationPitch);
            offset = new Vector2f(0, 0);
            return;
        }

        float yaw = target.rotationYaw;
        float pitch = target.rotationPitch;

        yaw -= 180;
        pitch = -pitch;

        yawAnimation.run(yaw);
        axis.x = (float) yawAnimation.getValue();

        pitchAnimation.run(pitch);
        axis.y = (float) pitchAnimation.getValue();

        Vector2f difference = RotationUtil.move(new Vector2f(yaw, pitch), RotationUtil.calculate(target), 1000);
        Vector2f move = RotationUtil.move(new Vector2f(yaw, pitch), RotationUtil.calculate(target), (Math.abs(difference.getX()) + Math.abs(difference.getY())) / (difference.getX() > 90 ? 1.05 : 1.5));

        yaw += move.x;
        pitch += move.y;

        offset = new Vector2f(MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), pitch - mc.thePlayer.rotationPitch);

        offset.x += (float) ((Math.random() - 0.5) * offset.getX() / 10);
        offset.y += (float) ((Math.random() - 0.5) * offset.getY() / 10);
    };

    private long time;

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        double percentage = (50D / (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();

        double f = mc.gameSettings.mouseSensitivity * (double) 0.6f + (double) 0.2f;
        double g = f * f * f;
        double h = g * 8.0;

        double x = (int) ((offset.getX()) / h);
        double y = (int) ((offset.getY()) / h);

        x = x * (1 / h) * (1 / 0.15f);
        y = y * (1 / h) * (1 / 0.15f);

        x /= percentage;
        y /= percentage;

        mc.thePlayer.setAngles((float) x, (float) (y * (mc.gameSettings.invertMouse ? 1D : -1D)));
    };

}
