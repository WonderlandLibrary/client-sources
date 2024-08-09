package dev.darkmoon.client.utility.misc;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ImageButton {
    protected int height;
    protected String description;
    protected int width;
    protected Minecraft mc;
    protected ResourceLocation image;
    protected int target;
    protected int x;
    protected float ani = 0.0f;
    protected int y;
    public static boolean hoverShop;

    public ImageButton(ResourceLocation resourceLocation, int x, int y, int width, int height, String description, int target) {
        this.image = resourceLocation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.description = description;
        this.target = target;
        this.mc = Minecraft.getMinecraft();
    }

    protected void hoverAnimation(int mouseX, int mouseY) {
        if (this.isHovered(mouseX, mouseY)) {
            if (this.target == 228) {
                hoverShop = true;
            }
            if (this.ani < 5.0f) {
                this.ani = (float)((double)this.ani + 0.3 * Minecraft.frameTime * 0.1);
            }
        } else if (this.ani > 0.0f) {
            this.ani = (float)((double)this.ani - 0.2 * Minecraft.frameTime * 0.1);
            hoverShop = false;
        }
    }

    public void onClick(int mouseX, int mouseY) {
        if (this.isHovered(mouseX, mouseY) && Mouse.isButtonDown(0)) {
            GuiChest chest;
            if (this.target == 16) {
                this.mc.displayGuiScreen(new GuiOptions(null, this.mc.gameSettings));
            }
            if (this.target == 15) {
                this.mc.displayGuiScreen(new GuiLanguage(null, this.mc.gameSettings, this.mc.getLanguageManager()));
            }
            if (this.target == 14) {
                this.mc.shutdown();
            }
            if (this.target == 12) {
                showURL("https://vk.com/darkmoonclient");
            }
        }
    }
    public static void showURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void draw(int mouseX, int mouseY, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.disableBlend();
        this.hoverAnimation(mouseX, mouseY);
        if (this.ani > 0.0f) {
            RenderUtility.drawImage(this.image, (float)this.x - this.ani, (float)this.y - this.ani, (float)(this.width + 4) + this.ani * 2.0f, (float)(this.height + 4) + this.ani * 2.0f, Color.BLACK);
            RenderUtility.drawImage(this.image, (float)this.x - this.ani, (float)this.y - this.ani, (float)this.width + this.ani * 2.0f, (float)this.height + this.ani * 2.0f, this.isHovered(mouseX, mouseY) ? new Color(156, 156, 156, 255) : Color.WHITE);
            if (this.isHovered(mouseX, mouseY)) {
                Fonts.mntsb18.drawStringWithShadow(this.description, (float)this.x + (float)this.width / 2.0f + (float)Fonts.mntsb18.getStringWidth(this.description) / 2.0f - (float)(this.target == 228 ? 35 : 6), this.y + this.height - 17, new Color(255, 255, 255, 255).getRGB());
            }
        } else {
            RenderUtility.drawImage(this.image, this.x, this.y, this.width + 3, this.height + 3, Color.BLACK);
            RenderUtility.drawImage(this.image, this.x, this.y, this.width, this.height, color);
        }
        GlStateManager.popMatrix();
    }

    protected boolean isHovered(int mouseX, int mouseY) {
        return RenderUtility.isHovered(this.x, this.y, this.x + this.width, this.y + this.height, mouseX, mouseY);
    }
}

