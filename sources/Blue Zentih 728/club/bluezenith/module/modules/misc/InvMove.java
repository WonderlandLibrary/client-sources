package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.clickgui.IntellijClickGui;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.BlockPos;

import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("unused")
public class InvMove extends Module {
    public InvMove() {
        super("InvMove", ModuleCategory.MISC, "InventoryMove");
    }

    private final BooleanValue sneak = new BooleanValue("Sneak", false, true, null).setIndex(1);
    private final BooleanValue wd = new BooleanValue("Hypixel", false).setIndex(2);
    private final LinkedBlockingQueue<Packet<?>> blink = new LinkedBlockingQueue<>();
//    private C08PacketPlayerBlockPlacement chestPacket = null;

    @Listener
    public void onUpdate(UpdateEvent event) {
        if(wd.get()) {
            if (mc.currentScreen instanceof GuiInventory) {
                PacketUtil.sendSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                PacketUtil.sendSilent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
                PacketUtil.sendSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                if (mc.currentScreen instanceof GuiChat || mc.currentScreen == null)
                    return;
                amogus();
                PacketUtil.sendSilent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
             /*   PacketUtil.sendSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                PacketUtil.sendSilent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));*/
            } else if (mc.currentScreen instanceof GuiChest && mc.thePlayer.openContainer != null) {
                amogus();
            } else if (mc.currentScreen instanceof ClickGui && !((ClickGui) mc.currentScreen).shouldDoSearch || mc.currentScreen instanceof IntellijClickGui) {
                amogus();
            }
        } else if(!(mc.currentScreen instanceof GuiChat))amogus();
    }
    private void set(KeyBinding key){
        key.pressed = GameSettings.isKeyDown(key);
    }

    void amogus() {
        set(mc.gameSettings.keyBindForward);
        set(mc.gameSettings.keyBindBack);
        set(mc.gameSettings.keyBindRight);
        set(mc.gameSettings.keyBindLeft);
        set(mc.gameSettings.keyBindJump);
        set(mc.gameSettings.keyBindSprint);
        if (sneak.get())
            set(mc.gameSettings.keyBindSneak);
    }

    @Listener
    public void onPacket(PacketEvent e) {
        if(!wd.get()) return;
        if(mc.thePlayer != null && e.packet instanceof C03PacketPlayer && mc.thePlayer.openContainer != null && mc.currentScreen instanceof GuiChest){
            blink.add(e.packet);
            e.cancel();
        }
        if (e.packet instanceof C0DPacketCloseWindow) {
            while(!blink.isEmpty()){
                try {
                    PacketUtil.sendSilent(blink.take());
                } catch (InterruptedException er) {
                    er.printStackTrace();
                }
            }
            blink.clear();
        }
        if (e.packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) e.packet;
            BlockPos blockPos = packet.getPosition();

            if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockChest) {
                blink.clear();
            }
        }
    }

//    @Listener
//    public void onPacket(PacketEvent e) {
//        if (e.packet instanceof C08PacketPlayerBlockPlacement) {
//            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) e.packet;
//            BlockPos blockPos = packet.getPosition();
//
//            if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockChest) {
//                chestPacket = packet;
//            }
//        }
//        else if (e.packet instanceof C0DPacketCloseWindow && mc.thePlayer.openContainer != null) {
//            C0DPacketCloseWindow packet = (C0DPacketCloseWindow) e.packet;
//
//            if (packet.getPacketID() == mc.thePlayer.openContainer.windowId) {
//                chestPacket = null;
//            }
//        }
//    }
}
