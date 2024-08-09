package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.other.BlockPlaceEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

@ModuleInfo(name = "Auto Anchor", description = "Автоматически активирует якорь возрождения.", category = Category.COMBAT)

public class AutoAnchor extends Module {
    private int getSlotForGlowstone() {
        for (int i = 0; i < 8; ++i)
            if (mc.player.inventory.getStackInSlot(i).getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) return i;
        return -1;
    }

    BlockPos pos;
    int ticksAction, hotSlot;
    private final Listener<BlockPlaceEvent> onPlaceBlock = event -> {
        if (event.getStack().getItem() != Item.getItemFromBlock(Blocks.RESPAWN_ANCHOR)) return;
        if (pos != null) return;
        if (mc.player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) return;
        pos = event.getPosition();
        hotSlot = getSlotForGlowstone();
        ticksAction = 0;
    };

    private void rClickBlockPos(BlockPos pos) {
        mc.player.connection.sendPacket(new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(mc.objectMouseOver.getHitVec(), Direction.UP, pos, true)));
        mc.player.swingArm(Hand.MAIN_HAND);
    }

    private final Listener<UpdateEvent> onUpdate = event -> {
        if (pos == null) return;
        switch (ticksAction) {
            case 0 -> {
                if (mc.player.isSneaking())
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.RELEASE_SHIFT_KEY));
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hotSlot));
                rClickBlockPos(pos);
            }
            case 1 -> {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
            case 2 -> {
                rClickBlockPos(pos);
                if (mc.player.isSneaking())
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.PRESS_SHIFT_KEY));
            }
        }
        if (ticksAction >= 2) pos = null;
        ++ticksAction;
    };
}
