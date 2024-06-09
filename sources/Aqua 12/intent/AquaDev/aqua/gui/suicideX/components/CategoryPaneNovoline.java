// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui.suicideX.components;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.modules.Module;
import intent.AquaDev.aqua.modules.visual.Blur;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.Aqua;
import java.awt.Color;
import de.Hero.settings.Setting;
import java.util.ArrayList;
import intent.AquaDev.aqua.utils.MouseClicker;
import intent.AquaDev.aqua.gui.suicideX.ClickguiScreenNovoline;
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
    public static boolean last;
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
        final Color alphaColor = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging) {
            if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + 4, this.width - 3, this.height - 4, 5.0, true, true, false, false, new Color(36, 36, 36, 255)), false);
            }
            else {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + 4, this.width - 3, this.height - 4, 5.0, true, true, false, false, new Color(255, 255, 255, 180)), false);
            }
        }
        RenderUtil.drawRoundedRect3(this.x + 2, this.y + 4, this.width - 3, this.height - 4, 5.0, true, true, false, false, alphaColor);
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + 5, this.width - 3, this.height - 5, 5.0, true, true, false, false, alphaColor), false);
        }
        Aqua.INSTANCE.novlineBigger.drawCenteredString(this.category.name(), this.x + this.width / 2.0f, (float)(this.y + 6), this.INACTIVATED_TEXT_COLOR);
        int add = this.height + this.scrollAdd;
        final List<Module> moduleList = Aqua.moduleManager.getModulesOrdered(this.category, false, Aqua.INSTANCE.comfortaa4);
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
        int c = 0;
        for (final Module module3 : moduleList) {
            if (module3.getCategory() == this.category) {
                final int finalAdd2 = add;
                ++c;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {}
                final int finalAdd3 = add;
                CategoryPaneNovoline.last = (c == moduleList.size());
                if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalAdd4 = add;
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging) {
                        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + finalAdd4, this.width - 3, CategoryPaneNovoline.last ? 16.0 : 15.0, 3.0, false, false, CategoryPaneNovoline.last, CategoryPaneNovoline.last, new Color(36, 36, 36, 255)), false);
                    }
                    RenderUtil.drawRoundedRect3(this.x + 2, this.y + add, this.width - 3, CategoryPaneNovoline.last ? 16.0 : 15.0, 3.0, false, false, CategoryPaneNovoline.last, CategoryPaneNovoline.last, new Color(36, 36, 36, 180));
                }
                else {
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging) {
                        final int finalAdd5 = add;
                        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + finalAdd5, this.width - 3, CategoryPaneNovoline.last ? 16.0 : 15.0, 3.0, false, false, CategoryPaneNovoline.last, CategoryPaneNovoline.last, new Color(255, 255, 255, 180)), false);
                    }
                    RenderUtil.drawRoundedRect3(this.x + 2, this.y + add, this.width - 3, CategoryPaneNovoline.last ? 16.0 : 15.0, 3.0, false, false, CategoryPaneNovoline.last, CategoryPaneNovoline.last, new Color(255, 255, 255, 180));
                }
                final int finalAdd6 = add;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    Blur.drawBlurred(() -> RenderUtil.drawRoundedRect3(this.x + 2, this.y + finalAdd6, this.width - 3, CategoryPaneNovoline.last ? 16.0 : 15.0, 3.0, false, false, CategoryPaneNovoline.last, CategoryPaneNovoline.last, new Color(36, 36, 36, 180)), false);
                }
                final int finalAdd7 = add;
                if (module3.isToggled()) {
                    final int finalAdd8 = add;
                    if (module3.isOpen()) {
                        RenderUtil.drawRoundedRect2Alpha(this.x + 2, this.y + add, this.width - 3, 15.0, 0.0, alphaColor);
                    }
                    else {
                        RenderUtil.drawRoundedRect2Alpha(this.x + 4, this.y + add + 1, this.width - 7, 13.0, 3.0, alphaColor);
                    }
                    if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(this.x + 4, this.y + finalAdd8 + 1, this.width - 7, 13.0, 3.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB()), false);
                    }
                }
                else {
                    if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        RenderUtil.drawRoundedRect2Alpha(this.x + 4, this.y + add + 1, this.width - 7, 13.0, 3.0, new Color(76, 76, 76, 220));
                    }
                    else {
                        RenderUtil.drawRoundedRect2Alpha(this.x + 4, this.y + add + 1, this.width - 7, 13.0, 3.0, new Color(255, 255, 255, 190));
                    }
                    final int finalAdd9 = add;
                    if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(this.x + 4, this.y + finalAdd9 + 1, this.width - 7, 13.0, 3.0, new Color(255, 255, 255, 150).getRGB()), false);
                    }
                }
                if (module3.isToggled()) {
                    Aqua.INSTANCE.novoline.drawCenteredString(module3.getName(), this.x + this.width / 2.0f, (float)(this.y + add + 2), Color.white.getRGB());
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Aqua.INSTANCE.novoline.drawCenteredString(module3.getName(), this.x + this.width / 2.0f, (float)(this.y + add + 2), Color.white.getRGB());
                }
                else {
                    Aqua.INSTANCE.novoline.drawCenteredString(module3.getName(), this.x + this.width / 2.0f, (float)(this.y + add + 2), Color.gray.getRGB());
                }
                final int modX = this.x + 2;
                final int modY = this.y + add;
                final int modWidth = this.width - 4;
                final int modHeight = 15;
                final ArrayList<Setting> settings1 = Aqua.setmgr.getSettingsFromModule(module3);
                if (!settings1.isEmpty() && !module3.isOpen()) {
                    if (module3.isToggled()) {
                        Aqua.INSTANCE.novoline.drawString("+", this.x + this.width / 2.0f + 40.0f, (float)(this.y + add + 2), -1);
                    }
                    else {
                        Aqua.INSTANCE.novoline.drawString("+", this.x + this.width / 2.0f + 40.0f, (float)(this.y + add + 2), Color.gray.getRGB());
                    }
                }
                if (!settings1.isEmpty() && module3.isOpen()) {
                    if (module3.isToggled()) {
                        Aqua.INSTANCE.novoline.drawString("-", this.x + this.width / 2.0f + 41.0f, (float)(this.y + add + 2), -1);
                    }
                    else {
                        Aqua.INSTANCE.novoline.drawString("-", this.x + this.width / 2.0f + 41.0f, (float)(this.y + add + 2), Color.gray.getRGB());
                    }
                }
                if (module3.isOpen()) {
                    final ArrayList<Setting> settings2 = Aqua.setmgr.getSettingsFromModule(module3);
                    int i = 0;
                    for (final Setting setting : settings2) {
                        i += setting.getHeight();
                        if (setting.type.equals(Setting.Type.COLOR)) {}
                        setting.setMouseX(mouseX);
                        setting.setMouseY(mouseY);
                        setting.drawSettingSuicideX(modX, modY + i, modWidth, modHeight, this.CATEGORY_MODULE_COLOR, this.ACTIVATED_TEXT_COLOR);
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
        final int finalAdd10 = currHeight - 1;
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
    
    static {
        CategoryPaneNovoline.last = false;
    }
}
