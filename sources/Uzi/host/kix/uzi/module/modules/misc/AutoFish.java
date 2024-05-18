package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S29PacketSoundEffect;

import java.util.Objects;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class AutoFish extends Module {

    private final Stopwatch timer;
    private boolean caught;

    public AutoFish() {
        super("AutoFish", 0, Category.MISC);
        this.timer = new Stopwatch();
        this.caught = false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod) {
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()));
        }
    }

    @SubscribeEvent
    public void recievePacket(RecievePacketEvent event) {
        final Packet packet = event.getPacket();
        if (this.caught) {
            if (this.timer.hasCompleted(1000)) {
                this.caught = false;
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()));
            }
        } else if (Objects.nonNull(Minecraft.getMinecraft().thePlayer.fishEntity) && packet instanceof S29PacketSoundEffect) {
            final S29PacketSoundEffect sound = (S29PacketSoundEffect) event.getPacket();
            if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFishingRod && sound.func_149212_c().equals("random.splash")) {
                this.caught = true;
                this.timer.reset();
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()));
            }
        }
    }

}
