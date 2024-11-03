package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.Render3DEvent;
import dev.lvstrng.argon.event.listeners.Render3DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.ESPDisplayMode;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Iterator;

public final class PlayerESP extends Module implements Render3DListener {
    private final EnumSetting displayMode;
    private final IntSetting alphaSetting;
    private final IntSetting lineWidthSetting;
    private final BooleanSetting tracersEnabled;

    public PlayerESP() {
        super("Player ESP", "Renders players through walls", 0, Category.RENDER);
        this.displayMode = new EnumSetting("Mode", ESPDisplayMode.FIELD_432, ESPDisplayMode.class);
        this.alphaSetting = new IntSetting("Alpha", 0, 255, 100, 1);
        this.lineWidthSetting = new IntSetting("Line Width", 1, 10, 1, 1);
        this.tracersEnabled = new BooleanSetting("Tracers", false).setDescription("Draws a line from your player to others");
        this.addSettings(new Setting[]{displayMode, lineWidthSetting, tracersEnabled});
    }

    @Override
    public void onEnable() {
        eventBus.registerPriorityListener(Render3DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        eventBus.unregister(Render3DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        Iterator<AbstractClientPlayerEntity> playerIterator = mc.world.getPlayers().iterator();
        while (playerIterator.hasNext()) {
            PlayerEntity playerEntity = playerIterator.next();
            if (playerEntity != mc.player) {
                renderPlayerESP(playerEntity, event);
            }
        }
    }

    private void renderPlayerESP(PlayerEntity playerEntity, Render3DEvent event) {
        boolean shouldDisplay = displayMode.is(ESPDisplayMode.FIELD_432);

        if (shouldDisplay) {
            Camera camera = mc.gameRenderer.getCamera();
            if (camera != null) {
                MatrixStack matrixStack = event.matrices;
                matrixStack.push();

                Vec3d cameraPos = camera.getPos();
                matrixStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                double lerpX = MathHelper.lerp(mc.getTickDelta(), playerEntity.prevX, playerEntity.getX());
                double lerpY = MathHelper.lerp(mc.getTickDelta(), playerEntity.prevY, playerEntity.getY());
                double lerpZ = MathHelper.lerp(mc.getTickDelta(), playerEntity.prevZ, playerEntity.getZ());

                renderBoundingBox(matrixStack, lerpX, lerpY, lerpZ, playerEntity);

                if (tracersEnabled.getValue()) {
                    renderTracerLine(matrixStack, playerEntity);
                }

                matrixStack.pop();
            }
        }
    }

    private void renderBoundingBox(MatrixStack matrixStack, double x, double y, double z, PlayerEntity playerEntity) {
        float width = playerEntity.getWidth() / 2.0f;
        float height = playerEntity.getHeight();
        Color color = calculateColor(alphaSetting.getValueInt());

        // RenderUtil.drawBoundingBox(matrixStack, (float) x - width, (float) y, (float) z - width,
        //         (float) x + width, (float) y + height, (float) z + width, color);
    }

    private void renderTracerLine(MatrixStack matrixStack, PlayerEntity playerEntity) {
        Camera camera = mc.gameRenderer.getCamera();
        Vec3d playerPos = playerEntity.getPos().subtract(camera.getPos());

        Color tracerColor = new Color(255, 255, 255, alphaSetting.getValueInt());
//        RenderUtil.drawLine(matrixStack, camera.getPos(), playerPos, tracerColor, lineWidthSetting.getValueInt());
    }

    private Color calculateColor(int alpha) {
        if (ClickGui.rainbowSetting.getValue()) {
            return ColorUtil.method520(1, alpha);
        }
        int red = ClickGui.colorRed.getValueInt();
        int green = ClickGui.colorGreen.getValueInt();
        int blue = ClickGui.colorBlue.getValueInt();
        return new Color(red, green, blue, alpha);
    }
}