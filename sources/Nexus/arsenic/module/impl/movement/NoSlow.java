package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.EnumProperty;
import arsenic.utils.minecraft.BlinkUtils;
import arsenic.utils.minecraft.PacketUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;

@ModuleInfo(name = "NoSlow", category = ModuleCategory.Movement)
public class NoSlow extends Module {
    public final EnumProperty<sMode> slowMode = new EnumProperty<sMode>("Sword Mode: ", sMode.VANILLA);
    public final EnumProperty<gMode> gappleMode = new EnumProperty<gMode>("Gapple Mode: ", gMode.VANILLA);

    @Override
    protected void postApplyConfig() {
        slowMode.onValueUpdate();
    }

    @EventLink
    public final Listener<EventUpdate.Pre> eventUpdateListener = e -> {
        // pre swords
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (slowMode.getValue() == sMode.SWITCH) {
                if (mc.thePlayer.isUsingItem()) {
                    PacketUtil.send(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
            if (slowMode.getValue() == sMode.INTAVE) {
                if (mc.thePlayer.isUsingItem()) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }
            if (slowMode.getValue() == sMode.POLAR) {
                if (mc.thePlayer.isUsingItem()) {
                    PacketUtil.send(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
            if (slowMode.getValue() == sMode.UNCP) {
                if (mc.thePlayer.isUsingItem()) {
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                    PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
            }
        }
        // gapples
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood) {
            if (gappleMode.getValue() == gMode.SWITCH) {
                if (mc.thePlayer.isUsingItem()) {
                    PacketUtil.send(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
            if (gappleMode.getValue() == gMode.INTAVE) {
                if (mc.thePlayer.isUsingItem()) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }
        }
    };
    @EventLink
    public final Listener<EventUpdate.Post> postListener = e -> {
        // post swords
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (slowMode.getValue() == sMode.HYPIXEL) {
                if (mc.thePlayer.isUsingItem()) {
                    PacketUtil.send(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
        }
    };

    @EventLink
    public final Listener<TickEvent> onTick = e -> {
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (slowMode.getValue() == sMode.INTAVE) {
                if (mc.thePlayer.isBlocking()) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                } else if (mc.thePlayer.isUsingItem()) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
        }
        // gapples
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood) {
            if (slowMode.getValue() == sMode.INTAVE) {
                if (mc.thePlayer.isEating()) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                }
            }
        }
    };

    @EventLink
    public final Listener<EventPacket.OutGoing> onPacket = event -> {
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood) {
            if (mc.theWorld == null) {
                return;
            }
            if (gappleMode.getValue() == gMode.BLINK) {
                if (mc.thePlayer.isUsingItem()) {
                    BlinkUtils.CancelMove(event);
                }
            }
        }
    };

    @EventLink
    public final Listener<EventPacket.Incoming.Pre> preListener = event -> {
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (slowMode.getValue() == sMode.POLAR) {
                if (mc.thePlayer.isUsingItem()) {
                    if (event.getPacket() instanceof S09PacketHeldItemChange || event.getPacket() instanceof S2FPacketSetSlot) {
                        event.cancel();
                    }
                }
            }
        }
    };

    public enum sMode {
        VANILLA,
        SWITCH,
        POLAR,
        HYPIXEL,
        UNCP,
        INTAVE,
        NONE
    }

    public enum gMode {
        VANILLA,
        SWITCH,
        INTAVE,
        BLINK,
        NONE
    }
}
