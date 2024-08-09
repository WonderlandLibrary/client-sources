/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.invoke.CallSite;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class ChunkInfoDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft client;
    private double field_217679_b = Double.MIN_VALUE;
    private final int field_217680_c = 12;
    @Nullable
    private Entry field_217681_d;

    public ChunkInfoDebugRenderer(Minecraft minecraft) {
        this.client = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        Object object;
        double d4 = Util.nanoTime();
        if (d4 - this.field_217679_b > 3.0E9) {
            this.field_217679_b = d4;
            object = this.client.getIntegratedServer();
            this.field_217681_d = object != null ? new Entry(this, (IntegratedServer)object, d, d3) : null;
        }
        if (this.field_217681_d != null) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.lineWidth(2.0f);
            RenderSystem.disableTexture();
            RenderSystem.depthMask(false);
            object = this.field_217681_d.field_217722_c.getNow(null);
            double d5 = this.client.gameRenderer.getActiveRenderInfo().getProjectedView().y * 0.85;
            for (Map.Entry<ChunkPos, String> entry : this.field_217681_d.field_217721_b.entrySet()) {
                ChunkPos chunkPos = entry.getKey();
                Object object2 = entry.getValue();
                if (object != null) {
                    object2 = (String)object2 + (String)object.get(chunkPos);
                }
                String[] stringArray = ((String)object2).split("\n");
                int n = 0;
                for (String string : stringArray) {
                    DebugRenderer.renderText(string, (chunkPos.x << 4) + 8, d5 + (double)n, (chunkPos.z << 4) + 8, -1, 0.15f);
                    n -= 2;
                }
            }
            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
        }
    }

    final class Entry {
        private final Map<ChunkPos, String> field_217721_b;
        private final CompletableFuture<Map<ChunkPos, String>> field_217722_c;
        final ChunkInfoDebugRenderer this$0;

        private Entry(ChunkInfoDebugRenderer chunkInfoDebugRenderer, IntegratedServer integratedServer, double d, double d2) {
            this.this$0 = chunkInfoDebugRenderer;
            ClientWorld clientWorld = chunkInfoDebugRenderer.client.world;
            RegistryKey<World> registryKey = clientWorld.getDimensionKey();
            int n = (int)d >> 4;
            int n2 = (int)d2 >> 4;
            ImmutableMap.Builder<ChunkPos, Object> builder = ImmutableMap.builder();
            ClientChunkProvider clientChunkProvider = clientWorld.getChunkProvider();
            for (int i = n - 12; i <= n + 12; ++i) {
                for (int j = n2 - 12; j <= n2 + 12; ++j) {
                    ChunkPos chunkPos = new ChunkPos(i, j);
                    Object object = "";
                    Chunk chunk = clientChunkProvider.getChunk(i, j, true);
                    object = (String)object + "Client: ";
                    if (chunk == null) {
                        object = (String)object + "0n/a\n";
                    } else {
                        object = (String)object + (chunk.isEmpty() ? " E" : "");
                        object = (String)object + "\n";
                    }
                    builder.put(chunkPos, object);
                }
            }
            this.field_217721_b = builder.build();
            this.field_217722_c = integratedServer.supplyAsync(() -> Entry.lambda$new$0(integratedServer, registryKey, n, n2));
        }

        private static Map lambda$new$0(IntegratedServer integratedServer, RegistryKey registryKey, int n, int n2) {
            ServerWorld serverWorld = integratedServer.getWorld(registryKey);
            if (serverWorld == null) {
                return ImmutableMap.of();
            }
            ImmutableMap.Builder<ChunkPos, CallSite> builder = ImmutableMap.builder();
            ServerChunkProvider serverChunkProvider = serverWorld.getChunkProvider();
            for (int i = n - 12; i <= n + 12; ++i) {
                for (int j = n2 - 12; j <= n2 + 12; ++j) {
                    ChunkPos chunkPos = new ChunkPos(i, j);
                    builder.put(chunkPos, (CallSite)((Object)("Server: " + serverChunkProvider.getDebugInfo(chunkPos))));
                }
            }
            return builder.build();
        }
    }
}

