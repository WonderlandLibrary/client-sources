package mathax.client.systems.modules.experimental;

import mathax.client.events.packets.PacketEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class SecretClose extends Module{
    public SecretClose(){
        super(Categories.Experimental, Items.DIAMOND_HOE, "SecretClose", "spoofs yourself still being in inventory");
    }
    public void  onSendPacket(PacketEvent.Send event){
        if (event.packet instanceof CloseHandledScreenC2SPacket){
            event.setCancelled(true);
        }


    }
}
