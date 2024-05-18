package wwe;

import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import wwe.utils.*;
import net.minecraft.world.*;
import net.minecraft.util.text.*;
import net.minecraft.inventory.*;

public class LVuv extends HAxG
{
    public TileEntityShulkerBox IccA;
    
    public LVuv() {
        super("peek");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox)) {
            HAxG.TuzD("You Have to hold a shulker box");
            return;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox) {
            HAxG.TuzD("Opening your shulker box.");
            final TileEntityShulkerBox iccA = new TileEntityShulkerBox();
            iccA.setWorld((World)Wrapper.mc.world);
            iccA.readFromNBT(Wrapper.mc.player.getHeldItemMainhand().getTagCompound().getCompoundTag("BlockEntityTag"));
            this.IccA = iccA;
            hSNE.lyvR(this);
        }
    }
    
    @Override
    public String UiIp() {
        return "Opens shulker box you are holding," + TextFormatting.DARK_RED + " " + TextFormatting.DARK_RED + "Do" + TextFormatting.DARK_RED + "not peek with a empty Shulker Box";
    }
    
    @Override
    public String ZlRY() {
        return "peek";
    }
    
    @oqrr
    public void KnYY(final omqH omqH) {
        Wrapper.mc.player.displayGUIChest((IInventory)this.IccA);
        hSNE.kduK(this);
    }
}
