// 
// Decompiled by Procyon v0.5.36
// 

package gui.jello.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.modules.visual.ShaderMultiplier;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.modules.Module;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.Aqua;
import java.awt.Color;
import intent.AquaDev.aqua.utils.MouseClicker;
import gui.jello.ClickguiScreen;
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
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered2(this.category, true, Aqua.INSTANCE.jelloClickguiPanelBottom);
        int allModHeight = 0;
        for (final Module module : moduleList) {
            if (module.getCategory() == this.category) {
                allModHeight += 15;
            }
        }
        int iiiii = 0;
        for (final Category cc : Category.values()) {
            int mcSize = 0;
            for (final Module module2 : Aqua.moduleManager.modules) {
                if (module2.getCategory() == cc) {
                    mcSize += 15;
                }
            }
            if (mcSize > iiiii) {
                iiiii = mcSize;
            }
        }
        final int maxPanelHeight = 127;
        ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha(this.x, this.y, this.width, maxPanelHeight + this.height, 0.0, new Color(0, 0, 0, 200)), false);
        RenderUtil.drawRoundedRect2Alpha(this.x, this.y, this.width, maxPanelHeight + this.height, 0.0, new Color(250, 250, 250, 255));
        ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha(this.x, this.y - 5, this.width, this.height + 5, 0.0, new Color(0, 0, 0, 200)), false);
        RenderUtil.drawRoundedRect2Alpha(this.x, this.y - 5, this.width, this.height + 5, 0.0, new Color(244, 244, 244, 255));
        Aqua.INSTANCE.jelloClickguiPanelTop.drawString(this.category.name(), (float)(this.x + 9), (float)this.y, Color.gray.getRGB());
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {}
        GL11.glEnable(3089);
        RenderUtil.scissor(this.x, this.y + this.height, this.width, maxPanelHeight);
        allModHeight += this.height;
        int settingsHeight = 0;
        for (final Module module3 : moduleList) {
            if (module3.getCategory() == this.category) {
                if (module3.isToggled()) {
                    final int finalAdd = add;
                    final int n;
                    ShaderMultiplier.drawGlowESP(() -> Gui.drawRect(this.x, this.y + n, this.x + this.width, this.y + n + 15, new Color(45, 165, 251).getRGB()), false);
                    Gui.drawRect(this.x, this.y + add, this.x + this.width, this.y + add + 15, new Color(45, 165, 251).getRGB());
                    Aqua.INSTANCE.jelloClickguiPanelBottom.drawString(module3.getName(), this.x + this.width / 2.0f - 37.0f, (float)(this.y + add + 1), module3.isToggled() ? Color.white.getRGB() : Color.darkGray.getRGB());
                }
                else {
                    Aqua.INSTANCE.jelloClickguiPanelBottom.drawString(module3.getName(), this.x + this.width / 2.0f - 41.0f, (float)(this.y + add + 1), module3.isToggled() ? Color.white.getRGB() : Color.darkGray.getRGB());
                }
                final int modX = this.x + 2;
                final int modY = this.y + add;
                final int modWidth = this.width - 4;
                final int modHeight = 15;
                if (module3.isOpen()) {
                    final ArrayList<Setting> settings = Aqua.setmgr.getSettingsFromModule(module3);
                    int i = 0;
                    for (final Setting setting : settings) {
                        i += setting.getHeight();
                        if (setting.type.equals(Setting.Type.COLOR)) {}
                        setting.setMouseX(mouseX);
                        setting.setMouseY(mouseY);
                        setting.drawSettingJello(modX, modY + i, modWidth, modHeight, this.CATEGORY_MODULE_COLOR, this.ACTIVATED_TEXT_COLOR);
                        if (setting.type.equals(Setting.Type.STRING) && setting.isComboExtended()) {
                            i += (int)setting.getBoxHeight();
                        }
                    }
                    settingsHeight += i;
                    add += i;
                }
                add += 15;
            }
        }
        final int currHeight = MathHelper.clamp_int(add, 0, Math.max(allModHeight - this.height, maxPanelHeight));
        this.currHeight = currHeight;
        if (this.mouseOver(mouseX, mouseY, this.x, this.y, this.width, currHeight)) {
            final int mouseDelta = Aqua.INSTANCE.mouseWheelUtil.mouseDelta;
            final int oldadd = this.scrollAdd;
            this.scrollAdd += mouseDelta / 5;
            this.scrollAdd = MathHelper.clamp_int(this.scrollAdd, Math.min(0, -add + this.height + oldadd + maxPanelHeight), 0);
        }
        GL11.glDisable(3089);
        this.checker.release(0);
    }
    
    public void clickMouse(final int mouseX, final int mouseY, final int mouseButton) {
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered2(this.category, true, Aqua.INSTANCE.jelloClickguiPanelBottom);
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
                    if (mouseButton == 1) {}
                }
                if (module.isOpen()) {
                    final ArrayList<Setting> settings = Aqua.setmgr.getSettingsFromModule(module);
                    for (final Setting setting : settings) {
                        add += setting.getHeight();
                        if (setting.type.equals(Setting.Type.STRING) && setting.isComboExtended()) {
                            add += (int)setting.getBoxHeight();
                        }
                        if (this.mouseOver(mouseX, mouseY, this.x, this.y + this.height, this.width, this.currHeight)) {
                            setting.clickMouse(mouseX, mouseY, mouseButton);
                        }
                    }
                }
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
