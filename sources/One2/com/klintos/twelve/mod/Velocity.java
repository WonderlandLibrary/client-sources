// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityPlayerSP;
import com.klintos.twelve.mod.events.EventRecieveVelocity;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueDouble;

public class Velocity extends Mod
{
    private ValueDouble percentage;
    
    public Velocity() {
        super("Velocity", 211, ModCategory.COMBAT);
        this.addValue(this.percentage = new ValueDouble("Percentage", 30.0, 0.0, 100.0, 0));
        Twelve.getInstance().getCmdHandler().addCmd(new Cmd("velocity", "Reduce velocity by percentage.", "velocity <Percentage>") {
            @Override
            public void runCmd(final String msg, final String[] args) {
                try {
                    final int percent = Integer.parseInt(args[1]);
                    if (percent >= 0 && percent <= 100) {
                        Velocity.this.percentage.setValue(percent);
                        this.addMessage("Velocity will now be at §c" + percent + "%§f.");
                    }
                }
                catch (Exception e) {
                    this.runHelp();
                }
            }
        });
    }
    
    @EventTarget
    public void onRecieveVelocity(EventRecieveVelocity event) {
        event.setCancelled(true);
        final Entity entity = Velocity.mc.getNetHandler().clientWorldController.getEntityByID(event.getPacket().func_149412_c());
        if (entity instanceof EntityPlayerSP) {
            final double velX = event.getPacket().func_149411_d() / 8000.0;
            final double velY = event.getPacket().func_149410_e() / 8000.0;
            final double velZ = event.getPacket().func_149409_f() / 8000.0;
            Velocity.mc.thePlayer.setVelocity(velX * (this.percentage.getValue() / 100.0), velY * (this.percentage.getValue() / 100.0), velZ * (this.percentage.getValue() / 100.0));
        }
    }
}
