// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import xyz.niggfaclient.utils.player.ItemUtils;
import net.minecraft.network.play.client.C02PacketUseEntity;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoTool", description = "Switches to the best tool", cat = Category.MISC)
public class AutoTool extends Module
{
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public AutoTool() {
        C02PacketUseEntity packetUseEntity;
        this.packetEventListener = (e -> {
            if ((this.mc.thePlayer != null || this.mc.theWorld != null) && e.getState() == PacketEvent.State.SEND) {
                if (e.getPacket() instanceof C02PacketUseEntity) {
                    packetUseEntity = (C02PacketUseEntity)e.getPacket();
                    if (packetUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                        this.mc.thePlayer.inventory.currentItem = ItemUtils.getBestSword();
                        this.mc.playerController.updateController();
                    }
                }
                if (this.mc.gameSettings.keyBindAttack.pressed && this.mc.objectMouseOver != null && this.mc.objectMouseOver.getBlockPos() != null) {
                    ItemUtils.updateTool(this.mc.objectMouseOver.getBlockPos());
                }
            }
        });
    }
}
