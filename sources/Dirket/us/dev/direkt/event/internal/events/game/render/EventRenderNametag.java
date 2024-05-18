package us.dev.direkt.event.internal.events.game.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventRenderNametag extends CancellableEvent {
    private final RenderManager renderManager;
    private final Entity renderEntity;
    private ITextComponent entityName;

    public EventRenderNametag(RenderManager renderManager, Entity renderEntity, ITextComponent entityName) {
        this.renderManager = renderManager;
        this.renderEntity = renderEntity;
        this.entityName = entityName;
    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    public Entity getRenderEntity() {
        return this.renderEntity;
    }

    public ITextComponent getEntityName() {
        return this.entityName;
    }

    public void setEntityName(ITextComponent entityName) {
        this.entityName = entityName;
    }
}
