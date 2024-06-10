// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockLiquid;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.BlockBB;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.BlockHelper;
import me.kaktuswasser.client.utilities.TimeHelper;

public class Jesus extends Module
{
    private int waterUpdate;
    private int getoutofwater;
    private int timer;
    private final TimeHelper time;
    private boolean shouldJump;
    private boolean nextTick;
    private boolean canJesus;
    
    public Jesus() {
        super("Jesus", -12028161, 36, Category.MOVEMENT);
        this.waterUpdate = 1;
        this.timer = 0;
        this.time = new TimeHelper();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof BlockBB) {
            if (Jesus.mc.thePlayer == null) {
                return;
            }
            final BlockBB e = (BlockBB)event;
            if (e.getBlock() instanceof BlockLiquid && !BlockHelper.isInLiquid() && !Jesus.mc.thePlayer.isSneaking()) {
                final IBlockState state = Jesus.mc.theWorld.getBlockState(new BlockPos(e.getX(), e.getY(), e.getZ()));
                if (state != null) {
                    float pos = 0.0f;
                    try {
                        pos = BlockLiquid.getLiquidHeightPercent(e.getBlock().getMetaFromState(state));
                    }
                    catch (Exception ex2) {}
                    this.shouldJump = (pos < 0.55f);
                }
                else {
                    this.shouldJump = false;
                }
                if (this.shouldJump && Jesus.mc.thePlayer.fallDistance <= 3.0f) {
                    e.setBoundingBox(AxisAlignedBB.fromBounds(e.getX(), e.getY(), e.getZ(), e.getX() + 1.0, e.getY() + 1.0, e.getZ() + 1.0));
                }
            }
        }
        else if (event instanceof PreMotion) {
            if (BlockHelper.isInLiquidNew() && Jesus.mc.thePlayer.isInsideOfMaterial(Material.air) && !Jesus.mc.thePlayer.isSneaking() && this.shouldJump) {
                Jesus.mc.thePlayer.motionY = 0.08;
            }
        }
        else if (event instanceof SentPacket) {
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                if (BlockHelper.isOnLiquid()) {
                    this.nextTick = !this.nextTick;
                    if (this.nextTick) {
                        final C03PacketPlayer c03PacketPlayer = player;
                        c03PacketPlayer.y -= 0.01;
                    }
                }
            }
        }
    }
}
