package xyz.mathax.mathaxclient.utils.render;

import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.renderer.Renderer3D;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Chams;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;
import xyz.mathax.mathaxclient.MatHax;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class WireframeEntityRenderer {
    private static final MatrixStack matrixStack = new MatrixStack();

    private static final Vector4f pos1 = new Vector4f();
    private static final Vector4f pos2 = new Vector4f();
    private static final Vector4f pos3 = new Vector4f();
    private static final Vector4f pos4 = new Vector4f();

    private static double offsetX, offsetY, offsetZ;

    private static Color sideColor, lineColor;
    private static ShapeMode shapeMode;

    public static void render(Render3DEvent event, Entity entity, double scale, Color sideColor, Color lineColor, ShapeMode shapeMode) {
        WireframeEntityRenderer.sideColor = sideColor;
        WireframeEntityRenderer.lineColor = lineColor;
        WireframeEntityRenderer.shapeMode = shapeMode;

        offsetX = MathHelper.lerp(event.tickDelta, entity.lastRenderX, entity.getX());
        offsetY = MathHelper.lerp(event.tickDelta, entity.lastRenderY, entity.getY());
        offsetZ = MathHelper.lerp(event.tickDelta, entity.lastRenderZ, entity.getZ());

        matrixStack.push();
        matrixStack.scale((float) scale, (float) scale, (float) scale);

        EntityRenderer<?> entityRenderer = mc.getEntityRenderDispatcher().getRenderer(entity);

        // LivingEntityRenderer
        if (entityRenderer instanceof LivingEntityRenderer renderer) {
            LivingEntity livingEntity = (LivingEntity) entity;
            EntityModel<LivingEntity> model = renderer.getModel();

            // PlayerEntityRenderer
            if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                PlayerEntityModel<AbstractClientPlayerEntity> playerModel = playerEntityRenderer.getModel();

                playerModel.sneaking = entity.isInSneakingPose();
                BipedEntityModel.ArmPose armPose = PlayerEntityRenderer.getArmPose((AbstractClientPlayerEntity) entity, Hand.MAIN_HAND);
                BipedEntityModel.ArmPose armPose2 = PlayerEntityRenderer.getArmPose((AbstractClientPlayerEntity) entity, Hand.OFF_HAND);

                if (armPose.isTwoHanded()) {
                    armPose2 = livingEntity.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
                }

                if (livingEntity.getMainArm() == Arm.RIGHT) {
                    playerModel.rightArmPose = armPose;
                    playerModel.leftArmPose = armPose2;
                } else {
                    playerModel.rightArmPose = armPose2;
                    playerModel.leftArmPose = armPose;
                }
            }

            model.handSwingProgress = livingEntity.getHandSwingProgress(event.tickDelta);
            model.riding = livingEntity.hasVehicle();
            model.child = livingEntity.isBaby();

            float bodyYaw = MathHelper.lerpAngleDegrees(event.tickDelta, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
            float headYaw = MathHelper.lerpAngleDegrees(event.tickDelta, livingEntity.prevHeadYaw, livingEntity.headYaw);
            float yaw = headYaw - bodyYaw;

            float animationProgress;
            if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof LivingEntity livingEntity2) {
                bodyYaw = MathHelper.lerpAngleDegrees(event.tickDelta, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
                yaw = headYaw - bodyYaw;
                animationProgress = MathHelper.wrapDegrees(yaw);

                if (animationProgress < -85) {
                    animationProgress = -85;
                }

                if (animationProgress >= 85) {
                    animationProgress = 85;
                }

                bodyYaw = headYaw - animationProgress;
                if (animationProgress * animationProgress > 2500) {
                    bodyYaw += animationProgress * 0.2;
                }

                yaw = headYaw - bodyYaw;
            }

            float pitch = MathHelper.lerp(event.tickDelta, livingEntity.prevPitch, livingEntity.getPitch());

            animationProgress = renderer.getAnimationProgress(livingEntity, event.tickDelta);
            float limbDistance = 0;
            float limbAngle = 0;

            if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
                limbDistance = MathHelper.lerp(event.tickDelta, livingEntity.lastLimbDistance, livingEntity.limbDistance);
                limbAngle = livingEntity.limbAngle - livingEntity.limbDistance * (1 - event.tickDelta);

                if (livingEntity.isBaby()) {
                    limbAngle *= 3;
                }

                if (limbDistance > 1) {
                    limbDistance = 1;
                }
            }

            model.animateModel(livingEntity, limbAngle, limbDistance, event.tickDelta);
            model.setAngles(livingEntity, limbAngle, limbDistance, animationProgress, yaw, pitch);

            renderer.setupTransforms(livingEntity, matrixStack, animationProgress, bodyYaw, event.tickDelta);
            matrixStack.scale(-1, -1, 1);
            renderer.scale(livingEntity, matrixStack, event.tickDelta);
            matrixStack.translate(0, -1.5010000467300415, 0);

            // Render
            if (model instanceof AnimalModel animalModel) {
                if (animalModel.child) {
                    matrixStack.push();
                    float g;
                    if (animalModel.headScaled) {
                        g = 1.5F / animalModel.invertedChildHeadScale;
                        matrixStack.scale(g, g, g);
                    }

                    matrixStack.translate(0.0D, animalModel.childHeadYOffset / 16.0F, animalModel.childHeadZOffset / 16.0F);

                    if (model instanceof BipedEntityModel bipedModel) {
                        render(event.renderer, bipedModel.head);
                    } else {
                        animalModel.getHeadParts().forEach(modelPart -> render(event.renderer, (ModelPart) modelPart));
                    }

                    matrixStack.pop();
                    matrixStack.push();
                    g = 1.0F / animalModel.invertedChildBodyScale;
                    matrixStack.scale(g, g, g);
                    matrixStack.translate(0.0D, animalModel.childBodyYOffset / 16.0F, 0.0D);

                    if (model instanceof BipedEntityModel bipedModel) {
                        render(event.renderer, bipedModel.body);
                        render(event.renderer, bipedModel.leftArm);
                        render(event.renderer, bipedModel.rightArm);
                        render(event.renderer, bipedModel.leftLeg);
                        render(event.renderer, bipedModel.rightLeg);
                    } else {
                        animalModel.getBodyParts().forEach(modelPart -> render(event.renderer, (ModelPart) modelPart));
                    }

                    matrixStack.pop();
                } else {
                    if (model instanceof BipedEntityModel bipedModel) {
                        render(event.renderer, bipedModel.head);
                        render(event.renderer, bipedModel.body);
                        render(event.renderer, bipedModel.leftArm);
                        render(event.renderer, bipedModel.rightArm);
                        render(event.renderer, bipedModel.leftLeg);
                        render(event.renderer, bipedModel.rightLeg);
                    } else {
                        animalModel.getHeadParts().forEach(modelPart -> render(event.renderer, (ModelPart) modelPart));
                        animalModel.getBodyParts().forEach(modelPart -> render(event.renderer, (ModelPart) modelPart));
                    }
                }
            }
            else {
                if (model instanceof SinglePartEntityModel singlePartModel) {
                    render(event.renderer, singlePartModel.getPart());
                } else if (model instanceof CompositeEntityModel compositeModel) {
                    compositeModel.getParts().forEach(modelPart -> render(event.renderer, (ModelPart) modelPart));
                } else if (model instanceof LlamaEntityModel llamaModel) {
                    if (llamaModel.child) {
                        matrixStack.push();
                        matrixStack.scale(0.71428573F, 0.64935064F, 0.7936508F);
                        matrixStack.translate(0.0D, 1.3125D, 0.2199999988079071D);
                        render(event.renderer, llamaModel.head);
                        matrixStack.pop();
                        matrixStack.push();
                        matrixStack.scale(0.625F, 0.45454544F, 0.45454544F);
                        matrixStack.translate(0.0D, 2.0625D, 0.0D);
                        render(event.renderer, llamaModel.body);
                        matrixStack.pop();
                        matrixStack.push();
                        matrixStack.scale(0.45454544F, 0.41322312F, 0.45454544F);
                        matrixStack.translate(0.0D, 2.0625D, 0.0D);
                        render(event.renderer, llamaModel.rightHindLeg);
                        render(event.renderer, llamaModel.leftHindLeg);
                        render(event.renderer, llamaModel.rightFrontLeg);
                        render(event.renderer, llamaModel.leftFrontLeg);
                        render(event.renderer, llamaModel.rightChest);
                        render(event.renderer, llamaModel.leftChest);
                        matrixStack.pop();
                    } else {
                        render(event.renderer, llamaModel.head);
                        render(event.renderer, llamaModel.body);
                        render(event.renderer, llamaModel.rightHindLeg);
                        render(event.renderer, llamaModel.leftHindLeg);
                        render(event.renderer, llamaModel.rightFrontLeg);
                        render(event.renderer, llamaModel.leftFrontLeg);
                        render(event.renderer, llamaModel.rightChest);
                        render(event.renderer, llamaModel.leftChest);
                    }
                } else if (model instanceof RabbitEntityModel rabbitModel) {
                    if (rabbitModel.child) {
                        matrixStack.push();
                        matrixStack.scale(0.56666666F, 0.56666666F, 0.56666666F);
                        matrixStack.translate(0.0D, 1.375D, 0.125D);
                        render(event.renderer, rabbitModel.head);
                        render(event.renderer, rabbitModel.leftEar);
                        render(event.renderer, rabbitModel.rightEar);
                        render(event.renderer, rabbitModel.nose);
                        matrixStack.pop();
                        matrixStack.push();
                        matrixStack.scale(0.4F, 0.4F, 0.4F);
                        matrixStack.translate(0.0D, 2.25D, 0.0D);
                        render(event.renderer, rabbitModel.leftHindLeg);
                        render(event.renderer, rabbitModel.rightHindLeg);
                        render(event.renderer, rabbitModel.leftHaunch);
                        render(event.renderer, rabbitModel.rightHaunch);
                        render(event.renderer, rabbitModel.body);
                        render(event.renderer, rabbitModel.leftFrontLeg);
                        render(event.renderer, rabbitModel.rightFrontLeg);
                        render(event.renderer, rabbitModel.tail);
                        matrixStack.pop();
                    } else {
                        matrixStack.push();
                        matrixStack.scale(0.6F, 0.6F, 0.6F);
                        matrixStack.translate(0.0D, 1.0D, 0.0D);
                        render(event.renderer, rabbitModel.leftHindLeg);
                        render(event.renderer, rabbitModel.rightHindLeg);
                        render(event.renderer, rabbitModel.leftHaunch);
                        render(event.renderer, rabbitModel.rightHaunch);
                        render(event.renderer, rabbitModel.body);
                        render(event.renderer, rabbitModel.leftFrontLeg);
                        render(event.renderer, rabbitModel.rightFrontLeg);
                        render(event.renderer, rabbitModel.head);
                        render(event.renderer, rabbitModel.rightEar);
                        render(event.renderer, rabbitModel.leftEar);
                        render(event.renderer, rabbitModel.tail);
                        render(event.renderer, rabbitModel.nose);
                        matrixStack.pop();
                    }
                }
            }
        }

        if (entityRenderer instanceof EndCrystalEntityRenderer renderer) {
            EndCrystalEntity crystalEntity = (EndCrystalEntity) entity;
            Chams chams = Modules.get().get(Chams.class);
            boolean chamsEnabled = chams.isEnabled() && chams.crystalsSetting.get();

            matrixStack.push();

            float h;
            if (chamsEnabled) {
                float f = (float) crystalEntity.endCrystalAge + event.tickDelta;
                float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
                g = (g * g + g) * 0.4F * chams.crystalsBounceSetting.get().floatValue();
                h = g - 1.4F;
            } else {
                h = EndCrystalEntityRenderer.getYOffset(crystalEntity, event.tickDelta);
            }

            float j = ((float) crystalEntity.endCrystalAge + event.tickDelta) * 3.0F;
            matrixStack.push();
            if (chamsEnabled) {
                matrixStack.scale(2.0F * chams.crystalsScaleSetting.get().floatValue(), 2.0F * chams.crystalsScaleSetting.get().floatValue(), 2.0F * chams.crystalsScaleSetting.get().floatValue());
            } else {
                matrixStack.scale(2.0F, 2.0F, 2.0F);
            }

            matrixStack.translate(0.0D, -0.5D, 0.0D);
            if (crystalEntity.shouldShowBottom()) {
                render(event.renderer, renderer.bottom);
            }

            if (chamsEnabled) {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j * chams.crystalsRotationSpeedSetting.get().floatValue()));
            } else {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));
            }

            matrixStack.translate(0.0D, 1.5F + h / 2.0F, 0.0D);
            matrixStack.multiply(new Quaternionf().setAngleAxis(60.0F, EndCrystalEntityRenderer.SINE_45_DEGREES, 0.0F, EndCrystalEntityRenderer.SINE_45_DEGREES));

            if (!chamsEnabled || chams.renderFrame1Setting.get()) {
                render(event.renderer, renderer.frame);
            }

            matrixStack.scale(0.875F, 0.875F, 0.875F);
            matrixStack.multiply(new Quaternionf().setAngleAxis(60.0F, EndCrystalEntityRenderer.SINE_45_DEGREES, 0.0F, EndCrystalEntityRenderer.SINE_45_DEGREES));

            if (chamsEnabled) {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j * chams.crystalsRotationSpeedSetting.get().floatValue()));
            } else {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));
            }

            if (!chamsEnabled || chams.renderFrame2Setting.get()) {
                render(event.renderer, renderer.frame);
            }

            matrixStack.scale(0.875F, 0.875F, 0.875F);
            matrixStack.multiply(new Quaternionf().setAngleAxis(60.0F, EndCrystalEntityRenderer.SINE_45_DEGREES, 0.0F, EndCrystalEntityRenderer.SINE_45_DEGREES));

            if (chamsEnabled) {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j * chams.crystalsRotationSpeedSetting.get().floatValue()));
            } else {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));
            }

            if (!chamsEnabled || chams.renderCoreSetting.get()) {
                render(event.renderer, renderer.core);
            }

            matrixStack.pop();
            matrixStack.pop();
        } else if (entityRenderer instanceof BoatEntityRenderer renderer) {
            BoatEntity boatEntity = (BoatEntity) entity;

            matrixStack.push();
            matrixStack.translate(0.0D, 0.375D, 0.0D);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - MathHelper.lerp(event.tickDelta, entity.prevYaw, entity.getYaw())));
            float h = (float)boatEntity.getDamageWobbleTicks() - event.tickDelta;
            float j = boatEntity.getDamageWobbleStrength() - event.tickDelta;
            if (j < 0.0F) {
                j = 0.0F;
            }

            if (h > 0.0F) {
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(h) * h * j / 10.0F * (float)boatEntity.getDamageWobbleSide()));
            }

            float k = boatEntity.interpolateBubbleWobble(event.tickDelta);
            if (!MathHelper.approximatelyEquals(k, 0.0F)) {
                matrixStack.multiply(new Quaternionf().setAngleAxis(boatEntity.interpolateBubbleWobble(event.tickDelta), 1.0F, 0.0F, 1.0F));
            }

            CompositeEntityModel<BoatEntity> boatEntityModel = renderer.texturesAndModels.get(boatEntity.getVariant()).getSecond();
            matrixStack.scale(-1.0F, -1.0F, 1.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
            boatEntityModel.setAngles(boatEntity, event.tickDelta, 0.0F, -0.1F, 0.0F, 0.0F);
            boatEntityModel.getParts().forEach(modelPart -> render(event.renderer, modelPart));
            if (!boatEntity.isSubmergedInWater() && boatEntityModel instanceof ModelWithWaterPatch modelWithWaterPatch) {
                render(event.renderer, modelWithWaterPatch.getWaterPatch());
            }

            matrixStack.pop();
        } else if (entityRenderer instanceof ItemEntityRenderer) {
            double dx = (entity.getX() - entity.prevX) * event.tickDelta;
            double dy = (entity.getY() - entity.prevY) * event.tickDelta;
            double dz = (entity.getZ() - entity.prevZ) * event.tickDelta;

            Box box = entity.getBoundingBox();
            event.renderer.box(dx + box.minX, dy + box.minY, dz + box.minZ, dx + box.maxX, dy + box.maxY, dz + box.maxZ, sideColor, lineColor, shapeMode, 0);
        }

        matrixStack.pop();
    }

    private static void render(Renderer3D renderer, ModelPart part) {
        if (!part.visible || (part.cuboids.isEmpty() && part.children.isEmpty())) {
            return;
        }

        matrixStack.push();
        part.rotate(matrixStack);

        for (ModelPart.Cuboid cuboid : part.cuboids) {
            render(renderer, cuboid, offsetX, offsetY, offsetZ);
        }

        for (ModelPart child : part.children.values()) {
            render(renderer, child);
        }

        matrixStack.pop();
    }

    private static void render(Renderer3D renderer, ModelPart.Cuboid cuboid, double offsetX, double offsetY, double offsetZ) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        for (ModelPart.Quad quad : cuboid.sides) {
            pos1.set(quad.vertices[0].pos.x / 16, quad.vertices[0].pos.y / 16, quad.vertices[0].pos.z / 16, 1);
            pos1.mul(matrix4f);

            pos2.set(quad.vertices[1].pos.x / 16, quad.vertices[1].pos.y / 16, quad.vertices[1].pos.z / 16, 1);
            pos2.mul(matrix4f);


            pos3.set(quad.vertices[2].pos.x / 16, quad.vertices[2].pos.y / 16, quad.vertices[2].pos.z / 16, 1);
            pos3.mul(matrix4f);

            pos4.set(quad.vertices[3].pos.x / 16, quad.vertices[3].pos.y / 16, quad.vertices[3].pos.z / 16, 1);
            pos4.mul(matrix4f);

            if (shapeMode.sides()) {
                int i1 = renderer.triangles.vec3(offsetX + pos1.x, offsetY + pos1.y, offsetZ + pos1.z).color(sideColor).next();
                int i2 = renderer.triangles.vec3(offsetX + pos2.x, offsetY + pos2.y, offsetZ + pos2.z).color(sideColor).next();
                int i3 = renderer.triangles.vec3(offsetX + pos3.x, offsetY + pos3.y, offsetZ + pos3.z).color(sideColor).next();
                int i4 = renderer.triangles.vec3(offsetX + pos4.x, offsetY + pos4.y, offsetZ + pos4.z).color(sideColor).next();
                renderer.triangles.quad(i1, i2, i3, i4);
            }

            if (shapeMode.lines()) {
                renderer.line(offsetX + pos1.x, offsetY + pos1.y, offsetZ + pos1.z, offsetX + pos2.x, offsetY + pos2.y, offsetZ + pos2.z, lineColor);
                renderer.line(offsetX + pos2.x, offsetY + pos2.y, offsetZ + pos2.z, offsetX + pos3.x, offsetY + pos3.y, offsetZ + pos3.z, lineColor);
                renderer.line(offsetX + pos3.x, offsetY + pos3.y, offsetZ + pos3.z, offsetX + pos4.x, offsetY + pos4.y, offsetZ + pos4.z, lineColor);
                renderer.line(offsetX + pos1.x, offsetY + pos1.y, offsetZ + pos1.z, offsetX + pos1.x, offsetY + pos1.y, offsetZ + pos1.z, lineColor);
            }
        }
    }
}
