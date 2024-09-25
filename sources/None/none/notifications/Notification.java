/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package none.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import none.Client;
import none.event.events.EventChat;
import none.fontRenderer.xdolf.Fonts;
import none.module.modules.render.ClientColor;
import none.module.modules.render.NotificationHUD;
import none.utils.render.TTFFontRenderer;

import java.awt.*;

public class Notification {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private NotificationType type;
    private String title;
    private String messsage;
    private long start;

    private long fadedIn;
    private long fadeOut;
    public long end;
    
    public int x = 0;
    public int y = 0;

    public Notification(NotificationType type, String title, String messsage, int length) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        
        if (NotificationHUD.NotificationType.getSelected().equalsIgnoreCase("Instant")) {
        	if (type != NotificationType.SP) {
        		length = 1;
        	}
        	fadedIn = 200 * length;
        	fadeOut = fadedIn;
        }else {
        	fadedIn = 200 * length;
        	fadeOut = fadedIn + 500 * length;
        }
        end = fadeOut + fadedIn;
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public NotificationType getType() {
		return type;
	}

    public void show() {
        start = System.currentTimeMillis();
    }
    
    public void showInstant() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    public long getTime() {
        return System.currentTimeMillis() - start;
    }

    public void render() {
    	if (type == NotificationType.SP) {
    		renderSP();
    		return;
    	}
        TTFFontRenderer fontRenderer = Client.fm.getFont("JIGR 19");
        
        ScaledResolution res = new ScaledResolution(mc);
        double offset;
        int width = (int) fontRenderer.getStringWidth(title + messsage) + 10;
        int height = 17;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        int color = new Color(0, 0, 0, 220).getRGB();
        int color1;
        if (type == NotificationType.INFO)
            color1 = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
        else if (type == NotificationType.WARNING)
            color1 = new Color(204, 193, 0).getRGB();
        else {
            color1 = new Color(204, 0, 18).getRGB();
            int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
            color = new Color(i, 0, 0, 220).getRGB();
        }

        drawRect(res.getScaledWidth() - offset, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 5 - height + y, res.getScaledWidth(), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - fontRenderer.getHeight(messsage) + y, color);
        drawRect(res.getScaledWidth() - offset, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 5 - height + y, res.getScaledWidth() - offset + 4, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - fontRenderer.getHeight(messsage) + y, color1);

        fontRenderer.drawString(title, (int) (res.getScaledWidth() - offset + 8), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 2 - height + y, color1);
        fontRenderer.drawString(messsage, (int) (res.getScaledWidth() - offset + fontRenderer.getStringWidth(title) + 8), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - height - 2 + y, -1);
    }
    
    public void renderSP() {
//    	if (type != NotificationType.SP) return;
        TTFFontRenderer fontRenderer = Client.fm.getFont("JIGR 19");
        
        ScaledResolution res = new ScaledResolution(mc);
        double offset;
        int width = (int) fontRenderer.getStringWidth(title + messsage) + 10;
        int height = 17;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        int color = new Color(0, 0, 0, 220).getRGB();
        int color1;
        	color1 = Color.BLUE.getRGB();

        drawRect(res.getScaledWidth() - offset, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 5 - height, res.getScaledWidth(), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - fontRenderer.getHeight(messsage), color);
        drawRect(res.getScaledWidth() - offset, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 5 - height, res.getScaledWidth() - offset + 4, (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - fontRenderer.getHeight(messsage), color1);

        fontRenderer.drawString(title, (int) (res.getScaledWidth() - offset + 8), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - 2 - height, color1);
        fontRenderer.drawString(messsage, (int) (res.getScaledWidth() - offset + fontRenderer.getStringWidth(title) + 8), (type == NotificationType.SP ? res.getScaledHeight() - 60 : res.getScaledHeight() - 30) - height - 2, -1);
    }
}
