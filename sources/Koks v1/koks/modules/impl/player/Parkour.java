package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @author avox | lmao | kroko
 * @created on 08.09.2020 : 22:35
 */
public class Parkour extends Module {

    public Parkour() {
        super("Parkour", "Your jump automaticaly", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            BlockPos curPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(curPos).getBlock() == Blocks.air) {
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}