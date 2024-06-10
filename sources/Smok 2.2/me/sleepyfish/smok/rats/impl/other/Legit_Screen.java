package me.sleepyfish.smok.rats.impl.other;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.impl.visual.*;
import me.sleepyfish.smok.rats.impl.blatant.*;
import me.sleepyfish.smok.rats.impl.useless.Spin;

// Class from SMok Client by SleepyFish
public class Legit_Screen extends Rat {

    private boolean[] enabled = new boolean[0];

    public Legit_Screen() {
        super("Legit Screen", Rat.Category.Other, "Makes your screen look legit");
    }

    @Override
    public void onEnableEvent() {

        this.enabled = new boolean[] {
                Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled() && Aura.showTarget.isEnabled(),
                Smok.inst.ratManager.getRatByClass(Chams.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Target_Hud.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Esp.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Text_Gui.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Add_Friends.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Detector.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Blink.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Bunny_Hop.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Scaffold.class).isEnabled(),
                Gui.blatantMode.isEnabled(),
                Smok.inst.ratManager.getRatByClass(Detector.class).isEnabled(),
                Smok.inst.ratManager.getRatByClass(Animations.class).isEnabled()
        };

        if (this.enabled[0]) {
            Smok.inst.ratManager.getRatByClass(Aura.class).toggle();
        }

        if (this.enabled[1]) {
            Aura.showTarget.toggle();
        }

        if (this.enabled[2]) {
            Smok.inst.ratManager.getRatByClass(Chams.class).toggle();
        }

        if (this.enabled[3]) {
            Smok.inst.ratManager.getRatByClass(Target_Hud.class).toggle();
        }

        if (this.enabled[4]) {
            Smok.inst.ratManager.getRatByClass(Esp.class).toggle();
        }

        if (this.enabled[5]) {
            Smok.inst.ratManager.getRatByClass(Text_Gui.class).toggle();
        }

        if (this.enabled[6]) {
            Smok.inst.ratManager.getRatByClass(Add_Friends.class).toggle();
        }

        if (this.enabled[7]) {
            Smok.inst.ratManager.getRatByClass(Detector.class).toggle();
        }

        if (this.enabled[8]) {
            Smok.inst.ratManager.getRatByClass(Blink.class).toggle();
        }

        if (this.enabled[9]) {
            Smok.inst.ratManager.getRatByClass(Bunny_Hop.class).toggle();
        }

        if (this.enabled[10]) {
            Smok.inst.ratManager.getRatByClass(Scaffold.class).toggle();
        }

        if (this.enabled[11]) {
            Gui.blatantMode.toggle();
        }

        if (this.enabled[12]) {
            Smok.inst.ratManager.getRatByClass(Detector.class).toggle();
        }

        if (this.enabled[13]) {
            Smok.inst.ratManager.getRatByClass(Animations.class).toggle();
        }
    }

    @Override
    public void onDisableEvent() {
        if (this.enabled[0]) {
            Smok.inst.ratManager.getRatByClass(Aura.class).toggle();
        }

        if (this.enabled[1]) {
            Aura.showTarget.toggle();
        }

        if (this.enabled[2]) {
            Smok.inst.ratManager.getRatByClass(Chams.class).toggle();
        }

        if (this.enabled[3]) {
            Smok.inst.ratManager.getRatByClass(Target_Hud.class).toggle();
        }

        if (this.enabled[4]) {
            Smok.inst.ratManager.getRatByClass(Esp.class).toggle();
        }

        if (this.enabled[5]) {
            Smok.inst.ratManager.getRatByClass(Text_Gui.class).toggle();
        }

        if (this.enabled[6]) {
            Smok.inst.ratManager.getRatByClass(Add_Friends.class).toggle();
        }

        if (this.enabled[7]) {
            Smok.inst.ratManager.getRatByClass(Detector.class).toggle();
        }

        if (this.enabled[8]) {
            Smok.inst.ratManager.getRatByClass(Blink.class).toggle();
        }

        if (this.enabled[9]) {
            Smok.inst.ratManager.getRatByClass(Bunny_Hop.class).toggle();
        }

        if (this.enabled[10]) {
            Smok.inst.ratManager.getRatByClass(Scaffold.class).toggle();
        }

        if (this.enabled[11]) {
            Gui.blatantMode.toggle();
        }

        if (this.enabled[12]) {
            Smok.inst.ratManager.getRatByClass(Detector.class).toggle();
        }

        if (this.enabled[13]) {
            Smok.inst.ratManager.getRatByClass(Animations.class).toggle();
        }
    }

}