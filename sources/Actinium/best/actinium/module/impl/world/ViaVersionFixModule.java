package best.actinium.module.impl.world;

import best.actinium.event.Event;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

@ModuleInfo(
        name = "Via Version Fix",
        description = "I Wonder Mhhhhhh",
        category = ModuleCategory.WORLD
)
public class ViaVersionFixModule extends Module {
    private BooleanProperty place = new BooleanProperty("Place",this,true);
    private BooleanProperty block = new BooleanProperty("Block",this,true);

    @Callback
    public void onUpdate(UpdateEvent e) {
        if(block.isEnabled() && e.getType() == EventType.PRE) {
            if (mc.thePlayer.isBlocking() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                PacketWrapper useItem = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                useItem.write(Type.VAR_INT, 1);
                PacketUtil.sendToServer(useItem, Protocol1_8To1_9.class, true, true);
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
        }
    }

    @Callback
    public void onPacket(PacketEvent e) {
        if(place.isEnabled() && e.getType() == EventType.OUTGOING) {
            final Packet<?> packet = e.getPacket();
            if (packet instanceof C08PacketPlayerBlockPlacement) {
                ((C08PacketPlayerBlockPlacement) packet).facingX = 0.5F;
                ((C08PacketPlayerBlockPlacement) packet).facingY = 0.5F;
                ((C08PacketPlayerBlockPlacement) packet).facingZ = 0.5F;
            }
        }
    }
}
