// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui.novolineOld.components;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import intent.AquaDev.aqua.utils.RenderUtil;
import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.modules.Module;
import intent.AquaDev.aqua.Aqua;
import java.awt.Color;
import de.Hero.settings.Setting;
import java.util.ArrayList;
import intent.AquaDev.aqua.utils.MouseClicker;
import intent.AquaDev.aqua.gui.novolineOld.ClickguiScreenNovoline;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CategoryPaneNovoline extends Gui
{
    private final Integer CATEGORY_PANE_TOP_COLOR;
    private final Integer CATEGORY_MODULE_COLOR;
    private final Integer INACTIVATED_TEXT_COLOR;
    Minecraft mc;
    Translate translate;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final Category category;
    private final ClickguiScreenNovoline novoline;
    private Integer ACTIVATED_TEXT_COLOR;
    private final MouseClicker checker;
    private int scrollAdd;
    private int currHeight;
    private final ArrayList<Setting> settings;
    
    public CategoryPaneNovoline(final int x, final int y, final int width, final int height, final Category category, final ClickguiScreenNovoline novoline) {
        this.CATEGORY_PANE_TOP_COLOR = new Color(0, 0, 0, 195).getRGB();
        this.CATEGORY_MODULE_COLOR = new Color(0, 0, 0, 180).getRGB();
        this.INACTIVATED_TEXT_COLOR = Color.decode("#FEFEFF").getRGB();
        this.mc = Minecraft.getMinecraft();
        this.checker = new MouseClicker();
        this.scrollAdd = 0;
        this.settings = new ArrayList<Setting>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        this.novoline = novoline;
    }
    
    public void draw(final int posX, final int posY, final int mouseX, final int mouseY) {
        this.ACTIVATED_TEXT_COLOR = Aqua.setmgr.getSetting("HUDColor").getColor();
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {}
        Gui.drawRect(this.x + 2, this.y, this.x + this.width - 1, this.y + this.height, new Color(29, 29, 29).getRGB());
        Aqua.INSTANCE.novlineBigger.drawStringWithShadow(this.category.name(), (float)(this.x + 9), (float)(this.y + 5), this.INACTIVATED_TEXT_COLOR);
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered(this.category, true, Aqua.INSTANCE.comfortaa4);
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
                    mcSize += (int)(float)Aqua.setmgr.getSetting("GUIGUIScizzor").getCurrentNumber();
                }
            }
            if (mcSize > iiiii) {
                iiiii = mcSize;
            }
        }
        final int maxPanelHeight = iiiii;
        GL11.glEnable(3089);
        RenderUtil.scissor(this.x, this.y + this.height, this.width, maxPanelHeight);
        allModHeight += this.height;
        int settingsHeight = 0;
        for (final Module module3 : Aqua.moduleManager.modules) {
            if (module3.getCategory() == this.category) {
                final int finalAdd2 = add;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {}
                Gui.drawRect(this.x + 2, this.y + add, this.x + this.width - 1, this.y + add + 17, new Color(40, 40, 40).getRGB());
                final int finalAdd3 = add;
                if (module3.isToggled()) {
                    Gui.drawRect(this.x + 4, this.y + add, this.x + this.width - 3, this.y + add + 15, this.ACTIVATED_TEXT_COLOR);
                }
                Aqua.INSTANCE.novoline.drawStringWithShadow(module3.getName(), this.x + this.width / 2.0f - 43.0f, (float)(this.y + add + 2), -1);
                final int modX = this.x + 2;
                final int modY = this.y + add;
                final int modWidth = this.width - 4;
                final int modHeight = 15;
                final ArrayList<Setting> settings1 = Aqua.setmgr.getSettingsFromModule(module3);
                if (!settings1.isEmpty() && !module3.isOpen()) {
                    Aqua.INSTANCE.novoline.drawString("+", this.x + this.width / 2.0f + 40.0f, (float)(this.y + add + 2), -1);
                }
                if (!settings1.isEmpty() && module3.isOpen()) {
                    Aqua.INSTANCE.novoline.drawString("-", this.x + this.width / 2.0f + 41.0f, (float)(this.y + add + 2), -1);
                }
                if (module3.isOpen()) {
                    final ArrayList<Setting> settings2 = Aqua.setmgr.getSettingsFromModule(module3);
                    int i = 0;
                    for (final Setting setting : settings2) {
                        i += setting.getHeight();
                        if (setting.type.equals(Setting.Type.COLOR)) {}
                        setting.setMouseX(mouseX);
                        setting.setMouseY(mouseY);
                        setting.drawSettingOldNovoline(modX, modY + i, modWidth, modHeight, this.CATEGORY_MODULE_COLOR, this.ACTIVATED_TEXT_COLOR);
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
        final int finalAdd4 = currHeight - 1;
        this.checker.release(0);
    }
    
    public void clickMouse(final int mouseX, final int mouseY, final int mouseButton) {
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered(this.category, true, Aqua.INSTANCE.comfortaa4);
        int allModHeight = 0;
        for (final Module module : Aqua.moduleManager.modules) {
            if (module.getCategory() == this.category) {
                allModHeight += 15;
            }
        }
        for (final Module module : Aqua.moduleManager.modules) {
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
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Category getCategory() {
        return this.category;
    }
}
