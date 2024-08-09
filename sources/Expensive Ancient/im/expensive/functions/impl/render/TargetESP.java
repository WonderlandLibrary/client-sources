package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.combat.KillAura;
import im.expensive.functions.impl.combat.TestAura;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.EntityUtils;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.DecelerateAnimation;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.animations.impl.EaseInOutCubic;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

@FunctionRegister(name = "TargetESP", type = Category.Render)
public class TargetESP extends Function {
    private final ModeSetting modeSetting = new ModeSetting("Мод", "Призраки", "Призраки", "Квадрат");
    private final Animation alpha = new DecelerateAnimation(600, 255);

    private LivingEntity currentTarget;

    private double speed;
    private long lastTime = System.currentTimeMillis();
    private LivingEntity target;

    public TargetESP() {
        addSettings(modeSetting);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        KillAura killAura = Expensive.getInstance().getFunctionRegistry().getKillAura();

        if (killAura.getTarget() != null) {
            currentTarget = killAura.getTarget();
        }

        alpha.setDirection(!killAura.isState() || killAura.getTarget() == null ? Direction.BACKWARDS : Direction.FORWARDS);
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (e.getType() != EventDisplay.Type.PRE) {
            return;
        }
        if (currentTarget != null && !alpha.finished(Direction.BACKWARDS) && modeSetting.is("Квадрат")) {
            double sin = Math.sin(System.currentTimeMillis() / 1000.0);
            float size = 150.0F;

            Vector3d interpolated = currentTarget.getPositon(e.getPartialTicks());
            Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + currentTarget.getHeight() / 2f, interpolated.z);
            GlStateManager.pushMatrix();
            GlStateManager.translatef(pos.x, pos.y, 0);
            GlStateManager.rotatef((float) sin * 360, 0, 0, 1);
            GlStateManager.translatef(-pos.x, -pos.y, 0);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
            int alpha = (int) this.alpha.getOutput();
            DisplayUtils.drawImage(new ResourceLocation("expensive/images/target1.png"), pos.x - size / 2f, pos.y - size / 2f, size, size, new Vector4i(
                    ColorUtils.setAlpha(HUD.getColor(0, 1), alpha),
                    ColorUtils.setAlpha(HUD.getColor(90, 1), alpha),
                    ColorUtils.setAlpha(HUD.getColor(180, 1), alpha),
                    ColorUtils.setAlpha(HUD.getColor(270, 1), alpha)
            ));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Subscribe
    private void onWorld(WorldEvent worldEvent) {
        if (currentTarget != null && !alpha.finished(Direction.BACKWARDS) && modeSetting.is("Призраки")) {
            MatrixStack stack = worldEvent.getStack();

            Vector3d pos = EntityUtils.getInterpolatedPositionVec(currentTarget);
            long currentTime = System.currentTimeMillis();

            speed += 3 * (currentTime - lastTime) / 1000.0F;
            lastTime = currentTime;

            for (int count = 0; count < 12; count = count + 3) {
                for (int point = 0; point < 20; point++) {

                    double headingAngle = speed + point * 0.1,
                            factor = 0.8,
                            yOffset = 0.5;

                    double x = pos.x + factor * Math.cos(headingAngle + count * count),
                            z = pos.z + factor * Math.sin(headingAngle - count),
                            y = pos.y + yOffset + 0.3 * Math.sin(speed + point * 0.2 + count) + 0.2 * count;

                    stack.push();
                    stack.translate(x, y, z);
                    stack.scale(0.005F + point / 2000F, 0.005F + point / 2000F, 0.005F + point / 2000F);
                    stack.rotate(mc.getRenderManager().getCameraOrientation());
                    int color = HUD.getColor(point * 3, 1);
                    int alpha = (int) this.alpha.getOutput();

                    DisplayUtils.drawImage(new ResourceLocation("expensive/images/glow.png"), -25, -25, 50, 50, ColorUtils.setAlpha(color, alpha));
                    stack.pop();
                }
            }
        }
    }
}
