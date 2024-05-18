package wwe.modules.movement;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import wwe.modules.exploits.*;
import net.minecraft.network.play.client.*;
import wwe.*;

public class NoSlowDown extends qMIe
{
    public NoSlowDown() {
        super("No Slow Down", "Makes you take no item slow down", 0, Category.MOVEMENT);
    }
    
    @oqrr
    public void RHie(final cfjG cfjG) {
        if (Wrapper.mc.player.isHandActive() && !Wrapper.mc.player.isRiding()) {
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            movementInput.moveForward /= 0.2f;
            final MovementInput movementInput2 = Wrapper.mc.player.movementInput;
            movementInput2.moveStrafe /= 0.2f;
        }
    }
    
    @oqrr
    public void Nalv(final omqH omqH) {
        if (Wrapper.mc.player.isHandActive() && !Wrapper.mc.player.isRiding()) {
            ItemStack itemStack;
            if (Wrapper.mc.player.getActiveHand().equals((Object)EnumHand.MAIN_HAND)) {
                itemStack = Wrapper.mc.player.getHeldItemMainhand();
                if (NoSlowDown.\u2004\u200c) {
                    throw null;
                }
            }
            else {
                itemStack = Wrapper.mc.player.getHeldItemOffhand();
            }
            if (itemStack.getItem() instanceof ItemShield) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, Wrapper.mc.objectMouseOver.getBlockPos(), Wrapper.mc.objectMouseOver.sideHit));
            }
        }
    }
    
    @oqrr
    public void Ktxe(final ILBH ilbh) {
        if (Wrapper.mc.player.isHandActive() && !Wrapper.mc.player.isRiding()) {
            ItemStack itemStack;
            if (Wrapper.mc.player.getActiveHand().equals((Object)EnumHand.MAIN_HAND)) {
                itemStack = Wrapper.mc.player.getHeldItemMainhand();
                if (NoSlowDown.\u2004\u200c) {
                    throw null;
                }
            }
            else {
                itemStack = Wrapper.mc.player.getHeldItemOffhand();
            }
            if (itemStack.getItem() instanceof ItemShield) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(Wrapper.mc.objectMouseOver.getBlockPos(), Wrapper.mc.objectMouseOver.sideHit, OffHand.txLa(), 0.0f, 0.0f, 0.0f));
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
}
