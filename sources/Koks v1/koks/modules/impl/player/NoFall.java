package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.Sys;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:39
 */
public class NoFall extends Module {

    public ModeValue<String> mode = new ModeValue<>("Mode", "Spoof Ground", new String[]{"Spoof Ground", "AAC 3.2.2"}, this);

    public NoFall() {
        super("NoFall", "You dosnt take fall damage", Category.PLAYER);
        addValue(mode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Spoof Ground":
                    double distance = 0;
                    for (int i = 0; i < 256; i++) {
                        BlockPos currentPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ);
                        if(mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.air)
                            distance = mc.thePlayer.posY - currentPos.getY();

                    }
                    if (mc.thePlayer.fallDistance > 2 && distance != 0) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        mc.thePlayer.fallDistance = 0;
                    }
                    break;
                case "AAC 3.2.2":
                    if (mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava())
                        mc.thePlayer.motionY -= 999;
                    break;
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