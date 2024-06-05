/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.CollisionEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class NoFall
extends Module {
    public ModeSetting nmode = new ModeSetting("NoFall mode", "Verus", "Edit", "Watchdog", "Verus");

    public NoFall() {
        super("NoFall", "Remove fall damage", 0, Category.Player);
        this.addSettings(this.nmode);
    }

    @Subscribe
    public void onCollide(CollisionEvent e) {
        String mode;
        switch (mode = this.nmode.getMode()) {
            case "Verus": {
                double x = NoFall.mc.thePlayer.posX;
                double y = NoFall.mc.thePlayer.posY;
                double z = NoFall.mc.thePlayer.posZ;
                if (!(NoFall.mc.thePlayer.fallDistance >= 5.0f)) break;
                e.getList().add(new AxisAlignedBB(x, y, z));
            }
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        String mode = this.nmode.getMode();
        this.setDisplayName("NoFall\u00a77 " + this.nmode.getMode());
        switch (mode) {
            case "Watchdog": {
                if (!((double)NoFall.mc.thePlayer.fallDistance > 2.69) || !e.isPre()) break;
                this.sendPackets();
                NoFall.mc.thePlayer.fallDistance = 0.0f;
            }
        }
    }

    public void sendPackets() {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
    }
}

