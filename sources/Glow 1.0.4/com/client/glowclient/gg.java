package com.client.glowclient;

import net.minecraftforge.common.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import com.client.glowclient.events.*;

public class gg
{
    public static void D() {
        MinecraftForge.EVENT_BUS.register((Object)new mg());
    }
    
    public gg() {
        super();
    }
    
    public static float M(final EntityBoat entityBoat, final float n) {
        final EventBoat eventBoat = new EventBoat(entityBoat, n);
        MinecraftForge.EVENT_BUS.post((Event)eventBoat);
        return eventBoat.getYaw();
    }
    
    public static boolean M(final Block block, final IBlockState blockState, final World world, final BlockPos blockPos, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity, final boolean b) {
        return MinecraftForge.EVENT_BUS.post((Event)new EventCollide(block, blockState, world, blockPos, axisAlignedBB, list, entity, b));
    }
    
    public static void D(final Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post((Event)new EventServerPacket(packet));
    }
    
    public static void M(final RenderChunk renderChunk, final BlockRenderLayer blockRenderLayer) {
        MinecraftForge.EVENT_BUS.post((Event)new EventRenderChunk(renderChunk, blockRenderLayer));
    }
    
    public static void M(final Chunk chunk) {
        MinecraftForge.EVENT_BUS.post((Event)new EventChunk(chunk));
    }
    
    public static void M() {
        MinecraftForge.EVENT_BUS.register((Object)new ee());
    }
    
    public static void M(final Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClientPacket(packet));
    }
    
    public static boolean M(final BlockPos blockPos, final IBlockState blockState, final IBlockAccess blockAccess, final BufferBuilder bufferBuilder) {
        return MinecraftForge.EVENT_BUS.post((Event)new EventBlock(blockPos, blockState, blockAccess, bufferBuilder));
    }
}
