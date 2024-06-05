package zamorozka.modules.ZAMOROZKA;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRenderChunk;
import zamorozka.event.events.EventRenderChunkContainer;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class ChunkAnimator extends Module {
	
    private final WeakHashMap<RenderChunk, AtomicLong> lifespans = new WeakHashMap<>();
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("AnimationDelay", this, 1000, 250, 5000, true));
	}
	
	public ChunkAnimator() {
		super("ChunkAnimator", 0, Category.Zamorozka);
	}
	
    private double easeOutCubic(double t)
    {
        return (--t) * t * t + 1;
    }

    @EventTarget
    public void onChunkRender(EventRenderChunk event) {
        if (mc.player != null) {
            if (!lifespans.containsKey(event.RenderChunk)) {
                lifespans.put(event.RenderChunk, new AtomicLong(-1L));
            }
        }
    }
    
    @EventTarget
    public void onChunk(EventRenderChunkContainer event) {
    	 if (lifespans.containsKey(event.RenderChunk)) {
             AtomicLong timeAlive = lifespans.get(event.RenderChunk);
             long timeClone = timeAlive.get();
             if (timeClone == -1L) {
                 timeClone = System.currentTimeMillis();
                 timeAlive.set(timeClone);
             }

             long timeDifference = System.currentTimeMillis() - timeClone;
             if (timeDifference <= (int)Zamorozka.settingsManager.getSettingByName("AnimationDelay").getValDouble()) {
                 double chunkY = event.RenderChunk.getPosition().getY();
                 double offsetY = chunkY / (int)Zamorozka.settingsManager.getSettingByName("AnimationDelay").getValDouble() * timeDifference;
                 GlStateManager.translate(0.0, -chunkY + offsetY, 0.0);
             }
    	 }
    }
}