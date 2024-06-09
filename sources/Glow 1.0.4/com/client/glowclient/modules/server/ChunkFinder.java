package com.client.glowclient.modules.server;

import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;

public class ChunkFinder extends ModuleContainer
{
    public static final NumberValue red;
    public static nB mode;
    public static final NumberValue green;
    private ChunkPos L;
    public static final NumberValue alpha;
    private static ArrayList<ChunkPos> B;
    public static final NumberValue blue;
    
    @Override
    public void E() {
        ChunkFinder.B.removeAll(ChunkFinder.B);
    }
    
    static {
        red = ValueFactory.M("ChunkFinder", "Red", "Red", 255.0, 1.0, 0.0, 255.0);
        green = ValueFactory.M("ChunkFinder", "Green", "Green", 255.0, 1.0, 0.0, 255.0);
        blue = ValueFactory.M("ChunkFinder", "Blue", "Blue", 255.0, 1.0, 0.0, 255.0);
        alpha = ValueFactory.M("ChunkFinder", "Alpha", "Transparency", 255.0, 1.0, 0.0, 255.0);
        ChunkFinder.mode = ValueFactory.M("ChunkFinder", "Mode", "Rendering source of ChunkFinder", "Flat", "Flat", "Wall");
        ChunkFinder.B = new ArrayList<ChunkPos>();
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final EventRenderWorld eventRenderWorld) throws ConcurrentModificationException {
        if (this.L != null && ChunkFinder.B != null) {
            for (final ChunkPos chunkPos : ChunkFinder.B) {
                if (ChunkFinder.mode.e().equals("Flat")) {
                    Ma.D(chunkPos, ChunkFinder.red.M(), ChunkFinder.green.M(), ChunkFinder.blue.M(), ChunkFinder.alpha.M());
                }
                if (ChunkFinder.mode.e().equals("Wall")) {
                    Ma.M(chunkPos, ChunkFinder.red.M(), ChunkFinder.green.M(), ChunkFinder.blue.M(), ChunkFinder.alpha.M());
                }
            }
        }
    }
    
    public ChunkFinder() {
        super(Category.SERVER, "ChunkFinder", false, -1, "Find newly loaded chunks");
    }
    
    @Override
    public String M() {
        return "";
    }
    
    @SubscribeEvent
    public void M(final EventChunk eventChunk) {
        this.L = eventChunk.getChunk().getPos();
        ChunkFinder.B.add(this.L);
    }
}
