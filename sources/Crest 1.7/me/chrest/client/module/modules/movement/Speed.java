// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import net.minecraft.potion.Potion;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.TickEvent;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import me.chrest.utils.LiquidUtils;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.option.OptionManager;
import me.chrest.client.option.Option;
import me.chrest.client.module.modules.movement.speed.Jump;
import me.chrest.client.module.modules.movement.speed.Minez;
import me.chrest.client.module.modules.movement.speed.Vanilla;
import me.chrest.client.module.modules.movement.speed.Vhop;
import me.chrest.client.module.modules.movement.speed.Bhop;
import me.chrest.client.module.Module;

@Mod(displayName = "Speed")
public class Speed extends Module
{
    public Bhop bhop;
    public Vhop vhop;
    private Vanilla vanilla;
    private Minez minez;
    private Jump jump;
    @Option.Op(min = 2.0, max = 4.5, increment = 0.25, name = "Boost")
    public static double boost;
    @Option.Op(min = 0.2, max = 10.0, increment = 0.05, name = "Run Speed")
    public double speed;
    
    public Speed() {
        this.bhop = new Bhop("Bhop", true, this);
        this.vhop = new Vhop("Vhop", false, this);
        this.vanilla = new Vanilla("Vanilla", false, this);
        this.minez = new Minez("Minez", false, this);
        this.jump = new Jump("Jump", false, this);
        this.speed = 0.5;
    }
    
    @Override
    public void postInitialize() {
        OptionManager.getOptionList().add(this.bhop);
        OptionManager.getOptionList().add(this.vhop);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.minez);
        OptionManager.getOptionList().add(this.jump);
        this.updateSuffix();
        super.postInitialize();
    }
    
    @Override
    public void enable() {
        this.bhop.enable();
        this.vhop.enable();
        this.vanilla.enable();
        this.minez.enable();
        this.jump.enable();
        super.enable();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (LiquidUtils.isInLiquid()) {
            return;
        }
        this.bhop.onMove(event);
        this.vhop.onMove(event);
        this.vanilla.onMove(event);
        this.minez.onMove(event);
        this.jump.onMove(event);
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        this.bhop.onUpdate(event);
        this.vhop.onUpdate(event);
        this.vanilla.onUpdate(event);
        this.minez.onUpdate(event);
        this.jump.onUpdate(event);
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.bhop.getValue()) {
            this.setSuffix("Bhop");
        }
        else if (this.vhop.getValue()) {
            this.setSuffix("Vhop");
        }
        else if (this.vanilla.getValue()) {
            this.setSuffix("Vanilla");
        }
        else if (this.jump.getValue()) {
            this.setSuffix("Jump");
        }
        else {
            this.setSuffix("Minez");
        }
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
