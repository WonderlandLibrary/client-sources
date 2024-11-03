package net.silentclient.client.gui.modmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.*;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.RedButtonTheme;
import net.silentclient.client.gui.theme.button.SelectedButtonTheme;
import net.silentclient.client.gui.theme.input.DefaultInputTheme;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import net.silentclient.client.mods.world.TimeChangerMod;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ModSettings extends SilentScreen {
    private final Mod mod;
    private final GuiScreen parentScreen;
    private long initTime;
    public double scrollY;
    public static SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private float scrollHeight = 0;

    public ModSettings(Mod mod, GuiScreen parentScreen) {
        if (mod == null) throw new IllegalArgumentException("Mod is null");

        this.mod = mod;
        this.parentScreen = parentScreen;
    }


    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.buttonList.clear();
        this.silentInputs.clear();
        this.initTime = System.currentTimeMillis();
        scrollY = 0;
        scrollAnimation.setValue(0);
        MenuBlurUtils.loadBlur();

        ModMenu.initBaseButtons(this.buttonList);

        this.buttonList.add(new Button(1, 3, 26, 144, 15, "Back"));
        this.buttonList.add(new Button(2, 3, this.height - 18, mod.getCategory() == ModCategory.MODS ? 70 : 144, 15, "Reset"));
        if(mod.getCategory() == ModCategory.MODS) {
            this.buttonList.add(new Button(3, 76, this.height - 18, 70, 15, mod.isEnabled() && !mod.isForceDisabled() ? "Enabled" : "Disabled", false, mod.isForceDisabled() ? new RedButtonTheme() : mod.isEnabled() ? new SelectedButtonTheme() : new DefaultButtonTheme()));
        }

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(setting.isInput()) {
                this.silentInputs.add(new Input(setting.getName(), setting.getValString()));
            }

            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                this.silentInputs.add(new Input(setting.getName(), setting.getKeybind()));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        ModMenu.drawOverlayListBase(height, mod.getName());

        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);

        ModMenu.trimContentStart(width, height);
        float settingY = 66 - scrollAnimation.getValue();
        int inputIndex = 0;
        GlStateManager.color(1, 1, 1, 1);
        MouseCursorHandler.CursorType cursorTypeCustom = mod.renderCustomComponent(3, (int) settingY, 144, height, mouseX, mouseY);

        if(cursorTypeCustom != null) {
            cursorType = cursorTypeCustom;
        }

        settingY += mod.customComponentHeight();

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(mod.getName() == "Crosshair" && Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean() && setting.getName() != "Scale" && setting.getName() != "Crosshair Color" && setting.getName() != "Vanilla Blendering") {
                continue;
            }
            if(mod.getName() == "Crosshair" && (setting.getName() == "Preset ID" || setting.getName() == "Preset Crosshair")) {
                continue;
            }
            if(mod.getName() == "Crosshair" && setting.getName() == "Vanilla Blendering" && !Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                continue;
            }
            GlStateManager.color(1, 1, 1, 1);
            int settingHeight = 15;
            if(setting.isInput()) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), 3, settingY, 12, SilentFontRenderer.FontType.TITLE);
                if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
                    StaticButton.render(150 - 3 - 65, settingY, 65, 12, "BUY PREMIUM+");
                } else {
                    settingY += settingHeight;
                    this.silentInputs.get(inputIndex).render(mouseX, mouseY, 3, settingY, 144, true);
                }
                settingY += 5;
                inputIndex++;
            }
            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), 3, settingY, 12, SilentFontRenderer.FontType.TITLE);
                this.silentInputs.get(inputIndex).render(mouseX, mouseY, 150 - 50 - 3, settingY, 50, true, new DefaultInputTheme(), true);
                settingY += 5;
                inputIndex++;
            }
            if (setting.isColor()) {
                RegularColorPicker.render(3, settingY, 144, setting.getName(), setting.getValColor().getRGB());
                if(RegularColorPicker.isHovered(mouseX, mouseY, 3, (int) settingY, 144)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
            }
            if (setting.isCheck()) {
                Switch.render(mouseX, mouseY, 3, settingY, setting.switchAnimation, setting.getValBoolean(), false);
                if(Switch.isHovered(mouseX, mouseY, 3, settingY)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }

                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), 3 + 18, settingY + (((float) 8 / 2) - ((float) 12 / 2)), 12, SilentFontRenderer.FontType.TITLE);
            }
            if(setting.isCellGrid()) {
                MouseCursorHandler.CursorType cellGridCursor = CellGrid.render(mouseX, mouseY, 3, settingY, setting);
                if(cellGridCursor != null) {
                    cursorType = cellGridCursor;
                }
                settingY += 135;
            }
            if(setting.isSlider()) {
                RegularSlider.render(3, settingY, 144, setting.getName(), setting.getMax(), setting.getValDouble());

                if(RegularSlider.isDrag(mouseX, mouseY, 3, settingY, 144) && (System.currentTimeMillis() - initTime) > 500) {
                    double diff = setting.getMax() - setting.getMin();
                    double mouse = MathHelper.clamp_double((mouseX - 3) / 144D, 0, 1);
                    double newVal = setting.getMin() + mouse * diff;
                    if(newVal != setting.getValDouble()) {
                        setting.setValDouble(newVal);
                        mod.onChangeSettingValue(setting);
                    }
                }

                settingY += 15;
            }

            if(setting.isCombo()) {
                RegularSelect.render(mouseX, mouseY, 3, settingY, 144, setting.getName(), setting.getValString());
                if(RegularSelect.nextHovered(mouseX, mouseY, 3, settingY, 144) || RegularSelect.prevHovered(mouseX, mouseY, 3, settingY)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                settingY += 15;
            }

            settingY += settingHeight;
        }

        this.scrollHeight = 66 + settingY + scrollAnimation.getValue();

        ModMenu.trimContentEnd();

        super.drawScreen(mouseX, mouseY, partialTicks);

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        scrollAnimation.setAnimation((float) scrollY, 12);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = this.scrollY;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            if (newScrollY + amountScrolled > 0)
                newScrollY += amountScrolled;
            else
                newScrollY = 0;
            if((newScrollY < scrollHeight && scrollHeight > height) || amountScrolled < 0) {
                this.scrollY = (float) newScrollY;
                if(this.scrollY < 0) {
                    this.scrollY = 0;
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(!ModMenu.mouseInContent(mouseX, mouseY, height, false)) {
            return;
        }

        float settingY = 66 - scrollAnimation.getValue();
        int inputIndex = 0;

        mod.customComponentClick(0, (int) settingY, mouseX, mouseY, mouseButton, this);

        settingY += mod.customComponentHeight();

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(mod.getName() == "Crosshair" && Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean() && setting.getName() != "Scale" && setting.getName() != "Crosshair Color" && setting.getName() != "Vanilla Blendering") {
                continue;
            }
            if(mod.getName() == "Crosshair" && (setting.getName() == "Preset ID" || setting.getName() == "Preset Crosshair")) {
                continue;
            }
            if(mod.getName() == "Crosshair" && setting.getName() == "Vanilla Blendering" && !Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                continue;
            }
            GlStateManager.color(1, 1, 1, 1);
            int settingHeight = 15;
            if(setting.isInput()) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), 3, settingY, 12, SilentFontRenderer.FontType.TITLE);
                if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
                    if(StaticButton.isHovered(mouseX, mouseY, 150 - 3 - 65, settingY, 65, 12)) {
                        Sounds.playButtonSound();
                        try {
                            Class<?> oclass = Class.forName("java.awt.Desktop");
                            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/premium")});
                        } catch (Throwable err) {
                            err.printStackTrace();
                        }
                    }
                } else {
                    this.scrollHeight += 12;
                    settingY += settingHeight;
                    this.silentInputs.get(inputIndex).onClick(mouseX, mouseY, 3, (int) settingY, 144, true);
                }
                settingY += 5;
                inputIndex++;
            }
            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), 3, settingY, 12, SilentFontRenderer.FontType.TITLE);
                this.silentInputs.get(inputIndex).onClick(mouseX, mouseY, 150 - 50 - 3, (int) settingY, 50, true);
                settingY += 5;
                inputIndex++;
            }
            if (setting.isColor() && RegularColorPicker.isHovered(mouseX, mouseY, 3, (int) settingY, 144)) {
                mc.displayGuiScreen(new ColorPicker(setting.getValColor(true), setting.isChroma(), setting.isCanChangeOpacity(), setting.getOpacity(), new ColorPickerAction() {
                    @Override
                    public void onChange(Color color, boolean chroma, int opacity) {
                        setting.setValColor(color);
                        setting.setChroma(chroma);
                        if(setting.isCanChangeOpacity()) {
                            setting.setOpacity(opacity);
                        }
                    }
                }, this));
            }
            if (setting.isCheck()) {
                if(Switch.isHovered(mouseX, mouseY, 3, settingY)) {
                    Sounds.playButtonSound();
                    setting.setValBoolean(!setting.getValBoolean());
                    mod.onChangeSettingValue(setting);
                }
            }

            if(setting.isCellGrid()) {
                CellGrid.click(mouseX, mouseY, mouseButton, setting);
                settingY += 135;
            }

            if(setting.isSlider()) {
                settingY += 15;
            }

            if(setting.isCombo()) {
                int index = 0;
                String curr = setting.getValString();
                String next = "";
                String prev = "";

                for(int i=0;i<setting.getOptions().size();i++) {
                    if(curr == setting.getOptions().get(i)) {
                        index = i;
                    }
                }
                if((index + 1) > (setting.getOptions().size() - 1)) {
                    next = setting.getOptions().get(0);
                } else {
                    next = setting.getOptions().get(index + 1);
                }

                if(!((index - 1) > (setting.getOptions().size() - 1)) && (index - 1) != -1) {
                    prev = setting.getOptions().get(index - 1);
                } else {
                    prev = setting.getOptions().get(setting.getOptions().size() - 1);
                }

                if(RegularSelect.prevHovered(mouseX, mouseY, 3, settingY)) {
                    Sounds.playButtonSound();
                    setting.setValString(prev);
                    mod.onChangeSettingValue(setting);
                }

                if(RegularSelect.nextHovered(mouseX, mouseY, 3, settingY, 144)) {
                    Sounds.playButtonSound();
                    setting.setValString(next);
                    mod.onChangeSettingValue(setting);
                }
                settingY += 15;
            }

            settingY += settingHeight;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        ModMenu.clickBaseButtons(button, this);

        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                mod.reset(false);
                break;
            case 3:
                if(mod.isForceDisabled()) {
                    break;
                }
                mod.toggle();
                button.displayString = mod.isEnabled() ? "Enabled" : "Disabled";
                if(button instanceof Button) {
                    ((Button) button).setTheme(mod.isEnabled() ? new SelectedButtonTheme() : new DefaultButtonTheme());
                }
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        int inputIndex = 0;

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if (setting.isInput()) {
                if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
                    inputIndex++;
                    continue;
                }
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                setting.setValString(this.silentInputs.get(inputIndex).getValue());
                mod.onChangeSettingValue(setting);
                inputIndex++;
            }

            if (setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                setting.setKeybind(this.silentInputs.get(inputIndex).getKey());
                mod.onChangeSettingValue(setting);
                inputIndex++;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        int inputIndex = 0;
        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if (setting.isInput()) {
                if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
                    inputIndex++;
                    continue;
                }
                setting.setValString(this.silentInputs.get(inputIndex).getValue().length() != 0 ? this.silentInputs.get(inputIndex).getValue() : setting.defaultsval);
                inputIndex++;
            }
            if (setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                setting.setKeybind(this.silentInputs.get(inputIndex).getKey() != 0 ? this.silentInputs.get(inputIndex).getKey() : setting.defaultkval);
                inputIndex++;
            }
        }
        Client.getInstance().configManager.save();
        MenuBlurUtils.unloadBlur();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(setting.isCellGrid()) {
                CellGrid.release();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return !(this.mod instanceof TimeChangerMod);
    }
}
