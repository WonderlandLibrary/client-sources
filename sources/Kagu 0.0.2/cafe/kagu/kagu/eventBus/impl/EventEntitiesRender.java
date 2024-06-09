/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

/**
 * @author lavaflowglow
 *
 */
public class EventEntitiesRender extends Event {

	/**
	 * @param eventPosition    The position of the event
	 * @param renderViewEntity The entity to render the view from
	 * @param camera           The camera
	 * @param partialTicks     The partial ticks, used for rendering
	 */
	public EventEntitiesRender(EventPosition eventPosition, Entity renderViewEntity, ICamera camera,
			float partialTicks) {
		super(eventPosition);
		this.renderViewEntity = renderViewEntity;
		this.camera = camera;
		this.partialTicks = partialTicks;
	}

	private Entity renderViewEntity;
	private ICamera camera;
	private float partialTicks;

	/**
	 * @return the renderViewEntity
	 */
	public Entity getRenderViewEntity() {
		return renderViewEntity;
	}

	/**
	 * @return the camera
	 */
	public ICamera getCamera() {
		return camera;
	}

	/**
	 * @return the partialTicks
	 */
	public float getPartialTicks() {
		return partialTicks;
	}

}
