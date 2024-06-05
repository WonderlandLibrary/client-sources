package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.Interpolation;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.init.Modules;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.awt.*;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SkeletonModule extends ToggleModule
{
    /**
     *
     */
    public SkeletonModule()
    {
        super("Skeleton", "Renders a skeleton to show player limbs",
                ModuleCategory.RENDER);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onRenderWorld(RenderWorldEvent event)
    {
        float g = event.getTickDelta();
        //
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(MinecraftClient.isFancyGraphicsOrBetter());
        RenderSystem.enableCull();
        for (Entity entity : mc.world.getEntities())
        {
            if (entity == null || !entity.isAlive())
            {
                continue;
            }
            if (entity instanceof PlayerEntity playerEntity)
            {
                if (mc.options.getPerspective().isFirstPerson() && playerEntity == mc.player)
                {
                    continue;
                }
                Vec3d skeletonPos = Interpolation.getInterpolatedPosition(entity, g);
                PlayerEntityRenderer livingEntityRenderer =
                        (PlayerEntityRenderer) (LivingEntityRenderer<?, ?>) mc.getEntityRenderDispatcher().getRenderer(playerEntity);
                PlayerEntityModel<PlayerEntity> playerEntityModel =
                        (PlayerEntityModel) livingEntityRenderer.getModel();
                float h = MathHelper.lerpAngleDegrees(g,
                        playerEntity.prevBodyYaw, playerEntity.bodyYaw);
                float j = MathHelper.lerpAngleDegrees(g,
                        playerEntity.prevHeadYaw, playerEntity.headYaw);
                float q = playerEntity.limbAnimator.getPos() - playerEntity.limbAnimator.getSpeed() * (1.0f - g);
                float p = playerEntity.limbAnimator.getSpeed(g);
                float o = (float) playerEntity.age + g;
                float k = j - h;
                float m = playerEntity.getPitch(g);
                playerEntityModel.animateModel(playerEntity, q, p, g);
                playerEntityModel.setAngles(playerEntity, q, p, o, k, m);
                boolean swimming = playerEntity.isInSwimmingPose();
                boolean sneaking = playerEntity.isSneaking();
                boolean flying = playerEntity.isFallFlying();
                ModelPart head = playerEntityModel.head;
                ModelPart leftArm = playerEntityModel.leftArm;
                ModelPart rightArm = playerEntityModel.rightArm;
                ModelPart leftLeg = playerEntityModel.leftLeg;
                ModelPart rightLeg = playerEntityModel.rightLeg;
                event.getMatrices().translate(skeletonPos.x, skeletonPos.y, skeletonPos.z);
                if (swimming) 
                {
                    event.getMatrices().translate(0, 0.35f, 0);
                }
                event.getMatrices().multiply(new Quaternionf().setAngleAxis((h + 180) * Math.PI / 180.0f, 0, -1, 0));
                if (swimming || flying) 
                {
                    event.getMatrices().multiply(new Quaternionf().setAngleAxis((90 + m) * Math.PI / 180.0f, -1, 0, 0));
                }
                if (swimming) 
                {
                    event.getMatrices().translate(0, -0.95f, 0);
                }
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                        VertexFormats.POSITION_COLOR);
                Matrix4f matrix4f = event.getMatrices().peek().getPositionMatrix();
                Color skeletonColor = Modules.COLORS.getColor();
                bufferBuilder.vertex(matrix4f, 0, sneaking ? 0.6f : 0.7f,
                        sneaking ? 0.23f : 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, sneaking ? 1.05f : 1.4f,
                        0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, -0.37f, sneaking ? 1.05f :
                        1.35f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0.37f, sneaking ? 1.05f :
                        1.35f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, -0.15f, sneaking ? 0.6f :
                        0.7f, sneaking ? 0.23f : 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0.15f, sneaking ? 0.6f : 0.7f,
                        sneaking ? 0.23f : 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                //
                event.getMatrices().push();
                event.getMatrices().translate(0, sneaking ? 1.05f : 1.4f, 0);
                rotateSkeleton(event.getMatrices(), head);
                matrix4f = event.getMatrices().peek().getPositionMatrix();
                bufferBuilder.vertex(matrix4f, 0, 0, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, 0.15f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                event.getMatrices().pop();
                //
                event.getMatrices().push();
                event.getMatrices().translate(0.15f, sneaking ? 0.6f : 0.7f, sneaking ? 0.23f : 0);
                rotateSkeleton(event.getMatrices(), rightLeg);
                matrix4f = event.getMatrices().peek().getPositionMatrix();
                bufferBuilder.vertex(matrix4f, 0, 0, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, -0.6f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                event.getMatrices().pop();
                // 
                event.getMatrices().push();
                event.getMatrices().translate(-0.15f, sneaking ? 0.6f : 0.7f, sneaking ? 0.23f : 0);
                rotateSkeleton(event.getMatrices(), leftLeg);
                matrix4f = event.getMatrices().peek().getPositionMatrix();
                bufferBuilder.vertex(matrix4f, 0, 0, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, -0.6f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                event.getMatrices().pop();
                // 
                event.getMatrices().push();
                event.getMatrices().translate(0.37f, sneaking ? 1.05f : 1.35f, 0);
                rotateSkeleton(event.getMatrices(), rightArm);
                matrix4f = event.getMatrices().peek().getPositionMatrix();
                bufferBuilder.vertex(matrix4f, 0, 0, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, -0.55f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                event.getMatrices().pop();
                //
                event.getMatrices().push();
                event.getMatrices().translate(-0.37f, sneaking ? 1.05f : 1.35f, 0);
                rotateSkeleton(event.getMatrices(), leftArm);
                matrix4f = event.getMatrices().peek().getPositionMatrix();
                bufferBuilder.vertex(matrix4f, 0, 0, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                bufferBuilder.vertex(matrix4f, 0, -0.55f, 0).color(skeletonColor.getRed(), skeletonColor.getGreen(), skeletonColor.getBlue(), 255.0f).next();
                event.getMatrices().pop();
                // bufferBuilder.clear();
                tessellator.draw();
                if (swimming) 
                {
                    event.getMatrices().translate(0, 0.95f, 0);
                }
                if (swimming || flying) 
                {
                    event.getMatrices().multiply(new Quaternionf().setAngleAxis((90 + m) * Math.PI / 180F, 1, 0, 0));
                }
                if (swimming) 
                {
                    event.getMatrices().translate(0, -0.35f, 0);
                }
                event.getMatrices().multiply(new Quaternionf().setAngleAxis((h + 180) * Math.PI / 180.0f, 0, 1, 0));
                event.getMatrices().translate(-skeletonPos.x, -skeletonPos.y,
                        -skeletonPos.z);
            }
        }
        RenderSystem.disableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
    }

    /**
     *
     * @param matrix
     * @param modelPart
     */
    private void rotateSkeleton(MatrixStack matrix, ModelPart modelPart)
    {
        if (modelPart.roll != 0.0f)
        {
            matrix.multiply(RotationAxis.POSITIVE_Z.rotation(modelPart.roll));
        }
        if (modelPart.yaw != 0.0f)
        {
            matrix.multiply(RotationAxis.NEGATIVE_Y.rotation(modelPart.yaw));
        }
        if (modelPart.pitch != 0.0f)
        {
            matrix.multiply(RotationAxis.NEGATIVE_X.rotation(modelPart.pitch));
        }
    }
}
