package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import wtf.resolute.evented.*;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.impl.combat.KillAura;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import static com.mojang.blaze3d.platform.GlStateManager.GL_QUADS;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR_TEX;
import static wtf.resolute.ResoluteInfo.startTime;

@ModuleAnontion(
        name = "TargetESP",
        type = Categories.Render,
        server = ""
)
public class TargetESP extends Module {
    private final KillAura killAura;

    @Getter
    public static LivingEntity target = null;
    public TargetESP(KillAura killAura) {
        this.killAura = killAura;
    }

    @Subscribe
    private void onRender(WorldEvent2 e) {
        if (killAura.isState() && killAura.getTarget() != null) {
            MatrixStack ms = DisplayUtils.matrixFrom(e.getStack(), mc.gameRenderer.getActiveRenderInfo());
            ms.push();

            RenderSystem.pushMatrix();
            RenderSystem.disableLighting();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableCull();
            RenderSystem.disableAlphaTest();
            RenderSystem.blendFuncSeparate(770, 1, 0, 1);

            double x = killAura.getTarget().getPosX();
            double y = killAura.getTarget().getPosY() + killAura.getTarget().getHeight() / 2f;
            double z = killAura.getTarget().getPosZ();
            double radius = 0.7f;
//                targetAnim = AnimationMath.fast(targetAnim,50+(target.hurtTime/5),5);
            float speed = 30;
            float size = 0.3f;
            double distance = 20; //расстояние между кругами
            int lenght = 24;
            int maxAlpha = 255;
            int alphaFactor = 20;


//                MainUtil.sendMesage(RenderUtil.Render2D.getHurtPercent(target)+"");

            ActiveRenderInfo camera = mc.getRenderManager().info;

            ms.translate(-mc.getRenderManager().info.getProjectedView().getX(),
                    -mc.getRenderManager().info.getProjectedView().getY(),
                    -mc.getRenderManager().info.getProjectedView().getZ());

            Vector3d interpolated = MathUtil.interpolate(killAura.getTarget().getPositionVec(), new Vector3d(killAura.getTarget().lastTickPosX, killAura.getTarget().lastTickPosY, killAura.getTarget().lastTickPosZ), e.getPartialTicks());
            interpolated.y += 0.75f;

            ms.translate(interpolated.x + 0.2f, interpolated.y + 0.5f, interpolated.z);
//                ms.translate(
//                        target.getPosX()-mc.player.getPosX(),y,z+0.7f);

            mc.getTextureManager().bindTexture(new ResourceLocation("resolute/images/circleglow.png"));

            //y1
            for (int i = 0; i < lenght; i++) {
                Quaternion r = camera.getRotation().copy();

                buffer.begin(GL_QUADS, POSITION_COLOR_TEX);


                double angle = 0.15f * (System.currentTimeMillis() - startTime - (i * distance)) / (speed); // Изменение скорости вращения
                double s = sin(angle) * radius;
                double c = cos(angle) * radius;

                ms.translate(s, (c), -c); // Смещение точки относительно центра

                ms.translate(-size / 2f, -size / 2f, 0);
                ms.rotate(r);
                ms.translate(size / 2f, size / 2f, 0);

                int color = ColorUtils.getColorStyle(i);
                int alpha = MathHelper.clamp(maxAlpha - (i * alphaFactor), 0, maxAlpha);
                buffer.pos(ms.getLast().getMatrix(), 0, -size, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(0, 0).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, -size, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(0, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, 0, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(1, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), 0, 0, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(1, 0).endVertex();

                tessellator.draw();

                ms.translate(-size / 2f, -size / 2f, 0);
                r.conjugate();
                ms.rotate(r);
                ms.translate(size / 2f, size / 2f, 0);

                ms.translate(-(s), -(c), (c)); // Смещение в обратную сторону
            }
            //y2
            for (int i = 0; i < lenght; i++) {
                Quaternion r = camera.getRotation().copy();

                buffer.begin(GL_QUADS, POSITION_COLOR_TEX);


                double angle = 0.15f * (System.currentTimeMillis() - startTime - (i * distance)) / (speed); // Изменение скорости вращения
                double s = sin(angle) * radius;
                double c = cos(angle) * radius;

                ms.translate(-s, s, -c); // Смещение точки относительно центра

                ms.translate(-size / 2f, -size / 2f, 0);
                ms.rotate(r);
                ms.translate(size / 2f, size / 2f, 0);


                int color = ColorUtils.getColorStyle(i);
                int alpha = MathHelper.clamp(maxAlpha - (i * alphaFactor), 0, maxAlpha);
                buffer.pos(ms.getLast().getMatrix(), 0, -size, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(0, 0).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, -size, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(0, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, 0, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(1, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), 0, 0, 0).color(DisplayUtils.reAlphaInt(color, alpha)).tex(1, 0).endVertex();

                tessellator.draw();

                ms.translate(-size / 2f, -size / 2f, 0);
                r.conjugate();
                ms.rotate(r);
                ms.translate(size / 2f, size / 2f, 0);

                ms.translate((s), -(s), (c)); // Смещение в обратную сторону
            }

            ms.translate(-x, -y, -z);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableAlphaTest();
            RenderSystem.depthMask(true);
            RenderSystem.popMatrix();
            ms.pop();
        }
    }
}