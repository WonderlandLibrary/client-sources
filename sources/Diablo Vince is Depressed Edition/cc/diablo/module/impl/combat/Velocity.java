/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplode;

public class Velocity
extends Module {
    public NumberSetting vertical = new NumberSetting("Vertical", 100.0, 0.0, 100.0, 1.0);
    public NumberSetting horizontal = new NumberSetting("Horizontal", 0.0, 0.0, 100.0, 1.0);
    public ModeSetting explosionBoost = new ModeSetting("Explosion Boost", "Absolute", "Cancel", "Multiply", "Absolute");
    public NumberSetting e_vertical = new NumberSetting("Explosion Vertical", 100.0, 0.0, 500.0, 1.0);
    public NumberSetting e_horizontal = new NumberSetting("Explosion Horizontal", 0.0, 0.0, 500.0, 1.0);

    public Velocity() {
        super("Velocity", "Modifies KB", 0, Category.Combat);
        this.addSettings(this.vertical, this.horizontal, this.explosionBoost, this.e_vertical, this.e_horizontal);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        this.setDisplayName("Velocity\u00a77 V:" + this.vertical.getVal() + " H:" + this.horizontal.getVal());
        if (e.getPacket() instanceof S27PacketExplode) {
            S27PacketExplode e_packet = (S27PacketExplode)e.getPacket();
            switch (this.explosionBoost.getMode()) {
                case "Cancel": {
                    e.setCancelled(true);
                    break;
                }
                case "Multiply": {
                    e_packet.motionY = (float)((double)(e_packet.motionY / 100.0f) * this.e_vertical.getVal());
                    e_packet.motionX = (float)((double)(e_packet.motionX / 100.0f) * this.e_horizontal.getVal());
                    e_packet.motionZ = (float)((double)(e_packet.motionZ / 100.0f) * this.e_horizontal.getVal());
                    break;
                }
                case "Absolute": {
                    e_packet.motionY = (float)this.e_vertical.getVal() / 50.0f;
                    e_packet.motionX = (float)((double)(e_packet.motionX / 100.0f) * this.e_horizontal.getVal() / 50.0);
                    e_packet.motionZ = (float)((double)(e_packet.motionZ / 100.0f) * this.e_horizontal.getVal() / 50.0);
                }
            }
        }
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
            if (this.vertical.getVal() == 0.0 && this.horizontal.getVal() == 0.0) {
                e.setCancelled(true);
            } else {
                packet.motionY = (int)((double)(packet.motionY / 100) * this.vertical.getVal());
                packet.motionX = (int)((double)(packet.motionX / 100) * this.horizontal.getVal());
                packet.motionZ = (int)((double)(packet.motionZ / 100) * this.horizontal.getVal());
            }
        }
    }
}

