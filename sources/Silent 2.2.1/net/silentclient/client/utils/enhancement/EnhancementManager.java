package net.silentclient.client.utils.enhancement;

import java.util.HashMap;
import java.util.Map;

import net.silentclient.client.utils.enhancement.text.EnhancedFontRenderer;

public class EnhancementManager {
	private static final EnhancementManager instance = new EnhancementManager();
    private final Map<Class<? extends Enhancement>, Enhancement> enhancementMap = new HashMap<>();

    public EnhancementManager() {
        enhancementMap.put(EnhancedFontRenderer.class, new EnhancedFontRenderer());
    }

    public void tick() {
        for (Map.Entry<Class<? extends Enhancement>, Enhancement> entry : enhancementMap.entrySet()) {
            entry.getValue().tick();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Enhancement> T getEnhancement(Class<T> enhancement) {
        return (T) enhancementMap.get(enhancement);
    }

    public static EnhancementManager getInstance() {
        return instance;
    }
}
