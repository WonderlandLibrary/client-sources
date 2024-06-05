package net.minecraft.client.gui;

import tk.rektsky.Guis.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.*;
import net.minecraft.util.*;
import java.awt.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    public static Boolean rendered;
    private static UnicodeFontRenderer fr;
    private static UnicodeFontRenderer frJoin;
    private static UnicodeFontRenderer frAuthor;
    private static UnicodeFontRenderer frVersion;
    private static UnicodeFontRenderer frDiscord;
    private static UnicodeFontRenderer frChannel;
    private long firstRenderTime;
    
    public GuiMainMenu() {
        this.firstRenderTime = 0L;
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 57) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseX > this.width - 42 && mouseX < this.width - 10 && mouseY > this.height - 42 && mouseY < this.height - 10 && mouseButton == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
    }
    
    @Override
    public void initGui() {
        this.firstRenderTime = Minecraft.getSystemTime();
        GuiMainMenu.rendered = true;
    }
    
    public static void Translate3DLayerEffect(final int width, final int height, final int mouseX, final int mouseY, final int layer) {
        GlStateManager.translate((-mouseX + width / 2.0f) / (layer * 20.0f), (-mouseY + height / 2.0f) / (layer * 20.0f), 0.0f);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final long renderedTime = Minecraft.getSystemTime() - this.firstRenderTime;
        this.mc.mcMusicTicker.currentMusic = null;
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.3f, 1.3f, 0.0f);
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 5);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("rektsky/images/background.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GlStateManager.popMatrix();
        if (renderedTime <= 4000L) {
            GlStateManager.translate((1.0 - AnimationUtil.easeOutElastic(renderedTime / 4000.0)) * -150.0, 0.0, 0.0);
        }
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 5);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("rektsky/icons/icon.png"));
        Gui.drawModalRectWithCustomSizedTexture(this.y(20.0f), this.y(10.0f), 0.0f, 0.0f, this.y(50.0f), this.y(50.0f), (float)this.y(50.0f), (float)this.y(50.0f));
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 4);
        GuiMainMenu.frVersion.drawString("B2", (float)this.y(65.0f), (float)this.y(61.0f), 16777215);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 3);
        GuiMainMenu.frChannel.drawString("Public Beta", this.y(65.0f) + GuiMainMenu.frChannel.getStringWidth("Public Beta") / 2.0f, (float)this.y(63.0f), 16777215);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 3);
        final UnicodeFontRenderer frAuthor = GuiMainMenu.frAuthor;
        final String text = "Made by hackage & fan87";
        final float x = 0.0f;
        final int height = this.height;
        GuiMainMenu.frAuthor.getClass();
        frAuthor.drawString(text, x, (float)(height - 9 - 2), 16777215);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 3);
        final UnicodeFontRenderer frDiscord = GuiMainMenu.frDiscord;
        final String string = "Welcome back, [" + Client.role + "] " + Client.userName;
        final float x2 = 5.0f;
        final int height2 = this.height;
        GuiMainMenu.frJoin.getClass();
        final int n = height2 - 9;
        GuiMainMenu.frDiscord.getClass();
        frDiscord.drawString(string, x2, (float)(n - 9 - 16), 16777215);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("rektsky/images/settings.png"));
        Gui.drawModalRectWithCustomSizedTexture(this.width - 42, this.height - 42, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Translate3DLayerEffect(this.width, this.height, mouseX, mouseY, 2);
        GlStateManager.translate(this.width / 2.0f, (float)(this.height - 40), 0.0f);
        float f = 1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.15f);
        f = f * 100.0f / (GuiMainMenu.frJoin.getStringWidth("Press [Space] to start the game") + 32);
        GlStateManager.scale(f, f, 0.0f);
        GuiMainMenu.frJoin.drawString("Press [Space] to start the game", (float)Math.round(-GuiMainMenu.frJoin.getStringWidth("Press [Space] to start the game") / 2.0f), 0.0f, 16777215);
        GlStateManager.translate(-this.width / 2.0f, (float)(-this.height + 40), 0.0f);
        GlStateManager.scale(f - 1.0f, f - 1.0f, 0.0f);
        GlStateManager.popMatrix();
        if (renderedTime <= 4000L) {
            GlStateManager.translate((1.0 - AnimationUtil.easeOutElastic(renderedTime / 4000.0)) * 150.0, 0.0, 0.0);
        }
        if (renderedTime <= 3000L) {
            final Color color = new Color(0.0f, 0.0f, 0.0f, 1.0f - renderedTime / 3000.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, renderedTime / 3000.0f);
            this.drawGradientRect(0, 0, this.width, this.height, color.getRGB(), color.getRGB());
        }
    }
    
    public int x(final float x) {
        return Math.round(this.width / 100.0f * x);
    }
    
    public int y(final float x) {
        return Math.round(this.height / 100.0f * x);
    }
    
    static {
        GuiMainMenu.rendered = false;
        GuiMainMenu.fr = Client.getFont();
        GuiMainMenu.frJoin = Client.getFontWithSize(40);
        GuiMainMenu.frAuthor = Client.getFontWithSize(18);
        GuiMainMenu.frVersion = Client.getFontWithSize(28);
        GuiMainMenu.frDiscord = GuiMainMenu.frVersion;
        GuiMainMenu.frChannel = GuiMainMenu.frAuthor;
    }
}
