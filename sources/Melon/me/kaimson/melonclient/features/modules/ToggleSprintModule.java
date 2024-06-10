package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.*;

public class ToggleSprintModule extends DefaultModuleRenderer
{
    private boolean toggled;
    private int wasPressed;
    private int keyHoldTicks;
    private static ToggleSprintModule instance;
    
    public ToggleSprintModule() {
        super("Toggle Sprint", 16);
        this.keyHoldTicks = 7;
        ToggleSprintModule.instance = this;
    }
    
    @Override
    public String getFormat() {
        return "[%value%]";
    }
    
    @Override
    public Object getValue() {
        return this.getDisplayText();
    }
    
    @Override
    public Object getDummy() {
        return "Sprinting (Toggled)";
    }
    
    public void updateMovement() {
        if (ModuleConfig.INSTANCE.isEnabled(this)) {
            if (this.mc.t.ae.d()) {
                if (this.wasPressed == 0) {
                    if (this.toggled) {
                        this.wasPressed = -1;
                    }
                    else if (this.mc.h.bA.b) {
                        this.wasPressed = this.keyHoldTicks + 1;
                    }
                    else {
                        this.wasPressed = 1;
                    }
                    this.toggled = !this.toggled;
                }
                else if (this.wasPressed > 0) {
                    ++this.wasPressed;
                }
            }
            else {
                if (this.keyHoldTicks > 0 && this.wasPressed > this.keyHoldTicks) {
                    this.toggled = false;
                }
                this.wasPressed = 0;
            }
        }
        else {
            this.toggled = false;
        }
        if (this.toggled) {
            this.mc.h.d(true);
        }
    }
    
    private String getDisplayText() {
        String displayText = null;
        final boolean isFlying = this.mc.h.bA.b;
        final boolean isSprintHeld = this.mc.t.ae.d();
        final boolean isSprinting = this.mc.h.aw();
        if (isFlying) {
            displayText = "Flying";
        }
        else if (this.toggled) {
            if (isSprintHeld) {
                displayText = "Sprinting (Key Held)";
            }
            else {
                displayText = "Sprinting (Toggled)";
            }
        }
        else if (isSprinting) {
            displayText = "Sprinting (Vanilla)";
        }
        return displayText;
    }
    
    public static ToggleSprintModule getInstance() {
        return ToggleSprintModule.instance;
    }
}
