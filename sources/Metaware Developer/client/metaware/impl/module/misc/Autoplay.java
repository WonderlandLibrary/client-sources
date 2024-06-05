package client.metaware.impl.module.misc;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.PacketUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S45PacketTitle;

@ModuleInfo(renderName = "Autoplay", name = "Autoplay", category = Category.PLAYER)
public class Autoplay extends Module {
    boolean nigga;

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;;
        if (this.nigga) {
            ItemStack slimeBall = this.mc.thePlayer.inventory.getStackInSlot(8);
            ItemStack paperItem = this.mc.thePlayer.inventory.getStackInSlot(7);
            if (slimeBall == null || paperItem == null) {
                this.nigga = false;
                return;
            }
            int slot = this.mc.thePlayer.inventory.currentItem;
            PacketUtil.packet(new C09PacketHeldItemChange(7));
            PacketUtil.packet(new C08PacketPlayerBlockPlacement(paperItem));
            PacketUtil.packet(new C09PacketHeldItemChange(slot));
        }
    };

    @EventHandler
    private Listener<PacketEvent> packetEventListener = event -> {
        if (event.getPacket() instanceof S45PacketTitle) {
            String the = ((S45PacketTitle)event.getPacket()).getMessage().getUnformattedText();
            this.nigga = (the.endsWith("is the CHAMPION!") || the.contains("You are now a spectator!"));
        }
    };
}
