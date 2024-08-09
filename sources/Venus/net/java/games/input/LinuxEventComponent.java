/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.LinuxAbsInfo;
import net.java.games.input.LinuxAxisDescriptor;
import net.java.games.input.LinuxEventDevice;
import net.java.games.input.LinuxNativeTypesMap;

final class LinuxEventComponent {
    private final LinuxEventDevice device;
    private final Component.Identifier identifier;
    private final Controller.Type button_trait;
    private final boolean is_relative;
    private final LinuxAxisDescriptor descriptor;
    private final int min;
    private final int max;
    private final int flat;
    static final boolean $assertionsDisabled = !(class$net$java$games$input$LinuxEventComponent == null ? (class$net$java$games$input$LinuxEventComponent = LinuxEventComponent.class$("net.java.games.input.LinuxEventComponent")) : class$net$java$games$input$LinuxEventComponent).desiredAssertionStatus();
    static Class class$net$java$games$input$LinuxEventComponent;

    public LinuxEventComponent(LinuxEventDevice linuxEventDevice, Component.Identifier identifier, boolean bl, int n, int n2) throws IOException {
        this.device = linuxEventDevice;
        this.identifier = identifier;
        this.button_trait = n == 1 ? LinuxNativeTypesMap.guessButtonTrait(n2) : Controller.Type.UNKNOWN;
        this.is_relative = bl;
        this.descriptor = new LinuxAxisDescriptor();
        this.descriptor.set(n, n2);
        if (n == 3) {
            LinuxAbsInfo linuxAbsInfo = new LinuxAbsInfo();
            this.getAbsInfo(linuxAbsInfo);
            this.min = linuxAbsInfo.getMin();
            this.max = linuxAbsInfo.getMax();
            this.flat = linuxAbsInfo.getFlat();
        } else {
            this.min = Integer.MIN_VALUE;
            this.max = Integer.MAX_VALUE;
            this.flat = 0;
        }
    }

    public final LinuxEventDevice getDevice() {
        return this.device;
    }

    public final void getAbsInfo(LinuxAbsInfo linuxAbsInfo) throws IOException {
        if (!$assertionsDisabled && this.descriptor.getType() != 3) {
            throw new AssertionError();
        }
        this.device.getAbsInfo(this.descriptor.getCode(), linuxAbsInfo);
    }

    public final Controller.Type getButtonTrait() {
        return this.button_trait;
    }

    public final Component.Identifier getIdentifier() {
        return this.identifier;
    }

    public final LinuxAxisDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final boolean isRelative() {
        return this.is_relative;
    }

    public final boolean isAnalog() {
        return this.identifier instanceof Component.Identifier.Axis && this.identifier != Component.Identifier.Axis.POV;
    }

    final float convertValue(float f) {
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

    final float getDeadZone() {
        return (float)this.flat / (2.0f * (float)(this.max - this.min));
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

