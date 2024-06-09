// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import events.listeners.EventPacketNofall;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import intent.AquaDev.aqua.modules.movement.Fly;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Nofall extends Module
{
    public Nofall() {
        super("Nofall", Type.Player, "Nofall", 0, Category.Player);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && Nofall.mc.thePlayer.fallDistance > 2.0f && Nofall.mc.thePlayer.ticksExisted > 20 && !Nofall.mc.thePlayer.onGround) {
            Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
            Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Nofall.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
        }
        if (event instanceof EventPacketNofall) {
            final Packet p = EventPacketNofall.getPacket();
            if (Nofall.mc.thePlayer.fallDistance > 2.0f && Nofall.mc.thePlayer.ticksExisted > 20 && !Nofall.mc.thePlayer.onGround && !Nofall.mc.isSingleplayer() && !Nofall.mc.getCurrentServerData().serverIP.contains("cubecraft.net") && p instanceof C0FPacketConfirmTransaction) {
                event.setCancelled(true);
            }
            if (p instanceof C03PacketPlayer) {
                if (Nofall.mc.thePlayer.fallDistance > 2.0f && Nofall.mc.thePlayer.ticksExisted > 20 && !Nofall.mc.thePlayer.onGround) {
                    final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)p;
                    c03PacketPlayer.onGround = true;
                    Nofall.mc.thePlayer.fallDistance = 0.0f;
                }
                else {
                    final C03PacketPlayer c03PacketPlayer2 = (C03PacketPlayer)p;
                }
            }
        }
    }
}
