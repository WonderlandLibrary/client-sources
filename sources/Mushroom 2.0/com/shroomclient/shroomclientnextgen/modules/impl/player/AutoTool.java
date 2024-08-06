package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;

@RegisterModule(
    name = "Auto Tool",
    uniqueId = "autotool",
    description = "Swaps To The Best Tool In Your Inventory",
    category = ModuleCategory.Player
)
public class AutoTool extends Module {

    int prevSlot = -1;
    int ticks = 0;
    int goodSlot = -1;
    boolean lastLeft = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigOption(
        name = "Only When Sneaking",
        description = "Only Autotool When Sneaking",
        order = 1
    )
    public Boolean autotoolup = false;

    @SubscribeEvent
    public void onPacket(PacketEvent.Send.Pre e) {
        if (!autotoolup || C.mc.options.sneakKey.isPressed()) {
            if (
                e.getPacket() instanceof PlayerActionC2SPacket h &&
                h
                    .getAction()
                    .equals(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK)
            ) {
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = C.p().getInventory().getStack(i);
                    Block block = C.w().getBlockState(h.getPos()).getBlock();
                    if (
                        stack != null &&
                        block != null &&
                        stack.getMiningSpeedMultiplier(
                                block.getDefaultState()
                            ) >
                            C.p()
                                .getInventory()
                                .getMainHandStack()
                                .getMiningSpeedMultiplier(
                                    block.getDefaultState()
                                )
                    ) {
                        goodSlot = i;
                    }
                }

                if (goodSlot != -1) {
                    prevSlot = C.p().getInventory().selectedSlot;
                    C.p().getInventory().selectedSlot = goodSlot;
                    goodSlot = -1;
                    ticks = 0;
                }
            }

            if (
                e.getPacket() instanceof PlayerActionC2SPacket h &&
                (h
                        .getAction()
                        .equals(
                            PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK
                        ) ||
                    h
                        .getAction()
                        .equals(
                            PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK
                        )) &&
                prevSlot != -1 &&
                !C.mc.options.attackKey.isPressed()
            ) {
                if (ticks != 0) {
                    C.p().getInventory().selectedSlot = prevSlot;
                    prevSlot = -1;
                    ticks = 0;
                } else ticks++;
            }

            if (
                lastLeft &&
                !C.mc.options.attackKey.isPressed() &&
                prevSlot != -1
            ) {
                C.p().getInventory().selectedSlot = prevSlot;
                prevSlot = -1;
            }

            lastLeft = C.mc.options.attackKey.isPressed();
        }
    }
}
