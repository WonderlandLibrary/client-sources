package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.AttackEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.particle.Particle3D;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Particles", description = "Создаёт красивые частицы.", category = Category.RENDER)
public class Particles extends Module {
    private final MultiBooleanValue elements = new MultiBooleanValue("Спавнить при", this)
            .add(
                    new BooleanValue("Ударе", true),
                    new BooleanValue("Ходьбе", true)
            );
    public final BooleanValue randomColor = new BooleanValue("Рандомный цвет", this, false);


    private final List<Particle3D> targetParticles = new ArrayList<>();
    private final List<Particle3D> flameParticles = new ArrayList<>();

    private final ResourceLocation texture = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/firefly.png");

    @Override
    protected void onEnable() {
        super.onEnable();
        targetParticles.clear();
        flameParticles.clear();
    }

    private final Listener<AttackEvent> onAttack = event -> {
        Entity target = event.getTarget();
        float motion = 2;
        if (elements.isEnabled("Ударе")) {
            for (int i = 0; i < 5; i++) {
                targetParticles.add(new Particle3D(new Vector3d(target.getPosX(), target.getPosY() + Mathf.getRandom(0, target.getHeight()), target.getPosZ()),
                        new Vector3d(Mathf.getRandom(-motion, motion), 0, Mathf.getRandom(-motion, motion)), targetParticles.size(), ColorUtil.random().hashCode()));
            }
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {

        if (elements.isEnabled("Ходьбе")) {
            if (mc.player.lastTickPosX != mc.player.getPosX() || mc.player.lastTickPosY != mc.player.getPosY() || mc.player.lastTickPosZ != mc.player.getPosZ()) {
                for (int i = 0; i < 3; i++) {
                    if (!mc.gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON)) {
                        flameParticles.add(new Particle3D(new Vector3d(mc.player.getPosX() + Mathf.getRandom(-0.5, 0.5), mc.player.getPosY() + Mathf.getRandom(0, mc.player.getHeight()), mc.player.getPosZ() + Mathf.getRandom(-0.5, 0.5)),
                                new Vector3d(mc.player.motion.x + Mathf.getRandom(-0.1, 0.1), 0, mc.player.motion.z + Mathf.getRandom(-0.1, 0.1))
                                        .mul(2 * (1 + Math.random())), flameParticles.size(), ColorUtil.random().hashCode()));
                    }
                }
            }
        }

        targetParticles.removeIf(particle -> particle.getTime().hasReached(5000));
        flameParticles.removeIf(particle -> particle.getTime().hasReached(3500));
    };
    private final Listener<WorldLoadEvent> worldLoadEventListener = event -> {
        targetParticles.clear();
        flameParticles.clear();
    };
    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        MatrixStack matrix = event.getMatrix();

        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        RenderSystem.pushMatrix();
        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light)
            RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        float pos = 0.1F;
        RectUtil.bindTexture(texture);

        matrix.push();
        if (!targetParticles.isEmpty()) {
            targetParticles.forEach(Particle3D::update);
            for (final Particle3D particle : targetParticles) {
                if ((int) particle.getAnimation().getValue() != 128 && !particle.getTime().hasReached(500)) {
                    particle.getAnimation().run(128);
                }
                if ((int) particle.getAnimation().getValue() != 0 && particle.getTime().hasReached(2000)) {
                    particle.getAnimation().run(0);
                }
                int color = ColorUtil.replAlpha(getTheme().getClientColor(particle.getIndex() * 50), (int) (particle.getAnimation().getValue()));
                if (randomColor.getValue())
                    color = ColorUtil.replAlpha(particle.getColor(), (int) (particle.getAnimation().getValue()));
                final Vector3d v = particle.getPosition();

                final float x = (float) v.x;
                final float y = (float) v.y;
                final float z = (float) v.z;

                matrix.push();

                RectUtil.setupOrientationMatrix(matrix, x, y, z);

                matrix.rotate(mc.getRenderManager().getCameraOrientation());

                matrix.push();
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                matrix.translate(0, pos / 2F, 0);
                RectUtil.drawRect(matrix, -pos, -pos, pos, pos, color, color, color, color, true, true);
                float size = pos / 2F;
                color = ColorUtil.replAlpha(-1, (int) (particle.getAnimation().getValue()));
                RectUtil.drawRect(matrix, -size, -size, size, size, color, color, color, color, true, true);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                matrix.pop();
                matrix.pop();
            }

        }
        matrix.pop();

        matrix.push();
        if (!flameParticles.isEmpty()) {
            flameParticles.forEach(Particle3D::update);
            for (final Particle3D particle : flameParticles) {
                if ((int) particle.getAnimation().getValue() != 128 && !particle.getTime().hasReached(500)) {
                    particle.getAnimation().run(128);
                }
                if ((int) particle.getAnimation().getValue() != 0 && particle.getTime().hasReached(3000)) {
                    particle.getAnimation().run(0);
                }
                int color = ColorUtil.replAlpha(getTheme().getClientColor(particle.getIndex() * 50), (int) (particle.getAnimation().getValue()));
                if (randomColor.getValue())
                    color = ColorUtil.replAlpha(particle.getColor(), (int) (particle.getAnimation().getValue()));

                final Vector3d v = particle.getPosition();

                final float x = (float) v.x;
                final float y = (float) v.y;
                final float z = (float) v.z;

                matrix.push();

                RectUtil.setupOrientationMatrix(matrix, x, y, z);

                matrix.rotate(mc.getRenderManager().getCameraOrientation());

                matrix.push();
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                matrix.translate(0, pos / 2F, 0);
                RectUtil.drawRect(matrix, -pos, -pos, pos, pos, color, color, color, color, true, true);
                float size = pos / 2F;
                color = ColorUtil.replAlpha(-1, (int) (particle.getAnimation().getValue()));
                RectUtil.drawRect(matrix, -size, -size, size, size, color, color, color, color, true, true);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                matrix.pop();
                matrix.pop();
            }

        }
        matrix.pop();

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light)
            RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();

    };
}
