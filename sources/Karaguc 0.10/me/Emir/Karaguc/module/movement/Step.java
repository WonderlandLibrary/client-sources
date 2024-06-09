package me.Emir.Karaguc.module.movement;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventStep;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Step extends Module {
    private boolean resetNextTick;
    private double preY;

    public Step() {
        super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        //TODO: Add more Step modes
        options.add("Reverse/NCP");
        options.add("Normal");
        options.add("AAC 3.3.9");
        //options.add("AAC");
        Karaguc.instance.settingsManager.rSetting(new Setting("Step Mode", this, "Reverse/NCP", options));
    }

    @EventTarget
    public void onStep(EventStep event) {
        if(Karaguc.instance.settingsManager.getSettingByName("Step Mode").getValString().equalsIgnoreCase("NCP")) {
        	
            if(event.getEntity() != mc.thePlayer)
                return;
            if(event.getType() == EventStep.Type.PRE) {
                preY = mc.thePlayer.posY;
                if(canStep())
                    event.setStepHeight(1.5F);
            } else {
                if(event.getStepHeight() != 1.5F)
                    return;
                double offset = mc.thePlayer.boundingBox.minY - preY;
                if(offset > .6D) {
                    double[] offsets = {.42D, (offset < 1D && offset > .8D) ? .73D : .75D, 1D, 1.16D, 1.23D, 1.2D};
                    for(int i = 0; i < (offset > 1D ? offsets.length : 2); i++)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offsets[i], mc.thePlayer.posZ, false));
                    mc.timer.timerSpeed = (offset > 1D) ? .15F : .37F;
                    resetNextTick = true;
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(Karaguc.instance.settingsManager.getSettingByName("Step Mode").getValString().equalsIgnoreCase("NCP")) {
        	
        	this.setDisplayName("Step \u00a77NCP");
        	
            if(resetNextTick) {
                mc.timer.timerSpeed = 1F;
                resetNextTick = false;
            }
        }

        if(Karaguc.instance.settingsManager.getSettingByName("Step Mode").getValString().equalsIgnoreCase("Normal")) {
        	this.setDisplayName("Step \u00a77Normal");
            mc.thePlayer.stepHeight = 1.5F;
        }
    }

    private boolean canStep() {
        return mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && mc.thePlayer.motionY < 0 && !mc.thePlayer.movementInput.jump;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(Karaguc.instance.settingsManager.getSettingByName("Step Mode").getValString().equalsIgnoreCase("Normal"))
            mc.thePlayer.stepHeight = .5F;
    }
}
