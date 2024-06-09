// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui.novolineOld.themesScreen;

import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.visual.Blur;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;

public class ThemeScreen extends GuiScreen
{
    public static boolean themeLoaded;
    public static boolean themeAqua;
    public static boolean themeHero;
    public static boolean themeSigma;
    public static boolean themeJello;
    public static boolean themeXave;
    public static boolean themeRise;
    public static boolean themeRise6;
    public static boolean themeAqua2;
    public static boolean themeTenacity;
    public static boolean legitTheme;
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int posX = scaledResolution.getScaledWidth() / 4;
        final int posY = 80;
        final int width = scaledResolution.getScaledWidth() / 2 + 10;
        final int height = scaledResolution.getScaledHeight() / 2 + 60;
        final int cornerRadius = 3;
        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadius, new Color(0, 0, 0, 100)), false);
        RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadius, new Color(0, 0, 0, 100));
        RenderUtil.drawRoundedRect2Alpha(posX + 3, posY + 3, 70.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 3, posY + 45, 60.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 80, posY + 45, 87.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 285, posY + 45, 117.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 180, posY + 45, 97.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 80, posY + 3, 87.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 178, posY + 3, 80.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 268, posY + 3, 65.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        RenderUtil.drawRoundedRect2Alpha(posX + 340, posY + 3, 80.0, 40.0, 3.0, new Color(0, 0, 0, 60));
        Aqua.INSTANCE.roboto.drawString("Hero", (float)(posX + 3), (float)(posY + 3), Color.GREEN.getRGB());
        Aqua.INSTANCE.roboto.drawString("Sigma", (float)(posX + 80), (float)(posY + 3), new Color(4, 154, 248, 255).getRGB());
        Aqua.INSTANCE.roboto.drawString("Aqua", (float)(posX + 180), (float)(posY + 3), new Color(0, 255, 245, 255).getRGB());
        Aqua.INSTANCE.roboto.drawString("Jello", (float)(posX + 270), (float)(posY + 3), new Color(163, 163, 163, 255).getRGB());
        Aqua.INSTANCE.roboto.drawString("Xave", (float)(posX + 345), (float)(posY + 3), Color.blue.getRGB());
        Aqua.INSTANCE.roboto.drawString("Rise", (float)(posX + 3), (float)(posY + 45), Color.cyan.getRGB());
        Aqua.INSTANCE.roboto.drawString("Rise 6", (float)(posX + 80), (float)(posY + 45), Color.cyan.getRGB());
        Aqua.INSTANCE.roboto.drawString("Aqua 2", (float)(posX + 180), (float)(posY + 45), Color.blue.getRGB());
        Aqua.INSTANCE.roboto.drawString("Tenacity", (float)(posX + 285), (float)(posY + 45), Color.green.getRGB());
        if (this.mouseOver(mouseX, mouseY, posX + 3, posY + 3, 70, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("Hero v0.9.18");
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = true;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 80, posY + 3, 87, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeSigma = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 180, posY + 3, 80, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 268, posY + 3, 65, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = true;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 340, posY + 3, 80, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = true;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 3, posY + 45, 60, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = true;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 80, posY + 45, 87, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = true;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 180, posY + 45, 97, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = true;
            ThemeScreen.themeTenacity = false;
        }
        if (this.mouseOver(mouseX, mouseY, posX + 285, posY + 45, 117, 40) && Mouse.isButtonDown(0)) {
            Display.setTitle("" + Aqua.name + " b" + Aqua.build + " by " + Aqua.dev);
            ThemeScreen.themeLoaded = true;
            ThemeScreen.themeHero = false;
            ThemeScreen.themeSigma = false;
            ThemeScreen.themeJello = false;
            ThemeScreen.themeXave = false;
            ThemeScreen.themeRise = false;
            ThemeScreen.themeRise6 = false;
            ThemeScreen.themeAqua2 = false;
            ThemeScreen.themeTenacity = true;
        }
    }
    
    @Override
    public void initGui() {
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
}
