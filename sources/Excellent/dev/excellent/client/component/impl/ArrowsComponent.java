package dev.excellent.client.component.impl;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.Component;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.luvbeeq.animation.Animation;
import dev.luvbeeq.animation.util.Easings;
import lombok.Setter;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.opengl.GL11;

@Setter
public class ArrowsComponent extends Component {
    private boolean render = false;
    private final Animation yawAnimation = new Animation();
    private final Animation moveAnimation = new Animation();
    private final Animation openAnimation = new Animation();

    private ResourceLocation arrow = new ResourceLocation(Excellent.getInst().getInfo().getNamespace(), "texture/arrow.png");

    private ResourceLocation upscaleTexture(ResourceLocation resLoc) {
        try {
            DynamicTexture dynamicTexture = new DynamicTexture(NativeImage.read(mc.getResourceManager().getResource(resLoc).getInputStream()));
            dynamicTexture.setBlurMipmap(true, false);
            resLoc = mc.getTextureManager().getDynamicTextureLocation(dynamicTexture.toString(), dynamicTexture);
        } catch (Exception ignored) {
        }
        return resLoc;
    }

    private final Listener<Render2DEvent> onRender2D = event -> {
        openAnimation.update();
        moveAnimation.update();
        yawAnimation.update();

        if (!render && openAnimation.getValue() == 0 && openAnimation.isFinished()) return;

        final float moveAnim = calculateMoveAnimation();

        openAnimation.run(render ? 1 : 0, 0.3, Easings.BACK_OUT, true);
        moveAnimation.run(render ? moveAnim : 0, 0.5, Easings.BACK_OUT, true);
        yawAnimation.run(mc.gameRenderer.getActiveRenderInfo().getYaw(), 0.3, Easings.BACK_OUT, true);

        final double cos = Math.cos(Math.toRadians(yawAnimation.getValue()));
        final double sin = Math.sin(Math.toRadians(yawAnimation.getValue()));
        final double radius = moveAnimation.getValue();
        final double xOffset = (scaled().x / 2F) - radius;
        final double yOffset = (scaled().y / 2F) - radius;

        int index = 0;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (!isValidPlayer(player)) continue;

            Vector3d vector3d = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double xWay = (((player.getPosX() + (player.getPosX() - player.lastTickPosX) * mc.getRenderPartialTicks()) - vector3d.x) * 0.01D);
            final double zWay = (((player.getPosZ() + (player.getPosZ() - player.lastTickPosZ) * mc.getRenderPartialTicks()) - vector3d.z) * 0.01D);
            final double rotationY = -(zWay * cos - xWay * sin);
            final double rotationX = -(xWay * cos + zWay * sin);
            final double angle = Math.toDegrees(Math.atan2(rotationY, rotationX));
            final double x = ((radius * Math.cos(Math.toRadians(angle))) + xOffset + radius);
            final double y = ((radius * Math.sin(Math.toRadians(angle))) + yOffset + radius);

            if (isValidRotation(rotationX, rotationY, radius)) {
                GL11.glPushMatrix();
                GL11.glTranslated(x, y, 0D);
                GL11.glRotated(angle, 0D, 0D, 1D);
                GL11.glRotatef(90F, 0F, 0F, 1F);
                int color = excellent.getFriendManager().isFriend(TextFormatting.getTextWithoutFormattingCodes(player.getName().getString())) ? ColorUtil.getColor(25, 227, 142) : getTheme().getClientColor(index);

                drawTriangle(event.getMatrix(), color);
                GL11.glPopMatrix();
            }
            index += 10;
        }
    };

    private float calculateMoveAnimation() {
        float set = 75;
        if (mc.currentScreen instanceof ContainerScreen<?> container) {
            set = Math.max(container.ySize, container.xSize) / 2F + 50;
        }
        float moveAnim = set;
        if (MoveUtil.isMoving()) {
            moveAnim += mc.player.isSneaking() ? 5 : 15;
        } else if (mc.player.isSneaking()) {
            moveAnim -= 10;
        }
        return moveAnim;
    }

    private boolean isValidPlayer(PlayerEntity player) {
        return player != mc.player && player.isAlive();
    }

    private boolean isValidRotation(double rotationX, double rotationY, double radius) {
        final double mrotY = -rotationY;
        final double mrotX = -rotationX;
        return MathHelper.sqrt(mrotX * mrotX + mrotY * mrotY) < radius;
    }

    private void drawTriangle(MatrixStack matrix, int color) {
        final float size = 12;
        RenderUtil.bindTexture(arrow);
        RectUtil.drawRect(matrix, -size, -size, size, size, color, color, color, color, true, true);
    }
}
