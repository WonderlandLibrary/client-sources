package dev.vertic.module.impl.player;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.other.TickEvent;
import dev.vertic.event.impl.other.UpdateEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class AutoTool extends Module {

    private final BooleanSetting switchBack = new BooleanSetting("Switch back", true);
    private final NumberSetting delay = new NumberSetting("Delay", 3, 0, 10, 1);
    private int oldSlot, ticks;
    private boolean wasDigging, doTick;

    public AutoTool() {
        super("AutoTool", "Switches to the best tool available in the hotbar.", Category.PLAYER);
        this.addSettings(switchBack, delay);
    }
    @Override
    public void onDisable() {
        if(wasDigging) {
            mc.thePlayer.inventory.currentItem = oldSlot;
            wasDigging = false;
        }
    }

    @EventLink
    public void onTick(TickEvent event) {
        if (Mouse.isButtonDown(0) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            ticks++;
        else
            ticks = 0;
    }

    @EventLink
    public void onUpdate(UpdateEvent event) {
        if((Mouse.isButtonDown(0) || mc.gameSettings.keyBindAttack.isKeyDown()) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.currentScreen == null && ticks >= delay.getInt()) {
            Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            float strength = 1.0f;
            if(!wasDigging) {
                oldSlot = mc.thePlayer.inventory.currentItem;
            }
            for(int i = 0; i <= 8; i++) {
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

                if(stack != null) {
                    float slotStrength = stack.getStrVsBlock(block);

                    if(slotStrength > strength) {
                        mc.thePlayer.inventory.currentItem = i;
                        strength = slotStrength;
                    }
                }
            }
            wasDigging = true;
            doTick = true;
        } else {
            if(wasDigging && switchBack.isEnabled()) {
                mc.thePlayer.inventory.currentItem = oldSlot;
                wasDigging = false;
            } else {
                oldSlot = mc.thePlayer.inventory.currentItem;
            }
            doTick = false;
        }
    }

}
