package me.finz0.osiris.gui;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.gui.clickgui.ClickGui;
import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.elements.*;
import me.finz0.osiris.gui.clickgui.listener.CheckButtonClickListener;
import me.finz0.osiris.gui.clickgui.listener.ComponentClickListener;
import me.finz0.osiris.gui.clickgui.listener.SliderChangeListener;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.GLUtils;

import java.util.ArrayList;

public class GuiManager extends ClickGui {

    public void Initialization() {
        addCategoryPanels();
    }

    public static ArrayList<Component> tickListeners = new ArrayList<>();

    private void addCategoryPanels() {
        int right = GLUtils.getScreenWidth();
        int framePosX = 20;
        int framePosY = 20;

        for (Module.Category category : Module.Category.values()) {
            int frameHeight = 170;
            int frameWidth = 100;
            int hacksCount = 0;
            String name = Character.toString(category.toString().toLowerCase().charAt(0)).toUpperCase() + category.toString().toLowerCase().substring(1);
            Frame frame = new Frame(framePosX, framePosY, frameWidth, frameHeight, name);

            for (final Module mod : ModuleManager.getModules()) {
                if (mod.getCategory() == category) {
                    final ExpandingButton expandingButton = new ExpandingButton(0, 0, frameWidth, 14, frame, mod.getName(), mod) {

                        @Override
                        public void onUpdate() {
                            setEnabled(mod.isEnabled());
                        }
                    };
                    expandingButton.addListner((component, button) -> mod.toggle());
                    expandingButton.setEnabled(mod.isEnabled());

                    if (AuroraMod.getInstance().settingsManager.getSettingsByMod(mod) != null) {
                        for (Setting s : AuroraMod.getInstance().settingsManager.getSettingsByMod(mod)) {
                            if (s.isCheck()) {
                                CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14, expandingButton, s.getDisplayName(), s.getValBoolean(), null);
                                button.addListeners(checkButton -> {
                                    for (Setting value1 : AuroraMod.getInstance().settingsManager.getSettingsByMod(mod)) {
                                        if (value1.getDisplayName().equals(s.getDisplayName())) {
                                            value1.setValBoolean(checkButton.isEnabled());
                                        }
                                    }
                                });
                                expandingButton.addComponent(button);

                            } else if (s.isSlider()) {
                                Slider slider = new Slider(s.getMin(), s.getMax(), s.getValDouble(), expandingButton, s.getDisplayName());
                                slider.addListener(slider1 -> {
                                    for (Setting value1 : AuroraMod.getInstance().settingsManager.getSettingsByMod(mod)) {
                                        if (value1.getDisplayName().equals(s.getDisplayName())) {
                                            value1.setValDouble(slider1.getValue());
                                        }
                                    }
                                });

                                expandingButton.addComponent(slider);


                            } else if (s.isCombo()) {
                                Dropdown dropdown = new Dropdown(0, 0, frameWidth, 14, frame, s.getDisplayName());

                                for(String mode : s.getOptions()) {
                                    CheckButton button = new CheckButton(0, 0,
                                            expandingButton.getDimension().width, 14, expandingButton,
                                            mode, s.getValString().equals(mode), s);

                                    button.addListeners(checkButton -> {
                                        for(String mode1 : s.getOptions()) {
                                            if (mode1.equals(mode)) {
                                                s.setValString(mode1);
                                            }
                                        }
                                    });
                                    dropdown.addComponent(button);
                                }
                                expandingButton.addComponent(dropdown);
                            }
                        }
                    }
                    KeybindMods keybind = new KeybindMods(0, 0, 8, 14, expandingButton, mod);
                    expandingButton.addComponent(keybind);
                    frame.addComponent(expandingButton);
                    hacksCount++;
                }
            }

            if (framePosX + frameWidth + 10 < right) {
                framePosX += frameWidth + 10;
            } else {
                framePosX = 20;
                framePosY += 60;
            }

            frame.setMaximizible(true);
            frame.setPinnable(true);
            this.addFrame(frame);
        }

        Frame frame = new Frame(framePosX, framePosY, 100, 24, "FPS");

        Button button = new Button(0, 0, 100, 14, frame, "FPS: ");
        tickListeners.add(button);
        //Text text = new Text(0, 0, 100, 14, frame, new String[]{"FPS: "});

        frame.addComponent(button);
        frame.setPinnable(true);
        frame.setPinned(true);
        frame.setMaximizible(true);
        this.addFrame(frame);

    }

}
