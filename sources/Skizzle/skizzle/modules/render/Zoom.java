/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.modules.render;

import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.settings.KeybindSetting;
import skizzle.util.AnimationHelper;
import skizzle.util.Timer;

public class Zoom
extends Module {
    public float preFov;
    public double fpsNumbers = 0.0;
    public Timer timer;
    public boolean setFov;
    public double fpsOverall = 0.0;
    public KeybindSetting zoomBind;
    public AnimationHelper animation = new AnimationHelper();

    @Override
    public void onEnable() {
        Zoom Nigga;
        Nigga.preFov = Nigga.mc.gameSettings.fovSetting;
        Nigga.animation.stage = Nigga.preFov;
    }

    public Zoom() {
        super(Qprot0.0("\uf462\u71c4\ucf23\ua7e9"), 0, Module.Category.RENDER);
        Zoom Nigga;
        Nigga.timer = new Timer();
        Nigga.zoomBind = new KeybindSetting(Qprot0.0("\uf462\u71c4\ucf23\ua7e9\u27af\u71c9\u8c2a\u980c"), 0);
        Nigga.addSettings(Nigga.zoomBind);
    }

    @Override
    public void onDisable() {
        Zoom Nigga;
        Nigga.mc.gameSettings.fovSetting = Nigga.preFov;
        Nigga.mc.gameSettings.smoothCamera = false;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        Zoom Nigga2;
        if (Nigga instanceof EventRenderGUI && Nigga2.animation.stage != 0.0) {
            double Nigga3 = Client.delay * 5.0;
            if (Keyboard.isKeyDown((int)Nigga2.zoomBind.getKeyCode())) {
                Nigga2.mc.gameSettings.smoothCamera = true;
                if (Nigga2.animation.stage > 30.0) {
                    Nigga2.animation.stage -= Nigga3;
                }
            } else {
                if (Nigga2.animation.stage < (double)Nigga2.preFov) {
                    Nigga2.animation.stage += Nigga3;
                }
                Nigga2.mc.gameSettings.smoothCamera = false;
            }
            if (Nigga2.animation.stage < 30.0) {
                Nigga2.animation.stage = 30.0;
            }
            if (Nigga2.animation.stage > (double)Nigga2.preFov) {
                Nigga2.animation.stage = Nigga2.preFov;
            }
        }
        if (Nigga2.animation.stage == 0.0) {
            Nigga2.animation.stage = 30.0;
        }
        Nigga2.mc.gameSettings.fovSetting = (float)Nigga2.animation.stage;
    }
}

