// 
// Decompiled by Procyon v0.5.36
// 

package gui.clickgui.ownClickgui.components;

import net.minecraft.client.gui.GuiScreen;
import gui.clickgui.ownClickgui.settingScreen.SettingScreen;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.modules.Module;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.Aqua;
import java.awt.Color;
import intent.AquaDev.aqua.utils.MouseClicker;
import gui.clickgui.ownClickgui.ClickguiScreen;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CategoryPaneOwn extends Gui
{
    public static CategoryPaneOwn INSTANCE;
    private final Integer CATEGORY_PANE_TOP_COLOR;
    private final Integer CATEGORY_MODULE_COLOR;
    private final Integer INACTIVATED_TEXT_COLOR;
    Minecraft mc;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final Category category;
    private final ClickguiScreen novoline;
    private Integer ACTIVATED_TEXT_COLOR;
    private final MouseClicker checker;
    private int scrollAdd;
    private int currHeight;
    
    public CategoryPaneOwn(final int x, final int y, final int width, final int height, final Category category, final ClickguiScreen novoline) {
        this.CATEGORY_PANE_TOP_COLOR = new Color(0, 0, 0, 195).getRGB();
        this.CATEGORY_MODULE_COLOR = new Color(0, 0, 0, 180).getRGB();
        this.INACTIVATED_TEXT_COLOR = Color.decode("#FEFEFF").getRGB();
        this.mc = Minecraft.getMinecraft();
        this.checker = new MouseClicker();
        this.scrollAdd = 0;
        CategoryPaneOwn.INSTANCE = this;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        this.novoline = novoline;
    }
    
    public void draw(final int posX, final int posY, final int mouseX, final int mouseY) {
        this.ACTIVATED_TEXT_COLOR = Aqua.setmgr.getSetting("HUDColor").getColor();
        Shadow.drawGlow(() -> Aqua.INSTANCE.comfortaa3.drawString(this.category.name(), (float)(this.x + 14), (float)(this.y + 3), Color.white.getRGB()), false);
        Aqua.INSTANCE.comfortaa3.drawString(this.category.name(), (float)(this.x + 14), (float)(this.y + 3), Color.white.getRGB());
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered(this.category, true, Aqua.INSTANCE.comfortaa4);
        int allModHeight = 0;
        for (final Module module : moduleList) {
            if (module.getCategory() == this.category) {
                allModHeight += 15;
            }
        }
        final int maxPanelHeight = 225;
        final int finalAllModHeight = allModHeight;
        RenderUtil.drawRoundedRect2Alpha(this.x, this.y, this.width, finalAllModHeight + this.height, 4.0, new Color(0, 0, 0, 100));
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(this.x, this.y, this.width, finalAllModHeight + this.height, 4.0, Color.black.getRGB()), false);
        }
        GL11.glEnable(3089);
        RenderUtil.scissor(this.x, this.y + this.height, this.width, allModHeight + this.height + 5);
        allModHeight += this.height;
        final int settingsHeight = 0;
        for (final Module module2 : moduleList) {
            if (module2.getCategory() == this.category) {
                Aqua.INSTANCE.comfortaa4.drawCenteredString(module2.getName(), this.x + this.width / 2.0f, (float)(this.y + add), module2.isToggled() ? this.ACTIVATED_TEXT_COLOR : this.INACTIVATED_TEXT_COLOR);
                final int modX = this.x + 2;
                final int modY = this.y + add;
                final int modWidth = this.width - 4;
                final int modHeight = 15;
                add += 15;
            }
        }
        final int currHeight = MathHelper.clamp_int(add, 0, Math.max(allModHeight - this.height, maxPanelHeight));
        this.currHeight = currHeight;
        if (this.mouseOver(mouseX, mouseY, this.x, this.y, this.width, currHeight)) {
            final int mouseDelta = Aqua.INSTANCE.mouseWheelUtil.mouseDelta;
            this.scrollAdd += mouseDelta / 5;
            this.scrollAdd = MathHelper.clamp_int(this.scrollAdd, -settingsHeight + (currHeight - allModHeight), 0);
        }
        GL11.glDisable(3089);
        final int finalAdd = currHeight - 1;
        this.checker.release(0);
    }
    
    public void clickMouse(final int mouseX, final int mouseY, final int mouseButton) {
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered(this.category, true, Aqua.INSTANCE.comfortaa4);
        int allModHeight = 0;
        for (final Module module : moduleList) {
            if (module.getCategory() == this.category) {
                allModHeight += 15;
            }
        }
        for (final Module module : moduleList) {
            if (module.getCategory() == this.category) {
                final int modX = this.x + 2;
                final int modY = this.y + add;
                final int modWidth = this.width - 4;
                final int modHeight = 15;
                if (this.mouseOver(mouseX, mouseY, modX, modY, modWidth, modHeight) && this.novoline.current == null && this.mouseOver(mouseX, mouseY, this.x, this.y + this.height, this.width, this.currHeight)) {
                    if (mouseButton == 0) {
                        module.toggle();
                        this.checker.stop();
                    }
                    if (mouseButton == 1) {
                        module.toggleOpen();
                    }
                    if (mouseButton == 1) {
                        Aqua.INSTANCE.fileUtil.saveClickGuiOwn(this.novoline);
                        try {
                            this.mc.displayGuiScreen(new SettingScreen(module, this.novoline));
                        }
                        catch (Exception ex) {}
                    }
                }
                if (module.isOpen()) {}
                add += 15;
            }
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public Category getCategory() {
        return this.category;
    }
}
