package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

@ModuleMetaData(name = "Off Arrows", description = "Shows arrows that point to the direction of the player", category = ModuleCategoryEnum.RENDER)
public final class OffArrowsModule extends AbstractModule {
    private final NumberSetting<Integer> offset = new NumberSetting<>("Offset", 20, 0, 100, 1);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);
    private final ColorSetting borderColor = new ColorSetting("Border Color", Color.BLACK);
    private final NumberSetting<Integer> borderWidth = new NumberSetting<>("Border Width", 1, 0, 10, 1);

    public OffArrowsModule() {
        this.registerSettings(offset, color, borderColor, borderWidth);
    }

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = event -> {
        final EntityPlayer thePlayer = mc.thePlayer;

        Vec3 cameraPos = mc.thePlayer.getPositionEyes(1.0f);

        for (Entity entity : mc.theWorld.playerEntities) {
            if (entity == thePlayer) {
                continue;
            }

            Vec3 entityPos = entity.getPositionVector();
            Vec3 direction = entityPos.subtract(cameraPos).normalize();

            double yaw = Math.atan2(direction.zCoord, direction.xCoord) * (180.0 / Math.PI);

            drawTriangle((int) (event.getScaledResolution().getScaledWidth() / 2 + Math.cos(Math.toRadians(yaw)) * offset.getValue()), (int) (event.getScaledResolution().getScaledHeight() / 2 + Math.sin(Math.toRadians(yaw)) * offset.getValue()), 10, 10, (int) yaw);
        }
    };

    private void drawTriangle(final int x, final int y, final int width, final int height, final int angle) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.rotate(angle, 0, 0, 1);
        RenderUtil.drawRect(0, 0, width, height, this.color.getValue().getRGB());
        RenderUtil.drawRect(0, 0, width, borderWidth.getValue(), this.borderColor.getValue().getRGB());
        RenderUtil.drawRect(0, 0, borderWidth.getValue(), height, this.borderColor.getValue().getRGB());
        RenderUtil.drawRect(width - borderWidth.getValue(), 0, width, height, this.borderColor.getValue().getRGB());
        RenderUtil.drawRect(0, height - borderWidth.getValue(), width, height, this.borderColor.getValue().getRGB());
        GlStateManager.popMatrix();
    }
}
