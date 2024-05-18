// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueDouble;

public class Step extends Mod
{
    private ValueDouble height;
    
    public Step() {
        super("Step", 48, ModCategory.PLAYER);
        this.addValue(this.height = new ValueDouble("Height", 1.4, 1.0, 10.0, 1));
        Twelve.getInstance().getCmdHandler().addCmd(new Cmd("step", "Set the step height.", "step <Height>") {
            @Override
            public void runCmd(final String msg, final String[] args) {
                try {
                    final double h = Double.parseDouble(args[1]);
                    if (h >= Step.this.height.getMin() && h <= Step.this.height.getMax()) {
                        Step.this.height.setValue(h);
                        this.addMessage("Step height now §c" + h + "§f.");
                    }
                }
                catch (Exception e) {
                    this.runHelp();
                }
            }
        });
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (Step.mc.thePlayer.handleWaterMovement()) {
            Step.mc.thePlayer.stepHeight = 0.5f;
        }
        else {
            Step.mc.thePlayer.stepHeight = (float)this.height.getValue();
        }

    }
    
    @Override
    public void onDisable() {
        Step.mc.thePlayer.stepHeight = 0.5f;
    }
}
