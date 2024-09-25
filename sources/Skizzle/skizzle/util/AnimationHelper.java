/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import skizzle.util.Timer;

public class AnimationHelper
extends Timer {
    public double stage;
    public Directions direction = Directions.IN;

    public boolean decrease(double Nigga, double Nigga2) {
        AnimationHelper Nigga3;
        boolean Nigga4 = false;
        if (Nigga3.stage > Nigga2) {
            Nigga3.stage -= Nigga;
            Nigga4 = true;
        }
        if (Nigga3.stage < Nigga2) {
            Nigga3.stage = Nigga2;
        }
        return Nigga4;
    }

    public boolean increase(double Nigga, double Nigga2) {
        AnimationHelper Nigga3;
        boolean Nigga4 = false;
        if (Nigga3.stage < Nigga2) {
            Nigga3.stage += Nigga;
            Nigga4 = true;
        }
        if (Nigga3.stage > Nigga2) {
            Nigga3.stage = Nigga2;
        }
        return Nigga4;
    }

    public static {
        throw throwable;
    }

    public AnimationHelper() {
        AnimationHelper Nigga;
    }

    public static enum Directions {
        IN,
        OUT,
        LEFT,
        RIGHT,
        UP,
        DOWN;


        public Directions() {
            Directions Nigga;
        }
    }
}

