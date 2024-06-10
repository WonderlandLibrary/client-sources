package me.kaimson.melonclient.gui.settings;

import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.gui.components.*;
import me.kaimson.melonclient.gui.utils.blur.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import me.kaimson.melonclient.gui.elements.settings.color.*;
import java.util.concurrent.atomic.*;
import me.kaimson.melonclient.gui.elements.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.elements.settings.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.gui.elements.settings.mode.*;

public abstract class SettingsBase extends GuiScreen
{
    private static int totalOffset;
    protected final axu parentScreen;
    protected avr sr;
    private axu nextScreen;
    public static GuiScreen lastWindow;
    
    @Override
    public void b() {
        super.b();
        SettingsBase.totalOffset = 0;
        this.sr = new avr(this.j);
        this.components.add(new ComponentToolbar(this.l / 2 - this.getWidth() / 2, this.m / 2 - this.getHeight() / 2, this.l / 2 - this.getWidth() / 2 + 16, this.m / 2 + this.getHeight() / 2, 3, 14, ComponentToolbar.Layout.VERTICAL).setOffset(1, 1).required(new ElementButtonIcon(14, 14, "icons/close.png", element -> this.nextGui(null)), ComponentToolbar.Position.POSITIVE).optional(new ElementButtonIcon(14, 14, "icons/home.png", element -> this.nextGui(this.parentScreen)), ComponentToolbar.Position.POSITIVE, this.parentScreen != null).optional(new ElementButtonIcon(14, 14, "icons/info.png", element -> {}), ComponentToolbar.Position.NEGATIVE, false).optional(new ElementButtonIcon(14, 14, "icons/settings.png", element -> this.nextGui(new GuiSettings(this))), ComponentToolbar.Position.NEGATIVE, !(this instanceof GuiSettings)).required(new ElementButtonIcon(14, 14, "icons/edit.png", element -> this.nextGui(new GuiHUDEditor(this))), ComponentToolbar.Position.NEGATIVE));
        BlurShader.INSTANCE.onGuiOpen(5.0f);
    }
    
    protected void drawBackground() {
        GuiUtils.a(0, 0, this.l, this.m, new Color(0, 0, 0, 125).getRGB());
        GLRectUtils.drawRoundedRect((float)(this.l / 2 - this.getWidth() / 2 + 18), (float)(this.m / 2 - this.getHeight() / 2), (float)(this.l / 2 + this.getWidth() / 2), (float)(this.m / 2 + this.getHeight() / 2), 5.0f, new Color(0, 0, 0, 140).getRGB());
        WatermarkRenderer.render(this.l, this.m);
    }
    
    @Override
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        final SettingElementColor color;
        this.enableInput = this.elements.stream().filter(element -> element instanceof SettingElementColor).noneMatch(element -> {
            color = element;
            return color.colorPane.hovered || color.huePane.hovered || color.alphaPane.hovered;
        });
    }
    
    @Override
    public void m() {
        super.m();
        if (this.nextScreen == null || !(this.nextScreen instanceof SettingsBase) || this.nextScreen == this.parentScreen || this.parentScreen == null) {
            if (this.nextScreen != this.parentScreen) {
                GuiModules.scrollState = 0;
            }
            BlurShader.INSTANCE.onGuiClose();
        }
        SettingsBase.lastWindow = null;
        if (this instanceof GuiModuleSettings && this.nextScreen == null) {
            SettingsBase.lastWindow = this;
        }
        SettingsBase.totalOffset = 0;
    }
    
    protected void nextGui(final axu screen) {
        this.nextScreen = screen;
        this.j.a(screen);
    }
    
    protected int getWidth() {
        return Math.max(356, this.l - this.l / 3);
    }
    
    protected int getHeight() {
        return this.m - this.m / 3;
    }
    
    protected void addSetting(final Setting setting, final int x, final int y) {
        if (setting.hasValue()) {
            Element element = null;
            switch (setting.getType()) {
                case COLOR: {
                    final AtomicBoolean found;
                    final AtomicBoolean atomicBoolean;
                    element = new SettingElementColor(x + 1, y + 1, 10, 10, this.getWidth() - 100, 0, setting, (mainElement, expanded) -> {
                        if (expanded) {
                            SettingsBase.totalOffset += 34;
                            this.expandScroll(37);
                        }
                        else {
                            SettingsBase.totalOffset -= 34;
                            this.expandScroll(-37);
                        }
                        found = new AtomicBoolean(false);
                        this.elements.forEach(settingElement -> {
                            if ((settingElement instanceof SettingElement || settingElement instanceof ElementCategory) && (settingElement == mainElement || atomicBoolean.get())) {
                                atomicBoolean.set(true);
                                if (settingElement != mainElement) {
                                    settingElement.addOffsetToY(((boolean)expanded) ? 34 : -34);
                                }
                            }
                        });
                        return;
                    }, (module, settingElement) -> module.setValue(new ColorObject(GuiUtils.getRGB(settingElement.color, settingElement.alpha), settingElement.chroma.active, (int)settingElement.chromaSpeed.getDenormalized())), this);
                    break;
                }
                case CHECKBOX: {
                    element = new SettingElementCheckbox(x + 1, y + 1, 10, 10, setting, (module, settingElement) -> setting.setValue(!setting.getBoolean()), this);
                    break;
                }
                case INT_SLIDER: {
                    element = new SettingElementSlider(x + 1, y + 4, 75, 5, setting.getRange(0), setting.getRange(1), setting.getRange(2), (float)setting.getInt(), setting, (module, settingElement) -> setting.setValue((int)MathUtil.denormalizeValue(settingElement.sliderValue, setting.getRange(0), setting.getRange(1), setting.getRange(2))), this);
                    element.setXOffset((int)Client.titleRenderer.getWidth(setting.getDescription()) + 6);
                    break;
                }
                case FLOAT_SLIDER: {
                    element = new SettingElementSlider(x + 1, y + 4, 75, 5, setting.getRange(0), setting.getRange(1), setting.getRange(2), setting.getFloat(), setting, (module, settingElement) -> setting.setValue(MathUtil.denormalizeValue(settingElement.sliderValue, setting.getRange(0), setting.getRange(1), setting.getRange(2))), this);
                    element.setXOffset((int)Client.titleRenderer.getWidth(setting.getDescription()) + 6);
                    break;
                }
                case KEYBIND: {
                    element = new SettingElementKeybind(x + 1, y + 1, 10, 10, setting, (module, settingElement) -> setting.setValue(new KeyBinding(settingElement.keycode)), this);
                    element.setXOffset((int)(Client.titleRenderer.getWidth(setting.getDescription()) + 4.0f));
                    break;
                }
                case MODE: {
                    element = new SettingElementMode(x + 1, y + 1, this.getWidth() - 145, 100, 10, setting.getInt(), setting, (module, settingElement) -> setting.setValue(settingElement.mode), this);
                    break;
                }
            }
            if (element == null) {
                return;
            }
            this.elements.add(element);
        }
        else {
            this.elements.add(new ElementCategory(x - 7, y + 3, 0, 10, setting.getDescription(), true));
        }
    }
    
    public SettingsBase(final axu parentScreen) {
        this.parentScreen = parentScreen;
    }
}
