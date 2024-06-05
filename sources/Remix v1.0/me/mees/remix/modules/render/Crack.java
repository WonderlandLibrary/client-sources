package me.mees.remix.modules.render;

import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class Crack extends Module
{
    public Crack() {
        super("Crack", 0, Category.RENDER);
        this.addSetting(new Setting("Crack size", this, 3.0, 1.0, 10.0, true, 1.0));
        this.addSetting(new Setting("Blood", this, false));
        this.addSetting(new Setting("Enchant", this, true));
        this.addSetting(new Setting("Criticals", this, true));
    }
    
    @Subscriber
    public void onPacket(final EventSendPacket event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                for (int i = 0; i < (int)this.getSettingByModule(this, "Crack size").doubleValue(); ++i) {
                    if (this.getSettingByModule(this, "Enchant").booleanValue()) {
                        Crack.mc.thePlayer.onEnchantmentCritical(packet.getEntity());
                    }
                    if (this.getSettingByModule(this, "Criticals").booleanValue()) {
                        Crack.mc.thePlayer.onCriticalHit(packet.getEntity());
                    }
                    if (this.getSettingByModule(this, "Blood").booleanValue()) {
                        final Random random = new Random();
                        final Entity target = packet.getEntity();
                        for (double i2 = 0.0; i2 < target.height; i2 += 0.2) {
                            for (int i3 = 0; i3 < 9; ++i3) {
                                Crack.mc.effectRenderer.spawnEffectParticle(37, target.posX, target.posY + i2, target.posZ, (random.nextInt(6) - 3) / 5, 0.1, (random.nextInt(6) - 3) / 5, Block.getIdFromBlock(Blocks.redstone_block));
                            }
                        }
                    }
                }
            }
        }
    }
}
