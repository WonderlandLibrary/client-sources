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

public class AutoMover extends Module {
    public AutoMover() {
        super("AutoMover", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("AutoWalk");
        modes.add("AutoJump");
        modes.add("Normal");
        modes.add("AutoDance");
        modes.add("AutoFarm");
        modes.add("AutoLeave");
        Karaguc.instance.settingsManager.rSetting(new Setting("AutoMover Mode", this, "Normal", modes));
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isBlocking()){
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.UP));
        }
    }
}
