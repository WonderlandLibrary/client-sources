package cn.Hanabi.modules.Ghost;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.client.entity.*;
import com.darkmagician6.eventapi.*;

public class LegitVelocity extends Mod
{
    public Value<Double> chance;
    public Value<Double> verti;
    public Value<Double> hori;
    
    
    public LegitVelocity() {
        super("LegitVelocity", Category.GHOST);
        this.chance = new Value<Double>("LegitVelocity_Chance", 100.0, 0.0, 100.0, 1.0);
        this.verti = new Value<Double>("LegitVelocity_Vertical", 100.0, 0.0, 100.0, 1.0);
        this.hori = new Value<Double>("LegitVelocity_Horizontal", 100.0, 0.0, 100.0, 1.0);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (LegitVelocity.mc.thePlayer.maxHurtResistantTime != LegitVelocity.mc.thePlayer.hurtResistantTime || LegitVelocity.mc.thePlayer.maxHurtResistantTime == 0) {
            return;
        }
        if (Double.valueOf(Double.valueOf(Math.random()) * 100.0) < (int)(Object)this.chance.getValueState()) {
            final float n = (float)(Object)this.hori.getValueState() / 100.0f;
            final float n2 = (float)(Object)this.verti.getValueState() / 100.0f;
            final EntityPlayerSP thePlayer = LegitVelocity.mc.thePlayer;
            thePlayer.motionX *= n;
            final EntityPlayerSP thePlayer2 = LegitVelocity.mc.thePlayer;
            thePlayer2.motionZ *= n;
            final EntityPlayerSP thePlayer3 = LegitVelocity.mc.thePlayer;
            thePlayer3.motionY *= n2;
        }
        else {
            final EntityPlayerSP thePlayer4 = LegitVelocity.mc.thePlayer;
            thePlayer4.motionX *= 1.0;
            final EntityPlayerSP thePlayer5 = LegitVelocity.mc.thePlayer;
            thePlayer5.motionY *= 1.0;
            final EntityPlayerSP thePlayer6 = LegitVelocity.mc.thePlayer;
            thePlayer6.motionZ *= 1.0;
        }
    }
}
