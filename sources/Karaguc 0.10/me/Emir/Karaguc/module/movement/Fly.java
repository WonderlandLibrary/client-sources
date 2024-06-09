package me.Emir.Karaguc.module.movement;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Fly extends Module {
    private String mode = Karaguc.instance.settingsManager.getSettingByName("Fly Mode").getValString();;

    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        //TODO: Add more fly modes
        options.add("Vanilla");
        options.add("Hypixel");
        //options.add("Test");
        //options.add("GommeHD");
        options.add("Mineplex/Gwen");
        //options.add("Best");
        //options.add("DAC");
        //options.add("Reflex");
        //options.add("TeamKyudoo");
        options.add("HAC");
        //options.add("Gcheat/BAC/Badlion");
        //options.add("NCP");
        options.add("Minesucht");
        options.add("Rewinside");
        //options.add("Fiona");
        //options.add("CubecraftFly")
        //options.add("AACP");
        //options.add("Advanced");
        options.add("Normal");
        options.add("FaithfulMC");
        Karaguc.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Normal", options));
        Karaguc.instance.settingsManager.rSetting(new Setting("UnKick", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("Glide", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("HypixelFlyBooster", this, 3.2, 0, 11, true));
        Karaguc.instance.settingsManager.rSetting(new Setting("FlySpeed", this, 1, 0, 10, true));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

        if(Karaguc.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel")) {
        
        }

        if(Karaguc.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vanilla")) {
        	this.setDisplayName("Fly \u00a77Vanilla");
        	mc.thePlayer.onGround = true;
        	mc.thePlayer.motionY = 0.001;
        }

            if(Karaguc.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("CubecrftFly")) {
        this.setDisplayName("Fly \u00a77CubecraftFly");

        }

    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.capabilities.isFlying = false;
    }
}
