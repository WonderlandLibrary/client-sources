package me.sleepyfish.smok.rats.impl.useless;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.settings.ModeSetting;

// Class from SMok Client by SleepyFish
public class Seed_Structure extends Rat {

    ModeSetting<Enum<?>> mode;

    public Seed_Structure() {
        super("Seed Structure", Rat.Category.Useless, "Opens a URL that shows where structures are located");
    }

    @Override
    public void setup() {
        this.addSetting(this.mode = new ModeSetting<>("Mode", Seed_Structure.modes.Mineshaft));
    }

    @Override
    public void onEnableEvent() {
        String middleUrl = "?";
        if (this.mode.getMode() == Seed_Structure.modes.NetherFortress) {
            middleUrl = "nether-fortress";
        }

        if (this.mode.getMode() == Seed_Structure.modes.JungleTemple) {
            middleUrl = "jungle-temple";
        }

        if (this.mode.getMode() == Seed_Structure.modes.DesertTemple) {
            middleUrl = "desert-temple";
        }

        if (this.mode.getMode() == Seed_Structure.modes.Slime) {
            middleUrl = "slime";
        }

        if (this.mode.getMode() == Seed_Structure.modes.Village) {
            middleUrl = "village";
        }

        if (this.mode.getMode() == Seed_Structure.modes.Igloo) {
            middleUrl = "igloo";
        }

        if (this.mode.getMode() == Seed_Structure.modes.Mineshaft) {
            middleUrl = "mineshaft";
        }

        if (middleUrl != "?") {
            String txt;
            try {
                String url = "https://www.chunkbase.com/apps/" + middleUrl + "-finder#";
                txt = url + ClientUtils.getSeed();
                ClientUtils.addMessage("Registered Seed.");
            } catch (Exception ignored) {
                txt = "Couldn't find seed.";
                ClientUtils.addMessage("Failed getting Seed.");
            }

            if (txt != "Couldn't find seed.") {
                ClientUtils.openLink(txt);
                ClientUtils.addMessage("Opening URL that belongs to the Seed.");
            }

            this.toggle();
        }
    }

    public enum modes {
        Mineshaft, NetherFortress,
        JungleTemple, DesertTemple,
        Slime, Village, Igloo;
    }

}