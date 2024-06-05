package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.MoveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class FastBridge extends Module {

    public Option<Boolean> only = new Option ( "Only blocks", "Only blocks", false );
    public Option<Boolean> shift = new Option ( "Auto Shift", "Auto Shift", true );
    public Option<Boolean> fast = new Option ( "Fast Place", "Fast Place", true );
    public Option<Boolean> strafe = new Option ( "Strafe", "Strafe", true );

    public FastBridge() {
        super("FastBridge", new String[]{"LegitScaffold", "FastBridge"}, ModuleType.Move);
        addValues(only, fast, shift, strafe);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        if (Minecraft.thePlayer != null && Minecraft.theWorld != null) {
            if (fast.getValue()) {
                mc.rightClickDelayTimer = 0;
            }

            if (strafe.getValue() && Minecraft.thePlayer.isMoving()){
                MoveUtils.strafe();
            }

            if (shift.getValue()) {
                ItemStack i = Minecraft.thePlayer.getCurrentEquippedItem();
                BlockPos bP = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1D, Minecraft.thePlayer.posZ);
                if (i != null) {
                    if (!only.getValue() || i.getItem() instanceof ItemBlock) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                        if (Minecraft.theWorld.getBlockState(bP).getBlock() == Blocks.air) {
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 6;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.onDisable();
    }
}
