package me.Emir.Karaguc.module.forgemods;

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

public class SpotifyMod extends Module {
    public SpotifyMod() { super("SpotifyMod", Keyboard.KEY_NONE, Category.ForgeMods); }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        //TODO: Add more AutoGgMod modes
        options.add("NewDesign");
        options.add("SpotifyDesign");
        Karaguc.instance.settingsManager.rSetting(new Setting("SpotifyMod Mode", this, "GG", options));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isBlocking()){
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.UP));
        }
    }
}
