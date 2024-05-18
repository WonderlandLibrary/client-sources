package wwe.modules.combat;

import wwe.modules.*;
import net.minecraft.item.*;
import wwe.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import wwe.*;

public class AutoCrystal extends qMIe
{
    int OJXa;
    
    public AutoCrystal() {
        super("Auto Crystal", "Automatically places crystals where cursor is", 0, Category.COMBAT);
        this.OJXa = 0;
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal && Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && (Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.BEDROCK || Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.OBSIDIAN)) {
            yYGD.AXbo();
        }
    }
}
