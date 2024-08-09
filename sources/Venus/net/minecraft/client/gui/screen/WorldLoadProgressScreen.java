/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.listener.TrackingChunkStatusListener;

public class WorldLoadProgressScreen
extends Screen {
    private final TrackingChunkStatusListener tracker;
    private long lastNarratorUpdateTime = -1L;
    private static final Object2IntMap<ChunkStatus> COLORS = Util.make(new Object2IntOpenHashMap(), WorldLoadProgressScreen::lambda$static$0);

    public WorldLoadProgressScreen(TrackingChunkStatusListener trackingChunkStatusListener) {
        super(NarratorChatListener.EMPTY);
        this.tracker = trackingChunkStatusListener;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.loading.done").getString());
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        String string = MathHelper.clamp(this.tracker.getPercentDone(), 0, 100) + "%";
        long l = Util.milliTime();
        if (l - this.lastNarratorUpdateTime > 2000L) {
            this.lastNarratorUpdateTime = l;
            NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.loading", string).getString());
        }
        int n3 = this.width / 2;
        int n4 = this.height / 2;
        int n5 = 30;
        WorldLoadProgressScreen.func_238625_a_(matrixStack, this.tracker, n3, n4 + 30, 2, 0);
        WorldLoadProgressScreen.drawCenteredString(matrixStack, this.font, string, n3, n4 - 4 - 30, 0xFFFFFF);
    }

    public static void func_238625_a_(MatrixStack matrixStack, TrackingChunkStatusListener trackingChunkStatusListener, int n, int n2, int n3, int n4) {
        int n5 = n3 + n4;
        int n6 = trackingChunkStatusListener.getDiameter();
        int n7 = n6 * n5 - n4;
        int n8 = trackingChunkStatusListener.func_219523_d();
        int n9 = n8 * n5 - n4;
        int n10 = n - n9 / 2;
        int n11 = n2 - n9 / 2;
        int n12 = n7 / 2 + 1;
        int n13 = -16772609;
        if (n4 != 0) {
            WorldLoadProgressScreen.fill(matrixStack, n - n12, n2 - n12, n - n12 + 1, n2 + n12, -16772609);
            WorldLoadProgressScreen.fill(matrixStack, n + n12 - 1, n2 - n12, n + n12, n2 + n12, -16772609);
            WorldLoadProgressScreen.fill(matrixStack, n - n12, n2 - n12, n + n12, n2 - n12 + 1, -16772609);
            WorldLoadProgressScreen.fill(matrixStack, n - n12, n2 + n12 - 1, n + n12, n2 + n12, -16772609);
        }
        for (int i = 0; i < n8; ++i) {
            for (int j = 0; j < n8; ++j) {
                ChunkStatus chunkStatus = trackingChunkStatusListener.getStatus(i, j);
                int n14 = n10 + i * n5;
                int n15 = n11 + j * n5;
                WorldLoadProgressScreen.fill(matrixStack, n14, n15, n14 + n3, n15 + n3, COLORS.getInt(chunkStatus) | 0xFF000000);
            }
        }
    }

    private static void lambda$static$0(Object2IntOpenHashMap object2IntOpenHashMap) {
        object2IntOpenHashMap.defaultReturnValue(0);
        object2IntOpenHashMap.put(ChunkStatus.EMPTY, 0x545454);
        object2IntOpenHashMap.put(ChunkStatus.STRUCTURE_STARTS, 0x999999);
        object2IntOpenHashMap.put(ChunkStatus.STRUCTURE_REFERENCES, 6250897);
        object2IntOpenHashMap.put(ChunkStatus.BIOMES, 8434258);
        object2IntOpenHashMap.put(ChunkStatus.NOISE, 0xD1D1D1);
        object2IntOpenHashMap.put(ChunkStatus.SURFACE, 7497737);
        object2IntOpenHashMap.put(ChunkStatus.CARVERS, 7169628);
        object2IntOpenHashMap.put(ChunkStatus.LIQUID_CARVERS, 3159410);
        object2IntOpenHashMap.put(ChunkStatus.FEATURES, 2213376);
        object2IntOpenHashMap.put(ChunkStatus.LIGHT, 0xCCCCCC);
        object2IntOpenHashMap.put(ChunkStatus.SPAWN, 15884384);
        object2IntOpenHashMap.put(ChunkStatus.HEIGHTMAPS, 0xEEEEEE);
        object2IntOpenHashMap.put(ChunkStatus.FULL, 0xFFFFFF);
    }
}

