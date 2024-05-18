package wwe.modules.player;

import wwe.modules.*;
import net.minecraft.network.play.client.*;
import wwe.utils.*;
import net.minecraft.world.*;
import wwe.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class AutoTool extends qMIe
{
    public int OxOf;
    
    public AutoTool() {
        super("Auto Tool", "Selects best tool when mining", 0, Category.PLAYER);
        this.OxOf = -1;
    }
    
    @oqrr
    public void zieK(final uqzS uqzS) {
        if (uqzS.MYlK() instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging cPacketPlayerDigging = (CPacketPlayerDigging)uqzS.MYlK();
            if (Wrapper.mc.playerController.getCurrentGameType() != GameType.CREATIVE && cPacketPlayerDigging.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                final int hxfn = Hxfn(Wrapper.mc.world.getBlockState(cPacketPlayerDigging.getPosition()).getBlock());
                if (hxfn != -1) {
                    if (this.OxOf == -1) {
                        this.OxOf = Wrapper.mc.player.inventory.currentItem;
                    }
                    Wrapper.mc.player.inventory.currentItem = hxfn;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    public static int Hxfn(final Block block) {
        float n = -1.0f;
        int n2 = -1;
        int i = 0;
        while (i < 9) {
            final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
            if (stackInSlot != null) {
                final float destroySpeed = stackInSlot.getItem().getDestroySpeed(stackInSlot, block.getDefaultState());
                if (destroySpeed > n) {
                    n = destroySpeed;
                    n2 = i;
                }
            }
            ++i;
            if (AutoTool.\u2007\u2000\u200c\u200d) {
                throw null;
            }
        }
        return n2;
    }
}
