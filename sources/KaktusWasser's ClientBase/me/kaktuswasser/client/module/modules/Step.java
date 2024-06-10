// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.*;
import me.kaktuswasser.client.*;
import me.kaktuswasser.client.event.*;
import me.kaktuswasser.client.event.events.*;
import me.kaktuswasser.client.module.*;
import me.kaktuswasser.client.values.*;
import net.minecraft.network.*;

public class Step extends Module
{
    public final ModeValue stepMode;
    private final Value<Double> stepHeight;
    private int stepStage;
    public static boolean stepping;
    private boolean cancelOne;
    
    public Step() {
        super("Step", -4919628, Category.MOVEMENT);
        this.stepMode = new ModeValue("step_Mode", "stepmode", "new", new String[] { "old", "new" }, this);
        this.stepHeight = (Value<Double>)new ConstrainedValue("step_Step Height", "step_height", 1.0, 0.5, 8.0, this);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreStep) {
            final PreStep event = (PreStep)e;
            if (this.stepHeight.getValue() > 1.0) {
                Step.mc.thePlayer.stepHeight = (float)(Object)this.stepHeight.getValue();
                event.stepHeight = this.stepHeight.getValue();
            }
            else {
                Step.mc.thePlayer.stepHeight = 0.6f;
                final Speed speed = (Speed)Client.getModuleManager().getModuleByName("speed");
                if (Step.mc.thePlayer.movementInput != null && !Step.mc.thePlayer.movementInput.jump && Step.mc.thePlayer.isCollidedVertically && ((speed.isEnabled() && speed.canStep) || !speed.isEnabled())) {
                    event.stepHeight = 1.0;
                    event.bypass = true;
                }
            }
        }
        else if (e instanceof EntityStep) {
            this.stepStage = 0;
            Step.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.42, Step.mc.thePlayer.posZ, Step.mc.thePlayer.onGround));
            Step.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.75, Step.mc.thePlayer.posZ, Step.mc.thePlayer.onGround));
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        this.stepStage = 0;
        if (Step.mc.thePlayer != null) {
            Step.mc.thePlayer.stepHeight = 0.5f;
        }
    }
}
