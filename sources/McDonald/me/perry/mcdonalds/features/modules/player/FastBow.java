// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import me.perry.mcdonalds.util.Util;
import net.minecraft.item.ItemBow;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class FastBow extends Module
{
    public Setting<Integer> drawLength;
    
    public FastBow() {
        super("FastBow", "bow go burr", Category.PLAYER, false, false, false);
        this.drawLength = (Setting<Integer>)this.register(new Setting("Hold time", (T)3, (T)3, (T)21));
    }
    
    @Override
    public void onUpdate() {
        if (Util.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && Util.mc.player.isHandActive() && Util.mc.player.getItemInUseMaxCount() >= this.drawLength.getValue()) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Util.mc.player.getHorizontalFacing()));
            Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(Util.mc.player.getActiveHand()));
            Util.mc.player.stopActiveHand();
        }
    }
}
