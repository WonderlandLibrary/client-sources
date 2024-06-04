package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import org.lwjgl.LWJGLException;

abstract interface AWTCanvasImplementation
{
  public abstract PeerInfo createPeerInfo(Canvas paramCanvas, PixelFormat paramPixelFormat, ContextAttribs paramContextAttribs)
    throws LWJGLException;
  
  public abstract GraphicsConfiguration findConfiguration(GraphicsDevice paramGraphicsDevice, PixelFormat paramPixelFormat)
    throws LWJGLException;
}
