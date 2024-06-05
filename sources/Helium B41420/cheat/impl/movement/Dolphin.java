package rip.helium.cheat.impl.movement;

import me.hippo.systems.lwjeb.annotation.Collect;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerMoveEvent;
import rip.helium.utils.PlayerUtils;

public class Dolphin extends Cheat {
    
    public Dolphin() {
        super ("Dolphin", "hax", CheatCategory.MOVEMENT);
    }

    boolean wasWater;

    @Collect
    public void onPlayerUpdate(PlayerMoveEvent event)
    {
        if ((this.mc.thePlayer.onGround) || (this.mc.thePlayer.isOnLadder())) {
            this.wasWater = false;
        }
        if (this.mc.thePlayer.motionY > 0.0D) {
            if ((this.mc.thePlayer.motionY < 0.03D) && (this.wasWater))
            {
                this.mc.thePlayer.motionY *= 1.20051D;
                this.mc.thePlayer.motionY += 0.06713D;
            }
            else if ((this.mc.thePlayer.motionY <= 0.05D) && (this.wasWater))
            {
                this.mc.thePlayer.motionY *= 1.20000000999D;
                this.mc.thePlayer.motionY += 0.06D;
            }
            else if ((this.mc.thePlayer.motionY <= 0.08D) && (this.wasWater))
            {
                this.mc.thePlayer.motionY *= 1.20000003D;
                this.mc.thePlayer.motionY += 0.055D;
            }
            else if ((this.mc.thePlayer.motionY <= 0.112D) && (this.wasWater))
            {
                this.mc.thePlayer.motionY *= 1.2000000056D;
                this.mc.thePlayer.motionY += 0.0535D;
            }
            else if (this.wasWater)
            {
                this.mc.thePlayer.motionY *= 1.000000000002D;
                this.mc.thePlayer.motionY += 0.0517D;
            }
        }
        if ((this.wasWater) && (this.mc.thePlayer.motionY < 0.0D) && (this.mc.thePlayer.motionY > -0.3D)) {
            this.mc.thePlayer.motionY += 0.045835D;
        }
        this.mc.thePlayer.fallDistance = 0.0F;
        if (!PlayerUtils.isInLiquid()) {
            return;
        }
        if (!PlayerUtils.isInLiquid()) {
            this.mc.thePlayer.motionY = 0.5D;
        }
        this.wasWater = true;
    }
}
