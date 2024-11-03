package net.silentclient.client.mods.world;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventPlaySound;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class SoundsMod extends Mod {
    public SoundsMod() {
        super("Sounds", ModCategory.MODS, "silentclient/icons/mods/soundsmod.png");
    }

    @Override
    public void setup() {
        this.addSliderSetting("Note", this, 100, 0, 100, true);
        this.addSliderSetting("Mobs", this, 100, 0, 100, true);
        this.addSliderSetting("Portal", this, 100, 0, 100, true);
        this.addSliderSetting("Records", this, 100, 0, 100, true);
        this.addSliderSetting("Step", this, 100, 0, 100, true);
        this.addSliderSetting("TNT", this, 100, 0, 100, true);
    }

    @EventTarget
    public void onPlaySound(EventPlaySound event) {
        float noteVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "Note").getValFloat();
        float mobsVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "Mobs").getValFloat();
        float recordsVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "Records").getValFloat();
        float portalVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "Portal").getValFloat();
        float stepVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "Step").getValFloat();
        float tntVolume = Client.getInstance().getSettingsManager().getSettingByName(this, "TNT").getValFloat();

        if(event.getSoundName().startsWith("note")) {
            event.setVolume(noteVolume / 100F);
        }

        if(event.getSoundName().equals("game.tnt.primed") || event.getSoundName().equals("random.explode") || event.getSoundName().equals("creeper.primed")) {
            event.setVolume(tntVolume / 100F);
        }

        if(event.getSoundName().contains("mob")) {
            event.setVolume(mobsVolume / 100F);
        }

        if(event.getSoundName().startsWith("records")) {
            event.setVolume(recordsVolume / 100F);
        }

        if(event.getSoundName().startsWith("step")) {
            event.setVolume(stepVolume / 100F);
        }

        if(event.getSoundName().startsWith("portal")) {
            event.setVolume(portalVolume / 100F);
        }
    }
}
