package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import com.client.glowclient.utils.*;
import net.minecraft.util.math.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import net.minecraft.init.*;

public class Jesus extends ModuleContainer
{
    public boolean L;
    public static nB mode;
    public Timer B;
    private AxisAlignedBB b;
    
    @Override
    public String M() {
        String s = "";
        if (Jesus.mode.e().equals("Dip")) {
            s = "Dip";
        }
        if (Jesus.mode.e().equals("Solid")) {
            s = "Solid";
        }
        return s;
    }
    
    public Jesus() {
        super(Category.MOVEMENT, "Jesus", false, -1, "Walk on solid water");
        final double n = 0.0;
        final double n2 = 0.99;
        final double n3 = 1.0;
        this.b = new AxisAlignedBB(n, n, n, n3, n2, n3);
        this.B = new Timer();
    }
    
    private static boolean M(final Entity entity, final BlockPos blockPos) {
        return entity.posY >= blockPos.getY();
    }
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        try {
            if (Jesus.mode.e().equals("Solid") && eventClientPacket.getPacket() instanceof CPacketPlayer && EntityUtils.M((Entity)Wrapper.mc.player, true) && !EntityUtils.M((Entity)Wrapper.mc.player) && !this.M((Entity)Wrapper.mc.player)) {
                final int n = Wrapper.mc.player.ticksExisted % 2;
                final double y = ((CPacketPlayer)eventClientPacket.getPacket()).y;
                if (n == 0) {
                    ((CPacketPlayer)eventClientPacket.getPacket()).y = y + 0.02;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void d() {
        if (this.L) {
            this.L = false;
            return;
        }
        this.L = true;
    }
    
    @SubscribeEvent
    public void M(final EventCollide eventCollide) {
        if (Jesus.mode.e().equals("Solid") && Wrapper.mc.player != null && eventCollide.getBlock() instanceof BlockLiquid && (EntityUtils.m(eventCollide.getEntity()) || EntityUtils.E(eventCollide.getEntity())) && !(eventCollide.getEntity() instanceof EntityBoat) && !Wrapper.mc.player.isSneaking() && Wrapper.mc.player.fallDistance < 3.0f && !EntityUtils.M((Entity)Wrapper.mc.player) && (EntityUtils.M((Entity)Wrapper.mc.player, false) || EntityUtils.M(Ob.M(), false)) && M((Entity)Wrapper.mc.player, eventCollide.getPos())) {
            final AxisAlignedBB offset = this.b.offset(eventCollide.getPos());
            if (eventCollide.getEntityBox().intersects(offset)) {
                eventCollide.getCollidingBoxes().add(offset);
            }
            eventCollide.setCanceled(true);
        }
    }
    
    static {
        Jesus.mode = ValueFactory.M("Jesus", "Mode", "Mode of Jesus", "Solid", "Solid", "Dip");
    }
    
    private boolean M(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double n = entity.posY - 0.01;
        int floor;
        int i = floor = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int floor2;
            int j = floor2 = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                final BlockPos blockPos = new BlockPos(floor, MathHelper.floor(n), floor2);
                if (Wrapper.mc.world.getBlockState(blockPos).getBlock().isFullBlock(Wrapper.mc.world.getBlockState(blockPos))) {
                    return true;
                }
                j = ++floor2;
            }
            i = ++floor;
        }
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!this.B.hasBeenSet()) {
            this.B.reset();
        }
        if (Jesus.mode.e().equals("Solid") && !ModuleManager.M("Freecam").k() && EntityUtils.M((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking()) {
            Wrapper.mc.player.motionY = 0.1;
            if (Wrapper.mc.player.getRidingEntity() != null && !(Wrapper.mc.player.getRidingEntity() instanceof EntityBoat)) {
                Wrapper.mc.player.getRidingEntity().motionY = 0.3;
            }
        }
        if (Jesus.mode.e().equals("Dip") && !Wrapper.mc.player.isSneaking()) {
            double motionY;
            if (this.L) {
                motionY = 0.02;
            }
            else {
                motionY = -0.01;
            }
            final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 0.5, Wrapper.mc.player.posZ);
            final BlockPos blockPos2 = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 0.6, Wrapper.mc.player.posZ);
            if (Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER || Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.LAVA) {
                if (Wrapper.mc.world.getBlockState(blockPos2).getBlock() == Blocks.WATER || Wrapper.mc.world.getBlockState(blockPos2).getBlock() == Blocks.LAVA) {
                    Wrapper.mc.player.motionY = 0.1;
                    return;
                }
                Wrapper.mc.player.motionY = motionY;
                this.d();
            }
        }
    }
}
