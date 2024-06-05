package win.sightclient.module.combat;

import java.util.ArrayList;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventAttack;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.Setting;

public class Criticals extends Module {

	private double FallStack;
	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Jump"});
	
	public Criticals() {
		super("Criticals", Category.COMBAT);
	}

	@Override
	public void onEvent(Event eu) {
		if (eu instanceof EventUpdate && Killaura.target != null && this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			EventUpdate e = (EventUpdate)eu;
            if (e.isPre()) {
            	EntityPlayerSP player = mc.thePlayer;
                if (isBlockUnder() && player.onGround && !Sight.instance.mm.isEnabled("Speed") && !Sight.instance.mm.isEnabled("Flight") && !mc.gameSettings.keyBindJump.isKeyDown() && player.fallDistance == 0) {
                	e.setOnGround(false);
                    if (FallStack >= 0 && FallStack < 0.1 && player.ticksExisted % 2 == 0) {
                        double value = 0.0524 + ThreadLocalRandom.current().nextDouble(1E-8, 1E-7);
                        FallStack += value;
                        e.setY(player.posY + value);
                    } else {
                        e.setY(player.posY + 1E-7);
                        if (FallStack < 0) {
                            FallStack = 0;
                            e.setOnGround(true);
                            e.setY(mc.thePlayer.posY);
                        }
                    }
                }
                else {
                    FallStack = -1;
                }
            }
		} else if (eu instanceof EventAttack && mc.thePlayer.onGround && this.mode.getValue().equalsIgnoreCase("Jump")) {
			mc.thePlayer.jump();
		}
	}
	
    private boolean isBlockUnder() {
        EntityPlayerSP player = mc.thePlayer;

        for (int i = (int) (player.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(player.posX, i, player.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
}
