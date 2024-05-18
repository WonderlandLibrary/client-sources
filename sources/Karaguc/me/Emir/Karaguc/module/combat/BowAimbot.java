package me.Emir.Karaguc.module.combat;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class BowAimbot extends Module {
    public BowAimbot() {
        super("NoSlowDown", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("AAC");
        modes.add("Hypixel");
        Karaguc.instance.settingsManager.rSetting(new Setting("NoSlowDown Mode", this, "Normal", modes));
        Karaguc.instance.settingsManager.rSetting(new Setting("VeryFaster", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("Slow", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("Normal", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("NoSwing", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("AutoAimOnSword", this, false));
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isBlocking()){
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.UP));
        }
    }
}
