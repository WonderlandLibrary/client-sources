package wtf.shiyeno.events.impl.player;

import net.minecraft.client.renderer.entity.PlayerRenderer;
import wtf.shiyeno.events.Event;

public class EventModelRender extends Event {

    public PlayerRenderer renderer;
    private Runnable entityRenderer;

    public EventModelRender(PlayerRenderer renderer, Runnable entityRenderer) {
        this.renderer = renderer;
        this.entityRenderer = entityRenderer;
    }

    public void render() {
        entityRenderer.run();
    }

}
