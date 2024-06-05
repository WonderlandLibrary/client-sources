package gq.vapu.czfclient.Util;

import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.Render.Colors;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class UIMenu {
    public float x;
    private final MouseInputHandler handler = new MouseInputHandler(0);
    private int width;
    private int height;
    private final ArrayList slots;

    public UIMenu(ArrayList slots) {
        this.slots = slots;
        this.x = 0.0F;
        this.width = 200;
    }

    public void draw(int mouseX, int mouseY) {
        this.width = 170;
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        CFontRenderer font = FontLoaders.GoogleSans18;
        this.height = res.getScaledHeight();
        byte tab_height = 20;
        int yStart = res.getScaledHeight() / 2 - this.slots.size() / 2 * tab_height - 45;
        float newX = (float) (this.isHovering(mouseX, mouseY) ? 0 : -this.width);
        this.x = (float) RenderUtil.getAnimationState(this.x, newX, ((double) (this.isHovering(mouseX, mouseY) ? (100.0F - this.x + 1.0F) * 10.0F : (Math.abs(this.x) + 1.0F) * 30.0F)) * 0.4D);
        RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/bg.png"), (int) (x - 30), 0, 230, res.getScaledHeight());

        //RenderUtil.drawImage(new ResourceLocation("ClientRes/aw.png"), res.getScaledWidth() - 70, res.getScaledHeight() - 75, 60, 68);

        for (Iterator var9 = this.slots.iterator(); var9.hasNext(); yStart += 45) {
            UIMenuSlot slot = (UIMenuSlot) var9.next();
            boolean hover = this.isHovering(mouseX, mouseY) && mouseY >= yStart - 10 && mouseY < yStart + tab_height;
            slot.animationX = RenderUtil.getAnimationState(slot.animationX, hover ? 1f : 0, (25.0D * slot.animationX + 1.0D) * 0.4d);

            if (slot.text.contains("Singleplayer")) {
                RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/single.png"), (int) (this.x + 30), yStart - 14, 32, 32);
            } else if (slot.text.contains("Multiplayer")) {
                RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/multi.png"), (int) (this.x + 30), yStart - 14, 32, 32);
            } else if (slot.text.contains("AltManager")) {
                RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/alt.png"), (int) (this.x + 30), yStart - 14, 32, 32);
            } else if (slot.text.contains("Options")) {
                RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/option.png"), (int) (this.x + 30), yStart - 14, 32, 32);
            } else if (slot.text.contains("Quit")) {
                RenderUtil.drawImage(new ResourceLocation("ClientRes/mainMenu/quit.png"), (int) (this.x + 30), yStart - 14, 32, 32);
            }

            if (hover) {
                font.drawString(slot.text, this.x + 65, (float) yStart - 5, Colors.GREY.c);
            } else {
                font.drawString(slot.text, this.x + 65, (float) yStart - 5, new Color(205, 205, 205).getRGB());
            }
            if (hover && this.handler.canExcecute()) {
                slot.run();
            }
        }

    }

    public boolean isHovering(int mouseX, int mouseY) {
        return (float) mouseX <= this.x + (float) this.width + 12.0F;
        //return true;
    }
}

