/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package digital.rbq.clickgui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import digital.rbq.clickgui.panel.Panel;
import digital.rbq.core.Autumn;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.impl.visuals.hud.HUDMod;

public final class ClickGuiScreen
extends GuiScreen {
    private static final HUDMod HUD = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class);
    private static ClickGuiScreen INSTANCE;
    private final List<Panel> panels = Lists.newArrayList();

    private ClickGuiScreen() {
        ModuleCategory[] categories = ModuleCategory.values();
        for (int i = categories.length - 1; i >= 0; --i) {
            this.panels.add(new Panel(categories[i], 5 + 120 * i, 5));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int panelsSize = this.panels.size();
        for (int i = 0; i < panelsSize; ++i) {
            this.panels.get(i).onDraw(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int panelsSize = this.panels.size();
        for (int i = 0; i < panelsSize; ++i) {
            this.panels.get(i).onMouseClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        int panelsSize = this.panels.size();
        for (int i = 0; i < panelsSize; ++i) {
            this.panels.get(i).onMouseRelease(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        int panelsSize = this.panels.size();
        for (int i = 0; i < panelsSize; ++i) {
            this.panels.get(i).onKeyPress(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    public static ClickGuiScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGuiScreen();
        }
        return INSTANCE;
    }

    public static Color getColor() {
        return (Color)ClickGuiScreen.HUD.color.getValue();
    }
}

