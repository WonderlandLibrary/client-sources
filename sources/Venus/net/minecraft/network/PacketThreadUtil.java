/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.optifine.util.PacketRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketThreadUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    public static RegistryKey<World> lastDimensionType = null;

    public static <T extends INetHandler> void checkThreadAndEnqueue(IPacket<T> iPacket, T t, ServerWorld serverWorld) throws ThreadQuickExitException {
        PacketThreadUtil.checkThreadAndEnqueue(iPacket, t, serverWorld.getServer());
    }

    public static <T extends INetHandler> void checkThreadAndEnqueue(IPacket<T> iPacket, T t, ThreadTaskExecutor<?> threadTaskExecutor) throws ThreadQuickExitException {
        if (!threadTaskExecutor.isOnExecutionThread()) {
            threadTaskExecutor.execute(new PacketRunnable(iPacket, () -> PacketThreadUtil.lambda$checkThreadAndEnqueue$0(iPacket, t)));
            throw ThreadQuickExitException.INSTANCE;
        }
        PacketThreadUtil.clientPreProcessPacket(iPacket);
    }

    protected static void clientPreProcessPacket(IPacket iPacket) {
        if (iPacket instanceof SPlayerPositionLookPacket) {
            Minecraft.getInstance().worldRenderer.onPlayerPositionSet();
        }
        if (iPacket instanceof SRespawnPacket) {
            SRespawnPacket sRespawnPacket = (SRespawnPacket)iPacket;
            lastDimensionType = sRespawnPacket.func_240827_c_();
        } else if (iPacket instanceof SJoinGamePacket) {
            SJoinGamePacket sJoinGamePacket = (SJoinGamePacket)iPacket;
            lastDimensionType = sJoinGamePacket.func_240819_i_();
        } else {
            lastDimensionType = null;
        }
    }

    private static void lambda$checkThreadAndEnqueue$0(IPacket iPacket, INetHandler iNetHandler) {
        PacketThreadUtil.clientPreProcessPacket(iPacket);
        if (iNetHandler.getNetworkManager().isChannelOpen()) {
            iPacket.processPacket(iNetHandler);
        } else {
            LOGGER.debug("Ignoring packet due to disconnection: " + iPacket);
        }
    }
}

