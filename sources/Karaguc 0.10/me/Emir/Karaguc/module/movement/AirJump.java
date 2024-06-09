package me.Emir.Karaguc.module.movement;

import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import org.lwjgl.input.Keyboard;

// Coded By : EmirProHD  //Real//
    public class AirJump extends Module {
        public AirJump() {
            super("AirJump", Keyboard.KEY_NONE, Category.MOVEMENT);
        }

    //public void setup() {
        //ArrayList<String> options = new ArrayList<>();
        //TODO: Add more AirJump modes
        //options.add("Vanilla");
        //options.add("Hypixel");
        //options.add("Test");
        //options.add("NCP")
        //Karaguc.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "NCP", options));

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        if(!this.isToggled())
            return;

        if(mc.gameSettings.keyBindJump.isPressed()){
            mc.thePlayer.motionX *= 1.5;
            mc.thePlayer.motionY = 0.4;
            mc.thePlayer.motionZ *= 1.5;
            mc.thePlayer.onGround =  true;
        }

    }
}
