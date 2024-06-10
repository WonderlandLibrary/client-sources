package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import org.lwjgl.opengl.*;

public class PerspectiveModule extends Module
{
    public static PerspectiveModule INSTANCE;
    private final Setting freelook;
    private final Setting behindYou;
    public float cameraYaw;
    public float cameraPitch;
    private int previousView;
    private boolean wasDown;
    private int behindYouPrevView;
    private boolean behindYouWasDown;
    
    public PerspectiveModule() {
        super("Perspective", 22);
        new Setting(this, "Freelook Options");
        this.freelook = new Setting(this, "Keybind", "freelook.keybind").setDefault(new KeyBinding(47));
        new Setting(this, "Behind You Options");
        this.behindYou = new Setting(this, "Keybind", "behindyou.keybind").setDefault(new KeyBinding(56));
        PerspectiveModule.INSTANCE = this;
    }
    
    public void onTick() {
        final boolean active = ((KeyBinding)this.behindYou.getObject()).isKeyDown();
        if (this.behindYouWasDown != active) {
            if (!(this.behindYouWasDown = active)) {
                ave.A().t.aB = this.behindYouPrevView;
            }
            this.behindYouPrevView = ave.A().t.aB;
            if (this.behindYouWasDown && ave.A().t.aB != 2) {
                ave.A().t.aB = 2;
            }
        }
    }
    
    public boolean isHeld() {
        final KeyBinding keyBinding = (KeyBinding)this.freelook.getObject();
        final boolean active = keyBinding.isKeyDown();
        if (!active) {
            this.cameraYaw = ave.A().h.y;
            this.cameraPitch = ave.A().h.z;
        }
        if (this.wasDown != active) {
            if (!(this.wasDown = active)) {
                ave.A().t.aB = this.previousView;
            }
            this.previousView = ave.A().t.aB;
            if (this.wasDown && (ave.A().t.aB == 0 || ave.A().t.aB == 2)) {
                ave.A().t.aB = 1;
            }
        }
        return active;
    }
    
    public static boolean overrideMouse() {
        if (ave.A().w && Display.isActive()) {
            if (!PerspectiveModule.INSTANCE.isHeld()) {
                return true;
            }
            ave.A().u.c();
            final float f1 = ave.A().t.a * 0.6f + 0.2f;
            final float f2 = f1 * f1 * f1 * 8.0f;
            final float f3 = ave.A().u.a * f2;
            final float f4 = ave.A().u.b * f2;
            final PerspectiveModule instance = PerspectiveModule.INSTANCE;
            instance.cameraYaw += f3 * 0.15f;
            final PerspectiveModule instance2 = PerspectiveModule.INSTANCE;
            instance2.cameraPitch += f4 * 0.15f;
            if (PerspectiveModule.INSTANCE.cameraPitch > 90.0f) {
                PerspectiveModule.INSTANCE.cameraPitch = 90.0f;
            }
            if (PerspectiveModule.INSTANCE.cameraPitch < -90.0f) {
                PerspectiveModule.INSTANCE.cameraPitch = -90.0f;
            }
            ave.A().g.m();
        }
        return false;
    }
    
    public static float getCameraYaw() {
        return PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraYaw : ave.A().h.y;
    }
    
    public static float getCameraPitch() {
        return PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraPitch : ave.A().h.z;
    }
}
