package net.minecraft.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.eventBus.impl.EventMouseDeltasUpdate;

public class MouseHelper
{
    /** Mouse delta X this frame */
    public int deltaX;

    /** Mouse delta Y this frame */
    public int deltaY;

    /**
     * Grabs the mouse cursor it doesn't move and isn't seen.
     */
    public void grabMouseCursor()
    {
        Mouse.setGrabbed(true);
        this.deltaX = 0;
        this.deltaY = 0;
    }

    /**
     * Ungrabs the mouse cursor so it can be moved and set it to the center of the screen
     */
    public void ungrabMouseCursor()
    {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange()
    {
    	EventMouseDeltasUpdate eventMouseDeltasUpdatePre = new EventMouseDeltasUpdate(EventPosition.PRE, Mouse.getDX(), Mouse.getDY());
    	eventMouseDeltasUpdatePre.post();
    	if (eventMouseDeltasUpdatePre.isCanceled()) {
    		deltaX = 0;
    		deltaY = 0;
    		return;
    	}
        this.deltaX = eventMouseDeltasUpdatePre.getDeltaX();
        this.deltaY = eventMouseDeltasUpdatePre.getDeltaY();
        EventMouseDeltasUpdate eventMouseDeltasUpdatePost = new EventMouseDeltasUpdate(EventPosition.PRE, deltaX, deltaY);
        eventMouseDeltasUpdatePost.post();
    }
}
