/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import java.awt.Graphics2D;

import cafe.kagu.kagu.eventBus.Event;
import cafe.kagu.kagu.ui.ghost.GhostUi;

/**
 * @author DistastefulBannock
 *
 */
public class EventRenderObs extends Event {
	
	private Graphics2D graphics;
	private GhostUi ghostUi;
	
	/**
	 * @param eventPosition The position of the event
	 * @param graphics The graphics object
	 * @param ghostUi The ghost ui object
	 */
	public EventRenderObs(EventPosition eventPosition, Graphics2D graphics, GhostUi ghostUi) {
		super(eventPosition);
		this.graphics = graphics;
		this.ghostUi = ghostUi;
	}
	
	/**
	 * @return the graphics
	 */
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	/**
	 * @return the ghostUi
	 */
	public GhostUi getGhostUi() {
		return ghostUi;
	}
	
}
