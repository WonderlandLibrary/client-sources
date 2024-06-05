package koks.modules.impl.world;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 11:08
 */
public class FastBridge extends Module {

    public FastBridge() {
        super("FastBridge", "You sneak automaticaly", Category.WORLD);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            BlockPos curPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(curPos).getBlock() == Blocks.air) {
                mc.gameSettings.keyBindSneak.pressed = true;
            } else {
                mc.gameSettings.keyBindSneak.pressed = false;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
    }

}