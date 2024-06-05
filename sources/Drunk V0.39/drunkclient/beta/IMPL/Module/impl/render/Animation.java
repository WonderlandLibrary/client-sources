/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import java.awt.Color;

public class Animation
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])AnimationMode.values(), (Enum)AnimationMode.Swank);
    public static Option<Boolean> newSwing = new Option<Boolean>("New Swing", "New Swing", false);
    public static Numbers<Double> speed = new Numbers<Double>("Swing Speed", "Swing Speed", 1.0, 0.1, 2.0, 0.1);
    public static Numbers<Double> x = new Numbers<Double>("Item X", "Item X", 0.0, -1.0, 1.0, 0.01);
    public static Numbers<Double> y = new Numbers<Double>("Item Y", "Item Y", 0.0, -1.0, 1.0, 0.01);
    public static Numbers<Double> z = new Numbers<Double>("Item Z", "Item Z", 0.0, -1.0, 1.0, 0.01);

    public Animation() {
        super("Animation", new String[]{"anim"}, Type.RENDER, "Change sword animations while blocking");
        this.setColor(new Color(159, 190, 192).getRGB());
        this.addValues(this.mode, speed, x, y, z, newSwing);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getModeAsString());
    }

    public static enum AnimationMode {
        Swank,
        Swing,
        Swang,
        Swong,
        Swaing,
        Punch,
        Stella,
        Styles,
        Slide,
        Interia,
        Ethereal,
        Sigma,
        Exhibition,
        Smooth,
        Spinning;

    }
}

