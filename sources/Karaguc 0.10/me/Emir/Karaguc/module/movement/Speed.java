package me.Emir.Karaguc.module.movement;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventPreMotionUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Speed extends Module {
	
    public Speed() {
        super("Speed", Keyboard.KEY_G, Category.MOVEMENT);
    }
    
    public void setup() {
    	ArrayList<String> modes = new ArrayList<String>();
    	modes.add("Hypixel");
        modes.add("Normal");
        //options.add("Vanilla");
        //options.add("Test");
        //options.add("Rewi/Rewinside");
        //options.add("AACP");
        modes.add("GommeHD");
        modes.add("TeamKyudoo");
        modes.add("HiveMC");
        modes.add("Mineplex/Gwen");
        //options.add("Best");
        //options.add("Guardian/VeltPvP");
        //options.add("DAC");
        //options.add("Storm")
        //options.add("Reflex");
        //options.add("HAC");
        modes.add("Gcheat/BAC/Badlion");
        modes.add("NCP");
        modes.add("AAC 3.3.14");
        //options.add("Minesucht");
        //options.add("Fiona");
        //options.add("Advanced");
        //options.add("Auto");
    	Karaguc.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "MineplexGwen", modes));
        Karaguc.instance.settingsManager.rSetting(new Setting("WallSpeed", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("WaterSpeed", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("IceSpeed", this, false));
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {

        if (Karaguc.instance.settingsManager.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Hypixel")) {
            this.setDisplayName("Speed \u00a77Hypixel");


        }

        if (Karaguc.instance.settingsManager.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Normal")) {
            this.setDisplayName("Speed \u00a77Normal");

            if(isOnLiquid())
                return;
            if(mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if(mc.thePlayer.ticksExisted % 2 != 0)
                    event.y += .4;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.095F;


            }

            if (Karaguc.instance.settingsManager.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("MineplexGwen")) {
                this.setDisplayName("Speed MineplexGwen");

                if(isOnLiquid())
                    return;
                if(mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                    if(mc.thePlayer.ticksExisted % 3 != 0)
                        event.y += .4;
                    mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 3 == 0 ? .75F : .3F);
                    mc.timer.timerSpeed = 2.095F;


                }

               }
    	    }
        }

    private boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(mc.thePlayer.boundingBox.minY - .01);
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if(block != null && !(block instanceof BlockAir)) {
                    if(!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    private float getDirection() {
		float var1 = mc.thePlayer.rotationYaw;

		if (mc.thePlayer.moveForward < 0.0F) { // If the player walks backward
			var1 += 180.0F;
		}

		float forward = 1.0F;

		if (mc.thePlayer.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (mc.thePlayer.moveForward > 0.0F) {
			forward = 0.5F;
		}

		if (mc.thePlayer.moveStrafing > 0.0F) {
			var1 -= 90.0F * forward;
		}

		if (mc.thePlayer.moveStrafing < 0.0F) {
			var1 += 90.0F * forward;
		}
		var1 *= 0.017453292F; // Faster version of Math.toRadians (x * 1 / 180 * PI)
		return var1;
	}
}
