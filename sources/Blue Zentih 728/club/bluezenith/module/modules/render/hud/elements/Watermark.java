package club.bluezenith.module.modules.render.hud.elements;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.listeners.PlaytimeMeter;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Calendar;

import static club.bluezenith.module.modules.render.hud.HUD.elements;
import static club.bluezenith.util.MinecraftInstance.mc;
import static java.lang.Float.MIN_VALUE;
import static java.lang.String.format;
import static org.lwjgl.opengl.GL11.GL_LINE_WIDTH;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Watermark implements Draggable {
    private float x = MIN_VALUE, y = MIN_VALUE, width, height = mc.fontRendererObj.FONT_HEIGHT;

    private static final int backgroundShadowColor = new Color(0, 0, 0).getRGB();

    private FontRenderer fontRenderer;

    @Override
    public boolean shouldBeRendered() {
        return !BlueZenith.isVirtueTheme && HUD.module.getState();
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return checkMouseBounds(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void draw(Render2DEvent event) {
        fontRenderer = HUD.module.font.get();

        final boolean setXY = x == MIN_VALUE || y == MIN_VALUE;
        final String text = (StringUtils.isBlank(HUD.module.hudName.get()) ? BlueZenith.fancyName : HUD.module.hudName.get());

        switch (HUD.module.watermarkMode.get()) {
            case "Simple":
              drawSimpleWatermark(text, setXY);
            break;

            case "Generic":
                drawModernWatermark(text, setXY);
            break;
        }
    }

    private void drawModernWatermark(String clientName, boolean setXY) {
        if(elements.getOptionState("Time"))
            clientName += " | " + getTimeString();
        if(HUD.module.genericWatermarkOptions.getOptionState("FPS"))
            clientName += " | " + Minecraft.getDebugFPS() + " FPS";
        if(HUD.module.genericWatermarkOptions.getOptionState("Server info")) { //replace with getOptionState: serverName
            String serverIP = PacketUtil.data == null ? "Singleplayer" : PacketUtil.data.serverIP;

            if(PacketUtil.data != null) {
                serverIP = serverIP.split(":")[0];
                serverIP = serverIP.substring(0, serverIP.length() - 1);
            }

            clientName += " | " + serverIP;
        }
        if(HUD.module.genericWatermarkOptions.getOptionState("Username"))
            clientName += " | " + mc.session.getUsername();
        if(HUD.module.genericWatermarkOptions.getOptionState("Playtime")) {
            PlaytimeMeter playtimeMeter = BlueZenith.getBlueZenith().getPlaytimeMeter();

            if(playtimeMeter == null) {
                BlueZenith.getBlueZenith().setPlaytimeMeter(playtimeMeter = new PlaytimeMeter());
                BlueZenith.getBlueZenith().register(playtimeMeter);
            }

            if(playtimeMeter.timePlayedString != null)
              clientName += " | " + playtimeMeter.timePlayedString;
        }

        final float x2 = x + fontRenderer.getStringWidthF(clientName),
                    y2 = y + (fontRenderer == mc.fontRendererObj ? fontRenderer.FONT_HEIGHT : ((TFontRenderer)fontRenderer).getHeight(clientName));


        RenderUtil.rect(x - 2, y - 2, x2 + 2, y2 + 2, new Color(0, 0, 0, 150));

        final float[] first = ColorUtil.get(HUD.module.getColor(0)),
                      second = ColorUtil.get(HUD.module.getColor(5));

        final int shadeModel = GL11.glGetInteger(GL11.GL_SHADE_MODEL);

        {
            float x = this.x - 2,
                    y = this.y - 2;

            float lineWidth = GL11.glGetFloat(GL_LINE_WIDTH);

            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glLineWidth(1);
            RenderUtil.start2D(GL11.GL_LINE_STRIP);
            GlStateManager.color(second[0], second[1], second[2], second[3]);
            glVertex2d(x2 + 2, y);
            GlStateManager.color(first[0], first[1], first[2], first[3]);
            glVertex2d(x, y);
            GlStateManager.color(first[0], first[1], first[2], first[3]);
            glVertex2d(x, y);
            GlStateManager.color(second[0], second[1], second[2], second[3]);
            glVertex2d(x2 + 2, y);
            RenderUtil.end2D();
            GL11.glShadeModel(shadeModel);
            GL11.glLineWidth(lineWidth);
        }

        final float fontX = setXY ? x = 5 : x,
                    fontY = setXY ? y = 5 : y;

        final boolean mcFont = fontRenderer == mc.fontRendererObj;

        fontRenderer.drawString(clientName,
                        mcFont ? fontX + 1 : fontX,
                        mcFont ? fontY + 1 : fontY,
                        -1,
                        false
        );

        height = y2;
        width = x2;
    }

    private void drawSimpleWatermark(String clientName, boolean setXY) {

        if(!HUD.module.colorMode.is("Custom")) {
           fontRenderer.setGradient((index) -> new Color(HUD.module.getColor(index)))
                    .drawString(clientName,
                    setXY ? x = 5 : x,
                    setXY ? y = 5 : y,
                            new Color(20, 20, 20).getRGB(),
                    true
            );
        } else {
            fontRenderer.drawString(
                    clientName,
                    setXY ? x = 5 : x,
                    setXY ? y = 5 : y,
                    HUD.module.getColor(1),
                    true
            );
        }

        if(elements.getOptionState("Time")) {
            final Calendar moment = Calendar.getInstance();
            String time = " §7(" + getTimeString() + ")";

            final float width = fontRenderer.getStringWidthF(clientName);
            fontRenderer.drawString(time, x + width, y, Color.GRAY.getRGB(), true);

            clientName += time;
        }

        width = fontRenderer.getStringWidthF(clientName);
    }

    private String getTimeString() {
        final Calendar calendar = Calendar.getInstance();
        return format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" + format("%02d", calendar.get(Calendar.MINUTE));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public String getIdentifier() {
        return "Watermark";
    }

    @Override
    public void resetPosition() {
        x = y = MIN_VALUE;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
