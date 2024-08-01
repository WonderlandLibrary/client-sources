package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

@ModuleMetaData(name = "Compass", description = "Draws a compass on your screen", category = ModuleCategoryEnum.RENDER)
public final class CompassModule extends AbstractModule {
    private final NumberSetting<Integer> compassWidth = new NumberSetting<>("Width", 250, 100, 500,1);
    private final NumberSetting<Integer> ocpacity = new NumberSetting<>("Opacity", 180, 0, 255,1);

    public CompassModule() {
        this.registerSettings(compassWidth, ocpacity);
    }

    @EventHandler
    private final Listener<OverlayEvent> render3DEventListener = e -> {
        final double yaw = Math.abs(mc.thePlayer.rotationYaw) % 360;

        final Color color = new Color(8, 8, 8, 0);

        final int width = compassWidth.getValue();
        final int height = 30;
        final int x = e.getScaledResolution().getScaledWidth() / 2 - width / 2;
        final int y = 6;

        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.prepareScissorBox(x, y, (x + width), (y + height));

        RenderUtil.drawRect(x, y, width, height, color.getRGB());

        final int centerX = x + width / 2;

        for (int i = 0; i < 180; i++) {
            final int offset = 2;
            final int yOffset = 5;

            for (int j = 0; j < 2; j++) {
                final boolean shouldPositivelyOffset = j == 0;

                final int yawOffset = (int) (yaw + (shouldPositivelyOffset ? i : -i)) % 360;

                if (yawOffset % 5 == 0) {
                    final int drawX = centerX - (shouldPositivelyOffset ? (offset * i) : -(offset * i));

                    int lineLength = 3;

                    if (yawOffset % 10 == 0) {
                        lineLength = 5;
                    }

                    if (yawOffset % 45 == 0) {
                        lineLength = 10;
                    }

                    int lineColor = new Color(255, 255, 255,ocpacity.getValue()).getRGB();

                    if (yawOffset % 5 == 0 && yawOffset % 10 != 0 && yawOffset % 45 != 0) {
                        lineColor = new Color(130, 130, 130,ocpacity.getValue()).getRGB();
                    }


                    final int drawY = y + height / 2 - lineLength / 2 - yOffset;

                    RenderUtil.drawRect(drawX, drawY, 1, lineLength, lineColor);
                }

                if (yawOffset % 45 == 0) {
                    String point = "";

                    switch (yawOffset) {
                        case 0:
                            point = "N";
                            break;
                        case 45:
                            point = "NE";
                            break;
                        case 90:
                            point = "E";
                            break;
                        case 135:
                            point = "SE";
                            break;
                        case 180:
                            point = "S";
                            break;
                        case 225:
                            point = "SW";
                            break;
                        case 270:
                            point = "W";
                            break;
                        case 315:
                            point = "NW";
                            break;
                    }

                    final int drawX = centerX - (shouldPositivelyOffset ? (offset * i) : -(offset * i)) - mc.fontRendererObj.getStringWidth(point) / 2 + 1;
                    final int drawY = y + height / 2 + 9 - yOffset;

                    mc.fontRendererObj.drawStringWithShadow(point, drawX, drawY, -1);
                }
            }

        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    };
}
