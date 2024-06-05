package white.floor.features.impl.movement;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

public class Eagle extends Feature {

    public Eagle() {
        super("Eagle", "press shift on last pixel on block.",0, Category.MOVEMENT);
    }

    @EventTarget
    public void blob(EventUpdate blob) {
        mc.gameSettings.keyBindSneak.pressed = false;
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ)).getBlock() == Blocks.AIR) {
            mc.gameSettings.keyBindSneak.pressed = true;
        }
    }
}