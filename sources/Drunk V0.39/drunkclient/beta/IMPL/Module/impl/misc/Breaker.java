/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Option;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Breaker
extends Module {
    public Option<Boolean> beds = new Option<Boolean>("beds", "beds", true);
    public Option<Boolean> cakes = new Option<Boolean>("cakes", "cakes", true);
    public Option<Boolean> eggs = new Option<Boolean>("eggs", "eggs", true);

    public Breaker() {
        super("Breaker", new String[]{"Breaker"}, Type.MISC, "Breaks Bed for you");
        this.addValues(this.beds, this.cakes, this.eggs);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        int radius = 5;
        int x = -radius;
        while (x < radius) {
            for (int y = radius; y > -radius; --y) {
                for (int z = -radius; z < radius; ++z) {
                    int xPos = (int)Minecraft.thePlayer.posX + x;
                    int yPos = (int)Minecraft.thePlayer.posY + y;
                    int zPos = (int)Minecraft.thePlayer.posZ + z;
                    BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                    Block block = Breaker.mc.theWorld.getBlockState(blockPos).getBlock();
                    if (((Boolean)this.beds.getValue()).booleanValue() && block instanceof BlockBed) {
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                    }
                    if (((Boolean)this.cakes.getValue()).booleanValue() && block instanceof BlockCake) {
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                    }
                    if (!((Boolean)this.eggs.getValue()).booleanValue() || !(block instanceof BlockDragonEgg)) continue;
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                }
            }
            ++x;
        }
    }
}

