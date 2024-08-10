// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.render.FreeLookUtil;
import cc.slack.utils.render.RenderUtil;
import cc.slack.utils.rotations.RaycastUtil;
import cc.slack.utils.rotations.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;

import java.awt.*;

@ModuleInfo(
        name = "SilentHitbox",
        category = Category.GHOST
)
public class SilentHitbox extends Module {

    private final NumberValue<Double> range = new NumberValue<>("Range", 4.5, 2.0, 6.0, 0.1);

    private final NumberValue<Integer> fov = new NumberValue<>("FOV", 10, 0, 90, 2);
    private final BooleanValue fovRender = new BooleanValue("FOV Render", false);

    private final NumberValue<Float> expand = new NumberValue<>("Expand", 0.3f, 0f, 3f, 0.05f);

    private boolean enabled = false;

    public SilentHitbox() {
        addSettings(range, fov, fovRender, expand);
    }

    @Override
    public void onDisable() {
        if (enabled) {
            disable();
        }
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        Entity target = AttackUtil.getTarget(range.getValue(), "FOV");
        if (target == null) {
            if (enabled) {
                disable();
            }
            return;
        }

        float[] tempRotation = RotationUtil.getPlayerRotation();

        if (enabled) {
            mc.thePlayer.rotationYaw = FreeLookUtil.cameraYaw + 180;
            mc.thePlayer.rotationPitch = FreeLookUtil.cameraPitch;
        }

        if ((RotationUtil.getRotationDifference(
                RotationUtil.getTargetRotations(
                        target.getEntityBoundingBox(),
                        RotationUtil.TargetRotation.EDGE,
                        0,
                        expand.getValue()
                )
        ) < fov.getValue() ||
            RaycastUtil.isHitable(
                range.getValue() + 1,
                RotationUtil.getPlayerRotation(),
                target,
                expand.getValue()))
        && !(RaycastUtil.isHitable(
                range.getValue() + 1,
                RotationUtil.getPlayerRotation(),
                target,
                0))) {
            RotationUtil.setPlayerRotation(tempRotation);
            if (!enabled) {
                FreeLookUtil.enable();
                FreeLookUtil.cameraYaw += 180;
                enabled = true;
            }
            RotationUtil.setPlayerRotation(
                    RotationUtil.getLimitedRotation(
                            RotationUtil.getPlayerRotation(),
                            RotationUtil.getTargetRotations(target.getEntityBoundingBox(), RotationUtil.TargetRotation.INNER, 0.02),
                            (float) MathUtil.getRandomInRange(7.8f, 12.3f))
            );
        } else {
            RotationUtil.setPlayerRotation(tempRotation);
            if (enabled) {
                disable();
            }
        }
    }

    @Listen
    public void onRender (RenderEvent event) {
        if (event.state != RenderEvent.State.RENDER_2D) return;
        if (!fovRender.getValue()) return;

        float h = mc.getScaledResolution().getScaledHeight();
        float w = mc.getScaledResolution().getScaledWidth();

        RenderUtil.drawCircle(w / 2, h / 2, h * fov.getValue() / mc.gameSettings.fovSetting, new Color(250, 250, 250, 50).getRGB());
    }

    private void disable() {
        mc.thePlayer.rotationYaw = FreeLookUtil.cameraYaw + 180;
        mc.thePlayer.rotationPitch = FreeLookUtil.cameraPitch;
        FreeLookUtil.setFreelooking(false);
        enabled = false;
    }

}
