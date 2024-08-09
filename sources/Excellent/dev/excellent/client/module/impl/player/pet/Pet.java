package dev.excellent.client.module.impl.player.pet;

import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.impl.player.LittleBro;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

@Data
public class Pet implements IAccess {
    private final ResourceLocation BLOOM_TEXTURE = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/bloom.png");
    private final Vector3d position = new Vector3d(0, 0, 0);
    private final Vector3d destination = new Vector3d(0, 0, 0);
    private final Vector3d motion = new Vector3d(0, 0, 0);
    private final AxisAlignedBB hitbox = new AxisAlignedBB(0, 0, 0, 0.25F, 0.25F, 0.25F);
    private final PetController petController = new PetController(this);
    private float yaw = 0;
    private float pitch = 0;
    private BlockPos blockTarget = new BlockPos(0, 0, 0);
    private PetState currentState = PetState.IDLE;
    private Entity target = null;

    @Getter
    @RequiredArgsConstructor
    public enum PetState {
        IDLE("афк"),
        MOVE_TO_ENTITY("следую за энтити");
        private final String state;
    }

    @Getter
    @RequiredArgsConstructor
    public enum PetTexture {
        TYPE_1("Робот 1", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_1.png")),
        TYPE_2("Робот 2", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_2.png")),
        TYPE_3("Робот 3", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_3.png")),
        TYPE_4("Робот 4", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_4.png")),
        TYPE_5("Пинку", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_5.png")),
        TYPE_6("Мистер Аксолотль", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_6.png")),
        TYPE_7("Мистер Свин", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_7.png")),
        TYPE_8("Военный Свин", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_8.png")),
        TYPE_9("CunWey", new ResourceLocation(excellent.getInfo().getNamespace(), "texture/pet/type_9.png")),
        ;
        private final String name;
        private final ResourceLocation location;

        @Override
        public String toString() {
            return name;
        }
    }

    public void onRender3DPosedEvent(Render3DPosedEvent event) {
        MatrixStack matrix = event.getMatrix();

        boolean light = false/*GL11.glIsEnabled(GL11.GL_LIGHTING)*/;

        startRender(matrix, light);
        {
            matrix.push();

            petController.update();

            RectUtil.setupOrientationMatrix(matrix, position.x, position.y, position.z);

            drawModel(matrix);

            matrix.pop();
        }
        endRender(light, matrix);
    }

    public void updateMovement() {
        updateRotation();

        double speed = 0.1;

        double deltaX = destination.x - position.x;
        double deltaY = destination.y - position.y;
        double deltaZ = destination.z - position.z;

//        position.x += deltaX * speed;
//        position.y += deltaY * speed;
//        position.z += deltaZ * speed;
        position.x = Interpolator.lerp(position.x, position.x + deltaX * speed, 0.05);
        position.y = Interpolator.lerp(position.y, position.y + deltaY * speed, 0.05);
        position.z = Interpolator.lerp(position.z, position.z + deltaZ * speed, 0.05);
    }

    private void updateRotation() {
        float targetYaw = calculateYaw(destination.x, destination.z);
        float targetPitch = calculatePitch(destination.x, destination.y, destination.z);
        float yawDiff = MathHelper.wrapDegrees(targetYaw - yaw);
        float pitchDiff = targetPitch - pitch;
        float yawStep = 10;
        if (yawDiff > yawStep) {
            yaw = Interpolator.lerp(yaw, yaw + yawStep, 0.1F);
        } else if (yawDiff < -yawStep) {
            yaw = Interpolator.lerp(yaw, yaw - yawStep, 0.1F);
        } else {
            yaw = (targetYaw % 360);
        }
        pitch = Interpolator.lerp(pitch, pitch + pitchDiff * 0.1F, 0.1F);
        pitch = (float) Mathf.clamp(-90, 90, pitch);
    }

    private float calculateYaw(double x, double z) {
        return (float) (MathHelper.atan2(z - position.z, x - position.x) * (180 / Math.PI)) - 90.0F;
    }

    private float calculatePitch(double x, double y, double z) {
        double xDist = x - position.x;
        double yDist = y - position.y;
        double zDist = z - position.z;
        double horizontalDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);
        return (float) (-(MathHelper.atan2(yDist, horizontalDist) * (180 / Math.PI)));
    }

    public void setDestination(Vector3d destination) {
        setDestination(destination.x, destination.y, destination.z);
    }

    public void setDestination(double x, double y, double z) {
        destination.set(x, y, z);
    }

    private void drawModel(MatrixStack matrix) {
        boolean depth = true;
        boolean o_depth = true;
        float overlay_offset = 0.03F;
        float overlay_forward = overlay_offset / 2F;
        matrix.push();
        matrix.rotate(Vector3f.YP.rotationDegrees(-yaw));
        matrix.rotate(Vector3f.XP.rotationDegrees(pitch));

        {
            matrix.push();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            matrix.translate(-(hitbox.maxX / 2F), 0, -(hitbox.maxX / 2F));
            {
                matrix.push();
                RectUtil.drawHeadSide(matrix, getTexture(), 0, 0, hitbox.maxX, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.FRONT, depth);
                RectUtil.drawHeadSide(matrix, getTexture(), 0, 0, 0, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.BACK, depth);
                matrix.pop();

                {
                    matrix.push();
                    RectUtil.drawHeadSide(matrix, getTexture(), 0 - overlay_offset / 2, 0 - overlay_offset / 2, hitbox.maxX + overlay_forward, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_FRONT, o_depth);
                    RectUtil.drawHeadSide(matrix, getTexture(), 0 - overlay_offset / 2, 0 - overlay_offset / 2, 0 - overlay_forward, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_BACK, o_depth);
                    matrix.pop();
                }

                matrix.push();
                matrix.rotate(Vector3f.YN.rotationDegrees(90));
                RectUtil.drawHeadSide(matrix, getTexture(), 0, 0, 0, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.RIGHT, depth);
                matrix.pop();

                {
                    matrix.push();
                    matrix.rotate(Vector3f.YN.rotationDegrees(90));
                    RectUtil.drawHeadSide(matrix, getTexture(), 0 - overlay_offset / 2, 0 - overlay_offset / 2, 0 + overlay_forward, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_RIGHT, o_depth);
                    matrix.pop();
                }

                matrix.push();
                matrix.rotate(Vector3f.YP.rotationDegrees(90));
                RectUtil.drawHeadSide(matrix, getTexture(), -hitbox.maxX, 0, hitbox.maxX, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.LEFT, depth);
                matrix.pop();

                {
                    matrix.push();
                    matrix.rotate(Vector3f.YP.rotationDegrees(90));
                    RectUtil.drawHeadSide(matrix, getTexture(), -hitbox.maxX - overlay_offset / 2, 0 - overlay_offset / 2, hitbox.maxX + overlay_offset / 2, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_LEFT, o_depth);
                    matrix.pop();
                }

                matrix.push();
                matrix.rotate(Vector3f.XN.rotationDegrees(90));
                RectUtil.drawHeadSide(matrix, getTexture(), 0, -hitbox.maxX, hitbox.maxX, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.TOP, depth);
                RectUtil.drawHeadSide(matrix, getTexture(), 0, -hitbox.maxX, 0, (float) hitbox.maxX, (float) hitbox.maxY, RectUtil.HeadSide.BOTTOM, depth);
                matrix.pop();

                {
                    matrix.push();
                    matrix.rotate(Vector3f.XN.rotationDegrees(90));
                    RectUtil.drawHeadSide(matrix, getTexture(), 0 - overlay_offset / 2, -hitbox.maxX - overlay_offset / 2, hitbox.maxX + overlay_offset / 2, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_TOP, o_depth);
                    RectUtil.drawHeadSide(matrix, getTexture(), 0 - overlay_offset / 2, -hitbox.maxX - overlay_offset / 2, 0 - overlay_offset / 2, (float) hitbox.maxX + overlay_offset, (float) hitbox.maxY + overlay_offset, RectUtil.HeadSide.O_BOTTOM, o_depth);
                    matrix.pop();
                }
            }
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            matrix.pop();
        }
        matrix.pop();
    }

    private void startRender(MatrixStack matrix, boolean light) {
        RenderSystem.pushMatrix();
        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light) RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    private void endRender(boolean light, MatrixStack matrix) {
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light) RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();
    }

    private ResourceLocation getTexture() {
        return LittleBro.singleton.get().getTexture().getValue().getLocation();
    }

    public void reset() {
        if (mc.player == null) return;
        double playerX = mc.player.getPositionVec().x;
        double playerY = mc.player.getPositionVec().y;
        double playerZ = mc.player.getPositionVec().z;
        position.set(playerX, playerY, playerZ);
        destination.set(playerX, playerY, playerZ);
        motion.set(0, 0, 0);
        yaw = 0;
        pitch = 0;
    }
}
