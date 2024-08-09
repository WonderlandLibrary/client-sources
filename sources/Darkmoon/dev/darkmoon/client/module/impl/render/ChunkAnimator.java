package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.render.EventRenderChunk;
import dev.darkmoon.client.event.render.EventRenderChunkContainer;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;

import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ModuleAnnotation(name = "ChunkAnimator", category = Category.RENDER)
public class ChunkAnimator extends Module {
    private final WeakHashMap<RenderChunk, AtomicLong> renderChunkMap = new WeakHashMap<>();

    private double easeOutCubic(double t) {
        return (--t) * t * t + 1;
    }

    @EventTarget
    private void onRenderChunk(EventRenderChunk event) {
        if (mc.player != null && mc.world != null) {
            if (!renderChunkMap.containsKey(event.getRenderChunk())) {
                renderChunkMap.put(event.getRenderChunk(), new AtomicLong(-1L));
            }
        }
    }

    @EventTarget
    private void onRenderChunkContainer(EventRenderChunkContainer event) {
        if (renderChunkMap.containsKey(event.getRenderChunk())) {
            AtomicLong timeAlive = renderChunkMap.get(event.getRenderChunk());
            long timeClone = timeAlive.get();
            if (timeClone == -1L) {
                timeClone = System.currentTimeMillis();
                timeAlive.set(timeClone);
            }

            long timeDifference = System.currentTimeMillis() - timeClone;
            if (timeDifference <= 600F) {
                double chunkY = event.getRenderChunk().getPosition().getY();
                double offsetY = chunkY * easeOutCubic(timeDifference / 600F);
                GlStateManager.translate(0.0D, -chunkY + offsetY, 0.0D);
            }
        }
    }
}
