package lol.point.returnclient.ui.cgui.dropdown;

import lol.point.Return;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.impl.client.ClickGUI;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.settings.impl.ColorSetting;
import lol.point.returnclient.ui.cgui.dropdown.comp.impl.*;
import lol.point.returnclient.util.render.FastFontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class DropdownClickGUI extends GuiScreen {

    private final FastFontRenderer regularFont = Return.INSTANCE.fontManager.getFont("Circular-Regular 18");
    private final FastFontRenderer settingFont = Return.INSTANCE.fontManager.getFont("Circular-Regular 17");

    private static final float WIDTH = 110, HEIGHT = 15;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        int screenWidth = sr.getScaledWidth();
        float categoryOffset = 0;

        int categoryCount = Category.values().length;
        float totalWidth = categoryCount * (WIDTH + 10) - 10;

        float x = (screenWidth - totalWidth) / 2.0f;
        float y = 20;

        for (Category category : Category.values()) {
            CategoryComponent categoryComponent = new CategoryComponent(category);
            categoryComponent.setSize(x + categoryOffset, y, WIDTH, HEIGHT);
            categoryComponent.setFont(regularFont);
            categoryComponent.setTheme(Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value));

            float moduleOffset = 15;
            if (category.expanded && !category.hidden) {
                for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                    ModuleComponent moduleComponent = new ModuleComponent(module);
                    moduleComponent.setSize(x + categoryOffset, y + moduleOffset, WIDTH, HEIGHT);
                    moduleComponent.setFont(regularFont);
                    moduleComponent.setTheme(Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value));

                    for (Setting setting : module.settings) {
                        if (!setting.getHidden().getAsBoolean()) {
                            SettingComponent settingComponent = new SettingComponent(setting);
                            settingComponent.setFont(settingFont);
                        }
                    }

                    float settingOffset = y + moduleOffset + 15;
                    if (module.expanded) {
                        for (Setting setting : module.settings) {
                            if (!setting.getHidden().getAsBoolean()) {
                                SettingComponent settingComponent = new SettingComponent(setting);
                                settingComponent.setFont(settingFont);
                                settingComponent.setSize(x + categoryOffset, settingOffset, WIDTH, HEIGHT);
                                settingComponent.setTheme(Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value));

                                if (setting instanceof ColorSetting) {
                                    settingOffset += 15;
                                    if (((ColorSetting) setting).expanded) {
                                        for (String ignored : new String[]{"Red", "Green", "Blue", "Alpha"}) {
                                            settingOffset += 15;
                                        }
                                    }
                                } else {
                                    settingOffset += 15;
                                }

                                settingComponent.drawScreen(mouseX, mouseY);
                            }
                        }
                    }

                    if (RenderUtil.hovered(mouseX, mouseY, 20 + categoryOffset, 20 + moduleOffset, WIDTH, HEIGHT)) {
                        regularFont.drawString(module.description, 5, this.height - regularFont.getHeight() - 3, -1);
                    }

                    moduleOffset += module.expanded ? !module.settings.isEmpty() ? settingOffset - moduleOffset - 20 : 15 : 15;
                    moduleComponent.drawScreen(mouseX, mouseY);
                }
            }

            categoryOffset += WIDTH + 3;
            categoryComponent.drawScreen(mouseX, mouseY);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        int screenWidth = sr.getScaledWidth();
        float categoryOffset = 0;

        int categoryCount = Category.values().length;
        float totalWidth = categoryCount * (WIDTH + 10) - 10;

        float x = (screenWidth - totalWidth) / 2.0f;
        float y = 20;

        for (Category category : Category.values()) {
            if (category.hidden) continue;
            CategoryComponent categoryComponent = new CategoryComponent(category);
            categoryComponent.setSize(x + categoryOffset, y, WIDTH, HEIGHT);

            float moduleOffset = 15;
            if (category.expanded) {
                for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                    ModuleComponent moduleComponent = new ModuleComponent(module);
                    moduleComponent.setSize(x + categoryOffset, y + moduleOffset, WIDTH, HEIGHT);

                    float settingOffset = y + moduleOffset + 15;
                    if (module.expanded) {
                        for (Setting setting : module.settings) {
                            if (setting.getHidden().getAsBoolean()) continue;
                            SettingComponent settingComponent = new SettingComponent(setting);
                            settingComponent.setSize(x + categoryOffset, settingOffset, WIDTH, HEIGHT);


                            if (setting instanceof ColorSetting) {
                                settingOffset += 15;
                                if (((ColorSetting) setting).expanded) {
                                    for (String ignored : new String[]{"Red", "Green", "Blue", "Alpha"}) {
                                        settingOffset += 15;
                                    }
                                }
                            } else {
                                settingOffset += 15;
                            }

                            settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
                        }
                    }

                    moduleOffset += module.expanded ? !module.settings.isEmpty() ? settingOffset - moduleOffset - 20 : 15 : 15;
                    moduleComponent.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }

            categoryOffset += WIDTH + 3;
            categoryComponent.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }

        float categoryOffset = 0;
        for (Category category : Category.values()) {
            if (category.hidden) continue;
            CategoryComponent categoryComponent = new CategoryComponent(category);
            categoryComponent.setSize(20 + categoryOffset, 20, WIDTH, HEIGHT);

            float moduleOffset = 15;
            if (category.expanded) {
                for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                    ModuleComponent moduleComponent = new ModuleComponent(module);
                    moduleComponent.setSize(20 + categoryOffset, 20 + moduleOffset, WIDTH, HEIGHT);

                    float settingOffset = 20 + moduleOffset + 15;
                    if (module.expanded) {
                        for (Setting setting : module.settings) {
                            if (setting.getHidden().getAsBoolean()) continue;
                            SettingComponent settingComponent = new SettingComponent(setting);
                            settingComponent.setSize(20 + categoryOffset, settingOffset, WIDTH, HEIGHT);
                            settingOffset += 15;
                            settingComponent.keyTyped(typedChar, keyCode);
                        }
                    }

                    moduleOffset += module.expanded ? !module.settings.isEmpty() ? settingOffset - moduleOffset - 20 : 15 : 15;
                    moduleComponent.keyTyped(typedChar, keyCode);
                }
            }

            categoryOffset += WIDTH + 3;
            categoryComponent.keyTyped(typedChar, keyCode);
        }
    }

    public boolean doesGuiPauseGame() {
        return Return.INSTANCE.moduleManager.getByClass(ClickGUI.class).pauseSingleplayer.value;
    }

}
