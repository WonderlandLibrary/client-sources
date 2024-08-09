/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.ButtonUsage;
import net.java.games.input.Component;
import net.java.games.input.ElementType;
import net.java.games.input.GenericDesktopUsage;
import net.java.games.input.KeyboardUsage;
import net.java.games.input.OSXEvent;
import net.java.games.input.OSXHIDDevice;
import net.java.games.input.UsagePage;
import net.java.games.input.UsagePair;

final class OSXHIDElement {
    private final OSXHIDDevice device;
    private final UsagePair usage_pair;
    private final long element_cookie;
    private final ElementType element_type;
    private final int min;
    private final int max;
    private final Component.Identifier identifier;
    private final boolean is_relative;

    public OSXHIDElement(OSXHIDDevice oSXHIDDevice, UsagePair usagePair, long l, ElementType elementType, int n, int n2, boolean bl) {
        this.device = oSXHIDDevice;
        this.usage_pair = usagePair;
        this.element_cookie = l;
        this.element_type = elementType;
        this.min = n;
        this.max = n2;
        this.identifier = this.computeIdentifier();
        this.is_relative = bl;
    }

    private final Component.Identifier computeIdentifier() {
        if (this.usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP) {
            return ((GenericDesktopUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        if (this.usage_pair.getUsagePage() == UsagePage.BUTTON) {
            return ((ButtonUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        if (this.usage_pair.getUsagePage() == UsagePage.KEYBOARD_OR_KEYPAD) {
            return ((KeyboardUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        return null;
    }

    final Component.Identifier getIdentifier() {
        return this.identifier;
    }

    final long getCookie() {
        return this.element_cookie;
    }

    final ElementType getType() {
        return this.element_type;
    }

    final boolean isRelative() {
        return this.is_relative && this.identifier instanceof Component.Identifier.Axis;
    }

    final boolean isAnalog() {
        return this.identifier instanceof Component.Identifier.Axis && this.identifier != Component.Identifier.Axis.POV;
    }

    private UsagePair getUsagePair() {
        return this.usage_pair;
    }

    final void getElementValue(OSXEvent oSXEvent) throws IOException {
        this.device.getElementValue(this.element_cookie, oSXEvent);
    }

    final float convertValue(float f) {
        if (this.identifier == Component.Identifier.Axis.POV) {
            switch ((int)f) {
                case 0: {
                    return 0.25f;
                }
                case 1: {
                    return 0.375f;
                }
                case 2: {
                    return 0.5f;
                }
                case 3: {
                    return 0.625f;
                }
                case 4: {
                    return 0.75f;
                }
                case 5: {
                    return 0.875f;
                }
                case 6: {
                    return 1.0f;
                }
                case 7: {
                    return 0.125f;
                }
                case 8: {
                    return 0.0f;
                }
            }
            return 0.0f;
        }
        if (this.identifier instanceof Component.Identifier.Axis && !this.is_relative) {
            if (this.min == this.max) {
                return 0.0f;
            }
            if (f > (float)this.max) {
                f = this.max;
            } else if (f < (float)this.min) {
                f = this.min;
            }
            return 2.0f * (f - (float)this.min) / (float)(this.max - this.min) - 1.0f;
        }
        return f;
    }
}

