/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;

@Module.Mod(displayName="SwordTransitions")
public class SwordTransitions
extends Module {
    @Option.Op(min=-2.0, max=2.0, increment=0.1)
    private double x = 0.0;
    @Option.Op(min=-2.0, max=2.0, increment=0.1)
    private double y = 0.0;
    @Option.Op(min=-2.0, max=2.0, increment=0.1)
    private double z = 0.0;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}

