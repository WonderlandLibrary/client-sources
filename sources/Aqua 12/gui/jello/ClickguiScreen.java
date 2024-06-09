// 
// Decompiled by Procyon v0.5.36
// 

package gui.jello;

import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import intent.AquaDev.aqua.gui.ConfigScreen;
import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import java.util.Iterator;
import java.awt.Color;
import org.lwjgl.input.Mouse;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.modules.visual.Blur;
import net.minecraft.client.gui.Gui;
import java.awt.image.BufferedImage;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.client.gui.GuiButton2;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.Minecraft;
import javax.imageio.ImageIO;
import java.io.File;
import intent.AquaDev.aqua.Aqua;
import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import intent.AquaDev.aqua.utils.Translate;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import gui.jello.components.CategoryPaneOwn;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class ClickguiScreen extends GuiScreen
{
    public static boolean awaitingClose;
    private final List<CategoryPaneOwn> categoryPanes;
    private final GuiScreen parentScreen;
    private final Animate animate;
    public CategoryPaneOwn current;
    Translate translate;
    private ResourceLocation resourceLocation;
    
    public ClickguiScreen(final GuiScreen parentScreen) {
        this.categoryPanes = new ArrayList<CategoryPaneOwn>();
        this.animate = new Animate();
        this.current = null;
        this.parentScreen = null;
    }
    
    @Override
    public void initGui() {
        try {
            final File file = new File(System.getProperty("user.dir") + "/" + Aqua.name + "//pic/" + Aqua.setmgr.getSetting("GuiElementsMode").getCurrentMode() + ".png");
            final BufferedImage bi = ImageIO.read(file);
            this.resourceLocation = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation("name", new DynamicTexture(bi));
        }
        catch (Exception ex) {}
        this.translate = new Translate(0.0f, 0.0f);
        this.categoryPanes.clear();
        int x = 70;
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton2(2, 0, sr.getScaledHeight() - 55, 31, 200, "Ghost"));
        this.buttonList.add(new GuiButton2(911, 30, sr.getScaledHeight() - 55, 55, 200, "Ghost"));
        int count = 0;
        int y = 93;
        for (final Category category : Category.values()) {
            if (count == 4) {
                y += 159;
                x = 70;
            }
            final CategoryPaneOwn categoryPane = new CategoryPaneOwn(x, y, 98, 20, category, this);
            if (!Aqua.setmgr.getSetting("GUIAutoPosReset").isState()) {
                Aqua.INSTANCE.fileUtil.loadClickGuiJello(categoryPane);
            }
            this.categoryPanes.add(categoryPane);
            x += 105;
            ++count;
        }
        ClickguiScreen.awaitingClose = false;
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled() && !Aqua.moduleManager.getModuleByName("GuiElements").isToggled()) {
            Blur.drawBlurred(() -> Gui.drawRect2(0.0, 0.0, this.mc.displayWidth, this.mc.displayHeight, -1), false);
        }
        final ScaledResolution scaledRes = new ScaledResolution(this.mc);
        final float posX = (float)Aqua.setmgr.getSetting("GuiElementsPosX").getCurrentNumber();
        final float posY = (float)Aqua.setmgr.getSetting("GuiElementsPosY").getCurrentNumber();
        final float width1 = (float)Aqua.setmgr.getSetting("GuiElementsWidth").getCurrentNumber();
        final float height1 = (float)Aqua.setmgr.getSetting("GuiElementsHeight").getCurrentNumber();
        final float alpha1 = (float)Aqua.setmgr.getSetting("GuiElementsBackgroundAlpha").getCurrentNumber();
        if (Aqua.moduleManager.getModuleByName("GuiElements").isToggled()) {
            if (Aqua.setmgr.getSetting("GuiElementsCustomPic").isState()) {
                try {
                    RenderUtil.drawImage((int)(scaledRes.getScaledWidth() - this.animate.getValue() - posX), (int)(scaledRes.getScaledHeight() - posY), (int)width1, (int)height1, this.resourceLocation);
                }
                catch (Exception ex) {}
            }
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                final int color = Aqua.setmgr.getSetting("HUDColor").getColor();
                final Color colorAlpha = ColorUtils.getColorAlpha(color, (int)alpha1);
                if (Aqua.setmgr.getSetting("GuiElementsBackgroundColor").isState()) {
                    Gui.drawRect2(0.0, 0.0, this.mc.displayWidth, this.mc.displayHeight, colorAlpha.getRGB());
                }
                Blur.drawBlurred(() -> Gui.drawRect(0, 0, this.mc.displayWidth, this.mc.displayHeight, -1), false);
            }
            if (Aqua.setmgr.getSetting("GuiElementsCustomPic").isState()) {
                try {
                    RenderUtil.drawImage((int)(scaledRes.getScaledWidth() - this.animate.getValue() - posX), (int)(scaledRes.getScaledHeight() - posY), (int)width1, (int)height1, this.resourceLocation);
                }
                catch (Exception ex2) {}
            }
        }
        this.translate.interpolate((float)ClickguiScreen.width, (float)ClickguiScreen.height, 4.0);
        final double xmod = ClickguiScreen.width / 2.0f - this.translate.getX() / 2.0f;
        final double ymod = ClickguiScreen.height / 2.0f - this.translate.getY() / 2.0f;
        GlStateManager.translate(xmod, ymod, 0.0);
        GlStateManager.scale(this.translate.getX() / ClickguiScreen.width, this.translate.getY() / ClickguiScreen.height, 1.0f);
        if (ClickguiScreen.awaitingClose && (int)this.animate.getValue() == (int)this.animate.getMin()) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {}
        this.animate.setEase(Easing.BACK_IN_OUT).setMin(1.0f).setMax(10.0f).setSpeed(25.0f).setReversed(ClickguiScreen.awaitingClose).update();
        GlStateManager.pushMatrix();
        final float scale = this.animate.getValue() / 10.0f * 0.06f;
        if (this.parentScreen != null) {}
        for (final CategoryPaneOwn categoryPane : this.categoryPanes) {
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                final CategoryPaneOwn categoryPaneOwn;
                Shadow.drawGlow(() -> categoryPaneOwn.draw(categoryPaneOwn.getX(), categoryPaneOwn.getY(), mouseX, mouseY), false);
            }
            categoryPane.draw(categoryPane.getX(), categoryPane.getY(), mouseX, mouseY);
        }
        for (final CategoryPaneOwn categoryPane : this.categoryPanes) {
            if (Mouse.isButtonDown(0) && (this.mouseOver(mouseX, mouseY, categoryPane.getX(), categoryPane.getY(), categoryPane.getWidth(), categoryPane.getHeight()) || this.current == categoryPane) && (this.current == categoryPane || this.current == null)) {
                (this.current = categoryPane).setX(mouseX - categoryPane.getWidth() / 2);
                categoryPane.setY(mouseY - categoryPane.getHeight() / 2);
            }
        }
        if (!Mouse.isButtonDown(0)) {
            this.current = null;
        }
        GlStateManager.popMatrix();
        RenderUtil.drawImage(30, sr.getScaledHeight() - 57, 50, 50, new ResourceLocation("Aqua/gui/themes.png"));
        RenderUtil.drawImage(1, sr.getScaledHeight() - 49, 35, 35, new ResourceLocation("Aqua/gui/configs.png"));
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (final CategoryPaneOwn categoryPane : this.categoryPanes) {
            categoryPane.clickMouse(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        for (final CategoryPaneOwn categoryPane : this.categoryPanes) {
            categoryPane.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (final CategoryPaneOwn categoryPane : this.categoryPanes) {
            categoryPane.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        ClickguiScreen.awaitingClose = true;
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    public void onGuiClosed() {
        Aqua.INSTANCE.fileUtil.saveClickGuiJello(this);
        super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 2) {
            this.mc.displayGuiScreen(new ConfigScreen());
        }
        if (button.id == 911) {
            this.mc.displayGuiScreen(new ThemeScreen());
        }
    }
    
    public List<CategoryPaneOwn> getCategoryPanes() {
        return this.categoryPanes;
    }
    
    static {
        ClickguiScreen.awaitingClose = true;
    }
}
