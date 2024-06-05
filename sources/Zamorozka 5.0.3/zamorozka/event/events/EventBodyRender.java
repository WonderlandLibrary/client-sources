package zamorozka.event.events;

import zamorozka.event.Event;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;

public class EventBodyRender extends Event {
    private Entity entity;
    private RenderLivingBase renderer;
    private boolean ispre;

    public EventBodyRender(Entity entity, final RenderLivingBase renderer, boolean pre) {
        this.entity = entity;
        this.renderer = renderer;
        this.ispre = pre;
    }

    public EventBodyRender(Entity entity, boolean pre) {
        this.entity = entity;
        this.ispre = pre;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public RenderLivingBase getRenderer() {
        return this.renderer;
    }

    public boolean ispre() {
        return ispre;
    }

    public boolean ispost() {
        return !ispre;
    }
}
