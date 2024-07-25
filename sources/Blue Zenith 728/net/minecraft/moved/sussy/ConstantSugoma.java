package net.minecraft.moved.sussy;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AuraTargetKilledEvent;
import club.bluezenith.module.modules.NoObf;


public class ConstantSugoma {

    double a = -35428358D;

    @NoObf
    public double the() {
        BlueZenith.getBlueZenith().getEventManager().register(new Object() {
            @Listener
            public void a(AuraTargetKilledEvent event) {
                if(event.target == null) {
                 //   System.out.println(C00Handshake.getHWID());
                }
            }
        });
        return a;
    }
}
