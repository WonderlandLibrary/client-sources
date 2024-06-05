/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

import java.awt.*;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("SafeWalk", new String[]{"eagle", "parkour"}, ModuleType.Movement);
        this.setColor(new Color(198, 253, 191).getRGB());
    }

    @EventHandler
    public void onMove(EventMove event) {
        double x2 = event.getX();
        double y2 = event.getY();
        double z2 = event.getZ();
        if (mc.thePlayer.onGround) {
            double increment = 0.05;
            while (x2 != 0.0) {
                if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(x2, -1.0, 0.0)).isEmpty())
                    break;
                if (x2 < increment && x2 >= -increment) {
                    x2 = 0.0;
                    continue;
                }
                if (x2 > 0.0) {
                    x2 -= increment;
                    continue;
                }
                x2 += increment;
            }
            while (z2 != 0.0) {
                if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -1.0, z2)).isEmpty())
                    break;
                if (z2 < increment && z2 >= -increment) {
                    z2 = 0.0;
                    continue;
                }
                if (z2 > 0.0) {
                    z2 -= increment;
                    continue;
                }
                z2 += increment;
            }
            while (x2 != 0.0 && z2 != 0.0 && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(x2, -1.0, z2)).isEmpty()) {
                x2 = x2 < increment && x2 >= -increment ? 0.0 : (x2 > 0.0 ? (x2 -= increment) : (x2 += increment));
                if (z2 < increment && z2 >= -increment) {
                    z2 = 0.0;
                    continue;
                }
                if (z2 > 0.0) {
                    z2 -= increment;
                    continue;
                }
                z2 += increment;
            }
        }
        event.setX(x2);
        EventMove.setY(y2);
        event.setZ(z2);
    }
}

