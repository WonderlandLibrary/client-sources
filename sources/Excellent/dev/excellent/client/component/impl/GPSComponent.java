package dev.excellent.client.component.impl;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.command.impl.GPSCommand;
import dev.excellent.client.component.Component;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.rotation.RotationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class GPSComponent extends Component {
    public static final Singleton<GPSComponent> singleton = Singleton.create(() -> excellent.getComponentManager().get(GPSComponent.class));

    private final Listener<Render2DEvent> onRender = event -> {
        val active = GPSCommand.active;
        val ix = GPSCommand.x + 0.5;
        val iz = GPSCommand.z + 0.5;
        val radius = 40;
        if (active) {

            val xPos = scaled().x / 2F;
            val yPos = scaled().y - 90;


            Vector3d vector3d = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double xWay = ((ix - vector3d.x) * 0.2D);
            final double zWay = ((iz - vector3d.z) * 0.2D);
            final double cos = Math.cos(mc.gameRenderer.getActiveRenderInfo().getYaw() * (Math.PI * 2F / 360F));
            final double sin = Math.sin(mc.gameRenderer.getActiveRenderInfo().getYaw() * (Math.PI * 2F / 360F));
            final double rotationY = -(zWay * cos - xWay * sin);
            final double rotationX = -(xWay * cos + zWay * sin);
            final double size = ((radius * 2F) + radius);
            final double xOffset = xPos - radius - radius / 2F;
            final double yOffset = yPos - radius - radius / 2F;
            final double angle = (Math.atan2(rotationY, rotationX) * 180F / Math.PI);
            double x = ((size / 2F) * Math.cos(Math.toRadians(angle))) + xOffset + size / 2F;
            double y = ((size / 2F) * Math.sin(Math.toRadians(angle))) + yOffset + size / 2F;
            int color = getTheme().getClientColor(0);
            int distance = (int) RotationUtil.getDistance(
                    new org.joml.Vector3d(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()),
                    new org.joml.Vector3d(ix, mc.player.getPosY(), iz)
            );

            Font font = Fonts.INTER_BOLD.get(12);
            String gpsText = "GPS";
            String distText = distance + "м";
            float textHeight = font.getHeight();
            float textWidth = Math.max(font.getWidth(gpsText), font.getWidth(distText));
            float margin = 2;


            float offsetW = textWidth + margin * 3;
            float offsetH = (textHeight * 2) + margin * 3;

            glPushMatrix();
            y = Mathf.clamp(yPos - offsetH, yPos + offsetH, y);
            x = Mathf.clamp(xPos - offsetW, xPos + offsetW, x);
            glTranslated(x, y, 0D);
            glRotated(angle, 0D, 0D, 1D);
            glRotatef(90F, 0F, 0F, 1F);

            drawTriangle(event.getMatrix(), ColorUtil.replAlpha(color, (int) Mathf.limit(distance, 5, 30, 250, 180)));
            glPopMatrix();

            RenderUtil.renderClientRect(event.getMatrix(), (float) (xPos - textWidth / 2F) - margin, (float) (yPos - textHeight) - margin, textWidth + (margin * 2), (textHeight * 2) + (margin * 2), false, 0);
            font.drawCenterShadow(event.getMatrix(), distText, xPos, yPos - (font.getHeight()), -1);
            font.drawCenterShadow(event.getMatrix(), gpsText, xPos, yPos, -1);

        }
    };

    private void drawTriangle(MatrixStack matrix, int color) {
        boolean needBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
        if (needBlend)
            GL11.glEnable(GL11.GL_BLEND);
        Font font = Fonts.ARROW.get(16);
        String ch = "A";

        RectUtil.drawShadowSegmentsExtract(matrix, 0, font.getHeight() / 2F, 0, font.getHeight() / 2F, 0, font.getWidth(ch), color, color, color, color, true, true);
        RectUtil.setupRenderRect(false, true);
        font.drawCenter(matrix, ch, 0, 0.5, ColorUtil.multDark(color, 0.25F));
        font.drawCenter(matrix, ch, 0, 0, color);
        RectUtil.endRenderRect(true);
        if (needBlend)
            GL11.glDisable(GL11.GL_BLEND);
    }

}
