package club.strifeclient.ui.implementations.hud.clickgui;

import club.strifeclient.Client;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.Setting;
import club.strifeclient.setting.implementations.*;
import club.strifeclient.ui.GuiBase;
import club.strifeclient.ui.implementations.hud.clickgui.components.*;
import club.strifeclient.ui.implementations.hud.clickgui.themes.StrifeTheme;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class GuiClickGUI extends GuiScreen implements GuiBase {

    @Setter
    private Theme theme;
    private final List<Component<?>> components;

    public GuiClickGUI() {
        // Discord Nitro for you https://dlsscord-gift.com/j7F2Kv1g
        components = new ArrayList<>();
        theme = new StrifeTheme();
        float categoryXOffset = 0;
        float yOffset = 0;
        for (Category category : Category.values()) {
            final CategoryComponent categoryComponent = new CategoryComponent(category, theme, categoryXOffset, 0, 110, 15);
            components.add(categoryComponent);
            for (Module module : Client.INSTANCE.getModuleManager().getModules(getModule(category)).stream().sorted(Comparator.comparing(Module::getName)).collect(Collectors.toList())) {
                final ModuleComponent moduleComponent = new ModuleComponent(module, theme, categoryComponent, categoryXOffset, 0, 110, 15);
                components.add(moduleComponent);
                components.add(new BindComponent(module, theme, moduleComponent, categoryXOffset, 0, 110, 15));
                for (Setting<?> setting : module.getSettings()) {
                    Component<?> component = null;
                    if (setting instanceof BooleanSetting)
                        components.add(component = new BooleanSettingComponent((BooleanSetting) setting, theme, moduleComponent, categoryXOffset, 0, 110, 15));
                    if (setting instanceof DoubleSetting)
                        components.add(component = new DoubleSettingComponent((DoubleSetting) setting, theme, moduleComponent, categoryXOffset, 0, 110, 15));
                    if (setting instanceof ModeSetting<?>)
                        components.add(component = new ModeSettingComponent((ModeSetting<?>) setting, theme, moduleComponent, categoryXOffset, 0, 110, 15));
                    if (setting instanceof ColorSetting)
                        components.add(component = new ColorSettingComponent((ColorSetting) setting, theme, moduleComponent, categoryXOffset, 0, 110, 15 * 5));
                    if (setting instanceof MultiSelectSetting) {
                        final MultiSelectSetting<?> multiSelectSetting = (MultiSelectSetting<?>) setting;
                        final MultiSelectComponent multiSelectComponent = new MultiSelectComponent(multiSelectSetting, theme, moduleComponent, categoryXOffset, 0, 110, 15);
                        components.add(component = multiSelectComponent);
                        for(Map.Entry<Enum<?>, Boolean> pair : multiSelectSetting.getValue().entrySet()) {
                            final Enum<?> value = pair.getKey();
                            final BooleanSetting multiItemSelectSetting = new BooleanSetting(value instanceof SerializableEnum ? ((SerializableEnum)value).getName() : value.name(), pair.getValue());
                            multiSelectSetting.addChangeCallback((old, change) -> multiItemSelectSetting.setValue(change.getValue().get(value)));
                            multiItemSelectSetting.addChangeCallback((old, change) -> multiSelectSetting.setSelected(value, change.getValue()));
                            Component<?> childComponent;
                            components.add(childComponent = new BooleanSettingComponent(multiItemSelectSetting, theme, multiSelectComponent, categoryXOffset, 0, 110, 15));
                            yOffset += childComponent.getHeight();
                        }
                    }
                    if (component != null)
                        yOffset += component.getHeight();
                }
            }
            categoryXOffset += categoryComponent.getWidth() + 3;
        }
    }

    @Override
    public void onGuiClosed() {
        components.forEach(Component::onGuiClosed);
    }

    @Override
    public void initGui() {
        theme.init();
        components.forEach(Component::initGui);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float yOffset = 0;
        for (Component<?> component : components) {
            if (component instanceof CategoryComponent) {
                yOffset = component.getY() + component.getOrigHeight();
            }
            if (component.isVisible()) {
                if (component.getParent() != null) {
                    component.x = component.getParent().getX();
                    component.y = yOffset;
                    yOffset += component.getHeight();
                }
                component.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (Component<?> component : components) {
            if (component.isVisible())
                component.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        for (Component<?> component : components) {
            if (component.isVisible())
                component.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        boolean interrupted = false;
        for (Component<?> component : components) {
            if (component.isVisible()) {
                component.keyTyped(typedChar, keyCode);
                if (component instanceof GuiFocusable) {
                    GuiFocusable focusable = (GuiFocusable) component;
                    if (focusable.isFocused()) {
                        interrupted = true;
                        focusable.setFocused(false);
                    }
                }
            }
        }
        if (!interrupted)
            super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private Predicate<Module> getModule(Category category) {
        return module -> module.getCategory() == category;
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
