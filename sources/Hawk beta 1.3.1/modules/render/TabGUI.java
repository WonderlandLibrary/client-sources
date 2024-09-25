package eze.modules.render;

import eze.modules.*;
import eze.events.*;
import java.awt.*;
import eze.*;
import eze.settings.*;
import org.lwjgl.input.*;
import eze.events.listeners.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class TabGUI extends Module
{
    public ModeSetting ColorOption;
    public int currentTab;
    public boolean expanded;
    
    public TabGUI() {
        super("TabGUI", 0, Category.RENDER);
        this.ColorOption = new ModeSetting("Color", "Colorful", new String[] { "Colorful", "Red", "Blue", "Orange", "Green", "White" });
        this.toggled = true;
        this.addSettings(this.ColorOption);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRenderGUI) {
            final FontRenderer fr = this.mc.fontRendererObj;
            int color = 0;
            if (this.ColorOption.is("Colorful")) {
                final float hue = System.currentTimeMillis() % 4500L / 4500.0f;
                color = Color.HSBtoRGB(hue, 1.0f, 1.0f);
            }
            if (this.ColorOption.is("Red")) {
                color = Color.HSBtoRGB(1.003112E7f, 1.0f, 1.0f);
            }
            if (this.ColorOption.is("Blue")) {
                color = -16756481;
            }
            if (this.ColorOption.is("Orange")) {
                color = -1350377;
            }
            if (this.ColorOption.is("Green")) {
                color = -13571305;
            }
            if (this.ColorOption.is("White")) {
                color = -1;
            }
            final int primaryColor = color;
            final int secondaryColor = -16748374;
            Gui.drawRect(5.0, 30.5, 70.0, 30 + Category.values().length * 16 + 1.5, -1879048192);
            Gui.drawRect(5.0, 30.5f + this.currentTab * 16, 70.0, 33 + this.currentTab * 16 + 12 + 2.5f, primaryColor);
            int count = 0;
            Category[] values;
            for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
                final Category c = values[i];
                fr.drawStringWithShadow(c.name, 11.0, 35 + count * 16, -1);
                ++count;
            }
            if (this.expanded) {
                final Category category = Category.values()[this.currentTab];
                final List<Module> modules = Client.getModulesByCategory(category);
                if (modules.size() == 0) {
                    return;
                }
                Gui.drawRect(70.0, 30.5, 138.0, 30 + modules.size() * 16 + 1.5, -1879048192);
                Gui.drawRect(70.0, 30.5f + category.moduleIndex * 16, 138.0, 33 + category.moduleIndex * 16 + 12 + 2.5f, primaryColor);
                count = 0;
                for (final Module m : modules) {
                    fr.drawStringWithShadow(m.name, 73.0, 35 + count * 16, -1);
                    if (count == category.moduleIndex && m.expanded) {
                        int index = 0;
                        int maxLength = 0;
                        for (final Setting setting : m.settings) {
                            if (setting instanceof BooleanSetting) {
                                final BooleanSetting bool = (BooleanSetting)setting;
                                if (maxLength < fr.getStringWidth(String.valueOf(setting.name) + " : " + (bool.enabled ? "Enabled" : "Disabled"))) {
                                    maxLength = fr.getStringWidth(String.valueOf(setting.name) + " : " + (bool.enabled ? "Enabled" : "Disabled"));
                                }
                            }
                            if (setting instanceof NumberSetting) {
                                final NumberSetting number = (NumberSetting)setting;
                                if (maxLength < fr.getStringWidth(String.valueOf(setting.name) + " : " + number.getValue())) {
                                    maxLength = fr.getStringWidth(String.valueOf(setting.name) + " : " + number.getValue());
                                }
                            }
                            if (setting instanceof ModeSetting) {
                                final ModeSetting mode = (ModeSetting)setting;
                                if (maxLength < fr.getStringWidth(String.valueOf(setting.name) + " : " + mode.getMode())) {
                                    maxLength = fr.getStringWidth(String.valueOf(setting.name) + " : " + mode.getMode());
                                }
                            }
                            if (setting instanceof KeybindSetting) {
                                final KeybindSetting keyBind = (KeybindSetting)setting;
                                if (maxLength < fr.getStringWidth(String.valueOf(setting.name) + " : " + Keyboard.getKeyName(keyBind.code))) {
                                    maxLength = fr.getStringWidth(String.valueOf(setting.name) + " : " + Keyboard.getKeyName(keyBind.code));
                                }
                            }
                            ++index;
                        }
                        if (!m.settings.isEmpty()) {
                            Gui.drawRect(138.0, 30.5, 70 + maxLength + 9 + 68, 30 + m.settings.size() * 16 + 1.5, -1879048192);
                            Gui.drawRect(138.0, 30.5f + m.index * 16, 68 + maxLength + 9 + 70, 33 + m.index * 16 + 12 + 2.5f, m.settings.get(m.index).focused ? secondaryColor : primaryColor);
                            index = 0;
                            for (final Setting setting : m.settings) {
                                if (setting instanceof BooleanSetting) {
                                    final BooleanSetting bool = (BooleanSetting)setting;
                                    fr.drawStringWithShadow(String.valueOf(setting.name) + " : " + (bool.enabled ? "Enabled" : "Disabled"), 143.0, 35 + index * 16, -1);
                                }
                                if (setting instanceof NumberSetting) {
                                    final NumberSetting number = (NumberSetting)setting;
                                    fr.drawStringWithShadow(String.valueOf(setting.name) + " : " + number.getValue(), 143.0, 35 + index * 16, -1);
                                }
                                if (setting instanceof ModeSetting) {
                                    final ModeSetting mode = (ModeSetting)setting;
                                    fr.drawStringWithShadow(String.valueOf(setting.name) + " : " + mode.getMode(), 143.0, 35 + index * 16, -1);
                                }
                                if (setting instanceof KeybindSetting) {
                                    final KeybindSetting keyBind = (KeybindSetting)setting;
                                    fr.drawStringWithShadow(String.valueOf(setting.name) + " : " + Keyboard.getKeyName(keyBind.code), 143.0, 35 + index * 16, -1);
                                }
                                ++index;
                            }
                        }
                    }
                    ++count;
                }
            }
        }
        if (e instanceof EventKey) {
            final int code = ((EventKey)e).code;
            final Category category2 = Category.values()[this.currentTab];
            final List<Module> modules2 = Client.getModulesByCategory(category2);
            if (this.expanded && !modules2.isEmpty() && modules2.get(category2.moduleIndex).expanded) {
                final Module module = modules2.get(category2.moduleIndex);
                if (!module.settings.isEmpty() && module.settings.get(module.index).focused && module.settings.get(module.index) instanceof KeybindSetting && code != 28 && code != 200 && code != 208 && code != 203 && code != 205 && code != 1) {
                    final KeybindSetting keyBind2 = module.settings.get(module.index);
                    keyBind2.code = code;
                    keyBind2.focused = false;
                    return;
                }
            }
            if (code == 200) {
                if (this.expanded) {
                    if (this.expanded && !modules2.isEmpty() && modules2.get(category2.moduleIndex).expanded) {
                        final Module module = modules2.get(category2.moduleIndex);
                        if (!module.settings.isEmpty()) {
                            if (module.settings.get(module.index).focused) {
                                final Setting setting2 = module.settings.get(module.index);
                                if (setting2 instanceof NumberSetting) {
                                    ((NumberSetting)setting2).increment(true);
                                }
                            }
                            else if (module.index <= 0) {
                                module.index = module.settings.size() - 1;
                            }
                            else {
                                final Module module2 = module;
                                --module2.index;
                            }
                        }
                    }
                    else if (category2.moduleIndex <= 0) {
                        category2.moduleIndex = modules2.size() - 1;
                    }
                    else {
                        final Category category3 = category2;
                        --category3.moduleIndex;
                    }
                }
                else if (this.currentTab <= 0) {
                    this.currentTab = Category.values().length - 1;
                }
                else {
                    --this.currentTab;
                }
            }
            if (code == 208) {
                if (this.expanded) {
                    if (this.expanded && !modules2.isEmpty() && modules2.get(category2.moduleIndex).expanded) {
                        final Module module = modules2.get(category2.moduleIndex);
                        if (!module.settings.isEmpty()) {
                            if (module.settings.get(module.index).focused) {
                                final Setting setting2 = module.settings.get(module.index);
                                if (setting2 instanceof NumberSetting) {
                                    ((NumberSetting)setting2).increment(false);
                                }
                            }
                            else if (module.index >= module.settings.size() - 1) {
                                module.index = 0;
                            }
                            else {
                                final Module module3 = module;
                                ++module3.index;
                            }
                        }
                    }
                    else if (category2.moduleIndex >= modules2.size() - 1) {
                        category2.moduleIndex = 0;
                    }
                    else {
                        final Category category4 = category2;
                        ++category4.moduleIndex;
                    }
                }
                else if (this.currentTab >= Category.values().length - 1) {
                    this.currentTab = 0;
                }
                else {
                    ++this.currentTab;
                }
            }
            if (code == 28 && this.expanded && modules2.size() != 0) {
                final Module module = modules2.get(category2.moduleIndex);
                if (!module.expanded && !module.settings.isEmpty()) {
                    module.expanded = true;
                }
                else if (module.expanded && !module.settings.isEmpty()) {
                    module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
                }
            }
            if (code == 205) {
                if (this.expanded && modules2.size() != 0) {
                    final Module module = modules2.get(category2.moduleIndex);
                    if (this.expanded && !modules2.isEmpty() && module.expanded) {
                        final Setting setting2 = module.settings.get(module.index);
                        if (!module.settings.isEmpty()) {
                            if (setting2 instanceof BooleanSetting) {
                                ((BooleanSetting)setting2).toggle();
                            }
                            if (setting2 instanceof ModeSetting) {
                                ((ModeSetting)setting2).cycle();
                            }
                        }
                    }
                    else if (!module.name.equals("TabGUI")) {
                        module.toggle();
                    }
                }
                else {
                    this.expanded = true;
                }
            }
            if (code == 203) {
                if (this.expanded && !modules2.isEmpty() && modules2.get(category2.moduleIndex).expanded) {
                    final Module module = modules2.get(category2.moduleIndex);
                    if (!module.settings.isEmpty() && !module.settings.get(module.index).focused) {
                        modules2.get(category2.moduleIndex).expanded = false;
                    }
                }
                else {
                    this.expanded = false;
                }
            }
        }
    }
}
