/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.Unpooled;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.pathfinding.Path;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugPacketSender {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void func_229752_a_(ServerWorld serverWorld, BlockPos blockPos, String string, int n, int n2) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeBlockPos(blockPos);
        packetBuffer.writeInt(n);
        packetBuffer.writeString(string);
        packetBuffer.writeInt(n2);
        DebugPacketSender.func_229753_a_(serverWorld, packetBuffer, SCustomPayloadPlayPacket.field_229729_o_);
    }

    public static void func_229751_a_(ServerWorld serverWorld) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        DebugPacketSender.func_229753_a_(serverWorld, packetBuffer, SCustomPayloadPlayPacket.field_229730_p_);
    }

    public static void sendChuckPos(ServerWorld serverWorld, ChunkPos chunkPos) {
    }

    public static void func_218799_a(ServerWorld serverWorld, BlockPos blockPos) {
        DebugPacketSender.func_240840_d_(serverWorld, blockPos);
    }

    public static void func_218805_b(ServerWorld serverWorld, BlockPos blockPos) {
        DebugPacketSender.func_240840_d_(serverWorld, blockPos);
    }

    public static void func_218801_c(ServerWorld serverWorld, BlockPos blockPos) {
        DebugPacketSender.func_240840_d_(serverWorld, blockPos);
    }

    private static void func_240840_d_(ServerWorld serverWorld, BlockPos blockPos) {
    }

    public static void sendPath(World world, MobEntity mobEntity, @Nullable Path path, float f) {
    }

    public static void func_218806_a(World world, BlockPos blockPos) {
    }

    public static void sendStructureStart(ISeedReader iSeedReader, StructureStart<?> structureStart) {
    }

    public static void sendGoal(World world, MobEntity mobEntity, GoalSelector goalSelector) {
        if (world instanceof ServerWorld) {
            // empty if block
        }
    }

    public static void sendRaids(ServerWorld serverWorld, Collection<Raid> collection) {
    }

    public static void sendLivingEntity(LivingEntity livingEntity) {
    }

    public static void func_229749_a_(BeeEntity beeEntity) {
    }

    public static void sendBeehiveDebugData(BeehiveTileEntity beehiveTileEntity) {
    }

    private static void func_229753_a_(ServerWorld serverWorld, PacketBuffer packetBuffer, ResourceLocation resourceLocation) {
        SCustomPayloadPlayPacket sCustomPayloadPlayPacket = new SCustomPayloadPlayPacket(resourceLocation, packetBuffer);
        for (PlayerEntity playerEntity : serverWorld.getWorld().getPlayers()) {
            ((ServerPlayerEntity)playerEntity).connection.sendPacket(sCustomPayloadPlayPacket);
        }
    }
}

