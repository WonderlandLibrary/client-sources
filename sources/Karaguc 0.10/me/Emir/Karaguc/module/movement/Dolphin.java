package me.Emir.Karaguc.module.movement;

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

public class Dolphin extends Module {
    public Dolphin() {
        super("Dolphin", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("AAC");
        modes.add("Hypixel");
        modes.add("Normal");
        modes.add("NCP 11Block L");
        Karaguc.instance.settingsManager.rSetting(new Setting("Dolphin Mode", this, "Normal", modes));
        Karaguc.instance.settingsManager.rSetting(new Setting("Sponge", this, false));
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isBlocking()){
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.UP));
        }
    }
}
