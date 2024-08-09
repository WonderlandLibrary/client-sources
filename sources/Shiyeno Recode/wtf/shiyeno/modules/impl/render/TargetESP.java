package wtf.shiyeno.modules.impl.render;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.impl.combat.AuraFunction;
import wtf.shiyeno.util.animations.Animation;
import wtf.shiyeno.util.animations.Direction;
import wtf.shiyeno.util.animations.impl.DecelerateAnimation;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.ProjectionUtils;
import wtf.shiyeno.util.render.RenderUtil;

@FunctionAnnotation(name="TargetESP", type=Type.Render)
public class TargetESP extends Function {
    private final Animation alpha = new DecelerateAnimation(600, 255.0);
    public static final long detime = System.currentTimeMillis();
    private LivingEntity currentTarget;

    public TargetESP() {
    }

    @Override
    public void onEvent(Event event) {
        boolean auraEnabled = Managment.FUNCTION_MANAGER.get("AttackAura").isState();

        if (event instanceof EventUpdate) {
            if (AuraFunction.target != null) {
                this.currentTarget = AuraFunction.target;
            }
            this.alpha.setDirection(!auraEnabled || AuraFunction.target == null ? Direction.BACKWARDS : Direction.FORWARDS);
        }

        if (event instanceof EventRender) {
            EventRender eventRender = (EventRender) event;
            if (this.alpha.finished(Direction.BACKWARDS)) {
                return;
            }

            if (this.currentTarget != null && this.currentTarget != TargetESP.mc.player) {
                double x = this.currentTarget.lastTickPosX + (this.currentTarget.getPosX() - this.currentTarget.lastTickPosX) * eventRender.partialTicks;
                double y = this.currentTarget.lastTickPosY + (this.currentTarget.getPosY() - this.currentTarget.lastTickPosY) * eventRender.partialTicks;
                double z = this.currentTarget.lastTickPosZ + (this.currentTarget.getPosZ() - this.currentTarget.lastTickPosZ) * eventRender.partialTicks;
                Vector2d projected = ProjectionUtils.project(x, y + 1.0, z);
                int color1 = ColorUtil.setAlpha(ColorUtil.getColorStyle(0.0f), (int) this.alpha.getOutput());
                int color2 = ColorUtil.setAlpha(ColorUtil.getColorStyle(90.0f), (int) this.alpha.getOutput());

                if (projected != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) projected.x, (float) projected.y, 0.0f);
                    GL11.glRotatef((float) (Math.sin((System.currentTimeMillis() - detime) / 1000.0f) * 360.0), 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef((float) (-projected.x), (float) (-projected.y), 0.0f);
                    RenderUtil.Render2D.drawImage(new ResourceLocation("shiyeno/images/Marker.png"), (float) (projected.x - 50.0), (float) (projected.y - 50.0), 100.0f, 100.0f, ColorUtil.getColorStyle(180.0f));
                    GL11.glPopMatrix();
                }
            }

            if (AuraFunction.target != null) {
                this.currentTarget = AuraFunction.target;
            }
            this.alpha.setDirection(!auraEnabled || AuraFunction.target == null ? Direction.BACKWARDS : Direction.FORWARDS);
        }
    }
}