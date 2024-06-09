package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S30PacketWindowItems;

/**
 * Created by myche on 2/25/2017.
 */
public class ChestStealer extends Module {

    private S30PacketWindowItems packet;
    private boolean shouldEmptyChest;
    private int delay;
    private int currentSlot;
    private int[] whitelist;

    public ChestStealer() {
        super("ChestStealer", 0, Category.MISC);
        delay = 0;
        whitelist = new int[]{54};
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
        if (e.type == EventType.PRE) {
            try {
                if (!this.mc.inGameHasFocus && this.packet != null && mc.thePlayer.openContainer.windowId == this.packet.func_148911_c() && mc.currentScreen instanceof GuiChest) {
                    if (!this.isContainerEmpty(this.mc.thePlayer.openContainer)) {
                        final int slotId = this.getNextSlot(this.mc.thePlayer.openContainer);
                        if (this.delay >= 1.5) {
                            this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, slotId, 0, 1, this.mc.thePlayer);
                            this.delay = 0;
                        }
                        ++this.delay;
                    } else {
                        this.mc.thePlayer.closeScreen();
                        this.packet = null;
                    }
                }
            } catch (Exception hexeption) {
                hexeption.printStackTrace();
            }
        }
    }

    private int getNextSlot(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }

    public boolean isContainerEmpty(final Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }

    @SubscribeEvent
    public void packet(RecievePacketEvent e) {
        if (e.getPacket() instanceof S30PacketWindowItems) {
            packet = (S30PacketWindowItems) e.getPacket();
        }
    }

}
