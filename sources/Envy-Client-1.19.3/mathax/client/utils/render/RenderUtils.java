package mathax.client.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import mathax.client.systems.modules.Modules;
import mathax.client.mixininterface.IMatrix4f;
import mathax.client.systems.modules.render.NoBob;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import static mathax.client.MatHax.mc;

public class RenderUtils {
    public static Vec3d center;

    // Items
    public static void drawItem(ItemStack itemStack, int x, int y, double scale, boolean overlay) {
        //RenderSystem.disableDepthTest();

        MatrixStack matrices = RenderSystem.getModelViewStack();

        matrices.push();
        matrices.scale((float) scale, (float) scale, 1);

        mc.getItemRenderer().renderGuiItemIcon(itemStack, (int) (x / scale), (int) (y / scale));
        if (overlay) mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, itemStack, (int) (x / scale), (int) (y / scale), null);

        matrices.pop();
        //RenderSystem.enableDepthTest();
    }

    public static void drawItem(ItemStack itemStack, int x, int y, boolean overlay) {
        drawItem(itemStack, x, y, 1, overlay);
    }

    public static void updateScreenCenter() {

        Vector3f pos = new Vector3f(0, 0, 1);

        if (mc.options.getBobView().getValue()) {
            if (!Modules.get().isActive(NoBob.class)) {
                MatrixStack bobViewMatrices = new MatrixStack();

                bobView(bobViewMatrices);
                bobViewMatrices.peek().getPositionMatrix().invert();

                pos.mul(bobViewMatrices.peek().getPositionMatrix().get3x3(new Matrix3f()));
            }
        }

        center = new Vec3d(pos.x, -pos.y, pos.z).rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch())).rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw())).add(mc.gameRenderer.getCamera().getPos());
    }

    private static void bobView(MatrixStack matrices) {
        Entity cameraEntity = mc.getCameraEntity();

        if (cameraEntity instanceof PlayerEntity playerEntity) {
            float f = mc.getTickDelta();
            float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
            float h = -(playerEntity.horizontalSpeed + g * f);
            float i = MathHelper.lerp(f, playerEntity.prevStrideDistance, playerEntity.strideDistance);

            matrices.translate(-(MathHelper.sin(h * 3.1415927f) * i * 0.5), -(-Math.abs(MathHelper.cos(h * 3.1415927f) * i)), 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(h * 3.1415927f) * i * 3));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(h * 3.1415927f - 0.2f) * i) * 5));
        }
    }
}
