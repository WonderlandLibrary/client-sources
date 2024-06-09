package me.Emir.Karaguc.module.player;

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

public class Timer extends Module {
    public Timer() {
        super("InvCleaner", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("AAC");
        Karaguc.instance.settingsManager.rSetting(new Setting("Timer Mode", this, "Normal", modes));
        Karaguc.instance.settingsManager.rSetting(new Setting("Selecter", this, 2, 1, 10, true));
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isBlocking()){
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.UP));
        }
    }
}
