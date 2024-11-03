package dev.stephen.nexus.gui.clickgui.old.components;

import dev.stephen.nexus.gui.clickgui.old.Window;
import dev.stephen.nexus.gui.clickgui.old.components.settings.*;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.*;
import dev.stephen.nexus.module.setting.impl.*;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import dev.stephen.nexus.utils.render.TextRenderer;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ModuleButton {
    public List<RenderableSetting> settings = new ArrayList<>();
    public dev.stephen.nexus.gui.clickgui.old.Window parent;
    public Module module;
    public int offset;
    public boolean extended;
    public int settingOffset;

    public ModuleButton(Window parent, Module module, int offset) {
        this.parent = parent;
        this.module = module;
        this.offset = offset;
        this.extended = false;

        setupSettingsAndstuff();
    }

    private void setupSettingsAndstuff() {
        settingOffset = parent.getHeight();
        settings.clear();
        for (Setting property : module.getSettings()) {
            boolean shouldContinue = false;

            if (!property.getDependencyBoolSettings().isEmpty()) {
                for (int i = 0; i < property.getDependencyBoolSettings().size(); i++) {
                    Setting dependency = property.getDependencyBoolSettings().get(i);
                    boolean expectedValue = property.getDependencyBools().get(i);

                    if (dependency instanceof BooleanSetting && ((BooleanSetting) dependency).getValue() != expectedValue) {
                        shouldContinue = true;
                        break;
                    }
                }
            }

            if (!property.getDependencyModeSettings().isEmpty()) {
                for (int i = 0; i < property.getDependencyModeSettings().size(); i++) {
                    Setting dependency = property.getDependencyModeSettings().get(i);
                    String expectedMode = property.getDependencyModes().get(i);

                    if (!(dependency instanceof ModeSetting && ((ModeSetting) dependency).isMode(expectedMode))) {
                        shouldContinue = true;
                        break;
                    }
                }
            }

            if (!property.getDependencyMultiModeSettings().isEmpty()) {
                for (int i = 0; i < property.getDependencyMultiModeSettings().size(); i++) {
                    Setting dependency = property.getDependencyMultiModeSettings().get(i);
                    String expectedMode = property.getDependencyMultiModes().get(i);

                    if (!(dependency instanceof MultiModeSetting && ((MultiModeSetting) dependency).isModeSelected(expectedMode))) {
                        shouldContinue = true;
                        break;
                    }
                }
            }

            if (!property.getDependencyNewModeSettings().isEmpty()) {
                for (int i = 0; i < property.getDependencyNewModeSettings().size(); i++) {
                    Setting dependency = property.getDependencyNewModeSettings().get(i);
                    String expectedMode = property.getDependencyNewModes().get(i);

                    if (!(dependency instanceof NewModeSetting && ((NewModeSetting) dependency).isMode(expectedMode))) {
                        shouldContinue = true;
                        break;
                    }
                }
            }

            if (shouldContinue) {
                continue;
            }

            if (property instanceof BooleanSetting booleanSetting) {
                settings.add(new CheckBox(this, booleanSetting, settingOffset));
            } else if (property instanceof NumberSetting numberSetting) {
                settings.add(new Slider(this, numberSetting, settingOffset));
            } else if (property instanceof ModeSetting modeSetting) {
                settings.add(new ModeBox(this, modeSetting, settingOffset));
            }  else if (property instanceof NewModeSetting newModeSetting) {
                settings.add(new NewModeBox(this, newModeSetting, settingOffset));
            } else if (property instanceof RangeSetting rangeSetting) {
                settings.add(new RangeSlider(this, rangeSetting, settingOffset));
            } else if (property instanceof StringSetting stringSetting) {
                settings.add(new StringSettingBox(this, stringSetting, settingOffset));
            } else if (property instanceof MultiModeSetting multiModeSetting) {
                settings.add(new MultiModeSettingBox(this, multiModeSetting, settingOffset));
            }

            settingOffset += parent.getHeight();
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + parent.getHeight() + offset, new Color(35, 35, 35, 175).getRGB());
        TextRenderer.drawCenteredMinecraftText(module.getName(), context, parent.getX() + (parent.getWidth() / 2), parent.getY() + offset + 8, module.isEnabled() ? ThemeUtils.getMainColor().getRGB() : Color.WHITE.getRGB());

        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + parent.getHeight() + offset, new Color(255, 255, 255, 10).getRGB());
        }

        if (extended) {
            for (RenderableSetting renderableSetting : settings) {
                renderableSetting.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (extended) {
            for (RenderableSetting renderableSetting : settings) {
                renderableSetting.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            }

            if (button == 1) {
                extended = !extended;
                parent.updateButtons();
            }
        }
        if (extended) {
            for (RenderableSetting renderableSetting : settings) {
                renderableSetting.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        setupSettingsAndstuff();
        for (RenderableSetting renderableSetting : settings) {
            renderableSetting.mouseReleased(mouseX, mouseY, button);
        }
        parent.updateButtons();
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.getX()
                && mouseX < parent.getX() + parent.getWidth()
                && mouseY > parent.getY() + offset
                && mouseY < parent.getY() + offset + parent.getHeight();
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (RenderableSetting renderableSetting : settings) {
            renderableSetting.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public void keyReleased(int keyCode, int scanCode, int modifiers) {
        for (RenderableSetting renderableSetting : settings) {
            renderableSetting.keyReleased(keyCode, scanCode, modifiers);
        }
    }
}