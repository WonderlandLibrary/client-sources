package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import wwe.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class WaterBucket extends qMIe
{
    public static BlockPos vUhs;
    
    public WaterBucket() {
        super("Water Bucket", "If you have a water bucket, will use it to stop fall damage", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.player.fallDistance >= 5.0f) {
            final int currentItem = Wrapper.mc.player.inventory.currentItem;
            int currentItem2 = -1;
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem().equals(Items.WATER_BUCKET)) {
                    currentItem2 = i;
                    if (WaterBucket.\u2003\u2001) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (WaterBucket.\u2003\u2001) {
                        throw null;
                    }
                    continue;
                }
            }
            if (!Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.WATER_BUCKET) && !this.cWXx()) {
                if (currentItem2 == -1) {
                    return;
                }
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem2));
                Wrapper.mc.player.inventory.currentItem = currentItem2;
                Wrapper.mc.playerController.updateController();
            }
            else {
                Wrapper.mc.player.rotationPitch = 90.0f;
                final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.getEntityBoundingBox().minY, Wrapper.mc.player.posZ);
                if (Wrapper.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && !(Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockLiquid)) {
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    LKsL.LInL();
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
                    Wrapper.mc.player.inventory.currentItem = currentItem;
                    Wrapper.mc.playerController.updateController();
                }
            }
        }
    }
    
    public boolean cWXx() {
        int i = 9;
        while (i < 36) {
            final ItemStack stack = Wrapper.mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem().equals(Items.WATER_BUCKET)) {
                Wrapper.mc.playerController.windowClick(Wrapper.mc.player.openContainer.windowId, i, Wrapper.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Wrapper.mc.player);
                return true;
            }
            ++i;
            if (WaterBucket.\u2003\u2001) {
                throw null;
            }
        }
        return false;
    }
}
