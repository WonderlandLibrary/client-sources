package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;

public class FPSLimiter extends Module{
    public static SliderSetting minFps;
    public static SliderSetting defaultFps;
    
    public ModSetting[] getModSettings() {
        minFps = new SliderSetting<Number>("Min FPS", ClientSettings.minFps, 1, 255, 1, ValueFormat.INT);
        defaultFps = new SliderSetting<Number>("Default/Restore FPS", ClientSettings.defaultFps, 1, 255, 1, ValueFormat.INT);
        return new ModSetting[] { minFps, defaultFps };
    }
    
        public FPSLimiter() {
            super("NoFocusFPS", Keyboard.KEY_NONE, Category.RENDER, "Limits the FPS when unfocused");
        }
        
        public void onEnable() {
            super.onEnable();
        }
        
        public void onDisable() {
            super.onDisable();
        }

    }
